<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/component/locker/resource/jNotify.jquery.css"/>" media="screen" />
<script type="text/javascript" src="<c:url value="/component/locker/resource/jNotify.jquery.js"/>"></script>

<script>
Com_IncludeFile('jquery.js');
</script>
<script>
function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete.formMapping"/>");
	return del;
}
function xform_openIndicatorDiv(win,_id) {
	  win = win|| this;
	  var m = "mask_div";  
	 
	  // 新激活图层  
	  var newDiv = win.document.createElement("div");  
	  newDiv.id = _id;  
	  newDiv.style.position = "absolute";  
	  newDiv.style.zIndex = "9999";  
	  //newDiv.style.width = "200px";  
	  //newDiv.style.height = "300px";  
	  newDiv.style.top = "100px";
	  newDiv.style.left = (parseInt(win.document.body.clientWidth-300)) / 2 + "px"; // 屏幕居中  
	  newDiv.style.background = "#FFFFFF";
	  newDiv.style.filter = "alpha(opacity=60)";
	  newDiv.style.opacity = "0.60";
	  newDiv.style.border = "0px solid #860001";  
	  newDiv.style.padding = "5px";  
	  newDiv.innerHTML = "<span style='font-size:12px;font-weight:bolder'><img  width=160px height=20px  src='"+Com_Parameter.ContextPath+"sys/lbpm/flowchart/images/indicator_long.gif'></img><br/>正在迁移，请稍候...</span>";
	  win.document.body.appendChild(newDiv);  
	  // mask图层  
	  var newMask = win.document.createElement("div");  
	  newMask.id = m;  
	  newMask.style.position = "absolute";  
	  newMask.style.zIndex = "1";  
	  newMask.style.width = win.document.body.clientWidth + "px";  
	  newMask.style.height = win.document.body.clientHeight + "px";  
	  newMask.style.top = "0px";  
	  newMask.style.left = "0px";  
	  newMask.style.background = "#FFFFFF";
	  newMask.style.filter = "alpha(opacity=60)";
	  newMask.style.opacity = "0.60";
	  win.document.body.appendChild(newMask);
return  {newMaskDiv:newMask,infoDiv:newDiv};
}
function xform_clearIndicatorDiv(obj,win){
	win = win|| window;
	win.document.body.removeChild(obj.newMaskDiv);
	win.document.body.removeChild(obj.infoDiv);
}
function moveHistoryData(){
	var rtn= confirm("历史文档数据中和当前映射信息匹配的字段将会被迁移，是否执行历史文档数据迁移？");
	if(!rtn){
		return;
	}
	var xform_div="";
	var win=window;
	$.ajax( {
		url : Com_Parameter.ContextPath+"sys/xform/base/sys_form_db_table/sysFormDbTable.do?method=moveMappingHistoryData&fdId=${param.fdId}",
		type : 'GET',
		async : true,//是否异步
		success : function(json) {
			if(json&&json.total>=0){
				jNotify("迁移结束,总共:"+json.total+"条,成功处理:"+json.success+"条,失败:"+json.error+"条", {
					TimeShown: 3000,
					VerticalPosition: 'top',
					HorizontalPosition:'center',
					ShowOverlay: false,
					ColorOverlay:'#000'
				});
				if(document.getElementById("jNotify")){
					$("#jNotify").css("left",document.body.clientWidth/2-$("#jNotify").width()+"px");
				}
			}
			
		},
		dataType : 'json',
		beforeSend : function() {
			xform_div=xform_openIndicatorDiv(win);
		},
		complete : function() {
			if(xform_div){
				xform_clearIndicatorDiv(xform_div,win);
			}
		},
		error : function(msg) {
			alert('执行迁移过程中出错！');
		}
	});
}
</script>
<kmss:windowTitle
	subjectKey="sys-xform:table.sysFormDbColumn.create.title"
	subject="${sysFormDbTableForm.fdName}"
	moduleKey="sys-xform:table.sysFormDbTable" />
	
	<div id="optBarDiv">
	<c:if test="${not empty reToFormUrl}">
		<input type=button value="<kmss:message key="sys-xform:sysFormDbTable.btn.reToForm" />" onclick="Com_OpenWindow('${reToFormUrl}', '_blank');"/>
	</c:if>
	<kmss:auth
	requestURL="/sys/xform/base/sys_form_db_table/sysFormDbTable.do?method=edit&fdModelName=${param.fdModelName}&fdTemplateModel=${param.fdTemplateModel}&fdFormType=${param.fdFormType}&fdModelId=${param.fdModelId}"
	requestMethod="GET">
		<input type=button value="<kmss:message key="sys-xform:sysFormDbTable.btn.HistoryMap" />" onclick="moveHistoryData();"></input>
	</kmss:auth>
	<%-- 编辑 --%>
	<kmss:auth
	requestURL="/sys/xform/base/sys_form_db_table/sysFormDbTable.do?method=edit&fdModelName=${param.fdModelName}&fdTemplateModel=${param.fdTemplateModel}&fdFormType=${param.fdFormType}&fdModelId=${param.fdModelId}"
	requestMethod="GET">
	<input type=button value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('<c:url value="/sys/xform/base/sys_form_db_table/sysFormDbTable.do">
			<c:param name="method" value="edit"/>
			<c:param name="fdId" value="${param.fdId}"/>
			<c:param name="fdFormId" value="${(empty param.fdTemplateId) ? (param.fdFormId) : (param.fdTemplateId)}"/>
			<c:param name="fdKey" value="${param.fdKey}"/>
			<c:param name="fdModelName" value="${param.fdModelName}"/>
			<c:param name="fdTemplateModel" value="${param.fdTemplateModel}"/>
			<c:param name="fdFormType" value="${param.fdFormType}"/>
			<c:param name="fdModelId" value="${param.fdModelId}"/>
		</c:url>', '_self');">
	</kmss:auth>
	<%-- 删除 --%>
	<kmss:auth
	requestURL="/sys/xform/base/sys_form_db_table/sysFormDbTable.do?method=delete&fdModelName=${param.fdModelName}&fdTemplateModel=${param.fdTemplateModel}&fdFormType=${param.fdFormType}&fdModelId=${param.fdModelId}"
	requestMethod="GET">
	<input type=button value="<bean:message key="button.delete"/>"
		onclick="if(!confirmDelete())return;Com_OpenWindow('<c:url value="/sys/xform/base/sys_form_db_table/sysFormDbTable.do">
			<c:param name="method" value="delete"/>
			<c:param name="fdId" value="${param.fdId}"/>
			<c:param name="fdFormId" value="${(empty param.fdTemplateId) ? (param.fdFormId) : (param.fdTemplateId)}"/>
			<c:param name="fdKey" value="${param.fdKey}"/>
			<c:param name="fdModelName" value="${param.fdModelName}"/>
			<c:param name="fdTemplateModel" value="${param.fdTemplateModel}"/>
			<c:param name="fdFormType" value="${param.fdFormType}"/>
			<c:param name="fdModelId" value="${param.fdModelId}"/>
		</c:url>', '_self');">
	</kmss:auth>
	<%-- 关闭 --%>
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
	</div>
	
	<%-- 显示标题 --%>
	<p class="txttitle">
		<bean:message bundle="sys-xform" key="sysFormTemplate.view.title" />
	</p>
	
	<center>
	<table class="tb_normal" width="95%" id="db_table">
	<tbody>
		<tr>
			<td class="td_normal_title" width=15%><kmss:message key="sys-xform:sysFormDbTable.fdName" /></td>
			<td colspan="3">${sysFormDbTableForm.fdName}</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><kmss:message key="sys-xform:sysFormDbTable.fdFormName" /></td>
			<td width=35%>
				${sysFormDbTableForm.fdFormName}
			</td>
			<td class="td_normal_title" width=15%><kmss:message key="sys-xform:sysFormDbTable.fdTable" /></td>
			<td width=35%>
				${sysFormDbTableForm.fdTable}
			</td>
		</tr>
		<c:if test="${not empty sysFormDbTableForm.fdColumns}">
		<tr>
			<td colspan="4">
			<table id="columnTable" class="tb_normal" width="100%">
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
			</td>
		</tr>
		</c:if> 
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
	</center>

<%@ include file="/resource/jsp/view_down.jsp"%>