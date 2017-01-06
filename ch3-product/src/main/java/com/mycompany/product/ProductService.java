package com.mycompany.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.product.dao.ProductRepository;
import com.mycompany.product.entity.Product;

@RestController
public class ProductService {

	@Autowired
	ProductRepository prodRepo ;
	
	@RequestMapping(value="/product/{id}", method = RequestMethod.GET )
	ResponseEntity<Product> getProduct(@PathVariable("id") int id) {
		Product prod = prodRepo.findOne(id);
		if (prod == null)
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<Product>(prod, HttpStatus.OK) ;
	}
	
	@RequestMapping("/products")
	List<Product> getProductsForCategory(@RequestParam("id") int id) {
		return prodRepo.findByCatId(id);
	}
	
	@RequestMapping(value="/product/{id}", method = RequestMethod.PUT)
	ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
		
		// First fetch an existing product and then modify it. 
		Product existingProduct = prodRepo.findOne(id); 
		if (existingProduct == null) {
			String errMsg = "Product Not found with code " + id ;
			System.out.println(errMsg);
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		
		// Now update it back 
		existingProduct.setCatId(product.getCatId());
		existingProduct.setName(product.getName());
		Product savedProduct = prodRepo.save(existingProduct) ;
		
		// Return the updated product with status ok 
		return new ResponseEntity<Product>(savedProduct, HttpStatus.OK);		
	}
	
	@RequestMapping(value="/product", method = RequestMethod.POST)
	ResponseEntity<Product> insertProduct(@RequestBody Product product) {
		
		Product savedProduct = prodRepo.save(product) ;
		return new ResponseEntity<Product>(savedProduct, HttpStatus.OK);		
	}
	
	
	@RequestMapping(value="/product/{id}", method = RequestMethod.DELETE)
	ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
		
		// First fetch an existing product and then delete it. 
		Product existingProduct = prodRepo.findOne(id); 
		if (existingProduct == null) {
			String errMsg = "Product Not found with code " + id ;
			System.out.println(errMsg);
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND );
		}
		
		// Return the inserted product with status ok
		prodRepo.delete(existingProduct);
		return new ResponseEntity<Product>(HttpStatus.OK);		
	}
}
