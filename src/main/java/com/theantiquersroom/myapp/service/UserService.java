package com.theantiquersroom.myapp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.theantiquersroom.myapp.domain.Criteria;
import com.theantiquersroom.myapp.domain.ProductVO;
import com.theantiquersroom.myapp.domain.UserDTO;
import com.theantiquersroom.myapp.domain.UserVO;


@Service
public interface UserService {

    // 회원가입
    public abstract boolean registerUsers(UserVO user);

    // 아이디 중복 검사
    public abstract boolean checkId(String userId);

    // 닉네임 중복검사
    public abstract boolean checkNickName(String nickName);

    // 연락처 중복검사
    public abstract boolean checkPhone(String phone);

    // 이메일 인증번호 매칭검사
    public abstract boolean confirmEmail(String eCode);

    // 이메일 발송
    public abstract boolean sendEmail(String userId) throws Exception;

    // 로그인 실행
    public abstract boolean login(String userId, String password);
    
    // 아이디 찾기 실행
    public abstract UserVO findId(String nickName, String phone);
    
    // 비밀번호 재설정
    public abstract boolean resetPwd(String userId, String nickName) throws Exception;

    
    // 회원정보 수정
    public abstract boolean modify(UserDTO userDto);
    
    // 회원 탈퇴
    public abstract boolean remove(String userId);
    
    // 나의 경매리스트 조회
    public abstract List<ProductVO> getMyAuctionList(Criteria cri);
    
    // 나의 입찰리스트 조회
    public abstract List<ProductVO> getBidList(Criteria cri);
    
} // end interface


