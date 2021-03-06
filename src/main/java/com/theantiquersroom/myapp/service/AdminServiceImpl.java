package com.theantiquersroom.myapp.service;

import java.util.List;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theantiquersroom.myapp.domain.MypageCriteria;
import com.theantiquersroom.myapp.domain.ProductDTO;
import com.theantiquersroom.myapp.domain.UserDTO;
import com.theantiquersroom.myapp.domain.UserVO;
import com.theantiquersroom.myapp.mapper.AdminMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Service
public class AdminServiceImpl implements AdminService {
	
	@Setter(onMethod_= {@Autowired})
	AdminMapper mapper;

	@Override
	public List<ProductDTO> getRequestedList(MypageCriteria cri) {
		log.debug("getRequestedList({}) invoked.",cri);
		
		List<ProductDTO> list=this.mapper.getRequestedList(cri);
		log.info("\t+ list size: {}", list.size());
		
		return list;
	}//getRequestedList

	@Override
	public Integer getRequestedListTotal() {
		log.debug("getRequestedListTotal({}) invoked.");

		return this.mapper.getRequestedListTotal();
	}//getRequestedListTotal

	@Override
	public Boolean modifyStatus(Integer pid) {
		log.debug("modifyStatus({}) invoked.", pid);
		
		int affectedRows=this.mapper.updateStatus(pid);
		log.info("\t+ affectedRows: {}", affectedRows);
		
		return affectedRows>0;
	} // modifyStatus

	@Override
	public Boolean rejectRequest(Integer pid) {
		log.debug("rejectRequest({}) invoked.", pid);
		
		int affectedRows=this.mapper.rejectRequest(pid);
		log.info("\t+ affectedRows: {}", affectedRows);
		
		return affectedRows>0;
	}//modifyStatus
  
  @Override
	public List<ProductDTO> getAuctionProductList(MypageCriteria cri) {
		log.debug("getAuctionProductList({}) invoked.",cri);
		
		List<ProductDTO> list=this.mapper.getAuctionProductList(cri);
		log.info("\t+ list size: {}", list.size());
		
		return list;
	}//getAuctionProductList

	@Override
	public Integer getAuctionTotal(String keyword, String status) {
		log.debug("getAuctionTotal({}, {}) invoked.", keyword, status);

		return this.mapper.getAuctionTotal(keyword, status);
	}//getAuctionTotal

	@Override
	public Boolean stopSale(Integer pid) {
		log.debug("stopSale({}) invoked.",pid);
		
		int affectedRows=this.mapper.stopSale(pid);
		log.info("\t+ affectedRows: {}", affectedRows);
		
		return affectedRows>0;
	}//stopSale
  
  @Override
	public List<UserDTO> getUserList(MypageCriteria cri) {
		log.debug("getUserList({}) invoked.", cri);
		
		List<UserDTO> users=this.mapper.selectUserList(cri);
		
		return users;
	} //getUserList

	@Override
	public Integer getTotalUsersCount() {
		log.debug("getTotalUsersCount() invoked.");
		
		return this.mapper.getTotalUsersCount();
	} //getTotalUsersCount

	@Override
	public List<UserVO> searchUser(String nickName) {
		log. debug("serchUser({}) invoked.", nickName);
		
		return this.mapper.selectUserByNick(nickName);
	} //searchUser

}//end class
