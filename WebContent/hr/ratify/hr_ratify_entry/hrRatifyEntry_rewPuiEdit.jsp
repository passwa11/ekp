<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table class="tb_normal" width="100%">
						<tr>
							<td><span class="fsBtnGroup"> <a class="fsBtnAdd"
									href="javascript:void(0);"
									onclick="HR_AddRatifyEntryNew('TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit');">
										<bean:message bundle="hr-ratify"
											key="hrRatifyRewPuni.addRatifyRewPuni" />
								</a>
							</span></td>
						</tr>
						<tr>
							<td colspan="4" width="100%">
								<table class="tb_normal" width="100%"
									id="TABLE_DocList_fdRewardsPunishments_Form" align="center"
									tbdraggable="true">

									<tr align="center" class="tr_normal_title">
										<td style="width: 20px;" class="bgColorTd"></td>
										<td style="width: 40px;" class="bgColorTd">${lfn:message('page.serial')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}</td>
										<td class="bgColorTd">${lfn:message('hr-ratify:hrRatifyRewPuni.fdDate')}</td>
									</tr>
									<tr KMSS_IsReferRow="1" style="display: none;" class="fdItem">
										<td align="center"><a href="javascript:void(0);"
											onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit');"
											title="${lfn:message('doclist.delete')}"> <img
												src="${KMSS_Parameter_StylePath}icons/icon_del.png"
												border="0" />
										</a> <xform:text
												property="fdRewardsPunishments_Form[!{index}].fdRemark"
												showStatus="noShow" /></td>
										<td align="center" KMSS_IsRowIndex="1"><span
											id="fdOOrderRewardsPunishments!{index}"><c:out value="!{index}"></c:out></span>
										</td>
										<td align="center">
											<%-- 奖惩名称--%> <input type="hidden"
											name="fdRewardsPunishments_Form[!{index}].fdId" value=""
											disabled="true" />
											<input type="text" name="fdRewardsPunishments_Form[!{index}].fdNameNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdRewardsPunishments_Form[!{index}].fdName" style="display: none;"
												_xform_type="text">
												<xform:text
													property="fdRewardsPunishments_Form[!{index}].fdName"
													showStatus="edit" 
													subject="${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}"
													validators=" maxLength(200)" style="width:95%;" />
											</div>
										</td>
										<td align="center">
											<%-- 奖惩日期--%>
											<input type="text" name="fdRewardsPunishments_Form[!{index}].fdDateNew" disabled="true"  class="inputsgl">
											<div id="_xform_fdRewardsPunishments_Form[!{index}].fdDate" style="display: none;"
												_xform_type="datetime">
												<xform:datetime
													property="fdRewardsPunishments_Form[!{index}].fdDate"
													showStatus="edit" dateTimeType="date" style="width:95%;" />
											</div>
										</td>
									</tr>
									<input type="hidden" name="fdRewardsPunishments_vstatusLength" value="${fn:length(hrRatifyEntryForm.fdRewardsPunishments_Form)}">
									<c:forEach
										items="${hrRatifyEntryForm.fdRewardsPunishments_Form}"
										var="fdRewardsPunishments_FormItem" varStatus="vstatus">
										<tr KMSS_IsContentRow="1" class="fdItem">
											<td align="center"><a href="javascript:void(0);"
												onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit');"
												title="${lfn:message('doclist.delete')}"> <img
													src="${KMSS_Parameter_StylePath}icons/icon_del.png"
													border="0" />
											</a> <xform:text
													property="fdRewardsPunishments_Form[${vstatus.index}].fdRemark"
													showStatus="noShow" /></td>
											<td align="center"
												onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit')">
												<span id="fdOOrderRewardsPunishments!{index}">${vstatus.index+1}</span>
											</td>
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit')">
												<%-- 奖惩名称--%> <input type="hidden"
												name="fdRewardsPunishments_Form[${vstatus.index}].fdId"
												value="${fdRewardsPunishments_FormItem.fdId}" />
												<input type="text" name="fdRewardsPunishments_Form[${vstatus.index}].fdNameNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdName"
													_xform_type="text" style="display:none;">
													<xform:text
														property="fdRewardsPunishments_Form[${vstatus.index}].fdName"
														showStatus="edit" 
														subject="${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}"
														validators=" maxLength(200)" style="width:95%;" />
												</div>
											</td>
											 
											<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdRewardsPunishments_Form','table_of_fdRewardsPunishments_detail_edit')">
												<%-- 奖惩日期--%>
												<input type="text" name="fdRewardsPunishments_Form[${vstatus.index}].fdDateNew" disabled="true"  class="inputsgl" />
												<div
													id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdDate"
													_xform_type="datetime" style="display:none;">
													<xform:datetime
														property="fdRewardsPunishments_Form[${vstatus.index}].fdDate"
														showStatus="readOnly" dateTimeType="date" style="width:95%;" />
												</div>
											</td>
										</tr>
									</c:forEach>
								</table> <input type="hidden" name="fdRewardsPunishments_Flag" value="1">
								<script>
                                        DocList_Info.push('TABLE_DocList_fdRewardsPunishments_Form');
                                    </script>
							</td>
						</tr>
					</table>