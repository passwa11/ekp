<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit"/>
    <%@ include file="/sys/ui/jsp/jshead.jsp" %>

    <script type="text/javascript">
        seajs.use(['theme!list', 'theme!portal']);
    </script>
    <script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript" src="${ LUI_ContextPath }/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript"
            src="${ LUI_ContextPath }/sys/profile/resource/js/dropdown.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript"
            src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
    <link type="text/css" rel="stylesheet"
          href="${ LUI_ContextPath }/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${ LUI_ContextPath }/sys/ui/extend/theme/default/style/icon.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${LUI_ContextPath}/sys/modeling/base/relation/relation/css/relation.css?s_cache=${LUI_Cache}"/>

    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
    <title>
        <template:block name="title"/>
    </title>
    <template:block name="head"/>
</head>
<body class="lui_list_body">

<div id="mainContent" class="lui_list_mainContent fullscreen" style="margin: 0;padding: 0;visibility:hidden; ">
    <c:if test="${param.main eq 'relation' || param.main eq ''}">
        <c:import url="/sys/modeling/base/sysModelingRelation.do?method=relationMap"
                  charEncoding="UTF-8">
            <c:param name="fdModelId" value="${param.fdAppModelId}"/>
        </c:import>
    </c:if>
    <div id="noxform" style="visibility:hidden;">
    
    <c:if test="${param.main eq 'trigger' || param.main eq 'behavior' }">
        <c:import url="/sys/modeling/base/relation/trigger_index.jsp" charEncoding="UTF-8">
            <c:param name="fdModelId" value="${param.fdAppModelId}"/>
        </c:import>
    </c:if>

    <%--                    <c:if test="${param.main eq 'scenes'  }">--%>
    <%--                        <c:import url="/sys/modeling/base/relation/scenes_index.jsp" charEncoding="UTF-8">--%>
    <%--                            <c:param name="fdModelId" value="${param.fdAppModelId}"/>--%>
    <%--                        </c:import>--%>
    <%--                    </c:if>--%>
    <c:if test="${param.main eq 'operation'}">
        <c:import url="/sys/modeling/base/relation/operation_index.jsp" charEncoding="UTF-8">
            <c:param name="fdModelId" value="${param.fdAppModelId}"/>
        </c:import>
    </c:if>
    </div>
</div>

<script>


    function renderList(flag) {
        Com_OpenWindow("${LUI_ContextPath}/sys/modeling/base/relation/index.jsp?main=" + flag + "&fdAppModelId=${param.fdAppModelId}", "_self");
    }

    $(function () {
        var viewHeight = window.innerHeight - 10;
        $('#_menu').css({height: viewHeight});
        $('#menu_nav').css({height: viewHeight - 5});

    });
    
    Com_AddEventListener(window,"load",function(){
 		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	 		var url = '<c:url value="/sys/modeling/base/modelingAppFlow.do" />?method=hasXForm&fdModelId=${param.fdAppModelId}';
	 		$.ajax({
					url: url,
					type: 'GET',
					dataType: 'json',
					success: function(rtn){
						if(rtn.hasXForm === "true"){
							$("#mainContent").css("visibility","visible");
							$("#noxform").css("visibility","visible");
						}else{
							var nurl = Com_Parameter.ContextPath + "sys/modeling/base/noxform/noxform.jsp?page=operation";
							window.location.href = nurl;
						}
					}
			   });
 		})
 	})
</script>
</body>
</html>
