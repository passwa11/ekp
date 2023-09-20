<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="sys-circulation" key="sysCirculationMain.mobile.details" />
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/mobile/css/themes/default/cir.css?s_cache=${MUI_Cache}" />	
		<c:if test="${JsParam.isNewVersion eq 'true' }">
	  	   <mui:cache-file name="mui-circulate.css" cacheType="md5"/>
		</c:if>
	</template:replace>
	<template:replace name="content">
	<c:set var="remindAuth" value="false" />
	<c:set var="recallAuth" value="false" />
	<kmss:auth requestURL="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=remindCir&fdId=${sysCirculationMainForm.fdId}" requestMethod="GET">
		<c:set var="remindAuth" value="true" />
	</kmss:auth>
	<kmss:auth requestURL="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=recallCir&fdId=${sysCirculationMainForm.fdId}" requestMethod="GET">
		<c:set var="recallAuth" value="true" />
	</kmss:auth>
		<div id="scrollView" class="gray circulationView ${JsParam.isNewVersion eq 'true' ? 'muiNewCirculationView' : '' }" data-dojo-type="mui/view/DocScrollableView">
			  <div>
				  <div class="muiFormContent">
				  	<c:if test="${JsParam.isNewVersion ne 'true' }">
				  	   <div class="circulateDetail">
					      <div class="circulateDetailHead"><b><bean:message bundle="sys-circulation" key="sysCirculationMain.fdRecord" /></b> </div>
					   </div>
					</c:if>
					<table class="muiSimple" cellpadding="0" cellspacing="0">
						<tr>
							<td class="muiTitle">
								<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculatorId" />
							</td>
							<td>
								<c:out 	value="${sysCirculationMainForm.fdCirculatorName}" />
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculationTime" />
							</td>
							<td>
								<c:out 	value="${sysCirculationMainForm.fdCirculationTime}" />
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="sys-circulation" key="sysCirculationMain.fdExpireTime" />
							</td>
							<td>
								<c:out 	value="${sysCirculationMainForm.fdExpireTime}" />
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRegular" />
							</td>
							<td>
								<c:if test="${not empty sysCirculationMainForm.fdRegular}">
									<sunbor:enumsShow value="${sysCirculationMainForm.fdRegular}" enumsType="sysCirculationMain_fdRegular" bundle="sys-circulation" />
								</c:if>	
								<c:if test="${sysCirculationMainForm.fdOpinionRequired == 'true'}">
									 ；<bean:message bundle="sys-circulation" key="sysCirculationMain.fdOpinionRequired" />
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="sys-circulation" key="table.sysCirculationCirculors" />
							</td>
							<td>
									<c:out value="${sysCirculationMainForm.receivedCirCulatorNames}" />
							</td>
						</tr>
						<tr>
							<td class="muiTitle" >
								<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRemark" />
							</td>
							<td>
								<c:out value="${sysCirculationMainForm.fdRemark}" />
							</td>
						</tr>
					</table>
				</div>			
				 <div class="circulateDetail">
				     <div class="circulateDetailHead"><b><bean:message bundle="sys-circulation" key="sysCirculationMain.statistical" /></b> </div>
				  </div>
				 <div style="height: 15px;"></div>
				 <div class="statusContainer" id="statusContainer"></div>
				 <div data-dojo-type="mui/list/StoreElementScrollableView" data-dojo-props="height:'450px'" id="storeElementScrollableView">
					 <div  data-dojo-type="mui/list/JsonStoreList" id="opinionList" 
						data-dojo-mixins="${LUI_ContextPath}/sys/circulation/mobile/js/CirculationOpinionItemListMixin.js"
						data-dojo-props="url:'/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=list&forward=listOpinion&fdMainId=${sysCirculationMainForm.fdId}&docStatus=all',lazy:false,isNewVersion:'${JsParam.isNewVersion}'">
					</div>
				</div>
			</div>
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" id="optBar">
				<c:if test="${recallAuth eq 'true'}">
					<c:choose>
						<c:when test="${JsParam.isNewVersion eq 'true' }">
					  	    <li data-dojo-type="sys/circulation/mobile/js/CirculationOperationButton" data-dojo-props="fdType:'recall',fdMainId:'${sysCirculationMainForm.fdId}'">
								<bean:message bundle="sys-circulation" key="button.recall" />
							</li>
						</c:when>
						<c:otherwise>
							 <li data-dojo-type="mui/tabbar/TabBarButton" onclick="recall(this);">
								<bean:message bundle="sys-circulation" key="button.recall" />
							 </li>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${remindAuth eq 'true'}">
					<c:choose>
						<c:when test="${JsParam.isNewVersion eq 'true' }">
					  		<li data-dojo-type="sys/circulation/mobile/js/CirculationOperationButton" data-dojo-props="fdType:'remind',fdMainId:'${sysCirculationMainForm.fdId}'">
								<bean:message bundle="sys-circulation" key="button.remind" />
							</li>
						</c:when>
						<c:otherwise>
							<li data-dojo-type="mui/tabbar/TabBarButton" onclick="remind(this);">
								<bean:message bundle="sys-circulation" key="button.remind" />
							</li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</ul>
		</div>
		<div id='recallView' data-dojo-type="dojox/mobile/View" class="optView">
			<div class="muiHeaderBasicInfo optHead" data-dojo-type="mui/header/Header" fixed="top" data-dojo-props="height:'3.8rem'">
				<div class="muiHeaderBasicInfoTitle">未阅记录</div>
				<div class="muiHeaderBasicInfoBack" onclick="backToDocView(this)">
					<i class="mui mui-close"></i>
				</div>
			</div>
			<div data-dojo-type="mui/view/DocScrollableView">
				 <div style="padding-left:1rem">
				    <div  data-dojo-type="mui/list/JsonStoreList" id="recallList" 
						data-dojo-mixins="${LUI_ContextPath}/sys/circulation/mobile/js/CirculationOpinionItemListOptMixin.js"
						data-dojo-props="url:''">
					</div>
				</div>
			</div>
			<div class="muiHeaderBasicInfo optFoot" data-dojo-type="mui/header/Header" fixed="bottom" data-dojo-props="height:'3.8rem'">
				<div class="muiHeaderBasicInfoBack" onclick="selectAll(this,'recall')" >
					<div class="muiCateSelArea">
						<div class="muiCateSel muiCateSelMul">
							<i class="mui mui-checked muiCateSelected" style="display: none;"></i>
						</div>
					</div>
					全选
				</div>
				<div class="muiHeaderBasicInfoTitle" onclick="submit_recall(this)">确定撤回</div>
			</div>
		</div>
		<div id='remindView' data-dojo-type="dojox/mobile/View" class="optView">
			<div class="muiHeaderBasicInfo optHead" data-dojo-type="mui/header/Header" fixed="top" data-dojo-props="height:'3.8rem'">
				<div class="muiHeaderBasicInfoTitle">未阅记录</div>
				<div class="muiHeaderBasicInfoBack" onclick="backToDocView(this)">
					<i class="mui mui-close"></i>
				</div>
			</div>
			<div data-dojo-type="mui/view/DocScrollableView">
				 <div style="padding-left:1rem">
				    <div  data-dojo-type="mui/list/JsonStoreList" id="remindList" 
						data-dojo-mixins="${LUI_ContextPath}/sys/circulation/mobile/js/CirculationOpinionItemListOptMixin.js"
						data-dojo-props="url:''">
					</div>
				</div>
			</div>
			<div class="muiHeaderBasicInfo optFoot" data-dojo-type="mui/header/Header" fixed="bottom" data-dojo-props="height:'3.8rem'">
				<div class="muiHeaderBasicInfoBack" onclick="selectAll(this,'remind');">
					<div class="muiCateSelArea">
						<div class="muiCateSel muiCateSelMul">
							<i class="mui mui-checked muiCateSelected" style="display: none;"></i>
						</div>
					</div>
					全选
				</div>
				<div class="muiHeaderBasicInfoTitle" onclick="submit_remind(this)">确定提醒</div>
			</div>
		</div>		
		<script>
		require(['dojo/topic',
		         'dojo/ready',
		         "dojo/dom-style",
		         "dojo/dom-attr" ,
		         "dojo/dom-class",
		         "dojo/query",
		         'dojox/mobile/TransitionEvent',
		         "dojo/request",
		         "mui/util",
		         "dojo/_base/array",
		         "dojo/_base/lang",
		         "mui/dialog/Tip",
		         'dijit/registry'
		         ],function(topic,ready,domStyle, domAttr, domClass,query,TransitionEvent,req,util,array,lang,Tip,registry){
			
			ready(function() {
				 req(util.formatUrl("/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=findByStatusCount"), {
						method : 'post',
						data : {"fdMainId":"${sysCirculationMainForm.fdId}"}
				}).then(lang.hitch(this, function(results) {
					if(results){
						var html = "";
						var data = JSON.parse(results);
						for(var key in data){
					　　　　var text = data[key].text;
						   var value = data[key].value;
						   var id = data[key].id;
						   html+= '<div class="status" onclick="changeStatus(\''+id+'\');" id="status_'+id+'"><span class="status-count">'+value+'</span><span class="status-text">'+text+'</span></div>'
					　　}
						document.getElementById("statusContainer").innerHTML =html;
					}
				}));
				var  allEle = query('.titleHead', document.getElementById('opinionList'));
				 
				 array.forEach(allEle, function(item, idx, arr){
					if(idx !=0){
						detail_expandRow(item);
					}
				 });
			}); 
			
			 window.changeStatus = function(value){
				 var url= Com_GetCurDnsHost()+Com_Parameter.ContextPath+"sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=list&forward=listOpinion&fdMainId=${sysCirculationMainForm.fdId}";
				if(value != 'all'){
					url += "&docStatus="+value;
				}
				registry.byId("opinionList").set('url',url);
				
				registry.byId("opinionList").reload();
				registry.byId("storeElementScrollableView").setScrollTop(0);
			};
			
			window.submit_recall = function(obj){
				var list = [];
				var selectObj = query('.muiCateSeled', document.getElementById('recallList'));
				for(var i = 0; i < selectObj.length; i++){
					list.push(selectObj[i].value);
				}
				if(list.length > 0){
					 req(util.formatUrl("/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=deleteBacks"), {
							method : 'post',
							data : {"List_Selected":list,"fdMainId":"${sysCirculationMainForm.fdId}"}
					}).then(lang.hitch(this, function(results) {
						backToDocView(obj);
						Tip.success({
							text : '撤回成功！'
						});
					}));
				}else{
					Tip.tip({icon:'mui mui-warn', text:'请选择操作的对象！',width:'180',height:'120'});
				}
			};
			
			window.submit_remind = function(obj){
				var list = [];
				var selectObj = query('.muiCateSeled', document.getElementById('remindList'));
				for(var i = 0; i < selectObj.length; i++){
					list.push(selectObj[i].value);
				}
				if(list.length > 0){
					 req(util.formatUrl("/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=addReminds"), {
							method : 'post',
							data : {"List_Selected":list}
					}).then(lang.hitch(this, function(results) {
						backToDocView(obj);
						Tip.success({
							text : '提醒成功！'
						});
					}));
				}else{
					Tip.tip({icon:'mui mui-warn', text:'请选择操作的对象！',width:'180',height:'120'});
				}
			};
			
			window.selectAll = function(obj,type){
				var opt = query('.muiCateSelected',obj)[0];
				var status = domStyle.get(opt,'display');
				var optList = document.getElementById(type+'List');
				var checkboxObj = query('.muiCateSel',optList);
				if(status == 'none'){
					domStyle.set(opt,'display','');
					domClass.add(query('.muiCateSel',obj)[0],"muiCateSeled");
					for(var i = 0; i < checkboxObj.length; i++){
						var aa = query('.muiCateSelected',checkboxObj[i])[0];
						domStyle.set(aa,'display','');
						domClass.add(checkboxObj[i],"muiCateSeled");
					}
				}else{
					domStyle.set(opt,'display','none');
					domClass.remove(query('.muiCateSel',obj)[0],"muiCateSeled");
					for(var i = 0; i < checkboxObj.length; i++){
						var aa = query('.muiCateSelected',checkboxObj[i])[0];
						domStyle.set(aa,'display','none');
						domClass.remove(checkboxObj[i],"muiCateSeled");
					}
				}
			};
			
			window.detail_expandRow = function(domNode){
				var domTable = $(domNode).closest('table')[0];
				var display = domAttr.get(domTable,'data-display'),
					newdisplay = (display == 'none' ? '' : 'none');
				domAttr.set(domTable,'data-display',newdisplay);
				var items = query('.contentTr',domTable);
				for(var i = 0; i < items.length; i++){
					if(newdisplay == 'none'){
						domStyle.set(items[i],'display','none');
					}else{
						domStyle.set(items[i],'display','');
					}
				}
				var opt = query('.displayOpt',domTable)[0];
				if(newdisplay == 'none'){
					domClass.add(opt,'displayUp');
					domClass.remove(opt,'displayDown');
				}else{
					domClass.add(opt,'displayDown');
					domClass.remove(opt,'displayUp');
				}
			};
			
			//返回主视图
			window.backToDocView=function(obj){
				var opts = {
					transition : 'slide',
					transitionDir:-1,
					moveTo:'scrollView'
				};
				new TransitionEvent(obj, opts).dispatch();
			};
			window.recall = function(obj){
				url=Com_GetCurDnsHost()+"${LUI_ContextPath}/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=list&forward=listOpinion&fdMainId=${sysCirculationMainForm.fdId}&docStatus=10";
				registry.byId("recallList").set('url',url);
				var opts = {
					transition : 'slide',
					transitionDir:1,
					moveTo:'recallView'
				};
				new TransitionEvent(obj.domNode, opts).dispatch();
				registry.byId("recallList").reload();
			};
			window.remind = function(obj){
				url=Com_GetCurDnsHost()+"${LUI_ContextPath}/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=list&forward=listOpinion&fdMainId=${sysCirculationMainForm.fdId}&docStatus=10";
				registry.byId("remindList").set('url',url);
				var opts = {
					transition : 'slide',
					transitionDir:1,
					moveTo:'remindView'
				};
				new TransitionEvent(obj.domNode, opts).dispatch();
				registry.byId("remindList").reload();
			};
		});
		</script>
	</template:replace>
</template:include>



