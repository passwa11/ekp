<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" >
			<ui:button text="${lfn:message('button.submit')}" onclick="Com_SubmitForm(document.sysHelpModuleMainForm, 'save');">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/help/sys_help_module_main/sysHelpModuleMain.do">
			<p class="txttitle"><bean:message bundle="sys-help" key="sysHelpModule.title"/></p>
			<center>
				<table class="tb_normal" id = "config_tb" width=95%>
				  <tbody id ="config_tbody">
				  	<tr>
					  <td style="width:100%"   colspan="4">
					    <p>
					    <b><bean:message bundle="sys-help" key="sysHelpModule.useingStep"/></b><br>
						   <bean:message bundle="sys-help" key="sysHelpModule.useingStep.info"/>
					    </p>
					  </td>
					</tr>
					<tr>
						<td style="width:25%" class="td_normal_title">
							<bean:message bundle="sys-help" key="sysHelpModule.module"/>
							<span class="txttitle moduleAdd" onclick="addExtendModule()"><bean:message bundle="sys-help" key="sysHelpModule.module.add"/></span>
							<span class="txttitle moduleAdd" onclick="transferModuleConfigurations()">
								<bean:message bundle="sys-help" key="sysHelpModule.module.transfer"/>
							</span>
						</td>
						<td style="width:20%" class="td_normal_title">
							<bean:message bundle="sys-help" key="sysHelpModule.fdIsOpen"/>
						</td>
						<td style="width:20%" class="td_normal_title">
							<bean:message bundle="sys-help" key="sysHelpModule.fdBuzImgIsOpen"/>
						</td>
						<td style="width:35%" class="td_normal_title">
							<bean:message bundle="sys-help" key="sysHelpModule.fdSceneIsOpen"/>
						</td>
					</tr>
					<c:forEach var="configItem" items="${sysHelpModuleMainForm.moduleList}" varStatus="vStatus">
						<tr class="contentTr">
							<td >
								${configItem.fdName}(<c:out value="${configItem.fdModulePath}"/>)
							</td>
							<td class="openConfig">
								<xform:radio property="moduleList[${vStatus.index}].fdIsOpen" showStatus="edit" value="${configItem.fdIsOpen}">
									<xform:enumsDataSource enumsType="common_yesno"></xform:enumsDataSource>
								</xform:radio>
							</td>
							<td class="openConfig">
								<c:if test="${configItem.fdBuzImgIsOpen!=null}">
									<xform:radio property="moduleList[${vStatus.index}].fdBuzImgIsOpen" showStatus="edit" value="${configItem.fdBuzImgIsOpen}">
										<xform:enumsDataSource enumsType="common_yesno"></xform:enumsDataSource>
									</xform:radio>
								</c:if>
							</td>
							<td class="openConfig">
								<xform:radio property="moduleList[${vStatus.index}].fdSceneIsOpen" showStatus="edit" value="${configItem.fdSceneIsOpen}">
									<xform:enumsDataSource enumsType="common_yesno"></xform:enumsDataSource>
								</xform:radio>
								<div>
									<bean:message bundle="sys-help" key="sysHelpModule.fdSceneUrl"/>：
									<xform:text property="moduleList[${vStatus.index}].fdSceneUrl" showStatus="readOnly" style="display:${(configItem.fdCustSceneUrl==null||configItem.fdCustSceneUrl=='')?'inline-block':'none'}"/>
									<xform:text property="moduleList[${vStatus.index}].fdCustSceneUrl" showStatus="readOnly" style="display:${(configItem.fdCustSceneUrl==null||configItem.fdCustSceneUrl=='')?'none':'inline-block'}"/>
								</div>
								<div>
									<span class="txttitle custSceneUrlBtn" onclick="customizeScenesUrl(${vStatus.index})"><bean:message bundle="sys-help" key="sysHelpModule.fdCustSceneUrl"/></span>
								</div>
							</td>
							<html:hidden property="moduleList[${vStatus.index}].fdId"/>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</center>
		</html:form>
		
		<style>
			#config_tb{
				margin-bottom: 30px;
			}
			#config_tb .td_normal_title{
				font-size: 15px;
				font-weight: 400;
				text-align: center;
			}
			#config_tb .moduleAdd,#config_tb .custSceneUrlBtn{
				font-size: 12px;
				font-weight: 400;
				text-decoration: underline;
				cursor: pointer;
			}
			.openConfig label {
				margin-left: 10px;
			}
		</style>
		
		<script>
			var selectedExtendModules = new Array();

			function transferModuleConfigurations(){
				seajs.use(['lui/dialog','lui/jquery'],function(dialog,$){
					dialog.confirm("<bean:message bundle='sys-help' key='sysHelpModule.module.transfer.confirm' />", function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url: '<c:url value="/sys/help/sys_help_module_main/sysHelpModuleMain.do?method=transferConfiguration"/>',
								type: 'GET',
								dataType: 'text',
								success: function(data) {
									if(window.del_load != null){
										window.del_load.hide();
									}
									if(data == 'success') {
										dialog.success("${ lfn:message('return.optSuccess') }");
										window.location.reload();
									}
								},
								error: function(xhr,status,error){
									dialog.failure(error);
								}
							});
						}
					});
				});
			}

			function repeatExtendModules(moduleInfo) {
				var ary = new Array();
				if (!moduleInfo) {
					return ary;
				}
				var mpaths = moduleInfo.split(';');
				if (selectedExtendModules.length == 0) {
					selectedExtendModules = mpaths;
					ary = mpaths;
				}else{
					for (var i = 0; i < mpaths.length; i++) {
						var repeat = false;
						for (var j = 0; j < selectedExtendModules.length; j++) {
							if (mpaths[i] == selectedExtendModules[j]) {
								repeat = true;
								break;
							}
						}
						if (!repeat) {
							selectedExtendModules.push(mpaths[i]);
							ary.push(mpaths[i]);
						}
					}
				}
				return ary;
			}

			function addExtendModule(){
			    var url='/sys/help/sys_help_module_main/sysHelpModuleMain.do?method=importModule';
			    seajs.use(['lui/dialog','lui/jquery'],function(dialog,$){
			        dialog.iframe(url,"${lfn:message('sys-help:sysHelpModule.module.config')}",null, {
			            width:980,
						height:550,
			            buttons:[{
			                name : "${lfn:message('button.ok')}",
							value : true,
							focus : true,
							fn : function(value,_dialog) {
							    var moduleInfo = _dialog.content.iframeObj[0].contentWindow.getSelectModule();
							    if(moduleInfo==""){
							        dialog.alert("${lfn:message('sys-help:sysHelpModule.module.select.oneNeed')}");
							    }else{
									moduleInfo = repeatExtendModules(moduleInfo);
									if (moduleInfo.length == 0) {
										_dialog.hide();
										return;
									}
									moduleInfo = moduleInfo.join(";");
							        $.getJSON(
										"${LUI_ContextPath}/sys/help/sys_help_module_main/sysHelpModuleMain.do?method=updateModule",
										$.param({"moduleInfo":moduleInfo},true),
										function(data) {
											if(data.rtnObj){
												buildContent(data.rtnObj);
												_dialog.hide();
											}
									});
							    }
							    
							}
			            },{
			                name : "${lfn:message('button.cancel')}",
							styleClass:"lui_toolbar_btn_gray iframeBtn",
							value : false,
							fn : function(value, _dialog) {
								_dialog.hide();
							}
			            }]
			        })
			        
			    })
			}
	
			function buildContent(contentList){
			    var existNum = $("#config_tb .contentTr").length;
			    seajs.use([ 'lui/view/Template', 'lui/jquery'],function(Template,$){
			        for(var i = 0;i < contentList.length;i++ ){
			            var item =	contentList[i];
			            var html =   new Template($('#content_templ').html()).render({
			                _fdName : item.fdName,
			                _fdModulePath : item.fdModulePath,
			                _existNum : existNum,
							_item: item
			            })
			            $("#config_tbody").append(html);
			            existNum++;
			        }
			    }) 
			}

			function customizeScenesUrl(rowIndex) {
				var context = '<div><bean:message bundle="sys-help" key="sysHelpModule.fdSceneUrl"/>：<input id="custSceneUrlInput" value="'+$("input[name='moduleList"+"["+rowIndex+"]"+".fdCustSceneUrl']").val()+'" style="width:80%;"/></div><br><div style="text-align: left;">'
						+ '<p><bean:message bundle="sys-help" key="sysHelpModule.fdCustSceneUrl.tip"/></p><br>'
						+ '<p><bean:message bundle="sys-help" key="sysHelpModule.fdCustSceneUrl.tip1"/></p><br>'
						+ '<p><bean:message bundle="sys-help" key="sysHelpModule.fdCustSceneUrl.tip2"/></p><br>'
						+ '<p><bean:message bundle="sys-help" key="sysHelpModule.fdCustSceneUrl.tip3"/></p><br>'
						+ '<p><bean:message bundle="sys-help" key="sysHelpModule.fdCustSceneUrl.tip4"/></p>'
						+ '</div>';
				seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic'], function(jquery, Dialog, topic) {
					var dialog = Dialog.build({
						config: {
							width: 700,
							height: 600,
							// lock: true,
							cache: false,
							title: "${lfn:message('sys-help:sysHelpModule.fdCustSceneUrl')}",
							content: {
								type: "html",
								html: context,
								buttons: [{
									name: "${lfn:message('button.ok')}",
									value: true,
									focus: true,
									fn: function (value, _dialog) {
										var custSceneUrl = $(_dialog.frame[0]).find("#custSceneUrlInput").val().trim();
										var $fdSceneUrl = $("input[name='moduleList"+"["+rowIndex+"]"+".fdSceneUrl']");
										var $fdCustSceneUrl = $("input[name='moduleList"+"["+rowIndex+"]"+".fdCustSceneUrl']");
										$fdSceneUrl.hide();
										$fdCustSceneUrl.hide();
										$fdCustSceneUrl.val(custSceneUrl);
										if (!custSceneUrl || custSceneUrl == '') {
											$fdSceneUrl.show();
											dialog.hide();
											return;
										}
										$fdCustSceneUrl.show();
										dialog.hide();
									}
								}, {
									name: "${lfn:message('button.cancel')}",
									value: false,
									styleClass: 'lui_toolbar_btn_gray',
									fn: function (value, dialog) {
										dialog.hide(value);
									}
								}]
							}
						}
					}).show();
				});
			}
		</script>
		
		<script type="text/template" id="content_templ">
			var itemObj = _item;
			{$
				<tr class="contentTr">
					<td>
						{%_fdName%}
					</td>
					<td class="openConfig">
						$}
						if(itemObj.fdIsOpen == true){
						{$
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdIsOpen" checked value="true"><bean:message key="message.yes"/>
						</label>
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdIsOpen" value="false"><bean:message key="message.no"/>
						</label>
						$}
						}else{
						{$
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdIsOpen" value="true"><bean:message key="message.yes"/>
						</label>
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdIsOpen" checked value="false"><bean:message key="message.no"/>
						</label>
						$}
						}
			{$
					</td>
					<td class="openConfig">
						$}
						if(itemObj.fdBuzImgIsOpen){
							if(itemObj.fdBuzImgIsOpen == null){

							}else{
								if(itemObj.fdBuzImgIsOpen == true){
								{$
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdBuzImgIsOpen" checked value="true"><bean:message key="message.yes"/>
						</label>
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdBuzImgIsOpen" value="false"><bean:message key="message.no"/>
						</label>
								$}
								}else{
								{$
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdBuzImgIsOpen" value="true"><bean:message key="message.yes"/>
						</label>
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdBuzImgIsOpen" checked value="false"><bean:message key="message.no"/>
						</label>
								$}
								}
							}
						}
			{$
					</td>
					<td class="openConfig">
						$}
						if(itemObj.fdSceneIsOpen == true){
						{$
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdSceneIsOpen" checked value="true"><bean:message key="message.yes"/>
						</label>
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdSceneIsOpen" value="false"><bean:message key="message.no"/>
						</label>
						$}
						}else{
						{$
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdSceneIsOpen" value="true"><bean:message key="message.yes"/>
						</label>
						<label>
							<input type="radio" name="moduleList[{%_existNum%}].fdSceneIsOpen" checked value="false"><bean:message key="message.no"/>
						</label>
						$}
						}
			{$
						<div>
							<bean:message bundle="sys-help" key="sysHelpModule.fdSceneUrl"/>：
							$}
							if(itemObj.fdSceneUrl){
							{$
							<xform:text property="moduleList[{%_existNum%}].fdSceneUrl" value="{%itemObj.fdSceneUrl%}" showStatus="readOnly" style="display:inline-block"/>
							<xform:text property="moduleList[{%_existNum%}].fdCustSceneUrl" showStatus="readOnly" style="display:none"/>
							$}
							}else if(itemObj.fdCustSceneUrl){
							{$
							<xform:text property="moduleList[{%_existNum%}].fdSceneUrl" showStatus="readOnly" style="display:none"/>
							<xform:text property="moduleList[{%_existNum%}].fdCustSceneUrl" value="{%itemObj.fdCustSceneUrl%}" showStatus="readOnly" style="display:inline-block"/>
							$}
							}else{
							{$
							<xform:text property="moduleList[{%_existNum%}].fdSceneUrl" showStatus="readOnly" style="display:inline-block"/>
							<xform:text property="moduleList[{%_existNum%}].fdCustSceneUrl" showStatus="readOnly" style="display:none"/>
							$}
							}
			{$
						</div>
						<div>
							<span class="txttitle custSceneUrlBtn" onclick="customizeScenesUrl({%_existNum%})"><bean:message bundle="sys-help" key="sysHelpModule.fdCustSceneUrl"/></span>
						</div>
					</td>
					<input type="hidden" name="moduleList[{%_existNum%}].fdName" value="{%_fdName%}"/>
					<input type="hidden" name="moduleList[{%_existNum%}].fdModulePath" value="{%_fdModulePath%}"/>
				</tr>
			$}
		</script>
	</template:replace>
</template:include>