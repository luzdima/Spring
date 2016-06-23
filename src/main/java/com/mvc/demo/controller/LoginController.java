package com.mvc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error) {

		ModelAndView mav = new ModelAndView();
		if (error != null) {
			  mav.addObject("error", "Invalid username or password!");
		}

		mav.setViewName("login");

		return mav;
	}
	

}
