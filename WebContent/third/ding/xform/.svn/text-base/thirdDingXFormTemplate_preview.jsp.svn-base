<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("templateList.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);
	Com_IncludeFile("preview.js","${LUI_ContextPath}/third/mall/resource/js/","js",true);
</script>
<html>
	<body style="overflow:hidden;">
		<div class="cm-preViews">
			 <div class="preViews-title">
			     <!-- 选项卡选择PC端 -->
			     <span class="client typeActive" name="clientType" value="pc"><bean:message key="third-mall:thirdMall.pc"/></span>
			     <span class="client" name="clientType" value="mobile"><bean:message key="third-mall:thirdMall.mobile"/></span>
			 </div>
			 <div class="preViews-content">
			     <!-- PC端预览 -->
			     <span class="imgWrap">
			     	<img src="" alt="" class="pc-img">
			     </span>
			     <!-- 移动端预览 -->
			     <span class="imgWrap" style="display:none;">
			     	<img src="" alt="" class="mb-img">
			     </span>
			 </div>
			 <input name="createUrl" type="hidden" value="${JsParam.createUrl}" />
			 <div class="preViews-tips" >
			 	<label><bean:message key="third-mall:thirdMall.tips"/></label>
			 </div>
			 <div class="preViews-footer">
			     <span class="useIt"><bean:message key="third-mall:thirdMall.useTemplate"/></span>
			     <span class="cancel"><bean:message key="third-mall:thirdMall.cancel"/></span>
			 </div>
		</div>
	</body>
</html>