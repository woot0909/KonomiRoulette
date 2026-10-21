# 🎰 Konomi Roulette

> 알고리즘이 정해준 취향 밖으로, 룰렛 한 번.

추천 알고리즘은 우리가 이미 좋아하는 것만 계속 보여줍니다. **Konomi Roulette**는 이런 필터버블에서 벗어나기 위해, 룰렛으로 **무작위 문화 미션**을 뽑아주는 안드로이드 앱입니다.
'Konomi(好み)'는 일본어로 '취향'을 뜻합니다.


## 주요 기능

- **카테고리 선택**: 영화 · 음악 · 책 · 게임
- **더블 룰렛**: 장르 룰렛과 와일드카드 키워드 룰렛을 돌려 미션 생성
  - 예) `음악` → `보사노바` + `브라질` → "브라질의 보사노바 음악을 들어보세요!"
- **카테고리별 와일드카드 풀**: 시대 · 국가 · 분위기 · 형식 · 테마 · 스타일
- **한국어 조사 자동 처리**: 받침 여부에 따라 을/를 등을 자동 선택(`josa()`)
- **미션 저장 / 삭제**: 마음에 드는 미션을 보관함에 저장
- **바로 검색**: 결과 화면에서 미션 키워드를 구글 검색으로 바로 연결

## 화면 구성

| Activity | 역할 |
|---|---|
| `MainActivity` | 카테고리 선택, 전구가 깜빡이는 마퀴 로고 |
| `RouletteActivity` | 장르와 키워드 룰렛 2개, 점점 느려지는 회전 애니메이션 |
| `ResultActivity` | 미션 표시, 저장 · 검색 · 다시 돌리기 · 홈 |
| `SavedMissionsActivity` | 저장한 미션 목록, 삭제 |

## 기술 스택

- **Language**: Java
- **Platform**: Android (minSdk 26 / targetSdk 36)
- **데이터 저장**: SharedPreferences + JSONArray
- **애니메이션**: Handler / Runnable 기반 룰렛 감속, 전구 점멸
- **화면 연결**: Intent(화면 전환, 웹 검색 `ACTION_VIEW`)
- **Design**: Figma, 레트로 극장 간판 콘셉트(블랙 + 골드)
- **Font**: Bebas Neue(영문), Galmuri11 Bold(한글)

## 실행 방법

```bash
git clone https://github.com/woot0909/KonomiRoulette.git
```
Android Studio에서 프로젝트를 열고 Gradle Sync가 끝나면 에뮬레이터나 기기에서 실행합니다.

## 개발자

**전서진** · 한국폴리텍대학 인공지능소프트웨어과
