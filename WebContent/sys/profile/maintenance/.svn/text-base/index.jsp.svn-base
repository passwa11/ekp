<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService"%>
<%@ page import="com.landray.kmss.sys.organization.service.ISysOrgPersonService" %>
<%@ page import="com.landray.kmss.sys.cluster.model.SysClusterParameter"%>
<%@ page import="com.landray.kmss.sys.log.service.ISysLogOnlineService"%>
<%@ page import="com.landray.kmss.sys.config.util.LicenseUtil"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page import="com.landray.kmss.sys.authentication.user.validate.Config" %>
<%@ page import="com.landray.kmss.util.DateUtil"%>
<%@ page import="com.landray.kmss.util.version.VersionXMLUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.sys.config.service.SysLicenseNotifyService" %>
<%@ page import="com.landray.kmss.sys.profile.service.ISysProfileBlueAfterService" %>

<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/maintenance/css/license.css?s_cache=${LUI_Cache}">
	</template:replace>
	<template:replace name="body">
		<%
			int unlimitCount = -1;
			String unlimit = ResourceUtil.getString("sysLicense.licenseType.unlimit", "sys-config");
			int licPrivCount = Config.getLicPrivCount();
			pageContext.setAttribute("licPrivCount", licPrivCount);
			// 限制
			String permit = LicenseUtil.get("license-permit-num");

			// 特权人数
			com.alibaba.fastjson.JSONObject privilegeCounts = ((ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService")).getPrivilegeCounts();
			int totalPriCount = privilegeCounts.getIntValue("totalCount");
			int outPriCount = privilegeCounts.getIntValue("outCount");
			int inPriCount = privilegeCounts.getIntValue("inCount");

			// 生态注册数
			int outRegCount  = ((ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService")).getCountByRegistered(true, false);
			// 内部注册数
			int inRegCount  = ((ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService")).getCountByRegistered(false, false);
			// 总注册人数
			int totalRegCount  = outRegCount + inRegCount;
			// 许可注册数（内部）
			int licenseInRegCount = StringUtil.getIntFromString(LicenseUtil.get("license-org-person"), unlimitCount);
			String licenseInRegCountString = licenseInRegCount == unlimitCount  ? unlimit : licenseInRegCount + "&nbsp;" + ResourceUtil.getString("sys-profile:sys.profile.maintenance.overview.person");
			// 许可注册数（生态）
			int licenseOutRegCount = StringUtil.getIntFromString(LicenseUtil.get("license-org-person-external"), unlimitCount);
			String licenseOutRegCountString = licenseOutRegCount == unlimitCount  ? unlimit : licenseOutRegCount + "&nbsp;" + ResourceUtil.getString("sys-profile:sys.profile.maintenance.overview.person");

			// 生态在线数
			long outOnlineCount = ((ISysLogOnlineService)SpringBeanUtil.getBean("sysLogOnlineService")).getOnlineUserNumByPc(true);
			// 内部在线数
			long inOnlineCount = ((ISysLogOnlineService)SpringBeanUtil.getBean("sysLogOnlineService")).getOnlineUserNumByPc(false);
			// 总在线人数
			long totalOnlineCount = outOnlineCount + inOnlineCount;
			// 许可在线数（内部）
			int licenseInOnlineCount = StringUtil.getIntFromString(LicenseUtil.get("license-user-online"), unlimitCount);
			String licenseInOnlineCountString = licenseInOnlineCount == unlimitCount  ? unlimit : licenseInOnlineCount + "&nbsp;" + ResourceUtil.getString("sys-profile:sys.profile.maintenance.overview.person");
			// 许可在线数（生态）
			int licenseOutOnlineCount = StringUtil.getIntFromString(LicenseUtil.get("license-user-online-external"), unlimitCount);
			String licenseOutOnlineCountString = licenseOutOnlineCount == unlimitCount  ? unlimit : licenseOutOnlineCount + "&nbsp;" + ResourceUtil.getString("sys-profile:sys.profile.maintenance.overview.person");
			// 显示异常样式
			int inRegWarnCount = inRegCount - inPriCount;
			int outRegWarnCount = outRegCount - outPriCount;
			String inRegWarn = licenseInRegCount != unlimitCount && inRegWarnCount > licenseInRegCount ? " status_warn" : "";
			String outRegWarn = licenseOutRegCount != unlimitCount && outRegWarnCount > licenseOutRegCount ? " status_warn" : "";
			String inOnlineWarn = licenseInOnlineCount != unlimitCount && inOnlineCount > licenseInOnlineCount ? " status_warn" : "";
			String outOnlineWarn = licenseOutOnlineCount != unlimitCount && outOnlineCount > licenseOutOnlineCount ? " status_warn" : "";

			// 获取提示信息
			List<String> systemTips = new ArrayList<String>();
			// 许可文件自身定义的有效期到期
			String licenseExpire = LicenseUtil.getExpireSubject(LicenseUtil.NOTIFY_SYS_JOB);
			if(StringUtil.isNotNull(licenseExpire)) {
				systemTips.add(licenseExpire);
			}
			// 注册用户限制（内部组织）
			if(licenseInRegCount != unlimitCount) {
				if (inRegWarnCount == licenseInRegCount && licenseInRegCount > 0) {
					systemTips.add(ResourceUtil.getString("sys-profile:sys.profile.maintenance.overview.license.tip.innerReg"));
				} else if (inRegWarnCount > licenseInRegCount) {
					systemTips.add(ResourceUtil.getString("sys-profile:sys.profile.maintenance.overview.license.tip.innerReg2"));
				}
			}
			// 注册用户限制（生态组织）
			if(licenseOutRegCount != unlimitCount) {
				if (outRegWarnCount == licenseOutRegCount && licenseOutRegCount > 0) {
					systemTips.add(ResourceUtil.getString("sys-profile:sys.profile.maintenance.overview.license.tip.outerReg"));
				} else if (outRegWarnCount > licenseOutRegCount) {
					systemTips.add(ResourceUtil.getString("sys-profile:sys.profile.maintenance.overview.license.tip.outerReg2"));
				}
			}

			// 查询注册用户是否有异常信息
			SysLicenseNotifyService sysLicenseNotifyService = (SysLicenseNotifyService) SpringBeanUtil.getBean("sysLicenseNotifyService");
			// 内部用户
			int regPersonType1 = sysLicenseNotifyService.showOverflow(false);
			if (regPersonType1 > 0) {
				String regPersonSubject1 = sysLicenseNotifyService.getSubject(false, regPersonType1);
				pageContext.setAttribute("regPersonSubject1", regPersonSubject1);
			}
			// 生态用户
			int regPersonType2 = sysLicenseNotifyService.showOverflow(true);
			if (regPersonType2 > 0) {
				String regPersonSubject2 = sysLicenseNotifyService.getSubject(true, regPersonType2);
				pageContext.setAttribute("regPersonSubject2", regPersonSubject2);
			}
		%>
		<!-- 发版许可模块 Start -->
		<div class="lui_publish_permission">
			<!-- 顶部错误提示 Start -->
			<div class="lui_publish_head_tips">
				<span class="lui_publish_head_tips_warning_staus">!</span>
				<span class="lui_publish_head_tips_warning_counts">
					<span class="lui_publish_head_tips_warning_cur">1</span>/<span class="lui_publish_head_tips_warning_total"><%=systemTips.size()%></span>
				</span>
				<span class="lui_publish_head_tips_context"></span>

				<div class="lui_publish_head_next_info">
					<span class="lui_publish_head_tips_before lui_publish_head_tips_primary">${lfn:message('sys-profile:sys.profile.maintenance.overview.tip.pre') }</span>
					<span class="lui_publish_head_tips_after lui_publish_head_tips_primary">${lfn:message('sys-profile:sys.profile.maintenance.overview.tip.next') }</span>
					<span class="lui_publish_head_close">X</span>
				</div>
			</div>
			<script>
				seajs.use(["lui/jquery"], function($) {
					var systemTips = [];
			<%
					for(String tip : systemTips) {
			%>
					systemTips.push("<%=tip%>");
			<%
					}
			%>
					$(function() {
						var idx = 0;
						window.showTip = function(i) {
							if(systemTips.length > 1) {
								$(".lui_publish_head_tips_before").show();
								$(".lui_publish_head_tips_after").show();
							} else {
								$(".lui_publish_head_tips_before").hide();
								$(".lui_publish_head_tips_after").hide();
							}
							$(".lui_publish_head_tips_warning_cur").text(i + 1);
							$(".lui_publish_head_tips_context").html(systemTips[i]);
						}
						window.showNextTip = function(pre) {
							if(pre) {
								// 上一条
								idx = idx - 1;
								if(idx < 0) {
									idx = systemTips.length - 1;
								}
							} else {
								// 下一条
								idx = idx + 1;
								if(idx >= systemTips.length) {
									idx = 0;
								}
							}
							showTip(idx);
						}
						var timeId = setInterval(showNextTip, 6000);
						// 关闭
						$(".lui_publish_head_close").click(function() {
							clearInterval(timeId);
							$(".lui_publish_head_tips").hide();
						});
						// 上一条
						$(".lui_publish_head_tips_before").click(function() {
							showNextTip(true);
						});
						// 下一条
						$(".lui_publish_head_tips_after").click(function() {
							showNextTip();
						});
						if(systemTips.length > 0) {
							$(".lui_publish_head_tips").show();
							showTip(0);
						}
						// 测试连接
						window.testConnection = function() {
							$.post('<c:url value="/sys/profile/sys_profile_main/maintenance.do?method=testConnection"/>', function(res) {
								dialog.alert(res);
							}, "json");
						}
						$.post('<c:url value="/sys/profile/sys_profile_main/maintenance.do?method=checkContact"/>', function(res) {
							if(res && res.length > 0) {
								systemTips.push(res);
								$(".lui_publish_head_tips_warning_total").text(systemTips.length);
								$(".lui_publish_head_tips").show();
								showTip(0);
							}
						});
					});
				});
			</script>
			<!-- 顶部错误提示 End -->

			<!-- 右侧固定宽度 Start -->
			<div class="lui_wrapper_right">

				<%
					com.alibaba.fastjson.JSONObject jsonObject = ((ISysProfileBlueAfterService)SpringBeanUtil.getBean("sysProfileBlueAfterService")).getBlueAfterData();
					boolean flag = false;
					if(jsonObject != null){
						flag = true;
					}
					request.setAttribute("blueData",jsonObject);
					request.setAttribute("blueFlag",flag);
				%>
				<!-- 蓝小悦 Start-->
				<c:if test="${blueFlag eq true}">
					<div class="lui_serve_life">
						<div class="lui_header">
							<span>${blueData.customerName}</span>
						</div>
						<div class="lui_container_context">
							<div class="lui_serve_header">
								<div class="lui_serve_header_left">
									<span>${lfn:message('sys-profile:sys.profile.maintenance.customerName.expired.date') }</span>
								</div>
								<div class="lui_serve_header_right">
									<i class="lui_landray_number">${blueData.serveEndDay}
									</i>${lfn:message('sys-profile:sys.profile.maintenance.expired.day') }
								</div>
							</div>
							<div class="lui_serve_legal">
								<a href="${blueData.indexUrl}" target="_blank" >
									<span>${lfn:message('sys-profile:sys.profile.maintenance.renew.right') }</span>
								</a>
							</div>
							<div class="lui_serve_life_items">
								<div class="lui_serve_life_item">
									<span>${lfn:message('sys-profile:sys.profile.maintenance.projects.under.construction') }</span>
									<a href="${blueData.buildingProjectUrl}" target="_blank">
										<span><i class="lui_landray_number">${blueData.buildingProjectCount}</i>${lfn:message('sys-profile:sys.profile.maintenance.individual') }</span>
									</a>
								</div>
								<div class="lui_serve_life_item">
									<span>${lfn:message('sys-profile:sys.profile.maintenance.safety.reminder') }</span>
									<a href="${blueData.warningUrl}" target="_blank" >
										<span><i class="lui_landray_number">${blueData.warningCount}</i>${lfn:message('sys-profile:sys.profile.maintenance.strip') }</span>
									</a>
								</div>
							</div>
							<div class="lui_serve_life_info">
								<a href="${blueData.activityUrl}" target="_blank" ><span>${blueData.activityName}</span></a>
							</div>
						</div>
					</div>
				</c:if>
				<!-- 蓝小悦 End -->

				<%
					String license = ResourceUtil.getKmssConfigString("kmss.sysLicense");
					String licenseType = LicenseUtil.get("license-type");
					String productName = LicenseUtil.get("license-product-name");
					String noNameClass = "";
					if(StringUtil.isNull(productName)) {
						noNameClass = "lui_title_gray";
						productName = ResourceUtil.getString("sys-profile:sys.profile.maintenance.overview.noSystemName");
					}
					String customInfoHeight = "";
					boolean isOfficial = false;
					if(licenseType != null && licenseType.equalsIgnoreCase("Official")) {
						isOfficial = true;
						customInfoHeight = "style='height: 150px;'";
					}
				%>
				<!-- 产品开发 Start-->
				<div class="lui_product_custom_info" <%=customInfoHeight%>>
					<div class="lui_header">
						<span><%=LicenseUtil.get("license-to")%></span>
					</div>
					<div class="lui_product_context lui_publish_item">
					<%
						if(isOfficial) {
					%>
							<div class="lui_custom_nav_official">
								<span class="lui_custom_title <%=noNameClass%>">
								  <%=productName%>
								</span>
								<div class="lui_version_diploma lui_version_test"><i></i><span>${lfn:message('sys-profile:sys.profile.maintenance.overview.license') }
					<%
							if(StringUtil.isNull(license)) {
					%>
								${lfn:message('sys-config:sysLicense.official') }
					<%
							} else {
								out.print(license);
							}
					%>
								</span></div>
							</div>
					<%
						} else {
							String expire = LicenseUtil.get("license-expire");
							int expireDate = (int) ((DateUtil.convertStringToDate(expire, "yyyy-MM-dd").getTime() - System.currentTimeMillis()) / DateUtil.DAY);
							pageContext.setAttribute("expireDate", expireDate);
							pageContext.setAttribute("expire", expire);
					%>
							<div class="lui_custom_nav">
								<span class="lui_custom_title <%=noNameClass%>">
								  <%=productName%>
								</span>
								<div class="lui_version_diploma lui_version_office"><i></i><span>${lfn:message('sys-profile:sys.profile.maintenance.overview.license') }
					<%
							if(StringUtil.isNull(license)) {
					%>
								${lfn:message('sys-config:sysLicense.trial') }
					<%
							} else {
								out.print(license);
							}
					%>
								</span></div>
							</div>
					<%
					%>
						<div class="lui_context_item">
							<span class="lui_context">${lfn:message('sys-profile:sys.profile.maintenance.overview.expireDate1') }</span>
							<span class="lui_float_right">${expire}</span>
						</div>
					<%
							if(expireDate <= 10) {
					%>
								<div class="lui_context_item">
									<span class="lui_context">${lfn:message('sys-profile:sys.profile.maintenance.overview.expireDate2') }</span>
									<span class="lui_float_right status_warn"><em>${expireDate}</em>&nbsp;${lfn:message('date.interval.day')}</span>
								</div>
					<%
							} else {
					%>
								<div class="lui_context_item">
									<span class="lui_context">${lfn:message('sys-profile:sys.profile.maintenance.overview.expireDate2') }</span>
									<span class="lui_float_right"><em>${expireDate}</em>&nbsp;${lfn:message('date.interval.day')}</span>
								</div>
					<%
							}
						}
					%>
					</div>
				</div>
				<!-- 产品开发 End -->

				<!-- 服务器信息 Start -->
				<div class="lui_server_info">
					<div class="lui_container_header">
						<span>${lfn:message('sys-profile:sys.profile.maintenance.overview.licenseServer') }</span>
					</div>
					<div class="lui_container_context">
						<%
							String path = request.getSession().getServletContext().getRealPath("/");
							path = path.replaceAll("\\\\", "/");
							if (!path.endsWith("/")) {
								path += "/";
							}
							String file = path + "WEB-INF/KmssConfig/version/description.xml";
							String version = VersionXMLUtil.getInstance(file).getDescriprion().getModule().getBaseline();
							StringBuffer buf = new StringBuffer();
							if(version != null) {
								int count = 0;
								for(int i=0; i<version.length(); i++) {
									if(version.charAt(i) == '.') {
										count++;
										if(count > 1) break;
									}
									buf.append(version.charAt(i));
								}
							}
							String __path = "/sys/profile/tripartiteAdminAction.do?method=showVersion";
							if(!TripartiteAdminUtil.isGeneralUser()) { // 非三员管理 才显示的内容
								__path = "/sys/profile/tripartiteAdminAction.do?method=showVersion&path=/all.version";
							}
							__path = request.getContextPath() + __path;
						%>
						<div class="lui_server_version lui_publish_item">
							<div class="lui_version_info">
								<span>${lfn:message('sys-profile:sys.profile.maintenance.overview.version') }</span>
								<em><%=buf.toString()%></em>
							</div>
							<div class="lui_look_link">
								<div class="lui_look_detail">
									<span><a href="<%=__path%>" target="_blank">${lfn:message('sys-profile:sys.profile.maintenance.overview.all') }</a></span>
								</div>
							</div>
							<%
								int licenseCluster = StringUtil.getIntFromString(LicenseUtil.get("license-cluster"), 1);
								String licenseClusterString = licenseCluster < 0 ? unlimit : String.valueOf(licenseCluster);
								List sysClusterServerList = SysClusterParameter.getInstance().getAllServers();
								pageContext.setAttribute("sysClusterServerSize", sysClusterServerList != null ? sysClusterServerList.size() : 0);
								pageContext.setAttribute("licenseClusterString", licenseClusterString);
							%>
							<%
								Map<String,String> serverHwaddr = LicenseUtil.getServerHwaddr();
								if(StringUtil.isNotNull(serverHwaddr.get("error"))){	%>
									<div class="lui_context_item">
										<span class="lui_context">${lfn:message('sys-config:sysLicense.licenseServer.error') }</span>
										<em class="lui_float_right"><%=serverHwaddr.get("error")%></em>
									</div>
							<%	} else {	%>
									<div class="lui_context_item">
										<span class="lui_context">${lfn:message('sys-profile:sys.profile.maintenance.overview.ip') }</span>
										<em class="lui_float_right"><%=serverHwaddr.get("ip")%></em>
									</div>
									<div class="lui_context_item">
										<span class="lui_context">${lfn:message('sys-profile:sys.profile.maintenance.overview.mac') }</span>
										<span class="lui_float_right"><%=serverHwaddr.get("mac")%></span>
									</div>
							<%	} if(StringUtil.isNotNull(serverHwaddr.get("suggest"))){	%>
									<div class="lui_context_item">
										<span class="lui_context">${lfn:message('sys-config:sysLicense.licenseServer.suggest') }</span>
										<em class="lui_float_right"><%=serverHwaddr.get("suggest")%></em>
									</div>
							<%	}	%>
						</div>
						<div class="lui_server_node lui_publish_item">
							<div class=" lui_server_node_item">
								<span class="lui_context">${lfn:message('sys-profile:sys.profile.maintenance.overview.licenseCluster') }</span>
								<span class="lui_float_right lui_serve_node_counts"><bean:message bundle="sys-profile" key="sys.profile.maintenance.overview.cluster" arg0="${sysClusterServerSize}" arg1="${licenseClusterString}"/></span>
							</div>
							<div class="lui_look_detail">
								<span><a href="<c:url value="/sys/profile/index.jsp#maintenance/server/"/>" target="_blank">${lfn:message('sys-profile:sys.profile.maintenance.overview.all') }</a></span>
							</div>
						</div>
						<%
							String dns = LicenseUtil.get("license-dns");
							if(StringUtil.isNotNull(permit) && StringUtil.isNotNull(dns)) {
								String[] split = dns.split(";");
						%>
							<div class="lui_server_domain lui_publish_item">
								<div class="lui_context_item">
									<span class="lui_context">${lfn:message('sys-profile:sys.profile.maintenance.overview.domain') }</span>
									<span class="lui_float_right">
										<%
											for(String str : split) {
												if(StringUtil.isNotNull(str)) {
										%>
										<span><%=str%></span>
										<%
												}
											}
										%>
									</span>
								</div>
							</div>
						<%
							}
						%>
					</div>
				</div>
				<!-- 服务器信息 End -->
			</div>
			<!-- 右侧固定宽度 End -->
			<!-- 左侧自适应 Start -->
			<div class="lui_wrapper_left">
				<!-- 用户许可 Start-->
				<div class="lui_user_permission">
					<div class="lui_user_permission_title">
						<span>${lfn:message('sys-profile:sys.profile.maintenance.overview.user') }</span>
					</div>
					<div class="lui_all_user">
						<div class="lui_user_panel">
							<div class="lui_user_register lui_publish_item">
								<div class="lui_count register">
									<ul>
										<li class="lui_float_left lui_context_overflow" title="${lfn:message('sys-profile:sys.profile.maintenance.overview.user.reg') }">${lfn:message('sys-profile:sys.profile.maintenance.overview.user.reg') }</li>
										<li class="lui_float_right_register"><span><%=totalRegCount%></span>&nbsp;${lfn:message('sys-profile:sys.profile.maintenance.overview.person') }</li>
									</ul>
								</div>
								<div class="lui_user_orgnize">
									<div class="lui_context_item">
										<span class="lui_context lui_context_overflow" title="${lfn:message('sys-profile:sys.profile.maintenance.overview.user.inner') }">${lfn:message('sys-profile:sys.profile.maintenance.overview.user.inner') }</span>
										<span class="lui_float_right"><span class="<%=inRegWarn%>"><%=inRegCount%>&nbsp;${lfn:message('sys-profile:sys.profile.maintenance.overview.person') }</span>&nbsp;/&nbsp;<%=licenseInRegCountString%>
										<c:if test="${not empty regPersonSubject1}"><img title="${regPersonSubject1}" src='<c:url value="/sys/ui/extend/theme/default/icon/s/icon_validator.png"/>'/></c:if>
										</span>
									</div>
									<div class="lui_context_item">
										<span class="lui_context lui_context_overflow" title="${lfn:message('sys-profile:sys.profile.maintenance.overview.user.outer') }">${lfn:message('sys-profile:sys.profile.maintenance.overview.user.outer') }</span>
										<span class="lui_float_right"><span class="<%=outRegWarn%>"><%=outRegCount%>&nbsp;${lfn:message('sys-profile:sys.profile.maintenance.overview.person') }</span>&nbsp;/&nbsp;<%=licenseOutRegCountString%>
										<c:if test="${not empty regPersonSubject2}"><img title="${regPersonSubject2}" src='<c:url value="/sys/ui/extend/theme/default/icon/s/icon_validator.png"/>'/></c:if>
										</span>
									</div>
								</div>
							</div>
						</div>

						<div class="lui_user_panel">
							<div class="lui_user_online lui_publish_item">
								<div class="lui_count online">
									<ul>
										<li class="lui_float_left lui_context_overflow" title="${lfn:message('sys-profile:sys.profile.maintenance.overview.user.online') }">${lfn:message('sys-profile:sys.profile.maintenance.overview.user.online') }</li>
										<li class="lui_float_right_online"><span><%=totalOnlineCount%></span>&nbsp;${lfn:message('sys-profile:sys.profile.maintenance.overview.person') }</li>
									</ul>
								</div>
								<div class="lui_user_orgnize">
									<div class="lui_context_item">
										<span class="lui_context lui_context_overflow" title="${lfn:message('sys-profile:sys.profile.maintenance.overview.user.inner') }">${lfn:message('sys-profile:sys.profile.maintenance.overview.user.inner') }</span>
										<span class="lui_float_right"><span class="<%=inOnlineWarn%>"><%=inOnlineCount%>&nbsp;${lfn:message('sys-profile:sys.profile.maintenance.overview.person') }</span>&nbsp;/&nbsp;<%=licenseInOnlineCountString%></span>
									</div>
									<div class="lui_context_item">
										<span class="lui_context lui_context_overflow" title="${lfn:message('sys-profile:sys.profile.maintenance.overview.user.outer') }">${lfn:message('sys-profile:sys.profile.maintenance.overview.user.outer') }</span>
										<span class="lui_float_right"><span class="<%=outOnlineWarn%>"><%=outOnlineCount%>&nbsp;${lfn:message('sys-profile:sys.profile.maintenance.overview.person') }</span>&nbsp;/&nbsp;<%=licenseOutOnlineCountString%></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 用户许可 End -->
				<!-- 特权用户 Start -->
				<div class="lui_privilege_user">
					<div class="lui_container_header">
						<span>${lfn:message('sys-profile:sys.profile.maintenance.overview.privilege') }</span>
						<span class="lui_container_header_link" title="${lfn:message('sys-profile:sys.profile.maintenance.overview.privilege.over.add') }" onclick="addPrivileges();">&nbsp;&nbsp;&nbsp;</span>
						<span>
							<input type="hidden" name="__ids">
							<input type="hidden" name="__names">
						</span>
						<span class="lui_float_right lui_container_header_explain">${lfn:message('sys-profile:sys.profile.maintenance.overview.privilege.desc') }</span>
					</div>
					<div class="lui_privilege_people lui_publish_item">
						<div class="lui_count privilege">
							<ul>
								<li class="lui_float_left">${lfn:message('sys-profile:sys.profile.maintenance.overview.privilege.count') }</li>
								<li class="lui_float_right"><span class="privilege_total_count totalCount"></span>&nbsp;${lfn:message('sys-profile:sys.profile.maintenance.overview.person') }&nbsp;/&nbsp;<span class="licenseCount" style="font-size: inherit;"></span>&nbsp;${lfn:message('sys-profile:sys.profile.maintenance.overview.person') }</li>
							</ul>
						</div>
						<div class="lui_cut_off"></div>
						<div class="lui_privilege_user_detail">
							<div class="lui_context_item">
								<span>${lfn:message('sys-profile:sys.profile.maintenance.overview.privilege.user') }</span>
							</div>
							<ul id="privilegeUsers" class="lui_privilege_user_icon">
							</ul>
						</div>
					</div>
				</div>
				<!-- 特权用户 End -->
			</div>
			<!-- 左侧自适应 End -->

		</div>
		<!-- 发版许可模块 End -->

		<script>Com_IncludeFile("dialog.js");</script>

		<script>
			window.permit = ${licPrivCount};
			seajs.use(["lui/jquery"], function($) {
				// 获取特权用户数量
				window.getPrivilegeCounts = function () {
					$.ajax({
						'url': '<c:url value="/sys/organization/sys_org_person/sysOrgPerson.do?method=getPrivilegeCounts"/>',
						'async': false,
						'dataType': 'json',
						'type': 'post',
						'success': function(res) {
							if(window.console) {
								console.log("特权用户数量：", res);
							}
							$("span.totalCount").text(res.data.totalCount);
							$("span.licenseCount").text(res.data.licenseCount);
							// 允许增加的数量 = 许可总数量 - 已经设置（并启用）的数量
							window.permit = res.data.licenseCount - res.data.totalCount;
							if(res.data.totalCount > res.data.licenseCount) {
								$(".privilege_total_count").addClass("status_warn");
							} else {
								$(".privilege_total_count").removeClass("status_warn");
							}
						}
					});
				}
				getPrivilegeCounts();
			});
		</script>

		<script>
			seajs.use(["lui/jquery", "lui/dialog"], function($, dialog) {
				// 查询已经设置的特权用户
				window.privilegeIds = [];
				// 查询特权人员
				window.findPrivileges = function () {
					$.post('<c:url value="/sys/organization/sys_org_person/sysOrgPerson.do?method=getPrivileges"/>', function(res) {
						if(window.console) {
							console.log("已设置的特权用户：", res);
						}
						if(!res.success && res.msg) {
							dialog.alert(res.msg);
							return false;
						}
						var invalid = "${lfn:message('sys-profile:sys.profile.maintenance.invalid')}";
						var privileges = $("#privilegeUsers");
						privileges.empty();
						privilegeIds = [];
						for(var i in res.data) {
							var obj = res.data[i];
							var tips = obj.status ? '' : '<span class="lui_privilege_tips">' +
															'<i class="lui_privilege_tips_arrow"></i>' +
															'<i class="lui_privilege_tips_inner">${lfn:message('sys-profile:sys.profile.maintenance.overview.privilege.over.limit')}</i>' +
														 '</span>';
							var status = "";
							if(!obj.status) {
								status += "_" + invalid;
							}
							if(!obj.available) {
								status += "_" + invalid;
							}
							var user = '<li onmouseover="showInvalidTip(this);" onmouseout="hideInvalidTip(this);">' +
											'<div class="lui_privilege_user_component ' + (obj.status ? '' : 'lui_privilege_user_invalid') + '">' +
												'<img src="' + obj.img + '" alt="">' +
												'<span>' + obj.userName + status + '</span>' +
												'<i class="lui_privilege_user_delete" onclick="delPrivilege(\'' + obj.userId + '\')"></i>' +
											'</div>' +
											tips +
										'</li>';
							privileges.append(user);
							privilegeIds.push(obj.userId);
						}
					}, 'json');
				}

				// 查询特权用户
				findPrivileges();

				// 显示限制提示
				window.showInvalidTip = function(li) {
					$(li).find(".lui_privilege_tips").show();
				}

				// 隐藏限制提示
				window.hideInvalidTip = function(li) {
					$(li).find(".lui_privilege_tips").hide();
				}

				// 删除特权人员
				window.delPrivilege = function(id) {
					dialog.confirm("${lfn:message('sys-profile:sys.profile.maintenance.overview.privilege.confirm') }", function(value) {
						if (value == true) {
							$.post('<c:url value="/sys/organization/sys_org_person/sysOrgPerson.do?method=deletePrivilege"/>', {'personId': id}, function(res) {
								if(res.success) {
									window.permit += 1;
									findPrivileges();
									getPrivilegeCounts();
								} else {
									dialog.alert(res.msg);
								}
							}, 'json');
						}
					});
				}

				// 增加特权人员
				window.addPrivileges = function() {
					if(window.permit < 1) {
						dialog.alert("${lfn:message('sys-profile:sys.profile.maintenance.overview.privilege.full') }");
						return false;
					}
					Dialog_Address(true, '__ids', '__names', ';', ORG_TYPE_PERSON, function (val) {
						$("[name='__ids']").val("");
						$("[name='__names']").val("");
						var data = val ? val.data : [];
						// 判断选择的用户数量是否达到限制
						var ids = [];
						for(var i=0; i<data.length; i++) {
							ids.push(data[i]['id']);
						}
						if(ids.length > window.permit) {
							var msg = "${lfn:message('sys-profile:sys.profile.maintenance.overview.privilege.over') }".replace("{0}", "${licPrivCount}");
							dialog.alert(msg);
							return false;
						}
						if(ids.length > 0) {
							$.post('<c:url value="/sys/organization/sys_org_person/sysOrgPerson.do?method=savePrivilege"/>', {'ids': ids.join(',')}, function(res) {
								if(res.success) {
									window.permit -= res.count;
									findPrivileges();
									getPrivilegeCounts();
								} else {
									dialog.alert(res.msg);
								}
							}, 'json');
						}
					}, null, null, null, null, null, privilegeIds);
				}

			});
		</script>
	</template:replace>
</template:include>	
	