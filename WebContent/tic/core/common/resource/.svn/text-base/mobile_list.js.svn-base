require(["dojo/ready","mui/util","dijit/registry"], function(ready,util,registry){
	window.getSource = function(){
		var context = listOption.createDialogCtx;
		var sourceUrl = context.sourceUrl;
		var params={};
		if(context.params){
    		for(var i=0;i<context.params.length;i++){
    			var argu = context.params[i];
    			for(var field in argu){
    				var tmpFieldObj = document.getElementsByName(field);
    				if(tmpFieldObj.length>0){
    					var wgtObj = registry.getEnclosingWidget(tmpFieldObj[0]);
    					if(wgtObj!=null)
    						params['c.' + argu[field] + '.'+field] = wgtObj.get("value");
    				}
    			}
    		}
		}
		return util.setUrlParameterMap(sourceUrl,params);
	}
});