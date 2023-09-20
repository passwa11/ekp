var dialogObject = null;
if(window.showModalDialog){
	dialogObject = window.dialogArguments;
}else{
	dialogObject = opener.Com_Parameter.Dialog;
}
//根据iframe里面内容高度，自动调整iframe窗口高度以及整个弹出窗口的高度
function dyniFrameSize(iframe) {
	var _iframe = iframe || "IF_sysRelation_content";
    var pTar = null;
    if (document.getElementById) {
        if (typeof _iframename == "string") {
			pTar = document.getElementById(_iframe);
        } else {
        	pTar = _iframe;
		}
    } else{
  	  	eval('pTar = ' + _iframe + ';');
    }
	try {
	    if (pTar && !window.opera) {
	        //begin resizing iframe
	        pTar.style.display = "block";
	        if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight) {
	        	//ns6 syntax
				pTar.height = pTar.contentDocument.body.offsetHeight+20;
				//pTar.width = pTar.contentDocument.body.scrollWidth+20;
	        } else if (pTar.Document && pTar.Document.body.scrollHeight){
	       	 	//ie5+ syntax
				pTar.height = pTar.Document.body.scrollHeight;
				//pTar.width = pTar.Document.body.scrollWidth;
				// 调整父Iframe
				if (param_frameName != "") {
					if (parent.document.getElementById(param_frameName)) {
						parent.document.getElementById(param_frameName).style.height=document.body.scrollHeight+7;
					}
				}
	        }
	    }
	} catch(e) {
		pTar.width = "100%";
		pTar.height = "100%";
	}
}

var params = "";
//加载结果数据
function loadSysRelationEntiry(loadIndex,_this) {
	var param = params.split("&separator=0");
	var itemParamStr = param[loadIndex];
	itemParamStr = itemParamStr+"&loadIndex="+loadIndex
	var $from = createIframeParamForm(itemParamStr);
    $("body").append($from);  // 将表单放置在页面body中
    $from.submit();  // 表单提交
    $from.remove();  // 移除表单
	setBackground(_this);
}


/**
* 创建一个表单，用于将参数以POST方式提交到IFrame，避免URL带的参数过长时，以GET方式请求会导致参数丢失
* @return
*/	
function createIframeParamForm(itemParamStr){
	var itemParamArray = itemParamStr.split("&");
	var hiddenArray = [];
	for(var i=0;i<itemParamArray.length;i++){
		var rowParam = itemParamArray[i];
		var rowParamArray = rowParam.split("=");
		if(rowParamArray&&rowParamArray.length>0){
			var key = decodeURIComponent(rowParamArray[0]);
			var value = decodeURIComponent(decodeURI(rowParamArray[1]))||"";
			var $hidden = $('<input type="hidden" />');
			$hidden.attr("name",key);
			$hidden.attr("value",value);
			hiddenArray.push($hidden);
		}	
	}
	var previewUrl = sysRelationMain_do_url+"?method=preview";
	var $form = $("<form>");
	$form.attr("style","display:none");  // form隐藏
	$form.attr("method","post");         // 请求类型（以post方式提交）
	$form.attr("action",previewUrl);     // 预览action路径
	$form.attr("target","relation_form_iframe"); // 提交的目标元素，这里指向到关联展现的IFrame
	for(var i=0;i<hiddenArray.length;i++){
		$form.append(hiddenArray[i]);
	}
	return $form;
}

function preview(){
	var url = Com_Parameter.ContextPath+'sys/relation/sys_relation_main/sysRelationMain.do?method=previewResultCount';
	if(window.dialogObject && window.dialogObject.relationEntry){
		params = window.dialogObject.relationEntry.p;
	}
 	$.ajax({
	 	type: "POST",
	   	url: url,
	    async:true,
	    processData: false,
	    data: params+"&loadIndex=0",
	    dataType:'html',
	    success: function(resHtml){
		 	document.getElementById("count").innerHTML = resHtml;
			var aTagObj = document.getElementById("count").getElementsByTagName("a")[0];
			if(aTagObj)
				aTagObj.style.backgroundColor = "yellow";
	    }
	 });
	loadSysRelationEntiry(0);//默认加载第一项
}
function setBackground(_this){
	var aTagList = document.getElementById("count").getElementsByTagName("a");
	for(var i=0;i<aTagList.length;i++){
		aTagList[i].style.backgroundColor = "";
	}
	if(_this)
		_this.style.backgroundColor = "yellow";
}
Com_AddEventListener(window, "load", preview);