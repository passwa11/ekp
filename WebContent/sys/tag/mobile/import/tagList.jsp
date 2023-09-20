<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		
	</template:replace>
	<template:replace name="head">
  		<link rel="stylesheet" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/tag.css">
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/view/DocScrollableView">
			<div class="all-tags">
				<div
					data-dojo-type="mui/list/JsonStoreList" 
					data-dojo-mixins="<%=request.getContextPath()%>/sys/tag/mobile/import/js/list/sysTagListMixin.js"
					data-dojo-props="url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list&allTag=true&s_ajax=true',lazy:false"
				></div>
			</div>
		</div>
		<script>
			var value=[];
			require(['dojo/topic'],function(topic){
				topic.subscribe('hr/staff/tag/selectvalue',function(data){
					value = data;
				})
				window.getValue=function(){
					return value;
				}
			})
		</script>
	</template:replace>
</template:include>