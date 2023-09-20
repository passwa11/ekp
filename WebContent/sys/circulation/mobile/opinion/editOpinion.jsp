<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="title">
		传阅意见
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=updateOpinion&mobile=true">
			<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
				<div data-dojo-type="mui/panel/AccordionPanel" class="editPanel">
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'传阅意见',icon:'mui-ul'">
						<div class="muiFormContent">
							<table class="muiSimple" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<html:hidden property="fdId" />
										<html:hidden property="sysCirculationMainId" />
										<html:hidden property="fdBelongPersonId" />
										<xform:textarea property="docContent" mobile="true" style="height:100px;width:96%" showStatus="edit" required="${required}" subject="传阅意见"></xform:textarea>
									</td>
								</tr>
								<tr>
									<td>
										<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
											<c:param name="formName" value="sysCirculationOpinionForm"></c:param>
											<c:param name="fdKey" value="attachment" />
										</c:import>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<c:if test="${'1' eq sysCirculationMain.fdRegular}">
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'即将流向',icon:'mui-ul'">
							<div class="nextInfo" style="padding:1rem 0rem;font-size: 1.5rem">即将流向:${not empty nextPerson?nextPerson:'传阅结束'}</div>
							<div class="nextInfoTips" style="padding-bottom:1rem;font-size: 1.5rem;color: red">当前发起的是有序传阅，需要您阅读后才能传阅给下一人。</div>
						</div>
					</c:if>
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'意见列表',icon:'mui-ul'">
						<div data-dojo-type="dojox/mobile/View">
							<ul
						    	data-dojo-type="mui/list/JsonStoreList"
						    	data-dojo-mixins="mui/list/ProcessItemListMixin"
						    	data-dojo-props="url:'/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=list&fdMainId=${sysCirculationMain.fdId}&isOpinion=true',lazy:false">
							</ul>
						</div>
					</div>
				</div>
			</div>
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
			  	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
			  		data-dojo-props='colSize:2,href:"javascript:submitForm();",transition:"slide"'>
			  		提交
			  	</li>
			</ul>
		</html:form>
		<script>
		require(["mui/form/ajax-form"],function(ajaxForm){
			ajaxForm.ajaxForm("[name='sysCirculationOpinionForm']", {back:true});
		});
		
		
		require(['dojo/ready','dijit/registry','dojo/topic','dojo/query','dojo/dom-style','dojo/dom-class',"dojo/_base/lang","mui/dialog/Tip","dojo/request","mui/device/adapter","mui/util","mui/device/device" ,'dojo/date/locale'],
				function(ready,registry,topic,query,domStyle,domClass,lang,Tip,req,adapter,util,device,locale){
			
			window.submitForm=function(){
				var validorObj = registry.byId('scrollView');
				if(!validorObj.validate()){
					return;
				}
				 req(util.formatUrl("/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=checkWrite"), {
					 handleAs : 'json',
					 method : 'post',
					 data : {"fdOpinionId":"${sysCirculationOpinionForm.fdId}"}
				}).then(lang.hitch(this, function(results) {
					if(results["repeat"] == 'true'){
						Tip.tip({icon:'mui mui-warn', text:'您已填写过意见!',width:'260',height:'60'});
					}else{
						if(document.getElementsByName("docContent")[0].value == ""){
							document.getElementsByName("docContent")[0].value = '<bean:message bundle="sys-circulation" key="sysCirculationOpinion.read" />';
						}
						var formObj = document.sysCirculationOpinionForm;
						Com_Submit(formObj, "updateOpinion");
					}
				}));
			}
		});
		</script>
	</template:replace>
</template:include>
