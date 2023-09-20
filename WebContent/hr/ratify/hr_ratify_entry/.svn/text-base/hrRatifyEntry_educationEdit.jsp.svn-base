<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table class="tb_normal" width="100%">
						<tr>
							<td><span class="fsBtnGroup"> <a class="fsBtnAdd"
									href="javascript:void(0);"
									onclick="HR_AddRatifyEntryNew('TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit');">
										<bean:message bundle="hr-ratify"
											key="hrRatifyEduExp.addRatifyEduExp" />
								</a>
							</span></td>
						</tr>
						<tr>
							<td colspan="4" width="100%">
								<table class="tb_normal" width="100%"
									id="TABLE_DocList_fdEducations_Form" align="center"
									tbdraggable="true">

									<tr align="center" class="tr_normal_title">
										<td style="width: 20px;" class="bgColorTd"></td>
										<td style="width: 40px;" class="bgColorTd">${lfn:message('page.serial')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyEduExp.fdAcademic')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyEduExp.fdEntranceDate')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyEduExp.fdGraduationDate')}</td>
									</tr>
									<tr KMSS_IsReferRow="1" style="display: none;" class="fdItem">
										<td align="center">
										<a href="javascript:void(0);" onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit');"
											title="${lfn:message('doclist.delete')}"> 
											<img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
										</a>
										<xform:text property="fdEducations_Form[!{index}].fdRemark" showStatus="noShow" />
										</td>
										<td align="center" KMSS_IsRowIndex="1"><span
											id="fdOOrderEducations!{index}"><c:out value="!{index}"></c:out></span>
										</td>
										<td align="center">
											<%-- 学校名称--%> <input type="hidden"
											name="fdEducations_Form[!{index}].fdId" value=""
											disabled="true" />
											<input type="text" name="fdEducations_Form[!{index}].fdNameNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdEducations_Form[!{index}].fdName" style="display: none;"
												_xform_type="text"> 
												<xform:text property="fdEducations_Form[!{index}].fdName"
													showStatus="edit" 
													subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 专业名称--%>
											<input type="text" name="fdEducations_Form[!{index}].fdMajorNew" disabled="true" class="inputsgl"/>
											<div id="_xform_fdEducations_Form[!{index}].fdMajor" style="display: none;"
												_xform_type="text">
												<xform:text property="fdEducations_Form[!{index}].fdMajor"
													showStatus="edit"
													subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 学位--%>
											<input type="text" name="fdEducations_Form[!{index}].fdAcadeRecordName" disabled="true" class="inputsgl"/>
											<input type="hidden" name="fdEducations_Form[!{index}].fdAcadeRecord" class="inputsgl" />
											<div id="_xform_fdEducations_Form[!{index}].fdAcadeRecordId" style="display: none;"
												_xform_type="text">
												<xform:select property="fdEducations_Form[!{index}].fdAcadeRecordId" htmlElementProperties="id='fdAcadeRecordId'" showStatus="edit">
                                                    <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" 
                                                    whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"/>
                                                </xform:select>
											</div>
										</td>
										<td align="center">
											<%-- 入学日期--%>
											<input type="text" name="fdEducations_Form[!{index}].fdEntranceDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdEducations_Form[!{index}].fdEntranceDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdEducations_Form[!{index}].fdEntranceDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 毕业日期--%>
											<input type="text" name="fdEducations_Form[!{index}].fdGraduationDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdEducations_Form[!{index}].fdGraduationDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdEducations_Form[!{index}].fdGraduationDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
									</tr>
									<input type="hidden" name="fdEducations_vstatusLength" value="${fn:length(hrRatifyEntryForm.fdEducations_Form)}">
									<c:forEach items="${hrRatifyEntryForm.fdEducations_Form}"
										var="fdEducations_FormItem" varStatus="vstatus">
										<tr KMSS_IsContentRow="1" class="fdItem">
											<td align="center"><a href="javascript:void(0);"
												onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit');"
												title="${lfn:message('doclist.delete')}"> <img
													src="${KMSS_Parameter_StylePath}icons/icon_del.png"
													border="0" />
											</a> <xform:text
													property="fdEducations_Form[${vstatus.index}].fdRemark"
													showStatus="noShow" /></td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<span id="fdOOrderEducations!{index}">${vstatus.index+1}</span>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<%-- 学校名称--%> <input type="hidden"
												name="fdEducations_Form[${vstatus.index}].fdId"
												value="${fdEducations_FormItem.fdId}" />
												<input type="text" name="fdEducations_Form[${vstatus.index}].fdNameNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdEducations_Form[${vstatus.index}].fdName"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdEducations_Form[${vstatus.index}].fdName"
														showStatus="edit" 
														subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<%-- 专业名称--%>
												<input type="text" name="fdEducations_Form[${vstatus.index}].fdMajorNew" disabled="true"  class="inputsgl" />
												<div id="_xform_fdEducations_Form[${vstatus.index}].fdMajor"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdEducations_Form[${vstatus.index}].fdMajor"
														showStatus="edit"
														subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<%-- 学位--%>
												<input type="text" value="${fdEducations_FormItem.fdAcadeRecordName }" name="fdEducations_Form[${vstatus.index}].fdAcadeRecordName" disabled="true"  class="inputsgl" />
												<input type="hidden" value="${fdEducations_FormItem.fdAcadeRecordId }" name="fdEducations_Form[${vstatus.index}].fdAcadeRecord" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdEducations_Form[${vstatus.index}].fdAcadeRecordId"
													_xform_type="text" style="display:none;">
													<xform:select value="${fdEducations_FormItem.fdAcadeRecordId }" property="fdEducations_Form[${vstatus.index}].fdAcadeRecordId" htmlElementProperties="id='fdAcadeRecordId'" showStatus="edit">
	                                                    <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" 
	                                                    whereBlock="hrStaffPersonInfoSettingNew.fdType = 'fdHighestDegree'"/>
                                                	</xform:select>
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<%-- 入学日期--%>
												<input type="text" name="fdEducations_Form[${vstatus.index}].fdEntranceDateNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdEducations_Form[${vstatus.index}].fdEntranceDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdEducations_Form[${vstatus.index}].fdEntranceDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdEducations_Form','table_of_fdEducations_detail_edit')">
												<%-- 毕业日期--%>
												<input type="text" name="fdEducations_Form[${vstatus.index}].fdGraduationDateNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdEducations_Form[${vstatus.index}].fdGraduationDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdEducations_Form[${vstatus.index}].fdGraduationDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div>
											</td>
										</tr>
									</c:forEach>	
								</table> <input type="hidden" name="fdEducations_Flag" value="1">
								<script>
                                        DocList_Info.push('TABLE_DocList_fdEducations_Form');
                                    </script>
							</td>
						</tr>
					</table>