<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
	<div id="CirculationMain">
		<list:listview channel="paging">
				<ui:source type="AjaxJson">
					{"url":"/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=listData&fdModelId=${sysCirculationForm.circulationForm.fdModelId}&fdModelName=${sysCirculationForm.circulationForm.fdModelName}"}
				</ui:source>
				<list:colTable isDefault="true" layout="sys.ui.listview.listtable" cfg-norecodeLayout="${param.norecodeLayout !=null and param.norecodeLayout != ''?param.norecodeLayout:'simple'}">
						
						<list:col-auto props="fdCirculationTime,fdCirculator.fdName,sysCirculationCirculors"></list:col-auto>
							
						<list:col-html title="${ lfn:message('sys-circulation:sysCirculationMain.fdRemark') }"  style="width:150px;text-align:left;word-break:break-all;">
							var remark = row.fdRemark;
							if(remark){
								remark = remark.replace(/\r\n/g,'<br>').replace(/\n/g,'<br>');
								{$  
									{% remark %}
								$}
							}
						</list:col-html>
						<list:col-html style="width:150px;">	
							if(row.fdState === '0') {
							{$<span>${ lfn:message('sys-circulation:sysCirculationMain.process') }</span>$}
							} else if(row.fdState === '1') {
							<c:if test="${isNew == 'true'}">
								{$<a href="javascript:;" style="float:right;padding-right:6px" onclick="openCirculation('{%row.fdId%}')" class="com_btn_link"><bean:message bundle="sys-circulation"  key="button.view" /></a>$}
								if(row.remindAuth == 'true'){	
									{$<a href="javascript:;" style="float:right;padding-right:6px" onclick="remindCirculation('{%row.fdId%}')" class="com_btn_link"><bean:message bundle="sys-circulation"  key="button.remind" /></a>$}
								}
								if(row.recallAuth == 'true'){
									{$<a href="javascript:;" style="float:right;padding-right:6px" onclick="recallCirculation('{%row.fdId%}')" class="com_btn_link"><bean:message bundle="sys-circulation"  key="button.recall" /></a>$}
								}
							</c:if>
							}
							if(row.fdState === '2') {
							{$<span>${ lfn:message('sys-circulation:sysCirculationMain.process.error') }</span>$}
							}
							if(row.fdState === '1' || row.fdState === '2') {
							<c:if test="${validateAuth=='true'}">	
								{$<a href="javascript:;"  style="float:right;padding-right:6px" onclick="deleteCirculation('{%row.fdId%}')" class="com_btn_link"><bean:message key="button.delete" /></a>$}
							</c:if>	
							}
						</list:col-html>
				</list:colTable>						
		</list:listview>
		<list:paging channel="paging" layout="sys.ui.paging.simple"></list:paging>
	</div>
	
