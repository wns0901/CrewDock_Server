# CrewDock

### 📍프로젝트 개요
프로젝트 기반 협업을 원하는 사용자들을 연결하는 플랫폼으로, 프로젝트 팀원 모집 혹은 참여를 할 수 있는 기회를 제공. 추가적으로, 프로젝트 기반 본인의 포트폴리오를 기록하여 본인의 역량을 효과적으로 제시할 수 있는 공간 제공.

### ⏲ 개발 기간
2025년 1월 7일 ~ 2025년 2월 18일 (약 40일간 진행)

### 📍ERD
<img width="621" alt="Image" src="https://github.com/user-attachments/assets/3d4a2409-f6c0-4a35-acc0-131b187d5ae8" />

### 📍흐름도 - 사용자 페이지
<img width="898" alt="스크린샷 2025-02-20 오후 3 46 13" src="https://github.com/user-attachments/assets/327f68b1-b6f9-40a9-8a76-7c5fefe05a37" />


### 📍흐름도 - 관리자 페이지
<img width="390" alt="스크린샷 2025-02-20 오후 3 46 35" src="https://github.com/user-attachments/assets/adfc4913-9f40-4742-85f6-5cd2d359c165" />


### 👥 구성원별 담당 파트
|구성원|담당 파트|
|------|---|
|**장준영(팀장)**|백엔드와 프론트 시큐리티, 채팅의 백엔드, 프론트 구현,  깃액션과 도커를 이용한 CI/CD 구현, S3 설정 및 사용 메소드 구현|
|**김민혁**|팀 프로젝트 페이지 CRUD 및 프론트, 관리자 페이지 CRUD 및 프론트, 메인 페이지 배너, 스크랩 CRUD 및 프론트|
|**정문선**|마이페이지 백엔드 및 프론트, 포트폴리오 CRUD 및 프론트, 프로젝트 생성 백엔드 및 프론트|
|**황예은**|게시판페이지 프로젝트 페이지 CRUD 및 프론트, 모집글 페이지 첨부파일 (프론트)|
|**최시후**|프로젝트 이슈 페이지 CRUD 및 프론트, Full Calendar 를 사용한 마이페이지, 프로젝트 페이지 CRUD 및 프론트|
|**노경민**|모집글 CRUD 및 메인페이지 필터 옵션(백엔드) 모집글, 작성 모달(프론트)|

### 📍Skills
![CrewDock Skills](https://github.com/user-attachments/assets/f110bd82-f4c9-4831-bcff-9558e6008794)

<hr/>

### 📍클라이언트 Github 
https://github.com/wns0901/crew-dock-client

### 📍URL 규칙
- **RESTful**: 복수형 명사만 사용하기!
  - **Query String (QS)**: 어떤 자원의 모든 "조건"을 보여줌.
  - **Parameter**: 원하는 데이터의 ID값을 전달해서 해당 ID의 데이터("정보")를 보여줌.

### 📍Patch
- 요청은 **body**에 담아서 URL에 ID를 표시하지 않음.
- 단, **DELETE** 요청은 body가 없어서 URL에 ID를 표시해야 함.

### 📍커밋 메시지 컨벤션
- Struct : 빌드 업무 수정, 패키지 매니저 수정
- Feat : 새로운 기능 추가
- Fix : 버그 수정
- Docs : 문서 수정
- Style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- Refactor : 코드 리펙토링
- Test : 테스트 코드, 리펙토링 테스트 코드 추가
- Chore : 빌드 업무 수정, 패키지 매니저 수정
- Conflict: 충돌 해결
예제: Feat(#이슈번호): 커밋내용

### 📍이슈 타이틀 컨벤션
- Struct : 빌드 업무 수정, 패키지 매니저 수정
- Feat : 새로운 기능 추가
- Fix : 버그 수정
- Docs : 문서 수정
- Style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- Refactor : 코드 리펙토링
- Test : 테스트 코드, 리펙토링 테스트 코드 추가
- Chore : 빌드 업무 수정, 패키지 매니저 수정
- Conflict: 충돌 해결
예제 [Feat/Back]: 이슈 내용


