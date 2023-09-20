package com.landray.kmss.hr.staff.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonFamily;
import com.landray.kmss.hr.staff.service.IHrStaffPersonFamilyService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 家庭信息
 * 
 * 
 */
public class HrStaffPersonFamilyAction extends HrStaffImportAction {
	private IHrStaffPersonFamilyService hrStaffPersonFamilyService;
	private ISysOrgPersonService sysOrgPersonService;
	@Override
	protected IHrStaffPersonFamilyService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffPersonFamilyService == null) {
			hrStaffPersonFamilyService = (IHrStaffPersonFamilyService) getBean(
					"hrStaffPersonFamilyService");
		}
		return hrStaffPersonFamilyService;
	}

	public IHrStaffPersonFamilyService getHrStaffPersonFamilyService() {
		return hrStaffPersonFamilyService;
	}

	public void setHrStaffPersonFamilyService(
			IHrStaffPersonFamilyService hrStaffPersonFamilyService) {
		this.hrStaffPersonFamilyService = hrStaffPersonFamilyService;
	}

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
            sysOrgPersonService = (ISysOrgPersonService) getBean(
                    "sysOrgPersonService");
        }
		return sysOrgPersonService;
	}

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
					.setWhereBlock(
							"sysOrgPerson.fdName like :fdKey or sysOrgPerson.fdLoginName like :fdKey or sysOrgPerson.fdMobileNo like :fdKey or sysOrgPerson.fdEmail like :fdKey");
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
		// 部门
		if (StringUtil.isNotNull(_fdDept)) {
			// 查询组织机构人员信息
			HQLInfo _hqlInfo = new HQLInfo();
			_hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			_hqlInfo.setWhereBlock("sysOrgPerson.hbmParent.fdId = :fdDept");
			_hqlInfo.setParameter("fdDept", _fdDept);
			List<String> ids = getSysOrgPersonService().findValue(_hqlInfo);

			whereBlock
					.append(" and (fdPersonInfo.fdOrgParent.fdId = :fdDept");
			if (!ids.isEmpty()) {
				whereBlock.append(" or fdPersonInfo.fdId in (:ids)");
				hqlInfo.setParameter("ids", ids);
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdDept", _fdDept);
		}
		whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock,
				"hrStaffPersonFamily", hqlInfo);
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	public void listData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String personInfoId = request.getParameter("personInfoId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrStaffPersonFamily.fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffPersonFamily> list = getServiceImp(request)
				.findPage(hqlInfo).getList();

		JSONArray source = new JSONArray();
		try {
			for (int i = 0; i < list.size(); i++) {
				JSONObject json = new JSONObject();
				json.put("fdName", list.get(i).getFdName());
				json.put("fdRelated", list.get(i).getFdRelated());
				json.put("fdCompany", list.get(i).getFdCompany());
				json.put("fdOccupation", list.get(i).getFdOccupation());
				json.put("fdConnect", list.get(i).getFdConnect());
				json.put("fdMemo", list.get(i).getFdMemo());
				json.put("fdId", list.get(i).getFdId());
				source.add(json);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		// 子类自己处理JOSN数组
		// JSONArray array = handleJSONArray(list);

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(source);
		response.getWriter().flush();
		response.getWriter().close();
	}

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString(
						"hr-staff:hrStaffPerson.family.xls");
	}

}
