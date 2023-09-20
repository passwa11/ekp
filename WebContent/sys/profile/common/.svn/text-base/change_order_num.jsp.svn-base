<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- 批量修改排序号JSP片断 --%>
<%-- 调用方法（直接在需要使用的页面上引入）：

<!-- 快速排序 -->
<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
	<c:param name="modelName" value="com.landray.kmss.sys.organization.model.SysOrgOrg"></c:param><!-- 必填，修改数据的modelName -->
	<c:param name="property" value="fdOrder"></c:param><!-- 必填，修改数据的属性 -->
	<c:param name="tableId" value=""></c:param><!-- 可选，数据表格ID，默认获取.lui_listview_columntable_table -->
	<c:param name="column" value=""></c:param><!-- 可选，需要修改的列号，默认第2列 -->
	<c:param name="btnOrder" value=""></c:param><!-- 可选，按钮的排序号 -->
	<c:param name="min" value=""></c:param><!-- 可选，排序号最小值（默认不限） -->
	<c:param name="max" value=""></c:param><!-- 可选，排序号最大值（默认不限） -->
</c:import>
 --%>

<ui:button id="quickSort" text="${lfn:message('sys-organization:org.operation.quickSort')}" onclick="quickSort()" order="${empty JsParam.btnOrder ? 5 : JsParam.btnOrder}" ></ui:button>

<script type="text/javascript">
    var isSave = false;
    var min = parseInt("${JsParam.min}");
    var max = parseInt("${JsParam.max}");
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
        /**
         * 获取修改的列号
         */
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
                $("div.lui_toolbar_btn>#quickSort>").find(".lui_widget_btn_txt").text("${lfn:message('sys-organization:org.operation.saveSort')}");
            } else {
                // 保存排序成功后，才会更新按钮
                $("div.lui_toolbar_btn>#quickSort>").find(".lui_widget_btn_txt").text("${lfn:message('sys-organization:org.operation.quickSort')}");
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
         * 显示错误信息
         */
        window.showError = function(elem, msg) {
            $(elem).parent().append("<span class='order_error'><span style='color: #cc0000 !important;font-weight: bolder;'>${lfn:message('model.fdOrder')}</span> " + msg + "</span>");
        }

        /**
         * 保存排序
         */
        window.saveSort = function(table) {
            // 清除错误信息
            table.find("span.order_error").remove();
            var orderNums = table.find("tbody>tr input[name='orderNum']");
            var orderNumsOld = table.find("tbody>tr input[name='orderNumOld']");
            var ordersOld = [];
            $.each(orderNumsOld, function(i, orderNumOld) {
                ordersOld.push(orderNumOld.value);
            });
            var orders = [];
            var err = false;
            $.each(orderNums, function(i, orderNum) {
                // 如果为空，不校验
                if($(orderNum).val().replace(/(^\s*)|(\s*$)/g, "") == "") {
                    $(orderNum).val("");
                    return true;
                }
                // 必须是整数（包含负整数）
                if (!/^-?\d+$/.test($(orderNum).val())) {
                    showError(orderNum, "${lfn:message('sys-profile:sys.profile.changeOrder.isNaN')}");
                    err = true;
                } else {
                    var num = parseInt($(orderNum).val());
                    if (min != NaN && num < min) {
                        showError(orderNum, "${lfn:message('sys-profile:sys.profile.changeOrder.min')}" + min);
                        err = true;
                    } else if (max != NaN && num > max) {
                        showError(orderNum, "${lfn:message('sys-profile:sys.profile.changeOrder.max')}" + max);
                        err = true;
                    }
                }
                orders.push({id: $(orderNum).attr("kmss_fdid"), orderNum: $(orderNum).val(), orderNumOld: ordersOld[i]});
            });
            if(err) {
                return false;
            }
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