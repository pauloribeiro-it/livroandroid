package hellomaterial.paulo.ribeiro.it.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import hellomaterial.paulo.ribeiro.it.CarrosApplication;
import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.activity.CarroActivity;
import hellomaterial.paulo.ribeiro.it.activity.MapaActivity;
import hellomaterial.paulo.ribeiro.it.activity.VideoActivity;
import hellomaterial.paulo.ribeiro.it.domain.Carro;
import hellomaterial.paulo.ribeiro.it.domain.CarroDB;
import hellomaterial.paulo.ribeiro.it.fragments.dialog.DeletarCarroDialog;
import hellomaterial.paulo.ribeiro.it.fragments.dialog.EditarCarroDialog;
import livroandroid.lib.utils.IntentUtils;

/**
 * Created by paulo on 05/07/17.
 */

public class CarroFragment extends BaseFragment {
    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro,container,false);
        carro = getArguments().getParcelable("carro");
        setHasOptionsMenu(true);
        view.findViewById(R.id.imgPlayVideo).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showVideo(carro.urlVideo,v);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTextString(R.id.tDesc,carro.desc);
        final ImageView imgView = (ImageView) getView().findViewById(R.id.img);
        Picasso.with(getContext()).load(carro.urlFoto).fit().into(imgView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag_carro,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.ic_action_edit){
            EditarCarroDialog.show(getFragmentManager(),carro, new EditarCarroDialog.Callback(){
                @Override
                public void onCarroUpdated(Carro carro) {
                    toast("Carro ["+carro.nome+"] atualizado");
                    CarroDB db = new CarroDB(getContext());
                    db.save(carro);

                    CarroActivity a = (CarroActivity) getActivity();
                    a.setTitle(carro.nome);
                    CarrosApplication.getInstance().getBus().post("refresh");
                }
            });
            return true;
        }else if(item.getItemId() == R.id.ic_action_delete){
            DeletarCarroDialog.show(getFragmentManager(),new DeletarCarroDialog.Callback(){
                public void onClickYes() {
                    toast("Carro ["+carro.nome+"] deletado.");
                    CarroDB db = new CarroDB(getActivity());
                    db.delete(carro);
                    getActivity().finish();
                    CarrosApplication.getInstance().getBus().post("refresh");
                }
            });
            return true;
        }else if(item.getItemId() == R.id.ic_action_share){
            toast("Compartilhar");
        }else if(item.getItemId() == R.id.ic_action_maps){
            Intent intent = new Intent(getContext(), MapaActivity.class);
            intent.putExtra("carro",carro);
            startActivity(intent);
        }else if(item.getItemId() == R.id.ic_action_video){
            final String url = carro.urlVideo;
            View menuItemView = getActivity().findViewById(item.getItemId());
            if(menuItemView != null && url != null){
                    showVideo(url,menuItemView);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showVideo(final String url, View ancoraView){
        if(url != null && ancoraView != null){
            PopupMenu popupMenu = new PopupMenu(getActionBar().getThemedContext(),ancoraView);
            popupMenu.inflate(R.menu.menu_popup_video);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId() == R.id.action_video_browser){
                        IntentUtils.openBrowser(getContext(),url);
                    }else if(item.getItemId() == R.id.action_video_player){
                        IntentUtils.showVideo(getContext(),url);
                    }else if(item.getItemId() == R.id.action_video_videoview){
                        Intent intent = new Intent(getContext(), VideoActivity.class);
                        intent.putExtra("carro", carro);
                        startActivity(intent);
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    }
}
