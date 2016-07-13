package com.silverlake.dms.controller;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.silverlake.dms.model.User;
import com.silverlake.dms.model.UserProfile;
import com.silverlake.dms.model.UserProfileType;
import com.silverlake.dms.service.UserService;
import com.silverlake.dms.validators.RegistrationValidator;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RegistrationValidator registrationValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(registrationValidator);
	}
	
	@RequestMapping(value = { "/registration" }, method = RequestMethod.GET)
	public String doRegister(ModelMap model, User user) {
		User userReg = new User();
		model.addAttribute("user", userReg);
		return "registration";
	}
	
	
	@RequestMapping(value = { "/registration" }, method = RequestMethod.POST)
	public String postRegister(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")  @Validated User user, BindingResult result) {	
		if (result.hasErrors()) {
			return "registration";
		} else {
		
			UserProfile profile = new UserProfile();
			profile.setId(1);
			profile.setType(UserProfileType.USER.getUserProfileType());
			
			Set<UserProfile> profiles = user.getUserProfiles();
			profiles.add(profile);
			
			user.setUserProfiles(profiles);
			
			try {
				userService.save(user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/login";
	}
}
