package com.example.konomiroulette;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout btnMovie, btnMusic, btnBook, btnGame, btnSaved;
    LinearLayout bulbRowTop, bulbRowBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);

        btnMovie = findViewById(R.id.btnMovie);
        btnMusic = findViewById(R.id.btnMusic);
        btnBook = findViewById(R.id.btnBook);
        btnGame = findViewById(R.id.btnGame);
        btnSaved = findViewById(R.id.btnSaved);
        bulbRowTop = findViewById(R.id.bulbRowTop);
        bulbRowBottom = findViewById(R.id.bulbRowBottom);

        addBulbs(bulbRowTop, true);
        addBulbs(bulbRowBottom, false);

        btnMovie.setOnClickListener(v -> goToRoulette("영화"));
        btnMusic.setOnClickListener(v -> goToRoulette("음악"));
        btnBook.setOnClickListener(v -> goToRoulette("책"));
        btnGame.setOnClickListener(v -> goToRoulette("게임"));
        btnSaved.setOnClickListener(v ->
                startActivity(new Intent(this, SavedMissionsActivity.class)));
    }

    void addBulbs(LinearLayout row, boolean startOn) {
        int count = 14;
        int rowWidth = getResources().getDisplayMetrics().widthPixels - 24;
        int spacing = rowWidth / count;
        List<View> bulbs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            View bulb = new View(this);
            int size = (int) (13 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins((spacing - size) / 2, 0, (spacing - size) / 2, 0);
            bulb.setLayoutParams(params);

            GradientDrawable circle = new GradientDrawable();
            circle.setShape(GradientDrawable.OVAL);
            boolean isOn = (i % 2 == 0) == startOn;
            circle.setColor(isOn ? getColor(R.color.accent_gold) : 0xFF2a2000);
            bulb.setBackground(circle);
            row.addView(bulb);
            bulbs.add(bulb);
        }

        // 깜빡임 애니메이션
        Handler handler = new Handler();
        boolean[] state = {startOn};

        Runnable blink = new Runnable() {
            @Override
            public void run() {
                state[0] = !state[0];
                for (int i = 0; i < bulbs.size(); i++) {
                    GradientDrawable circle = new GradientDrawable();
                    circle.setShape(GradientDrawable.OVAL);
                    boolean isOn = (i % 2 == 0) == state[0];
                    circle.setColor(isOn ?
                            getColor(R.color.accent_gold) : 0xFF2a2000);
                    bulbs.get(i).setBackground(circle);
                }
                handler.postDelayed(this, 700);
            }
        };
        handler.postDelayed(blink, 700);
    }

    void goToRoulette(String category) {
        Intent intent = new Intent(this, RouletteActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}