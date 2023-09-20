<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		签到记录详情
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/import/css/view.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/resource/css/attend.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="content">
		<div class='sysAttendListView' data-dojo-type='dojox/mobile/View'>
			<div class='sysAttendHeader' 
				data-dojo-type='mui/header/Header' data-dojo-props='height:"3.8rem"'>		
				<div class='sysAttendHeaderReturn' style="width:25%">
					<i class='mui mui-back'></i>
					<span class='personHeaderReturnTxt'>${lfn:message('sys-attend:sysAttendCategory.sysAttendview.topic20') }</span>
				</div>
				<div class='personHeaderTitle'>${lfn:message('sys-attend:sysAttendCategory.sysAttendview.topic21') }</div>
				<div></div>
			</div>
			<div data-dojo-type='mui/nav/NavBar' data-dojo-props='height:"3.8rem"'>
				<div id='unattendcount' class='muiNavitem muiNavitemSelected' 
					data-dojo-type='sys/attend/mobile/import/js/SysAttendNavItem'
					data-dojo-props='text:"未签到",value:0'>
				</div>
				<div id='attendcount' class='muiNavitem' 
					data-dojo-type='sys/attend/mobile/import/js/SysAttendNavItem'
					data-dojo-props='text:"已签到 ",value:1'>
				</div>
			</div>
			<div data-dojo-type='mui/list/StoreScrollableView'>
				<ul data-dojo-type='mui/list/JsonStoreList'
					data-dojo-mixins='sys/attend/mobile/import/js/SysAttendItemListMixin'
					data-dojo-props='url:"/sys/attend/sys_attend_main/sysAttendMain.do?method=list&operType=0&appId=${JsParam.appId}&fdCategoryId=${JsParam.categoryId }",lazy:false'>
				</ul>
			</div>
		</div>
		
		<script type="text/javascript">
			require(['dojo/topic','mui/dialog/Tip',"dojo/query","dojo/ready","dojo/on", "dojo/touch","dojo/request",'dojo/io-query','mui/util',"mui/device/adapter","dijit/registry"],
					function(topic,Tip,query,ready,on,touch,request,ioq,util,adapter,registry){
				ready(function(){
					on(query('.sysAttendHeaderReturn')[0],'click', function(){
						var rtn = adapter.goBack();
						if(rtn == null){
							history.back();
						}
					});
					var unattendcount = query('#unattendcount .muiNavitemSpan')[0];
					var attendcount = query('#attendcount .muiNavitemSpan')[0];
					var urlstat = util.formatUrl('/sys/attend/sys_attend_category/sysAttendCategory.do?method=stat');
					var promise1 = request(urlstat,{
						handleAs : 'json',
						method : 'POST',
						data : ioq.objectToQuery({
							appId : '${JsParam.appId}'
						})
					});
					promise1.then(function(stats){
						if(stats && stats.length>0){
							for(var k = 0;k < stats.length;k++){
								stat = stats[k];
								if(stat.fdId == '${JsParam.categoryId }'){
									unattendcount.innerHTML =  "${lfn:message('sys-attend:sysAttendCategory.sysAttendview.topic17') }" + stat.unattendcount;
									attendcount.innerHTML = "${lfn:message('sys-attend:sysAttendCategory.sysAttendview.topic14') }" + stat.attendcount;
									break;
								}
							}
						}
					});
				});
			});
		</script>
	</template:replace>
</template:include>