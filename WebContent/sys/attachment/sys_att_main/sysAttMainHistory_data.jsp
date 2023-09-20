<%@page
	import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.common.service.IBaseService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdVersion" style="width:20px"
			title="${lfn:message('sys-attachment:sysAttMain.fdVersion') }" >
			<%
				Object item = pageContext.getAttribute("item");

				if (item != null) {
					SysAttMain main = (SysAttMain) item;
					if(main.getFdVersion() != null && main.getFdVersion() != 0)
						out.print("V" + main.getFdVersion());
					else
						out.print("V1");
				}
			%>
		</list:data-column>
		<list:data-column property="fdFileName"
			title="${lfn:message('sys-attachment:sysAttRecovery.fdName') }">
		</list:data-column>

		<c:if test="${empty JsParam.hideNum  }">
			<list:data-column col="downloadSum"
				title="${lfn:message('sys-attachment:sysAttMain.downloadSum') }"
				escape="false">
				<a class="luiAttHref" onclick="downloadClick('${item.fdId}')"
					href="javascript:;">${item.downloadSum}</a>
			</list:data-column>

			<list:data-column col="fdBorrowCount"
				title="${lfn:message('sys-attachment:sysAttMain.fdBorrowCount') }"
				escape="false">
				<a class="luiAttHref" onclick="borrowClick('${item.fdId}')"
					href="javascript:;">${item.fdBorrowCount}</a>
			</list:data-column>
		</c:if>
		<list:data-column col="fdSize" style="width:40px"
			title="${lfn:message('sys-attachment:sysAttMain.button.filesize') }"
			escape="false">

			<%
				Object item = pageContext.getAttribute("item");

							if (item != null) {
								SysAttMain main = (SysAttMain) item;
								out.print(SysAttViewerUtil
										.convertFileSize(main.getFdSize()));
							}
			%>
		</list:data-column>
		<list:data-column col="fdCreatorId" style="width:80px"
			title="${lfn:message('sys-attachment:sysAttMain.fdCreatorId') }"
			escape="false">
			<ui:person personId="${item.fdCreatorId }" />
		</list:data-column>
		<list:data-column col="fdUploaderId" style="width:40px"
			title="${lfn:message('sys-attachment:sysAttMain.fdCreatorId') }"
			escape="false">
			<ui:person personId="${item.fdUploaderId }" />
		</list:data-column>		

		<c:if test="${not empty JsParam.hideNum  }">
			<list:data-column property="docCreateTime"
				title="${lfn:message('sys-attachment:sysAttMain.docCreateTime') }">
			</list:data-column>
		</c:if>
		<list:data-column property="fdUploadTime" style="width:40px"
			title="${lfn:message('sys-attachment:sysAttMain.docCreateTime') }">
		</list:data-column>

		<list:data-column col="mainName"
			title="${lfn:message('sys-attachment:sysAttMain.fdMainName') }"
			style="text-align:left" escape="false">
			<%
				Object item = pageContext.getAttribute("item");

							if (item != null) {
								SysAttMain main = (SysAttMain) item;
								String modelName = main.getFdModelName();
								String modelId = main.getFdModelId();

								if (StringUtil.isNotNull(modelName)) {
									ISysAttMainCoreInnerService service =
											(ISysAttMainCoreInnerService) SpringBeanUtil
													.getBean("sysAttMainService");
									String[] urlAndName=null;
									try{
										urlAndName = service.getMainUrlAndName(modelId, modelName);
									}catch(Exception e){
										e.printStackTrace();
									}
									if (urlAndName != null && StringUtil
											.isNotNull(urlAndName[1])) {
										out.print("<a href='"
												+ request.getContextPath()
												+ urlAndName[0]
												+ "' class='com_subject' target='_blank'>"
												+ urlAndName[1] + "</a>");
									}
								}

							}
			%>
		</list:data-column>

		<list:data-column col="module" title="${lfn:message('sys-attachment:sysAttMain.fdModule') }" style="width:100px">
			<%
				Object item = pageContext.getAttribute("item");

							if (item != null) {
								SysAttMain main = (SysAttMain) item;
								String modelName = main.getFdModelName();
								if (StringUtil.isNotNull(modelName)) {
									ISysAttMainCoreInnerService service =
											(ISysAttMainCoreInnerService) SpringBeanUtil
													.getBean("sysAttMainService");

									String moduleName =
											service.getMainModuleName(modelName);
									if (StringUtil.isNotNull(moduleName)) {
										out.print(moduleName);
									}
								}
							}
			%>
		</list:data-column>

	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
