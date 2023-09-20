/*******************************************************************************
 * 统计使用js
 ******************************************************************************/
define( function(require, exports, module) {
	var topic = require('lui/topic');
	var dialog = require('lui/dialog');
	var lang = require('lang!hr-staff');
	var strUtil = require('lui/util/str');
	// list数据加载完事件
	var EVENT_LISTDATA_LOADED = "list.data.loaded";

	// chart转换后的列表点击事件
	var EVENT_LIST_CLICK = "list.click";

	// chart和列表切换事件
	var EVENT_CHART_SWITCH = "chart.switch";

	var stat = {
		/******************************************
		 * 入口函数：执行查询
		 *****************************************/
		statExecutor : function(type) {
			if (!type) {
				type = 'chart';
			}
			var self = this;
			if (window.statValidation != null) {
				if (!statValidation.validate())
					return;
			}
			var url = Com_SetUrlParameter(location.href, "method", "statChart");
			// 加载图标数据
			LUI('hrStaffPersonReportChart').replaceDataSource({
				"type" : "AjaxJson",
				"url" : url + "&" + $.param(this._getConditionData(), true)
			});
			$("#div_chartArea").show();

			// 加载list数据
			url = Com_SetUrlParameter(location.href, "method", "statList");
			$.post(url, $.param(this._getConditionData(), true), function(contextData) {
				if (contextData != null) {
					self.showList($('#div_list'), contextData);
					topic.publish(EVENT_LISTDATA_LOADED, contextData);
				}
			}, 'json');

			// 显示图标还是列表
			if (type == 'chart') {
				$("#div_listSection").hide();
			} else {
				$("#div_listSection").show();
				$("#div_chartSection").hide();
			}

		},
		//条件参数
		_getConditionData : function() {
			var data = {};
			$("#div_condtionSection").find(":input").each( function() {
				var thisObj = $(this);
				var name = thisObj.attr("name");
				if (name != null && name != '') {
					if (thisObj.is(":radio")) {
						if (thisObj.is(":checked")) {
							data[name] = thisObj.val();
						}
					} else if (thisObj.is(":checkbox")) {
						if (thisObj.is(":checked")) {
							var oldVal = data[name];
							if (oldVal == null) {
								data[name] = thisObj.val();
							} else {
								data[name] = oldVal + ";" + thisObj.val();
							}
						}
					} else {
						data[name] = thisObj.val();
					}
				}
			});
			return data;
		},
		statSwitchStaues : false,
		/******************************************
		 * 显示图表
		 *****************************************/
		showChart : function(chartDiv, contextData) {

		},
		/******************************************
		 * 显示列表
		 *****************************************/
		showList : function(listDiv, result) {
			var fileds = result.fileds, datas = result.datas;
			var _self = this;
			listDiv.html('');
			var content = "<span>" + lang['hrStaffPersonReport.chart.noData'] + "</span>";
			if (datas != null && datas.length > 0) {
				content = $('<table class="tab_listData"></table>');
				var titleTr = $('<tr class="tab_title"/>');
				$('<th>' + lang['hrStaffPersonReport.chart.serial'] + '</th>').appendTo(titleTr);
				for ( var key in fileds) {
					var field = fileds[key]; 
					$('<th>' + strUtil.decodeOutHTML(field) + '</th>').appendTo(titleTr);
				}
				titleTr.appendTo(content);
				for ( var i = 0; i < datas.length; i++) {
					var tempData = datas[i];
					var dataTr = $('<tr class="tab_data" index="' + i + '"/>');
					$('<td>' + (i + 1) + '</td>').appendTo(dataTr);
					for ( var key in fileds) {
						field = fileds[key];
						$('<td></td>').text(tempData[key]).appendTo(dataTr);
					}
					dataTr.click( function(domObj) {
						var thisObj = $(this);
						topic.publish(EVENT_LIST_CLICK, {
							target : thisObj,
							data : datas[thisObj.attr("index")]
						});
					});
					dataTr.appendTo(content);
				}
			}
			if ($("#div_listSection").length > 0) {
				listDiv.append(content).append(
						$("<div class='div_close com_btn_link'>" + lang['hrStaffPersonReport.chart.return'] + "</div>").click( function() {
							_self.switchChart("0");
						}));
			} else {
				listDiv.append(content);
			}
		},
		/******************************************
		 * 图标和列表切换
		 *****************************************/
		switchChart : function(isTable) {
			if (isTable == "1") {//当前状态显示图标
				$("#div_chartSection").hide();
				$("#div_listSection").show();
			} else {
				$("#div_listSection").hide();
				$("#div_chartSection").show();
			}
			topic.publish(EVENT_CHART_SWITCH);
		},
		expandDiv : function(thisObj, sectionId) {
			var expObj = $("#" + sectionId);
			var isShow = expObj.attr("isShow");
			if (isShow == null || isShow == "1") {
				isShow = "1";
			} else {
				isShow = "0";
			}
			var iconSpan = $(thisObj).find("span");
			iconSpan.removeAttr("class");
			if (isShow == "1") {//当前状态显示图标
				expObj.attr("isShow", "0");
				expObj.hide();
				iconSpan.addClass("div_icon_coll");
			} else {
				expObj.attr("isShow", "1");
				expObj.show();
				iconSpan.addClass("div_icon_exp");
			}
		}
	};
	/***************************************************************************
	 * 对外使用
	 **************************************************************************/
	module.exports = stat;
});
