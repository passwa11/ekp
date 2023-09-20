<!DOCTYPE html>
<meta http-equiv="X-UA-Compaticle" content="IE=edge" />
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script>
	Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
	Com_IncludeFile("optbar.js", null, "js");
	Com_IncludeFile("ticJdbcDataExtend_edit_sqlGeneration_script.js", Com_Parameter.ContextPath+'tic/jdbc/tic_jdbc_data_set/', "js", true);
</script>
<body>
	<center>
		<!-- 操作栏 -->
		<div id="optBarDiv">
			<input type="button" value="<bean:message key='button.ok'/>" onclick="buildSql();">
			<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
		</div>
		<!-- 内容 -->
		<div class="mainData_extend_dialog_content" style="margin-top:20px">
			<div class="mainData_extend_dialog_tip">
				<span style="color:red;"></span>
			</div>
			<table class="tb_normal" width="95%">
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.dbTableName"/>
					</td>
					<td width="85%">
						<input type="text" name="extend_tableName" style="width:40%;" class="inputsgl" placeholder="${lfn:message('tic-jdbc:ticJdbcDataSet.sqlTool.plsInputTableName') }" onkeyup="enterTrigleSelect(event,this);" value=""/>
						<input type="button" class="lui_form_button" style="cursor:pointer;" value="${lfn:message('tic-jdbc:ticJdbcDataSet.sqlTool.loadTableData') }" onclick="loadDbtableData();" />
						<img id="mainData_extend_dialog_loading" src="${KMSS_Parameter_ContextPath}tic/jdbc/resource/images/loading.gif" style="margin-left:5px;display:none;" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.selectBlock"/>
					</td>
					<td width="85%">
						<!-- 查询条件 -->
						<div class="mainData_extend_dialog_whereBlock">
							<table id="mainData_extend_dialog_whereTable" class="tb_normal" style="float:left;width:90%;">
								<tr class="mainData_extend_dialog_tableTitle">
									<td width="33%"><bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.attr"/></td>
									<td width="15%"><bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.operator"/></td>
									<td width="30%"><bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.value"/></td>
									<td width="12%"><bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.isDyn"/></td>
									<td width="10%"><a href="javascript:void(0);" onclick="buildWhereHtmlTr();" style="color:#1b83d8;"><bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.add"/></a></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.returnAttr"/>
					</td>
					<td width="85%">
						<!-- 返回值 -->
						<div class="mainData_extend_dialog_returnBlock">
							<table id="mainData_extend_dialog_returnValueTable" class="tb_normal" style="float:left;width:90%;">
								<tr class="mainData_extend_dialog_tableTitle">
									<td width="90%"><bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.attr"/></td>
									<td style="width:10%;"><a href="javascript:void(0);" onclick="buildReturnFieldHtmlTr();" style="color:#1b83d8;"><bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.add"/></a></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.searchAttr"/>
					</td>
					<td width="85%">
						<!-- 搜索 -->
						<div class="mainData_extend_dialog_searchBlock">
							<table id="mainData_extend_dialog_searchTable" class="tb_normal" style="float:left;width:90%;">
									<tr class="mainData_extend_dialog_tableTitle">
										<td width="90%"><bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.attr"/></td>
										<td style="width:10%;"><a href="javascript:void(0);" onclick="buildSearchFieldHtmlTr();" style="color:#1b83d8;"><bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.add"/></a></td>
									</tr>
							</table>
						</div>
					</td>
				</tr>
				<%-- <tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.sqlTool.isPage"/>
					</td>
					<td width="85%">
						<!-- 是否分页 -->
						<div class="mainData_extend_dialog_isPage">
							<label><input type="radio" name="isPage" value="true" checked/><bean:message key="message.yes"/></label>
							<label><input type="radio" name="isPage" value="false" /><bean:message key="message.no"/></label>
						</div>
					</td>
				</tr> --%>
			</table>
		</div>
	</center>
	<script>
		var _mainData_extend_dialog_dbTable = {};
		//清空所有行
		function clearTable(tableId){
			$('#' + tableId + ' tr:not(:first)').remove();
		}
		
		//加载数据库表数据
		function loadDbtableData(){
			var tableName = $("input[name='extend_tableName']").val().trim();
			if(tableName == ''){
				alert("${lfn:message('tic-jdbc:ticJdbcDataSet.sqlTool.dbTableNameCantbeNull') }");
				return;
			}
			var url = Com_Parameter.ContextPath + "tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet.do?method=loadDbtableDatas&fdDbtable=" + tableName + "&fdDataSource=${param.fdDataSource}";
			$("#mainData_extend_dialog_loading").show();
			setTimeout(function(){
				$.ajax({
					url:url,
					type:"POST",
					async:false,
					success:function(result){
						if(result != null){
							result = $.parseJSON(result);
							// result : {"status":xxx,"errlog":xxx,"datas":xxx,"dbtype":xxx}
							// data : {"columnName:":xxx,"columnType":xxxxx}
							// dbtype : 数据库类型
							//只有状态为00的时候才算是正常状态
							if(result.status == '00'){
								//清空原有数据
								clearAllTableTr();
								_mainData_extend_dialog_dbTable.datas = result.datas;
								_mainData_extend_dialog_dbTable.dbtype = result.dbtype;
							}else{
								alert(result.errlog);	
							}
							$("#mainData_extend_dialog_loading").hide();
						}
					}
				});	
			},200);
		}
		
		function clearAllTableTr(){
			clearTable("mainData_extend_dialog_whereTable");
			clearTable("mainData_extend_dialog_returnValueTable");
			clearTable("mainData_extend_dialog_searchTable");
		}
		
		//按enter即可触发
		function enterTrigleSelect(event,self){
			if (event && event.keyCode == '13') {
				loadDbtableData();
			}
		}
		
	</script>
</body>