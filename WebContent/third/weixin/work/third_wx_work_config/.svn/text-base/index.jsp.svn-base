<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include file="/sys/profile/resource/template/list.jsp">

<%-- <template:replace name="content">
    <template:replace name="title">
        <c:out value="${ lfn:message('third-wx:module.third.wx') }-${ lfn:message('third-wx:table.thirdWxWorkConfig') }" />
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_top" />
            <ui:menu-item text="${ lfn:message('third-wx:table.thirdWxWorkConfig') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.create">
            <ui:varParam name="title" value="${ lfn:message('third-wx:table.thirdWxWorkConfig') }" />
            <ui:varParam name="button">
                [ {"text": "","href": "javascript:void(0);","icon": "/third/weixin/third_wx_work_config/thirdWxWorkConfig.do"} ]
            </ui:varParam>
        </ui:combin>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>

                <ui:content title="${ lfn:message('list.search') }">
                    <ul class='lui_list_nav_list'>

                        <li><a href="javascript:void(0)" onclick="openQuery();LUI('criteria1').clearValue();">${ lfn:message('list.alldoc') }</a>
                        </li>
                    </ul>
                </ui:content>

                <ui:content title="${ lfn:message('list.otherOpt') }">
                    <ul class='lui_list_nav_list'>
                        <li><a href="${LUI_ContextPath}/sys/profile/index.jsp#app/ekp/third/wx" target="_blank">${ lfn:message('list.manager') }</a>
                        </li>
                    </ul>
                </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace> --%>
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->

            <!-- 操作 -->
            <div class="lui_list_operation">
				<!-- 全选 -->
				<div class="lui_list_operation_order_btn">
					<list:selectall></list:selectall>
				</div>
                <!-- 分页 -->
				<div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                        	<ui:button text="${ lfn:message('third-weixin-work:thirdWxWorkConfig.transfer') }"
								onclick="transfer();">
							</ui:button>
							<ui:button text="${ lfn:message('button.add') }" 
								onclick="Com_OpenWindow('${LUI_ContextPath}/third/weixin/third_wx_work_config/thirdWxWorkConfig.do?method=edit','_self');">
							</ui:button>
                           <%--  <kmss:auth requestURL="/third/weixin/third_wx_work_config/thirdWxWorkConfig.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" /> --%>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
	            <ui:source type="AjaxJson">
					{url:'/third/weixin/third_wx_work_config/thirdWxWorkConfig.do?method=data'}
				</ui:source>
				<list:colTable isDefault="true" 
				rowHref="/third/weixin/third_wx_work_config/thirdWxWorkConfig.do?method=edit&fdKey=!{fdKey}"
				layout="sys.ui.listview.columntable" target="_self">
					<list:col-checkbox></list:col-checkbox>	
					<list:col-serial/>
					<list:col-auto props="fdName,fdKey,operations"></list:col-auto>			
				</list:colTable>
				<ui:event topic="list.loaded">
					Dropdown.init();
				</ui:event>
			</list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
        seajs.use(['lui/dialog', 'lui/topic'], function(dialog,topic){
	        window.deleteDoc = function(id){
	 			var url = '<c:url value="/third/weixin/third_wx_work_config/thirdWxWorkConfig.do?method=delete"/>';
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
					if(value==true){
						window.del_load = dialog.loading();
						$.ajax({
							url: url,
							type: 'GET',
							data:{"fdkey":id},
							dataType: 'json',
							error: function(data){
								if(window.del_load!=null){
									window.del_load.hide(); 
								}
								dialog.result(data.responseJSON);
							},
							success: delCallback
					   });
					}
				});
	 		};
	 		window.delCallback = function(data){
				if(window.del_load!=null){
					window.del_load.hide();
					topic.publish("list.refresh");
				}
				dialog.result(data);
			};
			window.transfer = function(){
				var url = '<c:url value="/third/weixin/third_wx_work_config/thirdWxWorkConfig.do?method=transfer"/>';
				dialog.confirm('<bean:message bundle="third-weixin-work"  key="thirdWxWorkConfig.transfer.msg"/>',function(value){
					if(value==true){
						window.del_load = dialog.loading();
						$.ajax({
							url: url,
							type: 'GET',
							dataType: 'json',
							error: function(data){
								if(window.del_load!=null){
									window.del_load.hide(); 
								}
								dialog.result(data.responseJSON);
							},
							success: delCallback
					   });
					}
				});
			};
        });
        </script>
    </template:replace>
</template:include>