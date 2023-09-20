<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="no">
	<template:replace name="title">
		<c:choose>
			<c:when test="${kmsMedalMainForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('kms-medal:module.kms.medal') }"></c:out>	
			</c:when> 
			<c:otherwise>
				<c:out value="${kmsMedalMainForm.fdName} - ${ lfn:message('kms-medal:module.kms.medal') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="head">
		<script>
			seajs.use(['kms/medal/resource/css/edit.css']);
		</script>
		<link rel="stylesheet" href="${ LUI_ContextPath }/kms/medal/resource/Jcrop/css/jquery.Jcrop.min.css" type="text/css" />
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ kmsMedalMainForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.kmsMedalMainForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ kmsMedalMainForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="saveMedal();"></ui:button>
				</c:when>
			</c:choose>	
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:combin ref="menu.path.simplecategory">
			<ui:varParams id="simplecategoryId"
				moduleTitle="${ lfn:message('kms-medal:module.kms.medal') }"
				autoFetch="false"
				modelName="com.landray.kmss.kms.medal.model.KmsMedalCategory"
				categoryId="${kmsMedalMainForm.fdCategoryId}" />
		</ui:combin>
	</template:replace>
	<template:replace name="content">
		<html:form action="/kms/medal/kms_medal_main/kmsMedalMain.do">
			<c:if test="${!empty kmsMedalMainForm.fdName}">
				<p class="txttitle" style="display: none;">${kmsMedalMainForm.fdName }</p>
			</c:if> 
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalMain.fdName"/>
						</td>
						<td width="35%">
							<xform:text property="fdName" style="width:85%;" validators="noSpecialChar checkNameOnly" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalMain.fdValidTime"/>
						</td>
						<td width="35%" colspan="3">
							<xform:datetime  property="fdValidTime" 
											 dateTimeType="date"  
											 onValueChange="dateChange" />
							<em style="color: #d02300;font-style: normal;display: inline-block;">
							(${lfn:message("kms-medal:kmsMedalMain.fdValidTime.forver.tip") })</em>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalMain.fdCategory"/>
						</td>
						<td width="35%" colspan="3">
							<input type="hidden" name="fdCategoryId" value="${kmsMedalMainForm.fdCategoryId}">
							<span id="fdCategoryTemp">${kmsMedalMainForm.fdCategoryName}</span>
								<a href="javascript:modifyCate()" style="margin-left:15px;" class="com_btn_link">
								${lfn:message("kms-medal:kmsMedalOwner.cate.info") }</a>
								<span class="txtstrong">*</span>
						</td>	
					</tr>
					<tr>											
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalMain.fdIntroduction"/>
						</td>
						<td width="35%" colspan="3">
							<xform:textarea property="fdIntroduction"  validators="maxLength(1500)" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalMain.pic"/>
						</td>
						<td colspan="3">
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
					          <c:param name="fdKey" value="medalPic"/>
					          <c:param name="fdAttType" value="pic"/>
					          <c:param name="fdMulti" value="false" />
					          <c:param name="enabledFileType" value="*.gif;*.jpg;*.jpeg;*.png" />	
					        </c:import> 
					        <p style="color: red;">
					        	${lfn:message("kms-medal:kmsMedalOwner.info") }
					        </p>
					        <br>
					        <div id="crop_ele">
					        	<div style="padding-bottom: 5px;min-width:200px;display:inline-block;" >
					        		<img id="crop_medal_img" src="${LUI_ContextPath}/kms/medal/resource/images/upload_medal_200_200.jpg" width="200" height="200">
					        	</div>
					        	<div style="display:inline-block;width:100px;vertical-align:top;margin-left:30px;">
						        	<div style="width: 100px;height: 100px;overflow: hidden;">
										<c:choose>
											<c:when test="${param.method eq 'add' }">
												<img id="big_medal_img" src="${LUI_ContextPath}/kms/medal/resource/images/upload_medal_100_100.jpg" width="100" height="100" >
											</c:when>
											<c:otherwise>
												<img id="big_medal_img" src="${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?${LUI_Cache}&method=download&fdId=${bigPicMedal.fdId}" width="100" height="100"><br>&nbsp;&nbsp;大图100*100
											</c:otherwise>
										</c:choose>
						        	</div>
						        	<p>${lfn:message("kms-medal:kmsMedalOwner.info.big") }</p>
						        	<div style="width: 30px;height: 30px;overflow: hidden;margin-top:30px;">
						        		<c:choose>
											<c:when test="${param.method eq 'add' }">
												<img id="small_medal_img" src="${LUI_ContextPath}/kms/medal/resource/images/upload_medal_30_30.jpg" width="30" height="30">
											</c:when>
											<c:otherwise>
												<img id="small_medal_img" src="${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?${LUI_Cache}&method=download&fdId=${smallPicMedal.fdId}" width="30" height="30"><br>&nbsp;&nbsp;小图30*30
											</c:otherwise>
										</c:choose>
						        		 
						        	</div>
						        	<p>${lfn:message("kms-medal:kmsMedalOwner.info.small") }</p>
					        	</div>
					        	
					        	<div>
					        		<a  href="javascript:;" style="margin-right:10px;display:none;" 
					        			class="lui_medal_cut_btn com_bgcolor_n com_bordercolor_d com_fontcolor_n"  onclick="sureCropImg();"
					        			title="${lfn:message('button.save') }">${lfn:message('button.save') }</a>
					        		<a  href="javascript:;" style="display:none;" 
					        			class="lui_medal_cut_btn com_bgcolor_n com_bordercolor_d com_fontcolor_n lui_medal_cut_cancel"  
					        			onclick="cancelCropImg();"
					        			title="${lfn:message('button.cancel') }">${lfn:message('button.cancel') }</a>
					        	</div>
					        </div>
							<c:if test="${param.method eq 'edit' }">
									<input type="hidden" name="oldSmallPicFdId" id="oldSmallPicFdId" value="${smallPicMedal.fdId}">
							</c:if>
					        <input type="hidden" name="smallPicFdId" id="smallPicFdId" value="${smallPicMedal.fdId}" validate="required">
					        <c:if test="${param.method eq 'edit' }">
									<input type="hidden" name="oldBigPicFdId" id="oldBigPicFdId" value="${bigPicMedal.fdId}">
							</c:if>
					        <input type="hidden"    name="bigPicFdId" id="bigPicFdId" value="${bigPicMedal.fdId}" validate="required">
					        <!-- 是否上传了图片 -->
					        <input type="hidden" id="hasMedalPic" value="${bigPicMedal.fdId}">
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalMain.docCreator"/>
						</td>
						<td width="35%">
							<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalMain.docCreateTime"/>
						</td>
						<td width="35%">
							<xform:datetime property="docCreateTime" showStatus="view"/>
						</td>						
					</tr>
					<c:if test="${not empty kmsMedalMainForm.docAlterorId}">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalMain.docAlteror"/>
						</td>
						<td width="35%">
							<xform:address propertyId="docAlterorId" propertyName="docAlterorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-medal" key="kmsMedalMain.docAlterTime"/>
						</td>
						<td width="35%">
							<xform:datetime property="docAlterTime" showStatus="view"/>
						</td>						
					</tr>
					</c:if>
				</table>
			</div>
			<div id="crop_div" style="display: none;">
				
			</div>
			<div style="display: none;">
				<label>${lfn:message("kms-medal:kmsMedalOwner.start.x") } <input type="text" size="4" id="startX" name="startX" /></label>
			    <label>${lfn:message("kms-medal:kmsMedalOwner.start.y") } <input type="text" size="4" id="startY" name="startY" /></label>
			    <label>${lfn:message("kms-medal:kmsMedalOwner.final.x") } <input type="text" size="4" id="finishX" name="finishX" /></label>
			    <label>${lfn:message("kms-medal:kmsMedalOwner.final.y") } <input type="text" size="4" id="finishY" name="finishY" /></label>
			    <label>${lfn:message("kms-medal:kmsMedalOwner.width") } <input type="text" size="4" id="cropWidth" name="cropWidth" /></label>
			    <label>${lfn:message("kms-medal:kmsMedalOwner.height") } <input type="text" size="4" id="cropHeight" name="cropHeight" /></label>
			</div>
		<html:hidden property="fdId" />
		<html:hidden property="docCreatorId" />
		<html:hidden property="docCreateTime" />
		<html:hidden property="method_GET" />
		</html:form>
		<iframe name="medalIframe" width="0" height="0" id="uploadIframe" src="about:blank" frameborder="0" marginwidth="0">
		</iframe>
		<script>
		$(function() {
			var date = $("input[name='fdValidTime']").val().substring(0, 10);
			$("input[name='fdValidTime']").val(date);

		});
		var validations = {
				'noSpecialChar':
				{
					error:"<span style='color:#cc0000;'>${lfn:message('kms-medal:kmsMedalCategory.fdName') }</span>&nbsp;${lfn:message('kms-medal:kmsMedalOwner.special') }",
					test:function(v,e,o) {
						//过滤特殊字符
						var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&;—|{}【】‘；：”“'。，、？]");
						v = $.trim(v);
						if(pattern.test(v)) {
							return false;
						}
						return true;
					}
				},
				'checkNameOnly':
				{
					error:"<span style='color:#cc0000;'>${lfn:message('kms-medal:kmsMedalCategory.fdName') }</span>&nbsp;${lfn:message('kms-medal:kmsMedalOwner.no.same') }",
					test:function(v,e,o) {
						//数据库判断
						v = encodeURI($.trim(v));
						var propertyName = $(e).attr("name");
						var fdName = propertyName.substring(propertyName.lastIndexOf(".") + 1, propertyName.length);
						propertyName = propertyName.substring(0,propertyName.lastIndexOf("."));
						//明细表每行的fdId（编辑的时候，用于过滤本身）
						var fdId = $("input[name='fdId']").val();
						var data = new KMSSData();
						data.UseCache = false;
						data.AddBeanData("kmsMedalCheckNameOnlyServiceImpl&fdId="+ fdId +"&beanName=kmsMedalMainService&fieldName=" + fdName +"&fieldValue="+v);
						var retVal = data.GetHashMapArray();
						if(retVal[0]['size']>0){
							return false;
						}
						return true;
					}
				}
			};
			var picValidate = (function() {
				var __reminder =  new Reminder("#crop_ele", "${lfn:message('kms-medal:kmsMedalOwner.img.no.null') }");
				return function() {
					var smallPicFdId = $("#smallPicFdId").val();
					var bigPicFdId = $("#bigPicFdId").val();
					if(bigPicFdId == "" || smallPicFdId == "") {
						__reminder.show();
						return false;
					} else  {
						__reminder.hide();
						return true;
					}
				};
			})();
			var vali = $KMSSValidation();
			vali.addValidators(validations);
			function dateChange(date) {
				var oldDay = "${kmsMedalMainForm.fdValidTime}";
				var dd = oldDay.substring(0, 10);
				if(dd != date) {
					seajs.use(['lui/jquery'], function($) {
						vali.addElements(document.getElementsByName("fdValidTime")[0], "after");
					});
				}
			}
			function modifyCate(){
				seajs.use(['lui/dialog', 'lui/util/env'],function(dialog, env) {
						//修改分类，弹出确认框
						dialog.confirm("${lfn:message('kms-medal:kmsMedalOwner.cate') }", 
								function(flag) {
									if(flag){
										setCategory();
									}
								});
						function setCategory(){
							dialog.tree('kmsMedalCategoryService&fdParentId=!{value}', '<bean:message bundle="kms-medal" key="kmsMedalMain.fdCategory"/>', function(data){
								if(data != null){
									LUI.$('input[name=fdCategoryId]').val(data.value);
									LUI.$('#fdCategoryTemp').text(data.text);
								}
							});
						}
				});
			}
			function saveMedal() {
				if(!vali.validate() || !picValidate()) {
					return;
				}	
				Com_Submit(document.kmsMedalMainForm, 'save');
			}
			
			function selectCate(){
				seajs.use(['lui/dialog', 'lui/util/env'],function(dialog, env) {
					//修改分类，弹出确认框
					dialog.tree('kmsMedalCategoryService&fdParentId=!{value}', '<bean:message bundle="kms-medal" key="kmsMedalMain.fdCategory"/>', function(data){
						if(data){
							LUI.$('input[name=fdCategoryId]').val(data.value);
							LUI.$('#fdCategoryTemp').text(data.text);
						}else{
							// 无分类状态下（一般于门户快捷操作）创建勋章，取消操作同时关闭当前窗口
							window.open('', '_self', '');
							window.close();
                        }
					});
				});
			}

			//无分类情况下进入新建页面，自动弹框选择类别
			<c:if test="${kmsMedalMainForm.method_GET=='add'}">
			    if(!'${kmsMedalMainForm.fdCategoryId}'){
			    	selectCate();
                }
			</c:if>
		</script>	
		<script src="${ LUI_ContextPath }/kms/medal/resource/Jcrop/js/jquery.Jcrop.js"></script>
		<%@ include file="kmsMedalMain_edit_js.jsp" %>	
	</template:replace>
</template:include>
