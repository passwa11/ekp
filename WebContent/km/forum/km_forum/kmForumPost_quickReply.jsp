<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.web.taglib.editor.CKEditorConfig"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>

<template:include ref="default.edit" sidebar="no" width="100%" showQrcode="false">
    <template:replace name="head">
          <%   CKEditorConfig __config = new CKEditorConfig();
			   __config.addConfigValue("smiley_height",300);
		  %>
		<link href="${LUI_ContextPath}/km/forum/resource/css/forum.css" rel="stylesheet" type="text/css" />
	    <%@ include file="/km/forum/km_forum_ui/kmForumPost_checkWork_script.jsp"%>
		<style>
			.lui_form_content {border: 0}
		</style>
        <script type="text/javascript">
		seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
	    	LUI.ready( function() {
	    		$('body').addClass('lui_forum_iframe_body').css("background","#fff");
		    	var fdParentId ="${JsParam.fdParentId}";
	    		//dyniFrameSize();
	    		//楼层回复防止附件上传自适应,回复和点击
	    	    var method="${kmForumPostForm.method_GET}";
	    	    //回复主题
	    		if(fdParentId ==""&&method!="edit"){
		    		 //附件添加后高度自适应
				    attachmentObject_attachment.on("uploadSuccess", function() {
				    	dyniFrameSize();
				    });
		    		
				    //附件删除后高度自适应
				    attachmentObject_attachment.on("editDelete", function() {
				    	dyniFrameSize();
				    });
				    ___ckeReady();
				//iframe回复楼层
	    		}else{
	    			//window.frameElement.style.height = "600px";
			    }
			    

			    //是否显示匿名回复选择框
			    var fdForumId = '<c:out value ="${param.fdForumId}"/>';
			    getIsAnonymous(fdForumId);
			    
	    	});
	   
            //自适应高度
	    	window.dyniFrameSize = function() {
	    		try {
	    			var href = window.location.href;
	    			try{
	    				var isDialog = Com_GetUrlParameter(href, "isDialog");
		    			if('true'==isDialog){
		    				return;
		    			}
	    			}catch(e1){
	    				
	    			}
	    			
	    			// 调整高度
	    			var arguObj = document.getElementById("quickReply_div");
	    			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
	    				var ckeHeight;
	    				try{
	    					ckeHeight = $(".cke_dialog").height();
	    					//alert($(".cke_dialog") + "---"+ ckeHeight);
	    				}catch(e1){
	    					
	    				}
	    				var arguHeight = arguObj.offsetHeight;
	    				if(ckeHeight && ckeHeight>arguHeight){
	    					arguHeight = ckeHeight;
	    				}
	    				arguObj.style.height = "100%";
	    				
	    				window.frameElement.style.height = (arguHeight)+45+ "px";
	    				window.frameElement.style.width = (arguObj.offsetWidth) + "px";
	    			}
	    		} catch(e) {
	    		}
	    	};

	    	//快速新建采用无刷新提交
	    	window.submitForm=function(method){
	    		for(var i=0; i<Com_Parameter.event["confirm"].length; i++){
					if(!Com_Parameter.event["confirm"][i]()){
						return false;
					}
				}    
	    		//提交表单校验
	    		var v=RTF_GetContent("docContent");
	    		if(v==null ||v=="") {
					dialog.alert("<bean:message  bundle='km-forum' key='kmForumPost.notEmpty'/>");
					return;
				}
	    		//检测编辑器多个空格
	    		v = v.replace(/&nbsp;/g,"").replace(/<p>/g,"").replace(/<\/p>/g,"").replace(/\s+/g,"");
	    		if(v==null ||v=="") {
					dialog.alert("<bean:message  bundle='km-forum' key='kmForumPost.notEmpty'/>");
					return;
				}
	    		//检测敏感词
	    		var emoji=/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g;
	    		var result = checkIsHasSenWords('kmForumPostForm',v,'<bean:message bundle="sys-profile" key="sys.profile.sensitive.word.content"/>');
	    		if(emoji.test(v)) {
	    			dialog.alert("<bean:message  bundle='km-forum' key='kmForumConfig.emoji.warn'/>");
					return;
				}
	    		if(result!=false || emoji.test(v)) {
					dialog.alert(result);
					return;
				}
	    		
	    		var editor = CKEDITOR.instances['docContent'].element;
	    		editor.fire('updateEditorElement');
				//var v1=RTF_GetContent("docContent");
	    		//document.getElementsByName("docContent")[0].value=v1;
				$.ajax({
					url: '${LUI_ContextPath}/km/forum/km_forum/kmForumPost.do?method='+method,
					type: 'POST',
					dataType: 'json',
					async: false,
					data: $("#kmForumPost").serialize(),
					success: function(data, textStatus, xhr) {
						if(data.timeInterval != null){
							dialog.alert(data.timeInterval);
						}else if(data==true){
							dialog.success('<bean:message key="return.optSuccess" />');
							editor.setValue('');
							editor.setHtml('');
							setTimeout(function (){
								var pageno = window.parent.document.getElementById("currPage").value;
								var rowsize = window.parent.document.getElementById("currRowsize").value;
								var totalrows = window.parent.document.getElementById("totalrows").value;
							    if ($("#toLastPage").is(":checked")) {//跳转到最后一页
									pageno = parseInt((totalrows+1)/rowsize);
								}
								//跳转到阅读页面定位锚点
								var fdPostId = "";
								if(method == "updateReply"){
									fdPostId = '${JsParam.fdId}';
								}else if(method == "saveQuick"){
									fdPostId = '${JsParam.fdParentId}';
								}
								window.parent.location.href="${LUI_ContextPath}/km/forum/km_forum/kmForumPost.do?method=view&fdForumId=${JsParam.fdForumId}&fdTopicId=${JsParam.fdTopicId}&pageno="+pageno+"&rowsize="+rowsize+"&sortType=asc"+"&fdPostId="+fdPostId;
							}, 1500);
						}else{
							dialog.alert('<bean:message key="return.optFailure" />');
						}
					}
				});
		    };

		    window.___ckeReady = function(){
		    	if (CKEDITOR.instances['docContent'] && CKEDITOR.instances['docContent'].instanceReady) {
		    	    CKEDITOR.instances['docContent'].on('resize',function(){
		    	    	dyniFrameSize();
		    		    });
		    		 dyniFrameSize();
		    	} else
		    		setTimeout(function() {
		    			___ckeReady();
		    		}, 200);
		    };

		  //判断是否可以匿名
			window.getIsAnonymous = function(fdForumId) {
				if(fdForumId == null){
                     return;
				 }
				var url = "${LUI_ContextPath}/km/forum/km_forum/kmForumPost.do?method=getIsAnonymous";
				var data ={fdForumId:fdForumId};
				var anonymousDiv = $("#isAnonymous");
				LUI.$.ajax({
					url: url,
					type: 'get',
					dataType: 'json',
					async: false,
					data: data,
					success: function(data, textStatus, xhr) {
						if(data==true){
							//显示
							if( $("#isAnonymous").is(":hidden")){
							   anonymousDiv.show();
							}
						}else{
							//隐藏区域
							if( $("#isAnonymous").is(":visible")){
							   anonymousDiv.hide();
							}
						}
					}
				});
			   
		    };

		    
		  });
	   </script>
	   <script type="text/javascript">
	   function submitKmForumPostForm() {
			Com_Submit(document.kmForumPostForm, 'saveQuick');
		}
	   </script>
	</template:replace>
	<template:replace name="content"> 
		    <%--新建帖子表格开始--%>  
             <html:form action="/km/forum/km_forum/kmForumPost.do" styleId="kmForumPost">
               <div id="quickReply_div" class="forum_quickPost_read">
                 <html:hidden property="fdId"/>
				 <html:hidden property="fdTopicId"/>
				 <html:hidden property="fdParentId"/>
				 <html:hidden property="fdQuoteMsg"/>
				 <html:hidden property="quoteMsg"/>
				 <html:hidden property="fdForumId"/>
				 <html:hidden property="fdForumName"/>
		    <c:if test="${ kmForumPostForm.method_GET == 'edit' }">		 
		         <html:hidden property="fdIsAnonymous"/>
		         <html:hidden property="fdPosterId"/>
		    </c:if>
				 <html:hidden property="docSubject"/>
				 <html:hidden property="fdSupportCount"/>
                 <table class="lui_sheet_c_table" style="width: 100%">
                    <tr>
                        <td class="td_user_img">
                            <%String fdId = UserUtil.getUser().getFdId();
                              request.setAttribute("imageUserId",fdId);%>
                             <img src="<person:headimageUrl personId='${imageUserId}' contextPath='true'/>" style="width: 60px;height: 60px" alt=""/>
                        </td>
                        <td>
				        
                            <xform:rtf property="docContent" height="280px" width="100%" validators="senWordsValidator(kmForumPostForm)"/>
                            <div style="height: 8px"></div>
                            <%--附件--%>
					        <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							     <c:param name="fdKey" value="attachment"/>
							     <c:param name="fdModelName" value="com.landray.kmss.km.forum.model.KmForumPost" />
						    </c:import>
						    <p class="p_upload"> </p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                        </td>
                        <td>
                            <span class="qucikPost_w"><span class="item">
                                <%--回帖后回到最后一页--%>
                                 <c:if test="${ kmForumPostForm.method_GET != 'edit' }"> 
                                        <input type="checkbox" name="toLastPage" id="toLastPage"><label for="toLastPage">${lfn:message("km-forum:KmForumPost.notify.title.replyTips") }</label>
                                        <%--匿名回复--%>
		                                <span class="item" id="isAnonymous">     
				                            <xform:checkbox property="fdIsAnonymous" htmlElementProperties="disabled:true">
						                        <xform:simpleDataSource value="1">${lfn:message("km-forum:KmForumPost.notify.title.anonReply") }</xform:simpleDataSource>
						                    </xform:checkbox> 
		                                </span>
	                              </c:if>
                               </span> 
                           </span>
                           <span class="qucikPost_btn">
	                           <c:choose>
			                         <c:when test="${ kmForumPostForm.method_GET == 'edit' }">
			                            <ui:button styleClass="com_bgcolor_d com_fontcolor_d com_bordercolor_d forum_button" text="${lfn:message('km-forum:KmForumPost.notify.title.quickUpdate') }" onclick="submitForm('updateReply');"/>
			                         </c:when>
			                         <c:otherwise>
			                          	<ui:button styleClass="com_bgcolor_d com_fontcolor_d com_bordercolor_d forum_button" text="${lfn:message('km-forum:KmForumPost.notify.title.quickReply') }" onclick="submitForm('saveQuick');"/>
			                         </c:otherwise>
			                   </c:choose>
			               </span>
                        </td>
                    </tr>
                </table>
               </div>
             </html:form>
            <!-- 新建帖子表格 结束 -->
		<script>
			var _validation=$KMSSValidation();
		</script>
	</template:replace>
</template:include>
