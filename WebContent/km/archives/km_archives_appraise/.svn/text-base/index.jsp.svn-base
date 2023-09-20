<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
    <script>
    	seajs.use(['theme!list']);
    </script>
            <!-- 筛选 -->
            <list:criteria id="appraiseCriteria">
            	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('km-archives:kmArchivesAppraise.docSubject')}"></list:cri-ref>
            	<list:cri-ref key="archivesNumber" ref="criterion.sys.string" title="${lfn:message('km-archives:kmArchivesAppraise.fdArchivesNumber')}"></list:cri-ref>
                <list:cri-criterion title="${ lfn:message('km-archives:kmArchivesBorrow.docStatus')}" key="docStatus"> 
					<list:box-select>
						<list:item-select cfg-if="param['docStatus'] !='00'">
							<ui:source type="Static">
								[{text:'${ lfn:message('status.draft')}', value:'10'},
								{text:'${ lfn:message('status.examine')}',value:'20'},
								{text:'${ lfn:message('status.refuse')}',value:'11'},
								{text:'${ lfn:message('status.discard')}',value:'00'},
								{text:'${ lfn:message('status.publish')}',value:'30'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesAppraise" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesAppraise" property="docCreateTime" />
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
	                            <list:sort property="docCreateTime" text="${lfn:message('km-archives:kmArchivesAppraise.docCreateTime')}" group="sort.list" value="down" />
	                            <list:sort property="fdOriginalDate" text="${lfn:message('km-archives:kmArchivesAppraise.fdOriginalDate')}" group="sort.list" />
	                            <list:sort property="fdAfterAppraiseDate" text="${lfn:message('km-archives:kmArchivesAppraise.fdAfterAppraiseDate')}" group="sort.list" />
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
                        	<kmss:auth requestURL="/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
							 <kmss:auth requestURL="/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                                 <ui:button text="${lfn:message('button.deleteall')}" onclick="appraiseDeleteAll()" order="4" id="btnDelete" />
                            </kmss:auth>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=data&q.j_path=/operation/myAppraiseDetails&q.mydoc=${JsParam.mydoc }')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.archives.model.KmArchivesAppraise" layout="sys.ui.listview.columntable" isDefault="false" rowHref="/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto/>
                </list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        <form name="printForm" method="post" action="${LUI_ContextPath }/km/archives/km_archives_appraise/kmArchivesAppraise.do" target="_blank" style="display:none;">
        	<input type="hidden" name="selectIds"/>
        </form>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.km.archives.model.KmArchivesAppraise',
                templateName: '',
                basePath: '/km/archives/km_archives_appraise/kmArchivesAppraise.do',
                canDelete: '${canDelete}',
                mode: '',
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
            
            seajs.use(['lui/jquery','lui/dialog','lui/topic'],function($,dialog,topic) {
	        	// 软删除
	  	 		window.appraiseDeleteAll = function(){
	  	 			var values = [];
	  				$("input[name='List_Selected']:checked").each(function(){
	  						values.push($(this).val());
	  					});
	  				if(values.length==0){
	  					dialog.alert('<bean:message key="page.noSelect"/>');
	  					return;
	  				}
	  				var config = {
	  					url : "${LUI_ContextPath}/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=deleteall", // 删除数据的URL
	  					data : $.param({"List_Selected":values},true), // 要删除的数据
	  					modelName : "com.landray.kmss.km.archives.model.KmArchivesAppraise" // 主要是判断此文档是否有部署软删除
	  				};
	  				// 通用删除方法
	  				Com_Delete(config, delCallback);
	  			};
	  			
	  			window.delCallback = function(data){
	  				topic.publish("list.refresh");
	  				dialog.result(data);
	  			};
            });
        </script>
    </template:replace>
</template:include>