<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<%--内容区--%>
	<template:replace name="body">
		<div id="graphContainer"
			style="position:relative;overflow:auto;width:600px;height:400px;background:url('${LUI_ContextPath}/sys/ui/js/mxgraph/lib/images/grid.gif');">
		</div>
		<script>
			window.mxBasePath = Com_Parameter.ContextPath + 'sys/ui/js/mxgraph/lib';
		</script>
		<script src="${LUI_ContextPath}/sys/ui/js/mxgraph/lib/mxclient.js"></script>
		<script>
			seajs.use(['lui/mxgraph/mxgraph'], function(MxGraph){
				new MxGraph(
						document.getElementById('graphContainer'),
						{
							nodes: [
								{ id: 'b', label: '流程管理', level: 1 },
								{ id: 'a', label: '核心模块', level: 0 },
								{ id: 'd', label: '新闻管理', level: 2 },
								{ id: 'c', label: '附件管理', level: 1 }
							],
							edges: [
								{ source: 'a', target: 'a' },
								{ source: 'a', target: 'b' },
								{ source: 'a', target: 'c' },
								{ source: 'c', target: 'd' }
							]
						},
						{
							onClickNode: function(value) {
								alert('node: ' + JSON.stringify(value));
							},
							onClickEdge: function(value) {
								alert('edge: ' + JSON.stringify(value));
							},
						}
				);
			});
		</script>
	</template:replace>
</template:include>