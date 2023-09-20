<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
	<%
	%>
<div id="usualCateDiv">	
	<ui:dataview>
		<ui:event event="load">
			var data = this.data;
			if(data.list && data.list.length == 0){
				this.erase();
			}  
		</ui:event>
		<ui:source type="AjaxJson">
		    {"url":"/sys/lbpmperson/SysLbpmPersonCreate.do?method=listUsual&mainModelName=${JsParam.mainModelName}&key=${JsParam.key }"}
		</ui:source>
		<ui:render type="Template">
			<c:choose>
				<c:when test="${empty JsParam.mainModelName}">
					<c:import url="/sys/lbpmperson/style/tmpl/listTemplate.jsp" charEncoding="UTF-8"></c:import>
				</c:when>
				<c:otherwise>
					<c:if test="${'globalCategory' eq param.cateType}">
						{$
					<div class="lui-cate-panel-heading usual-cate-title">		     
						 <h2 class="lui-cate-panel-heading-title"><bean:message key="lbpmperson.recent.cateTemplate" bundle="sys-lbpmperson"/></h2> 
					</div>
					<ul class='lui-cate-panel-list'>
				$} 
				var _data = data.list;
				for(var i=0;i < _data.length;i++){
					{$
						<li class="lui-cate-panel-list-item">
							 <div class="link-box">
						        <div class="link-box-heading">
						          <p><span>{%_data[i].templateDesc%}</span></p>
						        </div>
						        <div class="link-box-body">
						          <a  onclick="Com_OpenNewWindow(this)" data-href="{%Com_Parameter.ContextPath%}{%_data[i].addUrl%}" target="_blank"  title="{%_data[i].templateDesc %}"><bean:message key="button.add"/></a>
						        </div>
			        $}
								if(_data[i].fdIsImport == "true"){
									{$
										<div class="link-box-footer canCopyFooter">
											<h6 class="link-box-title canCopyTitle">
									$}
								}else{
									{$
										<div class="link-box-footer">
											<h6 class="link-box-title">
									$}
								}
								
						          if(_data[i].cateName){
						           {$
						         	  <i class="icon"></i><span>{%env.fn.formatText(_data[i].cateName)%}</span>
						         	$} 
						           }
						{$
						          </h6>
			          	$}
						          if(_data[i].fdIsImport == "true"){
								  	{$
								  		<span class="iconWrap">
								  			<i class="icon unlistImg" onclick="listRelationDocument('{%_data[i].fdTemplateId%}','{%_data[i].fdModelName%}','{%Com_Parameter.ContextPath%}{%_data[i].addUrl%}');"></i>
							  			</span>
								  	$} 
						          }
		          	{$
						        </div>
						     </div>
						     <div name="relationDocument" class="relationDocument" >
						      	<div class="lui-cate-panel-search" style="float: left;margin:0px;">
									<div class="lui_category_search_box">
										<a href="javascript:;" id="searchBackBtn" class="search-back" onclick="Qsearch_rtn();"></a>
										<div class="search-input" style="width:250px;">
											<input id="relationSearchTxt" class="lui_category_search_input" style="width:100%;" type="text" placeholder="请输入关键词" onkeyup="relationSearch();"/>
										</div>
										<a href="javascript:void(0);" class="search-btn" style="height:32px;" onclick="QRelationsearch();"></a>
									</div>
								</div>
						      </div>
						</li>
					$}
				}
			 {$
				</ul>
			$}
					</c:if>
					
				<c:if test="${'simpleCategory' eq param.cateType || empty param.cateType}">
					{$
					<div class="lui-cate-panel-heading usual-cate-title">		     
						 <h2 class="lui-cate-panel-heading-title"><bean:message key="lbpmperson.recent.category" bundle="sys-lbpmperson"/></h2> 
					</div>
					<ul class='lui-cate-simple-panel-list clearfloat'>
				$} 
				var _data = data.list;
				for(var i=0;i < _data.length;i++){
					{$
						<li class="lui-cate-simple-panel-list-item">
							 <div class="link-box">
								<div class="link-box-heading">
									<p class="link-box-title"> <span><i class="icon"></i><span>{%_data[i].templateDesc%}</span> </span>
									</p>
								</div>
								<div class="link-box-body">
									<a class="btn-add" onclick="Com_OpenNewWindow(this)" data-href="{%Com_Parameter.ContextPath%}{%_data[i].addUrl%}" target="_blank" title="{%_data[i].templateDesc %}"><bean:message key="button.add"/></a>
								</div>
							</div>
						</li>
					$}
				}
			 {$
				</ul>
			$}
				</c:if>
			</c:otherwise>
			</c:choose>
		</ui:render>
		<ui:event event="load">
			//dataview加载完成后，若无最进的流程，且选择的所有模块，则修改最进的流程是否为空的状态
			try {
				isRecentEmpty = arguments[0].source.data.list.length == 0 && ${empty  JsParam.mainModelName};
			} catch (e) {
			
			}
			//若无最常用的流程，且无最近使用流程，则修改定位
			if(isFrequentEmpty && isRecentEmpty){
				changeSelectedIfEmpty();
			}
			Com_IncludeFile("listRelationDoc.js",Com_Parameter.ContextPath + "sys/lbpmperson/resource/js/","js",true);
		</ui:event>
	</ui:dataview>
</div>	
	