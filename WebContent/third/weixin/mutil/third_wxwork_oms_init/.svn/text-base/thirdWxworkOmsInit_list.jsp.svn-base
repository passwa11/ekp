<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("document.css", "style/"+Com_Parameter.Style+"/doc/");
			Com_IncludeFile("data.js");
			Com_IncludeFile("dialog.js|jquery.js");
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/sys/ui/js/address/extend/simple/dialog.js"></script>
	</template:replace>
	<template:replace name="toolbar">
			
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/third/weixin/mutil/third_wxwork_oms_init/thirdWxworkOmsInit.do">
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<div style="color: red;font-size: 9px;margin-top: 10px;">
		<c:if test="${param.fdIsOrg=='1' }">
			<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.omsinit.finish.org"/><br>
			<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.handle.desc"/><br>
			&nbsp;&nbsp;&nbsp;<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.handle.wx.del"/><br>
			&nbsp;&nbsp;&nbsp;<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.handle.ekp.update"/>
		</c:if>
		<c:if test="${param.fdIsOrg=='0' }">
			<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.omsinit.finish.person"/><br>
			<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.handle.desc"/><br>
			&nbsp;&nbsp;&nbsp;<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.handle.wx.del.person"/><br>
			&nbsp;&nbsp;&nbsp;<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.handle.ekp.update.person"/>
		</c:if>
	</div>
	<table class="tb_normal" width=98%>
		<tr style="background-color: #D1EEEE;" id="rc">
			<sunbor:columnHead htmlTag="td">
				<td width="30pt" align="center">
					<bean:message key="page.serial"/>
				</td>
				<td align="center">
					<c:if test="${param.fdIsOrg=='1' }">
						<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.fdName"/>
					</c:if>
					<c:if test="${param.fdIsOrg=='0' }">
						<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.fdName.person"/>
					</c:if>
				</td>
				<c:if test="${param.fdIsOrg=='0' }">
					<td align="center">
						<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.fdWeixinId"/>
					</td>
				</c:if>
				<td align="center">
					<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.fdPath"/>
				</td>
				<c:if test="${param.fdIsOrg=='0' }">
					<td align="center">
						<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.fdStatus"/>
					</td>
				</c:if>
				<td align="center">
					<bean:message bundle="third-weixin-mutil" key="thirdWxWorkConfig.fdKey.title"/>
				</td>
				<td align="center" width="350px">
					<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.handle"/>
				</td>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="thirdWxworkOmsInit" varStatus="vstatus">
			<tr>
				<td align="center">
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${thirdWxworkOmsInit.fdName}" />
				</td>
				<c:if test="${param.fdIsOrg=='0' }">
					<td>
						<c:out value="${thirdWxworkOmsInit.fdWeixinId}" />
					</td>
				</c:if>
				<td>
					<c:out value="${thirdWxworkOmsInit.fdPath}" />
				</td>
				<c:if test="${param.fdIsOrg=='0' }">
					<td>
						<c:if test="${thirdWxworkOmsInit.fdStatus=='1' }">
							<bean:message bundle="third-weixin-mutil" key="enumeration_third_wx_oms_init_fd_status_1"/>
						</c:if>
						<c:if test="${thirdWxworkOmsInit.fdStatus=='2' }">
							<bean:message bundle="third-weixin-mutil" key="enumeration_third_wx_oms_init_fd_status_2"/>
						</c:if>
						<c:if test="${thirdWxworkOmsInit.fdStatus=='4' }">
							<bean:message bundle="third-weixin-mutil" key="enumeration_third_wx_oms_init_fd_status_4"/>
						</c:if>
					</td>
				</c:if>
				<td>
					<c:out value="${thirdWxworkOmsInit.fdWxKey}" />
				</td>
				<c:if test="${param.fdIsOrg=='1' }">
					<td align="center" style="white-space:nowrap">
						<a id="wx${thirdWxworkOmsInit.fdId }" style="cursor: pointer;text-decoration: underline;color: blue;font-size: 12px;" onclick="window.wxDel('${thirdWxworkOmsInit.fdId}','${thirdWxworkOmsInit.fdWeixinId}','1','${thirdWxworkOmsInit.fdWxKey}');"><bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.handle.wx"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
						<xform:dialog icon="orgelement" propertyId="fdEkpId${thirdWxworkOmsInit.fdId }" style="width:220px" propertyName="fdEkpName${thirdWxworkOmsInit.fdId }" showStatus="edit" htmlElementProperties="id='ekp${thirdWxworkOmsInit.fdId}'">
							Dialog_AddressSimple(false, 'fdEkpId${thirdWxworkOmsInit.fdId }','fdEkpName${thirdWxworkOmsInit.fdId }', ';',null,null, {nodeBeanURL: 'thirdWxworkOmsInitListService&orgType=3'});
						</xform:dialog>
						<script type="text/javascript">
							$(function(){
								var id = '${thirdWxworkOmsInit.fdId }';
								$("input[name='fdEkpId"+id+"']").change(function(){
									window.ekpHandle(id,'1');
								});
							});
						</script>
					</td>
				</c:if>
				<c:if test="${param.fdIsOrg=='0' }">
					<td align="center" style="white-space:nowrap">
						<a id="wx${thirdWxworkOmsInit.fdId }" style="cursor: pointer;text-decoration: underline;color: blue;font-size: 12px;" onclick="window.wxDel('${thirdWxworkOmsInit.fdId}','${thirdWxworkOmsInit.fdWeixinId}','0','${thirdWxworkOmsInit.fdWxKey}');"><bean:message bundle="third-weixin-work" key="thirdWxOmsInit.handle.wx"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
						<xform:dialog icon="orgelement" propertyId="fdEkpId${thirdWxworkOmsInit.fdId }" style="width:220px" propertyName="fdEkpName${thirdWxworkOmsInit.fdId }" showStatus="edit" htmlElementProperties="id='ekp${thirdWxworkOmsInit.fdId}'">
							Dialog_AddressSimple(false, 'fdEkpId${thirdWxworkOmsInit.fdId }','fdEkpName${thirdWxworkOmsInit.fdId }', ';',null,null, {nodeBeanURL: 'thirdWxworkOmsInitListService&orgType=8'});
						</xform:dialog>
						<script type="text/javascript">
							$(function(){
								var id = '${thirdWxworkOmsInit.fdId }';
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
				var message = '<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.org.init.tip"/>';
				var url = '<c:url value="/third/weixin/mutil/third_wxwork_oms_init/thirdWxworkOmsInit.do?method=omsInit&fdIsOrg=1" />';
				var inittip = '<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.omsinit.finish.org"/>';
				if('${param.fdIsOrg}'=='0'){
					message = '<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.person.init.tip"/>';
					url = '<c:url value="/third/weixin/mutil/third_wxwork_oms_init/thirdWxworkOmsInit.do?method=omsInit&fdIsOrg=0" />';
					inittip = '<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.omsinit.finish.person"/>';
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
						data.AddBeanData("thirdWxworkOmsInitService&fdIsOrg=0");
					}else{
						data.AddBeanData("thirdWxworkOmsInitService&fdIsOrg=1");
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
				
				window.wxDel = function(fdId,deptId,type,fdWxKey) {
					var msg = '<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.omsinit.dept.del"/>';
					var smsg = '<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.omsinit.dept.del.tip"/>';
					if("0"==type){
						msg = '<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.omsinit.person.del"/>';
						smsg = '<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.omsinit.person.del.tip"/>';
					}
					var url = '<c:url value="/third/weixin/mutil/third_wxwork_oms_init/thirdWxworkOmsInit.do?method=wxDel&parentId=" />'+deptId+"&fdId="+fdId+"&type="+type+"&fdWxKey="+fdWxKey;
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
						    	}else{
						    		dialog.alert('<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.omsinit.del"/>');
						    	}
						   }, "json");
						}
					});
				}		
				
				window.ekpHandle = function(fdId,type) {
					var url = '<c:url value="/third/weixin/mutil/third_wxwork_oms_init/thirdWxworkOmsInit.do?method=ekpUpdate&fdId=" />'+fdId+"&fdEKPId="+$("input[name='fdEkpId"+fdId+"']").val()+"&type="+type+"&fdWxKey="+'${thirdWxworkOmsInit.fdWxKey}';
					 $.post(url, function(data){
					    	if(data.status!="1"){
					    		dialog.alert('<bean:message bundle="third-weixin-mutil" key="thirdWxOmsInit.omsinit.error"/>');
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