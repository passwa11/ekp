<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
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

<div id="kms_knowledge_index_operation_content_box">
    <div class="content_title">
        <span class="text">${lfn:message('kms-knowledge:kmsKnowledge.workspace')}</span>
    </div>
    <div id="kn_opt">
        <ul>
            <li class="opt_item" onclick="addDoc('1,3')">
                <div class="doc"></div>
                <div class="word">${lfn:message('kms-knowledge:kmsKnowledge.workspace.newdoc')}</div>
            </li>
            <kmss:ifModuleExist path="/kms/wiki/">
                <li class="opt_item" onclick="addDoc('2,3')">
                    <div class="wiki"></div>
                    <div class="word">${lfn:message('kms-knowledge:kmsKnowledge.workspace.newwiki')}</div>
                </li>
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