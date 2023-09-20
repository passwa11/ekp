<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
<script type="text/javascript">

function setDefaultIdentity() {
	
	var identity=$("input[name='identity_radio']:checked").val();
	if(!identity){
		alert("${lfn:message('sys-lbpmperson:lbpmperson.set.indentity.chooseIdentity')}");
		return;
	}
	$.ajax({     
	     type:"post",    
	     url:"${LUI_ContextPath}/sys/lbpmservice/support/person_set/SysLbpmPersonSet.do?method=updateDefaultIdentity",     
	     async:true,
	     data :{"identity":identity},
	     success:function(data){
	    	 if(data){
	    		    seajs.use('lui/dialog', function(dialog) {
	    		        dialog.success("${ lfn:message('sys-lbpmperson:lbpmperson.set.indentity.setSuccess')}");
	    		    });

	    	 }
	    	
	  	 },
		error : function(msg) {
			alert('保存时出现异常！');
		}
     });
}
function loadDefaultIdentity(){
	$.ajax({     
	     type:"post",    
	     url:"${LUI_ContextPath}/sys/lbpmservice/support/person_set/SysLbpmPersonSet.do?method=findDefaultIdentity",      
	     async:true,
	     success:function(data){
	    	if(data){
	    		$("input[name='identity_radio'][value='"+data+"']").attr("checked",true);
	    	}
	  	 }
    });
}
</script>
<div style="width:100%;">
	  <ui:tabpanel layout="sys.ui.tabpanel.list">
		 <ui:content title="${ lfn:message('sys-lbpmperson:lbpmperson.defaultIndentity') }" style="" >
<table class="tb_normal" width="100%;">			
					<!--主题-->
					<tr>
						<td align="right" style="border-right: 0px;" width=15%>
						
							${lfn:message('sys-lbpmperson:lbpmperson.set.indentity.defaultSubmitIdentity')}
						<td >
							
							<%
							ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
							.getBean("sysOrgCoreService");
							SysOrgPerson currentUser=(SysOrgPerson) sysOrgCoreService.format(UserUtil.getUser());
							
							%>
								<label><input name='identity_radio' type='radio'  value='<%=currentUser.getFdId()%>'><%=currentUser.getFdName()%></input></label>
							<% 
				
							List<?> currentUserPost = currentUser.getFdPosts();
							for (Iterator<?> it = currentUserPost.iterator(); it.hasNext();) {
								SysOrgElement post = (SysOrgElement) it.next();
								%>
									<label><input name='identity_radio' type='radio' value='<%=post.getFdId()%>'><%=post.getFdName()%></input></label>
								<%
							}
							%>
							
							
						</td> 
						
					</tr> 
					<tr>
						<td align="right" style="border-right: 0px;" width=15%>
							${lfn:message('sys-lbpmperson:lbpmperson.set.indentity.disp')}
						<td >
						${lfn:message('sys-lbpmperson:lbpmperson.set.indentity.dispDetail')}
						</td> 
						
					</tr> 
				</table>
				<center style="margin-top: 10px;">
			<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="setDefaultIdentity();"></ui:button>
			
			</center>
			
</ui:content>
</ui:tabpanel>
</div>			
			<script>
			Com_AddEventListener(window,'load',function(){
				//防止页面没加载完就被触发
				setTimeout(loadDefaultIdentity,200);
				
				seajs.use(['lui/jquery'],function($){
					if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
						setTimeout(function(){
							window.frameElement.style.height =  $(document.body).height() +10+ "px";
						},100);
					}
				});
			});
			</script>
	</template:replace> 
</template:include>