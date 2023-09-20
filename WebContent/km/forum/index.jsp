<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.third.pda.util.PdaFlagUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%
	String redirectUrl = PdaFlagUtil.moduleHomeRedirectWhenMobile(request);
	if(StringUtil.isNotNull(redirectUrl)) {
%>
<script>
	location.href = '<%=redirectUrl%>';
</script>
<%
	} else {
%>
<template:include ref="default.view" showQrcode="false" sidebar="no">
	<template:replace name="title">
		<c:out value="${ lfn:message('km-forum:module.km.forum') }"></c:out>
	</template:replace>
	<template:replace name="head">  
	    <script type="text/javascript">
			seajs.use(['theme!list', 'theme!portal', 'theme!module']);	
		</script>
		<c:if test="${ (empty param['j_iframe'] || param['j_iframe'] != 'true') && ( empty param['j_rIframe'] && param['j_rIframe'] != 'true') }">
			<portal:header var-width="90%"/>
		</c:if>
	</template:replace>
	<template:replace name="content">
	<div data-lui-type="lui/title!Title" style="display: none;">
		<c:out value="${ lfn:message('km-forum:module.km.forum') }"></c:out>
	</div>  
	<%@ include file="/km/forum/forumCate_script.jsp"%>
	<script type="text/javascript">
	 
		    LUI.ready(function(){
		    	if(Com_Parameter['Lang']!=null && (Com_Parameter['Lang']=='zh-cn' || Com_Parameter['Lang']=='zh-hk')){
		    		//中文环境
		    	}else{
			    	//英文环境
		    	 	$('body').addClass('muilti_eng');
		    	}
		    	$('body').addClass('lui_forum_body tTemplate');
		    	$(".tempTB").css({'margin-top':$('.lui_portal_header').height()});
		    });
		 
		   window.replaceStr = function(testStr){
				var re=/<[^>]+>/gi;
				testStr=testStr.replace(re,'');
				return testStr;
			};
		    var _str = new Object();
			_str.textEllipsis = function(str, num) {
				if (str) {
					if (str.length * 2 <= num)
						return str;
					var rtnLength = 0;
					for (var i = 0; i < str.length; i++) {
						if (Math.abs(str.charCodeAt(i)) > 200)
							rtnLength = rtnLength + 2;
						else
							rtnLength++;
						if (rtnLength > num)
							return str.substring(0, i)
									+ (rtnLength % 2 == 0 ? ".." : "...");
					}
					return str;
				}
			};

			window.openUrl = function(object) {
				var url = object.getAttribute("href");
				if(url == ""){
					return;
				}
				window.open(url, "_blank");
			};

		    var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.forum.model.KmForumTopic";
</script>	    
    <link href="./resource/css/forum.css" rel="stylesheet" type="text/css" />
	<!-- 论坛头部 开始 -->
    <div class="lui_forum_header">
        <div class="lui_forum_headerC">
            <!-- 帖子导航 开始 -->
            <ui:dataview>
		       <ui:source type="AjaxXml">
					  {"url":"/sys/common/dataxml.jsp?s_bean=kmForumIndexDataService&type=newTopics"}
				</ui:source>
			    <ui:render type="Template">
			    {$<div class="lui_forum_Qicknav"> 
			    	<table>
						<tr class="row1">
							<td><span class="forum_nav_block1"><img src="./resource/images/forum_img_1.png" alt=""></span></td>
							<td>
								<div class="forum_post_color1" onclick="openUrl(this)" href="{%env.fn.formatUrl(data[0].href)%}">
									<p class="title">
										<a title="{%env.fn.formatText(data[1].subject)%}"> {%_str.textEllipsis(env.fn.formatText(data[0].subject),75)%} </a>
									</p>
									<p class="comment">$}
										if(data[0].count!=""){
				                        {$<a>
				                          {%data[0].count%}
				                         $}
				                        }
				                        {$</a>
									</p>
								</div>
							</td>
							<td><span class="forum_nav_block2"><img src="./resource/images/forum_img_2.png" alt=""></span></td>
							<td>
								<div class="forum_post_color2" onclick="openUrl(this)" href="{%env.fn.formatUrl(data[1].href)%}">
									<p class="title">
										<a title="{%env.fn.formatText(data[1].subject)%}"> {%_str.textEllipsis(env.fn.formatText(data[1].subject),75)%}</a>
									</p>
									<p class="comment">$}
										if(data[1].count!=""){
				                        {$<a>
				                          {%data[1].count%}
				                         $}
				                        }
				                        {$</a>
									</p>
								</div>
							</td>
						</tr>
						<tr class="row2">
							<td><div class="forum_post_color3" onclick="openUrl(this)" href="{%env.fn.formatUrl(data[2].href)%}">
									<p class="title">
										<a title="{%env.fn.formatText(data[2].subject)%}"> {%_str.textEllipsis(env.fn.formatText(data[2].subject),75)%} </a>
									</p>
									<p class="comment">$}
										if(data[2].count!=""){
				                        {$<a>
				                          {%data[2].count%}
				                         $}
				                        }
				                        {$</a>
									</p>
								</div>
							</td>
							<td colspan="2">
								<span class="forum_nav_block3"><img src="./resource/images/forum_img_3.png" alt=""></span>
							</td>
							<td><span class="forum_nav_block4"><img src="./resource/images/forum_img_4.png" alt=""></span></td>
						</tr>
						<tr class="row3">
							<td><span class="forum_nav_block5"><img src="./resource/images/forum_img_5.png" alt=""></span></td>
							<td><div class="forum_post_color4" onclick="openUrl(this)" href="{%env.fn.formatUrl(data[3].href)%}">
									<p class="title">
										<a title="{%env.fn.formatText(data[3].subject)%}"> {%_str.textEllipsis(env.fn.formatText(data[3].subject),75)%}</a>
									</p>
									<p class="comment">$}
										if(data[3].count!=""){
				                        {$<a>
				                          {%data[3].count%}
				                         $}
				                        }
				                        {$</a>
									</p>
								</div></td>
							<td><div class="forum_post_color5" onclick="openUrl(this)" href="{%env.fn.formatUrl(data[4].href)%}">
									<p class="title">
										<a title="{%env.fn.formatText(data[4].subject)%}"> {%_str.textEllipsis(env.fn.formatText(data[4].subject),75)%}</a>
									</p>
									<p class="comment">$}
										if(data[4].count!=""){
				                        {$<a>
				                          {%data[4].count%}
				                         $}
				                        }
				                        {$</a>
									</p>
								</div></td>
							<td><div class="forum_post_color6" onclick="openUrl(this)" href="{%env.fn.formatUrl(data[5].href)%}">
									<p class="title">
										<a title="{%env.fn.formatText(data[5].subject)%}"> {%_str.textEllipsis(env.fn.formatText(data[5].subject),75)%} </a>
									</p>
									<p class="comment">$}
										if(data[5].count!=""){
				                        {$<a>
				                          {%data[5].count%}
				                         $}
				                        }
				                        {$</a>
									</p>
								</div>
							</td>
						</tr>
					</table>
			     </div>$}
			   </ui:render>
			   <ui:event event="load">
				   if(window.del_load1!=null){
						window.del_load1.hide();
				   }
			   </ui:event>
		   </ui:dataview> 
            <!-- 帖子导航 结束 -->
            <!-- 用户信息 开始 --> 
              <ui:dataview>
		       <ui:source type="AjaxXml">
					  {"url":"/sys/common/dataxml.jsp?s_bean=kmForumIndexDataService&type=userInfo"}
				</ui:source>
			    <ui:render type="Template">
					  {$<div class="lui_forum_userinfo">
				              <div class="post_numSummary">
					               <p class="p1">
					                  <span>${lfn:message('km-forum:kmForumIndex.today')}：<em>{%data[0].count1%}</em></span>
					                  <span>${lfn:message('km-forum:kmForumIndex.yestoday')}：<em>{%data[0].count2%}</em></span>
					               </p>
					               <p class="p2">
					                  <span>${lfn:message('km-forum:kmForumIndex.topicCount')}：<em>{%data[0].count3%}</em></span>
					               </p>
				             </div>
		                     <div class="post_userinfo">
				                    <div class="basic_info">
				                        <div class="user_img"></div>
				                          <img src="{%env.fn.formatUrl(data[0].headurl)%}"/>
				                        <strong>{%data[0].name%}</strong>
				                      <!--<p class="sign">{%data[0].sign%}</p> -->
				                    </div>
				                    <div class="summary_info">
				                        <ul>
				                            <li><em>{%data[0].pcount%}</em><span>${lfn:message('km-forum:kmForumIndex.postCount')}</span></li>
				                            <li class="split"></li>
				                            <li><em>{%data[0].rcount%}</em><span>${lfn:message('km-forum:kmForumIndex.replyCount')}</span></li>
				                            <li class="split"></li>
				                            <li><em>{%data[0].score%}</em><span>${lfn:message('km-forum:kmForumIndex.score')}</span></li>
				                        </ul>
				                    </div>
				                    <div class="qucik_alink">
										<p class="p1"><a
											href="javascript:openCate('${ LUI_ContextPath }/km/forum/indexCriteria.jsp')" target="_self">${lfn:message('km-forum:kmForumIndex.newTopic')}</a><a
											href="javascript:openCate('${ LUI_ContextPath }/km/forum/indexCriteria.jsp?myTopic=create')" target="_self">${lfn:message('km-forum:kmForumIndex.myTopic')}</a></p>
										<p class="p2">
											 <kmss:auth requestURL="/km/forum/km_forum/kmForumPost.do?method=add&owner=true" requestMethod="GET">
						                            <a class="btn_quickPost" href="javascript:addDoc();"><i></i>${lfn:message('km-forum:kmForumIndex.quickPost')}</a>
						                     </kmss:auth>
						                 </p>
				                    </div>
		                	</div>
		            </div>$}
			   </ui:render>
			   <ui:event event="load">
				   if(window.del_load1!=null){
						window.del_load1.hide();
				   }
			   </ui:event>
		   </ui:dataview>
          <!-- 用户信息结束 -->   
        </div>
    </div>
    <script type="text/javascript">
			seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
				//新建
				window.addDoc = function() {
			       dialog.simpleCategoryForNewFile({modelName:"com.landray.kmss.km.forum.model.KmForumCategory",
				                                         url:"/km/forum/km_forum_cate/simple-category.jsp",
				                                    urlParam:"/km/forum/km_forum/kmForumPost.do?method=add&fdForumId=!{id}"});
				 
					};
				window.del_load1 = dialog.loading();
				window.openCate = function(url) {
					var mode = LUI.pageMode();
					if(url.indexOf('?') > -1) {
						url += '&mode='+mode;
					}else {
						url += '?mode='+mode;
					}
					LUI.pageOpen(url,'_iframe');
				};
			});
	</script>	
    <!-- 论坛板块信息开始 -->
    <div>
    	 <ui:dataview>
			<ui:source type="AjaxJson">
				{url:'/km/forum/km_forum_cate/kmForumCategory.do?method=main&isShowAll=true&filterDraft=true'}
			</ui:source>
			<ui:render type="Template">
				<c:import url="/km/forum/resource/tmpl/forumCate.jsp" charEncoding="UTF-8"></c:import>
			</ui:render>
		</ui:dataview> 
    </div>
    <!-- 论坛板块信息结束 -->
</template:replace>
</template:include>
<%}%>