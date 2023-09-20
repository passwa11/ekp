<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
    <script>
    	seajs.use(['theme!list']);
    </script>
            <!-- 筛选 -->
            <list:criteria id="archivesCriteria">
            	<list:tab-criterion title="" key="docStatus">
			   		 <list:box-select>
			   		 	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false" cfg-required="true"  cfg-if="param['navType'] =='examine' ">
							<ui:source type="Static">
								[{text:'${ lfn:message('status.examine') }', value:'20'},
								 {text:'${ lfn:message('status.refuse') }',value:'11'}]
							</ui:source>
						</list:item-select>
			    	</list:box-select>
			    </list:tab-criterion>
            	<list:tab-criterion title="" key="docStatus"> 
			   		 <list:box-select>
			   		 	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false" cfg-if="param['docStatus'] !='00' && param['navType'] != 'examine' ">
							<ui:source type="Static">
								[{text:'${ lfn:message('status.draft') }', value:'10'},
								{text:'${ lfn:message('status.examine') }', value:'20'},
								{text:'${ lfn:message('status.refuse') }', value:'11'},
								{text:'${ lfn:message('status.publish') }',value:'30'}]
							</ui:source>
						</list:item-select>
			    	</list:box-select>
			    </list:tab-criterion>
            	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('km-archives:kmArchivesMain.docSubject')}"></list:cri-ref>
                <list:cri-ref ref="criterion.sys.simpleCategory" key="docTemplate" multi="false" title="${lfn:message('km-archives:kmArchivesMain.docTemplate')}" expand="false">
                    <list:varParams modelName="com.landray.kmss.km.archives.model.KmArchivesCategory" />
                </list:cri-ref>
                <list:cri-criterion title="${lfn:message('km-archives:kmArchivesMain.kStatus')}" key="kStatus" multi="false">
                	<list:box-select>
			   		 	<list:item-select cfg-if="criteria('docStatus')[0]=='30' && param['navType'] != 'examine' ">
							<ui:source type="Static">
								[{text:'${lfn:message("km-archives:kmArchivesMain.kStatus.library")}', value:'library'},
								{text:'${lfn:message("km-archives:kmArchivesMain.kStatus.expired")}', value:'expire'}]
							</ui:source>
						</list:item-select>
			    	</list:box-select>
                </list:cri-criterion>
                <%-- <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docStatus" /> --%>
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

                            <kmss:authShow roles="ROLE_KMARCHIVES_CREATE">
                                <ui:button cfg-if="param['docStatus'] !='00'" text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:authShow>
                            <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=batchUpdate">
                                <ui:button cfg-if="param['docStatus'] !='00'" text="${lfn:message('km-archives:kmArchivesMain.batchUpdate')}" onclick="batchUpdate()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=importArchives">
                                <ui:button cfg-if="param['docStatus'] !='00'" id="importBtn" text="${lfn:message('km-archives:kmArchivesMain.importArchives')}" onclick="importArchives()" order="2" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc()" order="4" id="btnDelete" />
                            <%-- 修改权限 --%>
							<c:import url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
								<c:param name="spa" value="true" />
							</c:import>
                        	<c:import url="/km/archives/import/doc_right_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
								<c:param name="spa" value="true"/>
							</c:import>
							<c:import url="/km/archives/import/doc_cate_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
								<c:param name="docFkName" value="docTemplate" />
								<c:param name="cateModelName" value="com.landray.kmss.km.archives.model.KmArchivesCategory" />
								<c:param name="spa" value="true"/>
							</c:import>
							<kmss:authShow roles="ROLE_KMARCHIVES_TRANSPORT_EXPORT">
							<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain')" order="4" ></ui:button>
							</kmss:authShow>	
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:'/km/archives/km_archives_main/kmArchivesMain.do?method=data&categoryId=${JsParam.categoryId}'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.archives.model.KmArchivesMain" layout="sys.ui.listview.columntable" isDefault="false" rowHref="/km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto />
                </list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
       <%@ include file="/km/archives/km_archives_main/kmArchivesMain_index_script.jsp" %>
    </template:replace>
</template:include>