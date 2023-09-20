<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ page
	import="com.landray.kmss.util.SpringBeanUtil,
                java.util.List,
                com.landray.kmss.sys.authentication.identity.plugin.IdentityPlugin,
                com.landray.kmss.sys.authentication.identity.plugin.IdentityValidatePluginData,
                java.util.Map,
                java.util.TreeMap"%>
<%@ include file="/resource/jsp/common.jsp"%>
 <%
       List<IdentityValidatePluginData> identityList = IdentityPlugin.getExtensionListBySwitch();
		Map<String, String> returnMap = new TreeMap<String, String>();
		for(IdentityValidatePluginData data : identityList) {
			String key = String.valueOf(data.getKey());
			String value =data.getName();
			returnMap.put(key, value);
		}
		request.setAttribute("validateTypeMap",returnMap);
%>           
<tr LKS_LabelName="${ lfn:message('sys-mobile:sysMobile.reviewNode.security') }">
	<td>
		<table width="100%" class="tb_normal">
			<tr >
		 		<td width="100px"><bean:message bundle="sys-mobile" key="sysMobile.reviewNode.security" /></td>
		 		<td id="td1">
		 			<!-- 指纹审批 -->
		 			<label><input type="checkbox" value="true" name="ext_fingerPrintReview" onclick="checkUnique(this);"></label><bean:message bundle="sys-mobile" key="sysMobile.reviewNode.fingerprint.approval" />&nbsp;&nbsp;
		 			<!-- 刷脸审批 -->
		 			<label><input type="checkbox" value="true" name="ext_facePrintReview" onclick="checkUnique(this);"></label><bean:message bundle="sys-mobile" key="sysMobile.reviewNode.faceprint.approval" />
		 			<br/>
		 			<div class="txtstrong">
		 				<bean:message bundle="sys-mobile" key="sysMobile.reviewNode.takeCare" /><br/>
							<bean:message bundle="sys-mobile" key="sysMobile.reviewNode.takeCare.detail1" /><br/>
							<bean:message bundle="sys-mobile" key="sysMobile.reviewNode.takeCare.detail2" />
		 			</div>
		 		</td>
			</tr>
			<tr > 
		 		<td width="100px"><bean:message bundle="sys-authentication-identity" key="identity.authenVerify" /></td>
		 		<td id="td2">
		 			<c:forEach items="${validateTypeMap}" var="mymap">
		 				<label><input type="checkbox" value="true" name="ext_identity_${mymap.key}" onclick="checkUnique(this);"></label>${mymap.value}&nbsp;&nbsp;
					</c:forEach>
		 			<br/>
		 			<div class="txtstrong">
		 				
		 			</div>
		 		</td>
			</tr>
		</table>
	</td>
</tr>
<script type="text/javascript">
	function checkUnique(ext){
		var name=$(ext).attr("name");
		if(name.indexOf('ext_identity_')>-1){//身份校验，要把上面的勾选去掉
			if($("#td2 input[type=checkbox]").size()>0){
				$("#td1 input[type=checkbox]").attr("checked",false);
			}
		}else{
			if($(ext).prop("checked")){
				$("input[name='ext_fingerPrintReview']").prop("checked",false);
				$("input[name='ext_facePrintReview']").prop("checked",false);
				$(ext).prop("checked",true);
			}
			if($("#td1 input[type=checkbox]").size()>0){
				$("#td2 input[type=checkbox]").attr("checked",false);
			}
		}
	}
	
</script>