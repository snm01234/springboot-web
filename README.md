# springboot-web

 스프링부트 웹서비스 제작 및 배포 연습을 위한 프로젝트입니다.

-------------


[개발 환경]
+ JAVA (1.8, Open JDK)
+ Spring Boot (2.5.6)
+ JUnit5
+ JPA
+ Lombok
+ Spring Security, OAuth2
+ Mustache 
+ Gradle (7.1)
+ IntelliJ Community 
+ H2 Database 

[서버 배포 환경]
+ AWS EC2, RDS(MariaDB 10.5), S3, CodeDeploy
+ Github Actions 
+ NginX

-------------



-changelog
2021-12-04 작업

s3 파일 업로드 작업 하려다 롤백

2021-12-03 작업

검색기능과 페이징처리 개선 

검색 이후 페이지 이동 시에도 검색어가 남아있고 페이지 이동 시에도 검색기록을 반영하도록 

2021-12-02 작업

Actions, S3, CodeDeploy 연동으로 Test & Build는 되는데 정작 배포 자동화가 되지 않는 문제가 생겨서 찾아보았다.

nohub.out에 log를 확인해보니 application-real이 oauth 인식을 못하는 문제

springboot 2.4 이상에서 spring.profiles.include 등의 문법은 deprecated 되었기 때문에 

application.properties에서 spring.profiles.group으로 관리 

그 외에 코드에서 경로 등이 잘못 설정되어있는 것을 수정하여서 문제 해결

nginx 배포 환경 구축을 위한 설정

oauth redirect 주소 추가

health check 용 profile api, 테스트코드 작성, 배포를 위한 스크립트 작성하고 appspec.yml에서 수행

nginx로 서버 중단 없이 배포하는 무중단 배포 성공하였고 드디어 CI/CD 환경이 모두 구축되었음.


2021-12-01 작업

github Actions로 CI 배포 환경 설정

Actions, S3, CodeDeploy 연동 작업까지 완료

nginx 설정은 아직 오류가 나서 완료 못함

2021-11-29 작업

AWS EC2 인스턴스 생성, Elastic IP로 IP고정

RDS에 mariadb 생성하고 EC2와 연결 

EC2에 스프링부트 프로젝트 배포하고 구글, 네이버, 카카오 연동되게 로그인 설정 변경

2021-11-28 작업

파일 업로드 테스트

라이브리 sns 댓글 서비스 적용해보기 (배포 시에는 홈페이지에서 사이트 주소를 변경해야함)

2021-11-27 작업

게시물 또는 내용으로 검색할 수 있는 기능 추가

페이징 처리기능 구현 테스트용으로 한 페이지에 보여줄 게시글은 2개로 설정

thymeleaf가 아닌 mustache의 제한된 문법으로 페이징 네비게이션바를 만드는 것에 약간 어려움 있었다

첫번째 페이지에서 이전페이지 버튼 비활성화, 마지막 페이지에서 다음 페이지 버튼 비활성화 기능 구현

현재 페이지 번호를 비활성화하는 것은 mustache 문법의 제한된 기능으로 구현하기 어려웠음

2021-11-26 작업

세션 로그인된 사용자와 글 작성자를 비교해서 작성자 본인만 글의 수정, 삭제가 가능하도록 로직 변경 

2021-11-25 작업

글 등록, 수정 페이지 세션 정보 추가/ 글 등록시 작성자를 로그인한 사용자로 표시

2021-11-24 작업

OAuth2 네이버, 카카오 로그인 기능 추가. 

테스트 코드 시큐리티 환경에 맞게 변경

메소드 인자로 세션값을 받을 수 있도록 @LoginUser 어노테이션 제작 

세션 저장소로 jdbc 사용하도록 변경 

oauth2 로그인 시 권한 ROLE_USER로 변경해서 글쓰기 가능하게 변경

2021-11-23 작업

OAuth2 구글 로그인 기능 추가.

기초적인 로그인, 로그아웃, 회원정보 저장, 권한관리 기능 구현

2021-11-22 작업

Posts 클래스에 JPA Auditing 상속시켜서 모든 엔티티에 등록/수정시간 조회기능 추가

BootStrap, 템플릿 엔진인 Mustache 등을 사용해서 게시글 등록/조회/수정/삭제 페이지 구성, crud api 제작하고 js로 api 호출


2021-11-21 작업

자주 쓰던 maven과 Spring Initializr 없이 gradle 환경으로 spring boot 프로젝트 생성하고 환경설정

단위테스트를 위한 테스트코드 작성하고 동작하는지 테스트

jpa, h2 설정 / 등록,수정,조회 api와 테스트 코드 작성
