package es.uniovi.eii.myapplication.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.modelo.RelacionSeguido;
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private ArrayList<Usuario> listaUsuarios;
    private Usuario usuarioAModificar;
    private ArrayList<RelacionSeguido> listaRelaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle bundle = getIntent().getExtras();
        this.listaUsuarios = bundle.getParcelableArrayList("listaUsuarios");
        this.listaRelaciones = bundle.getParcelableArrayList("aux");
        this.usuarioAModificar = bundle.getParcelable("usuario");

        TextView textViewCrea= (TextView)findViewById(R.id.creacioncategoria);
        final EditText usuario= (EditText)findViewById(R.id.idUsuario);
        final EditText contraseña= (EditText)findViewById(R.id.contraseñaRegistro);
        final EditText nombre= (EditText)findViewById(R.id.idNombre);
        final EditText biografia= (EditText)findViewById(R.id.idBiografiaRegistro);
        final EditText universidad= (EditText)findViewById(R.id.idUniversidadRegistro);
        final EditText curso= (EditText)findViewById(R.id.idCurso);
        final EditText carrera= (EditText)findViewById(R.id.idCarrera);

        Button btnOk= (Button)findViewById(R.id.btnOk);
        Button btnCancel= (Button)findViewById(R.id.btnCancel);

        if (usuarioAModificar == null) {
            textViewCrea.setText("Creación de nuevo usuario");
        } else {
            textViewCrea.setText("Modificar usuario");
            usuario.setText(usuarioAModificar.getUsuario());
            usuario.setEnabled(false);
            contraseña.setText(usuarioAModificar.getContraseña());
            contraseña.setEnabled(false);
            nombre.setText(usuarioAModificar.getNombre());
            biografia.setText(usuarioAModificar.getBiografia());
            universidad.setText(usuarioAModificar.getUniversidad());
            curso.setText(Integer.toString(usuarioAModificar.getCurso()));
            carrera.setText(usuarioAModificar.getCarrera());
            btnOk.setText("Modificar");
        }

        usuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkUser(usuario.getText().toString())) {
                    Toast.makeText(RegisterActivity.this,"Ese usuario ya existe", Toast.LENGTH_LONG);
                }
            }

            private boolean checkUser(String userId) {
                for (Usuario u: listaUsuarios) {
                    if (u.getUsuario().equals(userId)) {
                        return true;
                    }
                }
                return false;
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                for (Usuario u : listaUsuarios) {
                    if (u.getUsuario().equals(usuarioAModificar.getUsuario().toString())) {
                        u.setNombre(nombre.getText().toString());
                        u.setContraseña(contraseña.getText().toString());
                        u.setBiografia(biografia.getText().toString());
                        u.setUniversidad(universidad.getText().toString());
                        u.setCarrera(carrera.getText().toString());
                        u.setCurso(Integer.parseInt(curso.getText().toString()));
                    }
                }
                Usuario registrado = new Usuario(usuario.getText().toString(), contraseña.getText().toString(), nombre.getText().toString(), biografia.getText().toString(), universidad.getText().toString(), carrera.getText().toString(),Integer.parseInt(curso.getText().toString()));

                if (usuarioAModificar == null) {
                    db.collection("usuarios")
                            .add(registrado)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("DATABASE", "DocumentSnapshot written with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("DATABASE", "Error adding document", e);
                                }
                            });
                } else {

                }
                setResult(RESULT_OK);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (usuarioAModificar == null ) {
            Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(login);
        } else {
            finish();
        }
    }
}