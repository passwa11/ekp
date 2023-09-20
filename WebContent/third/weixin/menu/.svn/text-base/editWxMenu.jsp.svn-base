<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@page import="com.landray.kmss.util.*
				,java.util.*
				,com.landray.kmss.third.pda.service.IPdaModuleConfigMainService
				,com.landray.kmss.third.pda.model.PdaModuleConfigMain"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%
	IPdaModuleConfigMainService pdaModuleConfigMainService = (IPdaModuleConfigMainService)SpringBeanUtil.getBean("pdaModuleConfigMainService");
	List cms = pdaModuleConfigMainService.findList("fdStatus='1' and fdSubMenuType='doc'","fdOrder asc");
%>
<script type="text/javascript">
Com_IncludeFile("select.js");
Com_IncludeFile("dialog.js");
Com_IncludeFile("json2.js");

function validateParam() {
	for(var j=1;j<=3;j++){
		var mn = document.getElementsByName("fdName"+j)[0].value;
		var options = document.getElementsByName("tmp_M"+j+"Sel")[0].options;
		if(options.length>0 && mn==""){
			alert("设置了菜单选项，菜单"+j+"的名称不能为空！");
			return false;
		}
		if(options.length>5){
			alert("每个一级菜单下可创建最多 5 个二级菜单！");
			return false;
		}
	}
	return true;
}

function submitForm(method){
	if(!validateParam()){
		return;
	}
	var menu = {};
	menu["button"]=[];
	var n=0;
	for(var j=1;j<=3;j++){
		var mn = document.getElementsByName("fdName"+j)[0].value;
		var options = document.getElementsByName("tmp_M"+j+"Sel")[0].options;
		if(options.length>0 && mn!=""){
			var mb = {};
			mb["name"]=mn;
			var subms = [];
			for(var i=0; i<options.length; i++){
				var subm = {};
				subm["type"]="view";
				subm["name"]=options[i].text;
				subm["url"]=options[i].value;
				subms[i]=subm;
			}
			mb["sub_button"]=subms;
			menu["button"][n++]=mb;
		}
	}

	document.getElementsByName("fdMenuJson")[0].value=JSON.stringify(menu);
	Com_Submit(document.wxMenuForm, method);
}
</script>
<html:form action="/third/wx/menu/wxMenu.do" >
<div id="optBarDiv">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="submitForm('save');">
		<input type=button value="<bean:message bundle="third-weixin" key="third.wx.menu.btn.publish"/>"
			onclick="submitForm('publish');">
		<input type=button value="自定义菜单"
			onclick="Com_OpenWindow('<c:url value="/third/wx/menu/wxMenuDefine.do" />?method=list');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="third-weixin" key="third.wx.menu.title"/></p>

<center>
<html:hidden property="fdId"/>
<html:hidden property="fdMenuJson" />
<table class="tb_normal" width="600px">
	<tr>
		<td colspan="2">
			<table   class="tb_normal" width="100%">
				<tr>
					<td colspan="3">
					<span class="txtstrong">说明：微信菜单可创建最多 3 个一级菜单，每个一级菜单下可创建最多 5 个二级菜单</span>
					</td>
				</tr>
				<tr class="tr_normal_title">
					<td><bean:message key="dialog.optList"/></td>
					<td><bean:message bundle="third-weixin" key="third.wx.menu.menu.1"/></td>
					<td><input type="text" name="fdName1" value="${fdName1}"  style="width:90%"/>
					</td>
				</tr>
				<tr>
					<td style="width:220px"  rowspan="5">
						<select name="tmp_SrcOpt" multiple="true" size="20" style="width:220px">
							<%
								for(int i=0;i<cms.size();i++){
									PdaModuleConfigMain cm = (PdaModuleConfigMain)cms.get(i);
									out.print("<option value='"+cm.getFdSubDocLink()+"'>"+cm.getFdName()+"</option>");
								}
							%>
						</select>
					</td>
					<td>
						<center>
							<input class="btnopt" type="button" value="<bean:message key="dialog.add"/>"
								onclick="Select_AddOptions('tmp_SrcOpt', 'tmp_M1Sel');"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.delete"/>"
								onclick="Select_DelOptions('tmp_M1Sel');"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.moveUp"/>"
								onclick="Select_MoveOptions('tmp_M1Sel', -1);"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.moveDown"/>"
								onclick="Select_MoveOptions('tmp_M1Sel', 1);">
						</center>
					</td>
					<td style="width:220px">
						<select name="tmp_M1Sel" multiple="true" size="6" style="width:220px"
							ondblclick="Select_DelOptions('tmp_M1Sel');">
							${tmp_M1Sel}
						</select>
					</td>
				</tr>

				<tr class="tr_normal_title">
					<td><bean:message bundle="third-weixin" key="third.wx.menu.menu.2"/></td>
					<td><input type="text" name="fdName2" value="${fdName2}" style="width:90%"/>
					</td>
				</tr>
				<tr>
					<td>
						<center>
							<input class="btnopt" type="button" value="<bean:message key="dialog.add"/>"
								onclick="Select_AddOptions('tmp_SrcOpt', 'tmp_M2Sel');"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.delete"/>"
								onclick="Select_DelOptions('tmp_M2Sel');"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.moveUp"/>"
								onclick="Select_MoveOptions('tmp_M2Sel', -1);"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.moveDown"/>"
								onclick="Select_MoveOptions('tmp_M2Sel', 1);">
						</center>
					</td>
					<td style="width:220px">
						<select name="tmp_M2Sel" multiple="true" size="6" style="width:220px"
							ondblclick="Select_DelOptions('tmp_M2Sel');">
							${tmp_M2Sel}
						</select>
					</td>
				</tr>

				<tr class="tr_normal_title">
					<td><bean:message bundle="third-weixin" key="third.wx.menu.menu.3"/></td>
					<td><input type="text" name="fdName3" value="${fdName3}" style="width:90%"/>
					</td>
				</tr>
				<tr>
					<td>
						<center>
							<input class="btnopt" type="button" value="<bean:message key="dialog.add"/>"
								onclick="Select_AddOptions('tmp_SrcOpt', 'tmp_M3Sel');"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.delete"/>"
								onclick="Select_DelOptions('tmp_M3Sel');"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.moveUp"/>"
								onclick="Select_MoveOptions('tmp_M3Sel', -1);"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.moveDown"/>"
								onclick="Select_MoveOptions('tmp_M3Sel', 1);">
						</center>
					</td>
					<td style="width:220px">
						<select name="tmp_M3Sel" multiple="true" size="6" style="width:220px"
							ondblclick="Select_DelOptions('tmp_M3Sel');">
							${tmp_M3Sel}
						</select>
					</td>
				</tr>

			</table>
		</td>
	</tr>


</table>
</center>
<html:hidden property="method_GET"/>
</html:form>

<script>
	$KMSSValidation();
</script>

<%@ include file="/resource/jsp/edit_down.jsp"%>