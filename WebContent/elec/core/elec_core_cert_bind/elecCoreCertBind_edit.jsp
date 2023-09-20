<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="elecCoreCertBindBusinessForm" value="${requestScope[param.formName]}" />
<style type="text/css">
.lui_paragraph_title {
	font-size: 15px;
	color: #15a4fa;
	padding: 15px 0px 5px 0px;
}

.lui_paragraph_title span {
	display: inline-block;
	margin: -2px 5px 0px 0px;
}

.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
	border: 0px;
	color: #868686
}
</style>
<script type="text/javascript">
				
				
				//弹出选择框
				var commonFuncs = {
						openSelectionDialog:function (params) {
							seajs.use(['lui/jquery','lui/dialog','lang!sys-ui','lang!elec-seal','lui/util/str'],function($,dialog,langUI,langSeal,strutil){
								var buttons=[];
								var rtfield=function(idField, nameField){
									var idObj, nameObj;
									if (idField != null) {
										if (typeof (idField) == "string")
											idObj = document.getElementsByName(idField)[0];
										else
											idObj = idField;
									}
									if (nameField != null) {
										if (typeof (nameField) == "string")
											nameObj = document.getElementsByName(nameField)[0];
										else
											nameObj = nameField;
									}
									return {
										idObj : idObj,
										nameObj : nameObj
									}
								}
								var handleOKFunc=function(value,dialogObj){
									var iframe = dialogObj.element.find('iframe').get(0);
									if(!iframe.contentWindow._getSelectedData){
										return;
									}
									var rtnInfo = iframe.contentWindow._getSelectedData(params.emptyMsg);
									if(rtnInfo==null) {
										return;
									}
									var datas = rtnInfo.data;
									//langSeal['seal.over.empty.num']
									var rtnDatas=[];
									var ids = [];
									var names=[];
									for(var i=0;i<datas.length;i++){
										var rowData = domain.toJSON(datas[i]);
										rtnDatas.push(rowData);
										ids.push($.trim(rowData[rtnInfo.idField]));
										names.push($.trim(rowData[rtnInfo.nameField]));
									}
									var fields = rtfield(params.idField, params.nameField);
									if(fields.idObj){
										fields.idObj.value = ids.join(";");
										$(fields.idObj).trigger('change');
									}
									if(fields.nameObj){
										fields.nameObj.value = names.join(";");
										$(fields.nameObj).trigger('change');
										fields.nameObj.focus();
									}
									if(params.action){
										params.action(rtnDatas);
									}
									dialogObj.hide(value);
								}
					    		if(true==params.multi){
					    			// 添加确定按钮，单选不需要
					    			buttons.push({
										name : langUI['ui.dialog.button.ok'],
										value : true,
										focus : true,
										fn : handleOKFunc,
										styleClass:"selection_dialog_btn_ok lui_toolbar_btn_def"
									})
					    		}
					    		if(!params.hasOwnProperty("showCancel")||params.showCancel==true){
					        		buttons.push({
					        			name : langUI['ui.dialog.button.cancel'],
					        			value : false,
					        			styleClass : 'lui_toolbar_btn_gray',
					        			fn : function (value, dialog) {
					        				dialog.hide(value);
					        			}
					        		})
					    		}
					    		var dialogUrl = '/elec/core/elec_core_cert_bind/selection-dialog.jsp?modelName=' + params.modelName + '&_key=dialog_' + params.idKey+'&props='+(params.props?params.props:'');
					    		if(params.multi==true){
					    			dialogUrl += '&mulSelect=true';
					    		}else{
					    			dialogUrl += '&mulSelect=false';
					    		}
					    		var dialogObj = dialog.build({
					    			config:{
					    				width: 860,
					    				height: 520,
					    				lock: true,
					    				cache: false,
					    				title : params.winTitle||"",
					    				content : {
					    					id : params.idField + '_dialog_div',
					    					scroll : false,
					    					type : "iframe",
					    					url : dialogUrl,
					    					params:null,
					    					buttons:buttons
					    				}
					    			}
					    		})
					    		domain.register('dialog_' + params.idKey,function (){
					    			handleOKFunc(null,dialogObj);
					    		});
					    		window['__dialog_' + params.idKey + '_dataSource'] = function (){
					    			if(params.idField==null){
					    				return strutil.variableResolver(params.source ,params.params);
					    			}else{
					    				return {url:strutil.variableResolver(params.source ,params.params),init:document.getElementsByName(params.idField)[0].value};
					    			}
					    		}
					    		dialogObj.show();
							})
						}
				}
				
				var dialogSelectCaInfo=function(){
					commonFuncs.openSelectionDialog({
						action:function(rtnData){
							if(rtnData.length==1){
								$("input[name='elecCoreCertBindForm.fdDn']").val(rtnData[0]["fdCertDn"]);
	        					$("input[name='elecCoreCertBindForm.fdCertName']").val(rtnData[0]["fdName"]);
	        					//证书载体
	        					var fdCarrier = rtnData[0]["fdCarrier"];
	        					if(fdCarrier == '1'){
	        						//1：软证书；显示密码框，且必填。
	        						$(".password_Tr").show();
	        						var validateValue = $("[name='elecCoreCertBindForm.fdCertPwd']").attr("validate");
	        						var reg = new RegExp("required","g");//g,表示全部替换。
	        						validateValue = validateValue.replace(reg,"");
	        						validateValue += " required";
	        						$("[name='elecCoreCertBindForm.fdCertPwd']").attr("validate", validateValue);
	        					}
	        					if(fdCarrier == '2'){
	        						//2：UKey；隐藏密码框，去除必填校验。
	        						var validateValue = $("[name='elecCoreCertBindForm.fdCertPwd']").attr("validate");
	        						var reg = new RegExp("required","g");//g,表示全部替换。
	        						validateValue = validateValue.replace(reg,"");
	        						$("[name='elecCoreCertBindForm.fdCertPwd']").attr("validate", validateValue);
	        						checkCertPwd();
	        						$(".password_Tr").hide();
	        					}
							}
						},
						multi:false,
						idField:"elecCoreCertBindForm.fdCertId",
						nameField:"elecCoreCertBindForm.fdCertName",
						winTitle:"请选择证书",
						emptyMsg:"",
						modelName:"com.landray.kmss.elec.ca.model.ElecCaInfo",
						source:"/elec/core/elec_core_info/elecCaInfoData.do?method=fdCaInfo&fdRange=${param.fdRange}&fdCertStatus=${param.fdCertStatus}&fdCertType=2&fdOrgCode=${param.fdOrgCode}",
						params:{},
						props:"fdName;fdCertDn;fdCertType.name;fdStartTime;fdEndTime;fdCertStatus.name"
					})
				}
				
				var validation = $KMSSValidation();
				validation.addValidator('checkCertPWd','证书密码错误',function(){
					var pwd = document.getElementsByName("elecCoreCertBindForm.fdCertPwd")[0].value;
					var fdCertId = document.getElementsByName("elecCoreCertBindForm.fdCertId")[0].value;
		            if(!pwd||!fdCertId){
		            	return true;
		            }else{
		            	var validateRes = false;
		            	$.ajax({
							type : "POST",
							dataType : "json",
							async: false,
							url : "${LUI_ContextPath}/elec/core/elec_core_info/elecCaInfoData.do?method=validtaCertPwd",
							data : {
								pwd:pwd,
								fdCertId:fdCertId
							},
							success : function(result) {
								if (result.result) {
									validateRes = true;
								}
							},
							error : function(s, s2, s3) {
								
							}
						});
		            	return validateRes;
		            }
	            }); 
				function checkCertPwd(){
					validation.validateElement($('[name="elecCoreCertBindForm.fdCertPwd"]')[0]);
				}
            </script>
<ui:content title="证书信息"
	expand="true">
	<table class="tb_normal" width="100%">
		<tr>
			<td class="td_normal_title" width="15%">
				请选择证书</td>
			<td width="35%">
				<%-- 证书信息--%>
				<div id="_xform_fdCertIdId" _xform_type="dialog">
					<xform:dialog propertyId="elecCoreCertBindForm.fdCertId" subject="证书信息" propertyName="elecCoreCertBindForm.fdCertName"
						showStatus="edit" style="width:95%;" required="true" htmlElementProperties="onchange='checkCertPwd()'">
                                        dialogSelectCaInfo();
                                        </xform:dialog>
					<br>
				</div>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="15%">
				${lfn:message('elec-core-certification:elecCoreCertBind.fdDn')}</td>
			<td width="35%">
				<%-- 证书Dn号--%>
				<div id="_xform_fdDn" _xform_type="text">
					<xform:text property="elecCoreCertBindForm.fdDn" subject="${lfn:message('elec-core-certification:elecCoreCertBind.fdDn')}" required="true" showStatus="edit" style="width:95%;" />
				</div>
			</td>
		</tr>
		
		<tr class="password_Tr" style="display:none">
		  	<td class="td_normal_title" width="15%">
				证书密码</td>
			<td width="85%">
				<%-- 证书密码--%>
				<div id="_xform_fdCertPassword" _xform_type="text">
										<!--  <xform:text property="fdCertPassword" showStatus="edit" style="width:95%;" required="true"/> -->
										<input name="elecCoreCertBindForm.fdCertPwd" subject="证书密码" class="inputsgl"
											 type="password"
											validate="required maxLength(200) checkCertPWd" style="width: 95%;" /><span class="txtstrong">*</span>
									</div>
			</td>
		</tr>
		
	</table>
</ui:content>
<input type="hidden" name="elecCoreCertBindForm.fdKey"  id="elecCoreCertBindForm.fdKey" value='<c:out value="${param.fdKey}"/>'/>
<input type="hidden" name="elecCoreCertBindForm.fdModelId" id="elecCoreCertBindForm.fdModelId" value='<c:out value="${elecCoreCertBindBusinessForm.fdId}" />' />
<input type="hidden" name="elecCoreCertBindForm.fdModelName" id="elecCoreCertBindForm.fdModelName" value='<c:out value="${elecCoreCertBindBusinessForm.modelClass.name}" />' />
