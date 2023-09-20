<%@page import="com.landray.kmss.sys.ui.xml.model.SysUiVarKind"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
    JSONArray vs = (JSONArray)request.getAttribute("vars");
    for (int i = 0; i < vs.size(); i++) {
        JSONObject var = vs.getJSONObject(i);
        SysUiVarKind varkind = SysUiPluginUtil.getVarKindById(var.getString("kind"));
        var.put("varkind", varkind);
        /*
            任意文件读取漏洞修复:
            这里只屏蔽系统提供的custom.jsp的入口
            架空custom.jsp的功能，在这里直接使用配置的body.file，意味着做完这一步后，custom.jsp可废弃
        */
        if(varkind.getFdFile().equals("/sys/ui/extend/varkind/custom.jsp")){
            String body = String.valueOf(var.get("body"));
            if(com.landray.kmss.util.StringUtil.isNotNull(body)){
                JSONObject bodyJSON = JSONObject.fromObject(body);
                if(bodyJSON.containsKey("file")){
                    String customFile = String.valueOf(bodyJSON.get("file"));
                    var.put("_customFile_", customFile);
                }
            }
        }
    }
    pageContext.setAttribute("vars-config-list",vs);
    pageContext.setAttribute("jsname",request.getParameter("jsname"));
    Object containerId = request.getAttribute("containerId");
    pageContext.setAttribute("containerId",containerId);
%>
<script>
    var ${lfn:escapeJs(jsname)} = new VariableSetting();

</script>
<c:if test="${not empty pageScope['vars-config-list'] }">
    <link rel="stylesheet" href="${LUI_ContextPath }/sys/ui/extend/varkind/resource/css/varkind.css?s_cache=${LUI_Cache }">
    <!-- <link rel="stylesheet" href="${ LUI_ContextPath }/sys/ui/extend/varkind/resource/css/varkind.css?s_cache=${LUI_Cache }"> -->
    <table class='tb_normal tb_variable' width="100%" id="tny">
        <tr class="tr_normal_title td_variable_title"><td colspan="2">${ lfn:message('sys-ui:ui.vars.config') }</td></tr>
        <c:forEach items="${ pageScope['vars-config-list'] }" var="var" varStatus="vstatus">
            <%--<tr><td colspan="2">${ var['varkind']['fdFile'] }</td></tr> --%>
            <c:choose>
                <c:when test="${var['key']=='showYworkCode' }">
                    <kmss:ifModuleExist path="/third/ywork/">
                        <c:if test="${var['key']=='showYworkCode' }">
                            <c:import url="${ var['varkind']['fdFile'] }" charEncoding="UTF-8">
                                <c:param name="var" value="${ var }"></c:param>
                                <c:param name="jsname" value="${jsname}"></c:param>
                                <c:param name="code" value="${param['fdId']}"></c:param>
                            </c:import>
                        </c:if>
                    </kmss:ifModuleExist>
                </c:when>
                <c:when test="${var['key']=='showLDingService' }">
                    <kmss:ifModuleExist path="/lding/console/">
                        <c:if test="${var['key']=='showLDingService' }">
                            <c:import url="${ var['varkind']['fdFile'] }" charEncoding="UTF-8">
                                <c:param name="var" value="${ var }"></c:param>
                                <c:param name="jsname" value="${jsname}"></c:param>
                                <c:param name="code" value="${param['fdId']}"></c:param>
                            </c:import>
                        </c:if>
                    </kmss:ifModuleExist>
                </c:when>
                <%-- 任意文件读取漏洞修复: 这里判断，如果存在customFIle，则直接使用，不再经过custom.jsp中转了 --%>
                <c:when test="${ not empty var['_customFile_'] }">
                    <c:import url="${ var['_customFile_'] }" charEncoding="UTF-8">
                        <c:param name="var" value="${ var }"></c:param>
                        <c:param name="jsname" value="${jsname}"></c:param>
                        <c:param name="code" value="${param['fdId']}"></c:param>
                        <c:param name="containerId" value="${containerId}"></c:param>
                    </c:import>
                </c:when>

                <c:otherwise>
                    <c:import url="${ var['varkind']['fdFile'] }" charEncoding="UTF-8">
                        <c:param name="var" value="${ var }"></c:param>
                        <c:param name="jsname" value="${jsname}"></c:param>
                        <c:param name="code" value="${param['fdId']}"></c:param>
                        <c:param name="containerId" value="${containerId}"></c:param>
                    </c:import>
                </c:otherwise>
            </c:choose>


        </c:forEach>
    </table>
    <kmss:ifModuleExist path="/third/ywork/">
        <script>
            $(function(){
                var url = '<c:url value="/third/ywork/ywork_doc/yworkDoc.do?method=isOpen" />';
                var tip = "${lfn:message('third-ywork:ywork.index.code.wxoffice') }";
                $.post(url,function(data){
                    if(data.status=="1"){
                        $("#tny").find("tr td").each(function(){
                            if($(this).text()==tip){
                                $(this).parent().show();
                            }
                        });
                    }else{
                        $("#tny").find("tr td").each(function(){
                            if($(this).text()==tip){
                                $(this).parent().hide();
                            }
                        });
                    }
                },"json");
            });
        </script>

    </kmss:ifModuleExist>
</c:if>
