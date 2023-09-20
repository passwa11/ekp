<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("document.js", "style/" + Com_Parameter.Style + "/doc/");
	Com_IncludeFile("jquery.js");
</script>
<script type="text/javascript">
	//提交时验证必填
	function check(id) {
		var flag = false;
		if(id.value!=""){
			var url = '<c:url value="/third/wx/weixinSynchroOrgCheck.do?method=agentIdCheck" />';
			$.ajax({
			   type: "POST",
			   url: url,
			   async:false,
			   data: "agentId="+$.trim(id.value)+"&type=1",
			   dataType: "json",
			   success: function(data){
					if(data.status=="1"){
						flag = true;
					}else{
						alert('<bean:message key="robot.agentid.check" bundle="third-weixin"/>');
						flag = false;
					}
			   }
			});
		}else{
			flag = true;
		}
		return flag;
	}
//提交时的数据组装
	function returnValue() {
		var AgentId = document.getElementById("AgentId");
		/* if (!check(AgentId)) {
			return null;
		} */
		var rtnJson = new Array();
		rtnJson.push("{");
		if(AgentId.value){
			rtnJson.push("\"AgentId\":\"" + $.trim(AgentId.value) + "\"");
		}
		rtnJson.push("}");
		return rtnJson.join('');
	}
//页面加载时获取保存的数据进行初始化
	$(function() {
		init();
	});
//页面加载时获取保存的数据进行初始化
	function init() {
		//获取应用列表
		var url = '<c:url value="/third/wx/menu/wxMenuDefine.do?method=appList" />';
		$.ajax({
		   type: "POST",
		   url: url,
		   async:false,
		   dataType: "json",
		   success: function(data){
				if(data.status=="1"){
					var apps = data.data;
					for(var i=0; i<apps.length; i++)  {
						$("#AgentId").append("<option value='"+apps[i].appId+"'>"+apps[i].name+"</option>");
					}
				}
		   }
		});
		// 节点原配置的类型不是当前类型或没有获取到相关配置
		if (parent.NodeData.unid != parent.document.getElementById("type").value
				|| !parent.NodeContent) {
			return;
		}
		// 获得内容对象
		var json = eval('(' + parent.NodeContent + ')');
		$("#AgentId").val(json.AgentId);
	};
</script>
</head>
<body>
<table id="main" width="100%" class="tb_normal">
	<tr>
		<td width="50px"><bean:message key="robot.agentid" bundle="third-weixin"/></td>
		<td width="150px">
			<select name="AgentId" id="AgentId">
				<option value=""><bean:message key="page.firstOption"/></option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="2"><bean:message key="robot.agentid.desc" bundle="third-weixin"/></td>
	</tr>
</table>
</body>
</html>