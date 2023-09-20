$(function(){
	$(".lui-ding-audit-postscript").each(function(){
		$(this).find(".lui-ding-audit-folder").click(function(){
			var $postscript = $(this).closest(".lui-ding-audit-postscript");
			$postscript.find(".lui-ding-audit-expand").show();
			if (!$postscript.hasClass("collapse")) {
				$postscript.addClass("collapse");
			}
			$(this).hide();
		});
		$(this).find(".lui-ding-audit-expand").click(function(){
			var $postscript = $(this).closest(".lui-ding-audit-postscript");
			 $postscript.find(".lui-ding-audit-folder").show();
			if ($postscript.hasClass("collapse")) {
				$postscript.removeClass("collapse");
			}
			$(this).hide();
		});
	})
})