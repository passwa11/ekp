<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp" %>
<c:if test="${JsParam.showTree eq 'true' }">
	<c:import url="/sys/profile/moduleindex.jsp?nav=/sys/search/ui/nav_search_cate_tree.jsp?fdModelName=${JsParam.modelName }&searchTitle=${JsParam.searchTitle }&j_rIframe=true">
	</c:import>
</c:if>
<c:if test="${JsParam.showTree ne 'true' }">
	<template:include ref="default.simple">
		<template:replace name="body">
		 <script type="text/javascript">
			seajs.use(['theme!module']);	
		 </script>
		  <link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/search/styles/newSearch.css" />
			<div>
				<ui:dataview>
						<ui:source type="AjaxJson">
							{"url":"/sys/search/sys_search_main/sysSearchMain.do?method=listConfig&modelName=${JsParam.modelName}"} 
						</ui:source>
						<ui:render type="Template">
							if(data.length > 0){
									{$
									<div id="searchTab" class="lui_tabpanel_navs_l">
									    <div class="lui_tabpanel_navs_r">
									        <div class="lui_tabpanel_navs_c clearfloat">
									$}
									        for(var n=0; n < data.length;n++){
									         {$
									            <div style="max-width:24.9%;" onclick="showSearchDialog('{%data[n].value%}',this);${empty varParams.onClick ? '' : varParams.onClick}"
									                class="lui_tabpanel_navs_item_l" data-lui-mark="panel.nav.frame"
									                data-lui-switch-class="lui_tabpanel_navs_item_selected">
									                <div class="lui_tabpanel_navs_item_r">
									                    <div class="lui_tabpanel_navs_item_c" data-lui-mark="panel.nav.title" title="{%strutil.encodeHTML(data[n].text)%}"><span
									                            class="lui_panel_title_main lui_tabpanel_navs_item_title">{%strutil.encodeHTML(data[n].text)%}</span></div>
									                </div>
									            </div>
									          $}
									         } 
									{$
									        </div>
									    </div>
									</div>
									
									
								$}
							}else{
								{$
									<div class="lui-search-panel-heading">			     
										    <div id="expandDiv" class="lui-search-extra-wrap">
											     <h2 class="lui-search-panel-heading-title"></h2>
												<div class="lui-search-extra-border"></div>
											</div> 
									</div>
									<div class="lui-cate-queryEmpty-wrap">
										<div class="lui-cate-queryEmpty">
											<div class="queryEmpty-icon">
											</div>
											<p class="queryEmpty-tips"><bean:message bundle="sys-search" key="search.common.empty"/></p>
											<p class="queryEmpty-suggest"><bean:message bundle="sys-search" key="search.common.empty.hint"/></p>
										</div>
									</div>
								$} 
							}
						</ui:render>
						<ui:event event="load">
							var data = this.data;
							if(data.length == 0){
								LUI('search_Iframe').erase();
								$('.lui-search-panel-heading-title').html('<bean:message bundle="sys-search" key="search.common"/>');
							}
							
							if($('#searchTab .lui_tabpanel_navs_item_l').length > 0){
								setTimeout(function(){
									$('#searchTab .lui_tabpanel_navs_item_l:first').trigger("click");
								},100);
							}
						</ui:event>
				</ui:dataview>
				<ui:dataview format="sys.ui.iframe" id="search_Iframe">
					<ui:source type="Static">
						{"src":""}
					</ui:source>
				</ui:dataview>
			</div>
	<script type="text/javascript">
		//条件查询
		seajs.use(['lui/util/str'], function(strutil) {
			window.strutil=strutil;
		});
		
		window.showSearchDialog = function(id,obj) {
			$('#searchTab .lui_tabpanel_navs_item_l').each(function(){
				$(this).removeClass("lui_tabpanel_navs_item_selected");
			})
			
			$(obj).addClass("lui_tabpanel_navs_item_selected");
			
			var title = '';
			
			if('${JsParam.searchTitle}' != ''){
				title = '${JsParam.searchTitle}';
			}
			
			$('.lui-search-panel-heading-title').html($(obj).text());
			
			seajs.use(['lui/util/str'], function(strutil) {
				var params = {
						value: id,
						modelName: '${param.modelName}'
				};
				var url = "/sys/search/search.do?method=condition&searchId=!{value}&fdModelName=!{modelName}&canClose=false&isNew=true";
				url = strutil.variableResolver(url, params);
				
				 var dataview=LUI("search_Iframe");
				 dataview.source.source={src:url};
				 dataview.refresh();
			});
		};
		
		
	</script>
		</template:replace>
	</template:include>
</c:if>
