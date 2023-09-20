<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
    String mainModelName = request.getParameter("mainModelName");
    mainModelName = org.apache.commons.lang.StringEscapeUtils.escapeHtml(mainModelName);

    SysDictModel dict = SysDataDict.getInstance().getModel(mainModelName);
    String url = dict.getUrl();
    String addUrl = url.substring(0, url.indexOf(".do"))
            + ".do";
    addUrl += "?method=add&fdTemplateId=!{id}&.fdTemplate=!{id}&i.docTemplate=!{id}";
    if (!addUrl.startsWith("/")) {
        addUrl += "/";
    }
    request.setAttribute("addUrl", addUrl);
%>
<script>
    Com_IncludeFile("recently_use.css", "${LUI_ContextPath}/sys/lbpmperson/resource/css/", "css", true);
    Com_IncludeFile("usualCate_ui.js", "${LUI_ContextPath}/sys/lbpmperson/resource/js/", "js", true);
    window.usualCate_modelName = "${JsParam.modelName}";
</script>
<div id="usualCateDiv">
    <div data-lui-type="${LUI_ContextPath}/sys/ui/js/summary/summaryDataView!SimpleSummaryDataView">
        <ui:source type="AjaxJson">
            {"url":"/sys/lbpmperson/SysLbpmPersonCreate.do?method=listUsual&mainModelName=${JsParam.mainModelName}&rowsize=10"}
        </ui:source>
        <ui:render type="Template">
            <c:import url="/sys/lbpmperson/import/tmpl/recently_use.jsp" charEncoding="UTF-8">
            </c:import>
        </ui:render>
        <script type="text/config">
            {
                id:"usualCate",
                button:{
                    className:"lui_summary_item_btn_star",
                    click:window.usualCateAddOrDelToFavorite
                }
            }
        </script>
    </div>
</div>
