package com.landray.kmss.sys.attend.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.model.SysAttendDevice;

import net.sf.json.JSONObject;

public interface ISysAttendDeviceService extends IBaseService {

	/**
	 * 获取当前用户的设备号信息
	 * 
	 * @param fdDeviceType
	 *            kk,ding
	 * @return
	 */
	public String getUserDeviceIds(String fdDeviceType) throws Exception;

	/**
	 * 获取当前用户的设备号信息
	 * 
	 * @param fdDeviceType
	 * @return
	 * @throws Exception
	 */
	public SysAttendDevice getUserDevice(String fdDeviceType) throws Exception;

	/**
	 * 根据设备类型,设备号获取用户设备信息列表
	 * 
	 * @param fdDeviceType
	 * @param fdDeviceId
	 * @return
	 * @throws Exception
	 */
	public List<SysAttendDevice> findDevicesById(String fdDeviceType,
			String fdDeviceId) throws Exception;

	/**
	 * 根据设备类型,设备号获取用户id集合.用于判断同一设备是否多人使用(有效期为1天)
	 * 
	 * @param fdDeviceType
	 * @param fdDeviceId
	 * @return
	 * @throws Exception
	 */
	public List<String> findOrgByDeviceId(String fdDeviceType,
			String fdDeviceId) throws Exception;

	/**
	 * 保存或更新用户设备信息
	 * 
	 * @param request
	 * @param result
	 * @throws Exception
	 */
	public void saveOrUpdateUserDevice(HttpServletRequest request,
			JSONObject result) throws Exception;
}
