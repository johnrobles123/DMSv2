package com.silverlake.dms.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.silverlake.dms.model.DeviceListBean;
import com.silverlake.dms.model.ReservationBean;
import com.silverlake.dms.util.Util;

@Repository("reservationDao")
public class ReservationDaoImpl extends AbstractDao<Integer, ReservationBean> implements ReservationDao {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	DeviceListDao deviceListDao;

	
	public DataSource getDataSource()
	{
			return this.dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{
			this.dataSource = dataSource;
	}
	
	@Override
	public boolean isOverlap(ReservationBean reservation, Model model) throws SQLException
	{
		String query;
		PreparedStatement pstmt;
		
		if (reservation.getRepeating()== "" || reservation.getRepeating().equals("Weekly") )
		{
			query = "Select username, reserve_date, time_from, time_to from device_journal where device_serial_no = ? and reserve_date = ? and (time_from between ? and ? or time_to between ? and ?) and seq_no <> ?";
			Date startDate = reservation.getDReserveDate();
			Date endDate = reservation.getDReserveDate();
			
			if (!(reservation.getRepeating()== ""))
			{
				reservation.getDRepeatTo();
			}
			
			
			System.out.println(startDate+"date after");

			while (!startDate.after(endDate))
			{	pstmt = dataSource.getConnection().prepareStatement(query);
				
				pstmt.setString(1, reservation.getDevice().getSerialNo());
				pstmt.setDate(2, new java.sql.Date(startDate.getTime()));
				pstmt.setTime(3, reservation.getTTimeFrom());
				pstmt.setTime(4, reservation.getTTimeTo());
				pstmt.setTime(5, reservation.getTTimeFrom());
				pstmt.setTime(6, reservation.getTTimeTo());
				pstmt.setInt(7, reservation.getSeqNo());
				pstmt.execute();
				
				startDate = Util.getNextDate(reservation.getRepeating() , startDate);
				System.out.println(startDate+"date after");
				ResultSet resultSet = pstmt.executeQuery();
				if (resultSet.next())
				{
					System.out.println("duplicate");
					model.addAttribute("message", "A reservation has already been booked for this period");
					return false;
				
				}

			}
		}
		else
		{
			query = "Select username, reserve_date, time_from, time_to from DEVICE_JOURNAL where device_serial_no = ? and a.status IS NULL and reserve_date between ? and ? and (time_from between ? and ? or time_to between ? and ? and seq_no <> ?)";
			pstmt = dataSource.getConnection().prepareStatement(query);
			pstmt.setString(1, reservation.getDevice().getSerialNo());
			pstmt.setDate(2, new java.sql.Date(reservation.getDReserveDate() .getTime()));
			
			pstmt.setDate(3, new java.sql.Date(reservation.getDRepeatTo() .getTime()));
			if (reservation.getRepeating().equals("Daily"))
			{
				pstmt.setTime(4, reservation.getTTimeFrom());
				pstmt.setTime(5, reservation.getTTimeTo());
				pstmt.setTime(6, reservation.getTTimeFrom());
				pstmt.setTime(7, reservation.getTTimeTo());
				pstmt.setInt(8, reservation.getSeqNo());
			}
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next())
			{	System.out.println("duplicate");
				model.addAttribute("message", resultSet.getString(1) + "already has a reservation for this period");
				return false;
			}

		}
			
		return true;
	}

	public boolean isValidRange(ReservationBean reservation, Model model) {
			boolean ret = true;
			//check if time range is valid
			System.out.println(reservation.getTTimeFrom()+"isvalid timefrom");
			System.out.println(reservation.getTTimeTo()+"isvalid timeto");
			if (//reservation.getReserveDate().isEmpty ||
				reservation.getTimeFrom().isEmpty() ||
				reservation.getTimeTo().isEmpty()||
				reservation.getDevice() == null)
			{
				ret = false;
			}
			
			if (reservation.getDReserveDate().getDay() > 5)
			{
				ret = false;
			}
			if (reservation.getTTimeFrom().after(reservation.getTTimeTo()))
			{
				System.out.println("Start time later then end time");
				model.addAttribute("message", "Invalid time");
				ret = false;
			}
			
			//check if repeating date is valid
			if (!reservation.getRepeating().isEmpty())
			{	
				if(reservation.getRepeating() != "" )
						{
				if (reservation.getDReserveDate().compareTo(reservation.getDRepeatTo()) >=0)
				{
					System.out.println("End date is earlier than Start date");
					model.addAttribute("message", "End date is earlier than Start date");
					ret = false;
				}}
			}		
			return ret;
	}
	@Override
	public void create(ReservationBean reservation) throws SQLException {
		Date startDate = reservation.getDReserveDate();
		Date endDate = reservation.getDReserveDate();
		
		Session session = getSession();
		
		if (!(reservation.getRepeating()==""))
		{
			endDate = reservation.getDRepeatTo();
		}
		
		System.out.println(startDate+"start date");

		while (!startDate.after(endDate))
		{	
			ReservationBean newReserve = new ReservationBean();
			
			newReserve.setDevice(reservation.getDevice());
			newReserve.setUserName(reservation.getUserName());
			newReserve.setReserveDate(startDate);
			newReserve.setLocation(reservation.getLocation());
			newReserve.setAddInfo(reservation.getAddInfo());
			newReserve.setTimeFrom(reservation.getTTimeFrom());
			newReserve.setTimeTo(reservation.getTTimeTo());
			
			session.persist(newReserve);
			startDate = Util.getNextDate(reservation.getRepeating() , startDate);
			System.out.println(startDate+"date after");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservationBean> selectAll() throws SQLException {
		List<ReservationBean> rbList = new ArrayList<ReservationBean>();
		
		Session session = getSession();
		// AND TIMESTAMP(a.reserve_date, a.time_to) >= CURRENT_TIMESTAMP() 
		Query query = session.createSQLQuery("SELECT a.* FROM device_journal a WHERE a.status IS NULL ORDER BY TIMESTAMP(a.reserve_date, a.time_to)")
			.addEntity(ReservationBean.class);
		
		rbList = query.list();
		
		for (ReservationBean rb : rbList) {
			DeviceListBean device = new DeviceListBean();
			device = deviceListDao.findBySerialNo(rb.getDevice().getSerialNo());
			rb.setDeviceName(device.getDeviceName());
			rb.setReserveDate(rb.getDReserveDate());
			rb.setTimeTo(rb.getTTimeTo());
			rb.setTimeFrom(rb.getTTimeFrom());
		}
		
		return rbList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ReservationBean> selectAllByDeviceSerialNo(String deviceSerialNo) {
		
		List<ReservationBean> reserveList = new ArrayList<ReservationBean>();
		
		StringBuilder stmt = new StringBuilder(); 
		//stmt.append("SELECT a.seq_no, a.device_serial_no, b.device_name, a.username, a.reserve_date, a.time_from, a.time_to, a.location, a.add_info FROM device_journal a, device_list b WHERE a.device_serial_no = b.serial_no ");
		stmt.append("SELECT {a.*}, {b.*} FROM device_journal a, device_list b WHERE a.device_serial_no = b.serial_no and a.status IS NULL ");
		if (!deviceSerialNo.equalsIgnoreCase("*")) {
			stmt.append("AND a.device_serial_no = :deviceSerialNo order by b.device_name, reserve_date");
		}		;
		//stmt.append("AND TIMESTAMP(a.reserve_date, a.time_to) >= CURRENT_TIMESTAMP()");
		
		Session session = getSession();
		Query query = session.createSQLQuery(stmt.toString())
				.addEntity("a", ReservationBean.class)
				.addJoin("b", "a.device")
				.setParameter("deviceSerialNo", deviceSerialNo);
		
		List<Object[]> rows = query.list();
		
		for (Object[] row : rows) {
			ReservationBean rb = new ReservationBean();
			DeviceListBean d = new DeviceListBean();
			
			rb = (ReservationBean) row[0];
			
			rb.setReserveDate(rb.getDReserveDate());
			rb.setTimeTo(rb.getTTimeTo());
			rb.setTimeFrom(rb.getTTimeFrom());
			
			d = (DeviceListBean) row[1];
			
			rb.setDeviceName(d.getDeviceName());
			reserveList.add(rb);
		}
		
		return reserveList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ReservationBean> selectAllByUserName(String userName) {
		
		List<ReservationBean> reserveList = new ArrayList<ReservationBean>();
		
		StringBuilder stmt = new StringBuilder(); 
		//stmt.append("SELECT a.seq_no, a.device_serial_no, b.device_name, a.username, a.reserve_date, a.time_from, a.time_to, a.location, a.add_info FROM device_journal a, device_list b WHERE a.device_serial_no = b.serial_no ");
		stmt.append("SELECT {a.*}, {b.*} FROM device_journal a, device_list b WHERE a.device_serial_no = b.serial_no and a.status IS NULL ")
			.append("AND a.username = :userName order by b.device_name, reserve_date");
		    //.append("AND TIMESTAMP(a.reserve_date, a.time_to) >= CURRENT_TIMESTAMP()");
		
		Session session = getSession();
		Query query = session.createSQLQuery(stmt.toString())
				.addEntity("a", ReservationBean.class)
				.addJoin("b", "a.device")
				.setParameter("userName", userName);
		
		List<Object[]> rows = query.list();
		
		for (Object[] row : rows) {
			ReservationBean rb = new ReservationBean();
			DeviceListBean d = new DeviceListBean();
			
			rb = (ReservationBean) row[0];
			
			rb.setReserveDate(rb.getDReserveDate());
			rb.setTimeTo(rb.getTTimeTo());
			rb.setTimeFrom(rb.getTTimeFrom());
			
			d = (DeviceListBean) row[1];
			
			rb.setDeviceName(d.getDeviceName());
			reserveList.add(rb);
		}
		
		return reserveList;
	}

	@Override
	public ReservationBean getReservation(int seqNo) 
	{
		ReservationBean rb = new ReservationBean();
		rb = getByKey(seqNo);
		
		DeviceListBean device = new DeviceListBean();
		
		try {
			device = deviceListDao.findBySerialNo(rb.getDevice().getSerialNo());
			rb.setDeviceName(device.getDeviceName());
			rb.setReserveDate(rb.getDReserveDate());
			rb.setTimeTo(rb.getTTimeTo());
			rb.setTimeFrom(rb.getTTimeFrom());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		rb.setDeviceName(device.getDeviceName());
		
		return rb;
	}
	
	@Override
	public void updateReservation(ReservationBean reservation) throws SQLException
	{
		Session session = getSession();
		
		session.saveOrUpdate(reservation);

	}

	@Override
	public List<ReservationBean> getCurrentDayRecords(String deviceSerialNo) throws SQLException {
		List<ReservationBean> reserveList = new ArrayList<ReservationBean>();
		String dbProductName = dataSource.getConnection().getMetaData().getDatabaseProductName();
		
		PreparedStatement pstmt = null;
		
		try {
			if (dbProductName.toLowerCase().contains("mysql")) {
				pstmt = dataSource.getConnection().prepareStatement("SELECT a.seq_no, a.device_serial_no, b.device_name, a.username, a.reserve_date, a.time_from, a.time_to, a.location, a.add_info, a.status FROM device_journal a, device_list b WHERE a.device_serial_no = b.serial_no AND a.device_serial_no = ? AND a.reserve_date = CURDATE() AND TIMESTAMP(a.reserve_date, a.time_to) >= CURRENT_TIMESTAMP() ORDER BY TIMESTAMP(a.reserve_date, a.time_to)");
			} else if (dbProductName.toLowerCase().contains("oracle")) {
				pstmt = dataSource.getConnection().prepareStatement("SELECT a.seq_no, a.device_serial_no, b.device_name, a.username, a.reserve_date, a.time_from, a.time_to, a.location, a.add_info, a.status FROM device_journal a, device_list b WHERE a.device_serial_no = b.serial_no AND a.device_serial_no = ? AND a.reserve_date = trunc(sysdate) ORDER BY time_from");
			}		 
			
			pstmt.setString(1, deviceSerialNo);
			
			try {
				ResultSet rbSet = pstmt.executeQuery();
				
				while (rbSet.next()) {
					ReservationBean rb = new ReservationBean();
					rb.setSeqNo(rbSet.getInt(1));
					//rb.setDevice(device);
					rb.setDeviceName(rbSet.getString(3));
					rb.setUserName(rbSet.getString(4));
					rb.setReserveDate(rbSet.getDate(5));
					rb.setTimeFrom(rbSet.getTime(6));
					rb.setTimeTo(rbSet.getTime(7));
					rb.setLocation(rbSet.getString(8));
					rb.setAddInfo(rbSet.getString(9));
					rb.setStatus(rbSet.getString(10));
					reserveList.add(rb);
				}
	
			} finally {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reserveList;
	}

	@Override
	public void updateReservation(int seqNo, String status) throws SQLException {
		
		Session session = getSession();
		Query query = session.createSQLQuery("update DEVICE_JOURNAL set status = :status where seq_no = :seqNo");
		query.setParameter("status", status);
		query.setParameter("seqNo", seqNo);
		
		@SuppressWarnings("unused")
		int result = query.executeUpdate();
	}	
	
	@Override
	public void updateReservationNonWorking(String deviceSerialNo, String status) throws SQLException {
		
		Session session = getSession();
		Query query = session.createSQLQuery("update DEVICE_JOURNAL set status = :status where device_serial_no = :deviceSerialNo");
		query.setParameter("status", status);
		query.setParameter("deviceSerialNo", deviceSerialNo);
		
		@SuppressWarnings("unused")
		int result = query.executeUpdate();
	}	
}
