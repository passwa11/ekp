/**
 * 单选的列表视图(属性面板)
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require("lui/dialog");
	var render = require("lui/view/render");
	var mobileBaseWidget = require("sys/modeling/base/mobile/design/mode/common/mobileBaseWidget");
	var modelingLang = require("lang!sys-modeling-base");
	
	var ListAttrBase = mobileBaseWidget.MobileBaseWidget.extend({
		
		renderHtml : "/sys/modeling/base/mobile/design/mode/common/listAttrBaseRender.html#",
		
		formatData : function(data){
			return $.extend(true, {},tmpData,data);
		},
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.isCount = true;	// 是否需要设置总计页签
			// 属性面板的模板HTML，使用模板文件方便调整
			this.panelTempRender = new render.Template({
				parent : this,
				src : this.renderHtml
			});
	    },
	    
	    startup : function($super,cfg){
	    	$super(cfg);
	    	this.panelTempRender.startup();
	    },
		
		// area为当前属性框面板
		listViewGetValue : function(key, info, area){
			var itemInfo = {};
			itemInfo.listView = area.find("[name*='listView']").val();
			itemInfo.listViewText = area.find("[name*='listViewText']").val();
			itemInfo.nodeType = area.find("[name*='nodeType']").val();
			itemInfo.listViewAppId = area.find("[name*='listViewAppId']").val();
			
			if(this.isCount){
				itemInfo.countLv = area.find("[name*='countLv']").val() || "";
				itemInfo.lvCollection = JSON.parse(area.find("[name*='lvCollection']").val() || "[]");				
			}
			
			return itemInfo;
		},
		
		listViewDraw : function(key, info, container, fieldBody, panel){
			var $div = panel.drawPanelContentWrap(fieldBody);
			info = info || {};
			// 首次初始化，需要等panelTempRender请求完之后再执行
			var self = this;
			this.panelTempRender.get(null,function(){
				var $item = self.createFormItem(info);
				$div.append($item);
			});
		},
		
		createFormItem : function(formData){
			var uuId = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
			formData = formData || {};
			formData.uuId = uuId;
			
			var $formItem = $(this.panelTempRender.html(formData));
			var self = this;
			/************* 添加事件 start *****************/
			// 添加列表视图点击事件
			$formItem.find(".listVieElement").on("click",function(){
				var todoType = $("[name*='sourceType']").val();
				if(todoType){
					//待办需要屏蔽无流程的列表
					var url = "/sys/modeling/base/resources/js/dialog/leftNav/leftNavDialog.jsp?isTodo=true";
				}else{
					// ___appId：在edit.jsp定义的全局变量
					var url = "/sys/modeling/base/resources/js/dialog/leftNav/leftNavDialog.jsp?isTodo=false";
				}
				var appId = $formItem.find("[name*='listViewAppId']").val();
				dialog.iframe(url,modelingLang['table.modelingAppMobileListView'],function(rtn){
					if(rtn && rtn.data){
						self.setValuesAfterListViewDialog(uuId, rtn, $formItem);
					}
				},{
					width : 800,
					height : 500,
					params : {
						"cateBean" : "modelingAppModelService",
						"fdAppId" : appId || ___appId,
						"dataBean" : "modelingAppMobileListViewService&modelId=!{value}",
						"isShowCalendar":self.widgetKey === "list"
					}
				});
			});
			//图表区
			$formItem.find(".listViewChart").on("click",function(){
				var url = "/sys/modeling/base/mobile/design/mode/mportalList/leftNavChartDialog.jsp";
				var appId = $formItem.find("[name*='listViewAppId']").val();
				dialog.iframe(url,modelingLang["modeling.business.chart"],function(rtn){
					if(rtn && rtn.data){
						self.setValuesAfterListViewDialog(uuId, rtn, $formItem);
					}
				},{
					width : 800,
					height : 500,
					params : {
						"appId" : appId || ___appId,
					}
				});
			});
			
			/************* 添加事件 end *****************/
			if(this.isCount){
				var dataValue = formData.value || {};
				self.updateCountBlock(dataValue.lvCollection, dataValue.countLv, uuId, $formItem);				
			}
			return $formItem;
		},
		
		setValuesAfterListViewDialog : function(uuId, rtn, $formItem){
			if(this.isCount){
				var views = this.transStrToJson(rtn.data.viewsjson);
				views = this.formatViews(views);
				this.updateCountBlock(views, "", uuId, $formItem);							
			}
			$formItem.find(".listViewText").html(rtn.data.text);
			$formItem.find(".listViewText").attr("title",rtn.data.text);
			$formItem.find("[name='"+ uuId +"_listViewText']").val(rtn.data.text);
			$formItem.find("[name='"+ uuId +"_nodeType']").val(rtn.data.nodetype || rtn.data.nodeType);
			$formItem.find("[name='"+ uuId +"_listViewAppId']").val(rtn.appId || ___appId);
			// 此处添加主动触发，是为了更新data
			$formItem.find("[name='"+ uuId +"_listView']").val(rtn.data.value).trigger($.Event("change"));
		},
		
		// 去除views多余的字段属性
		formatViews : function(views){
			var rs = [];
			for(var i = 0;i < views.length;i++){
				var view = views[i];
				rs.push({text : view.text, value : view.value});
			}
			return rs;
		},
		
		// 设置总计页签对应哪个页签
		updateCountBlock : function(views, value, uuId, area){
			views = views || [];
			if(views.length > 0){
				area.find(".lvCountWrap").html(this.createCustomSelect(views,value, uuId));
				area.find("[name*='lvCollection']").val(JSON.stringify(views));
			}else{
				area.find(".lvCountWrap").html(this.createCustomSelect(views,value, uuId));
			}
		},
		
		// 待优化
		createCustomSelect : function(views,value, uuId){
			var text = "===请选择===";
			var lvCountHtml = "";
			lvCountHtml += "<div class='model-mask-panel-table-select' style='margin-left:0px'>";
			lvCountHtml += "<p class='model-mask-panel-table-select-val'></p>";
			lvCountHtml += "<div class='model-mask-panel-table-option'>";
			for(var i = 0;i < views.length;i++){
				var lvTab = views[i];
				lvCountHtml += "<div option-value='"+ lvTab.value +"'";
				if(lvTab.value === value){
					text = lvTab.text;
				}
				lvCountHtml += ">"+ lvTab.text +"</div>";
			}
			lvCountHtml += "</div>";
			
			lvCountHtml += "</div>";
			var $select = $(lvCountHtml);
			$select.find(".model-mask-panel-table-select-val").html(text);
			// 添加事件
			$select.on("click", function (event) {
                event.stopPropagation();
                $(this).toggleClass("active")
            });
			$select.find(".model-mask-panel-table-option div").on("click", function () {
                var $tableSelect = $(this).closest(".lvCountWrap");
                var $p = $tableSelect.find(".model-mask-panel-table-select-val");
                $p.html($(this).html());
                
                var selectVal = $(this).attr("option-value");
                $tableSelect.closest(".content_item_form_element").find("[name*='countLv']").val(selectVal).trigger($.Event("change"));
            });
			return $select;
		},
		//获取多语言资源
		getModelingLang :function (){
			return modelingLang;
		},


	});
	
	exports.ListAttrBase = ListAttrBase;
})