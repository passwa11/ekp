/**
 * 单选的列表视图(属性面板)
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require("lui/dialog");
	var render = require("lui/view/render");
	var modelingLang = require("lang!sys-modeling-base");

	var ListAttrBase = base.DataView.extend({
		
		renderHtml : "/sys/modeling/base/mobile/design/custom/portlets/listAttrBaseRender.html#",
		
		formatData : function(data){
			return $.extend(true, {},tmpData,data);
		},
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.isCount = false;	// 是否需要设置总计页签
			this.widgetKey = cfg.widgetKey || "";
			this.data = cfg.data || {};
			this.area = cfg.area || null;
			this.data.uuId = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
			this.parent = cfg.parent || null;
	    },
	    
	    startup : function($super,cfg){
			this.setRender(new render.Template({
				src : this.renderHtml,
				parent : this
			}));
			this.render.startup();
	    	$super(cfg);
	    },

		doRender: function($super, cfg){
			var self = this;
			$super(cfg);
			this.area.append(this.element);
			this.addEvent();
		},

		draw : function($super, cfg){
			$super(cfg);
			this.render.get(this.data);
		},
		
		addEvent : function(){
			var formData = this.data;
			var uuId = formData.uuId;
			var $formItem = this.element;
			var self = this;
			/************* 添加事件 start *****************/
			if(this.widgetKey === "chart"){
				// //图表区
				$formItem.find(".listVieElement").on("click",function(){
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
			}else{
				// 添加列表视图点击事件
				$formItem.find(".listVieElement").on("click",function(){
					if(self.widgetKey === "todo"){
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
			}
			/************* 添加事件 end *****************/
			if(this.isCount){
				var dataValue = formData || {};
				self.updateCountBlock(dataValue.lvCollection, dataValue.countLv, uuId, $formItem);
			}
			this.parent.addValidateElements($formItem.find("[name='"+ uuId +"_listView']")[0],"required");
			//标签内容绑定值改变事件
			$formItem.find("[name='"+ uuId +"_listView']").off("change").on("change",function() {
				self.parent.validateElement(this);
			});
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

		getKeyData : function(){
			var itemInfo = {};
			itemInfo.listView = this.element.find("[name$='listView']").val();
			itemInfo.listViewText = this.element.find("[name$='listViewText']").val();
			itemInfo.nodeType = this.element.find("[name$='nodeType']").val();
			itemInfo.listViewAppId = this.element.find("[name$='listViewAppId']").val();

			if(this.isCount){
				itemInfo.countLv = this.element.find("[name$='countLv']").val() || "";
				itemInfo.lvCollection = JSON.parse(this.element.find("[name$='lvCollection']").val() || "[]");
			}

			return itemInfo;
		},
		destroy : function($super) {
			this.parent.removeValidateElements(this.element.find("[name='"+ this.data.uuId +"_listView']")[0],"required");
			$super();
		}
	});
	
	exports.ListAttrBase = ListAttrBase;
})