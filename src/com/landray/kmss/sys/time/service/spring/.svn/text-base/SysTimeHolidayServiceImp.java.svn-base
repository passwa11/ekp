package com.landray.kmss.sys.time.service.spring;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.model.SysTimeHolidayPach;
import com.landray.kmss.sys.time.service.ISysTimeHolidayPachService;
import com.landray.kmss.sys.time.service.ISysTimeHolidayService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
/**
 * 节假日设置业务接口实现
 * 
 * @author
 * @version 1.0 2017-09-26
 */
public class SysTimeHolidayServiceImp extends ExtendDataServiceImp
		implements ISysTimeHolidayService, IXMLDataBean,
		ApplicationContextAware {

	private ISysTimeHolidayPachService sysTimeHolidayPachService = null;
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	public void setSysTimeHolidayPachService(
			ISysTimeHolidayPachService sysTimeHolidayPachService) {
		this.sysTimeHolidayPachService = sysTimeHolidayPachService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		String search = requestInfo.getParameter("search");
		String where = null;
		if (StringUtil.isNotNull(search)) {
			where = "fdName like '%" + search + "%'";
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(where);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		hqlInfo.setOrderBy("fdName");
		List<SysTimeHoliday> holidays = findList(hqlInfo);
		HashMap<String, String> node = null;
		for (SysTimeHoliday holiday : holidays) {
			node = new HashMap<String, String>();
			node.put("id", holiday.getFdId());
			node.put("name", holiday.getFdName());
			rtnList.add(node);
		}
		return rtnList;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		String fdId = super.add(modelObj);
		SysTimeHoliday holiday = (SysTimeHoliday) modelObj;
		List<SysTimeHolidayDetail> details = holiday.getFdHolidayDetailList();
		SysTimeHolidayPach pach = null;
		for (int i = 0; i < details.size(); i++) {
			String pachtime = details.get(i).getFdPatchDay();
			if (StringUtil.isNotNull(pachtime)) {
				String[] pts = pachtime.split(",");
				for (String pt : pts) {
					if (StringUtil.isNotNull(pt)) {
						pach = new SysTimeHolidayPach();
						pach.setFdDetail(details.get(i));
						pach.setFdHoliday(holiday);
						pach.setFdName(holiday.getFdName());
						pach.setFdPachTime(
								DateUtil.convertStringToDate(pt, null, null));
						sysTimeHolidayPachService.add(pach);
					}
				}
			}
		}
		clearCache();
		publishAttendEvent();
		return fdId;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		List<SysTimeHolidayPach> plist = sysTimeHolidayPachService
				.findList("fdHoliday.fdId='" + modelObj.getFdId() + "'", null);
		for (int i = 0; i < plist.size(); i++) {
			sysTimeHolidayPachService.delete(plist.get(i));
		}
		SysTimeHoliday holiday = (SysTimeHoliday) modelObj;
		List<SysTimeHolidayDetail> details = holiday.getFdHolidayDetailList();
		SysTimeHolidayPach pach = null;
		for (int i = 0; i < details.size(); i++) {
			String pachtime = details.get(i).getFdPatchDay();
			if (StringUtil.isNotNull(pachtime)) {
				String[] pts = pachtime.split(",");
				for (String pt : pts) {
					if (StringUtil.isNotNull(pt)) {
						pach = new SysTimeHolidayPach();
						pach.setFdDetail(details.get(i));
						pach.setFdHoliday(holiday);
						pach.setFdName(holiday.getFdName());
						pach.setFdPachTime(
								DateUtil.convertStringToDate(pt, null, null));
						sysTimeHolidayPachService.add(pach);
					}
				}
			}
		}
		super.update(modelObj);
		clearCache();
		publishAttendEvent();
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		List<SysTimeHolidayPach> plist = sysTimeHolidayPachService
				.findList("fdHoliday.fdId='" + modelObj.getFdId() + "'", null);
		for (int i = 0; i < plist.size(); i++) {
			sysTimeHolidayPachService.delete(plist.get(i));
		}
		super.delete(modelObj);
		clearCache();
		publishAttendEvent();
	}

	private void clearCache() {
		KmssCache cache = new KmssCache(SysTimeHoliday.class);
		cache.setAfterCommit(false);
		cache.clear();
	}

	@Override
	public List<SysTimeHolidayDetail> getHolidayDetail(String fdHolidayId)
			throws Exception {
		List<SysTimeHolidayDetail> details = new ArrayList<SysTimeHolidayDetail>();
		if (StringUtil.isNull(fdHolidayId)) {
			return details;
		}
		SysTimeHoliday holiday = (SysTimeHoliday) findByPrimaryKey(fdHolidayId,
				SysTimeHoliday.class, true);
		details = holiday.getFdHolidayDetailList();
		return details;
	}

	@Override
	public List<SysTimeHolidayPach> getHolidayPach(String fdHolidayDetailId)
			throws Exception {
		List<SysTimeHolidayPach> pachs = new ArrayList<SysTimeHolidayPach>();
		if (StringUtil.isNull(fdHolidayDetailId)) {
			return pachs;
		}
		pachs = sysTimeHolidayPachService
				.findList("fdDetail.fdId='" + fdHolidayDetailId + "'", null);
		return pachs;
	}

	@Override
	public List<SysTimeHolidayPach> getHolidayPachs(String fdHolidayId)
			throws Exception {
		List<SysTimeHolidayPach> pachs = new ArrayList<SysTimeHolidayPach>();
		if (StringUtil.isNull(fdHolidayId)) {
			return pachs;
		}
		pachs = sysTimeHolidayPachService
				.findList("fdHoliday.fdId='" + fdHolidayId + "'", null);
		return pachs;
	}

	private void publishAttendEvent() {
		// 发送事件通知
		applicationContext.publishEvent(new Event_Common(
				"regenUserAttendMain"));
	}
}
