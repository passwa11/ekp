<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date,java.util.Calendar,com.landray.kmss.util.DateUtil,com.landray.kmss.util.UserUtil,com.landray.kmss.util.DateUtil" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendStatService,com.landray.kmss.util.SpringBeanUtil" %>
<%
	String fdId =  UserUtil.getUser().getFdId() == null ? "" :
		UserUtil.getUser().getFdId();
	String fdName = UserUtil.getUser().getFdName();
%>
<template:include ref="default.simple4list">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
		<style type="text/css">
			td.orgTd {position: relative;}
			td.orgTd label{position: absolute;top: 10px;margin-left: 5px;}
			.btn_txt {margin: 0px 2px;color: #2574ad;border-bottom: 1px solid transparent;}
			.btn_txt:hover {color: #123a6b;border-bottom-color: #123a6b;}
			.sign_status {color: #2574ad}
			.sign_status:hover {border-bottom:1px solid #2574ad;}
			.sign_status_exc {color: #f00;font-weight: bold;}
			.sign_status_exc:hover {border-bottom:1px solid #f00;}
			.lui-attend-report-container {
				max-width:unset;
			    padding: 10px 0px 10px 15px;
			}
			.lui-attend-report-section-title {
				left: 0px; 
			}
		</style>
		<script type="text/javascript">
			seajs.use(['theme!form']);
			Com_IncludeFile("validation.js|plugin.js|validation.jsp|xform.js|eventbus.js");
		</script>
	</template:replace>
	<template:replace name="nav">
		<!-- 新建按钮 -->
		<div class="lui_list_noCreate_frame">
			<ui:combin ref="menu.nav.create">
				<ui:varParam name="title" value="${ lfn:message('sys-attend:module.sys.attend') }" />
				<ui:varParam name="button">
					[
						{
							"text": "${ lfn:message('sys-attend:module.sys.attend') }",
							"href": "javascript:void(0)",
							"icon": "lui_icon_l_icon_89"
						}
					]
				</ui:varParam>				
			</ui:combin>
		</div>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
			 	<c:import url="/sys/attend/nav.jsp" charEncoding="UTF-8">
				   <c:param name="key" value="sysAttendCheck"></c:param>
				   <c:param name="criteria" value="checkCriteria"></c:param>
				</c:import>		 
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${lfn:message('sys-attend:sysAttendMain.data.check') }">
				<form id="calDayForm" name="calDayForm" action="${LUI_ContextPath}/sys/attend/sys_attend_main/sysAttendMain.do" method="post">
				 	<div style="margin-top: 20px; padding: 0 30px;">
					 	<table class="tb_normal" width=100%>
					 		<tr>
					 			<td class="td_normal_title" width="150px">
									${ lfn:message('sys-attend:sysAttendMain.docCreator') }
								</td>
								<td class="orgTd">
									<div id='targetDept' style="width: 50%;float:left;">
										<xform:address propertyId="fdTargetId" propertyName="fdTargetName"
											required="true" mulSelect="false" showStatus="edit" subject="${ lfn:message('sys-attend:sysAttendMain.docCreator') }"
											idValue="<%=fdId %>" nameValue="<%=fdName %>"
											style="width:150px;height: 25px;"
										   orgType="ORG_FLAG_AVAILABLEALL|ORG_TYPE_PERSON"
										></xform:address>
									</div>
								</td>
								<td class="td_normal_title" width="150px">
									${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }
								</td>			
								<td>
									<%
										String _endTime =  DateUtil.convertDateToString(Calendar.getInstance().getTime(), DateUtil.TYPE_DATE, null);
									%>
									<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="edit" 
										required="true" subject="${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }" value="<%=_endTime %>"
										style="width:150px"></xform:datetime>	 	
								</td>
					 		</tr>
					 	</table>
					 	<div style="text-align: center;padding: 20px;">
					 		<ui:button text="${ lfn:message('button.list') }" order="1" style="margin-right: 10px;"
						      onclick="listDetail();">
							</ui:button>
					 	</div>
				 	</div>
				 </form>
				 <div class="lui-attend-report-container">
					<div class="lui-attend-report-section">
						<div class="lui-attend-report-section-line"></div>
						<div class="lui-attend-report-section-title"  onclick="slideToggle(this, '#statTable');">
							${ lfn:message('sys-attend:table.sysAttendStatDetail') }
							<span class="lui-attend-report-line-icon"></span>
						</div>
					</div>
				</div>
				 <div style="display:none" id="statTable">
				 	<iframe id="statIframe" width="100%" height="100%" frameborder="0" src="" style="min-height:300px;"></iframe>
				 </div> 
				  <div class="lui-attend-report-container">
					<div class="lui-attend-report-section">
						<div class="lui-attend-report-section-line"></div>
						<div class="lui-attend-report-section-title"  onclick="slideToggle(this, '#validTable');">
							${ lfn:message('sys-attend:sysAttend.nav.all.record') }
							<span class="lui-attend-report-line-icon"></span>
						</div>
					</div>
				</div>
				 <div style="display:none" id="validTable">
				 	<iframe id="validIframe" width="100%" height="100%" frameborder="0" src="" style="min-height:300px;"> </iframe>
				 </div> 
				  <div class="lui-attend-report-container">
					<div class="lui-attend-report-section">
						<div class="lui-attend-report-section-line"></div>
						<div class="lui-attend-report-section-title"  onclick="slideToggle(this, '#originTable');">
							${ lfn:message('sys-attend:sysAttend.nav.original.record') }
							<span class="lui-attend-report-line-icon"></span>
						</div>
					</div>
				</div>
				 <div style="display:none" id="originTable">
				 	<iframe id="originIframe" width="100%" height="100%" frameborder="0" src="" style="min-height:300px;"></iframe>
				 </div> 
				
			</ui:content>
		</ui:tabpanel>	
		 
	 	<script type="text/javascript">	
	 		var detailValidation = $KMSSValidation(document.forms['calDayForm']);
	 		
	 		seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/data/source'], function($, dialog , topic, Source){
	 			LUI.ready(function(){
	 				//listDetail();
	 			})
				window.listDetail = function(pageNo){
					if(!validateCalForm())
						return;
					$('#statTable').show();
					$('#originTable').show();
					$('#validTable').show();
					var fdTargetId=$("[name=fdTargetId]").val();
					var fdEndTime=$("[name=fdEndTime]").val();
					var queryParams="&fdTargetId="+fdTargetId+"&fdEndTime="+fdEndTime;
					$("#statIframe").attr("src",'<c:url value="/sys/attend/sys_attend_main/sysAttendMain.do?method=viewCheck&viewType=viewStat"/>'+queryParams);
					$("#originIframe").attr("src",'<c:url value="/sys/attend/sys_attend_main/sysAttendMain.do?method=viewCheck&viewType=viewOrigin"/>'+queryParams);
					$("#validIframe").attr("src",'<c:url value="/sys/attend/sys_attend_main/sysAttendMain.do?method=viewCheck&viewType=viewValid"/>'+queryParams);
				};
				
				window.validlistview=function(pageNo){
					if(!validateCalForm())
						return;
					
					var __url = '/sys/attend/sys_attend_main/sysAttendMain.do?method=validListDetail';
					if(pageNo) {
						__url = __url + '&pageno=' + pageNo;
					}
					var listview = LUI('attendValidListview'),
						source = new Source.AjaxJson({
							url : __url,
							params:$(document.forms['calDayForm']).serialize(),
							commitType:'POST'
						});
					listview.table.redrawBySource(source);
					listview.table.source.get();
					$('.lui-attend-valid-table').show();
				}
				
				window.originlistview=function(pageNo){
					if(!validateCalForm())
						return;
					
					var __url = '/sys/attend/sys_attend_main/sysAttendMain.do?method=originListDetail';
					if(pageNo) {
						__url = __url + '&pageno=' + pageNo;
					}
					var listview = LUI('attendOriginListview'),
						source = new Source.AjaxJson({
							url : __url,
							params:$(document.forms['calDayForm']).serialize(),
							commitType:'POST'
						});
					listview.table.redrawBySource(source);
					listview.table.source.get();
					$('.lui-attend-origin-table').show();
				}
				
				function validateCalForm() {
					if(window.detailValidation && window.detailValidation.validate()) {
						return true;
					} else {
						return false;
					}
				};
				window.switchAttendPage = function(url,hash){
					url = Com_SetUrlParameter(url,'j_iframe','true');
					url = Com_SetUrlParameter(url,'j_aside','false');
					if(hash){
						url = url + hash;
					}
					LUI.pageOpen(url,'_rIframe');
				}
				
				window.openMainList = function(userId, date){
					window.open('${LUI_ContextPath }/sys/attend/sys_attend_main/sysAttendMain_index.jsp'
							+'#cri.q=docCreator:'+ userId +''
							+';docCreateTime:' + date + ';docCreateTime:' + date, '_blank');
				}
				window.slideToggle = function(srcObj, targetObj){
					$(targetObj).slideToggle();
					$(srcObj).find(".lui-attend-report-line-icon").toggleClass('slideUp');
				};
			});
		</script>
	</template:replace>
</template:include>
