package com.landray.kmss.sys.attend.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.attend.dao.ISysAttendSynDingDao;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendSynDing;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysAttendSynDingDaoImp extends BaseDaoImp implements ISysAttendSynDingDao {

	/**
	 * 根据时间跟用户查找相同时间的原始打卡记录
	 * @param fdUserId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private SysAttendSynDing getSysAttendSyncDing(String fdUserId,Date date) throws Exception {
		//TODO 避免相同的原始记录 判断。待完成
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock(" sysAttendSynDing.fdPersonId=:fdUserId and sysAttendSynDing.fdUserCheckTime =:date");
		hqlInfo.setParameter("fdUserId",fdUserId);
		hqlInfo.setParameter("date",date);
		hqlInfo.setOrderBy("sysAttendSynDing.fdId desc");
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(Boolean.FALSE);
		Page page =findPage(hqlInfo);
		if(page !=null && CollectionUtils.isNotEmpty(page.getList())){
			return (SysAttendSynDing) page.getList().get(0);
		}
		return null;
	}

    private ISysAttendCategoryService sysAttendCategoryService;

	protected ISysAttendCategoryService getSysAttendCategoryService() {
        if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
        }
        return sysAttendCategoryService;
    }
	@Override
	public String addRecord(SysAttendMain main) throws Exception {
		Integer fdStatus = main.getFdStatus();
		Integer fdType =0;
		if(main.getFdCategory() !=null){
			fdType =main.getFdCategory().getFdType();
		}else if(main.getFdHisCategory() !=null){
			fdType = main.getFdHisCategory().getFdType();
		}
		Integer fdWorkType = main.getFdWorkType();
		if (!CategoryUtil.CATEGORY_FD_TYPE_TRUE.equals(fdType)) {
			// 非考勤
			return null;
		}
		String id =null;
		//如果存在相同时间，相同人，相同考勤组 则不添加
		SysAttendSynDing ding =getSysAttendSyncDing(main.getDocCreator().getFdId(),main.getDocCreateTime());
		if(ding ==null) {
			ding = new SysAttendSynDing();
			//WIFI名称的记录
			ding.setFdWifiName(main.getFdWifiName());
			ding.setFdUserSsid(main.getFdWifiName());
			ding.setFdAppName(main.getFdAppName());
			ding.setFdTimeResult(formatStatus(fdStatus, main.getFdState()));
			ding.setFdPersonId(main.getDocCreator().getFdId());
			ding.setFdWorkDate(main.getFdWorkDate());
			ding.setFdUserCheckTime(main.getDocCreateTime());
			ding.setFdBaseCheckTime(main.getFdBaseWorkTime());
			ding.setFdLocationResult(formatOutside(main.getFdOutside()));
			ding.setFdCheckType(fdWorkType == 0 ? "OnDuty" : "OffDuty");
			ding.setFdOutsideRemark(main.getFdDesc());
			ding.setFdUserLatitude(main.getFdLat());
			ding.setFdUserLongitude(main.getFdLng());
			ding.setFdLatLng(main.getFdLatLng());
			ding.setFdBaseAddress(main.getFdLocation());
			ding.setFdUserAddress(main.getFdAddress());
			if(main.getFdHisCategory() !=null) {
				ding.setFdGroupId(main.getFdHisCategory().getFdId());
			}
			ding.setFdDateType(main.getFdDateType());
			ding.setFdBaseMacAddr(main.getFdWifiMacIp());
			ding.setFdUserSsid(main.getFdWifiName());
			ding.setFdUserMacAddr(main.getFdWifiMacIp());
			ding.setFdSourceType(main.getFdSourceType());
			ding.setDocCreator(main.getDocCreator());
			ding.setDocCreateTime(new Date());
			ding.setFdLocationTitle(main.getFdAlterRecord());
			// 设置用户场所
			SysOrgPerson person = main.getDocCreator();
			ding.setAuthArea(SysTimeUtil.getUserAuthArea(person.getFdId()));
			id = this.add(ding);
		} else {
			List<SysAttendCategory> list=new ArrayList<SysAttendCategory>();
			list.add(main.getFdCategory());
			if(main.getDocCreateTime()!=null&&main.getDocCreator()!=null&&main.getFdCategory()!=null){
			com.alibaba.fastjson.JSONArray datas = getSysAttendCategoryService().filterAttendCategory(list,
					main.getDocCreateTime(), true, main.getDocCreator());
			
				
				if(datas==null||datas.isEmpty())
					ding.setFdBaseCheckTime(main.getFdBaseWorkTime());
			}
			id =ding.getFdId();
			ding.setFdAppName(main.getFdAppName());
			ding.setFdClassId(formatClassId(main));
			//修改描述
			ding.setFdLocationTitle(main.getFdAlterRecord());
			ding.setFdTimeResult(formatStatus(fdStatus, main.getFdState()));
			ding.setFdLocationResult(formatOutside(main.getFdOutside()));
			ding.setDocCreateTime(new Date());
			ding.setFdUserCheckTime(main.getFdBusiness().getFdBusEndTime());
			
			if("Leave".equals(ding.getFdTimeResult()))
			ding.setFdAppName("本系统");
			this.update(ding);
		}
		/**
		 * 将有效考勤的附件复制到原始记录中
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("synDingId", id);
		params.put("sysAttendId", main.getFdId());
		applicationContext.publishEvent(new Event_Common("addAttendMainAtts", params));
		return id;
	}

	private String formatStatus(Integer fdStatus, Integer fdState) {
		boolean isOk = (fdState != null && fdState.intValue() == 2)
				? true : false;// 异常审批通过为true
		if (isOk) {
			return "Normal";
		}
		if (fdStatus == null) {
			return null;
		}
		if (fdStatus == 0) {
			return "NotSigned";
		}
		if (fdStatus == 1) {
			return "Normal";
		}
		if (fdStatus == 2) {
			return "Late";
		}
		if (fdStatus == 3) {
			return "Early";
		}
		if (fdStatus == 4) {
			return "Trip";
		}
		if (fdStatus == 5) {
			return "Leave";
		}
		if (fdStatus == 6) {
			return "Outgoing";
		}
		return fdStatus.toString();
	}

	private String formatOutside(Boolean outside) {
		if (Boolean.TRUE.equals(outside)) {
			return "Outside";
		}
		return "Normal";
	}

	private String formatClassId(SysAttendMain main) {
		SysAttendCategoryWorktime workTime = main.getWorkTime();
		String fdWorkKey = main.getFdWorkKey();
		if (StringUtil.isNotNull(fdWorkKey)) {
			return fdWorkKey;
		}
		return workTime != null ? workTime.getFdId() : null;
	}
}
