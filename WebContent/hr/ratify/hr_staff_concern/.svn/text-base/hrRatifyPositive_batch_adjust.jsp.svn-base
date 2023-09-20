<!-- 批量调整试用期or转正日期 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<template:include ref="default.dialog">
	<template:replace name="head">
    	<link rel="stylesheet" href="../resource/style/lib/form.css">
    	<link rel="stylesheet" href="../resource/style/hr.css">
	</template:replace>
	<template:replace name="content" >
		<script type="text/javascript">Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|dialog.js", null, "js");</script>
			<html:form action="/hr/ratify/hr_ratify_positive/hrRatifyPositive.do">
				<div class="hr_batch_adjust_wrap">
				
		            <!--选择人员-筛选 Starts-->
		            <input type="hidden" id="ids" value="${param.ids }" />
		            <input type="hidden" id="type" value="1" />
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
		            
		            <!-- 多标签 -->
		            <div class="lui_hr_tab_frame">
		                <div class="lui_hr_tab_head">
		                    <ul>
		                        <li name="hr_tab_head" class="current" onclick="">${ lfn:message('hr-ratify:hrRatifyPositive.change.fdPositiveTime') }</li>
		                        <li name="hr_tab_head">${ lfn:message('hr-ratify:hrRatifyPositive.change.fdTrialOperationPeriod') }</li>
		                    </ul>
		                </div>
		            </div>
		            
		            <div class="lui_hr_confirm_wrap" name="hr_tab_body">
		                <div class="lui_hr_validation_msg">
		                    <i class="lui_hr_validation_icon lui_hr_icon_warning">
		                    </i>
		                   	  ${ lfn:message('hr-ratify:hrRatifyPositive.change.fdPositiveTime.tips') }
		                </div>
		            </div>
		
		            <div class="lui_hr_confirm_wrap" name="hr_tab_body"  style="display: none;">
		                <div class="lui_hr_validation_msg">
		                    <i class="lui_hr_validation_icon lui_hr_icon_warning">
		                    </i>
		                   	  ${ lfn:message('hr-ratify:hrRatifyPositive.change.fdTrialOperationPeriod.tips') }
		                </div>
		            </div>
		            
		            <table class="tb_simple lui_hr_tb_simple">
		            	<tr name="hr_tab_body_tr">
							<td class="tr_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.plan.fdPositiveTime') }
							<td>
		           				<xform:datetime property="fdPositiveTime" dateTimeType="date" required="true" showStatus="edit" subject="${ lfn:message('hr-staff:hrStaffPersonInfo.fdPositiveTime') }"></xform:datetime>
							</td>
						</tr>
						<tr name="hr_tab_body_tr" style="display: none;">
							<td class="tr_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.fdTrialOperationPeriod') }
							<td>
								<xform:text property="fdTrialOperationPeriod" validators="digits min(0)" showStatus="edit" required="true" subject="${ lfn:message('hr-staff:hrStaffPersonInfo.fdTrialOperationPeriod') }"></xform:text>
							</td>
						</tr>
		            </table>
		            
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
				$(function(){
					validation.removeElements($("input[name='fdTrialOperationPeriod']")[0],"required");
					$("li[name='hr_tab_head']").click(function(){
						$("li[name='hr_tab_head']").attr("class","");
						$("div[name='hr_tab_body']").hide();
						$("tr[name='hr_tab_body_tr']").hide();
						$(this).attr("class","current");
						
						var lis=$(this).parent().children();
						
						//切换【调整转正日期】和【调整试用期】，需要将有校验的提示移除
						var validationAdvice = document.getElementsByClassName("validation-advice");
						if(validationAdvice != '' && validationAdvice != undefined && validationAdvice.length > 0)
						{
							for(var i = 0; i < validationAdvice.length; i++)
							{
								var va = validationAdvice[i];
								if(va != '' && va != undefined)
								{
									va.style.display="none";
								}
							}
							
						}
						for(var i=0;i<lis.length;i++){
							var classValue=$(lis[i]).attr("class");
							if(classValue=="current"){
								var process_bodys=$("div[name='hr_tab_body']");
								$(process_bodys[i]).show();	
								var bodys_tr=$("tr[name='hr_tab_body_tr']");
								$(bodys_tr[i]).show();	
								//切换【调整转正日期】和【调整试用期】,删除和添加校验
								if(i == 0){
									validation.removeElements($("input[name='fdTrialOperationPeriod']")[0],"required");
									validation.addElements($("input[name='fdPositiveTime']")[0],"required");
								}else{
									validation.removeElements($("input[name='fdPositiveTime']")[0],"required");
									validation.addElements($("input[name='fdTrialOperationPeriod']")[0],"required");
								}
								$('#type').val(i+1);
							}
						}
					});
				});
				$(document).ready(function(){
					$("li").click(function(){
						$(this).toggleClass("hover");
					});
				});
				window.removeEntry = function(id){
					var ids = $("#ids").val().split(",");
					var i = $("em i");
					if(ids.length>1){
						var index = ids.indexOf(id);
						if(index > -1){
							ids.splice(index,1);
							$("li.hover").remove();
							i.text(parseInt(i.text())-1);
						}
						$("#ids").val(ids.join(","));
					}else{
						dialog.alert("至少保留一个");
					}
				};
				
				//确定
				var __isSubmit = false;
				window.clickOk=function(){
					if(__isSubmit){
						return;
					}
					if(validation.validate()){
						__isSubmit = true;
						submit();
					}
				};
				function submit(){
					var ids = $("#ids").val();
					var type = $('#type').val();
					var fdPositiveTime = $('input[name=fdPositiveTime]').val();
					var fdTrialOperationPeriod = $('input[name=fdTrialOperationPeriod]').val();
					var data = {
						fdId: ids,
						type: type,
						fdPositiveTime: fdPositiveTime,
						fdTrialOperationPeriod: fdTrialOperationPeriod
					}
					var url = '<c:url value="/hr/ratify/hr_ratify_positive/hrRatifyPositive.do?method=updateBatchAdjust"/>';
					$.ajax({
						url : url,
						type : 'POST',
						data : data,
						dataType : 'json',
						error : function(data) {
							if(window.del_load != null) {
								window.del_load.hide(); 
							}
							dialog.result(data.responseJSON);
						},
						success: function(data) {
							if(window.del_load != null){
								window.del_load.hide(); 
							}
							dialog.result(data);
							setTimeout(function (){
								window.$dialog.hide("success");
							}, 1500);
						}
				   });
				}
			});
		</script>
	</template:replace>
</template:include>