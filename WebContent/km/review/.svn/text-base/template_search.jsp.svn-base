<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<%-- 右侧页面 --%>
	<template:replace name="body">
		<%-- 查询栏 --%>
		<div style="margin-top:10px">
			<list:criteria id="criteria1">
				<%-- 搜索条件:文档标题--%>
				<list:cri-ref key="fdName" ref="criterion.sys.docSubject">
				</list:cri-ref>
			</list:criteria>
		</div>
		<!-- 操作栏 -->
	<%--	<kmss:ifModuleExist path="/third/mall/">
			<div class="lui_list_operation" style="position: absolute;top:10px ;right:20px;z-index: 100;border-bottom:none;">
				<kmss:auth requestURL="/km/review/km_review_template/kmReviewTemplate.do?method=add&parentId=${param.parentId}" requestMethod="GET">
					<ui:button text="${lfn:message('km-review:kmReviewTemplate.enterTemplateCenter.info')}" onclick="goMallCenter();" order="2" ></ui:button>
				</kmss:auth>
			</div>
		</kmss:ifModuleExist>--%>
		<%--list页面--%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/km/review/km_review_template/kmReviewTemplateSearch.do?method=listChildren'}
			</ui:source>
			<%--摘要视图--%>
			<list:rowTable isDefault="false" rowHref="/km/review/km_review_template/kmReviewTemplate.do?method=view&fdId=!{fdId}" name="rowtable">
				<list:row-template>
					{$
					<div class="clearfloat lui_listview_rowtable_summary_content_box" kmss_fdId="{%row.fdId%}">
						<dl>
							<dt><span class="lui_listview_rowtable_summary_content_serial">{%row.index%}</span></dt>
						</dl>
						<dl>
							<dt>
								<a class="textEllipsis com_subject" title="{%row.fdName%}" onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/km/review/km_review_template/kmReviewTemplate.do?method=view&fdId={%row.fdId%}" target="_blank" data-lui-mark-id="{%row.rowId%}">{%row.fdName%}</a>
							</dt>
							<dd class="lui_listview_rowtable_summary_content_box_foot_info">
								<span>${lfn:message('km-review:kmReviewTemplate.creategory.path') }：{%row['creategoryFullName']%}</span>
								<span>${lfn:message('sys-doc:sysDocBaseInfo.docCreator') }：{%row['docCreator']%}</span>
								<span>${lfn:message('km-review:kmReviewTemplate.docCreateTime') }：{%row['docCreateTime']%}</span>
							</dd>
						</dl>
					</div>
					$}
				</list:row-template>
			</list:rowTable>
			<ui:event topic="list.loaded">
				$(".criteria-extra").remove();
				$(".lui_listview_rowtable_summary_content_box").last().css("border-bottom","2px solid #ccc");
			</ui:event>
		</list:listview>
		<br>

		<list:paging></list:paging>
	</template:replace>

</template:include>

<script type="text/javascript">
	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		<kmss:ifModuleExist path="/third/mall/">
		window.goMallCenter =function(){


			var kmReviewUrl="<c:url value="/km/review/km_review_template/kmReviewTemplate.do" />?method=openMallTemplate";
			$.ajax({
				url: kmReviewUrl,
				type: 'POST',
				data:{},
				dataType: 'json',
				error: function(data){
				},
				success: function(data){
					if(data){
						if(!data.netWork_reachable){
							dialog.alert("${lfn:message('third-mall:thirdMall.no_network_tip')}");
							return;
						}
						if(!data.isAuth){
							dialog.alert("${lfn:message('third-mall:mui.thirdMall.noAuth')}");
							return;
						}
						var createUrl =data.__absPath+"km/review/km_review_template/kmReviewTemplate.do?method=add&parentId=${param.parentId}";
						var url = createUrl + "&sourceFrom=Reuse&sourceKey=Reuse&type=2";
						url =data.moreURL+"&product="+data.productName+"&sysVerId="+data.version+"&createUrl="+encodeURIComponent(url);
						Com_OpenWindow(url);
					} else {
						dialog.alert("${lfn:message('third-mall:thirdMall.opt.failure')}");
					}
				}
			});

		}
		</kmss:ifModuleExist>
	});
</script>
