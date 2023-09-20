/*流程汇总审批配置编辑页面的js*/
Com_IncludeFile("dialog.js");
(function(){
	if(window.$lbpmSummaryConfig){
		return;
	}
	window.$lbpmSummaryConfig = new lbpmSummaryConfig();
	function lbpmSummaryConfig(){
		this.topDialog = null;
		this.topBase = null;
		
		this.beforeSubmit = beforeSubmit;
		this.afterSelectProcessActon = afterSelectProcessActon;
		this.selectNodes = selectNodes;
		
		this.init = configInit;
		this.init();
	}
	
	function configInit(){
	}
	
	//提交前的操作
	function beforeSubmit(){
		if(!validate()){
			return false;
		}
		convertNoticeTimeToJson();
		
		return true;
	}
	
	//执行校验
	function validate(){
		//执行时间校验
		var fdNoticeTimeHour = $("[name='fdNoticeTimeHour']").val();
		var fdNoticeTimeMinute = $("[name='fdNoticeTimeMinute']").val();
		var fdNoticeTimeSecond = $("[name='fdNoticeTimeSecond']").val();
		
		if(isDoubleAll(fdNoticeTimeHour)){
			alert(errorInteger.replace(/\{0\}/, "时"));
			return false;
		}
		if(isDoubleAll(fdNoticeTimeMinute)){
			alert(errorInteger.replace(/\{0\}/, "分"));
			return false;
		}
		if(isDoubleAll(fdNoticeTimeSecond)){
			alert(errorInteger.replace(/\{0\}/, "秒"));
			return false;
		}
		if(!inInterval(fdNoticeTimeHour, "时", 0, 23)){
			return false;
		}
		if(!inInterval(fdNoticeTimeMinute, "分", 0, 59)){
			return false;
		}
		if(!inInterval(fdNoticeTimeSecond, "秒", 0, 59)){
			return false;
		}
		
		return true;
	}
	
	//处理发送时间，转换成json
	function convertNoticeTimeToJson(){
		var fdNoticeTimeHour = $("[name='fdNoticeTimeHour']").val();
		var fdNoticeTimeMinute = $("[name='fdNoticeTimeMinute']").val();
		var fdNoticeTimeSecond = $("[name='fdNoticeTimeSecond']").val();
		
		var fdNoticeTimeJson = {};
		fdNoticeTimeJson.hour = formatZero(parseInt(fdNoticeTimeHour),2);
		fdNoticeTimeJson.minute = formatZero(parseInt(fdNoticeTimeMinute),2);
		fdNoticeTimeJson.second = formatZero(parseInt(fdNoticeTimeSecond),2);
		
		$("[name='fdNoticeTimeJson']").val(JSON.stringify(fdNoticeTimeJson));
	}
	
	function inInterval(value, fieldMsg, minValue, maxValue){
		value = parseInt(value);
		if(isNaN(value)){
			alert(errorInteger.replace(/\{0\}/, fieldMsg));
			return false;
		}
		if(value<minValue || value>maxValue){
			var msg = errorRange.replace(/\{0\}/, fieldMsg);
			msg = msg.replace(/\{1\}/, minValue);
			msg = msg.replace(/\{2\}/, maxValue);
			alert(msg);
			return false;
		}
		return true;
	}
	
	//是否为小数（包括正负小数，0.0)
	function isDoubleAll(value){
	    var reg = /^(([0-9])|([0-9]([0-9]+)))$/;
	    return !reg.test(value);
	};
	
	//位数不够补零
	function formatZero(num, len) {
	    if((num+"").length > len) return num;
	    return (Array(len).join(0) + num).slice(-len);
	}
	
	//选择流程后的处理
	function afterSelectProcessActon(rtn){
		if(!rtn){
			return;
		}
		var data = rtn.data[0];
		if(!data){//取消选定
			$("[name='fdTemplateId']").val("");
			$("[name='fdTemplateName']").val("");
			$("[name='fdTemplateHierarchy']").val("");
			$("[name='fdNodeFactIds']").val("");
			$("[name='fdNodeFactNames']").val("");
			$("[name='fdTemplateKey']").val("");
		}else{
			var id = data.id;
			var name = data.name;
			var templateId = GetUrlParameter_Unescape(id, "templateId");
			var templateName = GetUrlParameter_Unescape(id, "templateName");
			var categoryId = GetUrlParameter_Unescape(id, "categoryId");
			var categoryName = GetUrlParameter_Unescape(id, "categoryName");
			var modelName = GetUrlParameter_Unescape(id, "modelName");
			var moduleName = GetUrlParameter_Unescape(id, "moduleName");
			var categoryHierarchy = GetUrlParameter_Unescape(id, "categoryHierarchy");
			var templateKey = GetUrlParameter_Unescape(id, "key");
			
			if(!templateId && !templateName){//简单分类
				var fdTemplateHierarchy = moduleName + "/" + categoryHierarchy;
				
				$("[name='fdTemplateId']").val(categoryId);
				$("[name='fdTemplateName']").val(categoryName);
				$("[name='fdTemplateHierarchy']").val(fdTemplateHierarchy);
				$("[name='fdTemplateKey']").val(templateKey);
			}else{//全局分类
				var fdTemplateHierarchy = moduleName + "/" + categoryHierarchy + "/" + templateName;
				
				$("[name='fdTemplateId']").val(templateId);
				$("[name='fdTemplateName']").val(templateName);
				$("[name='fdTemplateHierarchy']").val(fdTemplateHierarchy);
				$("[name='fdTemplateKey']").val(templateKey);
			}
			if(templateId != $("[name='fdTemplateIdTemp']").val()){
				$("[name='fdNodeFactIds']").val("");
				$("[name='fdNodeFactNames']").val("");
			}
			$("[name='fdTemplateIdTemp']").val(templateId);
		}
		//手动触发校验
		window._validation.validateElement($("[name='fdTemplateHierarchy']")[0]);
		window._validation.validateElement($("[name='fdNodeFactNames']")[0]);
	}
	
	/**
	 * 获取URL中的参数（使用unescape对返回参数值解码）
	 */
	window.GetUrlParameter_Unescape = function(url, param){
		var re = new RegExp();
		re.compile("[\\?&]"+param+"=([^&]*)", "i");
		var arr = re.exec(url);
		if(arr==null) {
			return null;
		} else {
			return unescape(arr[1]);
		}
	}
	
	//选择节点
	function selectNodes(idField, nameField, splitStr, isMulField){
		var tempId = $("[name='fdTemplateId']").val();
		var key = $("[name='fdTemplateKey']").val();
		if(!tempId){
			alert("请先选择模板！");
			return;
		}
		var dialog = new KMSSDialog(true, true);
		dialog.BindingField(idField, nameField, splitStr, isMulField);
		
		var action = function(){
			window._validation.validateElement($("[name='fdNodeFactNames']")[0]);
		}
		dialog.SetAfterShow(action);
		
		//获取流程中所有节点
		var wfNodes = getWfNodes(tempId,key);
		var data=new KMSSData();
		var ary = new Array();
		for(var i=0;i<wfNodes.length;i++){
			//设置选中节点
			var temp = new Array();
			temp["id"]= wfNodes[i].value;
			temp["name"]= wfNodes[i].value + ' ' + wfNodes[i].name;
			ary.push(temp);
						
		}
		
		data.AddHashMapArray(ary);
		dialog.optionData.AddKMSSData(data);
		dialog.Show(window.screen.width*520/1366,window.screen.height*400/768);
	}
	
	//获取流程的节点
	function getWfNodes(tempId,key){
		var rtn = [];
		var data = new KMSSData();
		var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do?method=findNodes&tempId=" + tempId + "&key="+key;
		data.SendToUrl(url, function(rq){
			var xml = rq.responseText;
			if (xml.indexOf('<error>') > -1) {
				alert(xml);
				rtn = [];
			} else {
				var processData = loadXMLData(xml);
				if(processData.nodes){
					for(var i=0; i<processData.nodes.length; i++) {
						var n = processData.nodes[i];
						if(n.groupNodeId && n.groupNodeType){//过滤自由流的审批节点和签字节点
							continue;
						}
						//只提供审批和签字节点
						if('signNode'==n.XMLNODENAME || 'reviewNode'==n.XMLNODENAME){
							rtn.push({value:n.id,name:n.name});
						}
					}
				}
			}
		}, false);
		return rtn;
	}
	
	function LBPM_Template_getNodes(processData) {
		var nodes = [];
		if(processData.nodes){
			for(var i=0; i<processData.nodes.length; i++) {
				var n = processData.nodes[i];
				var desc = lbpm.nodedescs[lbpm.nodeDescMap[n.XMLNODENAME]];
				if (desc.isHandler(n) && n.groupNodeId == null) {
					nodes.push({value:n.id,name:n.name,type:n.XMLNODENAME});
				}
			}
		}
		return nodes;
	}
	
	function loadXMLData(xml,isArray,nodeNames){
		var xmlObj = xml;
		if(typeof(xml) == "string"){
			if(xml == null || xml == ""){
				return;
			}
			if(window.ActiveXObject){
				xmlObj = new ActiveXObject("MSXML2.DOMDocument.3.0");
				xmlObj.loadXML(xml);
				xmlObj = xmlObj.firstChild;
			}else{
				var dp = new DOMParser();
			    var newDOM = dp.parseFromString(xml, "text/xml");
			    xmlObj = newDOM.documentElement;
			}
		}
		var rtnVal = new Array();

		//读取属性
		rtnVal.XMLNODENAME = xmlObj.nodeName;
		var attNodes = xmlObj.attributes;
		for(var i=0; i<attNodes.length; i++)
			rtnVal[attNodes[i].nodeName] = attNodes[i].value;
		if(rtnVal.CHILDRENISARRAY!=null){
			isArray = rtnVal.CHILDRENISARRAY=="true";
		}

		//读取节点内容
		if(nodeNames!=null){
			if(nodeNames.indexOf(xmlObj.nodeName)!=-1){
				return xmlObj.text;  
			}
		}

		//读取子对象
		for(var node=xmlObj.firstChild; node!=null; node=node.nextSibling){
			if(node.nodeType==1){
				if(isArray)
					rtnVal[rtnVal.length] = loadXMLData(node, false,nodeNames);
				else
					rtnVal[node.nodeName] = loadXMLData(node, true,nodeNames);
			}
		}
		return rtnVal;
	}
})();