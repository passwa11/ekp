<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns list="${queryPage.list }" var="remoteClient"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		      ${status+1}
		</list:data-column>
		<list:data-column headerStyle="width:100px" col="operations" title="${lfn:message('sys-filestore:client.operations') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w" style="position: relative;display: inline-block;">
				<div class="conf_btn_edit">
					<kmss:authShow roles="SYSROLE_ADMIN">
						<a class="btn_txt" href="javascript:close('${remoteClient.fdId}')">${lfn:message('sys-filestore:client.operations.close') }</a>
						<%-- <a class="btn_txt" href="javascript:reboot('${remoteClient.fdId}')">${lfn:message('sys-filestore:client.operations.reboot') }</a> --%>
						<c:choose>
							<c:when test="${ remoteClient.avail == false }">
								<a class="btn_txt" href="javascript:able('${remoteClient.fdId}')">${lfn:message('sys-filestore:client.operations.able') }</a>
							</c:when>
							<c:when test="${ remoteClient.avail == true }">
								<a class="btn_txt" href="javascript:disable('${remoteClient.fdId}')">${lfn:message('sys-filestore:client.operations.disable') }</a>
							</c:when>
						</c:choose>
						<a class="btn_txt" href="javascript:config('${remoteClient.fdId}')">${lfn:message('sys-filestore:client.operations.config') }</a>
					</kmss:authShow>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		<list:data-column headerStyle="width:20px;" title="${ lfn:message('sys-filestore:sysFileConvertConfig.fdStatus') }" col="isAvail">
			<c:choose>
				<c:when test="${remoteClient.avail==true }">${ lfn:message('message.yes') }</c:when>
				<c:otherwise>${ lfn:message('message.no') }</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerStyle="width:80px" title="${ lfn:message('sys-filestore:sysFileConverter.fdConverterType') }" col="fdConverterType">
			<c:choose>
				<c:when test="${remoteClient.converterKey=='image2thumbnail' }">
					<bean:message bundle="sys-filestore"
						key="converterList.client.type.imageCompress" />
				</c:when>
				<c:when test="${remoteClient.converterKey=='toHTML' }">
					<bean:message bundle="sys-filestore"
						key="converterList.client.type.toHTML" />
				</c:when>
				<c:when test="${remoteClient.converterKey=='videoToFlv' }">
					<bean:message bundle="sys-filestore"
						key="converterList.client.type.videoToFlv" />
				</c:when>
				<c:when test="${remoteClient.converterKey=='videoToMp4' }">
					<bean:message bundle="sys-filestore"
						key="converterList.client.type.videoToFlv" />
				</c:when>				
				<c:when test="${remoteClient.converterKey=='wordToPic' }">
					<bean:message bundle="sys-filestore"
						key="converterList.client.type.wordToPic" />
				</c:when>
				<c:when test="${remoteClient.converterKey=='toSwf' }">
					<bean:message bundle="sys-filestore"
						key="converterList.client.type.toSwf" />
				</c:when>
				<c:when test="${remoteClient.converterKey=='toPDF' }">
					<bean:message bundle="sys-filestore"
						key="converterList.client.type.toPDF" />
				</c:when>
			</c:choose>
		</list:data-column>
		<list:data-column headerStyle="width:60px" title="${ lfn:message('sys-filestore:sysFileConverter.clientVersion') }" property="version"></list:data-column>
		<list:data-column headerStyle="width:120px" title="${ lfn:message('sys-filestore:sysFileConverter.fdPid') }" col="identity">${remoteClient.processID}@${remoteClient.clientIP}</list:data-column>
		<list:data-column headerStyle="width:60px" title="${ lfn:message('sys-filestore:sysFileConverter.clientPort') }" property="clientPort"></list:data-column>
		<list:data-column headerStyle="width:100px" title="${ lfn:message('sys-filestore:sysFileConverter.fdConverterFullKey') }" property="converterFullKey"></list:data-column>
		<list:data-column headerStyle="width:20px" title="${ lfn:message('sys-filestore:sysFileConverter.fdCapacity') }" property="taskCapacity"></list:data-column>
		<list:data-column headerStyle="width:20px" title="${ lfn:message('sys-filestore:convertStatus.1') }" property="taskConvertingNum"></list:data-column>
		<list:data-column headerStyle="width:300px" title="${ lfn:message('sys-filestore:sysFileConverter.converterVersion') }" property="converterVersion"></list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }"></list:data-paging>
</list:data>