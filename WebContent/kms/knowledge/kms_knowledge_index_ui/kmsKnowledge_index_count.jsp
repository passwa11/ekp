<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div id="kms_knowledge_index_count_content_box">
    <div class="count_title">
        <div class="count_title_text">${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.statistical')}</div>
    </div>
    <div class="count_chart">
        <ui:chart height="524px" width="100%" id="kn_count">
            <ui:source type="AjaxJson">
                {"url":"/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getIndexCountPieInfo&type=1,2,3"}
            </ui:source>
        </ui:chart>
    </div>
    <div class="chart_title">
        <div class="chart_title_doc">
            <div class="txt">${lfn:message('kms-knowledge:kmsKnowledge.index.count.doc')}</div>
            <div class="num">${data.docCount}</div>
        </div>
        <kmss:ifModuleExist path="/kms/wiki/">
            <style type="text/css">
                #kms_knowledge_index_count_content_box .chart_title .chart_title_doc{float: left;padding-right: 80px;}
            </style>
            <div class="chart_title_wiki">
                <div class="txt">${lfn:message('kms-knowledge:kmsKnowledge.index.count.wiki')}</div>
                <div class="num">${data.wikiCount}</div>
            </div>
        </kmss:ifModuleExist>
    </div>
</div>