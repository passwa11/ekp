<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<style>
.closeDataCoverageTipArea:before,.closeDataCoverageTipArea:after{
	content:"";
	display:inline-block;
	width:0;
	height:0;
	border: 6px solid transparent;
	border-bottom-color: #EAEDF3;
	position:absolute;
	top: -13px;
	z-index: 1;
	left: 50%;
	transform: translateX(-50%);
}

.closeDataCoverageTipArea:after{
	border-bottom-color: #fff;
	margin-top: 1px;
}

.closeDataCoverageTipArea{
	display: none;
	list-style: none;
	background: #FFFFFF;
	border: 1px solid #EAEDF3;
	box-shadow: 0 1px 3px 0 rgba(0,0,0,0.04);
	border-radius: 4px;
	width: 240px;
	position: fixed;
	z-index: 999;
	word-break: break-all;
}
</style>

<span id="closeDataCoverageWarnSpan" style="display:none;">
	<label>
		<input name="wf_closeDataCoverageWarn" type="checkbox" value="true">
		<bean:message bundle='sys-lbpmservice' key='lbpmservice.closeDataCoverageWarn'/>
	</label>
	<img class="closeDataCoverageTip" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/images/icon_help.png"></img>
	<div class="closeDataCoverageTipArea">
		<span style="line-height: 24px;"><bean:message bundle='sys-lbpmservice' key='lbpmservice.closeDataCoverageWarnTip'/></span>
	</div>
</span>
<script type="text/javascript">
$('input[type=radio][name=wf_processType]').change(function() {
	if(this.value=="2"){
		$("#closeDataCoverageWarnSpan").show();//显示"关闭同时审核数据覆盖提醒"按钮
	}else{
		$("#closeDataCoverageWarnSpan").hide();//隐藏"关闭同时审核数据覆盖提醒"按钮
	}
});

AttributeObject.Init.AllModeFuns.push(function() {
	var processType = AttributeObject.NodeData["processType"];
	if(processType=="2"){
		$("#closeDataCoverageWarnSpan").show();//显示"关闭同时审核数据覆盖提醒"按钮
	}
});

$(function(){
	$(".closeDataCoverageTip").mouseover(function(){
		$(".closeDataCoverageTipArea").show();
		var left = $(this).offset().left-$(".closeDataCoverageTipArea").width()/2+$(this).width()/2;
		var top = $(this).offset().top+23;
		var h = document.documentElement.scrollTop || document.body.scrollTop;
		$(".closeDataCoverageTipArea").css({
			"left" : left,
		    "top" : top-h
		});
	});
	$(".closeDataCoverageTip").mouseout(function(){
		$(".closeDataCoverageTipArea").hide();
	});
})
</script>