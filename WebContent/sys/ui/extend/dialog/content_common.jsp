<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
<div class="lui_dialog_content_{%layout.parent.iconType%}"></div>
<div class="lui_dialog_common_content_right">
	<div data-lui-mark="dialog.content.inside" class="lui_dialog_common_content_inside">
	</div>
	<div data-lui-mark="dialog.content.buttons" class="lui_dialog_common_buttons clearfloat">
		
	</div>
</div>
<script type="text/javascript">
$(function(){
	if(navigator.userAgent.indexOf("MSIE")>0 && navigator.appVersion.match(/8./i)=="8.") { 
        $(".lui_dialog_mask").css("zIndex", 2147483584);
		$(".lui_dialog_main").css("zIndex", 2147483647);
   	}
});
</script>
$}
