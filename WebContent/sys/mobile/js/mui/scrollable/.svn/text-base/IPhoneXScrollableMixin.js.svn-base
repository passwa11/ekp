define( [ "dojo/_base/declare"], function(declare) {

	function isIPhoneX(){
		var iOS = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream,
			ratio = window.devicePixelRatio || 1;
		var screen = {
		    width : window.screen.width * ratio,
		    height : window.screen.height * ratio
		};
		if (iOS && screen.width == 1125 && screen.height === 2436) {
			return true;
		}
		return false;
	}
	
	
	return declare("mui.scrollable.IPhoneXScrollableMixin", null, {

		scrollType : isIPhoneX() ? 3 : null
		
	});
});