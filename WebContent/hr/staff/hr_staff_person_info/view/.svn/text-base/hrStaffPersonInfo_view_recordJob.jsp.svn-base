<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
     <div  style="margin-top:15px;table-layout: fixed;" id="trackRecord">
      	<c:choose>
      	<c:when test="${param.print==null }">
         <div class="lui-personnel-file-header-title">
            <div class="lui-personnel-file-header-title-left">
              <div class="lui-personnel-file-header-title-text"><bean:message key="hrStaffTrackRecord.record" bundle="hr-staff"/></div>
            </div>
			 <c:if test="${!readOnly}">
            	<div class="lui-personnel-file-edit" onclick="addTrackInfo()">
               	<span class="lui-personnel-file-add-icon"></span>
               	<span class="lui-personnel-file-edit-text">${ lfn:message('button.add') }</span>
             	</div>
			 </c:if>
          </div>
        </c:when>
        <c:otherwise>
	 		<div class="tr_label_title">
				<div class="title"><bean:message key="hrStaffTrackRecord.record" bundle="hr-staff"/></div>
			</div>
		</c:otherwise>
	   </c:choose>
	   <div class="lui-split-line"></div>
	   
        <ui:dataview id="trackRecordList" style="overflow-x:auto !important; 
">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=listData&personInfoId=${JsParam.personInfoId}'}
			</ui:source>
			<ui:render type="Template">
			{$
					<div id="showHoverTitle" class="showHoverTitle" >测试内容测试内容测试内容测试内容测试内容</div>
	          <table class="borderTable1" >
	                <tr>
	                  <td>${lfn:message('hr-staff:hrStaffTrackRecord.startTime')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffTrackRecord.finishTime')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffPersonInfo.fdStaffingLevel')}</td>
	                  <td>${lfn:message('hr-staff:mobile.hr.staff.list.5')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts')}</td>
	                  <td>${ lfn:message('hr-staff:hrStaffTrackRecord.fdType')}</td>
	                  <td>${lfn:message('hr-organization:hrOrganizationConPost.fdType')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffPersonExperience.fdMemo')}</td>
	                   <td>${lfn:message('hr-staff:hrStaffTrackRecord.fdStartDateOfInternship')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffTrackRecord.fdEndDateOfInternship')}</td>
	                   <td>${lfn:message('hr-staff:hrStaffTrackRecord.fdIsInspection')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffTrackRecord.fdInternshipStartDate')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffTrackRecord.fdInternshipEndDate')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffTrackRecord.fdIsSecondEntry')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffTrackRecord.fdAppointmentCategory')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffTrackRecord.fdContractChangeRecord')}</td>
	                  <td>${lfn:message('hr-staff:hrStaffTrackRecord.fdChangeType')}</td>
	                  <c:if test="${param.print==null }">
	                  <td>${lfn:message('hr-staff:hrStaff.employee.operating')}</td>
	                  </c:if>
	                </tr>
	         $}

		         if(data.length < 1) {
		         	{$
		         		<td colspan="${param.print==null?9:6 }">${ lfn:message('message.noRecord') }</td>
		         	$}
		         }
		         console.log(data);
	         	for(var i=0; i<data.length; i++) {
	                 {$ <tr>
	                    <td width="137">{% data[i].fdEntranceBeginDate %}</td>
	                    <td width="138">{% data[i].fdEntranceEndDate %}</td>
	                    <td width="138">
	                    	<div class="showTitle" onmouseover="showTitle(event,'{% data[i].fdStaffingLevel %}')" onmouseout="hideTitle()">
                   		 		{% data[i].fdStaffingLevel %}
	                   		</div>
	                    </td>
	                    <td width="138" >
	                    	<div class="showTitle" onmouseover="showTitle(event,'{% data[i].fdRatifyDept %}')" onmouseout="hideTitle()">
	                    		{% data[i].fdRatifyDept %}
                    		</div>
                    	</td>
	                    <td width="138">
	                    	<div class="showTitle" onmouseover="showTitle(event,'{% data[i].fdType %}')" onmouseout="hideTitle()">
	                    		{% data[i].fdPosts %}
	                    	</div>
	                    </td>
	                    <td width="138">
	                    	<div class="showTitle" onmouseover="showTitle(event,'{% data[i].fdType %}')" onmouseout="hideTitle()">
	                    		{% data[i].fdTypeName %}
	                    	</div>
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdStatusName %}
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdMemo %}
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdStartDateOfInternship %}
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdEndDateOfInternship %}
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdIsInspection %}
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdInternshipStartDate %}
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdInternshipEndDate %}
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdIsSecondEntry %}
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdAppointmentCategory %}
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdContractChangeRecord %}
	                    </td>
	                    <td width="138">
	                    	{% data[i].fdChangeType %}
	                    </td>
	                   <c:if test="${param.print==null }">
		                    <td width="137" class="lui-personnel-file-table-btn">
		               $}
		               {$
								<c:if test="${!readOnly}">
								<span class="lui-personnel-file-table-btn-del" onclick="deleteTrackInfo( '{% data[i].fdId %}');" >${ lfn:message('button.delete') }</span>
		                       	<%-- <span class="lui-personnel-file-table-btn-edit" onclick="addTrackInfo('{% data[i].fdId %}');">${ lfn:message('button.edit') }</span> --%>
								</c:if>
								$}
		                       {$ 
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
    <div id="showHoverTitle"></div>
     <script src="${LUI_ContextPath}/hr/staff/resource/js/tableInfo.js"></script>
  	<script>
	function addTrackInfo(id){
		var iframeUrl = "/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=add&fdPersonInfoId=${param.fdId}";
		var url ="${LUI_ContextPath}/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=save"
		var loadFormUrl = ""
		if(id){
			var iframeUrl = "/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=edit&fdPersonInfoId=${param.fdId}&fdId="+id;
			var updateUrl = Com_SetUrlParameter(url,"method",'update');
			addInfo(iframeUrl,{url:updateUrl,title:"${lfn:message('hr-staff:hrStaffTrackRecord.record')}",id:id,dataviewId:"trackRecordList"});
			return null;
		}
		addInfo(iframeUrl,{url:url,title:"${lfn:message('hr-staff:hrStaffTrackRecord.record')}",dataviewId:"trackRecordList"});
	}
	function deleteTrackInfo(id){
		 var url = "${LUI_ContextPath}/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=deleteall"
		 deleleInfo({id:id,url:url,dataviewId:"trackRecordList",tip:"<bean:message key="page.comfirmDelete"/>"})
	}
	
	function showTitle(e,v){ 
		var title =$("#showHoverTitle");
		title.html(v);
		title.show();
		title.css('left',e.clientX+"px");
		title.css('top',e.clientY+"px") 
	}
	
	function hideTitle(){
		$("#showHoverTitle").hide(); 
	}
	
  	</script> 
  	<style>
  		.showTitle{
  		    height: 30px;
		    line-height: 30px;
		    overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
    	}
  		.showHoverTitle{
  			 background-color: aliceblue;
  			position: absolute;
  			display: none; 
  			z-index:1030;
  			border: 1px solid #CCCCCC;
  			padding:0px 10px ;
  		}
  		.borderTable1{
  min-width:780px;
  max-width:980px;
  border: 1px solid #e3e3e3;
  table-layout: fixed;
  border-right:0;
  border-bottom: 0;
  border-collapse: collapse;
}
.borderTable1 thead {
  background: #eee;
  color:#666;
  font-weight: normal;
}
.lui-personnel-file table.borderTable1 tbody tr td{
  color:#333;
}
.borderTable1 tr:first-child{
	background-color:#eee;
}
.borderTable1 thead th,
.borderTable1 tbody td{
    height:60px;
    text-align: center;
    font-weight: normal;
    word-break:break-all;
    border-bottom: 1px solid #e3e3e3;
    border-right: 1px solid #e3e3e3;
    box-sizing: border-box;
    line-height: 60px;;
}
  	</style>
