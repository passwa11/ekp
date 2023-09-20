<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
      <div class="lui-personnel-file-staffInfo" id="familyInfo">
      	<c:choose>
	    <c:when test="${param.print==null }">
         <div class="lui-personnel-file-header-title">
             <div class="lui-personnel-file-header-title-left">
               <div class="lui-personnel-file-header-title-text"><bean:message key="hrStaffPerson.family" bundle="hr-staff"/></div>
             </div>
             <kmss:authShow roles="ROLE_HRSTAFF_EDIT">
				 <c:if test="${!readOnly}">
				 <div class="lui-personnel-file-edit" onclick="addFamilyInfo()">
				   <span class="lui-personnel-file-add-icon"></span>
				   <span class="lui-personnel-file-edit-text">${ lfn:message('button.add') }</span>
				 </div>
				 </c:if>
             </kmss:authShow>
           </div>
	        </c:when>
	        <c:otherwise>
		 		<div class="tr_label_title">
					<div class="title"><bean:message key="hrStaffPerson.family" bundle="hr-staff"/></div>
				</div>
			</c:otherwise>
		   </c:choose>
		   	<div class="lui-split-line"></div>
	        <ui:dataview id="familyInfoList">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_person_family/hrStaffPersonFamily.do?method=listData&personInfoId=${JsParam.personInfoId}'}
			</ui:source>
			<ui:render type="Template">
			{$
	          <table class="borderTable">
	                <tr>
	                  <td>${ lfn:message('hr-staff:hrStaffPerson.family.related') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPerson.family.name') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPerson.family.company') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPerson.family.occupation') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPerson.family.connect') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPerson.family.fdMemo') }</td>
	                  <c:if test="${param.print==null }">
	                  <td width="138">${ lfn:message('list.operation') }</td>
	                  </c:if>
	                </tr>
	         $}

	         if(data.length < 1) {
	         	{$
	         		<td colspan="${param.print==null?7:6 }">${ lfn:message('message.noRecord') }</td>
	         	$}
	         }
	         	for(var i=0; i<data.length; i++) {
	                 {$ <tr>
	                    <td>{% data[i].fdRelated %}</td>
	                    <td>{% data[i].fdName %}</td>
	                    <td>{% data[i].fdCompany  %}</td>
	                    <td>{% data[i].fdOccupation %}</td>
	                    <td>{% data[i].fdConnect %}</td>
	                    <td>{% data[i].fdMemo %}</td>
	                    <c:if test="${param.print==null }">
		                    <td width="138" class="lui-personnel-file-table-btn">
							<c:if test="${!readOnly}">
		                      <span class="lui-personnel-file-table-btn-del" onclick="deleteFamilyInfo('{% data[i].fdId %}');" >${ lfn:message('button.delete') }</span>
		                      <span class="lui-personnel-file-table-btn-edit" onclick="addFamilyInfo('{% data[i].fdId %}');">${ lfn:message('button.edit') }</span>
							</c:if>
		                    </td>
	                    </c:if>
	                  </tr>
	                  $}
	            }
	               {$  
	              
	          </table>
	          $}
	      	</ui:render>
		</ui:dataview>
     </div>
<c:if test="${param.print==null}">
     <script src="${LUI_ContextPath}/hr/staff/resource/js/tableInfo.js"></script>
    <script>
		function addFamilyInfo(id){
			var iframeUrl = "/hr/staff/hr_staff_person_family/hrStaffPersonFamily.do?method=add&fdPersonInfoId=${param.fdId}";
			var url ="${LUI_ContextPath}/hr/staff/hr_staff_person_family/hrStaffPersonFamily.do?method=save"
			var loadFormUrl = ""
			if(id){
				var iframeUrl = "/hr/staff/hr_staff_person_family/hrStaffPersonFamily.do?method=edit&fdPersonInfoId=${param.fdId}&fdId="+id;
				var updateUrl = Com_SetUrlParameter(url,"method",'update');
				addInfo(iframeUrl,{url:updateUrl,title:"<bean:message key="hrStaffPerson.family" bundle="hr-staff"/>",id:id,dataviewId:"familyInfoList"});
				return null;
			}
			addInfo(iframeUrl,{url:url,title:"<bean:message key="hrStaffPerson.family" bundle="hr-staff"/>",dataviewId:"familyInfoList"});
		}
		function deleteFamilyInfo(id){
			 var url = "${LUI_ContextPath}/hr/staff/hr_staff_person_family/hrStaffPersonFamily.do?method=deleteall"
			 deleleInfo({id:id,url:url,dataviewId:"familyInfoList",tip:"<bean:message key="page.comfirmDelete"/>"})
		}
	</script>
</c:if>