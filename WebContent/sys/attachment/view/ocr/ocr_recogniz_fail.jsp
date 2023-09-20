<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.profile.list">
	<template:replace name="head">
	</template:replace>
	<template:replace name="content">

    <style type="text/css">
		.d_lui_mix_pop_ocr_title{
			margin-top:15px;
			margin-left: 83px;
			font-family: PingFang SC;
			font-size: 14px;
		}
		.d_lui_mix_pop_ocr_content{
			margin-top:10px;
			margin-left: 87px;
			font-family: PingFang SC;
			font-size: 12px;
		}
		.pop_ocr_content_detail{
			font-family: PingFang SC;
			font-size: 12px;
		}
	    .d_lui_mix_pop_ocr_pic_div{
	    	overflow: hidden
	    }
		.d_lui_mix_pop_ocr_icon i{
			background-image: url(../img/del.png);
		}
    </style>
		<div class="d_lui_mix_pop_ocr">
			<div class="d_lui_mix_pop_ocr_icon">
				<i></i>
			</div>
			<div class="d_lui_mix_pop_ocr_pic_div">
				<div style="float:left;margin-top: 18px;margin-left: 30px">
					<image src="../img/ocr/warn.png" />
				</div>
				<div class="d_lui_mix_pop_ocr_title">
				</div>
				<div class="d_lui_mix_pop_ocr_content">
					<span class="pop_ocr_content_type"></span>
					<span class="pop_ocr_content_detail"></span>
				</div>
			</div>
		</div>
		<script>
			function init() {
				var type = '${param.type }';
				console.log("type:"+type);
				if('recognizFail' == type){
					//上传成功，识别失败--后续需支持配置-国际化
					$(".d_lui_mix_pop_ocr_title").text("<kmss:message bundle='sys-attachment' key='sysAttachment.ocr.unrecognized' />");
					$(".pop_ocr_content_type").text("<kmss:message bundle='sys-attachment' key='sysAttachment.ocr.try' />");
					$(".pop_ocr_content_detail").html("<p><kmss:message bundle='sys-attachment' key='sysAttachment.ocr.reUploadValid' /><br><kmss:message bundle='sys-attachment' key='sysAttachment.ocr.zoomOut' />");
				}
				if('uploadFail' == type){
					//上传失败--后续需支持配置-国际化
					$(".d_lui_mix_pop_ocr_title").text("<kmss:message bundle='sys-attachment' key='sysAttachment.ocr.failReason' />");
					$(".pop_ocr_content_type").text("<kmss:message bundle='sys-attachment' key='sysAttachment.ocr.failReasonSize' />");
					$(".pop_ocr_content_detail").text("<kmss:message bundle='sys-attachment' key='sysAttachment.ocr.failReasonFormat' />");
				}
			}
			Com_AddEventListener(window, "load", init);
		</script>
	</template:replace>
</template:include>
