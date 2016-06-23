package com.mvc.demo.model.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;


/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Table(name="products")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRODUCTS_PRODUCTID_GENERATOR", sequenceName="PRODUCTS_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCTS_PRODUCTID_GENERATOR")
	@Column(name="product_id", unique=true, nullable=false)
	private Long productId;

	private Integer count;

	@Column(length=500)
	private String description;

	@Column(length=200)
	@NotBlank(message = "must be not empty")
	private String name;

	@Column(name = "price")
	private double price;
	
	@Column(name="image")
	private byte[] image;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public double getPrice() {
		return this.price;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String toString() {
		return String.format("id = %d, name = %s, desc = %s, price = %f, count = %d, cat = %s", 
				productId,name, description, price, count, category.getName());
	}

}