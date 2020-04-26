# git 설정 변경사항들

# 2020-04-26
그저께 부터 어제까지 (2020/04/24 ~ 2020/04/25) 커밋을 진짜 많이 했는데 커밋 그래프가 회색이었다ㅋㅋ. 
그래서 왜 이런 결과가 나오나 하고 찾아봤다.
원인은 ~/.gitconfig 라는 파일에 user의 name과 email이 github의 계정과 일치하지 않아서 생기는 문제였다.  
해결책은 아래에서 찾았다. 
- [커밋그래프 안보일때 해결법](https://android-blog.dev/37)
  
나의 경우는 github enterprise 계정이 따로 있고 회사에 묶여있어서 전역 설정인 ~/.gitconfig 는 그대로 두기로 했다. 
대신 프로젝트 생성시 마다 아래의 명령어를 입력해줘야 한다. (프로젝트 안에서만 유효한 user credentials)
 
```bash
$ git config user.name "sgjung"
$ git config user.email playmymind@daum.net
```
