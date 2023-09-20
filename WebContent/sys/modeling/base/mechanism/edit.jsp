<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="default.edit" sidebar="no">
	<template:replace name="content">
		<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/mechanism.css"/>

		<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="6">
			<ui:button text="${ lfn:message('button.save') }" order="1"  onclick="submitForm('updateMechanism');"></ui:button>
		</ui:toolbar>
		<html:form action="/sys/modeling/base/modelingAppModel.do">
			<html:hidden property="fdMechanismConfig"/>
			<div class="sideMenu">
			    <ul>
			        <%@ include file="./number.jsp"%>
			        <%@ include file="./print.jsp"%>
			        <%@ include file="./transportImport.jsp"%>
			    </ul>
			</div>
			
			<script type="text/javascript">
			    $(function(){
			        $(".sideMenu .nLi h3").click(function(){
			        	var self = this;
			            if($(self).parent(".nLi").hasClass("on")){
			            	$(self).parent(".nLi").removeClass("on")
			                $(self).next(".sub").slideUp(300);
			            }else{
			            	$(self).parent(".nLi").addClass("on")
			                $(self).next(".sub").slideDown(300);
			            }
			        })
			    })
			</script>
		</html:form>
		<script>
			var _numberSet = false;
			var beforeSubmitFuncArr = new Array();
			function submitForm(method){
				var cfg = {};
				var cfgStr = document.getElementsByName("fdMechanismConfig")[0].value;
				if(cfgStr!=""){
					cfg = LUI.toJSON(cfgStr);
				}
				console.log(beforeSubmitFuncArr);
				for (var i = 0; i < beforeSubmitFuncArr.length; i++) {
					beforeSubmitFuncArr[i].call(this,cfg);
				}
				document.getElementsByName("fdMechanismConfig")[0].value = LUI.stringify(cfg);
				Com_Submit(document.modelingAppModelForm,method);
			}

		function changeEnable(name){
			var upperName = name[0].toUpperCase()+name.substring(1);
			if($("input[name='is"+upperName+"Enable']").val() == 'true'){
				$("#visible_"+name+"_setting").show();
			}else{
				$("#visible_"+name+"_setting").hide();
			}
		}

		function XForm_Util_UnitArray(array, sysArray, extArray) {
			array = array.concat(sysArray);
			if (extArray != null) {
				array = array.concat(extArray);
			}
			return array;
		}
		
		function XForm_getXFormDesignerObj_modelingApp(){
			var obj = [];
			var xformId ="${xformId}";
			<%-- // 1 加载主文档的字典 --%>
			var sysObj = _XForm_GetSysDictObj("com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
			if (sysObj != null) {
				//通过xformId获取appmodel 从而获取有无流程标识
				var getIsFlowUrl = Com_Parameter.ContextPath + "sys/modeling/base/sysModelingBehavior.do?method=findModelIsFlowByXformId&xformId=" + xformId;
				$.ajax({
					url: getIsFlowUrl,
					type: "get",
					async: false,
					success: function (data) {
						if(!data){
							for (var i = 0; i <sysObj.length ; i++) {
								var sysItem  = sysObj[i];
								if(sysItem && sysItem.name=="fdProcessEndTime"){
									//无流程表单去掉流程结束时间
									sysObj.splice(i,1);
								}
							}
						}else{
							//#166825
							var fdHandlerObj = [];
							fdHandlerObj.name="LbpmExpecterLog_fdHandler";
							fdHandlerObj.label="${lfn:message('sys-modeling-base:modeling.lbpm.expecterLog_fdHandler')}";
							fdHandlerObj.type="com.landray.kmss.sys.organization.model.SysOrgPerson";
							sysObj.push(fdHandlerObj);
							var fdNodeObj = [];
							fdNodeObj.name="LbpmExpecterLog_fdNode";
							fdNodeObj.label="${lfn:message('sys-modeling-base:modeling.lbpm.expecterLog.fdNode')}";
							fdNodeObj.type="String";
							sysObj.push(fdNodeObj);
						}
					}
				});
			}
			var extObj = null;
			
			<%-- // 2加载扩展表单的字典 --%>
			extObj = _XForm_GetTempExtDictObj("${xformId}");

			return XForm_Util_UnitArray(obj, sysObj, extObj);
		}
		
		// 查询modelName的属性信息
		function _XForm_GetSysDictObj(modelName){
			return Formula_GetVarInfoByModelName(modelName);
		}
		
		// 查找自定义表单的数据字典
		function _XForm_GetTempExtDictObj(tempId) {
			return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId="+tempId).GetHashMapArray();
		}

		</script>
	</template:replace>
</template:include>