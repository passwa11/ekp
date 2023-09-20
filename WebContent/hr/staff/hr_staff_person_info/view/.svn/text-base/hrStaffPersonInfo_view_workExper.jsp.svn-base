<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
     <div class="lui-personnel-file-staffInfo" id="personnelExperience">
         <div class="lui-personnel-file-header-title">
            <div class="lui-personnel-file-header-title-left">
              <div class="lui-personnel-file-header-title-text">${ lfn:message('hr-staff:table.hrStaffPersonExperience') }</div>
            </div>
          </div>
        	<div class="hr-staff-workexper-content">
       			<div class="hr-staff-workexper-header">
       				<span class="hr-staff-workexper-header-flag"></span><span class="hr-staff-workexper-header-text">${ lfn:message('hr-staff:hrStaffPersonExperience.type.work') }</span>
       				<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
						<c:if test="${!readOnly}">
						<div class="lui-personnel-file-edit" onclick="addOrEdit('work')">
						   <span class="lui-personnel-file-add-icon"></span>
						   <span class="lui-personnel-file-edit-text">${ lfn:message('button.add') }</span>
						</div>
						</c:if>
		            </kmss:authShow>
       			</div>
	        	<ui:dataview id="work">
					<ui:source type="AjaxJson">
						{url:'/hr/staff/hr_staff_person_experience/work/hrStaffPersonExperienceWork.do?method=listData&personInfoId=${JsParam.personInfoId}'}
					</ui:source>
					<ui:render type="Template">
					{$
			          <table class="borderTable">
			                <tr>
			                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.fdBeginDate') }</td>
			                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.fdEndDate') }</td>
			                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdCompany') }</td>
			                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdPosition') }</td>
			                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdDescription') }</td>
			                  <td>${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdReasons') }</td>
			                  <td width="137">${ lfn:message('list.operation') }</td>
			                </tr>
			         $}
		
			         if(data.length < 1) {
			         	{$
			         		<td colspan="7">${ lfn:message('message.noRecord') }</td>
			         	$}
			         }
			         	for(var i=0; i<data.length; i++) {
			                 {$ <tr>
			                    <td width="137">{% data[i].fdBeginDate %}</td>
			                    <td width="138">{% data[i].fdEndDate %}</td>
			                    <td width="137">{% data[i].fdCompany %}</td>
			                    <td width="138">{% data[i].fdPosition %}</td>
			                    <td ><p class="lui-person-file-table-memo">{% data[i].fdDescription %}</p></td>
			                    <td ><p class="lui-person-file-table-memo">{% data[i].fdReasons %}</p></td>
			                    <td width="170" class="lui-personnel-file-table-btn">
									<c:if test="${!readOnly}">
									  <span class="lui-personnel-file-table-btn-del" onclick="delDetail('work', '{% data[i].fdId %}');" >${ lfn:message('button.delete') }</span>
									  <span class="lui-personnel-file-table-btn-edit" onclick="addOrEdit('work', '{% data[i].fdId %}');">${ lfn:message('button.edit') }</span>
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
			<div class="hr-staff-workexper-content">
				<div class="hr-staff-workexper-header">
       				<span class="hr-staff-workexper-header-flag"></span><span class="hr-staff-workexper-header-text">${ lfn:message('hr-staff:hrStaffPersonExperience.type.education') }</span>
       				<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
						<c:if test="${!readOnly}">
						<div class="lui-personnel-file-edit" onclick="addOrEdit('education')">
						   <span class="lui-personnel-file-add-icon"></span>
						   <span class="lui-personnel-file-edit-text">${ lfn:message('button.add') }</span>
						</div>
						</c:if>
		            </kmss:authShow>
       			</div>
					<ui:dataview id="education">
						<ui:source type="AjaxJson">
							{url:'/hr/staff/hr_staff_person_experience/education/hrStaffPersonExperienceEducation.do?method=listData&personInfoId=${JsParam.personInfoId}'}
						</ui:source>
						<ui:render type="Template">
							{$
							<table class="borderTable">
								<tr>
									<td>${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdBeginDate') }</th>
									<td>${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdEndDate') }</th>								
									<td width="137">${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdSchoolName') }</th>
									<td>${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdMajor') }</th>
									<td>${ lfn:message('hr-staff:hr.staff.tree.education') }</th>
									<td>${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdDegree') }</th>
									<td>${ lfn:message('hr-staff:hrStaffPersonExperience.fdMemo') }</th>
									<c:if test="${!param.isPrint}">
									<td width="137">${ lfn:message('list.operation') }</th>
									</c:if>
								</tr>
							$}
						
							if(data.length < 1) {
							{$
							<tr>
								<c:if test="${!param.isPrint}">
								<td colspan="8">
								</c:if>
								<c:if test="${param.isPrint}">
								<td colspan="7">
								</c:if>
									${ lfn:message('message.noRecord') }
								</td>
							</tr>
							$}
							}
							for(var i=0; i<data.length; i++) {
								{$
								<tr>
									<td>{% data[i].fdBeginDate %}</td>
									<td>{% data[i].fdEndDate %}</td>								
									<td>
										{% data[i].fdSchoolName %}
									</td>
									<td>{% data[i].fdMajor %}</td>
									<td>{% data[i].fdEducation %}</td>
									<td>{% data[i].fdDegree %}</td>

									<td width="137"><p class="lui-person-file-table-memo">{% data[i].fdMemo %}</p></td>
									<c:if test="${!param.isPrint}">
										<td width="137"  class="lui-personnel-file-table-btn">
											<c:if test="${!readOnly}">
											<span class="lui-personnel-file-table-btn-del" onclick="delDetail('education', '{% data[i].fdId %}');" title="${ lfn:message('button.delete') }">${ lfn:message('button.delete') }</span>
											<span class="lui-personnel-file-table-btn-edit" onclick="addOrEdit('education', '{% data[i].fdId %}');" title="${ lfn:message('button.edit') }">${ lfn:message('button.edit') }</span>
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
			<div class="hr-staff-workexper-content">
				<div class="hr-staff-workexper-header">
       				<span class="hr-staff-workexper-header-flag"></span><span class="hr-staff-workexper-header-text">${ lfn:message('hr-staff:hrStaffPersonExperience.type.training') }</span>
       				<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
						<c:if test="${!readOnly}">
						<div class="lui-personnel-file-edit" onclick="addOrEdit('training')">
						   <span class="lui-personnel-file-add-icon"></span>
						   <span class="lui-personnel-file-edit-text">${ lfn:message('button.add') }</span>
						</div>
						</c:if>
		            </kmss:authShow>	       				       			
       			</div>
				<ui:dataview id="training">
					<ui:source type="AjaxJson">
						{url:'/hr/staff/hr_staff_person_experience/training/hrStaffPersonExperienceTraining.do?method=listData&personInfoId=${JsParam.personInfoId}'}
					</ui:source>
					<ui:render type="Template">
						{$
						<table class="borderTable">
							<tr>
								<td>${ lfn:message('hr-staff:hrStaffPersonExperience.fdBeginDate') }</th>
								<td>${ lfn:message('hr-staff:hrStaffPersonExperience.fdEndDate') }</th>							
								<td>${ lfn:message('hr-staff:hrStaffPersonExperience.training.fdTrainingName') }</th>
								<td>${ lfn:message('hr-staff:hrStaffPersonExperience.training.fdTrainingUnit') }</th>
								<td>${ lfn:message('hr-staff:hrStaffPersonExperience.training.fdCertificate') }</th>
								<td>${ lfn:message('hr-staff:hrStaffPersonExperience.fdMemo') }</th>
								<c:if test="${!param.isPrint}">
								<td width="137">${ lfn:message('list.operation') }</th>
								</c:if>
							</tr>
						$}
					
						if(data.length < 1) {
						{$
						<tr>
							<c:if test="${!param.isPrint}">
							<td colspan="7">
							</c:if>
							<c:if test="${param.isPrint}">
							<td colspan="6">
							</c:if>
								${ lfn:message('message.noRecord') }
							</td>
						</tr>
						$}
						}
						for(var i=0; i<data.length; i++) {
							{$
							<tr>
								<td>{% data[i].fdBeginDate %}</td>
								<td>{% data[i].fdEndDate %}</td>							
								<td>
									{% data[i].fdTrainingName %}
								</td>
								<td>{% data[i].fdTrainingUnit %}</td>
								<td>{% data[i].fdCertificate %}</td>

								<td><p class="lui-person-file-table-memo">{% data[i].fdMemo %}</p></td>
								<c:if test="${!param.isPrint}">
								<td width="137" class="lui-personnel-file-table-btn">
									<c:if test="${!readOnly}">
									<span class="lui-personnel-file-table-btn-del" onclick="delDetail('training', '{% data[i].fdId %}');" title="${ lfn:message('button.delete') }">${ lfn:message('button.delete') }</span>
									<span class="lui-personnel-file-table-btn-edit" onclick="addOrEdit('training', '{% data[i].fdId %}');" title="${ lfn:message('button.edit') }">${ lfn:message('button.edit') }</span>
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
			<div class="hr-staff-workexper-content">
				<div class="hr-staff-workexper-header">
       				<span class="hr-staff-workexper-header-flag"></span><span class="hr-staff-workexper-header-text">${ lfn:message('hr-staff:hrStaffPersonExperience.type.qualification') }</span>
       				<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
						<c:if test="${!readOnly}">
						<div class="lui-personnel-file-edit" onclick="addOrEdit('qualification')">
						   <span class="lui-personnel-file-add-icon"></span>
						   <span class="lui-personnel-file-edit-text">${ lfn:message('button.add') }</span>
						</div>
						</c:if>
		            </kmss:authShow>
       			</div>
				<ui:dataview id="qualification">
					<ui:source type="AjaxJson">
						{url:'/hr/staff/hr_staff_person_experience/qualification/hrStaffPersonExperienceQualification.do?method=listData&personInfoId=${JsParam.personInfoId}'}
					</ui:source>
					<ui:render type="Template">
						{$
						<table class="borderTable">
							<tr>
								<td >${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdBeginDate') }</th>
								<td >${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdEndDate') }</th>							
								<td >${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdCertificateName') }</th>
								<td >${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdAwardUnit') }</th>
								<c:if test="${!param.isPrint}">
								<td width="137"><span>${ lfn:message('list.operation') }</span></th>
								</c:if>
							</tr>
						$}
					
						if(data.length < 1) {
						{$
						<tr>
							<c:if test="${!param.isPrint}">
							<td colspan="5">
							</c:if>
							<c:if test="${param.isPrint}">
							<td colspan="4">
							</c:if>
								${ lfn:message('message.noRecord') }
							</td>
						</tr>
						$}
						}
						for(var i=0; i<data.length; i++) {
							{$
							<tr>
								<td>{% data[i].fdBeginDate %}</td>
								<td>{% data[i].fdEndDate %}</td>							
								<td>
									<span class="com_subject">{% data[i].fdCertificateName %}</span>
								</td>
								<td>{% data[i].fdAwardUnit %}</td>
								<c:if test="${!param.isPrint}">
								<td width="137" class="lui-personnel-file-table-btn">
									<c:if test="${!readOnly}">
									<span class="lui-personnel-file-table-btn-del" onclick="delDetail('qualification', '{% data[i].fdId %}');" title="${ lfn:message('button.delete') }">${ lfn:message('button.delete') }</span>
									<span class="lui-personnel-file-table-btn-edit" onclick="addOrEdit('qualification', '{% data[i].fdId %}');" title="${ lfn:message('button.edit') }">${ lfn:message('button.edit') }</span>
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

    </div>

