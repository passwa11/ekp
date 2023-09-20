/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require("lui/dialog");
	var listAttrBase = require("sys/modeling/base/mobile/design/mode/common/listAttrBase");
	var listAttrItemBase = require("sys/modeling/base/mobile/design/mode/common/ListAttrItemBase");
	var modelingLang = require("lang!sys-modeling-base");
	var tmpData = {
			text : modelingLang['modeling.mobile.list'],
			iconClass : "label_icon_list",
			attr : {
				listView : {
					text : "列表视图",
					drawType : "multiListView",
					selfTitle : "listViewTitleDraw",
					validate : {
						listView : "required",
						tab : "required",
					},	// 校验器
					value : []
				}
			}
	};
	
	var ListBlock = listAttrItemBase.ListAttrItemBase.extend({
		
		widgetKey : "list",
		
		formatData : function(data){
			var datas = this.extend(tmpData, data);
			// 如果没有设置值，则默认生成1个未定义
			if(datas.attr.listView.value.length === 0){
				datas.attr.listView.value.push({
					tab : "",
				});

			}
			return datas;
		},
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.isCount = false;	// 是否需要设置总计页签
			this.setInitData(cfg.data);
	    },
	    
	    startup : function($super,cfg){
	    	$super(cfg);
	    },
		
		draw : function($super,cfg){
			var html = "<div class='mobileList_box list_sample_bg'></div>";
			this.element.html(html);
			this.element.addClass("mobileBlock");
			this.element.css("height","100%");
			$super(cfg);
		},
		
		validate : function(){
			var pass = true;
			this.updateData();
			// 列表视图必填
			if($.isEmptyObject(this.data.attr.listView.value)){
				pass = false;
				dialog.alert("");
			}
			return pass;
		}
	});
	
	module.exports = ListBlock;
})