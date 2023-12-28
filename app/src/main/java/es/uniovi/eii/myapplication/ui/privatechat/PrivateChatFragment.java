package es.uniovi.eii.myapplication.ui.privatechat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.Console;

import es.uniovi.eii.myapplication.R;

public class PrivateChatFragment extends Fragment {

    private PrivateChatViewModel privateChatViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        privateChatViewModel =
                ViewModelProviders.of(this).get(PrivateChatViewModel.class);
        View root = inflater.inflate(R.layout.fragment_private_chat, container, false);
        final TextView textView = root.findViewById(R.id.text_private_chat);
        Log.d("algo","Printeame porfa");
        privateChatViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}