<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdMerchId" ref="criterion.sys.docSubject" title="${lfn:message('third-payment:thirdPaymentMerchant.fdMerchId')}" />
                <list:cri-auto modelName="com.landray.kmss.third.payment.model.ThirdPaymentMerchant" property="fdMerchName" />
                <list:cri-auto modelName="com.landray.kmss.third.payment.model.ThirdPaymentMerchant" property="fdCorpName" />
                <list:cri-auto modelName="com.landray.kmss.third.payment.model.ThirdPaymentMerchant" property="fdMerchStatus" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdPaymentMerchant.docCreateTime" text="${lfn:message('third-payment:thirdPaymentMerchant.docCreateTime')}" group="sort.list" />
                            <list:sort property="thirdPaymentMerchant.docAlterTime" text="${lfn:message('third-payment:thirdPaymentMerchant.docAlterTime')}" group="sort.list" />
                            <list:sort property="thirdPaymentMerchant.fdMerchStatus" text="${lfn:message('third-payment:thirdPaymentMerchant.fdMerchStatus')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/payment/third_payment_merchant/thirdPaymentMerchant.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/third/payment/third_payment_merchant/thirdPaymentMerchant.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/payment/third_payment_merchant/thirdPaymentMerchant.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/payment/third_payment_merchant/thirdPaymentMerchant.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdMerchId;fdMerchType.name;fdMerchName;fdCorpName;docCreator.name;docCreateTime" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'merchant',
                modelName: 'com.landray.kmss.third.payment.model.ThirdPaymentMerchant',
                templateName: '',
                basePath: '/third/payment/third_payment_merchant/thirdPaymentMerchant.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("third-payment:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/payment/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>