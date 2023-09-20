<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.profile.util.SysProfileI18nConfigUtil"%>
<%@ page import="java.util.List,java.util.Map"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
List<Map<String, String>> moduleList = SysProfileI18nConfigUtil.getModuleList(true);
request.setAttribute("moduleList", moduleList);
%>

<template:include ref="default.simple">
	<template:replace name="title">
		<bean:message bundle="sys-profile" key="sys.profile.i18n.checker"/>
	</template:replace>
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<script type="text/javascript">
			Com_IncludeFile("data.js");
		</script>
		<link rel="stylesheet" href="<c:url value="/sys/admin/resource/images/dbcheck_select.css"/>?s_cache=${LUI_Cache}" />
		<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
		<style>
		.txttitle {
		    text-align: center;
		    font-size: 18px;
		    line-height: 30px;
		    color: #3e9ece;
		    font-weight: bold;
		}
		</style>
	</template:replace>
	<template:replace name="body">
		<br />
			<p class="txttitle"><bean:message bundle="sys-profile" key="sys.profile.i18n.checker"/></p>
			<center>
			<div id="div_main" class="div_main">
			<table width="100%" class="tb_normal" cellspacing="1">
				<tr>
					<td class="rd_title">
						<label>
							<input type="radio" name="fdCheckType" value="1" onclick="chooesType(this.value);" checked="checked"/>&nbsp;<bean:message key="sys.profile.i18n.checker.type.1" bundle="sys-profile" />
						</label>
					</td>
				</tr>
				<tr>
					<td class="rd_title">
						<label>
							<input type="radio" name="fdCheckType" value="2" onclick="chooesType(this.value);"/>&nbsp;<bean:message key="sys.profile.i18n.checker.type.2" bundle="sys-profile" />
						</label>
					</td>
				</tr>

				<tr id="advance" style="display: none">
					<c:if test="${moduleList.isEmpty()}">
					<td style='text-align: center;'><bean:message key="message.noRecord"/></td>
					</c:if>
					<c:if test="${! moduleList.isEmpty()}">
					<td align="center" style="border: 0px;">
						<table class="tb_noborder" width="100%">
							<tr>
								<td>
									<div style="border-bottom: 1px dashed; height: 25px;">
										<div style="float: left; margin-left: 5px; font-weight: bold;"><bean:message key="sys.profile.i18n.reset.info" bundle="sys-profile" /></div>
					  				</div>
			  						<div style="padding: 3px;">
									<table style="width:100%" class="tb_dotted">
										<tr>
										<c:forEach items="${moduleList}" var="element" varStatus="status">
									  		<td style="border: 0" width="25%">
									  			<label>
									  				<input type="radio" name="urlPrefix" value="${element['urlPrefix']}"/>&nbsp;<c:out value="${element['name']}" />
												</label>
												&nbsp;&nbsp;&nbsp;
												<span style="cursor:pointer;color:red" onclick="showDetails('${element['urlPrefix']}')"><bean:message key="sys.profile.i18n.checker.details" bundle="sys-profile" /></span>
											</td>
										<c:if test="${(status.index+1) mod 4 eq 0}">
										</tr>
										<c:if test="${!(status.last)}">
										<tr>
										</c:if>
										</c:if>
										</c:forEach>
										<c:if test="${fn:length(moduleList) mod 4 ne 0}">
											<c:if test="${entriesDesignCount mod 4 eq 1}">
												<td style="border: 0" width="25%"></td>
												<td style="border: 0" width="25%"></td>
												<td style="border: 0" width="25%"></td>
											</c:if>
											<c:if test="${fn:length(moduleList) mod 4 eq 2}">
												<td style="border: 0" width="25%"></td>
												<td style="border: 0" width="25%"></td>
											</c:if>
											<c:if test="${fn:length(moduleList) mod 4 eq 3}">
												<td style="border: 0" width="25%"></td>
											</c:if>
										</c:if>
										</tr>
									</table>
									</div>
								</td>
								</c:if>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<center>
			<table width="auto" class="tb_noborder" style="margin-top: 10px; background-color: transparent;"  cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<a href="javascript:void(0);" class="btn_submit_txt" onclick="window.check()"><bean:message key="button.ok" /></a>
					</td>
				</tr>
			</table>
			</center>
			</div>
			</center>

		<script type="text/javascript">
			var typeVar = "1";
			function chooesType(val) {
				var advance = document.getElementById("advance");
				typeVar = val;
				if(val == "1") {
					advance.style.display = 'none';
				} else {
					advance.style.display = '';
				}
			}
			$(document).ready(function() {
				chooesType(typeVar);
				
			});
			
			function showDetails(urlPrefix) {
				window.open("${LUI_ContextPath}/sys/profile/i18n/reset_details.jsp?urlPrefix=" + urlPrefix, "_blank")
			}

			seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				window.check = function() {
					var urlPrefix;
					if(typeVar == "1") {
						urlPrefix = "__all__";
					} else {
						urlPrefix = $("input[name=urlPrefix]:checked").val();
					}
					
					if(!urlPrefix || urlPrefix.length < 1) {
						dialog.alert("${ lfn:message('sys-profile:sys.profile.i18n.tools3.info1') }");
						return;
					}
					
					dialog.confirm("${ lfn:message('sys-profile:sys.profile.i18n.tools3.info2') }", function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url: '<c:url value="/sys/profile/i18n/sysProfileI18nConfig.do?method=resetI18n"/>',
								type: 'POST',
								dataType: 'text',
								data:$.param({"urlPrefix": urlPrefix}, true),
								success: function(data) {
									if(window.del_load != null){
										window.del_load.hide(); 
									}
									if(data == 'true') {
										dialog.success("${ lfn:message('return.optSuccess') }");
									} else {
										dialog.failure("${ lfn:message('return.optFailure') }");
									}
								}
						   });
						}
					});
				}
			});
		</script>
	</template:replace>
</template:include>
