package es.uniovi.eii.myapplication.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

public class RelacionSeguido implements Parcelable {
    String seguido;
    String seguidor;

    public RelacionSeguido() {
    }

    public RelacionSeguido(String seguido, String seguidor) {
        this.seguido = seguido;
        this.seguidor = seguidor;
    }

    protected RelacionSeguido(Parcel in) {
        seguido = in.readString();
        seguidor = in.readString();
    }

    public static final Creator<RelacionSeguido> CREATOR = new Creator<RelacionSeguido>() {
        @Override
        public RelacionSeguido createFromParcel(Parcel source) {
            return new RelacionSeguido(source);
        }

        @Override
        public RelacionSeguido[] newArray(int size) {
            return new RelacionSeguido[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(seguido);
        dest.writeString(seguidor);
    }

    public String getSeguido() {
        return seguido;
    }

    public String getSeguidor() {
        return seguidor;
    }

    @Override
    public String toString() {
        String result = "SEGUIDO - " + getSeguido() + ", SEGUIDOR - " + getSeguidor();
        return result;
    }

    public void setSeguido(String seguido) {
        this.seguido = seguido;
    }

    public void setSeguidor(String seguidor) {
        this.seguidor = seguidor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelacionSeguido that = (RelacionSeguido) o;
        return Objects.equal(seguido, that.seguido) &&
                Objects.equal(seguidor, that.seguidor);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(seguido, seguidor);
    }

    public String nombreDocumento() {
        return this.hashCode() + seguido.substring(0,1);
    }
}