<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>

	var kmss_tripartite_admin_enabled = false;
	function behavior_changeEnable(){
		var field = document.getElementsByName("_value(kmss.behavior.enabled)")[0];
		if(kmss_tripartite_admin_enabled) {
			// 三员管理开启，此功能强制关闭
			field.checked = false;
			// 开启三员时，此功能隐藏
			document.getElementById("table_behavior").style.display = "none";
		} else {
			document.getElementById("table_behavior").style.display = "";
		}
		
		var tbObj = document.getElementById("tbody_behavior");
		for (var i = 0; i < tbObj.rows.length; i++) {
			tbObj.rows[i].style.display = field.checked ? "" : "none";
			var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
			for (var j = 0; j < cfgFields.length; j++) {
				cfgFields[j].disabled = !field.checked;
			}
		}
	}
	function behavior_onload(){
		// 接收三员管理开关事件
		$(document).on("kmss.tripartite.admin.enabled", function(evn, val){
			kmss_tripartite_admin_enabled = val;
			behavior_changeEnable();
		});
	}
	
	// 提交前处理
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
		var field = document.getElementsByName("_value(kmss.behavior.enabled)")[0];
		if(kmss_tripartite_admin_enabled) {
			// 三员管理开启，此功能强制关闭
			field.checked = false;
			document.getElementsByName("value(kmss.behavior.enabled)")[0].value = "";
		}
		return true;
	}
	
	config_addOnloadFuncList(function() {
		behavior_onload();
	});
	
</script>
<table class="tb_normal" width=100% id="table_behavior">
	<tr>
		<td class="td_normal_title" colspan=2>
			<b>
				<label>
					<xform:checkbox property="value(kmss.behavior.enabled)" showStatus="edit" onValueChange="behavior_changeEnable()">
						<xform:simpleDataSource value="true">启用运营日志采集</xform:simpleDataSource>
					</xform:checkbox>
				</label>
			</b>
		</td>
	</tr>
	<tbody id="tbody_behavior">
	<tr>
		<td class="td_normal_title" width="15%">日志存放位置</td>
		<td>
			<xform:text property="value(kmss.behavior.logPath)" subject="日志存放位置" style="width:85%" showStatus="edit"/><br>
			<span class="message">
				日志文件保存路径，例：windows环境为“c:/landray/kmss/resource/behavior”,linux和unix为“/usr/landray/kmss/resource/behavior”<br>
				不填写则默认保存在“附件存放位置”目录下的“behavior”子文件夹
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">日志存放天数</td>
		<td>
			<xform:text property="value(kmss.behavior.dayToDel)" subject="日志存放天数" validators="digits" showStatus="edit"/><br>
			<span class="message">
				默认日志将保留30天
			</span>
		</td>
	</tr>
	</tbody>
	<tr>
		<td class="td_normal_title" width="15%">说明</td>
		<td>
			<span class="message">
				运营日志将比操作日志记录更加详细的信息，主要用于系统的热点分析、系统使用情况分析等，可以通过将日志上传到蓝凌服务网站（或者通过蓝凌的运营日志上传工具自动上传），日志上传后可以在服务网站查看到各项分析图表。若需要开通此功能，请联系蓝凌客服；若不需要开通此功能，可以关闭运营日志的采集功能。<br>
				开启运营日志后，可以禁用系统操作日志（在“基础设置-日志参数”设定），但禁用操作日志后将不能直接从系统查看操作日志。<br><br>
				日志文件容量估算：一般的，每个节点每天将产生大约100M的日志文件（若系统访问量比较大，可能会到达200M），超过200M的日志已经说明单台服务器的请求已经超负荷<br>
				若现有系统有5个节点，日志保留30天，则日志文件容量大概为：5 * ( 30 + 1 ) * 100M = 15500M（30+1中的1是当天的日志）
			</span>
		</td>
	</tr>
</table>