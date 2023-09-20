<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/fssc/mobile/common/attachement/attachment_view.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/lang.jsp" %>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/Mdate.css?s_cache=${LUI_Cache }">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/popups.css">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/search.css">
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/jquery.min.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/common.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/picker.min.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/Mdate.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/iScroll.js?s_cache=${LUI_Cache }"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/zepto.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/dingtalk.open.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/popups.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/common/attachement/attachment.js"></script>
<script>
	$(function(){
		$("#top").remove();  //移除回到最上的图标
		$(".checkbox_item").click(function(){
			var name = $(this).find("input[type=radio]").attr("name");
			var value = $(this).find("input[type=radio]").val();
			var subName = name.substring(1,name.length);
			$("[name='"+name+"']").parent().removeClass("checked");
			$("[name='"+name+"']").prop("checked",false);
			$(this).find("input[type=radio]").prop("checked",true);
			$(this).addClass("checked");
			$(this).parent().find("[name='"+subName+"']").val(value);
			var callback = $(this).parent().find("[name='"+subName+"']")[0].onclick;
			if(callback)callback();
		})
		$(".checkbox_item_multi").click(function(){
			var name = $(this).find("input[type=checkbox]").attr("name");
			var value = $(this).find("input[type=checkbox]").val();
			var subName = name.substring(1,name.length);
			if($(this).hasClass("checked")){
				$(this).removeClass("checked");
				$(this).find("input[type=checkbox]").prop("checked",false);
			}else{
				$(this).addClass("checked");
				$(this).find("input[type=checkbox]").prop("checked",true);
			}
			console.log(name)
			var vals = [];
			$("[name='"+name+"']:checked").each(function(){
				vals.push(this.value);
				console.log(this.value)
			})
			$(this).parent().find("[name='"+subName+"']").val(vals.join(';'));
			var callback = $(this).parent().find("[name='"+subName+"']")[0].onclick;
			if(callback)callback();
		})
	})
</script>
