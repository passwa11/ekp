<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
Com_IncludeFile("doclist.js|dialog.js");
</script>
<script type="text/javascript">
function config_gzip_openLang(){
	var tbObj = document.getElementById("config_gzip");
	var field = tbObj.rows[0].getElementsByTagName("INPUT")[0];
	for(var i=1; i<tbObj.rows.length; i++){
		tbObj.rows[i].style.display = field.checked?"":"none";
		var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
		for(var j=0; j<cfgFields.length; j++){
			cfgFields[j].disabled = !field.checked;
		}
	}
}
config_addOnloadFuncList(config_gzip_openLang);
</script>
<table class="tb_normal" width=100% id="config_gzip">
	<tr>
		<td class="td_normal_title" colspan=2>
			<b>
				<label>
					<xform:checkbox property="value(kmss.gzip.compression)" onValueChange="config_gzip_openLang()" showStatus="edit">
						<xform:simpleDataSource value="true">启用文件GZIP压缩功能</xform:simpleDataSource>
					</xform:checkbox>
				</label>
			</b>
			<span class="message">说明：适用于在非tomcat/jboss下，服务器本身无法启用GZIP功能情况下的配置。</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">需压缩的资源文件的最小值</td>
		<td>
			<xform:text property="value(kmss.gzip.compressionMinSize)" subject="最小文件压缩值" required="true" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">单位（字节），当文件大于该值时才进行压缩，默认值20480（20k）</span>
		</td>
	</tr>
</table>
