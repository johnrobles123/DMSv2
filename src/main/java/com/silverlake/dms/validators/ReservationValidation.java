package com.silverlake.dms.validators;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.sql.DataSource;

import com.silverlake.dms.model.*;
import com.silverlake.dms.dao.*;
import com.silverlake.dms.util.*;

public class ReservationValidation extends AbstractDao {
	@Autowired
	private static DataSource dataSource;
	@Autowired
	private static SessionFactory sessionFactory;
	
	public static boolean isOverlap(ReservationBean reservation, Model model) throws SQLException
	{
		String query;
		PreparedStatement pstmt;
		
		if (reservation.getRepeating()== "" || reservation.getRepeating().equals("Weekly") )
		{
			query = "Select count(1) from DEVICE_JOURNAL where device_serial_no = ? and reserve_date = ? and (time_from between ? and ? or time_to between ? and ?)";
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
				{	System.out.println("duplicate");
				model.addAttribute("message", "Overlap");
						return !(resultSet.getInt(1) > 0);
				}

			}
		}
		else
		{
			query = "Select count(1) from DEVICE_JOURNAL where device_serial_no = ? and reserve_date between ? and ? and (time_from between ? and ? or time_to between ? and ? and seq_no <> ?)";
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
			model.addAttribute("message", "Overlap");
					return !(resultSet.getInt(1) > 0);
			}

		}
			
		return true;
	}

	public static boolean isValidRange(ReservationBean reservation, Model model) {
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
				System.out.println("Invalid time");
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
}
