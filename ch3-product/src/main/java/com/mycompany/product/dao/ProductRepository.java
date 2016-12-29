package com.mycompany.product.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import com.mycompany.product.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	@Cacheable("productsByCategoryCache")
	List<Product> findByCatId(int catId);
}
