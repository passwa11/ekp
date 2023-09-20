define("mui/nav/MobileCfgNavBar", ['dojo/_base/declare', 
                "./NavBarStore",
                "mui/util"], function(declare, NavBarStore, util) {
	
		return declare('mui.nav.MobileCfgNavBar', [NavBarStore], {

			url : "/sys/mobile/sys_mobile_module_category_config/sysMobileModuleCategoryConfig.do?method=data&fdModelName=!{modelName}",

			modelName: null,
			
			// 是否做了数据源静态化
			isTiny : false,

			startup : function() {
				if (this._started)
					return;
				
				// 静态化数据源，由脚本提供
				if (typeof (navMemory) != 'undefined'
						&& this.modelName) {
					if (navMemory[this.modelName]) {
						this.isTiny = true;
						this.defer(function(){
							this.onComplete(navMemory[this.modelName]);
						},1)
					}
				} else {
					if (this.modelName) {
						this.url = util.urlResolver(this.url, {modelName: this.modelName});
					}else{
						this.url = util.urlResolver(this.url, {modelName: ''});
					}
				}
				
				this.inherited(arguments);
			},
			
			// 格式化数据
			_createItemProperties : function(item) {
				if (this.isTiny) {
					item['text'] = item[dojoConfig.locale];
				}
				return item;
			}
		
		});
});