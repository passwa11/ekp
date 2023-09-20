<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
	prefix="person"%>
<%@ page import="com.landray.kmss.util.StringUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.hr.staff.service.IHrStaffContractTypeService,com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract,com.landray.kmss.hr.staff.util.HrStaffPersonUtil" %>

<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/ratify/resource/style/lib/form.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/ratify/resource/style/hr.css">
	</template:replace>
	<template:replace name="content">
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
		<div class="hr_contract_renew_wrap">
			<div class="lui_hr_personInfo_wrap">
				<!--左边图片Starts-->
				<div class="lui_hr_person_pic">
					<img src="${LUI_ContextPath}/sys/person/resource/images/head${not empty hrStaffPersonInfo.fdSex?(hrStaffPersonInfo.fdSex eq 'M'?"_man":"_lady"):""}.png"
						 width="48" height="48"/>
				</div>
				<!--左边图片Ends-->
				<!--右边详细Starts-->
				<div class="lui_hr_person_info">
					<div class="lui_hr_person_info_content">
						<span class="lui_hr_person_name">${hrStaffPersonInfo.fdName}</span>
						<span class="lui_hr_sex lui_hr_${not empty hrStaffPersonInfo.fdSex?(hrStaffPersonInfo.fdSex eq 'M'?"man":"female"):""}"></span>
						<!--说明：lui_hr_female是女性的类名，男性的类名把lui_hr_female换成lui_hr_man -->
					</div>
					<div class="lui_hr_department">
						<span class="lui_hr_depart lui_hr_secondary_txt">${hrStaffPersonInfo.fdOrgParentsName}</span>
						<span class="lui_hr_status"><sunbor:enumsShow value="${ hrStaffPersonInfo.fdStatus }" enumsType="hrStaffPersonInfo_fdStatus" /></span>
					</div>
				</div>
				<!--右边详细Ends-->
			</div>
			<!--离职人员信息Ends-->

			<!--离职表格信息Starts-->
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
								${hrStaffPersonExperienceContractForm.fdContType }
								<html:hidden property="fdStaffContTypeId" value="${hrStaffPersonExperienceContractForm.fdStaffContTypeId }"/>
							</td>
						</tr>
						<tr>
							<!-- 签订标识 -->
							<td class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdSignType" />
							</td>
							<td>
								<html:hidden property="fdSignType" value="2"/>
								<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdSignType.2"/>
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
								<xform:datetime property="fdBeginDate" dateTimeType="date" validators="compareDate" required="true" showStatus="edit" value="${hrStaffPersonExperienceContractForm.fdBeginDate }"></xform:datetime>
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
				<html:hidden property="fdPersonInfoId" />
				<html:hidden property="fdPersonInfoName" />
				<html:hidden property="method_GET" value="add"/>
				<html:hidden property="fdId" />
				<input type="hidden" name="oldContractId" value="${oldContractId }" />
			</html:form>
			<!-- 历史合同信息 Starts -->
		    <div class="lui_hr_accordionpanel_frame">
		      <div class="lui_hr_accordionpanel_header"><span class="lui_text_primary">历史合同信息</span>
		      <!-- <i class="lui_hr_arrow arrow_down"></i> --></div>
		      <div class="lui_hr_accordionpanel_content">
		        <!-- 表格 -->
		        <table class="tb_simple lui_hr_tb_simple">
		        	 <tr>
			            <th>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdName') }</th>
			            <th>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContType') }</th>
			            <th>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdSignType') }</th>
			            <th>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.period') }</th>
			            <th>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContStatus') }</th>
			          </tr>
		        	<c:forEach items="${contracts }" var="contract">
		        		 <tr>
				            <td align="center">${contract.fdName }</td>
				            <td align="center">
				            	<%
									IHrStaffContractTypeService service = (IHrStaffContractTypeService)SpringBeanUtil.getBean("hrStaffContractTypeService");
									HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract)pageContext.getAttribute("contract");
									String fdContType = contract.getFdContType();
									if(service.checkExist(fdContType)){%>
										<c:out value="${contract.fdContType }"></c:out>
									<%}else if(StringUtil.isNotNull(fdContType)){
										String fdRelatedProcess = contract.getFdRelatedProcess();
										String[] str = fdContType.split("\\~");
										if(str.length > 1)
											out.println(HrStaffPersonUtil.getText(str[0], str[1], fdRelatedProcess));
									}
								%>
							</td>
				            <td align="center">
				            	<c:choose>
									<c:when test="${contract.fdSignType eq '1' or contract.fdSignType eq '2'}">
										<sunbor:enumsShow value="${contract.fdSignType }" enumsType="hrStaffPersonExperienceContract_fdSignType" />
									</c:when>
									<c:otherwise>
										<%
											HrStaffPersonExperienceContract experienceContract = (HrStaffPersonExperienceContract)pageContext.getAttribute("contract");
											String fdSignType = experienceContract.getFdSignType();
											if(StringUtil.isNotNull(fdSignType)){
												String fdRelatedProcess = experienceContract.getFdRelatedProcess();
												String[] str = fdSignType.split("\\~");
												if(str.length > 1)
													out.println(HrStaffPersonUtil.getText(str[0], str[1], fdRelatedProcess));
											}
										%>
									</c:otherwise>
								</c:choose>
				            </td>
				            <td align="center">
								<kmss:showDate value="${contract.fdBeginDate}" type="date" />
								至
								<c:choose>
									<c:when test="${contract.fdIsLongtermContract == true }">
										${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.1') }
									</c:when>
									<c:otherwise>
										<kmss:showDate value="${contract.fdEndDate}" type="date" />
									</c:otherwise>
								</c:choose>
							</td>
				            <td align="center">
				            	<c:choose>
									<c:when test="${empty contract.fdContStatus }">
										<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdContStatus.1" />
									</c:when>
									<c:otherwise>
										<sunbor:enumsShow value="${contract.fdContStatus }" enumsType="hrStaffPersonExperienceContract_fdContStatus" />
									</c:otherwise>
								</c:choose>
				            </td>
				          </tr>
		        	</c:forEach>
		        </table>
		      </div>
		    </div>
		    <!-- 历史合同信息 Ends -->
		</div>
		<div class="lui_hr_footer_btnGroup">
            <ui:button text="${lfn:message('hr-ratify:hrRatify.concern.renew.ok') }" onclick="_submit();"></ui:button>
            <ui:button text="${lfn:message('button.cancel') }" onclick="$dialog.hide(null);" styleClass="lui_toolbar_btn_gray"></ui:button>
        </div>
	</template:replace>
</template:include>
<script type="text/javascript">
	// 表单校验
	var _validation = $KMSSValidation();

	seajs.use( [ 'lui/jquery', 'lui/dialog', 'hr/staff/resource/js/dateUtil' ], function($, dialog, dateUtil) {
		// 表单序列化成JSON对象
			$.fn.serializeObject = function() {
				var o = {};
				var a = this.serializeArray();
				$.each(a, function() {
					if (o[this.name] !== undefined) {
						if (!o[this.name].push) {
							o[this.name] = [ o[this.name] ];
						}
						o[this.name].push(this.value || '');
					} else {
						o[this.name] = this.value || '';
					}
				});
				return o;
			};

			// 确认提交
			window._submit = function() {
				if ($KMSSValidation().validate()) {
					//var method = Com_GetUrlParameter(location.href,'method');
					//Com_Submit(document.hrStaffPersonExperienceContractForm,'renewContract');
					var fdName = $('[name="fdName"]').val();
					var fdEndDate = $('[name="fdEndDate"]').val();
					var fdBeginDate = $('[name="fdBeginDate"]').val();
					var fdIsLongtermContract = $('[name="fdIsLongtermContract"]').val();
					var fdStaffContTypeId = $('[name="fdStaffContTypeId"]').val();
					$.ajax({
						url : "${LUI_ContextPath}/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=getRepeatContract",
						type : 'POST',
						data: {
							"personInfoId":"${hrStaffPersonInfo.fdId}",
							"fdContType":fdStaffContTypeId,
							"fdName":fdName,
							"fdBeginDate":fdBeginDate,
							"fdEndDate":fdEndDate,
							"fdIsLongtermContract":fdIsLongtermContract
						},
						dataType : 'json',
						success: function(data) {
							if(data.result){
								$.post("${LUI_ContextPath}/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=saveRenewContract",$(document.hrStaffPersonExperienceContractForm).serialize(),function(data){
									if(data!=""){
										dialog.success('<bean:message key="return.optSuccess" />');
										setTimeout(function (){
											window.$dialog.hide("success");
										}, 1500);
									}else{
										dialog.failure('<bean:message key="return.optFailure" />');
									}
								})
							}else{
								dialog.alert("${ lfn:message('hr-staff:hrStaff.import.error.contract.repeat') }");
							}
						}
					});
				}
			};

			// 取消
			window._cancel = function() {
				window.$dialog.hide();
			};

			var compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error1") }';
			switch('${JsParam.type}'){
			case 'contract':{
				compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error2") }';
				break;
			}
			case 'education':{
				compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error3") }';
				break;
			}
			case 'qualification':{
				compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error4") }';
				break;
			}
			}

			// 日期区间校验
			_validation.addValidator('compareDate', compareDateMsg, function(v, e, o) {
				var fdBeginDate = $('[name="fdBeginDate"]');
				var fdEndDate = $('[name="fdEndDate"]');
				var result = true;
				if (fdBeginDate.val() && fdEndDate.val()) {
					var start = dateUtil.parseDate(fdBeginDate.val());
					var end = dateUtil.parseDate(fdEndDate.val());
					if (start.getTime() > end.getTime()) {
						result = false;
					}
				}
				return result;
			});

			// 勾选长期有效校验
			_validation.addValidator('checkLongterm', "${ lfn:message("hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.error") }", function(v, e, o) {
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