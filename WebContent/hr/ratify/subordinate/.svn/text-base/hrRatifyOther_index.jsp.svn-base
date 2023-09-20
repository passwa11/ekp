<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.framework.hibernate.extend.SqlPartitionConfig"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>

<!-- 筛选 -->
	    <list:criteria id="criteria2">
	       	<list:tab-criterion title="${lfn:message('hr-ratify:hrRatifyMain.docStatus') }" key="docStatus"> 
		 		<list:box-select>
			 		<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="true">
						<ui:source type="Static">
							[{text:'${ lfn:message('hr-ratify:enums.doc_status.10') }', value:'10'},
							{text:'${ lfn:message('hr-ratify:enums.doc_status.20') }', value:'20'},
							{text:'${ lfn:message('hr-ratify:enums.doc_status.11') }', value:'11'},
							{text:'${ lfn:message('hr-ratify:enums.doc_status.30') }', value:'30'},
							{text:'${ lfn:message('hr-ratify:enums.doc_status.00') }',value:'00'}]
						</ui:source>
					</list:item-select>
		  		</list:box-select>
	  		</list:tab-criterion>
		  	<list:tab-criterion title="" key="feedStatus">
			 	<list:box-select>
			 		<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false" cfg-required="true"  cfg-if="param['doctype'] == 'feedback'">
						<ui:source type="Static">
							[{text:'未反馈', value:'41'},
							 {text:'${ lfn:message('hr-ratify:enums.doc_status.31') }',value:'31'}]
						</ui:source>
					</list:item-select>
			  	</list:box-select>
			 </list:tab-criterion>
		     <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('hr-ratify:hrRatifyMain.docSubject')}" />
		     <list:cri-ref ref="criterion.sys.category" key="docTemplate" multi="false" title="${lfn:message('hr-ratify:hrRatifyMain.docTemplate')}" expand="false">
		         <list:varParams modelName="com.landray.kmss.hr.ratify.model.HrRatifyTemplate" />
		     </list:cri-ref>
		     <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyOther" property="docNumber" />
		     <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyOther" property="docCreator" />
		     <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyOther" property="docCreateTime" />
		     <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyOther" property="docPublishTime" />
	  	</list:criteria>

	<!-- 操作 -->
	    <div class="lui_list_operation">
	
	        <div style='color: #979797;float: left;padding-top:1px;'>
	            ${ lfn:message('list.orderType') }：
	        </div>
	        <div style="float:left">
	            <div style="display: inline-block;vertical-align: middle;">
	                <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
	                    <list:sort property="hrRatifyOther.docCreateTime" text="${lfn:message('hr-ratify:hrRatifyMain.docCreateTime')}" group="sort.list" />
	                    <list:sort property="hrRatifyOther.docPublishTime" text="${lfn:message('hr-ratify:hrRatifyMain.docPublishTime')}" group="sort.list" />
	                </ui:toolbar>
	            </div>
	        </div>
	        <div style="float:left;">
	            <list:paging layout="sys.ui.paging.top" />
	        </div>
	        <div style="float:right">
	            <div style="display: inline-block;vertical-align: middle;">
	                <ui:toolbar count="3">
	
	                    <%-- <kmss:authShow roles="ROLE_HRRATIFY_CREATE">
	                        <ui:button text="${lfn:message('button.add')}" onclick="addDoc0();" order="2" />
	                    </kmss:authShow>
	                    <kmss:auth requestURL="/hr/ratify/hr_ratify_entry/hrRatifyOther.do?method=deleteall">
	                        <c:set var="canDelete" value="true" />
	                    </kmss:auth>
	                    <!--删除-->
	                    <ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc();" order="4" id="btnDelete" /> --%>
	                </ui:toolbar>
	            </div>
	        </div>
	    </div>
	    <ui:fixed elem=".lui_list_operation" />
	    
<list:listview id="listview">
	<ui:source type="AjaxJson">
		{url:'/sys/subordinate/sysSubordinate.do?method=list&modelName=com.landray.kmss.hr.ratify.model.HrRatifyOther&orgId=${JsParam.orgId}'}
	</ui:source>
	<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.hr.ratify.model.HrRatifyOther" 
		isDefault="true" layout="sys.ui.listview.columntable" 
		rowHref="/sys/subordinate/sysSubordinate.do?method=view&modelId=!{fdId}&modelName=com.landray.kmss.hr.ratify.model.HrRatifyOther&orgId=${JsParam.orgId}">
		<list:col-serial></list:col-serial>
		<list:col-auto></list:col-auto> 
	</list:colTable>
</list:listview> 
<br>
<%@ include file="/sys/profile/showconfig/showConfig_paging_buttom.jsp"%>