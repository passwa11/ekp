<%@page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.modeling.main.forms.ModelingAppSimpleMainForm" %>
<%@ page import="com.landray.kmss.sys.modeling.base.forms.ModelingAppModelBaseForm" %>
<%@ page import="com.landray.kmss.sys.modeling.main.forms.ModelingAppModelMainBaseForm" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean" %>
<c:set var="__modelingMainForm" value="${requestScope[param.formBeanName]}"/>
<c:set var="formType" value="${requestScope[param.formType]}"/>
<script type="text/javascript">
    var mainForm = {};

    <%
           ModelingAppModelMainBaseForm mainBaseForm=(ModelingAppModelMainBaseForm)pageContext.getAttribute("__modelingMainForm");
          String docSubject =mainBaseForm.getDocSubject();
         pageContext.setAttribute("docSubject",StringUtil.lineEscape(docSubject));
    %>
    mainForm["docSubject"] = "<c:out value='${docSubject}'></c:out>"; //标题
    mainForm["fdModelName"] = '${__modelingMainForm.fdModelName}'; //表单名称
    mainForm["headerItemDatas"] = [
        {name: '所属表单', value: '${__modelingMainForm.fdModelName}'},
        {name: '创建时间', value: '${__modelingMainForm.docCreateTime}'}
    ];
</script>
<c:if test="${param.formType=='simple'}">

    <%-- 文档查看页公共头部   --%>
    <div data-dojo-type="mui/header/DocViewHeader"
         data-dojo-props='subject:window.mainForm.docSubject,
					 userId:"${__modelingMainForm.docCreatorId}",
					 userName:"${lfn:escapeJs(__modelingMainForm.docCreatorName)}",
					 itemDatas:window.mainForm.headerItemDatas'></div>
</c:if>

<c:if test="${param.formType == 'main'}">

    <%-- 文档查看页公共头部   --%>
    <div data-dojo-type="mui/header/DocViewHeader"
         data-dojo-props='subject:window.mainForm.docSubject,
					 userId:"${__modelingMainForm.docCreatorId}",
					 userName:"${lfn:escapeJs(__modelingMainForm.docCreatorName)}",
					 docStatus:window.mainForm.docStatus,
					 itemDatas:window.mainForm.headerItemDatas'></div>
</c:if>