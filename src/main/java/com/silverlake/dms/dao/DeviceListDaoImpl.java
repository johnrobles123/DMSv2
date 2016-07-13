package com.silverlake.dms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.silverlake.dms.dao.DeviceListDao;
import com.silverlake.dms.model.DeviceListBean;

@Repository("deviceListDao")
public class DeviceListDaoImpl extends AbstractDao<String, DeviceListBean> implements DeviceListDao {

	@Autowired
	DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}


	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	

	@Override
	public DeviceListBean findBySerialNo(String serialNo) throws SQLException {
		
		return getByKey(serialNo);
		/*
		String sql = "SELECT * FROM DEVICE_LIST WHERE serial_no = ?";

		PreparedStatement pstmt;
		pstmt = dataSource.getConnection().prepareStatement(sql);
		pstmt.setString(1, serialNo);
		ResultSet deviceSet = pstmt.executeQuery();
		DeviceListBean device = new DeviceListBean();

		if(deviceSet.next()){				
			device.setSerialNo(deviceSet.getString(1));
			device.setDeviceName(deviceSet.getString(2));
			device.setAdditionalInfo(deviceSet.getString(3));
		} else {
			device = null;
		}			
		
		return device;
		*/
	}
	
	@Override
	public List<DeviceListBean> findAll() {
		String sql = "SELECT * FROM DEVICE_LIST";
		List<DeviceListBean> dList = new ArrayList<DeviceListBean>();
		
		PreparedStatement pstmt;
		try {
			pstmt = dataSource.getConnection().prepareStatement(sql);
		
			try {
				ResultSet deviceSet = pstmt.executeQuery();
				
				while (deviceSet.next()) {
					DeviceListBean deviceList = new DeviceListBean();
					deviceList.setSerialNo(deviceSet.getString(1));
					deviceList.setDeviceName(deviceSet.getString(2));
					deviceList.setAdditionalInfo(deviceSet.getString(3));
					dList.add(deviceList);					
				}
	
			} finally {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dList;
	}



	@Override
	public void save(DeviceListBean device) throws SQLException {
	
		String sql  = "INSERT INTO DEVICE_LIST(DEVICE_NAME, SERIAL_NO, ADDITIONAL_INFO) "
				+ "VALUES (?,?,?)"; 
				
		PreparedStatement pstmt = dataSource.getConnection().prepareStatement(sql);
				
			pstmt.setString(1, device.getDeviceName());
			pstmt.setString(2,  device.getSerialNo());
			pstmt.setString(3,  device.getAdditionalInfo());
			pstmt.execute();
	}

	
	@Override
	public void update(DeviceListBean device) throws SQLException {
		String sql = "UPDATE DEVICE_LIST SET DEVICE_NAME=?, SERIAL_NO=?, ADDITIONAL_INFO=? "
				+ "WHERE serial_no =?";

		PreparedStatement pstmt = dataSource.getConnection().prepareStatement(sql);
		pstmt.setString(1, device.getDeviceName());
		pstmt.setString(2,  device.getSerialNo());
		pstmt.setString(3,  device.getAdditionalInfo());
		pstmt.setString(4,  device.getSerialNo());
		pstmt.execute();
		
	}


	@Override
	public void delete(String serialNo) throws SQLException {
		String sql = "DELETE FROM DEVICE_LIST WHERE serial_no = ?";

		PreparedStatement pstmt = dataSource.getConnection().prepareStatement(sql);
		pstmt.setString(1,  serialNo);
		pstmt.execute();
		
	}


	@Override
	public boolean isExistingDevice(String deviceName, String serialNo)
			throws SQLException {
		return false;
	}



}
