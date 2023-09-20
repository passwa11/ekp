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
				min-height: 340px!important;
			}
			.listContent .lui_listview_listtable_th{
				font-weight: bolder;
				color: black;
			}
			.listContent .lui_paging_contentBox{
			    text-align: right !important;
			}
			.list_down{
				text-align: right;
				margin-top:30px;
				position: relative;
			}
			.list_down .select_total{
				text-align:left;
				position: absolute;
				left: 0;
				color:#999999;
				font-size: 12px;
			}
			.selected{
				background: url(../images/icon_radio_active.png);
			    background-repeat: no-repeat;
			    background-position: center center;
			}
			.not_selected{
				background: url(../images/icon_radio.png);
			    background-repeat: no-repeat;
			    background-position: center center;
			}
			.except{
				background: url(../images/icon_radio_unable.png);
			    background-repeat: no-repeat;
			    background-position: center center;
			}
		</style>
		<script type="text/javascript">
			var _key = "${JsParam._key}".replace('---', '[*]');
			var selected = {};
			var isOpener = "${param.dialogType=='opener'}"=="true";
			var exceptValue = "${param.exceptValue}";//排除的值
			console.log(exceptValue);
			var selectTotal = 0;//选择的数目
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
					//统计选中的数目
					var newSelected = {};
					for(var key in selected){
						if(!key || key == ""){
							continue;
						}
						//newSelected.key = selected.key;
						newSelected[key] = selected[key];
					}
					selectTotal = Object.keys(newSelected).length || 0;
					_changeTotal();
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
								console.log(dataArr);
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
								//统计选中的数目
								var newSelected = {};
								for(var key in selected){
									if(!key || key == ""){
										continue;
									}
									//newSelected.key = selected.key;
									newSelected[key] = selected[key];
								}
								selectTotal = Object.keys(newSelected).length || 0;
								_changeTotal();
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
					//#126243，列表视图业务操作选择置灰的按钮点击确定之后按钮文本显示有误
					//为解决#126243加了个isExcept_modeling判断
					if(!isExcept_modeling){
						if(data){
							domain.call(winObj, _key, [data]);
						}else{
							domain.call(winObj, _key);
						}
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
			var isExcept_modeling; //全局变量。#126243，列表视图业务操作选择置灰的按钮点击确定之后按钮文本显示有误
			function _checkChg(id){
				var mul = '${param.mulSelect}'=='true';
				var rowContentObj = $("#row_" + id);
				//若是被排除的数据则跳过
				isExcept_modeling = rowContentObj.parents("tr").find(".except").eq(0).css("display") == "none" ? false : true;
				if(isExcept_modeling){
					return;
				}
				if(!mul){
					$("[name='List_Selected']").prop("checked",false);
					selected = [];
					//改变内容
					var table = rowContentObj.parents("table")[0];
					_changeStatus(table);
					//减少选中的数目
					var exceptedNum = $(".exceptedNum[style*='display: inline-block']").size(); //获取排除的数量
					if(selectTotal > 0 && exceptedNum != selectTotal){
						selectTotal--;
						_changeTotal();
					}
				}
				if(selected[id]){
					delete selected[id];
					rowContentObj.parents("tr").find("[name='List_Selected']").prop("checked",false);
					//改变内容
					rowContentObj.parents("tr").find(".selected").eq(0).css("display","none");
					rowContentObj.parents("tr").find(".not_selected").eq(0).css("display","inline-block");
					//减少选中的数目
					selectTotal--;
					_changeTotal();
				}else{
					selected[id] = rowContentObj.val();
					rowContentObj.parents("tr").find("[name='List_Selected']").prop("checked",true);
					//改变内容
					rowContentObj.parents("tr").find(".selected").eq(0).css("display","inline-block");
					rowContentObj.parents("tr").find(".not_selected").eq(0).css("display","none");
					//增加选择的数目
					selectTotal++;
					_changeTotal();
				}
				//start 当都被选中时候，多选按钮选中
				var expect=0;
				var ch = $("input[name='List_Selected']");

				for (var i = 0; i < ch.length; i++) {

					var c = $(ch[i]).next().children();

					var str=$($(c).get(1)).attr("style");

					if(str.indexOf("inline-block")!=-1){
						expect++;
					}
				}
				var num=parseInt(expect+selectTotal);
				var len=$(".lui_listview_columntable_table tbody tr").length;
				if(num==len){
					$(".checkAll").prop("checked",true);
				}else{
					$(".checkAll").prop("checked",false);
				}
				//end
				if(!mul && !isOpener){
					_notify();
				}
			}
			function _changeStatus(table){//修改所有非排除的数据为不选中
				var $trs = $(table).find("tr");
				for(var i=0; i<$trs.length; i++){
					var trObj = $trs[i];
					var isExcept = $(trObj).find(".except").eq(0).css("display") == "none" ? false : true;
					if(!isExcept){
						$(trObj).find(".selected").eq(0).css("display","none");
						$(trObj).find(".not_selected").eq(0).css("display","inline-block");
					}
				}
			}
			function _submit(){
				var rtData = _getSelectedData();
				if(rtData!=null){
					_notify(rtData);
					window.close();
				} 
			}
			function _changeTotal(){
				$("#selectTotal").text(selectTotal);
			}
			LUI.ready(function(){

				if(_key.indexOf("dialog_listOpers")!=-1){
					$('#selectBoxAll').css("display","none");
				}
				_search();
				//订阅列表加载完事件
				seajs.use(['lui/topic'], function(topic) {
					topic.subscribe('list.loaded', function() {
						//初始化
						for(var key in selected){
							var rowContentObj = $("#row_" + key);
							//改变内容
							rowContentObj.parents("tr").find(".selected").eq(0).css("display","inline-block");
							rowContentObj.parents("tr").find(".not_selected").eq(0).css("display","none");
							rowContentObj.parents("tr").find(".except").eq(0).css("display","none");
						}
						//排除值，进行切换
						if(exceptValue){
							var exceptValueArr = exceptValue.split(";");
							for(var i=0; i<exceptValueArr.length; i++){
								if(exceptValueArr[i]){
									var $parent = $("input[value='"+exceptValueArr[i]+"']").parents("tr").eq(0);
									$parent.find(".except").css("display","inline-block");
									$parent.find(".selected").css("display","none");
									$parent.find(".not_selected").css("display","none");
								}
							}
						}
					});
				});
			});

			//多选方法
			function checkAll(val) {
				var singleNum=selectTotal; //记录单击次数
				var ch = $("input[name='List_Selected']");
				if (val == true) {
					selected = {};
					for (var i = 0; i < ch.length; i++) {

						var c = $(ch[i]).next().children();

						var str=$($(c).get(1)).attr("style");

						if(str.indexOf("inline-block")!=-1){
							continue;
						}else{
							var id=$(ch[i]).val();
							var rowContentObj = $("#row_" + id);
							selected[id] = rowContentObj.val();
							$($(c).get(0)).css("display", "block");
							$($(c).get(2)).css("display", "none");
							selectTotal++;
							//_changeTotal();
						}
					}
					selectTotal=selectTotal-singleNum;
					_changeTotal();
				}else{
					//var ch = $("input[name='List_Selected']");
					for (var i = 0; i < ch.length; i++) {

						var c = $(ch[i]).next().children();

						var str=$($(c).get(1)).attr("style");

						if(str.indexOf("inline-block")!=-1){
							continue;
						}else{
							$($(c).get(0)).css("display", "none");
							$($(c).get(2)).css("display", "block");
							var id=$(ch[i]).val();
							delete selected[id];
							//selectTotal--;
							//_changeTotal();
						}
						selected = {};
						selectTotal=0;
						_changeTotal();
					}
				}
			}
		</script>
	</template:replace>
	<template:replace name="content">
		<style>
			#selectBoxAll{
				position: absolute;
				margin-top: 10px;
				margin-left: 9px;
				top: 60px;
				float: left;
				z-index:2;
				font-size: inherit;
				font-weight: bold;
			}
		</style>
		<div style="width: 95%;margin: 10px auto;">
			<div style="display: table;width:100%;">
				<%-- <div style="display: table-cell; width:15%;font-size: 16px;">
					${ lfn:message('message.keyword')}:
				</div> --%>
				<div style="display: table-cell;left:0px" id="searchInput">
					<div data-lui-type="lui/search_box!SearchBox"
						style="height:32px; line-height:32px;vertical-align: middle;width:40%">
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
	    	<div id="selectBoxAll">
				<input type="checkbox"  class="checkAll" onchange="checkAll(this.checked)" style="position: relative;top: 2px;">${lfn:message('sys-modeling-base:modeling.common.selectAll')}
			</div>
			<div class="listContent">
				<!-- 列表 --> 
				<list:listview id="listview" cfg-criteriaInit="true">
					<ui:source type="AjaxJson">
                    	{ url:_source()}
                	</ui:source>
					<!-- 列表视图 -->
					<list:colTable isDefault="false" name="columntable" onRowClick="_checkChg('!{fdId}');">
						<c:if test="${param.mulSelect eq 'false'}">
							<list:col-html title="" headerStyle="width:15px;">
							{$
								<input style="display: none" type="checkbox" name="List_Selected"
									value="{%row.fdId%}" {%selected[row.fdId]==null?'':'checked'%}/>
								<div style="display:inline-block">
									<div class="selected" style="display:none;width:15px;height:15px;border-radius: 50%"></div>
									<div class="except exceptedNum" style="display:none;width:15px;height:15px;border-radius: 50%"></div>
									<div class="not_selected" style="display:inline-block;width:15px;height:15px;border-radius: 50%"></div>
								</div>
							$}
							</list:col-html>
						</c:if>
						<c:if test="${param.mulSelect eq 'true'}">
							<list:col-html title="" headerStyle="width:15px;">
								{$
								<input style="display: none" type="checkbox" name="List_Selected"
									   value="{%row.fdId%}" {%selected[row.fdId]==null?'':'checked'%}/>
								<div style="display:inline-block">
									<div class="selected" style="display:none;width:15px;height:15px;background: url(../images/icon_checkbox_active.png); background-size: contain;"></div>
									<div class="except exceptedNum" style="display:none;width:15px;height:15px;background: url(../images/icon_checkbox_unable.png); background-size: contain;"></div>
									<div class="not_selected" style="display:inline-block;width:15px;height:15px;background: url(../images/icon_checkbox.png); background-size: contain;"></div>
								</div>
								$}
							</list:col-html>
						</c:if>
						<%-- <list:col-serial /> --%>
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
			<c:if test="${param.dialogType=='opener'}">
				<div class="list_down">
					<div class="select_total">
						已选&nbsp;<span id="selectTotal">0</span>&nbsp;个
					</div>
					<ui:button text="${ lfn:message('button.ok')}" onclick="_submit()" style="width:90px;height:30px;line-height:30px;margin-right:10px"></ui:button>
					<ui:button text="${ lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" style="width:90px;height:30px;line-height:30px" onclick="window.close();"></ui:button>
				</div>
				<br/>
			</c:if>
		</div>
	</template:replace>
</template:include>