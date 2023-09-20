//自适应大小
var bodySizeCache = {};
var listResize = function() {
	if(parent.dialoging){
		return;
	}
	var size = domain.getBodySize();
	if (size.width === bodySizeCache.width
			&& size.height === bodySizeCache.height) {
		return;
	}
	bodySizeCache = size;
	domain.call(parent,
			parent.identityType == "clientIndex" ? "filestoreClientEvent"
					: "filestoreQueueEvent", [ {
				type : "event",
				target : domain.getParam(window.location.href, 'LUIID'),
				name : "resize",
				data : {
					height : size.height,
					width : size.width
				}
			} ]);
};

var startAutoResize = function() {
	listResize();
	window.setTimeout(startAutoResize, 300);
};