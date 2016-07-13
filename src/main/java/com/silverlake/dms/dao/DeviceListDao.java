package com.silverlake.dms.dao;

import java.sql.SQLException;
import java.util.List;

import com.silverlake.dms.model.DeviceListBean;

public interface DeviceListDao {
	
	DeviceListBean findBySerialNo(String Serial_No) throws SQLException;
	
	List<DeviceListBean> findAll();
	
	void save(DeviceListBean device) throws SQLException;

	void update(DeviceListBean device) throws SQLException;

	void delete(String serialNo) throws SQLException;
	
	public boolean isExistingDevice(String deviceName, String serialNo) throws SQLException;
}
