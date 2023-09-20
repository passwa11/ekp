/**
 * 
 */
define(["dojo/_base/declare", "dijit/_WidgetBase", "mui/form/_GroupBase", "mui/util",
	"sys/modeling/main/xform/controls/placeholder/mobile/Util", "dojo/dom-construct", "mui/form/_StoreFormMixin"],
		function(declare, WidgetBase, _GroupBase, util, placeholderUtil, domConstruct, _StoreFormMixin){
	
	return declare("sys.modeling.main.xform.controls.placeholder.mobile.FormPlaceholder", [WidgetBase, _StoreFormMixin], {
		
		fetchUrl : "/sys/modeling/main/modelingAppXFormMain.do?method=executeQuery",
		
		recordDatas : {"showInfo":{},"valueInfo":{}},
		
		getCfgInfo : function(){
			return this.envInfo;
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			if (this.edit) {
				this.hiddenTextNode = domConstruct.create('input', {
					type : 'hidden',
					name : this.textName
				}, this.domNode);
			}
		},
		
		startup : function(){
			// 根据配置信息获取表单数据
			this.queryOptions = placeholderUtil.findFormValueByCfg(this.getCfgInfo());
			this.url = util.formatUrl(this.fetchUrl + "&widgetId=" + this.controlId + "&fdAppModelId=" + this.appModelId);
			this.inherited(arguments);
		},
		
		onComplete : function(initData){
			// 参考pc端，把数据源数据进行转换
			var data = this.formatData(initData);
			arguments[0] = data;
			this.inherited(arguments);
		},
		
		// 格式化数据，补全recordDatas信息
		formatData : function(sourceData){
			var columns = sourceData.columns;
			var valueIndex = 0;
			// 设置实际列
			for(var i = 0;i < columns.length;i++){
				var column = columns[i];
				// 约定name为fdId的为实际值列，而且 必须存在
				if(column.name === placeholderUtil.CONST.RECORDID){
					valueIndex = i;
					this.recordDatas.valueInfo = column;
					break;
				}
			}
			// 设置显示列
			this.setShowInfo(columns);
			
			// 为符合移动通用Group的数据要求，再对数据源进行转换
			var rs = [];
			var showInfoDatas = this.recordDatas.showInfo["data"];
			for(var i = 0;i < showInfoDatas.length;i++){
				rs.push({
					text : showInfoDatas[i],
					value : this.recordDatas.valueInfo["data"][i]
				});
			}
			return rs;
		},
		
		// 设置显示值
		setShowInfo : function(columns){
			var cfg = this.getCfgInfo();
			var transedDatas = this.transSourceData(columns);
			var expression = cfg["showTxt"]["expression"];
			var values = [];
			if(transedDatas && transedDatas.length){
				for(var i = 0;i < transedDatas.length;i++){
					var info = transedDatas[i];
					values.push(placeholderUtil.transValByExp(info, expression));
				}
			}
			this.recordDatas.showInfo = {data : values};
		},
		
		// 把列呈现的数据，转换成横向的数组
		transSourceData : function(columns){
			var rs = [];
			if(columns.length > 0){
				var len = columns[0]["data"].length;
				for(var i = 0;i < len;i++){
					var ele = {};
					for(var j = 0;j < columns.length;j++){
						ele[columns[j].name] = {};
						ele[columns[j].name]["value"] = columns[j]["data"][i];
					}
					rs.push(ele);
				}
			}
			return rs;
		},
		
		_setValueAttr : function(value) {
			this.inherited(arguments);
			if (this.edit){
				this.hiddenTextNode.value = this.getText();
			}
		},
		
		// _GroupBase的getText会让value和text无法正确的一一对应
		getText : function(){
			var text = [];
			if(this.value!=null && this.value!='') {
				var valArr = this.value.split(";");
				for (j = 0; j < valArr.length; j++) {
					for (var i = 0; i < this.values.length; i++) {
						var option = this.values[i];
						if (option.value == valArr[j]) {
							text.push(option.text);
							break;
						}						
					}
				}
			}
			return text.join(";");
		}
		
	});
	
})