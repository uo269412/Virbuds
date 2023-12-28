package es.uniovi.eii.myapplication.ui.companeros;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import es.uniovi.eii.myapplication.MainActivity;
import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.dummy.DummyContent.DummyItem;
import es.uniovi.eii.myapplication.modelo.CargadorUsuarios;
import es.uniovi.eii.myapplication.modelo.RelacionSeguido;
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.profile.fragments.ListaUsuariosAdapter;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCardRecyclerViewAdapter extends RecyclerView.Adapter<MyCardRecyclerViewAdapter.ViewHolder> {

    private final List<Usuario> mValues;
    Usuario user;
    private View.OnClickListener listener;

    public MyCardRecyclerViewAdapter(List<Usuario> items, View.OnClickListener listener) {
        mValues = items;
        user = MainActivity.getUser();
        this.listener = listener;
    }

    public interface OnClickListener {
        void onItemClick(Usuario item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nombre.setText(mValues.get(position).getNombre());
        holder.usuario.setText(mValues.get(position).getUsuario());
        holder.descripcion.setText(mValues.get(position).getBiografia());
        Picasso.get().load(mValues.get(position).getFotoperfil()).into(holder.profilePhoto);

        if (user.equals(holder.mItem)){
            holder.botonSeguir.setEnabled(false);
        } else {
            for (Usuario u : user.getSeguidos()){
                if (u.equals(holder.mItem)){
                    holder.botonSeguir.setText("Dejar de seguir");
                    break;
                }
            }
        }

        holder.botonSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargadorUsuarios cu = new CargadorUsuarios();

                RelacionSeguido seguido = new RelacionSeguido();
                seguido.setSeguido(holder.mItem.getUsuario());
                seguido.setSeguidor(user.getUsuario());


                if (user.getSeguidos().contains(holder.mItem)) {
                    cu.removeSeguido(seguido);
                    user.removeSeguido(holder.mItem);
                    holder.mItem.removeSeguidor(user);
                    holder.botonSeguir.setText(R.string.seguir_mensaje);
                } else {
                    cu.addSeguido(seguido);
                    user.addSeguido(holder.mItem);
                    holder.mItem.addSeguidor(user);
                    holder.botonSeguir.setText(R.string.dejar_seguir_mensaje);
                }

            }
        });

        holder.botonMD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí cambiar de fragmento a chat privado y crear una conversación con el usuario
                // en el momento que escriba algo
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView profilePhoto;
        public final TextView nombre;
        public final TextView usuario;
        public final TextView descripcion;
        public final Button botonSeguir;
        public final Button botonMD;
        public Usuario mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            profilePhoto = (ImageView) view.findViewById(R.id.imagen_perfil);
            nombre = (TextView) view.findViewById(R.id.nombre_perfil);
            usuario = (TextView) view.findViewById(R.id.id_usuario);
            descripcion = (TextView) view.findViewById(R.id.descripcion);
            botonSeguir = (Button) view.findViewById(R.id.boton_seguir);
            botonMD = (Button) view.findViewById(R.id.boton_MD);

            view.setOnClickListener(listener);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nombre.getText() + "'";
        }
    }
}