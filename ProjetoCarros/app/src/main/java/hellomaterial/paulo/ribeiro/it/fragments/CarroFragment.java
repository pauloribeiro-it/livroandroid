package hellomaterial.paulo.ribeiro.it.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.activity.CarroActivity;
import hellomaterial.paulo.ribeiro.it.domain.Carro;
import hellomaterial.paulo.ribeiro.it.domain.CarroDB;
import hellomaterial.paulo.ribeiro.it.fragments.dialog.EditarCarroDialog;

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
            //toast("Editar: "+carro.nome);
            EditarCarroDialog.show(getFragmentManager(),carro, new EditarCarroDialog.Callback(){
                @Override
                public void onCarroUpdated(Carro carro) {
                    toast("Carro ["+carro.nome+"] atualizado");
                    CarroDB db = new CarroDB(getContext());
                    db.save(carro);

                    CarroActivity a = (CarroActivity) getActivity();
                    a.setTitle(carro.nome);
                }
            });
            return true;
        }else if(item.getItemId() == R.id.ic_action_delete){
            toast("Deletar: "+carro.nome);
            return true;
        }else if(item.getItemId() == R.id.ic_action_share){
            toast("Compartilhar");
        }else if(item.getItemId() == R.id.ic_action_maps){
            toast("Mapa");
        }else if(item.getItemId() == R.id.ic_action_video){
            toast("Vídeo");
        }
        return super.onOptionsItemSelected(item);
    }
}
