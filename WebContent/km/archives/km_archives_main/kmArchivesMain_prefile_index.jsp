<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple" spa="true">
    <template:replace name="body">
   	 <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
    <script>
    	seajs.use(['theme!list']);
    </script>
            <!-- 筛选 -->
            <list:criteria id="archivesCriteria">
            	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('km-archives:kmArchivesMain.docSubject')}"></list:cri-ref>
                <list:cri-ref ref="criterion.sys.simpleCategory" key="docTemplate" multi="false" title="${lfn:message('km-archives:kmArchivesMain.docTemplate')}" expand="false">
                    <list:varParams modelName="com.landray.kmss.km.archives.model.KmArchivesCategory" />
                </list:cri-ref>
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docNumber" />
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="fdFileDate" />
				<!-- 属性库自定义筛选属性 -->
				<list:cri-property
						modelName="com.landray.kmss.km.archives.model.KmArchivesCategory" 
						cfg-spa="true" cfg-cri="docTemplate"/>
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
	                            <list:sort property="fdFileDate" text="${lfn:message('km-archives:kmArchivesMain.fdFileDate')}" group="sort.list" value="down" />
	                            <list:sort property="fdLibrary" text="${lfn:message('km-archives:kmArchivesMain.fdLibrary')}" group="sort.list" />
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
	                        <kmss:authShow roles="ROLE_KMARCHIVES_PREFILE_MANAGER">
	                        	<ui:button text="${lfn:message('km-archives:kmArchivesFileTemplate.logOper.fileDocAll')}" onclick="confirmFiles()" />
	                        	 <ui:button text="${lfn:message('km-archives:button.cateChange')}" onclick="changeCategorys()"  id="btnChangeCategory" />
	                            <ui:button text="${lfn:message('button.deleteall')}" onclick="delPreFileDoc()" />
							</kmss:authShow>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:'/km/archives/km_archives_main/kmArchivesMain.do?method=listPreFile'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable layout="sys.ui.listview.columntable"  url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.archives.model.KmArchivesMain"  isDefault="false" 
                			rowHref="/km/archives/km_archives_main/kmArchivesMain.do?method=viewPreFile&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props=""></list:col-auto>
                </list:colTable>
            </list:listview>
            <!-- 翻页 -->
           <list:paging />
        <%@ include file="/km/archives/km_archives_main/kmArchivesMain_index_script.jsp" %>
    </template:replace>
</template:include>