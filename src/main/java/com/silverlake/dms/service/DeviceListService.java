package com.silverlake.dms.service;

import java.sql.SQLException;
import java.util.List;

import com.silverlake.dms.model.DeviceListBean;

public interface DeviceListService {

	DeviceListBean findById(String serialNo) throws SQLException;
	
	List<DeviceListBean> findAll() throws SQLException;

	void saveOrUpdate(DeviceListBean device) throws SQLException;
	
	void delete(String serialNo) throws SQLException;
	
}
