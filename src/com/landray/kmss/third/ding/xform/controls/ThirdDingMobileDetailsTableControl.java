package com.landray.kmss.third.ding.xform.controls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.nodes.TagNode;

import com.landray.kmss.sys.xform.base.service.parse.ParseAnnotation;
import com.landray.kmss.sys.xform.base.service.parse.ParseContext;
import com.landray.kmss.sys.xform.base.service.parse.ParseElement;
import com.landray.kmss.sys.xform.base.service.parse.ParseExecutor;
import com.landray.kmss.sys.xform.base.service.parse.ParseHandler;
import com.landray.kmss.sys.xform.mobile.controls.dt.DetailsTableParseHelper;
import com.landray.kmss.third.ding.xform.controls.parse.DingParseUtil;
import com.landray.kmss.third.ding.xform.controls.tablecontext.Cell;
import com.landray.kmss.third.ding.xform.controls.tablecontext.TemplateTr;
import com.landray.kmss.third.ding.xform.controls.tablecontext.ThirdDingMobileDetailsTableContext;

@ParseAnnotation(acceptType = "dingDetailsTable")
public class ThirdDingMobileDetailsTableControl implements ParseHandler {

	@Override
    public boolean parse(final ParseElement elem, final ParseContext context)
			throws Exception {
		buildMobileTable(elem, context);
		return true;
	}

	private void buildMobileTable(ParseElement domNodeElem,
			ParseContext context) throws Exception {
		StringBuilder jsp = context.getJsp();
		TagNode domNodeTag = domNodeElem.getTagNode();
		ThirdDingMobileDetailsTableContext tableCtx = new ThirdDingMobileDetailsTableContext();
		// 1.获取基础属性
		tableCtx.setTitle(domNodeTag.getAttribute("label"));
		tableCtx.setControlId(domNodeTag.getAttribute("id"));
		tableCtx.setTableId("TABLE_DL_" + domNodeTag.getAttribute("id"));

		// 2.循环模板行
		for (ParseElement parseElement : domNodeElem) {
			Node node = parseElement.getNode();
			if (DingParseUtil.isDetailstableTemplateRow(node)) {
				TemplateTr templateTr = parseTemplateRow(parseElement, context,
						tableCtx);
				tableCtx.getTemplateRows().add(templateTr);
			}
		}
		// 3.遍历行，每两个单元格设置为一行
		for (TemplateTr templateTr : tableCtx.getTemplateRows()) {
			tableCtx.getMobileRows().addAll(templateTr.splitToMobileTr());
		}
		// 4.通过freemarker得到移动端HTML
		// freemarker构建上下文
		Map<String, Object> detailContext = new HashMap<String, Object>();
		detailContext.put("tableCtx", tableCtx);
		jsp.append(DetailsTableParseHelper.build(detailContext,
				"third/ding/xform/controls/dingDetailsTable.tmpl"));
	}

	/**
	 * 解析模板行
	 * 
	 * @param parseElement
	 * @param context
	 * @param tableCtx
	 * @return
	 * @throws Exception
	 */
	private TemplateTr parseTemplateRow(ParseElement parseElement,
			ParseContext context, ThirdDingMobileDetailsTableContext tableCtx)
			throws Exception {
		StringBuilder cellJsp = new StringBuilder();
		String controlId = tableCtx.getControlId();
		// 临时上下文
		ParseContext templateContext = new ParseContext(context.getLexer(),
				cellJsp, context.getHandlers());
		templateContext.addParam("dtableId", controlId);
		ParseExecutor executor = new ParseExecutor(templateContext);
		TemplateTr templateTr = new TemplateTr();
		Cell cell = null;
		for (ParseElement elem : parseElement) {
			if (DetailsTableParseHelper.isTdBeginTag(elem)) {
				// 清空
				cellJsp.setLength(0);
				cell = new Cell();
			}
			if (elem.isFormDesign()) {
				executor.execute(elem.newParseIterator());
			}
			if (DetailsTableParseHelper.isTdEndTag(elem)) {
				// 添加内容
				cell.setHtml(cellJsp.toString());
				templateTr.getCells().add(cell);
			}
		}
		fillDetailsTableIndex(templateTr.getCells(), controlId);
		return templateTr;
	}

	/**
	 * 补全控件的明细表ID和索引
	 * 
	 * @param cellHtml
	 * @param controlId
	 * @return
	 */
	private void fillDetailsTableIndex(List<Cell> cells, String controlId) {
		String newContentName = String
				.format(DetailsTableParseHelper.contentFieldName, controlId);
		String newTemplateName = String
				.format(DetailsTableParseHelper.templateFieldName, controlId);

		String newModelRightContentRefIdName = String
				.format(DetailsTableParseHelper.modelRightContentRefId,
						controlId);
		String newModelRightTemplateRefIdName = String
				.format(DetailsTableParseHelper.modelRightTemplateRefId,
						controlId);

		for (Cell cell : cells) {
			String cellHtml = cell.getHtml();
			// 内容单元格的索引替换
			String contentControlStr = DetailsTableParseHelper.namePattern
					.matcher(cellHtml)
					.replaceAll(newContentName);
			contentControlStr = DetailsTableParseHelper.valuePattern
					.matcher(contentControlStr)
					.replaceAll(DetailsTableParseHelper.contentValueName);
			contentControlStr = DetailsTableParseHelper.indexNamePattern
					.matcher(contentControlStr)
					.replaceAll("\\$\\{vstatus.index\\}");

			contentControlStr = DetailsTableParseHelper.modelRightRefIdPattern
					.matcher(contentControlStr)
					.replaceAll(newModelRightContentRefIdName);
			cell.setContentHtml(contentControlStr);

			// 模板单元格的索引替换
			String templateControlStr = DetailsTableParseHelper.namePattern
					.matcher(cellHtml)
					.replaceAll(newTemplateName);
			templateControlStr = DetailsTableParseHelper.modelRightRefIdPattern
					.matcher(templateControlStr)
					.replaceAll(newModelRightTemplateRefIdName);
			cell.setTemplateHtml(templateControlStr);
		}
	}
}
