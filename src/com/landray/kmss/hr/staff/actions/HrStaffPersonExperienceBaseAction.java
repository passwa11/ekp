package com.landray.kmss.hr.staff.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBase;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人经历基类
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public abstract class HrStaffPersonExperienceBaseAction extends
		HrStaffImportAction {
	private ISysOrgPersonService sysOrgPersonService;
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrStaffPersonExperienceBaseAction.class);

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
            sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
        }
		return sysOrgPersonService;
	}

	/**
	 * 子类自己处理JOSN数组
	 * 
	 * @param list
	 * @return
	 */
	public abstract JSONArray handleJSONArray(
			List<HrStaffPersonExperienceBase> list);

	@SuppressWarnings("unchecked")
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		// 以下筛选属性需要手工定义筛选范围
		String _fdKey = cv.poll("_fdKey");
		String _fdDept = cv.poll("_fdDept");

		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		} else {
			whereBlock = new StringBuffer("1 = 1");
		}

		// 姓名、登录名、手机号或邮箱
		if (StringUtil.isNotNull(_fdKey)) {
			// 查询组织机构人员信息
			HQLInfo _hqlInfo = new HQLInfo();
			_hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			_hqlInfo
					.setWhereBlock("sysOrgPerson.fdName like :fdKey or sysOrgPerson.fdLoginName like :fdKey or sysOrgPerson.fdMobileNo like :fdKey or sysOrgPerson.fdEmail like :fdKey");
			_hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
			List<String> ids = getSysOrgPersonService().findValue(_hqlInfo);

			whereBlock
					.append(" and ((fdPersonInfo.fdName like :fdKey or fdPersonInfo.fdMobileNo like :fdKey or fdPersonInfo.fdEmail like :fdKey)");
			if (!ids.isEmpty()) {
				whereBlock.append(" or fdPersonInfo.fdId in (:ids)");
				hqlInfo.setParameter("ids", ids);
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}
		// 部门
		if (StringUtil.isNotNull(_fdDept)) {
			// 查询组织机构人员信息
			HQLInfo _hqlInfo = new HQLInfo();
			_hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			_hqlInfo.setWhereBlock("sysOrgPerson.fdOrgType=:fdOrgType and sysOrgPerson.fdHierarchyId like :fdDept");
			_hqlInfo.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_PERSON);
			_hqlInfo.setParameter("fdDept","%"+ _fdDept+"%");

			List<String> ids = getSysOrgPersonService().findValue(_hqlInfo);
			if(!CollectionUtils.isEmpty(ids)){
				whereBlock.append(" and fdPersonInfo.fdId in (:ids) ");
				hqlInfo.setParameter("ids", ids);
			}else{
				//如果部门下面没有查询到人，则直接返回空查询
				whereBlock.append(" and 1=2 ");
			}
		}

		// 员工状态
		String[] _fdStatus = cv.polls("_fdStatus");
		if (_fdStatus != null && _fdStatus.length > 0) {
			List<String> fdStatus = new ArrayList<String>();
			boolean isNull = false;
			for (String _fdStatu : _fdStatus) {
				if ("official".equals(_fdStatu)) {
					isNull = true;
				}
				fdStatus.add(_fdStatu);
			}
			whereBlock.append(" and (fdPersonInfo.fdStatus in (:fdStatus)");
			if (isNull) {
				whereBlock.append(" or fdPersonInfo.fdStatus is null");
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdStatus", fdStatus);
		}

		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	/**
	 * 根据人员ID获取相应的数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void listData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String personInfoId = request.getParameter("personInfoId");

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setRowSize(Integer.MAX_VALUE);
		hqlInfo.setWhereBlock("fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffPersonExperienceBase> list = getServiceImp(request)
				.findPage(hqlInfo).getList();

		// 子类自己处理JOSN数组
		JSONArray array = handleJSONArray(list);
		response.setHeader("content-type", "application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array);
		response.getWriter().flush();
		response.getWriter().close();
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		JSONObject obj = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			obj.put("state", true);
		} catch (Exception e) {
			logger.error("", e);
			obj.put("msg", e);
			obj.put("state", false);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		JSONObject obj = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
			obj.put("state", true);
		} catch (Exception e) {
			logger.error("", e);
			obj.put("msg", e);
			obj.put("state", false);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

}
