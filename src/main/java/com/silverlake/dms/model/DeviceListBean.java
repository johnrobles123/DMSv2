package com.silverlake.dms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="DEVICE_LIST")
public class DeviceListBean {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="SERIAL_NO")
	private String serialNo;
	
	@Column(name="DEVICE_NAME", unique=true, nullable=false)
	private String deviceName;

	@Column(name="ADDITIONAL_INFO")
	private String additionalInfo; 

	public DeviceListBean () {
	/*	this.deviceName = deviceName; 
		this.serialNo = serialNo;
		this.additionalInfo = additionalInfo;*/
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public boolean isNew() {
		return (this.serialNo == null);	
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serialNo == null) ? 0 : serialNo.hashCode());
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
		if (!(obj instanceof DeviceListBean)) {
			return false;
		}
		DeviceListBean other = (DeviceListBean) obj;
		if (serialNo == null) {
			if (other.serialNo != null) {
				return false;
			}
		} else if (!serialNo.equals(other.serialNo)) {
			return false;
		}
		return true;
	}	
}