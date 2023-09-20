<%@page import="java.net.URLDecoder"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.Map,com.landray.kmss.util.StringUtil,net.sf.json.JSONObject,java.util.Iterator,java.net.URLDecoder,java.net.URLEncoder" %>
<%@page import="com.landray.kmss.dbcenter.echarts.forms.DbEchartsTabelInfo"%>
<%
	DbEchartsTabelInfo info = (DbEchartsTabelInfo)request.getAttribute("info");
	String dynamic = request.getParameter("db_dynamic");// 约定db_dynamic存储入参数据
	String db_dynamic = "";
	// 支持入参
	if(StringUtil.isNotNull(dynamic)){
		JSONObject dy = JSONObject.fromObject(URLDecoder.decode(dynamic,"UTF-8"));
		Iterator ite = dy.keys();
		while(ite.hasNext()){
			String key = (String)ite.next();
			db_dynamic += "&dy." + key + "=" + URLEncoder.encode(dy.getString(key),"UTF-8");
		}
	}
	String fdId = request.getParameter("fdId");
	String dbEchartsTableUrl = "/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=data&fdId="+fdId;
	dbEchartsTableUrl += db_dynamic;

	//设置图表整体宽度
	String tableWidth = "95%";
	JSONObject tableConfig = info.getConfig();
	if(tableConfig!=null && !tableConfig.isEmpty()){
		JSONObject listview =tableConfig.getJSONObject("listview");
		if(listview!=null){
			if(listview.containsKey("isAdapterWidth")){
				if(!"true".equalsIgnoreCase(listview.getString("isAdapterWidth"))
						&& listview.containsKey("width") && StringUtil.isNotNull(listview.getString("width"))){
					tableWidth = listview.getString("width")+"px";
				}
			}else {
				//编程模式
				if(listview.containsKey("width")&&StringUtil.isNotNull(listview.getString("width"))){
					tableWidth = listview.getString("width")+"px";
				}
			}
		}
	}
%>
<template:include ref="default.simple">
	<template:replace name="title">
		<c:out value="${info.docSubject}" />
	</template:replace>
	<template:replace name="body">
		<style>
			.lui_listview_columntable_table thead th {
				white-space: normal;
				line-height:20px;
			}
			.close-detail {
				display:inline-block;
				float:right;
				margin-right:5px;
				padding:5px;
			}
		</style>
		<form name="dbEchartsTableForm" method="post">
			<input type="hidden" name="fdId" value="${param.fdId}">
		</form>
		<script type="text/javascript">
			Com_IncludeFile("echartschart.js", "${LUI_ContextPath}/dbcenter/echarts/common/", null, true);
			seajs.use(['theme!list', 'theme!form']);
			domain.autoResize();
			function exportInfo() {
				var listview = LUI('listview1');
				var url = '${LUI_ContextPath}'+listview.sourceURL;
				url = Com_SetUrlParameter(url,'method','exportInfo');
				document.dbEchartsTableForm.action = url;
				document.dbEchartsTableForm.submit();
			}
			seajs.use(["lui/jquery"],function($) {
				LUI.ready(function() {
					var showDetail = '${param.showDetail}';
					if('1' == showDetail) {
						var $criteria = $(".criteria");
						var criteria = LUI($criteria.attr('id'));
						criteria.on('load',function() {
							$criteria.hide();
						})
					}
				});
			});
			seajs.use(["lui/dialog"],function(dialog){
				window.deleteDoc = function(delUrl){
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
						if(isOk){
							Com_OpenWindow(delUrl,'_self');
						}	
					});
					return;
				};
				

				//取消关注方法方法
				window.deleteMyAttentionEcharts = function(confirmUrl){
					dialog.confirm('${lfn:message('dbcenter-echarts:module.echarts.my.following.noConfirm') }',function(isOk){
						if(isOk){
							Com_OpenWindow(confirmUrl,'_self');
						}
					});
					return;
				};

				//关注方法
				window.createMyAttentionEcharts = function(confirmUrl){
					dialog.confirm('${lfn:message('dbcenter-echarts:module.echarts.my.following.confirm') }',function(isOk){
						if(isOk){
							Com_OpenWindow(confirmUrl,'_self');
						}
					});
					return;
				};
				
				
			});
			function innerCloseDetail() {
				if(window.parent && window.parent.echartschart) {
					window.parent.echartschart.innerCloseDetail('${param.fid}');
				}
			}
			function doReport() {
				var listview = LUI('listview1');
				var url = '${LUI_ContextPath}'+listview.sourceURL;
				url = Com_SetUrlParameter(url,'method','viewRpt');
				Com_OpenWindow(url);
			}

		</script>
		<c:if test="${'1' eq param.showDetail }">
			<div align="center" style="font-weight:bold;">
				<font size="4" >${param.title }</font>
				<a class="close-detail com_btn_link" href="javascript;" onclick="innerCloseDetail();">${lfn:message('button.close')}</a>
			</div>
		</c:if>
		<c:if test="${'0'!=param.showButton}">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%" cfg-dataInit="false">
				
				<c:if test='${isAttention=="1"}'><!--已关注-->
					<kmss:auth requestURL="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteMyAttentionEcharts&fdId=${param.fdId}">
						<ui:button text="${lfn:message('dbcenter-echarts:module.echarts.nofollowing.title')}" order="1" onclick="deleteMyAttentionEcharts('${LUI_ContextPath}/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteMyAttentionEcharts&fdId=${param.fdId}');"></ui:button>
					</kmss:auth>
				</c:if>
				<c:if test='${isAttention=="0"}'><!--未关注-->
					<kmss:auth requestURL="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=createMyAttentionEcharts&fdId=${param.fdId}">
						<ui:button text="${lfn:message('dbcenter-echarts:module.echarts.following.title')}" order="1" onclick="createMyAttentionEcharts('${LUI_ContextPath}/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=createMyAttentionEcharts&fdId=${param.fdId}');"></ui:button>
					</kmss:auth>
				</c:if>

				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=exportInfo&fdId=${param.fdId}" requestMethod="GET">
					<ui:button text="${lfn:message('dbcenter-echarts:dbEchartsTable.exportInfo')}" order="1" onclick="exportInfo();"></ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
					<ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('dbEchartsTable.do?method=edit&fdId=${param.fdId}','_self');" order="2">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
					<ui:button text="${lfn:message('button.copy')}" onclick="Com_OpenWindow('dbEchartsTable.do?method=clone&cloneModelId=${param.fdId}','_blank');" order="2">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=delete&fdId=${param.fdId}">
				    <ui:button text="${lfn:message('button.delete')}" order="4" onclick="deleteDoc('${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=delete&fdId=${param.fdId}');"></ui:button>
				</kmss:auth>
				<ui:button text="报表" onclick="doReport();"></ui:button>
				<ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow();"></ui:button>
			</ui:toolbar>
		</c:if>
		<c:if test="${info.showCriteria}">
			<c:import charEncoding="UTF-8" url="/dbcenter/echarts/common/criteria.jsp" />
		</c:if>

		<c:choose>
			<c:when test="${info.fdType eq '11'}">
				<c:if test="${'0'!=param.showButton}">
					<p class="txttitle"><c:out value="${info.docSubject}" /></p>
				</c:if>
			</c:when>
			<c:otherwise>
				<p class="txttitle"><c:out value="${info.mainTitle}" /></p>
				<p style="text-align:center;"><c:out value="${info.subTitle}" /></p>
			</c:otherwise>
		</c:choose>

		<div style="width:98%;overflow: auto">
			<div style="width:<%=tableWidth%>; margin:0px auto;">

				<c:if test="${info.showPage || info.showSort}">
					<div class="lui_list_operation">
						<c:if test="${info.showSort}">
							<div style='color: #979797;float: left;padding-top:1px;'>
									${ lfn:message('list.orderType') }：
							</div>
							<div style="float:left">
								<div style="display: inline-block;vertical-align: middle;">
									<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" >
										<list:sortgroup>
											<c:forEach items="${info.sortColumns}" var="column">
												<%
													pageContext.setAttribute("orderValue", info.getSortValue(pageContext.getAttribute("column")));
												%>
												<list:sort property="${column.key}" text="${column.name}" group="sort.list" value="${orderValue}"/>
											</c:forEach>
										</list:sortgroup>
									</ui:toolbar>
								</div>
							</div>
						</c:if>
						<c:if test="${info.showPage}">
							<div style="float:right;">
								<list:paging layout="sys.ui.paging.top" />
							</div>
						</c:if>
					</div>
					<ui:fixed elem=".lui_list_operation"></ui:fixed>
				</c:if>
				<list:listview id="listview1">
					<ui:source type="AjaxJson">{url:'<%=dbEchartsTableUrl%>'}</ui:source>
					<list:colTable layout="sys.ui.listview.columntable"
								   rowHref="${info.config.listview.url}"  name="columntable">
						<%--					<c:if test="${'0'!=param.showButton}">--%>
						<list:col-column  property="index" headerStyle="width:4.5%;text-align:center" title="${lfn:message('dbcenter-echarts:xuhao')}"></list:col-column>
						<%--					</c:if>--%>
						<c:forEach items="${info.config.columns}" var="column">
							<c:if test="${column.hidden!='true'}">
								<c:if test="${empty column.template}">
									<list:col-column property="${column.key}" title="${column.name}" style="text-align:${column.align}" headerStyle="${empty column.width?'':'width:'}${column.width}" />
								</c:if>
								<c:if test="${not empty column.template}">
									<list:col-html title="${column.name}" style="text-align:${column.align}" headerStyle="${empty column.width?'':'width:'}${column.width}">${column.template}</list:col-html>
								</c:if>
							</c:if>
						</c:forEach>
					</list:colTable>

					<ui:event topic="list.loaded">
						<% /** 判断嵌入当前窗口的父IFrame高度是否要跟随当前窗口的内容高度  **/ %>
						<c:if test="${param.iframeHeightFollowContent eq '1'}">
							seajs.use(['lui/jquery'],function($){
							if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
							window.frameElement.style.height =  $(document.body).height() +10+ "px";
							}
							});
						</c:if>
					</ui:event>
				</list:listview>
			</div>
		</div>
		<c:if test="${info.showPage}">
			<list:paging></list:paging>
		</c:if>
		<br>
	</template:replace>
</template:include>