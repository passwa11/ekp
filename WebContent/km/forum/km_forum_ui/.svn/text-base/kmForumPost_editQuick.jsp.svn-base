<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.web.taglib.editor.CKEditorConfig"%>
<template:include ref="default.edit" sidebar="no" width="100%" showQrcode="false">
    <template:replace name="head">
		<link href="${LUI_ContextPath}/km/forum/resource/css/forum.css" rel="stylesheet" type="text/css" />
        <%@ include file="/km/forum/km_forum_ui/kmForumPost_edit_script.jsp"%>
        <%@ include file="/km/forum/km_forum_ui/kmForumPost_checkWork_script.jsp"%>
        <script type="text/javascript">
		seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
	    	LUI.ready( function() {
	    		$('body').addClass('lui_forum_iframe_body').css("background","#fff");
		    	var fdForumId = document.getElementsByName("fdForumId")[0];
	    		//隐藏or显示匿名发帖区域
	    		getIsAnonymous(fdForumId.value);
	    	});

	    	//快速新建采用无刷新提交
	    	window.submitForm=function(){
	    		//提交表单校验
	    		for(var i=0; i<Com_Parameter.event["submit"].length; i++){
	    			if(!Com_Parameter.event["submit"][i]()){
	    				return false;
	    			}
	    		}
	    		//提交表单校验
	    		CKEDITOR.instances['docContent'].element.fire('updateEditorElement');
	    		var v=RTF_GetContent("docContent");
	    		if(v==null ||v=="") {
					dialog.alert("<bean:message  bundle='km-forum' key='kmForumPost.topicNotEmpty'/>");
					return;
				}
	    		var emoji=/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g;
	    		//检测敏感词
	    		var docSubject = $('input[name="docSubject"]').val();
	    		if(checkIsHasSenWords(docSubject)==true) {
					dialog.alert("<bean:message  bundle='km-forum' key='kmForumConfig.word.warn.topic'/>");
					return;
				}
				//检测敏感词
				if(checkIsHasSenWords(v)==true) {
					dialog.alert("<bean:message  bundle='km-forum' key='kmForumConfig.word.warn'/>");
					return;
				}
				
				//检测是否存在emoji表情
				if(emoji.test(docSubject)) {
					dialog.alert("<bean:message  bundle='km-forum' key='kmForumConfig.emoji.warn.topic'/>");
					return;
				}				
				if(emoji.test(v)) {
					dialog.alert("<bean:message  bundle='km-forum' key='kmForumConfig.emoji.warn'/>");
					return;
				}
				
				
				
				var editor = CKEDITOR.instances['docContent'].element;
	    		editor.fire('updateEditorElement');
		    	
	    		LUI.$.ajax({
					url: '${LUI_ContextPath}/km/forum/km_forum/kmForumPost.do?method=save&isQuick=true',
					type: 'POST',
					dataType: 'text',
					async: false,
					data: $("#kmForumPost").serialize(),
					success: function(data, textStatus, xhr) {
						if(data!=""){
							dialog.success('<bean:message key="return.optSuccess" />');
							setTimeout(function (){
								//window.location.href="${LUI_ContextPath}/km/forum/km_forum/kmForumPost.do?method=add&forward=quickPostEdit&fdForumId="+$("input[name='fdForumId']").val();
								window.parent.location.href = "${LUI_ContextPath}/km/forum/km_forum/kmForumPost.do?method=view&fdTopicId="+data+"&fdForumId="+$("input[name='fdForumId']").val();
							}, 1500);
						}else{
							dialog.faiture('<bean:message key="return.optFailure" />');
						}
					}
				});
		    };
		  });
	   </script>
	</template:replace>
	<template:replace name="content"> 
		    <%--新建帖子表格开始--%>  
            <div class="lui_forum_table" id="editContent">
              <html:form action="/km/forum/km_forum/kmForumPost.do" styleId="kmForumPost">
                <table class="lui_sheet_c_table tb_simple">
                    <%--类别显示--%>  
                    <tr>
                        <td>
                            <%--板块--%>
                            <div style="width:97%">
                              <div class="i_bar" style="width:20%">
                                <html:hidden property="fdForumId" />
                                <xform:text property="fdForumName" subject="${lfn:message('km-forum:kmForum.search.cate')}" htmlElementProperties="readonly=true"  className="input_1" required="true" title="${kmForumPostForm.fdForumName}"/>
                                <div class="sheet_i_iconBox s_i_icon_4" onclick="addDoc(true);">
                                </div>
                            </div>
                             <%--主题--%>  
                            <xform:text property="docSubject" subject="${lfn:message('km-forum:kmForumCategory.fdTopicCount')}" className="input_1" style="width:75%;margin-left: 10px;" required="true"/>
                     		</div>
                        </td>
                    </tr>
                    <%--内容--%>  
                    <tr>
                        <td>
				            <%
								CKEditorConfig __config = new CKEditorConfig();
								__config.addConfigValue("smiley_height","380");
							%>
                            <kmss:editor property="docContent" width="97%" height="250px" config="<%=__config %>"/>
                        </td>
                    </tr>
                    <tr>
                        <td>	
                         	<ui:button styleClass="com_bgcolor_d com_fontcolor_d com_bordercolor_d forum_button" text="${lfn:message('km-forum:kmForumPost.button.save') }" onclick="submitForm('save', 'fdForumId');"/>
                            <%--通知 onValueChange="clickCheckBox(this)"--%>
                            <xform:checkbox property="fdIsNotify" onValueChange="clickCheckBox(this)">
                                <xform:simpleDataSource value="1"> ${lfn:message("km-forum:KmForumPost.notify.title.message") }</xform:simpleDataSource>
                            </xform:checkbox> 
                                 <html:hidden property="fdIsNotify"/>
                                 <%--通知方式--%>
                                 <span class="attach_opt_notify" id="id_notify_type" style="display:none;padding-right:20px">
	                                    <span style="background:#f3f3f3">
	                                   	 ${lfn:message("km-forum:KmForumPost.notify.fdNotifyType") }：&nbsp;
		                                       <kmss:editNotifyType property="fdNotifyType"/>
		                                </span>
	                              </span>
	                  <%String globalIsAnonymous = new KmForumConfig().getAnonymous();
	                                if(globalIsAnonymous.equals("true")){%>   
                            <%--匿名--%>
	                        <span class="item" id="isAnonymous">     
	                            <xform:checkbox property="fdIsAnonymous" onValueChange="clickIsAnonymous(this)">
			                        <xform:simpleDataSource value="1">${lfn:message("km-forum:KmForumPost.notify.title.anonymous") }</xform:simpleDataSource>
			                    </xform:checkbox> 
			                </span>     
			          <%} %>
		                    <%--回帖仅作者可见--%>
	                          <span class="item"> 
	                               <xform:checkbox property="fdIsOnlyView">
	                                  <xform:simpleDataSource value="1"> ${lfn:message("km-forum:kmForumPost.fdIsOnlyView") }</xform:simpleDataSource>
	                               </xform:checkbox> 
	                          </span>
                        </td>
                    </tr>
                </table>
              </html:form>
            </div>
            <!-- 新建帖子表格 结束 -->
		<script>
			var _validation=$KMSSValidation();
		</script>
	</template:replace>
</template:include>
