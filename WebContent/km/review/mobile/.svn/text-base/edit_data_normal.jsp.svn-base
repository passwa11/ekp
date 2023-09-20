<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/component/locker/import/componentLockerVersion_show.jsp"%>
<ui:ajaxtext>
	<script type="text/javascript">
		//标识表单的业务参数，流程中通过此参数判断是否来自于km-review模块
		window.fromReview = "true";
	</script>
	<%-- 此处为浏览器窗口标题 --%>
	<div data-dojo-block="title">
	   ${requestScope.templateName}
	</div>
	<%-- 此处为内容 --%>
	<div data-dojo-block="content">
		<c:choose>
			<c:when test="${'false' eq kmReviewMainForm.fdIsMobileCreate}">
				<%-- 不支持移动端新建的场景，弹出tip提醒： “当前流程移动端“新建操作”受限，请切换至PC端操作。” --%>
				<script type="text/javascript">
					require(["mui/dialog/BarTip", "dojo/ready"], function(BarTip, ready) {
						ready(function() {
							BarTip.tip({text:"<bean:message key='km-review:kmReviewTemplate.tipmessage.create'/>"});
						});
					});
				</script>
			</c:when>
			<c:otherwise>
			    <%-- 支持移动端新建的场景，加载移动端的表单 --%>
				<html:form action="/km/review/km_review_main/kmReviewMain.do?method=save">
					<div>

						<%-- 滚动区域视图 --%>
						<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" data-dojo-props="validateNext:false" id="scrollView">
						  <div data-dojo-type="mui/panel/NavPanel" >
							<div data-dojo-type="mui/panel/Content" data-dojo-mixins="mui/form/_AlignMixin" data-dojo-props="title:'<bean:message bundle="km-review" key="mui.kmReviewMain.mobile.basicInfo" />'"  style="padding-top:1px">
								<html:hidden property="fdId" /><%-- 主键ID --%>
								<html:hidden property="fdModelId" />
								<html:hidden property="fdModelName" />
								<html:hidden property="docStatus" /><%-- 文档状态 --%>
								<%--<html:hidden property="fdCanCircularize" value="${kmReviewMainForm.fdCanCircularize}"/>--%><%-- 是否可传阅  --%>

								<table style="width:100%" id='baseInfoTable' class="muiSimple" cellpadding="0" cellspacing="0">
								    <%-- 主题  --%>
									<tr>
										<td class="muiTitle">${lfn:message('km-review:kmReviewMain.docSubject') }</td>
										<td>
											<c:if test="${kmReviewMainForm.titleRegulation==null || kmReviewMainForm.titleRegulation=='' }">
												<xform:text property="docSubject" mobile="true" subject="${lfn:message('km-review:kmReviewMain.docSubject') }" htmlElementProperties="id='docSubject'"/>
											</c:if>
											<c:if test="${kmReviewMainForm.editDocSubject eq 'true' && kmReviewMainForm.titleRegulation!=null && kmReviewMainForm.titleRegulation!='' }">
												<xform:text property="docSubject" mobile="true" value="${lfn:message('km-review:kmReviewMain.docSubject.info') }" htmlElementProperties="id='docSubject'"/>
											</c:if>
											<c:if test="${kmReviewMainForm.editDocSubject ne 'true' && kmReviewMainForm.titleRegulation!=null && kmReviewMainForm.titleRegulation!='' }">
												<xform:text property="docSubject" mobile="true" showStatus="readOnly" value="${lfn:message('km-review:kmReviewMain.docSubject.info') }" htmlElementProperties="id='docSubject'"/>
											</c:if>
										</td>
									</tr>
									<%-- 模板名称  --%>
									<tr>
										<td class="muiTitle">${lfn:message('km-review:kmReviewTemplate.fdName') }</td>
										<td>
											<html:hidden property="fdTemplateId" />
											<xform:text property="fdTemplateName" mobile="true" showStatus="view" subject="${lfn:message('km-review:kmReviewTemplate.fdName') }"/>
										</td>
									</tr>
									<%-- 申请人  --%>
									<tr>
										<td class="muiTitle">${lfn:message('km-review:kmReviewMain.docCreatorName') }</td>
										<td>
											<xform:text property="docCreatorName" mobile="true" showStatus="view" subject="${lfn:message('km-review:kmReviewMain.docCreatorName') }"/>
										</td>
									</tr>
									<%-- 允许传阅 --%>
									<c:if test="${kmReviewMainForm.fdCanCircularize eq 'true' }">
										<tr>
											<td class="muiTitle">${lfn:message('km-review:kmReviewMain.fdCanCircularize.yes') }</td>
											<td>
												<xform:radio property="fdCanCircularize" mobile="true" alignment="H" showStatus="edit" value="1">
													<xform:simpleDataSource value="1"><bean:message bundle="km-review" key="kmReviewTemplate.fdUseWord.yes" /></xform:simpleDataSource>
													<xform:simpleDataSource value="0"><bean:message bundle="km-review" key="kmReviewTemplate.fdUseWord.no" /></xform:simpleDataSource>
												</xform:radio>
											</td>
										</tr>
									</c:if>
									<c:if test="${kmReviewMainForm.fdCanCircularize ne 'true' }">
										<html:hidden property="fdCanCircularize"/>
									</c:if>
								</table>

								<%-- 引入 【权限】 相关hidden元素（包括：阅读权限、编辑权限、附件拷贝、附件下载、附件打印......）  --%>
								<c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="kmReviewMainForm" />
									<c:param name="moduleModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
								</c:import>
								<%-- 引入 【关联信息】 相关hidden元素  --%>
								<c:import url="/sys/relation/mobile/edit_hidden.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="kmReviewMainForm" />
								</c:import>
								<%-- 引入 【日程同步】 相关hidden元素  --%>
								<c:import url="/sys/agenda/mobile/edit_hidden.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="kmReviewMainForm" />
								</c:import>
								<%-- 引入 【组织架构--集团分级授权--场所管理】 相关hidden元素  --%>
								<c:import url="/sys/authorization/mobile/edit_hidden.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="kmReviewMainForm" />
								</c:import>
								<!-- 版本锁机制 -->
								<c:import url="/component/locker/import/componentLockerVersion_import.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="kmReviewMainForm" />
								</c:import>
							</div>

							<div data-dojo-type="mui/panel/Content" data-dojo-mixins="mui/form/_AlignMixin" data-dojo-props="title:'<bean:message bundle="km-review" key="mui.kmReviewMain.mobile.info" />'">
							    <%-- 未使用表单的场景 --%>
								<c:if test="${kmReviewMainForm.fdUseForm == 'false'}">
									<table class="muiSimple" cellpadding="0" cellspacing="0">
										<tr>
											<td colspan="2">
											<!-- #134052流程管理富文本模板的流程，移动端新增富文本（RTF）移动端编辑模式-开始 -->
												<%-- <c:if test="${empty kmReviewMainForm.docContent }">
													<c:set property="docContent" target="${kmReviewMainForm}" value=""/>
													<xform:config orient="vertical" >
													  <xform:textarea property="docContent" mobile="true" subject="${lfn:message('km-review:kmReviewMain.docContent') }"/>
													</xform:config>
												</c:if>
												<c:if test="${not empty kmReviewMainForm.docContent }">
													<html:hidden property="docContent"/>
													<xform:rtf showStatus="view" property="docContent" mobile="true"></xform:rtf>
												</c:if> --%>
												<div><xform:rtf property="docContent" mobile="true"></xform:rtf></div>
											<!-- #134052流程管理富文本模板的流程，移动端新增富文本（RTF）移动端编辑模式-结束 -->
											</td>
										</tr>
										<tr>
										    <td class="muiTitle">${lfn:message('km-review:kmReviewMain.attachment') }</td>
											<td>
												<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
													<c:param name="formName" value="kmReviewMainForm"></c:param>
													<c:param name="fdKey" value="fdAttachment"></c:param>
												</c:import>
											</td>
										</tr>
									</table>
								</c:if>
								<%-- 使用表单的场景 --%>
								<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
									<c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp"
										charEncoding="UTF-8">
										<c:param name="formName" value="kmReviewMainForm" />
										<c:param name="fdKey" value="reviewMainDoc" />
										<c:param name="backTo" value="scrollView" />
									</c:import>
								</c:if>
							</div>

							<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-review" key="mui.kmReviewMain.mobile.review" />'">
								<%-- 引入流程操作的表单元素（引用来源为LBPM应用服务模块）  --%>
								<c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="kmReviewMainForm" />
									<c:param name="fdKey" value="reviewMainDoc" />
									<c:param name="viewName" value="none" />
							        <c:param name="backTo" value="scrollView" />
									<c:param name="viewRenderType" value="panel" />
									<c:param name="isNew" value="true"></c:param>
								</c:import>
							</div>

						   </div>


						   <%-- 引入流程操作的底部 “流程图”、“暂存”、“提交” 操作栏（引用来源为LBPM应用服务模块） --%>
							<c:import url="/sys/lbpmservice/mobile/import/edit_bottom_tabbar.jsp" charEncoding="UTF-8">

							    <%-- 模块FormName --%>
								<c:param name="formName" value="kmReviewMainForm" />

								<%-- 是否支持“暂存”（保存草稿） --%>
								<c:param name="supportSaveDraft" value="true" />

								<%-- “暂存”（保存草稿）时需要校验的表单View对应的DOM元素ID --%>
								<c:param name="saveDraftValidateDomId" value="scrollView" />

								<%-- “暂存”（保存草稿）时需要需要校验的字段Id --%>
								<c:param name="saveDraftValidateElementId" value="docSubject" />

								<%-- “提交”按钮的onclick事件 --%>
								<c:param name="onClickSubmitButton" value="review_submit();" />

							</c:import>

						</div>


						<script type="text/javascript">
							require(["dojo/ready","dijit/registry","mui/form/ajax-form!kmReviewMainForm"], function(ready,registry) {
								<%-- 提交流程(点击“提交”按钮的响应函数)  --%>
								window.review_submit = function(){
									var submitFlag = true;
									var wdt = registry.byId("scrollView");
									if(!wdt.validate()){ // 校验表单
										return;
									}

									var is_leave_flag = $("#is_leave_flag").val();
									if(is_leave_flag && '0'===is_leave_flag){
										submitFlag = isLeave();
									}
									var cancelLevelDetailFlag = $("#cancelLevelDetailFlag").val();
									if(cancelLevelDetailFlag && '0'==cancelLevelDetailFlag){
										saveCancelDetail();
									}
									if(submitFlag){
										var status = document.getElementsByName("docStatus")[0];
										var method = Com_GetUrlParameter(location.href,'method');
										if(method=='add'){
											Com_Submit(document.forms[0],'save');
										}else{
											if(status.value=='10'||status.value=='11'){
												Com_Submit(document.forms[0],'publishDraft');
											}else{
												Com_Submit(document.forms[0],'update');
											}
										}
									}
								}
								//加载完成初始化一些内容
								ready(function(){
									/*******初始化主题表格样式start*********/
									if(window.getBaseInfoTableStyle){
										//调用表单提供的方法，获取到配置的基本信息表格样式(这个方法定义在表单，具体是哪个窗口看业务场景，进行调用即可)
										var baseInfoTableStyle = window.getBaseInfoTableStyle();
										if(baseInfoTableStyle
												&& baseInfoTableStyle.isDefault == false ){
											Com_IncludeFile(baseInfoTableStyle.fileName,baseInfoTableStyle.path,'css',true);
											$("#baseInfoTable").attr("class",baseInfoTableStyle.className);
										}
										//屏蔽默认行为
										/* else if(baseInfoTableStyle.isDefault == true){
                                            $("#baseInfoTable").attr("class",baseInfoTableStyle.className);
                                        } */
									}
									/*******初始化主题表格样式end*********/
								});
							});

						</script>
						<script>
							Com_IncludeFile("editTab.js","${LUI_ContextPath}/km/review/mobile/js/","js",true);
						</script>
					</div>
				</html:form>
			</c:otherwise>
		</c:choose>
	</div>
</ui:ajaxtext>