package com.mvc.demo.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.mvc.validator.Unique;

/**
 * The persistent class for the categories database table.
 * 
 */
@Entity
@Table(name="categories")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORIES_CATEGORYID_GENERATOR", sequenceName="CATEGORIES_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORIES_CATEGORYID_GENERATOR")
	@Column(name="category_id", unique=true, nullable=false)
	private Long categoryId;

	@Column(length=500)
	private String description;

	@Column(length=200)
	@NotBlank(message = "must be not empty")
	//@Unique(entity=Category.class, property = "", entityName = "categories", uniqueField = "name")
	private String name;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="category", fetch=FetchType.EAGER)
	private List<Product> products;


	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setCategory(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCategory(null);

		return product;
	}
	
	public String toString() {
		return String.format("id = %s, name = %s, desc = %s", categoryId, name, description);
	}

}