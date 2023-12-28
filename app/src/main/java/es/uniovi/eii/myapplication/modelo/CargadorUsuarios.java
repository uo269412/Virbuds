package es.uniovi.eii.myapplication.modelo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CargadorUsuarios {

    public CargadorUsuarios() {}

    public  List<Usuario> cargarUsuarios() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listaUsuarios.add(document.toObject(Usuario.class));
                            }
                        } else {
                            Log.d("Error", "Error consiguiendo los usuarios: ", task.getException());
                        }
                    }
                });
        return listaUsuarios;
    }

    public List<RelacionSeguido> cargarSeguidos() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final List<RelacionSeguido> listaRelaciones= new ArrayList<RelacionSeguido>();
        db.collection("seguidos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listaRelaciones.add(document.toObject(RelacionSeguido.class));
                            }
                        } else {
                            Log.d("Error", "Error consiguiendo las relaciones: ", task.getException());
                        }
                    }
                });
        return listaRelaciones;
    }

    public void addSeguido(RelacionSeguido seguido){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("seguidos").document(seguido.nombreDocumento()).set(seguido);
    }

    public void removeSeguido(RelacionSeguido seguido) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("seguidos").document(seguido.nombreDocumento())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

}
