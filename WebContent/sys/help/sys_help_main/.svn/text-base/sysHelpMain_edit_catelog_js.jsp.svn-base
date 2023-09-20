<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<script>
	Com_IncludeFile("ckfilter.js|ckeditor.js", "ckeditor/");
</script>

<script>
//编辑目录
var catelogJson = [];
<c:forEach items="${sysHelpMainForm.sysHelpCatelogList}" var="sysHelpCatelogForm" varStatus="vstatus">
<c:set var="order" value="${vstatus.index}" scope="request" /> 
	catelogJson["<c:out value='${order}' />"] = {
		fdId:"<c:out value='${sysHelpCatelogForm.fdId}' />",
		docSubject:"<c:out value='${sysHelpCatelogForm.docSubject}' />",
		fdParentDirName:"<c:out value='${sysHelpCatelogForm.fdParentDirName}' />",
		fdOrder:"<c:out value='${sysHelpCatelogForm.fdOrder}' />",
		fdParentId:"<c:out value='${sysHelpCatelogForm.fdParentId}' />",
        fdParentDir:"<c:out value='${sysHelpCatelogForm.fdParentDir}' />",
        fdLevel:"<c:out value='${sysHelpCatelogForm.fdLevel}' />"
	};
</c:forEach>

function editCatelog(){
	//隐藏编辑框(隐藏后才会将编辑的内容放入特指的元素中)
	//destroyDiv();
	LUI.$("body").click();
	if(catelogJson.length > 0){
		for(var i=0; i<catelogJson.length; i++){
			var _fdId = catelogJson[i].fdId;
			var contentObj = GetID("replace_"+_fdId);
			catelogJson[i].docContent = contentObj.innerHTML;//将编辑框的内容放入json中
			catelogJson[i].fdSubject=$("input[name='sysHelpCatelogList[" + i + "].docSubject']").val();
			catelogJson[i].fdLink=$("input[name='sysHelpCatelogList[" + i + "].fdLink']").val();
		}
	}
		
	//将修改后的目录和内容重新赋值
	var ___catelogJson = catelogIframe();
}

var editor;
var editorIndex;
var editorId;
/**
*删除编辑框
*/
function destroyDiv(){
	if (editor){
		editor.destroy();
	}
	//resetAddTableWidth();
}

function resetAddTableWidth(){
	seajs.use(['lui/jquery'],function($) {
		var mm = parseInt($("#main").width());
		var ll = parseInt($($(".bar-left")[0]).css("left"));
		var cc = parseInt($($(".content")[0]).css("padding-left"));
		var catelogWidth = mm + ll - cc - 36;
		$("#catelogTree table").each(function(i){
			var ___css = {"word-break": "break-all","word-wrap": "break-word"};
			if (this.width && this.width > catelogWidth
					|| parseInt($(this).css('width')) > catelogWidth) {
				___css.width = '100%';
				___css.height = 'auto';
			}
			$(this).css(___css);
		});
	});
}

//弹出编辑目录框
function catelogIframe(){
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$){
		$("div[data-lui-mark='dialog.nav.close']").click();
		dialog.iframe(
			'/sys/help/sys_help_catelog/sysHelpCatelog.do?method=openCatelogDialog', 
			" ",
			null, 
			{	
				scroll:true,
				width:870,
				height:470,
				buttons:[{
					name : "${lfn:message('button.ok')}",
					value : true,
					focus : true,
					fn : function(value,_dialog) {
						//获取弹出窗口的document对象里面的table
						var tableObj = LUI.$('#dialog_iframe').find('iframe')[0].contentDocument.getElementById('TABLE_DocList');
						var fdCheckFlag = LUI.$('#dialog_iframe').find('iframe')[0].contentWindow._CheckForm();
						if (!fdCheckFlag){
							return;
						}
						sysHelpCatelog_doOk(tableObj);
						_dialog.hide();
					}
				}, 
				{
					name : "${lfn:message('button.cancel')}",
					styleClass:"lui_toolbar_btn_gray",
					value : false,
					fn : function(value, _dialog) {
						_dialog.hide();
					}
				}]
			}
		);
	});
}


//编辑目录后确定
function sysHelpCatelog_doOk(tableObj) {
	var nullStr = "NULL";
    // 上级目录标号
	var parentIndexMap = {};
    // 上级目录下的标号
	var parentNumMap = {};
  
    // 根目录下的对象
    var rootCates = [];
    var itemCates = {};
	//拼装json
	var index = tableObj.rows.length - 1;
	for ( var i = 0; i < index; i++) {
		var _fdId = LUI.$(tableObj).find("input[name='sysHelpCatelogList[" + i + "].fdId']").val();
		var _fdParentDirName = LUI.$(tableObj).find("input[name='sysHelpCatelogList[" + i + "].fdParentDirName']").val();
		var _fdParentId = LUI.$(tableObj).find("input[name='sysHelpCatelogList[" + i + "].fdParentId']").val();
		var _fdParentDir = LUI.$(tableObj).find("[name='sysHelpCatelogList[" + i + "].fdParentDir']").val();
		var _fdLevel = LUI.$(tableObj).find("[name='sysHelpCatelogList[" + i + "].fdLevel']").val();
		var _docSubject = LUI.$(tableObj).find("input[name='sysHelpCatelogList[" + i + "].docSubject']").val();
		var _docContent = LUI.$(tableObj).find("input[name='sysHelpCatelogList[" + i + "].docContent']").val();

		var _fdParentDirIndex = (_fdParentDir.length == 0) ? nullStr : _fdParentDir;
    	var parentIndex = parentIndexMap[_fdParentDirIndex];
	   	
    	if (!parentIndex){
	        parentIndex = "";
	        parentIndexMap[_fdParentDirIndex] = parentIndex;
	    }
    	var parentNum = parentNumMap[_fdParentDirIndex];
	    if (!parentNum){
	        parentNum = 1;
	    } else {
	        parentNum++;
	    }
    	var thisIndex = parentIndex + parentNum;
	    parentIndexMap[_fdId]  = thisIndex + ".";
	    parentNumMap[_fdParentDirIndex] = parentNum;
	    var _fdCateIndex = thisIndex;
	    var _fdPaddingLeft = 0;
	    if (_fdLevel == 1 || _fdLevel == 2){
	    	_fdPaddingLeft = (_fdLevel - 1) * 10;
	    } else {
	    	_fdPaddingLeft = (_fdLevel-1)*10 + 15;
	    }
    	var oneCate = {
			fdId : _fdId,
			fdParentDirName : _fdParentDirName,
			fdOrder : i + 1,
			fdParentId : _fdParentId,
			fdParentDir : _fdParentDir,
            fdLevel : _fdLevel,
            fdCateIndex : _fdCateIndex,
            fdPaddingLeft : _fdPaddingLeft,
            docSubject :_docSubject,
            docContent : _docContent
		};
    
	    if (!_fdParentDir || _fdParentDir == "" || _fdParentDir == "undefined"){
	    // 没有上级就在根目录下
	        oneCate.fdParentDirName = null;
	        oneCate.fdParentDir = null;
	        rootCates.push(oneCate);
	    } else {
			// 有上级就加入上级的list中
	        var pdirCates = itemCates[_fdParentDir];
	        if (!pdirCates){
	            pdirCates = [];
	            itemCates[_fdParentDir] = pdirCates;
	        }
			pdirCates.push(oneCate);
		}
	}
	var _catelogJson = [];

	// 使用递归
	initCatelogJson(_catelogJson, rootCates, itemCates);
	catelogJson = _catelogJson;

	//修改页面上的目录
	var fdMainId = "${sysHelpMainForm.fdId}";
	//先删除所有页面中的目录及其内容（目录可能会改变）
	var _catelogTree = LUI.$('#catelogTree');
	_catelogTree.empty();
	
	//重新添加目录
	for(var i=0; i<catelogJson.length; i++) {
		var _id = catelogJson[i].fdId;
		var editparagraph = "${lfn:message('sys-help:sysHelpCatelog.editParagraph')}";
		_catelogTree.append('<div class="lui_sys_help_clear"></div>');
		_catelogTree.append('<div id="catelogChild_' + _id + '" style="margin-bottom: 10px;"></div>');
		LUI.$('#catelogChild_' + _id).append(
			'<div class="lui_sys_help_catelog clearfloat"  id="catelog_'+ _id +'"></div>',
			'<div class="lui_sys_help_content" id="editable_'+ _id +'"></div>'	
		);
		
		if (catelogJson[i].fdLevel == 1){
			LUI.$('#catelog_' + _id).append('<div class="com_bgcolor_d" style="width: 8px; display: inline-block; float: left; height: 15px; line-height: 30px; margin-top: 2px;"></div>');
		}else{
			LUI.$('#catelog_' + _id).append('<div class="com_bgcolor_d" style="width: 4px; display: inline-block; float: left; height: 15px; line-height: 30px; margin-top: 2px;"></div>');
		}
		
		var actionType = '${sysHelpMainForm.method_GET}';
		if(actionType && actionType == 'edit'){
			
		} else if(actionType && actionType == 'add') {
			
		}
		
		var rtfParam = "'" + i + "' , '" + _id + "'";
		var clickFunction = 'showRTF(' + rtfParam + ')'
		
		var htmlBtn = $('<div class="lui_sys_help_editparagraph lui_sys_help_edit_cate com_subject" id="editparagraph_' + _id + '"/>');
		
		var editBtn = $('<span id="edit_' + _id + '" onclick="' + clickFunction + '"><bean:message key="button.edit"/></span>');
		htmlBtn.append(editBtn);
		
		var submitBtn = $('<span class="submitBtn" onclick="submitRtf('+rtfParam+')" id="submit_' + _id + '"><bean:message key="button.save"/></span>');
		
		LUI.$('#catelog_' + _id).append(htmlBtn);
		LUI.$('#catelog_' + _id).append(submitBtn);
		LUI.$('#catelog_' + _id).append('<div class="lui_sys_help_title">'+"&nbsp;&nbsp;&nbsp;&nbsp;" + catelogJson[i].fdCateIndex + "&nbsp;&nbsp;" + catelogJson[i].docSubject + '</div>');
		LUI.$('#editable_' + _id).append('<div id="replace_' + _id +'" class="lui_sys_help_content_catelog"></div>');
		LUI.$('#editable_' + _id).append(createHidElement(i, "fdId", catelogJson[i].fdId));
		LUI.$('#editable_' + _id).append(createHidElement(i, "docSubject", catelogJson[i].docSubject));
		LUI.$('#editable_' + _id).append(createHidElement(i, "fdOrder", catelogJson[i].fdOrder));
		LUI.$('#editable_' + _id).append(createHidElement(i, "fdParentId", catelogJson[i].fdParentId));
        LUI.$('#editable_' + _id).append(createHidElement(i, "fdParentDir", catelogJson[i].fdParentDir));
        LUI.$('#editable_' + _id).append(createHidElement(i, "fdLevel", catelogJson[i].fdLevel));
        LUI.$('#editable_' + _id).append(createHidElement(i, "docContent", catelogJson[i].docContent));
		
        GetID('replace_'+_id).innerHTML = catelogJson[i].docContent;
	}
	//重新修改右侧目录
	/* 
	var right_catelog = GetID("catelogUl");
	var html = "";
	for(var i=0;i<catelogJson.length;i++){
		var curFdLevel = catelogJson[i].fdLevel * 1;
		var curFontSize = curFdLevel > 5 ? 12 : (17- curFdLevel);
		var curStyle= "font-size: " + curFontSize + "px;";
		if (curFdLevel == 1){
			curStyle += "color: #15a4fa;";
		}
		var curClass = "";
		if (curFdLevel == 2){
			curClass = "lui_catelog_dot";
		}
		
		var _fdLevel = catelogJson[i].fdLevel;
        var _fdPaddingLeft = 0;
        if (_fdLevel == 1 || _fdLevel == 2){
        	_fdPaddingLeft = (_fdLevel - 1) * 10;
        } else {
        	_fdPaddingLeft = (_fdLevel-1)*10 + 15;
        }
        catelogJson[i].fdPaddingLeft = _fdPaddingLeft;
		
		html = html + ['<li class="right_selectLi lui_sys_help_catelog_li" style="padding-left: ',catelogJson[i].fdPaddingLeft,'px;">','<a class="',curClass,'" href="#catelogChild_',catelogJson[i].fdId,'" style="',curStyle,'">'
						,(catelogJson[i].fdCateIndex + "&nbsp;" +catelogJson[i].docSubject),'</a>','<div id="viewable_',catelogJson[i].fdId,'"></div>' ].join('');
	}
	right_catelog.innerHTML = html; 
	*/
	//刷新二三级目录
	LUI.$('.lui_sys_help_content_catelog').each(function() {
		var _thisId = LUI.$(this).attr('id').split('_');
		var thisId = _thisId[1];
		//去掉原有目录
		 LUI.$('#viewable_' + thisId).find('li').remove();
		 LUI.$(this).find('h3,h4').each(
			function() {
				if(LUI.$(this).is('h3')) {
					LUI.$('#viewable_' + thisId).append('<li class="lui_sys_help_catelog_t" >' + $(this).text()  +'</li>');
				}
				else {
					LUI.$('#viewable_' + thisId).append('<li class="lui_sys_help_catelog_s" >' + $(this).text()  +'</li>');
				}
			}
		);
		
	});
	dbWhite(LUI.$);
}

function showRTF(indexNum, id){
	
	if(editorIndex && editorId)
		submitRtf(editorIndex, editorId);
	
	var dom = $('#editable_'+id+' input[name="sysHelpCatelogList['+indexNum+'].docContent"]');
	if(dom.length == 0)
		return;
	var value = dom[0].value;

	
	var replaceObj = $('#replace_' + id)[0];
	editor = CKEDITOR.replace(replaceObj, {"toolbar":"Wiki","toolbarStartupExpanded":false,"toolbarCanCollapse":true});
	
	$('#edit_'+id).css('display', 'none');
	$('#submit_'+id).css('display', 'block');
	
	editorIndex = indexNum;
	editorId = id;
}

function submitRtf(indexNum, id){
	
	if(!indexNum)
		indexNum = editorIndex;
	
	if(!id)
		id = editorId;
	
	destroyDiv();
	var dom = $('#editable_'+id+' input[name="sysHelpCatelogList['+indexNum+'].docContent"]');
	if(dom.length == 0)
		return;
	
	dom[0].value = $($('#replace_' + id)[0]).html();
	
	$('#edit_'+id).css('display', 'inherit');
	$('#submit_'+id).css('display', 'none');
}

//使用递归来排序
function initCatelogJson(_json, _list, _map){
    for (var i = 0; i < _list.length; i++) {
        var oneData = _list[i];
        _json.push(oneData);
        var fdId = oneData.fdId;
        var subList = _map[fdId];
        if (subList){
            initCatelogJson(_json, subList, _map);
        }
    }
}

//创建隐藏的元素
function createHidElement(iVal, nameVal, valueVal) {
    var ele = LUI.$('<input></input>');
    ele.attr('type', 'hidden');
    ele.attr('name', 'sysHelpCatelogList[' + iVal + '].' + nameVal);
    ele.attr('value', valueVal);
	return ele;
}

//公共方法---获取对象
function GetID(id){
	return document.getElementById(id) ;
}

function dbWhite(_$) {
	_$(".lui_sys_help_content").bind("dblclick", function(event){
		var p = _$(this).prev().find('.lui_sys_help_editparagraph')[0];
		openEdit(p);
	});
};

//提交时修改文档状态
function editStatus(status) {
	document.getElementsByName('docStatus')[0].value = status
}

</script>

<!-- 校验器 -->
<script>
	$KMSSValidation();
</script>




