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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private MaterialSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseHelper.getFirebaseAuth();
        initComponents();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ifood");
        setSupportActionBar(toolbar);
    }

    private void initComponents(){
        mSearchView = findViewById(R.id.material_search_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);

        MenuItem item = menu.findItem(R.id.menuSearch);
        mSearchView.setMenuItem(item);

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
            case R.id.menuSearch:

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
        startActivity(new Intent(HomeActivity.this, SettingsUserActivity.class));
    }

}
