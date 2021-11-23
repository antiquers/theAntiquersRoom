package com.theantiquersroom.myapp.mapper;


import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.theantiquersroom.myapp.domain.ProductCommand;
import com.theantiquersroom.myapp.domain.ProductDTO;
import com.theantiquersroom.myapp.domain.ProductFormDTO;
import com.theantiquersroom.myapp.domain.ProductImageDTO;


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

	// 상품번호에 따른 상품 상세정보 불러오기
	public abstract ProductDTO getDetailByPId(Integer pId);
	
	// 상품번호에 따른 상품별 이미지 파일 불러오기
	public abstract List<String> getProductImageUrls(Integer pId);
	
	//상품삭제
	public abstract Integer deleteProduct(Integer pId);
	
} //end interface

