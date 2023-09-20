<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		<link rel="stylesheet" type="text/css" 
			  href="${LUI_ContextPath}/sys/relation/sys_relation_main/style/rela_view.css">
	</template:replace>
	<template:replace name="body">
		<div id="relation_div" style="overflow-x: hidden;overflow-y: auto;">
			<c:if test="${!empty sysRelationMainForm.fdDesSubject}">
				<ui:accordionpanel toggle="false">
					<ui:content title="${sysRelationMainForm.fdDesSubject}">
						<div style="word-wrap:break-word"><xform:textarea property="fdDesContent" ></xform:textarea></div>
					</ui:content>
				</ui:accordionpanel>
			</c:if>
				<%int s=0; request.setAttribute("s", 0);%>
			<c:if test="${!empty sysRelationMainForm && !empty sysRelationMainForm.sysRelationEntryFormList}">
				<ui:accordionpanel>
					<c:forEach items="${sysRelationMainForm.sysRelationEntryFormList}" varStatus="vstatus" var="sysRelationEntryForm">
				
					<c:choose>
						
							<c:when test="${sysRelationEntryForm.fdType=='6'}">
						
							<ui:content title="${sysRelationEntryForm.fdModuleName}" expand="${vstatus.index==0?'true':'false' }">
							<div class="fd_des${s}">${sysRelationEntryForm.sysRelationTextFormList[0].fdDescription}</div>
							</ui:content>
							<script>
	             seajs.use(['lui/jquery'],function($){
		           var text= $(".fd_des${s}").html().replace(/(\r\n)|(\n)/g,'<br>');
		          $(".fd_des${s}").html(text); 	
	               })
               
                </script>
							<%s++; request.setAttribute("s", s);%>
								</c:when>
							<c:otherwise>
							<ui:content title="${sysRelationEntryForm.fdModuleName}" expand="${vstatus.index==0?'true':'false' }">
							<ui:dataview id="${sysRelationEntryForm.fdId}">
								<ui:source type="AjaxJson">
									{
										url:'/sys/relation/relation.do?method=loadRelationResult&forward=listUi&currModelId=${JsParam.currModelId}&currModelName=${JsParam.currModelName}&sortType=time&fdKey=${JsParam.fdKey}&fdType=${sysRelationEntryForm.fdType}&moduleModelId=${sysRelationEntryForm.fdId}&showCreateInfo=${JsParam.showCreateInfo}&moduleModelName=${sysRelationEntryForm.fdModuleModelName}&rowsize=10'
									}
								</ui:source>
								<ui:render ref="sys.ui.classic.tile" var-showCreator="true" var-showCreated="true" var-ellipsis="false">
								</ui:render>
							</ui:dataview>	
							<div id="pageBtn_${sysRelationEntryForm.fdId}" class="relaPageBtn">
								<a id="pagePre" style='display:none;' class="arrowL" onclick="changePage(this,'${sysRelationEntryForm.fdId}',0)"></a>
							   <a id="pageNext" style='display:none;' class="arrowR" onclick="changePage(this,'${sysRelationEntryForm.fdId}',1)"></a>
							</div></ui:content></c:otherwise>
						
						</c:choose>
						<ui:event event="load" args="evt">
						   
						    
							var listSource = evt.source;
							var totalPage = 0;
							var relaId;
							if(listSource){
								var listDate = listSource.data;
								if(listDate.length>0){
									totalPage = listDate[0].totalPage;
								}
								relaId = listSource.id;
							}
							 
							if(relaId){
								if(typeof(window["relaPage_"+relaId]) == "undefined" ){
									window["relaPage_"+relaId] = 1;
								}
								var curPageno = window["relaPage_"+relaId];
								if(curPageno && totalPage){
									var preBtn = $("#pageBtn_" + relaId +" #pagePre");
									var nextBtn = $("#pageBtn_" + relaId +" #pageNext");
									
									if(totalPage>1){
										preBtn.show();
										nextBtn.show();
										
										if(curPageno >= totalPage){
											nextBtn.addClass("unable");
										}else{
											nextBtn.removeClass("unable");
										}
										if(curPageno <= 1){
											preBtn.addClass("unable");
										}else{
											preBtn.removeClass("unable");
										}
									}
								}
							}
						</ui:event>
					
					</c:forEach>
				</ui:accordionpanel>
			</c:if>
		</div>
	</template:replace>
</template:include>
<script>
//自适应高度
function dyniFrameSize(){
	if(window!=parent){
		var arguObj = document.getElementById("relation_div");
		if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			window.frameElement.style.height = arguObj.offsetHeight + "px";
		}
    }
	setTimeout(dyniFrameSize,50);
}

function changePage(evt,entryId,type){
	var isable = $(evt).hasClass("unable");
	if(!isable){
		seajs.use(['lui/dialog'],function(dialog){
			var loading = dialog.loading();
			$("#"+entryId).empty();
			var listViewUrl = LUI(entryId).source.url;
			if(typeof(window["relaPage_"+entryId]) == "undefined" ){
				window["relaPage_"+entryId] = 1;
			}
			if(type == 0){
				window["relaPage_"+entryId] = window["relaPage_"+entryId] - 1;
			}else{
				window["relaPage_"+entryId] = window["relaPage_"+entryId] + 1;
			}
			
			var pagenoIndex = listViewUrl.indexOf("&pageno=");
			if(pagenoIndex > 0){
				listViewUrl = listViewUrl.substr(0,pagenoIndex);
			}
			LUI(entryId).source.url = listViewUrl + "&pageno=" + window["relaPage_"+entryId];
			LUI(entryId).source.get();
			loading.hide();
		});
	}
}
</script>
