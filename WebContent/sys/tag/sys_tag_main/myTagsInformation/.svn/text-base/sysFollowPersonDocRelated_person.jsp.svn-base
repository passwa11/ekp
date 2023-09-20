<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 筛选器 -->
	<list:criteria channel="docTag">
		<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" ></list:cri-ref>
		<c:choose>  
		   <c:when test="${not empty JsParam.fdTagName}">
		   </c:when>  
		   <c:otherwise> 
			<list:cri-criterion title="${lfn:message('sys-tag:table.sysTagMain.fdTagName') }" channel="docTag" key="followtype"> 
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas">
						<ui:source type="AjaxJson">
							{url: "/sys/follow/sys_follow_person_doc_related/sysFollowPersonDocRelated.do?method=getDocTagNames"}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			</c:otherwise>  
		</c:choose>
		<list:cri-criterion 
		    title="按状态" 
			key="mydoc" >
			<list:box-select >
				<list:item-select >
					<ui:source type="Static">
						[{text:'${lfn:message("sys-follow:sysFollowRelatedDoc.fdStatus.yes") }', value:'0'},
						{text:'${lfn:message("sys-follow:sysFollowRelatedDoc.fdStatus.no") }',value:'1'}]
					</ui:source>
				</list:item-select>
			</list:box-select>
		</list:cri-criterion>
	</list:criteria>
	
	<!-- 操作栏 -->
	<div class="lui_list_operation">
		<!-- 排序 -->
		<div class="lui_list_operation_order_text">${ lfn:message('list.orderType') }：</div>
		<div class="lui_list_operation_order_btn">
			<ui:toolbar layout="sys.ui.toolbar.sort" channel="docTag">
				<list:sortgroup>
			   		<list:sort property="sysFollowPersonDocRelated.followDoc.docCreateTime" text="阅读时间" group="sort.list" value="down" channel="docTag"></list:sort>
				</list:sortgroup>
			</ui:toolbar>
		</div>
		<!-- mini分页 -->
		<div class="lui_list_operation_page_top">
			<list:paging layout="sys.ui.paging.top" channel="docTag"></list:paging>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
	</div>
	
	<%--list视图--%>
		<list:listview id="docTag_overView" channel="docTag">
			<ui:source type="AjaxJson">
				{url:'/sys/follow/sys_follow_person_doc_related/sysFollowPersonDocRelated.do?method=listPerson&followtype=tag&rowsize=16'}
			</ui:source>
			<%--列表形式--%>
			<list:colTable layout="sys.ui.listview.columntable" name="columntable"
			rowHref="/sys/follow/sys_follow_doc/sysFollowDoc.do?method=view&fdId=!{fdId}">
					<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%"/> 
					<list:col-html title="${ lfn:message('sys-follow:sysFollowDoc.docSubject') }" style="width:45%;text-align:left;padding:0 8px" headerStyle="width:45%">
					 {$	 
						<span class="com_subject">{% row['docSubject']%}</span>
					 $}
					</list:col-html>
					<list:col-html title="${lfn:message('sys-follow:sysFollow.list.from') }" style="width:25%" headerStyle="width:25%">
					 {$	 
						{% row['from']%}
					 $}
					</list:col-html>
					<list:col-html title="${lfn:message('sys-follow:sysFollow.list.status') }" style="width:10%" headerStyle="width:10%">
					 {$	 
						{% row['status']%}
					 $}
					</list:col-html>
					<list:col-html title="${lfn:message('sys-follow:sysFollow.list.time') }" style="width:15%" headerStyle="width:15%">
					 {$	 
						{% row['docCreateTime']%}
					 $}
					</list:col-html>
			</list:colTable>
		</list:listview> 
	 	<list:paging channel="docTag"></list:paging>
