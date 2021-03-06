package com.theantiquersroom.myapp.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductModifyDTO {

    // 상품등록관련
    private Integer pid;
    private String name;
    private String content;
    private Integer categoryId;
    private String categoryName;

    // 경매관련
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endedAt;
    private Integer startedPrice;
    private Integer bidIncrement;

    // 이미지 관련
    private List<ProductImageDTO> imageList;

    // form images
    private List<MultipartFile> images;

    private List<String> imageUrls; // 파일별 등록된 이미지들의 주소

}
