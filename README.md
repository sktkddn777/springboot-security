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
