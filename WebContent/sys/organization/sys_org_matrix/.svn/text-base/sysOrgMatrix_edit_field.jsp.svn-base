<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<!-- 矩阵字段配置说明 start -->
<div class="sysOrgMatrixfieldBox">
    <div class="sysOrgMatrixfieldBoxConfig">
        <bean:message bundle="sys-organization" key="sysOrgMatrixRelation.fdFieldNameConfig"/>
        <div class="sysOrgMatrixfieldBoxExplain">
        <span class="sysOrgMatrixfieldExplainTitle">
          <bean:message bundle="sys-organization" key="sysOrgMatrixRelation.fdFieldNameExplain"/>
        </span>
            <span class="sysOrgMatrixfieldExplainTitle">
          <span class="sysOrgMatrixfieldConditionColor"></span>
          <bean:message bundle="sys-organization" key="sysOrgMatrixRelation.fdFieldConditionLabel"/>
        </span>
            <span class="sysOrgMatrixfieldExplainTitle">
          <span class="sysOrgMatrixfieldResultColor"></span>
          <bean:message bundle="sys-organization" key="sysOrgMatrixRelation.fdFieldResultLabel"/>
        </span>
        </div>
    </div>
    <c:if test="${sysOrgMatrixForm.matrixType!='1'}">
    <div class="sysOrgMatrixfieldBoxOption">
        <%-- 暂不支持拖动，先注释
       <span class="sysOrgMatrixfieldBoxOptionTips">
         <i></i>
         <bean:message bundle="sys-organization" key="sysOrgMatrixRelation.fdFieldDropTips"/>
       </span>
       --%>
        <div class="sysOrgMatrixfieldAddField ConditionField" onclick="addCon(this)">
            <bean:message bundle="sys-organization" key="sysOrgMatrixRelation.fdFieldAddCLabel"/>
        </div>
        <div class="sysOrgMatrixfieldAddField ResultField" onclick="addRes(this)">
            <bean:message bundle="sys-organization" key="sysOrgMatrixRelation.fdFieldAddRLabel"/>
        </div>
    </div>
    </c:if>
</div>
<!-- 矩阵字段配置说明 end -->
<div class="lui_matrix_div_wrap">
    <!-- 矩阵卡片 - 左右移动 Starts  -->
    <div class="lui_matrix_field_tb_wrap">
        <!-- 类型 -->
        <div class="lui_matrix_field_tb_item lui_matrix_field_tb_item_type">
            <table id="matrix_type_table" class="lui_matrix_tb_normal" style="width: 80px;">
                <tr>
                    <td class="lui_matrix_td_normal_title"><bean:message key="sys.common.viewInfo.type"/></td>
                </tr>
                <tr style="height: 80px;">
                    <td class="lui_matrix_td_normal_title"><bean:message bundle="sys-organization"
                                                                         key="sysOrgMatrixRelation.fdFieldName"/><br><bean:message
                            bundle="sys-organization" key="sysOrgMatrixRelation.fdFieldName.alias"/></td>
                </tr>
                <c:if test="${sysOrgMatrixForm.matrixType!='1'}">
                    <tr>
                        <td class="lui_matrix_td_normal_title"><bean:message key="list.operation"/></td>
                    </tr>
                </c:if>
            </table>
        </div>
        <!-- 条件数据 -->
        <div class="lui_matrix_field_tb_item lui_matrix_field_tb_item_condition">
            <table id="matrix_table" class="lui_matrix_tb_normal">
                <!-- 表头行 -->
                <tr id="matrix_field_type">
                </tr>
                <!-- 内容行 -->
                <tr id="matrix_field_value" style="height: 80px;">
                </tr>
                <!-- 操作行 -->
                <c:if test="${sysOrgMatrixForm.matrixType!='1'}">
                    <tr id="matrix_field_opt">
                    </tr>
                </c:if>
            </table>
        </div>
    </div>
    <kmss:auth requestURL="/sys/organization/sys_org_matrix_relation/sysOrgMatrixRelation.do?method=check&matrixId=${sysOrgMatrixForm.fdId}" requestMethod="GET">
    <c:if test="${not empty delCountDesc}">
    <div style="padding-top: 20px;">
        <span style="color: red;">
            <c:out value="${delCountDesc}"/>
            <a href="javascript:;" onclick="fieldCheck();">${lfn:message('sys-organization:sysOrgMatrix.field.check')}</a>
        </span>
    </div>
    </c:if>
    </kmss:auth>
</div>
<!-- 模板数据预览 -->
<div id="sysOrgMatrixPreviewContent">
    <div class="sysOrgMatrixPreviewTitle">
        <bean:message bundle="sys-organization" key="sysOrgMatrixRelation.fdFieldPreview"/>
    </div>
    <div id="sysOrgMatrixPreviewTable">
        <div class="sysOrgMatrixPreviewTable_l">
            <table>
                <tr>
                    <th class="lui_matrix_td_normal_title"><bean:message key='page.serial'/></th>
                </tr>
            </table>
        </div>
        <div class="sysOrgMatrixPreviewTable_c">
            <table>
                <tr>
                    <th></th>
                </tr>
            </table>
        </div>
        <div class="sysOrgMatrixPreviewTable_r">
            <table>
                <tr>
                    <th class="lui_matrix_td_normal_title"><bean:message key='list.operation'/></th>
                </tr>
            </table>
        </div>
    </div>
</div>

<!-- 最小列宽 -->
    <div class="">
        <bean:message bundle="sys-organization" key="sysOrgMatrix.min.width"/>
        <xform:text property="width"
                    subject="${lfn:message('sys-organization:sysOrgMatrix.min.width') }" required="true"
                    validators="number maxLength(200) min(100)"/>px
        <label>
            <div class="item_tips lui_icon_s lui_icon_s_cue4" style="float: none !important;"
                 title="<bean:message bundle='sys-organization' key='sysOrgMatrix.min.width.desc'/>"></div>
        </label>
    </div>
<!-- 模板数据 sysOrgMatrixForm.matrixType=='1'内置的矩阵数据不被修改 -->
<script id="tpl_con_type" type='text/template'>
    <c:if test="${sysOrgMatrixForm.matrixType=='1'}">
        <xform:select property="fdRelationConditionals[0].fdType1" showStatus="readOnly" required="true"
                      subject="${lfn:message('sys-organization:sysOrgMatrixRelation.fdType1') }" style="width:90%;"
                      onValueChange="conditionalChange">
            <xform:enumsDataSource enumsType="sys_org_matrix_conditional"/>
        </xform:select>
    </c:if>
    <c:if test="${sysOrgMatrixForm.matrixType!='1'}">
        <xform:select property="fdRelationConditionals[0].fdType1" required="true"
                      subject="${lfn:message('sys-organization:sysOrgMatrixRelation.fdType1') }" style="width:90%;"
                      onValueChange="conditionalChange">
            <xform:enumsDataSource enumsType="sys_org_matrix_conditional"/>
        </xform:select>
    </c:if>
    <input type="hidden" name="fdRelationConditionals[0].fdId">
    <input type="hidden" name="fdRelationConditionals[0].fdType">
    <div class="main_data_div" style="display: none;">
        <input type="hidden" name="fdRelationConditionals[0].fdMainDataType" disabled="disabled">
        <xform:text property="fdRelationConditionals[0].fdMainDataText" style="width:70%" validators="maxLength(200)"/>
        <a href="javascript:void(0);" onclick="selectMainData(this);"><bean:message key="dialog.selectOther"/></a>
    </div>
    <label>
        <div class="org_dept_div" style="display: none;">
            <input type="hidden" name="fdRelationConditionals[0].fdIncludeSubDept" class="type_dept"
                   disabled="disabled">
            <input type="checkbox" disabled="disabled" onclick=setValue(this)><bean:message bundle='sys-organization'
                                                                                            key='sysOrgMatrix.match.subdept'/>
            <div class="item_tips lui_icon_s lui_icon_s_cue4"
                 title="<bean:message bundle='sys-organization' key='sysOrgMatrix.match.subdept.tip'/>"></div>
        </div>
    </label>
    <div class="unique_div">
        <label>
            <input type="hidden" name="fdRelationConditionals[0].fdIsUnique">
            <input type="checkbox" onclick=setValue(this)><bean:message bundle='sys-organization'
                                                                        key='sysOrgMatrixRelation.fdIsUnique'/>
            <div class="item_tips lui_icon_s lui_icon_s_cue4"
                 title="<bean:message bundle='sys-organization' key='sysOrgMatrixRelation.fdIsUnique.note'/>"></div>
        </label>
    </div>
</script>
<script id="tpl_res_type" type='text/template'>
    <c:if test="${sysOrgMatrixForm.matrixType=='1'}">
        <xform:select property="fdRelationResults[0].fdType" required="true" showStatus="readOnly"
                      subject="${lfn:message('sys-organization:sysOrgMatrixRelation.fdType2') }" style="width:90%;">
            <xform:enumsDataSource enumsType="sys_org_matrix_result"/>
        </xform:select>
    </c:if>

    <c:if test="${sysOrgMatrixForm.matrixType!='1'}">
        <xform:select property="fdRelationResults[0].fdType" required="true"
                      subject="${lfn:message('sys-organization:sysOrgMatrixRelation.fdType2') }" style="width:90%;">
            <xform:enumsDataSource enumsType="sys_org_matrix_result"/>
        </xform:select>
    </c:if>

    <input type="hidden" name="fdRelationResults[0].fdId">
</script>
<script id="tpl_con_value" type='text/template'>
    <c:if test="${sysOrgMatrixForm.matrixType=='1'}">
        <xform:text property="fdRelationConditionals[0].fdName" showStatus="readOnly" style="width:93%;border:none;"
                    subject="${lfn:message('sys-organization:sysOrgMatrixRelation.fdFieldName1') }" required="true"
                    validators="maxLength(200)"/>
    </c:if>
    <c:if test="${sysOrgMatrixForm.matrixType!='1'}">
        <xform:text property="fdRelationConditionals[0].fdName" style="width:93%"
                    subject="${lfn:message('sys-organization:sysOrgMatrixRelation.fdFieldName1') }" required="true"
                    validators="maxLength(200)"/>
    </c:if>
</script>
<script id="tpl_res_value" type='text/template'>
    <c:if test="${sysOrgMatrixForm.matrixType=='1'}">
        <xform:text property="fdRelationResults[0].fdName" showStatus="readOnly" style="width:93%;border:none;"
                    subject="${lfn:message('sys-organization:sysOrgMatrixRelation.fdFieldName2') }" required="true"
                    validators="maxLength(200)"/>
    </c:if>
    <c:if test="${sysOrgMatrixForm.matrixType!='1'}">
        <xform:text property="fdRelationResults[0].fdName" style="width:93%"
                    subject="${lfn:message('sys-organization:sysOrgMatrixRelation.fdFieldName2') }" required="true"
                    validators="maxLength(200)"/>
    </c:if>
</script>
<script id="tpl_con_opt" type='text/template'>
    <c:if test="${sysOrgMatrixForm.matrixType!='1'}">
		<span class="lui_matrix_opt_bar">
         <a class="lui_text_primary lui_matrix_link icon_left" href="javascript:;" onclick="leftCon(this);" name="left"
            title="${lfn:message('sys-organization:sysOrgMatrixRelation.move.left')}"><i
                 class="sysOrgMatriTipsText">${lfn:message('sys-organization:sysOrgMatrixRelation.move.left')}</i></a>
         <a class="lui_text_primary lui_matrix_link icon_del" href="javascript:;" onclick="delCon(this);" name="del"
            title="${lfn:message('button.delete')}"><i
                 class="sysOrgMatriTipsText">${lfn:message('button.delete')}</i></a>
		 <a class="lui_text_primary lui_matrix_link icon_right" href="javascript:;" onclick="rightCon(this);"
            name="right" title="${lfn:message('sys-organization:sysOrgMatrixRelation.move.right')}"><i
                 class="sysOrgMatriTipsText">${lfn:message('sys-organization:sysOrgMatrixRelation.move.right')}</i></a>
    	</span>
    </c:if>

</script>
<script id="tpl_res_opt" type='text/template'>
    <c:if test="${sysOrgMatrixForm.matrixType!='1'}">
		<span class="lui_matrix_opt_bar">
        	<a class="lui_text_primary lui_matrix_link icon_left" href="javascript:;" onclick="leftRes(this);"
               name="left" title="${lfn:message('sys-organization:sysOrgMatrixRelation.move.left')}"><i
                    class="sysOrgMatriTipsText">${lfn:message('sys-organization:sysOrgMatrixRelation.move.left')}</i></a>
        	<a class="lui_text_primary lui_matrix_link icon_del" href="javascript:;" onclick="delRes(this);" name="del"
               title="${lfn:message('button.delete')}"><i
                    class="sysOrgMatriTipsText">${lfn:message('button.delete')}</i></a>
			<a class="lui_text_primary lui_matrix_link icon_right" href="javascript:;" onclick="rightRes(this);"
               name="right" title="${lfn:message('sys-organization:sysOrgMatrixRelation.move.right')}"><i
                    class="sysOrgMatriTipsText">${lfn:message('sys-organization:sysOrgMatrixRelation.move.right')}</i></a>
   		</span>
    </c:if>

</script>

<script language="JavaScript">
    // 字段检测
    window.fieldCheck = function() {
        Com_OpenWindow('<c:url value="/sys/organization/sys_org_matrix_relation/sysOrgMatrixRelation.do?method=check&matrixId=${sysOrgMatrixForm.fdId}"/>');
    }
</script>

<!-- 固定列的表格脚本 -->
<script type="text/javascript" src="${LUI_ContextPath}/sys/organization/resource/js/martrixEditField.js"></script>
