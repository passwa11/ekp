/**
 * 
 */
define([ "dojo/_base/declare", "sys/xform/mobile/controls/event/EventDataList", 
		"sys/modeling/main/xform/controls/placeholder/mobile/dialog/DataListItem","dojo/topic", "dojo/_base/array", "dojo/request", "mui/util", "mui/dialog/Tip"], 
		function(declare, EventDataList, DataListItem, topic, array, request, util, Tip) {
	var claz = declare("sys.modeling.main.xform.controls.placeholder.mobile.dialog.DataList", [EventDataList], {
		
		_dataLength : 15,
		
		defalutNull : "",
		
		setData : function(data){
			this.data = data;
			//重新构建每行数据结构，以便于回填
			this.result = this.getDatas(data.columns,data);
			if(this.result && this.result.length > 0){
				var len = this.result.length;
				this._dataLength = len;
				for(var i = 0;i < len;i++){
					this.append(this.createItem(i,this.result[i]),this.containerNode);
				}
			}else{
				this.showNoRecord();
			}
		},
		
		createItem:function(index,columns){
			var item = new DataListItem({key:this.key, isMul:this.isMul, data:columns, columnIndex:index,detailFdId:columns.detailFdId});
			item.startup();
			return item;
		},
		
		_appendNext:function(srcObj,handle){
			if(srcObj.key==this.key){
				if(this.pageAble){
					var _self=this;
					topic.publish("/sys/xform/event/nextpage",this,{dataLength : this._dataLength,pageNum:(this._pageNum+1),paramsJSON:this.paramsJSON},{done:function(data){
							if(data && data.columns.length > 0 && data.columns[0].data.length > 0){
								if(data.columns[0].data.length < 15){
									topic.publish("/sys/xform/event/showNomore",_self);
								}
								_self._dataLength = data.columns[0].data.length;
								_self._pageNum = _self._pageNum + 1;
								_self.setData(data);
							}else{
								topic.publish("/sys/xform/event/showNomore",_self);
							}
							handle.done();
						}
					});
				}else{
					handle.done();
				}
			}
		},
		
		_returnData:function(srcObj){
			if(this.key == srcObj.key){
				var rs = [];
				array.forEach(this.selectItems,function(item){
					if(item != null && item.selected){
						var info = item.rowInfo;
						rs.push(info);
					}
				},this);
				topic.publish("/sys/xform/event/modeling_selected", this, rs);
			}
		},
		
		getRowInfo : function(item,rowIndex){
			var columns = this.data.columns;
			// 行信息
			var rowInfo = {};
			for (var i = 0; i < columns.length; i ++) {
				var col = item.data[i];
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
		

		_searchDataInfo : function(srcObj, evt) {
			if (evt) {
				if (srcObj.key == this.key) {
					var self = this;
					if (self.argu.appendSearchResult == "false" || self.argu.appendSearchResult == null) {
						this.selectItems = [];
					}
					this.paramsJSON = null;
					if (typeof (evt.argu) == "undefined"){
						return;
					}else {
						this.paramsJSON = evt.argu.paramsJSON;
					}
					// 适配器：转换参数以适应当前组件的参数
					this.transParams(this.paramsJSON);
					
					request.post(util.formatUrl(evt.argu.queryDataUrl),{
						data : this.paramsJSON,
						handleAs : "json"
					}).then(function(data) {
						// 删除所有的子对象
						array.forEach(self.getChildren(),function(item) {
							item.destroy();
						}, self);
						if (data && data.columns.length > 0) {
							self.setData(data);
						} else {
							//搜索时，无数据显示暂无内容
							this.showNoRecord();
						}
						topic.publish("/sys/xform/event/searchData",self);
					}, function() {
						Tip.fail({
							text : "查询出错,请联系管理员!"
						});
					});
					//#138023
					this.handleToTopTopic();
				}
			}
		},
		handleToTopTopic : function() {
			// 刷新列表后，置顶列表
			var body = this.ownerDocument;
			var mblScrollableViewContainer = body.getElementsByClassName("mblScrollableViewContainer")[0];
			$(mblScrollableViewContainer).css("transform","translate3d(0px, 0px, 0px)");
		},
		// 转换参数
		transParams : function(paramsJSON){
			var tempOuterSearchsParams = {};
			var tempOuterSearchs = JSON.parse(paramsJSON.outerSearchs || "[]");
			for(var i = 0;i < tempOuterSearchs.length;i++){
				var search = tempOuterSearchs[i];
				tempOuterSearchsParams[search.tagName] = search.value;
			}
			paramsJSON.search = JSON.stringify(tempOuterSearchsParams);
			delete paramsJSON.outerSearchs;
			
			paramsJSON.pageno = 1;
		},
		getDatas: function (columns,requestDatas) {
			var datas = [];
			//主表行数
			var rowLength = 0;
			if(columns.length > 0 && columns[0].data && columns[0].data.length > 0){
				rowLength = columns[0].data.length;
			}
			//#171368 【日常缺陷】【低代码平台-修复】业务关联控件，PC选择正常，移动没有值
			//明细表字段信息数组
			var detailData = [];
			if(columns.length > 0){
				for (var i = columns.length-1; i >= 0; i--) {
					var col = columns[i];
					if (col.type === "detail" && col.name===requestDatas.detailField) {
						detailData = col.data;
						break;
					}
				}
			}
			if(requestDatas.showDetail && detailData.length > 0){
				//处理明细表显示
				datas = this.getDetailDatas(rowLength,columns,detailData);
			}else{
				//处理主表显示
				for(var j= 0;j<rowLength;j++){
					var rowInfo = {};
					var row ={};
					for (var i = 0; i < columns.length; i++) {
						var col = columns[i];
						rowInfo[col.name] = {value: col.data[j]};
						// 明细表
						rowInfo[col.name].type = col.type;
						rowInfo[col.name].hidden = col.hidden;
						rowInfo[col.name].name = col.name;
						rowInfo[col.name].title = col.title;
						rowInfo[col.name].isDetail = false;
						rowInfo[col.name].businessType = col.businessType;
						if (col.type === "detail") {
							// 列定义
							rowInfo[col.name].columns = col.columns;
						}
					}
					row["rowInfo"] = rowInfo;
					row["columnIndex"] = i;
					row["columns"] = columns;
					datas.push(row);
				}

			}
			return datas;
		},

		getDetailDatas:function (rowLength,columns,detailData){
			//明细表数据
			var datas = [];
			//重新构造每行数据的结构
			for (var i = 0; i < rowLength; i++) {
				for(var m=0;m < detailData.length;m++) {
					var detail = detailData[m];
					if (detail.length > 0 && columns[0].data[i] === detail[0].mainModelId) {
						for (var n = 0; n < detail.length; n++) {
							// 行信息
							var rowInfo = {};
							var row ={};
							var dData = [];
							dData.push(detail[n]);
							//存储选中的明细表数据和对应的主表数据
							for(var j = 0 ; j< columns.length;j++){
								var col = columns[j];
								rowInfo[col.name] = {value: col.data[i]};
								// 明细表
								rowInfo[col.name].type = col.type;
								rowInfo[col.name].hidden = col.hidden;
								rowInfo[col.name].name = col.name;
								rowInfo[col.name].title = col.title;
								rowInfo[col.name].businessType = col.businessType;

								if (col.type === "detail") {
									// 列定义
									rowInfo[col.name].columns = col.columns;
									rowInfo[col.name].value = dData;
								}
							}
							//用明细表id作唯一值校验
							row["detailFdId"] = detail[n].fdId;
							row["rowInfo"] = rowInfo;
							row["columnIndex"] = i;
							row["columns"] = columns;
							datas.push(row);
						}
					}
				}
			}
			return datas;
		}
	});
	return claz;
});