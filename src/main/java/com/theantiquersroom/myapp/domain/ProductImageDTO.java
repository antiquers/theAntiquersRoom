package com.theantiquersroom.myapp.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.util.Date;

@Data
public class ProductImageDTO {

    private Integer imageId; // 시퀀스 이미지번호
    private String imageName; // 실제 파일 이름
    private String imageUrl; // 실제 업로드된 경로
    private Integer pId; // 상품등록번호

}
