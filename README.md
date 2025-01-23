# CrewDock

### URL 규칙
- **RESTful**: 복수형 명사만 사용하기!
  - **Query String (QS)**: 어떤 자원의 모든 "조건"을 보여줌.
  - **Parameter**: 원하는 데이터의 ID값을 전달해서 해당 ID의 데이터("정보")를 보여줌.

### Petch
- 요청은 **body**에 담아서 URL에 ID를 표시하지 않음.
- 단, **DELETE** 요청은 body가 없어서 URL에 ID를 표시해야 함.

API 명세서 swagger 이거는 언젠가...... 

### 커밋 메시지 컨벤션
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

### 이슈 타이틀 컨벤션
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
