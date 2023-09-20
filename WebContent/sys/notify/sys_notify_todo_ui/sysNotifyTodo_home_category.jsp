<!-- 渲染   分类类型  列表  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${cateListTotal==0}">
	<!--空值提醒 Starts-->
	<div class="lui-nodata-tips lui-nodata-tips-todo">
		<div class="imgbox"></div>
		<div class="txt">
			<p>
				<bean:message bundle="sys-notify" key="sysNotifyTodo.home.you" />
				&nbsp;<font style="color:#FF6600;"><b><bean:message bundle="sys-notify" key="sysNotifyTodo.home.notHave" /></b></font>&nbsp;
				<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.text" />
			</p>
			<p><bean:message bundle="sys-notify" key="sysNotifyTodo.home.noData.info" /></p>
		</div>
	</div>
	<!--空值提醒 Ends-->
</c:if>

<c:if test="${cateListTotal>0 }">
	<c:choose>
	<%---------------------  显示分类与消息内容（Start）     ---------------------%>
	<c:when test="${ displayMode=='widthData' }">
		<ui:tabpanel layout="sys.ui.tabpanel.light" style="" id="catetab" scroll="false" >
			<c:forEach var="cate" items="${cateList}">
				<c:set var="_cateCountTxt" value="${lfn:message('sys-notify:sysNotifyTodo.cate.numbers')}"/>
				<c:if test="${cate.cateCount==1 || cate.cateCount==0}">
					<c:set var="_cateCountTxt" value="${lfn:message('sys-notify:sysNotifyTodo.cate.number')}"/>
				</c:if>
				<ui:content title="${cate.fdName} ${cate.cateCount} ${_cateCountTxt}">
					<ui:ajaxtext>
					<ui:dataview>
						<ui:source type="AjaxJson">
							{"url":"/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=listDatasByCate&home=${JsParam.home}&finish=${JsParam.finish}&rowsize=${JsParam.rowsize}&cateFdId=${cate.fdId}&dataType=${JsParam.dataType}&LUIID=${JsParam.LUIID}&sortType=${JsParam.sortType}&displayMode=${JsParam.displayMode}"}
						</ui:source>
						<ui:render type="Template">
							{$ <div class="lui_dataview_classic"> $}
								var dataType = "${JsParam.dataType}";
								var _isDone = false;
								if(data.length==0){
									{$
										<div class="lui_dataview_classic_row clearfloat">
												<div class="clearfloat">
 												<!--空值提醒 Starts-->
												<div class="lui-nodata-tips lui-nodata-tips-todo">
													<div class="imgbox"></div>
													<div class="txt">
														<p>
												<bean:message bundle="sys-notify" key="sysNotifyTodo.home.you" />&nbsp;<font style="color:#FF6600;"><b>
													<bean:message bundle="sys-notify" key="sysNotifyTodo.home.notHave" /></b></font>&nbsp;
									$}
									if(dataType=="todo"){
										{$<bean:message bundle="sys-notify" key="sysNotifyTodo.toDo.info" />$}
									}else if(dataType=="toview"){
										{$<bean:message bundle="sys-notify" key="sysNotifyTodo.toView.info" />$}
									}else if(dataType=="tododone" || dataType=='done' || dataType=='tododone;toviewdone'){
										{$<bean:message bundle="sys-notify" key="sysNotifyTodo.done.label.2" />$}
									}else if(dataType=="toviewdone"){
										{$<bean:message bundle="sys-notify" key="sysNotifyTodo.done.toViewDone" />$}
									}else{
										{$<bean:message bundle="sys-notify" key="sysNotifyTodo.todo.notify" />$}
									}
									{$
														</p>
														<p><bean:message bundle="sys-notify" key="sysNotifyTodo.home.noData.info" /></p>
													</div>
												</div>
												<!--空值提醒 Ends-->
											</div>
										</div>
									$}
								}else{
									if(dataType=="tododone" || dataType=='done' || dataType=='tododone;toviewdone' || dataType=="toviewdone"){
										_isDone = true;
									}
								}
								
								for(var i = 0;i<data.length;i++){
									{$	
										<div class="lui_dataview_classic_row clearfloat">
											<span class="lui_notify_title_icon lui_notify_content_{%data[i].fdType%}"></span>
												<div class="lui_dataview_classic_textArea clearfloat">
									$}
										if(_isDone==true){
											{$ <a title='{%data[i].subject4View%}' class="lui_dataview_classic_link" target="_blank" $}
											if(data[i].fdLink){
												{$ href="${LUI_ContextPath}<c:url value='{%data[i].fdLink%}'/>" $}
											}
										}else{
											{$ <a title='{%data[i].subject4View%}' class="lui_dataview_classic_link" target="_blank" data-href="${LUI_ContextPath}/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId={%data[i].fdId%}" onclick="onNotifyClick(this,'{%data[i].fdType%}');onNotifyCountClick('${cate.fdId }','{%data[i].fdType%}')" $}
										}
										
										if(_isDone !=true){
											if(data[i].read==true){
												{$ style='color:#999;' $}
											}
										}
										{$ > $}
										if(_isDone==true){
										  	{$<span class="lui_notify_done_flag">${lfn:message('sys-notify:sysNotifyTodo.subject.done')}</span>
										  	$}
										  }
										  if(data[i].lbpmPress){
										  	{$<span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.process.press')}</span>$}
										  }
										  if(data[i].fdType==3){
										  	{$<span class="lui_notify_pending">${lfn:message('sys-notify:sysNotifyTodo.type.suspend')}</span>$}
										  }
										  if(data[i].fdLevel==1){
										  	{$<span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}</span>$}
										  }
										  if(data[i].fdLevel==2){
										  	{$<span class="lui_notify_level lui_notify_level2">${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}</span>$}
										  }
										
										{$	{%data[i].subject4View%}$}
									 	{$		</a>
									 		</div>
									 	</div>
									$}
								}
							{$
								</div>
							$}
						</ui:render>
						<ui:event event="load">
							domain.autoResize();
						</ui:event>
					</ui:dataview>
					</ui:ajaxtext>
				</ui:content>
			</c:forEach>
		</ui:tabpanel>
	</c:when>
	<%---------------------  显示分类与消息内容（End）     ---------------------%>
	
	<%---------------------  只显示分类（Start）     ---------------------%>
	<c:when test="${ displayMode=='noData' }">
		<div style="padding:10px;">
		<ul class="lui_toolbar_db_list">
		    <c:set var="catePagePath" value="#j_path=%2Fprocess&dataType=todo"></c:set>
			<c:set var="criCate" value="&cri.list_todo.q=fdCateId:" />
			<c:if test="${HtmlParam.dataType=='toview'}">
				<c:set var="criCate" value="&cri.list_toview.q=fdCateId:" />
				<c:set var="catePagePath" value="#j_path=%2Fread&dataType=toview"></c:set>
			</c:if>
			<c:if test="${HtmlParam.dataType=='toviewdone'}">
				<c:set var="criCate" value="&cri.list_toview_done.q=fdCateId:" />
				<c:set var="catePagePath" value="#j_path=%2Fread&dataType=toviewdone"></c:set>
			</c:if>
			<c:if test="${fn:contains(HtmlParam.dataType, 'tododone')}">
				<c:set var="criCate" value="&cri.list_done.q=fdCateId:" />
				<c:set var="catePagePath" value="#j_path=%2Fprocess&dataType=tododone"></c:set>
			</c:if>
			<c:if test="${HtmlParam.dataType=='suspend'}">
				<c:set var="criCate" value="&cri.list_todo.q=fdType:3;fdCateId:" />
				<c:set var="catePagePath" value="#j_path=%2Fprocess&dataType=todo"></c:set>
			</c:if>
			<c:forEach var="cate" items="${cateList}">
				<li><span><a target="_blank" href="${LUI_ContextPath}/sys/notify/index.jsp${pageScope.catePagePath}${criCate}${cate.fdId}" ><em>${cate.cateCount}</em>${cate.fdName}</a></span></li>
			</c:forEach>
		</ul>
		<div style="clear: both;"></div>
		</div>	
	</c:when>
	<%---------------------  只显示分类（End）     ---------------------%>
	
	<%---------------------  显示单个分类 （Start）     ---------------------%>
	<c:when test="${ displayMode=='singleCategory' }">
		<ui:ajaxtext>
		<ui:dataview>
			<ui:source type="AjaxJson">
				{"url":"/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=listDatasByCate&home=${JsParam.home}&finish=${JsParam.finish}&rowsize=${JsParam.rowsize}&cateFdId=${JsParam.notifyCategoryId}&dataType=${JsParam.dataType}&LUIID=${JsParam.LUIID}&sortType=${JsParam.sortType}&displayMode=${JsParam.displayMode}"}
			</ui:source>
			<ui:render type="Template">
				{$ <div class="lui_dataview_classic"> $}
					var dataType = "${JsParam.dataType}";
					var _isDone = false;
					if(data.length!=0){
						if(dataType=="tododone" || dataType=='done' || dataType=='tododone;toviewdone' || dataType=="toviewdone"){
							_isDone = true;
						}
					}
					
					for(var i = 0;i<data.length;i++){
						{$	
							<div class="lui_dataview_classic_row clearfloat">
								<span class="lui_notify_title_icon lui_notify_content_{%data[i].fdType%}"></span>
									<div class="lui_dataview_classic_textArea clearfloat">
						$}
							if(_isDone==true){
								{$ <a title='{%data[i].subject4View%}' class="lui_dataview_classic_link" target="_blank" $}
								if(data[i].fdLink){
									{$ href="${LUI_ContextPath}<c:url value='{%data[i].fdLink%}'/>" $}
								}
							}else{
								{$ <a title='{%data[i].subject4View%}' class="lui_dataview_classic_link" target="_blank" data-href="${LUI_ContextPath}/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId={%data[i].fdId%}" onclick="onNotifyClick(this,'{%data[i].fdType%}');onNotifyCountClick('${JsParam.notifyCategoryId}','{%data[i].fdType%}')" $}
							}
							
							if(_isDone !=true){
								if(data[i].read==true){
									{$ style='color:#999;' $}
								}
							}
							{$ > $}
							if(_isDone==true){
							  	{$<span class="lui_notify_done_flag">${lfn:message('sys-notify:sysNotifyTodo.subject.done')}</span>
							  	$}
							  }
							  if(data[i].lbpmPress){
							  	{$<span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.process.press')}</span>$}
							  }
							  if(data[i].fdType==3){
							  	{$<span class="lui_notify_pending">${lfn:message('sys-notify:sysNotifyTodo.type.suspend')}</span>$}
							  }
							  if(data[i].fdLevel==1){
							  	{$<span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}</span>$}
							  }
							  if(data[i].fdLevel==2){
							  	{$<span class="lui_notify_level lui_notify_level2">${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}</span>$}
							  }
							
							{$	{%data[i].subject4View%}$}
						 	{$		</a>
						 		</div>
						 	</div>
						$}
					}
				{$
					</div>
				$}
			</ui:render>
				<ui:event event="load">
					domain.autoResize();
				</ui:event>
		</ui:dataview>
		</ui:ajaxtext>	
	</c:when>	
	<%---------------------  显示单个分类 （End）     ---------------------%>
	
  </c:choose>
</c:if>