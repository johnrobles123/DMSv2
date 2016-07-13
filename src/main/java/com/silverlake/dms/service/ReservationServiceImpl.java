package com.silverlake.dms.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silverlake.dms.dao.ReservationDao;
import com.silverlake.dms.email.MailSSL;
import com.silverlake.dms.model.DeviceListBean;
import com.silverlake.dms.model.ReservationBean;
import com.silverlake.dms.model.User;
import org.springframework.ui.Model;


@Service("reservationService")
@Transactional
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationDao reservationDao;
	
	public ReservationDao getReservationDao() {
		return reservationDao;
	}

	public void setReservationDao(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	@Override
	public boolean isValidRange(ReservationBean reservation, Model model)throws SQLException {
		return reservationDao.isValidRange(reservation, model);
	}

	@Override
	public boolean isOverlap(ReservationBean reservation, Model model)
			throws SQLException {
		return reservationDao.isOverlap(reservation, model);
	}

	@Override
	public void create(ReservationBean reservation, User user, DeviceListBean dl) throws SQLException {
		reservationDao.create(reservation);
		MailSSL ms = new MailSSL();
		ms.sendMail(reservation, user, dl);
	}

	@Override
	public List<ReservationBean> selectAll() throws SQLException {
		return reservationDao.selectAll();
	}

	@Override
	public ReservationBean getReservation(int seqNo) {
		return reservationDao.getReservation(seqNo);
	}

	@Override
	public void updateReservation(ReservationBean reservation, User user, DeviceListBean dl) throws SQLException {
		reservationDao.updateReservation(reservation);
		MailSSL ms = new MailSSL();
		ms.sendMail(reservation, user, dl);
	}
	
	@Override
	public boolean isDeviceAvailable(String deviceSerialNo) throws SQLException {
		boolean result = true;
		List<ReservationBean> rb = new ArrayList<ReservationBean>();
		
		rb = reservationDao.getCurrentDayRecords(deviceSerialNo);
		
		if (rb.size() > 0) {
			/*
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		    java.sql.Time sqlTime = new java.sql.Time(sqlDate.getTime());
			*/
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(rb.get(0).getTTimeFrom());
			long cal1Seconds = (cal1.get(Calendar.SECOND) +
			                    cal1.get(Calendar.MINUTE) * 60 +
			                    cal1.get(Calendar.HOUR) * 3600);
			
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(rb.get(0).getTTimeTo());
			long cal2Seconds = (cal2.get(Calendar.SECOND) +
			                    cal2.get(Calendar.MINUTE) * 60 +
			                    cal2.get(Calendar.HOUR) * 3600);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(cal.getTime());
			long calSeconds = (cal.get(Calendar.SECOND) +
		                       cal.get(Calendar.MINUTE) * 60 +
		                       cal.get(Calendar.HOUR) * 3600);
			
			System.out.println("[" + calSeconds + "] [" + cal1Seconds + "] [" + cal2Seconds + "]");
			//System.out.println("[" + cal.getTime().toString() + "] [" + cal1.getTime().toString() + "] [" + cal2.getTime().toString() + "]");
			if ((calSeconds - cal1Seconds >= 0) && (calSeconds - cal2Seconds <= 0)) {
				result = false;
			}
			// if there are no records, then it means the device is available
		} else { 
			result = true;
		}
		return result;
	}

	@Override
	public ReservationBean getNextStartTime(String deviceSerialNo) throws SQLException {
		List<ReservationBean> rbList = new ArrayList<ReservationBean>();
		ReservationBean rb = new ReservationBean();
		
		rbList = reservationDao.getCurrentDayRecords(deviceSerialNo);
		Collections.sort(rbList, new ReservationBean.OrderByTimeFrom());
		
		// get first record
		if (rbList.size() > 0) {
			rb = rbList.get(0);
		}
		
		return rb;
	}

	@Override
	public String getAvailabilityStartTime(String deviceSerialNo) throws SQLException {
		String retstr = null;
		ReservationBean rb = new ReservationBean();
		
		if (!deviceSerialNo.equalsIgnoreCase("*")) {
			if (isDeviceAvailable(deviceSerialNo)) {
				rb = getNextStartTime(deviceSerialNo);
				if (rb == null || rb.getSeqNo() == 0) {
					retstr = "Devices are available from this point onwards";
				} else {
					retstr = "Device " + rb.getDeviceName() + " is available until " + rb.getTimeFrom().toString();
				}
			} else {
				retstr = "Device is NOT available";
			}
		} else {
			retstr = null;
		}
		
		return retstr;
	}
	
	@Override
	public List<ReservationBean> getCurrentDayRecords(String deviceSerialNo) throws SQLException {		
		return reservationDao.getCurrentDayRecords(deviceSerialNo);
	}
	
	public List<ReservationBean> selectAllByDeviceSerialNo(String deviceSerialNo) {
		return reservationDao.selectAllByDeviceSerialNo(deviceSerialNo);
	}

	public List<ReservationBean> selectAllByUserName(String userName) {
		return reservationDao.selectAllByUserName(userName);
	}
	
	@Override
	public void updateReservation(int seqNo, String status) throws SQLException {
		reservationDao.updateReservation(seqNo, status);		
	}
}
