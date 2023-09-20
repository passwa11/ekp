<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" showQrcode="false" sidebar="auto">
	<template:replace name="content">
	  	<script type="text/javascript">
	  	
	  	//时时保存，不提交
	  	function SaveConfig() {
			Com_Submit(document.sysFileConvertGlobalConfigForm, "saveGlobalConvertConfig");
			
		}
	  
	  	//点击提交时保存
	  	function saveGlobalConvert()
	  	{
	  		Com_Submit(document.sysFileConvertGlobalConfigForm, "saveGlobalConvert");
	  		
	  	}
	  	
		</script>
	
      <html:form action="/sys/filestore/sys_filestore/sysFileConvertGlobalConfig.do" >
				<div style="margin-top: 6px; margin-bottom: 10px;">
					<table class="tb_normal" width="96%" id="attconvert.config">
						<tr>
						<td class="td_normal_title" width="180px" align="center"><label> 
							 <bean:message
										key="sysFilestore.conversion.save.converted"
										bundle="sys-filestore" />
						</label></td>
						<%-- enabledText="${ lfn:message('sys-filestore:attConvert.hint.info.global.oldscucessusehtmlview') }"
									disabledText="${ lfn:message('sys-filestore:attConvert.hint.info.global.oldscucessusehtmlview') }" --%>
							<td  colspan="2"><ui:switch
									id="attconvert.oldsuccess.usehtmlview"
									checked="${oldSuccessUseHTMLView}"
									property="attConvertOldSuccessUseHTML"
									enabledText="${ lfn:message('sys-filestore:sysFilestore.conversion.converted.text') }"
									disabledText="${ lfn:message('sys-filestore:sysFilestore.conversion.converted.text') }"></ui:switch>
							</td>
						</tr>
					<tr width="100%">
						<td class="td_normal_title" width="180px" align="center"><label> <bean:message
									key="attconfig.limit.thread.sleep"
									bundle="sys-filestore" />
						</label></td>
						<td colspan="2"><span class="message"><%-- <bean:message
									key='attconfig.limit.thread.sleep.desc' bundle='sys-filestore' /> --%>
									<bean:message
									key='sysFilestore.conversion.distribution.sleep' bundle='sys-filestore' />
									</span>
						<xform:text
								property="distributeThreadSleepTime" style="width:60px;"
								subject="${lfn:message('sys-filestore:attconfig.limit.thread.sleep')}"
								showStatus="edit" validators="min(0) digits" /> <bean:message
									key='sysFilestore.conversion.second' bundle='sys-filestore' /></td>
						
					</tr>
					<tr width="100%">
						<td class="td_normal_title" width="180px" align="center"><label> <bean:message
									key="attconfig.limit.unsignedtask.getnum"
									bundle="sys-filestore" />
						</label></td>
						<td colspan="2">
						<span class="message">
                              <bean:message
									key='sysFilestore.conversion.distribution.max'
									bundle='sys-filestore' /></span>
						<xform:text property="unsignedTaskGetNum"
								style="width:60px;"
								subject="${lfn:message('sys-filestore:attconfig.limit.unsignedtask.getnum')}"
								showStatus="edit" validators="min(1) digits" /> </td>
					</tr>
					<tr width="100%">
						<td class="td_normal_title" width="180px" align="center"><label> <bean:message
									key="attconfig.longtask.filesize"
									bundle="sys-filestore" />
						</label></td>
						<td colspan="2">
						<span class="message"> <bean:message
									key='sysFilestore.conversion.distribution.max.morethan'
									bundle='sys-filestore' /></span>
						<xform:text property="longTaskSize"
								style="width:60px;"
								subject="${lfn:message('sys-filestore:attconfig.longtask.filesize')}"
								showStatus="edit" validators="min(0) digits" /> M
								<span class="message"><bean:message
									key='sysFilestore.conversion.distribution.max.than'
									bundle='sys-filestore' /> </span>
									</td>
					</tr>					
					
					</table>
				</div>
				<center style="margin-top: 10px;"> 
			 		

					<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
						<ui:button text="${lfn:message('button.save') }" height="35" width="80" onclick="saveGlobalConvert();"></ui:button>
					</kmss:authShow>
						<ui:button text="${lfn:message('button.close') }" height="35" width="80" onclick="Com_CloseWindow();"> </ui:button>
              		</center> 
              
			<script>
				$KMSSValidation();
			</script>
		</html:form>
	</template:replace>
</template:include>