## 스프링 시큐리티

- 메타코딩 스프링부트 시큐리티

## MySQL DB 및 사용자 생성

```sql
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database security;
use security;
```

### user 회원가입 후

3명의 유저 생성
1번 아이디 : 일반 유저
2번 아이디 : 매니저
3번 아이디 : 관리자

```sql
update user set role = 'ROLE_MANAGER' where id = 2;
update user set role = 'ROLE_ADMIN' where id = 3;
commit;
```

## Google 로그인

1. 구글 콘솔 API에서 새 프로젝트 생성
2. OAuth동의 화면 > 외부 > 앱 등록
3. 사용자인증 정보 > OAuth 클라이언트 ID 만들기 > 승인된 리다이렉션 URI 추가
4. OAuth 클라이언트 라이브러리를 사용하면 `http://localhost:8080/login/oauth2/code/google` 이게 고정 (인가 코드 받는 url)
5. 발급받은 클라이언트 ID, 클라이언트 secret 적용

```yml
security:
  oauth2:
    client:
      registration:
        google:
          client-id: 703656983337-btk1jmhr65hkvq5fdokoasuigcd48lhj.apps.googleusercontent.com
          client-secret: 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
          scope:
            - email
            - profile
```

받은 유저 정보

```json
{
    sub=117661854383962663134,
    name=한상우/컴퓨터공학전공/학생,
    given_name=/컴퓨터공학전공/학생,
    family_name=한상우,
    picture=https://lh3.googleusercontent.com/a/ALm5wu2qEDFkJm2VXoVrNZwhGpsEP6pxOr-EQFvO6gxO=s96-c,
    email=sktkddn777@tukorea.ac.kr,
    email_verified=true,
    locale=ko, hd=tukorea.ac.kr
}

```

### 정리

- 원래 서버의 세션과는 별개로 스프링 시큐리티 만의 세션이 있다.
- 이 시큐리티 세션 안에는 Authentication 타입이 들어올 수 있는데
- 이 Authentication 타입안에 들어갈 수 있는 타입은 UserDetails(일반 로그인) 과 OAuth2User(OAuth 로그인) 타입 이렇게 2개가 존재한다.
- 만약 이 두타입을 혼용해서 로그인 처리를 하게 된다면 일반 로그인인지 oauth 로그인인지에 따라 캐스팅이 달라지고 불편해진다.
- 따라서 PrincipalDetails 에서 UserDetails를 상속받는거 뿐만 아니라 OAuth2User도 상속받고
- 로그인 할 때 타입을 PrincipalDetails로 잡아 해결
