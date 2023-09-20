var allNodes = [];
// 一键展开
function expand_all() {
	$("#expand_all").hide();
	$("#reduce_all").show();
	if (allNodes.length < 1) {
		var root = LKSTree.treeRoot;
		getAllNodes(root.firstChild);
	} else {
		for (var i = 0; i < allNodes.length; i++) {
			if(!allNodes[i].isExpanded)
				LKSTree.ExpandNode(allNodes[i]);
		}
	}
}

// 一键收缩
function reduce_all() {
	$("#expand_all").show();
	$("#reduce_all").hide();
	for (var i = 0; i < allNodes.length; i++) {
		if(allNodes[i].isExpanded)
			LKSTree.ExpandNode(allNodes[i]);
	}
}

// 获取所有树节点
function getAllNodes(node) {
	var tempNodes = [];
	while(node) {
		tempNodes.push(node);
		allNodes.push(node);
		node = node.nextSibling
	}
	for(var i=0; i<tempNodes.length; i++) {
		if(!tempNodes[i].isExpanded)
			LKSTree.ExpandNode(tempNodes[i]);
		node = tempNodes[i].firstChild;
		if(node) {
			getAllNodes(node);
		}
	}
}

//生成树
function generateTree()
{
	LKSTree = new TreeView("LKSTree", $data['fdConfName'], document.getElementById("treeDiv"));
	LKSTree.isShowCheckBox = true;
	LKSTree.isMultSel = false;
	var n1, n2, n3;
	n1 = LKSTree.treeRoot;
	n1.isExpanded = true;
	n1.value = " ";
	n1.AppendBeanData("sysOrgRoleLineTree&confId="+confId+"&lineId=!{value}");
	LKSTree.Show();
	
	var orgId = $data['orgId'];
	if(orgId !=null && orgId !=''){
		clickCurrentOrg(orgId);
	}
}

//定位角色线
function clickCurrentOrg(orgId)
{
	var tree = LKSTree,
	kmssData = new KMSSData();
	kmssData.AddBeanData("sysOrgRoleLineTree&confId="+confId+"&orgId="+orgId);
	var parentNodes = kmssData.GetHashMapArray(),
		visibleParentNode = null;
	for(var i = parentNodes.length - 1; i >= 0; i--){
		var _node = Tree_GetNodeByValue(tree.treeRoot,parentNodes[i].value);
		if(_node){
			visibleParentNode = _node;
			tree.ExpandNode(_node);
		}
	}
	if(parentNodes.length > 0 && visibleParentNode){
		var _node = Tree_GetNodeByValue(tree.treeRoot,visibleParentNode.value);
		tree.ClickNode(_node);
	}else{
		tree.ClickNode(tree.treeRoot);
	}
}

//快速添加下级
function opt_quickAdd(){
	var node = getSelectedNode(true);
	if(node==null)
		return;
	if(node.nodeType==ORG_TYPE_ORG || node.nodeType==ORG_TYPE_DEPT){
		alert($data['quickAdd.noexist']);
		return;
	}
	Dialog_Address(true, null, null, null, ORG_TYPE_ALLORG, function(rtnData){
		if(rtnData==null || rtnData.IsEmpty())
			return;
		node.CheckFetchChildrenNode();
		var values = rtnData.GetHashMapArray();
		var ids = values[0].id;
		for(var i=1; i<values.length; i++)
			ids += ";" + values[i].id;
		runAction("quickAdd", function(result){
			var newNode = undefined;
			for(var i=1; i<result.length; i++){
				var temp = node.AppendChild(result[i].text, null, null, result[i].value, null, result[i].nodeType,result[i].isExternal);
				if(!newNode) {
					newNode = temp;
				}
			}
			node.isExpanded = true;
			LKSTree.Show();
			if(newNode) {
				LKSTree.ClickNode(newNode);
				__scrollTo(newNode);
			}
		}, {parentId:node.value, orgIds:ids});
	}, null, null, null, null, null, null, null);
}

//添加下级
function opt_add(){
	var node = getSelectedNode(true);
	if(node==null)
		return;
	if(node.nodeType==ORG_TYPE_ORG || node.nodeType==ORG_TYPE_DEPT){
		alert($data['quickAdd.noexist']);
		return;
	}
	node.CheckFetchChildrenNode();
	var query = "method=add&fdConfId="+confId+"&fdParentId="+node.value+"&fdParentName="+encodeURIComponent(node.text);
	var url = "/sys/organization/sys_org_role_line/sysOrgRoleLine_dialog.jsp?query="+encodeURIComponent(query);
	openDialog(url,function(rtnVal){
		if(rtnVal==null)
			return;
		var newNode = node.AppendChild(rtnVal.text, null, null, rtnVal.value, null, rtnVal.nodeType, rtnVal.isExternal);
		node.isExpanded = true;
		LKSTree.Show();
		if(newNode) {
			LKSTree.ClickNode(newNode);
			__scrollTo(newNode);
		}
	});
}

//编辑
function opt_edit(){
	var node = getSelectedNode();
	if(node==null)
		return;
	var query = "method=edit&fdConfId="+confId+"&fdId="+node.value;
	var url = "/sys/organization/sys_org_role_line/sysOrgRoleLine_dialog.jsp?query="+encodeURIComponent(query);
	openDialog(url,function(rtnVal){
		if(rtnVal==null)
			return;
		var parent = node.parent;
		node.text = rtnVal.text;
		node.value = rtnVal.value;
		node.nodeType = rtnVal.nodeType;
		node.isExternal = rtnVal.isExternal;
		Tree_SetNodeHTMLDirty(node);
		LKSTree.Show();
	});
	
}

//删除节点
function opt_delete(){
	var node = getSelectedNode();
	if(node==null)
		return;
	node.CheckFetchChildrenNode();
	if(node.firstChild!=null){
		if(!confirm($data['delete.child.confirm'])){
			return;
		}else{
			runAction("deleteAll", function(result){
				node.Remove();
				LKSTree.Show();
			}, {id:node.value});
		}
	}else{
		if(!confirm($data['delete.confirm'])){
			return;
		}
		runAction("delete", function(result){
			node.Remove();
			LKSTree.Show();
		}, {id:node.value});
	}
}

//移动节点
function opt_move(){
	var node = getSelectedNode();
	if(node==null)
		return;
	var dialog = new KMSSDialog(false, false);
	var root = dialog.CreateTree($data['fdConfName']);
	root.value = " ";
	Data_XMLCatche = new Array();
	root.AppendBeanData("sysOrgRoleLineTree&nodept=true&confId="+confId+"&lineId=!{value}", null, null, null, node.value);
	dialog.SetAfterShow(function(rtnData){
		if(rtnData==null)
			return;
		var rtnVal = rtnData.GetHashMapArray()[0];
		var newParent = Tree_GetNodeByValue(LKSTree.treeRoot, rtnVal.id);
		if(newParent!=null){
			newParent.CheckFetchChildrenNode();
		}
		runAction("move", function(result){
			if(newParent!=null){
				newParent.AddChild(node);
			}else{
				node.Remove();
			}
			LKSTree.Show();
		}, {id:node.value, parentId:rtnVal.id});
	});
	dialog.notNull = true;
	dialog.Show();
}

//重复检查
function opt_check(){
	Com_OpenWindow($data['checkRepeatRoleUrl'],"_blank");
}

//获取当前选定的节点
function getSelectedNode(includeRoot){
	var node = LKSTree.GetCheckedNode();
	if(node==null){
		alert($data['pleaseSelect']);
		return null;
	}
	if(!includeRoot && node==LKSTree.treeRoot){
		alert($data['rootForbit']);
		return null;
	}
	return node;
}

function opt_simulator(){
	Com_OpenWindow($data['sysOrgRole_simulator_url'],'_blank');
}

//打开对话框窗口
function openDialog(url,callback){
	seajs.use(['lui/dialog'],function(dialog){
		dialog.iframe(url,null,function(rtn){
			if(rtn!= null){
				callback(rtn.rtnVal);
			}
		},{width:640,height:480})
	});
}

//执行一个ajax请求
function runAction(method, action, parameter){
	var kmssdata = new KMSSData();
	kmssdata.AddHashMap(parameter);
	kmssdata.SendToBean("sysOrgRoleLineOption&method="+method+"&confId="+confId, function(rtnData){
		var rtnVal = rtnData.GetHashMapArray();
		if(rtnVal[0].success=="true"){
			action(rtnVal);
		}
		if(rtnVal[0].message!=null)
			alert(rtnVal[0].message);
	});
}

// 节点定位
function __scrollTo(node){
	var element = document.getElementById("TVN_"+node.id);
	var offsetTop = element.offsetTop - 35 > 0 ? element.offsetTop - 35 : 0;
	$("html, body").animate({scrollTop:offsetTop}, 500);
}

$(function(){
	var quickLocate = $('.quciklocateDiv'),
		input = $('.quciklocateInput',quickLocate),
		dialog = $('.quciklocateDialog'),
		searchTimer;
	
	$(document.body).click(function(evt){
		var target  = $(evt.target);
		if(!target.parent().hasClass('quciklocateLi') && dialog){
			dialog.hide();
		}
	});

	input.keyup(function(evt){
		if(evt.keyCode==13 || evt.keyCode == 38 || evt.keyCode == 40){
			return;
		}
		if(searchTimer){
			clearTimeout(searchTimer);
		}
		searchTimer = setTimeout(function(){
			___search();
			searchTimer = null;
		},500);
	});
	
	$(quickLocate).keydown(function(e){
		e.stopPropagation();
		var selectedTarget = dialog.find('.quciklocateLi.selected');
		//处理Enter事件
		if(e.keyCode==13){
			___enter();
		}
		//处理上移事件
		if(e && e.keyCode == 38){
			var prevTarget = null;
			if(selectedTarget.length > 0){
				prevTarget = selectedTarget.prev();
			}
			if(prevTarget.length > 0 ){
				___select({ currentTarget : prevTarget[0] });
			}
		}
		//处理下移事件
		if(e && e.keyCode == 40){
			var nextTarget = null;
			if(selectedTarget.length == 0){
				nextTarget = dialog.find('.quciklocateLi:first');
			}else{
				nextTarget = selectedTarget.next();
			}
			if(nextTarget.length > 0 ){
				___select({ currentTarget : nextTarget[0] });
			}
		}
	});
	
	function ___search(){
		var value = input.val(),
			kmssData = new KMSSData();
		kmssData.AddHashMap({
			key : value,
			confId : confId
		});
		if(value){
			kmssData.SendToBean('sysOrgRoleLineTree',function(rtnData){
				dialog.html('');
				var ul = $('<ul/>').appendTo(dialog);
				if(rtnData.data.length == 0){
					var li = $('<li class="quciklocateLi nodata"/>');
					li.text($data['noData']);
					ul.append(li);
					dialog.hasValue = false;
				}else{
					for(var i = 0;i < rtnData.data.length;i++){
						var li = $('<li class="quciklocateLi"/>'),
							name = $('<div class="name"/>');
						li.append(name.text(rtnData.data[i].text).attr('title',rtnData.data[i].text));
						li.attr('data-hierarchyId',rtnData.data[i].hierarchyId);
						ul.append(li);
						li.mouseover(___select).click(___enter);
					}
					dialog.hasValue = true;
				}
				if( rtnData.data.length == 1){
					li.trigger('mouseover').trigger('click');
				}else{
					dialog.fadeIn('fast');
				}
			});
		}
	}
	
	function ___select(evt){
		var target = evt.currentTarget,
			selectedTarget = dialog.find('.quciklocateLi.selected');
		selectedTarget.removeClass('selected');
		$(target).addClass('selected');
		if(target.offsetTop + $(target).height() >= dialog.height() + dialog.scrollTop() ){
			dialog.scrollTop(target.offsetTop - dialog.height() + $(target).height() + 10 );
		}else if(target.offsetTop <=  dialog.scrollTop()){
			dialog.scrollTop(target.offsetTop );
		}
	}
	
	function ___enter (){
		var tree = window['LKSTree'],
			selectedTarget = dialog.find('.quciklocateLi.selected');
		if(selectedTarget.length > 0){
			var hierarchyId = selectedTarget.attr('data-hierarchyId'),
				hierarchyIdArray = hierarchyId.split('x'),
				visibleParentNode = null;
			for(var i = 0 ;i < hierarchyIdArray.length;i++){
				var _node = Tree_GetNodeByValue(tree.treeRoot,hierarchyIdArray[i]);
				if(_node){
					visibleParentNode = _node;
					if(!_node.isExpanded)
						tree.ExpandNode(_node);
				}
			}
			if(visibleParentNode){
				var _node = Tree_GetNodeByValue(tree.treeRoot,visibleParentNode.value);
				tree.ClickNode(_node);
				__scrollTo(_node);
			}
		}
		if(dialog){
			dialog.hide();
		}
	}
});