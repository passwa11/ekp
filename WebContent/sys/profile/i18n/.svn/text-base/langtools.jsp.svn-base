<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.landray.kmss.web.Globals"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.profile.util.SysProfileI18nConfigUtil"%>
<%@ page import="com.landray.kmss.sys.config.util.LanguageUtil"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String quicklyEdit = request.getParameter("quicklyEdit");
	if (quicklyEdit != null) {
		if ("true".equals(quicklyEdit)) {
			session.setAttribute("LANG_QUICKLY_EDIT", "true");
		} else {
			session.removeAttribute("LANG_QUICKLY_EDIT");
		}
	}

	String debug = request.getParameter("debug");
	if (debug != null) {
		if ("true".equals(request.getParameter("debug"))) {
			session.setAttribute("LANG_TOOLS_DEBUG", "true");
		} else {
			session.removeAttribute("LANG_TOOLS_DEBUG");
			session.removeAttribute("LANG_QUICKLY_EDIT");
		}
	}
	
	String localeLang = request.getParameter("j_lang");
	if (localeLang != null) {
		session.setAttribute(Globals.LOCALE_KEY, ResourceUtil.getLocale(localeLang));
	}else{
		Locale xlocale = ((Locale)session.getAttribute(Globals.LOCALE_KEY));
		if(xlocale != null)
			localeLang = xlocale.getLanguage();
	}
%>
<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		${ lfn:message('sys-profile:tools.i18n.title') }
	</template:replace>
	<template:replace name="head">
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/i18n.css?s_cache=${LUI_Cache}" />
        <style>
			.lui_handover_searchResult table th {
				text-align: center;
			}
			table td.rd_title {
				font-weight: bold;
				background-color: #f6f6f6;
			}
			.module_title {
				font-size: 16px;
				font-weight: bold;
				background-color: #f6f6f6;
				text-align: left;
			}
		</style>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="7">
			<% if(ResourceUtil.isDebug()) { %>
				<ui:button text="${ lfn:message('sys-profile:sys.profile.i18n.debug.close') }" order="1" onclick="switchDebug('false');"></ui:button>
				<% if(ResourceUtil.isQuicklyEdit()){ %>
					<ui:button text="${ lfn:message('sys-profile:sys.profile.i18n.tools6.close') }" order="2" onclick="switchQuicklyEdit('false');"></ui:button>
				<% } else { %>
					<ui:button text="${ lfn:message('sys-profile:sys.profile.i18n.tools6.open') }" order="2" onclick="switchQuicklyEdit('true');"></ui:button>
				<% } %>
			<% } else { %>
				<ui:button text="${ lfn:message('sys-profile:sys.profile.i18n.debug.open') }" order="1" onclick="switchDebug('true');"></ui:button>
			<% } %>
			<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
				<ui:button text="${ lfn:message('sys-profile:sys.profile.i18n.tools1') }" order="2" onclick="clearCache()"></ui:button>
				<ui:button text="${ lfn:message('sys-profile:sys.profile.i18n.tools3') }" order="3" onclick="resetI18n()"></ui:button>
				<ui:button text="${ lfn:message('sys-profile:sys.profile.i18n.tools2') }" order="4" onclick="clearFileKey()"></ui:button>
			</kmss:authShow>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content"> 
		<script>
			Com_IncludeFile("base64.js");
			function switchDebug(value) {
				var url = Com_SetUrlParameter(location.href, "debug", value);
				if(value == 'false'){
					url = Com_SetUrlParameter(url, "quicklyEdit", value);
				}
				location.href = url;
			}
			function switchQuicklyEdit(value) {
				var url = Com_SetUrlParameter(location.href, "quicklyEdit", value);
				location.href = url;
			}
			
			seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
				clearFileKey = function() {
					dialog.confirm("${ lfn:message('sys-profile:sys.profile.i18n.tools2.info') }", function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url: '<c:url value="/sys/profile/i18n/sysProfileI18nConfig.do?method=clearFileKey"/>',
								type: 'GET',
								dataType: 'text',
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
				
				clearCache = function() {
					dialog.iframe("/sys/profile/i18n/clearCache.jsp", "${ lfn:message('sys-profile:sys.profile.i18n.tools1') }", function(value) {
					}, {
						"width" : '75%',
						"height" : 500
					});
				}
				
				resetI18n = function() {
					dialog.iframe("/sys/profile/i18n/resetI18n.jsp?isReset=1", "${ lfn:message('sys-profile:sys.profile.i18n.tools3') }", function(value) {
					}, {
						"width" : '75%',
						"height" : 500
					});
				}
				</kmss:authShow>
				
				detail = function(val, isDiffer) {
					if(!val || val.length < 1) {
						dialog.alert("${ lfn:message('sys-profile:sys.profile.i18n.langtools.noMsg') }");
						return false;
					}
					dialog.iframe("/sys/profile/i18n/sysProfileI18nConfig.do?method=viewDetailOriginal&messageKey=" + val + "&isDiffer=" + isDiffer,
							"${ lfn:message('sys-profile:sys.profile.i18n.langtools.detailOriginal') }", function(value) {
					}, {
						"width" : 700,
						"height" : 400
					});
				}
				
				chooesType = function(value) {
					$("tr[name=items]").hide();
					$("#" + value).show();
				}
				
				$(function() {
					chooesType("module");
				});
				
				//搜索框按enter即可触发搜索
				enterTrigleSelect = function(event) {
					if (event && event.keyCode == '13') {
						var val = $("input[name='checkType']:checked").val();  
						if(val == "keyWord") {
							nextOperation();
						} else {
							dialog_moduleSelect();
						}
					}
				}
				
				// 下一步
				nextOperation = function() {
					$("#errorMsg").text("");
					// 隐藏搜索区域
					$("#searchDiv").hide();
					// 显示结果区域
					$("#resultDiv").show();
					window.del_load = dialog.loading();
					// 类型
					var checkType = $("input[name=checkType]:checked").val();
					if("module" == checkType) {
						var urlPrefix = $("input[name=urlPrefix]:checked").val();
						if(!urlPrefix) {
							$("#errorMsg").text("${ lfn:message('sys-profile:sys.profile.i18n.seach.module.null') }");
							return;
						}
						loadData(checkType, urlPrefix);
					} else {
						var keyWord = $("input[name=keyWord]").val();
						if(!keyWord || $.trim(keyWord).length < 1) {
							$("#errorMsg").text("${ lfn:message('sys-profile:sys.profile.i18n.seach.keyWord.null') }");
							return;
						}
						loadData(checkType, keyWord);
					}
				};
				previousOperation = function() {
					// 显示搜索区域
					$("#resultDiv").hide();
					// 隐藏结果区域
					$("#searchDiv").show();
				};
				
				// 提交操作
				submitOperation = function() {
					window.del_load = dialog.loading();
					$("input[name=methodType]").val($("input[name=checkType]:checked").val());
					// 针对某些资源信息里包含类似脚本信息，普通的提交会被拦截，所以统一进行Base64编码后再提交，后台接收到数据时进行Base64解码
					var dataArray = $("form[name=resultContentForm]").serializeArray();
					var newDatas = {};
					for(var i=0; i<dataArray.length; i++) {
						var data = dataArray[i];
						newDatas[data['name']] = Base64.encode(data['value']);
					}
					$.post('<c:url value="/sys/profile/i18n/sysProfileI18nConfig.do?method=saveMessage"/>',
							newDatas, function(result) {
						if(window.del_load != null) {
							window.del_load.hide();
						}
						if(result.state) {
							dialog.success("${ lfn:message('sys-profile:sys.profile.i18n.saveMessage.success') }");
						} else {
							dialog.alert(result.message);
						}
					}, "json");
				}
			});
		</script>
		
		<!-- 主体开始 -->
        <div id="lui_handover_w main_body" class="lui_handover_w main_body">
            <div class="lui_handover_header">
                <%-- 标题 --%>
                <span>
             		<% if(ResourceUtil.isDebug()) { %>
             		${ lfn:message('sys-profile:sys.profile.i18n.debug.mode') } ${ lfn:message('sys-profile:sys.profile.i18n.debug.mode.open') }
             		<% } else { %>
             		${ lfn:message('sys-profile:sys.profile.i18n.debug.mode') } ${ lfn:message('sys-profile:sys.profile.i18n.debug.mode.close') }
             		<% } %>
                </span>
            </div>
            <div class="lui_handover_content" id="searchDiv">
                <%-- 查询条件 --%>
                <table class="tb_simple lui_handover_headTb lui_sheet_c_table">
                    <tr>
						<td height="15px" class="rd_title" colspan="2">
							<label>
								<input type="radio" name="checkType" value="module" onclick="chooesType(this.value);" checked/>${ lfn:message('sys-profile:sys.profile.i18n.seach.module') }
							</label>
						</td>
					</tr>
                    <tr id="module" name="items" style="display: none">
                    	<td colspan="2">
                    		<!-- 内容 -->
							<c:import url="/sys/profile/i18n/module_list.jsp" charEncoding="UTF-8"></c:import>
                    	</td>
                    </tr>
                    <tr>
						<td height="15px" class="rd_title" colspan="2">
							<label>
								<input type="radio" name="checkType" value="keyWord" onclick="chooesType(this.value);"/>${ lfn:message('sys-profile:sys.profile.i18n.seach.keyWord') }
							</label>
						</td>
					</tr>
                    <tr id="keyWord" name="items" style="display: none">
                        <td width="10%" style="text-align:right;"><b>${ lfn:message('sys-profile:sys.profile.i18n.seach.keyWord.text') }</b></td>
						<td width="80%" id="from_edit">
							<input type="text" name="keyWord" size="100" onkeyup='enterTrigleSelect(event);'>
							<br>
							<%
								String currentLang = SysProfileI18nConfigUtil.getCurrentLangString();
								Map<String, String> supportedLangs = SysProfileI18nConfigUtil.getSupportedLangs();
								String langString = supportedLangs.get(currentLang);
								String __info = ResourceUtil.getString(request, "sys.profile.i18n.seach.keyWord.info", "sys-profile", new Object[]{currentLang, langString});
							%>
							<%=__info%>
							<br>
							<%=LanguageUtil.getLangHtml(request, "j_lang", localeLang)%>
							<script>
								//语言切换
								function changeLang(value) {
									var url = document.location.href;
									var temp = location.href.split("#");
									url = Com_SetUrlParameter(temp[0], "j_lang", value);
									if (temp.length > 1) {
										url = url + "#" + temp[1];
									}
									location.href = url;
								}
							</script>
						</td>
                    </tr>
               	 </table>
				 <div class="lui_handover_btn_w">
		               <ui:button style="width:100px" text="${ lfn:message('sys-profile:sys.profile.i18n.seach.next') }" onclick="nextOperation();"/>
		               <span id="errorMsg" style="color: red;"></span>
				 </div>
	         </div>
	         <%-- 结果部分--%>
	         <div class="lui_handover_searchResult" style="display: none" id="resultDiv">
	            <%@include file="/sys/profile/i18n/message_list.jsp"%>
	            <div class="lui_handover_btn_w">
	                 <ui:button style="width:100px" text="${ lfn:message('sys-profile:sys.profile.i18n.seach.previous') }" onclick="previousOperation();"/>
	           		<ui:button style="width:100px" text="${ lfn:message('sys-profile:sys.profile.i18n.seach.submit') }" onclick="submitOperation();"/>
	            </div>
	         </div>
	 	 </div>
     <!-- 主体结束 -->
	</template:replace>
</template:include>