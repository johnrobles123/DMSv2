package com.silverlake.dms.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.silverlake.dms.model.DeviceListBean;
import com.silverlake.dms.model.ReservationBean;
import com.silverlake.dms.model.User;
import com.silverlake.dms.model.UserProfile;
import com.silverlake.dms.model.UserProfileType;
import com.silverlake.dms.service.DeviceListService;
import com.silverlake.dms.service.ReservationService;
import com.silverlake.dms.service.UserService;
import com.silverlake.dms.validators.RegistrationValidator;

@Controller
public class MainController {

	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private DeviceListService deviceListService;

	@Autowired
	private UserService userService;
	
	//@Autowired
	//private RegistrationValidator registrationValidator;
	
	//@InitBinder
	//protected void initBinder(WebDataBinder binder) {
	//	binder.setValidator(registrationValidator);
	//}
	
	private static final String RETURNED = "R";
	private static final String CANCEL = "C";

//	@RequestMapping(value = { "/registration" }, method = RequestMethod.GET)
//	public String doRegister(ModelMap model, User user) {
//		User userReg = new User();
//		model.addAttribute("user", userReg);
//		return "registration";
//	}
//	
//	
//	//public String postRegister(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")  @Validated User user, BindingResult result) {
//	@RequestMapping(value = { "/registration" }, method = RequestMethod.POST)
//	public String postRegister(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")  User user) {
//		
//		//if (result.hasErrors()) {
//		//	return "registration";
//		//} else {
//		
//			UserProfile profile = new UserProfile();
//			profile.setId(1);
//			profile.setType(UserProfileType.USER.getUserProfileType());
//			
//			Set<UserProfile> profiles = user.getUserProfiles();
//			profiles.add(profile);
//			
//			user.setUserProfiles(profiles);
//			
//			try {
//				userService.save(user);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		//}
//		
//		return "redirect:/login";
//	}
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "redirect:/login";
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String adminPage(ModelMap model) {
		List<ReservationBean> reserveList = new ArrayList<ReservationBean>();
		List<DeviceListBean> dList = new ArrayList<DeviceListBean>();
		User userObj = new User();
		String role = UserProfileType.USER.getUserProfileType();
		
		String deviceStatusStr = null;
		
		try {
			//djList = deviceDelegate.selectAllData();
			deviceStatusStr = reservationService.getAvailabilityStartTime("12345");
			reserveList = reservationService.selectAll();
			dList = deviceListService.findAll();		    
			
			userObj = userService.findBySso(getPrincipal());
			
			for (UserProfile u : userObj.getUserProfiles()) {
				if (u.getType().equalsIgnoreCase(UserProfileType.ADMIN.getUserProfileType().toString())) {
					role = u.getType();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//model.addObject("deviceJournal", djList);
		model.addAttribute("deviceJournal", reserveList);
		model.addAttribute("deviceList", dList);
		model.addAttribute("deviceStatus", deviceStatusStr);
		model.addAttribute("user", getPrincipal());
		model.addAttribute("role", role);
		return "dashboard";
	}
	
	@RequestMapping(value="/dashboard/{serialNo}/refresh",method=RequestMethod.GET)
	public String refreshDashboard(@PathVariable("serialNo") String serialNo, ModelMap model) {
		List<ReservationBean> reserveList = new ArrayList<ReservationBean>();
		//List<ReservationBean> djList = new ArrayList<ReservationBean>();
		List<DeviceListBean> dList = new ArrayList<DeviceListBean>();
		String deviceStatusStr = null;
		User userObj = new User();
		String role = UserProfileType.USER.getUserProfileType();
		
		try {
			//reserveList = reservationDelegate.getCurrentDayRecords(serialNo);
			deviceStatusStr = reservationService.getAvailabilityStartTime(serialNo);
			if (serialNo.equalsIgnoreCase("*")) {
				reserveList = reservationService.selectAll();
			} else {
				reserveList = reservationService.selectAllByDeviceSerialNo(serialNo);
			}
			
			dList = deviceListService.findAll();
			
			userObj = userService.findBySso(getPrincipal());
			
			for (UserProfile u : userObj.getUserProfiles()) {
				if (u.getType().equalsIgnoreCase(UserProfileType.ADMIN.getUserProfileType().toString())) {
					role = u.getType();
					break;
				}
			}
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//model.addObject("deviceJournal", djList);
		model.addAttribute("deviceJournal", reserveList);
		model.addAttribute("deviceList", dList);
		model.addAttribute("deviceStatus", deviceStatusStr);
		model.addAttribute("selectedDevice", serialNo);
		model.addAttribute("user", getPrincipal());
		model.addAttribute("role", role);
		
		return "dashboard";
	}
	
	@RequestMapping(value="/dashboard/{user}/myreservation",method=RequestMethod.GET)
	public String myReservation(@PathVariable("user") String user, ModelMap model) {
		List<ReservationBean> reserveList = new ArrayList<ReservationBean>();
		//List<ReservationBean> djList = new ArrayList<ReservationBean>();
		List<DeviceListBean> dList = new ArrayList<DeviceListBean>();
		String deviceStatusStr = null;
		
		try {
			reserveList = reservationService.selectAllByUserName(user);			
			dList = deviceListService.findAll();
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//model.addObject("deviceJournal", djList);
		model.addAttribute("deviceJournal", reserveList);
		model.addAttribute("deviceList", dList);
		model.addAttribute("deviceStatus", deviceStatusStr);
		model.addAttribute("user", getPrincipal());

		return "dashboard";
	}
	
	@RequestMapping(value="/dashboard/{seqNo}/cancel",method=RequestMethod.POST)
	public String cancelDevice(@PathVariable("seqNo") int seqNo, ModelMap model, ReservationBean reserveBean) {
		String msg = null;
		
		try {
			reservationService.updateReservation(seqNo, CANCEL);
			msg = "SUCCESS: Record saved / updated";
		} catch (Exception e) {
			msg = "ERROR: " + e.toString();
			e.printStackTrace();
		}
		
		model.addAttribute("message", msg);
		return "dashboard";
	}
	
	@RequestMapping(value="/dashboard/{seqNo}/return",method=RequestMethod.POST)
	public String returnedDevice(@PathVariable("seqNo") int seqNo, ModelMap model, ReservationBean reserveBean) throws SQLException {
		String msg = null;
		
		try {
			reservationService.updateReservation(seqNo, RETURNED);
			msg = "SUCCESS: Record saved / updated";
		} catch (Exception e) {
			msg = "ERROR: " + e.toString();
			e.printStackTrace();
		}
		
		model.addAttribute("message", msg);
		return "dashboard";
	}

	@RequestMapping(value = "/db", method = RequestMethod.GET)
	public String dbaPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "dba";
	}

	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "accessDenied";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String aboutPage() {
		return "about";
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