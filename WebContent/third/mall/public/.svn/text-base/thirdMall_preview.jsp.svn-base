<%@ page import="com.landray.kmss.third.mall.util.MallUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%
	request.setAttribute("mallDomMain", MallUtil.MALL_DOMMAIN);
%>
<script type="text/javascript">
	Com_IncludeFile("common.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);

	LUI.ready(function(){
		seajs.use(['lui/jquery'],function($) {
			//等待$dialog初始化完成，否则会出现IE11预览图无法进行预览
			var interval = setInterval(____Interval, 50);
			function ____Interval() {
				if (!window['$dialog'])
					return;
				showImgWrap();
				clearInterval(interval);
			}

			function showImgWrap(){
				var applicationParams = $("input[name='mainParams']", window.top.document);
				var mainParamsType = $("input[name='mainParamsType']", window.top.document);
				var fdType="";
				if(mainParamsType ){
					fdType=mainParamsType.val();
				}
				if (applicationParams && applicationParams.val()) {
					var obj = JSON.parse(applicationParams.val());
					for(var i=0;i<obj.length;i++){
						var objName=obj[i]['picAttName'];
						var method="downloadPic&fdId=";
						if(fdType=='login'){
							objName="${lfn:message('third-mall:thirdMall.preview.title.login')}";
						}else if(fdType=='theme'){
							objName="${lfn:message('third-mall:thirdMall.preview.title.theme')}";
						}else if(fdType=='component'){
							objName="${lfn:message('third-mall:thirdMall.preview.title.component')}";
						}
						var objId=obj[i]['picAttId'];
						var tempTitle=$('<span data-id='+objId+' class=\"client '+(i==0 ?' typeActive ':'') +'\">'+objName+'</span>');
						$('#preViewTitle').append(tempTitle);
						var url="${mallDomMain}/km/reuse/mobile/kmReusePublicMobileAction.do?method="+method+objId;
						var imgWrap =$("#imgWrap")
						if(i==0){
							imgWrap.attr('src',url);
							imgWrap.show();
						}
						tempTitle.on('click',function(e){
							var currentTarget=$(e.currentTarget);
							url="${mallDomMain}/km/reuse/mobile/kmReusePublicMobileAction.do?method=downloadPic&fdId="+currentTarget.data("id");
							var client= $('.client');
							if(client && client.length > 0){
								client.attr('class','client');
							}
							currentTarget.attr('class','client typeActive');
							imgWrap.attr('src',url);
						})
					}
				}
			}
		});
	});
</script>
<html>
<body style="overflow:hidden;">
<div class="fy-process-preViews">
	<div class="preViews-title" id="preViewTitle">
	</div>
	<div class="preViews-content">
		 <span class="imgWrap">
			<img  id="imgWrap" src="" style="display: none"  onerror="this.src='${LUI_ContextPath}/third/mall/resource/images/no-thumb.png'" />
		 </span>
	</div>
</div>
</body>
</html>