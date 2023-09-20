<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('third-ding:thirdDingLeavelog.docSubject') }">
			    </list:cri-ref>
			    <list:cri-ref ref="criterion.sys.person"
					key="fdEkpUserid" multi="false"
					title="${lfn:message('third-ding:thirdDingLeavelog.docCreator') }" />    
			    <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingLeavelog" property="docAlterTime" />
				<list:cri-criterion title="${ lfn:message('km-review:kmReviewMain.docStatus') }" key="fdIstrue" > 
					<list:box-select>
						<list:item-select id="mydoc1" cfg-enable="true">
							<ui:source type="Static">
								[{text:'${ lfn:message('third-ding:thirdDingLeavelog.syncSuccess')}', value:'1'},
								{text:'${ lfn:message('third-ding:thirdDingLeavelog.syncError')}',value:'0'},
								{text:'${ lfn:message('third-ding:thirdDingLeavelog.syncCancel')}',value:'2'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>    	
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingLeavelog.docSubject" text="${lfn:message('third-ding:thirdDingLeavelog.docSubject')}" group="sort.list" value="" />
                            <list:sort property="thirdDingLeavelog.docCreateTime" text="${lfn:message('third-ding:thirdDingLeavelog.docCreateTime')}" group="sort.list" value="down" />
                            <list:sort property="thirdDingLeavelog.fdIstrue" text="${lfn:message('third-ding:thirdDingLeavelog.fdIstrue')}" group="sort.list" value="" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
							<!--
                            <kmss:auth requestURL="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                           
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
 							-->
 							<!-- 
 							<ui:button text="${lfn:message('third-ding:thirdDingLeavelog.syncAll')}" onclick="syncAll()" order="4" id="btnSyncAll" />
 							<ui:button text="${lfn:message('third-ding:thirdDingLeavelog.syncToEkpTimeManage')}" onclick="syncToEkpTimeManage()" order="4" id="btnSyncToEkpTimeManage" />
                        	-->                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=bussData')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable rowHref="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=bussView&fdId=!{fdId}" isDefault="false" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="docSubject;docCreator.name;fdUserid;fdFromTime;fdToTime;docAlterTime" url="" />
                    <list:col-html title="${ lfn:message('third-ding:thirdDingLeavelog.fdIstrue') }">
   						if(row['fdIstrue'] == '0'){
   							{$<span style="color:red;"><bean:message bundle="third-ding" key="thirdDingLeavelog.syncError"/></span>$}
	                    }else if(row['fdIstrue'] == '1'){
   							{$<span style="color:green;"><bean:message bundle="third-ding" key="thirdDingLeavelog.syncSuccess"/></span>$}
	                    }else{
	                       	{$<bean:message bundle="third-ding" key="thirdDingLeavelog.syncCancel"/>$}
	                    }
                    </list:col-html>
                    <list:col-html style="width:60px;" title="${ lfn:message('third-ding:thirdDingLeavelog.optitle') }">		
						if(row['fdIstrue'] == '0'){
							{$<a href="#" onclick="resyncBussToDing('{%row.fdId%}');" class="com_btn_link"><bean:message bundle="third-ding" key="thirdDingLeavelog.synctip"/></a>$}
						}
					</list:col-html>
                </list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingLeavelog',
                templateName: '',
                basePath: '/third/ding/third_ding_leavelog/thirdDingLeavelog.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("third-ding:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }
            };
            
            seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
            	// 重新同步到钉钉
                window.resyncBussToDing=function(fdId){
	              	var msg = '${lfn:message("third-ding:thirdDingLeavelog.confirmSync")}';
	            	var url = '<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=resyncBuss&fdId=" />'+fdId;
	
	            	dialog.confirm(msg, function(value){                		
	            		if(value == true) {
							$.ajax({
							   type: "POST",
							   url: url,
							   async:true,
							   dataType: "json",
							   success: function(data){
									dialog.alert(data.msg,function(){
										if(data.status=="1"){
											// 刷新数据
						                	$(".lui_paging_t_refresh").click();
										}
									});	
							   },
							   error: function(data){
								   dialog.alert('<bean:message bundle="third-ding" key="other.init.errormsg"/>');
							   }
							});
						}                		
	            	});
            	 }
            });
           
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>