<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript">
	if ("view" != "${sysIassisterItemForm.method_GET}") {
		Com_IncludeFile("ckfilter.js|ckeditor.js", "ckeditor/");
	}
	var frontEndConfigInfo = null;
	var luiReadyForConfigInfo = function() {
		var jsPath = '${baseUrl}/config_info/js/configInfo_vue.js';
		seajs.use([ jsPath ], function(front) {
			var initParams = {
				ctxPath : "${LUI_ContextPath}",
				choosedRuleId : "${sysIassisterItemForm.ruleId}",
				actionUrl : "${actionUrl}",
				method : "${sysIassisterItemForm.method_GET}",
				checkConfig : ${sysIassisterItemForm.checkConfig}
			}
			front.init(initParams);
			frontEndConfigInfo = front;
		})
	}
	LUI.ready(function() {
		seajs.use("lui/topic", function(topic) {
			topic.subscribe("headLoaded", luiReadyForConfigInfo);
		})
	});
</script>
<c:choose>
	<c:when test="${sysIassisterItemForm.method_GET eq 'view' }">
		<html:hidden property="checkConfig" />
	</c:when>
	<c:otherwise>
		<table class="tb_n_simple" width="100%" style="display: none">
			<tr>
				<td width="100%" colspan="4"><xform:text property="checkConfig"
						style="width:96%"
						subject="${lfn:message('sys-iassister:sysIassisterItem.checkConfig') }"
						htmlElementProperties="style='display:none' validator='true'"
						validators="checkConfig" /></td>
			</tr>
		</table>
	</c:otherwise>
</c:choose>
<div id="configInfoContainer" class="info_container config"
	style="display: none">
	<div class="config_info" v-show="ruleChoosed">
		<el-divider content-position="center"> <el-button
			type="text" size="mini"
			@click.native.prevent="showConfig=!showConfig">{{langHere["msg.config.model"]}}</el-button></el-divider>
		<div class="table_info config" v-show="showConfig">
			<config-info ref="configInfo" v-if="configData!=null"
				:config="configData" />
		</div>
	</div>
</div>