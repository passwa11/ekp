<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="template.person" pagewidth="90%">
	<!-- ref 对应id template.person -->
	<template:replace name="content">
		<%@ include file="../jsp/nav.jsp" %>
		<%@ include file="../jsp/changeheader.jsp" %>
		<%@ include file="../jsp/changetemplate.jsp" %>
		<ui:nonepanel layout="sys.ui.nonepanel.default" height="240" scroll="false" id="p_ac204c6b2e68af1d51ef">
			<portal:portlet title="数据统计部件"
				var-fdModelNames="com.landray.kmss.km.collaborate.model.KmCollaborateMain,com.landray.kmss.km.doc.model.KmDocKnowledge,com.landray.kmss.km.imeeting.model.KmImeetingMain,com.landray.kmss.km.review.model.KmReviewMain,com.landray.kmss.km.supervise.model.KmSuperviseMain,com.landray.kmss.sys.follow.model.SysFollowPersonConfig,com.landray.kmss.sys.task.model.SysTaskMain,com.landray.kmss.sys.bookmark.model.SysBookmarkMain">
				<ui:dataview format="sys.ui.stat">
					<ui:source ref="sys.person.stat.source"
						var-fdModelNames="com.landray.kmss.km.collaborate.model.KmCollaborateMain,com.landray.kmss.km.doc.model.KmDocKnowledge,com.landray.kmss.km.imeeting.model.KmImeetingMain,com.landray.kmss.km.review.model.KmReviewMain,com.landray.kmss.km.supervise.model.KmSuperviseMain,com.landray.kmss.sys.follow.model.SysFollowPersonConfig,com.landray.kmss.sys.task.model.SysTaskMain,com.landray.kmss.sys.bookmark.model.SysBookmarkMain">
					</ui:source>
					<ui:render ref="sys.ui.stat.default"></ui:render>
				</ui:dataview>
			</portal:portlet>
		</ui:nonepanel>
		<%@ include file="../jsp/portlet-personInfo.jsp" %>
		<%@ include file="../jsp/pic-menu.jsp" %>

	</template:replace>
</template:include>