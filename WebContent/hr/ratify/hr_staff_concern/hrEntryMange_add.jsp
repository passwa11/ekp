<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%
	String qCodeUrl = StringUtil.formatUrl("/hr/ratify/mobile/entry/invite_qr_code/index.html");
	request.setAttribute("qCodeUrl", qCodeUrl);
%>
<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<bean:message bundle="hr-ratify" key="mobile.hrStaffEntry.create" />
	</template:replace>
	<template:replace name="head">
    	<link rel="stylesheet" href="${LUI_ContextPath }/hr/ratify/resource/style/lib/form.css">
    	<link rel="stylesheet" href="${LUI_ContextPath }/hr/ratify/resource/style/hr.css">
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<ui:button text="${lfn:message('button.save')}" 
				onclick="commitMethod('save');" order="1">
			</ui:button>
			<ui:button text="${lfn:message('button.saveadd')}"
				onclick="commitMethod('saveAddMobile');" order="2">
			</ui:button>
			<ui:button text="${lfn:message('button.close')}" order="3" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		
			<html:form action="/hr/staff/hr_staff_entry/hrStaffEntry.do">
				<html:hidden property="fdId" value="${hrStaffEntryForm.fdId}"/>
				<html:hidden property="fdStatus" value="${hrStaffEntryForm.fdStatus}"/>
				  <!--主体内容区starts-->
				  <div class="lui_hr_container">
				
				    <!--内容starts-->
				    <div class="lui_form_content">
				    
				      <!--主体标题starts-->
				      <div class="lui_form_title_frame">
				        <div class="lui_form_subject">
				          	添加待入职员工信息
				        </div>
				      </div>
				      <!--主体标题ends-->
				      
				      <!--步骤starts-->
				      <div class="lui_hr_step_wrap">
				        <div class="lui_hr_step">
				          <!--步骤1 starts-->
				          <div class="lui_hr_step_item lui_hr_step_tail lui_hr_step_process">
				            <div class="lui_hr_step_item_tail"></div>
				            <div class="lui_hr_step_item_head">
				              <span class="lui_hr_step_icon lui_hr_step_icon_add"></span>
				              <span class="lui_hr_step_item_number">1</span>
				            </div>
				            <div class="lui_hr_step_item_main">
				              <span class="lui_text_primary lui_hr_step_item_title">HR提前添加待入职员工信息</span>
				            </div>
				          </div>
				          <!--步骤1 ends-->
				          <!--步骤2 starts-->
				          <div class="lui_hr_step_item lui_hr_step_tail">
				            <div class="lui_hr_step_item_tail"></div>
				            <div class="lui_hr_step_item_head">
				              <span class="lui_hr_step_icon lui_hr_step_icon_submit"></span>
				              <span class="lui_hr_step_item_number">2</span>
				            </div>
				            <div class="lui_hr_step_item_main">
				              <span class="lui_text_primary lui_hr_step_item_title">员工扫码提交入职登记</span>
				            </div>
				          </div>
				          <!--步骤2 ends-->
				          <!--步骤3 starts-->
				          <div class="lui_hr_step_item">
				            <div class="lui_hr_step_item_tail"></div>
				            <div class="lui_hr_step_item_head">
				              <span class="lui_hr_step_icon lui_hr_step_icon_confirm"></span>
				              <span class="lui_hr_step_item_number">3</span>
				            </div>
				            <div class="lui_hr_step_item_main">
				              <span class="lui_text_primary lui_hr_step_item_title">HR确认</span>
				            </div>
				          </div>
				          <!--步骤3 ends-->
				        </div>
				      </div>
				      <!--步骤ends-->
				      
				      <!--待入职员工信息starts-->
				      <div class="lui_text_primary lui_hr_panel_title">待入职员工信息</div>
				      <table class="tb_simple lui_hr_tb_simple">
				        <tr>
				          <td class="tr_normal_title">
				            	${lfn:message('hr-ratify:mobile.hrStaffEntry.fdName')}
				          </td>
				          <td>
					            <div id="_xform_fdName" _xform_type="text">
	                                <xform:text property="fdName" showStatus="edit" style="width:94%;" />
	                            </div>
				          </td>
				          <td class="tr_normal_title">
				            
				            	${lfn:message('hr-ratify:mobile.hrStaffEntry.fdMobileNo')}
				          </td>
				          <td>
				            	<div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdMobileNo" validators="uniqueMobileNo phoneNumber" showStatus="edit" style="width:94%;" />
                                </div>
				          </td>
				        </tr>
				        <tr>
				          <td class="tr_normal_title">
				            
				            	${lfn:message('hr-ratify:mobile.hrStaffEntry.dept')}
				          </td>
				          <td>
				            	<div id="_xform_fdName" _xform_type="address">
	                              	<xform:address propertyId="fdPlanEntryDeptId" propertyName="fdPlanEntryDeptName" required="true" orgType="ORG_TYPE_DEPT" showStatus="edit" style="width:95%;" />
	                          	</div>
				          </td>
				          <td class="tr_normal_title">
				            
				            	${lfn:message('hr-ratify:mobile.hrStaffEntry.post')}
				          </td>
				          <td>
				            <div id="_xform_fdName" _xform_type="address">
                                <xform:address propertyId="fdOrgPostIds" propertyName="fdOrgPostNames" mulSelect="true" orgType="ORG_TYPE_POST" showStatus="edit" style="width:95%;" />
                            </div>
				          </td>
				        </tr>
				        <tr>
				          <td class="tr_normal_title">
				         	
				           	${lfn:message('hr-ratify:mobile.hrStaffEntry.fdPlanEntryTime')}
				          </td>
				          <td>
			            		<div id="_xform_fdName" _xform_type="text">
                                     <xform:datetime property="fdPlanEntryTime" showStatus="edit" style="width:95%;" required="true" />
                                </div>
				          </td>
				          <td>
				          		${lfn:message('hr-ratify:hrStaffEntry.fdEmail') }
				          </td>
				          <td>
				          		<div _xform_type="text">
				          			<xform:text property="fdEmail" showStatus="edit" validators="email" style="width:95%;" />
				          		</div>
				          </td>
				        </tr>
						<tr>
							<!-- 是否允许修改 -->
							<td class="tr_normal_title">
								${lfn:message('hr-staff:hrStaffEntry.fdIsAllowModify') }</td>
							<td colspan="3"><html:radio property="fdIsAllowModify"
									value="true">
									<bean:message bundle="hr-staff"
										key="hrStaffEntry.fdIsAllowModify.yes" />
								</html:radio> <html:radio property="fdIsAllowModify" value="false">
									<bean:message bundle="hr-staff"
										key="hrStaffEntry.fdIsAllowModify.no" />
								</html:radio></td>
						</tr>
					</table>
				      <!--待入职员工信息ends-->
				      
				      <!--入职登记二维码starts-->
				      <div class="lui_text_primary lui_hr_panel_title">入职登记二维码</div>
				      <div class="lui_hr_qrcode_wrap">
				      	<div class="lui_hr_qrcode"></div>
				        <a class="lui_hr_btn lui_hr_btn_qrcode" id="down_qrcode">下载二维码</a>
				      </div>
				      <!--入职登记二维码ends-->
				    </div>
				    <!--内容ends -->
				  </div>
				  <!--主体内容区ends-->
			</html:form>
		<script>
			var validation=$KMSSValidation();//校验框架
			// 验证手机号是否已被注册
			validation.addValidator("uniqueMobileNo", "<bean:message key='sysOrgPerson.error.newMoblieNoSameOldName' bundle='sys-organization' />",
				function(v, e, o) {
					var value = document.getElementsByName("fdMobileNo")[0].value;
					if(startsWith(value, "+86")) {
						// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
						value = value.slice(3, value.length)
					}
					if(startsWith(value, "+")) {
						value = value.replace("+", "x")
					}
					var fdId = document.getElementsByName("fdId")[0].value;
					var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffEntryService&mobileNo="
							+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
					var result = _CheckUnique(url);
					if (!result) 
						return false;
					return true;
				});
			//验证手机号是否合法
			validation.addValidator("phone", '请输入有效的手机号',
				function(v, e, o) {
					if (v=='') {
						return false;
					}
					// 国内手机号可以有+86，但是后面必须是11位数字
					// 国际手机号必须要以+区号开头，后面可以是6~11位数据
					// （国际手机号需加区号，格式为“+区号手机号”或者“+区号-手机号”）
					if(startsWith(v, "+")) {
						if(startsWith(v, "+86")) {
							return /^(\+86)(-)?(\d{11})$/.test(v);
						} else {
							return /^(\+\d{1,5})(-)?(\d{6,11})$/.test(v);
						}
					} else {
						// 没有带+号开头，默认是国内手机号
						return /^(\d{11})$/.test(v);
					}
				});
			// 增加一个字符串的startsWith方法
			function startsWith(value, prefix) {
				return value.slice(0, prefix.length) === prefix;
			}
				
			// 检查是否唯一
			function _CheckUnique(url) {
				var xmlHttpRequest;
				if (window.XMLHttpRequest) { // Non-IE browsers
					xmlHttpRequest = new XMLHttpRequest();
				} else if (window.ActiveXObject) { // IE
					try {
						xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
					} catch (othermicrosoft) {
						try {
							xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
						} catch (failed) {
							xmlHttpRequest = false;
						}
					}
				}
				if (xmlHttpRequest) {
					xmlHttpRequest.open("GET", url, false);
					xmlHttpRequest.send();
					var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
					if (result != "") {
						return false;
					}
				}
				return true;
			}
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				window.commitMethod = function(method){
					Com_Submit(document.hrStaffEntryForm, method);
				};
			});
		</script>
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}hr/ratify/mobile/resource/js/jquery.qrcode.min.js"></script>
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}hr/ratify/mobile/resource/js/rem.js"></script>
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}hr/ratify/mobile/resource/js/dom-to-image.js"></script>
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath}hr/ratify/mobile/resource/js/FileSaver.js"></script>
		<script type="text/javascript">
 			$(document).ready(function () {
 				//获取二维码
		        $(".lui_hr_qrcode").qrcode({
		        	render: "table", //table方式
		        	width: 200, //宽度
		        	height:200, //高度
		        	text: '${qCodeUrl}'//任意内容
		        });
 				$("#down_qrcode").click(function(){
 					var ele = $(this).prev()[0];
 					domtoimage.toBlob(ele)
 				    .then(function (blob) {
 				        window.saveAs(blob, 'qrcode.png');
 				    });
 				});
 			});
		</script>
	</template:replace>
</template:include>