package com.mvc.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.mvc.demo.model.entity.Category;
import com.mvc.demo.model.entity.Product;
import com.mvc.demo.model.repository.CategoryRepository;


@Controller
@RequestMapping("/categories")
public class CategoryListController 
{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
	/**
	 * View to Categories List
	 * 
	 * @param id	Selected Category
	 */
	@RequestMapping(value="", method = RequestMethod.GET)
	public ModelAndView categoryList(@RequestParam(value="id", required=false) Long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("categories");
		
		mav.addObject("categories", categoryRepository.findAll());
		mav.addObject("updateId", id);
		
		return mav;
	}
	
	
	
	/**
	 * on Delete Category
	 * 
	 * @param id
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public View deleteCategory(@ModelAttribute("id") Long id) {
		System.out.println("delete category " + categoryRepository.findOne(id).toString());
		
		categoryRepository.delete(id);
		
		RedirectView redirect = new RedirectView("/555/categories");
	    redirect.setExposeModelAttributes(false);
	    return redirect;
	}
	
	
	
	/**
	 * on Create new Category
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addProduct(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
		
		//check on user inputs 
		if (bindingResult.hasErrors())
		    return "addCategory";

		categoryRepository.save(category);

	    return "redirect:/categories";
	}
	
	
	
	/**
	 * View to Create new Category Form
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addCategoryView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("addCategory");
		
		return mav;
	}
	
}
