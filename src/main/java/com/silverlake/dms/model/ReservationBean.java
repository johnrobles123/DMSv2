package com.silverlake.dms.model;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.silverlake.dms.util.Util;

@Entity
@Table(name="DEVICE_JOURNAL")
public class ReservationBean implements Comparable<ReservationBean> {
		
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SEQ_NO")
	private int seqNo;
	
	//, cascade = CascadeType.PERSIST
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="DEVICE_SERIAL_NO")
	private DeviceListBean device;

	@Column(name="username")
	private String userName;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="RESERVE_DATE")
	private Date dReserveDate;

	@Column(name="TIME_FROM")
	private Time tTimeFrom;
	
	@Column(name="TIME_TO")
	private Time tTimeTo;
	
	@Column(name="LOCATION")
	private String location;
	
	@Column(name="ADD_INFO")
	private String addInfo;
	
	@Column(name="STATUS")
	private String status;
	
	
	@Transient
	private String reserveDate;
	
	@Transient
	private String deviceName;

	@Transient
	private String timeFrom;
	
	@Transient
	private String timeTo;
	
	@Transient
	private String repeating;
	
	@Transient
	private String repeatTo;
	
	@Transient
	private boolean returned;	
	
	public ReservationBean() {
		this.setReturned(false);
		this.device = new DeviceListBean();
	}
	 
	public static class OrderByTimeFrom implements Comparator<ReservationBean> {

		public int compare(ReservationBean o1, ReservationBean o2) {
			DateFormat df = new SimpleDateFormat("hh:mm a");
			java.sql.Time timeFrom1 = null;
			java.sql.Time timeFrom2 = null;
			
			try {
				timeFrom1 = new java.sql.Time(df.parse(o1.timeFrom).getTime());
			    timeFrom2 = new java.sql.Time(df.parse(o2.timeFrom).getTime());
			
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (timeFrom1 != null && timeFrom2 != null) {
				if (timeFrom1.after(timeFrom2)) {
					return 1;
				}
				else if (timeFrom1.before(timeFrom2)) {
					return -1;
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		}
		
	}

 
	public String getTimeFrom() {
		return timeFrom;
	}
	
	public Time getTTimeFrom()
	{	return tTimeFrom;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
		this.tTimeFrom = Util.getTimeFormat(timeFrom);
	}
	public void setTimeFrom(Time timeFrom) {
		SimpleDateFormat format = new SimpleDateFormat("hh:mm a"); //if 24 hour format
		
		this.timeFrom = format.format(timeFrom);
		this.tTimeFrom = timeFrom;
	}
	
	
	public String getTimeTo() {
		return timeTo;
	}
	
	public Time getTTimeTo()
	{	
		return tTimeTo;
	}
	
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
		this.tTimeTo = Util.getTimeFormat(timeTo);
	}
	
	public void setTimeTo(Time timeFrom) {
		SimpleDateFormat format = new SimpleDateFormat("hh:mm a"); //if 24 hour format
		
		this.timeTo = format.format(timeFrom);
		this.tTimeTo = timeFrom;
	}
	
	public String getRepeating() {
		if (this.repeating == null)
			return "";
		return this.repeating;
	}
	public void setRepeating(String repeating) {
		this.repeating = repeating;
	}
	public Date getDRepeatTo(){
		if (this.repeatTo != null) {
			return Util.getDateFormat(repeatTo);
		} else {
			return null;
		}
	}
	public String getRepeatTo() {
		return repeatTo;
	}
	public void setRepeatTo(String repeatTo) {
		this.repeatTo = repeatTo;
	}
	
	
	
	public String getReserveDate() {
		return reserveDate;
	}
	public Date getDReserveDate(){
		return dReserveDate;
	}
	
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
		this.dReserveDate = Util.getDateFormat(reserveDate);
	}
	
	public void setReserveDate(Date reserveDate) {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
		this.reserveDate = DATE_FORMAT.format(reserveDate);
		this.dReserveDate = reserveDate;
	}
		
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getdeviceSerialNo(){
		return device.getSerialNo();
	}
	public DeviceListBean getDevice() {
		return device;
	}

	public void setDevice(DeviceListBean device) {
		this.device = device;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public int compareTo(ReservationBean o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + seqNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ReservationBean)) {
			return false;
		}
		ReservationBean other = (ReservationBean) obj;
		if (seqNo != other.seqNo) {
			return false;
		}
		return true;
	}
	
}
