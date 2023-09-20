seajs.use(['lui/topic','lui/jquery'],function(topic, $){
	//订阅页面resize事件（通常由页眉发出）
	$('body').addClass('tTemplate');
	topic.subscribe('lui.page.resize',resize);
	function resize(){
		var $header = $('.lui_portal_header'),
			$headerH = $header && $header.length > 0 ? $header.height() : 0,
			___evt = {
				'top' : $headerH + 'px'
			};
		//bad hack
		var $aside = $('.lui_list_left_sidebar_frame');
			$aside.css(___evt);
		var $body = $('.lui_list_body_frame');
			$body.css({ 'margin-top' : $headerH + 'px' });
			
		
		var $marginNeed =  $('[data-lui-mark="need-header-margin"]'), 
			lmt = $headerH;
		$marginNeed.css({ 'margin-top' : lmt + 'px' });
			
	}
	if(LUI.luihasReady){
		resize();
	}else{
		LUI.ready(function(){
			resize();
		});
	}
});