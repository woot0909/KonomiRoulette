package com.example.konomiroulette;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    String category, genre, wild, missionText;

    // 국가
    String[] countries = {
            "일본", "프랑스", "이탈리아", "이란", "인도",
            "북유럽", "동유럽", "아프리카", "홍콩", "라틴아메리카", "한국",
            "브라질", "쿠바", "아일랜드", "중동", "러시아",
            "영미권", "중국", "북미", "유럽"
    };

    // 시대
    String[] eras = {
            "고전", "근현대", "현대", "근대", "중세", "고대",
            "1950~60년대", "1970~80년대", "1990년대", "2000년대", "2010~20년대"
    };

    // 분위기
    String[] moods = {
            "노스탤직한", "몽환적인", "블랙코미디적인", "초현실적인", "미니멀한",
            "서정적인", "웅장한", "힐링되는", "시리어스한", "유쾌한", "미스터리한"
    };

    // 영화 형식
    String[] movieFormats = {
            "무성영화", "흑백", "옴니버스", "모큐멘터리", "로드무비", "단편"
    };

    // 음악 악기/구성
    String[] musicInstruments = {
            "피아노", "기타", "아카펠라", "빅밴드", "오케스트라", "솔로 연주", "연주곡"
    };

    // 음악 형식
    String[] musicFormats = {
            "콘셉트 앨범", "즉흥 연주", "퓨전", "라이브 녹음"
    };

    // 게임 형식
    String[] gameFormats = {
            "오픈월드", "텍스트 기반", "로그라이크", "픽셀아트", "멀티엔딩"
    };

    // 게임 주제
    String[] gameThemes = {
            "정체성", "종교", "예술", "전쟁", "혁명", "AI",
            "사이버펑크", "포스트아포칼립스"
    };

    // 게임 스타일
    String[] gameStyles = { "고전", "인디" };

    // 배경으로 한 → 를 사용하는 키워드
    String[] backgroundRa = {
            "미래", "우주", "판타지", "사이버펑크", "포스트아포칼립스", "고대문명"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);

        category = getIntent().getStringExtra("category");
        genre = getIntent().getStringExtra("genre");
        wild = getIntent().getStringExtra("wild");
        missionText = generateMission(category, genre, wild);

        ((TextView) findViewById(R.id.tvCategory)).setText(category);
        ((TextView) findViewById(R.id.tvGenreBadge)).setText(genre);
        ((TextView) findViewById(R.id.tvWildBadge)).setText(wild);
        ((TextView) findViewById(R.id.tvMission)).setText(missionText);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnSave).setOnClickListener(v -> saveMission());
        findViewById(R.id.btnSearch).setOnClickListener(v -> searchMission());
        findViewById(R.id.btnRetry).setOnClickListener(v -> finish());
        findViewById(R.id.btnHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    // 받침 여부에 따라 조사 반환
    String josa(String word, String withBatchim, String withoutBatchim) {
        char last = word.charAt(word.length() - 1);
        if (last >= '가' && last <= '힣') {
            return ((last - '가') % 28 > 0) ? withBatchim : withoutBatchim;
        }
        return withoutBatchim;
    }

    String generateMission(String category, String genre, String wild) {
        if (genre.equals(wild)) {
            return "정통 " + genre + " " + category + "의 세계를 탐험해보세요!";
        }

        boolean isCountry = contains(countries, wild);
        boolean isEra = contains(eras, wild);
        boolean isMood = contains(moods, wild);
        boolean isMovieFormat = contains(movieFormats, wild);
        boolean isMusicInstrument = contains(musicInstruments, wild);
        boolean isMusicFormat = contains(musicFormats, wild);
        boolean isGameFormat = contains(gameFormats, wild);
        boolean isGameTheme = contains(gameThemes, wild);
        boolean isGameStyle = contains(gameStyles, wild);
        boolean isBackgroundRa = contains(backgroundRa, wild);

        // 장르 조사
        String genreJosa = josa(genre, "을", "를");

        switch (category) {
            case "영화":
                if (isCountry) return wild + "의 " + genre + " 영화를 찾아보세요!";
                if (isEra) return wild + " " + genre + " 영화를 찾아보세요!";
                if (isMood) return wild + " " + genre + " 영화를 찾아보세요!";
                if (isMovieFormat) return wild + " 형식의 " + genre + " 영화를 찾아보세요!";
                if (wild.equals("LGBTQ")) return "LGBTQ를 다룬 " + genre + " 영화를 찾아보세요!";
                if (isBackgroundRa) return wild + "를 배경으로 한 " + genre + " 영화를 찾아보세요!";
                return wild + josa(wild, "을", "를") + " 소재로 한 " + genre + " 영화를 찾아보세요!";

            case "음악":
                if (isCountry) return wild + "의 " + genre + " 음악을 들어보세요!";
                if (isEra) return wild + " " + genre + " 음악을 들어보세요!";
                if (isMood) return wild + " " + genre + " 음악을 들어보세요!";
                if (isMusicInstrument) return wild + " 중심의 " + genre + " 음악을 들어보세요!";
                if (wild.equals("라이브 녹음")) return "라이브 녹음된 " + genre + " 음악을 들어보세요!";
                if (wild.equals("퓨전")) return "퓨전 " + genre + " 음악을 들어보세요!";
                if (isMusicFormat) return wild + " 형식의 " + genre + " 음악을 들어보세요!";
                return wild + josa(wild, "을", "를") + " 주제로 한 " + genre + " 음악을 들어보세요!";

            case "책":
                if (isCountry) return wild + "의 " + genre + genreJosa + " 읽어보세요!";
                if (isEra) return wild + " " + genre + genreJosa + " 읽어보세요!";
                if (wild.equals("LGBTQ")) return "LGBTQ를 다룬 " + genre + genreJosa + " 읽어보세요!";
                if (wild.equals("미래")) return "미래를 다룬 " + genre + genreJosa + " 읽어보세요!";
                if (wild.equals("AI")) return "AI를 다룬 " + genre + genreJosa + " 읽어보세요!";
                if (wild.equals("기후변화")) return "기후변화를 다룬 " + genre + genreJosa + " 읽어보세요!";
                return wild + josa(wild, "을", "를") + " 다룬 " + genre + genreJosa + " 읽어보세요!";

            case "게임":
                if (isGameStyle) return wild + " " + genre + " 게임을 해보세요!";
                if (isCountry) return wild + "에서 만든 " + genre + " 게임을 해보세요!";
                if (isGameFormat) return wild + " 형식의 " + genre + " 게임을 해보세요!";
                if (isMood) return wild + " 분위기의 " + genre + " 게임을 해보세요!";
                if (isGameTheme) return wild + josa(wild, "을", "를") + " 주제로 한 " + genre + " 게임을 해보세요!";
                if (isBackgroundRa) return wild + "를 배경으로 한 " + genre + " 게임을 해보세요!";
                return wild + josa(wild, "을", "를") + " 배경으로 한 " + genre + " 게임을 해보세요!";

            default:
                return genre + " + " + wild + josa(wild, "을", "를") + " 탐험해보세요!";
        }
    }

    boolean contains(String[] arr, String value) {
        for (String s : arr) {
            if (s.equals(value)) return true;
        }
        return false;
    }

    void saveMission() {
        try {
            SharedPreferences prefs = getSharedPreferences("KonomiRoulette", MODE_PRIVATE);
            String json = prefs.getString("savedMissions", "[]");
            JSONArray missions = new JSONArray(json);

            JSONObject mission = new JSONObject();
            mission.put("category", category);
            mission.put("genre", genre);
            mission.put("wild", wild);
            mission.put("mission", missionText);
            missions.put(mission);

            prefs.edit().putString("savedMissions", missions.toString()).apply();
            Toast.makeText(this, "미션이 저장됐어요!", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void searchMission() {
        String query = genre + " " + wild + " " + category + " 추천";
        Uri uri = Uri.parse("https://www.google.com/search?q=" + Uri.encode(query));
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}