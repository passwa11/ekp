/**
 * 显示项样式设置生成器
 */
define(function(require, exports, module) {

	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require('lui/topic');
	var dialog = require('lui/dialog');
	var fieldDisplayCssSet = require("sys/modeling/base/listview/config/js/fieldDisplayCssSet");
	var modelingLang = require("lang!sys-modeling-base");
	var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");

	var relationDiagram = require("sys/modeling/base/relation/trigger/behavior/js/relationDiagram");

	var DisplayCssSet = base.Component.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			// 全局变量:存放显示项字段
			this.fieldCssSet = {};
			this.operationEle = cfg.operationEle;
			this.contentTemp = cfg.contentEle.html();
			cfg.contentEle.remove();

			this.valueName = "fd_"+ parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
			this.build();
			this.storeData = cfg.storeData;
			this.allField = cfg.allField;
			this.modelDict = listviewOption.baseInfo.modelDict;
			this.displayType = cfg.displayType || "";
			this.channel = cfg.channel || null;
			this.initByStoreData(this.storeData);
		},
		build : function() {
			var self = this;
			if(this.channel){
				topic.channel(this.channel).subscribe("modeling.selectDisplay.change", function(data) {
					self.selectDisplayData = data;
					self.selectDisplayChange(data.data.selected);
				});
			}else{
				topic.subscribe("modeling.selectDisplay.change", function(data) {
					self.selectDisplayData = data;
					self.selectDisplayChange(data.data.selected);
				});
			}
		},
		selectDisplayChange : function(selected) {
			this.displaycss_create = this.operationEle.find(".displaycss_create");
			this.displaycss_create_pop = this.operationEle.find(".displaycss_create_pop");
			var self= this;
			if(JSON.stringify(selected) == "[]"){
				this.displaycss_create.unbind("mouseenter");
				this.displaycss_create.unbind("mouseleave");
				//显示项为空则显示样式的新增取消高亮
				this.displaycss_create.css("pointer-events","none");
			}else{
				this.displaycss_create.css("pointer-events","auto");
				// 移入出现下拉列表
				this.displaycss_create.on("mouseenter",function() {
					self.displaycss_create_pop.css("height",
							"150px");
					self.displaycss_create_pop.css("border",
							"1px solid #ddd");
				})

				this.displaycss_create.on("mouseleave", function() {
					self.displaycss_create_pop.css("height", "0");
					self.displaycss_create_pop.css("border", "0");
				})
			}
			this.displaycss_create_pop.find("li").each(function() {
				$(this).on("mouseenter",function() {
					$(this).addClass("active");
				})
				$(this).on("mouseleave",function() {
					$(this).removeClass("active");
				})
			})
			// 1、改变悬浮下拉框的值
			// 2、改变下面样式组件的增加删除
			var self = this;
			var data = this.selectDisplayData.data;
			this.displaycss_create_pop.html("");
			var $ul = $("<ul/>");
			var selectedField = [];
			for (var i = 0; i < data.selected.length; i++) {
				var $li = $("<li/>");
				$li.attr("title", data.text[i]);
				$li.attr("lui-css-pop-idx", i);
				$li.text(data.text[i]);
				$li.on("click", function() {
					var idx = $(this).attr("lui-css-pop-idx");
					self.displayCssSet(idx);
				});
				$ul.append($li);
				selectedField.push(data.selected[i].field.split(".")[0]);
			}
			this.displaycss_create_pop.append($ul);

			// 当显示项取消选择时，删除对应的样式组件
			for ( var f in this.fieldCssSet) {
				if (selectedField.indexOf(f) == -1) {
					// 显示项字段样式不在显示项已选列表中 -> 移除
					if (this.fieldCssSet[f] != null) {
						this.fieldCssSet[f].destroy();
						this.fieldCssSet[f] = null;
					}
				}
			}

		},
		displayCssSet : function(idx) {
			var self = this;
			var selected = self.selectDisplayData.data.selected[idx];
			var text = self.selectDisplayData.data.text[idx];
			// 显示项
			var url = '/sys/modeling/base/listview/config/dialog_display.jsp';
			dialog.iframe(url, modelingLang['modelingAppListview.fdDisplayCssSet']+"(" + text + ")", function(data) {
				// 回调
				if (!data) {
					return;
				}
				data = $.parseJSON(data);
				self.DrawDisplayCssSet(data, text);
			}, {
				width : 780,
				height : 500,
				params : {
					selected : selected,
					text : text,
					displayType:self.displayType,
					allField:self.allField,
					modelDict:self.modelDict
				}
			})
		},
		DrawDisplayCssSet : function(data, text) {
			var self = this;
			// 判断新增
			var fKey = data.fieldKey;
			if (!self.fieldCssSet[fKey]) {
				// 不存在
				var cfg = {
					contentTemp : self.contentTemp,
					operationEle : self.operationEle,
					fieldKey : fKey,
					text : text,
					data : data,
					parent : self,
					displayType:self.displayType,
					allField:self.allField
				};
				this.fieldCssSet[fKey] = new fieldDisplayCssSet.FieldDisplayCssSet(cfg);
			}
			this.fieldCssSet[fKey].addChild(data);
		},
		doAdaptorData:function(allField,storeData){
			//处理控件属性变更之后，显示项的数据也对应变更
			var allFieldObj = {}; 
			for(var i = 0;i < allField.length;i++){
				allFieldObj[allField[i].field.split(".")[0]] = {
						text:allField[i].text,
						type:allField[i].type
				}
			}
			for(var key in storeData){
				for(var i = 0;i < storeData[key].length; i++){
					if(allFieldObj[key]){
						if(storeData[key][i].fieldText != allFieldObj[key].text){
							//改名字
							storeData[key][i].fieldText = allFieldObj[key].text;
						}
						if(storeData[key][i].selected.type != allFieldObj[key].type){
							//改属性类型
							storeData[key][i].selected.type = allFieldObj[key].type;
							//where条件运算符和值恢复默认
							this.changeWhereData(storeData[key][i].where,key,allFieldObj);
						}
					}
				}
			}
		},
		changeWhereData : function(whereArr,key,allFieldObj){
			for(var j = 0;j < whereArr.length;j++){
				whereArr[j].expression = {};
				if(allFieldObj[key].type != "String"){
					whereArr[j].match = "eq";
				}else{
					whereArr[j].match = "=";
				}
			}
		},
		getKeyData : function() {
			// 显示项样式对象
			var displayCssSetArr = {};
			for ( var key in this.fieldCssSet) {
				// 一个显示项的样式
				if (this.fieldCssSet[key] != null) {
					var fieldCssSetArray = this.fieldCssSet[key].fieldCssSetArray;
					var cssArr = [];
					for (var i = 0; i < fieldCssSetArray.length; i++) {
						var item = fieldCssSetArray[i].getKeyData();
						cssArr.push(item);
					}
					displayCssSetArr[key] = cssArr;
				}
			}
			return displayCssSetArr;
		},
		initByStoreData : function(storeData) {
			if (storeData && JSON.stringify(storeData) !="{}") {
				storeData = $.parseJSON(storeData);
				// #157017【服务问题单】【业务建模-优化】业务建模列表视图无法保存 传过来的json字符串中包含了\r \n导致parseJSON报错。
				var allField = $.parseJSON(this.allField.replace(/[\r]/g, "\\r").replace(/[\n]/g, "\\n"));
				this.doAdaptorData(allField,storeData);
				for ( var fk in storeData) {
					var cssArr = storeData[fk];
					for (var j = 0; j < cssArr.length; j++) {
						this.DrawDisplayCssSet(cssArr[j],cssArr[j].fieldText);
					}
				}
			}
		}
	});

	exports.DisplayCssSet = DisplayCssSet;
});
