<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/component/locker/resource/jNotify.jquery.css"/>" media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/sys/modeling/base/resources/css/view.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/sys/modeling/base/resources/css/override.css"/>" />
<style>
	.model-view-panel-table .td_normal_title{
		color:#666666;
		text-align: left
	}
	.model-view-panel-table .model-view-panel-table-td{
		color:#333333;
		font-size:14px;
	}
	.lui_custom_list_boxs{
	  	border-top: 1px solid #d5d5d5;
		position:fixed;
		bottom:0;
		width:100%;
		background-color: #fff;
		z-index:1000;
		height:63px;
	  }
	  .goformdesign{
	  background-color:#4285f4;
	  width:fit-content;
	  color: white;
	  padding:5px;
	  }
	/*=== webkit浏览器滚动条样式修改 Starts =============================================*/
	::-webkit-scrollbar{
	  width:8px;
	  height:8px;
	  background-color: #d1d1d1;
	  -webkit-transition: background-color .3s ease-in-out;
	          transition: background-color .3s ease-in-out;
	}
	::-webkit-scrollbar:hover{
	  background-color:#d1d1d1;
	}
	::-webkit-scrollbar-thumb{
	  background-color:#d1d1d1;
	  height:50px;
	  outline-offset:-1px;
	  outline:1px solid #fff;
	  -webkit-border-radius:5px;
	  border-radius:5px;
	  border-right: 1px solid #fff;
	  border-left: 1px solid #fff;
	  -webkit-transition: background-color .3s ease-in-out;
	          transition: background-color .3s ease-in-out;
	}
	::-webkit-scrollbar-thumb:hover,
	::-webkit-scrollbar-thumb:active{
	  background-color:#9c9c9c;
	  border-right: 1px solid #f1f1f1;
	  border-left: 1px solid #f1f1f1;
	}
	::-webkit-scrollbar-track{
	  background-color:#fff;
	}
	::-webkit-scrollbar-track:hover{
	  background-color:#f1f1f1;
	  -webkit-box-shadow: inset 0 0 3px rgba(0,0,0,.15);
	          box-shadow: inset 0 0 3px rgba(0,0,0,.15);
	}
	/*=== webkit浏览器滚动条样式修改 Ends ===============================================*/
</style>
<script type="text/javascript" src="<c:url value="/component/locker/resource/jNotify.jquery.js"/>"></script>
<script>
	Com_IncludeFile('jquery.js');
	Com_IncludeFile("dialog.css","${LUI_ContextPath}/sys/modeling/base/resources/css/","css",true);
</script>
	
<center>
<c:choose> 
	<c:when test="${not empty sysFormDbTableForm.fdFormName and not empty sysFormDbTableForm.fdTable}">
	<table class="tb_simple model-view-panel-table" width="95%">
		<tr>
			<td class="td_normal_title" width="10%">
				${lfn:message('sys-modeling-base:modeling.model.form.name')}
			</td>
			<td class="model-view-panel-table-td"  width="90%">
				${sysFormDbTableForm.fdFormName}
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="10%">
				${lfn:message('sys-modeling-base:modeling.model.fdTableName')}
			</td>
			<td class="model-view-panel-table-td" width="90%">
				${sysFormDbTableForm.fdTable}
			</td>
		</tr>
	</table>
	<c:if test="${not empty sysFormDbTableForm.fdColumns}">
	<table id="columnTable" class="tb_normal" width="95%">
		<tr class="tr_normal_title">
			<td width="25px" rowspan="2"><kmss:message key="sys-xform:sysFormDbTable.No" /></td>
			<td width="20%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdName" /></td>
			<td width="20%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdColumn" /></td>
			<td width="5%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdLength" /></td>
			<td width="25%" colspan="2"><kmss:message key="sys-xform:sysFormDbColumn.type" /></td>
			<td width="30%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.info" /></td>
		</tr>
		<tr class="tr_normal_title">
			<td width=""><kmss:message key="sys-xform:sysFormDbColumn.type.db" /></td>
			<td width=""><kmss:message key="sys-xform:sysFormDbColumn.type.java" /></td>
		</tr>
		<c:forEach items="${sysFormDbTableForm.fdColumns}" var="column" varStatus="vstatus">
		<c:if test="${column.fdIsEnable == 'true'}">
		<tr>
			<td>${vstatus.index + 1}</td>
			<td>
				<c:if test="${column.fdName == 'fdId'}">
				ID
				</c:if>
				${dictNames[column.fdName]}
			</td>
			<td>
				<c:if test="${empty column.fdColumn}">
				${column.fdTableName}
				</c:if>
				<c:if test="${not empty column.fdColumn}">
				${column.fdColumn}
				</c:if>
			</td>
			<td>
				<c:if test="${column.fdLength != '0'}">
				${column.fdLength}  
				</c:if>
				
			</td>
			<td>
				<c:if test="${empty column.fdDataType}">
				<kmss:message key="sys-xform:sysFormDbColumn.fdRelType.table" />
				</c:if>
				<c:if test="${not empty column.fdDataType}">
				${column.fdDataType}
				</c:if>
			</td>
			<td>
				${column.fdType}
			</td>
			<td>
				<c:if test="${column.fdIsPk == 'true'}">
				<kmss:message key="sys-xform:sysFormDbColumn.fdIsPk" />
				</c:if>
				<c:if test="${column.fdRelation == 'oneToMany'}">
				<kmss:message key="sys-xform:sysFormDbColumn.relation.oneToMany" arg0="${column.fdTableName}" />
				</c:if>
				<c:if test="${column.fdRelation == 'manyToMany'}">
				<kmss:message key="sys-xform:sysFormDbColumn.relation.manyToMany" arg0="${column.fdTableName}" />
				</c:if>
				<c:if test="${column.fdRelation == 'manyToOne'}">
					<c:if test="${not empty column.fdModelName}">
					<kmss:message key="sys-xform:sysFormDbColumn.relation.manyToOne_sys" arg0="${column.fdModelText}" />
					</c:if>
					<c:if test="${empty column.fdModelName}">
					<kmss:message key="sys-xform:sysFormDbColumn.relation.manyToOne_table" arg0="${column.fdTableName}" />
					</c:if>
				</c:if>
			</td>
		</tr>
		</c:if>
		</c:forEach>
	</table>
	</c:if>
	<table class="tb_normal" width="95%" id="db_table">
	<tbody>
		<%-- <tr>
			<td class="td_normal_title" width=15%><kmss:message key="sys-xform:sysFormDbTable.fdFormName" /></td>
			<td width=35%>
				${sysFormDbTableForm.fdFormName}
			</td>
			<td class="td_normal_title" width=15%><kmss:message key="sys-xform:sysFormDbTable.fdTable" /></td>
			<td width=35%>
				${sysFormDbTableForm.fdTable}
			</td>
		</tr> --%>
		<%-- <c:if test="${not empty sysFormDbTableForm.fdColumns}">
		<tr>
			<td colspan="4">
			</td>
		</tr>
		</c:if>  --%>
		</tbody>
		<tbody id="subTable">
		<c:forEach items="${sysFormDbTableForm.fdTables}" var="table">
		<!-- 子表 -->
		<tr class="templateTitle">
			<td class="td_normal_title" width=15%>
				<kmss:message key="sys-xform:sysFormDbTable.subFormName" />
			</td>
			<td width=35%>
				${dictNames[table.fdName]}
			</td>
			<td class="td_normal_title" width=15%><kmss:message key="sys-xform:sysFormDbTable.fdTable" /></td>
			<td width=35%>
				${table.fdTable}
			</td>
		</tr>
		<c:if test="${not empty table.fdColumns}">
		<tr>
			<td colspan="4">
			<table class="tb_normal" width="100%">
				<tr class="tr_normal_title">
					<td width="25px" rowspan="2"><kmss:message key="sys-xform:sysFormDbTable.No" /></td>
					<td width="20%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdName" /></td>
					<td width="20%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdColumn" /></td>
					<td width="5%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdLength" /></td>
					<td width="25%" colspan="2"><kmss:message key="sys-xform:sysFormDbColumn.type" /></td>
					<td width="30%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.info" /></td>
				</tr>
				<tr class="tr_normal_title">
					<td width=""><kmss:message key="sys-xform:sysFormDbColumn.type.db" /></td>
					<td width=""><kmss:message key="sys-xform:sysFormDbColumn.type.java" /></td>
				</tr>
				<c:forEach items="${table.fdColumns}" var="column" varStatus="vstatus">
				<c:if test="${column.fdIsEnable == 'true'}">
				<tr>
					<td>${vstatus.index + 1}</td>
					<td>
						<c:if test="${column.fdName == 'fdId'}">
						ID
						</c:if>
						${dictNames[column.fdName]}
					</td>
					<td>
						<c:if test="${empty column.fdColumn}">
						${column.fdTableName}
						</c:if>
						<c:if test="${not empty column.fdColumn}">
						${column.fdColumn}
						</c:if>
					</td>
					<td>
						<c:if test="${column.fdLength != '0'}">
						${column.fdLength}  
						</c:if>
					</td>
					<td>
						<c:if test="${empty column.fdDataType}">
						<kmss:message key="sys-xform:sysFormDbColumn.fdRelType.table" />
						</c:if>
						<c:if test="${not empty column.fdDataType}">
						${column.fdDataType}
						</c:if>
					</td>
					<td>
						${column.fdType}
					</td>
					<td>
						<c:if test="${column.fdIsPk == 'true'}">
						<kmss:message key="sys-xform:sysFormDbColumn.fdIsPk" />
						</c:if>
						<c:if test="${column.fdRelation == 'oneToMany'}">
						<kmss:message key="sys-xform:sysFormDbColumn.relation.oneToMany" arg0="${column.fdTableName}" />
						</c:if>
						<c:if test="${column.fdRelation == 'manyToMany'}">
						<kmss:message key="sys-xform:sysFormDbColumn.relation.manyToMany" arg0="${column.fdTableName}" />
						</c:if>
						<c:if test="${column.fdRelation == 'manyToOne'}">
							<c:if test="${not empty column.fdModelName}">
							<kmss:message key="sys-xform:sysFormDbColumn.relation.manyToOne_sys" arg0="${column.fdModelText}" />
							</c:if>
							<c:if test="${empty column.fdModelName}">
							<kmss:message key="sys-xform:sysFormDbColumn.relation.manyToOne_table" arg0="${column.fdTableName}" />
							</c:if>
						</c:if>
					</td>
				</tr>
				</c:if>
				</c:forEach>
			</table>
			</td>
		</tr>
		</c:if>
		</c:forEach>
		</tbody>
	</table>
	</c:when>
	<c:otherwise>
	<div style="margin-top:15%;">
		<i class="td_normal_blank"></i>
	    <div style="margin:10px;">${lfn:message('sys-modeling-base:modeling.form.FormNotField')}</div>
		<a class="goformdesign" href="${LUI_ContextPath}/sys/modeling/base/modelingAppModel.do?method=frame&fdId=${modelingAppModelForm.fdId}" target="_blank">前往表单设计</a>
	</div>
	</c:otherwise>
	</c:choose>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>