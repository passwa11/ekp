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
<script>
${param['jsname']}.VarSet.push({
	"name":"${ luivar['key'] }",
	"getter":function(){
		$("#materialPreview-${ luivarid }").find("img").attr("src","${LUI_ContextPath}"+$("#${ luivarid }").val());
		// console.log("11${ luivar }")
		return $("#${ luivarid }").val();
	},
	"setter":function(val){
		$("#${ luivarid }").val(val);
		if(val.length){
			$("#materialPreview-${ luivarid }").find("img").attr("src","${LUI_ContextPath}"+$("#${ luivarid }").val());
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

function selectMaterial_${ luivarid }(id){
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
				//展示默认页脚logo图时去掉初始状态的+号背景 add by zhouwen 20220112
				$("#materialPreview-"+id).addClass("lui_varkind_material_img_select");
				$("#"+id).val(url);
			}
		}).show(); 
	});
}
function clearMaterial_${ luivarid }(id){
	$("#materialPreview-"+id).find("img").attr("src","");
	$("#materialPreview-"+id).find("img").hide();
	//还原初始状态的+号背景 add by zhouwen 20220112
	$("#materialPreview-"+id).removeClass("lui_varkind_material_img_select");
	$("#"+id).val("");
}
</script>
<tr>
	<td ><span>${ luivar['require'] ? "<i class='lui_varkind_require'>*</i>" : "" }${ lfn:msg(luivar['name']) }</span></td>
	<td class="lui_varkind_material_wrap_td">
		<%--判断是否存在默认logo背景图动态展示其样式和logo图片 add by zhouwen 20220112--%>
		<div class="lui_varkind_material_img ${luivar['default']!=''?'lui_varkind_material_img_select':''}" id="materialPreview-${ luivarid }" onclick="selectMaterial_${ luivarid }('${ luivarid }')">
			<img alt="" src="${ LUI_ContextPath }${luivar['default']}"/>
		</div>
		<a class="lui_varkind_material_btn" href="javascript:void(0)" onclick="selectMaterial_${ luivarid }('${ luivarid }')">
			${ lfn:message('sys-ui:ui.vars.select') }
		</a>
		<a class="lui_varkind_material_btn" href="javascript:void(0)" onclick="clearMaterial_${ luivarid }('${ luivarid }')">
			${ lfn:message('sys-ui:ui.vars.clear') }
		</a>
		<input style="width:95%" class="inputsgl" id="${ luivarid }" type="hidden" value="${luivar['default']}" />
		<p class="com_help">${ lfn:msg(luivarparam['help']) }</p>
	</td>
</tr>