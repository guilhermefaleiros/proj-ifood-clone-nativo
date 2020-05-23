package com.guifaleiros.ifood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.guifaleiros.ifood.R;
import com.guifaleiros.ifood.helper.FirebaseHelper;

import java.util.zip.Inflater;

public class CompanyActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        mAuth = FirebaseHelper.getFirebaseAuth();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ifood - Empresa");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_company, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuQuit:
                signOut();
                break;
            case R.id.menuConfig:
                openSettings();
                break;
            case R.id.menuNewProduct:
                openNewProduct();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut(){
        try{
            mAuth.signOut();
            finish();
        } catch( Exception e ){
            e.printStackTrace();
        }
    }

    private void openSettings(){
        startActivity(new Intent(CompanyActivity.this, SettingsCompanyActivity.class));
    }

    private void openNewProduct(){
        startActivity(new Intent(CompanyActivity.this, NewProductCompanyActivity.class));
    }
}
