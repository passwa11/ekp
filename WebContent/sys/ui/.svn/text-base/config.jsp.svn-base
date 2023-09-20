<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
function codeConfig(){
	var enabled = document.getElementsByName("value(kmss.third.ywork.share.enabled)");
	var val = "";
	for(var i=0;i<enabled.length;i++){
		if(enabled[i].checked){
			val = enabled[i].value;
		}
	}
	if(val==""){
		for(var k=0;k<enabled.length;k++){
			if(enabled[k].value=="1"){
				enabled[k].checked = true;
			}
		}
	}
}
config_addOnloadFuncList(codeConfig);

</script>
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" width="15%">二维码分享配置:</td>
		<td>
			<label>
				<xform:radio property="value(kmss.third.ywork.share.enabled)" showStatus="edit">
   					<xform:simpleDataSource value="0">关闭</xform:simpleDataSource>
					<xform:simpleDataSource value="1">开启</xform:simpleDataSource> 
				</xform:radio>
			</label>
		</td>
	</tr>
</table>
