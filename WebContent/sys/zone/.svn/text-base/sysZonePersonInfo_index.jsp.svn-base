<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page
	import="com.landray.kmss.sys.zone.service.ISysZonePersonInfoService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<template:include file="/sys/zone/zoneIndexTemplate.jsp">
	<template:replace name="title">
		<c:out value="${lfn:message('sys-zone:sysZonePerson.zoneIndex') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" >
			<ui:menu-item text="${lfn:message('home.home')}" href="/" target="_top" icon="lui_icon_s_home" />
			<ui:menu-item text="${lfn:message('sys-zone:module.sys.zone') }" href="javascript:top.open('${LUI_ContextPath }/sys/zone','_self');" />
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		 <!--头部 Starts-->
		 <div class="lui-zone-header">
		      <div class="lui-zone-header-content">
				        <h1 class="lui-zone-header-title">${lfn:message('sys-zone:sysZonePersonInfo.index.search')}</h1>
				        <html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=toSearch">
					        <div class="lui-zone-header-search-body">
						       <div class="lui-zone-header-searchbar">
						           	<input id="searchValue" class="form-control" type="text" name="searchValue" placeholder="${lfn:message('sys-zone:sysZonePerson.searchInputHelp') }">
						             <button class="lui-zone-header-btn search" id="searchBtn"></button>
						       </div>
					          <div class="lui-zone-header-btngroup">
					            <div class="lui-zone-header-btn" id="addressBtn">
					            	${lfn:message('sys-zone:sysZonePerson.address.list')}</div>
					          </div>
					        </div>
				        </html:form>
		      </div>
		 </div>
		 
		<c:import url="/sys/tag/import/sysTagGroup_setting.jsp" charEncoding="UTF-8">
			<c:param name="modelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
		</c:import>
		 
		  <%-- 按钮 --%>
		<div class="lui_list_operation">
			<div style="width:100%">
					<div style='color: #979797;width: 50px;text-align: center;float:left;padding-top:1px;'>
						${ lfn:message('list.orderType') }：
					</div>
					<%--排序按钮  --%>
					<div style="float: left;"> 
						<div style="display:inline-block;vertical-align:middle;" 
						     class="lui_wiki_sort_box">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="4">
								<list:sortgroup>
									<list:sort property="fdOrder" text="${lfn:message('model.fdOrder')}"
											   group="sort.list" value="up"></list:sort>
									<list:sort property="fdNamePinYin" text="${lfn:message('sys-zone:sysZonePersonInfo.username') }"
											   group="sort.list"></list:sort>
								</list:sortgroup>
							</ui:toolbar>
						</div>
					</div>
					<div style="float:left;">
						<list:paging layout="sys.ui.paging.top">		
						</list:paging>
					</div>
				</div>
		</div>
		
		 <div class="lui-zone-content-body">
			 <list:listview >
	                  <ui:source type="AjaxJson">
	                        {"url":"/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=searchPerson&rowsize=16"}
	                    </ui:source>
	                  <list:gridTable name="gridtable" columnNum="4" >
	                    <list:row-template >
	                        <c:import url="/sys/zone/sys_zone_personInfo/sysZoneNewPersonInfo_index_grid.jsp" 
	                        		  charEncoding="UTF-8"/>
	                    </list:row-template>
	                  </list:gridTable>
	          </list:listview>
          </div>
          <list:paging ></list:paging>
          <script>
          	   seajs.use(['lui/jquery'], function($) {
          		   
          		   var zoneSearch = function() {
         			 	Com_SubmitForm(document.sysZonePersonInfoForm, 'toSearch');
          		   }
          		   
          		   $("#addressBtn").on("click" , function() {
          			  Com_OpenWindow("${LUI_ContextPath}/sys/zone/address/", "_blank");
          		   });
          		   
          		   $("#searchBtn").on("click" , zoneSearch);
          		   
	          	   $('#searchValue').on('keydown',function(event){
	    	             if(event.keyCode == 13) {
	    	      	    	 zoneSearch();  
	    	      	    	 return;
	    	             }
	    	       });
          	   });
          </script>
	</template:replace>
</template:include>