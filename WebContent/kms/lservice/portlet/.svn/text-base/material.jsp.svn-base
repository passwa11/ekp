<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.taglib.fn.LuiFunctions"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
JSONObject var = JSONObject.fromObject(request.getParameter("var"));
pageContext.setAttribute("luivar",var);
pageContext.setAttribute("luivarid","var_"+IDGenerator.generateID());
pageContext.setAttribute("luivarparam",StringUtil.isNotNull(var.getString("body")) ? JSONObject.fromObject(var.get("body")) : new JSONObject());
//out.print(request.getParameter("var")); 
%>
<style>
	.varkindIcon .titleIconShowBox{
		display:inline-block;
		background-color:#e3e3e3;
	}
	.varkindIcon .materialPreview{
		padding:0px;
		height:50px;
		width:50px;
		line-height:50px;
		font-size:50px;
		magrin:3px;
	}
	.materialPreview img{
		width:100%;
		height:100%;
	}
	.varkindIcon a{
		color:#3eb2e6;
	}
</style>
<script>
${param['jsname']}.VarSet.push({
	"name":"${ luivar['key'] }",
	"getter":function(){
		$("#materialPreview-${ luivarid }").find("img").attr("src","${LUI_ContextPath}"+$("#${ luivarid }").val());
		return $("#${ luivarid }").val();
	},
	"setter":function(val){
		$("#${ luivarid }").val(val);
		var url="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="+$("#${ luivarid }").val();
		if(val.length){
			$("#materialPreview-${ luivarid }").find("img").attr("src","${LUI_ContextPath}"+url);
		}
		
	},
	"validation":function(){
		var val = this['getter'].call();
		var requ = ${ luivar['require'] ? "true" : "false" };
		if(requ){
			if($.trim(val)==""){
				return "${ lfn:msg(luivar['name']) }不能为空";
			}
		}
	}
});

function selectMaterial(id){
	seajs.use(['lui/dialog'],function(dialog){
		dialog.build({
			config : {
					width : 800,
					height : 600,  
					title :  "${ lfn:message('sys-portal:sysPortalPage.msg.selectMaterial ') }",
					content : {  
						type : "iframe",
						url : "/sys/portal/sys_portal_material_main/sysPortalMaterialMain.do?method=selectMaterial"
					}
			},
			callback : function(value, dia) {
			
				if(value==null){
					return ;
				}
				var url = value.url;
				$("#materialPreview-"+id).find("img").attr("src","${LUI_ContextPath}"+url);
				$("#materialPreview-"+id).find("img").show();
				$("#"+id).val(GetUrlParam(url,'fdId'));
			}
		}).show(); 
	});
}
function clearMaterial(id){
	$("#materialPreview-"+id).find("img").attr("src","");
	$("#materialPreview-"+id).find("img").hide();
	var key = "${ luivarid }";
	$("#"+id).val("");
}
function GetUrlParam(url,paraName) {
　　　　var arrObj = url.split("?");

　　　　if (arrObj.length > 1) {
　　　　　　var arrPara = arrObj[1].split("&");
　　　　　　var arr;

　　　　　　for (var i = 0; i < arrPara.length; i++) {
　　　　　　　　arr = arrPara[i].split("=");

　　　　　　　　if (arr != null && arr[0] == paraName) {
　　　　　　　　　　return arr[1];
　　　　　　　　}
　　　　　　}
　　　　　　return "";
　　　　}
　　　　else {
　　　　　　return "";
　　　　}
}
</script>
<tr>
	<td>${ lfn:msg(luivar['name']) }</td>
	<td class="varkindIcon">
		<div class="titleIconShowBox ">
			<div style="cursor: pointer;" class="materialPreview" id="materialPreview-${ luivarid }" onclick="selectMaterial()">
				<img alt=""></img>
			</div>
		</div>
		<a class="icon" href="javascript:void(0)" onclick="selectMaterial('${ luivarid }')">
			${ lfn:message('sys-ui:ui.vars.select') }
		</a>
		&nbsp;
		<a href="javascript:void(0)" onclick="clearMaterial('${ luivarid }')">
			${ lfn:message('sys-ui:ui.vars.clear') }
		</a>
		<input style="width:95%" class="inputsgl" id="${ luivarid }" type="hidden" value="${luivar['default']}" />
		${ luivar['require'] ? "<span style='color:red;'>*<span>" : "" }
		<span class="com_help">${ lfn:msg(luivarparam['help']) }</span>
	</td>
</tr>