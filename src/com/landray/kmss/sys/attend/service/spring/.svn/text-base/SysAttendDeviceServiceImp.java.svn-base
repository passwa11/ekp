package com.landray.kmss.sys.attend.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.model.SysAttendDevice;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendDeviceService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class SysAttendDeviceServiceImp extends BaseServiceImp
		implements ISysAttendDeviceService {

	private ISysAttendConfigService sysAttendConfigService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendDeviceServiceImp.class);
	
	@Override
	public String getUserDeviceIds(String fdDeviceType) throws Exception {
		String fdDeviceIds = "";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysAttendDevice.docCreator.fdId=:docCreatorId and sysAttendDevice.fdClientType=:fdClientType");
		hqlInfo.setParameter("docCreatorId",
				UserUtil.getUser().getFdId());
		hqlInfo.setParameter("fdClientType", fdDeviceType);
		hqlInfo.setOrderBy("sysAttendDevice.docAlterTime desc");
		SysAttendDevice sysAttendDevice = (SysAttendDevice) this.findFirstOne(hqlInfo);
		if (sysAttendDevice != null) {
			fdDeviceIds = sysAttendDevice.getFdDeviceIds();
		}
		return fdDeviceIds;
	}

	@Override
	public SysAttendDevice getUserDevice(String fdDeviceType) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysAttendDevice.docCreator.fdId=:docCreatorId and sysAttendDevice.fdClientType=:fdClientType");
		hqlInfo.setParameter("docCreatorId",
				UserUtil.getUser().getFdId());
		hqlInfo.setParameter("fdClientType", fdDeviceType);
		hqlInfo.setOrderBy("sysAttendDevice.docAlterTime desc");
		SysAttendDevice device = (SysAttendDevice) this.findFirstOne(hqlInfo);
		if (device != null) {
			return device;
		}
		return null;
	}

	@Override
	public List<SysAttendDevice> findDevicesById(String fdDeviceType,
			String fdDeviceId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysAttendDevice.fdDeviceIds like :fdDeviceId and sysAttendDevice.fdClientType=:fdClientType");
		hqlInfo.setParameter("fdClientType", fdDeviceType);
		hqlInfo.setParameter("fdDeviceId", "%" + fdDeviceId + "%");
		hqlInfo.setOrderBy("sysAttendDevice.docAlterTime desc");
		List<SysAttendDevice> list = this.findList(hqlInfo);
		return list;
	}

	@Override
	public List<String> findOrgByDeviceId(String fdDeviceType,
			String fdDeviceId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sb = new StringBuffer();
		sb.append(
				"sysAttendDevice.fdDeviceIds like :fdDeviceId and sysAttendDevice.fdClientType=:fdClientType");
		sb.append(
				" and sysAttendDevice.docAlterTime>=:startTime and sysAttendDevice.docAlterTime<:endTime");
		hqlInfo.setWhereBlock(sb.toString());
		hqlInfo.setParameter("fdClientType", fdDeviceType);
		hqlInfo.setParameter("fdDeviceId", "%" + fdDeviceId + "%");
		hqlInfo.setParameter("startTime", AttendUtil.getDate(new Date(), 0));
		hqlInfo.setParameter("endTime", AttendUtil.getDate(new Date(), 1));
		hqlInfo.setOrderBy("sysAttendDevice.docAlterTime desc");
		List<SysAttendDevice> list = this.findList(hqlInfo);
		Set<String> orgIds = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			SysAttendDevice device = list.get(i);
			orgIds.add(device.getDocCreator().getFdId());
		}
		return new ArrayList<String>(orgIds);
	}

	@Override
	public void saveOrUpdateUserDevice(HttpServletRequest request,
			JSONObject result) throws Exception {
		String fdDeviceId = request.getParameter("fdDeviceId");
		if (StringUtil.isNull(fdDeviceId)) {
			fdDeviceId = request.getParameter("fdDevice");
		}
		logger.warn("打卡后——打卡验证，获取用户当前设备码：" + fdDeviceId + ";");
		if (StringUtil.isNull(fdDeviceId)) {
			logger.warn("打卡后——设备码为空，打卡成功。");
			return;
		}
		SysAttendConfig config = this.sysAttendConfigService
				.getSysAttendConfig();
		if (config != null && config.getFdSameDeviceLimit() != null
				&& Boolean.FALSE.equals(config.getFdSameDeviceLimit())) {
			return;
		}
		if (StringUtil.isNotNull(fdDeviceId)) {
			// 支持kk,钉钉
			String fdDeviceType = AttendUtil
					.getDeviceClientType(request);
			SysAttendDevice device = this.getUserDevice(fdDeviceType);
			List<String> userList = this.findOrgByDeviceId(fdDeviceType,
					fdDeviceId);
			if (userList == null || userList.isEmpty()) {
				if (device == null) {
					device = new SysAttendDevice();
					device.setDocCreateTime(new Date());
					device.setDocCreator(UserUtil.getUser());
					device.setFdClientType(fdDeviceType);
				}
			} else {
				// 同一设备号不允许多人使用
				if (userList.size() > 1
						|| !userList.contains(UserUtil.getUser().getFdId())) {
					result.put("code", "01");
					logger.warn("打卡后——打卡验证失败，该设备码当天已有人使用。");
					logger.warn(
							"同一设备号不允许多人使用:用户名:" + UserUtil.getUser().getFdName()
									+ ";设备号:" + fdDeviceId);
					throw new Exception("同一设备号不允许多人使用");
				}
			}

			if (fdDeviceId.equals(device.getFdDeviceIds())) {
				device.setDocAlterTime(new Date());
				this.update(device);
				logger.warn("打卡后——打卡成功");
				return;
			}
			String fdDeviceIds = device.getFdDeviceIds();
			if (StringUtil.isNotNull(fdDeviceIds)) {
				fdDeviceIds = fdDeviceIds + ";" + fdDeviceId;
			} else {
				fdDeviceIds = fdDeviceId;
			}

			String[] deviceIdArr = fdDeviceIds.split(";");
			String tmpDeviceIds = fdDeviceIds;
			boolean isDeviceLimit = false;
			if (config != null && Boolean.TRUE.equals(config.getFdClientLimit())
					&& Boolean.TRUE.equals(config.getFdDeviceLimit())) {
				isDeviceLimit = true;
				Integer count = config.getFdDeviceCount();
				if (count != null && deviceIdArr.length > count) {
					tmpDeviceIds = "";
					for (int i = deviceIdArr.length
							- count; i < deviceIdArr.length; i++) {
						if (StringUtil.isNotNull(tmpDeviceIds)) {
							tmpDeviceIds = tmpDeviceIds + ";" + deviceIdArr[i];
						} else {
							tmpDeviceIds = deviceIdArr[i];
						}
					}
				}
			}
			if (!isDeviceLimit) {
				tmpDeviceIds = fdDeviceId;// 不限制客户端时,只保存最后一次设备号信息
			}
			device.setFdDeviceIds(tmpDeviceIds);
			device.setDocAlterTime(new Date());
			this.update(device);
			logger.warn("打卡后——打卡成功。");

		}
	}


	public void setSysAttendConfigService(
			ISysAttendConfigService sysAttendConfigService) {
		this.sysAttendConfigService = sysAttendConfigService;
	}

}
