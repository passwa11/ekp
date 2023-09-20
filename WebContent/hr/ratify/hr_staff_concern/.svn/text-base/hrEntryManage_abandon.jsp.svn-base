<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<template:include ref="default.dialog">
	<template:replace name="head">
    	<link rel="stylesheet" href="${LUI_ContextPath }/hr/ratify/resource/style/lib/form.css">
    	<link rel="stylesheet" href="${LUI_ContextPath }/hr/ratify/resource/style/hr.css">
	</template:replace>
	<template:replace name="content" >
		<script type="text/javascript">Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|dialog.js", null, "js");</script>
			<html:form action="/hr/staff/hr_staff_entry/hrStaffEntry.do" styleClass="hr_entry_manage_form">
				<div class="hr_entry_manage_wrap">
		            <!--选择人员-筛选 Starts-->
		            <input type="hidden" id="ids" value="${param.ids }" />
		            <div class="lui_hr_criteria_wrap">
		                <em class="lui_text_primary">${ lfn:message('hr-staff:hrStaffEntry.already.choose') }（<i>${fn:length(list) }</i>${ lfn:message('hr-staff:hrStaffEntry.person.index') }）：</em>
		                <ul class="lui_hr_criteria_wrap_list">
		             		<c:forEach items="${list }" var="entry">
		             			<li class="lui_hr_status_selected ">
		             				<a href="javascript:removeEntry('${entry.fdId }');" class="lui_text_primary">
		             					<span class="lui_hr_name">${entry.fdName }</span>
		             					<i class="lui_hr_cancel"></i>
		             				</a>
		             			</li>
		             		</c:forEach>
		                </ul>
		            </div>
		            <!--选择人员Ends-->
		
		            <!--放弃入职表格信息Starts-->
		            <div class="lui_hr_tb_simple_wrap">
		                <table class="tb_simple lui_hr_tb_simple">
		                    <tr>
		                        <td class="tr_normal_title">
		                        	<bean:message bundle="hr-staff" key="hrStaffEntry.fdAbandonReason"/>
		                        </td>
		                        <td>
		                            <xform:select property="fdAbandonReason" showStatus="edit" required="true">
		                            	<xform:beanDataSource serviceBean="hrRatifyLeaveReasonService" selectBlock="fdName" whereBlock="fdType='entry'" orderBy="fdOrder"></xform:beanDataSource>
		                            </xform:select>
		                        </td>
		                    </tr>
		
		                    <tr>
		                        <td class="tr_normal_title">
		                        	<bean:message bundle="hr-staff" key="hrStaffEntry.fdAbandonRemark"/>
		                        </td>
		                        <td>
		                            <xform:text property="fdAbandonRemark" showStatus="edit" required="true"></xform:text>
		                        </td>
		                    </tr>
		                    
		                </table>
		            </div>
		            <!--放弃入职表格信息Ends-->
		
		            <!--弹框底部按钮 产品标准组件 Starts-->
		            <%-- <div class="lui_hr_footer_btnGroup">
			           <ui:button text="${lfn:message('button.ok') }" onclick="clickOk();"></ui:button>
		               <ui:button text="${lfn:message('button.cancel') }" onclick="$dialog.hide(null);" styleClass="lui_toolbar_btn_gray"></ui:button>
		            </div> --%>
		            <!--弹框底部按钮 产品标准组件 Ends-->
		        </div>
			</html:form>
		<script>
			var validation=$KMSSValidation();//校验框架
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				$(document).ready(function(){
					$("li").click(function(){
						$(this).toggleClass("hover");
					});
				});
				window.removeEntry = function(id){
					var ids = $("#ids").val().split(";");
					var i = $("em i");
					if(ids.length>1){
						var index = ids.indexOf(id);
						if(index > -1){
							ids.splice(index,1);
							$("li.hover").remove();
							i.text(parseInt(i.text())-1);
						}
						$("#ids").val(ids.join(";"));
					}else{
						dialog.alert("至少保留一个");
					}
				};
				//确定
				var __isSubmit = false;
				window.clickOk = function(){
					if(__isSubmit){
						return;
					}
					if(validation.validate()){
						__isSubmit = true;
						var url = '<c:url value="/hr/staff/hr_staff_entry/hrStaffEntry.do?method=abandonEntry"/>';
						var ids = $("#ids").val();
						var reason = $("[name='fdAbandonReason']").val();
						var remark = $("[name='fdAbandonRemark']").val();
						var _data = {'ids':ids,'reason':reason,'remark':remark};
						$.ajax({
							url : url,
							type : 'POST',
							data : $.param(_data, true),
							dataType : 'json',
							error : function(data) {
								if(window.del_load != null) {
									window.del_load.hide(); 
								}
								dialog.failure('<bean:message key="return.optFailure" />');
							},
							success: function(data) {
								if(window.del_load != null){
									window.del_load.hide(); 
								}
								dialog.success('<bean:message key="return.optSuccess" />');
								setTimeout(function (){
									window.$dialog.hide("success");
								}, 1500);
							}
					   });
					}
				}
			});
		</script>
	</template:replace>
</template:include>