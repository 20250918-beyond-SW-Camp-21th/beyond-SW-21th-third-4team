# 한화시스템 Beyond SW 21기 프론트엔드 프로젝트 - 전설의 1군🐉

---
# 😄 팀소개
|[김세현]()|[송형욱]()|[유찬연]()|[윤홍석]()|[이상준]()
|:-:|:-:|:-:|:-:|:-:|
| 회원 & 장바구니 <br>(Auth & Cart) | 주문 & 결제 <br>(Order) | 상품 상세 <br>(Detail) | 주문 내역 & UI <br>(History) | 메인 & 상품 목록 <br>(List) |
| <img width="200" alt="image" src="https://github.com/user-attachments/assets/001e59f9-6dff-41ff-acfc-8313dca7619f" /> | <img width="200" alt="image" src="https://github.com/user-attachments/assets/ae19cad1-7646-4ce8-ab47-2f39556440d1" /> | <img width="200" alt="image" src="https://github.com/user-attachments/assets/59d2112b-f7ee-4e51-8376-becfd0af9c9b" /> | <img width="200" alt="image" src="https://github.com/user-attachments/assets/67fdafd0-5f6f-4528-bc2f-79fdacfee03f" /> | <img width="200" alt="image" src="https://github.com/user-attachments/assets/072f6b8c-bbd8-4f1e-bdd8-271531867fcd" /> |

---
# 🛠️ 기술 스택
<!-- Tech Stack Badges -->
🖥 Backend
![Java](https://img.shields.io/badge/Java%2017-007396?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=spring&logoColor=white)
![REST API](https://img.shields.io/badge/RESTful%20API-000000?style=flat-square&logo=fastapi&logoColor=white)

🌐 Frontend
![Vue.js](https://img.shields.io/badge/Vue.js%203-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-5A29E4?style=flat-square&logo=axios&logoColor=white)

🗄 Database
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=mariadb&logoColor=white)

🤝 Collaboration & Tools
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github&logoColor=white)
![Figma](https://img.shields.io/badge/Figma-F24E1E?style=flat-square&logo=figma&logoColor=white)
![Google Sheets](https://img.shields.io/badge/Google%20Sheets-34A853?style=flat-square&logo=googlesheets&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=flat-square&logo=discord&logoColor=white)

---
# ℹ️ 프로젝트 소개
## 1. 프로젝트 개요

본 프로젝트는 INSILENCE 웹 사이트를 참고하여 진행한 쇼핑몰 클론 코딩 프로젝트입니다.
회원가입부터 로그인, 상품 조회, 장바구니, 주문(Mock), 주문 내역 조회까지
쇼핑몰의 기본적인 사용자 흐름을 짧은 기간 안에 구현하는 것을 목표로 했습니다.

복잡한 비즈니스 로직이나 결제 시스템보다는,
프론트엔드와 백엔드가 어떻게 연결되고
각 기능이 어떤 순서로 동작하는지를 직접 구현하며 구조를 이해하는 데 집중했습니다.

## 2. 개발 배경

실제 서비스 형태의 프로젝트를 경험해보기 위해,
UI 구조가 비교적 명확하고 쇼핑몰의 기본 흐름이 잘 드러나는
INSILENCE 웹 페이지를 클론 대상으로 선정했습니다.

단순한 화면 구현에 그치지 않고,
“이 화면에서 어떤 API가 호출되고”,
“이 데이터가 다음 단계에서 어떻게 사용되는지”를 팀 단위로 나누어 구현하며
협업 기반의 기능 분리와 데이터 흐름을 경험하는 것을 목표로 프로젝트를 진행했습니다.

## 3. 프로젝트 목적

- 실제 쇼핑몰 화면을 참고하여 프론트엔드 화면 구성 경험을 쌓는 것
- Vue.js 기반 컴포넌트 구조와 페이지 설계 방식에 대한 이해
- API 연동과 상태 관리를 포함한 서비스 형태의 클론 코딩 경험
- 팀 단위 역할 분담을 통한 협업 개발 경험
- 이후 기능 확장과 유지보수를 고려한 구조로 페이지 구현

# 💡 주요 기능
본 프로젝트는 INSILENCE 클론 코딩을 목표로 한 단기 MVP로 쇼핑몰의 핵심 사용자 흐름을 구현하는 데 집중했습니다.

### 전체 사용자 흐름
회원가입 → 로그인 → 상품 탐색 → 장바구니 → 주문(Mock) → 주문내역 조회
### 📌 주요 기능 요약
| 기능 영역 | 기능명      | 설명                      |
| :---- | :------- | :---------------------- |
| 회원    | 회원 관리    | 회원가입, 로그인(JWT), 로그아웃    |
| 상품    | 상품 조회    | 메인 페이지, 상품 목록, 카테고리 조회  |
| 상품    | 상품 상세    | 상품 정보 확인, 수량 조절         |
| 장바구니  | 장바구니 관리  | 상품 담기, 목록 조회, 수량 변경, 삭제 |
| 주문    | 주문(Mock) | 주문서 작성, 주문 완료 처리        |
| 마이페이지 | 주문 내역    | 주문 내역 조회, 주문 상세 조회      |

### 1. 회원 (Auth)
- 회원가입
- 로그인 (JWT 발급)
- 로그아웃 (프론트엔드에서 토큰 제거 방식으로 처리)
- 로그인 상태 유지 (Authorization 헤더 기반)

### 2. 상품 (Product)
- 메인 페이지 (브랜드 이미지 고정 노출)
- 상품 목록 조회
  - 전체 상품 조회
  - 카테고리 기준 조회 (OUTER / TOP / BOTTOM / ACC)
- 상품 상세 조회
  - 상품 정보 확인
  - 수량 조절

### 3. 장바구니 (Cart)
- 장바구니 담기
- 장바구니 목록 조회
- 상품 수량 변경
- 장바구니 상품 삭제

### 4. 주문 (Order – Mock)
- 주문서 작성
- 배송지 정보 입력
- 주문 완료 처리
- window.confirm("결제하시겠습니까?") 승인 시 주문 성공 처리
- 장바구니 → 주문 데이터 이관 
- 주문 완료 후 장바구니 비우기

### 5. 주문 내역 (My Page)
- 주문 내역 리스트 조회
- 주문 상세 조회
- 주문 날짜
- 상품명
- 가격 정보
---
# 요구사항 정의서

---
# 스토리보드

---
# 태스트 케이스 명세서

--- 
# 🚀 추후 개선 및 확장 계획
1. 인증
Access Token(JWT) 기반 인증 사용
로그아웃은 프론트엔드에서 토큰 제거 방식으로 처리
→ 추후 Refresh Token 도입 및 서버 로그아웃 처리 가능
2. 결제
실제 결제 연동 없이 Mock 결제로 주문 완료 처리
→ 추후 외부 결제 API 연동 가능
3. 상품 옵션
옵션 및 재고 로직 단순화
→ 추후 상품 옵션 구조 확장, 품절 및 재고 부족 처리 로직 추가

---
# ✍️ 프로젝트 회고
| 이름 | 회고 내용 |
| :- |  :---- |
| 김세현 |       |
| 송형욱 |      |
| 유찬연 |    |
| 윤홍석 |       |
| 이상준 |      |
