<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@page import="com.landray.kmss.sys.news.model.SysNewsConfig"%>
<%
   	 SysNewsConfig sysNewsConfig = new SysNewsConfig();
     pageContext.setAttribute("ImageW",sysNewsConfig.getfdImageW());
     pageContext.setAttribute("ImageH",sysNewsConfig.getfdImageH());
 %>
<ui:ajaxtext>
	<%-- 此处为浏览器窗口标题 --%>
	<div data-dojo-block="title">
		<c:out value="${sysNewsMainForm.fdTemplateName}"></c:out>
	</div>
	<%--此处为内容 --%>
	<div data-dojo-block="content">
		<c:if test="${sysNewsMainForm.method_GET=='edit'}"><%-- 编辑页 --%>
			<html:form action="/sys/news/sys_news_main/sysNewsMain.do?method=edit">
				<div>
					<%-- 头部页签（1.审批内容、2.流程操作） --%>
					<div data-dojo-type="mui/fixed/Fixed" data-dojo-props="fixedOrder:1" class="muiFlowEditFixed">
						<div data-dojo-type="mui/fixed/FixedItem" >
							<div data-dojo-type="mui/nav/NavBarStore" data-dojo-mixins="mui/nav/NavBarStepMixin,sys/news/mobile/resource/js/SysNewsNavMixin" id="_flowNav"></div>
						</div>
					</div>
					<%-- 滚动区域视图 --%>
					<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" id="scrollView">
						<div data-dojo-type="mui/panel/NavPanel" data-dojo-props="fixedOrder:2">
							<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="sys-news" key="sysNewsMain.baseInfo" />'">
								<html:hidden property="fdId" /><%-- 主键ID --%>
								<html:hidden property="fdModelId" />
								<html:hidden property="fdModelName" />
								<html:hidden property="fdContentType" />
								<html:hidden property="fdHtmlContent" />
								<html:hidden property="fdSignEnable" />
								<html:hidden property="fdImportance" />
								<html:hidden property="fdTemplateId" />
								<html:hidden property="fdTemplateName" />
								<table class="muiSimple" cellpadding="0" cellspacing="0">
									<tr>
										<td class="muiTitle">${lfn:message('sys-news:sysNewsMain.docSubject') }</td>
										<td>
											<xform:text property="docSubject" mobile="true" validators="sensitiveWord" subject="${lfn:message('sys-news:sysNewsMain.docSubject') }" htmlElementProperties="id='docSubject'"/>
										</td>
									</tr>
									<%-- 作者类型/作者  --%>
									<tr>
										<td class="muiTitle">${lfn:message('sys-news:mobile.sysNews.add.publisherType') }</td>
										<td>
											<xform:radio property="fdIsWriter" mobile="true" mobileRenderType="normal" value="${sysNewsMainForm.fdIsWriter}" onValueChange="sysNews_changeAut">
												<xform:simpleDataSource value="false">${lfn:message('sys-news:mobile.sysNews.add.innerAuthor') }</xform:simpleDataSource>
												<xform:simpleDataSource value="true">${lfn:message('sys-news:mobile.sysNews.add.outerAuthor') }</xform:simpleDataSource>
											</xform:radio>
										</td>
									</tr>
									<tr>
										<td class="muiTitle">${lfn:message('sys-news:sysNewsMain.publisher') }</td>
										<c:choose>
											<c:when test="${sysNewsMainForm.fdIsWriter=='false'}">
												<td id="innerAuthor">
													<xform:address propertyId="fdAuthorId" mobile="true" required="true" isLoadDataDict="false" subject="${lfn:message('sys-news:sysNewsMain.publisher')}" propertyName="fdAuthorName" orgType='ORG_TYPE_PERSON' />
												</td>
												<td id="outerAuthor" style="display:none">
													<xform:text property="fdWriter" mobile="true"  validators="checkName maxLength(200)" subject="${lfn:message('sys-news:sysNewsMain.fdWriter') }"></xform:text>
												</td>
											</c:when>
											<c:otherwise>
												<td id="innerAuthor" style="display:none">
													<xform:address propertyId="fdAuthorId" mobile="true" isLoadDataDict="false" subject="${lfn:message('sys-news:sysNewsMain.publisher')}" propertyName="fdAuthorName" orgType='ORG_TYPE_PERSON' />
												</td>
												<td id="outerAuthor">
													<xform:text property="fdWriter" mobile="true" required="true" validators="checkName maxLength(200)" subject="${lfn:message('sys-news:sysNewsMain.fdWriter') }"></xform:text>
												</td>
											</c:otherwise>
										</c:choose>
									</tr>
									<%-- 所属部门  --%>
									<tr id="innerDept" style="${sysNewsMainForm.fdIsWriter=='false' ? '' : 'display:none'}">
										<td class="muiTitle">${lfn:message('sys-news:sysNewsMain.publishUnit')}</td>
										<td>
											<xform:address propertyId="fdDepartmentId" mobile="true" required="true" isLoadDataDict="false" subject="${lfn:message('sys-news:sysNewsMain.publishUnit')}" propertyName="fdDepartmentName" orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT'></xform:address>
										</td>
									</tr>
								</table>
							</div>
							<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="sys-news" key="sysNewsMain.docContent" />'">
								<table class="muiSimple" cellpadding="0" cellspacing="0">
									<colgroup><col class="muiTitle"><col></colgroup>
									<xform:config orient="vertical" >
										<tr>
											<td colspan="2"><xform:textarea property="fdDescription" validators="sensitiveWord maxLength(1500)" mobile="true" /></td>
										</tr>
									</xform:config>
									<tr>
										<td class="muiTitle">正文编辑方式</td>
										<td>
											<xform:radio property="fdEditType" mobile="true" mobileRenderType="normal" value="${sysNewsMainForm.fdContentType}" onValueChange="sysNews_changeType">
												<xform:simpleDataSource value="rtf"><bean:message bundle="sys-news" key="sysNewsMain.fdContentType.rtf"/></xform:simpleDataSource>
												<xform:simpleDataSource value="att"><bean:message bundle="sys-news" key="sysNewsMain.fdContentType.att"/></xform:simpleDataSource>
											</xform:radio>
										</td>
										</tr>
										<tr  id="rtfEdit">
											<td colspan="2">
												<%-- rtf  --%>
												<div><xform:rtf property="docContent" mobile="true" validators="sensitiveWord"></xform:rtf></div>
											</td>
										</tr>
										<%-- 附件上传模式--%>
										<tr  id="attEdit">
										<td class="muiTitle">
							              	<bean:message bundle="sys-news" key="mobile.sysNews.add.content"/>
							            </td>
							            <td>
							                	<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
													<c:param name="formName" value="sysNewsMainForm"></c:param>
													<c:param name="fdKey" value="newsMain"></c:param>
													<c:param name="fdMulti" value="false"></c:param>
													<c:param name="align" value="right"></c:param>
													<c:param name="fdModelId" value="${sysNewsMainForm.fdId }" />
												</c:import>
							            </td>
									</tr>
									<tr>
										<td class="muiTitle">
							              	${lfn:message('sys-news:sysNewsMain.portlet.attach')}
							            </td>
										<td><c:import
												url="/sys/attachment/mobile/import/edit.jsp"
												charEncoding="UTF-8">
												<c:param name="formName" value="sysNewsMainForm"></c:param>
												<c:param name="fdKey" value="fdAttachment" />
												<c:param name="align" value="right" />
											</c:import>
										</td>
									</tr>
									<tr>
										<td class="muiTitle">
							              	${lfn:message('sys-news:sysNewsMain.fdMainPicture')}<%-- 标题图片--%>
							            </td>
										<td><c:import
												url="/sys/attachment/mobile/import/edit.jsp"
												charEncoding="UTF-8">
												<c:param name="formName" value="sysNewsMainForm"></c:param>
												<c:param name="fdKey" value="Attachment" />
												<c:param name="align" value="right" />

												<c:param name="fdMulti" value="false" />
												<c:param name="fdAttType" value="pic" />
												<c:param name="fdImgHtmlProperty" value="width=120" />
												<c:param name="fdModelId" value="${param.fdId }" />
												<c:param name="fdModelName"
													value="com.landray.kmss.sys.news.model.SysNewsMain" />
												<%-- 图片设定大小 --%>
												<c:param name="picWidth" value="${ImageW}" />
												<c:param name="picHeight" value="${ImageH}" />
												<c:param name="proportion" value="false" />
												<c:param name="fdLayoutType" value="pic"/>
												<c:param name="fdPicContentWidth" value="${ImageW}"/>
												<c:param name="fdPicContentHeight" value="${ImageH}"/>
												<c:param name="fdViewType" value="pic_single"/>
											</c:import>
										</td>
									</tr>
									<tr>
										<td class="muiTitle">
							              	${lfn:message('sys-news:mobile.sysNews.add.fdCanComment')}<%-- 允许点评  --%>
							            </td>
										<td>
										<%--
											<ui:switch property="fdCanComment" enabledText="${ lfn:message('sys-news:sysNewsMain.fdCanComment.yes') }"  disabledText="${ lfn:message('sys-news:sysNewsMain.fdCanComment.no') }"  checked="${sysNewsMainForm.fdCanComment}"></ui:switch>
											 --%>
											<div data-dojo-type="mui/form/Switch" data-dojo-props="realValue:'${sysNewsMainForm.fdCanComment}',showStatus:'edit',orient:'horizontal',align:'right',property:'fdCanComment',leftLabel:'',rightLabel:''"></div>
										</td>
									</tr>

									<tr style="height: 6rem;">
										<td class="muiTitle">
							              	<div>${lfn:message('sys-news:sysNewsMain.docOverdueTime')}</div><%-- 信息有效日期  --%>
							              	<div class="muiNewsTitleDesc">${lfn:message('sys-news:mobile.sysNews.add.docOverdueTimeTip')}</div>
							            </td>
										<td>
											<xform:datetime property="docOverdueTime" mobile="true" dateTimeType="date" validators="after" subject="${lfn:message('sys-mobile:mui.date.select')}"></xform:datetime>
										</td>
									</tr>
								</table>
							</div>
							<c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="sysNewsMainForm" />
								<c:param name="moduleModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
							</c:import>
						</div>

						<%-- 操作按钮展示区域  --%>
						<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
							<c:if test="${sysNewsMainForm.docStatus=='10'||sysNewsMainForm.docStatus=='11'}">
							  	<%-- 暂存  --%>
								<li data-dojo-type="mui/tabbar/TabBarButton" id="saveDraft" onclick="review_submit(10);">
								<bean:message bundle="sys-news" key="news.button.store" /></li>
							</c:if>
							<%-- 下一步  --%>
						  	<li data-dojo-type="mui/tabbar/TabBarButton" class="mainTabBarButton"
						  		data-dojo-props='colSize:2,moveTo:"lbpmView",transition:"slide"'>
						  		<bean:message bundle="sys-news" key="news.button.next" /></li>
						</ul>
					</div>


					<%-- 引入流程操作的表单元素（引用来源为LBPM应用服务模块）  --%>
					<c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysNewsMainForm" />
						<c:param name="fdKey" value="newsMainDoc" />
						<c:param name="viewName" value="lbpmView" />
						<c:param name="backTo" value="scrollView" />
						<c:param name="onClickSubmitButton" value="review_submit(20);" />

					</c:import>
				</div>
			</html:form>
		</c:if>
		<c:if test="${sysNewsMainForm.method_GET=='add'}"><%-- 新建页 --%>
			<html:form action="/sys/news/sys_news_main/sysNewsMain.do?method=add">
				<div>
					<%-- 头部页签（1.审批内容、2.流程操作） --%>
					<div data-dojo-type="mui/fixed/Fixed" data-dojo-props="fixedOrder:1" class="muiFlowEditFixed">
						<div data-dojo-type="mui/fixed/FixedItem" >
							<div data-dojo-type="mui/nav/NavBarStore" data-dojo-mixins="mui/nav/NavBarStepMixin,sys/news/mobile/resource/js/SysNewsNavMixin" id="_flowNav"></div>
						</div>
					</div>

					<%-- 滚动区域视图 --%>
					<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" id="scrollView">
						<div data-dojo-type="mui/panel/NavPanel" data-dojo-props="fixedOrder:2">
							<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="sys-news" key="sysNewsMain.baseInfo" />'">
								<html:hidden property="fdId" /><%-- 主键ID --%>
								<html:hidden property="fdModelId" />
								<html:hidden property="fdModelName" />
								<html:hidden property="fdContentType" />
								<html:hidden property="fdHtmlContent" />
								<html:hidden property="fdSignEnable" />
								<html:hidden property="fdImportance" value="3" />

								<table class="muiSimple" cellpadding="0" cellspacing="0">
								    <%-- 主题  --%>
									<tr>
										<td class="muiTitle">${lfn:message('sys-news:sysNewsMain.docSubject') }</td>
										<td>
											<xform:text property="docSubject" mobile="true" validators="sensitiveWord" subject="${lfn:message('sys-news:sysNewsMain.docSubject') }" htmlElementProperties="id='docSubject'"/>
										</td>
									</tr>
									<%-- 作者类型/作者  --%>
									<tr>
										<td class="muiTitle">${lfn:message('sys-news:mobile.sysNews.add.publisherType') }</td>
										<td>
											<xform:radio property="fdIsWriter" mobile="true" mobileRenderType="normal" value="${sysNewsMainForm.fdIsWriter}" onValueChange="sysNews_changeAut">
												<xform:simpleDataSource value="false">${lfn:message('sys-news:mobile.sysNews.add.innerAuthor') }</xform:simpleDataSource>
												<xform:simpleDataSource value="true">${lfn:message('sys-news:mobile.sysNews.add.outerAuthor') }</xform:simpleDataSource>
											</xform:radio>
										</td>
									</tr>
									<tr>
										<td class="muiTitle">${lfn:message('sys-news:sysNewsMain.publisher') }</td>
										<td id="innerAuthor">
											<xform:address propertyId="fdAuthorId" mobile="true" required="true" isLoadDataDict="false" subject="${lfn:message('sys-news:sysNewsMain.publisher')}" propertyName="fdAuthorName" orgType='ORG_TYPE_PERSON' />
										</td>
										<td id="outerAuthor" style="display:none">
											<xform:text property="fdWriter" mobile="true"  validators="checkName maxLength(200)" subject="${lfn:message('sys-news:sysNewsMain.fdWriter') }"></xform:text>
										</td>
									</tr>
									<%-- 所属部门  --%>
									<tr id="innerDept" style="${sysNewsMainForm.fdIsWriter=='false' ? '' : 'display:none'}">
										<td class="muiTitle">${lfn:message('sys-news:sysNewsMain.publishUnit')}</td>
										<td>
											<xform:address propertyId="fdDepartmentId" mobile="true" required="true" isLoadDataDict="false" subject="${lfn:message('sys-news:sysNewsMain.publishUnit')}" propertyName="fdDepartmentName" orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT'></xform:address>
										</td>
									</tr>
								</table>
							</div>
							<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="sys-news" key="sysNewsMain.docContent" />'">
								<table class="muiSimple" cellpadding="0" cellspacing="0">
									<colgroup><col class="muiTitle"><col></colgroup>
									<xform:config orient="vertical" >
										<tr>
											<td colspan="2"><xform:textarea property="fdDescription" validators="sensitiveWord maxLength(1500)" mobile="true" /></td>
										</tr>
									</xform:config>
									<%--正文类型 --%>
									<tr>
										<td class="muiTitle">正文编辑方式</td>
										<td>
											<xform:radio property="fdEditType" mobile="true" mobileRenderType="normal" value="${sysNewsMainForm.fdContentType}" onValueChange="sysNews_changeType">
												<xform:simpleDataSource value="rtf"><bean:message bundle="sys-news" key="sysNewsMain.fdContentType.rtf"/></xform:simpleDataSource>
												<xform:simpleDataSource value="att"><bean:message bundle="sys-news" key="sysNewsMain.fdContentType.att"/></xform:simpleDataSource>
											</xform:radio>
										</td>
									</tr>
											<tr  id="rtfEdit">
									<td colspan="2">
										<%-- rtf  --%>
										<div><xform:rtf property="docContent" mobile="true" validators="sensitiveWord"></xform:rtf></div>
									</td>
									</tr>
									<%-- 附件上传模式--%>
									<tr  id="attEdit">
										<td class="muiTitle">
										<bean:message bundle="sys-news" key="mobile.sysNews.add.content"/>
							            </td>
							            <td>
							            </td>
									</tr>
									<tr>
										<td class="muiTitle">
							              	${lfn:message('sys-news:sysNewsMain.portlet.attach')}
							            </td>
										<td><c:import
												url="/sys/attachment/mobile/import/edit.jsp"
												charEncoding="UTF-8">
												<c:param name="formName" value="sysNewsMainForm"></c:param>
												<c:param name="fdKey" value="fdAttachment" />
												<c:param name="align" value="right" />
											</c:import>
										</td>
									</tr>
									<tr>
										<td class="muiTitle">
							              	${lfn:message('sys-news:sysNewsMain.fdMainPicture')}<%-- 标题图片--%>
							            </td>
										<td><c:import
												url="/sys/attachment/mobile/import/edit.jsp"
												charEncoding="UTF-8">
												<c:param name="formName" value="sysNewsMainForm"></c:param>
												<c:param name="fdKey" value="Attachment" />
												<c:param name="align" value="right" />

												<c:param name="fdMulti" value="false" />
												<c:param name="fdAttType" value="pic" />
												<c:param name="fdImgHtmlProperty" value="width=120" />
												<c:param name="fdModelName"
													value="com.landray.kmss.sys.news.model.SysNewsMain" />
												<%-- 图片设定大小 --%>
												<c:param name="picWidth" value="${ImageW}" />
												<c:param name="picHeight" value="${ImageH}" />
												<c:param name="proportion" value="false" />
												<c:param name="fdLayoutType" value="pic"/>
												<c:param name="fdPicContentWidth" value="${ImageW}"/>
												<c:param name="fdPicContentHeight" value="${ImageH}"/>
												<c:param name="fdViewType" value="pic_single"/>
											</c:import>
										</td>
									</tr>
									<tr>
										<td class="muiTitle">
							              	${lfn:message('sys-news:mobile.sysNews.add.fdCanComment')}<%-- 允许点评  --%>
							            </td>
										<td>
										<%--
											<ui:switch property="fdCanComment" enabledText="${ lfn:message('sys-news:sysNewsMain.fdCanComment.yes') }"  disabledText="${ lfn:message('sys-news:sysNewsMain.fdCanComment.no') }"  checked="${sysNewsMainForm.fdCanComment}"></ui:switch>
											 --%>
											<div data-dojo-type="mui/form/Switch" data-dojo-props="realValue:'${sysNewsMainForm.fdCanComment}',showStatus:'edit',orient:'horizontal',align:'right',property:'fdCanComment',leftLabel:'',rightLabel:''"></div>
										</td>
									</tr>

									<tr style="height: 6rem;">
										<td class="muiTitle">
							              	<div>${lfn:message('sys-news:sysNewsMain.docOverdueTime')}</div><%-- 信息有效日期  --%>
							              	<div class="muiNewsTitleDesc">${lfn:message('sys-news:mobile.sysNews.add.docOverdueTimeTip')}</div>
							            </td>
										<td>
											<xform:datetime property="docOverdueTime" mobile="true" dateTimeType="date" validators="after" subject="${lfn:message('sys-mobile:mui.date.select')}"></xform:datetime>
										</td>
									</tr>
								</table>
							</div>
							<c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="sysNewsMainForm" />
								<c:param name="moduleModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
							</c:import>
						</div>

						<%-- 操作按钮展示区域  --%>
						<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
						  	<%-- 暂存  --%>
							<li data-dojo-type="mui/tabbar/TabBarButton" id="saveDraft" onclick="review_submit(10);">
								<bean:message bundle="sys-news" key="news.button.store" /></li>
							<%-- 下一步  --%>
						  	<li data-dojo-type="mui/tabbar/TabBarButton" class="mainTabBarButton"
						  		data-dojo-props='colSize:2,moveTo:"lbpmView",transition:"slide"'>
						  		<bean:message bundle="sys-news" key="news.button.next" /></li>
						</ul>
					</div>

					<%-- 引入流程操作的表单元素（引用来源为LBPM应用服务模块）  --%>
					<c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysNewsMainForm" />
						<c:param name="fdKey" value="newsMainDoc" />
						<c:param name="viewName" value="lbpmView" />
						<c:param name="backTo" value="scrollView" />
						<c:param name="onClickSubmitButton" value="review_submit(20);" />

					</c:import>
				</div>
			</html:form>
		</c:if>
	</div>
	<script type="text/javascript">
		require(['dojo/ready',"dijit/registry","sys/news/mobile/resource/js/wordCheck"],function(ready,registry,wordCheck) {
			ready(function () {
				var validorObj = registry.byId('scrollView');
				validorObj._validation
						.addValidator(
								"sensitiveWord",
								'${lfn:message('sys-news:sysNews.word.sensitive.warn')}',
								function (v) {
									if(!v){
										return true;
									}
									return wordCheck.form_wordCheck(v, '${lfn:message('sys-news:sysNews.word.sensitive.warn')}');
								});
			});
		})
	</script>
	<script type="text/javascript">
		require(["mui/form/ajax-form!kmReviewMainForm"]);
		<%-- 提交流程  --%>
		require(["sys/news/mobile/resource/js/mui-news-edit"], function(edit) {
			var edit = new edit();
			var data = {};
			data.fdContentType  ="${sysNewsMainForm.fdContentType}"
			edit.init(data);
		});
	</script>
	<style>
		.muiValidate{
			margin-top: 0;
			padding-bottom: 1.5rem;
		}
	</style>
</ui:ajaxtext>