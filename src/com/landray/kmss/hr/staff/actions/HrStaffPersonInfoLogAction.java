package com.landray.kmss.hr.staff.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoLogService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 员工信息日志
 * 
 * @author 潘永辉 2017-1-7
 * 
 */
public class HrStaffPersonInfoLogAction extends ExtendAction {
	private IHrStaffPersonInfoLogService hrStaffPersonInfoLogService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffPersonInfoLogService == null) {
            hrStaffPersonInfoLogService = (IHrStaffPersonInfoLogService) getBean("hrStaffPersonInfoLogService");
        }
		return hrStaffPersonInfoLogService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String personInfoId = request.getParameter("personInfoId");
		StringBuffer whereBlock = new StringBuffer();
		String _whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNotNull(_whereBlock)) {
			whereBlock.append(_whereBlock);
		} else {
			whereBlock.append("1 = 1");
		}
		if (StringUtil.isNotNull(personInfoId)) {
			whereBlock
					.append(" and hrStaffPersonInfoLog.fdTargets.fdId in (:personInfoId)");
			hqlInfo.setParameter("personInfoId", personInfoId);
		}
		//非admin 过滤有权限的数据
		if(!UserUtil.getKMSSUser().isAdmin()) {
			whereBlock = HrStaffAuthorityUtil.builtAuthorityWhereBlock(whereBlock, "hrStaffPersonInfoLog.fdTargets", hqlInfo);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	/**
	 * 获取概况数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void overview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 这里的数据是对象，对象里有2个属性，一个是年月(title)，另一个是数组数据(list)
		JSONArray array = new JSONArray();

		// 这里的数据需要按年月分组
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setRowSize(20); // 获取20条数据
		hqlInfo.setOrderBy("fdCreateTime desc");
		StringBuffer whereBlock=new StringBuffer(" 1 = 1 ");
		//过滤有文档授权的数据
		whereBlock = HrStaffAuthorityUtil.builtAuthorityWhereBlock(whereBlock, "hrStaffPersonInfoLog.fdTargets", hqlInfo);
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<HrStaffPersonInfoLog> list = getServiceImp(request).findPage(
				hqlInfo).getList();
		// 添加日志信息
		UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());
		if (list != null && !list.isEmpty()) {
			Map<String, List<HrStaffPersonInfoLog>> map = group(list);

			// 通过ArrayList构造函数把map.entrySet()转换成list
			List<Map.Entry<String, List<HrStaffPersonInfoLog>>> listMap = new ArrayList<Map.Entry<String, List<HrStaffPersonInfoLog>>>(
					map.entrySet());
			// 通过比较器实现比较排序
			Collections
					.sort(
							listMap,
							new Comparator<Map.Entry<String, List<HrStaffPersonInfoLog>>>() {
								@Override
								public int compare(
										Entry<String, List<HrStaffPersonInfoLog>> e1,
										Entry<String, List<HrStaffPersonInfoLog>> e2) {
									return e2.getKey().compareTo(e1.getKey());
								}
							});

			String fdCreator = null;
			for (Map.Entry<String, List<HrStaffPersonInfoLog>> entry : listMap) {
				JSONObject obj = new JSONObject();
				JSONArray _list = new JSONArray();
				List<HrStaffPersonInfoLog> logs = entry.getValue();
				for (HrStaffPersonInfoLog log : logs) {
					JSONObject _obj = new JSONObject();
					_obj.put("fdCreateTime", DateUtil.convertDateToString(log
							.getFdCreateTime(), DateUtil.PATTERN_DATE));
					fdCreator = log.getFdCreator().getFdName();
					if (log.getIsAnonymous()) {
						fdCreator = ResourceUtil
								.getString("hr-staff:hrStaffPersonInfoLog.sync.creator");
					}
					_obj.put("fdCreator", fdCreator);
					_obj.put("fdDetails", log.getFdDetails());
					_obj.put("fdId", log.getFdId());

					_list.add(_obj);
				}
				obj.put("title", entry.getKey());
				obj.put("list", _list);
				array.add(obj);
			}
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 将集合数据按年月进行分组
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, List<HrStaffPersonInfoLog>> group(
			List<HrStaffPersonInfoLog> list) {
		Map<String, List<HrStaffPersonInfoLog>> group = new HashMap<String, List<HrStaffPersonInfoLog>>();
		String parrent = ResourceUtil
				.getString("hr-staff:hrStaffPersonInfoLog.fdCreateTime.overviewType");
		for (HrStaffPersonInfoLog log : list) {
			String key = DateUtil.convertDateToString(log.getFdCreateTime(),
					parrent);
			if (group.get(key) == null) {
				List<HrStaffPersonInfoLog> _list = new ArrayList<HrStaffPersonInfoLog>();
				_list.add(log);
				group.put(key, _list);
			} else {
				group.get(key).add(log);
			}
		}
		return group;
	}

	/**
	 * 获取操作对象
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void findTargets(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray array = new JSONArray();
		String logId = request.getParameter("logId");

		HrStaffPersonInfoLog personInfoLog = (HrStaffPersonInfoLog) getServiceImp(
				request).findByPrimaryKey(logId);
		UserOperHelper.logFind(personInfoLog);// 添加日志信息
		for (HrStaffPersonInfo info : personInfoLog.getFdTargets()) {
			JSONObject obj = new JSONObject();
			obj.put("fdId", info.getFdId());
			obj.put("fdName", info.getFdName());
			array.add(obj);
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
