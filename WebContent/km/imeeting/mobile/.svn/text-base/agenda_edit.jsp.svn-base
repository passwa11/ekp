<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">Com_IncludeFile("doclist.js");</script>
<script type="text/javascript">DocList_Info.push('TABLE_DocList');</script>
<div class="muiFlowInfoW" style="border-bottom: 0;">
	<table width="100%" cellpadding="0" cellspacing="0" id="TABLE_DocList">
		<tr  data-dojo-type="mui/form/Template"  KMSS_IsReferRow="1" style="display:none;" border='0'>
			<td class="detail_wrap_td">
				<table class="muiSimple" style="table-layout: auto;">
					<tr celltr-title="true">
						<td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
							<div class="muiDetailDisplayOpt muiDetailDown" onclick="expandRow(this);"></div>
							<span class="index" onclick="expandRow(this);" style="color: #4285F4;">
								!{index}
							</span>
							<span class="agendaSubject" onclick="expandRow(this);">
							</span>
							<xform:editShow>
								<div class="muiDetailTableDel" onclick="deleteRow(this);" style="color: #4285F4;">
									<bean:message key="button.delete" />
								</div>
							</xform:editShow>
						</td>
					</tr>
					<!-- 会议议题 -->
					<tr data-celltr="true">
						<td class="muiTitle" width="38%">
							${lfn:message('km-imeeting:kmImeetingAgenda.docSubject')}
						</td>
						<td width="62%">
							<xform:config  orient="none">
								<xform:text property="kmImeetingAgendaForms[!{index}].fdId" showStatus="noShow"  mobile="true"></xform:text>
								<xform:text property="kmImeetingAgendaForms[!{index}].fdMainId" showStatus="noShow"  mobile="true"></xform:text>
								<xform:text property="kmImeetingAgendaForms[!{index}].docSubject" showStatus="edit" htmlElementProperties="class='title' placeholder='${lfn:message('km-imeeting:kmImeetingAgenda.docSubject.mobile') }'" subject="${lfn:message('km-imeeting:kmImeetingAgenda.docSubject')}" required="true" validators="maxLength(200)" mobile="true"></xform:text>
							</xform:config>
						</td>
					</tr>
					<!-- 汇报人 -->
					<tr data-celltr="true">
						<td class="muiTitle" width="38%">
							${lfn:message('km-imeeting:kmImeetingAgenda.docReporter')}
						</td>
						<td width="62%">
							<xform:config  orient="none">
								<xform:address propertyName="kmImeetingAgendaForms[!{index}].docReporterName" propertyId="kmImeetingAgendaForms[!{index}].docReporterId" showStatus="edit" orgType="ORG_TYPE_PERSON" mobile="true"/>
							</xform:config>
						</td>
					</tr>
					<!-- 汇报时间 -->
					<tr data-celltr="true">
						<td class="muiTitle" width="38%">
							${lfn:message('km-imeeting:kmImeetingAgenda.docReporterTime')}
						</td>
						<td width="62%">
							<xform:config  orient="none">
								<xform:text property="kmImeetingAgendaForms[!{index}].docReporterTime" showStatus="edit" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingAgenda.docReporterTime.mobile') }'" subject="${lfn:message('km-imeeting:kmImeetingAgenda.docReporterTime')}" validators="digits min(0)" mobile="true"></xform:text>
							</xform:config>
						</td>
					</tr>
					<!-- 上会材料 -->
					<tr data-celltr="true">
						<td class="muiTitle" width="38%">
							${lfn:message('km-imeeting:kmImeetingAgenda.attachmentName')}
						</td>
						<td width="62%">
							<xform:config  orient="none">
								<xform:text property="kmImeetingAgendaForms[!{index}].attachmentName" showStatus="edit" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingAgenda.attachment.mobile') }'" subject="${lfn:message('km-imeeting:kmImeetingAgenda.attachmentName')}" validators="maxLength(200)" mobile="true"></xform:text>
							</xform:config>
						</td>
					</tr>
					<!-- 材料责任人 -->
					<tr data-celltr="true">
						<td class="muiTitle" width="38%">
							${lfn:message('km-imeeting:kmImeetingAgenda.docRespons')}
						</td>
						<td width="62%">
							<xform:config  orient="none">
								<xform:address propertyName="kmImeetingAgendaForms[!{index}].docResponsName" propertyId="kmImeetingAgendaForms[!{index}].docResponsId" showStatus="edit" orgType="ORG_TYPE_PERSON" mobile="true"/>
							</xform:config>
						</td>
					</tr>
					<!-- 材料提交时间 -->
					<tr data-celltr="true">
						<td class="muiTitle" width="38%">
							${lfn:message('km-imeeting:kmImeetingAgenda.attachmentSubmitTime')}
						</td>
						<td width="62%">
							<xform:config  orient="none">
								<xform:text property="kmImeetingAgendaForms[!{index}].attachmentSubmitTime" showStatus="edit" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingAgenda.attachmentSubmitTime.mobile') }'" subject="${lfn:message('km-imeeting:kmImeetingAgenda.attachmentSubmitTime')}" validators="digits min(0)" mobile="true"></xform:text>
							</xform:config>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<c:forEach items="${kmImeetingMainForm.kmImeetingAgendaForms}" var="kmImeetingAgendaitem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" data-celltr="true">
				<td class="detail_wrap_td">
					<table class="muiSimple">
						<tr celltr-title="true">
							<td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
								<div class="muiDetailDisplayOpt muiDetailDown"   onclick="expandRow(this);"></div>
								<span class="index" onclick="expandRow(this);" style="color: #4285F4;">
									${vstatus.index+1}
								</span>
								<span class="agendaSubject" onclick="expandRow(this);">
								</span>
								<xform:editShow>
									<div class="muiDetailTableDel" onclick="deleteRow(this);" style="color: #4285F4;">
										<bean:message key="button.delete" />
									</div>
								</xform:editShow>
							</td>
						</tr>
						<!-- 会议议题 -->
						<tr data-celltr="true">
							<td class="muiTitle" width="38%">
								${lfn:message('km-imeeting:kmImeetingAgenda.docSubject')}
							</td>
							<td width="62%">
								<xform:config  orient="none">
									<xform:text property="kmImeetingAgendaForms[${vstatus.index}].fdId" showStatus="noShow"  value="${kmImeetingAgendaitem.fdId}"  mobile="true"></xform:text>
									<xform:text property="kmImeetingAgendaForms[${vstatus.index}].fdMainId" showStatus="noShow"  value="${kmImeetingMainForm.fdId}" mobile="true"></xform:text>
									<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docSubject" value="${kmImeetingAgendaitem.docSubject}" htmlElementProperties="class='title' placeholder='${lfn:message('km-imeeting:kmImeetingAgenda.docSubject.mobile') }'" showStatus="edit" subject="${lfn:message('km-imeeting:kmImeetingAgenda.docSubject')}" required="true" validators="maxLength(200)" mobile="true"></xform:text>
								</xform:config>
							</td>
						</tr>
						<!-- 汇报人 -->
						<tr data-celltr="true">
							<td class="muiTitle" width="38%">
								${lfn:message('km-imeeting:kmImeetingAgenda.docReporter')}
							</td>
							<td width="62%">
								<xform:config  orient="none">
									<xform:address propertyName="kmImeetingAgendaForms[${vstatus.index}].docReporterName" propertyId="kmImeetingAgendaForms[${vstatus.index}].docReporterId" showStatus="edit" orgType="ORG_TYPE_PERSON" mobile="true"/>
								</xform:config>
							</td>
						</tr>
						<!-- 汇报时间 -->
						<tr data-celltr="true">
							<td class="muiTitle" width="38%">
								${lfn:message('km-imeeting:kmImeetingAgenda.docReporterTime')}
							</td>
							<td width="62%">
								<xform:config  orient="none">
									<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docReporterTime" showStatus="edit" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingAgenda.docReporterTime.mobile') }'" subject="${lfn:message('km-imeeting:kmImeetingAgenda.docReporterTime')}" validators="digits min(0)" mobile="true"></xform:text>
								</xform:config>
							</td>
						</tr>
						<!-- 上会材料 -->
						<tr data-celltr="true">
							<td class="muiTitle" width="38%">
								${lfn:message('km-imeeting:kmImeetingAgenda.attachmentName')}
							</td>
							<td width="62%">
								<xform:config  orient="none">
									<xform:text property="kmImeetingAgendaForms[${vstatus.index}].attachmentName" showStatus="edit" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingAgenda.attachment.mobile') }'" subject="${lfn:message('km-imeeting:kmImeetingAgenda.attachmentName')}" validators="maxLength(200)" mobile="true"></xform:text>
								</xform:config>
							</td>
						</tr>
						<!-- 材料责任人 -->
						<tr data-celltr="true">
							<td class="muiTitle" width="38%">
								${lfn:message('km-imeeting:kmImeetingAgenda.docRespons')}
							</td>
							<td width="62%">
								<xform:config  orient="none">
									<xform:address propertyName="kmImeetingAgendaForms[${vstatus.index}].docResponsName" propertyId="kmImeetingAgendaForms[${vstatus.index}].docResponsId" showStatus="edit" orgType="ORG_TYPE_PERSON" mobile="true"/>
								</xform:config>
							</td>
						</tr>
						<!-- 材料提交时间 -->
						<tr data-celltr="true">
							<td class="muiTitle" width="38%">
								${lfn:message('km-imeeting:kmImeetingAgenda.attachmentSubmitTime')}
							</td>
							<td width="62%">
								<xform:config  orient="none">
									<xform:text property="kmImeetingAgendaForms[${vstatus.index}].attachmentSubmitTime" showStatus="edit" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingAgenda.attachmentSubmitTime.mobile') }'" subject="${lfn:message('km-imeeting:kmImeetingAgenda.attachmentSubmitTime')}" validators="digits min(0)" mobile="true"></xform:text>
								</xform:config>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<div 
		data-dojo-type="sys/xform/mobile/controls/DetailTableAddButton" 
		onclick="window.detail_add(event)">
		${lfn:message('km-imeeting:kmImeetingAgenda.operation.addDetail.mobile')}
	</div>
</div>
<script type="text/javascript">
require(['dojo/ready', 'dijit/registry', 'dojo/topic', 'dojo/request', 
         'dojo/dom', 'dojo/dom-attr', 'dojo/dom-style', 'dojo/dom-class', 'dojo/query', 'mui/dialog/Tip', "dojo/parser", "mui/pageLoading"], 
		function(ready, registry, topic, request, dom, domAttr, domStyle, domClass, query, Tip, parser, pageLoading){
	
	ready(function(){
		if(DocList_TableInfo['TABLE_DocList']==null){
			DocListFunc_Init();
		}
	});
	
	//添加行
	window.detail_add = function(event) {
		event = event || window.event;
		if (event.stopPropagation)
			event.stopPropagation();
		else
			event.cancelBubble = true;
		detail_addRow("TABLE_DocList");
	};
	
	window.detail_addRow = function(tableId, callbackFun) {
		var newRow = DocList_AddRow(tableId);
		newRow.dojoClick = true;
		parser.parse(newRow).then(function(){
			var tabInfo = DocList_TableInfo[tableId];
			if(tabInfo['_getcols']== null){
				tabInfo.fieldNames=[];
				tabInfo.fieldFormatNames=[];
				DocListFunc_AddReferFields(tabInfo, newRow, "INPUT");
				DocListFunc_AddReferFields(tabInfo, newRow, "TEXTAREA");
				DocListFunc_AddReferFields(tabInfo, newRow, "SELECT");
				tabInfo['_getcols'] = 1;
			}
			detail_fixNo();
			topic.publish("/mui/list/resize",newRow);
			if(callbackFun)callbackFun(newRow);
		});
	};
	
	window.detail_fixNo = function() {
		$('#TABLE_DocList').find('.muiDetailTableNo').each(function(i) {
			$(this).children('.index').text(i + 1);
		});
	};
	
	window.deleteRow = function(domNode) {
		var td = $(domNode).closest('.detail_wrap_td')[0];
		var  trDom = td.parentNode;
		$(trDom).find("*[widgetid]").each(function (idx, widgetDom) {
            var widget = registry.byNode(widgetDom);
            if (widget && widget.destroy) {
                widget.destroy();
            }
        });
        var optTB = DocListFunc_GetParentByTagName("TABLE", trDom);
        var rowIndex = Com_ArrayGetIndex(optTB.rows, trDom);
        var tbInfo = DocList_TableInfo[optTB.id];
        DocList_DeleteRow_ClearLast(trDom);
        for (var i = rowIndex; i < tbInfo.lastIndex; i++) {
            var row = tbInfo.DOMElement.rows[i];
            query('*[widgetid]', row).forEach(function (widgetDom) {
                var widget = registry.byNode(widgetDom);
                if (widget.needToUpdateAttInDetail) {
                    var updateAttrs = widget.needToUpdateAttInDetail;
                    for (var j = 0; j < updateAttrs.length; j++) {
                        if (widget[updateAttrs[j]]) {
                            var updatFileds = query("[name='" + widget[updateAttrs[j]] + "']", row);
                            if (updatFileds.length > 0) {
                                updatFileds[0].name = window["detail_repalceIndexInfo"](updatFileds[0].name, i - tbInfo.firstIndex);
                            }
                            widget[updateAttrs[j]] = window["detail_repalceIndexInfo"](widget[updateAttrs[j]], i - tbInfo.firstIndex);
                        }
                    }
                } else if (widget.name) {
                    var tmpFileds = query("[name='" + widget.name + "']", row);
                    if (tmpFileds.length > 0) {
                        tmpFileds[0].name = window["detail_repalceIndexInfo"](tmpFileds[0].name, i - tbInfo.firstIndex);
                    }
                    widget.name = window["detail_repalceIndexInfo"](widget.name, i - tbInfo.firstIndex);
                } else if (widget.idField) {
                    var tmpIdField = query("[name='" + widget.idField + "']", row);
                    if (tmpIdField.length > 0) {
                        tmpIdField[0].name = window["detail_repalceIndexInfo"](tmpIdField[0].name, i - tbInfo.firstIndex);
                    }
                    var tmpNameField = query("[name='" + widget.nameField + "']", row);
                    if (tmpNameField.length > 0) {
                        tmpNameField[0].name = window["detail_repalceIndexInfo"](tmpNameField[0].name, i - tbInfo.firstIndex);
                    }
                    widget.idField = window["detail_repalceIndexInfo"](widget.idField, i - tbInfo.firstIndex);
                    widget.nameField = window["detail_repalceIndexInfo"](widget.nameField, i - tbInfo.firstIndex);
                }
            });
        }
        topic.publish('/mui/form/valueChanged');
        topic.publish("/mui/list/resize", trDom);
		detail_fixNo();
	};
	
	 window["detail_repalceIndexInfo"] = function (fieldName, index) {
         fieldName = fieldName.replace(/\[\d+\]/g, "[!{index}]");
         fieldName = fieldName.replace(/\.\d+\./g, ".!{index}.");
         fieldName = fieldName.replace(/!\{index\}/g, index);
         return fieldName;
     };
	
	window.expandRow = function(domNode){
		var domTable = $(domNode).closest('table')[0];
		var display = domAttr.get(domTable,'data-display'),
			newdisplay = (display == 'none' ? '' : 'none');
		domAttr.set(domTable,'data-display',newdisplay);
		var items = query('tr[data-celltr="true"],tr[statistic-celltr="true"]',domTable);
		for(var i = 0; i < items.length; i++){
			if(newdisplay == 'none'){
				domStyle.set(items[i],'display','none');
			}else{
				domStyle.set(items[i],'display','');
			}
		}
		var opt = query('.muiDetailDisplayOpt',domTable)[0];
		var del = query('.muiDetailTableDel',domTable)[0];
		var title = query('.title .muiInput', domTable)[0].value;
		if(newdisplay == 'none'){
			domClass.add(opt,'muiDetailUp');
			domClass.remove(opt,'muiDetailDown');
			domStyle.set(del, 'display','none');
			query('.agendaSubject', domTable).html(title);
		}else{
			domClass.add(opt,'muiDetailDown');
			domClass.remove(opt,'muiDetailUp');
			domStyle.set(del, 'display','');
			query('.agendaSubject', domTable).html('');
		}
	};

});
</script>
