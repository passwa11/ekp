<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:ajaxtext>
	<%-- 此处为浏览器窗口标题 --%>
	<div data-dojo-block="title">
	</div>
	<%-- 此处为内容 --%>
	<div data-dojo-block="content">
		<script>
			var isAvailable = '${fdIsAvailable}';
			//初始化的时候进行弹窗的处理
			require(["dojo/topic","mui/dialog/Alert","mui/device/adapter"],function(topic,Alert,adapter){
				//加载完毕后如果判断
				if(isAvailable == 'false'){
					Alert('<bean:message  bundle="km-review" key="kmReviewTemplate.msg.notAvailable.mobile"/>',null, function(){
						//返回上一页
						var rtn = adapter.goBack();
						if(rtn == null){
							history.back();
						}
					});
				}
			})
		</script>
	</div>
</ui:ajaxtext>