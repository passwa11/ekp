<%@page import="com.landray.kmss.constant.SysDocConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%@ page import="com.landray.kmss.sys.iassister.util.AssisterUtils"%>
<%@ page
	import="com.landray.kmss.sys.iassister.service.ISysIassisterTemplateService"%>
<c:set var="hasAuth" value="false"></c:set>
<kmss:authShow roles="ROLE_SYSIASSISTER_DEFAULT">
	<c:set var="hasAuth" value="true"></c:set>
</kmss:authShow>
<%
	String hasCheckItem = "false";
	try {
		JSONObject rtnData = new JSONObject();
		//获取模板的配置项信息
		JSONObject params = new JSONObject().fluentPut("templateId", request.getParameter("templateId"))
				.fluentPut("templateModelName", request.getParameter("templateModelName"))
				.fluentPut("mainModelId", request.getParameter("mainModelId"));
		rtnData = AssisterUtils.getService(ISysIassisterTemplateService.class).getTemplateInfo(params);
		if (rtnData != null) {
			hasCheckItem = rtnData.getString("hasCheckItems");
		}

	} catch (Exception e) {
	}
	pageContext.setAttribute("_hasCheckItem", hasCheckItem);
%>
<c:set var="modelForm" value="${requestScope[param.formName]}" />
<c:set var="lblMsgKey" value="sys-iassister:msg.check_items.label"></c:set>
<c:if test="${not empty param.messageKey }">
	<c:set var="lblMsgKey" value="${param.messageKey }"></c:set>
</c:if>
<c:set var="draftStatus" value="<%=SysDocConstant.DOC_STATUS_DRAFT%>"></c:set>
<script
	src="${LUI_ContextPath }/sys/iassister/resource/js/vue.min.js?s_cache=${LUI_Cache}"></script>
<script
	src="${LUI_ContextPath }/sys/iassister/resource/js/element.js?s_cache=${LUI_Cache}"></script>
<link rel="stylesheet" type="text/css"
	href="${ LUI_ContextPath}/sys/iassister/resource/css/element.css?s_cache=${LUI_Cache}" />
<script type="text/javascript">
	var frontEnd = null;
	var langHere = null;
	function luiReady() {
		var jsPath = 'sys/iassister/sys_iassister_template/js/check_items.js';
		seajs.use([ jsPath ], function(front) {
			langHere = front.lang;
			front.init({
				ctxPath : "${LUI_ContextPath}",
				method : "${param.method}",
				templateId : "${param.templateId}",
				templateModelName : "${param.templateModelName}",
				mainModelId : "${param.mainModelId}",
				mainModelName : "${param.mainModelName}",
				useVue : "${param.useVue}",
				resultTitle : "${lfn:message(lblMsgKey)}",
				draftStatus : "${draftStatus}",
				panelId : "${param.panelId}",
				idx : "${param.idx}",
				hasAuth : "${hasAuth}",
				hasCheckItems : "${_hasCheckItem}",
				fdKey : "${param.fdKey}"
			});
			frontEnd = front;
		})
	}
	LUI.ready(luiReady);
</script>
<c:set var="drawDir" value="r"></c:set>
<%-- <c:if test="${param.approveModel eq 'right'}">
	<c:set var="drawDir" value="l"></c:set>
</c:if> --%>
<ui:button style="display:none;" parentId="toolbar" id="icheckBtn"
	text="${lfn:message('sys-iassister:msg.icheck') }"
	onclick="frontEnd.icheck()" order="3">
</ui:button>
<c:set var="titleIcon" value="lui_iconfont_navleft_catalog"></c:set>
<c:if test="${param.approveModel eq 'right' }">
	<c:set var="titleIcon" value="lui-fm-icon-wiki-01"></c:set>
</c:if>
<c:if test="${hasAuth && _hasCheckItem eq 'true' }">
	<ui:content id="check_items_content" title="${lfn:message(lblMsgKey)}" titleicon="${titleIcon }">
		<c:choose>
			<c:when test="${'false' == param.useVue }"></c:when>
			<c:otherwise>
				<div id="resultContainer" class="result_container"
					style="display: none">
					<el-dialog v-if="checkItem!=null" ref="infoDialog" append-to-body
						:visible.sync="infoVisible" :title="infoTitle" :modal="true"
						:close-on-press-escape="false" :close-on-click-modal="true"
						class="info_container"> <template> <el-row
						:class="['main']"> <el-col>
					<div v-if="info.type=='text'" v-html="info.content"></div>
					<div v-if="info.type=='pic'">
						<el-row v-for="pr in picRows"> <el-col v-for="pic in pr"
							:span="8" class="pic_container">
						<div class="pic_wrapper">
							<div class="pic_img">
								<img alt="" :src="pic.url">
								<div class="pic_cover">
									<div class="cover_preview">
										<span @click="previewImg(pic.attId)"><em>{{langHere["button.preview"]}}</em></span>
									</div>
								</div>
							</div>
						</div>
						</el-col> </el-row>
					</div>
					<template v-if="info.type=='link'">
					<div v-for="link in info.linkList" class="link">
						<a :href="link.href" target="_blank">{{link.title}}</a>
					</div>
					</template> </el-col> </el-row> </template> <template slot="footer"> <el-row
						class="result_footer"> <el-col :span="18"
						class="ellipse check_item_label"
						:title="langHere['table.sysIassisterItem']+checkItem.label">{{langHere["table.sysIassisterItem"]}}：{{checkItem.label}}</el-col>
					<el-col :span="6"> <el-button @click="infoVisible=false">{{langHere["button.close"]}}</el-button></el-col>
					</el-row> </template> </el-dialog>
					<template v-if="checkResults.length>0">
					<div v-for="(cr,idx) in checkResults" class="check_result">
						<div :class="['area','head','group',cr.isGroup?'pointer':'']"
							@click="switchExpand(cr)">
							<el-row> <el-col :span="13">
							<div class="label ellipse col_inner" :title="cr.label">{{cr.label}}</div>
							</el-col> <el-col :span="11">
							<div v-if="cr.isGroup" class="col_inner expand right">
								<i :class="expandIcon(cr)"></i>
							</div>
							<div v-else :class="['right','col_inner']">
								<el-row> <el-col :span="showResultLabel?12:20"
									:class="['show_infos']"> <template
									v-if="!cr.showNone&&cr.showInfos"> <i
									v-if="cr.showInfos.pic.fileList.length>0"
									class="el-icon-picture-outline pointer"
									@click="showInfo(cr,'pic',cr.showInfos.pic)"></i> <i
									v-if="cr.showInfos.text.content"
									class="el-icon-document pointer"
									@click="showInfo(cr,'text',cr.showInfos.text)"></i> <i
									v-if="cr.showInfos.link.linkList.length>0"
									class="el-icon-link pointer"
									@click="showInfo(cr,'link',cr.showInfos.link)"></i></template> </el-col> <el-col
									:span="showResultLabel?12:4"> <el-row> <el-col
									:span="showResultLabel?9:24"> <i
									:class="resultIcon(cr)"></i></el-col> <el-col v-if="showResultLabel"
									:span="15" :class="['ellipse',cr.result,'left']">
								{{resultLabel(cr)}} </el-col> </el-row> </el-col> </el-row>
							</div>
							</el-col> </el-row>
						</div>
						<el-row v-if="cr.isGroup" v-show="cr.expand" class="area content">
						<el-col :span="24" class="check_items"> <el-row
							v-for="ci in cr.results" class="check_item"> <el-col
							:span="13">
						<div class="label ellipse" :title="ci.label">{{ci.label}}</div>
						</el-col> <el-col :span="11">
						<div :class="['right','col_inner']">
							<el-row> <el-col :span="showResultLabel?12:20"
								:class="['show_infos']"> <template
								v-if="!ci.showNone&&ci.showInfos"> <i
								v-if="ci.showInfos.pic.fileList.length>0"
								class="el-icon-picture-outline pointer"
								@click="showInfo(ci,'pic',ci.showInfos.pic)"></i> <i
								v-if="ci.showInfos.text.content"
								class="el-icon-document pointer"
								@click="showInfo(ci,'text',ci.showInfos.text)"></i> <i
								v-if="ci.showInfos.link.linkList.length>0"
								class="el-icon-link pointer"
								@click="showInfo(ci,'link',ci.showInfos.link)"></i></template> </el-col> <el-col
								:span="showResultLabel?12:4"> <el-row> <el-col
								:span="showResultLabel?9:24"> <i
								:class="resultIcon(ci)"></i></el-col> <el-col v-if="showResultLabel"
								:span="15" :class="['ellipse',ci.result,'left']">
							{{resultLabel(ci)}} </el-col> </el-row> </el-col> </el-row>
						</div>
						</el-col> </el-row> </el-col> </el-row>
					</div>
					</template>
					<div v-else class="empty_result">{{langHere["msg.empty.result"]}}</div>
				</div>
			</c:otherwise>
		</c:choose>
	</ui:content>
</c:if>