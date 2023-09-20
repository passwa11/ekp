<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.tic.core.common.util.TicCommonUtil" %>
    <template:include ref="config.list">
        <template:replace name="content">
            <div style="margin:5px 10px;">
                <!-- 筛选 -->
                <list:criteria id="criteria1">
                	<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('tic-core-common:ticCoreCommon.transFuncName')}">
			        </list:cri-ref>
			        <list:cri-ref ref="criterion.sys.simpleCategory" key="fdCategory" multi="false" title="${lfn:message('tic-core-common:ticCoreCommon.categoryNavigation')}" expand="true">
					     <list:varParams modelName="${JsParam.modelName}"/>
					</list:cri-ref>
                    <list:cri-criterion title="${lfn:message('tic-core-common:ticCoreTransSett.fdFunction')}" key="fdFunction" multi="false">
                        <list:box-select>
                            <list:item-select>
                                <ui:source type="Static">
                                    <%=
                                       TicCommonUtil.buildCriteria( "ticCoreFuncBaseService", "fdName", request.getParameter("fdEnviromentId")!=null?("fdFuncType!=8 and fdAppType="+request.getParameter("fdAppType")+" and fdEnviromentId="+ "'"+request.getParameter("fdEnviromentId")+ "'"):("fdFuncType!=8 and fdAppType="+request.getParameter("fdAppType")), null) %>
                                </ui:source>
                            </list:item-select>
                        </list:box-select>
             </list:cri-criterion>

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
		                         <list:sort property="ticCoreTransSett.docCreateTime" text="${lfn:message('tic-core-common:ticCoreFuncBase.docCreateTime')}" group="sort.list" />
		                     </ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top">
						</list:paging>
					</div>

                    <div style="float:right">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar count="3">

                                <kmss:auth requestURL="/tic/core/tic_core_trans_sett/ticCoreTransSett.do?method=add">
                                    <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                                </kmss:auth>
                                <kmss:auth requestURL="/tic/core/common/tic_core_trans_sett/ticCoreTransSett.do?method=deleteall">
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
                        {url:appendQueryParameter('/tic/core/common/tic_core_trans_sett/ticCoreTransSett.do?method=data&fdAppType=${JsParam.fdAppType}&fdEnviromentId=${JsParam.fdEnviromentId}')}
                    </ui:source>
                    <!-- 列表视图 -->
                    <list:colTable isDefault="false" rowHref="/tic/core/common/tic_core_trans_sett/ticCoreTransSett.do?method=edit&fdId=!{fdId}&fdAppType=${JsParam.fdAppType}&fdEnviromentId=${JsParam.fdEnviromentId}&ldingFlag=${JsParam.ldingFlag}&showTop=no" name="columntable">
                        <list:col-checkbox />
                        <list:col-serial/>
                        <list:col-auto props="" /></list:colTable>
                </list:listview>
                <!-- 翻页 -->
                <list:paging />
            </div>
            <script>
    		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) {
    			// 监听新建更新等成功后刷新
    			topic.subscribe('successReloadPage', function() {
    				setTimeout(function(){
    						topic.publish('list.refresh');
    				}, 100);
    			});
    		});
                var listOption = {
                    contextPath: '${LUI_ContextPath}',
                    modelName: 'com.landray.kmss.tic.core.common.model.TicCoreTransSett',
                    templateName: '${JsParam.modelName}',
                    basePath: '/tic/core/common/tic_core_trans_sett/ticCoreTransSett.do',
                    canDelete: '${canDelete}',
                    cateId: "${param.categoryId}",
                    fdAppType:"${param.fdAppType}",
                    fdEnviromentId:"${param.fdEnviromentId}",
                    ldingFlag:"${param.ldingFlag}",
                    mode: '',
                    customOpts: {

                        ____fork__: 0
                    },
                    lang: {
                        noSelect: '${lfn:message("page.noSelect")}',
                        comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                    }

                };
                Com_IncludeFile("list.js", "${LUI_ContextPath}/tic/core/common/resource/js/", 'js', true);
            </script>
        </template:replace>
    </template:include>