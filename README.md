## 0. 하이뮤비

나만의 영화 공간, 하이뮤비

## 1. 팀원

- 허진서 : 고려대학교 컴퓨터학과 21학번
- 조백균 : KAIST 생명화학공학과 19학번

## 2. 개발 환경

- FrontEnd: JAVA
- BackEnd: Spring Boot
- DB: MySQL

## 3. tab 소개

### 로그인 화면

저희가 구현한 로그인 방법은 두가지로, 카카오톡 로그인과 앱 자체 로그인이 있습니다. 

- **DB**
    - _member (카카오톡 로그인)
        - column: id(pk), email, nickname, profileimage
    - user_info (자체 로그인)
        - column: userid(pk), userpwd
- **기능**
    - 카카오톡 로그인(/member/login/do)
        - 카카오톡으로 로그인 시, token을 받아와 해당 token을 통해 사용자의 정보를 저장합니다.
    - 앱 자체 로그인(/userinfo/login, /userinfo/signup)
        - 앱 자체 로그인 시, 아이디, 패스워드를 받아와 해당 (아이디, 패스워드) 쌍이 DB에 존재하는 경우 로그인이 가능합니다.
        - 앱 자체 회원가입 시, 아이디, 패스워드를 받아올때 해당 아이디를 가진 사용자가 존재하는 경우 다시 작성해야 하고, 없는 경우 DB에 저장이 됩니다.

---

### Tab1 영화 검색 및 영화 감상평 작성

영화 검색이 가능하며 해당 영화에 대한 감상평 작성 및 감상평 모아보기가 가능합니다.

- **화면**


- **API**
    - kmdb에서 API를 불러와 movie table의 데이터를 수집하였습니다.
    - API에서 제공하는 데이터 중 불필요한 정보가 많아 앱에서 사용하고자 하는 데이터만 처리하여 DB에 저장하였습니다.
    - kmdb API
    
    [KMDb - 한국영화데이터베이스](https://www.kmdb.or.kr/info/api/apiDetail/6)
    
    - 구현방식
        - `getApiResponse`를 통해 api로부터 response를 받아오고 `runApiExplorer`로 10개씩 300번 데이터를 받습니다.
        - `ApiResponseHandler`를 통해 데이터를 DB에 set해주는데 이때 필요한 정보는 데이터 등을 처리해서 DB에 저장합니다.
- **DB table**
    - movie
        - column: docid(pk), actor1, actor2, director_nm, genre, poster_url, rating, re_rls_date, runtime, still_url, title, vod_url
    - comment
        - column: commented, linecomment, longcomment, score
        - movie, user_info와 `@ManyToOne` 으로 join하여 사용하였습니다.
- **기능**
    - 영화 검색(/movie/search)
        - 검색어를 작성하면 해당 검색어가 포함된 영화제목을 갖는 영화 리스트들을 볼 수 있습니다.
    - 영화 상세정보(/movie/view)
        - 영화 리스트에서 원하는 영화를 누르면 해당 영화에 대한 상세정보를 확인할 수 있습니다.
        - 하단에는 해당 영화에 대한 감상평을 모아볼 수 있습니다.
    - 영화 감상평 작성(/movie/list)
        - 오른쪽 하단 +버튼을 누르면 해당 영화에 대한 감상평을 작성할 수 있습니다.
        - 별점과 한줄평, 상세평가 등이 가능합니다.
        

---

### Tab2 사용자별 영화감상평 모아보기

영화


- **DB table**
    - user_info
        - column: userid, userpwd
    - comment
        - column: commented, linecomment, longcomment, score
- **기능**
    - 사용자에 따라 본인이 작성한 감상평을 모아 볼 수 있도록 하였습니다.

---

### Tab3 영화 초성게임

영화 초성을 보고 영화 제목을 맞추는 영화 관련 미니게임이 가능합니다.


- **DB table**
    - game
        - column: gamed, answer, poster_url, question
- **기능**
    - 초성 게임 (/game/id)
        - 영화 초성을 띄우면 그에 맞는 영화 제목을 입력합니다.
        - 답을 입력하고 정답을 보여줄 때는 영화 포스터도 함께 보여줍니다.
        - 해당 영화 제목이 정답과 일치할 때, 일치하지 않을 때를 나누어 점수로 하단에 표기됩니다.
