package com.hencesimplified.praveenassignment.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.hencesimplified.praveenassignment.R;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_video, R.id.nav_audio)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage("Are you sure, you want to exit?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", (dialog, which) -> ExitActivity(null));
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    public void ExitActivity(View view) {
        Intent ExitIntent = new Intent(Intent.ACTION_MAIN);
        ExitIntent.addCategory(Intent.CATEGORY_HOME);
        ExitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ExitIntent);
    }
}