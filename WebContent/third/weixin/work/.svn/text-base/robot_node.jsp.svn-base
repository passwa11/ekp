<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("document.js", "style/" + Com_Parameter.Style + "/doc/");
	Com_IncludeFile("jquery.js");
	Com_IncludeFile('dialog.js|data.js|jquery.js');
	Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js','js/jquery-plugin/manifest/');
	
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
			   data: "agentId="+$.trim(id.value)+"&type=2",
			   dataType: "json",
			   success: function(data){
					if(data.status=="1"){
						flag = true;
					}else{
						alert('<bean:message key="robot.agentid.check.work" bundle="third-weixin"/>');
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
		var sendTargetType = $("input[name='sendTargetType']:checked").val();
		if(!sendTargetType){
			alert("请先选择发送对象类型");
			return null;
		}
		
		var specifiedIds = $("input[name='specifiedIds']").val();
		if(sendTargetType=="specified" && (!specifiedIds || specifiedIds=='')){
			alert("请先选择发送对象");
			return null;
		}
		
		var specifiedNames = $("textarea[name='specifiedNames']").val();
		var rtnJson = new Array();
		rtnJson.push("{");
		rtnJson.push("\"sendTargetType\":\"" + $.trim(sendTargetType) + "\"");
		if(AgentId.value){
			rtnJson.push(",\"AgentId\":\"" + $.trim(AgentId.value) + "\"");
		}
		if(specifiedIds){
			rtnJson.push(",\"specifiedIds\":\"" + $.trim(specifiedIds) + "\"");
			rtnJson.push(",\"specifiedNames\":\"" + $.trim(specifiedNames) + "\"");
		}
		var readerNullToAll = $("input[name='readerNullToAll']").prop("checked");
		if(readerNullToAll==true){
			rtnJson.push(",\"readerNullToAll\":\"true\"");
		}else{
			rtnJson.push(",\"readerNullToAll\":\"false\"");
		}
		rtnJson.push("}");
		return rtnJson.join('');
	}
//页面加载时获取保存的数据进行初始化
	$(function() {
		Address_QuickSelection("specifiedIds","specifiedNames",";",ORG_TYPE_ALL,true,[],null,null,"");
		
		init();
		// 点击事件change
		$('input[name="sendTargetType"]').change(function () {
		    changeSendTargetType();
		});
		//alert(parent.FlowChartObject.IsEdit);
		//var templateUrl = window.parent.parent.dialogObject.Window.parent.parent.location.href;
		
	});
	
	
	var specifiedNames;
	
//页面加载时获取保存的数据进行初始化
	function init() {
		//获取应用列表
		var url = '<c:url value="/third/weixin/work/third_weixin_work/thirdWeixinWork.do?method=appList" />';
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
		var sendTargetType = null;
		var json = null;
		// 节点原配置的类型不是当前类型或没有获取到相关配置
		if (parent.NodeData.unid != parent.document.getElementById("type").value
				|| !parent.NodeContent) {
			sendTargetType = "all";
		}else{
			//alert(parent.NodeContent);
			json = eval('(' + parent.NodeContent + ')');
			sendTargetType = json.sendTargetType;
			if(!sendTargetType){
				sendTargetType = "all";
			}
			$("#AgentId").val(json.AgentId);
		}
		$("input[name='sendTargetType'][value='"+sendTargetType+"']").prop("checked",true);
		var readerNullToAll = json.readerNullToAll;
		if(readerNullToAll=="true"){
			$("input[name='readerNullToAll']").prop("checked",true);
		}
		if(sendTargetType=="specified"){
			var values = [];
			if(json && json.specifiedIds){
				var specifiedIds = json.specifiedIds;
				specifiedNames = json.specifiedNames;
				var specifiedIdsArray = specifiedIds.split(";");
				var specifiedNamesArray = specifiedNames.split(";");
				for(i=0;i<specifiedIdsArray.length;i++){
					var obj = {};
					obj.id = specifiedIdsArray[i];
					obj.name = specifiedNamesArray[i];
					values[i] = obj;
				}
				//initNewAddress("specifiedIds","specifiedNames",";","ORG_TYPE_ALL",true,values,null,null,"");
				$("input[name='specifiedIds']").val(specifiedIds);
				$("textarea[name='specifiedNames']").val(specifiedNames);
				Address_QuickSelection("specifiedIds","specifiedNames",";",ORG_TYPE_ALL,true,values,null,null,"");
				
			}
			$("#reader_div").hide();
			if(!parent.FlowChartObject.IsEdit){
				$("#specified_div").hide();
				$("#specifiedNames_view").text(specifiedNames);
				$("#specified_div_view").show();
			}
		}else if(sendTargetType=="reader"){
			$("#specified_div").hide();
			$("#reader_div").show();
		}else{
			$("#specified_div").hide();
			$("#reader_div").hide();
		}
		
	}
	
	function changeSendTargetType(){
		var sendTargetType = $("input[name='sendTargetType']:checked").val();
		if(sendTargetType=="specified"){
			$("#specified_div").show();
			$("#reader_div").hide();
		}else if(sendTargetType=="reader"){
			$("#specified_div").hide();
			$("#reader_div").show();
		}else{
			$("#specified_div").hide();
			$("#reader_div").hide();
		}
	}
</script>
</head>
<body>
<table id="main" width="100%" class="tb_normal">
	<tr>
		<td width="20%"><bean:message key="robot.agentid" bundle="third-weixin"/></td>
		<td width="80%">
			<select name="AgentId" id="AgentId">
				<option value=""><bean:message key="page.firstOption"/></option>
			</select>
			<br>
			<bean:message key="robot.agentid.desc" bundle="third-weixin"/>
		</td>
	</tr>
	
	
	<tr>
		<td width="20%">${lfn:message('third-weixin-work:third.weixin.work.config.sender')}</td>
		<td width="80%">
			<input type="radio" name="sendTargetType" value="all"/>${lfn:message('third-weixin-work:third.weixin.work.everyone')}
			<input type="radio" name="sendTargetType" value="reader"/>${lfn:message('third-weixin-work:third.weixin.work.preReader')}
			<input type="radio" name="sendTargetType" value="specified"/>${lfn:message('third-weixin-work:third.weixin.work.designated')}
			<br>
				<script>
				$(document).ready(
						//function(){
						//	Address_QuickSelection("authReaderIds","authReaderNames",";",ORG_TYPE_ALL,true,[],null,null,"");
						//	}
						);
				</script>
				<div id="reader_div" class="inputselectmul" style="width:98%;height:35px;">
					<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="readerNullToAll" value="true"/>${lfn:message('third-weixin-work:third.weixin.work.everyone.send')}
				</div>
				<div id="specified_div_view" class="inputselectmul" style="width:98%;height:80px;display:none;">
					<textarea id="specifiedNames_view" ></textarea>
				</div>
				<div id="specified_div" class="inputselectmul" style="width:98%;height:80px;">
				<input name="specifiedIds" xform-name="specifiedNames" value="" type="hidden">
				<div class="textarea" >
				<textarea style="display:none;" xform-type="newAddressHidden" xform-name="specifiedNames" name="specifiedNames"></textarea>
				<div class="mf_container">
				<ol class="mf_list" aria-atomic="false" aria-live="polite" aria-multiselectable="true" role="listbox"></ol>
				<textarea style="" xform-type="newAddress" xform-name="mf_specifiedNames" data-propertyid="specifiedIds" data-propertyname="specifiedNames" data-splitchar=";" data-orgtype="ORG_TYPE_ALL" data-ismulti="true" class="mf_input mp_input" aria-autocomplete="list" autocomplete="off" role="combobox" aria-required="true" style="width: 28px; height: 24px; overflow: hidden; line-height: 24px;"></textarea>
				<span class="mf_measure" style="font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Helvetica Neue&quot;, Arial, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Heiti SC&quot;, &quot;Segoe UI&quot;, sans-serif; font-size: 12px; font-style: normal; font-variant: normal; font-weight: 400; left: -9999px; letter-spacing: 0px; position: absolute; text-transform: none; top: -9999px; white-space: nowrap; width: auto; word-spacing: 0px;"></span>
				</div>
				</div>
				<ol class="mp_list" aria-atomic="true" aria-busy="false" aria-live="polite"  role="listbox" style="display: none;"></ol>
				<div onclick="Dialog_Address(true,$(this).attr('__id'),$(this).attr('__name'),';',ORG_TYPE_ALL,null,null,null,null,null,null,null);" __id="specifiedIds" __name="specifiedNames" class="orgelement">
				</div>
				</div>
				<br>
				${lfn:message('third-weixin-work:third.weixin.work.config.opt.tip')}
		</td>
	</tr>
</table>
</body>
</html>