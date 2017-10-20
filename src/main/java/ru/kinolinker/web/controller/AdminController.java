package ru.kinolinker.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.kinolinker.web.security.model.User;
import ru.kinolinker.web.security.service.SecurityService;
import ru.kinolinker.web.security.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(Model model, String error, String logout) {

		if (error != null) {
			model.addAttribute("error", "Username or password is incorrect.");
		}

		if (logout != null) {
			model.addAttribute("message", "Logged out successfully.");
		}

		return "admin/login";

	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String getMoviePage(Model model, String error, String logout) {

		return "redirect:/admin/movies";

	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "admin/registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm);

		securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

		return "redirect:/login";
	}
	
	@RequestMapping(value = "admin/settings", method = RequestMethod.GET)
	public String getSettingAdminPage(Model model) {

		return "admin/admin_settings";

	}
	
	@RequestMapping(value = "admin/change_password", method = RequestMethod.POST)
	public ResponseEntity<String> changeAdminPassword(Model model,HttpServletRequest request) {
		userService.updatePassword(request.getParameter("password"));
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

	}
	
	

}
