<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdModelId" ref="criterion.sys.docSubject" title="${lfn:message('third-ding:thirdDingFinstance.fdModelId')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingFinstance" property="fdTemplateId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingFinstance" property="fdProcessCode" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingFinstance" property="fdInstanceId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingFinstance" property="fdDingStatus" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingFinstance" property="fdEkpStatus" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingFinstance" property="fdStartFlow" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">
				<!-- 全选 -->
				<div class="lui_list_operation_order_btn">
					<list:selectall></list:selectall>
				</div>
				<!-- 分割线 -->
				<div class="lui_list_operation_line"></div>
				<!-- 排序 -->
				<div class="lui_list_operation_sort_btn">
					<div class="lui_list_operation_order_text">
						${ lfn:message('list.orderType') }：
					</div>
					<div class="lui_list_operation_sort_toolbar">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingFinstance.fdDingStatus" text="${lfn:message('third-ding:thirdDingFinstance.fdDingStatus')}" group="sort.list" />
                            <list:sort property="thirdDingFinstance.fdEkpStatus" text="${lfn:message('third-ding:thirdDingFinstance.fdEkpStatus')}" group="sort.list" />
                            <list:sort property="thirdDingFinstance.fdStartFlow" text="${lfn:message('third-ding:thirdDingFinstance.fdStartFlow')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <!-- 分页 -->
				<div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/ding/third_ding_finstance/thirdDingFinstance.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/third/ding/third_ding_finstance/thirdDingFinstance.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/ding/third_ding_finstance/thirdDingFinstance.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/third_ding_finstance/thirdDingFinstance.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdTemplateId;fdModelId;fdEkpStatus;fdStartFlow.name;fdProcessCode;fdInstanceId;fdDingStatus" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingFinstance',
                templateName: '',
                basePath: '/third/ding/third_ding_finstance/thirdDingFinstance.do',
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
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>