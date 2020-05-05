# CORS 설정 추가하기
# 1. 2020/05/05
## frontend
**참고자료**  
- [axios, ajax CORS 처리방법](https://idlecomputer.tistory.com/170)
- [github 이슈 - CORS 처리하는 다양한 방법들](https://github.com/axios/axios/issues/853)

**frontend/src/api/index.js (axios 관련 설정)**
```javascript
import axios from 'axios';

export default axios.create({
  baseURL: '//localhost:8080/api',
  headers:{
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'application/x-www-form-urlencoded'
  },
  crossdomain: true,
  proxy:{
    host:'localhost',
    port:8080
  }
});
```
### 아직은 나도 잘 모르는 내용들...
- 일단 저 위의 crossdomain, headers의 모든 항목들을 주석처리하고 spring boot를 켜도 잘 동작한다. 
- axios의 header에 어떤 것을 어떤 때에 넣는지는 꼭 나중에라도 정리를 해놓아야 할 듯하다.
 

## backend
front end 인 vue.js, axios 에만 관련 설정을 추가하고 아무생각없이 왜 안돼지 했는데...  
맞다. 역시 서버 쪽에도 CORS 헤더를 Response에 헤더로 심어서 전달해줘야지. 하는 생각이 들었다.  

**참고자료**
- [OKKY](https://okky.kr/article/554182)
- [tistory](https://osc131.tistory.com/118)

**코드**  
src/main/java/com.chartnomy.indicator/config/WebMvcConfig.java
```java
package com.chartnomy.indicators.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:9090");
	}
}
```