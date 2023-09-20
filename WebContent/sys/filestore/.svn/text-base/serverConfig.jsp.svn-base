<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>	
<%@ include file="/resource/jsp/common.jsp"%>

<script>
	function config_att_location_server_out() {
		var encryMode = "${sysConfigAdminForm.map['sys.att.encryption.mode']}" || "0";
		var modes = document
				.getElementsByName("value(sys.att.encryption.mode)")[encryMode].checked = true;
		encryModeChange(encryMode);
	}
	
	function encryModeChange(encryMode){
		var aesDiv = $("#aesDiv");
		if(encryMode == "3"){
			aesDiv.css("display","");
			$("#aespass").attr("validate","required");
		}else{
			aesDiv.css("display","none");
			$("#aespass").attr("validate","");
		}
	}
	
	function randomSeed(){
		var seed = Math.random().toString(36).substr(2);
		$("#aespass").val(seed);
    }
</script>
<tr>
	<td class="td_normal_title" width="15%">是否启用加密附件</td>
	<td><xform:radio property="value(sys.att.encryption.mode)"
			showStatus="edit" required="true" onValueChange="encryModeChange(this.value);">
			<xform:simpleDataSource value="0">不加密（不兼容加密附件）</xform:simpleDataSource>
			<xform:simpleDataSource value="1">简单加密（兼容不加密和AES加密，稍微影响性能）</xform:simpleDataSource>
			<xform:simpleDataSource value="2">不加密（兼容简单加密和AES加密，稍微影响性能）</xform:simpleDataSource>
			<xform:simpleDataSource value="3">AES加密（兼容不加密和简单加密，稍微影响性能）</xform:simpleDataSource>
		</xform:radio><br/><br/>
		
		<div id="aesDiv" style="display:none;">
			<c:choose>  
			   <c:when test="${sysConfigAdminForm.map['sys.att.encryption.aespass'] == null }"> 
			         <span>请输入AES密钥种子：</span>
				   <xform:text property="value(sys.att.encryption.aesseed)" subject="AES密钥种子" 
	                   required="true" style="width:150px" showStatus="edit" htmlElementProperties="id='aespass'"/>
	               <input type="button" class="btnopt" value="随机生成密钥种子" onclick="randomSeed();"/>
	               <span style="color:red;">系统根据AES密钥种子生成加密密钥，请务必牢记该密钥种子。</span>
			   </c:when>  
			   <c:otherwise>
			       <span style="color:red;">已生成AES密钥，请确保您已牢记密钥种子，否则在密钥丢失或损坏时加密文件无法恢复。</span>
			   </c:otherwise>  
			</c:choose> <br/><br/>
			
			<div style="color:blue;">
			AES加密说明：<br/>
            1、AES加密方式采用128位AES加密算法对文件进行加密，拥有足够的安全强度，加密和解密会稍微影响性能;
			</div><br/><br/>
			<div style="color:blue;">
			AES加密注意事项：<br/>
			1、请牢记AES密钥种子或将密钥种子保存在可靠的地方!<br/>
            2、密钥只能有一份，不能变，如丢失或者损坏，会造成附件相关操作失败，请进入admin.do使用第一次的密钥种子启用AES加密;<br/>
            3、第一次启用AES加密时系统根据输入的密钥种子生成密钥，加密和解密必须使用相同的密钥;<br/>
            4、启用AES加密须在admin.do保存成功后，将备份配置文件中的kmssconfig.properties和admin.properties文件复制到aspose或永中附件转换配置目录（\Converter\config）下;
			</div>
		</div>
	</td>
</tr>