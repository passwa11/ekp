<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript">
	var frontEndRuleInfo = null;
	var luiReadyForRuleInfo = function() {
		var jsPath = '${baseUrl}/rule_info/js/ruleInfo_vue.js';
		seajs.use([ jsPath ], function(front) {
			front.init({
				choosedRuleId : "${sysIassisterItemForm.ruleId}"
			});
			frontEndRuleInfo = front;
		})
	}
	LUI.ready(function() {
		seajs.use("lui/topic", function(topic) {
			topic.subscribe("headLoaded", luiReadyForRuleInfo);
		})
	});
</script>

<div id="ruleInfo" class="info_container rule" style="display: none">
	<div class="rule_info_container" v-show="ruleChoosed">
		<el-divider content-position="center"> <el-button
			type="text" size="mini" @click.native.prevent="showParam=!showParam">{{langHere["msg.rule.param"]}}</el-button></el-divider>
		<div class="table_info param" v-show="showParam">
			<el-table :data="params" border> <el-table-column
				type="index" width="100" :label="langHere['msg.index']"
				align="center"></el-table-column> <el-table-column
				:label="langHere['msg.param.name']" align="center" prop="label"></el-table-column>
			<el-table-column width="200" :label="langHere['msg.param.type']"
				align="center" prop="typeLabel"></el-table-column></el-table>
		</div>
		<el-divider content-position="center"> <el-button
			type="text" size="mini" @click.native.prevent="showRule=!showRule">{{langHere["msg.rule.detail"]}}</el-button></el-divider>
		<div class="table_info rule" v-show="showRule">
			<el-table :data="rules" border> <el-table-column
				type="index" width="100" :label="langHere['msg.index']"
				align="center"></el-table-column> <el-table-column width="300"
				:label="langHere['msg.rule.name']" align="center" prop="label"></el-table-column>
			<el-table-column :label="langHere['msg.rule.content']" align="center"
				prop="content"></el-table-column></el-table>
		</div>
	</div>
</div>