<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String modelName = request.getParameter("modelName");
	SysDictModel dictModel=SysDataDict.getInstance().getModel(modelName);
	if(dictModel!=null){
		pageContext.setAttribute("nameField", dictModel.getDisplayProperty());
	}
%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<style>
			.listContent{
				width:100%;
				margin-top:16px;
				border: 1px #e8e8e8 solid;
				padding:0px 5px;
				box-sizing: border-box;
			}
			.listContent .listview{
				min-height: 320px;
			}
			.listContent .lui_listview_listtable_th{
				font-weight: bolder;
				color: black;
			}
			.listContent .lui_paging_contentBox{
			    text-align: right !important;
			}
		</style>
		<script type="text/javascript">
			var _key = "${JsParam._key}".replace('---', '[*]');
			var selected = {};
			var isOpener = "${param.dialogType=='opener'}"=="true";
			function _source() {
				var winObj = null;
				if(isOpener){
					winObj = window.opener;
					if(winObj==null){//旧页面showModalDialog打开情况
						winObj = window.dialogArguments;
					}
				}else{
					winObj = window.parent;
				}
				var tempUrl = winObj['__' + _key + '_dataSource']();
				if(typeof(tempUrl)=="string"){
					return Com_SetUrlParameter(tempUrl,'rowsize','8'); 
				}else{
					if(tempUrl.init.length>0){
						var init=tempUrl.init.split(";");
						for(var i=0;i<init.length;i++){
							selected[init[i]]="-1";
						}
						var ajaxUrl=tempUrl.url;
						if(ajaxUrl.indexOf('&')!=-1){
							ajaxUrl=ajaxUrl.substring(0,ajaxUrl.indexOf('&'));
						}
						ajaxUrl='${LUI_ContextPath}'+ajaxUrl;
						$.ajax({
							url : ajaxUrl,
							type : 'post',
							data : {fdId:tempUrl.init,rowsize:'8'},
							async : true,           
							error : function(){
							} ,   
							success : function(data) {
								var dataArr=data.datas;
								for(var i=0;i<dataArr.length;i++){
									var dataId;
									var row={};
									for(var key in dataArr[i]){
										if(dataArr[i][key]['col']=='fdId'){
											dataId=dataArr[i][key]['value'];
										}
										row[dataArr[i][key]['col']]=dataArr[i][key]['value'];
									}
									row['rowId']='lui-rowId-'+row['index'];
									selected[dataId]=domain.stringify(row);
								}
							}
						});
					}
					return Com_SetUrlParameter(tempUrl.url,'rowsize','8'); 
				}
			}
			function _search(){
				seajs.use(['lui/topic'],function(topic){
					var keyword = LUI.$("#searchInput :text").val();
					var topicEvent = {
							criterions : [],
							query : []
						};
					topicEvent.criterions.push({"key":"_keyword","value":[keyword]});
					topic.publish("criteria.changed", topicEvent);				
				});
			}
			function _notify(data){
				var winObj = null;
				if(isOpener){
					winObj = window.opener;
				}else{
					winObj = window.parent;
				}
				if(winObj!=null){
					if(data){
						domain.call(winObj, _key, [data]);
					}else{
						domain.call(winObj, _key);
					}
				}else{//旧页面showModalDialog打开情况
					winObj = window.dialogArguments;
					if(winObj!=null){
						winObj[_key](data);
					}
				}
			}
			function _getSelectedData(){
				var rowContents = [];
				for(var key in selected){
					rowContents.push(selected[key]);
				}
				if(rowContents.length == 0){
					if('${param.mulSelect!=null}'=='true'){
						return {
							idField:"fdId",
							nameField:"${nameField}",
							data:[]
						};	
					}else{
						return {data:{}};
					}
				}
				if('${param.mulSelect!=null}'=='true'){
					return {
						idField:"fdId",
						nameField:"${nameField}",
						data:rowContents
					};	
				}else{
					return {data:rowContents[0]};
				}
			}
			function _checkChg(id){
				var mul = '${param.mulSelect}'=='true';
				var rowContentObj = $("#row_" + id);
				if(!mul){
					$("[name='List_Selected']").prop("checked",false);
					selected = [];
				}
				if(selected[id]){
					delete selected[id];
					rowContentObj.parents("tr").find("[name='List_Selected']").prop("checked",false);
				}else{
					selected[id] = rowContentObj.val();
					rowContentObj.parents("tr").find("[name='List_Selected']").prop("checked",true);
				}
				if(!mul && !isOpener){
					_notify();
				}
			}
			function _submit(){
				var rtData = _getSelectedData();
				if(rtData!=null){
					_notify(rtData);
					window.close();
				}
			}
			LUI.ready(function(){
				_search();
			});
		</script>
	</template:replace>
	<template:replace name="content">
		<div style="width: 95%;margin: 10px auto;">
			<c:if test="${param.dialogType=='opener'}">
				<div style="text-align: right;">
					<ui:button text="${ lfn:message('button.ok')}" onclick="_submit()"></ui:button>
					<ui:button text="${ lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" onclick="window.close();"></ui:button>
				</div>
				<br/>
			</c:if>
			<div style="display: table;width:100%;">
				<div style="display: table-cell; width:15%;font-size: 16px;">
					${ lfn:message('message.keyword')}:
				</div>
				<div style="display: table-cell;" id="searchInput">
					<div data-lui-type="lui/search_box!SearchBox"
						style="height:32px; line-height:32px;vertical-align: middle;">
						<script type="text/config">
						{
							placeholder: "${ lfn:message('sys-ui:ui.category.keyword') }",
							width: '90%'
						}
						</script>
						<ui:event event="search.changed" args="evt">
							_search();
						</ui:event>
					</div>
				</div>
			</div>
			<div class="listContent">
				<!-- 列表 --> 
				<list:listview id="listview" cfg-criteriaInit="true">
					<ui:source type="AjaxJson">
                    	{ url:_source()}
                	</ui:source>
					<!-- 列表视图 -->
					<list:colTable isDefault="false" name="columntable" onRowClick="_checkChg('!{fdId}');">
						<c:if test="${param.mulSelect!=null}">
							<list:col-html title="" headerStyle="width:10px;">
							{$
								<input type="checkbox" onclick="_checkChg('{%row.fdId%}');" name="List_Selected" 
									value="{%row.fdId%}" {%selected[row.fdId]==null?'':'checked'%}/>
							$}
							</list:col-html>
						</c:if>
						<list:col-serial />
						<list:col-auto/>
						<%--非 多选或单选 数据的情况 --%>
						<c:if test="${param.mulSelect==null || param.mulSelect==''}">
							<list:col-html title="${ lfn:message('sys-ui:ui.help.luiext.operation') }">
							{$
								<a class='com_btn_link' href="javascript:void(0)"  
								onclick="_checkChg('{%row.fdId%}');">${lfn:message('sys-ui:ui.vars.select')}</a>
							$}
							</list:col-html>
						</c:if>
						<list:col-html headerStyle="display:none;" style="display:none;">
						{$
							<input id="row_{%row.fdId%}" type="hidden" value='{%Com_HtmlEscape(domain.stringify(row))%}'/>
						$}
						</list:col-html>
					</list:colTable>
				</list:listview>
				<list:paging layout="sys.ui.paging.simple" />
			</div>
	</div>
	</template:replace>
</template:include>