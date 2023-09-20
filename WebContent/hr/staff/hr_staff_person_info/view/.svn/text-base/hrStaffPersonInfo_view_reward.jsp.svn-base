<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
     <div class="lui-personnel-file-staffInfo" id="rewardAndPunishmentInfo">
         <div class="lui-personnel-file-header-title">
            <div class="lui-personnel-file-header-title-left">
              <div class="lui-personnel-file-header-title-text">${ lfn:message('hr-staff:hrStaffPersonExperience.type.bonusMalus') }</div>
            </div>
			 <c:if test="${!readOnly}">
            <div class="lui-personnel-file-edit" onclick="addOrEdit('bonusMalus')">
              <span class="lui-personnel-file-add-icon"></span>
              <span class="lui-personnel-file-edit-text">${ lfn:message('button.add') }</span>
            </div>
			 </c:if>
          </div>
        <ui:dataview id="rewardAndPunishmentInfoList">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_person_experience/bonusMalus/hrStaffPersonExperienceBonusMalus.do?method=listData&personInfoId=${JsParam.personInfoId}'}
			</ui:source>
			<ui:render type="Template">
			{$
	          <table class="borderTable">
	                <tr>
	                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.bonusMalus.fdBonusMalusDate') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.bonusMalus.fdBonusMalusName') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.fdBonusMalusType') }</td>
	                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.fdMemo') }</td>
	                  <td width="137">${ lfn:message('list.operation') }</td>
	                </tr>
	         $}

	         if(data.length < 1) {
	         	{$
	         		<td colspan="5">${ lfn:message('message.noRecord') }</td>
	         	$}
	         }
	         	for(var i=0; i<data.length; i++) {
	                 {$ <tr>
	                 	<td width="138">{% data[i].fdBonusMalusDate %}</td>
	                    <td width="137">{% data[i].fdBonusMalusName %}</td>
	                    <td width="138">{% data[i].fdBonusMalusType %}</td>
	                    <td width="137"><p class="lui-person-file-table-memo">{% data[i].fdMemo %}</p></td>
	                    <td width="100" class="lui-personnel-file-table-btn">
							<c:if test="${!readOnly}">
	                      <span class="lui-personnel-file-table-btn-del" onclick="delDetail('bonusMalus', '{% data[i].fdId %}');" >${ lfn:message('button.delete') }</span>
	                      <span class="lui-personnel-file-table-btn-edit" onclick="addOrEdit('bonusMalus', '{% data[i].fdId %}');">${ lfn:message('button.edit') }</span>
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