<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath }/<%=(JSONObject.fromObject(SysUiPluginUtil.getThemes(request))).getJSONArray("prompt").get(0)%>"/>
	<script type="text/javascript">
	Com_SetWindowTitle("${requestScope['fdNodeFactName']}");
	Com_IncludeFile("content.css","${LUI_ContextPath}/sys/lbpmservice/support/lbpm_summary_approval/config/css/","css",true);
	Com_IncludeFile("data.js","${LUI_ContextPath}/sys/lbpmservice/support/lbpm_summary_approval/config/js/","js",true);
	</script>
	
	<c:if test='${isNotHandler eq "true" }'>
		<div id='emptyContainer' style="display:<c:if test="${processEmpty == 'true' }">block</c:if><c:if test="${processEmpty != 'true' }">none</c:if>">
			<div class="panel-tab-empty-content">
				<div class="panel-tab-empty-header">
					<div class="closeWin" onclick="Com_CloseWindow()"></div>
				</div>
				<c:import url="/sys/lbpmservice/support/lbpm_summary_approval/config/lsitview_not_handler.jsp" charEncoding="UTF-8"></c:import>
			</div>
		</div>
	</c:if>
	<c:if test='${isNotHandler ne "true" }'>
		<div id='emptyContainer' style="display:<c:if test="${processEmpty == 'true' }">block</c:if><c:if test="${processEmpty != 'true' }">none</c:if>">
			<div class="panel-tab-empty-content">
				<div class="panel-tab-empty-header">
					<div class="closeWin" onclick="Com_CloseWindow()"></div>
				</div>
				<c:import url="/sys/lbpmservice/support/lbpm_summary_approval/config/lsitview_empty.jsp" charEncoding="UTF-8"></c:import>
			</div>
		</div>
		<div id='noEmptyContainer'  style="display:<c:if test="${processEmpty == 'true' }">none</c:if><c:if test="${processEmpty != 'true' }">block</c:if>">
			<div data-lui-type="${Com_Parameter.ContextPath }sys/lbpmservice/support/lbpm_summary_approval/config/js/container!Container" id="container">
				<script type="text/config">
 			{
				storeData : ${datas},
				headerClass : 'panel-tab-header',
				mainClass : 'panel-tab-main',
				params : {}
			}
 			</script>
				<div class="panel-tab-content">
					<div class="panel-tab-header"></div>
					<div class="panel-tab-main"></div>
					<div class="panel-tab-footer">
						<div data-lui-type="lui/listview/paging!Paging"
							id='dataPaging' style="display: none;">
							<script type="text/config">
					{
    					"currentPage": "${pageNo}",
    					"pageSize": "${pageSize}",
						"totalSize":"${totalSize}"
					}
					</script>
							<div data-lui-type="lui/view/layout!Template" style="display: none;">
								<script type="text/config">
						{"kind": "paging"}
						</script>
								<script type='text/code'
									xsrc='/sys/ui/extend/listview/paging.jsp?s_cache=1596009817967'>
						</script>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
	</c:if>
<%@ include file="/resource/jsp/edit_down.jsp"%>