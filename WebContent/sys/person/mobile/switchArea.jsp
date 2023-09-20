<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.list">

	<template:replace name="content">
	
		<div data-dojo-type="mui/category/CategoryHeader"
			data-dojo-mixins="sys/person/mobile/js/AreaHeaderMixin"
				data-dojo-props="title: '请选择场所', height:'3.8rem'"></div>
				
		<div data-dojo-type="sys/person/mobile/js/AreaSearchBar"></div>
		
		<div data-dojo-type="dojox/mobile/ScrollableView">
			<ul data-dojo-type="mui/category/CategoryList"
				data-dojo-mixins="sys/person/mobile/js/AreaItemListMixin"
				data-dojo-props="lazy:false, selType: 1">
			</ul>
		</div>
			
		<script>
		
			require(['dojo/topic','dojo/request','mui/dialog/Tip'], function(topic,request,Tip) {
				
				topic.subscribe('/sys/person/area/submit', function(res) {
					var areaId = res.fdId; // 场所ID
					var url = '${LUI_ContextPath}/sys/authorization/sys_auth_area/sysAuthArea.do?method=switchArea&areaId='+areaId;
					request.get(url, {handleAs : 'json',headers: {"accept": "application/json"}})
					.response.then(function(datas) {
						if(datas.status=='200'){
							Tip.success({
								text:'切换场所成功' 
							});
							setTimeout(function(){
								history.go(-1);
							},1000);
						}
					});
					
				});
				
				topic.subscribe('/mui/category/cancel', function() {
					history.go(-1);
				});
				
			});
			
		</script>
		     
	</template:replace>
	
	
</template:include>