package com.theantiquersroom.myapp.service;



import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theantiquersroom.myapp.domain.Criteria;
import com.theantiquersroom.myapp.domain.ProductVO;
import com.theantiquersroom.myapp.domain.UserDTO;
import com.theantiquersroom.myapp.domain.UserVO;
import com.theantiquersroom.myapp.mapper.UserMapper;
import com.theantiquersroom.myapp.utils.Mailsender;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;




@AllArgsConstructor
@Log4j2

@Service
public class UserServiceImpl implements UserService, InitializingBean, DisposableBean{


    @Setter(onMethod_= {@Autowired})
    Mailsender mailsender;
    UserMapper mapper;



    @Override
    public boolean registerUsers(UserVO user) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkId(String userId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkNickName(String nickName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkPhone(String phone) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean confirmEmail(String userId, String auth) {

        Date serverTime = mapper.selectNow();
        log.debug("serverTime : {}",serverTime);
        log.debug("userId : {}",mapper.selectAuth(userId));

        return false;
    }

    @Override
    public boolean sendEmail(String userId) throws Exception {

        log.debug("userId : {} nickname : {} ",userId);
        boolean mailSentCheck = false;
        String nick = mapper.selectUserNickname(userId);

        log.debug(nick);

        String authorizationNumber = Integer.toString((int)(Math.random()*3000+1));

        if(userId != null){

            mailsender.sendmail("authorizationNumber : "+ authorizationNumber,userId);

            mapper.insertAuthorizationNumber(userId,authorizationNumber);

            mailSentCheck = true;

        }

        return mailSentCheck;
    }


    @Override
	public boolean login(String userId, String password) {
		log.debug("login({}, {}) invoked.", userId, password);

		UserVO vo =this.mapper.login(userId);
		log.info("\t+ vo: {}", vo);
		
		assert vo != null;

		return (vo.getPassword().equals(password));
	}

	@Override
	public UserVO findId(String nickName, String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean resetPwd(String userId, String nickname) throws Exception {

        log.debug("userId : {} nickname : {} ",userId,nickname);
        boolean mailSentCheck = false;
        String nick = mapper.selectUserNickname(userId);

        log.debug(nick);

        String newpassword = Integer.toString((int)(Math.random()*3000+1));

        if(nick.equals(nickname) && nick!=null){
            log.debug("yes you can");

           mailsender.sendmail("your new password is : "+ newpassword,userId);

            mapper.updatePassword(newpassword,userId);

            mailSentCheck = true;

        }

        return mailSentCheck;
	}

	@Override
	public boolean modify(UserDTO userDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ProductVO> getMyAuctionList(Criteria cri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductVO> getBidList(Criteria cri) {
		// TODO Auto-generated method stub
		return null;
	}
    
//---------------------------------------------------//
    @Override
    public void destroy() throws Exception {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
    	// TODO Auto-generated method stub
    	
    }

} // end class
