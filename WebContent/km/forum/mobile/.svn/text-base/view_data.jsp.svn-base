<%@page import="com.landray.kmss.km.forum.model.KmForumConfig"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="com.landray.kmss.km.forum.model.KmForumTopic"%>
<%@page import="com.landray.kmss.km.forum.model.KmForumPost"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<%
			KmForumTopic topic = (KmForumTopic)request.getAttribute("forumTopic");
			pageContext.setAttribute("_docCreatetime",DateUtil.convertDateToString(topic.getDocCreateTime(),null)); 
			Integer hotReplyCount = Integer.parseInt(new KmForumConfig().getHotReplyCount()); 
	     	request.setAttribute("hotReplyCount",hotReplyCount);
	     	int type = MobileUtil.getClientType(request);
	     	String isDingMb = "false";
	     	if(type == 11){
	     		//钉钉移动客户端
	     		isDingMb = "true";
	     	}
	     	request.setAttribute("isDingMb",isDingMb);
	     		     	
	   %>

<ui:ajaxtext>
	<div data-dojo-block="title">
		<c:out value="${forumTopic.kmForumCategory.fdName}"></c:out>
	</div>
	<div data-dojo-block="content">
		<style type="text/css">
			/* sys/mobile/css/themes/default/common-tiny.css，覆盖斜体样式 #168833 */
			i,em{
				font-style: italic;
			}
		</style>
		<script type="text/javascript">
			window.fdForumId = "${forumTopic.kmForumCategory.fdId}";
			window.fdTopicId = "${forumTopic.fdId}";
			require(
					[ "mui/device/adapter" ],
					function(adapter) {
						<%--推荐--%>
						window.introduceOperation = function(fdTopicId) {
							var url = "${LUI_ContextPath}/km/forum/km_forum/kmForumTopic.do?method=introduce&fdId="
									+ fdTopicId;
							adapter.open(url, '_self');
						}
					});
		</script>
		
	   <c:if test="${forumTopic.fdStatus=='40' }">
			<ul id="tabBarBottom" data-dojo-type="mui/tabbar/TabBar" fixed="bottom" >
		    	<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
				   <c:param name="fdModelName" value="com.landray.kmss.km.forum.model.KmForumTopic"/>
				   <c:param name="fdModelId" value="${forumTopic.fdId}"/>
				   <c:param name="fdSubject" value="${forumTopic.docSubject}"/>
				   <c:param name="showOption" value="label"/>
			    </c:import>
			    <div id="introduceButton" data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'',text:'推荐',colSize:4,href:'javascript:introduceOperation(\'${forumTopic.fdId}\');' ">
		    		<bean:message bundle="km-forum" key="kmForumTopic.introduce.button"/>
		    	</div>
		    	<div id="breakPageButton" data-dojo-type="km/forum/mobile/resource/js/view/ForumBreakPageButton"></div>
		    	<div id="sortPageButton" data-dojo-type="km/forum/mobile/resource/js/view/ForumSortButton" data-dojo-props="sortType:'desc'"></div>
			</ul>
		</c:if>
		<c:if test="${forumTopic.fdStatus!='40'}">
			<ul id="tabBarBottom" data-dojo-type="mui/tabbar/TabBar" fixed="bottom" <c:if test="${forumTopic.fdStatus == '30'}">data-dojo-props='fill:"grid"'</c:if>>
			  	<c:if test="${forumTopic.fdStatus == '30'}">
				  	<li class="muiBtnNext muiForumParseBtn" data-dojo-type="mui/tabbar/TabBarButton"
				  		data-dojo-mixins="km/forum/mobile/resource/js/view/ForumTopicParseMixin"
						data-dojo-props='modelId:"${forumTopic.forumPosts[0].fdId}",
						count:${forumTopic.forumPosts[0].docPraiseCount!=null?forumTopic.forumPosts[0].docPraiseCount:0},modelName:"com.landray.kmss.km.forum.model.KmForumPost"'>
						${forumTopic.forumPosts[0].docPraiseCount!=null?forumTopic.forumPosts[0].docPraiseCount:0}
					</li>
					<kmss:auth
						requestURL="/km/forum/km_forum/kmForumPost.do?method=reply&fdForumId=${forumTopic.kmForumCategory.fdId}" requestMethod="GET">
					  	<li class="muiBtnNext" data-dojo-type="mui/tabbar/TabBarButton"
					  		data-dojo-mixins='km/forum/mobile/resource/js/view/ForumTopicReplayMixin,km/forum/mobile/resource/js/view/ForumReplyMixin'
							data-dojo-props='count:${forumTopic.fdReplyCount},text:"${lfn:message('km-forum:kmForumTopic.fdReplyCount')}"'>
						</li>
					</kmss:auth>
					 <kmss:ifModuleExist path="/third/ding/">
						 <c:if test="${isDingMb == 'true'}">
							 <c:import url="/third/ding/third_ding_share/ding_share.jsp" charEncoding="UTF-8">
								   <c:param name="fdModelName" value="com.landray.kmss.km.forum.model.KmForumTopic"/> 
								   <c:param name="fdModelId" value="${forumTopic.fdId}"/>  
								   <c:param name="fdSubject" value="${forumTopic.docSubject}"/>   
								   <c:param name="reqUrl" value="/km/forum/km_forum/kmForumPost.do?method=view&fdTopicId=${forumTopic.fdId}"/>
								   <c:param name="fdContentPro" value="forumPosts[fdFloor=1].docContent"/>
							  </c:import>
						 </c:if>
					</kmss:ifModuleExist>
					
				</c:if>
			    <li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'fontmuis muis-more',text:'更多'">
			        <c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
					   <c:param name="fdModelName" value="com.landray.kmss.km.forum.model.KmForumTopic"/>
					   <c:param name="fdModelId" value="${forumTopic.fdId}"/>
					   <c:param name="fdSubject" value="${forumTopic.docSubject}"/>
					   <c:param name="showOption" value="label"/>
				    </c:import>
				    <div id="introduceButton" data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'',text:'推荐',colSize:4,href:'javascript:introduceOperation(\'${forumTopic.fdId}\');' ">
			    		<bean:message bundle="km-forum" key="kmForumTopic.introduce.button"/>
			    	</div>
			    	<div id="breakPageButton" data-dojo-type="km/forum/mobile/resource/js/view/ForumBreakPageButton" data-dojo-props="icon1:''"></div>
			    	<div id="sortPageButton" data-dojo-type="km/forum/mobile/resource/js/view/ForumSortButton" data-dojo-props="icon1:'',sortType:'desc'"></div>
				</li>
			  </ul>  				
		</c:if>
		
		
   		<div id="scrollView" data-dojo-type="mui/list/StoreElementScrollableView" 
   			data-dojo-mixins="km/forum/mobile/resource/js/view/ForumReloadMixin">
   			<div data-dojo-type="mui/panel/Content">
   				<div id="forumTopicHead"
					data-dojo-type="km/forum/mobile/resource/js/view/ForumTopicHead"
					data-dojo-props="label:'<c:out value="${lfn:escapeJs(forumTopic.docSubject)}"/>',
						cateName:'${forumTopic.kmForumCategory.fdName}',
						cateId:'${forumTopic.kmForumCategory.fdId}',
						count:${forumTopic.fdHitCount},
						replyCount:${forumTopic.fdReplyCount },
						created:'${_docCreatetime}',
						top:${forumTopic.fdSticked},
						hot:${forumTopic.fdReplyCount>=hotReplyCount},
						pinked:${forumTopic.fdPinked},
						closed:${forumTopic.fdStatus=='40'}">
				</div>
				<ul id="jsonStoreList" data-dojo-type="km/forum/mobile/resource/js/view/ForumJsonStoreList"   
			    	data-dojo-mixins="km/forum/mobile/resource/js/view/ForumPostItemListMixin,km/forum/mobile/resource/js/view/ForumReplyMixin"
			    	data-dojo-props="url:'/km/forum/mobile/kmForumPost.do?method=listReplays&fdTopicId=${forumTopic.fdId}'">
				</ul>
   			</div>
		</div>
	</div>
</ui:ajaxtext>
