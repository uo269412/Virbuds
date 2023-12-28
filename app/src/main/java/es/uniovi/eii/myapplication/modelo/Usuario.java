package es.uniovi.eii.myapplication.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Usuario implements Parcelable{
    String usuario;
    String nombre;
    String contraseña; //TODO: Sujeto a cambios
    String biografia;
    String universidad;
    String carrera;
    int curso;
    String fotoperfil;
    List<Usuario> seguidos = new ArrayList<>();
    List<Usuario> seguidores = new ArrayList<>();

    public Usuario() { }

    public Usuario(String usuario, String nombre, String biografia, String universidad, String carrera, int curso, String fotoperfil) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.biografia = biografia;
        this.universidad = universidad;
        this.carrera = carrera;
        this.curso = curso;
        this.fotoperfil = fotoperfil;
    }

    public Usuario(String usuario, String nombre, String contraseña, String biografia, String universidad, String carrera, int curso, String fotoperfil) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.biografia = biografia;
        this.universidad = universidad;
        this.carrera = carrera;
        this.curso = curso;
        this.fotoperfil = fotoperfil;
    }

    public Usuario(String usuario, String nombre, String contraseña, String biografia, String universidad, String carrera, int curso) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.biografia = biografia;
        this.universidad = universidad;
        this.carrera = carrera;
        this.curso = curso;
        this.fotoperfil = "http://lorempixel.com/output/animals-q-c-640-367-2.jpg";
    }

    protected Usuario(Parcel in) {
        usuario = in.readString();
        nombre = in.readString();
        biografia = in.readString();
        universidad = in.readString();
        carrera = in.readString();
        curso = in.readInt();
        fotoperfil = in.readString();
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel source) {
            return new Usuario(source);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(usuario);
        dest.writeString(nombre);
        dest.writeString(biografia);
        dest.writeString(universidad);
        dest.writeString(carrera);
        dest.writeInt(curso);
        dest.writeString(fotoperfil);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public String getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public String getContraseña() {return this.contraseña;}

    public void addSeguidor(Usuario username) {
        if (!seguidores.contains(username)) {
            seguidores.add(username);
        }

    }

    public void removeSeguidor(Usuario username) {
        seguidores.remove(username);
    }

    public void addSeguido(Usuario username) {
        if (!seguidos.contains(username)) {
            seguidos.add(username);
        }

    }

    public void removeSeguido(Usuario username) {
        seguidos.remove(username);
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
    }

    public List<Usuario> getSeguidos() {
        return seguidos;
    }

    public void setSeguidores(List<Usuario> seguidores) {
        this.seguidores = seguidores;
    }

    public void setSeguidos(List<Usuario> seguidos) {
        this.seguidos = seguidos;
    }

    @Override
    public String toString() {
        String result = getUsuario() + " " + getNombre() + " con seguidos [";
        for (Usuario usuario : seguidos) {
            result += usuario.getUsuario() + ",";
        }
        result += "] y con seguidores [";
        for (Usuario usuario : seguidores) {
            result += usuario.getUsuario() + ",";
        }
        result += "]";
        return result;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Usuario) {
            Usuario objeto = (Usuario) obj;
            if (usuario.equals(objeto.getUsuario())) {
                return true;
            }
        }
        return false;
    }
}