/*
 * 附件上传类
 */
define([
		"mui/device/attachment/_attachmentDirect",
		"mui/device/attachment/_attachmentProxy" ], function(
		_attachmentDirect,
		_attachmentProxy) {

	// 支持直连则使用直连上传插件
	if (window.attachmentConfig && window.attachmentConfig.isSupportDirect) {
		return _attachmentDirect;
	}

	return _attachmentProxy;

});
