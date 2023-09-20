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
	<input id="qrcode_${param.mainId }" type="checkbox" ${'true' eq param.hideCode?'checked':''} onclick="changeValue(this);"/>
	 <bean:message bundle="sys-modeling-main" key="message.print.qrcode.hide" />
</label>
</div>
<div id="qrcode_${param.mainId }Div" style="${'true' eq param.hideCode?'display:none':''}">
<div class="detailQrCode">

</div>
<div><bean:message bundle="sys-modeling-main" key="message.print.qrcode.detail" /></div>
</div>
</div>
</center>
<script type="text/javascript">
Com_AddEventListener(window,'load',function(){
	seajs.use(['lui/qrcode'], function(qrcode) {
		var url = Com_GetCurDnsHost()+'${KMSS_Parameter_ContextPath}${param.modelViewUrl}'
		var id = "qrcode_${param.mainId }Div .detailQrCode";
		qrcode.Qrcode({
			text :url,
			element : $("#"+id)[0],
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

