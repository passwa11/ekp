<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-cashier:module.fssc.cashier') }" />
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.create">
            <ui:varParam name="title" value="${ lfn:message('fssc-cashier:module.fssc.cashier') }" />
            <ui:varParam name="button">
                [ {"text": "","href": "javascript:void(0);","icon": "/fssc/cashier/fssc_cashier_payment_detail/fsscCashierPaymentDetail.do"} ]
            </ui:varParam>
        </ui:combin>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>
                <ui:content title="${ lfn:message('fssc-cashier:module.fssc.fsscCashierPayment') }">
                    <ul class='lui_list_nav_list'>
                        <li><a href="${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment/index.jsp${j_iframe}">${lfn:message('fssc-cashier:table.fsscCashierPayment')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment_detail/index.jsp${j_iframe}">${lfn:message('fssc-cashier:table.fsscCashierPaymentDetail')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/sys/profile/index.jsp#app/ekp/fssc/cashier" target="_blank">${ lfn:message('list.manager') }</a>
                        </li>
                    </ul>
                </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="fsscCashierDetailPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
            <ui:content id="fsscCashierDetailContent" title="${ lfn:message('fssc-cashier:table.fsscCashierPaymentDetail') }">
                <!-- 筛选 -->
                <list:criteria id="criteria1">
                    <list:cri-auto modelName="com.landray.kmss.fssc.cashier.model.FsscCashierPaymentDetail" property="fdModelNumber" expand="true"/>
                    <list:cri-auto modelName="com.landray.kmss.fssc.cashier.model.FsscCashierPaymentDetail" property="docNumber" expand="true"/>
                    <list:cri-criterion title="${ lfn:message('fssc-cashier:lbpm.my.doc') }" key="myflow"  expand="true">
                        <list:box-select>
                            <list:item-select  cfg-defaultValue="approval">
                                <ui:source type="Static">
                                    [{text:'${ lfn:message('list.approval')}', value:'approval'},
                                    {text:'${ lfn:message('list.approved')}',value:'approved'}]
                                </ui:source>
                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>
                    <list:cri-criterion title="${ lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPaymentType')}" key="fdPaymentType" expand="true">
                        <list:box-select>
                            <list:item-select >
                                <ui:source type="Static">
                                    [{text:'${ lfn:message('fssc-cashier:enums.fd.payment.type.1')}', value:'1'},
                                    {text:'${ lfn:message('fssc-cashier:enums.fd.payment.type.0')}',value:'0'}]
                                </ui:source>
                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>
                    <list:cri-criterion title="${ lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdStatus')}" key="fdStatus" expand="true">
                        <list:box-select>
                            <%--cfg-defaultValue="30"--%>
                            <list:item-select >
                                <ui:source type="Static">
                                    [{text:'${ lfn:message('fssc-cashier:enums.fd_status.10')}', value:'10'},
									 {text:'${ lfn:message('fssc-cashier:enums.fd_status.30')}', value:'30'},
                                     {text:'${ lfn:message('fssc-cashier:enums.fd_status.11')}', value:'11'},
                                     {text:'${ lfn:message('fssc-cashier:enums.fd_status.20')}', value:'20'}]
                                </ui:source>
                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>
					<list:cri-criterion title="${ lfn:message('fssc-cashier:fsscCashierPaymentDetail.docStatus')}" key="docStatus" expand="true">
                        <list:box-select>
                            <%--cfg-defaultValue="30"--%>
                            <list:item-select >
                                <ui:source type="Static">
                                    [{text:'${ lfn:message('status.discard')}', value:'00'},
                                    {text:'${ lfn:message('status.draft')}', value:'10'},
                                    {text:'${ lfn:message('status.refuse')}', value:'11'},
                                    {text:'${ lfn:message('status.examine')}', value:'20'},
                                    {text:'${ lfn:message('status.publish')}', value:'30'}]
                                </ui:source>
                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>



                    <!-- 公司名称 -->
                    <list:cri-criterion title="${ lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCompany')}" key="fdCompany.fdId"  expand="true"  multi="false">
                        <list:box-select>
                            <list:item-select>
                                <ui:source type="AjaxXml" >
                                    {"url":"/sys/common/dataxml.jsp?s_bean=eopBasedataCompanyService&authCurrent=true&userFlag=true"}
                                </ui:source>
                                <!-- 根据所选的公司名称，联动查询成本中心和预算科目 -->
                                <ui:event event="selectedChanged" args="evt">
                                    var vals = evt.values;
                                    if (vals.length > 0 && vals[0] != null) {
                                    var val = vals[0].value;
                                    LUI('cost-fdBasePayWayId-id').source.setUrl("/sys/common/dataxml.jsp?s_bean=eopBasedataPayWayService&type=default&model=cashier&fdCompanyId="+val+"&authCurrent=true");
                                    LUI('cost-fdBasePayWayId-id').source.resolveUrl();
                                    LUI('cost-fdBasePayWayId-id').refresh();
                                    LUI('cost-fdBasePayBankId-id').source.setUrl("/sys/common/dataxml.jsp?s_bean=eopBasedataPayBankService&fdCompanyId="+val+"&authCurrent=true");
                                    LUI('cost-fdBasePayBankId-id').source.resolveUrl();
                                    LUI('cost-fdBasePayBankId-id').refresh();
                                    }else{
                                    LUI('cost-fdBasePayBankId-id').source.setUrl("#");
                                    LUI('cost-fdBasePayBankId-id').source.resolveUrl();
                                    LUI('cost-fdBasePayBankId-id').refresh();
                                    LUI('cost-fdBasePayWayId-id').source.setUrl("#");
                                    LUI('cost-fdBasePayWayId-id').source.resolveUrl();
                                    LUI('cost-fdBasePayWayId-id').refresh();
                                    }
                                </ui:event>

                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>

                    <!-- 付款方式 fdBasePayWay -->
                    <list:cri-criterion title="${ lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBasePayWay')}"  key="fdBasePayWay.fdId" expand="true" multi="true">
                        <list:box-select>
                            <list:item-select id="cost-fdBasePayWayId-id">
                                <ui:source type="AjaxXml">
                                    {"url":"#"}
                                </ui:source>
                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>

                    <!-- 付款银行 fdBasePayBank -->
                    <list:cri-criterion title="${ lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBasePayBank')}"  key="fdBasePayBank.fdId" expand="true" multi="true">
                        <list:box-select>
                            <list:item-select id="cost-fdBasePayBankId-id">
                                <ui:source type="AjaxXml">
                                    {"url":"#"}
                                </ui:source>
                                <!-- 根据所选的 查询成本中心的子层级，若有则展现出来 -->
                                <ui:event event="selectedChanged" args="evtt">
                                    var valss = evtt.values;
                                    refreshPushButton(valss);
                                </ui:event>
                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>
                    <list:cri-auto modelName="com.landray.kmss.fssc.cashier.model.FsscCashierPaymentDetail" property="fdPaymentDate" expand="false"/>
                    <list:cri-auto modelName="com.landray.kmss.fssc.cashier.model.FsscCashierPaymentDetail" property="fdPlanPaymentDate" expand="false"/>
                </list:criteria>
                <!-- 操作 -->
                <div class="lui_list_operation">
                    <form style="display:none;" id="fsscCashierPaymentDetailForm" method="post" target="_blank" action="${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment_detail/fsscCashierPaymentDetail.do?method=exportInternetBanking">
                    </form>
                    <div style='color: #979797;float: left;padding-top:1px;'>
                            ${ lfn:message('list.orderType') }：
                    </div>
                    <div style="float:left">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            </ui:toolbar>
                        </div>
                    </div>
                    <div style="float:left;">
                        <list:paging layout="sys.ui.paging.top" />
                    </div>
                    <div style="float:right">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar count="10">
                                <%--推送銀行 --%>
                               <fssc:checkUseBank fdBank="BANK">
                                    <ui:button text="${lfn:message('fssc-cashier:button.pushToCmb')}" id="push1"  style="display:none" onclick="pushToBank('1')" order="1" />
                                    <ui:button text="${lfn:message('fssc-cashier:button.pushToBoc')}" id="push2"  style="display:none" onclick="pushToBank('2')" order="1" />
                                    <ui:button text="${lfn:message('fssc-cashier:button.pushToYonfin')}" id="push3"  style="display:none" onclick="pushToBank('3')" order="1" />
                                    <ui:button text="${lfn:message('fssc-cashier:button.pushToCbs')}" id="push4"  style="display:none" onclick="pushToBank('4')" order="1" />
                                    <ui:button text="${lfn:message('fssc-cashier:button.pushToCmbInt')}" id="push5"  style="display:none" onclick="pushToBank('5')" order="1" />
                            </fssc:checkUseBank>
                                <%--导出网银--%>
                                <ui:button text="${lfn:message('fssc-cashier:button.export.internetBanking')}" onclick="exportInternetBanking()" order="1" />
                                <%--<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />--%>
                            </ui:toolbar>
                        </div>
                    </div>
                </div>
                <ui:fixed elem=".lui_list_operation" />
                <!-- 列表 -->
                <list:listview id="listview">
                    <ui:source type="AjaxJson">
                        {url:appendQueryParameter('/fssc/cashier/fssc_cashier_payment_detail/fsscCashierPaymentDetail.do?method=data')}
                    </ui:source>
                    <!-- 列表视图 -->
                    <list:colTable isDefault="false" rowHref="/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=view&fdId=!{docMain.fdId}" name="columntable">
                        <list:col-checkbox />
                        <list:col-serial/>
                        <list:col-auto props="fdModelNumber;docStatus.name;docNumber;fdPayeeName;fdPayeeAccount;fdPayeeBankName;fdBaseCurrency.name;fdPaymentMoney;fdIsExport.name;fdBasePayBank.name;fdBasePayWay.name;fdStatus.name" /></list:colTable>
                </list:listview>
                <!-- 翻页 -->
                <list:paging />
            </ui:content>
        </ui:tabpanel>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.cashier.model.FsscCashierPaymentDetail',
                templateName: '',
                basePath: '/fssc/cashier/fssc_cashier_payment_detail/fsscCashierPaymentDetail.do',
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
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment/", 'js', true);
            Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/cashier/resource/js/", 'js', true);

        </script>
        <script>
            //导出网银
            function exportInternetBanking(){
                if($("input[name='List_Selected']:checked").length==0){
                    seajs.use(['lui/dialog'], function(dialog) {
                        dialog.alert('<bean:message key="page.noSelect"/>');
                    });
                    return;
                }
                var idArray = new Array();
                $("input[name='List_Selected']:checked").each(function(){
                    idArray.push($(this).val());
                });
                $.ajax({
                    url: "${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment_detail/fsscCashierPaymentDetail.do?method=getIsExport",
                    type : "POST",
                    dataType:"json",
                    async:false,
                    data:{"idArray":idArray.toString()},
                    success: function(data){
                        var fdIsExport = data.fdIsExport;
                        var message = data.message;
                        var confirmMessae = "${ lfn:message('fssc-cashier:confirm') }${ lfn:message('fssc-cashier:button.export.internetBanking') }?";
                        if(fdIsExport && message.length > 0){
                            confirmMessae = "${ lfn:message('fssc-cashier:button.export.internetBanking.message') }?".replace("%text%", message);
                        }
                        seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
                            dialog.confirm(confirmMessae, function(value){
                                if(value==true){
                                    var form = $("#fsscCashierPaymentDetailForm");
                                    form.empty();
                                    $("input[name='List_Selected']:checked").each(function(){
                                        var inputdataType=$('<input type="text" name="List_Selected" />');
                                        inputdataType.attr('value', $(this).val());
                                        form.append(inputdataType);
                                    });
                                    // 提交表单
                                    form.submit();
                                    setTimeout(function(){topic.publish('list.refresh');},1000);
                                }
                            });
                        });
                    }
                });
            }
            //推送到后台付款
            function pushToBank(bankType){
                if($("input[name='List_Selected']:checked").length==0){
                    seajs.use(['lui/dialog'], function(dialog) {
                        dialog.alert('<bean:message key="page.noSelect"/>');
                    });
                    return;
                }
                var idArray = new Array();
                $("input[name='List_Selected']:checked").each(function(){
                    idArray.push($(this).val());
                });
                seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
                    $.ajax({
                        url:Com_Parameter.ContextPath + 'fssc/cashier/fssc_cashier_payment_detail/fsscCashierPaymentDetail.do?method=batchPaymentCmb',
                        data:{params:idArray.join(","),'bankType':bankType},
                        async:false,
                        success:function(rtn){
                            rtn = JSON.parse(rtn);
                            if(rtn.result=='false'){
                                dialog.alert(rtn.massege);
                                return false;
                            }else{
                                dialog.alert(rtn.massege,function(){
                                    topic.publish("list.refresh");
                                });
                            }
                        }
                    });
                });
            }
            //根据银行类型来显示银行推送按钮
            function refreshPushButton  (valss){
                if(valss.length!=1){
                    $("#push1").css("display","none");
                    $("#push2").css("display","none");
                    $("#push3").css("display","none");
                    $("#push4").css("display","none");
                    $("#push5").css("display","none");
                }else{
                    $( "[id^='push']" ).css("display","none");;
                    seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
                        $.ajax({
                            url:'${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=getEopBasedataPayBankBelong',
                            data:{fdId:valss[0].value},
                            async:false,
                            success:function(rtn){
                                rtn = JSON.parse(rtn);
                                var fdPayBankBelong=rtn.fdPayBankBelong;
                                if(fdPayBankBelong!=""){
                                    $("#push"+fdPayBankBelong).css("display","block");
                                }
                            }
                        });
                    });


                }

            }
        </script>
    </template:replace>
</template:include>
