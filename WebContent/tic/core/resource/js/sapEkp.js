//扩展selectNodes方法
if(!(!!window.ActiveXObject || "ActiveXObject" in window)){
	 // prototying the XMLDocument 
	XMLDocument.prototype.selectNodes = function(cXPathString, xNode) {
	 	if( !xNode ) { 
	      xNode = this; 
		} 
		var oNSResolver = this.createNSResolver(this.documentElement); 
		var aItems = this.evaluate(cXPathString, xNode, oNSResolver,XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null) ;
		var aResult = []; 
		for( var i = 0; i < aItems.snapshotLength; i++) { 
		     aResult[i] = aItems.snapshotItem(i); 
		   } 
		   return aResult; 
	} 
	
	// prototying the Element 
	Element.prototype.selectNodes = function(cXPathString) { 
	    if(this.ownerDocument.selectNodes) { 
	         return this.ownerDocument.selectNodes(cXPathString, this); 
	    } else{
	         throw "For XML Elements Only";
	    } 
	 } 
	
	XMLDocument.prototype.selectSingleNode = function(cXPathString, xNode) {
		if(!xNode ) { 
		   xNode = this; 
		} 
	   var xItems = this.selectNodes(cXPathString, xNode); 
	   if( xItems.length > 0 ) { 
		  return xItems[0]; 
	   } else { 
	    return null; 
	   } 
	} 
	
	// prototying the Element 
	Element.prototype.selectSingleNode = function(cXPathString) { 
	   if(this.ownerDocument.selectSingleNode) { 
	      return this.ownerDocument.selectSingleNode(cXPathString, this); 
	   } else{
	      throw "For XML Elements Only";} 
	   } 

}
/**
 * 获取对象中的数据字典，用于公式定义器左边树展现
 * @return
 */
function TIC_XForm_GetSysDictObj(modelName){
	return new KMSSData().AddBeanData("ticCoreMappingDictVarTree&modelName="+modelName).GetHashMapArray();
}

//解析xml，把xml格式字符串转化为对象
function XML_CreateByContent(xmlContent){
	/* var xml = new ActiveXObject("MSXML.DOMDocument");
		xml.loadXML(xmlContent);
		return xml;
	
	try //Internet Explorer
	  {
		  xmlDoc=new ActiveXObject("MSXML.DOMDocument");//Microsoft.XMLDOM
		  xmlDoc.async="false";
		  xmlDoc.loadXML(xmlContent);
	  }catch(e){
	  try //Firefox, Mozilla, Opera, etc.
	    {
		   parser=new DOMParser();
		   xmlDoc=parser.parseFromString(xmlContent,"text/xml");
	    }catch(e) {
	    	alert(e.message);
	    }
	  }
	  */
	//debugger;
	  var xml = {};
		if(!!window.ActiveXObject || "ActiveXObject" in window){
			xml = new ActiveXObject("MSXML2.DOMDocument.3.0");		
			xml.loadXML(xmlContent);
		}else{
			xml = document.implementation.createDocument("","",null);
			var dp = new DOMParser();
		    var newDOM = dp.parseFromString(xmlContent, "text/xml");
		    var newElt = xml.importNode(newDOM.documentElement, true);
		    xml.appendChild(newElt);
		}
		return xml;
	
	
	
	
}
//获取节点列表
function XML_GetNodes(xmlContent,xPath){
	//return xmlContent.documentElement.selectNodes(xPath);
	var nodeValue="";
	//对不是ie的浏览器，进行扩展selectNodes方法
	if(!!window.ActiveXObject || "ActiveXObject" in window){
		nodeValue=xmlContent.documentElement.selectNodes(xPath);
	}else{
		nodeValue=xmlContent.selectNodes(xPath);
	}
	return nodeValue;
}

function XML_GetAttribute(xmlNode, attName, defaultValue){
//	var attNode = xmlNode.getAttributeNode(attName);
//	if(attNode==null)
//		return defaultValue;
//	else
//		return attNode.value;
	return xmlNode.getAttribute(attName);
}
//获取单个节点
function XML_GetSingleNode(xmlContent,xPath){
	//return xmlContent.documentElement.selectSingleNode(xPath);
	//debugger;
	if(!!window.ActiveXObject || "ActiveXObject" in window){
		return xmlContent.documentElement.selectSingleNode(xPath);
	}else{
		var xItems = XML_GetNodesByXpath(xmlContent,xPath);
		   if( xItems.length > 0 ) { 
			  return xItems[0]; 
		   } else { 
		    return null; 
		} 
	}
}
//获取某个节点的属性值
function XML_GetSingleNodeAttribute(xmlContent,xPath,attName){
	var att=XML_GetSingleNode(xmlContent,xPath).getAttributeNode(attName);
	//可能节点中还没有此属性则返回null
	if(att==null)
		return null;
		else
	return att.value;
}
//设置节点的属性值 传入一个json格式对象,没有属性会新建这个属性
function XML_SetNodeAttribute(xmlContent,xpath,attr_value){
	for(attr in attr_value){
	XML_GetSingleNode(xmlContent,xpath).setAttribute(attr,attr_value[attr]);}
}

