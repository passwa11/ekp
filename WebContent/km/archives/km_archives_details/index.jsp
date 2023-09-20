<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
    <script type="text/javascript">
		seajs.use(['theme!list']);	
	</script>
            <!-- 筛选 -->
            <list:criteria id="detailsCriteria">
            	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('km-archives:kmArchivesMain.docSubject')}"></list:cri-ref>
            	<list:cri-ref ref="criterion.sys.simpleCategory" key="docTemplate" multi="false" title="${lfn:message('km-archives:kmArchivesMain.docTemplate')}" expand="false">
                    <list:varParams modelName="com.landray.kmss.km.archives.model.KmArchivesCategory" />
                </list:cri-ref>
                <list:cri-criterion expand="false" title="${ lfn:message('km-archives:kmArchivesMain.fdLibrary') }" key="fdLibrary" multi="false">
                    <list:box-select>
                        <list:item-select>
                            <ui:source type="AjaxJson">
								{"url":"/km/archives/km_archives_library/kmArchivesLibrary.do?method=criteria"} 
							</ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docNumber" />
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="fdFileDate" />
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
                        	<list:sortgroup>
                        		<list:sort property="fdReturnDate" text="${lfn:message('km-archives:kmArchivesDetails.fdReturnDate')}" group="sort.list" value="down"></list:sort>
                        	</list:sortgroup>
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
							<kmss:authShow roles="ROLE_KMARCHIVES_TRANSPORT_EXPORT">
							<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain')" order="4" ></ui:button>
							</kmss:authShow>
                            <%-- <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" /> --%>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/km/archives/km_archives_details/kmArchivesDetails.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.archives.model.KmArchivesDetails" layout="sys.ui.listview.columntable" isDefault="false" rowHref="/km/archives/km_archives_details/kmArchivesDetails.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto/>
                </list:colTable> 
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        <script>
            var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.archives.model.KmArchivesBorrow;com.landray.kmss.km.archives.model.KmArchivesMain";
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.km.archives.model.KmArchivesBorrow',
                templateName: '',
                basePath: '/km/archives/km_archives_details/kmArchivesDetails.do',
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
            Com_IncludeFile("list.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
            function addBorrow() {
            	Com_OpenWindow("${LUI_ContextPath}/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add");
            }
        </script>
    </template:replace>
</template:include>