/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var listAttrBase = require("sys/modeling/base/mobile/design/mode/common/listAttrBase");
	var modelingLang = require("lang!sys-modeling-base");
	var tmpData = {
			text : modelingLang['modeling.Information.area'],
			iconClass : "label_icon_notify",
			attr : {
				listView : {
					text : "列表视图",
					drawType : "listView",
					validate : {
						listView : "listView",
					},	// 校验器
					value : {}
				}
			}
	};
	
	var defaultNotify = listAttrBase.ListAttrBase.extend({
		
		widgetKey : "notify",
		
		// 由于增加了待办数据来源，需重新定义html
		renderHtml : "/sys/modeling/base/mobile/design/mode/default/notifyRender.html",
		
		formatData : function(data){
			var datas = this.extend(tmpData, data);
			return datas;
		},
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.setInitData(cfg.data);
	    },
	    
	    startup : function($super,cfg){
	    	$super(cfg);
	    },
		
		draw : function($super,cfg){
			var html = "";
			html += '<div class="head_notify_content_box">';
			html += '<div class="head_notify_content">';
			html += '<i class="head_notify_icon"></i>';
			html += '<div class="head_notify_text">'+modelingLang['modeling.processes.todo']+'</div>';
			html += '</div>';
			html += '<i class="head_notify_link_icon"></i>';
			html += '</div>';
			html += '<div class="mobileBlock-mask"></div>';
			this.element.html(html);
			$super(cfg);
		},
		
		// area为当前属性框面板
		listViewGetValue : function(key, info, area){
			var itemInfo = {};
			itemInfo.sourceType = area.find("[name*='sourceType']:checked").val();
			if(itemInfo.sourceType === "custom"){
				itemInfo.listView = area.find("[name*='listView']").val();
				itemInfo.listViewText = area.find("[name*='listViewText']").val();
				itemInfo.nodeType = area.find("[name*='nodeType']").val();

				if(this.isCount){
					itemInfo.countLv = area.find("[name*='countLv']").val();
					itemInfo.lvCollection = JSON.parse(area.find("[name*='lvCollection']").val() || "[]");				
				}
			}
			return itemInfo;
		},
		
		createFormItem : function($super,formData){
			var $formItem = $super(formData);
			var dataValue = formData.value || {};
			dataValue.sourceType = dataValue.sourceType || "custom";
			// 添加待办点击事件
			$formItem.find("input[name*='sourceType']").on("change",function(){
				// 自定义，则显示列表视图的配置
				var sourceType = this.value;
				if(sourceType === "custom"){
					$formItem.find(".content_choose").show();
					$formItem.find(".content_select").show();
				}else{
					$formItem.find(".content_choose").hide();
					$formItem.find(".content_select").hide();
				}
			});
			// 填充原始值并触发
			$formItem.find("input[name*='sourceType'][value='"+ dataValue.sourceType +"']").prop("checked",true).trigger($.Event("change"));
			return $formItem;
		},
		
		listViewValidate : function(dom){
			this.notifySelfValidate(dom);
		},
		
		listViewDataValidate : function(v){
			return this.notifySelfDataValidate(v);
		},
		
		countLvValidate : function(dom){
			this.notifySelfValidate(dom);
		},
		
		countLvDataValidate : function(v){
			return this.notifySelfDataValidate(v);
		},
		
		// 自定义的校验器
		notifySelfValidate : function(dom){
			var sourceType = this.data.attr.listView.value.sourceType;
			// 如果数据源为自定义，则需要校验
			if(sourceType === "custom"){
				this.requiredValidate(dom);
			}
		},
		
		notifySelfDataValidate : function(v){
			var sourceType = this.data.attr.listView.value.sourceType;
			// 如果数据源为系统待办，则不用校验
			if(sourceType === "system"){
				return true;
			}else{
				return this.requiredDataValidate(v);
			}
		},
		//获取多语言资源
		getModelingLang :function (){
			return modelingLang;
		},
	});
	
	module.exports = defaultNotify;
})