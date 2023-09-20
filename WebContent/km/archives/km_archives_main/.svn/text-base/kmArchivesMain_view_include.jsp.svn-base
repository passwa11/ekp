<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService"%>
<%@page import="com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm"%>
<%@page import="com.landray.kmss.km.archives.service.IKmArchivesMainService"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.landray.kmss.util.ArrayUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesDetails"%>
<%@page import="com.landray.kmss.km.archives.forms.KmArchivesMainForm"%>
<%@page import="com.landray.kmss.km.archives.util.KmArchivesUtil"%>
<%@page import="java.util.Calendar"%>
<%
	KmArchivesMainForm mainForm = (KmArchivesMainForm)request.getAttribute("kmArchivesMainForm");
	String fdId = mainForm.getFdId();
	KmArchivesDetails detail = KmArchivesUtil.getBorrowDetail(fdId);
	boolean isValidity = KmArchivesUtil.isValidity(fdId);
	boolean canBorrow = KmArchivesUtil.isCanBorrowByTemplate(fdId);
	pageContext.setAttribute("isValidity", isValidity);
	pageContext.setAttribute("canBorrow", canBorrow);
	pageContext.setAttribute("isBorrowed", detail != null);
	pageContext.setAttribute("currentId",UserUtil.getUser().getFdId());
	pageContext.setAttribute("isWork",KmArchivesUtil.isWorkUser(mainForm.getFdId()));
	pageContext.setAttribute("viewAll", UserUtil.checkRole("ROLE_KMARCHIVES_VIEW_ALL"));
	boolean isFileReader = false; //是否是文件级可阅读者
	if(StringUtil.isNotNull(mainForm.getAuthFileReaderIds())) {
		String[] readerIds = mainForm.getAuthFileReaderIds().split(";");
		isFileReader = ArrayUtil.isListIntersect(UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds(), Arrays.asList(readerIds));
	}
	pageContext.setAttribute("isFileReader", isFileReader);
	boolean isConverting = false; //是否有正在转换中的附件
	ISysFileConvertQueueService queueService = (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
	isConverting = !ArrayUtil.isEmpty(queueService.isAllSuccess(fdId, "toPDF", "aspose"));
	pageContext.setAttribute("isConverting", isConverting);
%>

