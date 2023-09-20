<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<kmss:windowTitle moduleKey="sys-xform-maindata:tree.relation.jdbc.root"  subjectKey="sys-xform-maindata:tree.relation.main.dadta.insystem" subject="${sysFormMainDataInsystemForm.docSubject}" />
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>

<div id="optBarDiv">

	<input type="button"
		value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('sysFormMainDataInsystem.do?method=edit&from=${JsParam.from}&fdId=${JsParam.fdId}','_self');">


	<input type="button"
		value="<bean:message key="button.delete"/>"
		onclick="if(!confirmDelete())return;Com_OpenWindow('sysFormMainDataInsystem.do?method=delete&fdId=${JsParam.fdId}','_self');">
	
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-xform-maindata" key="tree.relation.main.dadta.insystem"/></p>

<center>
<table id="Label_Tabel" width=95%>
		<!-- 基本信息 -->
		<tr LKS_LabelName="<bean:message bundle='sys-xform-maindata' key='sysFormMainData.basicInfo'/>">
			<td>
				<table class="tb_normal" width=100%>
				<c:if test="${param.from ne 'modeling'}">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.docCategory"/>
						</td><td width="35%">
							<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }" propertyId="docCategoryId" style="width:90%"
									propertyName="docCategoryName" dialogJs="XForm_treeDialog()">
							</xform:dialog>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message key="model.fdOrder"/>
						</td><td width="35%">
							<%-- <xform:text property="fdOrder" style="width:85%" /> --%>
							<xform:text property="fdNewOrder" style="width:85%;" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.docSubject"/>
						</td>
						<td width=35%>
							<xform:text property="docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.docSubject') }" style="width:85%" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKey"/>
						</td>
						<td width=35%>
							<xform:text property="fdKey" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKey')}" style="width:15%" validators="alphanum"/>&nbsp;
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdModleName"/>
						</td>
						<td colspan="3">
							<c:out value="${sysFormMainDataInsystemForm.fdModelNameText }"></c:out>
						</td>
					</tr>

		</c:if>

		<c:if test="${param.from eq 'modeling'}">
			<!-- 标题 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.docSubject"/>
				</td>
				<td width=35%>
					<xform:text property="docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.docSubject') }" style="width:85%" />
					<html:hidden property="docCategoryId" />
					<html:hidden property="docCategoryName" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKey"/>
				</td>
				<td width=35%>
					<xform:text property="fdKey" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKey')}" style="width:85%" validators="myAlphanum"/></br>
					<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKeyTip"/>
				</td>
			</tr>
			
			<!-- 系统数据 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdModleName"/>
				</td>
				<td>
					<div style="width:20%;">
						<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.fdModleName') }" propertyId="fdModelName" style="width:90%"
								propertyName="fdModelNameText" dialogJs="XForm_selectModelNameDialog();">
						</xform:dialog>
					</div>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message key="model.fdOrder"/>
				</td><td width="35%">
					<%-- <xform:text property="fdOrder" style="width:85%" /> --%>
					<xform:text property="fdNewOrder" style="width:85%;" validators="digits min(0)" />
				</td>		
			</tr>
		</c:if>
					<!-- 查询条件 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdWhereBlock"/>
						</td>
						<td colspan="3">
							<c:out value="${sysFormMainDataInsystemForm.fdWhereBlockText }"></c:out>
						</td>
					</tr>
					<!-- 权限 -->
					<c:if test="${sysFormMainDataInsystemForm.isAuth eq 'true'}">
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdWhereAuth"/>
							</td>
							<td colspan="3">
								<label><input type="checkbox" name="fdAuthRead" <c:if test="${sysFormMainDataInsystemForm.fdReadAuthText eq 'true'}">checked</c:if> disabled/>可阅读者</label>
								<label><input type="checkbox" name="fdAuthEdit" <c:if test="${sysFormMainDataInsystemForm.fdEditAuthText eq 'true'}">checked</c:if> disabled/>可编辑者</label>
							</td>
						</tr>
					</c:if>
					<!-- 返回值 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdSelectBlock"/>
						</td>
						<td colspan="3">
							<c:out value="${sysFormMainDataInsystemForm.fdSelectBlockText }"></c:out>
						</td>
					</tr>
					<!-- 搜索条件 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdSearch"/>
						</td>
						<td colspan="3">
							<c:out value="${sysFormMainDataInsystemForm.fdSearchText }"></c:out>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.fdUseRange"/>
						</td>
						<td colspan="3">
							<div name="xform_main_data_range" onclick="xform_main_data_subject_display();">
								<xform:checkbox property="fdRangeXform" value="${sysFormMainDataInsystemForm.fdRangeXform }">
									<xform:simpleDataSource value="true"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.customForm"/></xform:simpleDataSource>
								</xform:checkbox>
								<xform:checkbox property="fdRangeRTF" value="${sysFormMainDataInsystemForm.fdRangeRTF }">
									<xform:simpleDataSource value="true">RTF</xform:simpleDataSource>
								</xform:checkbox>
								<xform:checkbox property="fdRangeRelation" value="${sysFormMainDataInsystemForm.fdRangeRelation }">
									<xform:simpleDataSource value="true"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.relationDocConfigation"/></xform:simpleDataSource>
								</xform:checkbox>
								<xform:checkbox property="fdRangeMatrix" value="${sysFormMainDataInsystemForm.fdRangeMatrix }">
									<xform:simpleDataSource value="true"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.matrix"/></xform:simpleDataSource>
								</xform:checkbox>
							</div>
						</td>
					</tr>
					<c:if test="${sysFormMainDataInsystemForm.fdRangeRelation != false || sysFormMainDataInsystemForm.fdRangeRTF != false}">
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.subjectCol"/>
							</td>
							<td colspan="3">
								<c:out value="${sysFormMainDataInsystemForm.fdRTFSubjectText }"></c:out>
							</td>
						</tr>						
					</c:if>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message key="model.fdCreator"/>
						</td>
						<td width="35%">
							<c:out value="${sysFormMainDataInsystemForm.docCreatorName }"></c:out>				
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message key="model.fdCreateTime"/>
						</td>
						<td width="35%">
							<c:out value="${sysFormMainDataInsystemForm.docCreateTime }"></c:out>
						</td>
					</tr>
					<c:if test="${not empty sysFormMainDataInsystemForm.docAlterorName}">
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="model.docAlteror"/>
							</td>
							<td width="35%">
								<c:out value="${sysFormMainDataInsystemForm.docAlterorName }"></c:out>
							</td>
							<td class="td_normal_title" width=15%>
								<bean:message key="model.fdAlterTime"/>
							</td>
							<td width="35%">
								<c:out value="${sysFormMainDataInsystemForm.docAlterTime }"></c:out>
							</td>
						</tr>
					</c:if>
					
				</table>
			</td>
		</tr>
		<!-- 被引用表单模板 -->
		<c:import url="/sys/xform/maindata/xFormMainDataRef_view.jsp" charEncoding="UTF-8">
			<c:param name="fdModelName" value="com.landray.kmss.sys.xform.maindata.model.SysFormMainDataInsystem"></c:param>
			<c:param name="fdId" value="${sysFormMainDataInsystemForm.fdId }"></c:param>
		</c:import>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>