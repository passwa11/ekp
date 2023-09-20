<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="title">
		<bean:message bundle="sys-modeling-base" key="modelingDbchecker.dbchecker" />
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
		<html:form action="/sys/modeling/base/modelingDbCheckAction.do?method=runDbCheckTask">
			<p class="txttitle"><bean:message bundle="sys-modeling-base" key="modelingDbchecker.dbchecker"/></p>
			<center>
			<div id="div_main" class="div_main">
			<table width="100%" class="tb_normal" cellspacing="1">
			<c:if test="${not empty sqlserver}">
				<tr>
					<td class="rd_title primary-title">
						<label>
							<html:radio property="fdCheckType" value="3" onclick="chooesType(this.value);" /><bean:message key="modelingDbchecker.fdCheckType.3" bundle="sys-modeling-base" />
						</label>
					</td>
				</tr>
			</c:if>
				<tr>
					<td class="rd_title">
						<label>
							<html:radio property="fdCheckType" value="1" onclick="chooesType(this.value);" /><bean:message key="modelingDbchecker.fdCheckType.1" bundle="sys-modeling-base" />
						</label>
					</td>
				</tr>
				<tr>
					<td class="rd_title">
						<label>
							<html:radio property="fdCheckType" value="2" onclick="chooesType(this.value);" /><bean:message key="modelingDbchecker.fdCheckType.2" bundle="sys-modeling-base" />
						</label>
					</td>
				</tr>

				<tr id="advance" style="display: none">
					<td align="center" style="border: 0px;">
						<table class="tb_noborder" width="100%">
							<tr>
								<td>
									<div style="border-bottom: 1px dashed; height: 25px;">
										<div style="float: left; margin-left: 5px; font-weight: bold;"><bean:message key="modelingDbchecker.byAppModel" bundle="sys-modeling-base" /></div>
										<div style="float: right; margin-right: 5px;">
											<label>
												<input type="checkbox" name="fdCheckScopeAll" value="all" onclick="selectAll(this);" /><bean:message key="modelingDbchecker.selectAll" bundle="sys-modeling-base" />
							  				</label>
						  				</div>
					  				</div>
			  						<div style="padding: 3px;">
									<table style="width:100%" class="tb_dotted">
										<tr>
										<c:forEach items="${moduleList}" var="element" varStatus="status">
									  		<td style="border: 0" width="25%">
									  			<label>
									  				<html:checkbox property="fdCheckScope" value="${element['name']}" onclick="selectElement(this);" />
													<c:out value="${element['name']}" />
												</label>
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
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<center>
			<table width="auto" class="tb_noborder" style="margin-top: 10px; background-color: transparent;"  cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<a href="javascript:void(0);" class="btn_submit_txt"
							onclick="window.check()"><bean:message bundle="sys-modeling-base" key="modelingDbchecker.startCheck" /></a>
					</td>
				</tr>
			</table>
			</center>
			</div>
			</center>
			<html:hidden property="method_GET" />
			</html:form>

		<script type="text/javascript">
			var typeVar = "1";
			<c:if test="${not empty sqlserver}">
				typeVar = "3";
			</c:if>
			function chooesType(val) {
				var advance = document.getElementById("advance");
				typeVar = val;
				if(val == "1") {
					advance.style.display = 'none';
				} else if(val == "3") {
					advance.style.display = 'none';
				} else {
					advance.style.display = '';
				}
			}
			//全选
			function selectAll(obj) {
				var _fdCheckScope = document.getElementsByName("fdCheckScope");
				for(var i = 0; i < _fdCheckScope.length; i++){
					if(obj.checked) {
						_fdCheckScope[i].checked = true;
					} else {
						_fdCheckScope[i].checked = false;
					}
				}
			}
			//单个的选择
			function selectElement(element){
				if(element && !element.checked){
					document.getElementsByName("fdCheckScopeAll")[0].checked = false;
				} else {
					var flag = true;
					var _fdCheckScope = document.getElementsByName("fdCheckScope");
					for (var j = 0; j < _fdCheckScope.length; j++){
						if(!_fdCheckScope[j].checked){
							flag = false;
							break;
						}
					}
					if(flag) { //勾选全选
						document.getElementsByName("fdCheckScopeAll")[0].checked = true;
					} else {
						document.getElementsByName("fdCheckScopeAll")[0].checked = false;
					}
				}
			}
			$(document).ready(function() {
				chooesType(typeVar);
				
			});

			seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				window.check = function() {
					var flag = false;
					
					if(typeVar == "1") {
						flag = true;
					} else if(typeVar == "3") {
						flag = true;
					} else {
						var _fdCheckScope = document.getElementsByName("fdCheckScope");
						for(var i = 0; i < _fdCheckScope.length; i++) {
							if(_fdCheckScope[i].checked) {
								flag = true;
								break;
							}
						}
					}
					if(flag) {
						dialog.confirm('<bean:message bundle="sys-modeling-base" key="modelingDbchecker.startCheck.comfirm"/>', function(value){
							if(value == true) {
								Com_Submit(document.modelingDbcheckerForm, 'runDbCheckTask');
								// 开启进度条
								window.progress = dialog.progress(false);
								window._progress();
							}
						});
					} else {
						dialog.alert('<bean:message bundle="sys-modeling-base" key="modelingDbchecker.noSelect"/>');
						return false;
					}
				}
				
				window._progress = function () {
					var data = new KMSSData();
					data.UseCache = false;
					data.AddBeanData("modelingDbCheckerXMLDataBean");
					var rtn = data.GetHashMapArray()[0];
					
					if(window.progress) {
						if(rtn.checkState == 1) {
							window.progress.hide();
						}
						// 设置进度提示
						window.progress.setProgressText(rtn.checkMessage);
						// 设置进度值
						var currentCount = rtn.checkCurrentCount || 0;
						var allCount = rtn.checkAllCount || 0;
						if(allCount == 0) {
							window.progress.setProgress(0);
						} else {
							window.progress.setProgress(currentCount, allCount);
						}
					}
					// 如果总数量等于-1，表示操作还未执行
					// 如果当前执行的数量比总数量少，表示执行未结束
					if(rtn.checkState != 1) {
						setTimeout("window._progress()", 1000);
					}
				} 
			});
		</script>
	</template:replace>
</template:include>
