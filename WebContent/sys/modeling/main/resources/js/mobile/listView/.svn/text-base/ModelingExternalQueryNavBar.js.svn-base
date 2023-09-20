/**
 *
 */
define("sys/modeling/main/resources/js/mobile/listView/ModelingExternalQueryNavBar",
		['dojo/_base/declare',"mui/nav/NavBarStore","dojo/request", "dojo/dom-style","dojo/query","mui/util"
			,  "sys/modeling/main/resources/js/mobile/listView/ModelingExternalQueryFilterUtil"],
		function(declare, NavBarStore, request, domStyle, query, util, ModelingExternalQueryFilterUtil) {

		return declare('sys.modeling.main.resources.js.mobile.listView.ModelingExternalQueryNavBar', [NavBarStore], {

			url : null,

			navInfoUrl :"/sys/modeling/main/externalQuery.do?method=getMobileNavInfo&fdModelId=!{fdModelId}",
			startup : function() {
				if (this._started)
					return;
				var url = util.urlResolver(this.navInfoUrl, {fdModelId:this.fdModelId});
				var self = this;
				self.isTiny = true;
				request.get(util.formatUrl(url),{handleAs : 'json'}).then(function(json){
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

				// 搜索
				if(headerInfo.hasOwnProperty("filter")){
					var filterInfos = headerInfo["filter"];
					items.push(ModelingExternalQueryFilterUtil.getAllFilterHtml(filterInfos));
				}
				return items.join("");
			},

			// 格式化数据
			_createItemProperties : function(item) {
				// 不知为什么返回的数据，总是把text给清了，故返回的时候用name代替，这里再转回来
				item['text'] = item['name'];
				return item;
			}
		});
});