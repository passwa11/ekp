<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.framework.hibernate.extend.SqlPartitionConfig"%>

<script type="text/javascript">
	seajs.use(['theme!list']);	
</script>

<!-- 筛选器 -->
<list:criteria id="collaborateMainCriteria">
     <!-- 主题 -->
    <list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
	<%
		if(SqlPartitionConfig.getInstance().isEnabled("com.landray.kmss.km.collaborate.model.KmCollaborateMain")){
	%>
	<list:cri-ref title="${ lfn:message('km-collaborate:kmCollaborateMain.docCreateTime') }" key="partition" ref="criterion.sys.partition" modelName="com.landray.kmss.km.collaborate.model.KmCollaborateMain" />
	<%
		}
	%>
	</list:cri-ref>
	<!-- 我的沟通 -->
    <list:cri-criterion title="${ lfn:message('km-collaborate:kmCollaborateMain.Collaborate.my') }" key="pageType" expand="false" multi="false">
		<list:box-select>
			<list:item-select cfg-enable="false">
		    	 <ui:source type="Static">
				    [{text:'${ lfn:message('km-collaborate:kmCollaborateMain.myLaunch') }',value:'myDoc'},
				    {text:'${ lfn:message('km-collaborate:kmCollaborateMain.myParticipate') }',value:'myJoin'},
				    {text:'${ lfn:message('km-collaborate:kmCollaborateMain.myAttention') }',value:'myFollow'}
				    ]
			     </ui:source>
			     <ui:event event="selectedChanged" args="evt">
						var vals = evt.values;
						if (vals.length > 0 && vals[0] != null) {
							var val = vals[0].value;
							if (val != 'myDoc') {
								LUI('mark').setEnable(true);
							} else{
								LUI('mark').setEnable(false);
							}
						}else{
						        LUI('mark').setEnable(false);
						}
	 			</ui:event>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>	
	<!-- 标记 -->
    <list:cri-criterion title="${ lfn:message('km-collaborate:kmCollaborateMain.mark') }" key="mark" expand="false" multi="false">
		<list:box-select>
			<list:item-select id="mark" cfg-enable="false"  cfg-if="param['pageType'] == 'myJoin' || param['pageType'] == 'listCopy'">
		    	 <ui:source type="Static">
				    [{text:'${ lfn:message('km-collaborate:kmCollaborateMain.mark.unRead') }',value:'unRead'},
				    {text:'${ lfn:message('km-collaborate:kmCollaborateMain.mark.readed') }',value:'readed'},
				    {text:'${ lfn:message('km-collaborate:kmCollaborateMain.mark.overdue') }',value:'overdue'}
				    ]
			     </ui:source>
			</list:item-select>
		</list:box-select>
	 </list:cri-criterion>	
	 <!-- 类型 -->
	 <list:cri-criterion title="${ lfn:message('km-collaborate:table.kmCollaborateCategory.tilteKind') }" key="fdCategory" expand="false">
		<list:box-select>
			<list:item-select cfg-defaultValue="${(param.fdTemplateId==null)?'':(param.fdTemplateId)}">
				<ui:source type="AjaxJson">
					{url:'/km/collaborate/km_collaborate_category/kmCollaborateCategory.do?method=getCollaborateCategory'}
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>	
	
	<!-- 沟通来源 -->
	<list:cri-criterion title="${lfn:message('km-collaborate:kmCollaborateMain.source') }" key="fdModelName" multi="true">
		<list:box-select>
			<list:item-select type="lui/criteria!CriterionSelectDatas">
				<ui:source type="AjaxJson" >
					{url: "/km/collaborate/km_collaborate_main/kmCollaborateMainIndex.do?method=getModules"}
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
   
	 <!-- 创建者、创建时间 -->
    <list:cri-auto modelName="com.landray.kmss.km.collaborate.model.KmCollaborateMain" property="fdNumber;docStatus" expand="false"/>
   <%
		if(SqlPartitionConfig.getInstance().isEnabled("com.landray.kmss.km.collaborate.model.KmCollaborateMain") == false){
	%> 
	<list:cri-auto modelName="com.landray.kmss.km.collaborate.model.KmCollaborateMain" property="docCreateTime" expand="false"/>
	<%
		}
	%>
</list:criteria>
<div class="lui_list_operation">
	<div style='color: #979797;float: left;padding-top:1px;'>
		${ lfn:message('list.orderType') }：
	</div>
	<div style="float:left">
		<div style="display: inline-block;vertical-align: middle;">
			<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" >
				<list:sortgroup>
					<list:sort property="kmCollaborateMain.docCreateTime" text="${lfn:message('km-collaborate:kmCollaborateLogs.docCreateTime')}" group="sort.list" value="down"></list:sort>
					<list:sort property="kmCollaborateMain.docReadCount" text="${lfn:message('km-collaborate:kmCollaborateMain.docReadCount')}" group="sort.list"></list:sort>
					<list:sort property="kmCollaborateMain.docReplyCount" text="${lfn:message('km-collaborate:kmCollaborateMain.docReplyCount')}" group="sort.list"></list:sort>
				</list:sortgroup>
			</ui:toolbar>
		</div>
	</div>
	<div style="float:left;">	
		<list:paging layout="sys.ui.paging.top" > 		
		</list:paging>
	</div>
</div>
	
<ui:fixed elem=".lui_list_operation"></ui:fixed>
<list:listview id="listview">
	<ui:source type="AjaxJson">
			{url:'/sys/subordinate/sysSubordinate.do?method=list&modelName=com.landray.kmss.km.collaborate.model.KmCollaborateMain&orgId=${JsParam.orgId}'}
	</ui:source>
	<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
		rowHref="/sys/subordinate/sysSubordinate.do?method=view&modelId=!{fdId}&modelName=com.landray.kmss.km.collaborate.model.KmCollaborateMain" name="columntable">
		<list:col-checkbox name="List_Selected" headerStyle="width:5%"></list:col-checkbox>
		<list:col-serial title="${ lfn:message('page.serial')}" headerStyle="width:5%"></list:col-serial>
		<list:col-auto props="fdIsLook;fdIsPriority;fdHasAttachment"></list:col-auto>
		<list:col-auto props="docSubject;fdNumber;fdCategory.fdName;docCreator.fdName;docReadCount;docReplyCount;docCreateTime;docStatus;attend"></list:col-auto>
	</list:colTable>
</list:listview>
<list:paging></list:paging>
