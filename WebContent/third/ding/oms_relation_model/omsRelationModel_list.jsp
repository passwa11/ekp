<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("data.js");
		</script>
	</template:replace>
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%" count="5">
				<c:if test="${param.type=='dept' }">
				<kmss:auth requestURL="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=omsInit&fdIsOrg=1">
					<ui:button text="${ lfn:message('third-ding:table.thirdDingOmsInit') }"
						onclick="window.check();">
					</ui:button>
				</kmss:auth>
				</c:if>
				<c:if test="${param.type=='person' }">
				<kmss:auth requestURL="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=omsInit&fdIsOrg=0">
					<ui:button text="${ lfn:message('third-ding:thirdDingOmsInit.person.init') }"
						onclick="window.check();">
					</ui:button>
				</kmss:auth>
				</c:if>
				<kmss:auth requestURL="/third/ding/oms_relation_model/omsRelationModel.do?method=add">
					<ui:button text="${ lfn:message('button.add') }"
						onclick="Com_OpenWindow('${LUI_ContextPath}/third/ding/oms_relation_model/omsRelationModel.do?method=add&type=${HtmlParam.type }');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/third/ding/oms_relation_model/omsRelationModel.do?method=deleteall">
					<ui:button text="${ lfn:message('button.delete') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.omsRelationModelForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
				<kmss:authShow roles="ROLE_THIRDDING_ADMIN">
					<ui:button text="${ lfn:message('button.search') }" onclick="Search_Show();" />
					<ui:button text="${ lfn:message('third-ding:omsRelationModel.file.batch.upload') }"
						onclick="Com_OpenWindow('${LUI_ContextPath}/third/ding/oms_relation_model/omsRelationModel_upload.jsp')"  style="min-width: 50px;"/>
					<ui:button text="${ lfn:message('third-ding:omsRelationModel.template.file.batch.upload') }"
						onclick="window.open('${LUI_ContextPath}/third/ding/oms_relation_model/upload template.xls');"/>
				</kmss:authShow>
			</ui:toolbar>
			<c:import url="/third/ding/oms_relation_model/omsRelationModel_search.jsp" charEncoding="UTF-8">
				<c:param name="fdModelName" value="${param.type }" />
			</c:import>
	</template:replace>

	<template:replace name="content">

<html:form action="/third/ding/oms_relation_model/omsRelationModel.do">
<c:if test="${queryPage.totalrows==0}">
	<br>
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="10pt">
					<input type="checkbox" name="List_Tongle">
				</td>
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<sunbor:column property="d.fd_name">
					<c:if test="${param.type=='person' }">
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId"/>
					</c:if>
					<c:if test="${param.type=='dept' }">
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId.org"/>
					</c:if>
				</sunbor:column>
				<c:if test="${param.type=='person' }">
					<sunbor:column property="p.fd_login_name">
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpLoginName"/>
					</sunbor:column>
				</c:if>
				<c:if test="${param.type=='dept' }">
					<sunbor:column property="m.fd_ekp_id">
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId.Id.org"/>
					</sunbor:column>
				</c:if>
				<sunbor:column property="m.fd_app_pk_id">
					<c:if test="${param.type=='person' }">
						<bean:message bundle="third-ding" key="omsRelationModel.fdAppPkId"/>
					</c:if>
					<c:if test="${param.type=='dept' }">
						<bean:message bundle="third-ding" key="omsRelationModel.fdAppPkId.org"/>
					</c:if>
				</sunbor:column>
				<c:if test="${param.type=='person' }">
					<sunbor:column property="m.fd_account_type">
						<bean:message bundle="third-ding" key="thirdDingOmsInit.fdAccountType"/>
					</sunbor:column>
					<sunbor:column property="m.fd_union_id">
						UnionId
					</sunbor:column>
				</c:if>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="omsRelationModel" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/third/ding/oms_relation_model/omsRelationModel.do" />?method=view&fdId=${omsRelationModel.fdId}&type=${HtmlParam.type}">
				<td>
					<input type="checkbox" name="List_Selected" value="${omsRelationModel.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${omsRelationModel.fdName}" />
				</td>
				<c:if test="${param.type=='person' }">
					<td>
						<c:out value="${omsRelationModel.fdLoginName}" />
					</td>
				</c:if>
				<c:if test="${param.type=='dept' }">
					<td>
						<c:out value="${omsRelationModel.fdEkpId}" />
					</td>
				</c:if>
				<td>
					<c:out value="${omsRelationModel.fdAppPKId}" />
				</td>
				<c:if test="${param.type=='person' }">
					<td>
						<c:if test="${omsRelationModel.fdAccountType=='common'}">
							普通账号
						</c:if>
						<c:if test="${omsRelationModel.fdAccountType=='dingtalk' }">
							专属账号(dingtalk)
						</c:if>
						<c:if test="${omsRelationModel.fdAccountType=='sso' }">
							专属账号(sso)
						</c:if>
					</td>
					<td>
						<c:out value="${omsRelationModel.fdUnionId}" />
					</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
	<script type="text/javascript">
		seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
			var type = '${JsParam.type}';
			var message = '<bean:message bundle="third-ding" key="thirdDingOmsInit.org.init.tip"/>';
			var url = '<c:url value="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=omsInit&fdIsOrg=1" />';
			var inittip = '<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.finish.org"/>';
			var errorUrl = '<c:url value="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=list&fdIsOrg=1" />';
			var dtval = "thirdDingOmsInitService&fdIsOrg=1";
			if(type=='person'){
				message = '<bean:message bundle="third-ding" key="thirdDingOmsInit.person.init.tip"/>';
				url = '<c:url value="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=omsInit&fdIsOrg=0" />';
				inittip = '<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.finish.person"/>';
				dtval = "thirdDingOmsInitService&fdIsOrg=0";
				errorUrl = '<c:url value="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=list&fdIsOrg=0" />';
			}
			window.check = function() {
				dialog.confirm(message, function(value){
					if(value == true) {
						$.ajax({
						   type: "POST",
						   url: url,
						   async:true,
						   dataType: "json",
						   success: function(data){
								if(data.status=="1"){
									if("1"==data.errors){
										dialog.confirm('<bean:message bundle="third-ding" key="other.init.error"/>',function(val){
											window.progress.hide();
											if(val){
												self.location.href = errorUrl;
											}else{
												self.location.reload();
											}
										});
									}else{
										dialog.alert('<bean:message bundle="third-ding" key="other.init.finish"/>',function(){
											window.progress.hide();
											self.location.reload();
										});
									}
								}else{
									dialog.alert('<bean:message bundle="third-ding" key="other.init.errormsg"/>',function(){
										window.progress.hide();
										self.location.reload();
									});
								}
						   },
						   error: function(data){
							   window.progress.hide();
						   }
						});
						// 开启进度条
						window.progress = dialog.progress();
						window._progress();
					}
				});
			}

			window._progress = function () {
				var data = new KMSSData();
				data.UseCache = false;
				data.AddBeanData(dtval);
				var rtn = data.GetHashMapArray()[0];
				if(window.progress) {
					if(rtn.checkState == 1) {
						window.progress.hide();
					}else{
						// 设置进度提示
						window.progress.setProgressText(rtn.checkMessage);
						// 设置进度值
						var currentCount = rtn.checkCurrentCount || 0;
						var allCount = rtn.checkAllCount || 0;
						if(allCount == 0) {
							window.progress.setProgress(0);
							//进度为0代表结束
						} else {
							window.progress.setProgress(currentCount, allCount);
						}
					}
				}
				// 如果总数量等于-1，表示操作还未执行
				// 如果当前执行的数量比总数量少，表示执行未结束
				if(rtn.checkState != 1) {
					setTimeout("window._progress()", 1000);
				}
			}
		});
		</script>
	</template:replace>
</template:include>