<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
Com_IncludeFile("doclist.js|dialog.js");
</script>
<script type="text/javascript">
function config_lang_openLang(){
	var tbObj = document.getElementById("config_language");
	var field = tbObj.rows[0].getElementsByTagName("INPUT")[0];
	for(var i=1; i<tbObj.rows.length; i++){
		tbObj.rows[i].style.display = field.checked?"":"none";
		var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
		for(var j=0; j<cfgFields.length; j++){
			cfgFields[j].disabled = !field.checked;
		}
	}
}
function config_lang_select_mode(){
	var field = $("#config_language").find("input[type=radio]:checked").val();
	if("radio" == field) {
		$("#config_language_select_mode_message").show();
	} else {
		$("#config_language_select_mode_message").hide();
	}
}

config_addOnloadFuncList(config_lang_openLang);
config_addOnloadFuncList(config_lang_select_mode);
</script>
<table class="tb_normal" width=100% id="config_language">
	<tr>
		<td class="td_normal_title" colspan=2>
			<b>
				<label>
					<xform:checkbox htmlElementProperties="id='language_support'" property="value(kmss.lang.suportEnabled)" onValueChange="config_lang_openLang()" showStatus="edit">
						<xform:simpleDataSource value="true">多语言支持</xform:simpleDataSource>
					</xform:checkbox>
				</label>
			</b>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">官方语言</td>
		<td>
			<xform:text property="value(kmss.lang.official)" subject="官方语言" required="true" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">例：中文(简体)|zh-CN，English|en-US，中文(香港)|zh-HK， 日本語|ja-JP。说明：当用户没有设置默认语言、选择语言的时候，使用官方语言（只能使用其中一种语言）</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">语言配置</td>
		<td>
			<xform:text property="value(kmss.lang.support)" subject="语言配置" required="true" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">例：中文(简体)|zh-CN;English|en-US;中文(香港)|zh-HK;日本語|ja-JP</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">语言选择模式</td>
		<td>
			<table width="100%">
				<tr>
					<td class="td_normal_title" width="20%">单屏登录页</td>
					<td width="20%" align="center">
						<a href="sys/config/configJsp/base/images/singleScreen.png" target="_blank" title="点击查看原图">
							<img alt="单屏登录页预览图" src="sys/config/configJsp/base/images/singleScreen.png" width="100px" height="50px"/>
						</a>
					</td>
					<td>
						<xform:radio property="value(kmss.lang.select.mode)" subject="语言选择模式" showStatus="edit" onValueChange="config_lang_select_mode">
							<xform:simpleDataSource value="select">下拉框</xform:simpleDataSource>
							<xform:simpleDataSource value="radio">单选框</xform:simpleDataSource>
						</xform:radio>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">多屏滑动登录页</td>
					<td width="20%" align="center">
						<a href="sys/config/configJsp/base/images/multiScreen.png" target="_blank" title="点击查看原图">
							<img alt="单屏登录页预览图" src="sys/config/configJsp/base/images/multiScreen.png" width="100px" height="50px"/>
						</a>
					</td>
					<td>仅下拉框，默认为“下拉框”模式。</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">随机背景登录页</td>
					<td width="20%" align="center">
						<a href="sys/config/configJsp/base/images/randomScreen.png" target="_blank" title="点击查看原图">
							<img alt="随机背景登录页预览图" src="sys/config/configJsp/base/images/randomScreen.png" width="100px" height="50px"/>
						</a>
					</td>
					<td>固定显示在右上方。</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
