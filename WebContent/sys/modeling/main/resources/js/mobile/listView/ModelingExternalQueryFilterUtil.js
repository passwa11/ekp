/**
 * 
 */
define(['dojo/_base/declare', 'mui/createUtils', "mui/address/AddressMixin", "dojo/_base/lang"], 
		function(declare, createUtils, AddressMixin, lang) {
	
	var h = createUtils.createTemplate;
	
	var claz = declare('sys.modeling.main.resources.js.mobile.listView.ModelingExternalQueryFilterUtil', null, {
		
		getAllFilterHtml : function(items){
			var props = {};
			var filters = [];
			// 塞过滤字段
			for(var i = 0;i < items.length;i++){
				var item = items[i];	// {field：xxx,type:xxx}
				var filterCfg = this.getFilterHtmlByType(item.type,item);
				if(filterCfg){
					filters.push(filterCfg);					
				}
			}
			if(filters.length > 0){
				props.filters = filters;
				return h('div', {
					className: 'muiHeaderItemRight',
					dojoType: 'mui/property/FilterItem',
					dojoProps: props
				});
			}else{
				return "";
			}
		},
		
		getFilterHtmlByType : function(type, item){
			if(this.isItemEnum(item)){
				return this.enumFilterDraw(item);
			}else if(type === "Date" || type === "DateTime" || type === "Time"){
				return this.dateFilterDraw(item);
			}else if(type.indexOf("com.landray.kmss.sys.organization") > -1){
				item.field = item.field + ".fdName"
				return this.strFilterDraw(item);
			}else if(type === "String"){
				return this.strFilterDraw(item);				
			}
		},
		
		isItemEnum : function(item){
			var isEnum = false;
			if(item.enumValues){
				isEnum = true;
			}
			return isEnum;
		},
		
		strFilterDraw : function(item){
			return {
					filterType: "FilterSearch",
					type: item.type.toLowerCase(),
					name: item.field,
					subject: item.text
			};
		},
		
		dateFilterDraw : function(item){
			return {
		          filterType: "FilterDatetime",
		          type: item.type.toLowerCase(),
		          name: item.field,
		          subject: item.text
			};
		},

		
		addressTypeTrans : function(item){
			var code = ORG_TYPE_ALL + "";
			if(item.customElement && item.customElement.orgType){
				code = item.customElement.orgType;
			}
			return code;
		},
		
		enumFilterDraw : function(item){
			//#158881 【日常缺陷】【列表视图】移动端的单选、下拉框为单值，筛选条件可以多选，不合理 增加枚举值businessType传入前台进行判断是多选还是单选。
			var businessType= item.businessType;
			if(businessType === "select" || businessType === "inputRadio"){
				return {
					filterType: "FilterRadio",
					type: item.type.toLowerCase(),
					name: item.field,
					subject: item.text,
					options : item.enumValues
				};
			}else {
				return {
					filterType: "FilterCheckBox",
					type: item.type.toLowerCase(),
					name: item.field,
					subject: item.text,
					options : item.enumValues
				};
			}
		}
	});
	
	return new claz();
})