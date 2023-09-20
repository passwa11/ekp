/**
 *
 */
define(function(require, exports, module) {
	// 引入地址本相关脚本，render里面使用
	require("resource/js/xml.js");
	require("resource/js/data.js");
	require("resource/js/formula.js");
	require("resource/js/dialog.js");
	require("resource/js/address.js");
	require("resource/js/treeview.js");
	
	var base = require("lui/base");
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	
	var Sort = base.DataView.extend({
		
		initSourceData : function(sourceData){
			if(sourceData){
				this.source.source = sourceData;
			}
		},
		
		getSortFormData: function(){
			var sort = {};
			this.element.find(".lui_toolbar_btn_r").each(function(){
				  if( $(this).find(".lui_icon_s").hasClass("lui_icon_s_up_filter")){
					  sort.orderBy = $(this).find(".lui_widget_btn_txt").attr("orderBy");
					  sort.orderType = "asc";
				  }
				  if( $(this).find(".lui_icon_s").hasClass("lui_icon_s_on_filter")){
					  sort.orderBy = $(this).find(".lui_widget_btn_txt").attr("orderBy");
				  	  sort.orderType = "desc";
				  }
			});

			return sort;
		},
		
		doSort : function(){
			var sortData = this.getSortFormData();
			topic.channel("placeholder").publish("sort",{sortData:sortData});
		}
	});
	
	exports.Sort = Sort;
})