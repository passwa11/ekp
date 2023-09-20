<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="kmsKnowledgeBaseDocListDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docCategory') }"></c:set>	
<%
	KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
	String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
	if ("true".equals(kmsCategoryEnabled)) {
%>
	<c:set var="kmsCategoryEnabled" value="true"></c:set>	
	<c:set var="kmsKnowledgeBaseDocListDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docCategory.categoryTrue') }"></c:set>
<%
	}
%>

<ui:ajaxtext>
    <script>
		seajs.use("kms/knowledge/kms_knowledge_portlet_ui/style/portlet_chosen.css");
	</script>
    <ui:dataview>
		<ui:source type="AjaxJson">
		      {"url":"/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getKnowledge&rowsize=${param.rowsize}&type=${param.type}&categoryId=${param.categoryId}&showCreator=${param.showCreator}&showCreated=${param.showCreated}&sumSize=!{param.sumSize}&scope=${param.scope}&dataType=col&orderby=${fdOrderBy}&ordertype=down"}
		</ui:source>
	    <ui:render type="Template">
				{$<div style="width:100%">$}
				    var sumSize = "${JsParam.sumSize}";
		    	    sumSize = parseInt(sumSize);
					if(isNaN(sumSize)){
						sumSize = 0;
					}
				   if(data && data.length>0){
				       for(var i=0;i < data.length;i++){
				         var summary =data[i].fddescription;
				         var _summary = summary;
				         if(sumSize>0 && summary && summary.length>sumSize){
						    summary = summary.substring(0, sumSize)+'..';
					      }
							{$<div class="lui_portlet_graphic_item">
								<div class="lui_img_content_td">
									<div class="lui_img_content">
									    <a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}{%data[i]['href']%}" target="_blank">
										     <img src="${LUI_ContextPath}{%data[i]['image']%}" />
										</a>
									</div>
								</div>
								<div class="lui_txt_content_td">
									<div class="lui_txt_content">
										<h3 class="lui_title_wrap"><span>{%data[i]['text']%}</span></h3>
										<p class="lui_summary">{%summary%}</p>
										<div class="lui_info">
											<div class="lui_fl">
										       <span class="lui_info_item">
											      ${kmsKnowledgeBaseDocListDocCategory }：{%data[i]['catename']%}
											   </span>
												<c:if test="${param.showCreated!=''}">
												   <span class="lui_info_item">
											          <bean:message bundle="kms-knowledge" key="kmsKnowledge.portlet.read" />：{%data[i]['read_c']%}
											       </span>
												</c:if>
											</div>
											<div class="lui_fr"><span class="lui_info_item lui_info_createdate">{%data[i]['docpublishTime']%}</span></div>
										</div>
										 <c:if test="${param.showCreator !=''}">
											<span class="lui_score">
												<em>
												$}
													if(data[i].docscore!=null){
														{$
															{%data[i].docscore%}
														$} 
								                     }else{
								                       {$
								                         --
								                       $}
								                     }
												{$
												</em>${lfn:message('kms-knowledge:kmsKnowledge.portlet.score') }
											</span>
										 </c:if>
									</div>
								</div>
							</div>$}
				       }
				   }
				
					
				{$</div>$}
				
			
	   </ui:render>
   </ui:dataview>
</ui:ajaxtext>