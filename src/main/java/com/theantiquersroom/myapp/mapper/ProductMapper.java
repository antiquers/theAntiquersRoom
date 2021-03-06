package com.theantiquersroom.myapp.mapper;


import com.theantiquersroom.myapp.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    // 입찰 최고가 구하기
    public Integer getMaxBid(@Param("pid") Integer pid);

    // 입찰 등록
    public Integer insertBid(BidHistoryDTO bidHistoryDTO);

    // 입찰 히스토리 SELECT
    public List<BidHistoryDTO> getBidHistory(Integer pid);

    // 상품번호에 따른 상품 상세정보 불러오기
    public abstract ProductDTO getDetailByPId(Integer pid);

    // 상품번호에 따른 상품별 이미지 파일 불러오기
    public abstract List<String> getProductImageUrls(Integer pid);

    //상품삭제
    public abstract Integer deleteProduct(Integer pid);

    // 상품 수정 정보 불러오기
    public ProductModifyDTO getupdateByPId(Integer pid);

    // 상품 정보 수정
    public Integer updateProduct(ProductFormDTO product);

    public void deleteProductImage(@Param("pid") Integer pid);

    // 새로 들어온 상품 조회
    public List<ProductDTO> getNewProduct();

    // 마감 임박 상품 조회
    public List<ProductDTO> getEndingProduct();

    // 유찰 등록
    public Integer reRegister(ProductReRegisterDTO reRegister);

} //end interface

