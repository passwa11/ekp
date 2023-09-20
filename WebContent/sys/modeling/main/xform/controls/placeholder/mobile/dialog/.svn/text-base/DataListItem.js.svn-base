/**
 * 
 */
define([ "dojo/_base/declare", "sys/modeling/main/xform/controls/event/EventDataListItem", "dojo/dom-construct", "dojo/dom-style", "dojo/_base/array"], 
		function(declare, EventDataListItem, domConstruct, domStyle, array) {
	var claz = declare("sys.modeling.main.xform.controls.placeholder.mobile.dialog.DataListItem", [EventDataListItem], {
		
		doRender : function() {
			this._buildItemBase();
		},

		_buildItemBase: function(){
			this.rowInfo = this.data.rowInfo;
			if(this.rowInfo){
				this.isFirstInVisi = true;	// 是否是所见的第一个选项
				for(var key in this.rowInfo){
					var info = this.rowInfo[key];
					if(!info){
						continue;
					}
					//处理主表字段
					if(info.hidden != "true"){
						var colVal = info.value;
						//解析字段显示值
						colVal = this.getColumnText(colVal,info);
						//构建字段显示样式
						this.buildInfo(colVal,info);
					}
					if(key == "fdId"){
						this.mainModelId = info.value;
						//判断是否有明细表字段，如果有则将每行数据的唯一校验改为明细表表行fdId,否则用主表fdId
						if(this.detailFdId){
							this.fdId = this.detailFdId;
						}else{
							this.fdId = info.value;
						}
					}
					//处理明细表字段
					if(info.type == "detail"){
						for (var i = 0; i < info.value.length; i++){
							var detailValue = info.value[i];
							for (var j = 0; j < info.columns.length; j++){
								var col = info.columns[j];
								if (col.hidden != "true" && detailValue) {
									var colVal = detailValue[col.name];
									//解析字段显示值
									colVal = this.getColumnText(colVal,col);
									//构建字段显示样式
									this.buildInfo(colVal,col);
								}
							}
						}
					}
				}
			}

		},
		
		getColumnText : function(colVal, col){
			//138386
			if(!colVal){
				colVal = "";
			}
			if(col.type.indexOf("com.landray.kmss.sys.organization") >- 1 && typeof(colVal) === "object"){
				colVal = colVal.name;
			}else if(typeof(colVal) === "object" && colVal.hasOwnProperty("value")){
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
			return colVal || "";
		},
		buildInfo : function(colVal, col){
			//构建列表显示项
			var eItem = domConstruct.create("div", {className:"muiEventItem"}, this.contentNode);
			domStyle.set(eItem, "padding-left", "1rem");
			var eventInfo = domConstruct.create("div", {className:"muiEventInfo"}, eItem);
			domConstruct.create("div", {className:"muiEventTitle", innerHTML: col.title}, eventInfo);
			var valDom = domConstruct.create("div", {className:"muiEventValue"}, eventInfo);
			//#167406 防止html代码注入
			valDom.innerText = colVal;
			if(col.hidden == "true"){
				domStyle.set(eItem, "display", "none");
			}else if(this.isFirstInVisi){
				this.isFirstInVisi = false;
				//判断是单选还是多选
				var areaDiv  = domConstruct.create("div", {className:"muiEventSelArea " + (this.isMul?"muiEventSelMul":"muiEventSelSgl")}, eventInfo);
				this.selNode = domConstruct.create("div", {className:"muiEventSel"}, areaDiv);
			}
		}
	});
	return claz;
});