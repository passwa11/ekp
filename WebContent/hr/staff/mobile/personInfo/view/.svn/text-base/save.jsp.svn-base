<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

		<c:if test="${param.canEdit}">
			<div class="file-view-submit">
				<div><bean:message key="button.save"/></div>
			</div>
			<script>
				var url = "<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=updatePersonInfo&fdId=${param.fdId}";
				var viewUrl = "<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${param.fdId}"
				require(["dojo/request","dojo/dom-form","dojo/query","dojo/on","dijit/registry"],function(request,domForm,query,on,registry){
					on(query(".file-view-submit div"),"click",function(){
						var data = domForm.toObject("baseInfoForm");
						var _validatorObj = registry.byId("baseInfoForm");
						data['type']="${param.key}";
						console.log(_validatorObj)
						if(_validatorObj.validate()){
							request.post(url,{data:data}).then(function(v){
								if(v){
									try{
										var res = JSON.parse(v);
										if(res["status"]==true)
											window.location.href=viewUrl;
									}catch(e){
										console.log(e)
									}
								}else{
									
								}
							})
						}
					})
				})
			</script>
		</c:if>