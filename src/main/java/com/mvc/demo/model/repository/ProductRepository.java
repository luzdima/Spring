package com.mvc.demo.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.mvc.demo.model.entity.Category;
import com.mvc.demo.model.entity.Product;


public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	
	Page<Product> findByCategory(Category c, Pageable pageable);
	
	Page<Product> findAll(Pageable pageable);
	
}
