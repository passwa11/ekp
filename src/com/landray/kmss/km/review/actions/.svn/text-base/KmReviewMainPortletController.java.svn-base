package com.landray.kmss.km.review.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.portal.cloud.dto.CellDataVO;
import com.landray.kmss.sys.portal.cloud.dto.ColumnDataVO;
import com.landray.kmss.sys.portal.cloud.dto.RowDataVO;
import com.landray.kmss.sys.portal.cloud.dto.TableDataVO;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.KmssMediaTypes;
import com.landray.kmss.web.RestResponse;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

/**
 * 门户数据源数据接口
 * 
 * @author chao
 *
 */
@Controller
@RequestMapping(value = "/data/km-review/kmReviewMainPortlet")
public class KmReviewMainPortletController {

	private IKmReviewMainService kmReviewMainService;

	public IKmReviewMainService getKmReviewMainService() {
		if (kmReviewMainService == null) {
			kmReviewMainService = (IKmReviewMainService) SpringBeanUtil
					.getBean("kmReviewMainService");
		}
		return kmReviewMainService;
	}


	@RequestMapping(value = "/listPortlet", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> listPortlet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		Map<String, ?> rtnMap = null;
		try {
			rtnMap = getKmReviewMainService().listPortlet(requestCtx);
		} catch (Exception e) {
			e.printStackTrace();
			// 错误处理
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
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

	/**
	 * 
	 * { columns:[{ property: string title: string align?: 'center' | 'left' |
	 * 'right' width?: string color?: string }], datas:[{ href : string cells:
	 * [{ col: string value: string href?: string color?: IColor icons?: Array<{
	 * type : string theme? : 'outline' | 'fill' style : object }> }] }] }
	 * 
	 * @param pageData
	 * @param params
	 * @return
	 */
	private TableDataVO getListData(Page pageData,
			RequestContext request) {
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
		col.setTitle(
				ResourceUtil.getString("km-review:kmReviewMain.docSubject"));
		columns.add(col);
		col = new ColumnDataVO(); // docCreateTime
		col.setProperty("docCreateTime");
		col.setTitle(
				ResourceUtil.getString("km-review:kmReviewMain.docCreateTime"));
		// col.setWidth("100px");
		columns.add(col);
		col = new ColumnDataVO();
		if ("unExecuted".equals(myFlow)) {
			col.setProperty("arrivalTime"); // arrivalTime
			col.setTitle(ResourceUtil.getString(
					"km-review:sysWfNode.processingNode.currentarrTime"));
			// col.setWidth("120px");
		} else {
			col.setProperty("docStatus"); // docStatus
			col.setTitle(
					ResourceUtil.getString("km-review:kmReviewMain.docStatus"));
			// col.setWidth("60px");
		}
		columns.add(col);
		if (StringUtil.isNull(owner)) {
			col = new ColumnDataVO(); // docCreator.fdName
			col.setProperty("docCreator.fdName");
			col.setTitle(ResourceUtil
					.getString("km-review:kmReviewMain.docCreatorName"));
			// col.setWidth("60px");
			columns.add(col);
		}
		col = new ColumnDataVO(); // handlerName
		col.setProperty("handlerName");
		col.setTitle(ResourceUtil.getString(
				"km-review:sysWfNode.processingNode.currentProcessor"));
		// col.setWidth("80px");
		columns.add(col);
		// 结束处理columns ===========
		// 开始处理datas ==============
		List<KmReviewMain> topics = pageData.getList();
		if (topics != null && !topics.isEmpty()) {
			RowDataVO rowData = null;
			List<CellDataVO> cells = null;
			CellDataVO cell = null;
			for (KmReviewMain topic : topics) {
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
						"/km/review/km_review_main/kmReviewMain.do?method=view&fdId="
								+ topic.getFdId());
				cells.add(cell);

				cell = new CellDataVO();
				cell.setCol("docCreateTime"); // docCreateTime
				cell.setValue(
						DateUtil.convertDateToString(topic.getDocCreateTime(),
								"date", request.getLocale()));
				cells.add(cell);

				cell = new CellDataVO();
				if ("unExecuted".equals(myFlow)) {
					cell.setCol("arrivalTime"); // arrivalTime
					String arrivalTime = ListDataUtil.getLbpmName(
							topic.getFdId(), "arrivalTime", false, null);
					if (StringUtil.isNotNull(arrivalTime)) {
						cell.setValue(arrivalTime);
					} else {
						cell.setValue("<无>");
					}
				} else {
					cell.setCol("docStatus"); // docStatus
					cell.setValue(ListDataUtil
							.getDocStatusString(topic.getDocStatus()));
					cell.setColor(ListDataUtil
							.getDocStatusColor(topic.getDocStatus()));
				}
				cells.add(cell);
				if (StringUtil.isNull(owner)) {
					cell = new CellDataVO();
					cell.setCol("docCreator.fdName"); // docCreator.fdName
					cell.setValue(topic.getDocCreator().getFdName());
					cells.add(cell);
				}
				cell = new CellDataVO();
				cell.setCol("handlerName"); // handlerName
				cell.setValue(ListDataUtil.getLbpmName(topic.getFdId(), "handlerName", false, null));
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
}
