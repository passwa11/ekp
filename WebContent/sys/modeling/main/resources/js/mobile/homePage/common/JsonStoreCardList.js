/**
 * 移动列表视图，用于portlet展示列表
 */
define([
    "dojo/_base/declare",
	"sys/mportal/mobile/card/JsonStoreCardList",
	'dojo/topic',
	'dojox/mobile/viewRegistry'
	], function(declare, JsonStoreCardList, topic, viewRegistry) {
	
	return declare("sys.modeling.main.resources.js.mobile.homePage.common.JsonStoreCardList", [JsonStoreCardList], {
		
		itemRenderer: null,
		
		// 请求完数据之后调用，添加标题字段和摘要字段信息
		formatDatas : function() {
			var datas = this.inherited(arguments);
			this.customBuildDatas(datas);
			return datas;
		},
		
		customBuildDatas : function(datas){
			var parentWgt = this.getParent();
			if(!parentWgt){
				console.log("【移动JsonStoreCardList组件】找不到父组件,无法获取配置信息");
				return;
			}
			var listInfo = this.getListInfo(parentWgt.cfgData);
			if(!listInfo){
				console.log("【移动JsonStoreCardList组件】找不到父组件的配置信息");
				return;
			}
			listInfo.summaryFields = listInfo.summaryFields || "";
			var summaryTitles = listInfo.summaryTitles || "{}";
			summaryTitles =JSON.parse(summaryTitles);
			if(this.fdType == "2"){
				this.containerNode.offsetParent.classList.add("cardViewMobileConfigContainer");
				this.containerNode.offsetParent.classList.add("boardViewContainer");
			}else if (this.fdType == "1") {
				this.containerNode.offsetParent.classList.add("viewMobileConfigContainer");
			}
			for(var i = 0;i < datas.length;i++){
				var data = datas[i];
				data.summary = [];
				var summaryNum = 0;
				var imgTemp = data['coverImg'] || '{}';
				imgTemp = JSON.parse(imgTemp);
				data.imgSrc = imgTemp[data.fdId] || "";
				data.columnNum = listInfo.fdColumnNum || '1';
				data.fdType = this.fdType;
				for(var key in data){
					// 取最后一个点后面的字段
					var tempKey = key.replace(/(\w+\.)*/g, "");
					// 标题字段
					//#131739 移动端多列表首页，修改标题后，首页显示的标题和点击更多显示的文档标题不一致
					if(tempKey === (listInfo.subjectField.indexOf("docCreator") > -1 ? "docCreator":listInfo.subjectField)){
						data["label"] = data[key];
					}
					if(listInfo.summaryFields.indexOf(tempKey) > -1){
						if(summaryNum < 6){
							var summaryValue = data[key] || '暂无数据';
							if(this.fdType == "2"){
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
		},
		
		getListInfo : function(cfgData){
			var tabInfo = cfgData.tabInfo;
			for(var i = 0;i < tabInfo.length;i++){
				var item = tabInfo[i]
				if(item.listViewsId === this.listViewsId && item.viewId === this.viewId){
					this.fdType = item.listViewType;
					return item.listInfo;
				}
			}
		}
		
	});
});