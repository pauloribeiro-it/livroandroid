package hellomaterial.paulo.ribeiro.it.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.activity.CarroActivity;
import hellomaterial.paulo.ribeiro.it.adapter.CarroAdapter;
import hellomaterial.paulo.ribeiro.it.domain.Carro;
import hellomaterial.paulo.ribeiro.it.domain.CarroService;

public class CarrosFragment extends livroandroid.lib.fragment.BaseFragment {

    private int tipo;
    protected RecyclerView recyclerView;
    private List<Carro> carros;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carros, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros();
    }

    private void taskCarros(){
        new GetCarrosTask().execute();
    }

    private CarroAdapter.CarrosOnClickListener onClickCarro(){
        return new CarroAdapter.CarrosOnClickListener(){
            @Override
            public void onClickCarro(View view, int idx) {
                Carro c = carros.get(idx);
                Intent intent = new Intent(getContext(), CarroActivity.class);
                intent.putExtra("carro", c);
                startActivity(intent);
            }
        };
    }

    private class GetCarrosTask extends AsyncTask<Void,Void,List<Carro>>{
        @Override
        protected List<Carro> doInBackground(Void... params) {
            try {
                return CarroService.getCarros(getContext(),tipo);
            } catch (IOException e) {
                Log.e("livroandroid",e.getMessage(),e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Carro> carros) {
            if(carros != null){
                CarrosFragment.this.carros = carros;
                recyclerView.setAdapter(new CarroAdapter(getContext(),carros, onClickCarro()));
            }

        }
    }
}
