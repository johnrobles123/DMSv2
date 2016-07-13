package com.silverlake.dms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silverlake.dms.dao.DeviceListDao;
import com.silverlake.dms.model.DeviceListBean;
import com.silverlake.dms.service.DeviceListService;

@Service("deviceListService")
@Transactional
public class DeviceListServiceImpl implements DeviceListService {
	
	@Autowired
	DeviceListDao deviceListDao;

	/*
	@Autowired
	public void setDeviceListDao(DeviceListDao deviceListDao) {
		this.deviceListDao = deviceListDao;
	}*/
	
	@Override
	public DeviceListBean findById(String serialNo) throws SQLException {
		//return null;
		return deviceListDao.findBySerialNo(serialNo);		
	}

	@Override
	public List<DeviceListBean> findAll() {
		return deviceListDao.findAll();
	}

	@Override
	public void saveOrUpdate(DeviceListBean device) throws SQLException {
		if ((findById(device.getSerialNo()))==null) {
			deviceListDao.save(device);
		} else {
			deviceListDao.update(device);
		}
		
	}

	@Override
	public void delete(String serialNo) throws SQLException {
		deviceListDao.delete(serialNo);
		
	}

}
