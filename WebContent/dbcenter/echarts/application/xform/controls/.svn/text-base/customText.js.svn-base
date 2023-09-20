/**
 * 
 */
(function(){
	
	function CustomText(config){
		
		this.domNode;
		this.url = "";
		
		this.requestParams = {};
		
		this._init = _CustomText_Init;
		this.load = CustomText_Load;
		
		this._init(config);
	}
	
	function _CustomText_Init(config){
		this.domNode = config.domNode ? config.domNode : null;
		this.url = config.url ? config.url : null;
	}
	
	function CustomText_Load(){
		var rtnTxt = "";
		$.ajax({
			url : this.url,
			type : "get",
	        dataType : 'text',
	        data : this.requestParams,
	        cache : false,
	        async : false, // 同步
        	success: function(text,textStatus, jqXHR) {
        		rtnTxt = text; 
            }
		});
		this.domNode.html(rtnTxt);
	}
	
	window.CustomText = CustomText;
})()