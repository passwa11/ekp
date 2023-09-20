/**
 * 
 */
define(['dojo/_base/declare', 'mui/createUtils', "mui/address/AddressMixin", "dojo/_base/lang"], 
		function(declare, createUtils, AddressMixin, lang) {
	
	var h = createUtils.createTemplate;
	
	var claz = declare('sys.modeling.main.xform.controls.filling.mobile.ModelingFilterUtil', null, {
		
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
					dojoMixins: 'sys/modeling/main/xform/controls/filling/mobile/FilterHashMixin',
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
				return this.addressFilterDraw(item);
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
					name: item.name,
					subject: item.label
			};
		},
		
		dateFilterDraw : function(item){
			return {
		          filterType: "FilterDatetime",
		          type: item.type.toLowerCase(),
				  name: item.name,
				  subject: item.label
			};
		},
		
		addressFilterDraw : function(item){
			var addressType = this.addressTypeTrans(item) || "";
			addressType = lang.isString(addressType) ? addressType : (addressType + "");
			// 是否包含子部门开关
			var isIncludeSub = addressType.indexOf("ORG_TYPE_DEPT") > -1 ? true : false;
			return {
		          filterType: "sys/modeling/main/resources/js/mobile/listView/FilterAddress",
		          isTypePathFull : true,
		          type: addressType,
		          name: item.name,
		          subject: item.label,
		          splitStr:';' ,
		          showStatus:'edit',
		          isIncludeSub : isIncludeSub
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
			return {
		          filterType: "FilterCheckBox",
		          type: item.type.toLowerCase(),
		          name: item.name,
		          subject: item.label,
		          options : item.enumValues
			};
		}
	});
	
	return new claz();
})