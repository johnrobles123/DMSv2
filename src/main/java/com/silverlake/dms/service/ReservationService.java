package com.silverlake.dms.service;

import java.sql.SQLException;
import java.util.List;

import com.silverlake.dms.model.DeviceListBean;
import com.silverlake.dms.model.ReservationBean;
import com.silverlake.dms.model.User;
import org.springframework.ui.Model;

public interface  ReservationService {
	public boolean isValidRange(ReservationBean reservation, Model model) throws SQLException;
	
	public boolean isOverlap(ReservationBean reservation, Model model) throws SQLException;
	
	public void create(ReservationBean reservation, User user, DeviceListBean dl) throws SQLException;
	
	public List<ReservationBean> selectAll() throws SQLException;
	
	public ReservationBean getReservation(int seqNo);
	
	public void updateReservation(ReservationBean reservation, User user, DeviceListBean dl) throws SQLException;
	
	public void updateReservation(int seqNo, String status) throws SQLException;
	
	public boolean isDeviceAvailable(String deviceSerialNo) throws SQLException;
	
	public ReservationBean getNextStartTime(String deviceSerialNo) throws SQLException;
	
	public String getAvailabilityStartTime(String deviceSerialNo) throws SQLException;
	
	public List<ReservationBean> getCurrentDayRecords(String deviceSerialNo) throws SQLException;
	
	public List<ReservationBean> selectAllByDeviceSerialNo(String deviceSerialNo);
	
	public List<ReservationBean> selectAllByUserName(String userName);
}
