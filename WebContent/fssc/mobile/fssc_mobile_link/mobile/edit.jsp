<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiTools"%>
<%@page import="com.landray.kmss.fssc.mobile.util.FsscMobileUiTools"%>
<%@page import="java.util.List"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/editMobileLink.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }" />
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }" >
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/footer.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/home.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css?s_cache=${LUI_Cache }">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/popups.css">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/search.css">
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/picker.min.js"></script>
	<script src="${LUI_ContextPath}/fssc/mobile/resource/js/zepto.js"></script>
	<script src="${LUI_ContextPath}/fssc/mobile/resource/js/dingtalk.open.js"></script>
	<script src="${LUI_ContextPath}/fssc/mobile/resource/js/popups.js"></script>
    
    
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js"></script>
    
    <script >
	    var formInitData={
	   		 'LUI_ContextPath':'${LUI_ContextPath}',
	    }
    		Com_IncludeFile("edit.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_link/mobile/", 'js', true);
    </script>
    <title>移快报</title>
    <%
		List<String> icon = FsscMobileUiTools.scanIconCssName("l",false);
		request.setAttribute("icons", icon);
		%> 
</head>
<body>
	<html:form action="/fssc/mobile/fssc_mobile_link/fsscMobileLink.do">
	<div class="fssc-mobile-links">
		<c:forEach var="link" items="${links }" varStatus="vstatus">
			<div class="fssc-mobile-links-link" onclick="editLink(this)">
				<img src="${LUI_ContextPath }/fssc/mobile/resource/images/icon/${link.fdIcon }"/>
				<span>${link.fdName }</span>
				<i onclick="deleteLink();">-</i>
				<input type="hidden" name="icon-index" value="${vstatus.index }"/>
				<input type="hidden" name="fdName" value="${link.fdName  }"/>
				<input type="hidden" name="fdUrl" value="${link.fdUrl }"/>
				<input type="hidden" name="fdIcon" value="${link.fdIcon }"/>
				<input type="hidden" name="fdId" value="${link.fdId }"/>
			</div>
		</c:forEach>
		<div class="fssc-mobile-links-link add" onclick="addLink();">
			<img src="${LUI_ContextPath}/fssc/mobile/resource/images/add_icon.png"/>
			<span>新增</span>
		</div>
	</div>
	<div class="fssc-mobile-link-add fssc-mobile-link-hidden">
		<div class="ld-info-item">
            <div class="title">名称</div>
            <div class="content">
                 <input type="text" name="fdNameNew" placeholder="请输入名称"/>
                 <input type="hidden" name="edit-index" value=""/>
            </div>
        </div>
        <div class="ld-info-item">
            <div class="title">图标</div>
            <div class="content" onclick="selectIcon()">
            		<div class="icon icon-none"></div>
                <input type="text" name="fdIcon1" readonly placeholder="请选择图标"/>
                <input type="hidden" name="fdIconNew"/>
                <i></i>
            </div>
        </div>
        <div class="ld-info-item">
            <div class="title">链接</div>
            <div class="content" onclick="selectLinkUrl()">
                 <input type="text" name="fdUrlNew" readonly placeholder="请选择链接"/>
                 <i></i>
            </div>
        </div>
        <div class="fssc-mobile-footer" style="position:relative;bottom:0;">
			<div class="fssc-mobile-footer-cancel" onclick="cancelAddLink()">取消</div>
			<div class="fssc-mobile-footer-add" onclick="saveLink()">保存</div>
		</div>
	</div>
	<div class="fssc-mobile-link-icons fssc-mobile-link-icons-hidden">
		<c:forEach var="icon" items="${icons }">
			<div class="fssc-mobile-link-icons-box" onclick="checkIcon(this)">
				<input type="hidden" value="${icon }"/>
				<img src="${LUI_ContextPath }/fssc/mobile/resource/images/icon/${icon}.png">
				<div></div>
			</div>
		</c:forEach>
		<div class="fssc-mobile-icons-footer fssc-mobile-icons-footer-hidden">
			<div class="fssc-mobile-icons-footer-cancel" onclick="cancelIcon()">取消</div>
			<div class="fssc-mobile-icons-footer-add" onclick="saveIcon()">确定</div>
		</div>
	</div>
	<div class="fssc-mobile-footer">
		<div class="fssc-mobile-footer-save" onclick="save()">提交</div>
	</div>
	<input type="hidden" value="" name="params"/>
	</html:form>
</body>
