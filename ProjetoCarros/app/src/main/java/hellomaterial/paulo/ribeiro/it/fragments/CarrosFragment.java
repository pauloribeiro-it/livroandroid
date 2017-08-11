package hellomaterial.paulo.ribeiro.it.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hellomaterial.paulo.ribeiro.it.CarrosApplication;
import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.activity.CarroActivity;
import hellomaterial.paulo.ribeiro.it.adapter.CarroAdapter;
import hellomaterial.paulo.ribeiro.it.domain.Carro;
import hellomaterial.paulo.ribeiro.it.domain.CarroDB;
import hellomaterial.paulo.ribeiro.it.domain.CarroService;
import livroandroid.lib.utils.AndroidUtils;
import livroandroid.lib.utils.IOUtils;
import livroandroid.lib.utils.SDCardUtils;

public class CarrosFragment extends livroandroid.lib.fragment.BaseFragment {

    private int tipo;
    protected RecyclerView recyclerView;
    private List<Carro> carros;
    private SwipeRefreshLayout swipeLayout;
    private ActionMode actionMode;
//    private Intent shareIntent;

    public static CarrosFragment newInstance(int tipo){
        Bundle args = new Bundle();
        args.putInt("tipo",tipo);
        CarrosFragment f = new CarrosFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.tipo = getArguments().getInt("tipo");
        }
        CarrosApplication.getInstance().getBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carros, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(R.color.refresh_progress_1,
                                            R.color.refresh_progress_2,
                                            R.color.refresh_progress_3);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros(false);
    }

    private void taskCarros(boolean pullToRefresh){
       startTask("carros",new GetCarrosTask(pullToRefresh),pullToRefresh?R.id.swipeToRefresh:R.id.progress);
    }

    private CarroAdapter.CarrosOnClickListener onClickCarro(){
        return new CarroAdapter.CarrosOnClickListener(){
            @Override
            public void onClickCarro(View view, int idx) {
                Carro c = carros.get(idx);
                if(actionMode == null){
                    Intent intent = new Intent(getContext(), CarroActivity.class);
                    intent.putExtra("carro", c);
                    startActivity(intent);
                }else{
                    c.selected = !c.selected;
                    updateActionModeTitle();
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

            }

            @Override
            public void onLongClickCarro(View view, int idx) {
                if(actionMode != null){
                    return;
                }

                actionMode = getAppCompatActivity().startSupportActionMode(getActionModeCallback());
                Carro c = carros.get(idx);
                c.selected = true;
                recyclerView.getAdapter().notifyDataSetChanged();
                updateActionModeTitle();
//                toast("Clicou e segurou: "+c.nome);
            }
        };
    }

    private void updateActionModeTitle(){
        if(actionMode != null){
            actionMode.setTitle("Selecione os carros.");
            actionMode.setSubtitle(null);
            List<Carro> selectedCarros = getSelectedCarros();
            if(selectedCarros.size() == 1){
                actionMode.setSubtitle("1 carro selecionado");
            }else if(selectedCarros.size() > 1){
                actionMode.setSubtitle(selectedCarros.size()+" carros selecionados");
            }
//            updateShareIntent(selectedCarros);
        }
    }

//    private void updateShareIntent(List<Carro> selectedCarros){
//        if(shareIntent != null){
//            shareIntent.putExtra(Intent.EXTRA_TEXT,"Carros: "+selectedCarros);
//        }
//    }

    private List<Carro> getSelectedCarros(){
        List<Carro> list = new ArrayList<>();
        for(Carro c:carros){
            if(c.selected){
                list.add(c);
            }
        }
        return list;
    }

    private class GetCarrosTask implements TaskListener<List<Carro>>{
        private boolean refresh;
        public GetCarrosTask(boolean refresh){
            this.refresh = refresh;
        }
        @Override
        public List<Carro> execute() throws Exception {
            return CarroService.getCarros(getContext(),tipo,refresh);
        }

        @Override
        public void updateView(List<Carro> carros) {
            if(carros != null){
                CarrosFragment.this.carros = carros;
                recyclerView.setAdapter(new CarroAdapter(getContext(),carros,onClickCarro()));
            }
        }

        @Override
        public void onError(Exception exception) {
            alert("Ocorreu algum erro ao buscar os dados.");
        }

        @Override
        public void onCancelled(String cod) {

        }
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener(){
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AndroidUtils.isNetworkAvailable(getContext())){
                    taskCarros(true);
                }else{
                    swipeLayout.setRefreshing(false);
                    snack(recyclerView,R.string.msg_error_conexao_indisponivel);
                }

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CarrosApplication.getInstance().getBus().unregister(this);
    }

    @Subscribe
    public void onBusAtualizar(String refresh){
        taskCarros(false);
    }

    private ActionMode.Callback getActionModeCallback(){
        return new ActionMode.Callback(){
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_frag_carros_context,menu);
//                MenuItem shareItem = menu.findItem(R.id.action_share);
//                ShareActionProvider share = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
//                shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/*");
//                share.setShareIntent(shareIntent);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                List<Carro> selectedCarros = getSelectedCarros();
                if(item.getItemId() == R.id.ic_action_delete){
                    CarroDB db = new CarroDB(getContext());
                    try{
                        for(Carro c:selectedCarros){
                            db.delete(c);
                            carros.remove(c);
                        }
                    }finally{
                        db.close();
                    }
                    snack(recyclerView,"Carros excluídos com sucesso.");
                }else if(item.getItemId() == R.id.ic_action_share){
                    toast("Compartilhar "+selectedCarros);
                }else if(item.getItemId() == R.id.action_share){
                    startTask("compartilhar",new CompartilharTask(selectedCarros));
                }
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
                for(Carro c:carros){
                    c.selected = false;
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        };
    }

    private class CompartilharTask implements TaskListener{
        ArrayList<Uri> imageUris = new ArrayList<>();
        private final List<Carro> selectedCarros;

        public CompartilharTask(List<Carro> selectedCarros){
            this.selectedCarros = selectedCarros;
        }

        @Override
        public Object execute() throws Exception {
            if(selectedCarros != null){
                for(Carro c:selectedCarros){
                    String url = c.urlFoto;
                    String fileName = url.substring(url.lastIndexOf("/"));
                    File file = SDCardUtils.getPrivateFile(getContext(),"carro",fileName);
                    IOUtils.downloadToFile(c.urlFoto,file);
                    imageUris.add(Uri.fromFile(file));
                }
            }
            return null;
        }

        @Override
        public void updateView(Object response) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,imageUris);
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent,"Enviar Carros"));
        }

        @Override
        public void onError(Exception exception) {
            alert("Ocorreu algum erro ao compartilhar.");
        }

        @Override
        public void onCancelled(String cod) { }
    }

}
