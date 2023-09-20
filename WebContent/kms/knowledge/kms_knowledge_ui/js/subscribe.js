define(function(require, exports, module) {

	var topic = require('lui/topic');
	var spaConst = require('lui/spa/const');

	topic.subscribe(spaConst.SPA_CHANGE_VALUES, function(e) {
		//后台设置页面不隐藏右侧iframe
		if(e.value && e.value['j_path'] == '/management'){
			return;
		}
		LUI.pageHide('_rIframe');
	});

	// 监听新建更新等成功后刷新
	topic.subscribe('successReloadPage', function() {

		topic.publish('list.refresh');
	});

	topic.subscribe(spaConst.SPA_CHANGE_VALUES, function(evt){
		if(evt.value && !evt.value['j_path']){
			//某些情况下j_path获取不到导致更多按钮被隐藏，此时，重新绘制
			//http://rdm.landray.com.cn/issues/163643
			//暂时采用此方法解决该问题
			if(LUI("kmsKnowledgeMoreBtn")){
				LUI("kmsKnowledgeMoreBtn").emit("redrawButton");
			}
		}
	});

})