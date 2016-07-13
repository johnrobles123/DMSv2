package com.silverlake.dms.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.silverlake.dms.model.DeviceListBean;
import com.silverlake.dms.model.ReservationBean;
import com.silverlake.dms.model.User;
import com.silverlake.dms.service.DeviceListService;
import com.silverlake.dms.service.ReservationService;
import com.silverlake.dms.service.UserService;

@Controller
@RequestMapping("/")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private DeviceListService deviceListService;
	
	@Autowired
	private UserService userService;
	
	private final List<String> tfList = new ArrayList<String>();
	private final List<String> ttList = new ArrayList<String>();
	
	public ReservationController()
	{
		tfList.add("08:00 AM");tfList.add("08:30 AM");tfList.add("09:00 AM");tfList.add("09:30 AM");
		tfList.add("10:00 AM");tfList.add("10:30 AM");tfList.add("11:00 AM");tfList.add("11:30 AM");
		tfList.add("12:00 PM");tfList.add("12:30 PM");tfList.add("01:00 PM");tfList.add("01:30 PM");
		tfList.add("02:00 PM");tfList.add("02:30 PM");tfList.add("03:00 PM");tfList.add("03:30 PM");
		tfList.add("04:00 PM");tfList.add("04:30 PM");tfList.add("05:00 PM");tfList.add("05:30 PM");
		tfList.add("06:00 PM");tfList.add("06:30 PM");tfList.add("07:00 PM");tfList.add("07:30 PM");
		tfList.add("08:00 PM");tfList.add("08:30 PM");
		ttList.add("08:29 AM");ttList.add("08:59 AM");ttList.add("09:29 AM");ttList.add("09:59 AM");
		ttList.add("10:29 AM");ttList.add("10:59 AM");ttList.add("11:29 AM");ttList.add("11:59 AM");
		ttList.add("12:29 PM");ttList.add("12:59 PM");ttList.add("01:29 PM");ttList.add("01:59 PM");
		ttList.add("02:29 PM");ttList.add("02:59 PM");ttList.add("03:29 PM");ttList.add("03:59 PM");
		ttList.add("04:29 PM");ttList.add("04:59 PM");ttList.add("05:29 PM");ttList.add("05:59 PM");
		ttList.add("06:29 PM");ttList.add("06:59 PM");ttList.add("07:29 PM");ttList.add("07:59 PM");
		ttList.add("08:29 PM");ttList.add("08:59 PM");
	}
	
	private List<DeviceListBean> getDeviceList()
	{
		List<DeviceListBean> dList = new ArrayList<DeviceListBean>();
		
		
		try {
			dList = deviceListService.findAll();
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dList;
	}
	
	@RequestMapping(value="/reserve",method=RequestMethod.POST)	
	public String createUpdateReservation(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("reservation")  ReservationBean reservation) throws SQLException {
		String ret = "reserve";
		User user = new User();
		DeviceListBean dl = new DeviceListBean();
		List<DeviceListBean> dList = getDeviceList();
		
		try
		{
			boolean isValidRange = reservationService.isValidRange(reservation, model);
			if (!isValidRange)
			{
				model.addAttribute("deviceList", dList);
				model.addAttribute("fromList", tfList);
				model.addAttribute("toList", ttList);
				model.addAttribute("reservation", reservation);
				return "reserve";
			}
			
			if (!reservationService.isOverlap(reservation, model))
			{
				model.addAttribute("deviceList", dList);
				model.addAttribute("reservation", reservation);
				model.addAttribute("fromList", tfList);
				model.addAttribute("toList", ttList);
				return "reserve";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		reservation.setUserName(getPrincipal());
		user = userService.findBySso(reservation.getUserName());
		dl = deviceListService.findById(reservation.getDevice().getSerialNo());
		
		System.out.println(reservation.getSeqNo()+"seq no");
		if (reservation.getSeqNo() == 0) {
			reservationService.create(reservation, user, dl);
		}
		else {
			System.out.println(reservation.getReserveDate()+"reserve");
			System.out.println(reservation.getDevice().getSerialNo() + " device");

			reservationService.updateReservation(reservation, user, dl);
		}
		
		return "redirect:/dashboard";
	}
	
	
	@RequestMapping(value="/reserve",method=RequestMethod.GET)
	public String display(HttpServletRequest request, HttpServletResponse response, Model model, ReservationBean reservation)
	{	ReservationBean reserve = new ReservationBean();
		List<DeviceListBean> dList = getDeviceList();
		
		//model.addObject("reservation", reservation);
		model.addAttribute("deviceList", dList);
		model.addAttribute("reservation", reserve);
		model.addAttribute("fromList", tfList);
		model.addAttribute("toList", ttList);
		return "reserve";
	}
	
	@RequestMapping(value = "/reserve/{seqNo}/update", method = RequestMethod.GET)
	public String showUpdateReservationForm(@PathVariable("seqNo") int seqNo, Model model) {

		//logger.debug("showUpdateUserForm() : {}", id);
		List<DeviceListBean> dList = getDeviceList();
		
		ReservationBean reserve = reservationService.getReservation(seqNo);
		
		model.addAttribute("deviceList", dList);
		model.addAttribute("reservation", reserve);
		model.addAttribute("fromList", tfList);
		model.addAttribute("toList", ttList);
		
		return "reserve";

	}
	
	@RequestMapping(value = "/viewreserve/{seqNo}", method = RequestMethod.GET)
	public String showViewReservationForm(@PathVariable("seqNo") int seqNo, Model model) {

		//logger.debug("showUpdateUserForm() : {}", id);
		List<DeviceListBean> dList = getDeviceList();
		ReservationBean reserve = reservationService.getReservation(seqNo);
		
		model.addAttribute("deviceList", dList);
		model.addAttribute("reservation", reserve);
		model.addAttribute("fromList", tfList);
		model.addAttribute("toList", ttList);
		
		return "viewreserve";

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
