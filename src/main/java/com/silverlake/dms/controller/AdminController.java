package com.silverlake.dms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.silverlake.dms.model.DeviceListBean;
import com.silverlake.dms.model.User;
import com.silverlake.dms.service.DeviceListService;
import com.silverlake.dms.service.UserService;


@Controller
public class AdminController {

	@Autowired
	private DeviceListService deviceListService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/admin",method=RequestMethod.GET)
	public String displayAdmin(Model model)
	{
		List<DeviceListBean> deviceList = new ArrayList<DeviceListBean>();
		List<User> userList = new ArrayList<User>();
		
		try {
			deviceList = deviceListService.findAll();
			userList = userService.selectAll();
					    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("deviceList", deviceList);
		model.addAttribute("userList", userList);
		model.addAttribute("user", getPrincipal());
		
		return "admin";
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
