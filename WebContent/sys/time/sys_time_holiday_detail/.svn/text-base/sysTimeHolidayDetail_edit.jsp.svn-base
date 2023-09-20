<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table width="100%" class="tb_normal" id="TABLE_DocList">
	<!-- 标题行 -->
	<tr class="td_normal_title" style="text-align:center;">
		<td class="td_normal_title" width="12%"><bean:message bundle="sys-time" key="sysTimeHoliday.holiday"/></td>
		<td class="td_normal_title" width="30%"><bean:message bundle="sys-time" key="sysTimeHoliday.holiday.day"/></td>
		<td class="td_normal_title" width="25%"><bean:message bundle="sys-time" key="sysTimeHoliday.mend.holiday.day"/></td>
		<td class="td_normal_title" width="25%"><bean:message bundle="sys-time" key="sysTimeHoliday.mend.work.day"/></td>
		<td class="td_normal_title" width="8%"><a onclick="addRow();" style="cursor:pointer"><bean:message key="button.create"/></a></td>
	</tr>
	<!-- 基准行 -->
	<tr KMSS_IsReferRow="1" style="display: none;">
		<td align="center">
			<!-- 节假日 -->
			<input type="hidden" name="fdHolidayDetailList[!{index}].fdId"/>
			<xform:text property="fdHolidayDetailList[!{index}].fdName" style="width:85%" required="true"/>
		</td>
		<td align="center">
			<!-- 休假日期 -->
			<xform:datetime property="fdHolidayDetailList[!{index}].fdStartDay" htmlElementProperties="readonly='readonly'" required="true" dateTimeType="date"/>
			<!-- <input type="text" readonly="readonly" class="inputSgl auto-kal" name="fdHolidayDetailList[!{index}].fdStartDay"><span style="color: red;">*</span> -->
			—
			<xform:datetime property="fdHolidayDetailList[!{index}].fdEndDay" htmlElementProperties="readonly='readonly'" required="true" dateTimeType="date"/>
			<!-- <input type="text" readonly="readonly" class="inputSgl auto-kal" name="fdHolidayDetailList[!{index}].fdEndDay"><span style="color: red;">*</span> -->
		</td>
		<!-- 补假日期 -->
		<td align="center">
			<div class="inputselectsgl" onclick="selectMulDate('fdHolidayDetailList[!{index}].fdPatchHolidayDay')" style="width: 95%;">
				<div class="input">
					<input type="text" name="fdHolidayDetailList[!{index}].fdPatchHolidayDay"  readonly="readonly" />
				</div>
				<div class="inputdatetime"></div>
			</div>
		</td>
		<!-- 补班日期 -->
		<td align="center">
			<div class="inputselectsgl" onclick="selectMulDate('fdHolidayDetailList[!{index}].fdPatchDay')" style="width: 95%;">
				<div class="input">
					<input type="text" name="fdHolidayDetailList[!{index}].fdPatchDay"  readonly="readonly" />
				</div>
				<div class="inputdatetime"></div>
			</div>
		</td>
		<td align="center">
			<a onclick="DocList_DeleteRow();" style="cursor:pointer"><bean:message key="button.delete"/></a>
		</td>
	 </tr>
	 <!-- 内容行 -->
	<c:forEach items="${sysTimeHolidayForm.fdHolidayDetailList}" var="fdHolidayDetailList" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" name="${fdHolidayDetailList.fdYear }" nm="dls">
				<td align="center">
					<input type="hidden" name="fdHolidayDetailList[${vstatus.index}].fdId" value="${fdHolidayDetailList.fdId }"/>
					<xform:text property="fdHolidayDetailList[${vstatus.index}].fdName" style="width:85%" required="true"/>
				</td>
				<td align="center">
					<xform:datetime property="fdHolidayDetailList[${vstatus.index}].fdStartDay" htmlElementProperties="readonly='readonly'" required="true" dateTimeType="date"/>
					<%-- <input type="text" readonly="readonly" class="inputSgl auto-kal" name="fdHolidayDetailList[${vstatus.index}].fdStartDay" value="${fn:substring(fdHolidayDetailList.fdStartDay,0,10)}"/><span style="color:red;">*</span> --%>
					—
					<xform:datetime property="fdHolidayDetailList[${vstatus.index}].fdEndDay" htmlElementProperties="readonly='readonly'" required="true" dateTimeType="date"/>
					<%-- <input type="text" readonly="readonly" class="inputSgl auto-kal" name="fdHolidayDetailList[${vstatus.index}].fdEndDay" value="${fn:substring(fdHolidayDetailList.fdEndDay,0,10) }"/><span style="color:red;">*</span> --%>
				</td>
				<!-- 补假日期 -->
					<td align="center">
						<div class="inputselectsgl" onclick="selectMulDate('fdHolidayDetailList[${vstatus.index}].fdPatchHolidayDay')" style="width: 95%;">
							<div class="input">
								<input type="text" name="fdHolidayDetailList[${vstatus.index}].fdPatchHolidayDay" value="${fdHolidayDetailList.fdPatchHolidayDay }" readonly="readonly" />
							</div>
							<div class="inputdatetime"></div>
						</div>
					</td>
				<td align="center">
					<div class="inputselectsgl" onclick="selectMulDate('fdHolidayDetailList[${vstatus.index}].fdPatchDay')" style="width: 95%;">
						<div class="input">
							<input type="text" name="fdHolidayDetailList[${vstatus.index}].fdPatchDay"  readonly="readonly" value="${fdHolidayDetailList.fdPatchDay }"/>
						</div>
						<div class="inputdatetime"></div>
					</div>
				</td>
				<td align="center">
					<a onclick="DocList_DeleteRow();" style="cursor:pointer"><bean:message key="button.delete"/></a>			
				</td>
			</tr>
	</c:forEach>
</table>