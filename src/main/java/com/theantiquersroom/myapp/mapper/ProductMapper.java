package com.theantiquersroom.myapp.mapper;


import com.theantiquersroom.myapp.domain.BidHistoryDTO;
import com.theantiquersroom.myapp.domain.ProductImageDTO;
import com.theantiquersroom.myapp.domain.ProductFormDTO;
import com.theantiquersroom.myapp.domain.ProductCommand;
import com.theantiquersroom.myapp.domain.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;


@Mapper
public interface ProductMapper {


    // 상품등록
    public Integer insertProduct(ProductFormDTO product);
  
    // 상품 이미지 등록
    public Integer insertProductImage(ProductImageDTO image);

    // 페이징 처리를 위한 메서드
    public List<ProductDTO> listCriteria(HashMap<Object,Object> map)throws Exception;

    // 전체 게시글 수 구하기
    public Integer getTotalCount(ProductCommand productCommand)throws Exception;

    // 입찰 최고가 구하기
    public Integer getMaxBid(@Param("pId") Integer pId);

    // 입찰 등록
    public Integer insertBid(BidHistoryDTO bidHistoryDTO);

	  // 상품번호에 따른 상품 상세정보 불러오기
    // public abstract ProductDTO getDetailByPId(Integer pId);
	// 상품번호에 따른 상품 상세정보 불러오기
	public abstract ProductDTO getDetailByPId(Integer pId);

	//상품삭제
	public abstract Integer deleteProduct(Integer pId);

} //end interface

