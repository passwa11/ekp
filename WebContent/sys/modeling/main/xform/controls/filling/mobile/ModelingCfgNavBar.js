/**
 *
 */
define("sys/modeling/main/xform/controls/filling/mobile/ModelingCfgNavBar",
		['dojo/_base/declare',"mui/nav/NavBarStore","dojo/request", "dojo/dom-style","dojo/query","mui/util"
			, "sys/modeling/main/xform/controls/filling/mobile/ModelingSortUtil", "sys/modeling/main/xform/controls/filling/mobile/ModelingFilterUtil"],
		function(declare, NavBarStore, request, domStyle, query, util,ModelingSortUtil,ModelingFilterUtil) {

		return declare('sys.modeling.main.xform.controls.filling.mobile.ModelingCfgNavBar', [NavBarStore], {

			url : null,

			navInfoUrl : "/sys/modeling/main/modelingAppXFormMain.do?method=getMobileNavInfo" +
				"&fieldName=!{fieldName}&fdAppModelId=!{fdAppModelId}&widgetId=!{widgetId}",

			fdAppModelId : "",
			modelName : "",
			fieldName :"",
			widgetId : "",
			ins:null,

			startup : function() {
				if (this._started)
					return;
				var url = util.urlResolver(this.navInfoUrl,
					{
						fieldName: this.fieldName,
						fdAppModelId: this.fdAppModelId,
						widgetId: this.widgetId
					});
				var self = this;
				self.isTiny = true;
				request.post(util.formatUrl(url),{data:{ins:this.ins},handleAs : 'json'}).then(function(json){
					// 处理请求返回的数据
					self.onComplete(self.formatInfo(json));
				});
				this.inherited(arguments);
			},

			// 格式化数据
			formatInfo : function(data){
				var tabInfos = data.tabInfo;
				for(var i = 0;i < tabInfos.length;i++){
					var tabInfo = tabInfos[i];
					tabInfo.headerTempStr = this.headerHtmlDraw(tabInfo.headerInfo);
				}
				return tabInfos;
			},
			headerHtmlDraw : function(headerInfo){
				var items = [];

				// 排序
				if(headerInfo.hasOwnProperty("order")){
					var orderInfos = headerInfo["order"];
					for(var i = 0;i < orderInfos.length;i++){
						var orderInfo = orderInfos[i];
						// text待修改
						items.push(ModelingSortUtil.getSortHtml({name:orderInfo.name.text,value:orderInfo.name.value,sort:orderInfo.expression.value}));
					}
				}

				// 搜索
				// if(headerInfo.hasOwnProperty("filter")){
				// 	var filterInfos = headerInfo["filter"];
				// 	items.push(ModelingFilterUtil.getAllFilterHtml(filterInfos));
				// }
				return items.join("");
			},

			// 格式化数据
			_createItemProperties : function(item) {
				item['text'] = item['name'];
				return item;
			},
		});
});