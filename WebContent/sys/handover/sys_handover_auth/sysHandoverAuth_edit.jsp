<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="auto">
    <template:replace name="head">
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/handover/resource/css/handover.css?s_cache=${LUI_Cache}" />
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/toolbar.css?s_cache=${LUI_Cache}" />
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/handover/sys_handover_config/js/jsTree/themes/default/style.min.css?s_cache=${LUI_Cache}" />
        <script src="${LUI_ContextPath}/sys/handover/sys_handover_config/js/jsTree/jstree.js?s_cache=${LUI_Cache}"></script>
        <script type="text/javascript" src="<c:url value="/sys/admin/resource/js/jquery.corner.js"/>?s_cache=${LUI_Cache}"></script>
	</template:replace>
	<%-- 标题 --%>
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-handover:sysHandoverConfigMain.create') } - ${ lfn:message('sys-handover:module.sys.handover') }"></c:out>
	</template:replace>
	<%-- 路径 --%>
	<template:replace name="path">
	    <ui:menu layout="sys.ui.menu.nav">
		    <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self">
				</ui:menu-item>
				<ui:menu-item text="${lfn:message('sys-handover:module.sys.handover')}" href="/sys/profile/index.jsp#org/handover" target="_self">
			   </ui:menu-item> 
		</ui:menu>
	</template:replace>	
	<%-- 内容 --%>
	<template:replace name="content"> 
	   <!-- 主体开始 -->
        <div id="lui_handover_w main_body" class="lui_handover_w main_body">
            <div class="lui_handover_header">
                <%-- 标题 --%>
                <span>
             		<kmss:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType" /> - 
             		<kmss:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType.auth" />
                </span>
                <%-- 重置 --%>
                <ui:button styleClass="btn_reset" text="${ lfn:message('sys-handover:sysHandoverConfigLog.operation.reset') }" onclick="resetOperation();"/>
            </div>
            <div class="lui_handover_content">
                <%-- 查询条件 --%>
                <form name="sysHandoverConfigMainForm">
                <input type="hidden" name="fdId" value="${sysHandoverConfigMainForm.fdId}">
                <table class="tb_simple lui_handover_headTb lui_sheet_c_table">
                    <tr>
                        <%-- 交接人 --%>
                        <td width="15%" style="text-align: right;"> <span>${lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }</span></td>
						<td width="35%" id="from_edit">
							<xform:address propertyId="fdFromId"
								propertyName="fdFromName" style="width:50%" required="true"
								onValueChange="changeFromName()"
								subject="${lfn:message('sys-handover:sysHandoverConfigMain.fdFromName')}"
								showStatus="edit" orgType="ORG_TYPE_POSTORPERSON|ORG_TYPE_DEPT|ORG_FLAG_AVAILABLEYES" textarea="false"></xform:address>
								<span><a href="javascript:selectInvalid();">${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }</a></span>
						</td>
						<td width="35%" id="from_view" style="display: none">
						    <input style="width:65%;height:23px" type="text" name="fdFromName" disabled/>
						</td>
						<%-- 接收人 --%>
						<td width="15%" style="text-align: right;">
                            <span>${lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }</span>
                        </td>
                        <td width="35%" id="to_edit">
							<xform:address propertyId="fdToId" propertyName="fdToName" style="width:50%"
								required="true" onValueChange="changeToName()" validators="handoverNameSame"
								subject="${lfn:message('sys-handover:sysHandoverConfigMain.fdToName')}"
								showStatus="edit" orgType="ORG_TYPE_POSTORPERSON|ORG_TYPE_DEPT" textarea="false"></xform:address>
                        </td>
                        <td width="35%" id="to_view" style="display: none">
						    <input style="width:65%;height:23px" type="text" name="fdToName" disabled/>
						</td>
                    </tr>
                    <tr>
                    	<td width="15%" style="text-align: right;">${lfn:message('sys-handover:sysHandoverConfigMain.fdAuthType')}</td>
                    	<td colspan="3" id="transAuthor">
                    		<xform:checkbox property="fdAuthType" alignment="H" value="authReaders;authEditors;authLbpmReaders;authAttPrints;authAttCopys;authAttDownloads" onValueChange="checkSituation()">
                    			<xform:simpleDataSource value="authReaders">${lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authReaders')}</xform:simpleDataSource>
                    			<xform:simpleDataSource value="authEditors">${lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authEditors')}</xform:simpleDataSource>
                    			<xform:simpleDataSource value="authLbpmReaders">${lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authLbpmReaders')}</xform:simpleDataSource>
                    			<xform:simpleDataSource value="authAttPrints">${lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authAttPrints')}</xform:simpleDataSource>
                    			<xform:simpleDataSource value="authAttCopys">${lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authAttCopys')}</xform:simpleDataSource>
                    			<xform:simpleDataSource value="authAttDownloads">${lfn:message('sys-handover-support-config-auth:sysHandoverDocAuth.authAttDownloads')}</xform:simpleDataSource>
                    		</xform:checkbox>
                    		<span class="txtstrong">*</span>
                    	</td>
                    </tr>
                </table>
                </form>
                <%-- 查询部分--%>
                <div class="lui_handover_search" id="searchDiv">
                	<%-- 交接内容--查询全选 --%>
                    <h3 class="title"> ${ lfn:message('sys-handover:sysHandoverConfigMain.content') } 
                    	<div style="float: right; margin-right: 5px;">
	                    	<label>
								<input type="checkbox" name="_searchSelectAll" checked="checked" id="_searchSelectAll" onclick="searchCheckAll();"/><bean:message key="sysAdminDbchecker.selectAll" bundle="sys-admin" />
							</label>
						</div>
					</h3>
					<div id="div_main" class="div_main">
						<table width="100%" class="tb_normal" cellspacing="1">
							<tr>
								<td align="center" style="border: 0px;">
									<div class="lui_handover_search_c">
			                        <ul class="clrfix">
			                                 <%int n = 0;%>
			                          <table width="100%">
			                           <tr>
			                             <c:forEach items="${moduleMapList}" var="module">   
										      <c:forEach items="${module}" var="moduleMap">   
													 <td width="20%">
													     <li><span class="item item_unck">
														      <input type="checkbox" name="searchModuleCheckBox" id="${moduleMap.key}_searchCheck" value="${moduleMap.key}" onclick="checkedSearchSelectAll(this);" checked="checked"/>
														      <label for="${moduleMap.key}_searchCheck">${moduleMap.value}</label>
														      </span>
														 </li>
													 </td>
											   <% 
												 n++;
												 if(n%5 == 0){
													 out.print("</tr><tr>");
											  	 }
											   %>
										      </c:forEach>
								  	 	</c:forEach>
								  	 </tr>
								   </table>
			                      </ul>
			                    </div>
								</td>
							</tr>
						</table>
					</div>
                    <div class="lui_handover_btn_w">
                       <ui:button style="width:100px" text="${ lfn:message('sys-handover:sysHandoverConfigLog.operation.next') }" onclick="nextOperation();"/>&nbsp;&nbsp;
                       <ui:button style="width:100px" text="${ lfn:message('sys-handover:sysHandoverConfigLog.operation.submit') }" onclick="submitOperation(true);"/>
                   </div>
                </div> 
                <%-- 结果部分--%> 
                <div class="lui_handover_searchResult" style="display: none" id="resultDiv">
                    <div id="valueDiv" style="display: none"></div>
                    <h3 class="title"> ${ lfn:message('sys-handover:sysHandoverConfigMain.searchResult') } </h3>
	                  	<%--记录显示--%> 
	                    <div name="resultContent" class="resultContent" id="resultContent">
	                    </div>
	                    <div class="lui_handover_btn_w" id="operationArea">
                          	<ui:button style="width:100px" text="${ lfn:message('sys-handover:sysHandoverConfigLog.operation.previous') }" onclick="previousOperation();"/>
                    		<ui:button style="width:100px" id="submit2" text="${ lfn:message('sys-handover:sysHandoverConfigLog.operation.submit') }" onclick="submitOperation(false);"/>
	                    </div>
	                    <%--无记录显示--%> 
	                    <div class="no_result_div" id="no_result_div" style="display:none">
	                    		<h3 class="title">${lfn:message('sys-handover:sysHandoverConfigMain.noResult.title') }</h3>
	                    		<span class="item item_unck ck_all">
							    	 <span class="a_spead_onekey_no" onclick="oneKeyShowModule(true);">${lfn:message('sys-handover:sysHandoverConfigMain.noResult.show') }</span>
							    	 <span class="a_retract_onekey_no" style="display: none" onclick="oneKeyShowModule(false);" style="display: none">${lfn:message('sys-handover:sysHandoverConfigMain.noResult.hide') }</span>
	                  	 		</span>
	                    </div>
	                    <div name="noResultContent" style="display: none" class="noResultContent" id="noResultContent">
	                    </div>
                   </div>
               </div>
        </div>
        <!-- 主体结束 -->
		
		<!-- 脚本文件 -->
		<%@ include file="./sysHandoverAuth_edit_script.jsp"%>
	</template:replace>
</template:include>