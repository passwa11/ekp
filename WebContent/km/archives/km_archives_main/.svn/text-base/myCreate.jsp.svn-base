<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		 <!-- 筛选 -->
        <list:criteria id="archivesCriteria1" channel="createChannel">
        	<list:tab-criterion title="" key="docStatus"> 
		  		 <list:box-select>
		  		 	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false">
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
            <%-- <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docStatus" /> --%>
            <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docNumber" />
            <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docCreator" />
            <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="fdFileDate" />
			<list:cri-property
						modelName="com.landray.kmss.km.archives.model.KmArchivesCategory" 
						cfg-spa="true" cfg-cri="docTemplate"/>
        </list:criteria>
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall channel="createChannel"></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sortgroup>
							<list:sort channel="createChannel" property="docCreateTime" text="${lfn:message('km-archives:kmArchivesMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" channel="createChannel"> 		
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="BtntoolbarCreate">
						<kmss:authShow roles="ROLE_KMARCHIVES_CREATE">
                            <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                        </kmss:authShow>
                        <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=importArchives">
                            <ui:button id="importBtn" text="${lfn:message('km-archives:kmArchivesMain.importArchives')}" onclick="importArchives()" order="2" />
                        </kmss:auth>
						<kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall">
							<ui:button id="delBtnCreate" text="${lfn:message('button.deleteall')}" order="3" onclick="delAllDoc()"></ui:button>
						</kmss:auth>
						<kmss:authShow roles="ROLE_KMARCHIVES_TRANSPORT_EXPORT">
							<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain')" order="4" ></ui:button>
						</kmss:authShow>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listviewCreate" channel="createChannel">
			<ui:source type="AjaxJson">
					{url:'/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=create'}
			</ui:source>
			<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.archives.model.KmArchivesMain" isDefault="true" layout="sys.ui.listview.columntable" rowHref="/km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto></list:col-auto> 
			</list:colTable>
		</list:listview> 
		<br>
	 	<list:paging channel="createChannel"></list:paging>
	 	<script type="text/javascript">
	 	var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.archives.model.KmArchivesMain";
	 	seajs.use(['lui/jquery', 'lui/dialog','lui/topic'], function($, dialog , topic) {
	 		//var cateId = null;
	 		window.importArchives = function() {
	 			dialog.simpleCategoryForNewFile('com.landray.kmss.km.archives.model.KmArchivesCategory', '/km/archives/km_archives_main/kmArchivesMain_upload.jsp?docTemplate=!{id}',
		    			false,null,null);
            	/* if(cateId == null || cateId == '') {
            		dialog.alert("${lfn:message('km-archives:please.choose.category')}");
            	}else {
            		 Com_OpenWindow("${LUI_ContextPath}/km/archives/km_archives_main/kmArchivesMain_upload.jsp?docTemplate="+cateId);
            	} */
        	};
        	/* topic.channel('createChannel').subscribe('criteria.spa.changed',function(evt){
        		cateId = null;
        		for(var i=0;i<evt['criterions'].length;i++){
					//获取分类id和类型
	             	if(evt['criterions'][i].key=="docTemplate"){
		                cateId= evt['criterions'][i].value[0];
	             	}
				}
        	}); */
		 	//删除
	 		window.delAllDoc = function(){
				var values = [];
				$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
				if(values.length==0){
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				var delUrl = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall"/>';
				dialog.iframe('/sys/edition/import/doc_delete_iframe.jsp?fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain&fdType=POST',
						"<bean:message key='ui.dialog.operation.title' bundle='sys-ui'/>",
						function (value){
	                        // 回调方法
							if(value) {
								delCallback(value);
							}
						},
						{width:400,height:160,params:{url:delUrl,data:$.param({"List_Selected":values},true)}}
				);
			};
			window.delCallback = function(data){
				topic.publish("list.refresh");
				dialog.result(data);
			};
			
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
			
		 	window.addDoc = function(){
		    	dialog.simpleCategoryForNewFile('com.landray.kmss.km.archives.model.KmArchivesCategory', '/km/archives/km_archives_main/kmArchivesMain.do?method=add&fdTemplateId=!{id}&.fdTemplate=!{id}&i.docTemplate=!{id}',
		    			false,null,null);
		    }
	 	});
	 	</script>
	</template:replace>
	</template:include>
