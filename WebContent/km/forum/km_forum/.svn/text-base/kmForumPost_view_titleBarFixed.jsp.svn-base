<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.praise.interfaces.ISysPraiseAndNegativeService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
            <!-- 标题固定开始 -->
             <div class="forum_titleBar_fixed" style="display: none;">
               <div class="forum_titleBar_w">
                  <div class="main_body" style="width:90%; min-width:980px; max-width:${fdPageMaxWidth };margin:0px auto;">
                    <div class="forum_content_titleBar">
                       <div class="${topic.fdIsAnonymous ==false?'userImage':'userImage anony'}">
                           <div class="floor_user_info_w"> 
                        <%--发帖人头像--%>
                        <c:if test="${topic.fdIsAnonymous == false}">  
                               <img src="<person:headimageUrl personId='${topic.fdPoster.fdId}' contextPath='true'/>" class="author_img"/>
                               <%--  <p class="level">
	                               <a href="#" onClick="Com_OpenWindow('<c:url value="/km/forum/km_forum_config/kmForumConfig.do" />?method=viewLevel','_blank');">
										<img src="${LUI_ContextPath}/resource/style/default/forum/level/${levelIcon}.gif" border="0"> 
								    </a>
						       </p> 
						       --%>
                        </c:if> 
                        <c:if test="${topic.fdIsAnonymous == true}">  <%--匿名--%>
                                <img src="${ LUI_ContextPath }/km/forum/resource/images/user_anon_img.png" class="author_img"/>
                        </c:if>
                        <c:if test="${topic.fdIsAnonymous ==false}">  
                                    <%--用户信息匿名不显示--%>
                                    <div class="floor_user_baseInfo">
                                        <%--用户名--%>
										<p class="user">
										    <a
											href="${LUI_ContextPath}/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=${topic.fdPoster.fdId}"
											target="_blank">${topic.fdPoster.fdName}</a>
										</p>
										<ul>
                                            <%--来自--%>
                                            <li><em><bean:message bundle="km-forum" 
                                                                  key="kmForumPost.from.title" />:</em>${topic.fdPoster.fdParent.fdName}</li>
                                            <%--岗位--%>
                                            <li><em><bean:message bundle="sys-organization" 
                                                                  key="sysOrgElement.post" />:</em>${postName}</li>
                                            <%--等级--%>
                                            <li>
												<em><bean:message bundle="km-forum" 
                                                                  key="kmForumConfig.shortLevel" />:</em>${level}  </li>
                                            <%--积分--%>
                                            <li><em><bean:message bundle="km-forum"
							                                      key="kmForumScore.fdScore" />:</em>${topicPoster.posterScore.fdScore}</li>
                                            <%--主题--%>
                                            <li><em>${lfn:message('km-forum:kmForumPost.topic')}:</em>${topicPoster.posterScore.fdPostCount}</li>
                                            <%--回帖--%>
                                            <li><em>${lfn:message('km-forum:kmForum.button.reply')}:</em>${topicPoster.posterScore.fdReplyCount}</li>
                                        </ul>
                                </div>
                           </c:if>
                          </div>
                        </div>
	                        <h1 class="title">
	                              <%--顶图标--%>
	                              <c:if test="${topic.fdSticked==true}">
					                   <img src="${LUI_ContextPath}/km/forum/resource/images/i_top.png" border="0" title="<bean:message bundle="km-forum" key="kmForumTopic.button.stick"/>">
					              </c:if>
					              <%--精图标--%>
					              <c:if test="${topic.fdPinked==true}">
									   <img src="${LUI_ContextPath}/km/forum/resource/images/i_pink.png" border="0" title="<bean:message bundle="km-forum" key="kmForumTopic.pink.title"/>">
								  </c:if> 
								  <%--热图标--%>
								  <c:if test="${topic.fdReplyCount>=hotReplyCount}">
									   <img src="${LUI_ContextPath}/km/forum/resource/images/i_hot.png" border="0" title="<bean:message bundle="km-forum" key="kmForumTopic.hot.title"/>">
								  </c:if>
								  <%--结贴图标--%>
								  <c:if test="${topic.fdStatus=='40'}">
									   <img src="${LUI_ContextPath}/km/forum/resource/images/end.gif" border="0" title="<bean:message bundle="km-forum" key="kmForumTopic.button.conclude"/>">
								  </c:if>
								  <%--显示主话题--%>
	                                 <c:out value="${topic.docSubject}"/>
	                          </h1>
	                        <%--发帖人信息--%>
	                        <div class="post_basicInfo">
	                              <%--发帖人姓名--%>
	                              <c:if test="${topic.fdIsAnonymous==false}">
									<ui:person personId="${topic.fdPoster.fdId}" personName="${topic.fdPoster.fdName}"></ui:person>
								  </c:if>
								  <c:if test="${topic.fdIsAnonymous==true}">
									   <bean:message bundle="km-forum" key="kmForumTopic.fdIsAnonymous.title" />
								  </c:if>
								  <%--发帖时间--%>
	                              <bean:message bundle="km-forum" key="kmForumTopic.docPublishTimeAt" /> 
			                             <kmss:showDate type="datetime" isInterval="true" showTitle="true" value="${topic.docCreateTime}" />
	                                  <em>|</em> 
	                              <%--最后回复时间--%>     
	                                      ${lfn:message('km-forum:kmForumTopic.fdLastPosterId')}
			                             <kmss:showDate type="datetime" isInterval="true" showTitle="true" value="${topic.fdLastPostTime}"/>                             
	                              <%--显示查看该作者和显示全部--%>
	                              <c:if test="${topic.fdIsAnonymous==false}">
									  <em>|</em> 
								      <c:if test="${param.type!='onlyViewPoster' &&not empty topic.fdPoster}">
										  <a href="#" class="search" onclick="Com_OpenWindow('<c:url value="/km/forum/km_forum/kmForumPost.do" />?method=view&fdId=${JsParam.fdTopicId}&fdForumId=${JsParam.fdForumId}&fdTopicId=${JsParam.fdTopicId}&fdPosterId=${topic.fdPoster.fdId}&type=onlyViewPoster','_self');">
										     <bean:message bundle="km-forum" key="kmForumPost.onlyViewPoster" />
										  </a>
							          </c:if>
							          <c:if test="${param.type=='onlyViewPoster'}">
								          <a href="#" class="search" onclick="Com_OpenWindow('<c:url value="/km/forum/km_forum/kmForumPost.do" />?method=view&fdId=${JsParam.fdTopicId}&fdForumId=${JsParam.fdForumId}&fdTopicId=${JsParam.fdTopicId}','_self');">
								             <bean:message bundle="km-forum" key="kmForumPost.ViewAll" /> 
								          </a>
							          </c:if>
						          </c:if>
                                  <div class="forum_floor_info">
                                    <c:if test="${topic.fdStatus == '30'}"> 
                                        <c:set var="fdId" value="${topicPoster.fdId}" scope="request"/>
					                    <%
					                   	    ISysPraiseAndNegativeService sysPraiseAndNegativeService = 
													(ISysPraiseAndNegativeService) SpringBeanUtil.getBean("sysPraiseAndNegativeService");
											Boolean isPraised = sysPraiseAndNegativeService.checkPraised(UserUtil.getUser().getFdId(),
																	request.getAttribute("fdId").toString(),"com.landray.kmss.km.forum.model.KmForumPost","1");
											pageContext.setAttribute("isPraised",isPraised);
											
											String praise = ResourceUtil.getString("sys-praise:sysPraiseMain.praise");
											String cancelPraise = ResourceUtil.getString("sys-praise:sysPraiseMain.cancel.praise");
											if(isPraised){
												pageContext.setAttribute("title",cancelPraise);
											}else{
												pageContext.setAttribute("title",praise);
											}
										%>
										<input type="hidden" name="isPraised" value="${isPraised}" id="check_isPraised">
                                        <div class="btn_good" id="btn_good">
                                            <span id="fix_praise" title="${title}">
                                               <a>${lfn:message('km-forum:kmForumPost.support')}（<span id="fix_praise_count">${(not empty topicPoster.docPraiseCount) ? (topicPoster.docPraiseCount) : '0'}</span>）</a>
                                            </span>
                                        </div>
                                    <kmss:auth
											requestURL="/km/forum/km_forum/kmForumPost.do?method=reply&fdForumId=${topic.kmForumCategory.fdId}"
											requestMethod="GET">
                                        <div class="btn_quickReply" id="btn_quickReply">
                                            <span><a>${lfn:message('km-forum:KmForumPost.notify.title.fastReply')}</a></span></div>
                                    </kmss:auth>
                                    </c:if>
                                  </div>
	                        </div>
                    </div>
                 </div>
              </div>
          </div>
          <!-- 标题结束 -->
          <script type="text/javascript">
                function initFixed(){
                	var top = $(".forum_content_titleBar").position().top, pos = $(".forum_content_titleBar").css("position");
                	LUI.$(window).scroll(function () {
    	                var scrolls = $(this).scrollTop();
    	                if (scrolls > top) { //如果滚动到页面超出了当前元素element的相对页面顶部的高度
    	                    $(".forum_titleBar_fixed").css("display", "block");
    	                } else {
    	                    $(".forum_titleBar_fixed").css({
    	                        "display": "none"
    	                    });
    	                }
    	            });	

                    LUI.$("#btn_quickReply").click(function(){
          	             if(window.location.hash=="#quickReplyContent"){
          	            	 window.location.hash="";
              	         }
      	                 window.location.hash="#quickReplyContent";
          	        });
                	
    	            LUI.$("#btn_good").click(function(){
        	             var fdModelId = "${fdId}";
        	             var fdModeName = "com.landray.kmss.km.forum.model.KmForumPost";
        	             sysFixedPraise(fdModelId,fdModeName);
        	        });
        	        
                };

                //点赞
            	function sysFixedPraise(fdModelId,fdModelName){
            		LUI.$.ajax({
            			type : "POST",
            			url :  "<c:url value='/sys/praise/sys_praise_main/sysPraiseMain.do?method=executePraise'/>",
            			data: {fdModelId: fdModelId,
            				   fdModelName: fdModelName},
            			dataType : 'text',
            			async: false,
            			success : function(data) {
            					   var praiseCount = parseInt($("#fix_praise_count")[0].innerHTML);
            					   var isPraised = $("#check_isPraised").val();
            						if("true" == isPraised){
            							$("#check_isPraised").val("false");
            							$("#fix_praise_count")[0].innerHTML = praiseCount - 1;
            							$("#fix_praise").attr("title","${ lfn:message('sys-praise:sysPraiseMain.praise')}");
            							
            							$("#aid_"+fdModelId+' #praise_count')[0].innerHTML = praiseCount - 1;
            							$("#aid_"+fdModelId+' #praise_icon').attr("class","sys_praise");
            							$("#check_"+fdModelId).innerHTML = praiseCount - 1;
            							$("#check_"+fdModelId).val("false");
            						}else{
            							$("#check_isPraised").val("true");
            							$("#fix_praise_count")[0].innerHTML = praiseCount + 1;
            							$("#fix_praise").attr("title","${ lfn:message('sys-praise:sysPraiseMain.cancel.praise')}");
            							
            							$("#aid_"+fdModelId+' #praise_count')[0].innerHTML = praiseCount + 1;
            							$("#aid_"+fdModelId+' #praise_icon').attr("class","sys_unpraise");
            							$("#check_"+fdModelId).innerHTML = praiseCount + 1;
            							$("#check_"+fdModelId).val("true");
            						}
            			},
            			error: function() {
            				
            			}		
            		});
            	}
          </script>          