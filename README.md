# 자유게시판

<img src='./src/main/resources/images/postman.png' width="100%">

## 목차
[프로젝트 개요](#1-프로젝트-개요)  

[프로젝트 소개](#2-프로젝트-소개)  

[기술스택](#3-기술스택)  

[기술적 의사결정](#4-기술적-의사결정)  

[트러블슈팅](#5-트러블-슈팅)  

[고민 점](#6-고민-점)  


### 1.프로젝트 개요

- 기간 : 2022년 06월 24일 ~ 07월 11일, 2주 간 진행
  

### 2.프로젝트 소개
<details>
<summary> 주요기능 </summary>

|기능|내용|
|:--|:--|
|게시글 조회|게시글 내용 및 댓글 조회|
|게시글 작성,수정,삭제,|작성자 혹은 관리자에 권한 부여|
|댓글 작성,수정,삭제|작성자 혹은 관리자에 권한 부여|
|회원가입,로그인|인증/인가, 유효성 검사|
</details>

  
### 3.기술스택

<img src="https://img.shields.io/badge/OpenJDK-232F3E?style=for-the-badge&logo=OpenJDK&logoColor=white"/> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white"/> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"/> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"/> <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"/>  


### 4.기술적 의사결정
<details>
<summary>1. <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white"/> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/></summary>
- 다양한 라이브러리를 가지고 있는 자바 기반의 프레임워크
- 스프링 부트는 복잡한 초기 설정을 간편하게 제공한다는 점에서 높은 사용성을 보이기 때문에 선택
</details>

<details>
<summary>2. <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> </summary>
- 관계형 데이터베이스들 중에서 가장 익숙하고, 오픈 소스이기 때문에 무료로 사용할 수 있기에 선택
</details>

<details>
<summary>3. <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"/> <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"/> </summary>
- 사용한 만큼 지불하는 방식이고, 가상 컴퓨터인 인스턴스를 완전하게 제어할 수 있으므로 EC2를 선택
- MySQL을 AWS RDS에서 지원하기도 하고, DB 관리를 자동화해주기 때문에 편리성면에서 RDS를 선택
</details>  


### 5.트러블 슈팅

<details>
<summary>1. 토큰 유효성 검사, 불필요한 코드 반복성 </summary>
- JWT토큰의 유효성 검사를 Service 계층에서 시행했을 때 해당 로직의 반복적으로 작성됨
- 인증, 인가를 일괄적으로 처리해주는 Spring Security 사용하여 불필요한 코드 반복성 해결
</details>

<details>
<summary>2. 필터에서 발생하는 예외 </summary>
- @ControllerAdvice를 적용하여 서비스 과정 중에 발생하는 예외들을 핸들링
- 
</details>  


### 6.고민 점
