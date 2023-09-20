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
		<div class="hr_contract_batch_renew_wrap">
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
		            <html:form action="/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do">
				<div class="lui_hr_tb_simple_wrap">
					<table class="tb_simple lui_hr_tb_simple">
						<tr>
							<td class="tr_normal_title"><span class="txtstrong">*</span>
								${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdName') }</td>
							<td>
								<xform:text property="fdName" showStatus="edit"  validators="maxLength(200)" value="${hrStaffPersonExperienceContractForm.fdName }"/>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title"><span class="txtstrong">*</span>
								${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContType') }</td>
							<td>
								<xform:select property="fdStaffContTypeId" showPleaseSelect="true" showStatus="edit" value="${hrStaffPersonExperienceContractForm.fdStaffContTypeId }">
									<xform:beanDataSource serviceBean="hrStaffContractTypeService" selectBlock="fdId,fdName" orderBy="fdOrder" />
								</xform:select>
							</td>
						</tr>
						<tr>
							<!-- 签订标识 -->
							<td class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdSignType" />
							</td>
							<td>
								<xform:radio property="fdSignType" value="2" showStatus="readOnly">
									<xform:enumsDataSource enumsType="hrStaffPersonExperienceContract_fdSignType" />
								</xform:radio>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdBeginDate') }
							</td>
							<td>
								<xform:radio property="fdBeginDateType" showStatus="edit" value="1" onValueChange="changeBeginDateType();">
									<xform:enumsDataSource enumsType="hrStaffPersonExperienceContract_fdBeginDateType" />
								</xform:radio>
							</td>
						</tr>
						<tr style="display: none;" class="fdBeginDateTR">
							<td>
							</td>
							<td>
								<xform:datetime property="fdBeginDate" dateTimeType="date" validators="compareDate" showStatus="edit" value="${hrStaffPersonExperienceContractForm.fdBeginDate }"></xform:datetime>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdEndDate') }
							</td>
							<td>
								<xform:datetime property="fdEndDate" dateTimeType="date" validators="compareDate checkLongterm"  showStatus="edit" ></xform:datetime>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract') }
							</td>
							<td>
								<xform:checkbox property="fdIsLongtermContract" value="${hrStaffPersonExperienceContractForm.fdIsLongtermContract}" onValueChange="cancelEndDate" showStatus="edit">
									<xform:simpleDataSource value="true"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdIsLongtermContract.1"/></xform:simpleDataSource>
								</xform:checkbox>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdHandleDate') }
							</td>
							<td>
								<xform:datetime property="fdHandleDate" dateTimeType="date" validators="compareDate" required="true" showStatus="edit" value="${hrStaffPersonExperienceContractForm.fdHandleDate }"></xform:datetime>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonExperience.fdMemo') }
							</td>
							<td>
								<xform:textarea property="fdMemo" style="width:98%;height:50px;" showStatus="edit"/>
							</td>
						</tr>
						<tr>
							<!-- 合同附件 -->
							<td class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.autoHashMap" />
							</td>
							<td colspan="3">
								<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="attHrExpCont"/>
									<c:param name="formBeanName" value="hrStaffPersonExperienceContractForm" />
								</c:import>
							</td>
						</tr>
					</table>
					</div>
					</html:form>
		            <!--放弃入职表格信息Ends-->
		
		            <!--弹框底部按钮 产品标准组件 Starts-->
		            <div class="lui_hr_footer_btnGroup">
			           <ui:button text="${lfn:message('button.ok') }" onclick="clickOk();"></ui:button>
		               <ui:button text="${lfn:message('button.cancel') }" onclick="$dialog.hide(null);" styleClass="lui_toolbar_btn_gray"></ui:button>
		            </div>
		            <!--弹框底部按钮 产品标准组件 Ends-->
			</div>
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
						var ids = $("#ids").val();
						var fdName = $('[name="fdName"]').val();
						var fdEndDate = $('[name="fdEndDate"]').val();
						var fdBeginDate = $('[name="fdBeginDate"]').val();
						var fdIsLongtermContract = $('[name="fdIsLongtermContract"]').val();
						var fdStaffContTypeId = $('select[name="fdStaffContTypeId"] option:selected').val();
						$.ajax({
							url : "${LUI_ContextPath}/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=getBatchRepeatContract",
							type : 'POST',
							data: {
								"ids":ids,
								"fdContType":fdStaffContTypeId,
								"fdName":fdName,
								"fdBeginDate":fdBeginDate,
								"fdEndDate":fdEndDate,
								"fdIsLongtermContract":fdIsLongtermContract
							},
							dataType : 'json',
							success: function(data) {
								if(data.result){
									__isSubmit = true;
									var url = '<c:url value="/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=saveBatchRenew"/>' + "&ids="+ids;
									window.del_load = dialog.loading();
									$.ajax({
										url : url,
										type : 'POST',
										data : $(document.hrStaffPersonExperienceContractForm).serialize(),
										/* dataType : 'json', */
										error : function(data) {
											console.log(data);
											if(window.del_load != null) {
												window.del_load.hide();
											}
											dialog.result(data.responseJSON);
											$dialog.hide('success')
										},
										success: function(data) {
											if(window.del_load != null){
												window.del_load.hide();
												topic.publish("list.refresh");
											}
											dialog.success('<bean:message key="return.optSuccess" />');
											setTimeout(function (){
												window.$dialog.hide("success");
											}, 1500);
										}
									});
								}else{
									dialog.alert(data.error);
								}
							}
						});
					}
				};
				// 勾选长期有效校验
				validation.addValidator('checkLongterm', "${ lfn:message("hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.error") }", function(v, e, o) {
					var result = true;
					var longtermContract = $('[name="fdIsLongtermContract"]').val();
					if(v){
						if(longtermContract == 'true'){
							result = false;
						}
					}
					return result;
				});

				// 长期有效勾选清空到期时间
				window.cancelEndDate = function() {
					var longtermContract = $('[name="fdIsLongtermContract"]').val();
					if(longtermContract == 'true'){
						$('[name="fdEndDate"]').val('');
					}
				}
				
				window.changeBeginDateType = function(){
					var fdBeginDateType = $('input[name="fdBeginDateType"]:checked').val();
					if(fdBeginDateType == '2'){
						$('.fdBeginDateTR').show();
					}else{
						$('.fdBeginDateTR').hide();
					}
				}
			});
		</script>
	</template:replace>
</template:include>