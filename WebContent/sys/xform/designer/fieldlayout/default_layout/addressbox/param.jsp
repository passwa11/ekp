<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">
	Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
	Com_IncludeFile("jquery.js");
	Com_IncludeFile('json2.js');
</script>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
</head>
<body>
	<form>
	<table class="tb_normal"  width=95%>
		<tr>
			<td align="center" colspan="2">
				<b><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_setParameters" /></b>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=40%><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_readOnly" /></td>
			<td>
				<input type="checkbox" name="control_readOnly" value="true" storage='true'/>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=40%><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_required" /></td>
			<td>
				<input type="checkbox" name="control_required" value="true" storage='true'/>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=40%><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_width" /></td>
			<td><input type='text' id='control_width' class='inputsgl' style="width:80%" storage='true'/></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=40%><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_multi" /></td>
			<td>
				<input type="checkbox" id="mulSelect"  value="true" storage='true'/>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=40%><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_orgType" /></td>
			<td>
				<input type="checkbox" name='org' value="ORG_TYPE_ORG"><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_org" /></input><br/>
				<input type="checkbox" name='org'  value="ORG_TYPE_DEPT"><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_dept" /></input><br/>
				<input type="checkbox" name='org'  value="ORG_TYPE_POST"><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_post" /></input><br/>
				<input type="checkbox" name='org'  value="ORG_TYPE_PERSON"><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_person" /></input><br/>
				<input type="checkbox" name='org'  value="ORG_TYPE_GROUP"><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_group" /></input><br/>
				
				<input type="hidden" name="orgType" storage='true'/>
				
			</td>
		</tr>
		<tr>
				<td colspan="2">
					<c:import url="/sys/xform/designer/fieldlayout/default_layout/param_style.jsp" charEncoding="UTF-8">
					</c:import>
				</td>
			</tr>
		<tr>
			<td align="center" colspan="2">
				 <%@ include file="/sys/xform/designer/fieldlayout/default_layout/common_param.jsp" %>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>
<script>
function after_load(params){
	var orgTypes=params["orgType"];
	if(orgTypes){
		$("input[name='org']").each(function(){
			if(orgTypes.indexOf(this.value)>=0){
				this.checked=true;
			}
			else{
				this.checked=false;
			}
		});
	}
}

$(function(){
	
	$("input[name='org']").click(function(){
		//
		if($(this).val()=='ORG_TYPE_GROUP'){
			var ck=this.checked;
			$("input[name='org'][value !='ORG_TYPE_GROUP']").each(function(){
				this.checked=ck;
			});
			
			$("input[name='org'][value !='ORG_TYPE_GROUP']").each(function(){
				$(this).attr("disabled",ck);
			});
		}
		if($(this).val()=='ORG_TYPE_DEPT'){
			var ck=this.checked;
			$("input[name='org'][value='ORG_TYPE_ORG']")[0].checked=ck;
			$("input[name='org'][value='ORG_TYPE_ORG']")[0].disabled=ck;
		}
		
		var vals=[];
		$("input[name='org']:checked").each(function(){
			vals.push($(this).val());
		});
		$("input[name='orgType']").val(vals.join("|"));
	});
	
});


function checkOK(){
	var control_width=$("#control_width").val();
	if(control_width&&!/^\d+%$|^\d+px$/g.test(control_width)){
		alert('<bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_widthValidate" />');
		return false;
	}
	return true;
}
</script>