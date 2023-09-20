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
	require("resource/js/calendar.js");
	require("resource/js/jquery-ui/jquery.ui");
	var base = require("lui/base");
	var topic = require('lui/topic');
	var $ = require('lui/jquery');

	var Search = base.DataView.extend({
		
		initSourceData : function(sourceData){
			if(sourceData){
				this.source.source = sourceData;
			}
		},
		
		getSearchFormData: function(){
			var search = {};
			//处理单选框
			this.element.find("input[type='radio']").each(function(){
				if($(this).is(":checked")){
					$(this).attr("data-search","true");
				}else {
					$(this).removeAttr("data-search");
				}
			});
			this.element.find("input[type='checkbox']").each(function(){
				if($(this).is(":checked")){
					$(this).attr("data-search-checkBox","true");
				}else {
					$(this).removeAttr("data-search-checkBox");
				}
			});
			//处理多选框checkBox参数拼接
			//1、获取有几组复选框被选中了
			var checkboxsz=[];
			this.element.find("input[data-search-checkBox='true']").each(function(){
				//去掉字段名的。id 方便后台获取数据
				var key = $(this).attr("name");
				if(!checkboxsz.includes(key)){
					checkboxsz.push(key);
				}
			});
			//2、给search参数赋值
			for(var i=0;i<checkboxsz.length;i++){
				var key = checkboxsz[i];
				var obj=document.getElementsByName(""+checkboxsz[i]+"");
				var checkBoxValue='';
				for(var k=0; k<obj.length; k++){
					if(obj[k].checked) checkBoxValue+=obj[k].value+';'; //如果选中,就设置值到checkBoxValue
				}
				checkBoxValue = checkBoxValue.substring(0, checkBoxValue.length - 1);
				search[key] =checkBoxValue;
			}
			//处理下拉框select
			this.element.find("select[data-search='true']").each(function(){
				var key = $(this).attr("name");
				search[key] = $(this).val();
			});
			this.element.find("input[data-search='true']").each(function(){
				//去掉字段名的。id 方便后台获取数据
				var key = $(this).attr("name");
				var d = key.length - ".id".length;
				if ((d >= 0 && key.lastIndexOf(".id") == d)) {
					key = key.substr(0, key.length - 3);
				}
				search[key] = $(this).val();
			});
			return search;
		},

		cleanFormData: function(){
			//处理单选框
			this.element.find("input[type='radio']").each(function(){
				if($(this).is(":checked")){
					$(this).prop("checked","");
				}
				$(this).removeAttr("data-search");
			});
			//处理多选框
			this.element.find("input[type='checkbox']").each(function(){
				if($(this).is(":checked")){
					$(this).prop("checked","");
				}
				$(this).removeAttr("data-search-checkBox");
			});
			//处理下拉框
			this.element.find("select").each(function () {
				var optionVal = $(this).find("option:selected").val();
				if(optionVal){
					$(this).find("option:first").prop("selected", 'selected');
				}
				$(this).removeAttr("data-search");
			})
		},
		
		doSearch : function(){
			var searchData = this.getSearchFormData();
			topic.channel("placeholder").publish("search",{searchData:searchData});
		}
		,doClean : function(){
			this.cleanFormData();
			topic.channel("placeholder").publish("search",{searchData:null});
		}
	});
	
	exports.Search = Search; 
})