define(function(require, exports, module) {

	var env = require('lui/util/env');

	function goToView(categoryId, moduleUrl, type) {
		var fdUrl = "";
		var template=1;
		if (!moduleUrl) {
			return fdUrl;
		}

		fdUrl = moduleUrl;
		
		if(type=='com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge')
			template=1;
		if(type=='com.landray.kmss.kms.wiki.model.KmsWikiMain')
			template=2;
		if(type=='com.landray.kmss.kms.kem.model.KmsKemMain')
			template=3;
			
		fdUrl = fdUrl + "#cri.q=template:" + template;
		
		if(categoryId && categoryId.indexOf(";") > -1) {
			categoryId = ""
		}
		if (categoryId) {
			fdUrl = fdUrl + "&docCategory=" + categoryId;
		}

		window.open(Com_Parameter.ContextPath + fdUrl, '_blank');
	}

	exports.goToView = goToView;
});