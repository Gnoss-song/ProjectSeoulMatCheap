# ReadMe

# SeoulMatcheap - Android

개발 기간 : 20121년 3월 8일 ~ 2021년 5월 30일

---

## **[ About ]**

**서울시의 착한 가격 업소들을 소개하는 서비스**

서울시와 행정안정부에서 선정한 착한 가격업소라는 곳이 있습니다. 서울시민들을 위해 물가상승에도 불구하고 예전 가격을 그대로 유지하는 업소들을 어플로 찾기 쉽게 만들었습니다.

정보 제공 방식은 다음과 같습니다.

- 서울시의 지도를 형상화한 지역별 찾기
- 검색 자동 완성 기능
- 네이버지도를 연동한 길찾기
- 전화, 공유 기능
- 리뷰 기능

---

## **[ Preview ]**

[SeoulMatcheap PDF file](https://github.com/Gnoss-song/MatCheap/blob/master/SeoulMatcheap.pdf)


![3](https://user-images.githubusercontent.com/79887378/120128992-231eb100-c1fe-11eb-81cd-644fc8d04a38.png)

---

## **[ Develop Environment ]**

- Language : Kotlin
- Android Depolyment Target : Android 6.0(Mashmallow)

---

## **[ Library ]**

1. Server
- [SpringBoot](https://spring.io/projects/spring-boot)
- [Retrofit2](https://square.github.io/retrofit/)
- [rxjava2](https://github.com/ReactiveX/RxJava)

 2.   Layout

- [Glide](https://github.com/bumptech/glide)
- [ScaleRatingBar](https://github.com/williamyyu/SimpleRatingBar)
- [gridlayout](https://mvnrepository.com/artifact/androidx.gridlayout/gridlayout/1.0.0-rc01)
- ConstraintLayout
- RelativeLayout

---

## **[API]**

- 서울시 착한가격업소 공공 API

---

## **[ Role ]**

- Mypage

- Review

---
## **[ Feature Implementation ]**

- 리뷰 구현

      — RecyclerView를 이용한 리뷰 리스트
    
      — 앨범에서 다중사진을 가져와 조건에 따른 화면 표시(Multi RecyclerView)
    
      — 수정, 삭제 기능

- 화면 이동

      — Intent,Bundle,Fragmentmanager를 이용한 화면 이동

- Intent

      — 특정 메일양식을 특정 이메일로 보내는 건의 메뉴 구현

      — SNS를 이용한 공유하기 기능 구현
    
      — 특정 전화번호로 다이얼 연결

- RatingBar 구현

- SpringBoot에서 데이터를 가져와 공지사항 작성

- 앱 버전 체크


- 로그아웃, 탈퇴 기능 구현

---

### **[ Tools ]**

- AndroidStudio

- Figma

- Zeplin

- Jandi

- Github

- Notion

- Postman
