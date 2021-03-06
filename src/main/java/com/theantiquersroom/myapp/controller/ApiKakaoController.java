package com.theantiquersroom.myapp.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.theantiquersroom.myapp.domain.KakaoTokenVO;
import com.theantiquersroom.myapp.domain.KakaoUserInfoVO;
import com.theantiquersroom.myapp.domain.UserDTO;
import com.theantiquersroom.myapp.service.UserService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Controller
public class ApiKakaoController {
	
    @Setter(onMethod_= {@Autowired})
    private UserService service;
	
    public static final String REST_API_KEY = "12a4433b06d9de4acedcd972fa6a3fa9";
    private static final String REDIRECT_URI = "http://localhost:8090/login/kakao";
    
	
	//카카오 로그인창 호출
	@RequestMapping("/login/getKakaoAuthUrl")
    public @ResponseBody String getKakaoAuthUrl(HttpServletRequest req) {	
        log.debug("getKakaoAuthUrl() invoked.");
        
        String reqUrl = 
        		"https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="
        		+ ApiKakaoController.REST_API_KEY
        		+ "&redirect_uri="
        		+ ApiKakaoController.REDIRECT_URI;
        
        return reqUrl;
    } //getKakaoAuthUrl
	
	
	//카카오 인가 코드 받기
	@RequestMapping(value="/login/kakao")
	public String oauthKakao(HttpServletRequest req, HttpServletResponse res, HttpSession session, Model model) throws Exception{
		log.debug("oauthKakao() invoked.");
		
		String code = req.getParameter("code"); //토큰 받기 요청에 필요한 인가 코드
		String error = req.getParameter("error"); //인증 실패 시 반환되는 에러 코드
		
		log.info("\t + code : {}", code);
		log.info("\t + error : {}", error);
		
		//사용자가 로그인을 취소했거나, 만 14세 미만 사용자의 보호자 동의에 실패한 경우, 로그인 초기 화면으로 이동
		if(error != null) {
			if(error.equals("access_denied")) {
				return "/login";
			}
		}
		
	    String accessToken = getAccessToken(req, code);
	    String kakaoUniqueId = getUserId(accessToken);
	    
		log.info("\t + accessToken : {}", accessToken);
		log.info("\t + userId : {}", kakaoUniqueId);
		
	    
	    if(kakaoUniqueId != null) { 
	    	session.setAttribute("kakaoUniqueId", kakaoUniqueId);
		    if(service.getKakaoUser(kakaoUniqueId) != null) { //이미 카카오 통해 가입한 사람이라면,
		        UserDTO user=this.service.getKakaoUser(kakaoUniqueId);
		        session.setAttribute(LoginController.authKey, user);

		    	return "redirect:/"; //메인 화면으로 이동
		    	
		    }else {
		    	return "/registerByKakao"; //카카오-회원가입 페이지로 이동
		    }//if-else
		    
	    }else {
		    return "/login";
	    }//if-else
	    
	}//oauthKakao
	
	
	//카카오 통한 회원가입 화면 요청
    @GetMapping("/registerByKakao")
    public String registerByKakao() {
        log.debug("registerByKakao() invoked.");
        
		return "redirect:/registerByKakao";
    } //register

    
    //카카오 통한 회원가입 서비스 수행, 저장
    @PostMapping("/registerByKakao")
	public @ResponseBody Map<String, Object> register(@RequestBody Map<String,Object> map, HttpSession session) { //회원가입 서비스 수행, 저장

		UserDTO user = new UserDTO();
		user.setUserId((String)map.get("userId"));
		user.setPhone((String)map.get("phone"));
		user.setNickName((String)map.get("nickName"));
		user.setKakaoUniqueId((String)session.getAttribute("kakaoUniqueId"));
		log.debug("register({}) invoked.", user);


		boolean result = this.service.registerKakaoUser(user);

		Map<String,Object> resultMap = new HashMap<>();

		resultMap.put("result",result);

		return resultMap;

	} //registerCheck
	
	
	//액세스 토큰, 리프레시 토큰 받기
	public String getAccessToken(HttpServletRequest request, String code) throws Exception {
		log.debug("getAccessToken() invoked.");

	    String accessToken = ""; //사용자 액세스 토큰 값	
	    String refreshToken = ""; //사용자 리프레시 토큰 값	

	    // restTemplate을 사용하여 API 호출
	    RestTemplate restTemplate = new RestTemplate();
	    String reqUrl = "https://kauth.kakao.com/oauth/token";
	    URI uri = URI.create(reqUrl);
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	    MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
	    parameters.set("grant_type", "authorization_code");
	    parameters.set("client_id", ApiKakaoController.REST_API_KEY);
	    parameters.set("redirect_uri",ApiKakaoController.REDIRECT_URI);
	    parameters.set("code", code);
	    

	    HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
	    
	    ResponseEntity<KakaoTokenVO> jsonResult =restTemplate.exchange(uri, HttpMethod.POST, restRequest, KakaoTokenVO.class);
	    
	    KakaoTokenVO responseBody = jsonResult.getBody();
	    log.info("\t + responseBody : {}", responseBody.toString());

	    accessToken = responseBody.getAccess_token();
	    log.info("\t + accessToken : {}", accessToken);
	    
	    refreshToken = responseBody.getRefresh_token();
	    log.info("\t + refreshToken : {}", refreshToken);
	    
	    return accessToken;
	}//getAccessToken

	
	//유저 카카오계정 ID 정보 가져오기
	public String getUserId (String accessToken) throws Exception {
	   log.debug("getUserId({}) invoked.", accessToken);

	    String userId = ""; //사용자 id   

	    // restTemplate을 사용하여 API 호출
	    RestTemplate restTemplate = new RestTemplate();
	    String reqUrl = "https://kapi.kakao.com/v2/user/me";
	    URI uri = URI.create(reqUrl);
	       
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	    headers.set("Authorization", "Bearer " + accessToken);
	    
	    HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(headers);
	       
	    ResponseEntity<KakaoUserInfoVO> result =restTemplate.postForEntity(uri,  restRequest, KakaoUserInfoVO.class);
	       
	    KakaoUserInfoVO responseBody = result.getBody();
	    log.info("\t + responseBody : {}", responseBody.toString());

	    userId = Long.toString(responseBody.getId());
	    log.info("\t + userId : {}", userId);
	       
	    return userId;
	}//getUserId

	
}  //end class
