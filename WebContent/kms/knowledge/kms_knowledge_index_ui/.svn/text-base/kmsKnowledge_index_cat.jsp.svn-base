<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div id="kms_knowledge_index_cat_content_box">
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
                {"url":"/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getIndexCatPieInfo&rowsize=10"}
            </ui:source>
            <ui:event event="load">
            </ui:event>
        </ui:chart>
    </div>
</div>