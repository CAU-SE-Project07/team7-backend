# team7-backend
이슈관리시스템 백엔드 리포지토리

# OH자일!

[OH자일!]은(는) Spring Boot를 백엔드로, React.js를 프론트엔드로 사용하여 구현된 웹 애플리케이션입니다. 이 프로젝트는 중앙대학교 2024 소프트웨어공학 봄학기 Term-Project 주제인 이슈 관리 시스템을 주제로 합니다. OOAD/GRASP 패턴/설계 원칙의 적용을 고려하여 설계되었습니다.

## 목차

- [기능](#기능)
- [설치](#설치)
- [사용 방법](#사용-방법)
- [프로젝트 구조](#프로젝트-구조)
- [설계와 문서화](#설계와-문서화)
- [테스트](#테스트)
- [기여](#기여)
- [라이선스](#라이선스)

## 기능

- **이슈 관리**: 이슈 생성, 수정, 삭제, 조회 기능
- **프로젝트 관리**: 프로젝트 생성, 수정, 삭제, 조회 기능
- **회원 관리**: 회원 가입, 수정, 삭제, 로그인 기능
- **댓글 관리**: 댓글 작성, 수정, 삭제, 조회 기능
- **우선순위 관리**: 우선순위 설정 및 관리 기능

## 설치

이 프로젝트를 로컬 환경에 설치하려면 다음 단계를 따르세요:

### 백엔드 설치

1. **레포지토리 클론**

    ```bash
    git clone https://github.com/yourusername/projectname.git
    cd projectname
    ```

2. **필수 도구 설치**

    - Java 8 이상
    - Gradle 6 이상

3. **의존성 설치**

    ```bash
    ./gradlew build
    ```

4. **애플리케이션 설정**

    `src/main/resources/application.properties` 파일을 설정하여 데이터베이스 연결 정보를 입력합니다. 본 프로젝트에서는 H2 데이터베이스를 사용합니다.

5. **애플리케이션 실행**

    ```bash
    ./gradlew bootRun
    ```

### 프론트엔드 설치

1. **프론트엔드 디렉토리로 이동**

    ```bash
    cd frontend
    ```

2. **필수 도구 설치**

    - Node.js 14 이상
    - npm 6 이상

3. **의존성 설치**

    ```bash
    npm install
    ```

4. **애플리케이션 실행**

    ```bash
    npm start
    ```

## 사용 방법

애플리케이션을 실행한 후, 웹 브라우저에서 `http://localhost:3000`에 접속하여 다양한 기능을 사용할 수 있습니다.
## 0. 어드민은 프로젝트와 사용자 정보를 각각 입력하고 ADD 버튼으로 유저와 프로젝트를 추가합니다.
<img width="321" alt="image" src="https://github.com/CAU-SE-Project07/team7-backend/assets/140313197/21a998c9-eed9-4c66-9e14-5c328f08a6bd">

### 1. 어드민이 생성해둔 아이디로 로그인 합니다.
<img width="519" alt="image" src="https://github.com/CAU-SE-Project07/team7-backend/assets/140313197/75228dbf-917b-436c-8468-e716b2092cc0">
<img width="561" alt="image" src="https://github.com/CAU-SE-Project07/team7-backend/assets/140313197/ffbc4cb7-2940-4397-9c7a-73be77c8a73c">


### 2. 메인 화면에서 모든 이슈를 브라우징 합니다.
<img width="561" alt="image" src="https://github.com/CAU-SE-Project07/team7-backend/assets/140313197/830cf89b-6afb-4ff3-8622-24ea571b7273">



### 3. My issue 탭에서 나에게 할당된 이슈를 브라우징 합니다.
<img width="561" alt="image" src="https://github.com/CAU-SE-Project07/team7-backend/assets/140313197/e3d904da-2192-4242-b057-74cc7cb5a6f0">



### 4. 이슈를 자세히 확인하고 수정합니다.
<img width="637" alt="image" src="https://github.com/CAU-SE-Project07/team7-backend/assets/140313197/2697bf41-08d6-43df-9ed7-68afc96c2ff8">




### 5. 이슈를 등록합니다.
<img width="633" alt="image" src="https://github.com/CAU-SE-Project07/team7-backend/assets/140313197/df4be6f6-05f4-423c-b079-efd99930ab69">


### API 엔드포인트

- **회원 관리**
  - `POST /members`: 회원 가입
  - `GET /members/{id}`: 회원 정보 조회
  - `PUT /members`: 회원 정보 수정
  - `DELETE /members/{id}`: 회원 삭제

- **프로젝트 관리**
  - `POST /projects`: 프로젝트 생성
  - `GET /projects/{id}`: 프로젝트 조회
  - `PUT /projects`: 프로젝트 수정
  - `DELETE /projects/{id}`: 프로젝트 삭제

- **이슈 관리**
  - `POST /issues`: 이슈 생성
  - `GET /issues/{id}`: 이슈 조회
  - `PUT /issues`: 이슈 수정
  - `DELETE /issues/{id}`: 이슈 삭제

- **댓글 관리**
  - `POST /comments`: 댓글 작성
  - `GET /comments/{id}`: 댓글 조회
  - `PUT /comments`: 댓글 수정
  - `DELETE /comments/{id}`: 댓글 삭제

- **우선순위 관리**
  - `POST /priorities`: 우선순위 설정
  - `GET /priorities/{id}`: 우선순위 조회
  - `PUT /priorities`: 우선순위 수정
  - `DELETE /priorities/{id}`: 우선순위 삭제

## 프로젝트 구조

```plaintext
backend
├── src
│   ├── main
│   │   ├── java
│   │   │   └── hello/hellospring
│   │   │       ├── config        # 설정 클래스
│   │   │       │   ├── SpringConfig.java
│   │   │       │   └── WebConfig.java
│   │   │       ├── controller    # 컨트롤러 클래스
│   │   │       │   ├── CommentController.java
│   │   │       │   ├── IssueController.java
│   │   │       │   ├── MemberController.java
│   │   │       │   ├── PriorityController.java
│   │   │       │   └── ProjectController.java
│   │   │       ├── entity        # 엔티티 클래스
│   │   │       │   ├── CommentEntity.java
│   │   │       │   ├── IssueEntity.java
│   │   │       │   ├── MemberEntity.java
│   │   │       │   ├── PriorityEntity.java
│   │   │       │   └── ProjectEntity.java
│   │   │       ├── repository    # 리포지토리 인터페이스
│   │   │       │   ├── CommentRepository.java
│   │   │       │   ├── IssueRepository.java
│   │   │       │   ├── MemberRepository.java
│   │   │       │   ├── PriorityRepository.java
│   │   │       │   └── ProjectRepository.java
│   │   │       ├── service       # 서비스 인터페이스 및 구현 클래스
│   │   │       │   ├── CommentService.java
│   │   │       │   ├── CommentServiceImpl.java
│   │   │       │   ├── IssueService.java
│   │   │       │   ├── IssueServiceImpl.java
│   │   │       │   ├── MemberService.java
│   │   │       │   ├── MemberServiceImpl.java
│   │   │       │   ├── PriorityService.java
│   │   │       │   ├── PriorityServiceImpl.java
│   │   │       │   ├── ProjectService.java
│   │   │       │   └── ProjectServiceImpl.java
│   │   │       ├── vo            # 값 객체 클래스
│   │   │       │   ├── CommentVo.java
│   │   │       │   ├── IssueCreateVo.java
│   │   │       │   ├── IssueUpdateVo.java
│   │   │       │   ├── IssueVo.java
│   │   │       │   ├── MemberVo.java
│   │   │       │   ├── PriorityVo.java
│   │   │       │   ├── ProjectVo.java
│   │   │       │   └── ResponseVo.java
│   │   │       └── util          # 기타 유틸리티 클래스
│   │   │           ├── PythonScriptExecutor.java
│   │   │           ├── text_similarity.py
│   │   │           ├── Priority.java
│   │   │           └── State.java
│   └── resources
│       ├── application.properties
│       └── static
│           └── ...
frontend
├── public
│   ├── index.html
│   └── ...
├── src
│   ├── components
│   ├── App.js
│   ├── index.js
│   └── ...
