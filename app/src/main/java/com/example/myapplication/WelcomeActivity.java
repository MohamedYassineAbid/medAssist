package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button nextButton;
    private Button skipButton;
    private LinearLayout dotsLayout;
    private SlideAdapter slideAdapter;
    private List<SlideAdapter.SlideItem> slideItems;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if this is the first run of the app
        if (!isFirstTime()) {
            startLoginActivity();
            finish();
            return;
        }

        setContentView(R.layout.welcome_screen);

        viewPager = findViewById(R.id.viewPager);
        nextButton = findViewById(R.id.nextButton);
        skipButton = findViewById(R.id.skipButton);
        dotsLayout = findViewById(R.id.dotsLayout);

        // Prepare slides data
        prepareSlideItems();

        // Set up the adapter
        slideAdapter = new SlideAdapter(this, slideItems);
        viewPager.setAdapter(slideAdapter);

        // Set up the dots indicator
        setupDots(0);

        // Set ViewPager change listener
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setupDots(position);

                if (position == slideItems.size() - 1) {
                    // Last slide - change button text to "Get Started"
                    nextButton.setText("Get Started");
                } else {
                    nextButton.setText("Next");
                }
            }
        });

        // Set button click listeners
        nextButton.setOnClickListener(v -> {
            int currentPosition = viewPager.getCurrentItem();
            if (currentPosition < slideItems.size() - 1) {
                // Go to next slide
                viewPager.setCurrentItem(currentPosition + 1);
            } else {
                // On last slide, go to login
                startLoginActivity();
            }
        });

        skipButton.setOnClickListener(v -> startLoginActivity());
    }

    private void prepareSlideItems() {
        slideItems = new ArrayList<>();

        // Add your slides here
        slideItems.add(new SlideAdapter.SlideItem(
                R.drawable.doctor,
                "Welcome to MedAssist",
                "Your personal health companion that helps you manage medications, appointments, and more."
        ));

        slideItems.add(new SlideAdapter.SlideItem(
                R.drawable.appointments,
                "Track Your Appointments",
                "Never miss a doctor's appointment again. Set reminders and get notifications."
        ));

        slideItems.add(new SlideAdapter.SlideItem(
                R.drawable.prescription,
                "Manage Prescriptions",
                "Keep all your prescriptions in one place for easy access and refills."
        ));

        slideItems.add(new SlideAdapter.SlideItem(
                R.drawable.dsfsfsdfs,
                "Health Calendar",
                "View all your medications and appointments in a simple calendar interface."
        ));
    }

    private void setupDots(int currentPosition) {
        if (dotsLayout != null) {
            dotsLayout.removeAllViews();
        }

        dots = new ImageView[slideItems.size()];

        for (int i = 0; i < slideItems.size(); i++) {
            dots[i] = new ImageView(this);

            if (i == currentPosition) {
                // Active dot
                dots[i].setImageResource(R.drawable.active_dot);
            } else {
                // Inactive dot
                dots[i].setImageResource(R.drawable.inactive_dot);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dotsLayout.addView(dots[i], params);
        }
    }

    private void startLoginActivity() {
        // Mark first time as done
        setFirstTimeDone();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isFirstTime() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("is_first_run", true);
    }

    private void setFirstTimeDone() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_first_run", false);
        editor.apply();
    }
}