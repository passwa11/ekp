/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require('lui/dialog');
	var popup = require("sys/modeling/base/resources/js/popup");
	var topic = require("lui/topic");
	var modelingLang = require("lang!sys-modeling-base");
	
	var FormListDataView = base.DataView.extend({
		
		ctx : {"curTriggerPopup":null},
		

		// 重新刷新列表
		doRefresh : function(){
			this.source.get();
		},
		
		// 渲染完毕之后添加事件
		doRender : function($super,cfg){
			$super(cfg);
			/************ 设置弹出层 start ****************/
			var popupWgt = new popup.Popup({
				triggerObjects:this.element.find(".form_popup_wrap"),
				contentElement : this.getPopupContent(),
				parent : this
			});
			popupWgt.startup();
			popupWgt.draw();
			
			// 弹出层内容事件
			var self = this;
			popupWgt.contentElement.find("li").on("click",function(){
				var formId = $(self.ctx.curTriggerPopup).closest("li[data-formid]").attr("data-formid");
				var method = $(this).attr("data-form-oper-method");
				self.runPopupEvent(formId,method);
				self.hidePopup();
			});
			//列表样式下编辑按钮事件
			this.element.find("[data-formlist-boxtype='edit']").each(function (index, dom) {
				$(dom).on("click", function () {
					var formId = $(dom).attr("data-formlist-id");
					var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=frame&fdId=" + formId;
					top.open(url, "_self");
				});
			});
			//列表样式下属性按钮事件
			this.element.find("[data-formlist-boxtype='property']").each(function (index, dom) {
				$(dom).on("click", function () {
					var formId = $(dom).attr("data-formlist-id");
					var dialogUrl = "/sys/modeling/base/modelingAppModel.do?method=viewFormInfo&fdId=" + formId;
					dialog.iframe(dialogUrl, modelingLang['modeling.form.FormProperties'], null, {width: 900, height: 500, close: true});
				});
			});
			/************ 设置弹出层 end ****************/
			//列表视图时更多按钮显示规则
			this.element.find(".modeling_form_table_operation").each(function (index, dom) {
				$(dom).on("click",  function (event) {
					$('.form_table_more_button').hide();
					$(dom).find(".form_table_more_button").show();
					$(document).one('click',function(){
						$('.form_table_more_button').hide();
					});
					event.stopPropagation();
					$(".form_table_more_button").on("click", function(e){
						e.stopPropagation();
					});
				});
				var $lis = $(dom).find(".buttonOptionList").children("li");
				$lis.each(function (index, d) {
					$(d).on("click", function () {
						var method= $(d).attr("data-table-oper-method");
						var formId= $(d).attr("data-formlist-id");
						var copyToText= $(d).attr("data-formlist-copyToText");
						var formInfosLength = $(d).attr("data-formlist-formInfosLength");
						if (method === "export") {
							//导出
							var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingDatainitMain.do?method=exportForm&fdModelId=" + formId;
							if ($('#exportDownLoadIframe').length > 0) {
								$('#exportDownLoadIframe')[0].src = url;
							} else {
								var elemIF = document.createElement("iframe");
								elemIF.id = "exportDownLoadIframe";
								elemIF.src = url;
								elemIF.style.display = "none";
								document.body.appendChild(elemIF);
							}
						}else if (method === "copy") {
							if(formInfosLength >= appInfos.maxNum){
								dialog.alert(modelingLang['modelingLicense.up.to.30.forms']);
							}else{
								var msg = modelingLang['modeling.page.confirm.copy'] + copyToText + modelingLang['table.modelingAppModel']+"?";
								if(copyToText == modelingLang['modeling.no.flow']){
									msg = modelingLang['modeling.page.confirm.copy'] + copyToText + modelingLang['table.modelingAppModel']+"?"+modelingLang['modeling.model.copyFormTips'];
								}
								dialog.confirm(msg, function (value) {
									if (value === true) {
										window._loading = dialog.loading();
										$.ajax({
											url: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=copyForm&fdAppModelId=" + formId,
											type: "GET",
											dataType: "json",
											success: function (rs) {
												if (rs.status) {
													dialog.success(modelingLang['modeling.form.copy.complete']);
													LUI("formList").doRefresh()
												} else {
													dialog.failure(rs);
												}
												if (window._loading != null)
													window._loading.hide();
											},
											error: function (XMLHttpRequest, textStatus, errorThrown) {
												if (XMLHttpRequest.status === 403) {
													dialog.failure(modelingLang['modeling.form.OperateTips']);
												} else {
													dialog.failure(textStatus);
												}
												if (window._loading != null)
													window._loading.hide();
											}
										});
									}
								});
							}
						}else if (method === "sameCopy") {
							if(formInfosLength >= appInfos.maxNum){
								dialog.alert(modelingLang['modelingLicense.up.to.30.forms']);
							}else{
								dialog.confirm(modelingLang['modeling.page.confirm.sameCopy']+"?", function (value) {
									if (value === true) {
										window._loading = dialog.loading();
										$.ajax({
											url: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=copyForm&fdAppModelId=" + formId+"&isSameCopy=true",
											type: "GET",
											dataType: "json",
											success: function (rs) {
												if (rs.status) {
													dialog.success(modelingLang['modeling.form.copy.complete']);
													LUI("formList").doRefresh()
												} else {
													dialog.failure(rs);
												}
												if (window._loading != null)
													window._loading.hide();
											},
											error: function (XMLHttpRequest, textStatus, errorThrown) {
												if (XMLHttpRequest.status === 403) {
													dialog.failure(modelingLang['modeling.form.OperateTips']);
												} else {
													dialog.failure(textStatus);
												}
												if (window._loading != null)
													window._loading.hide();
											}
										});
									}
								});
							}
						}else if (method === "delete"){
							dialog.confirm(modelingLang['modeling.form.DeleteTips'], function (value) {
								if (value === true) {
									var requestType = "get";
									$.ajax({
										url: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=deleteByAjax&fdId=" + formId,
										type: requestType,
										jsonp: "jsonpcallback",
										success: function (rs) {
											if (rs.status === "00") {
												dialog.success("“" + rs.data.dialog.fdName + "” "+modelingLang['modeling.form.del.complete']+"");
												LUI("formList").doRefresh()
											} else if (rs.status === "02") {
												//弹框提示
												var url = '/sys/modeling/base/resources/jsp/dialog_relation.jsp';
												dialog.iframe(url, modelingLang['modelingAppListview.relatedDialogTitle'], function (data) {
												}, {
													width: 600,
													height: 400,
													params: {relatedDatas: rs.data.dialog, delObjType: 'form'}
												});
											} else {
												dialog.failure(rs.errmsg);
											}
										},
										error: function (XMLHttpRequest, textStatus, errorThrown) {
											if (XMLHttpRequest.status === 403) {
												dialog.failure(modelingLang['modeling.form.OperateTips']);
											} else {
												dialog.failure(textStatus);
											}
										}
									});
								}
							});
						}
						$('.app_table_more_button').hide();
					});
				});
			});
		},
		
		runPopupEvent : function(formId,method){
			var self = this;
			// 查看表单映射字段属性信息
			if(method === "viewFormInfo"){
				var dialogUrl = "/sys/modeling/base/modelingAppModel.do?method="+ method +"&fdId=" + formId;
				dialog.iframe(dialogUrl,"表单属性",null,{width:900,height:500,close:true});
			}else if(method === "deleteByAjax"){
				dialog.confirm("一旦选择了删除，所选记录的相关数据都会被删除，无法恢复！您确认要执行此删除操作吗？",function(value){
					if(value === true){
						var requestType = "get";
						$.ajax({
							url : Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method="+ method +"&fdId=" + formId,
							type : requestType,
							jsonp:"jsonpcallback",
							success : function(rs){
								if(rs.status === "00"){
									dialog.success("操作成功！");
									self.doRefresh();
								}else if(rs.status === "02"){
									//弹框提示
									self.relatedHandler(rs.data.dialog);
								}else{
									dialog.failure(rs.errmsg);
								}
							},
							error: function (XMLHttpRequest, textStatus, errorThrown) {
								//dialog.failure(rs.errmsg);status
								if(XMLHttpRequest.status === 403){
									dialog.failure("您没有该操作权限！");
								}else{
									dialog.failure(textStatus);
								}
							}
						});
					}
				});
			} else if (method === "exportForm"){
				var values = [];
				$("input[name='List_Selected']:checked").each(function() {
					values.push($(this).val());
				});
				var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingDatainitMain.do?method="+ method +"&fdModelId=" + formId;
				if ($('#exportDownLoadIframe').length > 0) {
					$('#exportDownLoadIframe')[0].src = url;
				} else {
					var elemIF = document.createElement("iframe");
					elemIF.id = "exportDownLoadIframe";
					elemIF.src = url;
					elemIF.style.display = "none";
					document.body.appendChild(elemIF);
				}
			}
		},

		/**
		 * 关联弹框
		 */
		relatedHandler : function(datas){
			var url='/sys/modeling/base/resources/jsp/dialog_relation.jsp';
			dialog.iframe(url, "删除关联模块", function(data){
			},{
				width : 600,
				height : 400,
				params : { relatedDatas : datas, delObjType: 'form'}
			});
		},
		
		hidePopup : function(){
			this.ctx.curTriggerPopup = null;
			topic.channel("modelingPopup").publish("app.popup.hide");
		},
		
		// popupWgt调用（由主控件控制是否弹出弹出层）
		isExpandPopup : function(triggerObject){
			// 如果触发的是当前弹出层，则不展示，同时把对象置空
			if(this.ctx.curTriggerPopup === triggerObject){
				this.ctx.curTriggerPopup = null;
				return false;
			}else{
				this.ctx.curTriggerPopup = triggerObject;
				return true;
			}
		},
		
		// 弹出层表单内容
		getPopupContent : function(){
			var $ul = $("<ul />");
			$ul.append("<li data-form-oper-method='viewFormInfo'>表单属性</li>");
			$ul.append("<li data-form-oper-method='deleteByAjax'>删除表单</li>");
			$ul.append("<li data-form-oper-method='exportForm'>导出表单</li>");
			return $ul;
		}
		
	});
	
	exports.FormListDataView = FormListDataView;
	
})