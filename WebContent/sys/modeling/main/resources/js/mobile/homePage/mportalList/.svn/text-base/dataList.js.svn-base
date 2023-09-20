/**
 * 
 */
define(['dojo/_base/declare',"dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util", 
		"sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin", "dojo/dom-class", "sys/mportal/mobile/TabCard", "dojo/request","dojo/topic","dojo/query"],
		function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util, _IndexMixin, domClass, TabCard, request,topic,query){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.mportalList.statistics', [WidgetBase, openProxyMixin, _IndexMixin] , {
		
		url : "",
		
		listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}#path=!{tabIndex}",
		
		DATALOAD : "/sys/modeling/mobile/index/load",

		fdMobileId:"",
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe(this.DATALOAD, 'onComplete');
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "mportalList-dataList");
		},
		
		onComplete : function(data){
			domConstruct.empty(this.domNode)
			var attrs = data.dataList.attr;
			this.createContent(attrs.listView.value, attrs);
		},
		
		createContent : function(values, attrs){
			// 请求获取所有有权限的页签
			var url = util.urlResolver("/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=getMobileNavInfo&listViewId=!{listViewId}&fdMobileId=!{fdMobileId}&area=dataList",
					{listViewId: values.listView,fdMobileId:this.fdMobileId});
			var self = this;
			request.get(util.formatUrl(url),{handleAs : 'json'}).then(function(data){
				// 处理请求返回的数据
				self.doRenderContent(data, attrs);
			});
			
		},
		
		doRenderContent :function(data, attrs){
			var items = data.tabInfo;
			if(items.length === 0){
				// 没有展示项时
				var style1 = "border: 1px dashed #D4D6DB;border-radius: 2px;";
				var style2 = "height:20rem;";
				var imageStyle = "margin-top:7.7rem;";
				var textStyle = "margin-top:7.7rem;";
				this.showNoAuth(this.domNode,style1,style2,null,imageStyle,textStyle);
			}else{
				// 构造tabCardConfig
				//看板视图
				if(items[0].listViewType ==="2"){
					this.buildBoardDataInfo(data,attrs);
				}else{
					var cfg = this.buildTabCardConfig(items);
					var tabCardContainer = domConstruct.create("div",{
						className : "mportalList-card-container"
					},this.domNode);

					this.tabCard = new TabCard({
						domNode : tabCardContainer,
						title : attrs.title.value,
						configs : cfg,
						cfgData : data
					});
					if(this.tabCard){
						var selectedIndex = this.tabCard.selectedIndex;
						var listViewId = this.tabCard.datas[selectedIndex].listViewsId;
						topic.publish("sys.modeling.listviewId",listViewId);
					}
				}
			}
		},

		buildBoardDataInfo: function(data,attrs) {
			var tabInfo = data.tabInfo[0];
			if(tabInfo.group.groupType === "1"){
				var navInfoUrl = "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=getBoardGroupInfo";
			}else{
				var navInfoUrl = "/sys/modeling/main/collectionView.do?method=getGroupData";
			}

			var paramsData = {
				listViewId:tabInfo.listViewsId,
				groupField:tabInfo.group.groupField,
				fdAppModelId:tabInfo.appModelId
			}

			var self = this;
			request.post(util.formatUrl(navInfoUrl),{data:paramsData,handleAs : 'json'}).then(function(json){
				if (json && json.data){
					var result = json.data;
					var items = [];
					var len = result.length > 3 ? 3 : result.length;
					for (var i = 0; i < len; i++) {
						var item = JSON.parse(JSON.stringify(tabInfo));
						item.fieldName = result[i].field;
						item.fieldValue = result[i].value;
						item.name = result[i].name;
						items.push(item);
					}
					var cfg= self.buildTabCardConfig(items);
					var tabCardContainer = domConstruct.create("div",{
						className : "mportalList-card-container"
					},self.domNode);

					self.tabCard = new TabCard({
						domNode : tabCardContainer,
						title : attrs.title.value,
						configs : cfg,
						cfgData : data
					});
					if(self.tabCard){
						var selectedIndex = self.tabCard.selectedIndex;
						var listViewId = self.tabCard.datas[selectedIndex].listViewsId;
						topic.publish("sys.modeling.listviewId",listViewId);
					}
				}
			});
		},
		
		buildTabCardConfig : function(items){
			for(var i = 0;i < items.length;i++){
				var item = items[i];
				item.portletName = item.name;
				var isNewList = false;
				//判断该条列表是旧列表还是新列表
				if (item.url.indexOf("modelingAppMobileCollectionView") > -1){
					isNewList = true;
				}
				item.jsUrl = "/sys/modeling/main/resources/js/mobile/homePage/common/mainPortlet_list.js?" +
						"rowsize=!{rowsize}&isNewList=!{isNewList}&listViewsId=!{listViewsId}&viewId=!{viewId}&appModelId=!{appModelId}" +
					"&fieldName=!{fieldName}&fieldValue=!{fieldValue}";
				item.vars = {
					"rowsize" : "6",
					"listViewsId" : item.listViewsId,
					"viewId" : item.viewId,
					"appModelId" : item.appModelId,
					"isNewList" : isNewList,
					"fieldName" : item.fieldName,
					"fieldValue" : item.fieldValue
				};
				item.operations = {};
			}
			return items;
		},
		
		// 根据后台的权限进行数组的过滤
		filterByAuth : function(items){
			var rs = [];
			for(var i = 0;i < items.length;i++){
				if(items[i].auth === "true"){
					rs.push(items[i]);
				}
			}
			return rs;
		}
		
	});
});