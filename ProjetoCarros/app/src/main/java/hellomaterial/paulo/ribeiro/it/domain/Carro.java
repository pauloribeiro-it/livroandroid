package hellomaterial.paulo.ribeiro.it.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by paulo on 03/07/17.
 */

public class Carro implements Parcelable{
    private static final long serialVersionUID = 6601006766832473959L;
    public long id;
    public String tipo;
    public String nome;
    public String desc;
    public String urlFoto;
    public String urlInfo;
    public String urlVideo;
    public String latitude;
    public String longitude;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(this.tipo);
        dest.writeString(this.nome);
        dest.writeString(this.desc);
        dest.writeString(this.urlFoto);
        dest.writeString(this.urlInfo);
        dest.writeString(this.urlVideo);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    public void readFromParcel(Parcel parcel){
        this.id = parcel.readLong();
        this.tipo = parcel.readString();
        this.nome = parcel.readString();
        this.desc = parcel.readString();
        this.urlFoto = parcel.readString();
        this.urlInfo = parcel.readString();
        this.urlVideo = parcel.readString();
        this.latitude = parcel.readString();
        this.longitude = parcel.readString();
    }

    public static final Parcelable.Creator<Carro> CREATOR = new Parcelable.Creator<Carro>(){

        @Override
        public Carro createFromParcel(Parcel source) {
            Carro c = new Carro();
            c.readFromParcel(source);
            return c;
        }

        @Override
        public Carro[] newArray(int size) {
            return new Carro[size];
        }
    };

    @Override
    public String toString() {
        return "Carro{"+"nome='"+nome+"'}";
    }
}
