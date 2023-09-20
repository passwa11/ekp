<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link rel="stylesheet" href="${LUI_ContextPath}/sys/rule/resources/css/buttons.css">
<script type="text/javascript" src="${LUI_ContextPath}/sys/rule/resources/js/common.js"></script>
<script type="text/javascript" src="${LUI_ContextPath}/sys/rule/resources/js/formula_common.js"></script>
<script type="text/javascript" src="${LUI_ContextPath}/sys/rule/resources/js/simulator.js"></script>
<script type="text/javascript">
seajs.use(['theme!form']);
Com_IncludeFile('common.js|jquery.js|plugin.js|data.js|dialog.js|calendar.js|doclist.js');
var message_unknowfunc = '<bean:message bundle="sys-formula" key="validate.unknowfunc"/>';
var message_unknowvar = '<bean:message bundle="sys-formula" key="validate.unknowvar"/>';
var message_wait = '<bean:message bundle="sys-formula" key="validate.wait"/>';
var message_eval_error = '<bean:message bundle="sys-formula" key="validate.failure.evalError"/>';
var message_insert_formula = '<bean:message bundle="sys-formula" key="formula.link.insertFormula"/>';
</script>
<title>
</title>
</head>
<body class="lui_form_body" style="background-color:white">
<form>
	<!-- 错误信息返回页面 -->
	<c:import url="/resource/jsp/error_import.jsp" charEncoding="UTF-8" ></c:import>
	<p class="txttitle" style="margin-top:10px"><bean:message key='lbpmext.businessauth.simulator.fdName' bundle='sys-lbpmext-businessauth' /></p>
	<table class="tb_normal" width=95% style="margin-top:10px">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/>
			</td>
			<td width="85%" colspan="3">
				<input type="hidden" name="matrixId">
				<input type="text" name="matrixName" class="inputsgl" readonly="readonly">
				<a href="#" onclick="Dialog_Tree(false, 'matrixId', 'matrixName', null, 'sysOrgMatrixService&parent=!{value}', '${lfn:message('sys-organization:sysOrgMatrix.simulator.select')}', null, selectMatrix);">
					<bean:message key="button.select"/>
				</a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="version" name="version" style="display: none;"></select>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.conditional"/>
			</td>
			<td width="85%">
				<table id="fdConditionals" class="tb_normal" width="100%">
					<tr class="tr_normal_title">
						<td width="10%" align="center">
							<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
						</td>
						<td width="40%">
							<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.conditional.type"/>
						</td>
						<td width="40%">
							<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.conditional.value"/>
						</td>
						<td width="10%">
							<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="if(checkMatrix())DocList_AddRow('fdConditionals', Conditional_Row);" style="cursor:pointer">
						</td>
					</tr>
					<!--基准行-->
					<tr KMSS_IsReferRow="1" style="display:none">
						<td KMSS_IsRowIndex="1" align="center"></td>
						<td></td>
						<td>
							<input type="hidden" name="id[!{index}]">
							<input type="text" name="name[!{index}]" class="inputsgl" style="width:70%">
							<a href="#" name="select[!{index}]" onclick="selectConditional('id[!{index}]', 'name[!{index}]', this);" style="display: none;">
								<bean:message key="dialog.selectOrg" />
							</a>
						</td>
						<td>
							<div style="text-align:center">
								<img src="<c:url value="/resource/style/default/icons/delete.gif"/>" name="__del" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
								<img src="<c:url value="/resource/style/default/icons/up.gif"/>" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
								<img src="<c:url value="/resource/style/default/icons/down.gif"/>" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.result"/>
			</td>
			<td width="85%">
				<input type="hidden" name="resultId">
				<input type="text" name="resultName" class="inputsgl" readonly="readonly" style="width:70%;">
				<a href="javascript:selectResult();"><bean:message key="button.select"/></a>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=20%>
				<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.option"/>
			</td>
			<td width=80%>
				<label><input type="radio" name="option" value="1" checked="checked"/><bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.option1"/></label>
				<label><input type="radio" name="option" value="2"/><bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.option2"/></label>
				<label><input type="radio" name="option" value="3"/><bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.option3"/></label>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message key='lbpmext.businessauth.simulator.selfdAuth' bundle='sys-lbpmext-businessauth' />
			</td>
			<td width="85%" colspan="3">
				<input type="hidden" name="fdAuthId">
				<input type="text" name="fdAuthName" class="inputsgl" readonly="readonly" validate="required" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessauth.simulator.fdAuth') }" /><span class="txtstrong">*</span>	
				<a href="javascript:void(0)" onclick="showFdAuth();">
					<bean:message key="dialog.selectOrg"/>
				</a>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message key='lbpmext.businessauth.simulator.fdParameter' bundle='sys-lbpmext-businessauth' />
			</td>
			<td width="85%">
				<input type="text" name="fdParameter" class="inputsgl" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessauth.simulator.fdParameter') }" validate="required number scaleLength(0)" /><span class="txtstrong">*</span>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message key='lbpmext.businessauth.simulator.result' bundle='sys-lbpmext-businessauth' />
			</td>
	    	<td width="85%">
	    		<div class="result"></div>
	    	</td>
	    </tr>
	    <!-- 按钮 -->
	    <tr>
	    	<td align=center colspan="2">
	    		<ui:button text="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessauth.simulator.calculate') }" onclick="startCalculate();"></ui:button>
	    	</td>
	    </tr>
	</table>
</form>
	<script type="text/javascript">
		DocList_Info.push('fdConditionals');
		
		var _validation = $KMSSValidation();
		
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			window.Conditional_Row = [null, null];
			window.Conditional_Last = undefined;
			window.Conditional = {};
			
			// 选择矩阵
			window.selectMatrix = function(rtnVal) {
				if(rtnVal && rtnVal.data.length > 0) {
					var matrixId = rtnVal.data[0].id;
					if(matrixId != Conditional_Last) {
						// 清空条件和结果
						$("#fdConditionals").find("tr").each(function(i, n) {
							if(i > 0) {
								$(n).find("[name=__del]").click();
							}
						});
						$("input[name=resultId]").val("");
						$("input[name=resultName]").val("");
						
						// 获取条件字段
						var data = new KMSSData();
						data.UseCache = false;
						data.AddBeanData("sysOrgMatrixService&id=" + matrixId + "&rtnType=1");
						var rtn = data.GetHashMapArray();
						// 动态生成一个下拉框
						if(rtn.length > 0) {
							var select = [];
							select.push('<select name="conditional[!{index}]" onchange="conditionalChange(this.value, this);" style="width:70%">');
							select.push('<option value=""><bean:message key="page.firstOption"/></option>');
							for(var i=0; i<rtn.length; i++) {
								Conditional[rtn[i].value] = rtn[i];
								select.push('<option value="' + rtn[i].value + '" data-type="' + rtn[i].type + '" data-maindata="' + (rtn[i].mainDataType || '') + '" data-fieldname="' + (rtn[i].fieldName || '') + '">' + rtn[i].text + '</option>');
							}
							select.push('</select>');
							select.push('<select name="conditional_type[!{index}]" style="display: none;">');
							select.push('<option value="fdId">ID</option>');
							select.push('<option value="fdName"><bean:message key="model.fdName"/></option>');
							select.push('</select>');
							Conditional_Row[1] = select.join("");
						}
						// 保存上一次选择的矩阵
						Conditional_Last = matrixId;
						// 获取版本号
						$.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=getVersions" />', {'fdId': matrixId}, function(res) {
							var __ver = $("#version");
							__ver.empty();
							if(res) {
								for(var i=0; i<res.length; i++) {
									__ver.append("<option value='" + res[i].fdName + "'>" + res[i].fdName + "</option>");
								}
								__ver.show();
							} else {
								__ver.hide();
							}
						}, 'json');
					}
				}
			}
			
			// 检查是否已经选择矩阵
			window.checkMatrix = function() {
				var matrixId = $("input[name=matrixId]").val();
				if(matrixId.length < 1) {
					dialog.alert('<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.empty"/>');
					return false;
				}
				return true;
			}
			
			// 选择条件
			window.conditionalChange = function(value, elem) {
				if(value.length > 0) {
					var idx = $(elem).attr("name").match(/\d+/);
					var type = $(elem).find("option:selected").data("type");
					var maindata = $(elem).find("option:selected").data("maindata");
					var fieldname = $(elem).find("option:selected").data("fieldname");
					var temp = $("select[name='conditional_type[" + idx + "]']");
					var id = $("input[name='id[" + idx + "]']");
					var name = $("input[name='name[" + idx + "]']");
					// 清空原来的数据
					id.val("");
					name.val("");
					var select = $("a[name='select[" + idx + "]']");
					if(type == "org" || type == "dept" || type == "post" || type == "person" || type == "group") {
						// 组织架构
						temp.show();
						select.show();
						name.attr("readonly","readonly");
						select.data("type", type);
					} else if(type != "constant") {
						// 主数据
						temp.show();
						select.show();
						name.attr("readonly","readonly");
						select.data("fieldname", fieldname);
						select.data("type", maindata);
						select.data("id", type);
					} else {
						// 常量
						temp.hide();
						select.hide();
						name.removeAttr("readonly");
					}
				}
			}
			
			// 选择结果
			window.selectResult = function() {
				if(checkMatrix()) {
					var matrixId = $("input[name=matrixId]").val();
					Dialog_Tree(true, 'resultId', 'resultName', null, 'sysOrgMatrixService&id=' + matrixId + '&rtnType=2', '<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.select.result"/>');
				}
			}
			
			// 选择条件
			window.selectConditional = function(id, name, elem) {
				var dataid = $(elem).data("id");
				var type = $(elem).data("type");
				var fieldname = $(elem).data("fieldname");
				if(type == "org" || type == "dept" || type == "post" || type == "person" || type == "group") {
					var orgType = type == "org" ? 1 : type == "dept" ? 2 : type == "post" ? 4 : type == "person" ? 8 : type == "group" ? 16 : "";
					Dialog_Address(false, id, name, null, orgType);
				} else if(type == "sys") {
					Dialog_MainData(id, name, fieldname, '<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.select.maindata"/>');
				} else if(type == "cust") {
					Dialog_Tree(false, id, name, null, 'sysOrgMatrixMainDataService&id=' + dataid, '<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.select.cust"/>');
				}
			}
			
			// 开始计算
			window.startCalculate = function() {
				// 打包所有入参
				// 格式：{'id': '矩阵ID', 'results': '结果1ID;结果2ID', 'option': 1, 'conditionals': [{'id':'条件1ID', 'type': 'fdId/fdName', 'value': '条件值1'}, {'id':'条件2ID', 'type': 'fdId/fdName', 'value': '条件值2'}]}
				if(checkMatrix()) {
					if(!_validation.validate()){
						return;
					}
					var matrixId = $("input[name=matrixId]").val();
					var version = $("select[name=version]").val();
					var results = $("input[name=resultId]").val();
					var resultName = $("input[name=resultName]").val();
					var option = $("input[name=option]:checked").val();
					if(results.length < 1) {
						dialog.alert('<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.result.empty"/>');
						return false;
					}
					var conditionals = [];
					$("#fdConditionals").find("tr").each(function(i, n) {
						if(i > 0) {
							// 取条件类型
							var conditional = $("select[name='conditional[" + (i - 1) + "]']").find("option:selected");
							var id = conditional.val();
							var type = "fdId";
							var value = $("input[name='name[" + (i - 1) + "]']").val();
							if(conditional.data("type") != "constant") {
								type = $("select[name='conditional_type[" + (i - 1) + "]']").val();
								if(type == "fdId") {
									value = $("input[name='id[" + (i - 1) + "]']").val();
								}
							}
							conditionals.push({"id":id, "type":type, "value":value});
						}
					});
					if(conditionals.length < 1) {
						dialog.alert('<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.conditional.empty"/>');
						return false;
					}
					var matrixInfo = {"id":matrixId,"version":version,"results":results,"option":option,"conditionals":conditionals};
					var info = {"matrixData":JSON.stringify(matrixInfo),"fdAuthId":$("input[name='fdAuthId']").val(),"fdParameter":$("input[name='fdParameter']").val()}
					var data = new KMSSData();
					data.UseCache = false;
					data.AddHashMap(info);
					//模拟公式后回写数据
					var action = function (rtnVal){
						var content = [];
						content.push('<table class="tb_normal" width=100%>');
						content.push('<tr align="center">');
						content.push('<td class="td_normal_title" width=10%><bean:message key="page.serial"/></td>');
						content.push('<td class="td_normal_title" ><bean:message key="lbpmext.businessauth.simulator.handler" bundle="sys-lbpmext-businessauth" /></td>');
						content.push('<td class="td_normal_title" ><bean:message key="lbpmext.businessauth.simulator.post" bundle="sys-lbpmext-businessauth" /></td>');
						content.push('<td class="td_normal_title" ><bean:message key="lbpmext.businessauth.simulator.fdType" bundle="sys-lbpmext-businessauth" /></td>');
						content.push('<td class="td_normal_title" ><bean:message key="lbpmext.businessauth.simulator.fdLimit" bundle="sys-lbpmext-businessauth" /></td>');
						content.push('</tr>');
						if(rtnVal && rtnVal.data && rtnVal.data.length>0){
							for(var i=0; i<rtnVal.data.length; i++) {
								var obj = rtnVal.data[i];
								content.push('<tr>');
								content.push('<td align="center">' + (i + 1) + '</td>');
								content.push('<td align="center">' + obj.fdAuthorizedPersonName + '</td>');
								content.push('<td align="center">' + obj.fdAuthorizedPostName + '</td>');
								content.push('<td align="center">' + obj.fdTypeName + '</td>');
								if(obj.fdType==3){
									content.push('<td align="center"><bean:message key="lbpmext.businessAuthDetail.unlimited" bundle="sys-lbpmext-businessauth" /></td>');
								}else{
									content.push('<td align="center">' + obj.fdMinLimit + '~' + obj.fdLimit + '</td>');
								}
								content.push('</tr>');
							}
						}else{
							content.push('<tr align="center">');
							content.push('<td colspan="5"><bean:message key="lbpmext.businessauth.simulator.noRecord" bundle="sys-lbpmext-businessauth" /></td>');
							content.push('</tr>');
						}
						content.push('</table>');
						$(".result").html(content.join(""));
					}
					data.SendToBean("lbpmExtBusinessAuthSimulateService", action);
				}
			}
			// 系统主数据
			window.Dialog_MainData = function(id, name, fieldName, title) {
				var selected = $("input[name='" + id + "']").val();
				var matrixId = $("input[name=matrixId]").val();
				dialog.iframe("/sys/organization/sys_org_matrix/sysOrgMatrixData_mainData.jsp?matrixId=" + matrixId + "&fieldName=" + fieldName + "&selected=" + selected,
						title, function(data) {
					if(data) {
						if(data == "clear") {
							$("input[name='" + id + "']").val("");
							$("input[name='" + name + "']").val("");
						} else {
							$("input[name='" + id + "']").val(data.id);
							$("input[name='" + name + "']").val(data.name);
						}
					}
				}, {
					width : 1200,
					height : 600,
					buttons : [{
						name : '<bean:message key="button.ok" />',
						focus : true,
						fn : function(value, dialog) {
							if(dialog.frame && dialog.frame.length > 0) {
								var frame = dialog.frame[0];
								var contentDoc = $(frame).find("iframe")[0].contentDocument;
								$(contentDoc).find("input[name='List_Selected']:checked").each(function(i, n) {
									value = {};
									value.id = $(n).val();
									value.name = $(n).parent().parent().find("td.mainData_title").text();
									return true;
								});
							}
							setTimeout(function() {
								dialog.hide(value);
							}, 200);
						}
					}, {
						name : '<bean:message key="button.cancel" />',
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide();
						}
					}, {
						name : '<bean:message key="button.clear" />',
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide("clear");
						}
					}]
				});
			}
			// 选择条目
			window.showFdAuth = function(){
				Dialog_TreeList(false,'fdAuthId','fdAuthName',null,'lbpmExtBusinessAuthCateService&parentId=!{value}','<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.add.title"/>','lbpmExtBusinessAuthService&parentId=!{value}',function(rtnVal){
					if(rtnVal && rtnVal.data){
						if(rtnVal.data[0].fdScale){
							$("input[name='fdParameter']").attr("validate","required number scaleLength("+rtnVal.data[0].fdScale+")");
						}
						$("input[name='fdAuthName']").val(rtnVal.data[0].fdName);
					}
				},'lbpmExtBusinessAuthService&search=!{keyword}');
			}
			LUI.ready(function() {
				var paramMatrixId = "${JsParam.matrixId}";
				if(paramMatrixId.length > 0) {
					var paramMatrixName = decodeURIComponent("${JsParam.matrixName}");
					$("input[name=matrixId]").val(paramMatrixId);
					$("input[name=matrixName]").val(paramMatrixName);
					selectMatrix({"data": [{"id": paramMatrixId, "name": paramMatrixName}]});
				}
				
			});
		});
	</script>
	
</body>
</html>