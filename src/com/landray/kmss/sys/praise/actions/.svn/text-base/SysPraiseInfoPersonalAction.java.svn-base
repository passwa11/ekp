package com.landray.kmss.sys.praise.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoPersonalService;
import com.landray.kmss.sys.praise.util.SysPraiseInfoCommonUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.Sheet;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysPraiseInfoPersonalAction extends ExtendAction {

	private static Map<String, String> idMap = new HashMap<String, String>();

	private ISysPraiseInfoPersonalService sysPraiseInfoPersonalService;

	@Override
	protected ISysPraiseInfoPersonalService
			getServiceImp(HttpServletRequest request) {
		if (sysPraiseInfoPersonalService == null) {
			sysPraiseInfoPersonalService =
					(ISysPraiseInfoPersonalService) getBean(
							"sysPraiseInfoPersonalService");
		}
		return sysPraiseInfoPersonalService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		String fdTimeType = request.getParameter("q.fdTimeType");

		request.setAttribute("fdTimeType", fdTimeType);
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "sysPraiseInfoPersonal.fdPerson.fdIsAvailable=true ";
		} else {
			whereBlock +=
					" and sysPraiseInfoPersonal.fdPerson.fdIsAvailable=true ";
		}

		// #132466 修复部门支持多选
		String[] fdOrgInfo = request.getParameterValues("q.fdPerson");
		if (null != fdOrgInfo) {
			StringBuffer sb = new StringBuffer();

			sb.append(" and ( ");

			int index = 0;
			String sp = " ";
			for (String org : fdOrgInfo) {
				sb.append(sp + "sysPraiseInfoPersonal.fdPerson.fdHierarchyId like (:fdHierarchyId" + index + ")");
				hqlInfo.setParameter("fdHierarchyId" + index, "%" + org + "%");
				index++;
				sp = " or ";
			}

			sb.append(" )");

			whereBlock += sb.toString();
		}

		hqlInfo.setWhereBlock(whereBlock);

		reBuildOrderInfo(fdTimeType, hqlInfo, request);
	}

	protected void reBuildOrderInfo(String fdTimeType, HQLInfo hqlInfo,
			HttpServletRequest request) {
		String orderby = request.getParameter("orderby");
		String ordertype = request.getParameter("ordertype");
		String orderInfo = request.getParameter("orderInfo");

		if (StringUtil.isNull(orderby) && StringUtil.isNull(orderInfo)) {
			hqlInfo.setOrderBy("fdId DESC");
		} else {
			String fdContent = null;
			if (StringUtil.isNull(fdTimeType)
					|| SysPraiseInfoCommonUtil.TOTAL.equals(fdTimeType)) {
				fdContent = "fdTotal";
			} else if (SysPraiseInfoCommonUtil.WEEK.equals(fdTimeType)) {
				fdContent = "fdWeek";
			} else if (SysPraiseInfoCommonUtil.MONTH.equals(fdTimeType)) {
				fdContent = "fdMonth";
			} else if (SysPraiseInfoCommonUtil.YEAR.equals(fdTimeType)) {
				fdContent = "fdYear";
			} else {
				fdContent = "fdTotal";
			}

			if (StringUtil.isNotNull(orderInfo)
					&& "fdSumCount".equals(orderInfo)) {
				orderby = fdContent + ".fdPraisedNum + " + fdContent
						+ ".fdReceiveNum";
				orderby += " DESC";
			} else {
				boolean isReserve = false;
				if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
					isReserve = true;
				}
				orderby = fdContent + "." + orderby;
				if (isReserve) {
                    orderby += " desc";
                }
			}
		}
		hqlInfo.setOrderBy(orderby);
	}

	public ActionForward buildIdsInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ids = request.getParameter("ids");
		if (StringUtil.isNotNull(ids)) {
			idMap.put(UserUtil.getUser().getFdId() + "_praiseDetail", ids);
		}
		return null;
	}

	public ActionForward downExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<String> columnList = getcolumnList();
		String fdTimeType = request.getParameter("q.fdTimeType");
		String fdNamePart = null;
		String fdContent = null;
		if (StringUtil.isNull(fdTimeType)
				|| SysPraiseInfoCommonUtil.TOTAL.equals(fdTimeType)) {
			fdContent = ResourceUtil
					.getString("sys-praise:sysPraiseInfo.calculate.total");
			fdNamePart = "fdTotal";
		} else if (SysPraiseInfoCommonUtil.WEEK.equals(fdTimeType)) {
			fdContent = ResourceUtil
					.getString("sys-praise:sysPraiseInfo.calculate.week");
			fdNamePart = "fdWeek";
		} else if (SysPraiseInfoCommonUtil.MONTH.equals(fdTimeType)) {
			fdContent = ResourceUtil
					.getString("sys-praise:sysPraiseInfo.calculate.month");
			fdNamePart = "fdMonth";
		} else if (SysPraiseInfoCommonUtil.YEAR.equals(fdTimeType)) {
			fdContent = ResourceUtil
					.getString("sys-praise:sysPraiseInfo.calculate.year");
			fdNamePart = "fdYear";
		} else {
			fdContent = ResourceUtil
					.getString("sys-praise:sysPraiseInfo.calculate.total");
			fdNamePart = "fdTotal";
		}
		WorkBook workbook = new WorkBook();
		workbook.setLocale(request.getLocale());
		Sheet sheet = new Sheet();
		String title =
				ResourceUtil.getString("sys-praise:table.sysPraiseInfoDetail")
						+ "(" + fdContent + ")";
		sheet.setTitle(title);
		Column col = null;
		for (String str : columnList) {
			col = new Column();
			col.setTitle(str);
			col.setRedFont(true);
			sheet.addColumn(col);
		}
		sheet.setContentList(getContentList(request, fdNamePart));

		workbook.addSheet(sheet);
		workbook.setFilename(title);
		ExcelOutput output = new ExcelOutputImp();
		output.output(workbook, response);
		return null;
	}

	private List getContentList(HttpServletRequest request, String fdNamePart)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		changeFindPageHQLInfo(request, hqlInfo);
		String fdKey = UserUtil.getUser().getFdId() + "_praiseDetail";
		if (idMap.containsKey(fdKey)) {
			String ids = idMap.get(fdKey);
			String[] strs = ids.split(";");
			if (strs.length > 0) {
				String whereBlock = hqlInfo.getWhereBlock();
				String where = HQLUtil.buildLogicIN(
						"sysPraiseInfoPersonal.fdId", Arrays.asList(strs));
				whereBlock += " and " + where;
				hqlInfo.setWhereBlock(whereBlock);
			}
			idMap.remove(fdKey);
		}
		String selectBlock = "fdPerson.fdName," + fdNamePart + ".fdPraiseNum,"
				+ fdNamePart + ".fdPraisedNum," + fdNamePart + ".fdOpposeNum,"
				+ fdNamePart + ".fdOpposedNum," + fdNamePart + ".fdPayNum,"
				+ fdNamePart + ".fdRichPay," + fdNamePart + ".fdReceiveNum,"
				+ fdNamePart + ".fdRichGet";
		hqlInfo.setSelectBlock(selectBlock);
		List rtnList = getServiceImp(request).findList(hqlInfo);
		return rtnList;
	}

	private List<String> getcolumnList() {
		List<String> columnList = new ArrayList<String>();
		columnList.add(ResourceUtil
				.getString("sys-praise:sysPraiseInfo.calculate.person"));
		columnList.add(ResourceUtil
				.getString("sys-praise:sysPraiseInfoDetailBase.fdPraiseNum"));
		columnList.add(ResourceUtil
				.getString("sys-praise:sysPraiseInfoDetailBase.fdPraisedNum"));
		columnList.add(ResourceUtil
				.getString("sys-praise:sysPraiseInfoDetailBase.fdOpposeNum"));
		columnList.add(ResourceUtil
				.getString("sys-praise:sysPraiseInfoDetailBase.fdOpposedNum"));
		columnList.add(ResourceUtil
				.getString("sys-praise:sysPraiseInfoDetailBase.fdPayNum"));
		columnList.add(ResourceUtil
				.getString("sys-praise:sysPraiseInfoDetailBase.fdRichPay"));
		columnList.add(ResourceUtil
				.getString("sys-praise:sysPraiseInfoDetailBase.fdReceiveNum"));
		columnList.add(ResourceUtil
				.getString("sys-praise:sysPraiseInfoDetailBase.fdRichGet"));
		return columnList;
	}

	public ActionForward getRankInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject rtnArray = getRankInfoObj(request);

			request.setAttribute("lui-source", rtnArray);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-index", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	private JSONObject getRankInfoObj(HttpServletRequest request)
			throws Exception {

		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();

		String fdTimeType = request.getParameter("fdTimeType");
		String fdItem = null;
		if (StringUtil.isNull(fdTimeType)) {
			fdItem = "fdTotal";
		} else if (SysPraiseInfoCommonUtil.WEEK.equals(fdTimeType)) {
			fdItem = "fdWeek";
		} else if (SysPraiseInfoCommonUtil.MONTH.equals(fdTimeType)) {
			fdItem = "fdMonth";
		} else if (SysPraiseInfoCommonUtil.YEAR.equals(fdTimeType)) {
			fdItem = "fdYear";
		} else {
			fdItem = "fdTotal";
		}
		String fdSelect =
				fdItem + ".fdPraisedNum + " + fdItem + ".fdReceiveNum";
		String orderBy = fdSelect + " DESC";

		String rowSize = request.getParameter("rowsize");
		int rowsize = SysConfigParameters.getRowSize();
		if (StringUtil.isNotNull(rowSize)) {
			rowsize = Integer.parseInt(rowSize);
		}

		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock =
				"sysPraiseInfoPersonal.fdPerson.fdIsAvailable = :fdIsAvailable and sysPraiseInfoPersonal.fdPerson.fdIsBusiness = :fdIsBusiness";
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setParameter("fdIsBusiness", true);
		String fdDeptId = request.getParameter("deptId");
		if (StringUtil.isNotNull(fdDeptId)) {
			whereBlock +=
					" and sysPraiseInfoPersonal.fdPerson.fdHierarchyId like (:fdDeptId)";
			hqlInfo.setParameter("fdDeptId", "%" + fdDeptId + "%");
		}
		hqlInfo.setSelectBlock(
				"sysPraiseInfoPersonal.fdPerson.fdId,sysPraiseInfoPersonal.fdPerson,"
						+ fdSelect);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(orderBy);
		hqlInfo.setRowSize(rowsize);
		List<Object[]> list =
				getServiceImp(request).findPage(hqlInfo).getList();
		for (Object[] obj : list) {
			JSONObject item = new JSONObject();
			item.put("fdPersonId", obj[0]);
			item.put("fdPersonName", ((SysOrgElement)obj[1]).getFdName());
			item.put("fdPraisedSum", obj[2]);
			item.put("imgUrl", PersonInfoServiceGetter
					.getPersonHeadimageUrl(obj[0].toString(), "s"));

			// 记录日志
			// TODO
			if (UserOperHelper.allowLogOper("getRankInfo",
					getServiceImp(request).getModelName())) {
				UserOperContentHelper.putFind(obj[0].toString(),
						obj[1].toString(), SysOrgPerson.class.getName());
			}

			array.add(item);
		}
		object.put("rankInfo", array);
		return object;
	}
}
