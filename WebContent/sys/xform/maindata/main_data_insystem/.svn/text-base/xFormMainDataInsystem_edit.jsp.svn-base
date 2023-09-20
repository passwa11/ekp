<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="com.landray.kmss.sys.xform.maindata.util.MainDataInsystemEnumUtil" %>
<%@page import="net.sf.json.JSONObject" %>
<% response.setHeader("X-UA-Compatible","IE=edge"); %>
<kmss:windowTitle moduleKey="sys-xform-maindata:tree.relation.jdbc.root"  subjectKey="sys-xform-maindata:tree.relation.main.dadta.insystem" subject="${sysFormMainDataInsystemForm.docSubject}" />
<html:form action="/sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do">
<% 
	JSONObject enumJSON = MainDataInsystemEnumUtil.getAllEnum();
	String enumString = enumJSON.toString();
%>
<script>
	var _main_data_insystem_enumCollection = <%=enumString%>;
</script>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/css/xFormMainDataInsystem.css">
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<div id="optBarDiv">
	<c:if test="${sysFormMainDataInsystemForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="xform_main_data_submit('update');">
	</c:if>
	<c:if test="${sysFormMainDataInsystemForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="xform_main_data_submit('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="xform_main_data_submit('saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-xform-maindata" key="tree.relation.main.dadta.insystem"/></p>

<center>
<table class="tb_normal" width=95%>
<c:if test="${param.from ne 'modeling'}">
	<!-- 排序号、所属分类 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.docCategory"/>
		</td><td width="35%">
			<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }" propertyId="docCategoryId" style="width:90%"
					propertyName="docCategoryName" dialogJs="XForm_treeDialog()">
			</xform:dialog>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message key="model.fdOrder"/>
		</td><td width="35%">
			<%-- <xform:text property="fdOrder" style="width:85%" /> --%>
			<xform:text property="fdNewOrder" style="width:85%;" validators="digits min(0)" />
		</td>		
	</tr>
	<!-- 标题 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.docSubject"/>
		</td>
		<td width=35%>
			<xform:text property="docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.docSubject') }" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKey"/>
		</td>
		<td width=35%>
			<xform:text property="fdKey" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKey')}" style="width:85%" validators="myAlphanum"/></br>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKeyTip"/>
		</td>
	</tr>
	
	<!-- 系统数据 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdModleName"/>
		</td>
		<td colspan="3">
			<div style="width:20%;">
				<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.fdModleName') }" propertyId="fdModelName" style="width:90%"
						propertyName="fdModelNameText" dialogJs="XForm_selectModelNameDialog();">
				</xform:dialog>
			</div>
		</td>
	</tr>
		</c:if>

<c:if test="${param.from eq 'modeling'}">
	<!-- 标题 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.docSubject"/>
		</td>
		<td width=35%>
			<xform:text property="docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.docSubject') }" style="width:85%" />
			<html:hidden property="docCategoryId" />
			<html:hidden property="docCategoryName" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKey"/>
		</td>
		<td width=35%>
			<xform:text property="fdKey" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKey')}" style="width:85%" validators="myAlphanum"/></br>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKeyTip"/>
		</td>
	</tr>
	
	<!-- 系统数据 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdModleName"/>
		</td>
		<td>
			<div style="width:35%;">
				<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.fdModleName') }" propertyId="fdModelName" style="width:120px"
						propertyName="fdModelNameText" dialogJs="XForm_selectModelNameDialog();">
				</xform:dialog>
			</div>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message key="model.fdOrder"/>
		</td><td width="35%">
			<%-- <xform:text property="fdOrder" style="width:85%" /> --%>
			<xform:text property="fdNewOrder" style="width:85%;" validators="digits min(0)" />
		</td>		
	</tr>
</c:if>

	<!-- 查询条件 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdWhereBlock"/>
		</td>
		<td colspan="3">
			<table id="xform_main_data_whereTable" class="tb_normal" style="float:left;width:70%;">
				<tr class="xform_main_data_tableTitle">
					<td width="40%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataShowRelate.attribute"/></td>
					<td width="10%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataShowRelate.operator"/></td>
					<td width="35%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataShowRelate.value"/></td>
					<td style="width:15%;">
						<a href="javascript:void(0);" onclick="xform_main_data_addWhereItem(null,true);" style="color:#1b83d8;">
							<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.add"/>
						</a>
					</td>
				</tr>
			</table>
		</td>
		<input type="hidden" name="fdWhereBlock" />
	</tr>
	<!-- 权限过滤 -->
	<tr class="xform_main_data_table_authTr" style="display:none;">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdWhereAuth"/>
		</td>
		<td colspan="3">
			<label><input type="checkbox" name="fdAuthRead" value="authRead"/><bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdAuthReader"/></label>
			<label><input type="checkbox" name="fdAuthEdit" value="authEdit"/><bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdAuthEditor"/></label>
		</td>
		<input type="hidden" name="fdWhereAuth" />
	</tr>
	<!-- 返回值 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdSelectBlock"/>
		</td>
		<td colspan="3">
			<table id="xform_main_data_returnValueTable" class="tb_normal" style="float:left;width:70%;">
				<tr class="xform_main_data_tableTitle">
					<td width="45%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataShowRelate.attribute"/></td>
					<td width="25%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdDisplay"/></td>
					<td style="width:30%;"><a href="javascript:void(0);" onclick="xform_main_data_addSelectItem(null,null,true);" style="color:#1b83d8;"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.add"/></a></td>
				</tr>
			</table>
			<span class="txtstrong">*</span>
		</td>
		
		<input type="hidden" name="fdSelectBlock" />
	</tr>
	<!-- 搜索条件 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdSearch"/>
		</td>
		<td colspan="3">
			<table id="xform_main_data_searchTable" class="tb_normal" style="float:left;width:70%;">
				<tr class="xform_main_data_tableTitle">
					<td width="70%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataShowRelate.attribute"/></td>
					<td style="width:30%;"><a href="javascript:void(0);" onclick="xform_main_data_addSearchItem();" style="color:#1b83d8;"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.add"/></a></td>
				</tr>
			</table>
		</td>
		<input type="hidden" name="fdSearch" />
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdUseRange"/>
		</td>
		<td colspan="3">
			<div name="xform_main_data_range" onclick="xform_main_data_subject_display();">
				<xform:checkbox property="fdRangeXform" value="${sysFormMainDataInsystemForm.fdRangeXform }">
					<xform:simpleDataSource value="true"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.customForm"/></xform:simpleDataSource>
				</xform:checkbox>
				<xform:checkbox property="fdRangeRTF" value="${sysFormMainDataInsystemForm.fdRangeRTF }">
					<xform:simpleDataSource value="true">RTF</xform:simpleDataSource>
				</xform:checkbox>
				<xform:checkbox property="fdRangeRelation" value="${sysFormMainDataInsystemForm.fdRangeRelation }">
					<xform:simpleDataSource value="true"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.relationDocConfigation"/></xform:simpleDataSource>
				</xform:checkbox>
				<xform:checkbox property="fdRangeMatrix" value="${sysFormMainDataInsystemForm.fdRangeMatrix }">
					<xform:simpleDataSource value="true"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.matrix"/></xform:simpleDataSource>
				</xform:checkbox>
			</div>
		</td>
	</tr>
	<!-- rtf标题列 -->
	<tr id="xform_main_data_subject" style="display:none;">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.subjectCol"/>
		</td>
		<td colspan="3">
		</td>
	</tr>
	<!-- 查询预览 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.searchPreview"/>
		</td>
		<td colspan="3">
			<div class="xform_main_queryPreview" onclick="XForm_queryPreviewDialog();"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.searchPreview"/></div>
		</td>
	</tr>
	<!-- 创建者、修改者 -->
	<c:if test="${sysFormMainDataInsystemForm.method_GET=='edit'}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message key="model.fdCreator"/>
			</td>
			<td width="35%">
				<c:out value="${sysFormMainDataInsystemForm.docCreatorName }"></c:out>				
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message key="model.fdCreateTime"/>
			</td>
			<td width="35%">
				<c:out value="${sysFormMainDataInsystemForm.docCreateTime }"></c:out>
			</td>
		</tr>
		<c:if test="${not empty sysFormMainDataInsystemForm.docAlterorName}">
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message key="model.docAlteror"/>
				</td>
				<td width="35%">
					<c:out value="${sysFormMainDataInsystemForm.docAlterorName }"></c:out>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message key="model.fdAlterTime"/>
				</td>
				<td width="35%">
					<c:out value="${sysFormMainDataInsystemForm.docAlterTime }"></c:out>
				</td>
			</tr>
		</c:if>
	</c:if>
</table>
</center>
<script>
	Com_IncludeFile("xFormMainDataInsystem_edit_script.js",Com_Parameter.ContextPath+'sys/xform/maindata/main_data_insystem/','js',true);
	Com_IncludeFile("xFormMainDataInsystemContext.js",Com_Parameter.ContextPath+'sys/xform/maindata/main_data_insystem/','js',true);
	//用于时间选择框
	Com_IncludeFile('calendar.js');
</script>
<script>
	var xform_main_data_insystem_validation = $KMSSValidation();
	
	// 删除功能模板HTML
	var xform_main_data_insystem_delTempHtml = "<a href='javascript:void(0);' onclick='xform_main_data_delTrItem(this);' style='color:#1b83d8;'>${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.delete')}</a>";
	
	// 上下移功能模板HTML
	var xform_main_data_insystem_sortTempHtml = "<a href='javascript:void(0);' onclick='xform_main_data_moveTr(-1,this);' style='color:#1b83d8;'>${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.up')}</a>";
	xform_main_data_insystem_sortTempHtml += "&nbsp;<a href='javascript:void(0);' onclick='xform_main_data_moveTr(1,this);' style='color:#1b83d8;'>${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.down')}</a>";
	
	// 移动 -1：上移       1：下移
	function xform_main_data_moveTr(direct,dom){
		var tb = $(dom).closest("table")[0];
		var $tr = $(dom).closest("tr");
		var curIndex = $tr.index();
		var lastIndex = tb.rows.length - 1;
		if(direct == 1){
			if(curIndex >= lastIndex){
				alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.alreadyToDown')}");
				return;
			}
			$tr.next().after($tr);
		}else{
			if(curIndex < 4){
				alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.alreadyToUp')}");
				return;
			}
			$tr.prev().before($tr);			
		}
	}
	
	// 控制列表列的显示和隐藏
	function xform_main_data_subject_display(selectedItem,callback){
		if(!insystemContext.hasDictData()){
			return;
		}
		var flag = false;
		var $dom = $("div[name='xform_main_data_range']");
		var fdRangeRTF = $dom.find("input[name='fdRangeRTF']").val();
		var fdRangeRelation = $dom.find("input[name='fdRangeRelation']").val();
		if(fdRangeRTF == 'true' || fdRangeRelation == 'true'){
			flag = true;
		}
		var $tr = $("#xform_main_data_subject");
		if(flag){
			var $select = $tr.find("select");
			// 如果还没有构建select，则构建
			if($select.length == 0){
				xform_main_data_subject_init($tr,selectedItem);
			}
			$tr.show();
		}else{
			$tr.hide();
		}
		if(callback){
			callback($tr);
		}
	}
	
	function xform_main_data_subject_init($tr,selectedField){
		var html = "";
		html += xform_main_data_getFieldOptionHtml(insystemContext.strDictData,'fdRTFSubject','true',selectedField ? selectedField : (insystemContext.nameProperty ? insystemContext.nameProperty.field : ''));
		$tr.find("td:last").append(html);
	}
	
	//自定义校验方法
	xform_main_data_insystem_validation.addValidator('myAlphanum','${lfn:message("sys-xform-maindata:sysFormJdbcDataSet.fdKeyWaring") }',function(v, e, o){
		return this.getValidator('isEmpty').test(v) || !/\W/.test(v);
	});
	
	// 全局变量，存储当前model的信息
	var insystemContext = new Insystem_Context();
	
	//添加属性行
	function xform_main_data_addAttrItem(tableId,datas,selectedItem,callback){
		if(!insystemContext.hasDictData()){
			alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.chooseModuleFirst') }");
			return;
		}
		var $selectTable = $("#" + tableId);
		var $tr = $("<tr>");
		var html = "";
		//属性
		html += "<td>";
		html += xform_main_data_getFieldOptionHtml(datas,'fdAttrField','true',selectedItem == null ? null : selectedItem.field,selectedItem);
		html += "</td>";
		//删除
		html += "<td>"+ xform_main_data_insystem_delTempHtml +"</td>";
		$tr.append(html);
		$selectTable.append($tr);
		if(callback){
			callback($tr,selectedItem);
		}
	}
	
	//增加查询条件
	function xform_main_data_addWhereItem(selectedItem,isAdd){
		if(!insystemContext.hasDictData()){
			alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.chooseModuleFirst') }");
			return;
		}
		var $selectTable = $("#xform_main_data_whereTable");
		var $tr = $("<tr>");
		var html = "";
		//属性
		html += "<td>";
		html += xform_main_data_getFieldOptionHtml(insystemContext.filterDictData,'fdAttrField',null ,selectedItem == null ? null : selectedItem.field,selectedItem == null ? null : selectedItem);
		html += "</td>";
		//运算符
		html += "<td>";
		if(selectedItem){
			html += xform_main_data_getOperatorOptionHtml(selectedItem.fieldType,selectedItem.fieldOperator);
		}else{
			html += xform_main_data_getOperatorOptionHtml(insystemContext.dictData);
		}

		html += "</td>";
		//值
		html += "<td>";
		if(selectedItem){
			html += xform_main_data_getFieldvalueOptionHtml(selectedItem.fieldType,selectedItem);
		}else{
			html += xform_main_data_getFieldvalueOptionHtml(insystemContext.dictData);
		}

		html += "</td>";
		//删除
		html += "<td>"+ xform_main_data_insystem_delTempHtml +"</td>";
		$tr.append(html);
		$selectTable.append($tr);
		if(isAdd){
			$tr.find("select[name='fdAttrField']").trigger("change");
		}
	}
	
	// 添加返回值
	function xform_main_data_addSelectItem(selectedItem,callback,isAdd){
		if(!insystemContext.hasDictData()){
			alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.chooseModuleFirst') }");
			return;
		}
		var $selectTable = $("#xform_main_data_returnValueTable");
		var $tr = $("<tr>");
		var html = "";
		//属性
		html += "<td>";
		html += xform_main_data_getFieldOptionHtml(insystemContext.dictData,'fdAttrField','true',selectedItem == null ? null : selectedItem.field,selectedItem);
		html += "</td>";
		html += "<td><input name='fdSelectShowFlag' type='checkbox'";
		if(!selectedItem || !selectedItem.hasOwnProperty('show') || selectedItem.show == 'true'){
			html += " checked";
		}
		html += " /></td>";
		//删除
		html += "<td>"+ xform_main_data_insystem_delTempHtml + "&nbsp;" + xform_main_data_insystem_sortTempHtml + "</td>";
		$tr.append(html);
		$selectTable.append($tr);
		if(callback){
			callback($tr,selectedItem);
		}
		if(isAdd){
			$tr.find("select[name='fdAttrField']").trigger("change");
		}
	}
	
	// 添加搜索条件
	function xform_main_data_addSearchItem(selectedItem,callback){
		if(!insystemContext.hasDictData()){
			alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.chooseModuleFirst') }");
			return;
		}
		var $selectTable = $("#xform_main_data_searchTable");
		var $tr = $("<tr>");
		var html = "";
		//属性
		html += "<td>";
		html += xform_main_data_getFieldOptionHtml(insystemContext.filterDictData,'fdAttrField','true',selectedItem == null ? null : selectedItem.field,selectedItem);
		html += "</td>";
		//删除
		html += "<td>"+ xform_main_data_insystem_delTempHtml +"</td>";
		$tr.append(html);
		$selectTable.append($tr);
		if(callback){
			callback($tr,selectedItem);
		}
	}
	
	//所属分类的弹框
	function XForm_treeDialog() {
		Dialog_Tree(false, 'docCategoryId', 'docCategoryName', ',', 
				'sysFormJdbcDataSetCategoryTreeService&parentId=!{value}', 
				"${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }", 
				null, null, null, null, null, 
				"${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }");
	}
	
	//弹出查询预览对话框
	function XForm_queryPreviewDialog(){
		seajs.use(['lui/dialog'], function(dialog) {
			var url = "/sys/xform/maindata/main_data_insystem/xFormMainDataInsystem_edit_queryPreviewDialog.jsp";
			var height = screen.height * 0.6;
			var width = screen.width * 0.6;
			var dialog = dialog.iframe(url,"${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.searchPreview') }",null,{width:width,height : height,params:{"parentWin":window}});
		});
	}
	
	//弹出系统内数据对话框
	function XForm_selectModelNameDialog(){
		window.focus();
		seajs.use(['lui/dialog'], function(dialog) {
			var url = "/sys/xform/maindata/main_data_insystem/xFormMainDataInsystem_edit_dialog.jsp";
			var height = document.documentElement.clientHeight * 0.78;
			var width = document.documentElement.clientWidth * 0.6;
			var dialog = dialog.iframe(url,"${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.systemData') }",xform_main_data_setAttr,{width:width,height : height,close:false});
		});
	}
	
	//关闭、确定对话框的回调函数
	function xform_main_data_setAttr(value){
		if(value){
			if(value.modelName){
				$("input[name='fdModelName']").val(value.modelName);	
				insystemContext.modelName = value.modelName;
			}
			if(value.modelNameText){
				$("input[name='fdModelNameText']").val(value.modelNameText);	
			}
			xform_main_data_insystem_validation.validateElement($("input[name='fdModelNameText']")[0]);
			if(value.data){
				// 设置属性
				var data;
				try{
					data = $.parseJSON(value.data);
				}catch(e){
					alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.lookLog') }");
				}
				if(data){
					xform_main_data_setGlobal(data);
					//处理权限行
					xform_main_data_detailAuthTr(insystemContext.auth);
				}
			}
			xform_main_data_initAfterSetModel();
		}
	}
	
	// 在设置确定完model之后，初始化页面
	function xform_main_data_initAfterSetModel(){
		xform_main_data_emptyAllTr();
		// 默认添加上id和name，兼容历史数据
		xform_main_data_addSelectBlockIdAndName();
		// 处理rtf标题列隐藏和显示
		xform_main_data_subject_display(null,xform_main_data_subject_delSelect);
	}
	
	function xform_main_data_subject_delSelect($tr){
		$tr.find("select").remove();
		xform_main_data_subject_init($tr);
	}
	
	//设置权限js变量的数据字典变量和权限变量
	function xform_main_data_setGlobal(data){
		insystemContext.clear();
		insystemContext.initialize(data.fieldArray);
		insystemContext.auth = data.authJSON;
	}
	
	//权限行的显示和隐藏
	function xform_main_data_detailAuthTr(authData){
		var $tr = $(".xform_main_data_table_authTr");
		if(authData.isAuth && authData.isAuth == 'true'){
			$tr.show();
		}else{
			$tr.hide();
		}
	}
	
	Com_Parameter.event["submit"].push(xform_main_data_beforeSubmitValidate);
	
	//提交前校验
	function xform_main_data_beforeSubmitValidate(){
		if(!validateKeyUnique()){
			return false;
		}
		var dataJSON = xform_main_data_detailWithAllData(true);
		
		if(!xform_main_data_validateTr(dataJSON)){
			return false;
		}
		return true;
	}
	
	function xform_main_data_validateTr(dataJSON){
		var selectArray = dataJSON.select;
		var searchArray = dataJSON.search;
		var returnValue = dataJSON.returnValue;
		if(xform_main_data_fieldIsUnique(selectArray) == false){
			alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.fdWhereBlock') }" + "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.cantHaveRepeatItem') }");
			return false;
		}
		if(xform_main_data_fieldIsUnique(searchArray) == false){
			alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.fdSearch') }" + "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.cantHaveRepeatItem') }");
			return false;
		}
		if(xform_main_data_fieldIsUnique(returnValue) == false){
			alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.fdSelectBlock') }" + "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.cantHaveRepeatItem') }");
			return false;
		}
		//遍历查询条件，搜索条件不能和查询条件重复
		for(var i =  0;i < selectArray.length;i++){
			var selectAttr = selectArray[i];
			for(var j = 0;j < searchArray.length;j++){
				var selectConditionAttr = searchArray[j];
				if(selectConditionAttr.field == selectAttr.field){
					alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.searchItemCantRepeatWithSearch') }");
					return false;
				}
			}
		}		
		//返回值不能为空
		if(returnValue == null || returnValue.length == 0){
			alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.returnValCantBeNull') }");
			return false;
		}
		
		return true;
	}
	
	// 判断数组里面是否有重复数据
	function xform_main_data_fieldIsUnique(array){
		if(array && array.length > 0){
			var hash = {};
			for(var i = 0;i < array.length;i++){
				if(!hash[array[i].field]){
					hash[array[i].field] = true;
				}else{
					return false;
				}
			}
		}
		return true;
	}
	
	// 封装数据
	function xform_main_data_detailWithAllData(isSetValue){
		var dataJSON = {};
		//处理查询条件
		var $selectTr = $("#xform_main_data_whereTable").find("tr:not(.xform_main_data_tableTitle)");
		var selectArray = [];
		for(var i = 0;i < $selectTr.length; i++){
			var tr = $selectTr[i];
			selectArray.push(xform_main_data_detailSelectWhere(tr));
		}
		dataJSON.select = selectArray;
		
		//处理权限过滤
		var $authTr = $(".xform_main_data_table_authTr");
		var authJson = {};
		authJson.fdAuthRead = ($authTr.find("input[name='fdAuthRead']"))[0].checked ? "true" : "false";
		authJson.fdAuthEdit = ($authTr.find("input[name='fdAuthEdit']"))[0].checked ? "true" : "false";
		dataJSON.whereAuth = authJson;
		
		//处理返回值
		var $returnValueTr = $("#xform_main_data_returnValueTable").find("tr:not(.xform_main_data_tableTitle)");
		var valueArray = [];
		for(var i = 0;i < $returnValueTr.length; i++){
			var tr = $returnValueTr[i];
			valueArray.push(xform_main_data_detailAttrWhere(tr,'true'));
		}
		dataJSON.returnValue = valueArray;
		
		//处理搜索条件
		var $selectConditionTr = $("#xform_main_data_searchTable").find("tr:not(.xform_main_data_tableTitle)");
		var selectConditionArray = [];
		for(var i = 0;i < $selectConditionTr.length; i++){
			var tr = $selectConditionTr[i];
			selectConditionArray.push(xform_main_data_detailAttrWhere(tr,'true'));
		}
		dataJSON.search = selectConditionArray;
		
		if(isSetValue && isSetValue == true){
			$("input[name='fdWhereBlock']").val(JSON.stringify(selectArray));
			$("input[name='fdWhereAuth']").val(JSON.stringify(authJson));
			$("input[name='fdSelectBlock']").val(JSON.stringify(valueArray));
			$("input[name='fdSearch']").val(JSON.stringify(selectConditionArray));
		}
		
		return dataJSON;
	}
	
	//校验关键字的唯一性
	function validateKeyUnique(){
		var fdKey = document.getElementsByName("fdKey")[0];
		var isUnique = true;
		if(fdKey && fdKey.value != ''){
			var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=isUnique&fdKey=" + fdKey.value + "&fdId=${param.fdId}";
			$.ajax({ url: url, async: false, dataType: "json", cache: false, success: function(rtn){
				if("true" != rtn.isUnique){
					isUnique = false;
					alert("${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKeyNotUniqueWarning')}");
				}
			}});		
		}
		return isUnique;
	}
	
	//提交
	function xform_main_data_submit(method){
		Com_Submit(document.sysFormMainDataInsystemForm, method);
	}
	
	Com_AddEventListener(window,'load',xform_main_data_initVar);
	
	//初始化数据，主要用于edit编辑页面
	function xform_main_data_initVar(){
		var fdModelName = $("input[name='fdModelName']").val();
		var fdModelNameText = $("input[name='fdModelNameText']").val();
		if(fdModelName != "" && fdModelNameText != ""){
			//初始化数据字典变量
			var dictData = "${sysFormMainDataInsystemForm.modelDict}";
			dictData = dictData.replace(/&quot;/g,"\"");
			xform_main_data_setGlobal($.parseJSON(dictData));
			//处理权限行
			xform_main_data_detailAuthTr(insystemContext.auth);
			var whereAuth = '${sysFormMainDataInsystemForm.fdWhereAuth}';
			if(whereAuth){
				var whereAuthJSON = $.parseJSON(whereAuth);
				if(whereAuthJSON.fdAuthRead && whereAuthJSON.fdAuthRead == "true"){
					($(".xform_main_data_table_authTr input[name='fdAuthRead']"))[0].checked = true;
				}
				if(whereAuthJSON.fdAuthEdit && whereAuthJSON.fdAuthEdit == "true"){
					($(".xform_main_data_table_authTr input[name='fdAuthEdit']"))[0].checked = true;
				}
			}
			//处理查询条件
			var whereData = '${sysFormMainDataInsystemForm.fdWhereBlock}';
			if(whereData){
				var whereDataJsonArray = $.parseJSON(whereData);
				//遍历查询条件数据
				for(var i = 0;i < whereDataJsonArray.length;i++){
					if($.isEmptyObject(whereDataJsonArray[i])){
						continue;
					}
					xform_main_data_addWhereItem(whereDataJsonArray[i]);
				}
			}
			//处理返回值
			var selectData = '${sysFormMainDataInsystemForm.fdSelectBlock}';
			if(selectData){
				var selectDataJsonArray = $.parseJSON(selectData);
				// 默认添加上id和name，兼容历史数据
				xform_main_data_addSelectBlockIdAndName(selectDataJsonArray);
				//遍历查询条件数据
				for(var i = 0;i < selectDataJsonArray.length;i++){
					if($.isEmptyObject(selectDataJsonArray[i])){
						continue;
					}
					xform_main_data_addSelectItem(selectDataJsonArray[i],xform_main_data_custom_initEnum);
				}
			}
			//处理搜索条件
			var searchCondition = '${sysFormMainDataInsystemForm.fdSearch}';
			if(searchCondition){
				var searchJsonArray = $.parseJSON(searchCondition);
				//遍历查询条件数据
				for(var i = 0;i < searchJsonArray.length;i++){
					if($.isEmptyObject(searchJsonArray[i])){
						continue;
					}
					xform_main_data_addSearchItem(searchJsonArray[i],xform_main_data_custom_initEnum);
				}
			}
			// 处理rtf标题列
			xform_main_data_subject_display("${sysFormMainDataInsystemForm.fdRTFSubject}");
		}
		xform_main_data_custom_enumChangeEvent("xform_main_data_returnValueTable");
		xform_main_data_custom_enumChangeEvent("xform_main_data_searchTable");
	}
	
	// 增加id和名称行
	function xform_main_data_addSelectBlockIdAndName(arr){
		if(insystemContext.idProperty){
			var pro = insystemContext.idProperty;
			// 找到对应的列，取出来
			if(arr){
				pro = xform_main_data_delSameProInArr(pro,arr);
			}
			xform_main_data_addSelectItem(pro,xform_main_data_setSelectDisabled);	
		}
		if(insystemContext.nameProperty){
			var pro = insystemContext.nameProperty;
			if(arr){
				pro = xform_main_data_delSameProInArr(pro,arr);
			}
			xform_main_data_addSelectItem(pro,xform_main_data_setSelectDisabled);	
		}
	}
	
	// 删除arr里面跟pro相同的值
	function xform_main_data_delSameProInArr(pro,arr){
		var index;
		for(var i = 0;i < arr.length;i++){
			if(pro.field == arr[i].field){
				pro = arr[i];
				index = i;
				break;
			}
		}
		if(typeof(index) != 'undefined'){
			arr.splice(index,1);	
		}
		return pro;
	}
	
	// 调整id和name行的样式
	function xform_main_data_setSelectDisabled($tr,selectedItem){
		var color = "#848080";
		var select = $tr.find("select");
		select.css('color',color);
		select.attr('disabled','disabled');
		var lastTd = $tr.find('td:last');
		lastTd.empty();
	}
	
</script>

</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>