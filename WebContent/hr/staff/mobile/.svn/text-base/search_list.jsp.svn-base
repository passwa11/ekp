<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>

<template:include ref="mobile.list" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.1"/>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/index.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/reset.css">
	   	<script src="resource/js/rem.js"></script>
	   	<script src="resource/js/search/pinyin.js"></script>
		<style>
			#search-bg{
				display:flex;
				align-items: center;
				background-image: linear-gradient(270deg, #5AABFE 1%, #1C85ED 98%);
			}
			#search-content{
				flex:1;
			}
			.file-overview-search{
				background:none;
			}
			.search-cancel{
				font-size: .28rem;
				color: #FFFFFF;
				margin-right:.22rem;
			}
			html.mobile, .mobile body{
				background:#fff;
			}
			.search-list-item{
				height:1.07rem;
				display:flex;
				align-items: center;
				margin-left:.42rem;
				border-bottom: 1px solid #D5D5D5;
			}
			.search-list-item-img{
				height:.64rem;
				width:.64rem;
				border-radius:50%;
			}
			.search-list-item-name{
				margin-left:.2rem;
			}
			.search-list-item-href{
				height:1.07rem;
				position:absolute;
				top:0;
				display:inline-block;
				width:100%;
			}
			.mblEdgeToEdgeList>div{
				position:relative;
			}
			.search-list-item span{
				color:#1B7BE1;
				font-size: .28rem;
			}
			.muiListNoDataTxt{
				font-size:.32rem!important;
			}
		</style>
	</template:replace>
	<template:replace name="content">
	<%
		String moduleParam = request.getParameter("moduleParam");
		String urlParam = "";
		if(moduleParam!=null){
			urlParam = "&q._fdStatus="+moduleParam;
			if(moduleParam.equals("all")){
				urlParam ="&q._fdStatus=official&q._fdStatus=temporary&q._fdStatus=trial&q._fdStatus=trialDelay";
			}
	  		if(moduleParam.equals("monthEntry")){
	  			urlParam="&q._fdType="+moduleParam;
	  		}
	  		if(moduleParam.equals("monthLeave")){
	  			urlParam ="&q._fdType="+moduleParam+"&personStatus=quit";
	  		}
			if(moduleParam.equals("onpost")){
				urlParam ="";
			}
		}
	%>
		<div id="scrollView" 
				data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/list/StoreScrollableView">
			<div id="search-bg">
				 <div id="search-content" data-dojo-type='<%=request.getContextPath()%>/hr/staff/mobile/resource/js/search/search.js'
				 	data-dojo-props="value:'${param.key }',preUrl:'${param.preUrl}'"
				 	></div><div class="search-cancel"><bean:message bundle="sys-ui" key="ui.dialog.button.cancel"/></div>
			</div>
			<div data-dojo-type="mui/list/JsonStoreList"
				data-dojo-mixins="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/list/searchListMixin.js"
				data-dojo-props="key:'${param.key}',url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list&q.fdName=${param.key }&orderby=fdTimeOfEnterprise&ordertype=down<%=urlParam %>',lazy:false"
			></div>
		</div>
		<script>
			var cancelNode = document.querySelector('.search-cancel');
			cancelNode.addEventListener("click",function(){
				var preUrl = '${param.index}'
				if(preUrl){
					switch(preUrl){
					case 'index':
						window.location.href="<%=request.getContextPath()%>/hr/staff/mobile";
						break;
					}
				}else{
					window.history.back();
				}
			});
			//调整置顶按钮的样式
			require(['dojo/topic', 'dojo/query'], function(topic, query){
				topic.subscribe('mui/view/addBottomTip',function(){
					 query(".muiTop").style({width:"2.5rem",height:"2.5rem",bottom:"2.5rem",lineHeight:"2.5rem"});
				});
			});

		</script>		
	</template:replace>
</template:include>