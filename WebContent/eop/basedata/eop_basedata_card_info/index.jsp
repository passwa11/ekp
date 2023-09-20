<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdCardNumber" ref="criterion.sys.docSubject" title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdCardNumber')}" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCardInfo" property="fdAcctNbr" expand="true" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCardInfo" property="fdHolder" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCardInfo" property="fdHolderChiName" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCardInfo" property="fdHolderEngName" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCardInfo" property="fdActivationCode" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCardInfo" property="fdCirculationFlag" />
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdIsAvailable')}" key="fdIsAvailable">
                    <list:box-select>
                        <list:item-select  cfg-defaultValue="true">
                            <ui:source type="Static">
                                [{text:'${ lfn:message('message.yes')}', value:'true'},
                                {text:'${ lfn:message('message.no')}',value:'false'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCardInfo" property="fdActivationDate" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">
                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fdCardNumber" text="${lfn:message('eop-basedata:eopBasedataCardInfo.fdCardNumber')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="9" id="btn">
                        	<kmss:auth requestURL="/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1" />
                            </kmss:auth>
                            <fssc:ifModuleExists path="/fssc/ccard/">
                                <ui:button text="${lfn:message('eop-basedata:button.syncCardInfo')}" onclick="syncCardInfo()" order="2" />
                            </fssc:ifModuleExists>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do?method=enable">
								<ui:button text="${lfn:message('eop-basedata:button.enable')}" onclick="enable()" order="2" />
							</kmss:auth>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do?method=disable">
								<ui:button text="${lfn:message('eop-basedata:button.disable')}" onclick="disable()" order="3" />
							</kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do?method=deleteall">
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
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdCorNum;fdActNum;fdAcctNbr;fdCardNumber;fdHolder.name;fdHolderEngName;fdActivationDate;docCreateTime;docCreator.name;fdIsAvailable.name;operations" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCardInfo',
                templateName: '',
                basePath: '/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do',
                canDelete: '${canDelete}',
                mode: '',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
            seajs.use(['lui/dialog','lui/topic'],function(dialog,topic){
                window.syncCardInfo = function(){
                    var load = dialog.loading();
                    $.ajax({
                        url:'${LUI_ContextPath}/fssc/ccard/fssc_ccard_card_info/fsscCcardCardInfo.do?method=syncCardInfo',
                        data:{},
                        dataType:'json',
                        type:'POST',
                        success:function(rtn){
                            dialog.result(rtn);
                            topic.publish("list.refresh");
                            load.hide();
                        },
                        error:function(){
                            dialog.failure('操作失败');
                            load.hide();
                        }
                    });
                }
            });
        </script>
        <c:import url="/eop/basedata/resource/jsp/eopBasedataImport_include.jsp">
        </c:import>
    </template:replace>
</template:include>
