# 자유게시판

<img src='./src/main/resources/images/postman.png' width="100%">

## 목차
[프로젝트 개요](#1-프로젝트-개요)  

[프로젝트 소개](#2-프로젝트-소개)  

[기술스택](#3-기술스택)  

[기술적 의사결정](#4-기술적-의사결정)  

[트러블슈팅](#5-트러블-슈팅)  

[고민 점](#6-고민-점)  

* * *  

### 1 프로젝트 개요  

- 기간 : 2022년 06월 24일 ~ 07월 11일, 2주 간 진행되었습니다.
- 기본적인 CRUD 기능을 가지고 있는 게시판 프로젝트입니다.
  
* * * 
  
### 2 프로젝트 소개  

<details>
<summary> 주요기능 </summary>

|기능|내용|
|:--|:--|
|게시글 조회|게시글 내용 및 댓글 조회|
|게시글 작성,수정,삭제,|작성자 혹은 관리자에 권한 부여|
|댓글 작성,수정,삭제|작성자 혹은 관리자에 권한 부여|
|회원가입,로그인|인증/인가, 유효성 검사|
</details>

* * *  

### 3 기술스택

<img src="https://img.shields.io/badge/OpenJDK-232F3E?style=for-the-badge&logo=OpenJDK&logoColor=white"/> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white"/> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"/> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"/> <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"/>  

* * * 

### 4 기술적 의사결정  

<details>
<summary><img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white"/> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/></summary>  
  
  - 다양한 라이브러리를 가지고 있는 자바 기반의 프레임워크입니다.
        
  - 스프링 부트는 복잡한 초기 설정을 간편하게 제공한다는 점에서 높은 사용성을 보이기 때문에 선택했습니다.
</details>

<details>
<summary><img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> </summary>  

    
  - 기본적인 CRUD 기능과 유저 및 게시글 데이터를 처리하기에 용이한 것은 관계형 데이터베이스라고 생각했습니다.
    
  - 따라서 관계형 데이터베이스 중에서 가장 익숙하고, 오픈 소스이기 때문에 무료로 사용할 수 있는 MySQL을 선택했습니다.
</details>

<details>
<summary><img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"/> <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"/> </summary>  
  
  - 가상 컴퓨터를 구매하여 FileZilla를 이용하여 쉽게 배포가 가능하고, 사용한 만큼 지불하는 방식이기 때문에 EC2를 선택했습니다.
      
  - MySQL을 AWS RDS에서 지원하기도 하고, DB 관리를 자동화해주기 때문에 편리성면에서 RDS를 선택했습니다.
</details>  

* * *  

### 5 트러블 슈팅

<details>
<summary>1. 토큰 유효성 검사, 불필요한 코드 반복성 </summary>  
  
  - JWT토큰의 유효성 검사를 Service 계층에서 시행했을 때 해당 로직의 반복적으로 작성되는 문제가 있었습니다.
    
  - 인증, 인가를 일괄적으로 처리해주는 Spring Security 사용하여 불필요한 코드 반복성 해결했습니다.
</details>

<details>
<summary>2. 필터에서 발생하는 예외 </summary>  
  
  - 기존에는 @ControllerAdvice를 적용하여 서비스 과정 중에 발생하는 예외들을 처리했습니다.
      
  - 토큰 유효성 검사 필터에서 발생하는 예외는 Contorller 계층으로 들어오기 전에 발생하므로 @ControllerAdivce로 처리할 수 없음을 인지했습니다.
      
  - 따라서 별도의 예외처리기가 필요하다고 생각하였고, OncePerRequestFilter를 상속받는 JwtExceptionFilter를 생성하여 해당 예외들을 처리했습니다.

```java
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, ErrorCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException | NullPointerException | UnsupportedEncodingException e) {
            setErrorResponse(response, ErrorCodeEnum.TOKEN_INVALID);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCodeEnum errorCodeEnum) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCodeEnum.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(objectMapper.writeValueAsString(error(errorCodeEnum.getMessage(), errorCodeEnum.getStatus().value())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
</details>  

<details>
<summary>3. 정렬 요구사항 </summary>  
  
  - 기존에는 가장 오래된 순서대로 댓글들이 조회되었고, 이는 최근 등록된 순으로 조회되어야 하는 요구사항과 달랐습니다.
    
  - 요구사항을 충족시키고자, 처음에는 PostResponseDto의 CommentList에서 stream을 이용하여 sorted(collection,reverseOrder())로 재구성하는 방법이었습니다.
    
  - 이 부분에서 CommentResponseDto가 Comparable이 구현하지 않았기 때문에 처음에는 에러가 발생했으나, Comparable을 구현하고 정렬기준 메서드를 오버라이딩하여 자료구조의 정렬기능을 사용할 수 있게끔 설정했습니다.

  - 그러나 코드 리팩토링 과정에서 ArrayList의 자료구조인 CommentList가 sorted를 이용한 재정렬과정에서 중간 데이터의 수정과 변경에 비효율적인 성능을 보일 수 있겠다고 판단했고, 애플리케이션 차원에서 재정렬하는 방식을 변경하고자 했습니다.

  - 따라서 DB에서 직접 역순정렬된 데이터를 가져오는 방식을 사용했습니다.

```java
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct p from Post p left join fetch p.commentList cl order by p.createdAt desc, cl.createdAt desc")
    List<Post> findAllByOrderByCreatedAtAtDesc();
}
```
    
</details>

* * *  

### 6 고민 점
