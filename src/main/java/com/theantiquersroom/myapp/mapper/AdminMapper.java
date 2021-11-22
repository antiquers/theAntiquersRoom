package com.theantiquersroom.myapp.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.theantiquersroom.myapp.domain.MypageCriteria;
import com.theantiquersroom.myapp.domain.ProductDTO;


@Mapper
public interface AdminMapper {
	
	// 승인요청상품 총 게시물 개수를 반환
	public abstract Integer getRequestedListTotal();
	
    //경매상태가 "승인대기중"인 상품 리스트 반환
    public abstract List<ProductDTO> getRequestedList(MypageCriteria cri);
    
}// end interface
