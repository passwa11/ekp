<%@ page import="com.landray.kmss.sys.modeling.base.util.ViewIncParamUtil" %>
<%@ page import="com.landray.kmss.sys.modeling.base.util.ViewoperUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.list">
	<template:replace name="head">
		<style>
			#listview .lui_listview_body{
				overflow-x: auto !important;
			}
			.lui_toolbar_popup.lui-component.lui_popup_border_content{
				z-index: 1032 !important;
			}
			.lui_list_operation .lui_toolbar_frame .lui_toolbar_btn_l div.lui_widget_btn_txt {
				vertical-align: top;
			}
			.lui_toolbar_sort_group_popup.lui-component.lui_popup_border_content{
				z-index: 1032 !important;
			}
			#ui-datepicker-div{
				z-index: 1033 !important;
			}
		</style>
		<c:if test="${listViewType eq '0'}">
			<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/collectionListView.css?s_cache=${LUI_Cache}" rel="stylesheet">
		</c:if>
		<c:if test="${listViewType eq '1'}">
			<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/collectionCardListView.css?s_cache=${LUI_Cache}" rel="stylesheet">
			<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/template.css?s_cache=${LUI_Cache}" rel="stylesheet">
		</c:if>
		<c:if test="${listViewType eq '2'}">
		    <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/boardTemplate.css?s_cache=${LUI_Cache}" />
	    </c:if>
	</template:replace>
	<template:replace name="content">
		<!-- 统计项 -->
		<%@ include file="/sys/modeling/base/listview/ui/statistics.jsp" %>
		<!-- 筛选器 -->
		<%@ include file="/sys/modeling/base/listview/ui/criteria.jsp" %>
		
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<c:if test="${not empty viewOrderInfo.columns}">
				<!-- 分割线 -->
				<div class="lui_list_operation_line"></div>
				<!-- 排序 -->
				<div class="lui_list_operation_sort_btn">
					<%@ include file="/sys/modeling/base/listview/ui/order.jsp"  %>
				</div>
			</c:if>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<c:if test="${listViewType ne '2'}">
					<list:paging layout="sys.ui.paging.top" >
					</list:paging>
				</c:if>
				<c:if test="${listViewType eq '2'}">
					<%@ include file="/sys/modeling/base/listview/ui/boardGroup.jsp" %>
					<c:if test="${groupType eq '1'}">
						<html:hidden property="customCfg" value="${customCfg}"></html:hidden>
					</c:if>
				</c:if>
			</div>

			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="toolbar" style="float:right; padding-right: 5px" count="3">
						<%-- 业务操作按钮 --%>
						<%@ include file="/sys/modeling/base/view/ui/viewopers.jsp"%>
					</ui:toolbar>
				</div>
				<c:if test="${listViewType eq '2'}">
					<div style="float:right">
						<div style="display:inline-block;">
							<ul class="model-board-list-view-module ">
								<li data-value="normal" class="active"><i class="mode-normal" style="color:#999;font-size:40px"></i></li>
								<li data-value="summary"><i class="mode-summary" style="color:#999;font-size:40px"></i></li>
								<li data-value="simple"><i class="mode-simple" style="color:#999;font-size:40px"></i></li>
							</ul>
						</div>
					</div>
				</c:if>
				<c:if test="${listViewType eq '1'}">
					<div style="float:right">
						<div style="display:inline-block;">
							<ul class="model-card-list-view-module">
								<li data-value="2" class="<c:if test="${columnNum eq '2'}">active</c:if>" onclick="changeCardColumn(this);"><i class="mode-normal" style="color:#999;font-size:40px"></i></li>
								<li data-value="4" class="<c:if test="${columnNum eq '4'}">active</c:if>" onclick="changeCardColumn(this);"><i class="mode-summary" style="color:#999;font-size:40px"></i></li>
								<li data-value="5" class="<c:if test="${columnNum eq '5'}">active</c:if>" onclick="changeCardColumn(this);"><i class="mode-simple" style="color:#999;font-size:40px"></i></li>
							</ul>
						</div>
					</div>
				</c:if>
			</div>
		</div>
		<!-- 内容列表 -->
		<c:choose>
			<c:when test="${ listViewType == '1' }">
				<div class="lui_list_card_wrap index${columnNum}">
					<list:listview id="listview">
						<ui:source type="AjaxJson">
							<c:if test="${not empty newListView}">
								<%=ViewIncParamUtil.buildNewAjaxUrl(request) %>
							</c:if>
							<c:if test="${empty newListView}">
								<%=ViewIncParamUtil.buildAjaxUrl(request) %>
							</c:if>
						</ui:source>
						<!-- 格子视图 -->
						<list:gridTable name="gridtable" id="card_view_gridtable" columnNum="${columnNum}">
							<list:row-template>
								<c:import url="/sys/modeling/base/listview/ui/sys_modeling_card_tmpl.jsp" charEncoding="UTF-8"></c:import>
							</list:row-template>
						</list:gridTable>
					</list:listview>
				</div>
			</c:when>
			<c:when test="${ listViewType == '2' }">
				<div>
					<div data-lui-type="lui/listview/template!Template"
						 style="display:none;" id="lui-id-48"
						 class="lui-component" data-lui-cid="lui-id-48"
						 data-lui-parse-init="44">
						<script type="text/code" id="boardtemplate">
									<c:import url="/sys/modeling/base/listview/ui/board/sys_modeling_board_tmpl.jsp" charEncoding="UTF-8"></c:import>
									</script>
					</div>
				</div>
				<div class="boardContainer modeNormal">
					<div class="cardClassifyContent">
						<div class="cardClassifyHeader clearfix">
							<div class="cardClassifyHeaderText"></div>
							<div class="cardClassifyHeaderCount">0</div>
							<span class="cardClassifyHeaderBtn"></span>
						</div>
						<div class="cardClassifyDetails">
							<div class="cardClassifyDetailsNull">
								${lfn:message("sys-modeling-main:modeling.listview.board.nodata")}
							</div>
						</div>
					</div>
				</div>

			</c:when>
			<c:otherwise>
				<list:listview id="listview" style="position: relative;">
					<ui:source type="AjaxJson">
						<c:if test="${not empty newListView}">
							<%=ViewIncParamUtil.buildNewAjaxUrl(request) %>
						</c:if>
						<c:if test="${empty newListView}">
							<%=ViewIncParamUtil.buildAjaxUrl(request) %>
						</c:if>
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" rowHref="<%=ViewoperUtil.buildRowHref(request) %>">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="${fdDisplay }"></list:col-auto>
					</list:colTable>
					<ui:event topic="list.loaded">
						Dropdown.init();
						iframeAutoFit();
					</ui:event>
				</list:listview>
			</c:otherwise>
		</c:choose>
		<c:if test="${listViewType ne '2'}">
			<c:if test="${listViewType == '0'}">
				<!-- 底部数据统计 -->
				<%@ include file="/sys/modeling/base/listview/ui/footStatistics.jsp" %>
			</c:if>
			<!-- 分页 -->
			<list:paging layout="${pageLayout}"/>
		</c:if>

	 	<script>
			var listViewLang={
				none:'${lfn:message("sys-modeling-base:listview.top.statistics.show.num.unit.null")}',
				noData:'${lfn:message("sys-modeling-base:listview.no.data")}'
			}
			var ___url = "";
			function changeCardDetailsOpt(val){
				//val.stopPropagation();
				if($(val).is(':checked')){
					$(val).parents('.cardClassifyDetails').addClass('selected')
				}else{
					$(val).parents('.cardClassifyDetails').removeClass('selected')
				}
			}

	 		//导入成功刷新列表
		 	seajs.use([ 'sys/ui/js/dialog' ,'lui/topic', 'lui/data/source','lui/parser', 'sys/modeling/base/listview/ui/board/boardtable',
						'lui/listview/template','lui/listview/listview','lui/view/Template','lui/jquery'],
					function(dialog,topic,source,parser,boardTable,template,listview,tmpl,$) {

				window.showMoreAttachment = function(e,data,attIconList){
					//停止冒泡
					// 如果提供了事件对象，则这是一个非IE浏览器
					if ( e && e.stopPropagation ) {
						// 因此它支持W3C的stopPropagation()方法
						e.stopPropagation();
					} else {
						// 否则，我们需要使用IE的方式来取消事件冒泡
						window.event.cancelBubble = true;
					}
					$('.listViewAttachmentMoreDiv').css("display","none");
					if($(e).find(".listViewAttachmentMoreDiv").length > 0){
						$(e).find(".listViewAttachmentMoreDiv").css("display","");
					}else{
						//构建更多选项dom
						var $div = $("<div class='listViewAttachmentMoreDiv'></div>");
						for (var i = 0; i < data.length; i++) {
							var $item = $("<div class='listViewAttachmentDiv' onclick='jumpToAttachmentDetail(this)' attMainId='" + data[i].fdId + "' />");
							$item.append("<i class='listViewAttachmentDiv-i-icon' style='background-image: url(../../attachment/view/img/file/2x/" + attIconList[i] + ")'> </i>");
							$item.append("<i class='listViewAttachmentDiv-i-text' title='" + data[i].fdFileName + "'> " + data[i].fdFileName + "</i>");
							$div.append($item);
						}

						var top = $(e).offset().top;
						var left = $(e).offset().left;
						$div.css({
							"top":top -105,
							"left":left
						})
						$(e).append($div);
					}

				}

				$(document).on('click',function(e) {
					$('.listViewAttachmentMoreDiv').css("display","none");
				})

				window.jumpToAttachmentDetail = function(e){
					//停止冒泡
					// 如果提供了事件对象，则这是一个非IE浏览器
					if ( e && e.stopPropagation ) {
						// 因此它支持W3C的stopPropagation()方法
						e.stopPropagation();
					} else {
						// 否则，我们需要使用IE的方式来取消事件冒泡
						window.event.cancelBubble = true;
					}
					var attMainId = $(e).attr("attMainId");
					var url = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&open=1&fdId="+attMainId;
					Com_OpenWindow(url, "_blank");
				}

		 		window.init = function(){
		 			<c:if test="${listViewType eq '2'}">
						<c:if test="${groupType eq '0'}">
							renderBoardListView("${fdDefaultGroup}");
						</c:if>
						<c:if test="${groupType eq '1'}">
							renderBoardListViewForCustom("${fdDefaultGroup}");
						</c:if>
						getBoardListCount();
						setBoardContainerSize();
					</c:if>
				};
				window.getFixMaxHeight = function() {
					var $body = $(window.top.document.body),
							$header = $body.find('.lui_portal_header'),
							$headerH = $header && $header.length > 0 ? $header.height() : 0,
							$panel = $body.find('[data-lui-mark="panel.nav.frame"]'),
							$panelH = $panel && $panel.length > 0 ? 55 : 25,
							$offsetTop = 100;
					var criteria1 = $("#criteria1") && $("#criteria1").length>0?0:50;
					return $headerH + $offsetTop + $panelH - criteria1;
				};
		 		window.setBoardContainerSize=function (){
		 			//计算最大高度
					var height = window.screen.height>600?window.screen.height:720;
					height = height - getFixMaxHeight();
					$(".cardClassifyContent").css("max-height",(height-144) +"px");
					$(".boardContainer").css("max-height",height +"px");
					$(".boardContainer").css("max-width",document.documentElement.clientWidth - 42 +"px");
				};

		 		LUI.ready(function (){
		 			if(LUI("sortToolbar")){
						LUI("sortToolbar").element.find("[data-lui-type='lui/listview/sort!Sort']").on("click",function () {
							if(!___url){
								var listView = LUI("listview");
								___url = listView.source.ajaxConfig.url + "&sortChange=true";
								listView.source.setUrl(___url);
							}
						})
					}
					<c:if test="${listViewType eq '1'}">
					$(".lui_list_operation_order_btn").find("input[type='checkbox']").on('click',function(){
						var attChecked = $(this).is(":checked");
						$(".cardClassifyDetails").each(function() {
							if(attChecked == true && !$(this).hasClass("selected")){
								$(this).addClass("selected");
							}
							if(attChecked == false){
								$(this).removeClass("selected");
							}
						});
					});
					</c:if>
				})
				window.boardListViewArray=[];
		 		window.renderBoardListView = function(value){
		 			if(!value){
		 				return;
					}
					var groupUrl = '<%=ViewIncParamUtil.buildGroupAjaxUrl(request) %>';
					var listviewUrl = "<%=ViewIncParamUtil.buildBoardListViewAjaxUrl(request) %>";
					$.ajax({
						url:Com_Parameter.ContextPath + groupUrl,
						type:"post",
						dataType:"json",
						data:{groupField:value},
						success:function(result){
							if(result && result.count>0){
								$(".boardContainer").empty();
								for(var i =0;i<result.count;i++){
									var __url = listviewUrl;
									var boardListView =new listview.ListView({id:"boardListView"+i});
									var $boardListViewHtml = $("<div class='cardClassifyContent'/>");
									var boardTableView = new boardTable.BoardTable({isDefault:true,name:"boardTable",container:$boardListViewHtml,title:result.data[i].name});
									var datasource = new source.AjaxJson({
										url : __url,
										params:{fieldName:value,fieldValue:result.data[i].value},
										commitType:'POST'
									});
									var boardViewTemp = new template.Template();
									var temp = new tmpl();
									boardViewTemp.code = $('#boardtemplate').html();
									boardViewTemp.parent = boardTableView;
									boardViewTemp.setTmpl(temp);
									boardTableView.setTemplate(boardViewTemp);
									boardListView.addChild(datasource);
									boardListView.addChild(boardTableView);
									boardListView.startup();
									boardTableView.parent = boardListView;
									boardTableView.startup();
									if(boardTableView.layout){
										boardTableView.layout.startup();
									}
									boardViewTemp.startup();
									boardListView.draw();
									window.boardListViewArray.push(boardListView);
									$(".boardContainer").append($boardListViewHtml);
								}
							}
							window.setBoardContainerSize();
						}
					})
				}

				window.renderBoardListViewForCustom = function(value){
					if(!value){
						return;
					}
					var customCfg = $("[name=customCfg]").val();
					customCfg = JSON.parse(customCfg);
					var result = [];
					for (var i = 0; i < customCfg.length; i++) {
						var groupRule = customCfg[i];
						if(groupRule.name == value){
							result = groupRule.groupRules;
						}
					}
					result.unshift({name:""});
					var listviewUrl = "<%=ViewIncParamUtil.buildBoardListViewAjaxUrl(request) %>";
					$(".boardContainer").empty();
					if(result && result.length>0){
						for(var i =0;i<result.length;i++){
							var __url = listviewUrl;
							var boardListView =new listview.ListView({id:"boardListView"+i});
							var $boardListViewHtml = $("<div class='cardClassifyContent'/>");
							var boardTableView = new boardTable.BoardTable({
								isDefault:true,
								name:"boardTable",
								container:$boardListViewHtml,
								title:result[i].name || "${lfn:message('sys-modeling-main:modeling.listview.board.otherCategory')}"});
							var datasource = new source.AjaxJson({
								url : __url,
								params:{fieldName:value,fieldValue:result[i].name},
								commitType:'POST'
							});
							var boardViewTemp = new template.Template();
							var temp = new tmpl();
							boardViewTemp.code = $('#boardtemplate').html();
							boardViewTemp.parent = boardTableView;
							boardViewTemp.setTmpl(temp);
							boardTableView.setTemplate(boardViewTemp);
							boardListView.addChild(datasource);
							boardListView.addChild(boardTableView);
							boardListView.startup();
							boardTableView.parent = boardListView;
							boardTableView.startup();
							if(boardTableView.layout){
								boardTableView.layout.startup();
							}
							boardViewTemp.startup();
							boardListView.draw();
							window.boardListViewArray.push(boardListView);
							$(".boardContainer").append($boardListViewHtml);
						}
					}
				}

				window.getBoardListCount=function (){
					var listviewUrl = "<%=ViewIncParamUtil.buildBoardListViewAjaxUrl(request) %>";
					listviewUrl = listviewUrl.substring(1);
					var countUrl = listviewUrl + "&rowsize=1";
					$.ajax({
						url:Com_Parameter.ContextPath + countUrl,
						type:"post",
						dataType:"json",
						success:function(result){
							if(result){
								topic.publish("modeling.board.list.count",{totalSize:result.page.totalSize,viewType:"2"});
							}
						}
					})
				}

				window.clearBoardListView=function(){
					$(".lui_list_board_wrap").empty();
					for(var i=0;i<window.boardListViewArray.length;i++){
						window.boardListViewArray[i].destroy();
					}
					window.boardListViewArray=[];
				}
				window.init();
				window.fdGroupChange = function(obj){
		 			var value= $(obj).data("value");
					clearBoardListView();
					<c:if test="${groupType eq '0'}">
					renderBoardListView(value);
					</c:if>
					<c:if test="${groupType eq '1'}">
					renderBoardListViewForCustom(value);
					</c:if>
				}
				window.changeCardColumn = function(val){
					var module = $(val).data("value");
					var className = "index" + module;
					var tableColumn = LUI("listview").table.columnNum;
					if(tableColumn == module){
						return;
					}
					$(val).addClass("active");
					$(".model-card-list-view-module li").not(val).removeClass("active");
					LUI("listview").table.columnNum = module;
					topic.publish("list.refresh");
					$(".lui_list_card_wrap").removeClass("index2").removeClass("index4").removeClass("index5").addClass(className);
				}

	 			topic.subscribe('importPage', function() {
	 		          topic.publish("list.refresh");
	 		    });

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					var listViewType = "${listViewType}";
					if(listViewType == "2" && LUI("boardListView0") == undefined){
						window.location.reload();
					}else{
						//在引入的JS文件里已经进行刷新此处不再进行刷新 （/sys/modeling/base/resources/js/index.js）
						//topic.publish("list.refresh");
					}
				});

				topic.subscribe('list.loaded',function(datas){
					$(".cardClassifyDetails").find(".cardClassifyDetailsOpt").each(function() {
						if($(this).is(':checked')){
							$(this).parents('.cardClassifyDetails').addClass('selected')
						}else{
							$(this).parents('.cardClassifyDetails').removeClass('selected')
						}
					});
					$('.statistics-content-value').each(function () {
						$(this).attr("title","0");
						$(this).find("p").text("0");
					})
					var listData = datas.listview._data.datas;
					if(listData ){
						var staticsInfos;
						if( listData.length > 0){
							for(var i = 0;i < listData[0].length;i++){
								if(listData[0][i].col == "staticsInfos"){
									var parseValue = listData[0][i].value ? listData[0][i].value : '[]';
									staticsInfos =  JSON.parse(parseValue);
									break;
								}
							}
						}else{
							if('' != '${statisticsInfo}'){
								staticsInfos =  JSON.parse('${statisticsInfo}');
							}
						}

						if(staticsInfos && staticsInfos.length > 0){
							$(".statistics-body").css("display","block")
							$(".statistics-container").css("display","block")

							$(".statistics-content-center").empty();
							for(var i = 0;i < staticsInfos.length;i++){
								var showType = staticsInfos[i].showType;
								if("1" == showType){
									var html = '<div class="statistics-content" id="statistics-content-'+i+'">'
											+ '<div class="statistics-content-name"><p>'+staticsInfos[i].name+'</p></div>'
											+ '<div class="statistics-content-value">'
											+ '<div class="statistics-content-value-num">'+ staticsInfos[i].value +'</div><div class="statistics-content-value-unit">'+staticsInfos[i].unit +'</div>'
											+ '</div>'
											+'</div>'
								}else{
									var html = '<div class="statistics-content" id="statistics-content-'+i+'">'
											+ '<div class="statistics-content-name"><p>'+staticsInfos[i].name+'</p></div>'
											+ '<div class="statistics-content-value">'
											+ '<div class="statistics-content-value-num">'+ staticsInfos[i].value +'%</div><div class="statistics-content-value-percent"></div>'
											+ '</div>'
											+'</div>'
								}
								$(".statistics-content-center").append(html)
							}

							//判断是否显示左右切换图标
							var maxIndex = $(".statistics-content").length - 1 ;
							var firstTop = $("#statistics-content-0").offset().top;
							var endTop = $("#statistics-content-"+maxIndex).offset().top;
							if(firstTop != endTop){
								//显示
								$(".statistics-content-left").css("background","url(../base/resources/images/listview/left@2x.png) no-repeat center");
								$(".statistics-content-left").css("background-size","16px 16px");
								$(".statistics-content-left").hover(function () {
									$(".statistics-content-left").css("background","url(../base/resources/images/listview/left_active@2x.png) no-repeat center");
									$(".statistics-content-left").css("background-size","16px 16px");
								},function(){
									$(".statistics-content-left").css("background","url(../base/resources/images/listview/left@2x.png) no-repeat center");
									$(".statistics-content-left").css("background-size","16px 16px");
								})
								$(".statistics-content-right").css("background","url(../base/resources/images/listview/right@2x.png) no-repeat center");
								$(".statistics-content-right").css("background-size","16px 16px");
								$(".statistics-content-right").hover(function () {
									$(".statistics-content-right").css("background","url(../base/resources/images/listview/right_active@2x.png) no-repeat center");
									$(".statistics-content-right").css("background-size","16px 16px");
								},function(){
									$(".statistics-content-right").css("background","url(../base/resources/images/listview/right@2x.png) no-repeat center");
									$(".statistics-content-right").css("background-size","16px 16px");
								})
							}
						}else{
							$(".statistics-container").css("display","none")
							$(".statistics-body").css("display","none")
						}
					}

				})
	 			//地址本
	 			window.setAddressCriteriaVal = function(item,criteriaObj){
	 				//默认值
	 				if(item.config.defaultValue){
		 				var defaultValue = JSON.parse(item.config.defaultValue);
		 				var model = item.children[1].criterionSelectElement.selectedValues.model;
		 				var values = [];
		 				if(defaultValue != null && defaultValue.length > 0){
		 					if(defaultValue.length > 1){
			 					//触发多选
				 				if(item.config.canMulti){
				 					LUI(item.children[1].cid).setMulti(true);
				 				}
		 					}
		 					for(var i=0; i<defaultValue.length; i++){
			 					values.push(defaultValue[i].value);
			 					var isExit = false;
			 					for(var j=0; j<model.length; j++){
			 						if(model[j].value == defaultValue[i].value){
			 							isExit = true;
			 						}
			 					}
			 					if(!isExit){
			 						if(item.config.conditionType == "person")
			 							model.push(defaultValue[i]);
			 					}
			 				}
		 					item.children[1].criterionSelectElement.selectedValues.updateModel(model);
			 				item.children[1].criterionSelectElement.selectedValues.addAll(values); 
		 				}
	 				}
	 			}
	 			//枚举、文档状态（有流程）
	 			window.setEnumCriteriaVal = function(item,criteriaObj){
	 				//默认值
	 				if(item.selectBox.criterionSelectElement.config.defaultValue){
	 					var defaultValue = JSON.parse(item.selectBox.criterionSelectElement.config.defaultValue);
		 				var values = [];
		 				if(defaultValue != null){
		 					if(defaultValue.length > 1){
			 					//触发多选
				 				if(item.config.canMulti){
				 					LUI(item.selectBox.cid).setMulti(true);
				 				}
		 					}
		 					for(var i = 0 ;i < defaultValue.length; i++){
		 						values.push(defaultValue[i].value);
		 					}
		 					item.selectBox.criterionSelectElement.selectedValues.addAll(values);
		 				}
	 				}
	 			}
	 			//数字，金额
	 			window.setNumberCriteriaVal = function (item,criteriaObj){
	 				//默认值
	 				if(item.config.defaultValue){
		 				var defaultValue = JSON.parse(item.config.defaultValue);
		 				var values = [];
		 				var isnull = false;
		 				if(defaultValue != null){
			 				for (var j = 0;j < defaultValue.length;j++){
			 					var n = Number(defaultValue[j].value);
			 					if (isNaN(n)){
			 						//不是数字
			 						isnull = false;
			 						break;
			 					}
			 					values.push(defaultValue[j].value);
			 					if(defaultValue[j].value != ""){
			 						isnull = true;
			 					}
			 				}
		 				}
		 				if(isnull){
		 					item.selectBox.criterionSelectElement.selectedValues.addAll(values);
		 				}
	 				}
	 			}
	 			//时间
	 			window.setTimeCriteriaVal = function(item,criteriaObj){
	 				//默认值
	 				if(item.config.defaultValue){
		 				var defaultValue = JSON.parse(item.config.defaultValue);
		 				var isnull = false;
		 				for(var i = 0;i < defaultValue.length;i++ ){
		 					var regexs = /^(([0-2][0-3])|([0-1][0-9])):[0-5][0-9]$/;
		 					if(defaultValue[i] != ""){
			 					isnull = true;
		 						if(!regexs.test(defaultValue[i])){
			 						return
			 					}
		 					}
		 				}
		 				if(isnull){
		 					item.selectBox.criterionSelectElement.selectedValues.addAll(defaultValue);
		 				}
	 				}
	 			}
	 			//文本
	 			window.setTextCriteriaVal = function(item,criteriaObj){
	 				var values = [];
	 				//默认值
	 				if(item.config.defaultValue){
	 					var defaultValue = item.config.defaultValue;
		 				values.push(defaultValue);
		 				item.selectBox.criterionSelectElement.selectedValues.addAll(values);
	 				}
	 			}
	 			//日期
	 			window.setDateCriteriaVal = function(item,calendarObj){
	 				var allDates = item.selectBox.criterionSelectElement.allDates;
	 				if(item.config.defaultValue){
	 					setDateCriteriaValModify(allDates,item,item.config.defaultValue);
	 				}
	 			}
	 			//修改日期类型组件values
	 			window.setDateCriteriaValModify = function(allDates,item,type){
	 				if(type.indexOf("[") == -1){
	 					var values = [];
						var allDatasArr = allDates[type];
						if(allDatasArr){
							for(var i = 0;i < allDatasArr.length;i++){
								var obj = {};
								obj.text = allDatasArr[i];
								obj.value = allDatasArr[i];
								values.push(obj);
							}
							item.selectBox.criterionSelectElement.selectedValues.addAll(values);
						}
	 				}
	 			}
		 	});
	 		
		 	Com_AddEventListener(window,"load",function(){
		 		$(document).on("click",".lui_paging_next_center",function(){
		 			$(window.parent.document).scrollTop(0);
		 		})
		 		
		 		$(document).on("click",".lui_paging_num_left",function(){
		 			$(window.parent.document).scrollTop(0);
		 		})
		 		//排序字段悬浮显示全称
		 		$(document).on("mouseover",".lui_widget_btn_txt",function(){
		 			var text = $(this).text();
		 			$(this).attr("title",text);
		 		})
		 		
		 	});
			var listOption = {
				fdAppModelId : '${param.fdAppModelId}',
				isFlow : '${param.isFlow}',
				fdDisplayCssObj : '${fdDisplayCss}',
				allField : '${allField}',
				modelDict : '${modelDict}'
			}
	    	Com_IncludeFile("index.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
			Com_IncludeFile("listview_export.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
			Com_IncludeFile("iframe.js",Com_Parameter.ContextPath+'sys/modeling/main/resources/js/view/','js',true);
			//Com_IncludeFile("select_panel.js", "${LUI_ContextPath}/sys/ui/js/criteria/", 'js', true);
			Com_IncludeFile("index_display_css.js", Com_Parameter.ContextPath+'sys/modeling/base/listview/ui/js/', 'js', true);
			Com_IncludeFile("index_display_css_card.js", Com_Parameter.ContextPath+'sys/modeling/base/listview/ui/js/', 'js', true);

			//筛选器设置默认值
			var num = setInterval(function () {
            var criteria = $(".criteria")[0];
            if (!criteria) {
               return;
            }
            var uid = $(criteria).attr("data-lui-cid");
            //打开筛选
            var criteriaObj = LUI(uid);
            if (!criteriaObj.isDrawed || !criteriaObj.moreAction) {
              return;
            }
            clearInterval(num);
            criteriaObj.expandCriterions(criteriaObj.expand);
            //初始化筛选器的值
            setTimeout(function(){
                var criteriaArr = criteriaObj.criterions;
                for (var i = 0; i < criteriaArr.length; i++) {
                    var item = criteriaArr[i];
                    var bsnsType = item.selectBox.criterionSelectElement.config.conditionBusinessType || "";
                    if(item.config.conditionType == "DateTime" || item.config.conditionType == "Date" ){
                    	var calendar = $(".criterion-calendar")[0];
						if(calendar){
							//日期
							uid = $(calendar).attr("data-lui-cid");
							var calendarObj = LUI(uid);
		                    setDateCriteriaVal(item,calendarObj);
						}
                    }else if(item.config.conditionType == "Time"){
                    	//时间
                    	setTimeCriteriaVal(item,calendarObj);
                    }else if(item.config.conditionType == "person" || item.config.conditionType == "post" ||item.config.conditionType == "dept"){
                    	//地址本
                    	setAddressCriteriaVal(item,criteriaObj);
                    }else if(item.config.conditionType == "number"){
                    	//数字，金额
                    	setNumberCriteriaVal(item,criteriaObj);
                    }else if(bsnsType == "select" || bsnsType == "inputCheckbox" || bsnsType == "inputRadio" || item.config.key === "docStatus"){
                    	//（枚举）多选，单选，下拉，文档状态
                    	setEnumCriteriaVal(item,criteriaObj);
                    }else{
                    	//文本
                    	setTextCriteriaVal(item,criteriaObj);
                    }
                }
            },500);
        }, 200);

	 	</script>
 	</template:replace>
</template:include>
