<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" sidebar="auto">
	<template:replace name="body">
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
			var _NodeContent = eval('(' + parent.NodeContent + ')');
			var allparams = {};
			if(_NodeContent) {
				allparams = _NodeContent.params;
			}

			function serializeObject(form) {
				var o = {};
				var a = form.serializeArray();
				$.each(a, function() {
					if (o[this.name] !== undefined) {
						if (!o[this.name].push) {
							o[this.name] = [o[this.name]];
						}
						o[this.name].push(this.value || '');
					} else {
						o[this.name] = this.value || '';
					}
				});

				// 保存下拉选择的文本值，因为在view页面不能读取表单数据，所以只能在这里保存
				var _s = new Array("fdApplicant","fdOrgParent","fdOrgPost","fdCategory","fdOrgRank","fdBeforeOrgRank",
						"fdReportLeader","fdBeforeReportLeader","fdOfficeAreaProvinceId","fdOfficeAreaCityId",
						"fdOfficeAreaAreaId","fdOfficeLocation","fdMoveType","fdMoveDate");
				$.each(_s, function(i, n) {
					var name = n + "_text";
					o[name] = $("select[name=" + n + "]").find("option:selected").text();
				});
				return o;
			}

			// 必须实现的方法，供父窗口(attribute_robotnode.html)调用。
			function returnValue() {
				return "{\"params\":" + LUI.stringify(serializeObject($("#mainForm"))) + "}";
			};

			function setLeaveType() {
				var _allparams = allparams || {};
				// 设置下拉框内容
				$.each($("#mainForm select"), function(i, n) {
					if(n.options.length == 1)
						n.options[0].innerText = _allparams[$(n).attr("name") + "_text"];
				});
			};

			/**
			 * 获取所有表单字段
			 */
			function transFormFieldList() {
				var rtnResult = new Array();
				var fieldList = parent.FlowChartObject.FormFieldList;
				if (!fieldList)
					return rtnResult;
				// 转换成option支持的格式
				for ( var i = 0, length = fieldList.length; i < length; i++) {
					rtnResult.push( {
						value : fieldList[i].name,
						name : fieldList[i].label
					});
				}
				return rtnResult;
			};

			LUI.ready(function(){
				createSelectElementHTML("fdApplicant");
				createSelectElementHTML("fdOrgParent");
				createSelectElementHTML("fdOrgPost");
				createSelectElementHTML("fdCategory");
				createSelectElementHTML("fdOrgRank");
				createSelectElementHTML("fdBeforeOrgRank");
				createSelectElementHTML("fdReportLeader");
				createSelectElementHTML("fdBeforeReportLeader");
				createSelectElementHTML("fdOfficeAreaProvinceId");
				createSelectElementHTML("fdOfficeAreaCityId");
				createSelectElementHTML("fdOfficeAreaAreaId");
				createSelectElementHTML("fdOfficeLocation");
				createSelectElementHTML("fdMoveType");
				createSelectElementHTML("fdMoveDate");
				setLeaveType();
			});

			function createSelectElementHTML(fieldName) {
				var _allparams = allparams || {};
				var options = transFormFieldList();
				var rtnResult = new Array();
				var checkVal = _allparams[fieldName];
				if (options == null || options.length == 0) {
					rtnResult.push('<option value=\'\' selected><bean:message key="hrStaff.robot.select" bundle="hr-staff"/></option>');
				} else {
					for ( var i = 0, length = options.length; i < length; i++) {
						var option = options[i], value = option.value || option.name;
						rtnResult.push('<option value=\'' + value + '\'');
						if (value == checkVal)
							rtnResult.push(' selected');
						rtnResult.push('>' + option.name + '</option>');
					}
				}
				var select = '<select name=\'' + fieldName + '\'>' + rtnResult.join('') + '</select>';
				$("#" + fieldName).html(select);
			};
		</script>

		<center>
			<form id="mainForm" action="">
				<h3>${lfn:message('hr-staff:hrStaff.robot.formMapping') }</h3>
				<table width="100%" class="tb_normal">
					<tr align="center">
						<td width="20%">
							姓名
						</td>
						<td width="30%">
							${lfn:message('hr-staff:hrStaff.robot.formField') }
						</td>
						<td width="20%">
							属性
						</td>
						<td width="30%">
							${lfn:message('hr-staff:hrStaff.robot.formField') }
						</td>
					</tr>
					<tr align="center">
						<td>姓名</td><td id="fdApplicant"></td>
						<td>所在部门</td><td id="fdOrgParent"></td>
					</tr>
					<tr align="center">
						<td>岗位名称</td><td id="fdOrgPost"></td>
						<td>职类</td><td id="fdCategory"></td>
					</tr>
					<tr align="center">
						<td>职级</td><td id="fdOrgRank"></td>
						<td>直接上级</td><td id="fdReportLeader"></td>
					</tr>
					<tr align="center">
						<td>异动前职级</td><td id="fdBeforeOrgRank"></td>
						<td>异动前直接上级</td><td id="fdBeforeReportLeader"></td>
					</tr>
					<tr align="center">
						<td>异动类型</td><td id="fdMoveType"></td>
						<td>异动时间</td><td id="fdMoveDate"></td>
					</tr>
					<tr align="center">
						<td>办公所在省份</td><td id="fdOfficeAreaProvinceId"></td>
						<td>办公所在市</td><td id="fdOfficeAreaCityId"></td>
					</tr>
					<tr align="center">
						<td>办公所在区</td><td id="fdOfficeAreaAreaId"></td>
						<td>办公详细地址</td><td id="fdOfficeLocation"></td>
					</tr>
				</table>
			</form>
		</center>
	</template:replace>
</template:include>