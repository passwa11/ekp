(function(){
	if(window.echartschart!=null){
		return;
	}
	var echartschart = {}; 
	
	echartschart.toInt = function(value){
		if(value==null){
			return null;
		}
		if(LUI.$.isArray(value)){
			for(var i=0; i<value.length; i++){
				value[i] = parseInt(value[i], 10);
			}
		}else{
			value = parseInt(value, 10);
		}
		return value;
	};
	
	echartschart.toFloat = function(value){
		if(value==null){
			return null;
		}
		if(LUI.$.isArray(value)){
			for(var i=0; i<value.length; i++){
				value[i] = parseFloat(value[i]);
			}
		}else{
			value = parseFloat(value);
		}
		return value;
	};
	
	echartschart.toJSON = function(){
		var result = [];
		for(var i=0; i<arguments[1].length; i++){
			var json = {};
			for(var j=0; j<arguments.length; j+=2){
				json[arguments[j]] = arguments[j+1][i];
			}
			result[i] = json;
		}
		return result;
	};
	
	echartschart.open = function(href, params, chart, inner){
		var index = 0;
		var url = '';
		var re = /!\{([^\}]+)\}/g;
		var value;
		for(var arr = re.exec(href); arr!=null; arr=re.exec(href)){
			url += href.substring(index, arr.index);
			switch(arr[1]){
			case 'name':
				value = params.name;
				break;
			case 'value':
				value = params.value;
				break;
			case 'series':
				value = params.seriesName;
				break;
			default:
				value = echartschart.findData(chart.KmssData, arr[1], params.dataIndex);
				if(value==null){
					value = echartschart.findData(chart.getOption().series, arr[1], params.dataIndex);
				}
			}
			if(value!=null){
				url += value;
			}
			index = arr.index + arr[0].length;
		}
		url += href.substring(index);
		if(inner) {
			echartschart.innerShowDetail(url,chart,params.name);
		}else {
			window.open(url, 'blank');
		}
	};
	//内部图表查看数据页面
	echartschart.innerShowDetail = function(url,chart,title) {
		var fid = null;
		if(chart && chart.id) {
			fid = 'innerShow-'+chart.id;
		}else {
			fid = 'innerShow-'+new Date().getTime();
		}

		var pNode = chart._dom.parentNode;  //查看页面父节点
		echartschart.innerCloseDetail(fid);
		var _frame = document.createElement('iframe');
		_frame.id = fid;
		_frame.style.width = '100%';
		_frame.style.height = '220px';
		_frame.style.overflow = 'auto';
		_frame.style.border = '0';
		_frame.style.position = 'relative';
		_frame.style.borderTop = '1px solid #eee';
		pNode.appendChild(_frame);
		var cidx = url.indexOf('#cri.q');
		if(cidx > -1) {
			var url1 = url.substring(0,cidx);
			var url2 = url.substring(cidx);
			var paramUrl = 'showButton=0&showDetail=1&iframeHeightFollowContent=1&title='+title+'&fid='+fid+url2;
			if(url.indexOf('?')>-1) {
				url = url1+'&'+paramUrl;
			}else {
				url = url1+'?'+paramUrl;
			}
		}else {
			var paramUrl = "showButton=0&showDetail=1&iframeHeightFollowContent=1&title="+title+'&fid='+fid;
			if(url.indexOf('?')>-1) {
				url += '&'+paramUrl;
			}else {
				url += '?'+paramUrl;
			}
		}
		_frame.src = url;
	};
	echartschart.innerCloseDetail = function(id) {
		var container = document.getElementById(id);
		if(container != null) {
			container.parentNode.removeChild(container);
			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
				var h = window.frameElement.style.height;
				h = parseInt(h.substring(0,h.length - 2));
				window.frameElement.style.height = (h - 225)+'px';
			}
		}
	};
	echartschart.findData = function(series, key, index){
		if(series){
			for(var i=0; i<series.length; i++){
				if(series[i].name==key){
					return series[i].data[index];
				}
			}
		}
		return null;
	};
	echartschart.param = function(){
		var url = arguments[0];
		var hash = location.hash;
		if(hash && hash.substring(0,7)!='#cri.q='){
			return url;
		}
		hash = hash.substring(7);
		var q = null;
		if(arguments.length==1){
			q = hash;
		}else{
			var arr = hash.split(';');
			for(var i=1; i<arguments.length; i++){
				for(var j=0; j<arr.length; j++){
					if(arr[j].substring(0, arguments[i].length+1)==arguments[i]+':'){
						if(q==null){
							q = arr[j];
						}else{
							q += ';' + arr[j];
						}
					}
				}
			}
		}
		if(q==null || q==''){
			return url;
		}
		var index = url.indexOf('#cri.q=');
		if(index==-1){
			return url + '#cri.q=' + q;
		}
		if(index==url.length-7){
			return url + q;
		}
		return url + ';' + q;
	};
	
	window.echartschart = echartschart;
})();