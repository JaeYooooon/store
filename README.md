## 📍 Store Project

매장 테이블 예약 서비스 프로젝트


## ⚙ Tech Stack

- Language : Java
- Build : Gradle
- FrameWork : Spring Boot 2.7.12
- JDK : JDK 11
- Database : MySQL

## 🔌 Dependencies

- Spring Web
- Spring Data JPA
- Spring Security
- Lombok
- Mysql Connector
- JWT
- Swagger


## 🛠 Function

**Function**     | **구현 완료** |
:--------------- | :----------: |
**파트너 회원 가입, 로그인, 탈퇴**      | :heavy_check_mark: |
**유저 회원 가입, 로그인, 탈퇴**        | :heavy_check_mark: |
**매장 등록, 수정, 삭제**              | :heavy_check_mark: |
**매장 검색**                         | :heavy_check_mark: |
**매장 상세 정보 조회**                | :heavy_check_mark: |
**매장 예약, 수정, 취소**              | :heavy_check_mark: |
**예약 10분전 체크인**                 | :heavy_check_mark: |
**매장 이용 후 리뷰 작성, 수정, 삭제**   | :heavy_check_mark: |
**예약이 들어오면 승인/거절**           | :heavy_check_mark: |


## 🔻 RestAPI EndPoint


| **Method Type** | **URI**                | **Function**   | **Method Type** | **URI**                             | **Function** |
|-----------------|------------------------|----------------|-----------------|-------------------------------------|--------------|
| POST            | /signup/partner        | 파트너 회원가입  | GET             | /reserve                            | 예약정보         |
| POST            | /signup/user           | 유저 회원가입    | POST            | /reserve                            | 예약 생성        |
| POST            | /signin                | 로그인          | PUT             | /reserve/{reserveId}                | 예약 변경        |
| DELETE          | /delete                | 화원탈퇴         | DELETE          | /reserve/{reserveId}                | 예약 취소       |
| POST            | /partner/shop          | 상점 등록        | POST            | /reserve/{reserveId}                | 예약 체크인      |
| PUT             | /partner/shop/{shopId} | 상점 수정        | GET             | /partner/reserve/{shopId}           | 상점의 예약목록   |
| DELETE          | /partner/shop/{shopId} | 상점 삭제        | PUT             | /partner/reserve/status/{reserveId} | 예약 상태 변경    |
| GET             | /shop                  | 상점 전체 목록    | POST            | /review                             | 리뷰 작성        |
| GET             | /shop/{shopId}         | 상점 상세 조회    | PUT             | /review/{reviewId}                  | 리뷰 수정        |
| GET             | /shop/search           | 상점 이름 검색    | DELETE          | /review/{reviewId}                  | 리뷰 삭제        |
| GET             | /shop/name             | 상점 이름순 정렬   | GET             | /shop/star                          | 상점 별점순 정렬  |

## ❗ Impression