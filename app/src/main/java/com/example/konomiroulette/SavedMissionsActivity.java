package com.example.konomiroulette;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SavedMissionsActivity extends AppCompatActivity {

    LinearLayout savedList;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);

        prefs = getSharedPreferences("KonomiRoulette", MODE_PRIVATE);
        savedList = findViewById(R.id.savedList);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        loadMissions();
    }

    void loadMissions() {
        savedList.removeAllViews();
        try {
            String json = prefs.getString("savedMissions", "[]");
            JSONArray missions = new JSONArray(json);

            if (missions.length() == 0) {
                TextView empty = new TextView(this);
                empty.setText("저장된 미션이 없어요!");
                empty.setTextColor(getColor(R.color.text_muted));
                empty.setTypeface(getResources().getFont(R.font.bebas_neue));
                empty.setTextSize(18);
                empty.setGravity(Gravity.CENTER);
                empty.setPadding(0, 80, 0, 0);
                savedList.addView(empty);
                return;
            }

            for (int i = 0; i < missions.length(); i++) {
                JSONObject mission = missions.getJSONObject(i);
                addMissionCard(mission, i, missions);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void addMissionCard(JSONObject mission, int index, JSONArray missions) throws JSONException {
        String category = mission.getString("category");
        String genre = mission.getString("genre");
        String wild = mission.getString("wild");
        String missionText = mission.getString("mission");

        // 카드
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(32, 28, 32, 28);
        card.setBackgroundResource(R.drawable.btn_category_normal);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(0, 0, 0, 16);
        card.setLayoutParams(cardParams);

        // 카테고리 + 뱃지 행
        LinearLayout badgeRow = new LinearLayout(this);
        badgeRow.setOrientation(LinearLayout.HORIZONTAL);
        badgeRow.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams badgeRowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        badgeRowParams.setMargins(0, 0, 0, 12);
        badgeRow.setLayoutParams(badgeRowParams);

        // 카테고리
        TextView tvCat = new TextView(this);
        tvCat.setText(category);
        tvCat.setTextColor(getColor(R.color.text_muted));
        tvCat.setTypeface(getResources().getFont(R.font.bebas_neue));
        tvCat.setTextSize(13);
        LinearLayout.LayoutParams catParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        catParams.setMargins(0, 0, 16, 0);
        tvCat.setLayoutParams(catParams);
        badgeRow.addView(tvCat);

        // 장르 뱃지
        TextView tvGenre = new TextView(this);
        tvGenre.setText(genre);
        tvGenre.setTextColor(getColor(R.color.accent_gold));
        tvGenre.setTypeface(getResources().getFont(R.font.galmuri11_bold));
        tvGenre.setTextSize(13);
        tvGenre.setPadding(16, 6, 16, 6);
        tvGenre.setBackgroundResource(R.drawable.badge_gold);
        LinearLayout.LayoutParams genreParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        genreParams.setMargins(0, 0, 8, 0);
        tvGenre.setLayoutParams(genreParams);
        badgeRow.addView(tvGenre);

        // 와일드 뱃지
        TextView tvWild = new TextView(this);
        tvWild.setText(wild);
        tvWild.setTextColor(getColor(R.color.accent_gold));
        tvWild.setTypeface(getResources().getFont(R.font.galmuri11_bold));
        tvWild.setTextSize(13);
        tvWild.setPadding(16, 6, 16, 6);
        tvWild.setBackgroundResource(R.drawable.badge_gold);
        badgeRow.addView(tvWild);

        card.addView(badgeRow);

        // 미션 텍스트
        TextView tvMission = new TextView(this);
        tvMission.setText(missionText);
        tvMission.setTextColor(getColor(R.color.text_main));
        tvMission.setTypeface(getResources().getFont(R.font.galmuri11_bold));
        tvMission.setTextSize(15);
        tvMission.setLineSpacing(0, 1.5f);
        card.addView(tvMission);

        // 삭제 버튼
        TextView btnDelete = new TextView(this);
        btnDelete.setText("▶ 삭제");
        btnDelete.setTextColor(getColor(R.color.text_muted));
        btnDelete.setTypeface(getResources().getFont(R.font.bebas_neue));
        btnDelete.setTextSize(14);
        btnDelete.setGravity(Gravity.END);
        LinearLayout.LayoutParams deleteParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        deleteParams.setMargins(0, 12, 0, 0);
        btnDelete.setLayoutParams(deleteParams);

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("미션 삭제")
                    .setMessage("이 미션을 삭제할까요?")
                    .setPositiveButton("삭제", (dialog, which) -> {
                        try {
                            String json = prefs.getString("savedMissions", "[]");
                            JSONArray arr = new JSONArray(json);
                            JSONArray newArr = new JSONArray();
                            for (int j = 0; j < arr.length(); j++) {
                                if (j != index) newArr.put(arr.get(j));
                            }
                            prefs.edit().putString("savedMissions", newArr.toString()).apply();
                            Toast.makeText(this, "삭제됐어요!", Toast.LENGTH_SHORT).show();
                            loadMissions();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();
        });
        card.addView(btnDelete);

        savedList.addView(card);
    }
}