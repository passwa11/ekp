<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("document.css", "style/"+Com_Parameter.Style+"/doc/");
			Com_IncludeFile("dialog.js|jquery.js");
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/sys/ui/js/address/extend/simple/dialog.js"></script>
	</template:replace>
	<template:replace name="toolbar">
			<%-- <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<c:if test="${param.fdIsOrg=='1' }">
					<kmss:auth requestURL="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=omsInit&fdIsOrg=1">
						<ui:button text="${ lfn:message('third-ding:table.thirdDingOmsInit') }" 
							onclick="window.check();">
						</ui:button>
					</kmss:auth>
				</c:if>
				<c:if test="${param.fdIsOrg=='0' }">
					<kmss:auth requestURL="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=omsInit&fdIsOrg=0">
						<ui:button text="${ lfn:message('third-ding:thirdDingOmsInit.person.init') }"
							onclick="window.check();">
						</ui:button>
					</kmss:auth>
				</c:if>
			</ui:toolbar> --%>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/third/ding/third_ding_oms_init/thirdDingOmsInit.do">
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<div style="color: red;font-size: 9px;margin-top: 10px;">
		<c:if test="${param.fdIsOrg=='1' }">
			<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.finish.org"/><br>
			<bean:message bundle="third-ding" key="thirdDingOmsInit.handle.desc"/><br>
			&nbsp;&nbsp;&nbsp;<bean:message bundle="third-ding" key="thirdDingOmsInit.handle.wx.del"/><br>
			&nbsp;&nbsp;&nbsp;<bean:message bundle="third-ding" key="thirdDingOmsInit.handle.ekp.update"/>
		</c:if>
		<c:if test="${param.fdIsOrg=='0' }">
			<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.finish.person"/><br>
			<bean:message bundle="third-ding" key="thirdDingOmsInit.handle.desc"/><br>
			&nbsp;&nbsp;&nbsp;<bean:message bundle="third-ding" key="thirdDingOmsInit.handle.wx.del.person"/><br>
			&nbsp;&nbsp;&nbsp;<bean:message bundle="third-ding" key="thirdDingOmsInit.handle.ekp.update.person"/>
		</c:if>
	</div>
	<table class="tb_normal" width=98%>
		<tr style="background-color: #D1EEEE;" id="rc">
			<sunbor:columnHead htmlTag="td">
				<td width="30pt" align="center">
					<bean:message key="page.serial"/>
				</td>
				<td align="center">
					<bean:message bundle="third-ding" key="thirdDingOmsInit.fdName"/>
				</td>
				<c:if test="${param.fdIsOrg=='0' }">
					<td align="center">
						<bean:message bundle="third-ding" key="thirdDingOmsInit.fdAccountType"/>
					</td>
					<td align="center">
						<bean:message bundle="third-ding" key="thirdDingOmsInit.fdDingId"/>
					</td>
				</c:if>
				<td align="center">
					<bean:message bundle="third-ding" key="thirdDingOmsInit.fdPath"/>
				</td>
				<td align="center" width="350px">
					<bean:message bundle="third-ding" key="thirdDingOmsInit.handle"/>
				</td>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="thirdDingOmsInit" varStatus="vstatus">
			<tr>
				<td align="center">
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${thirdDingOmsInit.fdName}" />
				</td>
				<c:if test="${param.fdIsOrg=='0' }">
					<td>
						<c:if test="${thirdDingOmsInit.fdAccountType=='common'}">
                            普通账号
						</c:if>
						<c:if test="${thirdDingOmsInit.fdAccountType=='dingtalk' }">
							专属账号(dingtalk)
						</c:if>
						<c:if test="${thirdDingOmsInit.fdAccountType=='sso' }">
							专属账号(sso)
						</c:if>

					</td>
					<td>
						<c:out value="${thirdDingOmsInit.fdDingId}" />
					</td>
				</c:if>
				<td>
					<c:out value="${thirdDingOmsInit.fdPath}" />
				</td>
				<c:if test="${param.fdIsOrg=='1' }">
					<td align="center" style="white-space:nowrap">
						<a id="wx${thirdDingOmsInit.fdId }" style="cursor: pointer;text-decoration: underline;color: blue;font-size: 12px;" onclick="window.dingDel('${thirdDingOmsInit.fdId}','${thirdDingOmsInit.fdDingId}','1');"><bean:message bundle="third-ding" key="thirdDingOmsInit.handle.wx"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
						<xform:dialog icon="orgelement" propertyId="fdEkpId${thirdDingOmsInit.fdId }" style="width:220px" propertyName="fdEkpName${thirdDingOmsInit.fdId }" showStatus="edit" htmlElementProperties="id='ekp${thirdDingOmsInit.fdId}'">
							Dialog_AddressSimple(false, 'fdEkpId${thirdDingOmsInit.fdId }','fdEkpName${thirdDingOmsInit.fdId }', ';',null,null, {nodeBeanURL: 'thirdDingOmsInitListService&orgType=3'});
						</xform:dialog>
						<script type="text/javascript">
							$(function(){
								var id = '${thirdDingOmsInit.fdId }';
								$("input[name='fdEkpId"+id+"']").change(function(){
									window.ekpHandle(id,'1');
								});
							});
						</script>
					</td>
				</c:if>
				<c:if test="${param.fdIsOrg=='0' }">
					<td align="center" style="white-space:nowrap">
						<a id="wx${thirdDingOmsInit.fdId }" style="cursor: pointer;text-decoration: underline;color: blue;font-size: 12px;" onclick="window.dingDel('${thirdDingOmsInit.fdId}','${thirdDingOmsInit.fdDingId}','0');"><bean:message bundle="third-ding" key="thirdDingOmsInit.handle.wx"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
						<xform:dialog icon="orgelement" propertyId="fdEkpId${thirdDingOmsInit.fdId }" style="width:220px" propertyName="fdEkpName${thirdDingOmsInit.fdId }" showStatus="edit" htmlElementProperties="id='ekp${thirdDingOmsInit.fdId}'">
							Dialog_AddressSimple(false, 'fdEkpId${thirdDingOmsInit.fdId }','fdEkpName${thirdDingOmsInit.fdId }', ';',null,null, {nodeBeanURL: 'thirdDingOmsInitListService&orgType=8'});
						</xform:dialog>
						<script type="text/javascript">
							$(function(){
								var id = '${thirdDingOmsInit.fdId }';
								$("input[name='fdEkpId"+id+"']").change(function(){
									window.ekpHandle(id,'0');
								});
							});
						</script>
					</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %><br><br>
</c:if>
</html:form>
		<script type="text/javascript">
		seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
			var message = '<bean:message bundle="third-ding" key="thirdDingOmsInit.org.init.tip"/>';
			var url = '<c:url value="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=omsInit&fdIsOrg=1" />';
			var inittip = '<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.finish.org"/>';
			if('${param.fdIsOrg}'=='0'){
				message = '<bean:message bundle="third-ding" key="thirdDingOmsInit.person.init.tip"/>';
				url = '<c:url value="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=omsInit&fdIsOrg=0" />';
				inittip = '<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.finish.person"/>';
			}
			window.check = function() {
				dialog.confirm(message, function(value){
					if(value == true) {
						$.ajax({
						   type: "POST",
						   url: url,
						   async:true,
						   dataType: "json",
						   success: function(data){
								if(data.status=="1"){
									self.location.reload();
								}
						   },
						   error: function(data){
							   window.progress.hide();
						   }
						});
						// 开启进度条
						window.progress = dialog.progress();
						window._progress();
					}
				});
			}
			
			window._progress = function () {
				var data = new KMSSData();
				data.UseCache = false;
				if('${param.fdIsOrg}'=='0'){
					data.AddBeanData("thirdDingOmsInitService&fdIsOrg=0");
				}else{
					data.AddBeanData("thirdDingOmsInitService&fdIsOrg=1");
				}
				var rtn = data.GetHashMapArray()[0];
				if(window.progress) {
					if(rtn.checkState == 1) {
						window.progress.hide();
					}
					// 设置进度提示
					window.progress.setProgressText(rtn.checkMessage);
					// 设置进度值
					var currentCount = rtn.checkCurrentCount || 0;
					var allCount = rtn.checkAllCount || 0;
					if(allCount == 0) {
						window.progress.setProgress(0);
					} else {
						window.progress.setProgress(currentCount, allCount);
					}
				}
				// 如果总数量等于-1，表示操作还未执行
				// 如果当前执行的数量比总数量少，表示执行未结束
				if(rtn.checkState != 1) {
					setTimeout("window._progress()", 1000);
				}
			}
			
			window.dingDel = function(fdId,deptId,type) {
				var msg = '<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.dept.del"/>';
				var smsg = '<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.dept.del.tip"/>';
				if("0"==type){
					msg = '<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.person.del"/>';
					smsg = '<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.person.del.tip"/>';
				}
				var url = '<c:url value="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=dingDel&parentId=" />'+deptId+"&fdId="+fdId+"&type="+type;
				dialog.confirm(msg, function(value){
					if(value == true) {
						$.post(url, function(data){
					    	if(data.status=="1"){
					    		if(type=="1"){
					    			dialog.alert(smsg);
						    		self.location.reload();
					    		}else{
					    			$("#wx"+fdId).attr("style","text-decoration: none;color: gray;font-size: 12px;");
					    			$("#wx"+fdId).removeAttr("onclick");
					    			$("#ekp"+fdId).attr("style","color: gray;font-size: 12px;");
					    			$("#ekp"+fdId).removeAttr("onclick");
					    		}
					    	}
					   }, "json");
					}
				});
			}
			window.ekpHandle = function(fdId,type) {
				var url = '<c:url value="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=ekpUpdate&fdId=" />'+fdId+"&fdEKPId="+$("input[name='fdEkpId"+fdId+"']").val()+"&type="+type;
				 $.post(url, function(data){
				    	if(data.status!="1"){
				    		dialog.alert('<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.error"/>');
				    		$("input[name='fdEkpId"+fdId+"']").val("");
				    		$("input[name='fdEkpName"+fdId+"']").val("");
				    	}else{
				    		$("#wx"+fdId).attr("style","text-decoration: none;color: gray;font-size: 12px;");
			    			$("#wx"+fdId).removeAttr("onclick");
				    	}
				   }, "json");
			}
		});
		</script>
	</template:replace>
</template:include>