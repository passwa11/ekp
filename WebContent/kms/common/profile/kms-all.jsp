<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.kms.common.util.KmsCommonSysProfileHandle" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/profile/profile.tld" prefix="profile"%>
<%
	boolean showKmsKnowledgeOperationMenu = KmsCommonSysProfileHandle.getInstance().checkMenuSubTypeExist(KmsCommonSysProfileHandle.MENU_KMS_KNOWLEDGE_OPERATION);
	boolean showKmsTrainLearnMenu = KmsCommonSysProfileHandle.getInstance().checkMenuSubTypeExist(KmsCommonSysProfileHandle.MENU_KMS_TRAIN_LEARN);
	boolean showKmsIntelligenceService = KmsCommonSysProfileHandle.getInstance().checkMenuSubTypeExist(KmsCommonSysProfileHandle.MENU_KMS_INTELLIGENCE_SERVICE);
	boolean showKmsKnowledgeAsset = KmsCommonSysProfileHandle.getInstance().checkMenuSubTypeExist(KmsCommonSysProfileHandle.MENU_KMS_KNOWLEDGE_ASSET);
	pageContext.setAttribute("showKmsKnowledgeOperationMenu", showKmsKnowledgeOperationMenu);
	pageContext.setAttribute("showKmsTrainLearnMenu", showKmsTrainLearnMenu);
	pageContext.setAttribute("showKmsIntelligenceService", showKmsIntelligenceService);
	pageContext.setAttribute("showKmsKnowledgeAsset", showKmsKnowledgeAsset);
	boolean isShowAllNotExistModules = KmsCommonSysProfileHandle.getInstance().checkIsShowAllNoExistModules();
	pageContext.setAttribute("isShowAllNotExistModules", isShowAllNotExistModules);

%>
<!doctype html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="renderer" content="webkit" />
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<title>
	</title>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/profile/resource/css/homepage.css" />
	<script type="text/javascript">seajs.use(['theme!profile'])</script>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}kms/common/profile/resource/css/kms-all.css" />
</head>
<body class="lui_profile_listview_body">
<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
<c:set var="type" scope="page" value="${empty param.type ? 'ekp' : param.type}"/>
<div class="kmsCommonSysProfileTopBackground"></div>
<div class="kmsCommonSysProfile">
	<div class="multiMenu">
		<div class="table-cell table-align-center menuItem selected kms-all" onclick="changeProfile(this,'kms-all')">
			<div class="titleEllipsis" title="${lfn:message('kms-common:kms.common.sys.profile.menu.all')}">
				${lfn:message('kms-common:kms.common.sys.profile.menu.all')}
			</div>
			<div class="menuItem-bottom"><div class="line"></div></div>
		</div>
		<c:if test="${showKmsKnowledgeAsset}">
			<div class="table-cell table-align-center menuItem kms-knowledge-asset" onclick="changeProfile(this,'kms-knowledge-asset')">
				<div class="titleEllipsis" title="${lfn:message('kms-common:kms.common.sys.profile.menu.knowledge.asset')}">
					${lfn:message('kms-common:kms.common.sys.profile.menu.knowledge.asset')}
				</div>
				<div class="menuItem-bottom"><div class="line"></div></div>
			</div>
		</c:if>
		<c:if test="${showKmsTrainLearnMenu}">
			<div class="table-cell table-align-center menuItem kms-train-learn" onclick="changeProfile(this,'kms-train-learn')">
				<div class="titleEllipsis" title="${lfn:message('kms-common:kms.common.sys.profile.menu.train.learn')}">
					${lfn:message('kms-common:kms.common.sys.profile.menu.train.learn')}
				</div>
				<div class="menuItem-bottom"><div class="line"></div></div>
			</div>
		</c:if>
		<c:if test="${showKmsKnowledgeOperationMenu}">
			<div class="table-cell table-align-center menuItem kms-knowledge-operation" onclick="changeProfile(this,'kms-knowledge-operation')">
				<div class="titleEllipsis" title="${lfn:message('kms-common:kms.common.sys.profile.menu.knowledge.operation')}">
					${lfn:message('kms-common:kms.common.sys.profile.menu.knowledge.operation')}
				</div>
				<div class="menuItem-bottom"><div class="line"></div></div>
			</div>
		</c:if>
		<c:if test="${showKmsIntelligenceService}">
			<div class="table-cell table-align-center menuItem kms-intelligence-service" onclick="changeProfile(this,'kms-intelligence-service')">
				<div class="titleEllipsis" title="${lfn:message('kms-common:kms.common.sys.profile.menu.intelligence.service')}">
					${lfn:message('kms-common:kms.common.sys.profile.menu.intelligence.service')}
				</div>
				<div class="menuItem-bottom"><div class="line"></div></div>
			</div>
		</c:if>
	</div>

	<div class="singleMenu">
		<div>
			<div class="table-cell table-align-center menuItem selected" onclick="showMenuItems(this)">
				<div style="display:inline-block;">
					<span class="title titleEllipsis" title="${lfn:message('kms-common:kms.common.sys.profile.menu.all')}">${lfn:message('kms-common:kms.common.sys.profile.menu.all')}</span>
					<div class="menuItem-bottom"><div class="line"></div></div>
				</div>
				<i class="menu-icon icon"></i>
			</div>
			<div class="singleMenuItemContainer" style="display: none;">
				<div class="item kms-all selected" onclick="changeProfile(this,'kms-all')">
					<span class="titleEllipsis" title="${lfn:message('kms-common:kms.common.sys.profile.menu.all')}">
						${lfn:message('kms-common:kms.common.sys.profile.menu.all')}
					</span>
				</div>
				<c:if test="${showKmsKnowledgeAsset}">
					<div class="item kms-knowledge-asset" onclick="changeProfile(this,'kms-knowledge-asset')">
						<span class="titleEllipsis" title="${lfn:message('kms-common:kms.common.sys.profile.menu.knowledge.asset')}">
							${lfn:message('kms-common:kms.common.sys.profile.menu.knowledge.asset')}
						</span>
					</div>
				</c:if>
				<c:if test="${showKmsTrainLearnMenu}">
					<div class="item kms-train-learn" onclick="changeProfile(this,'kms-train-learn')">
						<span class="titleEllipsis"  title="${lfn:message('kms-common:kms.common.sys.profile.menu.train.learn')}">
							${lfn:message('kms-common:kms.common.sys.profile.menu.train.learn')}
						</span>
					</div>
				</c:if>
				<c:if test="${showKmsKnowledgeOperationMenu}">
					<div class="item kms-knowledge-operation" onclick="changeProfile(this,'kms-knowledge-operation')">
						<span class="titleEllipsis"  title="${lfn:message('kms-common:kms.common.sys.profile.menu.knowledge.operation')}">
							${lfn:message('kms-common:kms.common.sys.profile.menu.knowledge.operation')}
						</span>
					</div>
				</c:if>
				<c:if test="${showKmsIntelligenceService}">
					<div class="item kms-intelligence-service" onclick="changeProfile(this,'kms-intelligence-service')">
						<span class="titleEllipsis" title="${lfn:message('kms-common:kms.common.sys.profile.menu.intelligence.service')}">
							${lfn:message('kms-common:kms.common.sys.profile.menu.intelligence.service')}
						</span>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
<profile:listview id="sysProfileBlock">
	<ui:source type="AjaxJson">
		{"url":"/kms/common/kms_common_sys_profile/kmsCommonSysProfile.do?method=data&subType=kms-all"}
	</ui:source>
	<ui:render type="Javascript" ref="kms.common.sys.profile.listview.default">
		<%--TODO 配置项完善--%>
	</ui:render>
</profile:listview>
<script>
	//{"url":"/sys/profile/sys_profile_main/sysCfgProfileConfig.do?method=data&type=${type}"}
	var isShowAllNotExistModules = "${isShowAllNotExistModules}";

	var kmsCommonI18N = {
		"kms-all": "${lfn:message('kms-common:kms.common.sys.profile.menu.all')}",
		"kms-knowledge-asset": "${lfn:message('kms-common:kms.common.sys.profile.menu.knowledge.asset')}",
		"kms-train-learn": "${lfn:message('kms-common:kms.common.sys.profile.menu.train.learn')}",
		"kms-knowledge-operation": "${lfn:message('kms-common:kms.common.sys.profile.menu.knowledge.operation')}",
		"kms-intelligence-service": "${lfn:message('kms-common:kms.common.sys.profile.menu.intelligence.service')}"
	};

	function showMenuItems(obj) {
		$(".singleMenu .icon").addClass("iconSelected");
		$(".singleMenuItemContainer").show();
	}

	function changeProfile(obj,subType){
		//多列菜单操作
		$(".multiMenu .menuItem").removeClass("selected");
		$(".multiMenu ." + subType).addClass("selected");

		//单列菜单操作
		$(".singleMenu .item").removeClass("selected");
		$(".singleMenu ." + subType).addClass('selected');
		$(".singleMenu .title").html(kmsCommonI18N[subType]);
		$(".singleMenu .title").attr("title",kmsCommonI18N[subType])
		$(".singleMenu .icon").removeClass("iconSelected");
		$(".singleMenuItemContainer").hide()

		if(subType === "kms-knowledge-operation"){
			if(isShowAllNotExistModules == "true" && !window.__kmsCommonSysProfile_hadHideTip){
				LUI("sysProfileBlock").showTip = true;
				LUI("sysProfileBlock").buildBusinessModuleTipContainer = function(){
					return $('<div class="tipBox">' +
							'<i class="tip-icon icon-tip"></i><div class="tipMessage">${lfn:message("kms-common:kms.common.sys.profile.menu.intelligence.service.tip")}</div>'+
							'<div class="closeBtn" onclick="LUI(\'sysProfileBlock\').hideTip(this)"><i class="tip-icon icon-close"></i></div>' +
							'</div>');
				}
				LUI("sysProfileBlock").hideTip = function(obj){
					$(obj).parent().hide();
					window.__kmsCommonSysProfile_hadHideTip = true;
				}
			}
		}else{
			LUI("sysProfileBlock").showTip = false;
		}
		LUI("sysProfileBlock").source.setUrl("/kms/common/kms_common_sys_profile/kmsCommonSysProfile.do?method=data&subType="+subType);
		LUI("sysProfileBlock").onRefresh();
	}
</script>
</body>
</html>
