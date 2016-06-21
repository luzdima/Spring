package com.mvc.demo.model.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mvc.demo.model.entity.Category;


public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	List<Category> findAll();

	Category getByName(String selectedCategory);
	
	Category getByCategoryId(Long categoryId);

	Category findByName(String categoryName);
}
