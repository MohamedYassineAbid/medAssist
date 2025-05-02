package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.Appointment.AppointmentActivity;
import com.example.myapplication.CalendarActivity;
import com.example.myapplication.Emergency.EmergencyContactsActivity;
import com.example.myapplication.Medication.MedicationListActivity;
import com.example.myapplication.Prescription.PrescriptionListActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setupNavigationDrawer(toolbar);
        setupCardViewListeners();
    }

    private void setupNavigationDrawer(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_account_info) {
                showAccountInfo();
            } else if (id == R.id.nav_account_security) {
                showAccountSecurity();
            } else if (id == R.id.nav_logout) {
                logout();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void setupCardViewListeners() {
        setupCardNavigation(R.id.buttonRendezVous, AppointmentActivity.class);
        setupCardNavigation(R.id.buttonpill, MedicationListActivity.class);
        setupCardNavigation(R.id.buttonemergency, EmergencyContactsActivity.class);
        setupCardNavigation(R.id.buttonpresreption, PrescriptionListActivity.class);
        setupCardNavigation(R.id.buttoncalendar, CalendarActivity.class); // New calendar button
    }

    private void setupCardNavigation(int cardViewId, Class<?> activityClass) {
        CardView cardView = findViewById(cardViewId);
        cardView.setOnClickListener(v -> navigateTo(activityClass));
    }

    private void navigateTo(Class<?> activityClass) {
        try {
            Intent intent = new Intent(MainActivity.this, activityClass);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error navigating to activity", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAccountInfo() {
        Toast.makeText(this, getString(R.string.account_info_selected), Toast.LENGTH_SHORT).show();
    }

    private void showAccountSecurity() {
        Toast.makeText(this, getString(R.string.account_security_selected), Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, getString(R.string.logged_out), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}