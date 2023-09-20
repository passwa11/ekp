<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<!-- 分组按钮 -->
<div class="lui_maxtrix_toolbar">
    <div class="lui_maxtrix_toolbar_r matrix_data_cate" style="float: left;margin-top: 10px;">

    </div>
</div>
<!-- 操作按钮 -->
<div class="lui_maxtrix_toolbar">
    <div class="lui_maxtrix_toolbar_r">
        <kmss:auth
                requestURL="/sys/organization/sys_org_matrix/sysOrgMatrixTemplate.do?method=updateVerState&fdId=${param.fdMatrixId}">
        <a class="lui_maxtrix_toolbar_optBtn publish_btn" href="javascript:;" onclick="publishVer('${HtmlParam.fdIsEnable}');"><bean:message bundle="sys-organization" key="org.personnel.activation.one"/></a>
        </kmss:auth>
        <a class="lui_maxtrix_toolbar_optBtn" href="javascript:;" onclick="addData();"><bean:message key="button.add"/></a>
        <kmss:auth
                requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=downloadTemplate&fdId=${param.fdMatrixId}">
            <a class="lui_maxtrix_toolbar_optBtn" href="javascript:;" onclick="downloadTemplate();"><bean:message
                    bundle="sys-organization" key="sysOrgMatrix.template.download"/></a>
        </kmss:auth>
        <kmss:auth
                requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=importData&fdId=${param.fdMatrixId}">
            <a class="lui_maxtrix_toolbar_optBtn" href="javascript:;"
               onclick="importData();"><bean:message key="button.import"/></a>
        </kmss:auth>
        <kmss:auth
                requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=exportMatrixData&fdId=${param.fdMatrixId}">
            <a class="lui_maxtrix_toolbar_optBtn" href="javascript:;"
               onclick="exportData();"><bean:message key="button.export"/></a>
        </kmss:auth>
        <a class="lui_maxtrix_toolbar_optBtn" href="javascript:;"
           onclick="addMoreDatas();"><bean:message bundle="sys-organization"
                                                   key="sysOrgMatrix.edit.bulk.button"/></a>
        <a class="lui_maxtrix_toolbar_optBtn" href="javascript:;"
           onclick="batchReplace();"><bean:message bundle="sys-organization"
                                                   key="sysOrgMatrix.edit.batchReplace.button"/></a>
        <a class="lui_maxtrix_toolbar_optBtn" href="javascript:;"
           onclick="delAllData();"><bean:message key="button.deleteall"/></a>
    </div>
</div>

<!-- 矩阵卡片 - 左右移动 Starts -->
<div class="lui_matrix_data_tb_wrap">
    <!-- 类型 -->
    <div class="lui_matrix_data_tb_item lui_matrix_data_tb_item_l">
        <table id="matrix_seq_table_${HtmlParam.version}" name="matrix_seq_table_${HtmlParam.version}"
               data-version="${HtmlParam.version}" class="lui_matrix_tb_normal">
            <tr style="height: 50px;">
                <th class="lui_matrix_td_normal_title"><input id="matrix_seq_checkbox_${HtmlParam.version}"
                                                              type="checkbox"></th>
                <th class="lui_matrix_td_normal_title"><bean:message key="page.serial"/></th>
            </tr>
        </table>
    </div>
    <!-- 条件数据 -->
    <div class="lui_matrix_data_tb_item lui_matrix_data_tb_item_c">
        <table id="matrix_data_table_${HtmlParam.version}" name="matrix_data_table_${HtmlParam.version}"
               data-version="${HtmlParam.version}" class="lui_matrix_tb_normal">
            <tr style="height: 50px;">
            </tr>
        </table>
    </div>
    <!-- 操作数据 -->
    <div class="lui_matrix_data_tb_item lui_matrix_data_tb_item_r">
        <table id="matrix_opt_table_${HtmlParam.version}" name="matrix_opt_table_${HtmlParam.version}"
               data-version="${HtmlParam.version}" class="lui_matrix_tb_normal">
            <tr style="height: 50px;">
                <th>
                    <bean:message key="list.operation"/>
                </th>
            </tr>
        </table>
    </div>
</div>
<list:paging id="matrix_data_table_${HtmlParam.version}_page" viewSize="5"></list:paging>
<!-- 矩阵卡片 Ends -->
<!-- 表格脚本 -->
<script language="JavaScript">
    seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'sys/organization/resource/js/matrixPanel'], function ($, dialog, topic, matrixPanel) {
        var matrixPanel = new matrixPanel.MatrixPanel({'version': '${JsParam.version}'});
        matrixPanel.render();
        matrixPanelArray['${JsParam.version}'] = matrixPanel;
    });
</script>