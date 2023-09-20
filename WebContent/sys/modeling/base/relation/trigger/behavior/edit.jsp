<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.edit">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/relation/trigger/behavior/css/behavior.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>

        <style>
            #top {
                display: none !important;
            }

            .fdMsgBox {
                /*background-color: #EDF2FD;*/
                padding: 10px;
                margin-top: 10px;
            }

            .fdMsgBox span {
                display: inline-block;
                padding: 2px 5px;

                margin-left: 10px;
                border-radius: 5px;
            }

            .fdMsgBox span[dataMsgType='add'] {
                border: 1px solid #0aa908;
                color: #0aa908;
            }

            .fdMsgBox span[dataMsgType='remove'] {
                border: 1px solid #d67c1c;
                color: #d67c1c;
            }

            .view_flow_creator .model-mask-panel-table-show {
                margin-left: 0;
            }
            .sqlError{
                clear: both;
                margin: 5px 0;
                padding: 5px;
                background-color: #fff5d8;
                border: 1px solid #e0a385;
            }
        </style>
        <script type="text/javascript">
            var formInitData = {};
            var messageInfo = {};

            var initData = {
                contextPath: '${LUI_ContextPath}',
            };

            Com_IncludeFile("plugin.js");
            Com_IncludeFile("validation.js");

        </script>
    </template:replace>
    <template:replace name="content">

        <html:form action="/sys/modeling/base/sysModelingBehavior.do">
            <div class="model-mask-panel medium" style="padding-bottom: 72px">
                <div>
                    <div class="model-mask-panel-table">
                        <ul id="relationEditContainer">
                            <li class="model-table-item" mdlng-rltn-mrk="region" mdlng-bhvr-data="basic">
                                <div class="model-table-left">
                                    <i></i>
                                    <p>${lfn:message('sys-modeling-base:behavior.baseinfo')}</p>
                                </div>
                                <div class="model-table-right">
                                    <table class="tb_simple modeling_form_table " mdlng-rltn-mrk="regionTable">
                                        <tbody>
                                        <tr>
                                            <td class="td_normal_title"
                                                width="15%">${lfn:message('sys-modeling-base:sysModelingBehavior.fdName')}</td>
                                            <td width="85%">
                                                    <%-- 触发名称--%>
                                                <div id="_xform_fdName" _xform_type="text">
                                                    <xform:text property="fdName" showStatus="edit" style="width:94%;"
                                                                validators="required" required="true"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="td_normal_title"
                                                width="15%">${lfn:message('sys-modeling-base:sysModelingBehavior.fdType')}</td>
                                            <td width="85%">
                                                    <%-- 动作类型--%>
                                                <div id="_xform_fdType" _xform_type="checkbox">
                                                    <xform:radio property="fdType"
                                                                 value="${sysModelingBehaviorForm.fdType}"
                                                                 showStatus="edit">
                                                        <xform:enumsDataSource enumsType="sys_modeling_behavior_type"/>
                                                    </xform:radio>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr id="fdUpdateType_tr">
                                            <td class="td_normal_title" width="15%">
                                                ${lfn:message('sys-modeling-base:sysModelingBehavior.fdUpdateType')}
                                            </td>
                                            <td width="85%">
                                                    <%-- 动作类型--%>
                                                <div id="_xform_fdUpdateType" _xform_type="radio">
                                                    <xform:radio property="fdUpdateType"
                                                                 value="${sysModelingBehaviorForm.fdUpdateType}"
                                                                 showStatus="edit">
                                                        <xform:simpleDataSource value="0">${lfn:message('sys-modeling-base:behavior.update.only')}</xform:simpleDataSource>
                                                        <xform:simpleDataSource value="1">${lfn:message('sys-modeling-base:behavior.add.or.update')}</xform:simpleDataSource>
                                                    </xform:radio>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr id="modelTarget_tr"
                                            modeling-validation="modelTargetId;${lfn:message('sys-modeling-base:sysModelingBehavior.modelTarget')};required;name:modelTargetId;id:_xform_modelTargetId">
                                            <td class="td_normal_title" width="15%">
                                                    ${lfn:message('sys-modeling-base:sysModelingBehavior.modelTarget')}
                                            </td>
                                            <td width="85%">
                                                    <%-- 关联表单--%>
                                                <div id="_xform_modelTargetId" _xform_type="text">
                                                    <input type="hidden" name="modelTargetId"
                                                           value="${sysModelingBehaviorForm.modelTargetId}"/>
                                                    <div onclick="selectModel()"
                                                         class="model-mask-panel-table-show">

                                                        <p class="modelTargetNameBox">${sysModelingBehaviorForm.modelTargetName}</p>
                                                    </div>
                                                        <%--                                                    <span class="highLight" onclick="selectModel()">选择</span>--%>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr id="fdRunType_tr">
                                            <td class="td_normal_title" width="15%">
                                                 ${lfn:message('sys-modeling-base:behavior.data.execution.order')}
                                            </td>
                                            <td width="85%">
                                                    <%-- 动作类型--%>
                                                <div id="_xform_fdRunType" _xform_type="radio">
                                                    <xform:radio property="fdRunType"
                                                                 value="${sysModelingBehaviorForm.fdRunType}"
                                                                 showStatus="edit">
                                                        <xform:simpleDataSource value="1">${lfn:message('sys-modeling-base:behavior.sequential.execution')}</xform:simpleDataSource>
                                                        <xform:simpleDataSource value="0">${lfn:message('sys-modeling-base:behavior.synchronous.execution')}</xform:simpleDataSource>
                                                    </xform:radio>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                            <li class="model-table-item" mdlng-rltn-mrk="region" mdlng-bhvr-data="precfg">
                                <div class="model-table-left">
                                    <i></i>
                                    <p>${lfn:message('sys-modeling-base:behavior.trigger.premise')}</p>
                                </div>
                                <div class="model-table-right behavior_view_precfg">
                                        <%--                                    <table class="tb_simple modeling_form_table " mdlng-rltn-mrk="regionTable">--%>
                                        <%--                                        <tbody>--%>

                                        <%--                                        </tbody>--%>
                                        <%--                                    </table>--%>
                                </div>
                            </li>
                            <li class="model-table-item" mdlng-rltn-mrk="region" mdlng-bhvr-data="cfg">
                                <div class="model-table-left">
                                    <i></i>
                                    <p>${lfn:message('sys-modeling-base:behavior.trigger.information')}</p>
                                </div>
                                <div class="model-table-right behavior_view">
                                        <%--                                    <table class="tb_simple modeling_form_table " mdlng-rltn-mrk="regionTable">--%>
                                        <%--                                        <tbody>--%>

                                        <%--                                        </tbody>--%>
                                        <%--                                    </table>--%>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="toolbar-bottom">
                    <ui:button styleClass="lui_toolbar_btn_gray" text="${ lfn:message('button.close') }" order="5"
                               onclick="Com_CloseWindow();"/>
                    <c:choose>
                        <c:when test="${ sysModelingBehaviorForm.method_GET == 'edit' }">
                            <ui:button text="${ lfn:message('button.update') }"
                                       onclick="dosubmit( 'update');"/>
                        </c:when>
                        <c:when test="${ sysModelingBehaviorForm.method_GET == 'add' }">
                            <ui:button text="${ lfn:message('button.save') }"
                                       onclick="dosubmit( 'save');"/>
                        </c:when>
                    </c:choose>
                </div>
            </div>


            <%--            ==============================================================================--%>
            <!-- 用来设置一些标签生成的HTML模板结构(初始化之后会被删除)，相当于tmpl -->
            <div class="tmp_html" style="display:none">
                <table class="notify_tmp_html tb_simple modeling_form_table">
                    <tr>
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.type.of.use')}</td>
                        <td width="85%">
                            <div id="_xform_fdUserType" _xform_type="checkbox">
                                <xform:radio property="fdUserType" showStatus="edit" value="1">
                                    <xform:simpleDataSource value="1">${lfn:message('sys-modeling-base:behavior.scene.message')}</xform:simpleDataSource>
                                    <xform:simpleDataSource value="2">${lfn:message('sys-modeling-base:behavior.trigger.message')}</xform:simpleDataSource>
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.notification.type')}</td>
                        <td width="85%">
                            <div id="_xform_fdNotifyType" _xform_type="checkbox">
                                <xform:radio property="fdNotifyType" showStatus="edit" value="1">
                                    <xform:enumsDataSource enumsType="sys_modeling_notify_type"/>
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    <tr class="fdMsgKeyTr">
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.to-do.mark')}</td>
                        <td width="85%">
                            <input type="text" name="fdMsgKey" class="inputsgl" value="${sysModelingBehaviorForm.fdId}">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-modeling-base:sysModelingBehavior.fdNotifyWay')}</td>
                        <td width="85%">
                                <%-- 通知方式--%>
                            <div id="_xform_fdNotifyWay" _xform_type="radio">
                                <xform:checkbox property="fdNotifyWay" showStatus="edit"
                                                value="${sysModelingBehaviorForm.fdNotifyWay}">
                                    <xform:enumsDataSource enumsType="sys_modeling_notify_way"/>
                                </xform:checkbox>
                            </div>
                        </td>
                    </tr>

                    <tr class="view_notify_target_org">
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.target.population')}</td>
                        <td>
                            <div id="_xform_targetElementsType" _xform_type="radio">
                                <xform:radio property="targetElementsType" showStatus="edit" value="3">
                                    <xform:simpleDataSource value="4">${lfn:message('sys-modeling-base:modelingAppListview.enum.formula')}</xform:simpleDataSource>
                                    <xform:simpleDataSource value="3">${lfn:message('sys-modeling-base:behavior.directly.specify')}</xform:simpleDataSource>
                                </xform:radio>
                            </div>

                            <div class="view_notify_target"></div>
                        </td>
                    </tr>
                    <tr class="view_notify_msg">
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.message.content')}</td>
                        <td>
                            <div id="_xform_fdActionMsg" _xform_type="text"
                                 class="model-mask-panel-table-show highLight">
                                <input name="msgFormula_name" class="textEllipsis inputsgl"
                                       style="width:85%;ime-mode:disabled"
                                       readonly/>
                                <xform:text property="msgFormula" showStatus="noShow"/>
                            </div>
                            <div></div>
                        </td>
                    </tr>
                    <tr class="view_notify_link">
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.message.link')}</td>
                        <td>
                            <div id="_xform_notifyLinkType" _xform_type="radio">
                                <xform:radio property="notifyLinkType" showStatus="edit" required="true" value="0">
                                    <xform:simpleDataSource value="0">${lfn:message('sys-modeling-base:table.modelingAppView')}</xform:simpleDataSource>
                                    <xform:simpleDataSource value="1">${lfn:message('sys-modeling-base:behavior.edit.view')}</xform:simpleDataSource>
                                    <xform:simpleDataSource value="2">${lfn:message('sys-modeling-base:behavior.other.URL')}</xform:simpleDataSource>
                                </xform:radio>
                            </div>
                            <div id="_xform_notifyLinkView" _xform_type="text"
                                 class="model-mask-panel-table-show highLight">
                                <input name="link_view_type" type="hidden" value=""/>
                                <input name="link_view_name" style="width:35%;;ime-mode:disabled" value="${lfn:message('sys-modeling-base:relation.default.view')}"
                                       readonly/>
                                <xform:text property="link_view_id" value="_def" showStatus="noShow"/>
                            </div>
                            <div id="_xform_notifyLinkUrl" _xform_type="text" style="display: none">
                                <xform:text property="notifyLinkUrl" showStatus="edit" style="width:96%;"/>
                            </div>
                        </td>
                    </tr>
                </table>
                <!-- 流程单选(新建/更新视图使用) -->
                <div class="flow_radio_tmp_html">
                    <div class="view_flow_creator_radio" _xform_type="radio" style="margin-left: 0">
                        <label class="lui-lbpm-radio">
                            <input type="radio" name="flowCreatorType" checked="" value="1" >
                            <span class="radio-label">${lfn:message('sys-modeling-base:behavior.drafter')}</span>
                        </label>&nbsp;
                        <label class="lui-lbpm-radio">
                            <input type="radio" name="flowCreatorType" value="2" >
                            <span class="radio-label">${lfn:message('sys-modeling-base:behavior.current.person')}</span>
                        </label>&nbsp;
                        <label class="lui-lbpm-radio">
                            <input type="radio" name="flowCreatorType" value="3" >
                            <span class="radio-label">${lfn:message('sys-modeling-base:behavior.directly.specify')}</span></label>&nbsp;
                        <label class="lui-lbpm-radio">
                            <input type="radio" name="flowCreatorType" value="4" >
                            <span class="radio-label">${lfn:message('sys-modeling-base:modelingAppListview.enum.formula')}</span>
                        </label>&nbsp;
                        <span class="txtstrong">*</span>
                    </div>
                    <div class="view_flow_creator"></div>
                </div>
                <div class="no_flow_radio_tmp_html">
                    <div class="view_no_flow_creator_radio" _xform_type="radio" style="margin-left: 0">
                        <div class="view_no_flow_creator_radio" _xform_type="radio" style="margin-left: 0">
                            <label class="lui-lbpm-radio">
                                <input type="radio" name="noFlowCreatorType" checked="" value="3">
                                <span class="radio-label">${lfn:message('sys-modeling-base:behavior.directly.specify')}</span>
                            </label>&nbsp;
                            <label class="lui-lbpm-radio">
                                <input type="radio" name="noFlowCreatorType" value="4">
                                <span class="radio-label">${lfn:message('sys-modeling-base:modelingAppListview.enum.formula')}</span>
                            </label>&nbsp;
                            <span class="txtstrong">*</span>
                        </div>
                    </div>
                    <div class="view_no_flow_creator"></div>
                </div>
                <table class="pre_model_tmp_html">
                    <tr>
                    <td class="td_normal_title">
                            ${lfn:message('sys-modeling-base:sysModelingBehavior.modelPre')}
                    </td>
                        <td>
                        <%-- 前置表单--%>
                    <div class="pre_model_content">
                        <input type="hidden" name="modelPreId"
                               value="${sysModelingBehaviorForm.modelPreId}"/>
                        <div onclick=""
                             class="modelPreNameDiv model-mask-panel-table-show">
                            <p class="modelPreNameBox">${sysModelingBehaviorForm.modelPreName}</p>
                        </div>
                    </div>
                        </td>
                    </tr>
                </table>
                <!-- 前置表单查询条件模板 -->
                <table class="pre_model_where_tmp_html">
                    <tr class="pre_model_where">
                        <td class="td_normal_title" width="15%"></td>
                        <td width="85%">
                            <div style="margin-left:15px;margin-top: 5px;" class="preModelWhereTypediv">
                                <label class="PreModelWhereTypeinput1"><input type="radio" value="0" name="fdPreModelWhereType" checked="checked"/>满足所有条件（默认）</label>
                                <label class="PreModelWhereTypeinput2"><input type="radio" value="1" name="fdPreModelWhereType" />满足任一条件</label>
                                <label class="PreModelWhereTypeinput4" style="margin-left:10px;"><input type="radio" value="3" name="fdPreModelWhereType" />无查询条件</label>
                            </div>
                            <div class="model-mask-panel-table-base view_field_pre_model_where_div">
                                <table class="tb_normal field_table view_field_pre_model_where_table" width="100%">
                                    <thead>
                                    <tr>
                                        <td width="35%">字段名</td>
                                        <td width="10%">运算符</td>
                                        <td width="15%">值类型</td>
                                        <td width="30%">值</td>
                                        <td width="10%">
                                            操作
                                        </td>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="model-mask-panel-table-create table_opera" style="margin-left: 0px">
                                    <div>新增</div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
                 <!-- 触发前置查询条件模板 -->
                <table class="pre_where_tmp_html">
                	<tr class="main_where">
                		<td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.trigger.conditions')}</td>
                		<td width="85%"><span style="color:#999999;margin-left:15px;">${lfn:message('sys-modeling-base:behavior.form.meet.condtion.trigger')}</span></td>
                	</tr>
                    <tr class="main_where">
                        <td class="td_normal_title" width="15%"></td>
                        <td width="85%">
	                         <div style="margin-left:15px;margin-top: 5px;" class="preWhereTypediv">
								<label class="PreWhereTypeinput1"><input type="radio" value="0" name="fdPreWhereType" checked="checked"/>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}</label>
								<label class="PreWhereTypeinput2"><input type="radio" value="1" name="fdPreWhereType" />${lfn:message('sys-modeling-base:relation.meet.any.conditions')}</label>
								<!-- <label class="PreWhereTypeinput3" style="margin-left:10px;"><input type="radio" value="2" name="fdPreWhereType" />查询所有数据</label> -->
								<label class="PreWhereTypeinput4" ><input type="radio" value="3" name="fdPreWhereType" />${lfn:message('sys-modeling-base:behavior.no.query.conditions')}</label>
							</div>
                            <div class="model-mask-panel-table-base view_field_pre_where_div">
                                <table class="tb_normal field_table view_field_pre_where_table" width="100%">
                                    <thead>
                                    <tr>
                                        <td width="30%">${lfn:message('sys-modeling-base:behavior.field.name')}</td>
                                        <td width="10%">${lfn:message('sys-modeling-base:modelingAppViewincpara.fdOperator')}</td>
                                        <td width="15%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                        <td width="30%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
                                        <td width="15%">
                                            ${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}
                                        </td>
                                    </tr>
                                        <%--<span class="table_opera">添加</span>--%>
                                    </thead>
                                </table>
                                <div class="model-mask-panel-table-create table_opera" style="margin-left: 0px">
                                    <div> ${lfn:message('sys-modeling-base:button.add')}</div>
                                </div>
                            </div>
                            <div class="model-mask-panel-table-base">
                                <table class="tb_normal field_table view_fdId_where_table" width="100%">
                                    <thead>
                                    <tr>
                                        <td width="35%">fdId</td>
                                        <td width="10%">${lfn:message('sys-modeling-base:modelingAppViewincpara.fdOperator')}</td>
                                        <td width="15%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                        <td width="30%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
                                        <td width="10%">

                                        </td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr class="view_fdId_tr">
                                        <td><select class="where_field" value="fdId" readonly="readonly">
                                            <option value="fdId" data-property-type="String">fdId</option>
                                        </select></td>
                                        <td><select class="where_operator" value="=" readonly="readonly">
                                            <option value="=">${lfn:message('sys-modeling-base:modelingAppListview.enum.equal')}</option>
                                        </select></td>
                                        <td><select class="where_style">
                                            <option value="1">${lfn:message('sys-modeling-base:modelingAppListview.enum.fix')}</option>
                                            <option value="2">${lfn:message('sys-modeling-base:modelingAppListview.enum.dynamic')}</option>
                                            <option value="4">${lfn:message('sys-modeling-base:modelingAppListview.enum.formula')}</option>
                                        </select></td>
                                        <td class="view_fdId_input_td">
                                            <input type="text" name="view_fdId_input" class="inputsgl where_value"
                                                   style="width:150px"></td>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table >

                <!-- 明细表查询模板 -->
                <table class="detail_query_tmp_html">
                    <tr class="detail_query">
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.detail.data.filtering')}</td>
                        <td width="85%"><span style="color:#999999;margin-left:15px;">${lfn:message('sys-modeling-base:behavior.current.detail.data.filtering')}</span></td>
                    </tr>
                    <tr class="detail_query">
                        <td class="td_normal_title" width="15%"></td>
                        <td width="85%">
                            <div class="model-mask-panel-table-base view_field_detail_query_div">
                                <table class="tb_normal field_table view_field_detail_query_table" width="100%">
                                    <thead>
                                    <tr>
                                        <td width="25%">${lfn:message('sys-modeling-base:behavior.detail.table')}</td>
                                        <td width="20%">${lfn:message('sys-modeling-base:behavior.condition')}</td>
                                        <td width="40%">${lfn:message('sys-modeling-base:behavior.query.conditions')}</td>
                                        <td width="15%">
                                                ${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}
                                        </td>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="model-mask-panel-table-create table_opera" style="margin-left: 0px">
                                    <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
                
                <!-- 查询条件模板 -->
                <table class="where_main_tmp_html">
                    <tr>
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:sysModelingBehavior.fdWhereBlock')}</td>
                        <td width="85%">
                            <div style="margin-left:15px;margin-top: 5px;" class="WhereCodeDiv">
                                <label class="WhereCodeinput1"><input type="radio" value="0" name="fdSqlWhereType" checked="checked"/>${lfn:message('sys-modeling-base:behavior.configuration.mode')}</label>
                                <label class="WhereCodeinput2" style="margin-left:10px;"><input type="radio" value="1" name="fdSqlWhereType" />${lfn:message('sys-modeling-base:behavior.code.mode')}</label>
                                <div class="modeling-viewcover-tip"><span>
                                    ${lfn:message('sys-modeling-base:behavior.code.mode.viewcover.tip1')}<br/>
                                            ${lfn:message('sys-modeling-base:behavior.code.mode.viewcover.tip2')}<br>
                                            ${lfn:message('sys-modeling-base:behavior.code.mode.viewcover.tip3')}<br>
                                            ${lfn:message('sys-modeling-base:behavior.code.mode.viewcover.tip4')}
                                </span></div>
                            </div>
                        </td>
                    </tr>
                </table>

                <table class="where_tmp_html">
                    <tr class="main_where where_sql_0">
                        <td class="td_normal_title" width="15%">&nbsp;</td>
                        <td width="85%">
	                         <div style="margin-left:15px;margin-top: 5px;" class="WhereTypediv">
								<label class="WhereTypeinput1"><input type="radio" value="0" name="fdWhereType" <c:if test="${empty sysModelingBehaviorForm.fdWhereType or sysModelingBehaviorForm.fdWhereType eq '0'}">checked</c:if> />${lfn:message('sys-modeling-base:relation.meet.all.conditions')}</label>
								<label class="WhereTypeinput2"><input type="radio" value="1" name="fdWhereType" <c:if test="${sysModelingBehaviorForm.fdWhereType eq '1'}">checked</c:if> />${lfn:message('sys-modeling-base:relation.meet.any.conditions')}</label>
								<label class="WhereTypeinput3" style="margin-left:10px;"><input type="radio" value="2" name="fdWhereType" <c:if test="${sysModelingBehaviorForm.fdWhereType eq '2'}">checked</c:if>/>${lfn:message('sys-modeling-base:relation.query.all.data')}</label>
								<label class="WhereTypeinput4" style="margin-left:10px;"><input type="radio" value="3" name="fdWhereType" <c:if test="${sysModelingBehaviorForm.fdWhereType eq '3'}">checked</c:if>/>${lfn:message('sys-modeling-base:behavior.no.query.conditions')}</label>
							</div>
                            <div class="model-mask-panel-table-base view_field_where_div">
                                <table class="tb_normal field_table view_field_where_table" width="100%">
                                    <thead>
                                    <tr>
                                        <td width="30%">${lfn:message('sys-modeling-base:behavior.field.name')}</td>
                                        <td width="10%">${lfn:message('sys-modeling-base:modelingAppViewincpara.fdOperator')}</td>
                                        <td width="15%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                        <td width="30%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
                                        <td width="15%">
                                                ${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}
                                        </td>
                                    </tr>
                                        <%--<span class="table_opera">添加</span>--%>
                                    </thead>
                                </table>
                                <div class="model-mask-panel-table-create table_opera" style="margin-left: 0px">
                                    <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                </div>
                            </div>
                            <div class="model-mask-panel-table-base">
                                <table class="tb_normal field_table view_fdId_where_table" width="100%">
                                    <thead>
                                    <tr>
                                        <td width="35%">fdId</td>
                                        <td width="10%">${lfn:message('sys-modeling-base:modelingAppViewincpara.fdOperator')}</td>
                                        <td width="15%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                        <td width="30%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
                                        <td width="10%">

                                        </td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr class="view_fdId_tr">
                                        <td><select class="where_field" value="fdId" readonly="readonly">
                                            <option value="fdId" data-property-type="String">fdId</option>
                                        </select></td>
                                        <td><select class="where_operator" value="=" readonly="readonly">
                                        <option value="=">${lfn:message('sys-modeling-base:modelingAppListview.enum.equal')}</option>
                                        </select></td>
                                        <td><select class="where_style">
                                            <option value="1">${lfn:message('sys-modeling-base:modelingAppListview.enum.fix')}</option>
                                            <option value="2">${lfn:message('sys-modeling-base:modelingAppListview.enum.dynamic')}</option>
                                            <option value="4">${lfn:message('sys-modeling-base:modelingAppListview.enum.formula')}</option>
                                        </select></td>
                                        <td class="view_fdId_input_td">
                                            <input type="text" name="view_fdId_input" class="inputsgl where_value"
                                                   style="width:150px"></td>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>

                <table class="sql_where_tmp_html">
                    <tr class="main_where where_sql_1">
                        <td class="td_normal_title" width="15%">&nbsp;</td>
                        <td width="85%">
                            <div class="model-mask-panel-table-base">
                                <textarea id="sqlWhereArea" name="sqlWhereArea" style="width:97%;height:80px;margin: 0" onmouseover="checkedSql();" onchange="checkedSql();"></textarea>
<%--                                <xform:textarea property="sqlWhereArea" showStatus="edit" subject="代码模式SQL" validators="checkedSql" style="width:97%;height:80px;margin: 0" />--%>
                                <span class="txtstrong">*</span>
                            </div>
                            <div class="model-mask-panel-table-base sqlError" style="display: none" name="sqlError">
                                <div class="lui_icon_s lui_icon_s_icon_validator"></div>
                                ${lfn:message('sys-modeling-base:behavior.code.mode.SQL.notEmpty')}
                            </div>
                            <div class="model-mask-panel-table-base view_field_where_sql_div">
                                <table class="tb_normal field_table view_field_where_sql_table" width="100%">
                                    <thead>
                                    <tr>
                                        <td width="30%">${lfn:message('sys-modeling-base:behavior.variable.alias')}</td>
                                        <td width="15%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                        <td width="30%">${lfn:message('sys-modeling-base:modelingAppViewincpara.fdValue')}</td>
                                        <td width="15%">
                                                ${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}
                                        </td>
                                    </tr>
                                        <%--<span class="table_opera">添加</span>--%>
                                    </thead>
                                </table>
                                <div class="model-mask-panel-table-create table_sql_opera" style="margin-left: 0px">
                                    <div>    ${lfn:message('sys-modeling-base:button.add')}</div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>

                <!-- 前置查询模板 -->
                <table class="pre_query_tmp_html">
                    <tr class="pre_query">
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:modeling.behavior.preQuery')}</td>
                        <td width="85%">
                            <div class="model-mask-panel-table-base view_field_pre_query_div">
                                <table class="tb_normal field_table view_field_pre_query_table" width="100%">
                                    <thead>
                                    <tr>
                                        <td width="25%">${lfn:message('sys-modeling-base:sysModelingBehavior.modelTarget')}</td>
                                        <td width="20%">${lfn:message('sys-modeling-base:modeling.behavior.nickName')}</td>
                                        <td width="40%">${lfn:message('sys-modeling-base:modeling.behavior.preQuery.returnValue')}</td>
                                        <td width="15%">
                                                ${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}
                                        </td>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="model-mask-panel-table-create table_opera" style="margin-left: 0px">
                                    <div>新增</div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
                <!-- 目标字段 -->
                <table class="target_tmp_html">
                    <tr class="main_target">
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.target.field')}</td>
                        <td width="85%">
                            <div class="model-mask-panel-table-base">
                                <table class="tb_normal field_table view_field_target_table" width="100%">
                                    <thead>
                                    <tr>
                                        <td width="40%">${lfn:message('sys-modeling-base:behavior.field.name')}</td>
                                        <td width="20%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                        <td width="40%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
                    <%-- 主表数据or主表数据--%>
                <table class="detail_rul_tmp_html">
                    <tr class="type">
                        <td class="td_normal_title" id = "type" width="15%">${lfn:message('sys-modeling-base:behavior.type')}</td>
                        <td width="85%">
                            <div class="model-mask-panel-table-base">
                                <div class="detailOrMain" _xform_type="radio" style="margin-left: 0">
                                    <label class="lui-lbpm-radio" detailOrMain_label="1">
                                        <input type="radio" name="detailOrMain" value="1" checked subject="类型"><span
                                            class="radio-label">${lfn:message('sys-modeling-base:enums.relation_source_type.0')}</span>
                                    </label>
                                    <label class="lui-lbpm-radio" detailOrMain_label="2">
                                        <input type="radio" name="detailOrMain" value="2" subject="类型"><span
                                            class="radio-label">${lfn:message('sys-modeling-base:enums.relation_source_type.1')}</span>
                                    </label>
                                    <label class="lui-lbpm-radio" detailOrMain_label="3">
                                        <input type="radio" name="detailOrMain" value="3" subject="类型"><span
                                            class="radio-label">${lfn:message('sys-modeling-base:behavior.main.and.detailed.data')} </span>
                                    </label>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="detailChecked_Tr">
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:sysModelingRelation.fdTargetDetail')}</td>
                        <td width="85%">
                            <div id="detailChecked" _xform_type="checkbox">
                            </div>
                        </td>
                    </tr>
                </table>

                <table class="detail_tmp_html">
                    <tr class="detail_where">
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.detailed.query.conditions')}</td>
                        <td width="85%">
                        	<div style="margin-left:15px;margin-top: 5px;" class="detailWhereTypediv">
								<label class="detailWhereTypeinput1"><input type="radio" value="0" name="fdDetailWhereType" checked />${lfn:message('sys-modeling-base:relation.meet.all.conditions')}</label>
								<label class="detailWhereTypeinput2"><input type="radio" value="1" name="fdDetailWhereType"  />${lfn:message('sys-modeling-base:relation.meet.any.conditions')}</label>
								<label class="detailWhereTypeinput3" style="margin-left:10px;"><input type="radio" value="2" name="fdDetailWhereType" />${lfn:message('sys-modeling-base:relation.query.all.data')}</label>
								<label class="detailWhereTypeinput4" style="margin-left:10px;"><input type="radio" value="3" name="fdDetailWhereType" />${lfn:message('sys-modeling-base:behavior.no.query.conditions')}</label>
							</div>
                            <div class="model-mask-panel-table-base view_field_detail_where_div">
                                <table class="tb_normal field_table view_field_detail_where_table"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <td width="30%">${lfn:message('sys-modeling-base:behavior.field.name')}</td>
                                        <td width="10%">${lfn:message('sys-modeling-base:modelingAppViewincpara.fdOperator')}</td>
                                        <td width="15%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                        <td width="30%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
                                        <td width="15%">
                                                ${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}
                                        </td>
                                    </tr>
                                    </thead>
                                </table>
                                <div class="model-mask-panel-table-create table_opera"
                                     style="margin-left: 0px">
                                    <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="detail_target">
                        <td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:behavior.detailed.target.Field')}</td>
                        <td width="85%">
                            <div class="model-mask-panel-table-base">
                                <table class="tb_normal field_table view_field_detail_target_table"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <td width="40%">${lfn:message('sys-modeling-base:behavior.field.name')}</td>
                                        <td width="20%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                        <td width="40%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>

            </div>
            <html:hidden property="fdId"/>
            <html:hidden property="fdAction"/>
            <html:hidden property="modelMainId"/>
            <html:hidden property="method_GET"/>
            <html:hidden property="fdPreQueryModelIds"/>
        </html:form>

        <script>
            seajs.use(["lui/dialog"], function (dialog) {
                window.targetInfo_load = dialog.loading();
            });

            var validation = $KMSSValidation();
            Com_IncludeFile("localValidate.js", Com_Parameter.ContextPath
                + 'sys/modeling/base/resources/js/', 'js', true);
            validation.addValidator("checkedSql","${lfn:message('sys-modeling-base:behavior.code.mode.SQL.notEmpty')}",function (v,e,o){
                return false;
            });

            function checkedSql(){
                if(!_checkedSql()){
                    $(".sqlError").show();
                }else{
                    $(".sqlError").hide();
                }
            }

            function _checkedSql(){
                var result = false;
                $(".WhereCodeDiv").each(function (i, n) {
                    if(!$(this).is(":hidden")){
                        var type = $(this).find(":checked").val();
                        if(type != "0"){
                            var sqlStr = $(this).closest("table").find("#sqlWhereArea").val();
                            if(sqlStr){
                                result = true;
                            }
                        }else{
                            result = true;
                        }
                    }
                })
                return result

            }
            seajs.use(["lui/dialog", "lui/topic"], function (dialog, topic) {

                window.selectModel = function () {
                    dialog.iframe("/sys/modeling/base/relation/import/model_select.jsp?appId=${sysModelingBehaviorForm.fdApplicationId}", "${lfn:message('sys-modeling-base:behavior.select.form')}",
                        function (value) {
                            if (value) {
                                $(".modelTargetNameBox").html(value.fdName);
                                $("[name='modelTargetId']").val(value.fdId);
                                var fdName = $(".modelTargetNameBox").text();
                                initTargetInfo(value.fdId,fdName);
                            }
                        }, {
                            width: 1010,
                            height: 600
                        });
                };

                //初始化模块
                window.initTargetInfo = function (modelId,modelName) {
                    var url = "${LUI_ContextPath}/sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelId;
                    $.ajax({
                        url: url,
                        type: "get",
                        async: false,
                        success: function (data, status) {
                            if (data) {
                                topic.channel("modelingBehavior").publish("soureData.load", {
                                    "data": JSON.parse(data),
                                    "modelId": modelId
                                });
                            }
                        }
                    });
                }
                window.dosubmit=function (method) {
                    var fdAction = {};
                    fdAction = behaviorInstance.getKeyData();
                    var behaviorValidate = behaviorInstance.validators();
                    if(!behaviorValidate){
                        return;
                    }
                    $("[name='fdAction']").val(JSON.stringify(fdAction));
                    var  validate = localValidate();
                    console.debug("validate::",validate,"\nfdAction::", fdAction)
                    var fdType = $("input[name='fdType']:checked").val();
                    var msgFormula = $("input[name='msgFormula']").val();
                    var msgFormula_name = $("input[name='msgFormula_name']").val();
                    var notifydetailChecked = $("input[name='notifydetailChecked']:checked").val();
                    if (fdType =="0" && msgFormula && msgFormula_name.indexOf("$明细表")!=-1){
                        if (notifydetailChecked && msgFormula.indexOf(notifydetailChecked) == -1){
                            dialog.alert("${lfn:message('sys-modeling-base:behavior.re-select.message.content')}");
                            return;
                        }
                    }
                    var notifyTargetName = $("input[name='notifyTarget_name']").val();
                    if (!notifyTargetName && fdType =="0"){
                        dialog.alert("${lfn:message('sys-modeling-base:behavior.select.target.group')}");
                        return;
                    }
                    if(!_checkedSql()){
                        checkedSql();
                        return;
                    }

                    //#150854 代码模式下，sql语句和入参配置未做任何检查验证
                    var sqlWhere = fdAction.sqlWhere;

                    if(sqlWhere){
                        var sqlWhereArr = [];
                        while(sqlWhere.indexOf("=:") > -1){
                            var startIndex = sqlWhere.indexOf("=:") + 2;
                            console.log("startIndex",startIndex)
                            while(sqlWhere[startIndex] == " " && startIndex < sqlWhere.length){
                                startIndex++;
                            }
                            if(startIndex == sqlWhere.length){
                                break;
                            }
                            var endIndex = startIndex;
                            while(sqlWhere[endIndex] != " " && endIndex < sqlWhere.length){
                                endIndex ++;
                            }
                            var param = sqlWhere.substr(startIndex,endIndex-startIndex);
                            sqlWhereArr.push(param);
                            sqlWhere = sqlWhere.substr(endIndex);
                        }
                        for(var i = 0;i < sqlWhereArr.length;i++){
                            for(var j = 0;j < sqlWhereArr.length;j++){
                                if(sqlWhereArr[i] == sqlWhereArr[j] && i != j){
                                    dialog.alert("${lfn:message('sys-modeling-base:behavior.code.mode.SQL.tip1')}");
                                    return;
                                }
                            }
                        }
                        var sqlWhereParams = fdAction.sqlWhereParams;
                        if(sqlWhereParams.length != sqlWhereArr.length){
                            dialog.alert("${lfn:message('sys-modeling-base:behavior.code.mode.SQL.tip2')}");
                            return;
                        }
                        for(var i = 0;i < sqlWhereArr.length;i++){
                            var isTrue = false;
                            for(var j = 0;j < sqlWhereParams.length;j++){
                                var text = sqlWhereParams[j].name.text;
                                if(sqlWhereArr[i].trim() == text){
                                    isTrue = true;
                                }
                            }
                            if(!isTrue){
                                dialog.alert("${lfn:message('sys-modeling-base:behavior.code.mode.SQL.tip3')}");
                                return;
                            }
                        }
                    }
                   if (validate && checkMainWhere(fdAction) && checkHasMultiDetail(fdAction)){
                        Com_Submit(document.sysModelingBehaviorForm, method);
                   }
                }
                
                window.checkMainWhere = function(action){
                	var behaviorType = $("[name='fdType']:checked").val();				//触发动作类型
                	var createType= $("[name='createdetailOrMain']:checked").val();		//新建的查询类型（明细表、主+明细）
                	var updateType= $("[name='updatedetailOrMain']:checked").val();		//更新的查询类型（明细表、主+明细）
                	var createOrUpdateType= "";
                	var whereType = $("[name='fdWhereType']:checked").val();
                	behaviorType === "3"?createOrUpdateType = updateType:(behaviorType === "1"?createOrUpdateType = createType : "");
                	if(createOrUpdateType === "2" || createOrUpdateType === "3"){
                		//（明细表、主+明细）
                		if(action.where.length <= 0 && whereType != "2" && whereType != "3"){
                			//dialog.alert("触发信息中,类型选择明细表数据或主表+明细表数据时，需要配置查询条件，请配置查询条件");
                			var $tipContainer = {};
                			if(behaviorType === "1"){
                				$tipContainer = $(".behavior_create_view");
                			}else if(behaviorType === "3"){
                				$tipContainer = $(".behavior_update_view");
                			}
                			$tipContainer.find(".mainModelWhereTip").remove();
                			$tipContainer.find(".view_field_where_div").after("<div style='color:#ff0000;margin-left:16px;' class='mainModelWhereTip'>请配置查询条件</div>");
                            return false;
                		}
                	}
                	return true;
                }

                window.checkHasMultiDetail = function(action){
                    var modelPreId = $("[name=modelPreId]").val();
                    if(modelPreId){
                        var where = action.where;
                        var tips = "${lfn:message("sys-modeling-base:sysModelingBehavior.multi.detail.tips")}";
                        var preModelTips = '${lfn:message("sys-modeling-base:sysModelingBehavior.multi.preModel.tips")}';
                        if(checkMultiDetail(where)){
                            tips = "${lfn:message("sys-modeling-base:sysModelingBehavior.multi.detail.Deduplication")}" + tips;
                            dialog.alert(tips);
                            return false;
                        }
                        if(checkMultiPreModel(where,modelPreId)){
                            preModelTips = "${lfn:message("sys-modeling-base:sysModelingBehavior.multi.detail.Deduplication")}" + preModelTips;
                            dialog.alert(preModelTips);
                            return false;
                        }
                        var target = action.target;
                        if(checkMultiDetail(target)){
                            tips = "${lfn:message("sys-modeling-base:sysModelingBehavior.multi.detail.target")}" + tips;
                            dialog.alert(tips);
                            return false;
                        }
                        if(checkMultiPreModel(target,modelPreId)){
                            preModelTips = "${lfn:message("sys-modeling-base:sysModelingBehavior.multi.detail.target")}" + preModelTips;
                            dialog.alert(preModelTips);
                            return false;
                        }
                        var detail = action.detail;
                        if(detail && detail.length>0){
                            for (var i = 0; i < detail.length; i++) {
                                var where = detail[i].where;
                                if(checkMultiDetail(where)){
                                    tips = detail[i].name + "${lfn:message("sys-modeling-base:sysModelingBehavior.multi.detail.query")}" + tips;
                                    dialog.alert(tips);
                                    return false;
                                }
                                if(checkMultiPreModel(where,modelPreId)){
                                    preModelTips = detail[i].name + "${lfn:message("sys-modeling-base:sysModelingBehavior.multi.detail.query")}" + preModelTips;
                                    dialog.alert(preModelTips);
                                    return false;
                                }
                                var target = detail[i].target;
                                if(checkMultiDetail(target)){
                                    tips = detail[i].name + "${lfn:message("sys-modeling-base:sysModelingBehavior.multi.detail.detail.target")}" + tips;
                                    dialog.alert(tips);
                                    return false;
                                }
                                if(checkMultiPreModel(target,modelPreId)){
                                    preModelTips = detail[i].name + "${lfn:message("sys-modeling-base:sysModelingBehavior.multi.detail.detail.target")}" + preModelTips;
                                    dialog.alert(preModelTips);
                                    return false;
                                }
                            }
                        }
                    }
                    return true;
                }

                //校验是否存在多个明细表
                window.checkMultiDetail=function(where){
                    if(where && where.length>0){
                        var detailKey = null;
                        for(var i=0;i<where.length;i++){
                            var type = where[i].type;
                            //值类型为公式定义
                            if(type.value == "4"){
                                var expression = where[i].expression;
                                var expValue = expression.value;
                                if(expValue.indexOf(".")>-1){
                                    var detailPropKey = getDetailPropKeys(expValue);
                                    if(detailPropKey){
                                        if(detailKey){
                                            if(detailKey != detailPropKey){
                                                //如果存在多个明细则直接返回true
                                                return true;
                                            }
                                        }else{
                                            detailKey = detailPropKey;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return false;
                }
                //存在不是当前前置表单的字段
                window.checkMultiPreModel=function(where,modelPreId){
                    if(where && where.length>0){
                        var detailKey = modelPreId;
                        for(var i=0;i<where.length;i++){
                            var type = where[i].type;
                            //值类型为公式定义
                            if(type.value == "4"){
                                var expression = where[i].expression;
                                var expValue = expression.value;
                                if(expValue.indexOf("#")>-1){
                                    var detailPropKey = getPreModelKeys(expValue);
                                    if(detailPropKey){
                                        if(detailKey){
                                            if(detailKey != detailPropKey){
                                                //如果存在多个前置表单的字段则直接返回true
                                                return true;
                                            }
                                        }else{
                                            detailKey = detailPropKey;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return false;
                }

                //获取明细表key
                window.getDetailPropKeys = function(expr) {
                    var startIdx = -1;
                    if(!expr){
                        return null;
                    }
                    for (var i = 0; i < expr.length; i++) {
                        var c = expr[i];
                        if (startIdx != -1) {
                            if (c == '$') {
                                var fieldName = expr.substring(startIdx + 1, i);
                                var reg = /[\u4e00-\u9fa5]/g;
                                var chinese = fieldName.match(reg);
                                if (chinese && chinese.length>0) {
                                    startIdx = -1;
                                    continue;
                                }
                                if (fieldName.indexOf('.') > -1) {
                                    var index = fieldName.indexOf(".");
                                    var detailKey = fieldName.substring(0, index);
                                    return detailKey;
                                } else {
                                    startIdx = -1;
                                }
                            }
                        } else {
                            if (c == '$') {
                                startIdx = i;
                            }
                        }
                    }
                    return null;
                }
                //获取前置表单Id
                window.getPreModelKeys = function(expr) {
                    var startIdx = -1;
                    if(!expr){
                        return null;
                    }
                    for (var i = 0; i < expr.length; i++) {
                        var c = expr[i];
                        if (startIdx != -1) {
                            if (c == '$') {
                                var fieldName = expr.substring(startIdx + 1, i);
                                var reg = /[\u4e00-\u9fa5]/g;
                                var chinese = fieldName.match(reg);
                                if (chinese && chinese.length>0) {
                                    startIdx = -1;
                                    continue;
                                }
                                if (fieldName.indexOf('#') > -1) {
                                    var index = fieldName.indexOf("#");
                                    var preKey = fieldName.substring(0, index);
                                    return preKey;
                                } else {
                                    startIdx = -1;
                                }
                            }
                        } else {
                            if (c == '$') {
                                startIdx = i;
                            }
                        }
                    }
                    return null;
                }
            });



            function localValidate() {
                var lv = _localValidation();
                if (validation.validate() && lv) {
                    return true;
                }
                return false;
            }

            function init() {
                seajs.use(["sys/modeling/base/relation/trigger/behavior/js/behavior",
                        "lui/dialog", "lui/topic","sys/modeling/base/formlog/res/mark/behaviorFMMark"]
                    , function (behavior, dialog, topic,behaviorFMMark) {
                	//本表单信息
                    var modelMainId = "${sysModelingBehaviorForm.modelMainId}";
                    var currentData = {};
                    if (modelMainId) {
                   	 var url = "${LUI_ContextPath}/sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelMainId;
                        $.ajax({
                            url: url,
                            type: "get",
                            async: false,
                            success: function (data, status) {
                                if (data) {
                                    if(typeof(data) === 'object'){
                                        currentData = data;
                                    }
                                    else{
                                        currentData = JSON.parse(data);
                                    }
                                }
                            }
                        });
                    }
                    window.behaviorInstance = new behavior.Behavior({
                        "$behaviorTypeEle": $("input[type=radio][name='fdType']"),
                        "$fdUpdateTypeTypeEle": $("input[type=radio][name='fdUpdateType']"),
                        "viewContainer": $(".behavior_view"),
                        "preViewContainer": $(".behavior_view_precfg"),
                        "$tmpEle": $(".tmp_html"),
                        "xformId": "${xformId}",
                        "sourceData":${sourceData},
                        "modeMainId": "${sysModelingBehaviorForm.modelMainId}",
                        "currentData": currentData,
                        "appId":"${sysModelingBehaviorForm.fdApplicationId}"
                    });
                    behaviorInstance.startup();

                    var targetId = "${sysModelingBehaviorForm.modelTargetId}";
                    if (targetId) {
                        initTargetInfo(targetId);
                    }

                    var fdAction = ${sysModelingBehaviorForm.fdAction};
                    if (!$.isEmptyObject(fdAction)) {
                        behaviorInstance.initByStoreData(fdAction);
                    }
                    window.fmmark = new behaviorFMMark.BehaviorFMMark({fdId:"${sysModelingBehaviorForm.fdId}"});
                    fmmark.startup();
                    if(window.targetInfo_load){
                        window.targetInfo_load.hide();
                    }
                });
            }
            Com_AddEventListener(window, "load", init);
        </script>
    </template:replace>
</template:include>