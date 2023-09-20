<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>Com_IncludeFile("doclist.js");</script>
<script type="text/javascript">
seajs.use(['lui/dialog'], function(dialog){
	window.dialog=dialog;
});
var topicListIds="";
function showTopicList(){
	var url=Com_GetCurDnsHost()+Com_Parameter.ContextPath + 'km/imeeting/km_imeeting_topic/index_dialog_select.jsp'; 
 	dialog.iframe(url,'<bean:message bundle="km-imeeting" key="kmImeetingAgenda.operation.addDetailTopic.mobile" />',function(ids){
 		if (ids!=""&&ids!=null){
			var data = new KMSSData();
		    var url = "${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=loadTopicList&ids="+ ids;
		    data.SendToUrl(url, function(data) {
		    	var results = eval("(" + data.responseText + ")");
		    	if(results.length>0){
			    	for(var i=0;i<results.length;i++){
				    	if(topicListIds.indexOf(results[i].fdTopicId,0)==-1){
				    		topicListIds +=results[i].fdTopicId +",";
				    		// table 添加逻辑
				    		var newRow = DocList_AddRow("TABLE_DocList");
							if(results[i].docSubject){
								$(newRow).find(".td_subject input").val(results[i].docSubject);
								$(newRow).find(".td_subject").append(formatText(results[i].docSubject));
					    	}
							if(results[i].fdReporterId){
								$(newRow).find(".td_reporter input").val(results[i].fdReporterId);
								$(newRow).find(".td_reporter").append(formatText(results[i].fdReporterName));
					    	}
							if(results[i].fdReporterId){
								$(newRow).find(".td_chargeUnit input").val(results[i].fdChargeUnitId);
								$(newRow).find(".td_chargeUnit").append(formatText(results[i].fdChargeUnitName));
					    	}
							if(results[i].fdAttendUnitIds){
								$(newRow).find(".td_attendUnit input").val(results[i].fdAttendUnitIds);
								$(newRow).find(".td_attendUnit").append(formatText(results[i].fdAttendUnitNames));
					    	}
							if(results[i].fdListenUnitIds){
								$(newRow).find(".td_listenUnit input").val(results[i].fdListenUnitIds);
								$(newRow).find(".td_listenUnit").append(formatText(results[i].fdListenUnitNames));
					    	}
							if(results[i].fdMaterialStaffId){
								$(newRow).find(".td_respons input").val(results[i].fdMaterialStaffId);
								$(newRow).find(".td_respons").append(formatText(results[i].fdMaterialStaffName));
					    	}
							// 附件
							var attIds = "";
					    	var mainAtt = results[i].mainAtt;
					    	var mainHtml = "<table style='border:none'>";
					    	for(var m=0;m<mainAtt.length;m++){
					    		if(mainAtt[m].fdId != ""){
					    			attIds += mainAtt[m].fdId+";";
					    			var fileIcon = window.GetIconNameByFileName(mainAtt[m].fdFileName);
						    		mainHtml +="<tr class='attachment_list'><td style='padding-right:1rem'>";
						    		mainHtml += "<img src='"+Com_Parameter.ResPath+"style/common/fileIcon/"+fileIcon+"' height='16' width='16' border='0' align='absmiddle' style='margin-right:3px;margin-left:3px' />";
						    		mainHtml += Com_HtmlEscape(mainAtt[m].fdFileName);
						    		mainHtml += "</td></tr>";
					    		}
					    	}
					    	mainHtml +="</table>";
					    	var commonAtt = results[i].commonAtt;
					    	var commonHtml = "<table style='border:none'>";
					    	for(var n=0;n<commonAtt.length;n++){
					    		if(commonAtt[n].fdId != ""){
					    			attIds += commonAtt[n].fdId+";";
					    			var fileIcon = window.GetIconNameByFileName(commonAtt[n].fdFileName);
					    			commonHtml +="<tr class='attachment_list'><td style='padding-right:1rem'>";
					    			commonHtml += "<img src='"+Com_Parameter.ResPath+"style/common/fileIcon/"+fileIcon+"' height='16' width='16' border='0' align='absmiddle' style='margin-right:3px;margin-left:3px' />";
					    			commonHtml += Com_HtmlEscape(commonAtt[n].fdFileName);
					    			commonHtml += "</td></tr>";
					    		}
					    	}
					    	commonHtml +="</table>";
					    	$(newRow).find(".td_fromTopic input").val(results[i].fdTopicId);
					    	$(newRow).find(".td_attachment").append(mainHtml+commonHtml);
					    }
			    	}
			    }
			});
		}
		else{
			  return false;
	   }
	 },{width:950,height:550});
}
$(document).on('table-delete',"table[id$='TABLE_DocList']",function(evt, data) {
	var topicObj = $(data).find('.muiTopicId');
	if(topicObj!=null&&topicObj.length>0){
		topicListIds=topicListIds.replace(topicObj[0].value,"");
	}
});

function selectTopicList(){
	showTopicList();
}

function formatText(str) {
    if (str == null || str.length == 0) return "";
    return str
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;")
      .replace(/\'/g, "&#39;")
      .replace(/\"/g, "&quot;")
      .replace(/\n/g, "<br>");
}

function decodeHTML(str) {
    if (str == null || str.length == 0) return "";
    return (str + "")
      .replace(/&quot;/g, '"')
      .replace(/&gt;/g, ">")
      .replace(/&lt;/g, "<")
      .replace(/&amp;/g, "&");
  }
</script>
<table  width=100% id="TABLE_DocList" align="center">
	<tr style="visibility:hidden">
		<td  width="4%" ></td>
		<td  width="96%" ></td>
	</tr>
	<tr KMSS_IsReferRow="1" style="display: none">
		<td   KMSS_IsRowIndex="1" width="4%" class="index_num">
			<i class="meeting-content-num">!{index}</i>
		</td>
  		<td width="96%" >
  			<div class="meeting-content-item">
                <div class="meeting-content-panel">
                  <div>
                    <div class="meeting-view-title clearfix">
                      <p class="meeting-view-title-name td_reporter"><input type='hidden' name='kmImeetingAgendaForms[!{index}].docReporterId'/></p>
                      <div class="meeting-view-title-time clearfix">
                        <p><xform:datetime property="kmImeetingAgendaForms[!{index}].fdExpectStartTime" style="width:200px" required="true"  validators="after valExpectTime" subject="${lfn:message('km-imeeting:kmImeetingAgenda.fdExpectStartTime') }" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingAgenda.fdExpectStartTime') }'"></xform:datetime></p>
                      </div>
                      <p class="meeting-view-title-del" onclick="DocList_DeleteRow_ClearLast();"><bean:message bundle="km-imeeting" key="kmImeetingTopic.delect" /></p>
                      <p class="meeting-view-title-del" onclick="DocList_MoveRow(1);"><bean:message bundle="km-imeeting" key="kmImeetingTopic.moveDown" /></p>
                      <p class="meeting-view-title-del" onclick="DocList_MoveRow(-1);"><bean:message bundle="km-imeeting" key="kmImeetingTopic.moveUp" /></p>
                    </div>
                    <div class="meeting-view-content">
                      <div class="meeting-view-content-top">
                        <div>
			  			  <input type="hidden" name="kmImeetingAgendaForms[!{index}].fdId"/>
		       			  <input type='hidden' name='kmImeetingAgendaForms[!{index}].fdMainId' value='${kmImeetingMainForm.fdId}'/>
                          <p class="meeting-view-content-title td_subject">
                          	 <input type='hidden' name='kmImeetingAgendaForms[!{index}].docSubject' />
                          </p>
                          <div class="meeting-view-content-line">
                            <p class="meeting-view-content-desc"><bean:message bundle="km-imeeting" key="kmImeetingTopic.organizer" />：</p>
                            <p class="meeting-view-content-info td_chargeUnit"><input type='hidden' name='kmImeetingAgendaForms[!{index}].fdChargeUnitId'/></p>
                          </div>
                          <div class="meeting-view-content-line">
                            <p class="meeting-view-content-desc">${lfn:message('km-imeeting:kmImeetingAgenda.docRespons')}：</p>
                            <p class="meeting-view-content-info td_respons"><input type='hidden' name='kmImeetingAgendaForms[!{index}].docResponsId'/></p>
                          </div>
                          <div class="meeting-view-content-line">
                            <p class="meeting-view-content-desc">${lfn:message('km-imeeting:kmImeetingAgenda.attachmentName')}：</p>
                            <span class="td_fromTopic">
								<input type='hidden' name='kmImeetingAgendaForms[!{index}].fdFromTopicId' class="muiTopicId"/>
							</span>
							<p class="meeting-view-content-info td_attachment"></p>
                          </div>
                        </div>
                      </div>
                      <div class="meeting-view-content-bottom">
                        <div class="meeting-view-content-line">
                          <p class="meeting-view-content-desc"><bean:message bundle="km-imeeting" key="kmImeetingTopic.recommended.attendance.units" />：</p>
                          <ul class="meeting-view-content-list clearfix">
                            <li class="td_attendUnit"><input type='hidden' name='kmImeetingAgendaForms[!{index}].fdAttendUnitIds'/></li>
                          </ul>
                        </div>
                        <div class="meeting-view-content-line">
                          <p class="meeting-view-content-desc"><bean:message bundle="km-imeeting" key="kmImeetingTopic.suggested.listening.units" />：</p>
                          <ul class="meeting-view-content-list clearfix">
                            <li class="td_listenUnit"><input type='hidden' name='kmImeetingAgendaForms[!{index}].fdListenUnitIds'/></li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
             </div>
  		</td>
	</tr>
	<%--内容行--%>
	<c:forEach items="${kmImeetingMainForm.kmImeetingAgendaForms}"  var="kmImeetingAgendaitem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" >
			<td KMSS_IsContentRow="1"  width="4%" class="index_num">
				<i class="meeting-content-num">${vstatus.index+1}</i>
			</td>
			<td  width="96%">
				<div class="meeting-content-item">
	                <div class="meeting-content-panel">
	                  <div>
	                    <div class="meeting-view-title clearfix">
	                      <p class="meeting-view-title-name td_reporter" title="${kmImeetingAgendaitem.docReporterName}">
	                      	<c:out value="${kmImeetingAgendaitem.docReporterName}"></c:out>
							<input type="hidden"  name="kmImeetingAgendaForms[${vstatus.index}].docReporterId" value="${kmImeetingAgendaitem.docReporterId}"/>
	                      </p>
	                      <div class="meeting-view-title-time clearfix">
	                        <p><xform:datetime property="kmImeetingAgendaForms[${vstatus.index}].fdExpectStartTime"  style="width:200px" required="true"  validators="after valExpectTime" subject="${lfn:message('km-imeeting:kmImeetingAgenda.fdExpectStartTime') }" showStatus="edit" value="${kmImeetingAgendaitem.fdExpectStartTime}" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingAgenda.fdExpectStartTime') }'"></xform:datetime></p>
	                      </div>
	                      <p class="meeting-view-title-del" onclick="DocList_DeleteRow_ClearLast();"><bean:message bundle="km-imeeting" key="kmImeetingTopic.delect" /></p>
	                      <p class="meeting-view-title-del" onclick="DocList_MoveRow(1);"><bean:message bundle="km-imeeting" key="kmImeetingTopic.moveDown" /></p>
	                      <p class="meeting-view-title-del" onclick="DocList_MoveRow(-1);"><bean:message bundle="km-imeeting" key="kmImeetingTopic.moveUp" /></p>
	                    </div>
	                    <div class="meeting-view-content">
	                      <div class="meeting-view-content-top">
	                        <div>
	                          <input type="hidden" name="kmImeetingAgendaForms[${vstatus.index}].fdId" value="${kmImeetingAgendaitem.fdId}" />
							  <input type="hidden" name="kmImeetingAgendaForms[${vstatus.index}].fdMainId" value="${kmImeetingMainForm.fdId}" />
							  
	                          <p class="meeting-view-content-title td_subject">
	                          	<input type="hidden" name="kmImeetingAgendaForms[${vstatus.index}].docSubject" value="${kmImeetingAgendaitem.docSubject}"/>
	                          	<c:out value="${kmImeetingAgendaitem.docSubject}"></c:out>
	                          </p>
	                          <div class="meeting-view-content-line">
	                            <p class="meeting-view-content-desc"><bean:message bundle="km-imeeting" key="kmImeetingTopic.organizer" />：</p>
	                            <p class="meeting-view-content-info td_chargeUnit">
	                            	<c:out value="${kmImeetingAgendaitem.fdChargeUnitName}"></c:out>
									<input type='hidden' name='kmImeetingAgendaForms[${vstatus.index}].fdChargeUnitId' value="${kmImeetingAgendaitem.fdChargeUnitId}"/>
	                            </p>
	                          </div>
	                          <div class="meeting-view-content-line">
	                            <p class="meeting-view-content-desc">${lfn:message('km-imeeting:kmImeetingAgenda.docRespons')}：</p>
	                            <p class="meeting-view-content-info td_respons">
	                            	<c:out value="${kmImeetingAgendaitem.docResponsName}"></c:out>
									<input type="hidden"  name="kmImeetingAgendaForms[${vstatus.index}].docResponsId" value="${kmImeetingAgendaitem.docResponsId}"/>
	                            </p>
	                          </div>
	                          <div class="meeting-view-content-line">
	                            <p class="meeting-view-content-desc">${lfn:message('km-imeeting:kmImeetingAgenda.attachmentName')}：</p>
	                            <span class="td_fromTopic">
									<input type="hidden" name="kmImeetingAgendaForms[${vstatus.index}].fdFromTopicId" value="${kmImeetingAgendaitem.fdFromTopicId}" class="muiTopicId"/>
								</span>
								<p class="meeting-view-content-info td_attachment">
									<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="ImeetingUploadAtt_${kmImeetingAgendaitem.fdId }" />
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
										<c:param name="fdModelId" value="${kmImeetingMainForm.fdId }" />
										<c:param name="isShowDownloadCount" value="false" />
									</c:import>
								</p>
	                          </div>
	                        </div>
	                      </div>
	                      <div class="meeting-view-content-bottom">
	                        <div class="meeting-view-content-line">
	                          <p class="meeting-view-content-desc"><bean:message bundle="km-imeeting" key="kmImeetingTopic.recommended.attendance.units" />：</p>
	                          <ul class="meeting-view-content-list clearfix">
	                            <li class="td_attendUnit">
	                            	 <c:out value="${kmImeetingAgendaitem.fdAttendUnitNames}"></c:out>
									<input type="hidden"  name="kmImeetingAgendaForms[${vstatus.index}].fdAttendUnitIds" value="${kmImeetingAgendaitem.fdAttendUnitIds}"/>
	                            </li>
	                          </ul>
	                        </div>
	                        <div class="meeting-view-content-line">
	                          <p class="meeting-view-content-desc"><bean:message bundle="km-imeeting" key="kmImeetingTopic.suggested.listening.units" />：</p>
	                          <ul class="meeting-view-content-list clearfix">
	                            <li class="td_listenUnit">
	                            	<c:out value="${kmImeetingAgendaitem.fdListenUnitNames}"></c:out>
									<input type="hidden"  name="kmImeetingAgendaForms[${vstatus.index}].fdListenUnitIds" value="${kmImeetingAgendaitem.fdListenUnitIds}"/>
	                            </li>
	                          </ul>
	                        </div>
	                      </div>
	                    </div>
	                  </div>
	                </div>
	             </div>
	             <script>
				 	topicListIds += "${kmImeetingAgendaitem.fdFromTopicId},";
				</script>
	  		</td>
		</tr>
	</c:forEach>
</table>