package com.mvc.demo.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mvc.demo.model.entity.Product;
import com.mvc.demo.model.repository.CategoryRepository;
import com.mvc.demo.model.repository.ProductRepository;



@Controller
@RequestMapping("/products/add")
public class AddProductController{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	
	
	/**
	 * View to Create new Product 
	 * 
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView  addProductView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("addProduct");
		
		mav.addObject("categoriesList", categoryRepository.findAll());
		
		return mav;
	}

	
	
	/**
	 * on Create new Product
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView addProduct(
			@Valid @ModelAttribute("product") Product product,
			BindingResult bindingResult, RedirectAttributes redir,
			@RequestParam("file") MultipartFile image
	) {
		
		ModelAndView mav = new ModelAndView();
		
		//if invalid input then ask for reenter data
		if (bindingResult.hasErrors()) {
			mav.setViewName("addProduct");
		}

				
		if(!image.isEmpty()){
			//if file isn't image type
			if (!image.getContentType().startsWith("image/")) {
			    mav.addObject("fileError", true);
			    mav.setViewName("addProduct");
			    return mav;
			}
			
            try {
				product.setImage(image.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }

		
		productRepository.save(product);
		
	    redir.addFlashAttribute("product", product);
	    mav.setViewName("redirect:/products");
	    return mav;
	}

}
