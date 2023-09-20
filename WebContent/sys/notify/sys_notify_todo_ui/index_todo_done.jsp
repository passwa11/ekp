<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

 <ui:content id="notifyTododoneContent" title="${ lfn:message('sys-notify:sysNotifyTodo.tab.title3') }" cfg-route="{path:'/processed'}" >
  <list:criteria id="done" channel="list_done">
  	   <list:cri-ref key="fdSubject" ref="criterion.sys.docSubject">
	   </list:cri-ref>

	<!--星标条件-->
	<c:if test="${empty starCon }">
		<list:cri-criterion title="${lfn:message('sys-notify:sysNotifyTodo.todo.star.condition.title') }" key="star" multi="false"> 
			<list:box-select>
				<list:item-select>
					<ui:source type="Static">
						[{text:'${ lfn:message('sys-notify:sysNotifyTodo.todo.star.condition.label') }', value:'1'}]
					</ui:source>
				</list:item-select>
			</list:box-select>
		</list:cri-criterion>
	</c:if>
	<list:cri-criterion title="${lfn:message('sys-notify:sysNotifyTodo.todo.category') }" key="fdCateId" multi="false">
		<list:box-select>
			<list:item-select type="lui/criteria!CriterionSelectDatas"  id="fdCateId_todo_done">
				<ui:source type="Static">
					${cateJsonArr}
				</ui:source>
				<ui:event event="selectedChanged" args="evt">
					var vals = evt.values;
					if (vals.length > 0 && vals[0] != null) {
						LUI('moduleNames_todo_done').setEnable(false);
						
					}else{
						LUI('moduleNames_todo_done').setEnable(true);
					}
				</ui:event>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>

	<list:cri-criterion title="${lfn:message('sys-notify:sysNotifyTodo.moduleName') }" key="fdModelName" multi="true">
		<list:box-select>
			<list:item-select type="lui/criteria!CriterionSelectDatas"  id="moduleNames_todo_done">
				<ui:source type="AjaxJson" >
					{url: "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getModules&oprType=done&parentId=!{value}&fdCateId=${JsParam.fdCateId}"}
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>

	<list:cri-criterion title="${lfn:message('sys-notify:sysNotifyTodo.todo.system') }" key="fdAppName" multi="false">
		<list:box-select>
			<list:item-select type="lui/criteria!CriterionSelectDatas"   cfg-if="param['fdAppName']"  id="appNames_todo_done">
				<ui:source type="AjaxJson" >
					{url: "/sys/notify/sys_notify_category/sysNotifyCategory.do?method=getAppNames&fdAppName=${JsParam.fdAppName}"}
				</ui:source>
					<ui:event event="selectedChanged" args="evt">
					var vals = evt.values;
					if (vals.length > 0 && vals[0] != null) {
						LUI('moduleNames_todo_done').setEnable(false);
						
					}else{
						LUI('moduleNames_todo_done').setEnable(true);
					}
				</ui:event>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>

	
	<list:cri-auto modelName="com.landray.kmss.sys.notify.model.SysNotifyTodo" 
	property="docCreator;fdCreateTime" />
 </list:criteria>
 
 <div class="lui_list_operation">
 		<!-- 全选 -->
 		<div class="lui_list_operation_order_btn">
			<list:selectall channel="list_done"></list:selectall>
		</div>
		<!-- 分割线 -->
		<div class="lui_list_operation_line"></div>
		<!-- 排序 -->
		<div class="lui_list_operation_sort_btn">
	 		<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
			</div>
			<div class="lui_list_operation_sort_toolbar">
				<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="list_done">
					<list:sortgroup>
					<list:sort property="fdCreateTime" text="${lfn:message('sys-notify:sysNotifyTodo.finishDate') }" group="sort.list" value="down"></list:sort>
					<list:sort property="fdLevel" text="${lfn:message('sys-notify:sysNotifyTodo.level') }" group="sort.list"></list:sort>
					<list:sort property="fdSubject" text="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" group="sort.list"></list:sort>
					</list:sortgroup>
				</ui:toolbar>
			</div>
		</div>
		<!-- 分页 -->
		<div class="lui_list_operation_page_top">
			<list:paging layout="sys.ui.paging.top"  channel="list_done"> 
			</list:paging>
		</div>
		<div class="lui_list_operation_toolbar">
			<ui:toolbar count="3">
				<ui:togglegroup order="0">
					<ui:toggle selected="true" icon="lui_icon_s_liebiao" title="${ lfn:message('list.columnTable') }" 
						value="columntable_2"  group="tg_2" text="${ lfn:message('list.columnTable') }" 
						onclick="LUI('listview2').switchType(this.value);">
					</ui:toggle>
				    <ui:toggle icon="lui_icon_s_zaiyao" title="${ lfn:message('list.rowTable') }" 
						group="tg_2" text="${ lfn:message('list.rowTable') }" value="rowtable_2"
						onclick="LUI('listview2').switchType(this.value);">
					</ui:toggle>
				</ui:togglegroup>

				<!--add by wubing date:2016-02-24-->
				<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.star.set')}" onclick="doStar('1')" order="3"></ui:button>	
				<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.star.unset')}" onclick="doStar('0')" order="4"></ui:button>	
			</ui:toolbar>
		</div>
</div>
 	
<list:listview id="listview2" channel="list_done">
	<ui:source type="AjaxJson">
			{url:'/sys/notify/sys_notify_todo/sysNotifyMainIndex.do?method=list&dataType=tododone&fdType=13&fdCateId=${JsParam.fdCateId}${starCon }'}
	</ui:source>
	
	<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
		  name="columntable_2" id="fixedForTable_todoDone">
		<list:col-checkbox name="List_Selected"></list:col-checkbox>
		<list:col-serial></list:col-serial>
		<list:col-html title="${ lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" style="text-align:left">
			{$ <a class="lui_notify_alink" href="${KMSS_Parameter_ContextPath}sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId={%row.fdId%}" target="_blank">{%row['todo.subject4View']%} </a>$}
		</list:col-html>
		<list:col-auto props="star;fdType4View"></list:col-auto>
		<list:col-html headerStyle="width:150px;" title="${ lfn:message('sys-notify:sysNotifyTodo.moduleName') }">
				{$<c:if test="${not empty HtmlParam.fdAppName}">{%row['appName']%}</c:if><c:if test="${empty HtmlParam.fdAppName}">{%row['modelNameText']%}</c:if>$}
		</list:col-html>
		<list:col-auto props="fdCreateTime4View,finishTime4View"></list:col-auto>
	</list:colTable>
	<!-- 摘要视图 -->	
	<list:rowTable isDefault="false"
		rowHref="!{tr_href}" name="rowtable_2" >
			<list:row-template>
		  {$
		  <div class="clearfloat lui_listview_rowtable_summary_content_box">
			<dl>
				<dt>
					<input type="checkbox" data-lui-mark="table.content.checkbox" value="{%row.fdId%}" name="List_Selected"/>
					<span class="lui_listview_rowtable_summary_content_serial">{%row.index%}</span>
				</dt>
			</dl>
	         <dl>		
	            <dt>
					<a href="{%row.tr_href%}" class="textEllipsis com_subject" 
					{%row.read%}
					target="_blank" data-lui-mark-id="{%row.rowId%}">{%row['todo.subject4View']%}</a>
				</dt>		
				
				<dd class="lui_listview_rowtable_summary_content_box_foot_info"	{%row.read%}>
			$}
				if(row['docCreator.fdName']){
					{$
						<span>${lfn:message('sys-notify:sysNotifyTodo.docCreatorName')}：{%row['docCreatorName']%}</span>
					$}
				}
				{$
					<span>{%row['docCreateTimeTitle']%}：{%row['fdCreateTime4View']%}</span>
					<span>${ lfn:message('sys-notify:sysNotifyTodo.moduleName') }：<c:if test="${not empty HtmlParam.fdAppName}">{%row['appName']%}</c:if><c:if test="${empty HtmlParam.fdAppName}">{%row['modelNameText']%}</c:if></span>
				$}
				if(row['lbpmCurrNode.key']){
					{$
						<span>{%row['lbpmCurrNode.key']%}：{%row['lbpmCurrNode.value']%}</span>
					$}
				}
				if(row['docFinishedTime.key']){
					{$
						<span>${lfn:message('sys-notify:sysNotifyTodo.kmPindagateMain.docEndTime')}：{%row['docFinishedTime.value']%}</span>
					$}
				}
				
				
			{$
				<span style="display:inline-block;width:200px;float:right; ">{%row.star%}</span>
				</dd>
			</dl>
		 </div>
		    $}		      
		</list:row-template>
	</list:rowTable>
	
</list:listview> 
	<list:paging channel="list_done"></list:paging>	
 </ui:content>
