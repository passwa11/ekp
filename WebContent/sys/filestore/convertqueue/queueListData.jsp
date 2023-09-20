<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>	
<%@ page import="com.landray.kmss.sys.log.util.ParseOperContentUtil" %>	
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page
	import="com.landray.kmss.sys.filestore.model.SysFileConvertQueue"%>
<%@page import="com.landray.kmss.sys.filestore.util.SysFileStoreUtil"%>

<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>

<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil"%>
<%
	boolean wpsoaassist=SysAttWpsoaassistUtil.isEnable();
%>

<list:data>
	<list:data-columns list="${queryPage.list }" var="sysFileConvertQueue"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		      ${status+1}
		</list:data-column>
		<list:data-column col="fdAttMainId"
			title="${ lfn:message('sys-filestore:fujianId') }" escape="false"
			style="text-align:center">
			<%
				SysFileConvertQueue sysFileConvertQueue = (SysFileConvertQueue) pageContext
									.getAttribute("sysFileConvertQueue");
							if (sysFileConvertQueue != null) {
								if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN
										&& sysFileConvertQueue.getFdDispenser().equals("remote")
										&& !"com.landray.kmss.sys.attachment.model.SysAttRtfData".equals(sysFileConvertQueue.getFdAttMainModelName())) {
									
									if(wpsoaassist&&("docx".equals(sysFileConvertQueue.getFdFileExtName().toLowerCase())||"doc".equals(sysFileConvertQueue.getFdFileExtName().toLowerCase())||"wps".equals(sysFileConvertQueue.getFdFileExtName().toLowerCase())))
										out.append("<a href='javascript:void(0)' onclick='openWpsOAAssit(\""+sysFileConvertQueue.getFdAttMainId()+"\",\"\")'>"
												+ sysFileConvertQueue.getFdAttMainId() + "</a>");
									else if(wpsoaassist&&("xls".equals(sysFileConvertQueue.getFdFileExtName().toLowerCase())||"xlsx".equals(sysFileConvertQueue.getFdFileExtName().toLowerCase())||"et".equals(sysFileConvertQueue.getFdFileExtName().toLowerCase())))
										out.append("<a href='javascript:void(0)' onclick='openEtOAAssit(\""+sysFileConvertQueue.getFdAttMainId()+"\")'>"
												+ sysFileConvertQueue.getFdAttMainId() + "</a>");
									else
										out.append("<a href='" + request.getAttribute("LUI_ContextPath")
											+ "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId="
											+ sysFileConvertQueue.getFdAttMainId() + "' target='_blank'>"
											+ sysFileConvertQueue.getFdAttMainId() + "</a>");
								} else {
									out.append(sysFileConvertQueue.getFdAttMainId());
								}
							}
			%>
		</list:data-column>
		<list:data-column col="fdModule"
			title="${ lfn:message('sys-filestore:suoshuyingyong') }"
			escape="false" style="text-align:center">
			<%
				SysFileConvertQueue sysFileConvertQueue = (SysFileConvertQueue) pageContext
									.getAttribute("sysFileConvertQueue");
							if (sysFileConvertQueue != null) {						
								if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN
										&& sysFileConvertQueue.getFdDispenser().equals("remote")
										&& StringUtil.isNotNull(sysFileConvertQueue.getFdModelUrl())) {
									out.append("<a href='" + request.getAttribute("LUI_ContextPath")
											+ sysFileConvertQueue.getFdModelUrl() + "' target='_blank'>"
											+ SysFileStoreUtil.getModule(sysFileConvertQueue.getFdAttMainId()) + "</a>");
								} else {
									out.append(SysFileStoreUtil.getModule(sysFileConvertQueue.getFdAttMainId()));
								}
							}
			%>
		</list:data-column>
		<list:data-column col="fdFileName"
			title="${ lfn:message('sys-filestore:wenjianmingcheng') }" escape="false" style="text-align:center">
			<%
				SysFileConvertQueue sysFileConvertQueue = (SysFileConvertQueue) pageContext.getAttribute("sysFileConvertQueue");
				if (sysFileConvertQueue != null) {
					// 开启三员后文件名显示50%
					if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
						out.append(ParseOperContentUtil.hideDisplayName(sysFileConvertQueue.getFdFileName()));
					} else {
						out.append(sysFileConvertQueue.getFdFileName());
					}
				}
			%>
		</list:data-column>
		
		<list:data-column col="linkFileName"
			title="${ lfn:message('sys-filestore:wenjianmingcheng') }"
			escape="false" style="text-align:center">
			<%
				SysFileConvertQueue sysFileConvertQueue = (SysFileConvertQueue) pageContext
									.getAttribute("sysFileConvertQueue");
							if (sysFileConvertQueue != null) {
								// 开启三员后文件名显示50%
								if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
									out.append(ParseOperContentUtil.hideDisplayName(sysFileConvertQueue.getFdFileName()));
								} else {
									//html文件不允许下载
									if(sysFileConvertQueue.getFdFileExtName() != null && "html".equals(sysFileConvertQueue.getFdFileExtName().toLowerCase())){
										out.append(sysFileConvertQueue.getFdFileName());
									}else if("com.landray.kmss.sys.attachment.model.SysAttRtfData".equals(sysFileConvertQueue.getFdAttMainModelName())){//富文本资源不允许下载
										out.append(sysFileConvertQueue.getFdFileName());
									}else{
										out.append("<a href='" + request.getAttribute("LUI_ContextPath")
											+ "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
											+ sysFileConvertQueue.getFdAttMainId() + "' title='"
											+ sysFileConvertQueue.getFdFileId() + "'>" + sysFileConvertQueue.getFdFileName()
											+ "</a>");
									}
								}
							}
			%>
		</list:data-column>
		<list:data-column col="fdConverterType"
						  title="${ lfn:message('sys-filestore:sysFilestore.conversion.type') }"
						  escape="false" style="text-align:center">
			<c:choose>
				<c:when test="${ sysFileConvertQueue.fdConverterType == 'yozo' }">
					<c:out value="${ lfn:message('sys-filestore:sysFilestore.conver.server.yozo') }"></c:out>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConverterType == 'wpsCenter' }">
					<c:out value="${ lfn:message('sys-filestore:sysFilestore.conversion.type.wpsCenter') }"></c:out>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConverterType == 'wps' }">
					<c:out value="${ lfn:message('sys-filestore:sysFilestore.conversion.type.wps') }"></c:out>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConverterType == 'dianju' }">
					<c:out value="${ lfn:message('sys-filestore:sysFilestore.conversion.type.dianju') }"></c:out>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConverterType == 'foxit' }">
					<c:out value="${ lfn:message('sys-filestore:sysFilestore.conversion.type.foxit') }"></c:out>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConverterType == 'shuke' }">
					<c:out value="${ lfn:message('sys-filestore:sysFilestore.conversion.type.shuke') }"></c:out>
				</c:when>
				<c:otherwise>
					<c:out value="${ lfn:message('sys-filestore:sysFilestore.conversion.type.aspose') }"></c:out>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column property="fdConverterKey"
			title="${ lfn:message('sys-filestore:sysFileConvertConfig.fdConverterKey') }"
			escape="false" style="text-align:center">
		</list:data-column>
		<list:data-column property="fdConvertNumber"
			title="${ lfn:message('sys-filestore:sysFileConvertQueue.fdConvertNumber') }"
			escape="false" style="text-align:center">
		</list:data-column>
		<list:data-column col="fdConvertStatus"
			title="${ lfn:message('sys-filestore:convertQueue.status') }"
			escape="false" style="text-align:center">
			<c:choose>
				<c:when test="${ sysFileConvertQueue.fdConvertStatus == 0 }">
					<c:out value="${ lfn:message('sys-filestore:convertStatus.0') }"></c:out><bean:message key="sysFileConvertConfig.clickShowLog" bundle="sys-filestore"/>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConvertStatus == 1 }">
					<c:out value="${ lfn:message('sys-filestore:convertStatus.1') }"></c:out><bean:message key="sysFileConvertConfig.clickShowLog" bundle="sys-filestore"/>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConvertStatus == 2 }">
					<c:out value="${ lfn:message('sys-filestore:convertStatus.2') }"></c:out><bean:message key="sysFileConvertConfig.clickShowLog" bundle="sys-filestore"/>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConvertStatus == 3 }">
					<c:out value="${ lfn:message('sys-filestore:convertStatus.3') }"></c:out><bean:message key="sysFileConvertConfig.clickShowLog" bundle="sys-filestore"/>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConvertStatus == 5 }">
					<c:out value="${ lfn:message('sys-filestore:convertStatus.5') }"></c:out><bean:message key="sysFileConvertConfig.clickShowLog" bundle="sys-filestore"/>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConvertStatus == 6 }">
					<c:out value="${ lfn:message('sys-filestore:convertStatus.6') }"></c:out><bean:message key="sysFileConvertConfig.clickShowLog" bundle="sys-filestore"/>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConvertStatus == 4 }">
					<c:out value="${ lfn:message('sys-filestore:convertStatus.4') }"></c:out><bean:message key="sysFileConvertConfig.clickShowLog" bundle="sys-filestore"/>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConvertStatus == 9 }">
					<c:out value="${ lfn:message('sys-filestore:convertStatus.9') }"></c:out><bean:message key="sysFileConvertConfig.clickShowLog" bundle="sys-filestore"/>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConvertStatus == 99 }">
					<c:out value="${ lfn:message('sys-filestore:convertStatus.99') }"></c:out><bean:message key="sysFileConvertConfig.clickShowLog" bundle="sys-filestore"/>
				</c:when>
				<c:when test="${ sysFileConvertQueue.fdConvertStatus == 999 }">
					<c:out value="${ lfn:message('sys-filestore:convertStatus.999') }"></c:out><bean:message key="sysFileConvertConfig.clickShowLog" bundle="sys-filestore"/>
				</c:when>
			</c:choose>
		</list:data-column>
		<list:data-column headerStyle="width:120px" col="fdStatusTime"
			title="${ lfn:message('sys-filestore:convertQueue.statusTime') }">
			<c:if test="${not empty sysFileConvertQueue.fdStatusTime }">
				<kmss:showDate value="${sysFileConvertQueue.fdStatusTime}"
					type="datetime"></kmss:showDate>
			</c:if>
		</list:data-column>
		<list:data-column headerStyle="width:120px" col="fdCreateTime"
			title="${ lfn:message('sys-filestore:convertQueue.inQueueTime') }">
			<c:if test="${not empty sysFileConvertQueue.fdCreateTime }">
				<kmss:showDate value="${sysFileConvertQueue.fdCreateTime}"
					type="datetime"></kmss:showDate>
			</c:if>
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }"></list:data-paging>
</list:data>