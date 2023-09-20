<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
	Com_IncludeFile("dialog.js|doclist.js|data.js");
</script>
<script>
function initArray(){
    if (!Array.prototype.indexOf)
    {
      Array.prototype.indexOf = function(elt /*, from*/)
      {
        var len = this.length >>> 0;
        var from = Number(arguments[1]) || 0;
        from = (from < 0)
             ? Math.ceil(from)
             : Math.floor(from);
        if (from < 0)
          from += len;
        for (; from < len; from++)
        {
          if (from in this &&
              this[from] === elt)
            return from;
        }
        return -1;
      };
    }
}
</script>
<script type="text/javascript">
initArray();
seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
	var this$ = $;
	var _dialog = dialog;
	var catelogJson = top.catelogJson ? top.catelogJson : [];
	$(function() {
		setTimeout(init, 500);// 延迟初始化阶段工作(必须延迟不然添加行报错)
	})

	//初始化目录
	function init() {
		for ( var i = 0; i < catelogJson.length; i++) {
			var catelog = catelogJson[i];
			if (catelog != null) {
				DocList_AddRow('TABLE_DocList');//增加一行
				if(catelog.fdParentDirName == null){
					catelog.fdParentDirName = "";
				}
				if(catelog.fdParentDir == null){
					catelog.fdParentDir = "";
				}
				GetEl("sysHelpCatelogList[" + i + "].fdId").value = catelog.fdId;
				GetEl("sysHelpCatelogList[" + i + "].docSubject").value = catelog.docSubject;
				GetEl("sysHelpCatelogList[" + i + "].fdParentDirName").value = catelog.fdParentDirName;
				GetEl("sysHelpCatelogList[" + i + "].fdOrder").value = catelog.fdOrder;
				GetEl("sysHelpCatelogList[" + i + "].fdParentId").value = catelog.fdParentId;
				GetEl("sysHelpCatelogList[" + i + "].fdParentDir").value = catelog.fdParentDir;
				GetEl("sysHelpCatelogList[" + i + "].fdLevel").value = catelog.fdLevel;
				GetEl("sysHelpCatelogList[" + i + "].docContent").value = catelog.docContent;
			}
		}
	}

	/**
	 * 获取所有目录，要记得删除隐藏域中的一个
	 */
	function GetElsFdCatelogList_seclect(){
		var selectObj = GetEls("fdCatelogList_seclect");
		var selCount = selectObj.length;
		var cateCount = GetID('TABLE_DocList').rows.length - 1;
		if (selCount > cateCount){
			var selectObjNew = [];
			for(var i = 0; i < cateCount; i++){
				selectObjNew.push(selectObj[i]);
			}
			return selectObjNew;
		}
		return selectObj;
		
	}
	//全选
	window.changeSelectAll= function(thisObj) {
		var thisObjId = thisObj.id;
		var othisObjId = "fdCatelogList_seclectAll2";
		if (thisObjId == othisObjId){
			othisObjId = "fdCatelogList_seclectAll";
		}
		var selectObj = GetElsFdCatelogList_seclect(); //GetEls("fdCatelogList_seclect");
		if (thisObj.checked == true) {
			document.getElementById(othisObjId).checked = true;
			//全选
			if (selectObj != null) {
				for ( var i = 0; i < selectObj.length; i++) {
					selectObj[i].checked = true;
				}
			}
		} else {
			//取消全选
			document.getElementById(othisObjId).checked = false;
			for ( var i = 0; i < selectObj.length; i++) {
				selectObj[i].checked = false;
			}
		}
	}

	//选择行
	window.changeSelect = function(thisObj) {
		var selectAllObj = GetEls("fdCatelogList_seclectAll");
		if (thisObj.checked == true) {
			var isAll = true;
			var selectObj = GetElsFdCatelogList_seclect(); //GetEl("fdCatelogList_seclect");
			for ( var i = 0; i < selectObj.length; i++) {
				if (selectObj[i].checked == false) {
					isAll = false;
					break;
				}
			}
			selectAllObj.checked = isAll;
		} else {
			selectAllObj.checked = false;
		}
	}

	var addRowFlag = 1;
	// 添加主目录
	window.add_Row = function() {
		
        var selectObj = GetElsFdCatelogList_seclect();
        var n = 0;
        for (var i = 0; i < selectObj.length; i++) {
            if (selectObj[i].checked == true) {
                n++;//计算选中的个数。
                if (n > 1) {
                    _dialog.alert("${lfn:message('sys-help:sysHelpCatelog.onlyOne')}");
                    return false;
                }
            }
        }
        if (n == 0) {
        	_catelogAddRow(null, null, 1, null);
        }else{
        	add_sub_Row(selectObj);
        }
		
	}
	//添加子目录
	function add_sub_Row(selectObj) {
        var pLevel;
        var pFdId
        var pFdName;
        var checkedCateIndex = 0;
        for ( var i = 0; i < selectObj.length; i++) {
            if (selectObj[i].checked == true) {
            	pLevel = GetEl("sysHelpCatelogList[" + i + "].fdLevel").value * 1;
            	pFdId = GetEl("sysHelpCatelogList[" + i + "].fdId").value;
            	pFdName = GetEl("sysHelpCatelogList[" + i + "].docSubject").value;
            	checkedCateIndex = i;
                break;
            }
        }
        if (pLevel > 8){
            // 目前最高设定9层
            _dialog.alert("${lfn:message('sys-help:sysHelpCatelog.over.fdLevel')}");
            return false;
        }
        if (pLevel){
        	pLevel++;
        	_catelogAddRow(pFdId, pFdName, pLevel, checkedCateIndex);
        }
	}
	
	function _catelogAddRow(parentDir, parentDirName, curFdLevel, checkedCateIndex){
		if (addRowFlag == 0){
			return;
		}
		document.getElementById("add_Row1").style.cursor="not-allowed";
		document.getElementById("add_Row2").style.cursor="not-allowed";
		addRowFlag = 0;
		// id 层级 上级id 上级名称
	    DocList_AddRow('TABLE_DocList'); //添加行
	    var url = "sysHelpGenerateId";//获取ID
        var data = new KMSSData();
        data.SendToBean(url, getGenerateID);
        setTimeout(function (){
        	addRowFlag = 1;
    		document.getElementById("add_Row1").style.cursor="pointer";
    		document.getElementById("add_Row2").style.cursor="pointer";
       	}, 800);
        // 层级
        var index = GetID('TABLE_DocList').rows.length - 1;
        var newCateIndex = index - 1;
        GetEl("sysHelpCatelogList[" + newCateIndex + "].fdLevel").value = curFdLevel;
        if (parentDir){
            // 查看是否移动排序
            GetEl("sysHelpCatelogList[" + newCateIndex + "].fdParentDir").value = parentDir;
            GetEl("sysHelpCatelogList[" + newCateIndex + "].fdParentDirName").value = parentDirName;
	        moveSubCateToParent(checkedCateIndex, newCateIndex);
        }
	}
	
	/**
	 * 增加子目录之后，移动子目录
	 */
	function moveSubCateToParent(checkedCateIndex, newCateIndex){
		var newCateFdId = GetEl("sysHelpCatelogList[" + newCateIndex + "].fdId").value;
		if (!newCateFdId || newCateFdId.length == 0){
			setTimeout(function (){moveSubCateToParent(checkedCateIndex, newCateIndex)}, 300);
			return;
		}
		
	    if ((newCateIndex - checkedCateIndex) > 1){
	        var allCheckbox = GetElsFdCatelogList_seclect(); //GetEls("fdCatelogList_seclect");
	        var allMaxIndex = allCheckbox.length - 1;
	        var maxIndex = newCateIndex > allMaxIndex ? allMaxIndex : newCateIndex;
	        var parentMap = [];
	        var targetIndex = newCateIndex;
	        parentMap.push(GetEl("sysHelpCatelogList[" + checkedCateIndex + "].fdId").value);
	        for ( var i = checkedCateIndex + 1; i < maxIndex + 1; i++) {
	            var thisFdId = GetEl("sysHelpCatelogList[" + i + "].fdId").value;
	            var thisPId = GetEl("sysHelpCatelogList[" + i + "].fdParentDir").value;
	            if (parentMap.indexOf(thisPId) == -1){
	                targetIndex = i;
	                break;
	            } else if (thisFdId && thisFdId.length > 0){
	                parentMap.push(thisFdId);
	            }
	            
	        }
	        
	        if (targetIndex != newCateIndex){
	        	var res = targetIndex - newCateIndex;
	        	var moveStep = res < 0 ? -1 : 1;
	        	var moveNum = res < 0 ? (0-res) : res;
	        	var moveObj = GetEl("sysHelpCatelogList[" + newCateIndex + "].docSubject").parentNode.parentNode;
	        	for(var j = 0; j < moveNum; j++){
	            	DocList_MoveRow(moveStep, moveObj);
	        	}
	        }
	    }
	}
	
	
	//增行生成id
	function getGenerateID(rtnData) {
		if (rtnData.GetHashMapArray().length >= 1) {
			var obj = rtnData.GetHashMapArray()[0];
			var fdId = obj['fdId'];
			var index = GetID('TABLE_DocList').rows.length - 1;
			GetEl("sysHelpCatelogList[" + (index - 1) + "].fdId").value = fdId;
		}
	}

	//上移
	window.move_Row = function(val) {
		var selectObj = GetElsFdCatelogList_seclect(); //GetEls("fdCatelogList_seclect");
		var n = 0;
		for ( var i = 0; i < selectObj.length; i++) {
			if (selectObj[i].checked == true) {
				n++;//计算选中的个数。
				if (n > 1) {
					_dialog.alert("${lfn:message('sys-help:sysHelpCatelog.onlyOne')}");
					return false;
				}
			}
		}
		if (n == 0) {
			_dialog.alert("${lfn:message('sys-help:sysHelpCatelog.noOne.noOne')}");
			return false;
		}
		for ( var i = 0; i < selectObj.length; i++) {
			if (selectObj[i].checked == true) {
				DocList_MoveRow(val, selectObj[i].parentNode.parentNode);
				break;
			}
		}
	}

	//删除
	window.delete_Row = function() {
		
        // 只要有一个不能删除就不删除，上下级全部选中才能一起删除
        var allCheckedFdId = [];
        var allCheckedFdOrder = [];
        // 统计一下上下级关系
        var parentMap = {};
        var allCateOrder = {};
        
		var delArr = [], m = 0, selectObj = GetElsFdCatelogList_seclect(); //GetEls("fdCatelogList_seclect");
		// 遍历所有需要删除行
		for ( var i = 0; i < selectObj.length; i++) {
			var curObj = GetEl("sysHelpCatelogList[" + i + "].fdId");
			if (!curObj){
				continue;
			}
			var curFdId = curObj.value;
			allCateOrder[curFdId] = i + 1;
			if (selectObj[i].checked == true) {
				delArr[m] = selectObj[i].parentNode.parentNode;
				m++;
                // 统计一下被选中的信息
				allCheckedFdId.push(curFdId);
                allCheckedFdOrder.push(selectObj[i].parentNode.parentNode.childNodes[1].innerHTML);
			}
	        // 统计一下上下级关系
            var curFdParentDir = GetEl("sysHelpCatelogList[" + i + "].fdParentDir").value;
            if (curFdParentDir){
                var subFdIds = parentMap[curFdParentDir];
                subFdIds = subFdIds ? subFdIds : [];
                subFdIds.push(curFdId);
                parentMap[curFdParentDir] = subFdIds;
            }
		}
		if (delArr == 0) {
			_dialog.alert("${lfn:message('sys-help:sysHelpCatelog.noOne')}");
			return false;
		}
	    
	    var notAllSubCheckOrder = [];
	    var notCheckOrderShow = [];
	    var alreadyCheckedFdIds = [];
	    // 查看子节点是否都被选中 indexOf == -1  false
	    for (var i = 0; i < allCheckedFdId.length; i++){
	        // 遍历被选中的节点
	        var curFdId = allCheckedFdId[i];
	        var curSubFdIds = parentMap[curFdId];
	        
	        if (alreadyCheckedFdIds.indexOf(curFdId) != -1){
	        	continue;
	        }
	        // 查看是否有子节点
	        if (curSubFdIds){
	            // 子目录ID集合，被选中的ID集合，上下级关系，父目录序号，所有序号，需要显示的未选中序号,不能删除的父目录序号集合,已经检查过的ID集合
	            catelogCheckSub(curSubFdIds, allCheckedFdId, parentMap, 
	            		allCateOrder[curFdId], allCateOrder, notCheckOrderShow, 
	            		notAllSubCheckOrder,alreadyCheckedFdIds);
	        }
	        alreadyCheckedFdIds.push(curFdId);
	    }
	    notCheckOrderShow.sort();
	    if (notCheckOrderShow.length > 0){
	        var mess = "<div style='font-size:14px;text-align:left;word-wrap:break-word;word-break:break-all;'>"
	        			+ "${lfn:message('sys-help:sysHelpCatelog.del.parent')}";
	        _dialog.alert(mess + notCheckOrderShow.toString() + "</div>");
	        return false;
	    }
		
		
		_dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value, dialog) {
				if (value == true) {
					for ( var i = 0; i < delArr.length; i++) {
						DocList_DeleteRow(delArr[i]);
					}
					dialog.hide();
				}
			}
	   	);
		
	}

	/**
	 * 递归查看子代是否被选中
	 * 子目录ID集合，被选中的ID集合，上下级关系， 父目录序号，所有序号，需要显示的未选中序号，不能删除的父目录序号集合,已经检查过的ID集合
	 */
	function catelogCheckSub(curSubFdIds, allCheckedFdId, parentMap, 
			parentOrder, allCateOrder, notCheckOrderShow, 
			notAllSubCheckOrder, alreadyCheckedFdIds){
	    if (curSubFdIds){
	        for (var j = 0; j < curSubFdIds.length; j++){
	            var thisSubFdId = curSubFdIds[j];
	            var thisCateOrder = allCateOrder[thisSubFdId];
	            // 查看子节点是否都被选中
	            if (allCheckedFdId.indexOf(thisSubFdId) == -1){
	            	if(notAllSubCheckOrder.indexOf(parentOrder) == -1){
	                    notAllSubCheckOrder.push(parentOrder);
	            	}
	            	if(notCheckOrderShow.indexOf(thisCateOrder) == -1){
	                    notCheckOrderShow.push(thisCateOrder);
	            	}
	            }
	            var nextSubFdIds = parentMap[thisSubFdId];
	            if (nextSubFdIds){
	                // 子目录ID集合，被选中的ID集合，上下级关系，父目录序号，所有序号，需要显示的未选中序号，不能删除的父目录序号集合,已经检查过的ID集合
	                catelogCheckSub(nextSubFdIds, allCheckedFdId, parentMap, 
	                		thisCateOrder, allCateOrder, notCheckOrderShow,
	                		notAllSubCheckOrder, alreadyCheckedFdIds);
	            }
	            alreadyCheckedFdIds.push(thisSubFdId);
	        }
	    }
	}


	//提交
	/* window.sysHelpCatelog_doOk =  function() {
		catelogJson = []; //清空json,重新拼装
		//拼装json
		var index = GetID('TABLE_DocList').rows.length - 1;
		for ( var i = 0; i < index; i++) {
			var _fdId = GetEl("fdCatelogList[" + i + "].fdId").value;
			var _fdName = GetEl("fdCatelogList[" + i + "].fdName").value;
			var _fdParentDirName = GetEl("fdCatelogList[" + i + "].fdParentDirName").value;
			var _docContent = GetEl("fdCatelogList[" + i + "].docContent").value;
			var _fdParentId = GetEl("fdCatelogList[" + i + "].fdParentId").value;
			var _authEditorIds = GetEl("fdCatelogList[" + i + "].authEditorIds").value;
			
			var _authEditorNames = GetEl("fdCatelogList[" + i+ "].authEditorNames").value;
            var _fdParentDir = GetEl("fdCatelogList[" + i+ "].fdParentDir").value;
            var _fdLevel = GetEl("fdCatelogList[" + i+ "].fdLevel").value;
            var _fdSubject = GetEl("fdCatelogList[" + i+ "].fdSubject").value;
            var _fdLink = GetEl("fdCatelogList[" + i+ "].fdLink").value;
            var _fdSource = GetEl("fdCatelogList[" + i+ "].fdSource").value;
            var _fdKemId = GetEl("fdCatelogList[" + i+ "].fdKemId").value;
            var _fdFlag = GetEl("fdCatelogList[" + i+ "].fdFlag").value;
            var _fdNotifyRelate = GetEl("fdCatelogList[" + i+ "].fdNotifyRelate").value;
			catelogJson[i] = {
				fdId : _fdId,
				fdName : _fdName,
				fdParentDirName : _fdParentDirName,
				fdOrder : i + 1,
				docContent : _docContent,
				fdParentId : _fdParentId,
				authEditorIds : _authEditorIds,
				authEditorNames : _authEditorNames,
                fdParentDir : _fdParentDir,
                fdLevel : _fdLevel,
                fdSubject:_fdSubject,
                fdLink:_fdLink,
                fdSource:_fdSource,
                fdKemId:_fdKemId,
                fdFlag:_fdFlag,
                fdNotifyRelate:_fdNotifyRelate
                
			};
		}
		// 兼容多浏览器
		top.returnValue = catelogJson;
		top.opener = top;
		top.open("", "_self");
		top.close();
	} */

	//公共方法---获取对象
	function GetEl(element) {
		return document.getElementsByName(element)[0];
	}

	//公共方法---获取对象数组
	function GetEls(element) {
		return document.getElementsByName(element);
	}

	//公共方法---获取对象
	function GetID(id) {
		return document.getElementById(id);
	}
 });

var catelog_edit_val = {
	"checkForm": {
	      error: "${ lfn:message('sys-help:sysHelpCatelog.docSubject') }",
	      test: function(v,e,o) {
	    	  _CheckForm();
	    	  return true;
	      }
	  },
	  'fillParentDirName': {
	      error: "${lfn:message('sys-help:sysHelpCatelog.docSubject')}",
	      test: function(v,e,o) {
	          var inName = e.name;
	          var inIndex = inName.substring(19,20);
	          _fillParentDirName(inIndex);
	          return true;
	      }
	  }
	}
	
function _GetEl(element) {
    return document.getElementsByName(element)[0];
}
	
function _fillParentDirName(inIndex){
    var inFdId = _GetEl("sysHelpCatelogList[" + inIndex + "].fdId").value;
    var inFdName = _GetEl("sysHelpCatelogList[" + inIndex + "].docSubject").value;
    if (!inFdId || inFdId.length == 0){
    	return;
    }
    var index = document.getElementById('TABLE_DocList').rows.length - 1;
    for ( var i = 0; i < index; i++) {
        var inFdParentDir = _GetEl("sysHelpCatelogList[" + i + "].fdParentDir").value;
        if (inFdId == inFdParentDir){
        	_GetEl("sysHelpCatelogList[" + i + "].fdParentDirName").value = inFdName;
        }
    }
}
function _CheckForm(){
    for(var i=0; i<Com_Parameter.event["submit"].length; i++){
        if(!Com_Parameter.event["submit"][i]()){
            if (Com_Submit.ajaxCancelSubmit) {
                Com_Submit.ajaxCancelSubmit(formObj);
            }
	    document.getElementById("fdCheckFlag").value = false;
        return false;
        }
    }
    document.getElementById("fdCheckFlag").value = true;
    return true;
}

function initCatelogEditCateScroll(){
	seajs.use(['lui/jquery'],function($) {
		$(window).scroll(function() {
			var tb_offset = $("#TABLE_DocList").offset().top;
			var win_scrollTop = $(document).scrollTop();
			if (tb_offset > win_scrollTop){
				$("#table_doclist_bar").hide();
			}else{
				$("#table_doclist_bar").show();
			}
		});
	});
}
initCatelogEditCateScroll();
</script>
