package es.uniovi.eii.myapplication.ui.profile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import es.uniovi.eii.myapplication.MainActivity;
import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.modelo.Usuario;

public class InfoFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile_biografia, container, false);

        final TextView biografia = root.findViewById(R.id.biografiaInfoFragment);
        final TextView nombre = root.findViewById(R.id.nombreInfoFragment);

        Bundle args = getArguments();
        Usuario seleccionado = args.getParcelable("usuario");

        nombre.setText(seleccionado.getNombre());
        biografia.setText(seleccionado.getBiografia());

        return root;
    }

}
