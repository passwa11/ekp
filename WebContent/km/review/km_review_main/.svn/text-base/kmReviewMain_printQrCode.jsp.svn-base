<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
.detailQrCode{ margin:25px auto 5px;}
@media print {
	.qrcodeArea{display: none;}
}
</style>
<center>
<div style="margin-top:20px">
<div class="qrcodeArea">
<label>
	<input id="qrcode" type="checkbox" ${'true' eq param.hideCode?'checked':''} onclick="changeValue(this);"/>
	 <bean:message bundle="km-review" key="message.print.qrcode.hide" />
</label>
</div>
<div id="qrcodeDiv" style="${'true' eq param.hideCode?'display:none':''}">
<div class="detailQrCode">

</div>
<div><bean:message bundle="km-review" key="message.print.qrcode.detail" /></div>
</div>
</div>
</center>
<script type="text/javascript">
$(document).ready(function(){
	seajs.use(['lui/qrcode'], function(qrcode) {
		var url = Com_GetCurDnsHost()+'${KMSS_Parameter_ContextPath}km/review/km_review_main/kmReviewMain.do?method=view&fdId=${kmReviewMainForm.fdId}'
		qrcode.Qrcode({
			text :url,
			element : $("#qrcodeDiv .detailQrCode")[0],
			render :  'canvas'
		});
	});
});

function changeValue(obj){
	if(obj.checked){
      $("#"+obj.id+"Div").hide();
	}else{
		$("#"+obj.id+"Div").show();
	}
}
</script>

