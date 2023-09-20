<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/config/resource/edit_top.jsp"%>

<html>
<head>
<title>蓝凌统一搜索系统配置</title>
<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/util.js"></script>
<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/jquery.js"></script>
<script>

function hideDisplayTcpBlocking(){
	if(document.getElementsByName("value(kmss.onesearch.config.version)")[0].checked == true){
		document.getElementById("esTcpBlocking").style.display = "none";
		document.getElementById("ssl").style.display = "";
		document.getElementById("pwd").style.display = "";
	}else{
		document.getElementById("esTcpBlocking").style.display = "";
		document.getElementById("ssl").style.display = "none";
		document.getElementById("pwd").style.display = "none";
	}
	
}
function _submitForm(){
	if(_check()){
		Com_Submit(document.onesearchConfigForm, "save");
	}
}

function _check(){ 
	var host = document.getElementsByName("value(kmss.onesearch.config.host)")[0].value.replace(/[ ]/g,""); 
	var port = document.getElementsByName("value(kmss.onesearch.config.port)")[0].value.replace(/[ ]/g,""); 
	var system = document.getElementsByName("value(kmss.onesearch.config.system)")[0].value.replace(/[ ]/g,"");
	if(host == "" || port == ""||system=="" ){
		alert("都是必填项，不能缺省！");
		return false;
	}
	var near = document.getElementsByName("value(kmss.onesearch.config.near)")[0].value;
	var over = document.getElementsByName("value(kmss.onesearch.config.over)")[0].value;
	var bigFileSize = document.getElementsByName("value(kmss.onesearch.config.bigFileSize)")[0].value;
	var maxFileSize = document.getElementsByName("value(kmss.onesearch.config.maxFileSize)")[0].value;
	var filterFileSize = document.getElementsByName("value(kmss.onesearch.config.filterFileSize)")[0].value;
	var similarity = document.getElementsByName("value(kmss.onesearch.config.doc.min.similarity)")[0].value;
	var reqTimeout = document.getElementsByName("value(kmss.onesearch.config.reqTimeout)")[0].value;
	var maxOffset = document.getElementsByName("value(kmss.onesearch.config.aucomplete.maxOffset)")[0].value;
	var bigExcelSize = document.getElementsByName("value(kmss.onesearch.config.bigExcelSize)")[0].value;
	if(validata(near) ||
			validata(over) ||
			validata(bigFileSize)||
			validata(maxFileSize)||
			validata(bigExcelSize)||
			validata(similarity)||
			validata(reqTimeout)||
			validata(maxOffset)||
			validata(filterFileSize)){
		alert("相关参数配置中配置项必须是数字且可以是任意小数位，请确认！");
		return false;
	}
	return true;
}
function validata(value) {

	var index = value.indexOf('.');
	if(index > 0){
		var sub = value.substring(0,index);
		if(sub.length>1){
			if(sub.substring(0,1)=="0"){
				return true;
			}
		}
	}else {
		if(value.length>1){
			if(value.substring(0,1)=="0"){
				return true;
			}
		}
	}
	
	if (value.match("^[0-9]+(\\.[0-9]+)?$")) {
		return false;
	}
	return true;
	
}

</script>
</head>
<body onload="hideDisplayTcpBlocking()">
<html:form action="/third/ftsearch/onesearchConfig.do?method=save">
	<div id="optBarDiv">
		<input type="button" class="btnopt" value="保存" onclick="_submitForm();"/>
		<input type="button" class="btnopt" value="关闭" onclick="top.close();"/>
	</div>
<p class="txttitle">蓝凌统一搜索系统配置</p>
	<center>
	<table id="Label_Tabel" width=95%>
		<!-- 基本配置 -->
		<tr
			LKS_LabelName="基本配置">
			<td>
			<table class="tb_normal" width=100% id="onesearchConfigForm">
				<tr><td>
					<table class="tb_normal" width=100%>
					    <tr id="esVersion">
							<td class="td_normal_title" width="15%" >蓝凌统一搜索服务版本</td>
							<td>
								<html:radio onchange="javascript:hideDisplayTcpBlocking()" value = "0" property="value(kmss.onesearch.config.version)" style="width:10px"/>16.0及以上版本
								<html:radio onchange="javascript:hideDisplayTcpBlocking()"  value = "1" property="value(kmss.onesearch.config.version)" style="width:10px"/>15.0及以前版本
								<span class="txtstrong">*</span><br>
								<span class="message">蓝凌统一搜索版本</span>
							</td>
						</tr>
					
						<tr id="esSearchSystem">
							<td class="td_normal_title" width="15%" >蓝凌统一搜索系统名称</td>
							<td>
								<html:text property="value(kmss.onesearch.config.system)" style="width:150px"/>
								<span class="txtstrong">*</span><br>
								<span class="message">系统名，默认ekp。</span>
							</td>
						</tr>
							
						<tr id="esSearchServer">
							<td class="td_normal_title"  width="15%" >蓝凌统一搜索服务器地址</td>
							<td>
								<html:text property="value(kmss.onesearch.config.host)" style="width:150px" />
								<span class="txtstrong">*</span><br>
								<span class="message">服务器地址 与下面配置服务端口的顺序要一一对应，如有用到集群，地址用  ,号隔开 例：127.0.0.1,127.0.0.2</span>
							</td>
						</tr>
						<tr id="esSearchPort">
							<td class="td_normal_title" width="15%" >蓝凌统一搜索服务端口</td>
							<td>
								<html:text property="value(kmss.onesearch.config.port)" style="width:150px"/>
								<span class="txtstrong">*</span><br>
								<span class="message">服务端口 与服务器地址顺序要一一对应 如有用到集群，端口用,号隔开 例：9200,9200或9300,9300，16.0及以后版本默认9200，15.0及以前版本默认9300</span>
							</td>
						</tr>
						
						<tr id="esTcpBlocking">
							<td class="td_normal_title" width="15%" >启用TcpBlocking</td>
							<td>
								<html:radio value = "false" property="value(kmss.onesearch.config.tcpBlocking)" style="width:10px"/>否
								<html:radio value = "true" property="value(kmss.onesearch.config.tcpBlocking)" style="width:10px"/>是
								<span class="txtstrong">*</span><br>
								<span class="message">如索引定时任务造成CPU长期100%使用，默认为否</span>
							</td>
						</tr>
						
						<tr id="ssl">
							<td class="td_normal_title" width="15%" >是否启用https</td>
							<td>
								<html:radio value = "0" property="value(kmss.onesearch.config.ssl)" style="width:10px"/>否
								<html:radio value = "1" property="value(kmss.onesearch.config.ssl)" style="width:10px"/>是
								<span class="txtstrong">*</span><br>
								<span class="message">统一搜索服务是否启用基于ssl的https传输</span>
							</td>
						</tr>
							
						<tr id="pwd">
							<td class="td_normal_title" width="15%" >登录统一搜索服务密码</td>
							<td>
								<html:password  property="value(kmss.onesearch.config.pwd)" styleClass="inputSgl" />
								<span class="message">统一搜索服务如有配置登录安全管控，则需要配置密码</span>
							</td>
						</tr>
						
						
						<tr>
							<td></td>
							<td>
								<span class="message">此页面的参数配置修改直接保存即可生效不需要重启系统。</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
	<tr LKS_LabelName="相关参数配置">
		<td>
			<table class="tb_normal" width=100% id="onesearchConfigForm" >
				<tr><td>
					<table class="tb_normal" width=100%>
						<tr id="stringSearchNear">
							<td class="td_normal_title" width="15%" >字母和数字组合搜索最小长度比例</td>
							<td>
								<html:text property="value(kmss.onesearch.config.near)" style="width:150px"/>
								<span class="txtstrong">*</span><br>
								<span class="message">字母和数字组合搜索最小长度比例，范围 0~1，默认1.0。</span>
							</td>
						</tr>
						<tr id="stringSearchOver">
							<td class="td_normal_title" width="15%" >字母和数字组合搜索最大长度比例</td>
							<td>
								<html:text property="value(kmss.onesearch.config.over)" style="width:150px"/>
								<span class="txtstrong">*</span><br>
								<span class="message">字符串搜索匹配字符串最大长度比例，范围1~10，默认2.0。</span>
							</td>					
						</tr>
						<tr id="bigFileSize">
							<td class="td_normal_title" width="15%" >多线程索引时，附件解析串行处理阀值(单位m)</td>
							<td>
								<html:text property="value(kmss.onesearch.config.bigFileSize)" style="width:150px"/>
								<span class="txtstrong">*</span><br>
								<span class="message">索引线程数超过1时，当附件的大小超过此阀值时采用串行的方式处理，避免内存溢出(单位m)</span>
							</td>					
						</tr>
						<tr id="maxFileSize">
							<td class="td_normal_title" width="15%" >附件内容解析阀值(单位m，0为不限制)</td>
							<td>
								<html:text property="value(kmss.onesearch.config.maxFileSize)" style="width:150px"/>
								<span class="message">当附件大小超过此阈值时不解析附件内容(单位m，0为不限制)</span>
							</td>					
						</tr>
						<tr id="filterFileType">
							<td class="td_normal_title" width="15%" >不解析内容的附件类型</td>
							<td>
								<html:text property="value(kmss.onesearch.config.filterFileType)" style="width:150px"/>
								<span class="message">指定不解析内容的附件后缀名，多个用英文(,)隔开</span>
							</td>					
						</tr>
						<tr id="filterFileSize">
							<td class="td_normal_title" width="15%" >指定附件类型不解析内容阈值(单位为m，0为不限制)</td>
							<td>
								<html:text property="value(kmss.onesearch.config.filterFileSize)" style="width:150px"/>
								<span class="message">当指定的附件类型的大小超过此阈值时才不解析(单位为m，0为不限制)</span>
							</td>					
						</tr>
						<tr id="bigExcelSize">
							<td class="td_normal_title" width="15%" >大excel阀值，单位m，默认为5m</td>
							<td>
								<html:text property="value(kmss.onesearch.config.bigExcelSize)" style="width:150px"/>
								<span class="txtstrong">*</span><br>
								<span class="message">大于0的浮点数，超过该值的excel，则认为为大excel</span>
							</td>					
						</tr>
						<tr id="reqTimeout">
							<td class="td_normal_title" width="15%" >向统一搜索服务发起请求超时时间(单位秒)</td>
							<td>
								<html:text property="value(kmss.onesearch.config.reqTimeout)" style="width:150px"/>
								<span class="txtstrong">*</span><br>
								<span class="message">向统一搜索服务发起请求的超时时间,超时自动返回，避免请求堵塞</span>
							</td>					
						</tr>
						<tr id="maxOffset">
							<td class="td_normal_title" width="15%" >输入联想起始最大不匹配字数</td>
							<td>
								<html:text property="value(kmss.onesearch.config.aucomplete.maxOffset)" style="width:150px"/>
								<span class="txtstrong">*</span><br>
								<span class="message">输入联想，前面最多可以有几个不匹配的字，0表示首字开始匹配</span>
							</td>					
						</tr>
						<tr id="docSimilarity">
							<td class="td_normal_title" width="15%" >相类似知识最小相似度</td>
							<td>
								<html:text property="value(kmss.onesearch.config.doc.min.similarity)" style="width:150px"/>
								<span class="txtstrong">*</span><br>
								<span class="message">取值范围：0-1.0，维基知识库等文档的显示界面的相类似知识获取接口的最小相似度，利用搜索引擎搜索相类似知识的相似度参数</span>
							</td>					
						</tr>
						<tr>
							<td></td>
							<td class="message">注意：此页面的参数,只能保留一位小数且不是技术人员请不要随意更改!</td>
						</tr>
			</table>	
		</td>
	</tr>
	</table>
	</center>
</html:form>
</body>
</html>
