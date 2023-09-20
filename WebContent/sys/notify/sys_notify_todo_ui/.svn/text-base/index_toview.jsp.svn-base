<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style type="text/css">
.Ellipsis
{
width: 85%;
  overflow: hidden;
  display:  inline-block;
  white-space: nowrap;
  text-overflow: ellipsis;
  vertical-align:middle;
 
}</style>
 <ui:content id="notifyToviewContent" title="${ lfn:message('sys-notify:sysNotifyTodo.tab.title2') }" cfg-route="{path:'/unread'}">
  <list:criteria id="toview" channel="list_toview">
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
			<list:item-select type="lui/criteria!CriterionSelectDatas"  id="fdCateId_toview">
				<ui:source type="Static">
					${cateJsonArr}
				</ui:source>
				<ui:event event="selectedChanged" args="evt">
					var vals = evt.values;
					if (vals.length > 0 && vals[0] != null) {
						LUI('moduleNames_toview').setEnable(false);
						
					}else{
						LUI('moduleNames_toview').setEnable(true);
					}
				</ui:event>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>

	<list:cri-criterion title="${lfn:message('sys-notify:sysNotifyTodo.moduleName') }" key="fdModelName" multi="true">
		<list:box-select>
			<list:item-select type="lui/criteria!CriterionSelectDatas"  id="moduleNames_toview">
				<ui:source type="AjaxJson" >
					{url: "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getModules&oprType=doing&parentId=!{value}&fdCateId=${JsParam.fdCateId}"}
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>

	<list:cri-criterion title="${lfn:message('sys-notify:sysNotifyTodo.todo.system') }" key="fdAppName" multi="false">
		<list:box-select>
			<list:item-select type="lui/criteria!CriterionSelectDatas"   cfg-if="param['fdAppName']"  id="appNames_toview">
				<ui:source type="AjaxJson" >
					{url: "/sys/notify/sys_notify_category/sysNotifyCategory.do?method=getAppNames&fdAppName=${JsParam.fdAppName}"}
				</ui:source>
					<ui:event event="selectedChanged" args="evt">
					var vals = evt.values;
					if (vals.length > 0 && vals[0] != null) {
						LUI('moduleNames_toview').setEnable(false);
						
					}else{
						LUI('moduleNames_toview').setEnable(true);
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
			<list:selectall channel="list_toview"></list:selectall>
		</div>
		<!-- 分割线 -->
		<div class="lui_list_operation_line"></div>
		<!-- 排序 -->
		<div class="lui_list_operation_sort_btn">
			<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
			</div>
			<div class="lui_list_operation_sort_toolbar">
				<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="list_toview">
					<list:sortgroup>
					<list:sort property="fdCreateTime" text="${lfn:message('sys-notify:sysNotifyTodo.docCreateTime.1') }" group="sort.list" value="down"></list:sort>
					<list:sort property="fdLevel" text="${lfn:message('sys-notify:sysNotifyTodo.level') }" group="sort.list"></list:sort>
					<list:sort property="fdSubject" text="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" group="sort.list"></list:sort>
					</list:sortgroup>
				</ui:toolbar>
			</div>
		</div>
		<!-- 分页 -->
		<div class="lui_list_operation_page_top">
			<list:paging layout="sys.ui.paging.top"  channel="list_toview"> 
			</list:paging>
		</div>
		<div class="lui_list_operation_toolbar">
			<ui:toolbar count="3">
					<ui:togglegroup order="0">
						<ui:toggle icon="lui_icon_s_liebiao" title="${ lfn:message('list.columnTable') }" 
							selected="true" value="columntable_3"  group="tg_3" text="${ lfn:message('list.columnTable') }" 
							onclick="LUI('listview3').switchType(this.value);">
						</ui:toggle>
						<ui:toggle icon="lui_icon_s_zaiyao" title="${ lfn:message('list.rowTable') }" 
							 group="tg_3" text="${ lfn:message('list.rowTable') }" value="rowtable_3"
							onclick="LUI('listview3').switchType(this.value);">
						</ui:toggle>
					</ui:togglegroup>
					<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.finish')}" onclick="mngDelete()" order="2"></ui:button>	
					<!--add by wubing date:2016-02-24-->
					<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.star.set')}" onclick="doStar('1')" order="3"></ui:button>	
					<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.star.unset')}" onclick="doStar('0')" order="4"></ui:button>	
				</ui:toolbar>
		</div>
	</div>
	
	<list:listview id="listview3" channel="list_toview">
		<ui:source type="AjaxJson">
				{url:'/sys/notify/sys_notify_todo/sysNotifyMainIndex.do?method=list&dataType=toview&fdType=2&fdCateId=${JsParam.fdCateId}${starCon }'}
		</ui:source>
		<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
			 name="columntable_3" id="fixedForTable_toView">
			<list:col-checkbox name="List_Selected"></list:col-checkbox>
			<list:col-serial></list:col-serial>
			<list:col-html   title="${ lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" style="text-align:left">
				{$ <a class="lui_notify_alink" onclick="onNotifyClick(this,'{%row._fdType%}')" data-href="${KMSS_Parameter_ContextPath}sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId={%row.fdId%}" target="_blank">{%row['todo.subject4View']%} </a>$}
			</list:col-html>
			<list:col-auto props="star;fdType4View"></list:col-auto>
			<list:col-html headerStyle="width:150px;" title="${ lfn:message('sys-notify:sysNotifyTodo.moduleName') }">
				{$<c:if test="${not empty HtmlParam.fdAppName}">{%row['appName']%}</c:if><c:if test="${empty HtmlParam.fdAppName}">{%row['modelNameText']%}</c:if>$}
			</list:col-html>
			<list:col-auto props="docCreatorName;fdCreateTime4View"></list:col-auto>
		</list:colTable>
		 <!-- 摘要视图 -->	
		<list:rowTable isDefault="false"
			rowHref="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=!{fdId}" name="rowtable_3" >
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
						<a onclick="onNotifyClick(this,'{%row._fdType%}')" data-href="${KMSS_Parameter_ContextPath}sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId={%row.fdId%}" class="textEllipsis com_subject" 
						{%row.read%}
						target="_blank" data-lui-mark-id="{%row.rowId%}">{%row['todo.subject4View']%}</a>
					</dt>		
					
					<dd class="lui_listview_rowtable_summary_content_box_foot_info" {%row.read%}>
						$}
							if(row['docCreator.fdName']){
								{$
									<span>{%row['docCreatorNameTitle']%}：{%row['docCreatorName']%}</span>
								$}
							}
								{$
									<span>{%row['docCreateTimeTitle']%}：{%row['fdCreateTime4View']%}</span>
									<span>{%row['moduleNameTitle']%}：<c:if test="${not empty HtmlParam.fdAppName}">{%row['appName']%}</c:if><c:if test="${empty HtmlParam.fdAppName}">{%row['modelNameText']%}</c:if></span>
								$}
							if(row['lbpmCurrNode.key']){
								{$
									<span>{%row['lbpmCurrNode.key']%}：{%row['lbpmCurrNode.value']%}</span>
								$}
							}
							if(row['docFinishedTime.key']){
								{$
									<span>{%row['docFinishedTime.key']%}：{%row['docFinishedTime.value']%}</span>
								$}
							}
							
						{$
						
							<!--//add by wubing date:2016-02-24-->
							<span  style="display:inline-block;width:200px;float:right; ">{%row.star%}</span>

					</dd>
				</dl>
			 </div>
			    $}		      
			</list:row-template>
		</list:rowTable>
		
	</list:listview> 
 	<list:paging channel="list_toview"></list:paging>
  </ui:content>

