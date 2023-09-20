/**
 * 移动列表视图
 */
define("sys/modeling/main/resources/js/mobile/listView/ModelingBoardNavBar", [
    "dojo/_base/declare",
	"mui/nav/NavBarStore",
	'dojo/topic',
	'dojox/mobile/viewRegistry',
	'dojo/dom',"dojo/query","dojo/dom-class",'dojo/dom-style','dojo/dom-construct',"dojo/request","mui/util",
	"sys/modeling/main/resources/js/mobile/listView/BoardNavItem",
	"mui/hash",
	'dojo/_base/array',
	], function(declare,NavBarStore, topic, viewRegistry,dom,query,domClass,domStyle,domConstruct,request,util,BoardNavItem,hash,array) {
	
	return declare("sys.modeling.main.resources.js.mobile.listView.ModelingBoardNavBar", [NavBarStore], {
		itemRenderer : BoardNavItem,

		url : null,

		navInfoUrl : "/sys/modeling/main/collectionView.do?method=getGroupData",
		listViewsId : "",
		fdModelId:"",
		groupField:"",
		appModelId:"",
		group:{},

		startup: function () {
			if (this._started)
				return;
			var self = this;
			if(this.group.groupType=='1'){
				this.navInfoUrl ="/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=getBoardGroupInfo";
			}
			var paramsData = {
				listViewId:this.listViewsId,
				groupField:this.group.groupField,
				fdAppModelId:this.appModelId
			}

			self.isTiny = true;
			request.post(util.formatUrl(this.navInfoUrl),{data:paramsData,handleAs : 'json'}).then(function(json){
				// 处理请求返回的数据
				self.onComplete(self.formatInfo(json));
			});
			this.inherited(arguments);
		},

		// 格式化数据
		formatInfo : function(data){
			var tabInfos = data.data;
			return tabInfos;
		},

		// 格式化数据
		_createItemProperties : function(item) {
			// 不知为什么返回的数据，总是把text给清了，故返回的时候用name代替，这里再转回来
			item['text'] = item['name'];
			return item;
		},

		onComplete : function(items) {
			// // 无数据启用默认url
			if (items.length == 0 && !this.defaulted && this.defaultUrl) {
				this.set('defaulted', true);
				this.url = this.defaultUrl;
				this.store.target = this.url;
				this.setQuery();
				return;
			}
			this.generateList(items);
			topic.publish('/modeling/board/nav/onComplete', this, items);
			var self= this;

			if(hash.matchPath(this.key)){
				var query = hash.getQuery()
				if(!query || !query.fieldName || !query.fieldValue){
					if(this.getChildren().length > 1){
						this.selectedItem = this.getChildren()[1];
					} else if(this.getChildren().length > 0){
						this.selectedItem = this.getChildren()[0];
					}
				}else {
					array.forEach(this.getChildren(), function(element){
						if (query.fieldName === element.field && query.fieldValue === element.value) {
							self.selectedItem = element;
							return;
						}
					});
				}
			}
			if(!this.selectedItem){
				if(this.getChildren().length > 1){
					this.selectedItem = this.getChildren()[1];
				} else if(this.getChildren().length > 0){
					this.selectedItem = this.getChildren()[0];
				}
			}
			if (this.selectedItem) {
				this.selectedItem.setSelected();
				this.selectedItem._onClick();
				if (this.selectedItem.moveTo) {
					this.selectedItem.transitionTo(this.selectedItem.moveTo);
				}
			}
		},

	});
});