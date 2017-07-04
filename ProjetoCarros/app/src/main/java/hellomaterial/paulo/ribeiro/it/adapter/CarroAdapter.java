package hellomaterial.paulo.ribeiro.it.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.R.*;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.domain.Carro;

/**
 * Created by paulo on 03/07/17.
 */

public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.CarrosViewHolder> {

    protected static final String TAG = "livroandroid";
    private final List<Carro> carros;
    private final Context context;
    private CarrosOnClickListener carroOnClickListener;

    public CarroAdapter(Context context, List<Carro> carros, CarrosOnClickListener carroOnClickListener){
        this.context = context;
        this.carros = carros;
        this.carroOnClickListener = carroOnClickListener;
    }

    @Override
    public int getItemCount(){
        return this.carros != null?this.carros.size():0;
    }

    @Override
    public CarrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_carro,parent,false);
        CarrosViewHolder holder = new CarrosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CarrosViewHolder holder, final int position) {
        Carro c = carros.get(position);
        holder.tNome.setText(c.nome);
        holder.progress.setVisibility(View.VISIBLE);
        Picasso.with(context).load(c.urlFoto).fit().into(holder.img,new com.squareup.picasso.Callback(){
            @Override
            public void onSuccess() {
                holder.progress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.progress.setVisibility(View.GONE);
            }
        });

        if(carroOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    carroOnClickListener.onClickCarro(holder.itemView,position);
                }
            });
        }
    }

    public interface CarrosOnClickListener{
        public void onClickCarro(View view, int idx);
    }

    public static class CarrosViewHolder extends RecyclerView.ViewHolder{
        public TextView tNome;
        ImageView img;
        ProgressBar progress;
        CardView cardView;

        public CarrosViewHolder(View view){
            super(view);
            tNome = (TextView) view.findViewById(R.id.text);
            progress = (ProgressBar) view.findViewById(R.id.progressImg);
            img = (ImageView) view.findViewById(R.id.img);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }
}
