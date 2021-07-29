# ReadMe

# GooroomeeLite- Android

개발 기간 : 2021년 6월 1일 ~ 2021년 7월 30일

---

## **[ About ]**

**캠 스터디 구루미의 혼공버전을 지원하는 서비스**

기존의 캠 스터디인 구루미에서 캠 없이 혼자 공부하는것을 기록할수 있는 혼공족을 위한 
스터디메이트 앱 입니다.

정보 제공 방식은 다음과 같습니다.

- 타이머를 이용한 공부 기록
- 서비스를 이용한 미디어 플레이어 기능
- 프로필 수정, 플레이스토어 연결 기능
- 공유, 카메라, 스티커 기능
- 그래프를 이용한 공부 데이터 정리 기능

---

## **[ Preview ]**

GooroomeeLite PDF 예정

---

대략적인 PDF 이미지 프리뷰

## **[ Develop Environment ]**

- Language : Kotlin
- Android Depolyment Target : Android 6.0(Mashmallow)

---

## **[ Library ]**

1. Server
- Firebasedatabase
- Firebasestorage

 2.   Layout

- [Glide](https://github.com/bumptech/glide)
- [ScaleRatingBar](https://github.com/williamyyu/SimpleRatingBar)
- [gridlayout](https://mvnrepository.com/artifact/androidx.gridlayout/gridlayout/1.0.0-rc01)
- ConstraintLayout
- RelativeLayout
- [ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2?hl=ko)

 3. Function

- [TedPermission](https://github.com/ParkSangGwon/TedPermission)
- [ColorPicker](https://github.com/Dhaval2404/ColorPicker)
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)

---

## **[ Role ]**

- Devleopment leading

- FirebaseData Management

- Mypage

- Login

- MediaPlayer

---
### **[ Feature Implementation ]**

- 회원가입,비밀번호찾기

    — 로그인

      — 이메일
    
        — 구글로그인, 이메일 로그인

        — 아이디 중복검사, 아이디 조건식 , 닉네임 설정, 목표 시간 설정

      — 비밀번호

        — 정규식, 비밀번호 조건, 비밀번호 찾기

- 스플래시, 온보딩 구현

      — ViewPager2를 이용한 온보딩 구현

      — 스플래시에 JobScheduler 구현
      
      — indicator 
      

- SeekBar, ProgressBar 구현
- Firebasedatabase와 연동하여 데이터 관리

      — 공부시간 기록, 초기화, 다른 페이지들과 데이터 연동

      — MVVM패턴으로 livedata와 databinding을 이용하여 오늘 공부한 시간 실시간 연산

- 화이트 노이즈 구현

      — 미디어플레이어 서비스를 이용하여 백그라운드에서 음악재생,반복

      — 싱글톤을 이용하여 한개의 서비스가 유지되도록 설정

- 프로필 구현

      — 프로필이 지정되어있을때와 아닐때를 체크하여 기본 이미지 설정

      — 구글 로그인과 일반 이메일 로그인을 구분

      — 앨범을 통한 사진 세팅, 파이어베이스와 통신
      
- 뽀모도로 타이머 구현

      — SharedPreference를 이용하여 일반 타이머와 뽀모도로 타이머의 모드 설정

      — 

- 플레이스토어 연결
- 앱 버전 체크
- 로그아웃, 탈퇴 기능 구현

---

### **[ Tools ]**

- AndroidStudio

- Figma

- Slack

- Github

- Notion
