<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
    <template:replace name="title">
        <c:out value="${ lfn:message('third-ding:module.third.ding') }-${ lfn:message('third-ding:table.thirdDingTodoTemplate') }" />
    </template:replace>
    
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
              <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('third-ding:thirdDingTodoTemplate.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingTodoTemplate" property="docCreateTime" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingTodoTemplate" property="docAlterTime" />
                <%-- <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingTodoTemplate" property="fdModelName" /> --%>

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingTodoTemplate.fdName" text="${lfn:message('third-ding:thirdDingTodoTemplate.fdName')}" group="sort.list" />
                            <list:sort property="thirdDingTodoTemplate.docCreateTime" text="${lfn:message('third-ding:thirdDingTodoTemplate.docCreateTime')}" group="sort.list" />
                            <list:sort property="thirdDingTodoTemplate.docAlterTime" text="${lfn:message('third-ding:thirdDingTodoTemplate.docAlterTime')}" group="sort.list" />
                            <%-- <list:sort property="thirdDingTodoTemplate.fdModelName" text="${lfn:message('third-ding:thirdDingTodoTemplate.fdModelName')}" group="sort.list" /> --%>
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdModelNameText;docCreateTime;docAlterTime;docCreator.name" url="" />
                    <list:col-html style="width:60px;" title="${ lfn:message('third-ding:thirdDingLeavelog.optitle') }">		
						
						{$<a href="#" onclick="toEdit('{%row.fdId%}');" class="com_btn_link">${ lfn:message('third-ding:thirdDingTodoTemplate.edit') }</a>&nbsp;$}
						
						if(row['fdIsdefault']== 0){
							{$<a href="#" onclick="delect('{%row.fdId%}','{%row.fdName%}');" class="com_btn_link">${ lfn:message('third-ding:thirdDingTodoTemplate.delete') }</a>$}
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
                jPath: 'todo_template',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingTodoTemplate',
                templateName: '',
                basePath: '/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do',
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
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
            
            function toEdit(fdId){
            	window.open("${LUI_ContextPath}/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=edit&fdId="+fdId);
            }
            
            function delect(fdId,fdName){
            	
            	if(confirm("您确定要删除模版 "+fdName+" 吗？")){
            		$.ajax({
        				url:"${LUI_ContextPath}/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=delectModel&fdId="+fdId,
        				type:"GET",
        				async:false,
        				success:function(result){
        					if(result == "success"){
        						// 刷新数据
			                	$(".lui_paging_t_refresh").click();
        					}else{
        						alert("删除失败！");
        					}     					     					      					
        				}
        			});
            	}
            }
            
        </script>
    </template:replace>
</template:include>