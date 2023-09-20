<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.edit" compatibleMode="true" gzip="true">
	<template:replace name="title">
	    <%-- 浏览器title   --%> 
		<bean:message bundle="km-review" key="kmReviewTemplate.create.title" />
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="mui-review-view.css"/>
		<script type="text/javascript">
		   	require(["dojo/store/Memory","dojo/topic","dijit/registry"],function(Memory,topic,registry){
		   		<%-- 页签数据： “01 / 表单设计”、 “02 / 流程设置”  “03 / 权限设置”--%>
		   		var navData = [
		   		               {'text':'01<bean:message bundle="km-review" key="mui.kmReviewTemplate.mobile.info" />','moveTo':'scrollView','selected':true,key:"scrollView"}, 
		   		               {'text':'02<bean:message bundle="km-review" key="mui.kmReviewTemplate.mobile.flow" />','moveTo':'lbpmView',key:"lbpmView"},
		   		               {'text':'03<bean:message bundle="km-review" key="mui.kmReviewTemplate.mobile.right" />','moveTo':'rightView',key:"rightView"}
		   		              ];
		   		window._narStore = new Memory({data:navData});
		   		<%-- 切换至指定的页签 --%> 
		   		var changeNav = function(view){
		   			var wgt = registry.byId("_flowNav");
		   			for(var i=0;i<wgt.getChildren().length;i++){
		   				var tmpChild = wgt.getChildren()[i];
		   				if(view.id == tmpChild.moveTo){
		   					topic.publish("/mui/navitem/selected",tmpChild,{target:tmpChild.domNode,key:tmpChild.key});
		   					return;
		   				}
		   			}
		   		}
		   		<%-- 表单校验未通过，切换至未通过校验的页签 --%> 
		   		topic.subscribe("mui/form/validateFail",function(view){
		   			changeNav(view);
		   		});
		   		<%-- 展现页面的动画（如：右侧滑入、淡入......）等执行完后，再切换至指定的页签 --%> 
				topic.subscribe("mui/view/currentView",function(view){
					changeNav(view);
		   		});
				
				window.nextBtnClick = function(viewId){
					var view = registry.byId(viewId);
					changeNav(view);
				}
		   	});
	   </script>
	</template:replace>
	<template:replace name="content">
		<c:choose>
			<c:when test="${'false' eq kmReviewTemplateForm.fdIsMobileCreate}">
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
				<html:form action="/km/review/km_review_template/kmReviewTemplate.do?method=save">
					<div>
						<div data-dojo-type="mui/fixed/Fixed" class="muiFlowEditFixed">
							<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowEditFixedItem">
								<div data-dojo-type="mui/nav/NavBarStore" id="_flowNav" data-dojo-props="store:_narStore">
								</div>
							</div>
						</div>
						<div data-dojo-type="mui/view/DocScrollableView" 
							 data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView" class="muiHideAddButtomTips">
							 <div class="muiFlowInfoW muiTemplateForm muiFormContent">
								<html:hidden property="fdId" /><%-- 主键ID --%>
								<html:hidden property="fdIsMobileCreate" /> <%-- 移动端创建 --%>
								<html:hidden property="fdUseForm" />
								<html:hidden property="fdNumberPrefix"  value="BH"/>
								<table class="muiSimple" cellpadding="0" cellspacing="0">
									<%-- 模板名称  --%>
									<tr>
										<td class="muiTitle">${lfn:message('km-review:kmReviewTemplate.fdName') }</td>
										<td>
											<xform:text property="fdName" mobile="true" showStatus="edit" align="right" required="true" subject="${lfn:message('km-review:kmReviewTemplate.fdName') }"/>
										</td>
									</tr>
									<%-- 分类  --%>
				                    <tr>
					                    <td class="muiTitle">${lfn:message('km-review:kmReviewTemplate.category') }</td>
					                    <td>
					                      <%-- <kmss:auth
					                        requestURL="/km/review/km_review_template/kmReviewTemplate.do?method=add"
					                        requestMethod="GET"> --%>
					                          <div data-dojo-type="mui/form/Category" class="muiTemplateCate muiFormRight"
					                            data-dojo-mixins="km/review/mobile/resource/js/view/SysOnlyCategoryMixin"
					                            data-dojo-props="getTemplate:'0',isMul:'false',type:0,idField:'fdCategoryId',nameField:'fdCategoryName',authType:'02',
					                                                   modelName:'com.landray.kmss.km.review.model.KmReviewTemplate',showFavoriteCate:'true',
					                                                   isOnlyShowCate : 1,isOnlyReturnCate : 1,showSelect : 1,
					                                                   validate:'required',required:true,placeholder:'${lfn:message('km-review:kmReviewTemplate.category.placeholder') }',
					                                                   subject:'${lfn:message('km-review:kmReviewTemplate.category') }'">
					                          </div>
					                    <%--   </kmss:auth> --%>
					                   </td>
				                   </tr>
								</table>
							</div>
							<div class="muiFlowInfoW muiFormContent">
							    <%-- 未使用表单的场景 --%>
								<c:if test="${kmReviewTemplateForm.fdUseForm == 'false'}">
									<table class="muiSimple" cellpadding="0" cellspacing="0">
										<tr>
											<td colspan="2">
												<img src="../mobile/resource/images/reuse_bg.png" width="100%;" />
											</td>
										</tr>
									</table>
								</c:if>
								<%-- 使用表单的场景 --%>
								<c:if test="${kmReviewTemplateForm.fdUseForm == 'true' || empty kmReviewTemplateForm.fdUseForm}">
									<c:import url="/sys/xform/mobile/import/sysFormTemplateMobile_edit.jsp"
										charEncoding="UTF-8">
										<c:param name="formName" value="kmReviewTemplateForm" />
										<c:param name="fdKey" value="reviewMainDoc" />
										<c:param name="backTo" value="scrollView" />
									</c:import>
								</c:if>
							</div>
							<%-- 操作按钮展示区域  --%>
							<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" <c:if test="${'false' ne kmReviewTemplateForm.fdIsMobileCreate}">data-dojo-props='fill:"grid"'</c:if>>
								<%-- 下一步  --%> 	
							  	<li data-dojo-type="mui/tabbar/TabBarButton" class="mainTabBarButton"
							  		data-dojo-props='colSize:2,transition:"slide"' onclick="nextBtnClick('lbpmView');">
							  		<bean:message  bundle="km-review"  key="button.next" /></li>
							</ul>
						</div>
						<%-- 引入 流程设置 --%>
						<c:import url="/sys/lbpmservice/mobile/import/template/edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmReviewTemplateForm" />
							<c:param name="fdKey" value="reviewMainDoc" />
							<c:param name="onClickNextToButton" value="nextBtnClick('rightView');" />
						</c:import> 
						<%-- 引入 设置【模板可使用者,模板可维护者】 --%>
						<c:import url="/km/review/mobile/template/right_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmReviewTemplateForm" />
							<c:param name="fdKey" value="reviewMainDoc" />
							<c:param name="onClickSubmitButton" value="Com_Submit(document.forms[0],'save');" />
						</c:import>
						<script type="text/javascript">
							require(["mui/form/ajax-form!kmReviewTemplateForm"]);
						</script>
				</html:form>
			</c:otherwise>
		</c:choose>
	</template:replace>
</template:include>
