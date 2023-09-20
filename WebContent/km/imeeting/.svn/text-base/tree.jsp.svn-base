<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.km.imeeting" bundle="km-imeeting"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5,defaultNode;
	n1 = LKSTree.treeRoot;
	
	
	<%-- 模块设置--%>
	n2 = n1.AppendURLChild(
		"<bean:message key="kmImeeting.tree.moduleConfig" bundle="km-imeeting" />"
	);
	<%-- 议题类别设置--%>
	n3 = n2.AppendURLChild(
		"<bean:message key="tree.issueCategorySetting" bundle="km-imeeting" />",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory&actionUrl=/km/imeeting/km_imeeting_topic_category/kmImeetingTopicCategory.do&formName=kmImeetingTopicCategoryForm&mainModelName=com.landray.kmss.km.imeeting.model.KmImeetingTopic&docFkName=fdTopicCategory" />"
	);
	<%-- 会议类别设置--%>
	n3=n2.AppendURLChild(
		"<bean:message key="kmImeeting.tree.categorySetting" bundle="km-imeeting" />",
		"<c:url value="/km/imeeting/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingTemplate&mainModelName=com.landray.kmss.km.imeeting.model.KmImeetingMain&categoryName=docCategory&templateName=fdTemplate&authReaderNoteFlag=2" />"
	);
	<%-- 卡片库--%>
	n2.AppendURLChild(
		"<bean:message key="kmImeeting.tree.templateSetting.card" bundle="km-imeeting" />",
		"<c:url value="/km/imeeting/km_imeeting_template/index.jsp" />"
	);
	
	<kmss:authShow roles="ROLE_KMIMEETING_SETTING">
	n2.AppendURLChild(
		"<bean:message key="tree.issueFlowTemp" bundle="km-imeeting" />",
		"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory&fdKey=mainTopic" />"
	);
	<%-- 会议安排流程模板--%>
	n2.AppendURLChild(
		"<bean:message key="kmImeeting.tree.mainMeetingFlow" bundle="km-imeeting" />",
		"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingTemplate&fdKey=ImeetingMain" />"
	);
	
	<%-- 会议纪要流程模板--%>
	n2.AppendURLChild(
		"<bean:message key="kmImeeting.tree.summaryFlow" bundle="km-imeeting" />",
		"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingTemplate&fdKey=ImeetingSummary" />"
	);		
	
	LKSTree.ExpandNode(n2);
	
	n2.AppendURLChild(
		"<bean:message key="tree.issueNumRule" bundle="km-imeeting" />",
		"<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingTopic" />"
	);
	<%-- 编号机制--%>
	n2.AppendURLChild(
			"<bean:message bundle="sys-number" key="sysNumber.config.tree.numberMain"/>",
			"<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingMain" />"
		);
		
		
	<%-- 基础设置--%>	
	n2.AppendURLChild(
		"<bean:message key="kmImeeting.tree.moduleBaseConflict" bundle="km-imeeting" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.km.imeeting.model.KmImeetingConfig" />"
	);
	
	<!-- 坐席模板设置 -->
	n2.AppendURLChild(
		"<bean:message key="kmImeetingSeatTemplate.config" bundle="km-imeeting" />",
		"<c:url value="/km/imeeting/km_imeeting_seat_template/index.jsp" />"
	);
	
	<%-- 列表显示设置--%>	
	n2.AppendURLChild(
		"<bean:message key="kmImeeting.tree.listShow" bundle="km-imeeting" />",
		"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingMain"/>"
	);
	
	n2.AppendURLChild(
		"<bean:message key="kmImeetingSummary.tree.listShow" bundle="km-imeeting" />",
		"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary"/>"
	);
	n2.AppendURLChild(
		"<bean:message key="kmImeetingTopic.tree.listShow" bundle="km-imeeting" />",
		"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingTopic"/>"
	);
	</kmss:authShow>
	
	
	<%-- 会议室管理设置--%>
	n2 = n1.AppendURLChild(
		"<bean:message key="kmImeeting.tree.place" bundle="km-imeeting" />"
	);
	
	<%-- 会议室分类 --%>
	n2.AppendURLChild(
		"<bean:message key="table.kmImeetingResCategory" bundle="km-imeeting" />",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.km.imeeting.model.KmImeetingResCategory&actionUrl=/km/imeeting/km_imeeting_res_category/kmImeetingResCategory.do&formName=kmImeetingResCategoryForm&mainModelName=com.landray.kmss.km.imeeting.model.KmImeetingRes&docFkName=docCategory" />"
	);
	
	<%-- 会议室信息 --%>
	defaultNode = n3 = n2.AppendURLChild(
		"<bean:message key="table.kmImeetingRes" bundle="km-imeeting" />",
		"<c:url value="/km/imeeting/km_imeeting_res/index.jsp" />"
	);	
	
	n3.AppendSimpleCategoryData(
    	"com.landray.kmss.km.imeeting.model.KmImeetingResCategory",
    	"<c:url value="/km/imeeting/km_imeeting_res/index.jsp?docCategoryId=!{value}&authReaderNoteFlag=2&dataWithAdmin=true" />"
    );
	
	<%-- 会议辅助设备 --%>
	 n2.AppendURLChild(
		"<bean:message key="table.kmImeetingEquipment" bundle="km-imeeting" />",
		"<c:url value="/km/imeeting/km_imeeting_equipment/index.jsp" />"
	);
	
	<%-- 会议辅助服务 --%>
	 n2.AppendURLChild(
		"<bean:message key="table.kmImeetingDevice" bundle="km-imeeting" />",
		"<c:url value="/km/imeeting/km_imeeting_device/index.jsp" />"
	);
	
	<kmss:authShow roles="ROLE_KMIMEETING_RES_READER">
	<%-- 会议室查询 --%>
	 defaultNode = n2.AppendURLChild(
		"<bean:message key="kmImeeting.tree.listUse" bundle="km-imeeting" />",
		"<c:url value="/km/imeeting/km_imeeting_res/index_listuse.jsp" />"
	);
	</kmss:authShow>
	
	LKSTree.ExpandNode(n2);
	
	<%-- 文档维护 --%>
	n6 = n1.AppendURLChild("<bean:message key="tree.sysCategory.maintains" bundle="sys-category" />")
	
	<%-- 会议安排--%>
	n7 = n6.AppendURLChild("<bean:message key="kmImeeting.tree.myHandleMeeting" bundle="km-imeeting" />")
	n7.authType="01";
	<kmss:authShow roles="ROLE_KMIMEETING_OPTALL">
		n7.authRole="optAll";
	</kmss:authShow>
	n7.AppendCategoryDataWithAdmin("com.landray.kmss.km.imeeting.model.KmImeetingTemplate","<c:url value="/km/imeeting/km_imeeting_main/kmImeetingMain_manageList.jsp?categoryId=!{value}"/>","<c:url value="/km/imeeting/km_imeeting_main/kmImeetingMain_manageList.jsp?categoryId=!{value}"/>");
	
	<%-- 会议纪要安排--%>
	n8 = n6.AppendURLChild("<bean:message key="kmImeeting.tree.myHandleSummary" bundle="km-imeeting" />")
	n8.authType="01";
	<kmss:authShow roles="ROLE_KMIMEETING_OPTALL">
		n8.authRole="optAll";
	</kmss:authShow>
	n8.AppendCategoryDataWithAdmin ("com.landray.kmss.km.imeeting.model.KmImeetingTemplate","<c:url value="/km/imeeting/km_imeeting_summary/kmImeetingSummary_manageList.jsp?categoryId=!{value}"/>","<c:url value="/km/imeeting/km_imeeting_summary/kmImeetingSummary_manageList.jsp?categoryId=!{value}"/>");
	
	<%-- 会议议题--%>
	n9 = n6.AppendURLChild("<bean:message key="kmImeeting.tree.myHandleTopic" bundle="km-imeeting" />")
	n9.authType="01";
	<kmss:authShow roles="ROLE_KMIMEETING_OPTALL">
		n9.authRole="optAll";
	</kmss:authShow>
	n9.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory","<c:url value="/km/imeeting/km_imeeting_topic/kmImeetingTopic_manageList.jsp?categoryId=!{value}"/>","<c:url value="/km/imeeting/km_imeeting_topic/kmImeetingTopic_manageList.jsp?categoryId=!{value}" />");
	
	
	<%-- 会议室预约 --%>
	n10 = n6.AppendURLChild("<bean:message key="table.kmImeetingBook" bundle="km-imeeting" />","<c:url value="/km/imeeting/km_imeeting_book/index.jsp" />");
	
	LKSTree.ExpandNode(n6);
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
 </template:replace>
</template:include>