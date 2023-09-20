<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>

var LKSTree;		//树对象
var currentOpt;		//当前的操作
var currentNode;	//当前操作的节点
var displayType;
Tree_IncludeCSSFile();
//更新导航类型的显示
function refreshType(){
	refreshDisplay();
}
//更新数据展现方式的显示
function refreshDisplay(){
	var fields = document.getElementsByName("fdDisplay");
	if(fields[0].checked){
		document.getElementById("designMode").style.display = "";
		document.getElementById("sourceMode").style.display = "none";
		//trArr[5].style.display = "";
		//trArr[6].style.display = "none";
		if(displayType!="Tree")
			generateTree();
		displayType = "Tree";
	}else{
		if(!checkChange()){
			fields[0].checked = true;
			return;
		}
		//trArr[5].style.display = "none";
		//trArr[6].style.display = "";
		currentNode = null;
		currentOpt = null;
		if(displayType!="Data")
			generateData();
		document.getElementById("designMode").style.display = "none";
		document.getElementById("sourceMode").style.display = "";
		displayType = "Data"
	}
}
//刷新操作栏的视图
function refreshOptView(){
	var divObj = document.getElementById("DIV_AllControl");
	if(currentNode==null){
		divObj.style.display = "none";
		return;
	}
	divObj.style.display = "";
	var elements = divObj.getElementsByTagName("span");
	var nodeType = currentNode.XMLDataInfo==null?"Node":"Data";
	for(var i=0; i<elements.length; i++){
		var lks_info = elements[i].getAttribute("lks_info");
		if(lks_info==null)
			continue;
		elements[i].style.display = (lks_info.indexOf(currentOpt)>-1 && lks_info.indexOf(nodeType)>-1)?"":"none";
	} 

	document.getElementsByName("tmpNodeName")[0].value = "";
	
	var offLang = langJson["official"]["value"];
	
	for(var j=0;j < langJson["support"].length;j++){
		var lang = langJson["support"][j]["value"];
		if(offLang==lang)
			continue;
		
		document.getElementById("tmpNodeName_"+lang).value = "";
	}
	
	if(currentOpt=="Edit"){
	    if(!currentNode.parent){
	    	$('span[lks_info="NewEditNode"]').css('display','none');
	    	$('span[lks_info_btn="EditNode"]').css('display','none');
	    	$('span[lks_info_btn="EditNodeData"]').css('display','none');
	    }else{
	    	$('span[lks_info="NewEditNode"]').css('display','');
	    	$('span[lks_info_btn="EditNode"]').css('display','');
	    	$('span[lks_info_btn="EditNodeData"]').css('display','');
	    	
	    }
	    //限制三级节点
	    if(currentNode.parent && currentNode.parent.parent){
	    	$('a[lks_info_btn="addNode"]').css('display','none');
	    	$('div[lks_info_item="tmpexpand"]').css('display','none');
	    	
	    }else{
	    	$('a[lks_info_btn="addNode"]').css('display','');
	    	$('div[lks_info_item="tmpexpand"]').css('display','');
	    }

		document.getElementsByName("tmpNodeName")[0].value = currentNode.text;
		
		var offLang = langJson["official"]["value"];
			
		var offValue = document.getElementById("tmpNodeName_"+offLang).value;

		currentNode.parameter = currentNode.parameter || {};
		
		for(var j=0;j < langJson["support"].length;j++){
			var lang = langJson["support"][j]["value"];
			if(offLang==lang)
				continue;
			
			if (typeof currentNode.parameter[lang] != "undefined")
				document.getElementById("tmpNodeName_"+lang).value = currentNode.parameter[lang] ||'';
		}

		document.getElementsByName("tmpURL")[0].value = currentNode.parameter.tmpUrl || '';
		initLinkTargetShow(currentNode.parameter.tmpTarget || '');
		initLinkExpandShow(currentNode.parameter.tmpexpand || 'true');
		initTmpIconShow(currentNode.parameter.tmpIcon || '');
		initTmpImgShow(currentNode.parameter.tmpImg || '');

	}
	if(currentOpt=="New"){
		initLinkTargetShow('_top');
		initLinkExpandShow('true');
		$('#tmpIconPreview').removeClass().addClass("lui_iconfont_selectIcon iconfont_nav");
		setTmpIconValue("iconfont_nav");
	}
}
//修改树显示的大小
function resizeTreeDiv(x){
	x = x*100;
	var h = parseInt(document.getElementById("DIV_Tree").style.height);
	h += x;
	if(h<100)
		return;
	document.getElementById("DIV_Tree").style.height = h+"px";
}
//新建节点
function optAddNode(){
	if(checkChange()){
		currentOpt = "New";
		refreshOptView();
	}
}
//检查数据是否有修改
function checkChange(){
	if(currentNode==null)
		return true;
	var nodeName = document.getElementsByName("tmpNodeName")[0].value;
	var url = document.getElementsByName("tmpURL")[0].value || '';
	var target = getLinkTargetValue() || '';
	var expand = getLinkExpandValue() || '';
	
	var icon = getTmpIconValue()|| '';
	var img = getTmpImgValue()|| '';
	
	if(currentOpt=="New"){
		if(nodeName!="" || url!=""){
			if(confirm("<bean:message  bundle="sys-portal" key="sysHomeNav.msg.confirm.create"/>"))
				return confirmNewNode();
		}
	}else{
		var oldUrl = currentNode.parameter ? currentNode.parameter.tmpUrl || '':'';
		var oldTarget = currentNode.parameter ? currentNode.parameter.tmpTarget || '': '';
		var oldExpand = currentNode.parameter ? currentNode.parameter.tmpexpand || 'true': '';
		var oldIcon = currentNode.parameter ? currentNode.parameter.tmpIcon || '': '';
		var oldImg = currentNode.parameter ? currentNode.parameter.tmpImg || '': '';
		if(nodeName!=currentNode.text  || (!oldUrl && oldUrl !=url) || (oldUrl && oldUrl!=url)
			|| (!oldUrl && oldUrl !=url) || (oldTarget && oldTarget!=target) || (oldExpand && oldExpand!=expand)
			|| (!oldIcon && oldIcon !=icon) || (oldImg && oldImg!=img)){
			if(confirm("<bean:message  bundle="sys-portal" key="sysHomeNav.msg.confirm.update"/>"))
				return confirmEditNode();
		}
	}
	return true;
}
//新建节点的确定操作
function confirmNewNode(){
	var field = document.getElementsByName("tmpNodeName")[0];
	if(field.value==""){
		alert("<bean:message  bundle="sys-portal" key="sysHomeNav.msg.error.nameNotNull"/>");
		return false;
	}
	var text = field.value;

	var parameter = {};
	
	for(var j=0;j < langJson["support"].length;j++){
		var lang = langJson["support"][j]["value"];
		var value = document.getElementById("tmpNodeName_"+lang).value;
		parameter[lang] = value;
	}
	
	field = document.getElementsByName("tmpURL")[0];
	parameter.tmpUrl = field.value;
	parameter.tmpTarget = getLinkTargetValue();
	parameter.tmpexpand = getLinkExpandValue();
	parameter.tmpIcon = getTmpIconValue();
	parameter.tmpImg = getTmpImgValue();

	var node = new TreeNode(text, parameter)
	currentNode.isExpanded = true;
	currentNode.AddChild(node);
	LKSTree.Show();
	currentOpt = "Edit";
	refreshOptView();
	return true;
}
//取消
function optCancel(){
	currentOpt = "Edit";
	refreshOptView();
}
//编辑节点的确定操作
function confirmEditNode(){
	var field = document.getElementsByName("tmpNodeName")[0];
	if(field.value==""){
		alert("<bean:message  bundle="sys-portal" key="sysHomeNav.msg.error.nameNotNull"/>");
		return false;
	}
	currentNode.text = field.value;
	field = document.getElementsByName("tmpURL")[0];

	currentNode.parameter = {};

	
	for(var j=0;j < langJson["support"].length;j++){
		var lang = langJson["support"][j]["value"];
		var value = document.getElementById("tmpNodeName_"+lang).value;
		currentNode.parameter[lang] = value;
	}
	
	currentNode.parameter.tmpUrl = field.value;
	currentNode.parameter.tmpTarget = getLinkTargetValue();
	currentNode.parameter.tmpexpand = getLinkExpandValue();
	currentNode.parameter.tmpIcon = getTmpIconValue();
	currentNode.parameter.tmpImg = getTmpImgValue();
	LKSTree.Show();
	return true;
}
//上移节点
function optMoveUp(node){
	if(node==null)
		return;
	var pNode = node.parent;
	if(pNode==null)
		return;
	var preNode = node.prevSibling;
	if(preNode==null)
		return;
	pNode.AddChild(node, preNode);
	LKSTree.Show();
}
//左移节点
function optMoveLeft(){
	var pNode = currentNode.parent;
	if(pNode==null)
		return;
	var fNode = pNode.parent;
	if(fNode==null)
		return;
	fNode.AddChild(currentNode, pNode.nextSibling);
	LKSTree.Show();
}
//右移节点
function optMoveRight(){
	var nNode = currentNode.prevSibling;
	if(nNode==null)
		return;
	if(nNode.firstChild!=null && nNode.firstChild.XMLDataInfo!=null)
		return;
	nNode.AddChild(currentNode);
	nNode.isExpanded = true;
	LKSTree.Show();
}
//删除节点
function optDeleteNode(){
	if(currentNode.parent==null){
		alert("<bean:message  bundle="sys-portal" key="sysHomeNav.msg.error.rootCanNotDel"/>");
		return;
	}
	if(!confirm("<bean:message  bundle="sys-portal" key="sysHomeNav.msg.confirm.delete"/>"))
		return;
	currentNode.Remove();
	currentNode = null;
	currentOpt = null;
	refreshOptView();
	LKSTree.Show();
}
//生成树
function generateTree(){
	var xmlTxt, treeData, nodeArr, i, lv;
	xmlTxt = document.getElementsByName("fdContent")[0].value;
	if(xmlTxt=="")
		treeData = [];
	else
		treeData = domain.toJSON(xmlTxt);
	if(LKSTree){
		document.getElementById("DIV_Tree").innerHTML = "";
		delete LKSTree;
	}
	LKSTree = new TreeView("LKSTree", '<bean:message  bundle="sys-portal" key="sysHomeNav.msg.node.root"/>', document.getElementById("DIV_Tree"));
	generateChildrenTree(LKSTree.treeRoot,treeData);
	LKSTree.ClickNode = onTreeNodeClick;
	LKSTree.Show();
	currentNode = null;
	currentOpt = null;
	refreshOptView();
}
function generateChildrenTree(root,data){
	for(var i =0;i<data.length;i++){
		var node = new TreeNode();
		node.text = data[i].text;
		node.isExpanded = true;
		node.nodeType = "node"; 
		node.parameter = {};
		if(data[i].href!=null){
			node.parameter.tmpUrl = data[i].href;
			node.parameter.tmpTarget = data[i].target;
			node.parameter.tmpexpand = data[i].expand;
			node.parameter.tmpIcon = data[i].icon;
			node.parameter.tmpImg = data[i].img;
		}
		
		for(var j = 0;j < langJson["support"].length ; j++){
			var lang = langJson["support"][j]["value"];
			node.parameter[lang] = data[i][lang];
			if(curLang==lang){
				var tmp = data[i][lang];
				if(typeof(tmp) != 'undefined'){
					tmp = tmp.replace(/^\s+|\s+$/gm,'');
					if(tmp!='')
						node.text = data[i][lang];
				}
			}
		}
		
		root.AddChild(node);
		if(data[i].children!=null&&data[i].children.length >0){
			generateChildrenTree(node,data[i].children);
		}
	} 
} 
//从树生成XML
function generateData(){ 
	if($("#designMode").is(":visible")){
		document.getElementsByName("fdContent")[0].value = domain.stringify(getChildrenXML(LKSTree.treeRoot));
	}
	//document.getElementsByName("fdContent")[0].value = getChildrenXML(LKSTree.treeRoot);
}
function getChildrenXML(node){
	var children = [];
	for(var child = node.firstChild; child!=null; child = child.nextSibling){
		var no = {};
		no.text = child.text;
		if(child.parameter!=null){
			no.href = child.parameter.tmpUrl;
			no.target = child.parameter.tmpTarget;
			no.expand = child.parameter.tmpexpand;
			no.icon = child.parameter.tmpIcon;
			no.img = child.parameter.tmpImg;

			for(var j=0;j < langJson["support"].length;j++){
				var lang = langJson["support"][j]["value"];

				no[lang] = child.parameter[lang];
			}
		}
		if(child.firstChild != null){
			no.children = getChildrenXML(child);
		}
		children.push(no);
	}
	return children;
} 
//树的节点点击事件
function onTreeNodeClick(node){
	if(typeof(node)=="number")
		node = Tree_GetNodeByID(this.treeRoot, node);
	if(!checkChange())
		return;
	this.SetCurrentNode(node);
	currentNode = node;
	currentOpt = "Edit";
	refreshOptView();
}
//选择模块
function selectAppModule(){
	//Dialog_List(false, "tmpURL", "tmpNodeName", null, "sysHomeModuleDialog",null,null,null,null,"<bean:message  bundle="sys-portal" key="sysHomeNav.button.selectNode"/>");
	SysLinksDialog("tmpURL", "tmpNodeName");
}


//重载window的onload事件
Com_AddEventListener(window,'load', function (){
	refreshType();
});
function submitForm(method,isValidate){
	if(isValidate){
		var result = checkChange();
		if(result != true){
			retrun;
		}
	}
	generateData();
	Com_Submit(document.sysPortalNavForm, method);
}
function getLinkTargetValue(){
	return $("input[name='tmpTarget']:checked").val();
}
function initLinkTargetShow(value){
	$("input[name='tmpTarget']").each(function(index,item){
		if(value==item.value){
			item.checked=true;
		}else{
			item.checked=false;
		}
	});
}
//展开节点方式
function getLinkExpandValue(){
	return $("input[name='tmpexpand']:checked").val();
}
function initLinkExpandShow(value){
	$("input[name='tmpexpand']").each(function(index,item){
		if(value==item.value){
			item.checked=true;
		}else{
			item.checked=false;
		}
	});
}
function getTmpIconValue(){
	return $("input[name='tmpIcon']").val();
}

function getTmpImgValue(){
	var value;
	value=$("input[name='tmpImg']").val();
	return value;
}


function setTmpIconValue(value){
	$("input[name='tmpIcon']").val(value);
}
function initTmpIconShow(value){
	$("input[name='tmpIcon']").val(value);
	$('#tmpIconPreview').empty().removeClass().addClass("lui_iconfont_selectIcon " + value);
}

function initTmpImgShow(value){
		if(value){
			var imgUrl = value;
			if(imgUrl.indexOf("/") == 0){
			imgUrl = imgUrl.substring(1);
			}
			$('#tmpIconPreview').empty().removeClass().addClass("lui_iconfont_selectIcon ");
			$('#tmpIconPreview').html('<img class="lui_img_l" src="" width="100%"/>');
			$(".lui_img_l").attr('src',Com_Parameter.ContextPath+imgUrl);
			$(".lui_img_l").css("display","block");

			$("input[name='tmpImg']").val(value);
	}
}


// 生成URL
function generateUrl() {
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		var tmpURL = $("input[name='tmpURL']").val();
		if($.trim(tmpURL).length < 1) {
			dialog.alert("<bean:message bundle="sys-portal" key="portlet.generateUrl.null"/>");
			return false;
		}
		dialog.iframe('/sys/ui/commontools/urltools.jsp?_dialog=true',
			"<bean:message bundle="sys-admin" key="sys.admin.commontools.generateUrl"/>",
			function (value){
            	if($.trim(value).length > 0)
            		$("input[name='tmpURL']").val(value);
			},
			// 注意：url需要传入用于解析的地址
			{width:800,height:350,params:{url:tmpURL}}
		);
	});
}