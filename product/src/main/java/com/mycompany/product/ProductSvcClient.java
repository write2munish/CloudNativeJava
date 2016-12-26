package com.mycompany.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ProductSvcClient {

	@Autowired
	public RestTemplate restTemplate;	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(ProductSvcClient.class, args);
		
		ProductSvcClient svcClient = new ProductSvcClient();
		Product product = svcClient.getProduct();
		System.out.println(product);
	}
	
	public Product getProduct() {
		String url = "http://Product/dev/product/3" ;
        ResponseEntity<Product> resultStr = restTemplate.getForEntity(url, Product.class);

        Product product = resultStr.getBody();
        return product ;

	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

}
