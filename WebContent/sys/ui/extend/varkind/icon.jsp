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
<script>
		${param['jsname']}.VarSet.push({
			"name":"${ luivar['key'] }",
			"getter":function(){
				var img = $("[name='img']").val();
				if(img){
					$("#iconPreview").attr("class","lui_icon_l ")
					return img;
				}else{
					$("#iconPreview").attr("class","lui_icon_l "+$("#${ luivarid }").val())
					return $("#${ luivarid }").val();
				}
			},
			"setter":function(val){
				if (val.indexOf("/") == 0) { //素材库图片
					imgUrl = val.substring(1);
					$("#iconPreview").attr("class","lui_icon_l ");
					$("[name='img']").val(val);
					$("#iconPreview .lui_img_l").attr('src',Com_Parameter.ContextPath+imgUrl);
				}else{ //系统图片
					$("#iconPreview .lui_img_l").attr('src',"");
					$("#${ luivarid }").val(val);
					$("#iconPreview").attr("class","lui_icon_l "+$("#${ luivarid }").val())
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
//选择图标
function selectIcon(){
	seajs.use(['lui/dialog'],function(dialog){
		dialog.build({
			config : {
					width : 750,
					height : 500,
					title : "${ lfn:message('sys-portal:sysPortalPage.msg.selectIcon') }",
					content : {
						type : "iframe",
						url : "/sys/ui/jsp/iconfont.jsp"
					}
			},
			callback : function(value, dia) {
				if(value==null){
					return ;
				}
				if(value.url) {//素材库图标
					var imgUrl = value.url;
					if (imgUrl.indexOf("/") == 0) {
						imgUrl = imgUrl.substring(1);
					}
					var key = "img";
					$("[name='"+key+"']").val(value.url);
					$("#iconPreview").attr("class","lui_icon_l ");
					$("[name='icon']").val("");
					$("#iconPreview>.lui_img_l").css("display","block");
					$("#iconPreview>.lui_img_l").attr('src',Com_Parameter.ContextPath+imgUrl);
				}else{//系统
					$("#iconPreview>.lui_img_l").css("display","none");
					$("#iconPreview").attr("class","lui_icon_l "+value);
					$("#iconPreview>.lui_img_l").attr('src',"");
					var key = "${ luivar['key'] }";
					$("[name='img']").val("");
					$("[name='"+key+"']").val(value);
				}
			}
		}).show(); 
	});
}
//清除
function clearIcon(){
	$("#iconPreview").attr("class","lui_icon_l ");
	var key = "${ luivar['key'] }";
	$("[name='"+key+"']").val("");
	$("[name='img']").val("");
	$(".lui_img_l").attr('src',"");
	$(".lui_img_l").css("display","none");
}
</script>
<tr>
	<td ><span>${ luivar['require'] ? "<i class='lui_varkind_require'>*</i>" : "" }${ lfn:msg(luivar['name']) }</span></td>
	<td class="varkindIcon">
		<div class="titleIconShowBox ">
			<div style="cursor: pointer;" id='iconPreview' class="lui_icon_l ${luivar['default']}" onclick="selectIcon()">
				<img class="lui_img_l" src="" width="100%">
			</div>
		</div>
		<a class="icon" href="javascript:void(0)" onclick="selectIcon()">
			${ lfn:message('sys-ui:ui.vars.select') }
		</a>
		<a href="javascript:void(0)" onclick="clearIcon()">
			${ lfn:message('sys-ui:ui.vars.clear') }
		</a>
		<input style="width:95%" class="inputsgl" id="${ luivarid }" name="${ luivar['key'] }" type="hidden" value="${luivar['default']}" />
		<input style="width:95%" class="inputsgl" id="${ luivarid }" name="img" type="hidden" value="" />
		<span class="com_help">${ lfn:msg(luivarparam['help']) }</span>
	</td>
</tr>