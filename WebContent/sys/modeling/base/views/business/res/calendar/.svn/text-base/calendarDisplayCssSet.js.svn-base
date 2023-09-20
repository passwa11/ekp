/**
 * 显示项样式设置生成器
 */
define(function(require, exports, module) {

	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require('lui/topic');
	var dialog = require('lui/dialog');
	var displayCssSet = require("sys/modeling/base/listview/config/js/displayCssSet");
	var lang = require("lang!sys-modeling-base");

	var CalendarDisplayCssSet = displayCssSet.DisplayCssSet.extend({
		selectDisplayData:{},
		initProps : function($super, cfg) {
			this.showMode = cfg.showMode || "";
			this.allField = cfg.allField;
			this.modelDict = listviewOption.baseInfo.modelDict;
			$super(cfg);
		},
		selectDisplayChange : function(selected) {
			// 1、改变悬浮下拉框的值
			// 2、改变下面样式组件的增加删除
			var data = this.selectDisplayData.data;
			var selectedField = [];
			for (var i = 0; i < data.selected.length; i++) {
				selectedField.push(data.selected[i].field.split(".")[0]);
			}

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
			if (this.fieldCssSet){
				for ( var f in this.fieldCssSet) {
					if (this.fieldCssSet[f] != null
						&& this.fieldCssSet[f].fieldCssSetArray
						&& this.fieldCssSet[f].fieldCssSetArray.length > 0) {
						this.fieldCssSet[f].fieldCssSetArray[0].updatedisplayCssSet();
						return;
					}
				}
			}
			var self = this;
			if(self.selectDisplayData.data
				&& self.selectDisplayData.data.selected
				&& self.selectDisplayData.data.selected.length > 0){
				var selected = self.selectDisplayData.data.selected[idx];
				var text = self.selectDisplayData.data.text[idx];
				// 显示项
				var url = '/sys/modeling/base/listview/config/dialog_display.jsp';
				dialog.iframe(url, lang['modelingAppListview.fdDisplayCssSet']+"(" + text + ")", function(data) {
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
						showMode:self.showMode,
						allField:self.allField,
						modelDict:self.modelDict
					}
				})
			}
		},
	});

	exports.CalendarDisplayCssSet = CalendarDisplayCssSet;
});
