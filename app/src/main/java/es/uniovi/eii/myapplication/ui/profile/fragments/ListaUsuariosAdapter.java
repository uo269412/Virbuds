package es.uniovi.eii.myapplication.ui.profile.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.modelo.Usuario;

public class ListaUsuariosAdapter extends RecyclerView.Adapter<ListaUsuariosAdapter.UsuarioViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Usuario item);
    }

    private List<Usuario> listaUsuarios;
    private final OnItemClickListener listener;

    public ListaUsuariosAdapter(List<Usuario> listaUsuarios, OnItemClickListener listener) {
        this.listaUsuarios = listaUsuarios;
        this.listener = listener;
    }

    /* Indicamos el layout a "inflar" para usar en la vista
     */
    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creamos la vista con el layout para un elemento
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.linea_recycler_view_peliculas, parent, false);
        return new UsuarioViewHolder(itemView);
    }

    /** Asocia el contenido a los componentes de la vista,
     * concretamente con nuestro ActorViewHolder que recibimos como parámetro
     */
    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        // Extrae de la lista el elemento indicado por posición
        Usuario usuario= listaUsuarios.get(position);
        Log.i("Lista","Visualiza elemento: "+usuario);
        // llama al método de nuestro holder para asignar valores a los componentes
        // además, pasamos el listener del evento onClick
        holder.bindUser(usuario, listener);
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }


    /*Clase interna que define los compoonentes de la vista*/
    public static class UsuarioViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre_usuario;
        private TextView descripcion_usuario;
        private ImageView imagen_usuario;

        public UsuarioViewHolder(View itemView) {
            super(itemView);

            nombre_usuario= (TextView)itemView.findViewById(R.id.nombre_usuarioFragment);
            descripcion_usuario = (TextView)itemView.findViewById(R.id.descripcion_usuarioFragment);
            imagen_usuario= (ImageView)itemView.findViewById(R.id.imagen_usuarioFragment);
        }

        // asignar valores a los componentes
        public void bindUser(final Usuario usuario, final OnItemClickListener listener) {
            nombre_usuario.setText(usuario.getNombre());
            descripcion_usuario.setText(usuario.getBiografia());
            //Picasso.get().load(usuario.getFotoperfil()).into(imagen_usuario);

            Picasso.get().load(usuario.getFotoperfil())
                    .into(imagen_usuario, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap imageBitmap = ((BitmapDrawable) imagen_usuario.getDrawable()).getBitmap();
                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(itemView.getResources(), imageBitmap);
                            imageDrawable.setCircular(true);
                            imageDrawable.setCornerRadius(Math.min(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                            imagen_usuario.setImageDrawable(imageDrawable);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(usuario);
                }
            });
        }




    }

}