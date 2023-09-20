/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");

	var BaseView = base.Container.extend({
		
		isInit : false,
		CONST:{
			DETAIL_RULE_MAIN :'1',
			DETAIL_RULE_DETAIL :'2',
			DETAIL_RULE_BOTH :'3'
		},
		// 初始化，生成视图结构
		init : function(){
			this.element = this.build();
		},
		
		show : function(){
			if(!this.isInit){
				this.init();
				this.isInit = true;
			}
			this.element.show();
		},
		
		hide : function(){
			this.element.hide();
		},
		
		getKeyData : function(){
			return {};
		},
		
		// 根据数据初始化
		initByStoreData : function(storeData){
			
		}
		
	});
	
	exports.BaseView = BaseView;
});