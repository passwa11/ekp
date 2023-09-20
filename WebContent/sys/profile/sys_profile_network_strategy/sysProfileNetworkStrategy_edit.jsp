<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.profile.forms.SysProfileNetworkStrategyForm"%>
<%@page import="com.landray.kmss.sys.profile.util.IPUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	SysProfileNetworkStrategyForm mainForm = (SysProfileNetworkStrategyForm)request.getAttribute("sysProfileNetworkStrategyForm");
	int ipType = 4;
	if(StringUtil.isNotNull(mainForm.getFdStartIp()) && mainForm.getFdStartIp().indexOf(":")>-1) {
		ipType = 6;
	}
	pageContext.setAttribute("ipType", ipType);
%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<center>
		<html:form action="/sys/profile/sysProfileNetworkStrategy.do?method=save" >
		<xform:text property="fdStartIp" showStatus="noShow"/>
		<xform:text property="fdEndIp" showStatus="noShow"/>
		<ui:tabpanel id="iptab" layout="sys.ui.tabpanel.list">
			<ui:content title="ipv4">
				<table class="tb_normal" style="margin-top: 20px" width=98%>
					<tr>
						<!-- 起始IP ipv4-->
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-profile:sys.profile.networkStrategy.fdStartIp') }
						</td>
						<td colspan="3">
							<div>
							<input type="text" name="fdStartIp4_1" class="inputsgl" size="4" validate="required digits range(0,255)">
							<b>.</b>
							<input type="text" name="fdStartIp4_2" class="inputsgl" size="4" validate="required digits range(0,255)">
							<b>.</b>
							<input type="text" name="fdStartIp4_3" class="inputsgl" size="4" validate="required digits range(0,255)">
							<b>.</b>
							<input type="text" name="fdStartIp4_4" class="inputsgl" size="4" validate="required digits range(0,255)">
							<span class="txtstrong">*</span>
							</div>
						</td>
					</tr>
					<tr>
						<!-- 截止IP ipv4-->
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-profile:sys.profile.networkStrategy.fdEndIp') }
						</td>
						<td colspan="3">
							<input type="text" name="fdEndIp4_1" class="inputsgl" size="4" validate="required digits range(0,255)">
							<b>.</b>
							<input type="text" name="fdEndIp4_2" class="inputsgl" size="4" validate="required digits range(0,255)">
							<b>.</b>
							<input type="text" name="fdEndIp4_3" class="inputsgl" size="4" validate="required digits range(0,255)">
							<b>.</b>
							<input type="text" name="fdEndIp4_4" class="inputsgl" size="4" validate="required digits range(0,255)">
							<span class="txtstrong">*</span>
						</td>
					</tr>
				</table>
			</ui:content>
			<ui:content title="ipv6">
				<table class="tb_normal" style="margin-top: 20px" width=98%>
					<tr>
						<!-- 起始IP ipv6-->
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-profile:sys.profile.networkStrategy.fdStartIp') }
						</td>
						<td colspan="3">
							<input type="text" name="fdStartIp6_1" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdStartIp6_2" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdStartIp6_3" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdStartIp6_4" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdStartIp6_5" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdStartIp6_6" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdStartIp6_7" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdStartIp6_8" class="inputsgl" size="4" validate="required ipv6check">
							<span class="txtstrong">*</span>
						</td>
					</tr>
					<tr>
						<!-- 截止IP ipv6-->
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-profile:sys.profile.networkStrategy.fdEndIp') }
						</td>
						<td colspan="3">
							<input type="text" name="fdEndIp6_1" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdEndIp6_2" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdEndIp6_3" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdEndIp6_4" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdEndIp6_5" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdEndIp6_6" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdEndIp6_7" class="inputsgl" size="4" validate="required ipv6check">
							<b>:</b>
							<input type="text" name="fdEndIp6_8" class="inputsgl" size="4" validate="required ipv6check">
							<span class="txtstrong">*</span>
						</td>
					</tr>
				</table>
			</ui:content>
		</ui:tabpanel>
		<table class="tb_normal" width=98% style="border-top:none;">
			<tr style="border-top:none;">
				<!-- 网段说明-->
				<td width="15%" class="td_normal_title">
					${ lfn:message('sys-profile:sys.profile.networkStrategy.fdMark') }
				</td>
				<td colspan="3">
					<xform:textarea property="fdMark" style="width:98%;height:50px;"/>
				</td>
			</tr>
		</table>
		</html:form>
		<ui:button text="${lfn:message('button.save')}" onclick="_submit();" height="35" width="120" ></ui:button>

		<script type="text/javascript">
			// 表单校验
			var _validation = $KMSSValidation();
			_validation.addValidator("ipv6check","${ lfn:message('sys-profile:sys.profile.networkStrategy.ipv6check.error') }",function(v,e,i) {
				var reg = /^[a-fA-F0-9]{1,4}$/;
				var res = reg.test(v);
				return res;
			});
			seajs.use( [ 'lui/jquery', 'lui/dialog', 'sys/profile/resource/js/dateUtil' ], function($, dialog, dateUtil) {
				// 表单序列化成JSON对象
				$.fn.serializeObject = function() {
					var o = {};
					var a = this.serializeArray();
					$.each(a, function() {
						if (o[this.name] !== undefined) {
							if (!o[this.name].push) {
								o[this.name] = [ o[this.name] ];
							}
							o[this.name].push(this.value || '');
						} else {
							o[this.name] = this.value || '';
						}
					});
					return o;
				};
				var nowIpType = ${ipType};
				LUI.ready(function() {
					var iptab = LUI("iptab");
					var ip4validator = "required digits range(0,255)";
					var ip6validator = "required ipv6check";
					iptab.on("indexChanged",function(evt) {
						var index = evt.index.after;
						if(index == 0) { //ipv4
							nowIpType = 4;
							$("[name^=fdStartIp6_]").removeAttr("validate");
							$("[name^=fdEndIp6_]").removeAttr("validate");
							$("[name^=fdStartIp4_]").attr("validate",ip4validator);
							$("[name^=fdEndIp4_]").attr("validate",ip4validator);
						} else { //ipv6
							nowIpType = 6;
							$("[name^=fdStartIp4_]").removeAttr("validate");
							$("[name^=fdEndIp4_]").removeAttr("validate");
							$("[name^=fdStartIp6_]").attr("validate",ip6validator);
							$("[name^=fdEndIp6_]").attr("validate",ip6validator);
						}
					});
					iptab.setSelectedIndex(nowIpType===4?0:1);
				});
				// 确认提交
				window._submit = function() {
					if ($KMSSValidation().validate()) {
						_merge();
						window.$dialog.hide($("form").serializeObject());
					}
				};
	
				// 取消
				window._cancel = function() {
					window.$dialog.hide();
				};
				
				var getIpSetting = function() {
					if(nowIpType === 4) {
						return {joinStr:".",ipLength:4};
					} else {
						return {joinStr:":",ipLength:8};
					}
				};
				
				var names = ["fdStartIp", "fdEndIp"];
				_merge = function() {
					var setting = getIpSetting();
					$.each(names, function(i, n) {
						var ip = [];
						for(var i=1; i<=setting.ipLength; i++) {
							ip.push($("input[name=" + n + nowIpType + "_" + i + "]").val());
						}
						$("input[name=" + n + "]").val(ip.join(setting.joinStr));
					});
				}
				
				$(function() {
					var setting = getIpSetting();
					$.each(names, function(i, n) {
						var ip = $("input[name=" + n + "]").val();
						if(ip && ip.length > 0) {
							var _ip = ip.split(setting.joinStr);
							for(var i=1; i<=setting.ipLength; i++) {
								$("input[name=" + n + nowIpType+"_" + i + "]").val(_ip[i - 1]);
							}
						}
					});
				});
			});
		</script>
		</center>
	</template:replace>
</template:include>