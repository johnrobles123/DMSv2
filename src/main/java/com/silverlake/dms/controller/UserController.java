package com.silverlake.dms.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.silverlake.dms.dao.UserDaoImpl;
import com.silverlake.dms.model.DeviceListBean;
import com.silverlake.dms.model.ReservationBean;
import com.silverlake.dms.model.User;
import com.silverlake.dms.service.DeviceListService;
import com.silverlake.dms.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	private DeviceListService deviceListService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String displayLogin(HttpServletRequest request, HttpServletResponse response, User user) {
		ModelAndView model = new ModelAndView("user");
		User userBean = new User();
		model.addObject("user", userBean);
		return "user";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String addUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")  User user, Model model) throws SQLException {
		String ret = null;
		try {
			//request.setAttribute("addedUser", user.getId());
			ret = "admin";
		
			if (user.getId() == 0 )	{	
				userService.save(user);
			} else {
				userService.update(user);
			}
			
			populateDefaults(model);
			model.addAttribute("message", "SUCCESS: Record saved/updated");
			model.addAttribute("user", getPrincipal());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
		
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ModelAndView queryUser(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int ssoId) {
		ModelAndView model = new ModelAndView("user");
		User user = userService.findById(ssoId);
		
		model.addObject("user", user);
		return model;
	}

	@RequestMapping(value = "/viewuser/{id}", method = RequestMethod.GET)
	public ModelAndView queryviewUser(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int ssoId) {
		ModelAndView model = new ModelAndView("viewuser");
		User user = userService.findById(ssoId);
		
		model.addObject("user", user);
		return model;
	}


	// delete
	@RequestMapping(value = "/user/{id}/delete", method = RequestMethod.POST)
	public String deleteUser(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id, Model model) throws SQLException {

		userService.delete(id);		
		return "redirect:/admin";

	}
	
	private void populateDefaults(Model model) {
		List<DeviceListBean> dList = new ArrayList<DeviceListBean>();
		List<User> userList = new ArrayList<User>();
		
		try {
			dList = deviceListService.findAll();
			userList = userService.selectAll();
					    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("deviceList", dList);
		model.addAttribute("userList", userList);
	}
	
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}
