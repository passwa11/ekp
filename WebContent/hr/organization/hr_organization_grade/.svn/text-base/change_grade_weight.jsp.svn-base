<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:button id="quickSort" text="${lfn:message('hr-organization:hr.organization.info.grade.setweight') }" onclick="quickSort()" order="1" ></ui:button>
<script type="text/javascript">
var isSave = false;
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	/**
	 * 列表刷新事件
	 */
	topic.subscribe("list.loaded", function() {
		window.btnChange(false);
	});
	/**
	 * 获取数据表格
	 */
	window.getTable = function() {
		var tableId = "${JsParam.tableId}";
		var table;
		// 获取数据表格ID，默认获取.lui_listview_columntable_table
		if(tableId == "" || tableId == "null") {
			table = $("table.lui_listview_columntable_table");
		} else {
			table = $("#" + tableId);
		}
		return table;
	};
	window.getColumn = function() {
		var column = "${JsParam.column}";
		// 排序字段在表格中的第几列，默认第2列
		if(column == "" || column == "null") {
			column = "2";
		}
		return column;
	};

	/**
	 * 快速排序
	 */
	window.quickSort = function() {
		var table = getTable();
		var column = getColumn();
		if(table.length < 1) return false;

		if(isSave) {
			saveSort(table);
		} else {
			buildQuick(table, column);
		}
	};

	// 按钮改变
	window.btnChange = function(flag) {
		if(flag) {
			// 快速排序构建完后，变更按钮
			isSave = true;
			$("div.lui_toolbar_btn>#quickSort>").find(".lui_widget_btn_txt").text("${lfn:message('hr-organization:hr.organization.info.grade.saveweight') }");
		} else {
			// 保存排序成功后，才会更新按钮
			$("div.lui_toolbar_btn>#quickSort>").find(".lui_widget_btn_txt").text("${lfn:message('hr-organization:hr.organization.info.grade.setweight') }");
			isSave = false;
		}
	}

	// 构建快速排序
	window.buildQuick = function(table, column) {
		// 获取表格中所有的行
		var trs = table.find("tbody>tr");
		if(trs.length > 0) {
			// 循环处理行
			$.each(trs, function(i, tr) {
				// 获取文档ID
				var id = $(tr).attr("kmss_fdid");
				// 获取要处理的列
				var td = $(tr).find("td:nth-child(" + column + ")");
				// 获取原来的排序号
				var val = td.text();
				// 构建一个文本输入框，并传入原来的排序号
				td.html("<input type='text' name='orderNum' value='" + val + "' kmss_fdid='" + id + "' style='width:30px;'/><td></br><input type='hidden' name='orderNumOld' value='" + val + "' kmss_fdid='" + id + "'/></td>");
			});

			window.btnChange(true);
		}
	};
	
	/**
	 * 保存排序
	 */
	window.saveSort = function(table) {
		var orderNums = table.find("tbody>tr input[name='orderNum']");
		var orderNumsOld = table.find("tbody>tr input[name='orderNumOld']");
		var ordersOld = [];
		$.each(orderNumsOld, function(i, orderNumOld) {
			ordersOld.push(orderNumOld.value);
		});
		
		var orders = [];
		$.each(orderNums, function(i, orderNum) {
			orders.push({id: $(orderNum).attr("kmss_fdid"), orderNum: $(orderNum).val(), orderNumOld: ordersOld[i]});
		});

		window.del_load = dialog.loading();
		$.ajax({
			url : '<c:url value="/sys/profile/sysProfileOrder.do" />?method=updateOrderNum',
			type : 'POST',
			data : {modelName:"${JsParam.modelName}", property:"${JsParam.property}", orders:JSON.stringify(orders)},
			dataType : 'json',
			error : function(data) {
				if(window.del_load != null) {
					window.del_load.hide(); 
				}
				dialog.result(data.responseJSON);
			},
			success: function(data) {
				if(window.del_load != null){
					window.del_load.hide(); 
					topic.publish("list.refresh");
				}
				dialog.result(data);

				window.btnChange(false);
			}
	   });
	};
});
</script>