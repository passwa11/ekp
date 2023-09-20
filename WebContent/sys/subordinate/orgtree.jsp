<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.landray.kmss.sys.subordinate.plugin.PluginUtil"%>
<%@ page import="com.landray.kmss.sys.subordinate.plugin.PluginItem"%>

<%	
	String moduleMessageKey = request.getParameter("moduleMessageKey");
	List<PluginItem> items = PluginUtil.getModuleMap().get(moduleMessageKey);
	String type = ""; // dept: 按部门， person: 按人
	for(PluginItem item : items) {
		type = item.getType();
		break;
	}
	pageContext.setAttribute("type", type);
%>


<template:include ref="config.profile.tree">
	<template:replace name="head">
<!-- 快速定位搜索样式 -->
<style>
ul,li{list-style: none;}
ul{padding:0;margin:0;}
.lui-address-quciklocate-searchbar{
	width: 150px;
    border-bottom: 1px solid #b4b4b4;
    margin-right: 22px;
	position: relative;
	margin-bottom: 10px;
	text-align: right;
}
.lui-address-quciklocate-searchbar .lui-form-control {
    width: 90%;
    padding: 1px 4%;
    font-size: 12px;
    height: 22px;
    line-height: 20px;
    color: #666;
    border-radius: 5px;
    background-color: #fff;
    background-image: none;
    border: 1px solid #b4b4b4;
    border-right: 0;
    outline: none;
    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
    display: block;
    position: relative;
}
.lui-address-quciklocate-searchbar .lui-form-control{
    padding-right: 8%;
    padding-top: 3px;
    padding-bottom: 3px;
    /*width: 77%;*/
    position: absolute;
    background: transparent;
    bottom:0px;
    left:0px;
    border: none;
}
.lui-address-quciklocate-searchbar button{
    padding: 3px 0;
    width: 26px;
    height: 20px;
    line-height: 20px;
    text-align: left;
    border: 1px solid transparent;
    transition: all 0.3s;
    outline: none;
}
.lui-address-quciklocate-searchbar button{
	cursor: default;
	background:#fbfbfb url(./resource/image/address_icon_quicklocate.png) no-repeat 50%;
	position:relative;
}
.lui-address-quciklocate-dialog{
	width: 150px;
	height:200px;
    position: absolute;
    border: 1px solid #ECECEC;
    margin-right: 25px;
    box-shadow:1px 1px 1px #ECECEC,-1px -1px 1px #ECECEC;
    overflow-y :auto;
    display: none;
    background-color: #fff;
    z-index : 2;
}

.lui-address-quciklocate-li{
	padding: 5px 8px;
	cursor: pointer;
}
.lui-address-quciklocate-li.nodata{
	cursor: default;
    text-align: center;
    color: #9f9f9f;
    padding-left: 0;
    padding-right: 0;
    margin-top: 20px;
}
.lui-address-quciklocate-li .name{
    color: #333;
    font-size: 12px;
    min-width: 50px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    line-height :18px;
}
.lui-address-quciklocate-li .dept{
    line-height: 16px;
    color: #9f9f9f;
    display: block;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
}
.lui-address-quciklocate-li.selected {
	background-color: #f6efec;
}
</style>
</template:replace>
<template:replace name="content">
Com_IncludeFile("jquery.js");
function generateTree()
{
	LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-subordinate" key="table.sys.subordinate"/>", document.getElementById("treeDiv"));
	var n1 = LKSTree.treeRoot;
	
	n1.AppendBeanData("sysOrgElementTreeService&type=${type}&parentId=!{value}", "<c:url value="/sys/subordinate/list.jsp?moduleMessageKey=${JsParam.moduleMessageKey}&orgId=!{value}"/>");
	LKSTree.Show();
	generateQuickLocate();
	getFirst();
}

// 获取并定位到第一个可用节点
function getFirst() {
	var kmssData = new KMSSData();
	kmssData.AddHashMap({
		isFirst : 'true',
		orgType : 8,
		accurate : false,
		returnHierarchyId : true
	});
	kmssData.SendToBean('sysOrgElementTreeService&type=${type}', function(rtnData) {
			if(rtnData.data.length < 1) return;
			var hierarchyId = rtnData.data[0].hierarchyId, 
				hierarchyIdArray = hierarchyId.split('x'), 
				visibleParentNode = null;

			for ( var i = 0; i < hierarchyIdArray.length; i++) {
				var _node = Tree_GetNodeByValue(LKSTree.treeRoot, hierarchyIdArray[i]);
				if (_node) {
					visibleParentNode = _node;
					if (!_node.isExpanded)
						LKSTree.ExpandNode(_node);
				}
			}
			if (visibleParentNode)
				LKSTree.ClickNode(visibleParentNode);
		});
}

// 快速定位搜索
function generateQuickLocate() {
	var treeDiv = $("#treeDiv"), 
		quickLocate = $('<div class="lui-address-quciklocate-searchbar" />'), 
		input = $('<input class="lui-form-control" type="text" data-lui-mark="lui-quciklocate-search-input" />'), 
		searchbtn = $('<button type="button" data-lui-mark="lui-quciklocate-search-btn"></button>'), 
		searchDialog = null, 
		searchTimer = null;

	input.attr('placeholder', '<bean:message bundle="sys-subordinate" key="org.tree.search.info"/>');
	quickLocate.append(input).append(searchbtn);
	treeDiv.prepend(quickLocate);

	$(document.body).click( function(evt) {
		var target = $(evt.target);
		if (!target.parent().hasClass('lui-address-quciklocate-li') && searchDialog) {
			searchDialog.hide();
		}
	});
	input.click( function(evt) {
		if (!input.val())
			return;
		evt.stopPropagation();
		if (searchDialog && searchDialog.hasValue) {
			searchDialog.fadeIn('fast');
		}
	});

	searchbtn.click( function(evt) {
		if (!input.val())
			return;
		evt.stopPropagation();
		___search();
	});

	input.keyup( function(evt) {
		if (evt.keyCode == 13 || evt.keyCode == 38 || evt.keyCode == 40) {
			return;
		}
		if (!searchDialog) {
			searchDialog = $('<div class="lui-address-quciklocate-dialog" />');
			quickLocate.after(searchDialog);
		}
		if (searchTimer) {
			clearTimeout(searchTimer);
		}
		searchTimer = setTimeout( function() {
			___search();
			searchTimer = null;
		}, 500);
	});

	function ___search() {
		var keyword = input.val();
		if (keyword) {
			var kmssData = new KMSSData();
			kmssData.AddHashMap( {
				key : keyword,
				orgType : 8,
				accurate : false,
				returnHierarchyId : true
			});
			kmssData.SendToBean(
				'sysOrgElementTreeService&type=${type}',
				function(rtnData) {
					searchDialog.html('');
					var ul = $('<ul/>').appendTo(searchDialog);
					if (rtnData.data.length == 0) {
						var li = $('<li class="lui-address-quciklocate-li nodata"/>');
						li.text('<bean:message key="return.noRecord.reason2"/>');
						ul.append(li);
						searchDialog.hasValue = false;
					} else {
						for ( var i = 0; i < rtnData.data.length; i++) {
							var li = $('<li class="lui-address-quciklocate-li"/>'), 
								name = $('<div class="name"/>'), 
								dept = $('<div class="dept" />');
							li.append(name.text(rtnData.data[i].text).attr('title', rtnData.data[i].text));
							li.attr('data-hierarchyId', rtnData.data[i].hierarchyId);
							ul.append(li);
							li.mouseover(___select).click(___enter);
						}
						searchDialog.hasValue = true;
					}
					if (rtnData.data.length == 1) {
						li.trigger('mouseover').trigger('click');
					} else {
						searchDialog.fadeIn('fast');
					}
				});
		} else {
			searchDialog.hide();
		}
	}

	function ___select(evt) {
		var target = evt.currentTarget, 
			selectedTarget = searchDialog.find('.lui-address-quciklocate-li.selected');
		selectedTarget.removeClass('selected');
		$(target).addClass('selected');
		if (target.offsetTop + $(target).height() >= searchDialog.height() + searchDialog.scrollTop()) {
			searchDialog.scrollTop(target.offsetTop - searchDialog.height() + $(target).height() + 10);
		} else if (target.offsetTop <= searchDialog.scrollTop()) {
			searchDialog.scrollTop(target.offsetTop);
		}
	}

	function ___enter() {
		var selectedTarget = searchDialog.find('.lui-address-quciklocate-li.selected');
		if (selectedTarget.length > 0) {
			var hierarchyId = selectedTarget.attr('data-hierarchyId'), 
				hierarchyIdArray = hierarchyId.split('x'), 
				visibleParentNode = null;

			for ( var i = 0; i < hierarchyIdArray.length; i++) {
				var _node = Tree_GetNodeByValue(LKSTree.treeRoot, hierarchyIdArray[i]);
				if (_node) {
					visibleParentNode = _node;
					if (!_node.isExpanded)
						LKSTree.ExpandNode(_node);
				}
			}
			if (visibleParentNode) {
				LKSTree.ClickNode(visibleParentNode);
				scrollTo(visibleParentNode);
			} else {
				// 这里的逻辑会进入“（未指定机构/部门）”节点作处理
				LKSTree.ExpandNode(LKSTree.treeRoot.lastChild);
				if (LKSTree.treeRoot.lastChild) {
					var child = LKSTree.treeRoot.lastChild.firstChild;
					while(child) {
						if(child.value == hierarchyIdArray[1]) {
							LKSTree.ClickNode(child);
							scrollTo(child);
							break;
						}
						child = child.nextSibling;
						if(!child) break;
					}
				}
			}
		}
		if (searchDialog) {
			searchDialog.hide();
		}
	}
	
	function scrollTo(node) {
		var element = document.getElementById("TVN_"+node.id);
		var offsetTop = element.offsetTop - 35 > 0 ? element.offsetTop - 35 : 0;
		$("body").animate({scrollTop:offsetTop});
	}
}
	</template:replace>
</template:include>