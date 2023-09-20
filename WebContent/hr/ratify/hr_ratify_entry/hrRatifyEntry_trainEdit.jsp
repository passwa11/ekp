<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table class="tb_normal" width="100%">
						<tr>
							<td><span class="fsBtnGroup"> <a class="fsBtnAdd"
									href="javascript:void(0);"
									onclick="HR_AddRatifyEntryNew('TABLE_DocList_fdTrains_Form','table_of_fdTrains_detail_edit');">
										<bean:message bundle="hr-ratify"
											key="hrRatifyTrain.addRatifyTrain" />
								</a>
							</span></td>
						</tr>
						<tr>
							<td colspan="4" width="100%">
								<table class="tb_normal" width="100%"
									id="TABLE_DocList_fdTrains_Form" align="center"
									tbdraggable="true">

									<tr align="center" class="tr_normal_title">
										<td style="width: 20px;"  class="bgColorTd"></td>
										<td style="width: 40px;" class="bgColorTd">${lfn:message('page.serial')}</td>
										<td  class="bgColorTd">${lfn:message('hr-ratify:hrRatifyTrain.fdName')}</td>
										<td  class="bgColorTd"> ${lfn:message('hr-ratify:hrRatifyTrain.fdTrainCompany')}</td>
										<td  class="bgColorTd"> ${lfn:message('hr-ratify:hrRatifyTrain.fdStartDate')}</td>
										<td  class="bgColorTd"> ${lfn:message('hr-ratify:hrRatifyTrain.fdEndDate')}</td>
									</tr>
									<tr KMSS_IsReferRow="1" style="display: none;" class="fdItem">
										<td align="center"><a href="javascript:void(0);"
											onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdTrains_Form','table_of_fdTrains_detail_edit');"
											title="${lfn:message('doclist.delete')}"> <img
												src="${KMSS_Parameter_StylePath}icons/icon_del.png"
												border="0" />
										</a> <xform:text property="fdTrains_Form[!{index}].fdRemark"
												showStatus="noShow" /></td>
										<td align="center" KMSS_IsRowIndex="1"><span
											id="fdOOrderTrains!{index}"><c:out value="!{index}"></c:out></span>
										</td>
										<td align="center">
											<%-- 名称--%> <input type="hidden"
											name="fdTrains_Form[!{index}].fdId" value="" disabled="true" />
											<input type="text" name="fdTrains_Form[!{index}].fdNameNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdTrains_Form[!{index}].fdName" style="display: none;"
												_xform_type="text">
												<xform:text property="fdTrains_Form[!{index}].fdName"
													showStatus="edit" 
													subject="${lfn:message('hr-ratify:hrRatifyTrain.fdName')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 培训单位--%>
											<input type="text" name="fdTrains_Form[!{index}].fdTrainCompanyNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdTrains_Form[!{index}].fdTrainCompany" style="display: none;"
												_xform_type="text">
												<xform:text
													property="fdTrains_Form[!{index}].fdTrainCompany"
													showStatus="edit"
													subject="${lfn:message('hr-ratify:hrRatifyTrain.fdTrainCompany')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 开始时间--%>
											<input type="text" name="fdTrains_Form[!{index}].fdStartDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdTrains_Form[!{index}].fdStartDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdTrains_Form[!{index}].fdStartDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 结束时间--%>
											<input type="text" name="fdTrains_Form[!{index}].fdEndDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdTrains_Form[!{index}].fdEndDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime property="fdTrains_Form[!{index}].fdEndDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
									</tr>
									<input type="hidden" name="fdTrains_vstatusLength" value="${fn:length(hrRatifyEntryForm.fdTrains_Form)}">
									<c:forEach items="${hrRatifyEntryForm.fdTrains_Form}"
										var="fdTrains_FormItem" varStatus="vstatus">
										<tr KMSS_IsContentRow="1" class="fdItem">
											<td align="center"><a href="javascript:void(0);"
												onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdTrains_Form','table_of_fdTrains_detail_edit');"
												title="${lfn:message('doclist.delete')}"> <img
													src="${KMSS_Parameter_StylePath}icons/icon_del.png"
													border="0" />
											</a> <xform:text
													property="fdTrains_Form[${vstatus.index}].fdRemark"
													showStatus="noShow" /></td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdTrains_Form','table_of_fdTrains_detail_edit')">
												<span id="fdOOrderTrains!{index}">${vstatus.index+1}</span>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdTrains_Form','table_of_fdTrains_detail_edit')">
												<%-- 名称--%> <input type="hidden"
												name="fdTrains_Form[${vstatus.index}].fdId"
												value="${fdTrains_FormItem.fdId}" />
												<input type="text" name="fdTrains_Form[${vstatus.index}].fdNameNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdTrains_Form[${vstatus.index}].fdName"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdTrains_Form[${vstatus.index}].fdName"
														showStatus="edit" 
														subject="${lfn:message('hr-ratify:hrRatifyTrain.fdName')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdTrains_Form','table_of_fdTrains_detail_edit')">
												<%-- 培训单位--%>
												<input type="text" name="fdTrains_Form[${vstatus.index}].fdTrainCompanyNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdTrains_Form[${vstatus.index}].fdTrainCompany"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdTrains_Form[${vstatus.index}].fdTrainCompany"
														showStatus="edit"
														subject="${lfn:message('hr-ratify:hrRatifyTrain.fdTrainCompany')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdTrains_Form','table_of_fdTrains_detail_edit')">
												<%-- 开始时间--%>
												<input type="text" name="fdTrains_Form[${vstatus.index}].fdStartDateNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdTrains_Form[${vstatus.index}].fdStartDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdTrains_Form[${vstatus.index}].fdStartDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdTrains_Form','table_of_fdTrains_detail_edit')">
												<%-- 结束时间--%>
												<input type="text" name="fdTrains_Form[${vstatus.index}].fdEndDateNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdTrains_Form[${vstatus.index}].fdEndDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdTrains_Form[${vstatus.index}].fdEndDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div>
											</td>
										</tr>
									</c:forEach>
								</table> <input type="hidden" name="fdTrains_Flag" value="1"> <script>
                                        DocList_Info.push('TABLE_DocList_fdTrains_Form');
                                    </script>
							</td>
						</tr>
					</table>