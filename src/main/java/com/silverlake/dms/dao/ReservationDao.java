package com.silverlake.dms.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.ui.Model;

import com.silverlake.dms.model.ReservationBean;

public interface ReservationDao {
	public boolean isValidRange(ReservationBean reservation, Model model) throws SQLException;
	
	public boolean isOverlap(ReservationBean reservation, Model model) throws SQLException;
	
	public void create(ReservationBean reservation) throws SQLException;
	
	public List<ReservationBean> selectAll() throws SQLException;
	
	public List<ReservationBean> selectAllByDeviceSerialNo(String deviceSerialNo);
	
	public List<ReservationBean> selectAllByUserName(String userName);
	
	public ReservationBean getReservation(int seqNo);
	
	public void updateReservation(ReservationBean reservation) throws SQLException;
	
	public void updateReservation(int seqNo, String status ) throws SQLException;
	
	public List<ReservationBean> getCurrentDayRecords(String deviceSerialNo) throws SQLException;

	public void updateReservationNonWorking(String deviceSerialNo, String status) throws SQLException;
}
