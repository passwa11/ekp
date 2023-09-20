<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<title><bean:message  bundle="sys-modeling-base" key="table.modelingExternalQuery" /></title>
<template:include ref="config.profile.list">
	<template:replace name="head">
		<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/collectionListView.css?s_cache=${LUI_Cache}" rel="stylesheet">
		<link href="${LUI_ContextPath}/sys/modeling/main/externalQuery/css/index.css?s_cache=${LUI_Cache}" rel="stylesheet">
	</template:replace>
	<template:replace name="content">
		<div>
			<div class="indexHead">
				<div class="indexTitle">${indexTitle}</div>
			</div>
			<div class="indexContent">
				<div class="indexQuery">
					<!-- 筛选器 -->
					<%@ include file="/sys/modeling/main/externalQuery/criteria.jsp" %>
				</div>
				<div class="lui_list_operation"></div>
				<div class="indexListView">
					<list:listview id="listview" style="position: relative;">
						<ui:source type="AjaxJson">
							{url:'/sys/modeling/main/externalQuery.do?method=queryForPc&fdAppModelId=${fdAppModelId}'}
						</ui:source>
						<list:colTable isDefault="true" layout="sys.ui.listview.columntable" rowHref="">
							<list:col-checkbox></list:col-checkbox>
							<list:col-serial></list:col-serial>
							<list:col-auto props="${fdDisplay }"></list:col-auto>
						</list:colTable>
						<ui:event topic="list.loaded">
							Dropdown.init();
							iframeAutoFit();
						</ui:event>
					</list:listview>
					<!-- 分页 -->
					<list:paging layout="${pageLayout}"/>
				</div>
			</div>
		</div>
		<script>
			var listViewLang={
				none:'${lfn:message("sys-modeling-base:listview.top.statistics.show.num.unit.null")}',
				noData:'${lfn:message("sys-modeling-base:listview.no.data")}'
			}
			var ___url = "";
			//导入成功刷新列表
			seajs.use([ 'sys/ui/js/dialog' ,'lui/topic', 'lui/data/source','lui/parser',
						'lui/listview/template','lui/listview/listview','lui/view/Template','lui/jquery'],
					function(dialog,topic,source,parser,template,listview,tmpl,$) {

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

			});
			var listOption = {
				fdAppModelId : '${param.fdAppModelId}',
				isFlow : '${param.isFlow}',
				fdDisplayCssObj : '${fdDisplayCss}',
				allField : '${allField}',
				modelDict : '${modelDict}'
			}
			Com_IncludeFile("index.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
			Com_IncludeFile("iframe.js",Com_Parameter.ContextPath+'sys/modeling/main/resources/js/view/','js',true);

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
