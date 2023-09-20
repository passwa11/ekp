/**
 * 
 */
define(function(require, exports, module) {
	var Spa = require("lui/spa/Spa");
	var base = require("lui/base");
	var router = require("lui/framework/router/router");
	
	var modelingUtil = base.Base.extend({
		
		// 返回当前选择的动态模块item
		getDynamicItem : function(){
			// 根据modelKey获取modelName
			// __modelType index页面定义的全局变量
			var item;
			var currentKey = Spa.spa.getValue('modelKey');
			if(currentKey){
				if(__modelType.hasOwnProperty(currentKey)){
					item = __modelType[currentKey];
				}
			}
			/*if(!item){
				item = __modelType[""];
			}*/
			return item;
		},
		
		equalPath : function(data){
			if(data && data.params){
				var key = data.params["key"] || "listviewId";
				var uuid = Spa.spa.getValue(key) || '';
				if(uuid == data.params[key]){
					return true;					
				}
			}
			return false;
		}
	});
	
	module.exports = new modelingUtil();
});