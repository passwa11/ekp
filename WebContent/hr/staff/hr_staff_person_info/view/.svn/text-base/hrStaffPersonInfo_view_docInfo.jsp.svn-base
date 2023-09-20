<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
     <div class="lui-personnel-file-staffInfo" id="contractInfo">
         <div class="lui-personnel-file-header-title">
            <div class="lui-personnel-file-header-title-left">
              <div class="lui-personnel-file-header-title-text">${ lfn:message('hr-staff:hrStaffPersonExperience.type.contract') }</div>
            </div>
			 <c:if test="${!readOnly}">
             <div class="lui-personnel-file-edit" onclick="addOrEdit('contract')">
               <span class="lui-personnel-file-add-icon"></span>
               <span class="lui-personnel-file-edit-text">${ lfn:message('button.add') }</span>
             </div>
			 </c:if>
          </div>
        <ui:dataview id="contractInfoList">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=listData&personInfoId=${JsParam.personInfoId}'}
			</ui:source>
			<ui:render type="Template">
			{$
	          <table class="borderTable">
	                <tr>
	                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdBeginDate') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdEndDate') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdName') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContType') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdSignType') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContStatus') }</td>
						<td>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContPeriod') }</td>
						<td>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContUnit') }</td>
	                 	<td>${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdMemo') }</td>
	                 <td width="138">${ lfn:message('list.operation') }</td>
	                </tr>
	         $}
			console.log(data);
	         if(data.length < 1) {
	         	{$
	         		<td colspan="10">${ lfn:message('message.noRecord') }</td>
	         	$}
	         }
	         	for(var i=0; i<data.length; i++) {
	         
	                 {$ <tr>
	                    <td>{% data[i].fdBeginDate %}</td>
	                    <td>{% data[i].fdEndDate %}</td>
	                    <td>{% data[i].fdName %}</td>
	                    <td>{% data[i].fdContType  %}</td>
	                    <td>{% data[i].fdSignType %}</td>
	                    <td>{% data[i].fdContStatus %}</td>
	                  	$}
	                  	if(data[i].fdContractYear || data[i].fdContractMonth){
	                  	{$
						<td>{% data[i].fdContractYear %}年{% data[i].fdContractMonth %}月
						</td>
						$}
						}
						else{
	                  	{$
						<td>无固定期限</td>
						$}
						}
						{$
				  		<td>{% data[i].fdContractUnit %}</td>
				  		<td>
				  		<div class="showTitle" onmouseover="showTitle(event,'{% data[i].fdMemo %}')" onmouseout="hideTitle()">	                    	
				  		{% data[i].fdMemo %}
				  		</div>
				  		</td>
				  	
	                    <td  class="lui-personnel-file-table-btn">
							<c:if test="${!readOnly}">
	                      <span class="lui-personnel-file-table-btn-del" onclick="delDetail('contract', '{% data[i].fdId %}');" >${ lfn:message('button.delete') }</span>
	                      <span class="lui-personnel-file-table-btn-edit" onclick="addOrEdit('contract', '{% data[i].fdId %}');">${ lfn:message('button.edit') }</span>
							</c:if>
						</td>
	                  </tr>
	                  $}
	                  
	            }
	               {$  
	              
	          </table>
	          $}
	      	</ui:render>
		</ui:dataview>
    </div>
