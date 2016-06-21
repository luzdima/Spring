package com.mvc.demo.controller;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.mvc.demo.model.entity.Product;
import com.mvc.demo.model.repository.CategoryRepository;
import com.mvc.demo.model.repository.ProductRepository;


@Controller
@RequestMapping("/products")
public class ProductListController 
{
	private static final String allCategories = "-";	//all categories value in user's input "select category"
	private static final Integer defaultLimit = 10;		//default rows on page
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	
	
	/**
	 * View to Product List
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView productsView(
			@RequestParam(value="category", required=false) String selectedCategory,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="limit", required=false) Integer limit,
			@RequestParam(value="sortDir", required=false) Sort.Direction sortDir,
			@RequestParam(value="sortPar", required=false) String sortPar,
			HttpSession session
	) {
		
		initSession(session);
		
		//=== set request params ===
		if (limit == null || page == null){
			page = 0; 
			limit = (Integer)session.getAttribute("limit");
		}
		
		
		Pageable pageable;
		if (sortDir == null || sortPar == null)
			pageable = new PageRequest(page, limit);
		else
			pageable = new PageRequest(page, limit, new Sort(sortDir, sortPar));

		
		if (selectedCategory == null) 
			selectedCategory = allCategories;
		
		
		Page<Product> curPage;
		if (selectedCategory.equals(allCategories))
			curPage = productRepository.findAll(pageable);
		else
			curPage = productRepository.findByCategory(categoryRepository.getByName(selectedCategory),pageable);
			
		
		//=== set View ===
		ModelAndView mav = new ModelAndView();
		mav.setViewName("products");
		
		mav.addObject("selectedCategory", selectedCategory);
		mav.addObject("categoriesList", categoryRepository.findAll());
		mav.addObject("productsList", curPage.getContent());
		mav.addObject("curPage", curPage.getNumber());
		mav.addObject("pageSize", curPage.getTotalPages());
		mav.addObject("sortDir", sortDir);
		mav.addObject("sortPar", sortPar);
		
		
		System.out.println(String.format("selected category = %s, page = %d, limit = %d", 
				selectedCategory, page, limit));
		System.out.println("curPage = " + curPage.getNumber() + " total = " + curPage.getTotalPages());
		System.out.println();
		
		
		return mav;
	}
	
	
	
	/**
	 * View to Create new Product 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView  addProductView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("addProduct");
		
		mav.addObject("categoriesList", categoryRepository.findAll());
		
		return mav;
	}

	
	
	/**
	 * on Create new Product
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addProduct(
			@Valid @ModelAttribute("product") Product product,
			BindingResult bindingResult, RedirectAttributes redir
	) {
		
		//if invalid input then ask for reenter data
		if (bindingResult.hasErrors()) {
			return "addProduct";
		}

		//product.setCategory(categoryRepository.findByName(categoryName));
		productRepository.save(product);
		
	    //redir.addFlashAttribute("catName", categoryName);
	    redir.addFlashAttribute("product", product);
	    return "redirect:/products";
	}
	
	
	
	/**
	 * on Delete Product
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public View deleteProduct(
			@ModelAttribute("id") Long id, 
			HttpServletRequest request, HttpSession session) {

		productRepository.delete(id);
		
		RedirectView redirect = new RedirectView("/555/products" + genRequestParameters(request, session));
	    redirect.setExposeModelAttributes(false);
	    return redirect;
	}
	
	
	
	/**
	 * View to Update Product
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateProduct(@RequestParam(value="id", required=true) Long id) {
		
		//use Form for Create new Product with filled inputs
		ModelAndView mav = addProductView();
		mav.addObject("product", productRepository.findOne(id));
		
		return mav;
	}
	
	
	
	/**
	 * on change rows limit on Page
	 */
	@RequestMapping(value = "setLimit", method = RequestMethod.POST)
	public ModelAndView setLimit(
			@RequestParam(value="limit", required=true) Integer limit,
			HttpServletRequest request, HttpSession session) {

		session.setAttribute("limit", limit);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/products" + genRequestParameters(request, session));
		
		return mav;
	}
	
	
	
	/**
	 * generate parameterized string for GET request 
	 * 
	 * @return		parameterized string
	 */
	private String genRequestParameters(HttpServletRequest request, HttpSession session) {
		String res = "?";
		
		if (request.getParameter("category") != null) 
			res += "category=" + request.getParameter("category").replaceAll(" ", "+") + "&";
		if (request.getParameter("page") != null) 
			res += "page=" + request.getParameter("page") + "&";	
		if (session.getAttribute("limit") != null) 
			res += "limit=" + session.getAttribute("limit") + "&";
		if (request.getParameter("sortDir") != null) 
			res += "sortDir=" + request.getParameter("sortDir") + "&";
		if (request.getParameter("sortPar") != null) 
			res += "sortPar=" + request.getParameter("sortPar").replaceAll(" ", "+") + "&";

		return res;
	}
	
	
	
	/**
	 * set default values for user's inputs if it doesn't initialized yet
	 * 
	 * @param session
	 */
	private void initSession(HttpSession session) {
		/*if (session.getAttribute("category") == null) 
			session.setAttribute("category", allCategories);
		if (session.getAttribute("page") == null) 
			session.setAttribute("page", 0);	*/
		if (session.getAttribute("limit") == null) 
			session.setAttribute("limit", defaultLimit);
	}
	
	
}
