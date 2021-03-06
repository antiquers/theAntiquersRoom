package com.theantiquersroom.myapp.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.theantiquersroom.myapp.domain.ProductDTO;
import com.theantiquersroom.myapp.domain.ProductFormDTO;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ProductMapperTests {

	@Setter(onMethod_= {@Autowired})
	private ProductMapper mapper;
	
	
	@Before
	public void setup() {
		log.debug("setup() invoked.");
		
		assert this.mapper != null;
		log.info("\t+ mapper: {}", mapper);
		log.info("\t+ type: {}", this.mapper.getClass().getName());
		
	} //setup
	
    // 상품 등록 Test
    @Test
    public void testInsert() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ProductFormDTO product = new ProductFormDTO();
            product.setName("이름");
            product.setContent("내용");
            product.setCategoryId(1);
            product.setUserId("asdfasdf@gmail.com");
            product.setStartedAt(LocalDateTime.parse("2021-11-15 00:00:10", formatter));
            product.setEndedAt(LocalDateTime.parse("2021-11-16 00:00:15", formatter));
            product.setStartedPrice(5000);
            product.setBidIncrement(500);
            product.setStatus("REQUESTED");

        int affectedLines = this.mapper.insertProduct(product);
        log.info("\t+ affectedLines: {}", affectedLines);
    }

	@Test
	public void testGetDetailByPId() {
		log.debug("testGetDetailByPId() invoked.");
		
		Integer pId = 30;

		ProductDTO dto = this.mapper.getDetailByPId(pId);
		
		log.info("\t+ dto: {}", dto);
	} //testGetDetailByPId
	
	@Test
	public void testGetProductImageUrls() {
		log.debug("testGetProductImageUrls() invoked.");
		
		Integer pId = 76;
		List<String> urls = this.mapper.getProductImageUrls(pId);
		
		log.info("\t+ urls: {}", urls);
	} //testGetProductImageUrls

	// 상품 수정 테스트
	@Test
	public void testModify() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ProductFormDTO product = new ProductFormDTO();
		product.setPid(5);
		product.setName("바뀌어라이름이름");
		product.setContent("금요일이당");
		product.setCategoryId(1);
		product.setStartedAt(LocalDateTime.parse("2021-11-26 02:16:17",formatter));
		product.setEndedAt(LocalDateTime.parse("2021-11-28 02:16:17",formatter));
		product.setStartedPrice(300);
		product.setBidIncrement(5000);
		product.setStatus("승인대기중");

		Integer result = mapper.updateProduct(product);
		log.info("result :" + result);
	}
	
} //end class
