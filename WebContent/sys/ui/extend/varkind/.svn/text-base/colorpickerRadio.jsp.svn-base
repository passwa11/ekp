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
		// 自定义
		if($("input[name='render_colorpicker_${ luivarid }']:checked").val() === 'custom'){
			return $("#${ luivarid }").val();
		}
		// 跟随主题
		else{
			return '';
		}
	},
	"setter":function(val){
			// 自定义
		if($.trim(val)==''){
			
			$("#render_colorpicker_${ luivarid }_theme").attr("checked",true);
			$("#render_colorpicker_${ luivarid }_custom").attr("checked",false);
		}
		else{
			$("#lui_varkind_colorpicker_wrap_tr_${ luivarid }").show();

			$("#render_colorpicker_${ luivarid }_theme").attr("checked",false);
			$("#render_colorpicker_${ luivarid }_custom").attr("checked",true);

			var ck = $("#ck-${ luivarid }").prop("checked");
			if(ck){
				val = 'transparent';
			} 
		}
		
		$("#${ luivarid }").val(val);
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

function changeRadio_${ luivarid }(event){
	if($(event.target).val()===""){
		$("#lui_varkind_colorpicker_wrap_tr_${ luivarid }").hide();
	}else{
		$("#lui_varkind_colorpicker_wrap_tr_${ luivarid }").show();
	}
}
</script>
<tr>
	<td >
		<span>${ luivar['require'] ? "<i class='lui_varkind_require'>*</i>" : "" }${ lfn:msg(luivar['name']) }</span>
	</td>
	<td>
		<label class="varkind_radio"><input type="radio" name="render_colorpicker_${ luivarid }" id="render_colorpicker_${ luivarid }_theme" onclick="changeRadio_${ luivarid }(event)" checked value="">
			<span>
				${ lfn:message('sys-portal:sysPortalPage.follow.theme') }
			</span>
		</label>
		<label class="varkind_radio"><input type="radio" name="render_colorpicker_${ luivarid }" id="render_colorpicker_${ luivarid }_custom" onclick="changeRadio_${ luivarid }(event)" value="custom">
			<span>${ lfn:message('sys-portal:sysPortal.portal.diy') }</span>
		</label>
	</td>
</tr>
<tr class="lui_varkind_colorpicker_wrap_tr" style="display: none;" id="lui_varkind_colorpicker_wrap_tr_${ luivarid }">
	<td >
	</td>
	<td>
		<input class="inputsgl" type="text" style="width: 150px;" id="${ luivarid }" name="${ luivar['key'] }" placeholder="#4285F4" value="${luivar['default']}">
		<span class="com_help">${ lfn:msg(luivarparam['help']) }</span>
	</td>
</tr>