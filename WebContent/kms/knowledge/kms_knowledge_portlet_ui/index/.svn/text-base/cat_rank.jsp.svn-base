<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>

<script type="text/javascript">
    seajs.use("kms/knowledge/kms_knowledge_portlet_ui/index/style/portlet_index_cat_rank.css");
</script>

<div id="kms_knowledge_portlet_index_cat_content_box">
    <div class="cat_rank_title">
        <div class="cat_rank_title_text">${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.rank.title.long')}</div>
        <div class="cat_rank_title_icon">
            <i class="top_icon">&nbsp;&nbsp;&nbsp;&nbsp;</i>
            TOP 10
        </div>
    </div>
    <div class="cat_rank_chart">
        <ui:chart height="234px" width="100%" id="kn_cat_chart">
            <ui:source type="AjaxJson">
                {"url":"/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getIndexCatPieInfo&rowsize=${JsParam.rowsize}"}
            </ui:source>
            <ui:event event="load">
            </ui:event>
        </ui:chart>
    </div>
</div>

<script type="text/javascript">
    domain.autoResize();
</script>