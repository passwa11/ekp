/**
 * 移动列表视图
 */
define([
    "dojo/_base/declare",
	"mui/list/HashJsonStoreList",
	'dojo/topic',
	'dojox/mobile/viewRegistry',
	"mui/util",
	"dojo/dom-style",
	"dojo/query",
	"dijit/registry",
	"mui/i18n/i18n!sys-modeling-main"
	], function(declare, HashJsonStoreList, topic, viewRegistry,util,domStyle,query,registry,modelingLang) {

	return declare("sys.modeling.main.resources.js.mobile.listView.ExternalQueryJsonStoreList", [HashJsonStoreList], {

		itemRenderer: null,

		isFirst: true,
		// 请求完数据之后调用，添加标题字段和摘要字段信息
		formatDatas : function() {
			if(this.isFirst){
				//默认展开筛选
				var fitterIcon = registry.byId("mui_property_FilterItem_0");
				fitterIcon.openDialog();
				this.isFirst = false;
			}
			var datas = this.inherited(arguments);
			this.customBuildDatas(datas);
			return datas;
		},

		customBuildDatas : function(datas){
			var scroll = viewRegistry.getEnclosingScrollable(this.domNode);
			if (!scroll) {
				scroll = viewRegistry.getEnclosingView(this.domNode);
			}
			if(!scroll){
				return;
			}

			var showField = [];
			var mobileTitleText,mobileTitleField;
			if(scroll.params && scroll.params.rel && scroll.params.rel.headerInfo){
				if( scroll.params.rel.headerInfo.showField){
					showField = scroll.params.rel.headerInfo.showField;
				}
				if( scroll.params.rel.headerInfo.mobileTitleText){
					mobileTitleText = scroll.params.rel.headerInfo.mobileTitleText;
				}
				if( scroll.params.rel.headerInfo.mobileTitleField){
					mobileTitleField = scroll.params.rel.headerInfo.mobileTitleField;
				}
			}

			for(var i = 0;i < datas.length;i++){
				var data = datas[i];
				this.formatTitleInfo(mobileTitleField,mobileTitleText,data)
				for (var j = 0; j < showField.length; j++) {
					this.formatFieldInfo(showField[j],data)
				}
			}
			this._loadOver = true;
		},

		formatFieldInfo:function (showFieldInfo,data){
			for (var key in data){
				var field = key.substring(key.lastIndexOf(".")+1);
				if(field == showFieldInfo.field){
					var param = this.deepClone(showFieldInfo);
					param.value = data[key];
					if(!data.showFieldData){
						data.showFieldData = [];
					}
					data.showFieldData.push(param);
					return;
				}
			}
		},

		//使用递归实现深拷贝
		deepClone:function (obj) {
			//判断拷贝的obj是对象还是数组
			var objClone = {};
			for (var key in obj) {
				if (obj.hasOwnProperty(key)) {
					if (obj[key] && typeof obj[key] === "object") { //obj里面属性值不为空并且还是对象，进行深度拷贝
						objClone[key] = this.deepClone(obj[key]); //递归进行深度的拷贝
					} else {
						objClone[key] = obj[key]; //直接拷贝
					}
				}
			}
			return objClone;
		},

		formatTitleInfo:function (mobileTitleField,mobileTitleText,data){
			for (var key in data){
				var field = key.substring(key.lastIndexOf(".")+1);
				if(field == mobileTitleField){
					data.mobileTitleText = mobileTitleText
					data.mobileTitleValue = data[key];
					return;
				}
			}
		},

		reload: function(handle) {
			this.pageno = 1;
			this._loadOver = false;
			this.url+="&sortChange=true";
			return this.doLoad(handle, false);
		},
	});
});


