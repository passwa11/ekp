package com.landray.kmss.sys.time.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.model.SysTimeWorkDetail;
import com.landray.kmss.sys.time.service.ISysTimeCommonTimeService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 班次时间业务接口实现
 */
public class SysTimeCommonTimeServiceImp extends BaseServiceImp implements
		ISysTimeCommonTimeService {


	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimeUtil.updateSignTimesCatch();
		return super.add(modelObj);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		SysTimeUtil.updateSignTimesCatch();
		super.delete(modelObj);
	}
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysTimeUtil.updateSignTimesCatch();
		super.update(modelObj);
	}
	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String modelName = form.getModelClass().getName();
		modelName = StringUtil.isNotNull(modelName) ? modelName
				: getModelName();
		UserOperHelper.logAdd(modelName);
		IBaseModel model = convertFormToModel(form, null, requestContext);
		String fdId = model.getFdId();
		return add(model);
	}

	// 获取通用班次list 排除自定义班次
	@Override
	public JSONArray getCommonTime(String fdId) throws Exception {
		HQLInfo hql = new HQLInfo();
		if (fdId != null) {
			hql.setWhereBlock("fdId=:fdId and status='true' and type!='2'");
			hql.setParameter("fdId", fdId);
		} else {
			hql.setWhereBlock("status='true' and type!='2'");
		}
		List<SysTimeCommonTime> commonTimes = findList(hql);
		JSONArray ja = new JSONArray();
		for (SysTimeCommonTime common : commonTimes) {
			JSONObject obj = new JSONObject();
			obj.put("type", "1");
			obj.put("fdId", common.getFdId());
			obj.put("name", common.getFdName());
			obj.put("color", common.getFdWorkTimeColor());
			obj.put("fdTotalDay", common.getFdTotalDay());
			obj.put("restStart", DateUtil.convertDateToString(
					common.getFdRestStartTime(), DateUtil.TYPE_TIME,
					null));
			obj.put("restEnd", DateUtil.convertDateToString(
					common.getFdRestEndTime(), DateUtil.TYPE_TIME,
					null));
			obj.put("fdRestStartType",common.getFdRestStartType());
			obj.put("fdRestEndType",common.getFdRestEndType());


			JSONArray deJa = new JSONArray();
			for (SysTimeWorkDetail detail : common.getSysTimeWorkDetails()) {
				JSONObject deObj = new JSONObject();
				deObj.put("fdId", detail.getFdId());
				deObj.put("start", DateUtil.convertDateToString(
						detail.getFdWorkStartTime(),
						DateUtil.TYPE_TIME, null));
				deObj.put("end", DateUtil.convertDateToString(
						detail.getFdWorkEndTime(), DateUtil.TYPE_TIME,
						null));
				String overTime = "1";
				if (detail.getFdOverTimeType() != null) {
					overTime = detail.getFdOverTimeType().toString();
				}
				deObj.put("overTimeType", overTime);
				/**
				 * 最早最晚打卡时间
				 */
				deObj.put("beginTime", DateUtil.convertDateToString(
						detail.getFdStartTime(),
						DateUtil.TYPE_TIME, null));
				deObj.put("overTime", DateUtil.convertDateToString(
						detail.getFdOverTime(), DateUtil.TYPE_TIME,
						null));

				String endOverTime = "1";
				if (detail.getFdEndOverTimeType() != null) {
					endOverTime = detail.getFdEndOverTimeType().toString();
				}
				deObj.put("endOverTimeType", endOverTime);

				deJa.add(deObj);
			}
			obj.put("times", deJa);
			ja.add(obj);
		}
		return ja;
	}

	// 获取引用该通用班次的区域组
	@Override
	public JSONArray getRelevantAreas(String fdId) throws Exception {
		SysTimeCommonTime commonTimes = (SysTimeCommonTime) findByPrimaryKey(
				fdId);
		List<HashMap<String, String>> areas = new ArrayList<>();

		for (SysTimeWork timeWork : commonTimes.getSysTimeWorkList()) {
			if (timeWork == null || timeWork.getSysTimeArea() == null) {
				continue;
			}
			HashMap<String, String> map = new HashMap<>();
			map.put(timeWork.getSysTimeArea().getFdId(),
					timeWork.getSysTimeArea().getFdName());
			if (!areas.contains(map)) {
				areas.add(map);
			}
		}
		for (SysTimePatchwork patchWork : commonTimes.getSysTimePatchwork()) {
			if (patchWork == null || patchWork.getSysTimeArea() == null) {
				continue;
			}
			HashMap<String, String> map = new HashMap<>();
			map.put(patchWork.getSysTimeArea().getFdId(),
					patchWork.getSysTimeArea().getFdName());
			if (!areas.contains(map)) {
				areas.add(map);
			}
		}
		return JSONArray.fromObject(areas);
	}

}
