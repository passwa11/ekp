<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<template:include ref="mobile.list" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-ratify" key="porlet.flow.center"/>
	</template:replace>
	<template:replace name="head">	   
	  <link rel="Stylesheet" href="./resource/css/view.css?s_cache=${MUI_Cache}"/>
	  <link rel="Stylesheet" href="./resource/css/list.css?s_cache=${MUI_Cache}"/>
	  <script src="./resource/js/rem/rem.js"></script>
	  <style>
	  		.muiListItem{
				border-bottom:1px solid #EAEBF0;
			}
			.mblTabBarTabBar{
				border-top:none;
				box-shadow: 0 6px 10px 0 rgba(59,68,93,0.50);
			}
			.mblTabBarButtonLabel img{
				width:2.2rem;
				height:2.2rem;
				margin-right:1.1rem;
				vertical-align: middle;
			}
			#tabBtnSubmit li,#tabBtnSubmit .mblTabBarButtonLabel{
				height:5.52rem;
				padding:0;
				line-height:5.52rem;
				font-size:1.76rem;
				
			}
			#tabBtnSubmit .muiTabBarGrid.mblTabBarTabBar .mblTabBarButton{
				padding:0;
			}
			html,body{
				backgroud-color:#fff;
			}
			.muiProcessStatusBorder:before{
				content:"";
				border:none!important;
				width:auto;
				height:auto;
			}
			.muiProcessStatusBorder {
				border:1px solid #C9CAD1;
			}
	  </style>
	</template:replace>
	<template:replace name="content">
	<%
	 String modulename = request.getParameter("modulekey");
	 String mobile = request.getParameter("mobile");
	 String upperModuleName = modulename.substring(0, 1).toUpperCase() + modulename.substring(1);
	 String mainModuleName = "";
	 String moduleClass = "";
	 String fdTempKey="";
	 if(!modulename.equals("")){
		 request.setAttribute("modulename", modulename);
		 request.setAttribute("upperModuleName", upperModuleName);
		 if(mobile!=null){
			 if(!mobile.equals("")){
				 String navname = mobile.substring(0, 1).toUpperCase() + mobile.substring(1);
				 request.setAttribute("navname",navname);
				 String preFix = "com.landray.kmss.hr.ratify.model.";
				 if(mobile.equals("contract")){
					 moduleClass =preFix+"HrRatifySign;"+preFix+"HrRatifyRemove;"+preFix+"HrRatifyChange";
				     request.setAttribute("moduleClass",moduleClass);
					 fdTempKey="HrRatifySignDoc;HrRatifyRemoveDoc;HrRatifyChangeDoc";
					 request.setAttribute("fdTempKey",fdTempKey);
				 }
				 if(mobile.equals("other")){
					 moduleClass =preFix+"HrRatifyRehire;"+preFix+"HrRatifyRetire;"+preFix+"HrRatifyOther;"+preFix+"HrRatifyFire";
				     request.setAttribute("moduleClass",moduleClass);
					 fdTempKey="HrRatifyRehireDoc;HrRatifyRetireDoc;HrRatifyOtherDoc;HrRatifyFireDoc";
					 request.setAttribute("fdTempKey",fdTempKey);
				 }
			 }else{
				 request.setAttribute("navname",upperModuleName );
			 }
		 }else{
			 	 mainModuleName="mainModelName:'com.landray.kmss.hr.ratify.model.HrRatify"+upperModuleName+"',";
			 	 moduleClass = "com.landray.kmss.hr.ratify.model.HrRatify"+upperModuleName;
			 	 request.setAttribute("mainModelName",mainModuleName);
				 request.setAttribute("navname",upperModuleName );
				 fdTempKey="HrRatify"+upperModuleName+"Doc";
				 if(!mainModuleName.equals("Main")){
					 request.setAttribute("moduleClass",moduleClass); 
				 }else{
					 request.setAttribute("moduleClass","");
				 }
				 if(modulename.equals("main")){
					 fdTempKey ="";
				 }
				 request.setAttribute("fdTempKey",fdTempKey);
		 }

	 }
	%>
	<div data-dojo-type="hr/ratify/mobile/resource/js/ReviewView"
			data-dojo-mixins="hr/ratify/mobile/resource/js/ReviewScrollableViewMixin">
			<div data-dojo-type="mui/header/Header"
					data-dojo-props="'name':'',imgUrl:'',modelName:'${modulename eq "main"?"": moduleClass}',modelKey:'${param.modulekey}',mobileKey:'${param.mobile}'"
					data-dojo-mixins="hr/ratify/mobile/resource/js/ReviewHeaderMixin">
				<div data-dojo-type="mui/header/HeaderItem"
					data-dojo-mixins="mui/folder/_Folder,hr/ratify/mobile/resource/js/ReviewHomeButton"></div>
				<div
					data-dojo-type="mui/nav/MobileCfgNavBar" 
					data-dojo-props="defaultUrl:'/hr/ratify/mobile/list/nav/hrRatify${navname}_nav.jsp',modelName:'com.landray.kmss.hr.ratify.model.HrRatify${upperModuleName}',curIndex:1">
				</div>
			<!-- 	<div data-dojo-type="mui/header/HeaderItem"
					data-dojo-mixins="mui/folder/Folder"
					data-dojo-props="tmplURL:'/hr/ratify/mobile/query.jsp'"></div> -->
			 </div>
			<!-- 列表 -->
			<div data-dojo-type="mui/list/NavSwapScrollableView"
			     data-dojo-mixins="hr/ratify/mobile/resource/js/ReviewListScrollableViewMixin">
			    <ul
			    	data-dojo-type="mui/list/JsonStoreList"
			    	data-dojo-mixins="mui/list/ProcessItemListMixin">
				</ul>
			</div>
		</div>
		
		<%-- <kmss:authShow roles="ROLE_KMREVIEW_CREATE"> --%>
			<ul id="tabBtnSubmit" data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
		  		<li data-dojo-type="mui/tabbar/CreateButton" 
			  		data-dojo-mixins="mui/syscategory/SysCategoryMixin,hr/ratify/mobile/resource/js/HrRatifyCategoryMixin"
			  		data-dojo-props="icon1:'',createUrl:'/hr/ratify/hr_ratify_${modulename}/hrRatify${upperModuleName}.do?method=add&i.docTemplate=!{curIds}',
			  		mobileCate:'${not empty mainModuleName?true:false}',
					${not empty mainModuleName?mainModelName:null}
			  		modelName:'com.landray.kmss.hr.ratify.model.HrRatifyTemplate',fdTmepKey:'${fdTempKey}'"><img src="./resource/images/addplus_blue.png"><bean:message bundle="hr-ratify" key="button.opt.create"/></li>
			</ul>
		<%-- </kmss:authShow> --%>
	<script>
		var baseUrl = "${LUI_ContextPath}";
		setTimeout(function(){
			var topHeight = document.querySelector(".muiCommonHeader");
			if(topHeight){
				window.headerOffsetHeight = topHeight.offsetHeight;
			}
		},2000);
		require(["dojo/ready","dojo/request","dojo/query","dojo/topic","mui/device/adapter","mui/util","dojo/ready"],
				function(ready,request,query,topic,adapter,util) {
			 topic.subscribe("hr/ratify/afterSelectCate",function(tempId){
				 if(tempId){
					 request.post("${LUI_ContextPath}/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=loadRatifyTemplate&tempId="+tempId).then(function(res){
							if(res){
								var createUrl = baseUrl+"/"+res;
								adapter.open(createUrl,"_self");
							}
							
					 }) 
				 }
					
			 });
		});
	</script>
	</template:replace>
</template:include>