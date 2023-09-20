<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.kms.category.model.KmsCategoryConfig" %>
<c:set var="kmsCategoryEnabled" value="false"></c:set>
<%
    KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
    String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
    if ("true".equals(kmsCategoryEnabled)) {
%>
<c:set var="kmsCategoryEnabled" value="true"></c:set>
<%
    }
%>

<link rel="stylesheet" type="text/css"
      href="${LUI_ContextPath}/kms/knowledge/kms_knowledge_portlet_ui/index/style/portlet_index_operation.css?s_cache=${LUI_Cache}"/>

<div id="kms_knowledge_porlet_index_operation_content_box">
    <div class="content_title">
        <span class="text">${lfn:message('kms-knowledge:kmsKnowledge.workspace')}</span>
    </div>
    <div id="kn_opt">
        <ul>
            <c:if test="${(JsParam.type eq '1,2,3') or (JsParam.type eq '1')}">
                <li class="opt_item" onclick="addDoc('1,3')">
                    <div class="doc"></div>
                    <div class="word">${lfn:message('kms-knowledge:kmsKnowledge.workspace.newdoc')}</div>
                </li>
            </c:if>

            <kmss:ifModuleExist path="/kms/wiki/">
                <c:if test="${(JsParam.type eq '1,2,3') or (JsParam.type eq '2')}">
                    <li class="opt_item" onclick="addDoc('2,3')">
                        <div class="wiki"></div>
                        <div class="word">${lfn:message('kms-knowledge:kmsKnowledge.workspace.newwiki')}</div>
                    </li>
                </c:if>
            </kmss:ifModuleExist>
        </ul>
    </div>
</div>

<script type="text/javascript">
    var cateId = "";
    var kmsCategoryEnabled = <%=kmsCategoryEnabled%>;

    seajs.use("kms/knowledge/kms_knowledge_ui/js/create", function (create) {
        window.addDoc = function (type) {
            create.addDoc(cateId, type, kmsCategoryEnabled);
        }
    });
</script>

<script type="text/javascript">
    domain.autoResize();
</script>
