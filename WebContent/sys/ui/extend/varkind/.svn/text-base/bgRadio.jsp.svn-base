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
			// 自定义
			if($("input[name='render_material_${ luivarid }']:checked").val() === 'custom'){
				$("#materialPreview-${ luivarid }").find("img").attr("src","${LUI_ContextPath}"+$("#${ luivarid }").val());
				return $("#${ luivarid }").val();
			}
			// 跟随主题
			else {
				return '';
			}
		},
		"setter":function(val){
			// 跟随主题
			if($.trim(val)==''){
				$("#render_material_${ luivarid }_theme").attr("checked",true);
				$("#render_material_${ luivarid }_custom").attr("checked",false);
			}
			// 自定义
			else {
				$("#lui_varkind_material_wrap_tr_${ luivarid }").show();

				$("#render_material_${ luivarid }_theme").attr("checked",false);
				$("#render_material_${ luivarid }_custom").attr("checked",true);

				if(val.length){
					$("#materialPreview-${ luivarid }").find("img").attr("src","${LUI_ContextPath}"+val);
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

	function selectMaterial_${ luivarid }(id){
		seajs.use(['lui/dialog'],function(dialog){
			dialog.build({
				config : {
					width : 800,
					height : 600,
					title :  "${ lfn:message('sys-portal:sysPortalPage.msg.selectMaterial') }",
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
					$("#"+id).val(url);
				}
			}).show();
		});
	}
	function clearMaterial_${ luivarid }(id){
		$("#materialPreview-"+id).find("img").attr("src","");
		$("#materialPreview-"+id).find("img").hide();
		var key = "${ luivarid }";
		$("#"+id).val("");
	}

	function changeRadio_${ luivarid }(event){
		if($(event.target).val()===""){
			$("#lui_varkind_material_wrap_tr_${ luivarid }").hide();
		}else{
			$("#lui_varkind_material_wrap_tr_${ luivarid }").show();
		}
	}
</script>
<tr>
	<td >
		<span>${ luivar['require'] ? "<i class='lui_varkind_require'>*</i>" : "" }${ lfn:msg(luivar['name']) }</span>
	</td>
	<td>
		<label class="varkind_radio"><input type="radio" name="render_material_${ luivarid }" id="render_material_${ luivarid }_theme" onclick="changeRadio_${ luivarid }(event)" checked value="">
			<span>${ lfn:message('sys-portal:sysPortalPage.follow.theme') }</span></label>
		<label class="varkind_radio"><input type="radio" name="render_material_${ luivarid }" id="render_material_${ luivarid }_custom" onclick="changeRadio_${ luivarid }(event)" value="custom">
			<span>${ lfn:message('sys-portal:sysPortal.portal.diy') }</span></label>
	</td>
</tr>
<tr class="lui_varkind_material_wrap_tr" style="display: none;" id="lui_varkind_material_wrap_tr_${ luivarid }">
	<td style="width: 15%;"></td>
	<td class="lui_varkind_material_wrap_td">
		<div class="lui_varkind_material_img" id="materialPreview-${ luivarid }" onclick="selectMaterial_${ luivarid }('${ luivarid }')">
			<img alt=""></img>
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