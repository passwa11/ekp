<!-- 新增调动调薪 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<template:include ref="default.dialog">
	<template:replace name="head">
    	<link rel="stylesheet" href="../resource/style/lib/form.css">
    	<link rel="stylesheet" href="../resource/style/hr.css">
    	<script>
    		seajs.use('hr/ratify/hr_staff_concern/js/hrTransferManage_add.js');
    	</script>
	</template:replace>
	<template:replace name="content" >
		<script type="text/javascript">Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|dialog.js", null, "js");</script>
			<html:form action="/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do" styleId="hrStaffTrackRecordForm" styleClass="hr_leave_deal_form">
				<div class="hr_leave_deal_wrap">
			        <!--提示文字Starts-->
			        <!-- alert_info 蓝色底 -->
			        <div class="lui_hr_tip_alert alert_info">
			            <div class="lui_hr_tip_alert_L">
			                <div class="lui_hr_validation_msg">
			                    <i class="lui_hr_validation_icon lui_hr_icon_info_warning">
			                    </i>
			                   	 需要走审批流程？
			                </div>
			            </div>
			            <div class="lui_hr_tip_alert_R lui_hr_tip_opt">
			                <a class="lui_text_primary" onclick="addRatifyTransfer('${param.fdId }');" target="_blank">发起调动审批/</a><a class="lui_text_primary" onclick="addRatifySalary('${param.fdId }');" target="_blank">发起调薪审批</a>
			            </div>
			
			        </div>
			        <!--提示文字Ends-->
			
			        <!--离职人员信息Starts-->
			        <div class="lui_hr_personInfo_wrap">
			            <!--左边图片Starts-->
			            <div class="lui_hr_person_pic">
							<img src="${LUI_ContextPath}/sys/person/resource/images/head${not empty personInfo.fdSex?(personInfo.fdSex eq 'M'?"_man":"_lady"):""}.png"
								 width="48" height="48"/>
			            </div>
			            <!--左边图片Ends-->
			            <!--右边详细Starts-->
			            <div class="lui_hr_person_info">
			                <div class="lui_hr_person_info_content">
			                    <span class="lui_hr_person_name">${personInfo.fdName }</span>
								<span class="lui_hr_sex lui_hr_${not empty personInfo.fdSex?(personInfo.fdSex eq 'M'?"man":"female"):""}"></span>
			                    <!--说明：lui_hr_female是女性的类名，男性的类名把lui_hr_female换成lui_hr_man -->
			                </div>
			                <div class="lui_hr_department">
			                    <span class="lui_hr_depart lui_hr_secondary_txt">${personInfo.fdOrgParent.fdName }</span>&nbsp;|
			                    <span class="lui_hr_status">
			                    	<sunbor:enumsShow enumsType="hrStaffPersonInfo_fdStatus" value="${personInfo.fdStatus }"></sunbor:enumsShow>
			                    </span>
			                </div>
			            </div>
			            <!--右边详细Ends-->
			        </div>
			        <!--离职人员信息Ends-->
			        <table class="tb_simple lui_hr_tb_simple">
				        <tr>
				          <td class="tr_normal_title" width="33.5%">部门</td>
				          <td><span class="lui_hr_secondary_txt">${personInfo.fdOrgParent.fdName }</span></td>
				        </tr>
				        <tr>
				          <td class="tr_normal_title" width="33.5%">岗位</td>
				          <td>
				          	<span class="lui_hr_secondary_txt">
					          	<c:forEach items="${personInfo.fdOrgPosts }" var="fdOrgPost">
					          		${fdOrgPost.fdName }
					          	</c:forEach>
				          	</span>
				          </td>
				        </tr>
				        <tr style="display: none" width="122px">
				          <td class="tr_normal_title">薪资</td>
				          <td><span class="lui_hr_secondary_txt">${currSalary }</span></td>
				        </tr>
				    </table>
				      
				     <!-- 分割线 -->
      				<div class="lui_hr_split"></div>
      				
      				<html:hidden property="fdOrgPersonId"/>
      				<html:hidden property="fdOrgPersonName"/>
      				<html:hidden property="fdPersonInfoId"/>
      				<html:hidden property="fdPersonInfoName"/>
			        <div class="lui_hr_tb_simple_wrap">
			            <table class="tb_simple lui_hr_tb_simple">
			            	<tr>
								<td class="tr_normal_title">
									${ lfn:message('hr-ratify:hrRatify.transfer.person.entranceBeginDate') }</td>
								<td>
									<xform:datetime property="fdEntranceBeginDate" dateTimeType="date" required="true" showStatus="edit" style="width:50%"></xform:datetime>
								</td>
							</tr>
							<tr>
								<td class="tr_normal_title">
									${ lfn:message('hr-ratify:hrRatify.transfer.person.entranceEndDate') }
								</td>
								<td> 
									<xform:datetime property="fdEntranceEndDate" dateTimeType="date" validators="compareEnd" showStatus="edit" style="width:50%"></xform:datetime>
								</td>
							</tr>
			                <tr>
			                    <td class="tr_normal_title">
			                        	异动后部门
			                    </td>
			                    <td>
			                   		<xform:address propertyId="fdRatifyDeptId" propertyName="fdRatifyDeptName" showStatus="edit" orgType="ORG_TYPE_DEPT" style="width:50%"></xform:address>
			                    </td>
			                </tr>
			                <tr>
			                    <td class="tr_normal_title">
			                      		  异动后岗位
			                    </td>
			                    <td>
			                    	<xform:address propertyId="fdOrgPostsIds" propertyName="fdOrgPostsNames" showStatus="edit" orgType="ORG_TYPE_POST" style="width:50%"></xform:address>
			                    </td>
			                </tr>
			                <tr>
			                    <td class="tr_normal_title">
			                      		  异动后薪资
			                    </td>
			                    <td>
			                   		<xform:text property="fdTransSalary" showStatus="edit"  validators="maxLength(200)" style="width:50%"/>
			                    </td>
			
			                </tr>
			                <tr>
			                    <td class="tr_normal_title">
			                    		异动生效日期
			                    </td>
			                    <td>
			                        <xform:datetime property="fdTransDate" dateTimeType="date" showStatus="edit" style="width:50%" required="true"/>
			                    </td>
			                </tr>
			
			            </table>
			        </div>
			
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
				window.addRatifyTransfer = function(userId){
					if(userId){
						dialog.category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docTemplateId','docTemplateName',false,function(rtn){
							if(rtn != false&&rtn != null){
								var tempId = rtn.id;
								var tempName = rtn.name;
								if(tempId !=null && tempId != ''){
									$dialog.hide('null');
									var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=add&i.docTemplate="+tempId+"&userId="+userId;
									Com_OpenWindow(url, '_blank');
								}
							}
						},null,null,null,null,null,'HrRatifyTransferDoc');
					}
				};
				//新建文档
				window.addRatifySalary = function(userId){
					dialog.category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docTemplateId','docTemplateName',false,function(rtn){
						if(rtn != false&&rtn != null){
							var tempId = rtn.id;
							var tempName = rtn.name;
							if(tempId !=null && tempId != ''){
								var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_salary/hrRatifySalary.do?method=add&i.docTemplate="+tempId+"&userId="+userId;
								Com_OpenWindow(url, '_blank');
							}
						}
					},null,null,null,null,null,'HrRatifySalaryDoc');
				};
				//确定
				var __isSubmit = false;
				window.clickOk=function(){
					if(__isSubmit){
						return;
					}
					var fdRatifyDeptId = $('input[name=fdRatifyDeptId]').val();
					var fdOrgPostsIds = $('input[name=fdOrgPostsIds]').val();
					var fdTransSalary = $('input[name=fdTransSalary]').val();
					if( "" == fdTransSalary && "" == fdOrgPostsIds){
						dialog.alert("异动后岗位、异动后薪资不能同时为空！");
						return;
					}
					if(validation.validate()){
						__isSubmit = true;
						submit();
					}
				};
				function submit(){
					var url = '<c:url value="/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=addTransfer"/>';
					$.ajax({
						url : url,
						type : 'POST',
						data : $("#hrStaffTrackRecordForm").serialize(),
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