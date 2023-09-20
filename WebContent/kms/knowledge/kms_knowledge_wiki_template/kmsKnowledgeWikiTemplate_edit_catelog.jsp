<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<p><input type="button" value="${lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.templateInfo.add')}" class="btnopt" id="addRow"
    onclick="wikiAddRow();"
/> <input type="button"
    value="${lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.templateInfo.up')}" class="btnopt" id="upRow" onclick="move_Row(-1)"/> <input
    type="button" value="${lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.templateInfo.down')}" class="btnopt" id="downRow"
    onclick="move_Row(1);" /> <input type="button" value="${lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.templateInfo.delete')}"
    class="btnopt" id="deleteRow" onclick="delete_Row()" /></p>
<center>
<table class="tb_normal" width=100% id="TABLE_DocList">
    <tr>
        <td class="td_normal_title" width="5%"><input type="checkbox"
            id="fdCatelogList_seclectAll" onclick="changeSelectAll(this);"/></td>
        <td class="td_normal_title" width="10%">${lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.templateInfo.order')}</td>
        <td class="td_normal_title" width="25%">${lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.directory.name')}</td>
        <td class="td_normal_title" width="25%">${lfn:message('kms-knowledge:kmsKnowledgeWikiCatalog.fdParentName')}</td>
        <td class="td_normal_title" width="35%">${lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.editor.default')}</td>
    </tr>
    <%--基准行--%><%-- 这里的标签不能换行，顺序不能变，获取元素子节点有换行空格影响，要和内容行一致--%>
    <tr KMSS_IsReferRow="1" style="display: none;"><td><input type="checkbox"
        name="fdCatelogList_seclect" onclick="changeSelect(this);" /></td><td KMSS_IsRowIndex="1"></td><td><input type='text'
            name='fdCatelogList[!{index}].fdName'
            subject="${lfn:message('kms-knowledge:kmsKnowledgeWikiCatalog.fdName')}"
            validate="checkCateName required maxLength(200)"
            style="width: 90%" class="inputsgl" /><input type='hidden'
            name='fdCatelogList[!{index}].fdTemplateId' value='${kmsKnowledgeWikiTemplateForm.fdId}' /><input type='hidden'
            name='fdCatelogList[!{index}].fdId' /><input type='hidden'
            name='fdCatelogList[!{index}].fdOrder' /><input type='hidden'
            name='fdCatelogList[!{index}].fdParentId' /><input type='hidden'
            name='fdCatelogList[!{index}].fdParentName' /><input type='hidden'
            name='fdCatelogList[!{index}].fdLevel' /></td><td><input type='text'
            name='fdParentName' style="width: 90%" class="inputsgl" readOnly="readonly"/></td><td><div class="inputselectsgl" 
            onclick="Dialog_Address(true, 'fdCatelogList[!{index}].authTmpEditorIds', 
            'fdCatelogList[!{index}].authTmpEditorNames', ';', 'ORG_TYPE_ALL');" 
            style="width:97%;"><input name="fdCatelogList[!{index}].authTmpEditorIds" value="" 
            type="hidden"><div class="input"><input name="fdCatelogList[!{index}].authTmpEditorNames"
            readonly="true"></div><div class="orgelement"></div></div></td></tr>
    <%---内容行--%><%-- 这里的标签不能换行，顺序不能变获取元素子节点有换行空格影响，要和基准行一致--%>
    <c:forEach items="${kmsKnowledgeWikiTemplateForm.fdCatelogList}"
        var="kmsKnowledgeWikiCatalogForm" varStatus="vstatus">
        <tr KMSS_IsContentRow="1"><td><input type="checkbox"
            name="fdCatelogList_seclect" onclick="changeSelect(this);" /></td><td>${vstatus.index+1}</td><td><input type='text'
                name='fdCatelogList[${vstatus.index}].fdName'
                subject="${lfn:message('kms-knowledge:kmsKnowledgeWikiCatalog.fdName')}"
                validate="checkCateName required maxLength(200)" value="<c:out value='${kmsKnowledgeWikiCatalogForm.fdName}' />"
                style="width: 90%" class="inputsgl" /><input type='hidden'
                name='fdCatelogList[${vstatus.index}].fdTemplateId' value='${kmsWikiTemplateForm.fdId}' /><input type='hidden'
                name='fdCatelogList[${vstatus.index}].fdId' value='${kmsKnowledgeWikiCatalogForm.fdId}' /><input type='hidden'
                name='fdCatelogList[${vstatus.index}].fdOrder' value='${kmsKnowledgeWikiCatalogForm.fdOrder}' /><input type='hidden'
                name='fdCatelogList[${vstatus.index}].fdParentId' value='${kmsKnowledgeWikiCatalogForm.fdParentId}' /><input type='hidden'
                name='fdCatelogList[${vstatus.index}].fdParentName' value="<c:out value='${kmsKnowledgeWikiCatalogForm.fdParentName}'/>" /><input type='hidden'
                name='fdCatelogList[${vstatus.index}].fdLevel' value='${kmsKnowledgeWikiCatalogForm.fdLevel}' /></td><td><input type='text'
                name='fdParentName' style="width: 90%" class="inputsgl" value="<c:out value='${kmsKnowledgeWikiCatalogForm.fdParentName}'/>" 
                readOnly="readonly"/></td><td><div class="inputselectsgl" onclick="Dialog_Address(true, 'fdCatelogList[${vstatus.index}].authTmpEditorIds', 
                'fdCatelogList[${vstatus.index}].authTmpEditorNames', ';', 'ORG_TYPE_ALL');" 
                style="width:97%;"><input name="fdCatelogList[${vstatus.index}].authTmpEditorIds" 
                value="${kmsKnowledgeWikiCatalogForm.authTmpEditorIds}"type="hidden"><div class="input"><input name="fdCatelogList[${vstatus.index}].authTmpEditorNames" 
                value="${kmsKnowledgeWikiCatalogForm.authTmpEditorNames}" readonly="true"></div><div class="orgelement"></div></div></td></tr>
    </c:forEach>
</table>
</center>
<%@ include file="/kms/wiki/resource/js/kmsWikiJS.jsp"%>
<script>
    $KMSSValidation(document.forms['kmsKnowledgeWikiTemplateForm']).addValidators(validations);
</script>
<script>
initArray();
var orderTdIndex = 1;
var cateInfoTdIndex = 2;

var cateFdNameIndex = 0;
var cateFdIdIndex = 2;
var cateFdPIdIndex = 4;
var cateFdLevelIndex = 6;
/**
 * 添加主目录
 */
function wikiAddRow(){
    var selectObj = GetEl("fdCatelogList_seclect");
    var n = 0;
    // 被选中行的下标
    var checkedCateIndex = 0;
    for ( var i = 0; i < selectObj.length; i++) {
        if (selectObj[i].checked == true) {
            checkedCateIndex = i;
            n++;//计算选中的个数。
            if (n > 1) {
            	seajs.use(['lui/dialog'],function(dialog) {
            		dialog.alert("${lfn:message('kms-knowledge:kmsKnowledgeWikiCatalog.onlyOne')}");
            	});
                return false;
            }
        }
    }
    if (n == 0) {
        _wikiAddRow(null, null, 1, null);
    }else{
    	wikiAddSubRow(selectObj, checkedCateIndex );
    }
}
/**
 * 添加子目录
 */
function wikiAddSubRow(selectObj, checkedCateIndex ){
    // 被选中的目录所有的td
    var checkedCateTds = selectObj[checkedCateIndex].parentNode.parentNode.childNodes;
    // 被选中目录的详细情况的td-input
    var checkedInfoIns = checkedCateTds[cateInfoTdIndex].childNodes;
    // 被选中目录的fdid
    var checkedFdId = checkedInfoIns[cateFdIdIndex].value;
    // 被选中目录的目录名
    var checkedFdName = checkedInfoIns[cateFdNameIndex].value;
    // 被选中目录的层级
    var checkedFdLevel = checkedInfoIns[cateFdLevelIndex].value * 1;
    if (checkedFdLevel > 8){
    	// 目前最高设定9层
    	seajs.use(['lui/dialog'],function(dialog) {
    		dialog.alert("${lfn:message('kms-knowledge:kmsKnowledgeWikiCatalog.over.fdLevel')}");
    	});
        return false;
    }
    checkedFdLevel ++;
    // 添加子目录
    _wikiAddRow(checkedFdId, checkedFdName, checkedFdLevel, checkedCateIndex);
}

function _wikiAddRow(parentId, parentName, curFdLevel, checkedCateIndex){
    DocList_AddRow( 'TABLE_DocList');
    var allCheckbox = GetEl("fdCatelogList_seclect");
    var lastCateTds = allCheckbox[allCheckbox.length - 1].parentNode.parentNode.childNodes;
    var cateInfoTd = lastCateTds[cateInfoTdIndex];
    var cateInfoIns = cateInfoTd.childNodes;
    var cateFdId = cateInfoIns[cateFdIdIndex];
    // fdId 生成id
    var cateFdId = cateInfoIns[cateFdIdIndex];
    cateFdId.value = TMPLEDITHEAD + (++TMPLEDITID);
    // curFdLevel 层级
    var cateFdLevel = cateInfoIns[cateFdLevelIndex];
    cateFdLevel.value = curFdLevel;
    // fdParentId 和 fdParentName
    if (parentId){
    	var newCateIndex = allCheckbox.length - 1;
        var cateFdPId = cateInfoIns[cateFdPIdIndex];
        cateFdPId.value = parentId;
        GetEl("fdParentName")[newCateIndex].value = parentName;
        // 默认可编辑者
        var defEditIds = GetEl("fdCatelogList[" + checkedCateIndex + "].authTmpEditorIds")[0].value;
        var defEditNames = GetEl("fdCatelogList[" + checkedCateIndex + "].authTmpEditorNames")[0].value;
        if (defEditIds){
            GetEl("fdCatelogList[" + newCateIndex + "].authTmpEditorIds")[0].value = defEditIds;
            GetEl("fdCatelogList[" + newCateIndex + "].authTmpEditorNames")[0].value = defEditNames;
        }
        
        // 查看是否移动排序
        moveSubCateToParent(checkedCateIndex, newCateIndex);
    }
}

/**
 * 增加子目录之后，移动子目录
 */
function moveSubCateToParent(checkedCateIndex, newCateIndex){
	if ((newCateIndex - checkedCateIndex) > 1){
	    var allCheckbox = GetEl("fdCatelogList_seclect");
	    var allMaxIndex = allCheckbox.length - 1;
	    var maxIndex = newCateIndex > allMaxIndex ? allMaxIndex : newCateIndex;
	    var parentMap = [];
	    var targetIndex = newCateIndex;
	    parentMap.push(GetEl("fdCatelogList[" + checkedCateIndex + "].fdId")[0].value);
	    for ( var i = checkedCateIndex + 1; i < maxIndex + 1; i++) {
	        var thisFdId = GetEl("fdCatelogList[" + i + "].fdId")[0].value;
	        var thisPId = GetEl("fdCatelogList[" + i + "].fdParentId")[0].value;
	        if (parentMap.indexOf(thisPId) == -1){
	        	targetIndex = i;
	        	break;
	        } else {
	        	parentMap.push(thisFdId);
	        }
	    	
	    }
	    
	    if (targetIndex != newCateIndex){
            var res = targetIndex - newCateIndex;
            var moveStep = res < 0 ? -1 : 1;
            var moveNum = res < 0 ? (0-res) : res;
            var moveObj = GetEl("fdCatelogList[" + newCateIndex + "].fdName")[0].parentNode.parentNode;
            for(var j = 0; j < moveNum; j++){
            DocList_MoveRow(moveStep, moveObj);
	        }
        }
	}
}

/**
 * 上移
 */
function move_Row(val) {
    var selectObj = GetEl("fdCatelogList_seclect");
    var n = 0;
    for ( var i = 0; i < selectObj.length; i++) {
        if (selectObj[i].checked == true) {
            n++;//计算选中的个数。
            if (n > 1) {
            	seajs.use(['lui/dialog'],function(dialog) {
            		dialog.alert("${lfn:message('kms-knowledge:kmsKnowledgeWikiCatalog.onlyOne')}");
            	});
                return false;
            }
        }
    }
    if (n == 0) {
    	seajs.use(['lui/dialog'],function(dialog) {
    		dialog.alert("${lfn:message('kms-knowledge:kmsKnowledgeWikiCatalog.noOne')}");
    	});
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

function delete_Row() {
    var selectObj = $('input[name="fdCatelogList_seclect"]:checked');
    if (!(selectObj.length)) {
    	seajs.use(['lui/dialog'],function(dialog) {
    		dialog.alert("${lfn:message('kms-knowledge:kmsKnowledgeWikiCatalog.noOne')}");
    	});
        return;
    }
    
    // 只要有一个不能删除就不删除，上下级全部选中才能一起删除
    // 统计一下被选中的信息
    var allCheckedFdId = [];
    var allCheckedFdName = [];
    var allCheckedFdOrder = [];
    for(var i =0; i < selectObj.length; i++){
        var curCateTds = selectObj[i].parentNode.parentNode.childNodes;
        var curInfoTd = curCateTds[cateInfoTdIndex];
        var curInfoIns = curInfoTd.childNodes;
        var curFdId = curInfoIns[cateFdIdIndex].value;
        allCheckedFdId.push(curFdId);
        var curFdName = curInfoIns[cateFdNameIndex].value
        allCheckedFdName.push(curFdName);
        var curFdOrder = curCateTds[orderTdIndex].innerHTML;
        allCheckedFdOrder.push(curFdOrder);
    }
    
    // 统计一下上下级关系
    var parentMap = {};
    var allCateOrder = {};
    var allCate = GetEl("fdCatelogList_seclect");
    for ( var i = 0; i < allCate.length; i++) {
        var curCateTds = allCate[i].parentNode.parentNode.childNodes;
        var curOrder = curCateTds[orderTdIndex].innerHTML;
        var curInfoTd = curCateTds[cateInfoTdIndex];
        var curInfoIns = curInfoTd.childNodes;
        var curFdId = curInfoIns[cateFdIdIndex].value;
        var curFdPId = curInfoIns[cateFdPIdIndex].value;
        allCateOrder[curFdId] = curOrder;
        if (curFdPId){
        	var subFdIds = parentMap[curFdPId];
        	subFdIds = subFdIds ? subFdIds : [];
        	subFdIds.push(curFdId);
        	parentMap[curFdPId] = subFdIds;
        }
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
            wikiCheckSub(curSubFdIds, allCheckedFdId, parentMap,
            		allCateOrder[curFdId], allCateOrder, notCheckOrderShow,
            		notAllSubCheckOrder,alreadyCheckedFdIds);
    	}
        alreadyCheckedFdIds.push(curFdId);
    }
    notCheckOrderShow.sort();
    if (notCheckOrderShow.length > 0){
        var mess = "<div style='font-size:14px;text-align:left;word-wrap:break-word;word-break:break-all;'>"
	        + "${lfn:message('kms-knowledge:kmsKnowledgeWikiCatalog.del.parent')}";
        
        seajs.use(['lui/dialog'],function(dialog) {
        	dialog.alert(mess + notCheckOrderShow.toString() + "</div>");
        });
    	return;
    }
    
    if (confirm('<bean:message key="page.comfirmDelete"/>')) {
        selectObj.each( function(e) {
            var dom = $('input[name="fdCatelogList_seclect"]:checked')[0];
            DocList_DeleteRow(dom.parentNode.parentNode);
        });
    }
}

/**
 * 递归查看子代是否被选中
 * 子目录ID集合，被选中的ID集合，上下级关系， 父目录序号，所有序号，需要显示的未选中序号，不能删除的父目录序号集合,已经检查过的ID集合
 */
function wikiCheckSub(curSubFdIds, allCheckedFdId, parentMap,
		parentOrder, allCateOrder, notCheckOrderShow, 
		notAllSubCheckOrder, alreadyCheckedFdIds){
    if (curSubFdIds){
        for (var j = 0; j < curSubFdIds.length; j++){
        	var thisSubFdId = curSubFdIds[j];
        	var thisCateOrder = allCateOrder[thisSubFdId];
            // 查看子节点是否都被选中
            if (allCheckedFdId.indexOf(thisSubFdId) == -1){
            	if (notAllSubCheckOrder.indexOf(parentOrder) == -1){
                    notAllSubCheckOrder.push(parentOrder);
            	}
            	if (notCheckOrderShow.indexOf(thisCateOrder) == -1){
            		notCheckOrderShow.push(thisCateOrder);
            	}
            }
        	var nextSubFdIds = parentMap[thisSubFdId];
        	if (nextSubFdIds){
        		// 子目录ID集合，被选中的ID集合，上下级关系，父目录序号，所有序号，需要显示的未选中序号，不能删除的父目录序号集合,已经检查过的ID集合
        		wikiCheckSub(nextSubFdIds, allCheckedFdId, parentMap,
        				thisCateOrder, allCateOrder, notCheckOrderShow,
        				notAllSubCheckOrder, alreadyCheckedFdIds);
        	}
            alreadyCheckedFdIds.push(thisSubFdId);
        }
    }
}

//选择可编辑者
function selectEditors(thisObj) {
    var idObj = thisObj.parentNode.childNodes[0];
    var nameObj = thisObj.parentNode.childNodes[1].nextSibling;
    Dialog_Address(true, idObj.name, nameObj.name, ';', 'ORG_TYPE_ALL');
}

//全选
function changeSelectAll(thisObj) {
    var selectObj = GetEl("fdCatelogList_seclect");
    if (thisObj.checked == true) {
        //全选
        if (selectObj != null) {
            for ( var i = 0; i < selectObj.length; i++) {
                selectObj[i].checked = true;
            }
        }
    } else {
        //取消全选
        for ( var i = 0; i < selectObj.length; i++) {
            selectObj[i].checked = false;
        }
    }
}

//选择行
function changeSelect(thisObj) {
    var selectAllObj = GetID("fdCatelogList_seclectAll");
    if (thisObj.checked == true) {
        var isAll = true;
        var selectObj = GetEl("fdCatelogList_seclect");
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

//提交前将order重新赋值
Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
    var index = GetID('TABLE_DocList').rows.length;//获取行数
    for ( var i = 1; i < index; i++) {          
        GetEl("fdCatelogList[" + (i - 1) + "].fdOrder")[0].value = i;
    }
    return true;
}

function GetEl(element) {
    return document.getElementsByName(element);
}

function GetID(element) {
    return document.getElementById(element);
}
</script>
