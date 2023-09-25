# 자유게시판 프로젝트

<img src='./src/main/resources/images/postman.png' width="100%">


### 1. 프로젝트 개요

- 기간 : 2022년 06월 24일 ~ 07월 11일 

### 2. 프로젝트 소개

주요기능

|기능|내용|
|:--|:--|
|게시글 조회|게시글 내용 및 댓글 조회|
|게시글 작성,수정,삭제,|작성자 혹은 관리자에 권한 부여|
|댓글 작성,수정,삭제|작성자 혹은 관리자에 권한 부여|
|회원가입,로그인|인증/인가, 유효성 검사|


### 3. 기술스택

<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=green"></br>

### 4. 트러블 슈팅

### 1-1) 토큰을 Service 계층에서 검증하기 때문에 발생하는 코드의 반복

- 컨트롤러에 요청이 들어오기 전에 인증, 인가 부분을 일괄적으로 처리해주는 Spring Security 사용하여 반복되는 코드의 문제를 해결하고자 했습니다.


### 1-2) 필터에서 발생하는 예외처리

- 예외처리에 대해서 깊은 관심을 가지고 있었기 때문에 진행기간 동안 @ControllerAdvice를 적용하여 발생하는 예외에 대한 정보들을 얻고 이들을 핸들링해보고자 했습니다.
- 이러한 과정에서 필터에서 발생하는 예외는 Controller 계층으로 들어오기 전에 발생하는 예외이기 때문에 Spring Security 사용 시, 토큰에서 발생하는 예외는 @ControllerAdivce로 예외를 처리할 수 없음을 인지하였습니다.
- 따라서 앞단에서 발생하는 예외들을 처리해주는 별도의 예외처리기가 필요하다고 생각하게 되었습니다.
- OncePerRequestFilter를 상속받는 JwtExceptionFilter를 생성하여 토큰에서 발생할 수 있는 예외를 처리하였습니다
- <img src='./src/main/resources/images/postman.png' width="100%">


### 1-3) 최근 등록순으로 정렬해야하는 요구사항

- 단순히 하나의 엔티티를 정렬하기 위해서는 쿼리메서드를 이용하여 구현하는 것이 쉬웠지만 Post와 연관관계를 맺고 있는 Comment의 경우에는 어떻게 해당 기준을 구현할지 고민하였습니다.
- 처음에 생각했던 것은 PostResponseDto에서 stream() 을 사용해서sorted(collection,reverseOrder())를 이용하는 방법이었습니다. 즉, CommentResponseDto가 Comparable을 구현하고 정렬기준 메서드를 오버라이딩했습니다.
- 시간이 지난 후 다시 코드를 검토할 때, CommentList의 타입이 탐색, 조회에는 우수한 성능을 보이지만, 중간 데이터의 수정과 변경에는 비교적 느린 성능을 보이는 ArrayList이기 때문에 해당 부분을 sorted를 이용해 애플리케이션 차원에서 재정렬하는 것은 생각보다 많은 리소스 소모와 성능에서의 약점을 보일 수 있을 것 같다는 생각하게 되었습니다.
- 따라서 DB에서 직접 역순정렬된 데이터를 가져오는 게 더 좋을 것 같다고 결론을 내리게 되었고, 해당 방식을 사용하였습니다.
- 다만 두 가지 방식에서는 그렇게 크게 성능적 이점은 없기에 변경사항이 생기면 애플리케이션에서 로직을 빠르게 수정할 수 있도록 해주는 Comparable 방식도 나쁘지 않은 것 같다고 피드백을 받았습니다.


### 2) 고민


### 2-1) 비즈니스 로직의 위치

- Setter의 사용을 지양하면서, '도메인이 특정 조건에 따라 자신의 상태를 유연하게 변경하고 이를 통해 외부에서는 쉽게 도메인의 상태를 변경하지 못하도록 설계하는 방식에 대해 고민했습니다. 
- 객체지향을 공부하면서 가장 크게 재밌었던 점은 객체들은 다른 객체가 어떤 역할과 속성을 지니고 있는지 알지 못한 채, 자신에게 메세지가 온다면 그저 그 메세지를 수행할 뿐이라는 점에 집중하여 코드 리팩토링을 진행하였습니다.
- Service 계층의 작성자 혹은 관리자만 삭제할 수 있는 유효성 검증 로직은 반복되고 있었기 때문에 해당 부분을 도메인에 할당하면서 유저 일치와 권한에 따라 수정 여부를 도메인 스스로가 결정할 수 있게 설정해보고자 했습니다.
- 그러나 도메인에 비즈니스 로직을 넣은 시도 자체는 좋았지만 결국에 해당 비즈니스 로직은 도메인 내부에서도 반복됨을 확인하였습니다. 즉, 리팩토링의 결과물은 비즈니스 로직을 도메인 안에 넣기만 했을 뿐, 반복성을 해결하지 못했다고 생각했습니다.
- 이를 해결하기 위해, boolean 반환타입으로 조건 일치 여부만 도메인 내부에 넣으려고 했으나, 이는 도메인이 메세지를 수신한 뒤 조건 판단을 하는 역할만 할 뿐, 추가적인 상태변경은 Service 계층에 맡기는 모양이 된다고 생각했고, 이러한 방식은 제가 의도했던 설계가 아니었습니다. 
- 기존 방식에 얽매이지 않고 여러 방식으로 코드를 작성해보려고 했던 점은 좋은 시도였던 것 같습니다. 또 그 과정에서 하나의 특정 Service 계층에서 실행되는 공통 로직은 해당 Service 계층에서 공통 메서드로 추출하고, 여러 Service 계층에서 일관적으로 사용되는 로직들은 도메인 내부로 넣는 게 좀 더 도메인 모델을 사용하기 적합한 환경이 아닐까라는 추가적인 고민을 해볼 수 있었습니다.
