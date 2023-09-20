define(['dojo/request','dojo/topic','mui/util','dojo/dom-construct','dojo/dom-attr'], 
		function(_request,topic,util,domConstruct,domAttr) {
	
	var _queue = [];
	
	var queue = function(msg){
		_queue.push(msg);
	};
	
	var request = function(callback){
		//单独处理title
		var titleMsg = handleTitle();
		if(titleMsg){
			_queue.push(titleMsg);
		}
		var url = util.formatUrl('/sys/mobile/js/mui/i18n/i18nbatch.jsp');
		_request.post(url,{
			data : {
				queue : _queue.join(';')
			},
			handleAs : 'json'
		}).then(function(msgObj){
			topic.publish('mui.i18n.complete',msgObj);
			if(titleMsg){
				document.title = msgObj[titleMsg];
			}
			_queue = [];
			callback && callback();
		});
	};
	
	function handleTitle(){
		var title = document.title,
			titlebundle = null,
			titlekey = null;
		try{
			var dom = domConstruct.toDom(title);
			if(dom){
				var props = domAttr.get(dom,'data-dojo-props');
				props = myEval("{" + props + "}");
				titlebundle = props['bundle'];
				titlekey = props['key'];
				return !titlebundle ? titlekey : titlebundle + ':' + titlekey;
			}
		}catch(e){
			console.log(e);
		}
		return null;
	}
	
	function myEval(text){
		return eval("(" + text + ")");
	}
	
	return {
		queue : queue,
		request : request
	};
	
});