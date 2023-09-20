<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<template:replace name="title">
		<c:out value="${ lfn:message('sns-group:module.sns.group') }"></c:out>
	</template:replace>
	<template:replace name="content">
		<script>
								
								var datas = {
										data : [
												{
													value : '${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=14b5760e70eb49b7282b86f46e29f86a'
												}],
										value : '${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=14b5760e70eb49b7282b86f46e29f86a',
										valueType:'url'
								};

							
								function test() {

									seajs
											.use(
													[ 'lui/imageP/preview' ],
													function(preview) {
														preview({
															data : datas, 
															//path: {data:[],pathId:''},//pathId为当前页面ui path对象id；data为路径数据。二选一
															panel : {
																url : 'http://www.sina.com',//panel路径，可以携带!{**},替换来源于data数据，例如上面的cateId
																type : 'iframe'//可选为iframe和text
															}
														});
													});
								}
								
								
			/*点击更改path范例
			seajs.use(['lui/topic'],function(topic){
				topic.subscribe('preview/change',function(evt){
					//evt = {value : '当前值', index : '当前索引',data : 数据包};，判断后发出事件重回path对象
					topic.publish('preview/pathChange',{data:[{'text':'主页','href':''},{'text':'分类1'},{'text':'分类2'}]})
				})
				
			})
			 */
		</script>
		<button value="test" onclick="test()">test</button>
	</template:replace>
</template:include>