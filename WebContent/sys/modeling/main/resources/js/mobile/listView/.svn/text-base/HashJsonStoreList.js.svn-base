/**
 * 移动列表视图
 */
define([
    "dojo/_base/declare",
	"mui/list/HashJsonStoreList",
	'dojo/topic',
	'dojox/mobile/viewRegistry',
	"mui/util",
	"mui/i18n/i18n!sys-modeling-main"
	], function(declare, HashJsonStoreList, topic, viewRegistry,util,modelingLang) {

	return declare("sys.modeling.main.resources.js.mobile.listView.HashJsonStoreList", [HashJsonStoreList], {

		itemRenderer: null,

		// 请求完数据之后调用，添加标题字段和摘要字段信息
		formatDatas : function() {
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

			var fdType = scroll.rel.listViewType || "0";
			var listInfo = scroll.rel.listInfo || {};
			listInfo.summaryFields = listInfo.summaryFields || "";
			var summaryTitles = listInfo.summaryTitles || "{}";
			summaryTitles =JSON.parse(summaryTitles);
			var index = scroll.id.substring(scroll.id.indexOf('mui_list_StoreElementScrollableView_')+36);
			topic.publish("statistics.info.update",index,datas);
			if(fdType == "2"){
				scroll.containerNode.classList.add("cardViewMobileConfigContainer");
				scroll.containerNode.classList.add("boardViewContainer");
			}else if (fdType == "1") {
				scroll.containerNode.classList.add("viewMobileConfigContainer");
			}
			for(var i = 0;i < datas.length;i++){
				var data = datas[i];
				data.summary = [];
				var summaryNum = 0;
				var imgTemp = data['coverImg'] || '{}';
				imgTemp = JSON.parse(imgTemp);
				data.imgSrc = imgTemp[data.fdId] || "";
				data.columnNum = listInfo.fdColumnNum || '1';
				data.fdType = fdType;
				for(var key in data){
					// 取最后一个点后面的字段
					var tempKey = key.replace(/(\w+\.)*/g, "");
					// 标题字段
					//#122334 标题字段选择创建者时，listInfo.subjectField == docCreator.fdName,tempKey == docCreator,不匹配，导致显示未定义
					if(listInfo.subjectField.indexOf("docCreator") > -1){
						listInfo.subjectField = "docCreator";
					}
					if(tempKey === listInfo.subjectField){
						data["label"] = data[key] || modelingLang['mui.modeling.no.data'];
					}
					if(listInfo.summaryFields.indexOf(tempKey) > -1){
						if(summaryNum < 6){
							var summaryValue = data[key] || modelingLang['mui.modeling.no.data'];
							if(fdType == "2"){
								if (summaryTitles[tempKey]){
									data.summary.push({text:summaryTitles[tempKey],value: summaryValue});
								} else{
									data.summary.push({text:"",value:summaryValue});
								}
							}else{
								//摘要字段
								if (summaryTitles[tempKey]){
									data.summary.push(summaryTitles[tempKey]+": "+summaryValue);
								} else{
									data.summary.push(summaryValue);
								}
							}
							summaryNum++;
						}
					}
				}
			}
			topic.publish("/modeling/statisticList/render",scroll);
		},
		reload: function(handle) {
			this.pageno = 1;
			this._loadOver = false;
			this.url+="&sortChange=true";
			return this.doLoad(handle, false);
		},
	});
});


