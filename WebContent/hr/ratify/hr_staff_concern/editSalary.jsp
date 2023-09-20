<!-- 新增调动调薪 -->
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
			<html:form action="/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do" styleId="hrStaffTrackRecordForm" styleClass="hr_leave_deal_form">
				<div class="hr_leave_deal_wrap">
			       
			        <!--离职人员信息Starts-->
			        <div class="lui_hr_personInfo_wrap">
			            <!--左边图片Starts-->
			            <div class="lui_hr_person_pic">
			                <img width="48" height="48" src="<person:headimageUrl contextPath='true' personId='${personInfo.fdId }'/>">
			            </div>
			            <!--左边图片Ends-->
			            <!--右边详细Starts-->
			            <div class="lui_hr_person_info">
			                <div class="lui_hr_person_info_content">
			                    <span class="lui_hr_person_name">${personInfo.fdName }</span>
			                    <c:choose>
			                    	<c:when test="${personInfo.fdSex eq 'M' }">
			                    		<span class="lui_hr_sex lui_hr_man"></span>
			                    	</c:when>
			                    	<c:otherwise>
			                    		<span class="lui_hr_sex lui_hr_female"></span>
			                    	</c:otherwise>
			                    </c:choose>
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
				          <td class="tr_normal_title" width="33%">部门</td>
				          <td><span class="lui_hr_secondary_txt">${personInfo.fdOrgParent.fdName }</span></td>
				        </tr>
				        <tr>
				          <td class="tr_normal_title" width="33%">岗位</td>
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
      				
      				<html:hidden property="fdId"/>
      				<html:hidden property="fdOrgPersonId"/>
      				<html:hidden property="fdOrgPersonName"/>
      				<html:hidden property="fdPersonInfoId"/>
      				<html:hidden property="fdPersonInfoName"/>
			        <div class="lui_hr_tb_simple_wrap">
			            <table class="tb_simple lui_hr_tb_simple">
			            	<tr>
			                    <td class="tr_normal_title">
			                      		  异动前薪资
			                    </td>
			                    <td>
			                   		&nbsp;${hrStaffTrackRecordForm.fdBeforSalary}
			                    </td>
			                </tr>
			                <tr>
			                    <td class="tr_normal_title">
			                      		  异动后薪资
			                    </td>
			                    <td>
			                   		<xform:text property="fdTransSalary" showStatus="edit"  validators="maxLength(200)" style="width:50%" required="true" subject="异动后薪资"/>
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
					var url = '<c:url value="/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=updateSalary"/>';
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