<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld"
	prefix="template"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld"
	prefix="xform"%>
<template:include ref="mobile.view">
	<template:replace name="title">
		表单样例
	</template:replace>

	<template:replace name="content">

		<style>
.muiAccordionPanelContent {
	padding: 0 1rem;
}

.muiAccordionPanelContent>span {
	color: blue;
	font-size: 1.2rem;
}

.muiAccordionPanelTitle {
	background-color: #2e64aa;
}

.muiAccordionPanelTitle>div {
	color: #fff;
}
</style>
		<div data-dojo-type="mui/view/DocScrollableView">
			<div data-dojo-type="mui/panel/AccordionPanel">

				<div data-dojo-type="mui/panel/Content"
					data-dojo-props="title:'多行文本'">
					<span>编辑状态</span>
					<div id="div_summary" data-dojo-type="mui/form/Textarea"
						data-dojo-props="name:'input1',value:'请输入摘要；请输入摘要；请输入摘要；请输入摘要；请输入摘要请输入摘要；请输入摘要；请输入摘要;请输入摘要；请输入摘要；请输入摘要；请输入摘要；请输入摘要；请输入摘要；'"></div>
				</div>

			</div>
			<br/>
			<div onclick="test();" style="text-align: center;">修改值</div>
		</div>
		<script type="text/javascript">
		require(['dijit/registry'],function(registry){
			window.test=function(){
				var wgt = registry.byId("div_summary");
				wgt.set("value","请输入摘要；请输入摘要请输入摘要；请输入摘要；");
			};
		});
		</script>
	</template:replace>
</template:include>
