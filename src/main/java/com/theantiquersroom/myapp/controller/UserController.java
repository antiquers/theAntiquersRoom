package com.theantiquersroom.myapp.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.theantiquersroom.myapp.domain.*;
import com.theantiquersroom.myapp.service.ChatService;
import com.theantiquersroom.myapp.service.UserService;
import com.theantiquersroom.myapp.utils.ProductPageMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.theantiquersroom.myapp.service.UserService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/users")
@Controller
public class UserController {

    @Setter(onMethod_= {@Autowired})
    private UserService service;

    @Setter(onMethod_= {@Autowired})
      private ChatService chatService;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session) {	// 로그아웃 실행
        log.debug("logout() invoked.");
        
        String kakaoUniqueId = (String) session.getAttribute("kakaoUniqueId");
       
        if(kakaoUniqueId != null) { //카카오로 로그인했다면, 카카오계정 로그아웃도 함께 진행
        	log.debug("===== kakao logout");
        	
            String logout_redirect_uri = "http://localhost:8090";

            String reqUrl = 
            		"https://kauth.kakao.com/oauth/logout?client_id="
            		+ ApiKakaoController.REST_API_KEY
            		+ "&logout_redirect_uri="
            		+ logout_redirect_uri;
            
            session.invalidate();
            
            return reqUrl;
            
        }else { //일반회원 로그아웃 시 세션 초기화
        	log.debug("===== 일반회원 logout");
            session.invalidate();
            
            return "redirect:/";
        }
    } //logout

    @GetMapping("/myAuctionList")
    public String getMyAuctionList(
    		HttpSession session,
    		@ModelAttribute("cri") MypageCriteria cri,
    		Model model) {	// 나의 경매리스트 페이지로 이동
        log.debug("getMyAuctionList({}, {}) invoked.", cri, model);

        UserDTO user = (UserDTO) session.getAttribute(LoginController.authKey);
        String userId = user.getUserId();
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("cri", cri);
        
        List<ProductDTO> myAuctionList = this.service.getMyAuctionList(map);
 		log.info("\t+ myAuctionList size: {}", myAuctionList.size());

 		model.addAttribute("myAuctionList",myAuctionList);
	
 		//페이징 처리
 		Integer totalAmount = this.service.getMyAuctionTotal(userId);
		
		MyPageDTO pageDTO = new MyPageDTO(cri, totalAmount);
		
		model.addAttribute("pageMaker", pageDTO);
    
		return "/users/myAuctionList";
    } //getMyAuctionList


    @GetMapping("/getBidList")
    public String getBidList() {	// 나의 입찰리스트 페이지로 이동
        log.debug("getBidList() invoked.");

        return "/user/myBidList";
    } //getBidList
 	
 	@GetMapping({"/modify" , "/mypage"})
 	public void get(String userId, Model model) {         
 		log.debug("get({}, {}) invoked." , userId, model);
 		
 		UserDTO user = this.service.get(userId);
 		log.info("\t+ board: {}" , user);
 		
 		model.addAttribute("user", user);
 	} // mypage, modify
 	
 	@PostMapping("/modify")
 	public String modify(modifyDTO user, RedirectAttributes rttrs) {
 		log.debug("modify({}, {}) invoked.", user,rttrs);
 		
 		boolean result=this.service.modify(user);
 			
 		return "redirect:/users/mypage";
 	} //modify
 	
 	
    // 아이디 찾기 페이지 이동
	@GetMapping("/findId")
	public String findIdView() {
		return "users/findId";
	} // findId
	
    // 아이디 찾기 실행
	@PostMapping("/findId")
	public String findIdAction(UserDTO dto, Model model) {
		UserDTO user = this.service.findId(dto);
		
		if(user == null) { 
			model.addAttribute("check", 1);
		} else { 
			model.addAttribute("check", 0);
			model.addAttribute("userId", user.getUserId());
		}
		
		return "users/findId";
	} // findId

    
	@PostMapping("/remove")
	public String remove(
			@RequestParam("userId") String userId,
			RedirectAttributes rttrs) 
	{
		log.debug("remove({}) invoked.", userId);
		
		boolean result=this.service.remove(userId);
		rttrs.addAttribute("result", result);
		
		return "redirect:/users/getUserList";
	} //remove

	@GetMapping("/getMyBidList")
	public String getMyBidList(
			HttpSession session,
			@ModelAttribute("cri") ProductCriteria cri,
			Model model) {	// 나의 입찰리스트 페이지로 이동
		log.debug("getMyAuctionList({}, {}) invoked.", cri, model);

		UserDTO user = (UserDTO) session.getAttribute(LoginController.authKey);
		String userId = user.getUserId();
		cri.setPerPageNum(7);
		HashMap<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("cri", cri);

		List<ProductDTO> myBidList = this.service.getMyBidList(cri,map);

		model.addAttribute("myBidList",myBidList);

		ProductPageMaker pageMaker =new ProductPageMaker();
		//페이징 처리
		pageMaker.setCri(cri);
		Integer totalNum = this.service.getMyBidTotal(userId);
		log.debug("totalNum {} ", totalNum);
		pageMaker.setTotalCount(totalNum);

		model.addAttribute("pageMaker", pageMaker);

		return "/users/myBidList";
	} //getMyBidList

	@GetMapping("/chat")
	public String chat(@RequestParam("productId") Integer pId, Model model, HttpSession session){

    	//아이디 체크
		//True or False 반환

		//만약 True 이면 계속 진행

    	//매퍼에서 기존 DB에 저장되어있던 채팅정보들을 가져옵니다..
		// List<ChatDTO> list = service.getChat(pId);

		//model에 지정해줍니다.
		//model.addAtrribute("chatList",list);
		UserDTO user = (UserDTO) session.getAttribute(LoginController.authKey);

		String userId = user.getUserId();

		model.addAttribute("userId",userId);
		model.addAttribute("productId",pId);
		model.addAttribute("chatList",chatService.getChat(pId));

    	return "/users/chat";
	}

}  //end class
