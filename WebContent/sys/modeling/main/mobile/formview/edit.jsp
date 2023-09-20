<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.edit" compatibleMode="true" gzip="true" isNative="true">
	<template:replace name="title">
		<c:out value="${modelingAppModelMainForm.fdModelName}"></c:out>
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="mui-review-view.css"/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/main/mobile/css/common.css?s_cache=${LUI_Cache}"/>
		<script type="text/javascript">
			if(dojoConfig){
				dojoConfig.tiny = true;
			}
		   	require(["dojo/store/Memory","dojo/topic","dijit/registry"],function(Memory,topic,registry){
		   		var navData = [{'text':'01  /  <bean:message bundle="sys-modeling-main" key="mobile.info" />',
		   			'moveTo':'scrollView','selected':true},{'text':'02  /  <bean:message bundle="sys-modeling-main" key="mobile.review" />',
			   		'moveTo':'lbpmView'}]
		   		window._narStore = new Memory({data:navData});
		   		var changeNav = function(view){
		   			var wgt = registry.byId("_flowNav");
		   			for(var i=0;i<wgt.getChildren().length;i++){
		   				var tmpChild = wgt.getChildren()[i];
		   				if(view.id == tmpChild.moveTo){
		   					tmpChild.beingSelected(tmpChild.domNode);
		   					return;
		   				}
		   			}
		   		}
		   		topic.subscribe("mui/form/validateFail",function(view){
		   			changeNav(view);
		   		});
				topic.subscribe("mui/view/currentView",function(view){
					changeNav(view);
		   		});
		   	});

	   </script>
	</template:replace>
	<template:replace name="content">
		<html:hidden property="detailOperationAuthConfig" value="${detailOperationAuthConfig}" />

		<html:form action="/sys/modeling/main/modelingAppModelMain.do">
			<html:hidden property="listviewId" value="${param.listviewId}"/>
			<html:hidden property="fdId" value="${modelingAppModelMainForm.fdId}"/>
			<html:hidden property="docStatus" />
			<html:hidden property="fdModelId" />
			<html:hidden property="docCreateTime"/>
			<html:hidden property="docCreatorId"/>
			<html:hidden property="fdTreeNodeData"/>
			<div>
				<div data-dojo-type="mui/fixed/Fixed" class="muiFlowEditFixed">
					<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowEditFixedItem">
						<div data-dojo-type="mui/nav/NavBarStore" id="_flowNav" data-dojo-props="store:_narStore">
						</div>
					</div>
				</div>
				<div data-dojo-type="mui/view/DocScrollableView" 
					data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
					<div class="muiFlowInfoW muiFormContent">
						<html:hidden property="fdId" />
						<c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="modelingAppModelMainForm" />
							<c:param name="moduleModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain" />
						</c:import>
					</div>
					<div class="muiFlowInfoW muiFormContent">
						<c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="modelingAppModelMainForm" />
							<c:param name="fdKey" value="modelingApp" />
							<c:param name="backTo" value="scrollView" />
						</c:import>
					</div>
					<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
					  	<li data-dojo-type="mui/tabbar/TabBarButton" class="mainTabBarButton"
					  		data-dojo-props='colSize:2,moveTo:"lbpmView",transition:"slide"'>
					  		${lfn:message('sys-modeling-main:modeling.next.step') }
						</li>
					</ul>
				</div>
				<c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppModelMainForm" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="viewName" value="lbpmView" />
					<c:param name="backTo" value="scrollView" />
					<c:param name="supportSaveDraft" value="true" />
					<c:param name="saveDraftValidateDomId" value="scrollView" />
					<c:param name="onClickSubmitButton" value="modeling_submit();" />
				</c:import>
				<c:if test="${modelingAppModelMainForm.sysWfBusinessForm.fdIsHander == 'true' && modelingAppModelMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.eqbSign =='true'}">
					<c:import url="/elec/eqb/elec_eqb_common_default/elecEqbCommonDefaultSign.do?method=getEqbSignPage" charEncoding="UTF-8">
						<c:param name="signId" value="${modelingAppModelMainForm.fdId }" />
					</c:import>
				</c:if>
				<script type="text/javascript">
					Com_IncludeFile("detailOperationAuth.js", "${LUI_ContextPath}/sys/modeling/main/resources/js/", 'js', true);
				require(["mui/form/ajax-form!modelingAppModelMainForm"]);
				require(["dijit/registry",'mui/dialog/Confirm'],function(registry,Confirm){
					window.modeling_submit = function(){
						var status = document.getElementsByName("docStatus")[0];
						var method = Com_GetUrlParameter(location.href,'method');
						//数据唯一性校验
						if(Modeling_DataUniqueValidate()){
							if(method=='add'){
								document.getElementsByName("docStatus")[0].value = "20";
								Com_Submit(document.forms[0],'save');
							}else{
								if(status.value=='10' || status.value=='11' || status.value=='20'){
									document.getElementsByName("docStatus")[0].value = "20";
									Com_Submit(document.forms[0],'publishUpdate');
								}else{
									Com_Submit(document.forms[0],'update');
								}
							}
						}
					}
					window.Modeling_DataUniqueValidate = function(){
						var isUnique = false;
						var url = Com_Parameter.ContextPath + "sys/modeling/main/dataValidate.do?method=dataValidate";
					    $.ajax({
					        url: url,
					        type: "post",
					        data: $('form').serialize(),
					        async : false,
					        success: function (rtn) {
					            if (rtn.status === '00') {
									isUnique = true;
					            }else{
									var tips = rtn.errmsg || "${lfn:message('sys-modeling-main:modeling.data.uniqueness.verification.tips') }";
									Confirm(tips, null, function(status){
									});
									isUnique = false;
								}
					        },
					        error : function(rtn){
					        	var tips = rtn.errmsg || "${lfn:message('sys-modeling-main:modeling.data.uniqueness.verification.tips') }";
				            	Confirm(tips, null, function(status){
								});
					        	isUnique = false;
					        }
					    });
						
						return isUnique;
					}
				})

				require(["dojo/ready"], function (ready) {
					ready(function () {
						initDetailOperationAuth($("input[name='detailOperationAuthConfig']").val(),true, "${param.method}","${nodeId}",true);
					});
				});

			</script>
			</div>
		</html:form>
	</template:replace>
</template:include>
