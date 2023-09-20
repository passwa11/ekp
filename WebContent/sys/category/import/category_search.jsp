<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
<c:when test="${'true' eq param.isSimpleCategory}">
	<c:set var="search_placeholder" value="${lfn:message('sys-category:sysCategory.search.placeholder.simple') }"></c:set>
</c:when>
<c:otherwise>
	<c:set var="search_placeholder" value="${lfn:message('sys-category:sysCategory.search.placeholder') }"></c:set>
</c:otherwise>
</c:choose>

	<div class="lui-cate-panel-search">
		<div class="lui_category_search_box">
			<a href="javascript:;" id="searchBackBtn" class="search-back" onclick="Qsearch_rtn();"></a>
			<div class="search-input" >
				<input  id="searchTxt" class="lui_category_search_input" type="text" placeholder="${search_placeholder}" onkeyup="searchCate();"/>
			</div>
			<a href="javascript:void(0);" class="search-btn" onclick="Qsearch();"></a>
		</div>
	</div>

	<div id="CateSearchDiv" style="display: none">
		<ui:dataview id="CateSearch">
			<ui:source type="AjaxJson">
				{url: ''}
			</ui:source>
			<ui:render type="Template">

				if(data.length > 0){
					{$
						<div>
						<ul class='lui-cate-simple-panel-list clearfloat'>
					$}
						var _data = data[0];
						if(_data.length > 0){
							for(var i=0;i < _data.length;i++){
								<%--#145023-流程发起页面调整-开始--%>
								<%--{$
									<li class="lui-cate-simple-panel-list-item">
										 <div class="link-box">
											<div class="link-box-heading">
												<p class="link-box-title"> <span><i class="icon"></i><span>{%env.fn.formatText(_data[i].text)%}</span> </span>
												</p>
											</div>
											<div class="link-box-body">
												<a class="btn-add" href="javascript:;" onclick="javascript:openCreate('{%_data[i].value%}');" title="{%env.fn.formatText(_data[i].text)%}"><bean:message key="button.add" /></a>
											</div>
										</div>
									</li>
								$}--%>
								{$
									<li class="lui-cate-panel-list-item" style="margin: 15px 10px;">
										<%--li显示Div--%>
										<div class="link-box-new" onclick="javascript:openCreate('{%_data[i].value%}');">
											<%--图标显示Div--%>
											<div class="link-box-icon">
								$}
												if(typeof(_data[i].fdIcon) == "undefined" || _data[i].fdIcon =='lui_icon_l_icon_1' || _data[i].fdIcon ==''){
													{$
														<img class="lui_img_l" src="{%Com_Parameter.ContextPath%}sys/ui/extend/theme/default/images/icon_test.png" width="100%">
													$}
												}
												if(typeof(_data[i].fdIcon) != "undefined" && (_data[i].fdIcon =='lui_icon_l_icon_1' || _data[i].fdIcon !='')){
													{$
														<img class="lui_img_l" src="{%_data[i].fdIcon%}" width="100%">
													$}
												}
								{$
											</div>
											<%--文字说明Div--%>
											<div class="text-search">
												<div class="temp-search">
													<p><span title="{%env.fn.formatText(_data[i].text)%}">{%env.fn.formatText(_data[i].text)%}</span></p>
												</div>
											</div>
										</div>
									</li>
								$}
								<%--#145023-流程发起页面调整-结束--%>
							}

						}else{
							{$
								<div class="lui-cate-simple-panel-noresult">
									<bean:message bundle="sys-category" key="sysCategory.search.noResult" />
								</div>
							$}
						}
					{$
						</div>
						</ul>
					$}
				}else{
					{$
						<div class="lui-cate-simple-panel-noresult">
							<bean:message bundle="sys-category" key="sysCategory.search.noResult" />
						</div>
					$}
				}
			</ui:render>
		</ui:dataview>
	</div>
    <script type="text/javascript">
    seajs.use(['lui/jquery','lui/util/str','lui/dialog'], function($,str,dialog) {

    	window.searchCate = function(evt){
    		var event = event || window.event;
    		if ( (evt && evt.keyCode == 13) || (event && event.keyCode == 13) ){
    			Qsearch();
    		}
    	};

	    window.Qsearch = function(){
			var searchTxt = $("#searchTxt").val();
			if(searchTxt !="" && $.trim(searchTxt) != ""){
				if($("#favouriteCateDiv")){
					$("#favouriteCateDiv").hide();
				}
				if($("#usualCateDiv")){
					$("#usualCateDiv").hide();
				}
				$("#searchBackBtn").css({'display':'inline-block'});
				$("#CateSearchDiv").show();
				if(LUI("CateSearch")){
					if("${JsParam.isSimpleCategory}" == 'false'){
						LUI("CateSearch").source.setUrl('/sys/category/criteria/sysCategoryCriteria.do?method=select&modelName=${param.modelName}&searchText='+encodeURIComponent(searchTxt)+'&type=03&getTemplate=1&authType=2&qSearch=true');
					}else{
						LUI("CateSearch").source.setUrl('/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=select&modelName=${param.modelName}&searchText='+encodeURIComponent(searchTxt)+'&type=03&authType=2&qSearch=true');
					}
					LUI("CateSearch").source.get();
				}
			}else{
				dialog.alert('<bean:message bundle="sys-category" key="sysCategory.search.keyword" />');
			}
		};
		window.Qsearch_rtn = function(){
			$("#searchTxt").val("");
			$("#CateSearchDiv").hide();
			$("#searchBackBtn").css({'display':'none'});
			if($("#favouriteCateDiv")){
				$("#favouriteCateDiv").show();
			}
			if($("#usualCateDiv")){
				$("#usualCateDiv").show();
			}
		};
    });
    </script>
