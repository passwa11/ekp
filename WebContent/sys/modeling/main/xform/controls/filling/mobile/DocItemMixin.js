define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"dojo/on",
   	"mui/dialog/Tip", 
	"mui/openProxyMixin",
	"dojo/dom-geometry",
	"dojo/_base/window",
	"mui/category/CategoryItemMixin"
	, "dojo/request", "dojo/topic", "dojo/_base/array"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,on,Tip, openProxyMixin,domGeometry,win,CategoryItemMixin,request,topic,array) {
	var item = declare("sys.modeling.main.xform.controls.filling.mobile.DocItemMixin", [CategoryItemMixin, openProxyMixin], {
		tag:"li",
		baseClass:"muiDocItem",
		//链接
		href:"",
		//标签
		tagNames:"",

		relationId:null,
		widgetId:null,
		mainModelId:null,
		fsKey:null,
		isMul: false,

		buildRendering:function(){
			this.inherited(arguments);
		},

		startup : function() {
			this.inherited(arguments);
		},

		_buildItemBase: function(){
			this.inherited(arguments);
			if(this.rowInfo){
				for(var key in this.rowInfo){
					var info = this.rowInfo[key];
					if(!info){
						continue;
					}

					if(info.hidden != "true"){
						var colVal = info.value;
						// 地址本默认取名称显示
						if (info.type.indexOf("com.landray.kmss.sys.organization") > -1 && typeof (colVal) === "object") {
							colVal = colVal.name;
						} else if (typeof (colVal) === "object" && colVal.hasOwnProperty("value")) {
							// // 由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分
							colVal = colVal.text;
						}else if(info.businessType === "relevance" && colVal && colVal.indexOf("[")>-1){
							//处理关联文档显示
							colVal = JSON.parse(colVal);
							if(colVal.length > 0){
								var relValue = "";
								for(var v= 0;v< colVal.length;v++){
									if(relValue && colVal[v].subject){
										relValue+=";" + colVal[v].subject;
									}else{
										relValue += colVal[v].subject;
									}
								}
								colVal = relValue;
							}else{
								colVal ="";
							}
						}
						//#172889 是否显示千分位
						if(colVal && info.showThousand){
							colVal = colVal.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,');
						}
						var infoDom = domConstruct.create('div',{
							className: 'muiFillingInfo'
						},this.titleNode);
						var titleDom = domConstruct.create('div',{
							className: 'muiFillingLabelName'
						},infoDom);
						titleDom.innerText = info.title;
						var valDom = domConstruct.create('div',{
							className: 'muiFillingValue'
						},infoDom);
						//#167406 防止html代码注入
						valDom.innerText =  colVal;
					}
					if(key == "fdId"){
						this.mainModelId = info.value;
						if(this.detailFdId){
							this.fdId = this.detailFdId+"_"+this.relationId;
						}else{
							this.fdId = info.value+"_"+this.relationId;
						}
					}
					if(info.type == "detail"){
						for (var i = 0; i < info.value.length; i++){
							var detailValue = info.value[i];
							for (var j = 0; j < info.columns.length; j++){
								var col = info.columns[j];
								if (col.hidden != "true" && detailValue) {
									var colVal = detailValue[col.name];
									// 地址本默认取名称显示
									if (col.type.indexOf("com.landray.kmss.sys.organization") > -1 && typeof (colVal) === "object") {
										colVal = colVal.name;
									} else if (typeof (colVal) === "object" && colVal.hasOwnProperty("value")) {
										// // 由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分
										colVal = colVal.text;
									}else if(col.businessType === "relevance" && colVal && colVal.indexOf("[")>-1){
										//处理关联文档显示
										colVal = JSON.parse(colVal);
										if(colVal.length > 0){
											var relValue = "";
											for(var v= 0;v< colVal.length;v++){
												if(relValue && colVal[v].subject){
													relValue+=";" + colVal[v].subject;
												}else{
													relValue += colVal[v].subject;
												}
											}
											colVal = relValue;
										}else{
											colVal ="";
										}
									}
									//#172889 是否显示千分位
									if(colVal && col.showThousand){
										colVal = colVal.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,');
									}
									var infoDom = domConstruct.create('div',{
										className: 'muiFillingInfo'
									},this.titleNode);
									var titleDom = domConstruct.create('div',{
										className: 'muiFillingLabelName'
									},infoDom);
									titleDom.innerText = col.title;
									var valDom = domConstruct.create('div',{
										className: 'muiFillingValue'
									},infoDom);
									//#167406 防止html代码注入
									valDom.innerText =  colVal;
								}
							}
						}
					}
				}
				if(this.throughList){
					this.href = this.throughList.url;
				}
			}

		},

		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},

		//是否显示选择框
		showSelect:function(){
			return true;
		},

		_setSelectedTrigger: function() {
			var itemData = {};
			if(this.relationId && this.widgetId){
				itemData[this.widgetId]={};
				itemData[this.widgetId][this.relationId]={};
				itemData[this.widgetId][this.relationId] = this.rowInfo;
			}
			topic.publish("/mui/category/selected", this, {
				label: this.label,
				fdId: this.fdId,
				icon: this.icon,
				type: this.type,
				currentData:itemData,
				columnIndex:this.columnIndex,
			})
			topic.publish("/mui/category/cate_selected", this, {
				label: this.label,
				fdId: this.fdId,
				icon: this.icon,
				type: this.type,
				currentData:itemData,
				columnIndex:this.columnIndex,
			})
		},

		_toggleSelect: function(select) {
			if (select) {
				this._setSelected(this, this);
				this.topicCancelEvt();
			} else {
				this._cancelSelected(this, this)
			}

		},

		_cancelSelected: function(srcObj, evt) {
			if (srcObj.key == this.key || srcObj.key == this.fsKey) {
				if (evt && evt.fdId) {
					if (evt.fdId.indexOf(this.fdId) > -1) {
						if (this.checkedIcon) {
							domClass.remove(this.selectNode, "muiCateSeled")
							domConstruct.destroy(this.checkedIcon)
							this.checkedIcon = null
							this._cancelSelectedTrigger(evt)
							if(this.isMul){
								var pWeiget = this.getParent()
								pWeiget.curIds = array.filter(pWeiget.curIds,function (item) {
									return item != evt.fdId;
								})
							}
						}
					}
				}
			}
		},

		// 给同级节点发送取消事件
		topicCancelEvt: function() {
			var event = "/mui/category/cancelSelected"

			var list = this.getParent(),
				children = list.getChildren()
			if(!list.isMul){
				var self = this;
				array.forEach(
					children,
					function(child, index) {
						if (child == self) {
							return
						}
						topic.publish(event, this, {
							fdId: child.fdId
						})
					},
					this
				)
			}
		},

		_setSelected: function(srcObj, evt) {
			if (srcObj.key == this.key) {
				if (evt && evt.fdId) {
					if (evt.fdId == this.fdId) {
						if(this.isMul){
							var pWeiget = this.getParent()
							pWeiget.curIds.push(this.fdId);
						}else{
							if(window._curIds){
								topic.publish("/mui/category/unselected", this, {
									fdId: window._curIds
								})
							}
							window._curIds = this.fdId;
						}
						if (this.checkedIcon) {
							domConstruct.destroy(this.checkedIcon)
							this.checkedIcon = null
						}

						if (!this.selectNode) {
							return
						}

						if (!domClass.contains(this.selectNode, "muiCateSeled")) {
							domClass.add(this.selectNode, "muiCateSeled")
						}

						this.checkedIcon = domConstruct.create(
							"i",
							{
								className: "mui mui-checked muiCateSelected"
							},
							this.selectNode
						)
						this._setSelectedTrigger(evt)
					}
				}
			}
		},

		//是否选中
		isSelected:function(){
			if (window._curIds) {
				// 查看window._curIds是否存在选中值
				if (this.fdId == window._curIds){
					return true;
				}
			} else {
				var pWeiget = this.getParent()
				if (pWeiget && pWeiget.curIds) {
					if (array.indexOf(pWeiget.curIds, this.fdId) > -1) {
						return true
					}
				}
			}
			return false;
		},
		getScreenWidth : function() {
			return win.global.innerWidth
				|| win.doc.documentElement.clientWidth
				|| win.doc.documentElement.offsetWidth;
		},

		getRowInfo : function(item,rowIndex){
			var columns = this.columns;
			// 行信息
			var rowInfo = {};
			for (var i = 0; i < columns.length; i ++) {
				var col = item[i];
				rowInfo[col.name] = {value:col.data[rowIndex]};
				// 明细表
				rowInfo[col.name].type = col.type;
				if(col.type === "detail"){
					// 列定义
					rowInfo[col.name].columns = col.columns;
				}
			}
			return rowInfo;
		},
		getDrillingUrl: function () {
			var url = "";
			var throughList = this.throughList || {};
			if (throughList.isThrough) {
				var tmpUrl = throughList.url;
				var idInfo = this.mainModelId || "";
				if (idInfo && tmpUrl) {
					url = util.formatUrl(tmpUrl.replace(/\:fdId/g, idInfo));
				} else {
					console.error("【业务填充控件】获取穿透跳转链接失败!跳转的模板链接:" + tmpUrl + ";fdId:" + idInfo.value);
				}
			}
			return url;
		},
	});
	return item;
});