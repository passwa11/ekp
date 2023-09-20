<%@page import="java.util.Set"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.framework.hibernate.extend.SqlPartitionConfig"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%
String modelName = "com.landray.kmss.km.review.model.KmReviewShowConfig";
ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
Map map = sysAppConfigService.findByKey(modelName);
request.setAttribute("showConfig", map);

	//固定为蓝天凌云主题
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.list" j_rIframe='true'>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/ding_list.css?s_cache=${LUI_Cache }"/>
	</template:replace>
	<template:replace name="content">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<div class="luiKmReviewContent">
			<list:criteria id="criteria1">
				<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${ lfn:message('km-review:kmReviewMain.docSubject') }">
				</list:cri-ref>
				<c:if test="${param['doctype'] == 'feedback'}">
					<list:tab-criterion title="" key="docStatus">
				   		 <list:box-select>
				   		 	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false" cfg-required="true">
								<ui:source type="Static">
									[{text:'${ lfn:message('km-review:status.unfeedback') }', value:'41'},
									 {text:'${ lfn:message('km-review:status.feedback') }',value:'31'}]
								</ui:source>
							</list:item-select>
				    	</list:box-select>
				    </list:tab-criterion>
				</c:if>
			    <c:if test="${(param['doctype'] == 'examine' )&& param['docStatus'] !='00' && param['docStatus'] !='32'}">
			    	<list:tab-criterion title="" key="docStatus">
				   		 <list:box-select>
				   		 	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false" cfg-required="true"  cfg-if=" ">
								<ui:source type="Static">
									[{text:'${ lfn:message('km-review:status.append') }', value:'20'},
									 {text:'${ lfn:message('km-review:status.refuse') }',value:'11'}]
								</ui:source>
							</list:item-select>
				    	</list:box-select>
				    </list:tab-criterion>
			    </c:if>
			     
			    <c:if test="${(param['mydoc'] == 'all' || param['mydoc'] == 'create' || param['mydoc'] == 'approved' || param['doctype'] == 'follow') && param['docStatus'] !='00' && param['docStatus'] !='32'}">
				    <list:tab-criterion cfg-tabTop='false' title="" key="docStatus"> 
				   		 <list:box-select>
				   		 	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" >
								<ui:source type="Static">
									[{text:'${ lfn:message('km-review:status.draft') }', value:'10'},
									{text:'${ lfn:message('km-review:status.append') }', value:'20'},
									{text:'${ lfn:message('km-review:status.refuse') }', value:'11'},
									{text:'${ lfn:message('km-review:status.publish') }', value:'30'},
									{text:'${ lfn:message('km-review:status.feedback') }',value:'31'},
									{text:'${ lfn:message('km-review:status.discard') }',value:'00'}]
								</ui:source>
							</list:item-select>
				    	</list:box-select>
				    </list:tab-criterion>
			    </c:if>
				
				<%
					if(SqlPartitionConfig.getInstance().isEnabled("com.landray.kmss.km.review.model.KmReviewMain")){
				%>
				<list:cri-ref title="${ lfn:message('km-review:kmReviewMain.docCreateTime') }" key="partition" ref="criterion.sys.partition" modelName="com.landray.kmss.km.review.model.KmReviewMain" />
				<%
					}
				%>
				<list:cri-ref ref="criterion.sys.category" key="fdTemplate" multi="false" expand="false" title="${ lfn:message('km-review:kmReviewMain.criteria.fdTemplate') }">
				  <list:varParams modelName="com.landray.kmss.km.review.model.KmReviewTemplate"/>
				</list:cri-ref>
				<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="fdNumber"/>
				<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false" title="${lfn:message('km-review:kmReviewMain.docCreatorName') }" />
			   <%--当前处理人--%>
				<list:cri-ref ref="criterion.sys.postperson.availableAll"  cfg-if="param['docStatus']!='00' && param['docStatus']!='32'"
							  key="fdCurrentHandler" multi="false"
							  title="${lfn:message('km-review:kmReviewMain.currentHandler') }" />
			   <%--已处理人--%>
				<list:cri-ref ref="criterion.sys.person" 
							  key="fdAlreadyHandler" multi="false"
							  title="${lfn:message('km-review:kmReviewMain.fdAlreadyHandler') }" />
				<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" 
					property="fdDepartment" />
				<%
					if(SqlPartitionConfig.getInstance().isEnabled("com.landray.kmss.km.review.model.KmReviewMain") == false){
				%>
				<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" cfg-defaultValue="${showConfig.loadDataVolume}" property="docCreateTime"/>
				<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="docPublishTime"/>
				<%
					}
				%>
				<%if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
					<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="authArea"/>
				<%} %>
				<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="docProperties"/>	
				<list:cri-criterion title="${ lfn:message('km-review:kmReviewMain.fdIsFiling')}" key="fdIsFile"> 
					<list:box-select>
						<list:item-select cfg-if="param['j_path']!='/listFiling'">
							<ui:source type="Static">
								[{text:'${ lfn:message('km-review:kmReviewMain.fdIsFiling.have')}', value:'1'},
								{text:'${ lfn:message('km-review:kmReviewMain.fdIsFiling.nothave')}',value:'0'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>		
			</list:criteria>
			
			<div class="lui_list_operation">
				<!-- 全选 -->
				<div class="lui_list_operation_order_btn">
					<list:selectall></list:selectall>
				</div>
				<!-- 分割线 -->
				<div class="lui_list_operation_line"></div>
				<!-- 排序 -->
				<div class="lui_list_operation_sort_btn">
					<div class="lui_list_operation_order_text">
						${ lfn:message('list.orderType') }：
					</div>
					<div class="lui_list_operation_sort_toolbar">
						<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
							<list:sortgroup>
								<list:sort property="docCreateTime" text="${lfn:message('km-review:kmReviewMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
								<list:sort property="docPublishTime" text="${lfn:message('km-review:kmReviewMain.docPublishTime') }" group="sort.list"></list:sort>
							</list:sortgroup>
						</ui:toolbar>
					</div>
				</div>
				<!-- 分页 -->
				<div class="lui_list_operation_page_top">
					<%@ include file="/sys/profile/showconfig/showConfig_paging_top.jsp" %>
				</div>
				<div style="float:right">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar id="Btntoolbar" count="2">
							<%-- 汇总审批 --%>
							<c:import url="/sys/lbpmservice/support/lbpm_summary_approval/button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
							</c:import>
						  	<%-- <kmss:authShow roles="ROLE_KMREVIEW_CREATE">
							 	<ui:button text="${lfn:message('button.add')}" id="add" onclick="window.parent.moduleAPI.kmReview.addDoc();" order="2" cfg-if="param['docStatus'] != '00' && param['docStatus'] != '32'" ></ui:button>
							</kmss:authShow>
							<kmss:authShow roles="ROLE_KMREVIEW_TRANSPORT_EXPORT">
							<ui:button 
									text="${lfn:message('button.export')}" id="export" 
									onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.review.model.KmReviewMain')"
									order="2" >
							</ui:button>
							</kmss:authShow> --%>
							<%-- 批量打印 --%>
							<kmss:authShow roles="ROLE_KMREVIEW_PRINTBATCH">
								<ui:button text="${lfn:message('km-review:kmReviewMain.printBatch')}" id="batchPrint" onclick="window.moduleAPI.kmReview.batchPrint();" order="3" ></ui:button>
							</kmss:authShow>
							<kmss:auth
								requestURL="/km/review/km_review_main/kmReviewMain.do?method=deleteall&categoryId=${param.categoryId}&nodeType=${param.nodeType}"
								requestMethod="GET">
								<ui:button id="del" text="${lfn:message('button.deleteall')}" order="4" onclick="window.moduleAPI.kmReview.delDoc()"></ui:button>
							</kmss:auth>
							<%-- 收藏 --%>
							<%--<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp" charEncoding="UTF-8">
								<c:param name="fdTitleProName" value="docSubject" />
								<c:param name="fdModelName"	value="com.landray.kmss.km.review.model.KmReviewMain" />
							</c:import>--%>
							<%-- 分类转移 --%>
							<kmss:auth
									requestURL="/km/review/km_review_main/kmReviewMain.do?method=changeTemplate&categoryId=${param.categoryId}&nodeType=${param.nodeType}"
									requestMethod="GET">
								<ui:button id="chgCate" text="批量修改分类" order="5" onclick="window.moduleAPI.kmReview.chgSelect();"></ui:button>
							</kmss:auth>
							<%-- 修改权限 --%>
							<c:import url="/sys/right/import/cchange_doc_right_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
								<c:param name="authReaderNoteFlag" value="2" />
								<c:param name="nodeType" value="${param.nodeType }"></c:param>
								<c:param name="categoryId" value="${param.categoryId}"></c:param>
							</c:import>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation" id="hack_fix"></ui:fixed>
			
			<list:listview id="listview" cfg-criteriaInit="${empty param.categoryId?'false':'true'}">
				<ui:source type="AjaxJson">
					{url:'/km/review/km_review_index/kmReviewIndex.do?method=list&pagingSetting=${showConfig.pagingSetting }&q.mydoc=${HtmlParam.mydoc}&q.doctype=${HtmlParam.doctype}'}
				</ui:source>
				<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.review.model.KmReviewMain" isDefault="true" layout="sys.ui.listview.columntable" rowHref="/km/review/km_review_main/kmReviewMain.do?method=view&fdId=!{fdId}">
					<list:col-checkbox></list:col-checkbox>
					<list:col-serial></list:col-serial>
					<list:col-auto></list:col-auto> 
				</list:colTable>
				<ui:event topic="list.loaded">
					window.parent && window.parent.updateCount();
					setTimeout(function(){
					   initRefer();
					},1000*60);
				</ui:event>
			</list:listview> 
			<br>
			<%@ include file="/sys/profile/showconfig/showConfig_paging_buttom.jsp"%>
	  </div>
	</template:replace>
	<template:replace name="script">
		<script>
			seajs.use([ 'lui/jquery','lui/topic' ], function(jquery,topic) {
				window.initRefer = function() {
					topic.publish('list.refresh');
				}
			})
		</script>
      	<script type="text/javascript" src="${LUI_ContextPath}/sys/ui/js/criteria/criterion_calendar_init_date.js"></script>
   		<%-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 --%>
      	<script type="text/javascript">
      		var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.review.model.KmReviewMain";
      
      		seajs.use(['lui/framework/module','lui/criteria/criterion_calendar_init_date','lui/topic'],function(Module, init_date,topic){
      			var loadDateVolume = '${showConfig.loadDataVolume}';
          		var startAndEndTime = getStartTimeAndEndTime(loadDateVolume,init_date);
          		
      			Module.install('kmReview',{
					//模块变量
					$var : {
						isAdmin : '${isAdmin}',
						startAndEndTime : startAndEndTime,
						startpath:'/index'
					},
 					//模块多语言
 					$lang : {
 						pageNoSelect : '${lfn:message("page.noSelect")}',
 						confirmFiled : '${lfn:message("sys-archives:confirm.filed")}',
 						optSuccess : '${lfn:message("return.optSuccess")}',
 						optFailure : '${lfn:message("return.optFailure")}',
 						buttonDelete : '${lfn:message("button.deleteall")}',
 						buttonFiled : '${lfn:message("sys-archives:button.filed")}',
 						changeRightBatch : '${lfn:message("sys-right:right.button.changeRightBatch")}'
 					},
 					//搜索标识符
 					$search : ''
  				});
      			
      			topic.subscribe('successReloadPage', function() {
      				window.parent.updateCount();
      			});

      		});
      		
      		function getStartTimeAndEndTime(value,func){
      			var time = 'docCreateTime:';
      			switch(value){
	      			case '1':
	      				time += func.getLastWeek();
	      				break;
	      			case '2':
	      				time += func.getLastMonth();
	      				break;
	      			case '3':
	      				time += func.getLast3Month();
	      				break;
	      			case '4':
	      				time += func.getLastHalfYear();
	      				break;
	      			case '5':
	      				time += func.getLastYear();
	      				break;
	      			case '6':
	      			default:
	      				return "";
	      		}
      			time = time + ';docCreateTime:'+func.getToday();
      			return time;
      		}
      		
      	</script>
      	<script type="text/javascript" src="${LUI_ContextPath}/km/review/resource/js/index.js"></script>
	</template:replace>
</template:include>