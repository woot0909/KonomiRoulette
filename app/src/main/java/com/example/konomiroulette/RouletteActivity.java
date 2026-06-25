package com.example.konomiroulette;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Random;

public class RouletteActivity extends AppCompatActivity {

    TextView tvCategory, btnBack, btnSpin, btnMission;
    TextView tvGenreTop, tvGenreSelected, tvGenreBottom;
    TextView tvWildTop, tvWildSelected, tvWildBottom;

    String category;
    String selectedGenre = "";
    String selectedWild = "";
    boolean isSpinning = false;
    boolean hasSpun = false;

    HashMap<String, String[]> genreMap = new HashMap<>();

    // 카테고리별 와일드카드
    HashMap<String, String[]> wildMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);

        // 장르 데이터
        genreMap.put("영화", new String[]{
                "느와르", "뮤지컬", "서부극", "SF", "다큐멘터리",
                "애니메이션", "공포", "멜로", "코미디", "판타지"
        });
        genreMap.put("음악", new String[]{
                "재즈", "보사노바", "소울", "클래식", "포크",
                "레게", "블루스", "컨트리", "전자음악", "월드뮤직",
                "CCM", "랩/힙합", "앰비언트", "록", "메탈"
        });
        genreMap.put("책", new String[]{
                "소설", "시집", "에세이", "희곡", "철학서", "단편집", "자서전"
        });
        genreMap.put("게임", new String[]{
                "퍼즐", "RPG", "어드벤처", "전략", "시뮬레이션",
                "아케이드", "공포", "격투", "스포츠", "플랫포머",
                "TCG", "JRPG", "액션"
        });

        // 와일드카드 데이터 (카테고리별)
        wildMap.put("영화", new String[]{
                "고전", "미래", "근대", "중세", "고대",
                "일본", "프랑스", "이탈리아", "이란", "인도",
                "북유럽", "동유럽", "아프리카", "홍콩", "라틴아메리카", "한국",
                "전쟁", "혁명", "독재", "이민", "LGBTQ", "인종", "빈곤", "냉전",
                "죽음", "기억", "정체성", "고독", "종교", "예술",
                "AI", "기후변화", "감시사회", "사이버펑크",
                "우주", "농촌",
                "무성영화", "흑백", "옴니버스", "모큐멘터리", "로드무비", "단편",
                "노스탤직한", "몽환", "블랙코미디", "초현실", "미니멀"
        });
        wildMap.put("영화", new String[]{
                // 시대
                "고전", "미래", "근대", "중세", "고대",
                // 국가
                "일본", "프랑스", "이탈리아", "이란", "인도",
                "북유럽", "동유럽", "아프리카", "홍콩", "라틴아메리카", "한국",
                // 사회/정치
                "전쟁", "혁명", "독재", "이민", "LGBTQ", "인종", "빈곤", "냉전",
                // 철학
                "죽음", "기억", "정체성", "고독", "종교", "예술",
                // 현대
                "AI", "기후변화", "감시사회", "사이버펑크",
                // 공간
                "우주", "농촌",
                // 형식
                "무성영화", "흑백영화", "옴니버스", "모큐멘터리", "로드무비", "단편",
                // 분위기
                "노스탤직한", "몽환적인", "블랙코미디적인", "초현실적인", "미니멀한"
        });

        wildMap.put("음악", new String[]{
                // 시대
                "고전", "1950~60년대", "1970~80년대", "1990년대", "2000년대", "2010~20년대",
                // 국가
                "일본", "프랑스", "브라질", "쿠바", "아프리카",
                "인도", "아일랜드", "북유럽", "중동", "라틴아메리카", "한국",
                // 악기/구성
                "피아노", "기타", "아카펠라", "빅밴드", "오케스트라", "솔로 연주", "연주곡",
                // 형식
                "라이브 녹음", "콘셉트 앨범", "즉흥 연주", "퓨전",
                // 분위기
                "노스탤직한", "몽환적인", "서정적인", "미니멀한", "웅장한"
        });

        wildMap.put("책", new String[]{
                // 시대
                "고전", "근현대", "현대",
                // 국가
                "일본", "프랑스", "러시아", "영미권", "라틴아메리카",
                "동유럽", "아프리카", "중동", "북유럽", "한국", "중국",
                // 사회/정치
                "전쟁", "혁명", "독재", "이민", "인종", "빈곤", "냉전", "아동", "이방인",
                // 철학
                "죽음", "기억", "정체성", "고독", "종교", "예술",
                // 현대
                "AI", "기후변화", "미래", "LGBTQ"
        });

        wildMap.put("게임", new String[]{
                // 시대/레트로
                "고전", "인디",
                // 국가
                "일본", "북미", "유럽",
                // 배경/세계관
                "미래", "중세", "고대", "전쟁", "AI", "사이버펑크",
                "우주", "포스트아포칼립스", "판타지", "고대문명",
                // 형식
                "오픈월드", "텍스트 기반", "로그라이크", "픽셀아트", "멀티엔딩",
                // 분위기
                "힐링되는", "시리어스한", "유쾌한", "미스터리한",
                // 주제
                "정체성", "종교", "예술"
        });

        tvCategory = findViewById(R.id.tvCategory);
        btnBack = findViewById(R.id.btnBack);
        btnSpin = findViewById(R.id.btnSpin);
        btnMission = findViewById(R.id.btnMission);
        tvGenreTop = findViewById(R.id.tvGenreTop);
        tvGenreSelected = findViewById(R.id.tvGenreSelected);
        tvGenreBottom = findViewById(R.id.tvGenreBottom);
        tvWildTop = findViewById(R.id.tvWildTop);
        tvWildSelected = findViewById(R.id.tvWildSelected);
        tvWildBottom = findViewById(R.id.tvWildBottom);

        category = getIntent().getStringExtra("category");
        tvCategory.setText(category);

        showInitialRoulette();

        btnBack.setOnClickListener(v -> finish());
        btnSpin.setOnClickListener(v -> {
            if (!isSpinning) spinRoulette();
        });
        btnMission.setOnClickListener(v -> {
            if (hasSpun) goToResult();
        });
    }

    void showInitialRoulette() {
        String[] genres = genreMap.get(category);
        String[] wilds = wildMap.get(category);
        if (genres == null || wilds == null) return;

        tvGenreTop.setText(genres[0]);
        tvGenreSelected.setText("?");
        tvGenreBottom.setText(genres[1]);
        tvWildTop.setText(wilds[0]);
        tvWildSelected.setText("?");
        tvWildBottom.setText(wilds[1]);

        btnMission.setTextColor(getColor(R.color.text_dark));
    }

    void spinRoulette() {
        isSpinning = true;
        btnSpin.setTextColor(getColor(R.color.text_dark));

        String[] genres = genreMap.get(category);
        String[] wilds = wildMap.get(category);
        Random rand = new Random();
        Handler handler = new Handler();
        int[] count = {0};
        int totalFrames = 20;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (count[0] < totalFrames) {
                    int gi = rand.nextInt(genres.length);
                    int wi = rand.nextInt(wilds.length);

                    tvGenreSelected.setText(genres[gi]);
                    tvWildSelected.setText(wilds[wi]);
                    tvGenreTop.setText(genres[(gi - 1 + genres.length) % genres.length]);
                    tvGenreBottom.setText(genres[(gi + 1) % genres.length]);
                    tvWildTop.setText(wilds[(wi - 1 + wilds.length) % wilds.length]);
                    tvWildBottom.setText(wilds[(wi + 1) % wilds.length]);

                    int delay = 50 + (count[0] * 15);
                    count[0]++;
                    handler.postDelayed(this, delay);
                } else {
                    int gi = rand.nextInt(genres.length);
                    int wi = rand.nextInt(wilds.length);

                    selectedGenre = genres[gi];
                    selectedWild = wilds[wi];

                    tvGenreSelected.setText(selectedGenre);
                    tvWildSelected.setText(selectedWild);
                    tvGenreTop.setText(genres[(gi - 1 + genres.length) % genres.length]);
                    tvGenreBottom.setText(genres[(gi + 1) % genres.length]);
                    tvWildTop.setText(wilds[(wi - 1 + wilds.length) % wilds.length]);
                    tvWildBottom.setText(wilds[(wi + 1) % wilds.length]);

                    isSpinning = false;
                    hasSpun = true;
                    btnSpin.setText("▶ 다시 돌리기");
                    btnSpin.setTextColor(getColor(R.color.accent_gold));
                    btnMission.setTextColor(getColor(R.color.accent_gold));
                }
            }
        };
        handler.post(runnable);
    }

    void goToResult() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("genre", selectedGenre);
        intent.putExtra("wild", selectedWild);
        startActivity(intent);
    }
}