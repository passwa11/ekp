<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.mportal.plugin.MportalMportletUtil" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="java.util.List" %>
<template:include ref="default.simple">
	<template:replace name="title">
		${ lfn:message('sys-mportal:sysMportalCard.dialog.select') }
	</template:replace>
	<template:replace name="body">
	<script>
		seajs.use(["theme!form"]);
		function selectPortlet(id,name,description) {
			var data = {
					"uuid":"opt_<%=IDGenerator.generateID()%>",
					"fdPortletId":id,
					"fdPortletName":name,
					"description":description
			};
			window.$dialog.hide(data);
		}
	</script>
	<table style="width: 95%;margin: 3px auto;" border="0">
			<tr>
				<td width="30%;">
					<div style="text-align: left">
					<select name="categories" onchange="DoCategoryFilter();">
						<option value=""><bean:message key="page.firstOption"/></option>
						<% 
							List <String> list = MportalMportletUtil.getModules();
							for(String module : list) { 
								out.append("<option value='" + module + "'>" 
										+ ResourceUtil.getMessage(module) + "</option>");
							}
						%>
					</select>
					<script>
						function DoCategoryFilter() {
							seajs.use(["lui/jquery"], function($) {
								var val = $('[name="categories"]').val();
								LUI('sourceList').tableRefresh({criterions:[{key:"module", value: [val]}]});
							});
						}
					</script>
					</div>
				</td>
				<td width="*">
				<div style="width: 100%;">
					<div data-lui-type="lui/search_box!SearchBox">
						<script type="text/config">
						{
							placeholder: "${lfn:message('sys-ui:ui.criteria.search')}",
							width: '90%'
						}
					</script>
						<ui:event event="search.changed" args="evt">
							LUI('sourceList').tableRefresh({criterions:[{key:"key", value: [evt.searchText]}]});
						</ui:event>
					</div>
					</div>
				</td>
			</tr>
		</table>
	<div style="margin:20px auto;width:95%;">
			<div style="border: 1px #e8e8e8 solid;padding: 5px;height:420px;">
				<list:listview id="sourceList" >
					<ui:source type="AjaxJson">
						{"url":"/sys/mportal/sys_mportal_mportlet/sysMportalMportlet.do?method=select&rowsize=8"}
					</ui:source>
					<list:colTable sort="false" layout="sys.ui.listview.listtable" 
						onRowClick="selectPortlet('!{fdPortletId}','!{fdName}','!{description}')">
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdName,fdModuleName,description,fdModule" ></list:col-auto>
						<list:col-html title="" style="width:80px">
							{$
								<a class='com_btn_link' href="javascript:void(0)" 
									onclick="selectPortlet('{%row['fdPortletId']%}','{%row['fdName']%}','{%row['description']%}')">
									${ lfn:message('button.select') }</a>
							$}
						</list:col-html>
					</list:colTable>
					<ui:event event="load" args="evt">
						  var datas = evt.table.kvData;
						  var portletId = "${param.portletId}";
		                  function getRowId(id) {
		                  	if(id=="") return "";
		                    for (var i = 0; i < datas.length; i ++) {
		                      if (datas[i].fdPortletId == id) {
		                        return datas[i].rowId;
		                      }
		                    }
		                    return "";
		                  }
						  var rowId = getRowId(portletId);
		                  if(rowId!=""){
		                  	evt.table.element.find("[data-lui-mark-id="+rowId+"]").css("background-color","#e9fdff");
		                  }
					</ui:event>
				</list:listview>
				<div style="height: 10px;"></div>
				<list:paging layout="sys.ui.paging.simple"></list:paging>
			</div>
	</div>
	</template:replace>
</template:include>