package com.landray.kmss.km.imeeting.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.km.imeeting.model.KmImeetingSummary;
import com.landray.kmss.km.imeeting.service.IKmImeetingSummaryService;
import com.landray.kmss.sys.portal.cloud.dto.CellDataVO;
import com.landray.kmss.sys.portal.cloud.dto.ColumnDataVO;
import com.landray.kmss.sys.portal.cloud.dto.RowDataVO;
import com.landray.kmss.sys.portal.cloud.dto.TableDataVO;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.KmssMediaTypes;
import com.landray.kmss.web.RestResponse;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

/**
 * 门户数据源数据接口
 * 
 * @author ASUS
 *
 */
@Controller
@RequestMapping(value = "/data/km-imeeting/kmImeetingSummaryPortlet")
public class KmImeetingSummaryPortletController {

	private IKmImeetingSummaryService kmImeetingSummaryService;

	public void setKmImeetingSummaryService(
			IKmImeetingSummaryService kmImeetingSummaryService) {
		this.kmImeetingSummaryService = kmImeetingSummaryService;
	}

	@RequestMapping(value = "/listPortlet", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> listPortlet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		Map<String, ?> rtnMap = null;
		try {
			rtnMap = kmImeetingSummaryService.listPortlet(requestCtx);
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
			return RestResponse.ok(getListDataJson(page, requestCtx));
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
	private TableDataVO getListDataJson(Page pageData,
			RequestContext request) {
		TableDataVO table = new TableDataVO();
		List<RowDataVO> datas = new ArrayList<>();
		List<ColumnDataVO> columns = new ArrayList<>();
		// 开始处理columns =========
		ColumnDataVO col = new ColumnDataVO(); // fdId
		col.setProperty("fdId");
		col.setRenderType("hidden");
		columns.add(col);
		col = new ColumnDataVO();
		col.setProperty("fdName"); // fdName
		col.setTitle(
				ResourceUtil.getString("km-imeeting:kmImeetingSummary.fdName"));
		columns.add(col);
		col = new ColumnDataVO(); // docCreator.fdName
		col.setProperty("docCreator.fdName");
		col.setTitle(
				ResourceUtil
						.getString("km-imeeting:kmImeetingSummary.docCreator"));
		// col.setWidth("80px");
		columns.add(col);
		col = new ColumnDataVO(); // fdDate
		col.setProperty("fdDate");
		col.setTitle(ResourceUtil
				.getString("km-imeeting:kmImeetingMain.fdDate"));
		// col.setWidth("135px");
		columns.add(col);
		col = new ColumnDataVO(); // fdTemplate.fdName
		col.setProperty("fdTemplate.fdName");
		col.setTitle(ResourceUtil
				.getString("km-imeeting:kmImeetingSummary.fdTemplate"));
		// col.setWidth("150px");
		columns.add(col);
		// 结束处理columns ===========
		// 开始处理datas ==============
		List<KmImeetingSummary> topics = pageData.getList();
		if (topics != null && !topics.isEmpty()) {
			RowDataVO rowData = null;
			List<CellDataVO> cells = null;
			CellDataVO cell = null;
			for (KmImeetingSummary topic : topics) {
				rowData = new RowDataVO();
				cells = new ArrayList<>();

				cell = new CellDataVO(); // fdId
				cell.setCol("fdId");
				cell.setValue(topic.getFdId());
				cells.add(cell);

				cell = new CellDataVO();
				cell.setCol("fdName"); // fdName
				cell.setValue(topic.getDocSubject());
				cell.setHref(
						"/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view&fdId="
								+ topic.getFdId());
				cells.add(cell);

				cell = new CellDataVO();
				cell.setCol("docCreator.fdName"); // docCreator.fdName
				cell.setValue(topic.getDocCreator().getFdName());
				cells.add(cell);

				cell = new CellDataVO();
				cell.setCol("fdDate"); // fdDate
				cell.setValue(
						DateUtil.convertDateToString(topic.getFdHoldDate(),
								"datetime", ResourceUtil.getLocaleByUser())
								+ " ~ "
								+ DateUtil.convertDateToString(
										topic.getFdFinishDate(), "datetime",
										ResourceUtil.getLocaleByUser()));
				cells.add(cell);

				cell = new CellDataVO();
				cell.setCol("fdTemplate.fdName"); // fdTemplate.fdName
				cell.setValue(topic.getFdTemplate() != null
						? topic.getFdTemplate().getFdName() : null);
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
