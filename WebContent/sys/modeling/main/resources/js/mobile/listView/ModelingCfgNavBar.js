/**
 *
 */
define("sys/modeling/main/resources/js/mobile/listView/ModelingCfgNavBar",
		['dojo/_base/declare',"mui/nav/NavBarStore","dojo/request", "dojo/dom-style","dojo/query","mui/util"
			, "sys/modeling/main/resources/js/mobile/listView/ModelingSortUtil", "sys/modeling/main/resources/js/mobile/listView/ModelingFilterUtil"
		,"sys/modeling/main/resources/js/mobile/listView/ModelingBoardGroupUtil"],
		function(declare, NavBarStore, request, domStyle, query, util, ModelingSortUtil, ModelingFilterUtil,ModelingBoardGroupUtil) {

		return declare('sys.modeling.main.resources.js.mobile.listView.ModelingCfgNavBar', [NavBarStore], {

			url : null,

			navInfoUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=getMobileNavInfo" +
				"&listViewId=!{listViewId}&tabId=!{tabId}&fdModelId=!{fdModelId}&viewTabId=!{viewTabId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&area=!{area}&nodeType=!{nodeType}&order=!{order}",

			listViewId : "",

			tabId : "",

			fdModelId:"",

			viewTabId:"",
			isNewListview:"",
			arrIndex:"",
			fdMobileId:"",
			area:"",
			nodeType:"",
			order:"",
			startup : function() {
				if (this._started)
					return;

				// 请求获取nav的信息
				if(this.isNewListview){
					this.navInfoUrl = "/sys/modeling/main/mobile/modelingAppMobileCollectionView.do?method=getMobileNavInfo" +
						"&listViewId=!{listViewId}&tabId=!{tabId}&fdModelId=!{fdModelId}&viewTabId=!{viewTabId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&area=!{area}&nodeType=!{nodeType}&order=!{order}"
				}
				var url = util.urlResolver(this.navInfoUrl, {listViewId: this.listViewId,tabId: this.tabId,fdModelId:this.fdModelId,viewTabId:this.viewTabId,arrIndex: this.arrIndex,fdMobileId:this.fdMobileId,area:this.area,nodeType:this.nodeType,order:this.order});
				var self = this;
				self.isTiny = true;
				request.get(util.formatUrl(url),{handleAs : 'json'}).then(function(json){
					// 处理请求返回的数据
					self.onComplete(self.formatInfo(json));
				});
				this.inherited(arguments);
				if (this.tabId && this.tabId.split(";").length === 1) {
					var headerDom = query(this.domNode).closest(".muiHeader")[0]
					domStyle.set(headerDom, "display", "none");
				}
				this.subscribe('/modeling/statisticList/render', 'resizeHeight');
			},
				resizeHeight : function(scroll) {
				this.inherited(arguments);
				scroll.resize();
				},
			// 格式化数据
			formatInfo : function(data){
				var tabInfos = data.tabInfo;
				for(var i = 0;i < tabInfos.length;i++){
					var tabInfo = tabInfos[i];
					tabInfo.headerTempStr = this.headerHtmlDraw(tabInfo.headerInfo,tabInfo.group);
					if(tabInfo.group){
						tabInfo.url += "&fieldName="+ tabInfo.group.groupField + "&fieldValue="+ tabInfo.group.groupValue;
					}
				}
				return tabInfos;
			},
			headerHtmlDraw : function(headerInfo,group){
				var items = [];

				//看板项目分类
				if(group && group.hasOwnProperty("groupInfo")){
					var groupInfos = group.groupInfo;
					items.push(ModelingBoardGroupUtil.getBoardGroupHtml(group));
				}

				// 排序
				if(headerInfo.hasOwnProperty("order")){
					var orderInfos = headerInfo["order"];
					for(var i = 0;i < orderInfos.length;i++){
						var orderInfo = orderInfos[i];
						// text待修改
						items.push(ModelingSortUtil.getSortHtml({name:orderInfo.text,value:orderInfo.field,sort:orderInfo.orderType}));
					}
				}

				// 搜索
				if(headerInfo.hasOwnProperty("filter")){
					var filterInfos = headerInfo["filter"];
					items.push(ModelingFilterUtil.getAllFilterHtml(filterInfos));
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