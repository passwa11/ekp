package com.landray.kmss.km.forum.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.sys.portal.cloud.dto.CellDataVO;
import com.landray.kmss.sys.portal.cloud.dto.ColumnDataVO;
import com.landray.kmss.sys.portal.cloud.dto.RowDataVO;
import com.landray.kmss.sys.portal.cloud.dto.TableDataVO;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.KmssMediaTypes;
import com.landray.kmss.web.RestResponse;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 门户数据源数据接口
 * 
 * @author chao
 *
 */
@Controller
@RequestMapping(value = "/data/km-forum/kmForumPortlet")
public class KmForumPortletController {

	private IKmForumTopicService kmForumTopicService;

	public IKmForumTopicService getKmForumTopicService() {
		if (kmForumTopicService == null) {
            kmForumTopicService = (IKmForumTopicService) SpringBeanUtil
                    .getBean("kmForumTopicService");
        }
		return kmForumTopicService;
	}


	@RequestMapping(value = "/getTopicList", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> getTopicList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		Map<String, ?> rtnMap = null;
		try {
			rtnMap = getKmForumTopicService().getTopicList(requestCtx);
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
			return RestResponse.ok(getTopicListData(page, requestCtx));
		}
	}

	@RequestMapping(value = "/getRankList", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> getRankList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		Page page = null;
		JSONArray datas = new JSONArray();
		try {
			String type = request.getParameter("type");
			page = getKmForumTopicService().getRankList(requestCtx);
			List<KmForumScore> topics = page.getList();
			for (KmForumScore topic : topics) {
				JSONObject data = new JSONObject();
				// 主题
				data.put("text", topic.getPerson().getFdName());
				data.put("href",
						"/km/forum/indexCriteria.jsp?myTopic=create&mode=default#cri.q=fdPoster:"
								+ topic.getPerson().getFdId());
				data.put("target", "_blank");
				if ("score".equals(type)) {
					data.put("count", topic.getFdScore());
				} else if ("postCount".equals(type)) {
					data.put("count", topic.getFdPostCount());
				}
				data.put("unit", ""); // 单位
				data.put("image", ListDataUtil.formatUrl(
						"/sys/person/image.jsp?personId="
								+ topic.getPerson().getFdId()));
				datas.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 错误处理
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(datas);
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
	private TableDataVO getTopicListData(Page pageData,
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
		col.setProperty("docSubject");
		col.setTitle(
				ResourceUtil.getString("km-forum:kmForumTopic.docSubject"));
		col.setAlign("left");
		columns.add(col);
		col = new ColumnDataVO(); // fdReplyCount
		col.setProperty("fdReplyCount");
		col.setTitle(
				ResourceUtil.getString(
						"km-forum:portlet.kmForum.fdPostCount.portlet"));
		// col.setWidth("80px");
		columns.add(col);
		col = new ColumnDataVO();
		col.setProperty("fdLastPostTime"); // fdLastPostTime
		col.setTitle(ResourceUtil.getString(
				"km-forum:kmForumTopic.list.fdLastPostTime"));
		// col.setWidth("120px");
		columns.add(col);
		// 结束处理columns ===========
		// 开始处理datas ==============
		List<KmForumTopic> topics = pageData.getList();
		if (topics != null && !topics.isEmpty()) {
			RowDataVO rowData = null;
			List<CellDataVO> cells = null;
			CellDataVO cell = null;
			for (KmForumTopic topic : topics) {
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
						"/km/forum/km_forum/kmForumPost.do?method=view&fdTopicId="
								+ topic.getFdId());
				cells.add(cell);

				cell = new CellDataVO();
				cell.setCol("fdReplyCount"); // fdReplyCount
				cell.setValue(topic.getFdReplyCount());
				cell.setColor("green");
				cells.add(cell);

				cell = new CellDataVO();
				cell.setCol("fdLastPostTime"); // fdLastPostTime
				cell.setValue(
						DateUtil.convertDateToString(topic.getFdLastPostTime(),
								"date", request.getLocale()));
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
	private TableDataVO getRankListData(Page pageData,
			RequestContext request) {
		String type = request.getParameter("type");
		TableDataVO table = new TableDataVO();
		List<RowDataVO> datas = new ArrayList<>();
		List<ColumnDataVO> columns = new ArrayList<>();
		// 开始处理columns =========
		ColumnDataVO col = new ColumnDataVO(); // fdId
		col.setProperty("fdId");
		columns.add(col);
		col = new ColumnDataVO();
		col.setProperty("person");
		col.setTitle(
				ResourceUtil
						.getString("km-forum:kmForumConfig.canModifyNickname"));
		col.setAlign("center");
		columns.add(col);

		if ("score".equals(type)) {
			col = new ColumnDataVO();
			col.setProperty("fdScore");
			col.setTitle(
					ResourceUtil.getString("km-forum:kmForumScore.fdScore"));
			columns.add(col);
		} else if ("postCount".equals(type)) {
			col = new ColumnDataVO();
			col.setProperty("fdPostCount");
			col.setTitle(
					ResourceUtil.getString(
							"km-forum:kmForumScore.fdPostCount.portlet"));
			columns.add(col);
		}
		// 结束处理columns ===========
		// 开始处理datas ==============
		List<KmForumScore> topics = pageData.getList();
		if (topics != null && !topics.isEmpty()) {
			RowDataVO rowData = null;
			List<CellDataVO> cells = null;
			CellDataVO cell = null;
			for (KmForumScore topic : topics) {
				rowData = new RowDataVO();
				cells = new ArrayList<>();
				cell = new CellDataVO(); // fdId
				cell.setCol("fdId");
				cell.setValue(topic.getFdId());
				cells.add(cell);
				cell = new CellDataVO();
				cell.setCol("person"); // person
				cell.setValue(topic.getPerson().getFdName());
				cells.add(cell);

				if ("score".equals(type)) {
					cell = new CellDataVO();
					cell.setCol("fdScore"); // fdScore
					cell.setValue(topic.getFdScore());
					cell.setColor("green");
					cells.add(cell);
				} else if ("postCount".equals(type)) {
					cell = new CellDataVO();
					cell.setCol("fdPostCount"); // fdPostCount
					cell.setValue(topic.getFdPostCount());
					cell.setColor("green");
					cells.add(cell);
				}
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
