<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>

<script type="text/javascript">
    seajs.use("kms/knowledge/kms_knowledge_portlet_ui/index/style/portlet_index_count.css");
</script>

<div id="kms_knowledge_portlet_index_count_content_box">
    <div class="count_title">
        <div class="count_title_text">${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.statistical')}</div>
    </div>
    <div class="count_chart">
        <ui:chart height="524px" width="100%" id="kn_count">
            <ui:source type="AjaxJson">
                {"url":"/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getIndexCountPieInfo&type=${JsParam.type}"}
            </ui:source>
        </ui:chart>
    </div>
    <div class="chart_title">
        <kmss:ifModuleExist path="/kms/wiki/">
            <c:if test="${(JsParam.type eq '1,2,3') or (JsParam.type eq '1')}">
                <div class="chart_title_doc" style="${(JsParam.type eq '1') ?'float:unset;':''}">
                    <div class="txt">${lfn:message('kms-knowledge:kmsKnowledge.index.count.doc')}</div>
                    <div class="num">${data.docCount}</div>
                </div>
            </c:if>
        </kmss:ifModuleExist>
        <c:if test="${(JsParam.type eq '1,2,3') or (JsParam.type eq '2')}">
            <div class="chart_title_wiki" style="${(JsParam.type eq '2') ?'float:unset;padding-right:unset;':''}">
                <div class="txt">${lfn:message('kms-knowledge:kmsKnowledge.index.count.wiki')}</div>
                <div class="num">${data.wikiCount}</div>
            </div>
        </c:if>
    </div>
</div>

<script type="text/javascript">
    // #145383 在饼状图的时候需要重写这个方法
    // 否则高度获取会错误
    domain.getBodySize = function () {
        var chs = document.body.childNodes;
        var bh = 0;
        var bw = 0;
        for (var i = 0; i < chs.length; i++) {
            var tbh = chs[i].offsetTop + chs[i].offsetHeight;
            var tbw = chs[i].offsetLeft + chs[i].offsetWidth;
            if (tbh > bh) {
                bh = tbh;
            }
            if (tbw > bw) {
                bw = tbw;
            }
        }
        return {"width": bw, "height": bh};
    };

    domain.autoResize();
</script>