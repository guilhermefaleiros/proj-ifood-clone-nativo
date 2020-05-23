package com.guifaleiros.ifood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.guifaleiros.ifood.R;
import com.guifaleiros.ifood.helper.FirebaseHelper;
import com.guifaleiros.ifood.helper.UserFirebaseHelper;

public class AuthActivity extends AppCompatActivity {

    private Button mButtonAccess;
    private EditText mFieldEmail, mFieldPassword;
    private Switch mTypeAccess;
    private Switch mTypeUser;
    private LinearLayout mLinearTypeUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initComponents();

        mAuth = FirebaseHelper.getFirebaseAuth();

        //mAuth.signOut();

        checkUserIsSignedIn();

        mTypeAccess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mLinearTypeUser.setVisibility(View.VISIBLE);
                }else{
                    mLinearTypeUser.setVisibility(View.GONE);
                }
            }
        });

        mButtonAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mFieldEmail.getText().toString();
                String password = mFieldPassword.getText().toString();

                if(!email.isEmpty()){
                    if(!password.isEmpty()){
                        if(mTypeAccess.isChecked()){ //Cadastrar usuário
                            mAuth.createUserWithEmailAndPassword(
                                    email, password
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AuthActivity.this,
                                                "Cadastro realizado com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        UserFirebaseHelper.updateUserType(getTypeUser());
                                        openMainScreen(getTypeUser());
                                    } else{
                                        String errorException = "";

                                        try{
                                            throw task.getException();
                                        } catch(FirebaseAuthWeakPasswordException e){
                                            errorException = "Digite uma senha mais forte";
                                        } catch(FirebaseAuthInvalidCredentialsException e){
                                            errorException = "Por favor, digite um email válido";
                                        } catch(FirebaseAuthUserCollisionException e){
                                            errorException = "Esta conta já foi cadastrada";
                                        } catch(Exception e){
                                            errorException = "ao cadastrar usuário: " + e.getMessage();
                                        }

                                        Toast.makeText(AuthActivity.this,
                                                "Erro: " + errorException,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{ //Login
                            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        String type = task.getResult().getUser().getDisplayName();

                                        openMainScreen(type);
                                    } else{

                                        Toast.makeText(AuthActivity.this,
                                            "Erro ao fazer login",
                                            Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else{
                        Toast.makeText(AuthActivity.this, "Preencha a Senha",
                            Toast.LENGTH_SHORT)
                            .show();
                    }
                } else{
                    Toast.makeText(AuthActivity.this, "Preencha o Email",
                        Toast.LENGTH_SHORT)
                        .show();
                }


            }
        });
    }

    private String getTypeUser(){
        return mTypeUser.isChecked() ? "C" : "U";
    }

    private void openMainScreen(String userType){
        if( userType.equals("C")){
            startActivity(new Intent(getApplicationContext(), CompanyActivity.class));
        } else{
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }

    private void initComponents(){
        this.mButtonAccess = findViewById(R.id.buttonAccess);
        this.mFieldEmail = findViewById(R.id.editEmail);
        this.mFieldPassword = findViewById(R.id.editPassword);
        this.mTypeAccess = findViewById(R.id.switchAccess);
        this.mTypeUser = findViewById(R.id.switchTypeUser);
        this.mLinearTypeUser = findViewById(R.id.linearTypeUser);
    }

    private void checkUserIsSignedIn(){
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if( currentUser != null) {
            String type = currentUser.getDisplayName();
            openMainScreen(type);
        }
    }
}
