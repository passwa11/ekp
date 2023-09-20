<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="no" showQrcode="false">
    <template:replace name="head">
		<link href="${LUI_ContextPath}/km/forum/resource/css/forum.css" rel="stylesheet" type="text/css" />
        <%@ include file="/km/forum/km_forum_ui/kmForumPost_edit_script.jsp"%>
        <%@ include file="/km/forum/km_forum_ui/kmForumPost_checkWork_script.jsp"%>
        <style>
        .notNull{padding-left:10px;border:solid #DFA387 1px;padding-top:8px;padding-bottom:8px;background:#FFF6D9;color:#C30409;margin-top:3px;}
        </style>
        <script type="text/javascript">
		seajs.use(['lui/dialog'], function(dialog) {
	    	LUI.ready( function() {
		    	//类别弹出框
	    		var fdForumId = document.getElementsByName("fdForumId")[0];
	    		//隐藏or显示匿名发帖区域
	    		getIsAnonymous(fdForumId.value);
	    		if(fdForumId.value==null ||fdForumId.value==''){
		    		  addDoc();
	    	    	}
			    });	
		  });
	   </script>
	</template:replace>
	<%--标签页标题--%>
	<template:replace name="title">
		<c:choose>
			<c:when test="${ kmForumPostForm.method_GET == 'add' }">
				<c:out value="${lfn:message('km-forum:kmForumTopic.create')} - ${ lfn:message('km-forum:module.km.forum') } "></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${kmForumPostForm.docSubject} - ${ lfn:message('km-forum:module.km.forum') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	
	<%--导航路径--%>
	<template:replace name="path">
			<ui:combin ref="menu.path.simplecategory">
				<ui:varParams 
					moduleTitle="${ lfn:message('km-forum:module.km.forum') }" 
					modelName="com.landray.kmss.km.forum.model.KmForumCategory" 
					autoFetch="false"
					categoryId="${kmForumPostForm.fdForumId}" />
			</ui:combin>
	</template:replace>	
	
	<template:replace name="content"> 
		<!-- 快捷菜单 开始 -->
	        <div class="edit_forum_shortcut_menu" id="forum_shortcut_menu">
	             <ul>
	               <li><a id="returnList" title="${ lfn:message('km-forum:KmForumPost.notify.title.returnList') }" href="${LUI_ContextPath}/km/forum/indexCriteria.jsp?categoryId=${JsParam.fdForumId}" class="menu3"></a></li>
	            </ul>
	        </div>
	        <!-- 快捷菜单 结束 -->
		    <%--新建帖子表格开始--%>  
            <div class="lui_forum_table">
              <html:form action="/km/forum/km_forum/kmForumPost.do">
                <table class="lui_sheet_c_table tb_simple tb_forum_post">
                    <%--类别显示--%>  
                    <tr>
                        <td>
                            <%--板块--%>
                          <div style="width:96%">
                            <div class="i_bar" style="width:20%">
                                <html:hidden property="fdForumId" />
                                <xform:text property="fdForumName" subject="${lfn:message('km-forum:kmForum.search.cate')}" htmlElementProperties="readonly=true"  className="input_1" required="true" title="${kmForumPostForm.fdForumName}"/>
                               	<c:if test="${!(kmForumPostForm.method_GET =='edit' && kmForumTopic.fdStatus == '30')}">
	                                <div class="sheet_i_iconBox s_i_icon_4" onclick="addDoc();">
	                                </div>
                                </c:if>
                            </div>
                            <%--主题--%>  
                            <html:hidden property="fdId" />
                            <html:hidden property="fdTopicId" />
                            <html:hidden property="fdSupportCount" />
                            <xform:text property="docSubject" subject="${lfn:message('km-forum:kmForumCategory.fdTopicCount')}" className="input_1" style="width:75%;margin-left: 10px;" required="true" validators="senWordsValidator(kmForumPostForm)"/>
                         </div>
                        </td>
                    </tr>
                    <%--内容--%>  
                    <tr>
                        <td>
                            <xform:rtf property="docContent" width="96%" validators="senWordsValidator(kmForumPostForm) required"></xform:rtf>
                        </td>
                    </tr>
                    <%--附件--%>
                    <tr class="tr_line">
                        <td>
                            <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							     <c:param name="fdKey" value="attachment"/>
							     <c:param name="uploadAfterSelect" value="true" />
						    </c:import>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="attach_opt_w" style="width:96%">
                                <%--附件选项--%>
                                <em>${lfn:message("km-forum:kmForumPost.editSelect") }</em>
                                <div class="attach_opt">
                                    <span class="qucikPost_w">
                                        <c:if test="${kmForumPostForm.method_GET =='edit' && kmForumTopic.fdStatus != '10'}">
                                               <c:set value="true" var="showView"></c:set>
                                        </c:if>
                                        <c:if test="${showView==true}">
                                              <html:hidden property="fdIsAnonymous"/>
                                        </c:if>
	                                       <%--通知--%>
	                                       <span class="item">  
	                                               <xform:checkbox property="fdIsNotify" onValueChange="clickCheckBox(this)" showStatus="edit">
	                                                    <xform:simpleDataSource value="1"> ${lfn:message("km-forum:KmForumPost.notify.title.message") }</xform:simpleDataSource>
	                                               </xform:checkbox> 
	                                       </span> 
	                                 <%String globalIsAnonymous = new KmForumConfig().getAnonymous();
	                                       if(globalIsAnonymous.equals("true")){%>   
	                                       <%--匿名发帖--%>
	                                       <span class="item" id="isAnonymous"> 
			                                    <xform:checkbox property="fdIsAnonymous" onValueChange="clickIsAnonymous(this)" showStatus="${showView==true?'readOnly':'edit'}">
			                                         <xform:simpleDataSource value="1">${lfn:message("km-forum:KmForumPost.notify.title.anonymous") }</xform:simpleDataSource>
			                                    </xform:checkbox> 
	                                       </span> 
	                                 <%} %>
	                                        <%--回帖仅作者可见--%>
	                                       <span class="item"> 
	                                               <xform:checkbox property="fdIsOnlyView" showStatus="edit">
	                                                     <xform:simpleDataSource value="1"> ${lfn:message("km-forum:kmForumPost.fdIsOnlyView") }</xform:simpleDataSource>
	                                               </xform:checkbox> 
	                                       </span>
                                      </span>
                                    <br>
                                </div>
                                  <%--通知方式--%>
                                 <div class="attach_opt_notify" id="id_notify_type" style="display:none">
                                    <span style="padding-left: 16px">
                                   	 ${lfn:message("km-forum:KmForumPost.notify.fdNotifyType") }：&nbsp;
	                                       <kmss:editNotifyType property="fdNotifyType" />                                             
	                                </span>
                                </div>
                                	<div class="notNull" id="fdNotifyType">×&nbsp;&nbsp;&nbsp;<bean:message bundle="km-forum" key="KmForumPost.notify.fdNotifyType.warn"/>
	                              		</div>
                            </div>
                        </td>
                    </tr>
                    <tr class="tr_button">
                        <td>	
	                       <c:if test="${kmForumPostForm.method_GET=='edit' && kmForumTopic.fdStatus != '10'}">		
	                      	 	<ui:button styleClass="com_bgcolor_d com_fontcolor_d com_bordercolor_d forum_button"  text="${lfn:message('button.save')}" onclick="submitKmForumPostForm('update');"/>
	                       </c:if>
	                       <c:if test="${kmForumPostForm.method_GET=='edit' && kmForumTopic.fdStatus == '10'}">
	                        	<ui:button styleClass="com_bgcolor_d com_fontcolor_d com_bordercolor_d forum_button"  text="${lfn:message('km-forum:kmForumPost.button.save')}" onclick="submitKmForumPostForm('update');"/>
	                        	<ui:button styleClass="com_bgcolor_l com_fontcolor_l com_bordercolor_l forum_button"  text="${lfn:message('km-forum:kmForumPost.button.saveDraft')}" onclick="submitKmForumPostForm('updateDraft', 'fdForumId');"/>
							</c:if>
						   <c:if test="${kmForumPostForm.method_GET=='add'}">
						   		<ui:button styleClass="com_bgcolor_d com_fontcolor_d com_bordercolor_d forum_button"  text="${lfn:message('km-forum:kmForumPost.button.save')}" onclick="submitKmForumPostForm('save', 'fdForumId');"/>
						   		<ui:button styleClass="com_bgcolor_l com_fontcolor_l com_bordercolor_l forum_button"  text="${lfn:message('km-forum:kmForumPost.button.saveDraft')}" onclick="submitKmForumPostForm('saveDraft', 'fdForumId');"/>
						   </c:if>
                        </td>
                    </tr>
                </table>
              </html:form>
            </div>
            <!-- 新建帖子表格 结束 -->
            
        <script>	          
			$(function(){
			$("#fdNotifyType").hide();
			// name会发生改变
			var fdNotifyType = document.getElementsByName("fdNotifyType")[0];
			$("input[name^='__notify_type_']").click(function() {
			//checkNotifyType();
				});
			});
		</script>
		<script>
		 	var _validation=$KMSSValidation();
		 	//隐藏通知方式区域
	    	var href = window.location.href; 
	    	var reg = /method=edit|method=add|method=reload/;
	    	if(reg.test(href)){
	    	var value = "<c:out value="${kmForumPostForm.fdIsNotify}"/>"
	    		if(value == '1'){
	    			document.getElementById("id_notify_type").style.display="";
	    		}else{
	    			document.getElementById("id_notify_type").style.display="none";
	    		}
	    	}
		</script>
	</template:replace>
</template:include>
