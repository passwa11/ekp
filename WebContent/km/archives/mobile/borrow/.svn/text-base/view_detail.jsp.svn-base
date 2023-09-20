<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div data-dojo-type="mui/table/ScrollableHContainer">
	<div data-dojo-type="mui/table/ScrollableHView">
		<table class="detailTableNormal" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="detailTableNormalTd">
					<table class="muiNormal selectedArchTable" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>${lfn:message('page.serial')}</td>
							<td> ${lfn:message('km-archives:kmArchivesMain.docSubject')}</td>
							<td> ${lfn:message('km-archives:kmArchivesMain.docTemplate')}</td>
							<td> ${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}</td>
							<td> ${lfn:message('km-archives:kmArchivesDetails.fdAuthorityRange')}</td>
							<td> ${lfn:message('km-archives:kmArchivesDetails.fdReturnDate')}</td>
						</tr>
						<c:forEach items="${kmArchivesBorrowForm.fdBorrowDetail_Form}" var="fdBorrowDetail_FormItem" varStatus="vstatus">
							<tr KMSS_IsContentRow="1" align="center" class="selectedArchRow">
								<td KMSS_IsRowIndex=1 class="muiCheckTD">
									${vstatus.index+1}
								</td>
								<td>
									<input type="hidden" name="fdBorrowDetail_Form[${vstatus.index}].fdId" value="${fdBorrowDetail_FormItem.fdId}" /> 
									<a style="color:#007aff; font-size: 1.4rem;" target="_self" href="javascript:void(0)" onclick="window.open('${KMSS_Parameter_ContextPath}km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId=${fdBorrowDetail_FormItem.fdArchives.fdId}&_mobile=1', '_self')">
										<xform:text property="fdBorrowDetail_Form[${vstatus.index}].fdArchives.docSubject" style="width:90%;" mobile="true" />
									</a>
								</td>
								<td>
									<xform:text property="fdBorrowDetail_Form[${vstatus.index}].fdArchives.docTemplate.fdName" style="width:90%;" mobile="true" />
								</td>
								<td>
									<c:choose>
										<c:when test="${not empty fdBorrowDetail_FormItem.fdArchives.fdValidityDate}">
											<kmss:showDate value="${fdBorrowDetail_FormItem.fdArchives.fdValidityDate }" type="date"></kmss:showDate>
										</c:when>
										<c:otherwise>
											<bean:message bundle="km-archives" key="kmArchivesMain.fdValidityDate.forever"/>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${kmArchivesBorrowForm.docStatus eq '20' and kmArchivesBorrowForm.sysWfBusinessForm.fdNodeAdditionalInfo.modifyAuthRange =='true'}">
											<c:set var="showStatus" value="edit"></c:set>
										</c:when>
										<c:otherwise>
											<c:set var="showStatus" value="view"></c:set>
										</c:otherwise>
									</c:choose>
									<xform:checkbox property="fdBorrowDetail_Form[${vstatus.index}].fdAuthorityRange" value="${fdBorrowDetail_FormItem.fdAuthorityRange }" mobile="true" showStatus="${showStatus}" htmlElementProperties="style='width:25rem;'">
			                            <xform:simpleDataSource value="copy">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.copy') }</xform:simpleDataSource>
										<xform:simpleDataSource value="download">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.download') }</xform:simpleDataSource>
										<xform:simpleDataSource value="print">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.print') }</xform:simpleDataSource>
			                        </xform:checkbox>
								</td>
								<td>
									<xform:datetime htmlElementProperties="style='width:120px;'" property="fdBorrowDetail_Form[${vstatus.index}].fdReturnDate" value="${empty fdBorrowDetail_FormItem.fdRenewReturnDate?fdBorrowDetail_FormItem.fdReturnDate:fdBorrowDetail_FormItem.fdRenewReturnDate }" dateTimeType="date" mobile="true"/>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>