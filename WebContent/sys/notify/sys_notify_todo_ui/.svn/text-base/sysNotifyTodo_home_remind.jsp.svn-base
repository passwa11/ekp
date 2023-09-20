<!-- 消息部件顶部提醒内容（  “您有 N 条 需处理事项” 、邮件数量、 刷新图标 、快速审批图标   ）  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="__fdAppName" value="${HtmlParam.fdAppName}"></c:set>
<%
		//模块首页筛选条件
		String __dataType = (String)request.getAttribute("dataType");
		boolean isDone = StringUtil.isNotNull(__dataType)&& __dataType.indexOf("done")!=-1;
		String _fdAppName = (String)pageContext.getAttribute("__fdAppName");
		String fdType = request.getParameter("fdType");
		String notifyCategoryId = request.getParameter("notifyCategoryId");
		pageContext.setAttribute("cateId",notifyCategoryId);
		String crqPrefix="";
		String jPath = "";
		if(isDone){
			crqPrefix = "toviewdone".equals(__dataType)?"#cri.list_toview_done.q=":"#cri.list_done.q=";
			//jPath = "toviewdone".equals(__dataType)? "#j_path=%2Fread":"#j_path=%2Fprocessed";
		}else{
			crqPrefix = "toview".equals(__dataType)?"#cri.list_toview.q=":"#cri.list_todo.q=";
			//jPath = "toview".equals(__dataType)? "#j_path=%2Funread":"#j_path=%2Fpending";
		}
		StringBuffer _crqCon = new StringBuffer();
		List<String> _crqConList = new ArrayList<String>();
		if(StringUtil.isNotNull(_fdAppName)){
			request.setAttribute("_fdAppName", "&fdAppName="+_fdAppName);
			_crqConList.add("fdAppName:"+_fdAppName);
		}
		if("todo".equals(__dataType)){
			if("1".equals(fdType)) _crqConList.add("fdType:1");
			if("3".equals(fdType)) _crqConList.add("fdType:3");
		}
		for(String con : _crqConList){
			if(StringUtil.isNotNull(_crqCon.toString())){
				_crqCon.append(";"+con);
			}else{
				_crqCon.append(con);
			}
		}
		crqPrefix = StringUtil.isNotNull(_crqCon.toString()) ? crqPrefix.replace("#", jPath + "&")+_crqCon.toString():jPath;
		request.setAttribute("crqPrefix", crqPrefix);
%>

<% /**  定义  是否显示邮件标识  变量（默认为true）   **/ %>
<c:set var="isShowEmail" value="true"></c:set>

<% /**  定义  图文事项是否已完成  变量（默认为false）   **/ %>
<c:set var="_isGraphicDone" value="false"></c:set>

<% /** --------Start--------- 获取应该展示的事项类型（需处理待办、待阅读事项、待办事项、暂挂事项、已阅读事项、已处理事项）--------Start--------- **/ %>
<c:choose>
    <c:when test="${portletType=='graphic'}"> <% /**  图文类型的门户组件   **/ %>
            <c:set var="has_todo" value="false"></c:set>
            <c:set var="has_tododone" value="false"></c:set>
            <c:set var="has_toview" value="false"></c:set>
            <c:set var="has_toviewdone" value="false"></c:set>
            <c:set var="has_done" value="false"></c:set>
			<c:forEach items="${requestScope.dataTypeList}" var="dType">
				 <c:if test="${dType eq 'todo'}"> <c:set var="has_todo" value="true" /> </c:if>
				 <c:if test="${dType eq 'tododone'}"> <c:set var="has_tododone" value="true" /> </c:if>
				 <c:if test="${dType eq 'toview'}"> <c:set var="has_toview" value="true" /> </c:if>
				 <c:if test="${dType eq 'toviewdone'}"> <c:set var="has_toviewdone" value="true" /> </c:if>
				 <c:if test="${dType eq 'done'}"> <c:set var="has_done" value="true" /> </c:if>
			</c:forEach>
    
		    <c:choose>
			    <c:when test="${pageScope.has_todo==true && pageScope.has_toview==false}">
			        <!-- 需处理待办 -->
			        <c:set var="infoTip" value="sysNotifyTodo.toDo.info"></c:set>
			        <!-- 是否需要显示“快速审批” （是） -->
					<c:set var="fastTodo" value="true"></c:set> 
			    </c:when>
			    <c:when test="${pageScope.has_todo==false && pageScope.has_toview==true}">
			        <!-- 待阅读事项 -->
					<c:set var="infoTip" value="sysNotifyTodo.toView.info"></c:set>
			    </c:when>
			    <c:when test="${pageScope.has_tododone==false && pageScope.has_toviewdone==true}">
			        <!-- 已阅读事项 -->
					<c:set var="infoTip" value="sysNotifyTodo.done.toViewDone"></c:set>
					<!-- 是否需要显示“邮件数量” （否） -->
					<c:set var="isShowEmail" value="false"></c:set>
					<c:set var="_isGraphicDone" value="true"></c:set>
			    </c:when>
			    <c:when test="${pageScope.has_done==true || pageScope.has_tododone==true}">
			        <!-- 已处理事项 -->
					<c:set var="infoTip" value="sysNotifyTodo.done.label.2"></c:set>
					<!-- 是否需要显示“邮件数量” （否） -->
					<c:set var="isShowEmail" value="false"></c:set>
					<c:set var="_isGraphicDone" value="true"></c:set>
			    </c:when>				    				    
			    <c:otherwise>
			        <!-- 消息通知事项 -->
			        <c:set var="infoTip" value="sysNotifyTodo.todo.notify"></c:set>
			    </c:otherwise>
		    </c:choose>    
    </c:when>
    <c:otherwise><% /**  非图文类型的门户组件   **/ %>
			<c:if test="${param.fdType == 1 || param.fdType == 13 || empty param.fdType && empty param.finish}">
				<!-- 需处理待办 -->
				<c:set var="infoTip" value="sysNotifyTodo.toDo.info"></c:set>
				<c:set var="_dataType" value="todo"></c:set>
				<c:set var="fastTodo" value="true"></c:set>
			</c:if>
			<c:if test="${param.fdType == 2}">
			    <!-- 待阅读事项 -->
				<c:set var="infoTip" value="sysNotifyTodo.toView.info"></c:set>
				<c:set var="_dataType" value="toview"></c:set>
			</c:if>
			<c:if test="${param.fdType == 14}">
			    <!-- 待办事项 -->
				<c:set var="infoTip" value="sysNotifyTodo.type.list.all.info"></c:set>
				<c:set var="_dataType" value="todo"></c:set>
				<c:set var="fastTodo" value="true"></c:set>
			</c:if>
			<c:if test="${param.fdType == 3}">
			    <!-- 暂挂事项 -->
				<c:set var="infoTip" value="sysNotifyTodo.suspend.info"></c:set>
				<c:set var="_dataType" value="todo"></c:set>
			</c:if>
			<c:if test="${param.finish == 1}">
				<c:set var="isShowEmail" value="false"></c:set>
				<c:if test="${param.dataType=='toviewdone' }">
				    <!-- 已阅读事项 -->
					<c:set var="infoTip" value="sysNotifyTodo.done.toViewDone"></c:set>
					<c:set var="_dataType" value="toviewdone"></c:set>
				</c:if>
				<c:if test="${param.dataType!='toviewdone' }">
				    <!-- 已处理事项 -->
					<c:set var="infoTip" value="sysNotifyTodo.done.label.2"></c:set>
					<c:set var="_dataType" value="tododone"></c:set>
				</c:if>
			</c:if>    
	</c:otherwise>
</c:choose>   
<% /** --------End--------- 获取应该展示的事项类型（需处理待办、待阅读事项、待办事项、暂挂事项、已阅读事项、已处理事项）--------End--------- **/ %>

<% /** --------Start--------- 获取应该展示的事项类型对应的消息列表页面路径--------Start--------- **/ %>
<c:if test="${not empty requestScope.dataType || not empty pageScope._dataType}">
    <c:set var="param_data_type" value="${not empty requestScope.dataType ? requestScope.dataType : pageScope._dataType}"></c:set>
        <c:set var="itemPagePath" value="#j_path=%2Fprocess&dataType=${pageScope.param_data_type}"></c:set> <!-- #122562 15版本后调整了待办首页归类，导致没法精确的跳转，先给个默认首页 -->
    <c:if test="${pageScope.param_data_type eq 'todo' }"> <% /**  待办   **/ %>
        <c:set var="itemPagePath" value="#j_path=%2Fprocess&dataType=${pageScope.param_data_type}"></c:set>
    </c:if>
    <c:if test="${pageScope.param_data_type eq 'toview' }"> <% /**  待阅   **/ %>
        <c:set var="itemPagePath" value="#j_path=%2Fread&dataType=${pageScope.param_data_type}"></c:set>
    </c:if> 
    <c:if test="${pageScope.param_data_type eq 'tododone' }"> <% /**  已办   **/ %>
        <c:set var="itemPagePath" value="#j_path=%2Fprocess&dataType=${pageScope.param_data_type}"></c:set>
    </c:if> 
    <c:if test="${pageScope.param_data_type eq 'toviewdone' }"> <% /**  已阅   **/ %>
        <c:set var="itemPagePath" value="#j_path=%2Fread&dataType=${pageScope.param_data_type}"></c:set>
    </c:if>              
</c:if>
<% /** --------End--------- 获取应该展示的事项类型对应的消息列表页面路径--------End--------- **/ %>
<!-- 如果是单个分类类型，点击待处理事项时需要把分类id带过去 -->
<c:if test="${displayMode=='singleCategory' }">
	<c:set var="fdCateId" value="&cateId=${cateId}"></c:set>
</c:if>
<div class="lui_dataview_classic_heading">

    <% /**  显示信件图标   **/ %>
    <span id="lui_notify_todo_tip" class="lui_dataview_classic_icon"></span>
    
	<span class="lui_notify_count_tip">
        <% /**  您 没有   **/ %>
		<c:if test="${totalrows==0}">
			<bean:message bundle="sys-notify" key="sysNotifyTodo.home.you" />
			<b><bean:message bundle="sys-notify" key="sysNotifyTodo.home.notHave" /></b>
		</c:if>
		<% /**  您有 “N” 条   **/ %>
		<c:if test="${totalrows>0}">
			<bean:message bundle="sys-notify" key="sysNotifyTodo.home.youHave" />
			<b id="toViewCount">${totalrows}</b> <c:if test="${totalrows>1}"><bean:message bundle="sys-notify" key="sysNotifyTodo.home.counts" /></c:if><c:if test="${totalrows==1}"><bean:message bundle="sys-notify" key="sysNotifyTodo.home.count" /></c:if>
		</c:if>	
		<% /**  事项类型超链接（需处理待办、待阅读事项、待办事项、暂挂事项、已阅读事项、已处理事项）  **/ %>
		<c:if test="${not empty infoTip}">
			<a class="lui_notify_link_txt" href="${LUI_ContextPath }/sys/notify/index.jsp${pageScope.itemPagePath}${_fdAppName}${crqPrefix}${fdCateId}" target="_blank">
			    <bean:message bundle="sys-notify" key="${infoTip}" />
			</a>
		</c:if>
	</span>
	
	<% /**  显示 邮件数量    **/ %>
	<c:if test="${isShowEmail=='true'}">
		<%@ include file="/sys/notify/sys_notify_todo_ui/email.jsp"%>
	</c:if>
	
	<% /**  显示 刷新图标    **/ %>
	<span class="lui_refreshIcon"><a onclick="location.reload();onPublishPortalRefresh()"><img src="../resource/images/refresh.png" alt='<bean:message bundle="sys-notify" key="sysNotifyTodo.portal.refresh" />'/></a></span>
	
	<% /**  显示 快速审批图标    **/ %>
	<%
		boolean isCanFastTodo = false;
		String fastTodoIds = new LbpmSettingDefault().getFastTodoIds();
		if(StringUtil.isNull(fastTodoIds) || UserUtil.checkUserIds(Arrays.asList(fastTodoIds.split(";")))){
			isCanFastTodo = true;
		}
		request.setAttribute("isCanFastTodo",isCanFastTodo);
	%>
	<c:if test="${fastTodo && isCanFastTodo}">
		  <span class="lui_fastApproveIcon" title='<bean:message bundle="sys-notify" key="sysNotifyTodo.tab.title5" />'>
			  <a href="${LUI_ContextPath }/sys/notify/index.jsp#j_path=%2Ffastview${_fdAppName}${crqPrefix}" target="_blank">
			      <img src="${LUI_ContextPath}/sys/notify/resource/images/fastreview.png" alt='<bean:message bundle="sys-notify" key="sysNotifyTodo.portal.refresh" />'/>
			  </a>
		  </span>
	</c:if>

</div>