package es.uniovi.eii.myapplication.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.myapplication.MainActivity;
import es.uniovi.eii.myapplication.R;
import es.uniovi.eii.myapplication.modelo.CargadorUsuarios;
import es.uniovi.eii.myapplication.modelo.RelacionSeguido;
import es.uniovi.eii.myapplication.modelo.Usuario;
import es.uniovi.eii.myapplication.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private List<Usuario> listaUsuarios;
    private List<RelacionSeguido> listaRelaciones;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        CargadorUsuarios cargador = new CargadorUsuarios();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        listaUsuarios = cargador.cargarUsuarios();
        listaRelaciones = cargador.cargarSeguidos();
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    // updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validLogin = false;
                for (Usuario usuario : listaUsuarios) {
                    if (usuario.getUsuario().equals(usernameEditText.getText().toString())) {
                        if (usuario.getContraseña().equals(passwordEditText.getText().toString())) {
                            validLogin = true;
                            break;
                        }
                    }
                }
                if (validLogin) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    ArrayList<Usuario> listaParaActivity = new ArrayList<Usuario>(listaUsuarios);
                    ArrayList<RelacionSeguido> aux = new ArrayList<>(listaRelaciones);
                    bundle.putParcelableArrayList("listaUsuarios", listaParaActivity);
                    bundle.putParcelableArrayList("listaRelaciones", aux);
                    bundle.putString("username", usernameEditText.getText().toString());
                    mainActivity.putExtras(bundle);

                    Log.d("Lista usuarios login", listaParaActivity.toString());
                    Log.d("Lista relaciones login", aux.toString());

                    startActivity(mainActivity);
                } else {
                    usernameEditText.setError("");
                    passwordEditText.setError("");
                    Toast.makeText(getApplicationContext(), "Combinación errónea de datos", Toast.LENGTH_LONG).show();
                }
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                    Bundle bundle = new Bundle();
                    ArrayList<Usuario> listaParaActivity = new ArrayList<Usuario>(listaUsuarios);
                    ArrayList<RelacionSeguido> aux = new ArrayList<>(listaRelaciones);
                    bundle.putParcelableArrayList("listaUsuarios", listaParaActivity);
                    bundle.putParcelableArrayList("listaRelaciones", aux);
                    registerActivity.putExtras(bundle);
                    Log.d("Lista usuarios registro", listaParaActivity.toString());

                    startActivity(registerActivity);
            }
        });

    }


    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
