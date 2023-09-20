<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.profile.list">
	<template:replace name="head">
	</template:replace>
	<template:replace name="content">

    <style type="text/css">
	    .d_lui_mix_pop_ocr_pic{
    	    height: 38px;
		    line-height: 45px;
		    background: url(../img/ocr/upload_success.png) no-repeat center;
		    background-size: 100%,100%;
		    display: block;
		    margin-top: 1px;
			overflow: hidden;
	    }
	    .d_lui_mix_pop_ocr_pic_div{
	    	overflow: hidden;
	    }
    </style>
		<div class="d_lui_mix_pop_ocr_pic_div">
			<div class="d_lui_mix_pop_ocr_pic">
			</div>
		</div>
		<script>
		function init() {
			console.log("success");
			let interval = setInterval(function(){
				if($dialog) {
					//$dialog.hide();

				}				
			}, 200);
		}
		Com_AddEventListener(window, "load", init);
		</script>
	</template:replace>
</template:include>
