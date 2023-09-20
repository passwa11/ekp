<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

	<div class="lui-cate-panel-search" style="float: left;margin:0px;padding:0px 1px 1px 1px;">
		<div class="lui_category_search_box">
			<a href="javascript:;" id="searchBackBtn" class="search-back" onclick="Qsearch_rtn();"></a>
			<div class="search-input" >
				<c:if test="${param.cateType ne 'globalCategory'}">
					<input  id="searchTxt" class="lui_category_search_input" type="text" placeholder="<bean:message key="lbpmperson.createDoc.search.allCategorySearch" bundle="sys-lbpmperson"/>" onkeyup="searchCate();"/>
				</c:if>
				<c:if test="${param.cateType eq 'globalCategory'}">
					<input  id="searchTxt" class="lui_category_search_input" type="text" placeholder="<bean:message key="lbpmperson.createDoc.search.allCateTemplateSearch" bundle="sys-lbpmperson"/>" onkeyup="searchCate();"/>
				</c:if>
			</div>
			<a href="javascript:void(0);" class="search-btn" onclick="Qsearch();"></a>
		</div>
	</div>
	<div id="CateSearchDiv" style="display: none;clear: both;">
		<ui:dataview id="CateSearch">
			<ui:source type="AjaxJson">
				{url: ''}
			</ui:source>
			<ui:render type="Template">
				<c:import url="/sys/lbpmperson/style/tmpl/listSearch.jsp" charEncoding="UTF-8"></c:import>
			</ui:render>
		</ui:dataview>
	</div>
    <script type="text/javascript">
    seajs.use(['lui/jquery','lui/util/str','lui/dialog'], function($,str,dialog) {
    	
    	window.searchCate = function(evt){
    		var event = evt ||  Com_GetEventObject();
    		if (event && event.keyCode == 13){
    			Qsearch(event);
    		}
    	};
    	
	    window.Qsearch = function(event){
	    	var event = event || Com_GetEventObject();
	    	var srcDom = event.srcElement||event.target;
	    	srcDom.blur();
			var searchTxt = $("#searchTxt").val();
			if(searchTxt !="" && $.trim(searchTxt) != ""){
				if($("#favouriteCateDiv")){
					$("#favouriteCateDiv").hide();
				}
				if($("#usualCateDiv")){
					$("#usualCateDiv").hide();
				}
				
				
				var modelName="${JsParam.mainModelName}";
				if(!modelName){
					if($("#_secondaryDirectory").is(":hidden")){
						modelName=$("#search_module").val();
					}else{
						modelName=$("#_secondaryDirectory").val();
					}
				}
				//cateType == 'isSimpleCategory'
				if(LUI("CateSearch")){
					if(document.getElementById("search_module")){
						if(!document.getElementById("search_module").value){
							//dialog.alert('<bean:message key="lbpmperson.createDoc.search.selectModuleFirst" bundle="sys-lbpmperson"/>');
							LUI("CateSearch").source.setUrl('/sys/lbpmperson/SysLbpmPersonSearch.do?method=search&searchText='+encodeURIComponent(searchTxt));
							LUI("CateSearch").source.get();
							$("#searchBackBtn").css({'display':'inline-block'});
							$("#CateSearchDiv").show();
							return;
						}
					}
					var templateName=_mapModuleConfigInfo[modelName].templateModelName;
					
					var cateType="${JsParam.cateType}";
					if(!cateType){
						cateType=_mapModuleConfigInfo[modelName].cateType;
					}
					if(cateType == 'globalCategory'){
						var url = '/sys/category/criteria/sysCategoryCriteria.do?method=select&modelName='+templateName+'&searchText='+encodeURIComponent(searchTxt)+'&type=03&getTemplate=1&authType=2&qSearch=true';
						if(templateName == 'com.landray.kmss.km.asset.model.KmAssetApplyTemplate' || templateName == 'com.landray.kmss.hr.recruit.model.HrRecruitTemplate' || templateName == 'com.landray.kmss.hr.ratify.model.HrRatifyTemplate')
							url += '&tempKey='+_mapModuleConfigInfo[modelName].key;
						LUI("CateSearch").source.setUrl(url);
					}else if(cateType == 'simpleCategory'){
						LUI("CateSearch").source.setUrl('/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=select&modelName='+templateName+'&searchText='+encodeURIComponent(searchTxt)+'&type=03&authType=2&qSearch=true');
					}else{
						LUI("CateSearch").source.setUrl("");
						if(console){
							console.log("非简单或全局分类模板不支持搜索"+templateName);
						}
						return;
					}
					LUI("CateSearch").source.get();
				}
			}else{
				dialog.alert('<bean:message key="lbpmperson.createDoc.search.enterKeywordFirst" bundle="sys-lbpmperson"/>');
				return;
			}
			$("#searchBackBtn").css({'display':'inline-block'});
			$("#CateSearchDiv").show();
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
