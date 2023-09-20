<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.sys.admin.dbchecker.core.*" %>
<template:include ref="default.simple">
	<template:replace name="title">
		<bean:message bundle="sys-admin" key="sysAdminDbchecker.checkResult" />
	</template:replace>
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	
		<script type="text/javascript">
		Com_IncludeFile("jquery.js|json2.js|popdialog.js");
        Com_Parameter.CloseInfo='<bean:message key="message.closeWindow"/>';
		Lang = {
				plus: '<bean:message bundle="sys-admin" key ="sysAdminDbchecker.plus" />',
				minus: '<bean:message bundle="sys-admin" key ="sysAdminDbchecker.minus" />',
				noSelect: '<bean:message bundle="sys-admin" key ="sysAdminDbchecker.noSelect" />',
				comfirmRepair: '<bean:message bundle="sys-admin" key ="sysAdminDbchecker.comfirmRepair" />',
				waitting: '<bean:message bundle="sys-admin" key ="sysAdminDbchecker.waitting" />',
				running: '<bean:message bundle="sys-admin" key ="sysAdminDbchecker.running" />',
				start: '<bean:message bundle="sys-admin" key="sysAdminDbchecker.start" />',
				stop: '<bean:message bundle="sys-admin" key="sysAdminDbchecker.stop" />',
				success: '<bean:message bundle="sys-admin" key="sysAdminDbchecker.success" />',
				failure: '<bean:message bundle="sys-admin" key="sysAdminDbchecker.failure" />',
				close: '<bean:message key="button.close" />',
				repairProgress: '<bean:message bundle="sys-admin" key="sysAdminDbchecker.repairProgress" />',
				ignoredManualFix:	'<bean:message bundle="sys-admin" key="sysAdminDbchecker.ignoredManualFixResult" />',
				ignoredManualFixNum:	'<bean:message bundle="sys-admin" key="sysAdminDbchecker.ignoredManualFixResultNum" />'
			};
			function showDetail(id, type) {
				id = jqSelector(id);
				var isExpand = true;
				var _img = $("#img_"+id);
				if(_img) {
					var plus = "${KMSS_Parameter_ResPath}style/default/icons/plus.gif";
					var minus = "${KMSS_Parameter_ResPath}style/default/icons/minus.gif";
					if(_img.attr("src").indexOf(plus) > -1) {
						_img.attr("src", minus).attr("alt", Lang.minus).attr("title", Lang.minus);
					} else {
						isExpand = false;
						_img.attr("src", plus).attr("alt", Lang.plus).attr("title", Lang.plus);
					}
				}
				var _objs = $("table[id='table_result'] tr["+type+"='tr_"+id+"']");
				if(isExpand) {
					_objs.show();
				} else {
					_objs.hide();
				}
			}
		    /**
		    * 对JQuery选择器字符串进行转义（解决选择器字符串中有特殊字符时不能正确获取元素对象）
		    * @param str 选择器字符串   
		    * @return 返回转义之后的字符串
		    */
			function jqSelector(str)
			{
			    return str.replace(/([;&,\/\.\+\*\~':"\!\^#$%@\[\]\(\)=>\|])/g, '\\\\$1');
			}
			function onCbClick(cb, level, moduleName, fdId, normalCheckFlag) {
				var _cb = $(cb);
				var _childs = $(":checkbox:enabled[name^='"+cb.name+"_']"); // 以名称开头
				var checkedFlag = false;
				if(_cb.prop("checked") == true) {
					_childs.prop("checked", true);
					checkedFlag = true;
				} else {
					_childs.prop("checked", false);
				}
				clickEvent(_cb, true);
				//ajax send select download sql items
				var urlStr = "sysAdminDbchecker.do?method=updateCheckedResultItem&checked=" + checkedFlag + "&level=" + level;
				if (moduleName != '') {
					urlStr = urlStr + '&module=' + moduleName;
				}
				if (fdId != '') {
					urlStr = urlStr + '&fdId=' + fdId;
				}

				if (normalCheckFlag) {
					$.ajax({     
		    	     type:"post", 
		    	     data: '',   
		    	     url:urlStr,
		    	     success:function(data){
		    	    	 
					  }
					});
				}
				
			}
			// 父类事件
			function clickEvent(cb, flag) {
				var _parentName = cb.attr("parent");
				if(_parentName != null) {
					var _parent = $(":checkbox[name='"+_parentName+"']");
					if(_parent != null) {
						if(!flag || cb.prop("checked") == false) {
							// 子类不选择，父类不选择
							_parent.prop("checked", false);
						} else {
							// 子类选择，判断父类是否选择（全选）
							var _childs = $(":checkbox:enabled[name^='"+_parent.attr("name")+"_']");
							_childs.each(function() {
								if($(this).prop("checked") == false) {
									flag = false;
									return false; // 中断循环（break）;
								}
							});
							if(flag) {
								_parent.prop("checked", true);
							} else {
								_parent.prop("checked", false);
							}
						}
						clickEvent(_parent, flag);
					}
				}
			}
			function repair(cbname) { // 修复
				var arr = [], i = 0, n = 0, j = 0, m = 0;
				var ignoredItemStr = '';
				var ignoredItemSize = 0;
			
				$(":checkbox:checked[name^='"+cbname+"_']").each(function() {
					var _this = $(this);
					var val = _this.val();
					if(val) {
						var suggestStr = _this.siblings("input[name='suggest_"+val+"']").val();
						
						var repaireStrategy = _this.siblings("input[name='strategy_"+val+"']").val();
						if (repaireStrategy != 'com.landray.kmss.sys.admin.dbchecker.core.NullResultRepairStrategy' && suggestStr == '') {
							arr.push({
								fdId : val,
								priority : _this.siblings("input[name='priority_"+val+"']").val(),
								strategy : _this.siblings("input[name='strategy_"+val+"']").val(),
								param : _this.siblings("input[name='param_"+val+"']").val()
							});
						} else {
							if (ignoredItemStr == '') {
							    ignoredItemStr += _this.siblings("input[name='result_"+val+"']").val();
							    ignoredItemStr += '<br>';
							}
							ignoredItemSize++;
						}
					}
				});
				
				if(ignoredItemStr != '') {
					var infoStr = '<b><font color="red">' + Lang.ignoredManualFix + '&nbsp;'  
					    + ignoredItemSize + '&nbsp;' + Lang.ignoredManualFixNum + '</font></b>'+ ignoredItemStr;
					if(arr.length == 0) {
						infoStr = Lang.noSelect + '<br>' + infoStr;
					}
					seajs.use( [ 'sys/ui/js/dialog' ],function(dialog) {
						dialog.alert(infoStr,function() {
						});
						
					});
					if(arr.length == 0) {
						return;
					}
				} else {
					if(arr.length == 0) {
						seajs.use( [ 'sys/ui/js/dialog' ],function(dialog) {
							dialog.alert(Lang.noSelect,function() {
							});
							
						});
						return;
					}
				}
				
				

				if(!confirm(Lang.comfirmRepair)) {
					return;
				}
				arr.sort(function(x, y) { // 按优先级排序
					if(y.priority && x.priority) {
				    	return y.priority - x.priority;
					} else {
						return -999;
					}
				});
				for(i = 0, n = arr.length; i < n; i++) {
					$("#result_" + arr[i].fdId).html(Lang.waitting); // 等待执行...
				}
				var stopRepair = function (comp) {
					comp.stop();
					for(i = comp.index, n = comp.datas.length; i < n; i++) {
						var dat = comp.datas[i];
						for (j = 0, m = dat.item.length; j < m; j++) {
							$("#result_" + dat.item[j].fdId).html(Lang.stop); // 停止修复
						}
					}
				};
				var component = ajaxSyncComponent(Com_Parameter.ContextPath+"sys/admin/resource/jsp/jsonp.jsp?s_name=com.landray.kmss.sys.admin.dbchecker.service.spring.SysAdminDbRepairService");
				var btns = $("input[name='btnRepair']").attr("value", Lang.stop);
				for(i = 0, n = btns.length; i < n; i++) {
					btns[i].onclick = function() { // 注册停止修复事件
						stopRepair.call(this, component);
					};
				}
				var batch = 15, index = 0; // 批量提交
				for(i = 1, n = arr.length; i <= n; i++) { // 设置参数
					if(i % batch == 0 || i == n) {
						component.addData({item: arr.slice(index, i)});
						index = i;
					}
				}
				component.beforeRequest = function(comp) { // 请求前事件
					var _data = comp.datas[comp.index];
					for(i = 0; i < _data.item.length; i++) {
						$("#result_" + _data.item[i].fdId).html(Lang.running);
					}
				};
				component.afterResponse = function(arr, comp) { // 响应后事件
					for(i = 0; i < arr.length; i++) {
						var res = $("#result_" + arr[i].fdId);
						var sug = "";
						if(arr[i].suggest) {
							sug = "&nbsp;&nbsp;<a href=\"javascript:createDialog('"+htmlEscape(arr[i].suggest.fdName)+"', '"+htmlEscape(arr[i].suggest.fdContent)+"');\">"+htmlEscape(arr[i].suggest.fdName)+"</a>";
						}
						if("SUCCESS" === arr[i].state) {
							$("#"+arr[i].fdId).prop("checked", false).prop("disabled", true); // id查找提高性能
							
							res.css("color","#00FF00").html(Lang.success+sug);
						} else if("FAILURE" === arr[i].state) {
							res.css("color","#FF0000").html(Lang.failure+sug);
						}
						var p = Math.round(comp.index*100/comp.count);
						$("#progress1").html(Lang.repairProgress+p+"%");
						$("#progress2").html(Lang.repairProgress+p+"%");
					}
				};
				component.onComplate = function(comp) { // 完成后事件
					btns.attr("value", Lang.start);
					for(i = 0, n = btns.length; i < n; i++) {
						btns[i].onclick = function() {
								repair();
						};
					}
				};
				component.traverse();
			}
			function htmlEscape(s){
				if(s==null || s=="")
					return "";
				var re = /'/g;
				s = s.replace(re, "\\'");
				return s;
			}

			function createDialog(title, content) {
				var dialog = new PopDialog({width:600, title:title, content:content, buttons:{'<bean:message key="button.close" />': colseDialog}});
				function colseDialog() {
					dialog.close();
				}
			}

	        function reCheck(){
	      		window.open('<c:url value="/sys/admin/sys_admin_dbchecker/sysAdminDbchecker.do" />?method=select&reCheck=true', '_self');
	        }

	        function checkDownloadSql(ev, cbname) { //check selected items
				var i = 0;

				$(":checkbox:checked[name^='"+cbname+"_']").each(function() {
					var _this = $(this);
					i++;
				});
				if (i == 0) {
					ev.preventDefault();
					seajs.use( [ 'sys/ui/js/dialog' ],function(dialog) {
						dialog.alert(Lang.noSelect,function() {
						});
						
					});
				}
	        }
	        
	        function downloadFile(info) {
	        	if ($(":checkbox[name=agree]").is(':checked')) {
	        		window.open('<c:url value="/sys/admin/sys_admin_dbchecker/sysAdminDbchecker.do?method=downloadSql&fdCheckType=3" />' + info, '_blank');
	        	} else {
		        	seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
						dialog.alert('<bean:message bundle="sys-admin" key="sys.admin.commontools.clearLog.download.agree"/>');
					});
	        	}
	        }

	        function toClearLog() {
	        	window.open('<c:url value="/sys/admin/commontools/clearLog.jsp" />', '_blank');
	        }
	    </script>
		<script type="text/javascript" src="<c:url value="/sys/admin/resource/js/jquery.corner.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/sys/admin/resource/js/ajaxSyncComponent.js"/>"></script>
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
	<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
	<ui:toolbar>
			<ui:button text="${lfn:message('sys-admin:sysAdminDbchecker.reCheck')}" onclick="reCheck()"/>
	</ui:toolbar>
	</div>
			</div>
			<p class="txttitle"><bean:message bundle="sys-admin" key="sysAdminDbchecker.checkResult" /></p>
		<br />
		<html:form action="/sys/admin/sys_admin_dbchecker/sysAdminDbchecker.do">
		
			
			<center>
<table width="95%" border="0" align="center">
	<tr>
		<td width="15" bgcolor="#FF0000">&nbsp;</td>
		<td><bean:message bundle="sys-admin" key="sysAdminDbchecker.error" /></td>
		<td></td>
	</tr>
	<tr>
		<td width="15" bgcolor="#FFFF00">&nbsp;</td>
		<td><bean:message bundle="sys-admin" key="sysAdminDbchecker.warn" /></td>
		<td></td>
	</tr>
	<tr>   
		<td width="15" bgcolor="#D1D1D1">&nbsp;</td>
		<td><bean:message bundle="sys-admin" key="sysAdminDbchecker.suggest" /></td>
		<td></td>
	</tr>
	<tr>
		<td width="15" bgcolor="#00FF00">&nbsp;</td>
		<td><bean:message bundle="sys-admin" key="sysAdminDbchecker.info" /></td>
		<td></td>
	</tr>
</table>
<kmss:auth requestURL="/sys/admin/resource/jsp/jsonp.jsp">
<table width=95%>
	<tr>
		<td align="right">
			<span id="progress1" style="color: red"></span>
		</td>
		<td align="right" width="10%">
		
		</td>
	</tr>
</table>
</kmss:auth>
<table class="tb_normal" width=98% id="table_result">
	<tr>
		<td width="35%" class="td_normal_title" style="word-break: keep-all;"><bean:message bundle="sys-admin" key="sysAdminDbchecker.tableName" /></td>
		<td width="45%" class="td_normal_title"><bean:message bundle="sys-admin" key="sysAdminDbchecker.result" /></td>
		<td width="10%" class="td_normal_title"><bean:message bundle="sys-admin" key="sysAdminDbchecker.repairSuggest" /></td>
		<td width="10%" class="td_normal_title"><bean:message bundle="sys-admin" key="sysAdminDbchecker.repairResult" /></td>
	</tr>
	<c:set var="isCheckOk" value="true" />
	<c:set var="isNormalCheck" value="${empty checkType}"/>
	<c:forEach items="${checkResultDetailMap}" var="checkResultDetailMap" varStatus="statusLevel">
	<c:if test="${not empty checkResultDetailMap.value}">
	<c:set var="isCheckOk" value="false" />
	<%-- 错误级别 --%>
	<tr
	<c:choose>
		<c:when test="${'ERROR' eq checkResultDetailMap.key.type}">
			style="background-color:#FF0000"
		</c:when>
		<c:when test="${'WARN' eq checkResultDetailMap.key.type}">
			style="background-color:yellow"
		</c:when>
		<c:when test="${'SUGGEST' eq checkResultDetailMap.key.type}">
			style="background-color:#D1D1D1"
		</c:when>
		<c:when test="${'INFO' eq checkResultDetailMap.key.type}">
			style="background-color:#00FF00"
		</c:when>
	</c:choose>>
		<td>
			<img id="img_${checkResultDetailMap.key.type}" alt="<bean:message bundle="sys-admin" key ="sysAdminDbchecker.minus" />" border="0" 
				src="${KMSS_Parameter_ResPath}style/default/icons/minus.gif" onclick="showDetail('${checkResultDetailMap.key.type}', 'module');"/>
			<label>
				<c:if test="${checkResultDetailMap.key.type eq 'ERROR'}">
					<%-- 错误级别默认选中 --%>
					<input type="checkbox" name="cb_${statusLevel.index}" value="" onclick="onCbClick(this,'<c:out value="${checkResultDetailMap.key.type}"/>','','',<c:out value="${isNormalCheck}"/>);" checked />
				</c:if>
				<c:if test="${checkResultDetailMap.key.type eq 'WARN' || checkResultDetailMap.key.type eq 'SUGGEST'}">
					<input type="checkbox" name="cb_${statusLevel.index}" value="" onclick="onCbClick(this, '<c:out value="${checkResultDetailMap.key.type}"/>','','',<c:out value="${isNormalCheck}"/>);" />
				</c:if>
				<c:out value="${checkResultDetailMap.key.name}" />
			</label>
		</td>
		<td>
		</td>
		<td align="left">
		<c:if test="${empty checkType and !(checkResultDetailMap.key.type eq 'INFO') and !(checkResultDetailMap.key.type eq 'SUGGEST')}">
			<ui:toolbar>
				<ui:button text="${lfn:message('sys-admin:sysAdminDbchecker.start')}" onclick="repair('cb_${statusLevel.index}');"/>
			</ui:toolbar>
		</c:if>
		</td>
		<td align="left">
		<c:if test="${empty checkType and !(checkResultDetailMap.key.type eq 'INFO') }">
			<a id="downloadCheckedSql_${statusLevel.index}" onclick="checkDownloadSql(event, 'cb_${statusLevel.index}');" href="sysAdminDbchecker.do?method=downloadCheckedSql&level=<c:out value="${checkResultDetailMap.key.type}"/>">
			<bean:message bundle="sys-admin" key ="sysAdminDbchecker.downloadSql"/>
			</a>
		</c:if>
		</td>
	</tr>
	<%-- 按模块 --%>
	<c:forEach items="${checkResultDetailMap.value}" var="checkResultModuleMap" varStatus="statusModule">
	<tr module="tr_${checkResultDetailMap.key.type}">
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<img id="img_${checkResultDetailMap.key.type}_${checkResultModuleMap.key}" 
				alt='<bean:message bundle="sys-admin" key ="sysAdminDbchecker.minus" />' border="0" 
				src="${KMSS_Parameter_ResPath}style/default/icons/minus.gif" 
				onclick="showDetail('${checkResultDetailMap.key.type}_${checkResultModuleMap.key}', 'item');"/>
			<label>
				<c:if test="${checkResultDetailMap.key.type eq 'ERROR'}">
					<%-- 错误级别默认选中 --%>
					<input type="checkbox" name="cb_${statusLevel.index}_${statusModule.index}" 
						parent="cb_${statusLevel.index}" value="" onclick="onCbClick(this, '<c:out value="${checkResultDetailMap.key.type}"/>',
						'<c:out value="${checkResultModuleMap.key}"/>', '',<c:out value="${isNormalCheck}"/> );" checked />
				</c:if>
				<c:if test="${checkResultDetailMap.key.type eq 'WARN' || checkResultDetailMap.key.type eq 'SUGGEST'}">
					<input type="checkbox" name="cb_${statusLevel.index}_${statusModule.index}" 
						parent="cb_${statusLevel.index}" value="" onclick="onCbClick(this, '<c:out value="${checkResultDetailMap.key.type}"/>',
						'<c:out value="${checkResultModuleMap.key}"/>', '',<c:out value="${isNormalCheck}"/>);" />
				</c:if>
				<c:out value="${checkResultModuleMap.key}" />
			</label>
		</td>
		<td colspan="3"></td>
	</tr>
	<c:forEach items="${checkResultModuleMap.value}" var="checkResult" varStatus="status">
	<tr module="tr_${checkResultDetailMap.key.type}" item="tr_${checkResultDetailMap.key.type}_${checkResultModuleMap.key}">
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<label>
			<c:set var="disabledCheckbox" value="false" />
			<c:if test="${'com.landray.kmss.sys.admin.dbchecker.core.NullResultRepairStrategy' == checkResult.resultRepair.fdResultRepairStrategy.name || empty checkResult.resultRepair.fdResultRepairStrategy.name}">
				<c:set var="disabledCheckbox" value="true" />
			</c:if>
			<c:if test="${checkResultDetailMap.key.type eq 'ERROR'}">
				<input type="checkbox" name="cb_${statusLevel.index}_${statusModule.index}_${status.index}" id="${checkResult.fdId}"
					parent="cb_${statusLevel.index}_${statusModule.index}" value="${checkResult.fdId}" onclick="onCbClick(this, '<c:out value="${checkResultDetailMap.key.type}"/>',
					'<c:out value="${checkResultModuleMap.key}"/>', '<c:out value="${checkResult.fdId}"/>',<c:out value="${isNormalCheck}"/>);" checked/>
			</c:if>
			<c:if test="${checkResultDetailMap.key.type eq 'WARN' || checkResultDetailMap.key.type eq 'SUGGEST'}">
				<input type="checkbox" name="cb_${statusLevel.index}_${statusModule.index}_${status.index}" id="${checkResult.fdId}"
					parent="cb_${statusLevel.index}_${statusModule.index}" value="${checkResult.fdId}" onclick="onCbClick(this, '<c:out value="${checkResultDetailMap.key.type}"/>',
					'<c:out value="${checkResultModuleMap.key}"/>', '<c:out value="${checkResult.fdId}"/>',<c:out value="${isNormalCheck}"/>);"/>
			</c:if>
			<%-- 优先级 --%>
			<input type="hidden" name="priority_${checkResult.fdId}" value="<c:out value='${checkResult.fdPriority}' />" />
			<%-- 修复策略 --%>
			<input type="hidden" name="strategy_${checkResult.fdId}" value="<c:out value='${checkResult.resultRepair.fdResultRepairStrategy.name}' />" />
			<%-- 参数 --%>
			<input type="hidden" name="param_${checkResult.fdId}" value="<c:out value='${checkResult.resultRepair.jsonString}' />" />
			<c:choose>
				<c:when test="${not empty checkResult.resultRepair.fdSuggest.fdName}">
					<input type="hidden" name="suggest_${checkResult.fdId}" value="<c:out value='${checkResult.resultRepair.fdSuggest.fdContent}'/>" />
				</c:when>
				<c:otherwise>
				    <input type="hidden" name="suggest_${checkResult.fdId}" value="" /> 
				
				</c:otherwise>
			</c:choose>
			<input type="hidden" name="result_${checkResult.fdId}" value="<c:out value='${checkResult.fdResult}' />" />
			
			
			<c:out value="${checkResult.fdTable}" />
			</label>
		</td>
		<td><c:out value="${checkResult.fdResult}" /></td>
		<td>
		<br>
			<c:choose>
				<c:when test="${not empty checkResult.resultRepair.fdSuggest.fdName}">
				<c:choose>
					<c:when test="${not empty checkType and checkType eq '3' and not empty checkResult.resultRepair.fdSuggest.fdDescription}">
					    <a href="javascript:createDialog('<c:out value="${checkResult.resultRepair.fdSuggest.fdName}" />',
					    '<c:out value="${checkResult.resultRepair.fdSuggest.fdDescription}" />');">
					    <c:out value="${checkResult.resultRepair.fdSuggest.fdName}" />
					    </a>
						
					</c:when>
					<c:otherwise>
						<a href="javascript:createDialog('<c:out value="${checkResult.resultRepair.fdSuggest.fdName}" />','<c:out value="${checkResult.resultRepair.fdSuggest.fdContent}" />');">
						<c:out value="${checkResult.resultRepair.fdSuggest.fdName}" />
					</a>
					</c:otherwise>
				</c:choose>
				</c:when>
				<c:otherwise>
					<c:out value="${checkResult.fdLevelType.info}" />
				</c:otherwise>
			</c:choose>
		</td>
		<td>
			<span id="result_${checkResult.fdId}">N/A</span>
		</td>
	</tr>
	</c:forEach>
	</c:forEach>
	</c:if>
	</c:forEach>
	<c:if test="${'true' == isCheckOk}">
		<tr>
			<td colspan="4"><bean:message bundle="sys-admin" key="sysAdminDbchecker.checkResult.ok" /></td>
		</tr>
	</c:if>
</table>
<kmss:auth requestURL="/sys/admin/resource/jsp/jsonp.jsp">
<table width=95%>
	<tr>
		<td align="right">
			<span id="progress2" style="color: red"></span>
		</td>
		<td align="right" width="10%">
			
		</td>
	</tr>
</table>



</kmss:auth>
</center>
<html:hidden property="method_GET" />
</html:form>

	</template:replace>
</template:include>
