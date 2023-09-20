<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-loan:module.fssc.loan') }-${ lfn:message('fssc-loan:table.fsscLoanRepayment') }" />
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="fsscRepaymentPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
            <ui:content id="fsscRepaymentContent" title="${ lfn:message('fssc-loan:table.fsscLoanRepayment') }">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
            	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('fssc-loan:fsscLoanRepayment.docSubject')}" />
            	<list:cri-auto modelName="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" property="docNumber" expand="true" />
                <list:cri-criterion expand="false" title="${lfn:message('fssc-loan:lbpm.my')}" key="mydoc" multi="false">
                    <list:box-select>
                        <list:item-select>
                            <ui:source type="Static">
                                [{text:'${ lfn:message('fssc-loan:lbpm.create.my') }', value:'create'},{text:'${ lfn:message('fssc-loan:lbpm.approval.my') }',value:'approval'}, {text:'${ lfn:message('fssc-loan:lbpm.approved.my') }', value: 'approved'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" property="docStatus" />
                <list:cri-ref ref="criterion.sys.simpleCategory" key="docTemplate" multi="false" title="${lfn:message('fssc-loan:fsscLoanRepayment.docTemplate')}" expand="false">
                    <list:varParams modelName="com.landray.kmss.fssc.loan.model.FsscLoanReCategory" />
                </list:cri-ref>
                <list:cri-auto modelName="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" property="fdRepaymentPerson" />
                <list:cri-auto modelName="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" property="fdRepaymentDept" />
				<%--当前处理人--%>
		        <list:cri-ref ref="criterion.sys.postperson.availableAll"  cfg-if="param['docStatus']!='00' && param['docStatus']!='32'" key="fdCurrentHandler" multi="false" title="${lfn:message('fssc-loan:lbpm.currentHandler')}" />
		        <%--已处理人--%>
		        <list:cri-ref ref="criterion.sys.person"  key="fdAlreadyHandler" multi="false" title="${lfn:message('fssc-loan:lbpm.approvedHandler')}" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscLoanRepayment.docCreateTime" text="${lfn:message('fssc-loan:fsscLoanRepayment.docCreateTime')}" group="sort.list" value="down" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAllByAuth()" order="4" id="btnDelete" />
                            <ui:button text="${lfn:message('button.export')}" id="export" order="5" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanRepayment')" />
                            <c:import  url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
                                <c:param name="modelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
                                <c:param name="authReaderNoteFlag" value="2" />
                            </c:import>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=data&q.j_path=repayment')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto url="/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
                    </list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </ui:content>
        </ui:tabpanel>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.loan.model.FsscLoanRepayment',
                templateName: 'com.landray.kmss.fssc.loan.model.FsscLoanReCategory',
                basePath: '/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do',
                canDelete: '${canDelete}',
                mode: 'main_scategory',
                templateService: 'fsscLoanReCategoryService',
                templateAlert: '${lfn:message("fssc-loan:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>
