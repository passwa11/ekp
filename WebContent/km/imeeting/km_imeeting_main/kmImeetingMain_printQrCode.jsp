<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<input id="qrcode" type="checkbox" onclick="changeValue(this);"/>
	 <bean:message bundle="km-imeeting" key="message.print.qrcode.hide" />
</label>
</div>
<div id="qrcodeDiv">
<div class="detailQrCode">

</div>
<div><bean:message bundle="km-imeeting" key="message.print.qrcode.detail" /></div>
</div>
</div>
</center>
<script type="text/javascript">
$(document).ready(function(){
	seajs.use(['lui/qrcode'], function(qrcode) {
		var url = Com_GetCurDnsHost()+'${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view&fdId=${kmImeetingSummaryForm.fdId}'
		qrcode.Qrcode({
			text :url,
			element : $(".detailQrCode")[0],
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

