package com.landray.kmss.km.smissive.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.smissive.model.KmSmissiveMain;
import com.landray.kmss.km.smissive.service.IKmSmissiveMainService;
import com.landray.kmss.km.smissive.service.IKmSmissiveTemplateService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.portal.cloud.dto.CellDataVO;
import com.landray.kmss.sys.portal.cloud.dto.ColumnDataVO;
import com.landray.kmss.sys.portal.cloud.dto.RowDataVO;
import com.landray.kmss.sys.portal.cloud.dto.TableDataVO;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.sys.portal.util.PortletTimeUtil;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.KmssMediaTypes;
import com.landray.kmss.web.RestResponse;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <P>数据源接口</P>
 * @author 孙佳
 * @version 1.0 2019年5月16日
 */
@Controller
@RequestMapping(value = "/data/km-smissive/kmSmissiveMainPortlet")
public class KmSmissiveMainPortletController {

	protected IKmSmissiveMainService kmSmissiveMainService;

	protected IKmSmissiveMainService getServiceImp() {
		if (kmSmissiveMainService == null) {
            kmSmissiveMainService = (IKmSmissiveMainService) SpringBeanUtil.getBean("kmSmissiveMainService");
        }
		return kmSmissiveMainService;
	}

	// 获取类别
	protected IKmSmissiveTemplateService kmSmissiveTemplateService;

	protected IKmSmissiveTemplateService getTreeServiceImp() {
		if (kmSmissiveTemplateService == null) {
            kmSmissiveTemplateService = (IKmSmissiveTemplateService) SpringBeanUtil.getBean("kmSmissiveTemplateService");
        }
		return kmSmissiveTemplateService;
	}

	@RequestMapping(value = "/listPortlet", produces = { KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> listPortlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		Map<String, ?> rtnMap = null;
		try {
			rtnMap = listPortlet(requestCtx);
		} catch (Exception e) {
			// 错误处理
			e.printStackTrace();
			return RestResponse.error(RestResponse.ERROR_CODE_500, e.getMessage());
		}
		JSONArray datas = (JSONArray) rtnMap.get("datas"); // 列表形式使用
		Page page = (Page) rtnMap.get("page"); // 简单列表使用
		String dataview = requestCtx.getParameter("dataview");
		if ("classic".equals(dataview)) {// 视图展现方式:classic(简单列表)
			return RestResponse.ok(datas);
		} else {// 视图展现方式:listtable(列表)
			return RestResponse.ok(getListData(page, requestCtx));
		}

	}

	private Object getListData(Page pageData, RequestContext request) {
		String myFlow = request.getParameter("myFlow");
		String owner = request.getParameter("owner");
		TableDataVO table = new TableDataVO();
		List<RowDataVO> datas = new ArrayList<>();
		List<ColumnDataVO> columns = new ArrayList<>();
		// 开始处理columns =========
		ColumnDataVO col = new ColumnDataVO(); // fdId
		col.setProperty("fdId");
		col.setRenderType("hidden");
		columns.add(col);

		col = new ColumnDataVO();
		col.setProperty("docSubject");
		col.setTitle(ResourceUtil.getString("km-smissive:kmSmissiveMain.docSubject"));
		// col.setWidth("100px");
		columns.add(col);

		col = new ColumnDataVO(); // docCreateTime
		col.setProperty("docCreateTime");
		col.setTitle(ResourceUtil.getString("km-smissive:kmSmissiveMain.docCreateTime"));
		// col.setWidth("100px");
		columns.add(col);

		col = new ColumnDataVO();
		col.setProperty("docCreator.fdName");
		col.setTitle(ResourceUtil.getString("km-smissive:kmSmissiveMain.docCreatorId"));
		// col.setWidth("100px");
		columns.add(col);

		col = new ColumnDataVO();
		col.setProperty("catename");
		col.setTitle(ResourceUtil.getString("km-smissive:kmSmissiveMain.fdTemplateId"));
		// col.setWidth("100px");
		columns.add(col);

		// 结束处理columns ===========
		// 开始处理datas ==============
		List<KmSmissiveMain> topics = pageData.getList();
		if (topics != null && !topics.isEmpty()) {
			RowDataVO rowData = null;
			List<CellDataVO> cells = null;
			CellDataVO cell = null;
			for (KmSmissiveMain topic : topics) {
				rowData = new RowDataVO();
				cells = new ArrayList<>();
				cell = new CellDataVO(); // fdId
				cell.setCol("fdId");
				cell.setValue(topic.getFdId());
				cells.add(cell);
				cell = new CellDataVO();
				cell.setCol("docSubject"); // docSubject
				cell.setValue(topic.getDocSubject());
				cell.setHref(
						"/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view&fdId="
								+ topic.getFdId());
				cells.add(cell);

				cell = new CellDataVO();
				cell.setCol("docCreateTime"); // docCreateTime
				cell.setValue(
						DateUtil.convertDateToString(topic.getDocCreateTime(),
								"date", request.getLocale()));
				cells.add(cell);

				cell = new CellDataVO();
				cell = new CellDataVO();
				cell.setCol("docCreator.fdName"); // docCreator.fdName
				cell.setValue(topic.getDocCreator().getFdName());
				cells.add(cell);

				cell = new CellDataVO();
				cell.setCol("catename");
				cell.setValue(topic.getFdTemplate().getFdName());
				cells.add(cell);

				rowData.setCells(cells);
				datas.add(rowData);
			}
		}
		// 结束处理datas ==============
		table.setColumns(columns);
		table.setData(datas);
		return table;
	}

	private Map<String, ?> listPortlet(RequestContext request) throws Exception {
		try {
			Map<String, Object> rtnMap = new HashMap<>();
			JSONArray datas = new JSONArray();// 列表形式使用
			Page page = Page.getEmptyPage();// 简单列表使用

			String myFlow = request.getParameter("myFlow");
			HQLInfo hqlInfo = new HQLInfo();
			String param = request.getParameter("rowsize");
			int rowsize = 6;
			if (!StringUtil.isNull(param)) {
                rowsize = Integer.parseInt(param);
            }
			if (StringUtil.isNotNull(myFlow)) {
				getMyFlowDate(myFlow, hqlInfo);
			}
			//时间范围参数
			String scope = request.getParameter("scope");
			if (StringUtil.isNotNull(scope) && !"no".equals(scope)) {
				String block = hqlInfo.getWhereBlock();
				hqlInfo.setWhereBlock(
						StringUtil.linkString(block, " and ", "kmSmissiveMain.docCreateTime > :fdStartTime"));
				hqlInfo.setParameter("fdStartTime", PortletTimeUtil.getDateByScope(scope));
			}
			hqlInfo.setOrderBy("kmSmissiveMain.docCreateTime desc");
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setGetCount(false);
			page = getServiceImp().findPage(hqlInfo);
			if (UserOperHelper.allowLogOper("listPortlet", getServiceImp().getModelName())) {
				if (!ArrayUtil.isEmpty(page.getList()) && page.getList().get(0) instanceof IBaseModel) {
					UserOperContentHelper.putFinds(page.getList());
				}
			}
			List<KmSmissiveMain> rtnList = page.getList();
			for (int i = 0; i < rtnList.size(); i++) {
				KmSmissiveMain kmSmissiveMain = (KmSmissiveMain) rtnList.get(i);
				JSONObject data = new JSONObject();
				// 主题
				data.put("text", kmSmissiveMain.getDocSubject());
				if (request.isCloud()) {
					data.put("creator", ListDataUtil
							.buildCreator(kmSmissiveMain.getDocCreator()));
					data.put("created", kmSmissiveMain.getDocCreateTime().getTime());

					// List<IconDataVO> icons = new ArrayList<>(1);
					// IconDataVO icon = new IconDataVO();
					// icon.setName("tree-navigation");
					// icons.add(icon);
					// data.put("icons", icons);

				} else {
					// 创建人
					data.put("creator", kmSmissiveMain.getDocCreator().getFdName());
					// 创建时间
					data.put("created",
							DateUtil.convertDateToString(kmSmissiveMain.getDocCreateTime(), DateUtil.TYPE_DATE, null));
				}
				data.put("catename", kmSmissiveMain.getFdTemplate().getFdName());
				data.put("text", kmSmissiveMain.getDocSubject());
				data.put("created", DateUtil.convertDateToString(kmSmissiveMain.getDocCreateTime(), DateUtil.TYPE_DATE,
						request.getLocale()));
				StringBuffer sb = new StringBuffer();
				sb.append("/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view");
				sb.append("&fdId=" + kmSmissiveMain.getFdId());
				data.put("href", sb.toString());
				data.put("id", kmSmissiveMain.getFdId());
				datas.add(data);
			}
			rtnMap.put("datas", datas);
			rtnMap.put("page", page);
			return rtnMap;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void getMyFlowDate(String myFlow, HQLInfo hqlInfo) throws Exception {
		String eventType = "";
		if ("executed".equals(myFlow)) {
			eventType = ResourceUtil.getString("km-smissive:smissive.approved.my");
			SysFlowUtil.buildLimitBlockForMyApproved("kmSmissiveMain", hqlInfo);
		} else if ("unExecuted".equals(myFlow)) {
			eventType = ResourceUtil.getString("km-smissive:smissive.approval.my");
			SysFlowUtil.buildLimitBlockForMyApproval("kmSmissiveMain", hqlInfo);
		} else if ("myCreate".equals(myFlow)) {
			eventType = ResourceUtil.getString("km-smissive:smissive.create.my");
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ", " kmSmissiveMain.docCreator.fdId=:creatorId");
			hqlInfo.setParameter("creatorId", UserUtil.getUser().getFdId());
			hqlInfo.setWhereBlock(whereBlock);
		}
		if (UserOperHelper.allowLogOper("listPortlet", getServiceImp().getModelName())) {
			UserOperHelper.setEventType(eventType);
		}
	}

}
