<%@page import="com.landray.kmss.sys.property.custom.DynamicAttributeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/config/resource/edit_top.jsp"%>
<%@ page import="com.landray.kmss.third.ldap.*" %>
<%@ page import="com.landray.kmss.third.ldap.form.*" %>
<%@ page import="com.landray.kmss.sys.language.utils.*" %>
<%@ page import="com.landray.kmss.sys.organization.model.*" %>
<%@ page import="java.util.*" %>
<%@page import="com.landray.kmss.web.filter.ResourceCacheFilter"%>


<html>
<head>
<%
LdapConfig ldapConfig = new LdapConfig();
String omsOut = ldapConfig.getValue("kmss.oms.out.ldap.enabled");
request.setAttribute("omsOut", omsOut);
LdapSettingForm form = (LdapSettingForm)request.getAttribute("ldapSettingForm");
boolean langEnabled = SysLangUtil.isLangEnabled();
boolean langSupport = SysLangUtil.isPropertyLangSupport(SysOrgElement.class.getName(), "fdName");
request.setAttribute("langEnabled", langEnabled);
request.setAttribute("langSupport", langSupport);
String[] orgTypes = {"dept","person","post","group"};
Map<String,String> langs = new HashMap<String,String>();
String officialLang = null;
Map<String,List> ldapSettingLangMap = new HashMap();
if(langEnabled&&langSupport){
	officialLang = SysLangUtil.getOfficialLang();
	langs = SysLangUtil.getSupportedLangs();
	for(int i=0; i<orgTypes.length;i++){
		List ldapSettingLangList = new ArrayList();
	for(String lang:langs.keySet()){
		if(lang.equals(officialLang)){
			continue;
		}
		LdapSettingLang sl = new LdapSettingLang();
		sl.setLangName(langs.get(lang));
		sl.setLangCountry(lang);
		String fieldKey = "value(kmss.ldap.type."+orgTypes[i]+".prop.name"+lang+")";
		sl.setFieldKey(fieldKey);
		String value = (String)form.getValue("kmss.ldap.type."+orgTypes[i]+".prop.name"+lang);
		if(value!=null){
			sl.setFieldValue(value);
		}else{
			sl.setFieldValue("");
		}
		ldapSettingLangList.add(sl);
		
		}
	ldapSettingLangMap.put(orgTypes[i],ldapSettingLangList);
	}
	request.setAttribute("langsMap", ldapSettingLangMap);
}

List<LdapCustomProp> customPorps = LdapUtil.getLdapCustomProps(form);
request.setAttribute("customPorps", customPorps);


%>
<%
if(request.getAttribute("LUI_ContextPath")==null){
	String LUI_ContextPath = request.getContextPath();
	request.setAttribute("LUI_ContextPath", LUI_ContextPath);
	request.setAttribute("LUI_Cache",ResourceCacheFilter.cache);
}
%>
<title><bean:message bundle='third-ldap' key='ldap.system.setting'/></title>
<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/util.js"></script>
<%@ include file="/sys/ui/jsp/jshead.jsp"%> 

<script>
<%=LdapUtil.getDefaultValues()%>

var LdapType = {
	"IBM Directory Server(IDS/TDS)":{tp:"yyyyMMddHHmmss.000000",mt:"modifytimestamp",unid:"ibm-entryUUID"},
	"Active Directory":{tp:"yyyyMMddHHmmss.0",mt:"whenchanged",unid:"objectGUID",
		other_dept:"objectClass=top;objectClass=organizationalUnit",
		other_person:"objectClass=top;objectClass=person;objectClass=organizationalPerson;objectClass=user;userAccountControl=66080",
		other_post:"objectClass=top;objectClass=group",
		other_group:"objectClass=top;objectClass=group",
		group_member:"member",
		group_member_objKey:"dn",
		post_member:"member",
		post_member_objKey:"dn",
		password:"unicodePwd"},
	"SunOne":{tp:"yyyyMMddHHmmss",mt:"modifytimestamp",unid:"nsuniqueId"},
	"Novell eDirectory":{tp:"yyyyMMddHHmmss",mt:"modifytimestamp",unid:"GUID"},
	"Domino Ldap Server":{tp:"yyyyMMddHHmmss",mt:"modifytimestamp",unid:"DominoUnid"},
	"other":{tp:"yyyyMMddHHmmss",mt:"modifytimestamp",unid:"DistinguishedName"}
};

function $$(name){
	//debugger;
	var result = document.getElementsByName(name)[0];
	if(!result){
		result = document.getElementById(name);
	}
	return result;
}

function Element_GetValue(obj){
	return obj.value;
}

function _show(element){
	element.style.display="";
}
function _hide(element){
	element.style.display="none";
}

function getRadioValue(name)
{
  var temp = document.getElementsByName(name);
  for(var i=0;i<temp.length;i++)
  {
     if(temp[i].checked)
           return temp[i].value;
  }
  return "false";
} 

function _switchMode(el,ek){
	var elv = getRadioValue(el.name);
	if(elv=="false"){
		_show($$(ek));
	}else{
		_hide($$(ek));
	}
}

function getLdapType(){
	var b1= document.getElementsByName('value(kmss.ldap.config.ldap.type)');
	for (var i = 0; i < b1.length; i++) {
	   if (b1[i].checked == true) {//如果选中，下面的alert就会弹出选中的值
	      return b1[i].value;
	   }
	}
	return null;
}

function initOmsDefaultValue(el){
	va = null;
	if(el){
		va = Element_GetValue(el);
	}else{
		va = getLdapType();
	}
	LDAP_SetValue($$("value(kmss.ldap.type.dept.prop.other)"),LdapType[va]["other_dept"]);
	LDAP_SetValue($$("value(kmss.ldap.type.person.prop.other)"),LdapType[va]["other_person"]);
	LDAP_SetValue($$("value(kmss.ldap.type.post.prop.other)"),LdapType[va]["other_post"]);
	LDAP_SetValue($$("value(kmss.ldap.type.group.prop.other)"),LdapType[va]["other_group"]);
	LDAP_SetValue($$("value(kmss.ldap.type.post.prop.member)"),LdapType[va]["post_member"]);
	LDAP_SetValue($$("value(kmss.ldap.type.post.prop.member.objKey)"),LdapType[va]["post_member_objKey"]);
	LDAP_SetValue($$("value(kmss.ldap.type.group.prop.member)"),LdapType[va]["group_member"]);
	LDAP_SetValue($$("value(kmss.ldap.type.group.prop.member.objKey)"),LdapType[va]["group_member_objKey"]);
	var omsOut = '${omsOut}';
	if("true"==omsOut){
		LDAP_SetValue($$("value(kmss.ldap.type.person.prop.password)"),LdapType[va]["password"]);
	}else{
		LDAP_SetValue($$("value(kmss.ldap.type.person.prop.password)"),"");
	}
}

function _switchLdapType(el,init){
	if(init){
		return;
	}
	va = Element_GetValue(el);
	Element_SetValue($$("value(kmss.ldap.config.timePattern)"),LdapType[va]["tp"]);
	Element_SetValue($$("value(kmss.ldap.type.common.prop.modifytimestamp)"),LdapType[va]["mt"]);
	Element_SetValue($$("value(kmss.ldap.type.common.prop.unid)"),LdapType[va]["unid"]);
	initOmsDefaultValue(el);
}

function LDAP_SetValue(ele,value){
	var valueOld = Element_GetValue(ele);
	if(valueOld && valueOld!=''){
		return;
	}
	Element_SetValue(ele,value);
}

function _switchTable(el,tblName){
	var tbl = $$(tblName);
	//_clearTableValues(el,tbl,true);
	
	for(var i=1; i<tbl.rows.length; i++){
		if(el.checked){
			_show(tbl.rows[i]);
		}else{
			_hide(tbl.rows[i]);
		}
	}
	
	var omsOut = '${omsOut}';
	var tr_name = "omsOut";
	if("true"==omsOut){
		tr_name = "omsIn";
	}
	var temp = document.getElementsByName(tr_name);
  	for(var i=0;i<temp.length;i++)
  	{
     	_hide(temp[i]);
  	}
  	initOmsDefaultValue();
}

function changeUpdatePassConfig(el,tblName){
	var tbl = $$(tblName);
	if(el.checked){
		_show(tbl);
	}else{
		_hide(tbl);
	}
}

function _clearTableValues(el,tbl,def){
	if(!el.checked){
		var els = tbl.getElementsByTagName("INPUT");
		for(var i=0;i<els.length;i++){
			if(def){
				if(typeof _default[els[i].name]!="undefined"){
					Element_SetValue(els[i],_default[els[i].name]);
				}else{
					Element_SetValue(els[i],"");
				}
			}else{
				Element_SetValue(els[i],"");
			}
		}
	}
}

function _load(){
	_init();
	_switchTable($$("value(kmss.ldap.config.auth.check)"),"authZone");
	_switchTable($$("value(kmss.ldap.config.dept.check)"),"deptZone");
	_switchTable($$("value(kmss.ldap.config.person.check)"),"personZone");
	_switchTable($$("value(kmss.ldap.config.post.check)"),"postZone");
	_switchTable($$("value(kmss.ldap.config.group.check)"),"groupZone");
	_switchTable($$("value(kmss.ldap.config.mapping.check)"),"mappingZone");
	
	_switchLdapType($$("value(kmss.ldap.config.ldap.type)"),true);
	_switchMode($$("value(kmss.ldap.type.dept.prop.parent.byParentDN)"),"deptObjKey");
	_switchMode($$("value(kmss.ldap.type.person.prop.dept.byParentDN)"),"personObjKey");
	_switchMode($$("value(kmss.ldap.type.post.prop.dept.byParentDN)"),"postObjKey");

	<%
	if("auth".equals(request.getParameter("type"))){
		out.println("setTimeout('_setTimeOut()',100);");;	
	}
	%>

	changeUpdatePassConfig($$("value(kmss.ldap.config.auth.updatePass)"),"updatePass");
	
	var omsOut = '${omsOut}';
	var tr_name = "omsOut";
	if("true"==omsOut){
		tr_name = "omsIn";
	}
	var temp = document.getElementsByName(tr_name);
  	for(var i=0;i<temp.length;i++)
  	{
     	_hide(temp[i]);
  	}
}

function _setTimeOut(){
	Doc_SetCurrentLabel("Label_Tabel","6");
	$$("value(kmss.ldap.config.auth.check)").checked=true;
}

Com_AddEventListener(window, "load", _load);

function _init(){
	Element_SetValue($$("value(kmss.ldap.config.ldap.type)"),"${ldapSettingForm.map['kmss.ldap.config.ldap.type']}");
	Element_SetValue($$("value(kmss.ldap.type.dept.prop.parent.byParentDN)"),"${ldapSettingForm.map['kmss.ldap.type.dept.prop.parent.byParentDN']}");
	Element_SetValue($$("value(kmss.ldap.type.person.prop.dept.byParentDN)"),"${ldapSettingForm.map['kmss.ldap.type.person.prop.dept.byParentDN']}");
	Element_SetValue($$("value(kmss.ldap.type.post.prop.dept.byParentDN)"),"${ldapSettingForm.map['kmss.ldap.type.post.prop.dept.byParentDN']}");
}

function _submitForm(){
	if(_check()){
		_clearTableValues($$("value(kmss.ldap.config.auth.check)"),$$("authZone"));
		_clearTableValues($$("value(kmss.ldap.config.dept.check)"),$$("deptZone"));
		_clearTableValues($$("value(kmss.ldap.config.person.check)"),$$("personZone"));
		_clearTableValues($$("value(kmss.ldap.config.post.check)"),$$("postZone"));
		_clearTableValues($$("value(kmss.ldap.config.group.check)"),$$("groupZone"));

		$$("ldapSettingForm").submit();
	}
}

var LdapCheck={
	"setting":{
	"index":1,
	"value(kmss.ldap.config.url)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.config.url'/>"},
	"value(kmss.ldap.config.managerDN)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.config.managerDN'/>"},
	"value(kmss.ldap.config.password)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.config.password'/>"},
	"value(kmss.ldap.config.ldap.type)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.config.ldap.type'/>"},
	"value(kmss.ldap.config.timePattern)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.config.timePattern'/>"},
	"value(kmss.ldap.config.timeZone)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.config.timeZone'/>"},
	"value(kmss.ldap.config.fetchsize)":{label:"<bean:message bundle='third-ldap' key='ldap.synchro.fetchsize'/>"},
	"value(kmss.ldap.type.common.prop.modifytimestamp)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.common.prop.modifytimestamp'/>"}
	},
	"value(kmss.ldap.config.dept.check)":{
		"index":2,
		"value(kmss.ldap.type.dept.baseDN)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.dept.baseDN'/>"},
		"value(kmss.ldap.type.dept.prop.name)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.name'/>"},
		"value(kmss.ldap.type.dept.prop.unid)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.unid'/>"}
	},
	"value(kmss.ldap.config.person.check)":{
		"index":3,
		"value(kmss.ldap.type.person.baseDN)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.person.baseDN'/>"},
		"value(kmss.ldap.type.person.prop.name)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.name'/>"},
		"value(kmss.ldap.type.person.prop.loginName)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.loginName'/>"},
		"value(kmss.ldap.type.person.prop.unid)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.unid'/>"}
		//,"value(kmss.ldap.type.person.prop.sex)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.sex'/>"}
	},
	"value(kmss.ldap.config.post.check)":{
		"index":4,
		"value(kmss.ldap.type.post.baseDN)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.post.baseDN'/>"},
		"value(kmss.ldap.type.post.prop.name)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.name'/>"},
		"value(kmss.ldap.type.post.prop.unid)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.unid'/>"}
	},
	"value(kmss.ldap.config.group.check)":{
		"index":5,
		"value(kmss.ldap.type.group.baseDN)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.group.baseDN'/>"},
		"value(kmss.ldap.type.group.prop.name)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.name'/>"},
		"value(kmss.ldap.type.group.prop.unid)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.unid'/>"}
	},
	"value(kmss.ldap.config.auth.check)":{
		"index":6,
		"value(kmss.ldap.type.auth.baseDN)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.config.auth.baseDN'/>"},
		"value(kmss.ldap.type.auth.prop.login)":{label:"<bean:message bundle='third-ldap' key='kmss.ldap.config.auth.login'/>"}
		//"value(kmss.ldap.type.auth.trustStore)":{label:"证书路径"},
		//"value(kmss.ldap.type.auth.trustStorePassword)":{label:"证书密码"}
	},
	"value(kmss.ldap.config.auth.updatePass)":{
		"index":7
		//"value(kmss.ldap.type.auth.trustStore)":{label:"证书路径"},
		//"value(kmss.ldap.type.auth.trustStorePassword)":{label:"证书密码"}
	}
}

function _checkUnid(name){
	if(name.indexOf(".unid")>0 && Element_GetValue($$("value(kmss.ldap.type.common.prop.unid)"))!=""){
		return true;
	}
	return false;
}

function _check(){
	for(var p in LdapCheck){
		if(p=="setting"){
			for(var pp in LdapCheck[p]){
				if(pp=="index"){
					continue;
				}
				if(Element_GetValue($$(pp))==""){
					alert(_replace("<bean:message key='errors.required'/>",LdapCheck[p][pp]["label"]));
					Doc_SetCurrentLabel("Label_Tabel",LdapCheck[p]["index"]);
					$$(pp).focus();
					return false;
				}
			}
		}else if(p=="value(kmss.ldap.config.auth.updatePass)"){
			if($$(p).checked){
				for(var pp2 in LdapCheck["value(kmss.ldap.config.auth.updatePass)"]){
					if(pp2=="index"){
						continue;
					}
					if(Element_GetValue($$(pp2))==""){
						alert(_replace("<bean:message key='errors.required'/>",LdapCheck[p][pp2]["label"]));
						Doc_SetCurrentLabel("Label_Tabel",LdapCheck[p]["index"]);
						$$(pp2).focus();
						return false;
					}
				}
			}
		}else{
			if($$(p).checked){
				for(var pp in LdapCheck[p]){
					if(pp=="index"){
						continue;
					}
					if(Element_GetValue($$(pp))==""){
						if(_checkUnid(pp)){
							continue;
						}
						alert(_replace("<bean:message key='errors.required'/>",LdapCheck[p][pp]["label"]));
						Doc_SetCurrentLabel("Label_Tabel",LdapCheck[p]["index"]);
						$$(pp).focus();
						return false;
					}
				}
			}
		}
	}
	return true;
}

function _replace(msg, param1, param2, param3){
	var re;
	if(param1!=null){
		re = /\{0\}/gi;
		msg = msg.replace(re, param1);
	}
	if(param2!=null){
		re = /\{1\}/gi;
		msg = msg.replace(re, param2);
	}
	if(param3!=null){
		re = /\{2\}/gi;
		msg = msg.replace(re, param3);
	}
	return msg;
}; 

function _showSample(){
	seajs.use('sys/ui/js/dialog',function(dialog){		
		dialog.confirm('<bean:message bundle='third-ldap' key='kmss.ldap.saveshow.confirm'/>',function(flag,d){
			if(flag){
				$$("ldapSettingForm").action="<c:url value="/third/ldap/setting.do"/>?method=show";
				_submitForm();
			}
		})
	});
}

function _doMapping(){
	if(confirm("该操作会更新组织架构中的映射信息（sys_org_element表中的fd_import_info和fd_ldap_dn），请谨慎操作")){
		$$("ldapSettingForm").action="<c:url value="/third/ldap/setting.do"/>?method=doMapping";
		_submitForm();
	}
	
}

function _testValidatePass(){
	var ldapAuth = $$("value(kmss.ldap.config.auth.check)");
	if(!ldapAuth.checked){
		alert("<bean:message bundle='third-ldap' key='kmss.ldap.enbale.auth.please'/>");
		return;
	}
	seajs.use('sys/ui/js/dialog',function(dialog){		
		dialog.confirm('<bean:message bundle='third-ldap' key='kmss.ldap.validatePass.confirm'/>',function(flag,d){
			if(flag){
				$$("ldapSettingForm").action="<c:url value="/third/ldap/setting.do"/>?method=toValidatePass";
				_submitForm();
			}
		})
	});
}

function _getPInfo(){
	var s =["value(kmss.ldap.type.person.baseDN)","value(kmss.ldap.type.person.filter)","value(kmss.ldap.type.person.prop.loginName)"]; 
	var o =["value(kmss.ldap.type.auth.baseDN)","value(kmss.ldap.type.auth.filter)","value(kmss.ldap.type.auth.prop.login)"];
	for(var i = 0;i<s.length;i++){
		Element_SetValue($$(o[i]),Element_GetValue($$(s[i])));
	}
	
}

function expandMethod(imgSrc,divSrc) {
	var imgSrcObj = $$(imgSrc);
	var divSrcObj = $$(divSrc);
	if(divSrcObj.style.display!=null && divSrcObj.style.display!="") {
		divSrcObj.style.display = "";
		imgSrcObj.src = "${KMSS_Parameter_StylePath}icons/collapse.gif";
	}else{
		divSrcObj.style.display = "none";
		imgSrcObj.src = "${KMSS_Parameter_StylePath}icons/expand.gif";		
	}
}

function _person_post_check(obj){
	var elem1 = document.getElementsByName("value(kmss.ldap.type.person.prop.post)")[0];
	var elem2 = document.getElementsByName("value(kmss.ldap.type.person.prop.post.objKey)")[0];
	if(obj.checked){
		elem1.value = "";
		elem1.readOnly = true;
		elem2.selectedIndex=0;
	}else{
		elem1.readOnly = false;
	}
}
function _post_member_check(obj){
	var elem1 = document.getElementsByName("value(kmss.ldap.type.post.prop.member)")[0];
	var elem2 = document.getElementsByName("value(kmss.ldap.type.post.prop.member.objKey)")[0];
	if(obj.checked){
		elem1.value = "";
		elem1.readOnly = true;
		elem2.selectedIndex=0;
	}else{
		elem1.readOnly = false;
	}
}

function _delTimeStamp(){
	window.open('<c:url value="/third/ldap/setting.do"/>?method=deleteTimeStamp','_blank'); 
}

function _close(){
	
	window.opener=null;
	window.open('','_self','');
	window.close();
}

function changeValidateType(validateType){
	var ad = $$("value(kmss.ldap.type.auth.prop.validateByAD)");
	var ldap = $$("value(kmss.ldap.type.auth.prop.validateByOpenLdap)");
	var ldapAuth = $$(validateType);
	if(ldap.checked && ad.checked){
		alert("不能同时启用‘使用AD登录名登录’和‘使用平级LDAP产品’");
		ldapAuth.checked = false;
		return;
	}
	
}

function updateEkpMapping(orgType){
	
	var elem1 = document.getElementsByName("value(kmss.ldap.type.mapping.prop."+orgType+".ldapAttr)")[0];
	var elem2 = document.getElementsByName("value(kmss.ldap.type.mapping.prop."+orgType+".ekpAttr)")[0];
	if(elem2.value == "hierName"){
		elem1.value = "DN";
	}
	
}
</script>

	<div id="optBarDiv">
		<input type="button" class="btnopt" value="<bean:message bundle='third-ldap' key='ldap.button.save'/>" onclick="_submitForm();"/>
		<input type="button" class="btnopt" value="<bean:message bundle='third-ldap' key='ldap.button.showSample'/>" onclick="_showSample();"/>
		<input type="button" class="btnopt" value="<bean:message bundle='third-ldap' key='ldap.button.ldap.auth.test'/>" onclick="_testValidatePass();"/>
		<input type="button" class="btnopt" value="<bean:message bundle='third-ldap' key='ldap.button.delTimeStamp'/>" onclick="_delTimeStamp();" title="<bean:message bundle='third-ldap' key=''/>删除时间戳后，下次同步会执行全量同步，请谨慎操作！"/>
		
	</div>

</head>
<body>
<html:form action="/third/ldap/setting.do?method=save">
<p class="txttitle"><bean:message bundle='third-ldap' key='ldap.system.setting'/></p>
	<center>
	<table id="Label_Tabel" width=95%>

		<!-- 基本配置 -->
		<tr
			LKS_LabelName="<bean:message bundle='third-ldap' key='kmss.ldap.config.basesetting'/>">
			<td>
			<table class="tb_normal" width=100%>
				<!--基本配置 -->
				<tr><td>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" colspan=2><b><bean:message bundle='third-ldap' key='kmss.ldap.config.basesetting'/></b></td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.url'/></td>
							<td>
								<html:text property="value(kmss.ldap.config.url)" style="width:85%"/>
								<span class="txtstrong">*</span>
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.config.url.note'/></span>
								
								<img id="urlSrc" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('urlSrc','urlNote')" style="cursor:hand"><br>
								<div id="urlNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.url.tip'/>
								</div>
								
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.managerDN'/></td>
							<td>
								<html:text property="value(kmss.ldap.config.managerDN)" style="width:85%"/>
								<span class="txtstrong">*</span>

								<img id="managerDNImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('managerDNImg','managerDNNote')" style="cursor:hand"><br>
								<div id="managerDNNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.managerDN.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.password'/></td>
							<td>
								<html:password property="value(kmss.ldap.config.password)" styleClass="inputsgl" style="width:85%"/>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.ldap.type'/></td>
							<td>
								<script>
									var typeHTML = "";
									for(var p in LdapType){
										typeHTML+="<div><input type='radio' name='value(kmss.ldap.config.ldap.type)' value='"+p+"' onclick='_switchLdapType(this);'>"+p+"</div>";
									}
									document.write(typeHTML);
								</script>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.timePattern'/></td>
							<td>
								<html:text property="value(kmss.ldap.config.timePattern)" style="width:85%"/>
								<span class="txtstrong">*</span>

								<img id="timePatternImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('timePatternImg','timePatternNote')" style="cursor:hand"><br>
								<div id="timePatternNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.timePattern.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.timeZone'/></td>
							<td>
								<html:text property="value(kmss.ldap.config.timeZone)" style="width:85%"/>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.showCount'/></td>
							<td>
								<html:text property="value(kmss.ldap.config.showCount)" style="width:85%"/><span class="txtstrong">*</span>

								<img id="showCountImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('showCountImg','showCountNote')" style="cursor:hand"><br>
								<div id="showCountNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.showCount.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.batchReadCount'/></td>
							<td>
								<html:text property="value(kmss.ldap.config.fetchsize)" style="width:85%"/><span class="txtstrong">*</span>

								<img id="fetchCountImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('fetchCountImg','fetchCountNote')" style="cursor:hand"><br>
								<div id="fetchCountNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.batchReadCount.tip'/>
								</div>

							</td>
						</tr>
					</table>
					<p>				
				
					<!--全局属性 -->
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" colspan=2><b><bean:message bundle='third-ldap' key='kmss.ldap.config.global'/></b></td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.common.prop.modifytimestamp'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.common.prop.modifytimestamp)" style="width:85%"/>
								<span class="txtstrong">*</span>

								<img id="modifytimestampImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('modifytimestampImg','modifytimestampNote')" style="cursor:hand"><br>
								<div id="modifytimestampNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.modifytimestamp.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.common.prop.unid'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.common.prop.unid)" style="width:85%"/>

								<img id="unidImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('unidImg','unidNote')" style="cursor:hand"><br>
								<div id="unidNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.unid.tip'/>
								</div>

							</td>
						</tr>
						
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='ldap.config.synchronization.strategies'/></td>
							<td>
								<sunbor:enums property="value(kmss.ldap.type.common.prop.syncStrategy)" enumsType="ldap_sync_strategy" elementType="radio"  htmlElementProperties="onchange=\"updateEkpMapping('post');\""
									bundle="third-ldap" isEmptyAllow="false"/>
								<br>
								<span style="color:red">
									<bean:message bundle='third-ldap' key='ldap.config.synchronization.tip'/>
								</span>
							</td>
						</tr>			
					</table>

				</td></tr>
			</table>
			</td>
		</tr>


		<!--  部门信息 -->
		<tr
			LKS_LabelName="<bean:message bundle='third-ldap' key='kmss.ldap.config.dept'/>">
			<td>
			<table class="tb_normal" width=100%>
				<!--基本配置 -->
				<tr><td>
					<table id="deptZone" class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" colspan=2><b>
							<label>
								<html:checkbox property="value(kmss.ldap.config.dept.check)" value="true" onclick="_switchTable(this,'deptZone');"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.config.dept.check'/>
							</label>
							</b></td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.baseDN'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.dept.baseDN)" style="width:85%"/>
								<span class="txtstrong">*</span>

								<img id="deptImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('deptImg','deptNote')" style="cursor:hand"><br>
								<div id="deptNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.dept.baseDN.tip'/>
								</div>

							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.basednisroot'/></td>
							<td>
								<html:checkbox property="value(kmss.ldap.type.dept.basednisroot)" value="true" /> 
							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.filter'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.dept.filter)" style="width:85%"/>

								<img id="deptfilterImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('deptfilterImg','deptfilterNote')" style="cursor:hand"><br>
								<div id="deptfilterNote">
									<bean:message bundle='third-ldap' key='ldap.config.dept.filter.tip1'/></br>
									<font color="red"><bean:message bundle='third-ldap' key='ldap.config.dept.filter.tip2'/></font>
								</div>
							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.filter'/>(<bean:message bundle='third-ldap' key='ldap.config.invalid.record'/>)</td>
							<td>
								<html:text property="value(kmss.ldap.type.dept.filterDel)" style="width:85%"/>
								<div>
									<font color="red"><bean:message bundle='third-ldap' key='ldap.config.invalid.department'/></font>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.name'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.dept.prop.name)" style="width:85%"/>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						<c:if test="${langEnabled && langSupport }">
							<c:forEach items="${langsMap.dept }" var="lang">
								<tr>
									<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.name'/>(${lang.langName })</td>
									<td>
										<input class="inputsgl" type="text" name="${lang.fieldKey }" style="width:85%" value="${ lang.fieldValue}"/>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.number'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.dept.prop.number)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.order'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.dept.prop.order)" style="width:85%"/>
								<br>
									<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.order.note'/></span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.keyword'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.dept.prop.keyword)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.memo'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.dept.prop.memo)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.parent'/></td>
							<td>
								<html:hidden property="value(kmss.ldap.type.dept.prop.parent.type)"/>
								<input type="radio" name="value(kmss.ldap.type.dept.prop.parent.byParentDN)" value="true" onclick="_switchMode(this,'deptObjKey');"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.parent.type.dn'/>
								<input type="radio" name="value(kmss.ldap.type.dept.prop.parent.byParentDN)" value="false" onclick="_switchMode(this,'deptObjKey');"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.parent.type.key'/>
								<input type="radio" name="value(kmss.ldap.type.dept.prop.parent.byParentDN)" value="" onclick="_switchMode(this,'deptObjKey');"><bean:message bundle='third-ldap' key='ldap.config.dept.byParentDN.noSetting'/>
								<div id="deptObjKey">
								<bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.parent.propName'/>
								<html:text property="value(kmss.ldap.type.dept.prop.parent)" style="width:30%"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.parent.objKey'/>
								<sunbor:enums property="value(kmss.ldap.type.dept.prop.parent.objKey)" enumsType="ldap_objKey" elementType="select" bundle="third-ldap" isEmptyAllow="true"/>
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.parent.note'/></span>
								</div>


								<img id="deptparentImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('deptparentImg','deptparentNote')" style="cursor:hand"><br>
								<div id="deptparentNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.dept.byParentDN.dn.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.thisleader'/></td>
							<td>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.thisleader.propName'/>
								<html:text property="value(kmss.ldap.type.dept.prop.thisleader)" style="width:30%"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.thisleader.objKey'/>
								<sunbor:enums property="value(kmss.ldap.type.dept.prop.thisleader.objKey)" enumsType="ldap_objKey" elementType="select" bundle="third-ldap" isEmptyAllow="true"/>

								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.thisleader.note'/></span>

								<img id="deptthisleaderImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('deptthisleaderImg','deptthisleaderNote')" style="cursor:hand"><br>
								<div id="deptthisleaderNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.dept.byParentDN.key.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.superleader'/></td>
							<td>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.superleader.propName'/>
								<html:text property="value(kmss.ldap.type.dept.prop.superleader)" style="width:30%"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.superleader.objKey'/>
								<sunbor:enums property="value(kmss.ldap.type.dept.prop.superleader.objKey)" enumsType="ldap_objKey" elementType="select" bundle="third-ldap" isEmptyAllow="true"/>

								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.superleader.note'/></span>

								<img id="deptsuperleaderImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('deptsuperleaderImg','deptsuperleaderNote')" style="cursor:hand"><br>
								<div id="deptsuperleaderNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.dept.superleader.key.tip'/>
								</div>

							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.unid'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.dept.prop.unid)" style="width:85%"/>
								
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.dept.prop.unid.note'/></span>
								
								<img id="deptunidImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('deptunidImg','deptunidNote')" style="cursor:hand"><br>
								<div id="deptunidNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.dept.unid.tip'/>
								</div>
								
							</td>
						</tr>
						<tr name="omsOut">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.prop.other'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.dept.prop.other)" style="width:85%"/>
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.prop.other.note'/></span>
							</td>
						</tr>
					</table>

				</td></tr>
			</table>
			</td>
		</tr>

		<!--  员工信息 -->
		<tr
			LKS_LabelName="<bean:message bundle='third-ldap' key='kmss.ldap.config.person'/>">
			<td>
			<table class="tb_normal" width=100%>
				<!--基本配置 -->
				<tr><td>
					<table id="personZone" class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" colspan=2><b>
							<label>
								<html:checkbox property="value(kmss.ldap.config.person.check)" value="true" onclick="_switchTable(this,'personZone');"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.config.person.check'/>
							</label>
							</b></td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.baseDN'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.baseDN)" style="width:85%"/>
								<span class="txtstrong">*</span>

								<img id="personbaseDNImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('personbaseDNImg','personbaseDNNote')" style="cursor:hand"><br>
								<div id="personbaseDNNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.person.baseDN.tip'/>
								</div>

							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.filter'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.filter)" style="width:85%"/>

								<img id="personfilterImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('personfilterImg','personfilterNote')" style="cursor:hand"><br>
								<div id="personfilterNote" >
									<bean:message bundle='third-ldap' key='ldap.config.person.filter.tip1'/></br>
									<font color="red"><bean:message bundle='third-ldap' key='ldap.config.person.filter.tip2'/></font>
									<br>
									<bean:message bundle='third-ldap' key='ldap.config.person.filter.tip3'/>
									<a href="userAccountControl_help.html" target="_blank"><font color="blue"><bean:message bundle='third-ldap' key='ldap.config.person.filter.tip4'/></font></a>
								</div>

							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.filter'/>(<bean:message bundle='third-ldap' key='ldap.config.invalid.record'/>)</td>
							<td>
								<html:text property="value(kmss.ldap.type.person.filterDel)" style="width:85%"/>
								<div>
									<font color="red"><bean:message bundle='third-ldap' key='ldap.config.invalid.employee'/></font>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.name'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.name)" style="width:85%"/>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						<c:if test="${langEnabled && langSupport }">
							<c:forEach items="${langsMap.person }" var="lang">
								<tr>
									<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.name'/>(${lang.langName })</td>
									<td>
										<input class="inputsgl" type="text" name="${lang.fieldKey }" style="width:85%" value="${ lang.fieldValue}"/>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.number'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.number)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.order'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.order)" style="width:85%"/>
								<br>
									<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.order.note'/></span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.mail'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.mail)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.mobileNo'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.mobileNo)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.workPhone'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.workPhone)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.loginName'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.loginName)" style="width:85%"/>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.password'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.password)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.lang'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.lang)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.keyword'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.keyword)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.rtx'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.rtx)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.scard'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.scard)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.memo'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.memo)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.sex'/></td>
							<td>
								<div><html:text property="value(kmss.ldap.type.person.prop.sex)" style="width:85%"/></div>
								<div><bean:message bundle='third-ldap' key='ldap.config.person.sex.M'/><html:text property="value(kmss.ldap.config.sex.m)" style="width:50%"/><bean:message bundle='third-ldap' key='ldap.config.person.sex.M.tip'/></div>
								<div><bean:message bundle='third-ldap' key='ldap.config.person.sex.F'/><html:text property="value(kmss.ldap.config.sex.f)" style="width:50%"/><bean:message bundle='third-ldap' key='ldap.config.person.sex.F.tip'/></div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.wechat'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.wechat)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.shortNo'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.shortNo)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.dept'/></td>
							<td>
								<html:hidden property="value(kmss.ldap.type.person.prop.dept.type)"/>
								<input type="radio" name="value(kmss.ldap.type.person.prop.dept.byParentDN)" value="true" onclick="_switchMode(this,'personObjKey');"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.dept.type.dn'/>
								<input type="radio" name="value(kmss.ldap.type.person.prop.dept.byParentDN)" value="false" onclick="_switchMode(this,'personObjKey');"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.dept.type.key'/>
								<input type="radio" name="value(kmss.ldap.type.person.prop.dept.byParentDN)" value="" onclick="_switchMode(this,'personObjKey');"><bean:message bundle='third-ldap' key='ldap.config.person.byParentDN.noSetting'/>
								<div id="personObjKey">
								<bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.dept.propName'/>
								<html:text property="value(kmss.ldap.type.person.prop.dept)" style="width:30%"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.dept.objKey'/>
								<sunbor:enums property="value(kmss.ldap.type.person.prop.dept.objKey)" enumsType="ldap_objKey" elementType="select" bundle="third-ldap" isEmptyAllow="true"/>
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.dept.note'/></span>

								<img id="persondeptImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('persondeptImg','persondeptNote')" style="cursor:hand"><br>
								<div id="persondeptNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.person.byParentDN.dn.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.post'/></td>
							<td>
								<html:checkbox property="value(kmss.ldap.type.person.prop.post.check)" onclick="_person_post_check(this)" /><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.post.check'/>
								<br>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.post.propName'/>
								<html:text property="value(kmss.ldap.type.person.prop.post)" style="width:30%"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.post.objKey'/>
								<sunbor:enums property="value(kmss.ldap.type.person.prop.post.objKey)" enumsType="ldap_objKey" elementType="select" bundle="third-ldap" isEmptyAllow="true"/>

								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.post.note'/></span>

								<img id="personpostImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('personpostImg','personpostNote')" style="cursor:hand"><br>
								<div id="personpostNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.person.byParentDN.key.tip'/>
								</div>

							</td>
						</tr>
						<c:forEach items="${customPorps }" var="customProp">
								<tr>
									<td class="td_normal_title" width="15%">${customProp.fieldDisplayName }</td>
									<td>
										<input class="inputsgl" type="text" name="${customProp.fieldKey }" style="width:85%" value="${ customProp.fieldValue}"/>
									</td>
								</tr>
						</c:forEach>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.unid'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.unid)" style="width:85%"/>
								
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.person.prop.unid.note'/></span>

								<img id="personunidImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('personunidImg','personunidNote')" style="cursor:hand"><br>
								<div id="personunidNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.person.unid.tip'/>
								</div>

							</td>
						</tr>
						<tr name="omsOut">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.prop.other'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.person.prop.other)" style="width:85%"/>
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.prop.other.note'/></span>
							</td>
						</tr>
					</table>

				</td></tr>
			</table>
			</td>
		</tr>

		<!--  岗位信息 -->
		<tr
			LKS_LabelName="<bean:message bundle='third-ldap' key='kmss.ldap.config.post'/>">
			<td>
			<table class="tb_normal" width=100%>
				<tr><td>
					<table id="postZone" class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" colspan=2><b>
							<label>
								<html:checkbox property="value(kmss.ldap.config.post.check)" value="true" onclick="_switchTable(this,'postZone');"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.config.post.check'/>
							</label>
							</b></td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.baseDN'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.post.baseDN)" style="width:85%"/>
								<span class="txtstrong">*</span>
								
								<img id="postbaseDNImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('postbaseDNImg','postbaseDNNote')" style="cursor:hand"><br>
								<div id="postbaseDNNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.post.baseDN.tip'/>
								</div>

							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.filter'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.post.filter)" style="width:85%"/>

								<img id="postfilterImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('postfilterImg','postfilterNote')" style="cursor:hand"><br>
								<div id="postfilterNote" >
									<bean:message bundle='third-ldap' key='ldap.config.post.filter.tip1'/></br>
									<font color="red"><bean:message bundle='third-ldap' key='ldap.config.post.filter.tip2'/></font>
								</div>

							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.filter'/>(<bean:message bundle='third-ldap' key='ldap.config.invalid.record'/>)</td>
							<td>
								<html:text property="value(kmss.ldap.type.post.filterDel)" style="width:85%"/>
								<div>
									<font color="red"><bean:message bundle='third-ldap' key='ldap.config.invalid.post'/></font>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.name'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.post.prop.name)" style="width:85%"/>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						<c:if test="${langEnabled && langSupport }">
							<c:forEach items="${langsMap.post }" var="lang">
								<tr>
									<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.name'/>(${lang.langName })</td>
									<td>
										<input class="inputsgl" type="text" name="${lang.fieldKey }" style="width:85%" value="${ lang.fieldValue}"/>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.number'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.post.prop.number)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.order'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.post.prop.order)" style="width:85%"/>
								<br>
									<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.order.note'/></span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.keyword'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.post.prop.keyword)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.memo'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.post.prop.memo)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.dept'/></td>
							<td>
								<html:hidden property="value(kmss.ldap.type.post.prop.dept.type)"/>
								<input type="radio" name="value(kmss.ldap.type.post.prop.dept.byParentDN)" value="true" onclick="_switchMode(this,'postObjKey');"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.dept.type.dn'/>
								<input type="radio" name="value(kmss.ldap.type.post.prop.dept.byParentDN)" value="false" onclick="_switchMode(this,'postObjKey');"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.dept.type.key'/>
								<input type="radio" name="value(kmss.ldap.type.post.prop.dept.byParentDN)" value="" onclick="_switchMode(this,'postObjKey');"><bean:message bundle='third-ldap' key='ldap.config.post.byParentDN.noSetting'/>
								<div id="postObjKey">
								<bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.dept.propName'/>
								<html:text property="value(kmss.ldap.type.post.prop.dept)" style="width:30%"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.dept.objKey'/>
								<sunbor:enums property="value(kmss.ldap.type.post.prop.dept.objKey)" enumsType="ldap_objKey" elementType="select" bundle="third-ldap" isEmptyAllow="true"/>

								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.dept.note'/></span>
								</div>

								<img id="postdeptImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('postdeptImg','postdeptNote')" style="cursor:hand"><br>
								<div id="postdeptNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.post.byParentDN.dn.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.member'/></td>
							<td>
								<html:checkbox property="value(kmss.ldap.type.post.prop.member.check)" onclick="_post_member_check(this)" /><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.member.check'/>
								<br>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.member.propName'/>
								<html:text property="value(kmss.ldap.type.post.prop.member)" style="width:30%"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.member.objKey'/>
								<sunbor:enums property="value(kmss.ldap.type.post.prop.member.objKey)" enumsType="ldap_objKey" elementType="select" bundle="third-ldap" isEmptyAllow="true"/>

								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.member.note'/></span>

								<img id="postmemberImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('postmemberImg','postmemberNote')" style="cursor:hand"><br>
								<div id="postmemberNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.post.byParentDN.key.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.thisleader'/></td>
							<td>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.thisleader.propName'/>
								<html:text property="value(kmss.ldap.type.post.prop.thisleader)" style="width:30%"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.thisleader.objKey'/>
								<sunbor:enums property="value(kmss.ldap.type.post.prop.thisleader.objKey)" enumsType="ldap_objKey" elementType="select" bundle="third-ldap" isEmptyAllow="true"/>

								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.thisleader.note'/></span>

								<img id="postthisleaderImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('postthisleaderImg','postthisleaderNote')" style="cursor:hand"><br>
								<div id="postthisleaderNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.post.thisleader.tip'/>
								</div>

							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.unid'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.post.prop.unid)" style="width:85%"/>
								
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.post.prop.unid.note'/></span>
								
								<img id="postunidImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('postunidImg','postunidNote')" style="cursor:hand"><br>
								<div id="postunidNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.post.unid.tip'/>
								</div>
								
							</td>
						</tr>
						<tr name="omsOut">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.prop.other'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.post.prop.other)" style="width:85%"/>
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.prop.other.note'/></span>
							</td>
						</tr>
					</table>

				</td></tr>
			</table>
			</td>
		</tr>

		<!--  常用群组信息 -->
		<tr
			LKS_LabelName="<bean:message bundle='third-ldap' key='kmss.ldap.config.group'/>">
			<td>
			<table class="tb_normal" width=100%>
				<tr><td>
					<table id="groupZone" class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" colspan=2><b>
							<label>
								<html:checkbox property="value(kmss.ldap.config.group.check)" value="true" onclick="_switchTable(this,'groupZone');"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.config.group.check'/>
							</label>
							</b></td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.baseDN'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.group.baseDN)" style="width:85%"/>
								<span class="txtstrong">*</span>

								<img id="groupbaseDNImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('groupbaseDNImg','groupbaseDNNote')" style="cursor:hand"><br>
								<div id="groupbaseDNNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.group.baseDN.tip'/>
								</div>

							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.filter'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.group.filter)" style="width:85%"/>

								<img id="groupfilterImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('groupfilterImg','groupfilterNote')" style="cursor:hand"><br>
								<div id="groupfilterNote" >
									<bean:message bundle='third-ldap' key='ldap.config.group.filter.tip1'/></br>
									<font color="red"><bean:message bundle='third-ldap' key='ldap.config.group.filter.tip2'/></font>
								</div>
							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.filter'/>(<bean:message bundle='third-ldap' key='ldap.config.invalid.record'/>)</td>
							<td>
								<html:text property="value(kmss.ldap.type.group.filterDel)" style="width:85%"/>
								<div>
									<font color="red"><bean:message bundle='third-ldap' key='ldap.config.invalid.group'/></font>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.name'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.group.prop.name)" style="width:85%"/>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						<c:if test="${langEnabled && langSupport }">
							<c:forEach items="${langsMap.group }" var="lang">
								<tr>
									<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.name'/>(${lang.langName })</td>
									<td>
										<input class="inputsgl" type="text" name="${lang.fieldKey }" style="width:85%" value="${ lang.fieldValue}"/>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.number'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.group.prop.number)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.order'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.group.prop.order)" style="width:85%"/>
								<br>
									<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.order.note'/></span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.keyword'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.group.prop.keyword)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.memo'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.group.prop.memo)" style="width:85%"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.member'/></td>
							<td>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.member.propName'/>
								<html:text property="value(kmss.ldap.type.group.prop.member)" style="width:30%"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.member.objKey'/>
								<sunbor:enums property="value(kmss.ldap.type.group.prop.member.objKey)" enumsType="ldap_objKey" elementType="select" bundle="third-ldap" isEmptyAllow="true"/>

								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.member.note'/></span>

								<img id="groupmemberImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('groupmemberImg','groupmemberNote')" style="cursor:hand"><br>
								<div id="groupmemberNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.group.member.tip'/>
								</div>

							</td>
						</tr>
						<tr name="omsIn">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.unid'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.group.prop.unid)" style="width:85%"/>
								
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.group.prop.unid.note'/></span>

								<img id="groupunidImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('groupunidImg','groupunidNote')" style="cursor:hand"><br>
								<div id="groupunidNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.group.unid.tip'/>
								</div>

							</td>
						</tr>
						<tr name="omsOut">
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.type.prop.other'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.group.prop.other)" style="width:85%"/>
								<br>
								<span class="txtstrong"><bean:message bundle='third-ldap' key='kmss.ldap.type.prop.other.note'/></span>
							</td>
						</tr>
					</table>

				</td></tr>
			</table>
			</td>
		</tr>

		<!--  LDAP登录验证 -->
		<tr
			LKS_LabelName="<bean:message bundle='third-ldap' key='kmss.ldap.config.auth.label'/>">
			<td>
			<table class="tb_normal" width=100%>
				<tr><td>
					<!--员工信息 -->
					<table id="authZone" class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" colspan=2><b>
							<label>
								<html:checkbox property="value(kmss.ldap.config.auth.check)" value="true" onclick="_switchTable(this,'authZone');"/>
								<bean:message bundle='third-ldap' key='kmss.ldap.config.auth.label'/>
							</label>
							</b>
							<span>(<bean:message bundle='third-ldap' key='kmss.ldap.config.auth.label.note'/>)</span>
							<a href="#"
						onclick="_getPInfo();">【<bean:message bundle='third-ldap' key='kmss.ldap.config.auth.button.get'/>】</a>
							</td>
						</tr>

						<!--tr>
							<td class="td_normal_title" colspan=2><b><bean:message bundle='third-ldap' key='kmss.ldap.config.person'/></b></td>
						</tr-->
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.auth.baseDN'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.auth.baseDN)" style="width:85%"/>
								<span class="txtstrong">*</span>

								<img id="authbaseDNImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('authbaseDNImg','authbaseDNNote')" style="cursor:hand"><br>
								<div id="authbaseDNNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.auth.baseDN.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.auth.filter'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.auth.filter)" style="width:85%"/>

								<img id="authfilterImg" src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
									onclick="expandMethod('authfilterImg','authfilterNote')" style="cursor:hand"><br>
								<div id="authfilterNote"  style="display:none">
									<bean:message bundle='third-ldap' key='ldap.config.auth.filter.tip'/>
								</div>

							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='kmss.ldap.config.auth.login'/></td>
							<td>
								<html:text property="value(kmss.ldap.type.auth.prop.login)" style="width:85%"/>
								<span class="txtstrong">*</span>
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='ldap.config.auth.validate.AD'/></td>
							<td>
							<html:checkbox property="value(kmss.ldap.type.auth.prop.validateByAD)" value="true"  onchange="changeValidateType('value(kmss.ldap.type.auth.prop.validateByAD)');"/>
								<bean:message bundle='third-ldap' key='ldap.config.auth.AD.enable'/>
								<div>
								<bean:message bundle='third-ldap' key='ldap.config.auth.loginSuffix'/><html:text property="value(kmss.ldap.type.auth.loginSuffix)" style="width:85%"/>
								<br>
								<bean:message bundle='third-ldap' key='ldap.config.auth.AD.tip'/><br>
								
								<a href="ad_validate_help.png" target="_blank"><font color="red">
								<bean:message bundle='third-ldap' key='ldap.config.auth.validate.AD.help'/></font>
								</a>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='ldap.config.auth.validate.openLdap'/></td>
							<td>
							<html:checkbox property="value(kmss.ldap.type.auth.prop.validateByOpenLdap)" value="true" onchange="changeValidateType('value(kmss.ldap.type.auth.prop.validateByOpenLdap)');"/>
								<bean:message bundle='third-ldap' key='ldap.config.auth.AD.enable'/>
								<div>
									<bean:message bundle='third-ldap' key='ldap.config.auth.validate.openLdap.tip'/>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='ldap.config.auth.synchroPass'/></td>
							<td>
							<html:checkbox property="value(kmss.ldap.config.auth.updatePass)" value="true" onclick="changeUpdatePassConfig(this,'updatePass');"/>
								<bean:message bundle='third-ldap' key='ldap.config.auth.synchroPass.enable'/>
								<div id="updatePass">
								<bean:message bundle='third-ldap' key='ldap.config.auth.trustStore'/><html:text property="value(kmss.ldap.type.auth.trustStore)" style="width:85%"/>
								<br>
								<bean:message bundle='third-ldap' key='ldap.config.auth.trustStorePassword'/><html:text property="value(kmss.ldap.type.auth.trustStorePassword)" style="width:85%"/>
								</div>
								<br><bean:message bundle='third-ldap' key='ldap.config.auth.synchroPass.tip'/><br>
								<bean:message bundle='third-ldap' key='ldap.config.auth.synchroPass.tip1'/><br>
								<bean:message bundle='third-ldap' key='ldap.config.auth.synchroPass.tip2'/><br>
								<bean:message bundle='third-ldap' key='ldap.config.auth.synchroPass.tip3'/><br>
								<bean:message bundle='third-ldap' key='ldap.config.auth.synchroPass.tip4'/><br>
								<bean:message bundle='third-ldap' key='ldap.config.auth.synchroPass.tip5'/>
							</td>
						</tr>
					</table>
				</td></tr>
			</table>
			</td>
		</tr>



<!--  初始化映射 -->
		<tr
			LKS_LabelName="<bean:message bundle='third-ldap' key='ldap.mapping.init'/>">
			<td>
			<table class="tb_normal" width=100%>
				<tr><td>
					<!--员工信息 -->
					<table id="mappingZone" class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" colspan=2><b>
							<label>
								<html:checkbox property="value(kmss.ldap.config.mapping.check)" value="true" onclick="_switchTable(this,'mappingZone');"/>
								<bean:message bundle='third-ldap' key='ldap.mapping.init'/>
							</label>
							
							</td>
						</tr>

						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='ldap.mapping.dept'/></td>
							<td>
								<bean:message bundle='third-ldap' key='ldap.mapping.ldap.dept.attribute'/><html:text property="value(kmss.ldap.type.mapping.prop.dept.ldapAttr)" style="width:25%"/><bean:message bundle='third-ldap' key='ldap.mapping.ldap.value'/>
								
								<bean:message bundle='third-ldap' key='ldap.mapping.ekp.dept'/><sunbor:enums property="value(kmss.ldap.type.mapping.prop.dept.ekpAttr)" enumsType="ldap_mapping_dept" elementType="select"  htmlElementProperties="onchange=\"updateEkpMapping('dept');\""
									bundle="third-ldap" isEmptyAllow="true"/><bean:message bundle='third-ldap' key='ldap.mapping.value'/>

							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='ldap.mapping.employee'/></td>
							<td>
								<bean:message bundle='third-ldap' key='ldap.mapping.ldap.employee.attribute'/><html:text property="value(kmss.ldap.type.mapping.prop.person.ldapAttr)" style="width:25%"/><bean:message bundle='third-ldap' key='ldap.mapping.ldap.value'/>
								
								<bean:message bundle='third-ldap' key='ldap.mapping.ekp.employee'/><sunbor:enums property="value(kmss.ldap.type.mapping.prop.person.ekpAttr)" enumsType="ldap_mapping_person" elementType="select" 
									bundle="third-ldap" isEmptyAllow="true"/><bean:message bundle='third-ldap' key='ldap.mapping.value'/>

							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='ldap.mapping.post'/></td>
							<td>
								<bean:message bundle='third-ldap' key='ldap.mapping.ldap.post.attribute'/><html:text property="value(kmss.ldap.type.mapping.prop.post.ldapAttr)" style="width:25%"/><bean:message bundle='third-ldap' key='ldap.mapping.ldap.value'/>
								
								<bean:message bundle='third-ldap' key='ldap.mapping.ekp.post'/><sunbor:enums property="value(kmss.ldap.type.mapping.prop.post.ekpAttr)" enumsType="ldap_mapping_dept" elementType="select"  htmlElementProperties="onchange=\"updateEkpMapping('post');\""
									bundle="third-ldap" isEmptyAllow="true"/><bean:message bundle='third-ldap' key='ldap.mapping.value'/>

							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width="15%"><bean:message bundle='third-ldap' key='ldap.mapping.common.group'/></td>
							<td>
								<bean:message bundle='third-ldap' key='ldap.mapping.ldap.group.attribute'/><html:text property="value(kmss.ldap.type.mapping.prop.group.ldapAttr)" style="width:25%"/><bean:message bundle='third-ldap' key='ldap.mapping.ldap.value'/>
								
								<bean:message bundle='third-ldap' key='ldap.mapping.ekp.group'/><sunbor:enums property="value(kmss.ldap.type.mapping.prop.group.ekpAttr)" enumsType="ldap_mapping_group" elementType="select"
									bundle="third-ldap" isEmptyAllow="true"/><bean:message bundle='third-ldap' key='ldap.mapping.value'/>

							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" colspan="2">
								<span style="color:red">
								<bean:message bundle='third-ldap' key='ldap.mapping.tip'/>
								</span>
								<br>
								<input type="button" class="btnopt" value="<bean:message bundle='third-ldap' key='ldap.mapping.save'/>" onclick="_doMapping();"/>
							</td>
						</tr>
					</table>

				</td></tr>
			</table>
			</td>
		</tr>
		
	</table>
	</center>
</html:form>
</body>
</html>
