<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.view">
	<template:replace name="head">
		<style>
		body {
			overflow:hidden;
		}
		</style>
	</template:replace>
	<template:replace name="content">
		<br/>
		<html:form action="/sys/profile/sysCommonSensitiveConfig.do" method="POST">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.xformRelevance')}<br><font color="red">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.xformRelevance.note')}</font></td>
					<td width="85%">
						<ui:switch property="value(xformRelevanceEnable)" checked="${sysCommonSensitiveConfig.xformRelevanceEnable}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						<br/>
						<span class="message">
							${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.xformRelevance.switchNote') }
						</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-profile" key="sys.profile.org.passwordSecurityConfig.specialSearchEnable"/>
					</td>
					<td width="85%">
						<ui:switch property="value(specialSearchEnable)" checked="${sysCommonSensitiveConfig.specialSearchEnable}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						<br/>
						<span class="message">
							${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.specialSearchEnable.desc') }
						</span>
						<br/>
						<a class="btn_txt" target="_blank" onclick="Com_OpenWindow('${LUI_ContextPath }/sys/common/config.do?method=chgLog&class=com.landray.kmss.util.HQLUtil&level=DEBUG')">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.specialSearchEnable.debug') }</a>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-profile" key="sys.profile.sensitive.word.switch"/>
					</td>
					<td width="85%">
						<ui:switch property="value(isSensitiveCheck)" checked="${sysCommonSensitiveConfig.isSensitiveCheck}" enabledText="${lfn:message('sys-profile:sys.profile.sensitive.word.switch.enabled')}" disabledText="${lfn:message('sys-profile:sys.profile.sensitive.word.switch.disabled')}" onValueChange="checkSensitive(this);"></ui:switch>
						<input type="hidden" id="isSensitiveCheck" value="${sysCommonSensitiveConfig.isSensitiveCheck}"/>
					</td>
				</tr>
			</table>
			<div id="senDiv" style="display: none;">
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="sys-profile" key="sys.profile.sensitive.word.moduleSelect"/>
						</td>
						<td width="85%">
							<html:hidden property="value(moduleConfig)"/>
							<xform:text property="value(moduleName)" style="width:95%" showStatus="readOnly"></xform:text>
							<a href="#" onclick="Dialog_List(true,'value(moduleConfig)','value(moduleName)',';','sysCommonSensitiveDataBean');"><bean:message key="button.select"/></a>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="sys-profile" key="sys.profile.sensitive.word.senWords"/>
						</td>
						<td width="85%">
							<xform:textarea property="value(fdSensitiveWord)" style="width:95%" value="${fdSensitiveWord}"></xform:textarea>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" colspan="4">
							<span style="color: red;"><bean:message bundle="sys-profile" key="sys.profile.sensitive.word.tips"/></span>
						</td>
					</tr>
				</table>
			</div>
			<div>
             <table class="tb_normal" width=100%>
                 <tr>
                        <td class="td_normal_title" width="15%">
                            <bean:message bundle="sys-profile" key="sys.profile.java.xml.deserialize.whitelist"/>
                        </td>
                        <td width="85%">
							<c:choose>
								<c:when test="${empty sysAppConfigForm.map.fdXmlDeserializeWhitelist && !(sysAppConfigForm.map.containsKey('fdXmlDeserializeWhitelist'))}">
									<xform:textarea property="value(fdXmlDeserializeWhitelist)" style="width:95%" value="java.math.BigDecimal;java.sql.Timestamp"></xform:textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(fdXmlDeserializeWhitelist)" style="width:95%" value="${fdXmlDeserializeWhitelist}"></xform:textarea>
								</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" colspan="4">
                            <span style="color: red;"><bean:message bundle="sys-profile" key="sys.profile.java.xml.deserialize.whitelist.msg"/></span>
                        </td>
                    </tr>
             </table>
            </div>
			<div>
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="sys-profile" key="sys.profile.java.formula.deserialize.blacklist"/>
						</td>
						<td width="85%">
							<c:choose>
								<c:when test="${empty sysAppConfigForm.map.formulaDeserializeBlacklist && !(sysAppConfigForm.map.containsKey('formulaDeserializeBlacklist'))}">
									<xform:textarea property="value(formulaDeserializeBlacklist)" style="width:95%" value="Runtime;curl;Process;ProcessBuilder;Reader;File;FileWriter;FileReader;BufferedWriter;BufferedReader;PrintWriter;PrintStream
FileOutputStream;FileInputStream;OutputStream;InputStream;OutputStreamWriter;InputStreamReader;BufferedOutputStream;BufferedInputStream;DataOutputStream;DataInputStream"></xform:textarea>
								</c:when>
								<c:otherwise>
									<xform:textarea property="value(formulaDeserializeBlacklist)" style="width:95%" value="${formulaDeserializeBlacklist}"></xform:textarea>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" colspan="4">
							<span style="color: red;"><bean:message bundle="sys-profile" key="sys.profile.java.formula.deserialize.blacklist.msg"/></span>
						</td>
					</tr>
				</table>
			</div>
			<center style="margin:10px 0;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="formSubmit();"></ui:button>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.profile.model.SysCommonSensitiveConfig" />
			<input type="hidden" name="autoclose" value="false" />
		</html:form>
		<script type="text/javascript">
		Com_IncludeFile("dialog.js");
		window.formSubmit=function(){
			var formObj=document.sysAppConfigForm;
			var url = Com_CopyParameter(formObj.action);
			url = Com_SetUrlParameter(url, "method", 'update');
			formObj.action = url;
			formObj.submit();
		};
		window.checkSensitive=function(obj){
			if(obj.checked){
				$("#senDiv").css('display','block');
				var $height = $(document.body).height();
				window.frameElement.style.height = $height+"px";
				if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
					if(!!window.ActiveXObject||"ActiveXObject" in window){
						$height+=100;
					}
					window.frameElement.style.height = $height+"px";
				}
			}else{
				$("#senDiv").css('display','none');
			}
		};
		window.onload=function(){
			var outValue=outValue=$("#isSensitiveCheck").val();
			if(outValue=='true'||outValue==null||outValue==''){
				$("#senDiv").css('display','block');
			}
			var $height = $(document.body).height();
			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
				if(!!window.ActiveXObject||"ActiveXObject" in window){
					$height+=100;
				}
				window.frameElement.style.height = $height+"px";
			}
		}
		</script>	
	</template:replace>
</template:include>