<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

		<c:if test="${param.canEdit}">
			<div class="file-view-submit">
				<div><bean:message key="button.save"/></div>
			</div>
			<script>
				var url = "<%=request.getContextPath()%>${param.url}";
				var viewUrl = "<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${param.personInfoId}";
				var repeatContractUrl = "<%=request.getContextPath()%>/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=getRepeatContract";
				var checkNum = 0;
				require(["dojo/request","dojo/dom-form","dojo/query","dojo/on","dijit/registry","mui/dialog/Tip"],function(request,domForm,query,on,registry,Tip){
					on(query(".file-view-submit div"),"click",function(){
						var data = domForm.toObject("baseInfoForm");
						var _validatorObj = registry.byId("baseInfoForm");
						if(_validatorObj.validate() && checkNum == 0){
							var fdName = $('[name="fdName"]').val();
							var fdEndDate = $('[name="fdEndDate"]').val();
							var fdBeginDate = $('[name="fdBeginDate"]').val();
							var fdIsLongtermContract = $('[name="fdIsLongtermContract"]').val();
							var fdContType = $('[name="fdContType"]').val();
							request.post(repeatContractUrl,{
								handleAs: "json",
								data:{
									"personInfoId":"${param.personInfoId}",
									"fdId":"${param.fdId }",
									"fdContType":fdContType,
									"fdName":fdName,
									"fdBeginDate":fdBeginDate,
									"fdEndDate":fdEndDate,
									"fdIsLongtermContract":fdIsLongtermContract
								}
							}).then(function (result) {
								if (result.result){
									checkNum++;
									request.post(url,{data:data}).then(function(v){
										if(v){
											try{
												window.location.href=viewUrl;
											}catch(e){
												console.log(e)
											}
										}else{

										}
									})
								}else{
									Tip.warn({text: '<bean:message key="hrStaff.import.error.contract.repeat" bundle="hr-staff"/>'});
								}
							})
						}
					})
				})
			</script>
		</c:if>