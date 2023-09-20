<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table class="tb_normal" width="100%">
						<tr>
							<td><span class="fsBtnGroup"> <a class="fsBtnAdd"
									href="javascript:void(0);"
									onclick="HR_AddRatifyEntryNew('TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit');">
										<bean:message bundle="hr-ratify"
											key="hrRatifyCertifi.addRatifyCertifi" />
								</a>
							</span></td>
						</tr>
						<tr>
							<td colspan="4" width="100%">
								<table class="tb_normal" width="100%"
									id="TABLE_DocList_fdCertificate_Form" align="center"
									tbdraggable="true">

									<tr align="center" class="tr_normal_title">
										<td style="width: 20px;" class="bgColorTd"></td>
										<td style="width: 40px;" class="bgColorTd">${lfn:message('page.serial')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyCertifi.fdIssueDate')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyCertifi.fdInvalidDate')}</td>
									</tr>
									<tr KMSS_IsReferRow="1" style="display: none;" class="fdItem">
										<td align="center"><a href="javascript:void(0);"
											onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit');"
											title="${lfn:message('doclist.delete')}"> <img
												src="${KMSS_Parameter_StylePath}icons/icon_del.png"
												border="0" />
										</a> <xform:text property="fdCertificate_Form[!{index}].fdRemark"
												showStatus="noShow" /></td>
										<td align="center" KMSS_IsRowIndex="1"><span
											id="fdOOrderCertificate!{index}"><c:out value="!{index}"></c:out></span>
										</td>
										<td align="center">
											<%-- 证书名称--%> <input type="hidden"
											name="fdCertificate_Form[!{index}].fdId" value=""
											disabled="true" />
											<input type="text" name="fdCertificate_Form[!{index}].fdNameNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdCertificate_Form[!{index}].fdName" style="display: none;"
												_xform_type="text">
												<xform:text property="fdCertificate_Form[!{index}].fdName"
													showStatus="edit" 
													subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 颁发单位--%>
											<input type="text" name="fdCertificate_Form[!{index}].fdIssuingUnitNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdCertificate_Form[!{index}].fdIssuingUnit" style="display: none;"
												_xform_type="text">
												<xform:text
													property="fdCertificate_Form[!{index}].fdIssuingUnit"
													showStatus="edit"
													subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}"
													validators=" maxLength(100)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 颁发日期--%>
											<input type="text" name="fdCertificate_Form[!{index}].fdIssueDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdCertificate_Form[!{index}].fdIssueDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdCertificate_Form[!{index}].fdIssueDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 失效日期--%>
											<input type="text" name="fdCertificate_Form[!{index}].fdInvalidDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdCertificate_Form[!{index}].fdInvalidDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdCertificate_Form[!{index}].fdInvalidDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
									</tr>
									<input type="hidden" name="fdCertificate_vstatusLength" value="${fn:length(hrRatifyEntryForm.fdCertificate_Form)}">
									<c:forEach items="${hrRatifyEntryForm.fdCertificate_Form}"
										var="fdCertificate_FormItem" varStatus="vstatus">
										<tr KMSS_IsContentRow="1" class="fdItem">
											<td align="center"><a href="javascript:void(0);"
												onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit');"
												title="${lfn:message('doclist.delete')}"> <img
													src="${KMSS_Parameter_StylePath}icons/icon_del.png"
													border="0" />
											</a> <xform:text
													property="fdCertificate_Form[${vstatus.index}].fdRemark"
													showStatus="noShow" /></td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit')">
												<span id="fdOOrderCertificate!{index}">${vstatus.index+1}</span>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit')">
												<%-- 证书名称--%> <input type="hidden"
												name="fdCertificate_Form[${vstatus.index}].fdId"
												value="${fdCertificate_FormItem.fdId}" />
												<input type="text" name="fdCertificate_Form[${vstatus.index}].fdNameNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdCertificate_Form[${vstatus.index}].fdName"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdCertificate_Form[${vstatus.index}].fdName"
														showStatus="edit" 
														subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit')">
												<%-- 颁发单位--%>
												<input type="text" name="fdCertificate_Form[${vstatus.index}].fdIssuingUnitNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdCertificate_Form[${vstatus.index}].fdIssuingUnit"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdCertificate_Form[${vstatus.index}].fdIssuingUnit"
														showStatus="edit"
														subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}"
														validators=" maxLength(100)" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit')">
												<%-- 颁发日期--%>
												<input type="text" name="fdCertificate_Form[${vstatus.index}].fdIssueDateNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdCertificate_Form[${vstatus.index}].fdIssueDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdCertificate_Form[${vstatus.index}].fdIssueDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div> 
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdCertificate_Form','table_of_fdCertificate_detail_edit')">
												<%-- 失效日期--%>
												<input type="text" name="fdCertificate_Form[${vstatus.index}].fdInvalidDateNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdCertificate_Form[${vstatus.index}].fdInvalidDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdCertificate_Form[${vstatus.index}].fdInvalidDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div>
											</td>
										</tr>
									</c:forEach>
								</table> <input type="hidden" name="fdCertificate_Flag" value="1">
								<script>
                                        DocList_Info.push('TABLE_DocList_fdCertificate_Form');
                                    </script>
							</td>
						</tr>
					</table>