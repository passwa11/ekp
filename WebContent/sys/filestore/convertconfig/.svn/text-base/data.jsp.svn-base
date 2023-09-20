<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysFileConvertConfig" list="${queryPage.list }"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		      ${status+1}
		</list:data-column>
		<list:data-column property="fdFileExtName"
			title="${ lfn:message('sys-filestore:sysFileConvertConfig.fdFileExtName') }"
			escape="false" style="text-align:center">
		</list:data-column>
		<list:data-column property="fdModelName"
			title="${ lfn:message('sys-filestore:sysFileConvertConfig.fdModelName') }"
			escape="false" style="text-align:center">
		</list:data-column>
		<list:data-column col="fdConverterKey"
			title="${ lfn:message('sys-filestore:sysFileConvertConfig.fdConverterKey.Type') }"
			escape="false" style="text-align:center">
			<c:choose>
			<c:when
					test="${ sysFileConvertConfig.fdConverterKey == 'image2thumbnail' }">
					<c:out
						value="${ lfn:message('sys-filestore:sysFileConvertConfig.fdConverterKey.image2thumbnail') }"></c:out>
				</c:when>
				<c:when test="${ sysFileConvertConfig.fdConverterKey == 'toJPG' }">
						<c:out
							value="JPG"></c:out>
				</c:when>
				<c:when test="${ sysFileConvertConfig.fdConverterKey == 'toHTML' }">
						<c:out
							value="HTML"></c:out>
				</c:when>
				<c:when test="${ sysFileConvertConfig.fdConverterKey == 'toOFD' }">
						<c:out
							value="OFD"></c:out>
				</c:when>
				<c:when test="${ sysFileConvertConfig.fdConverterKey == 'toPDF' }">
						<c:out
							value="PDF"></c:out>
				</c:when>
				<c:when test="${ sysFileConvertConfig.fdConverterKey == 'videoToMp4' }">
						<c:out
							value="MP4"></c:out>
				</c:when>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdStatus"
			title="${ lfn:message('sys-filestore:sysFileConvertConfig.fdStatus') }"
			escape="false" style="text-align:center">
			<c:choose>
				<c:when test="${ sysFileConvertConfig.fdStatus == '1' }">
					<c:out
						value="${ lfn:message('sys-filestore:sysFileConvertConfig.fdStatus.1') }"></c:out>
				</c:when>
				<c:otherwise>
					<c:out
						value="${ lfn:message('sys-filestore:sysFileConvertConfig.fdStatus.0') }"></c:out>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdConverterType"
			title="${ lfn:message('sys-filestore:sysFileConvertConfig.fdConverterType') }"
			escape="false" style="text-align:center">
			<c:if
				test="${ sysFileConvertConfig.fdConverterKey != 'image2thumbnail' }">
					<c:choose>
						<c:when test="${ sysFileConvertConfig.fdConverterType == 'yozo' }">
							<c:out
								value="${ lfn:message('sys-filestore:sysFilestore.conver.server.yozo') }"></c:out>
						</c:when>
						<c:when test="${ sysFileConvertConfig.fdConverterType == 'wps' }">
							<c:out
								value="${ lfn:message('sys-filestore:sysFilestore.conversion.wps.online.ofd') }"></c:out>
						</c:when>
						<c:when test="${ sysFileConvertConfig.fdConverterType == 'wpsCenter' }">
							<c:out
								value="${ lfn:message('sys-filestore:sysFilestore.conversion.wps.center.ofd') }"></c:out>
						</c:when>
						<c:when test="${ sysFileConvertConfig.fdConverterType == 'skofd' }">
							<c:out
								value="${ lfn:message('sys-filestore:sysFilestore.conversion.sk.ofd') }"></c:out>
						</c:when>
						<c:when test="${ sysFileConvertConfig.fdConverterType == 'dianju' }">
							<c:out
									value="${ lfn:message('sys-filestore:sysFilestore.conversion.dianju.ofd') }"></c:out>
						</c:when>
						<c:when test="${ sysFileConvertConfig.fdConverterType == 'foxit' }">
							<c:out
									value="${ lfn:message('sys-filestore:sysFilestore.conver.server.foxit') }"></c:out>
						</c:when>
						<c:otherwise>
							<c:out
								value="ASPOSE"></c:out>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if
				test="${ sysFileConvertConfig.fdConverterKey == 'image2thumbnail' }">
				<c:out
					value="${ lfn:message('sys-filestore:sysFileConvertConfig.fdConverterType.notneed') }"></c:out>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width200" col="operations"
			title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth
						requestURL="/sys/filestore/sys_filestore/sysFileConvertConfig.do?method=edit&fdId=${sysFileConvertConfig.fdId}"
						requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt"
							href="javascript:editConvertConfig('${sysFileConvertConfig.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
						<kmss:auth
							requestURL="/sys/filestore/sys_filestore/sysFileConvertConfig.do?method=delete&fdId=${sysFileConvertConfig.fdId}"
							requestMethod="POST">
							<!-- 删除 -->
							<a class="btn_txt"
								href="javascript:delConfigs('${sysFileConvertConfig.fdId}')">${lfn:message('button.delete')}</a>
						</kmss:auth>
<%--					 </c:if>--%>
					<kmss:authShow roles="SYSROLE_ADMIN">
						<c:choose>
							<c:when test="${ sysFileConvertConfig.fdStatus == '1' }">
								<a class="btn_txt"
									href="javascript:changeConvertConfigStatus('disablechoose', '${sysFileConvertConfig.fdId}');">${lfn:message('sys-filestore:filestore.convertconfig.disablechoose')}</a>
							</c:when>
							<c:when test="${ sysFileConvertConfig.fdStatus == '0' }">
								<a class="btn_txt"
									href="javascript:changeConvertConfigStatus('enablechoose', '${sysFileConvertConfig.fdId}');">${lfn:message('sys-filestore:filestore.convertconfig.enablechoose')}</a>
							</c:when>
						</c:choose>
					</kmss:authShow>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>