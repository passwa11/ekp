<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="title">
		<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl"/>
	</template:replace>
	<template:replace name="head">
		<script src="${LUI_ContextPath}/sys/ui/resource/js/clipboard.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			//判断当前字符串是否以str开始
			if (typeof String.prototype.startsWith != 'function') {
				String.prototype.startsWith = function (str) {
					return this.slice(0, str.length) == str;
				};
			}
			//判断当前字符串是否以str结束
			if (typeof String.prototype.endsWith != 'function') {
				String.prototype.endsWith = function (str){
					return this.slice(-str.length) == str;
				};
			}
			
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				// 解析
				var noHeaderUrl, noHeaderNavUrl, _DNS;
				window.analysis = function() {
					// 解析出来的模块路径
					var path;
					// 取原始的URL
					var url = $("input[name='originalUrl']").val();
					if($.trim(url).length < 1) {
						alert('<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.originalUrl.null"/>');
						return false;
					}
					
					// 对URL进行解析
					var obj = parseURL(url);
					if(window.console)
						window.console.log("url:", obj);
					
					// 判断是否是最新的URL，最新的URL是带有Hash值的
					if(hasProp(obj.hashs)) {
						// 取j_start参数值
						if(obj.hashs.j_start) {
							path = decodeURIComponent(obj.hashs.j_start);
							delete obj.hashs.j_start;
						} else {
							path = obj.path;
						}
					} else {
						path = obj.path;
					}
					if(path.endsWith(".index")) {
						path = path.slice(0, -6) + "/index.jsp";
					} else if(path.endsWith("/")) {
						path += "index.jsp";
					}
					
					if(!path.endsWith("/index.jsp")) {
						alert('<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.originalUrl.error"/>');
						path = null;
						return false;
					}

					noHeaderUrl = path + "?";
					noHeaderNavUrl = path + "?";
					if(obj.params) {
						var __params = "";
						for(var k in obj.params) {
							if (typeof obj.params[k] == 'string') {
								__params += k + "=" + obj.params[k] + "&";
							}
						}
						if(__params.length > 0) {
							noHeaderUrl += __params;
							noHeaderNavUrl += __params;
						}
					}
					noHeaderUrl += "j_iframe=true";
					noHeaderNavUrl += "j_iframe=true&j_aside=false";
					
					if(hasProp(obj.hashs)) {
						var _hash = "";
						for(key in obj.hashs) {
							_hash += "&" + key + "=" + obj.hashs[key]
						}
						
						noHeaderUrl += "#" +_hash.slice(1);
						noHeaderNavUrl += "#" +_hash.slice(1);
					}
					
					// 获取DNS
					_DNS = obj.protocol + "://" + obj.host + (obj.port == "" ? "" : ":" + obj.port) + obj.contextPath;
					// 是否要去除DNS
					var noDns = document.getElementsByName("noDns")[0].checked;
					if(!noDns) {
						noHeaderUrl = _DNS + noHeaderUrl;
						noHeaderNavUrl = _DNS + noHeaderNavUrl;
					}
					
					$("input[name='noHeaderUrl']").val(noHeaderUrl);
					$("input[name='noHeaderNavUrl']").val(noHeaderNavUrl);
				}
				
				window.hasProp = function(obj) {
					var hasProp = false;
				    for (var prop in obj) {
				        hasProp = true;
				        break;
				    }
				    return hasProp;
				}
				
				// 帮助
				window.help = function() {
					var infos = [];
					infos.push({url:'/sys/portal/page.jsp#j_start=%2Fkm%2Freview.index&j_target=_iframe', node:''});
					infos.push({url:'/km/review/index.jsp?j_module=true#j_path=%2FallArchives&mydoc=all', node:''});
					infos.push({url:'/km/review/index.jsp', node:''});
					infos.push({url:'/km/review.index', node:''});
					infos.push({url:'/km/review/', node:'<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.help.node1"/>'});
					
					var html = [];
					html.push('    <table class="tb_normal" width=95%>');
					html.push('        <tbody>');
					html.push('            <tr>');
					html.push('                <td class="td_normal_title" colspan="3">');
					html.push('                    <center><h3><bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.help.info"/></h3></center>');
					html.push('                </td>');
					html.push('            </tr>');
					html.push('            <tr>');
					html.push('                <td class="td_normal_title" width="8%">');
					html.push('                    <center><bean:message key="page.serial"/></center>');
					html.push('                </td>');
					html.push('                <td class="td_normal_title" width="65%">');
					html.push('                    <center>URL</center>');
					html.push('                </td>');
					html.push('                <td class="td_normal_title" width="25%">');
					html.push('                    <center><bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.help.node"/></center>');
					html.push('                </td>');
					html.push('            </tr>');
					$.each(infos, function(i, info) {
						html.push('            <tr>');
						html.push('                <td>');
						html.push('                    <center>'+(i+1)+'</center>');
						html.push('                </td>');
						html.push('                <td align="left">');
						html.push('                    '+info['url']);
						html.push('                </td>');
						html.push('                <td>');
						html.push('                    '+info['node']);
						html.push('                </td>');
						html.push('            </tr>');
					});
					html.push('        </tbody>');
					html.push('    </table>');
					
					dialog.build({
						config : {
							width : 800,
							height : 300,
							lock : true,
							title : '<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.help"/>',
							content : {
								type : "Html",
								html : html.join(""),
								buttons : [ {
									name : "<bean:message key='button.ok'/>",
									value : true,
									focus : true,
									fn : function(value, dialogFr) {
										dialogFr.hide();
									}
								}]
							}
						},
					}).show();
				}
				
				// 预览
				window.preview = function(name) {
					var noDns = document.getElementsByName("noDns")[0].checked;
					var url = $("input[name='" + name + "']").val();
					
					if(noDns) {
						url = _DNS + url;
					}
					window.open(url, "_blank");
				}
				
				// 使用
				window.apply = function(name) {
					var url = $("input[name='" + name + "']").val();
					if($.trim(url).length > 0)
						$dialog.hide(url);
					else
						dialog.alert('<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.analysis.error"/>');
				}
				
				// 复制
				window.copy = function(name) {
					$("#copy_result_tr > td").empty();
					var url = $("input[name='" + name + "']").val();
					$('#__copy__').attr('data-clipboard-text', url);
					$('#__copy__').click();
				}

				var myVar, count = 0;
				window._getURL = function() {
					// 防止死循环
					if (count++ > 10) {
						clearTimeout(myVar);
						dialog.alert('<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.param.url.null"/>');
						return;
					}
					if (window.$dialog) {
						var url = window.$dialog.content.params.url;
						$("input[name='originalUrl']").val(url);
						clearTimeout(myVar);
					} else {
						myVar = setTimeout("_getURL()", 100);
					}
				}
				
				window.clearMessage = function() {
					if (count++ > 2) {
						clearTimeout(myVar);
						$("#copy_result_tr > td").html("");
						count = 0;
						return;
					}
					myVar = setTimeout("clearMessage()", 1000);
				}

				$(function() {
					var isDialog = "${JsParam._dialog}";
					if (isDialog == "true") {
						$("#noHeaderUrl_apply_btn").show();
						$("#noHeaderNavUrl_apply_btn").show();
						_getURL();
					} else {
						$("#noHeaderUrl_copy_btn").show();
						$("#noHeaderNavUrl_copy_btn").show();
						$("#copy_result_tr").show();
						$("#copy_result_tr > td").empty();
						
						// 初始化复制功能
						var clipboard = new ClipboardJS('#__copy__');
						clipboard.on('success', function(e) {
							if(window.console) {
								window.console.info('e:', e);
							}
						    e.clearSelection();
						    $("#copy_result_tr > td").html('<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.copy.success"/>');
						    clearMessage();
						});
						clipboard.on('error', function(e) {
							dialog.alert('<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.copy.error"/>');
						});
					}
				});
			});

			function parseURL(url) {
				var a = document.createElement('a');
				a.href = url;
				var contextPath = '${LUI_ContextPath}';
				return {
					contextPath : contextPath,
					source : url,
					protocol : a.protocol.replace(':', ''),
					host : a.hostname,
					port : a.port,
					query : a.search,
					params : (function() {
						var ret = {}, seg = a.search.replace(/^\?/, '').split('&'), len = seg.length, i = 0, s;
						for (; i < len; i++) {
							if (!seg[i]) {
								continue;
							}
							s = seg[i].split('=');
							ret[s[0]] = s[1];
						}
						return ret;
					})(),
					file : (a.pathname.match(/\/([^\/?#]+)$/i) || [ , '' ])[1],
					hash : a.hash.replace('#', ''),
					hashs : (function() {
						var ret = {}, seg = a.hash.replace('#', '').split('&'), len = seg.length, i = 0, s;
						for (; i < len; i++) {
							if (!seg[i]) {
								continue;
							}
							s = seg[i].split('=');
							ret[s[0]] = s[1];
						}
						return ret;
					})(),
					path : a.pathname.replace(/^([^\/])/, '/$1').replace(contextPath + '/', '/'),
					relative : (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [ , '' ])[1].replace(contextPath + '/', '/'),
					segments : a.pathname.replace(/^\//, '').split('/')
				};
			}
		</script>
	</template:replace>
	<template:replace name="content"> 
		<div class="lui_form_content_frame" style="padding-top:20px;">
			<p class="txttitle"><bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl"/></p>
			<table class="tb_simple" width=100%>
				<tr>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.originalUrl"/>
					</td>
					<td>
						<input type="text" name="originalUrl" class="inputsgl" value="${HtmlParam.url}" style="width:80%;"/>
						<ui:button text="${lfn:message('sys-admin:sys.admin.commontools.generateUrl.analysis') }" onclick="analysis();"></ui:button>
						<ui:button text="${lfn:message('sys-admin:sys.admin.commontools.generateUrl.help') }" styleClass="lui_toolbar_btn_gray" onclick="help();"></ui:button>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.other"/>
					</td>
					<td>
						<label><input type="checkbox" name="noDns" value="1" checked="checked"/> ${lfn:message('sys-admin:sys.admin.commontools.generateUrl.other.noDns') }</label>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.noHeaderUrl"/>
					</td>
					<td>
						<input type="text" name="noHeaderUrl" class="inputsgl" style="width:80%;"/>
						<ui:button text="${lfn:message('sys-admin:sys.admin.commontools.generateUrl.preview') }" onclick="preview('noHeaderUrl');"></ui:button>
						<ui:button id="noHeaderUrl_apply_btn" text="${lfn:message('sys-admin:sys.admin.commontools.generateUrl.apply') }" onclick="apply('noHeaderUrl');" style="display:none;"></ui:button>
						<ui:button id="noHeaderUrl_copy_btn" text="${lfn:message('sys-admin:sys.admin.commontools.generateUrl.copy') }" onclick="copy('noHeaderUrl');" style="display:none;"></ui:button>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl.noHeaderNavUrl"/>
					</td>
					<td>
						<input type="text" name="noHeaderNavUrl" class="inputsgl" style="width:80%;"/>
						<ui:button text="${lfn:message('sys-admin:sys.admin.commontools.generateUrl.preview') }" onclick="preview('noHeaderNavUrl');"></ui:button>
						<ui:button id="noHeaderNavUrl_apply_btn" text="${lfn:message('sys-admin:sys.admin.commontools.generateUrl.apply') }" onclick="apply('noHeaderNavUrl');" style="display:none;"></ui:button>
						<ui:button id="noHeaderNavUrl_copy_btn" text="${lfn:message('sys-admin:sys.admin.commontools.generateUrl.copy') }" onclick="copy('noHeaderNavUrl');" style="display:none;"></ui:button>
					</td>
				</tr>
				<tr id="copy_result_tr" style="display: none;">
					<td colspan="2" align="center" style="color:red"></td>
				</tr>
			</table>
		</div>
		
		<div style="display: none;">
			<button id="__copy__"></button>
		</div>
	</template:replace>
</template:include>