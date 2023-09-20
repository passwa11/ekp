<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.fssc.cashier.model.FsscCashierRuleConfig" property="fdIsAvailable" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscCashierRuleConfig.docAlterTime" text="${lfn:message('fssc-cashier:fsscCashierRuleConfig.docAlterTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <ui:button text="${lfn:message('fssc-cashier:button.init')}" onclick="updateInit()" order="1" />
                            <kmss:auth requestURL="/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                                 <ui:button text="${lfn:message('button.copy')}" onclick="copyDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdCashierModelConfig.name;fdCategoryName;fdIsAvailable.name" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.cashier.model.FsscCashierRuleConfig',
                templateName: '',
                basePath: '/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-cashier:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/cashier/resource/js/", 'js', true);

            //初始化
            function updateInit(){
                seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
                    $.ajax({
                        url :'${LUI_ContextPath}/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do?method=updateInit',
                        type : 'POST',
                        dataType : 'json',
                        async : false,
                        success:function(data) {
                            if (data.fdIsBoolean == "true") {
                                dialog.success("${lfn:message('return.optSuccess')}");
                            } else {
                                dialog.failure("${lfn:message('return.optFailure')}");
                            }
                            seajs.use(['lui/jquery','lui/topic'], function($, topic) {
                                topic.publish('list.refresh');
                            });
                        },
                        error: function() {
                            dialog.failure("${lfn:message('return.optFailure')}");
                        }
                    });
                });
            }
            
            //复制
            function copyDoc(){
            	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
	            	var ids = [];
	            	$("[name=List_Selected]:checked").each(function(){
	            		ids.push(this.value);
	            	});
	            	if(ids.length==0){
	            		dialog.alert(listOption.lang.noSelect);
	            		return;
	            	}
                    $.ajax({
                        url :'${LUI_ContextPath}/fssc/cashier/fssc_cashier_rule_config/fsscCashierRuleConfig.do?method=copyDoc',
                        type : 'POST',
                        dataType : 'json',
                        data:$.param({"List_Selected":ids},true),
                        async : false,
                        success:function(data) {
                            if (data.fdIsBoolean == "true") {
                                dialog.success("${lfn:message('return.optSuccess')}");
                            } else {
                                dialog.failure("${lfn:message('return.optFailure')}");
                            }
                            seajs.use(['lui/jquery','lui/topic'], function($, topic) {
                                topic.publish('list.refresh');
                            });
                        },
                        error: function() {
                            dialog.failure("${lfn:message('return.optFailure')}");
                        }
                    });
                });
            }
        </script>
    </template:replace>
</template:include>
