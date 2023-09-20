/*压缩类型：标准*/
/***********************************************
JS文件说明：
该文件提供了常用的动态列表的通用方法。
使用说明：
	1、请在页面中定义一个ID为"TABLE_DocList"的表格，该表格即为动态列表的表格，若不希望使用TABLE_DocList作为表格ID，请改写DocList_Info的值（ID数组）
	2、在该表格的定义一个基准行，当新增行的时候，程序会自动复制基准行的HTML代码进行创建新行。标签属性说明：
		TR标签：
			KMSS_IsReferRow="1"：必须，表示该行为基准行
		TD标签：
			KMSS_IsRowIndex="1"：可选，表示该列用于显示序号
		域name属性的替换符：
			!{index}：行索引号
	3、若表格中本来就有内容，内容的行必须紧跟在基准行的后面，内容行的TR标签必须定义KMSS_IsContentRow="1"属性。
	4、当表格中需要出现选择框的时候，可以采用DocList_GetPreField函数获取到临近的域对象。
HTML样例：
<table id="TABLE_DocList" class="tb_normal" width="90%">
	<tr>
		<td>序号</td>
		<td>文本内容</td>
		<td>输入框</td>
		<td>
			<a href="#" onclick="DocList_AddRow();">添加</a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td>文本内容</td>
		<td><input name="field[0][!{index}]" value="!{index}"></td>
		<td>
			<a href="#" onclick="DocList_DeleteRow();">删除</a>
			<a href="#" onclick="DocList_MoveRow(-1);">上移</a>
			<a href="#" onclick="DocList_MoveRow(1);">下移</a>
			<a href="#" onclick="DocList_CopyRow();">复制</a>
		</td>
	</tr>
	<!--内容行-->
	<tr KMSS_IsContentRow="1">
		<td>1</td>
		<td>文本内容</td>
		<td><input name="field[0][0]" value="0"></td>
		<td>
			<a href="#" onclick="DocList_DeleteRow();">删除</a>
			<a href="#" onclick="DocList_MoveRow(-1);">上移</a>
			<a href="#" onclick="DocList_MoveRow(1);">下移</a>
			<a href="#" onclick="DocList_CopyRow();">复制</a>
		</td>
	</tr>
	<tr>
		<td colspan="4">这是其他行的内容
			<a onclick="DocList_importExcel('TABLE_DocList','kmImeetingAgendaForms','com.landray.kmss.km.imeeting.model.KmImeetingMain');">Excel上传</a><!--Excel模板在弹框里面下载-->
		</td>
	</tr>
</table>

作者：叶中奇
版本：1.0 2006-4-3
***********************************************/

Com_RegisterFile("doclistnew.js");
Com_IncludeFile("data.js");
if(!window.dojo){
	Com_IncludeFile("jquery.js");
	Com_IncludeFile("doclistdnd.js");
}
var DocList_Info = new Array("TABLE_DocList");		//全局变量，ID列表
var DocList_TableInfo = new Array;
var _flag="add";
var _uploadProgressflag=false;
var _uploadNewProgress=false;
/***********************************************
功能：获取界面中某个对象中指定对象的字段的和
参数：
	obj：
		必选，对象
	fieldRe：
		必选，域名称或域的正则表达式
***********************************************/
function DocList_GetSum(obj, fieldRe){
	var isReg = typeof(fieldRe)!="string";
	var fields = obj.getElementsByTagName("INPUT");
	var sum = 0;
	for(var i=0; i<fields.length; i++){
		if(isReg){
			if(!fieldRe.test(fields[i].name))
				continue;
		}else{
			if(fields[i].name!=fieldRe)
				continue;
		}
		var value = parseFloat(fields[i].value);
		if(!isNaN(value))
			sum += value;
	}
	return sum;
}

/***********************************************
功能：添加行
参数：
	optTB：
		可选，表格ID或表格对象，默认取当前操作的表格
	content：
		可选，HTML代码数组，往每个单元格塞的内容，默认从基准行中取数据
			若希望只提供几个单元格的数据时，只需要将其他单元格对应数组元素设置为null
	fieldValues：
		可选，对象，格式为fieldValues[fieldName]=fieldValue，fieldName可带索引号
***********************************************/
function DocList_AddRow(optTB, content, fieldValues,addtype){
	if(optTB==null)
		optTB = DocListFunc_GetParentByTagName("TABLE");
	else if(typeof(optTB)=="string")
		optTB = document.getElementById(optTB);
	if(content==null)
		content = new Array;
	var tbInfo = DocList_TableInfo[optTB.id];
	var index = tbInfo.lastIndex - tbInfo.firstIndex;
	var htmlCode, newCell;
	var newRow = optTB.insertRow(tbInfo.lastIndex);
	tbInfo.lastIndex++;
	newRow.className = tbInfo.className;
	$(newRow).attr("IsReferRow","1");
	for(var i=0; i<tbInfo.cells.length; i++){
		newCell = newRow.insertCell(-1);
		newCell.className = tbInfo.cells[i].className;
		newCell.align = tbInfo.cells[i].align ? tbInfo.cells[i].align : '';
		newCell.vAlign = tbInfo.cells[i].vAlign ? tbInfo.cells[i].vAlign : '';
		if(tbInfo.cells[i].isIndex){
			htmlCode = content[i]==null?tbInfo.cells[i].innerHTML:content[i];
			if(htmlCode==null || htmlCode==""){
				htmlCode = ''+(index+1);
			}else{
				htmlCode = DocListFunc_ReplaceIndex(htmlCode, index + 1,addtype);
				htmlCode =  htmlCode.replace("{1}", index + 1);//自定义表单中明细表处理
			}
		}else
			htmlCode = DocListFunc_ReplaceIndex(content[i]==null?(DocList_formatHtml(tbInfo.cells[i])):content[i], index,addtype);
		newCell.innerHTML = htmlCode;
		//初始化明细表新增的行的地址本 by 郑超
		if (!window.dojo) {
			Address_QuickSelectionDetail(htmlCode);
		}
	}
	
	//contain function
	function contains(array, obj) {
        var length = array.length;
        while (length--) {
            if (array[length] === obj) {
                return true;
            }
        }
        return false;
    }
	
	//DocList_ResetFieldWidth(newRow);重置宽度，会导致百分比失效，因此去掉。---modify by miaogr
	if(fieldValues!=null){
		for(var name in fieldValues){
			var value = fieldValues[name],addressName = name;
			name = DocListFunc_ReplaceIndex(name, index);
			var fields = document.getElementsByName(name);
			//var fields = $(newRow).find("[name='"+name+"']");
			fieldLoop:
			for(var i=0; i<fields.length; i++){
				//pObj=fields[i];
				for(var pObj=fields[i].parentNode; pObj!=null; pObj=pObj.parentNode){
					if(pObj==newRow)
						break;
				}
				if(pObj!=null){
					switch(fields[i].tagName){
						case "INPUT":
							if(fields[i].type=="radio"){
								if(fields[i].value==value){
									fields[i].checked = true;
									break fieldLoop;
								}
								break;
							}
							if(fields[i].type=="checkbox"){
								var valueArray = value ? value.split(';') : [];
								fields[i].checked = contains(valueArray,fields[i].value);
								break;
							}
						case "TEXTAREA":
							var textareaName = fields[i].name;
							if(textareaName){
								var displayTextarea = document.getElementsByName("_" + textareaName);
								if(displayTextarea && displayTextarea.length > 0){
									//#69748 明细表复制后，多选值丢失
									if (displayTextarea[0].type !== "checkbox"){
										displayTextarea[0].value = value;
									}
								}
							}
							fields[i].value = value;
							break;
						case "SELECT":
							for(var j=0; j<fields[i].options.length; j++){
								if(fields[i].options[j].value==value)
									fields[i].options[j].selected = true;
							}
							break;
					}
					//新地址本
					if(Address_isNewAddress(fields[i])) {
						var address = Address_GetAddressObj(name,i);
						var idName = addressName.replace("Name","Id");
						if(idName == addressName) {
							idName = addressName.replace(".name",".id");
						}
						var idValues = fieldValues[idName].split(";"),nameValues = value.split(";");
						var addressValues = new Array();
						for (var j = 0; j < idValues.length; j++) {
							if(idValues[j] != null && idValues[j] != '' && nameValues[j] != null && nameValues[j] != '')
								addressValues.push({id:idValues[j],name:nameValues[j]});
						}
						try{
							// 清空值
							address.emptyAddress(false);
							address.idField.value = fieldValues[idName];
							address.nameField.value = value;
							address.addData(addressValues);
						}catch(err) {
						}
					}
				}
			}
		}
	}
	DocList_RemoveDeleteAllFlag(optTB);
	//增加表格操作事件 作者 曹映辉 #日期 2014年6月19日
	//$(optTB).trigger($.Event("table-add",newRow));
	var evetS=addtype=="child"?"table-add-child":"table-add";
	$(optTB).trigger($.Event(evetS),newRow);
	// 新的新增行事件，有更好的扩展性 by zhugr 2017-10-28
	$(optTB).trigger($.Event("table-add-new"),{'row':newRow,'vals':fieldValues,'table':optTB});
	if(window.doclistdnd && tbInfo.tbdraggable){
		window.doclistdnd.rebuild(tbInfo.DOMElement);
	}
	DocList_ResetOptCol(tbInfo);
	return newRow;
}

function DocList_ResetFieldWidth(obj){
	var fields = obj.getElementsByTagName("INPUT");
	for(var i=0; i<fields.length; i++){
		if(fields[i].offsetWidth>0)
			fields[i].style.width = fields[i].offsetWidth;
	}
	var fields = obj.getElementsByTagName("TEXTAREA");
	for(var i=0; i<fields.length; i++){
		if(fields[i].offsetWidth>0)
			fields[i].style.width = fields[i].offsetWidth;
	}
}

function DocList_ResetOptCol(tbInfo){
	if(tbInfo && tbInfo.compatible)
		return;
	var domElement = tbInfo.DOMElement,
		firstRow = domElement.rows[0];
	if(firstRow != null && firstRow.cells != null){
		var columns = 0;
		// 计算总列数
		for(var i = 0;i < firstRow.cells.length;i++){
			var cell = firstRow.cells[i];
			var colspan = $(cell).attr("colspan");
			if(colspan){
				var l = parseInt(colspan);
				if(isNaN(l)){
					columns++;
				}else{
					columns += l;	
				}
			}else{
				columns++;
			}
		}
		var optRow = domElement.rows[domElement.rows.length - 1];
		if(optRow.getAttribute('type') == 'optRow' && optRow.cells.length >0){
			optRow.cells[0].setAttribute('colspan',columns);
		}
	}
}

//兼容项目中JSP片段逻辑
function DocList_Compatible(tbInfo){
	tbInfo.compatible = true;
	var domElement =  tbInfo.DOMElement,
		titleRow = $(domElement).find('[type="titleRow"]'),
		optRow = $(domElement).find('[type="optRow"]');
	if(titleRow.length > 0){
		var td = titleRow.find('td:last'),
			addOpt = $('<span style="cursor:pointer" onclick="DocList_AddRow();XFom_RestValidationElements();"></span>');
			addOpt.addClass('optStyle opt_add_compatible_style')
				.attr('title',Data_GetResourceString('doclist.add'));
		td.append(addOpt);
	}
	if(optRow.length > 0){
		optRow.remove();
	}
}

/***********************************************
功能：补充html中value等表单属性的值
参数：
	html：
		必填，页面html
***********************************************/
function DocList_formatHtml(html) {
	if(!html)
		return;
	var $html = $(html);
	$("input,button", $html).each(function() {
				this.setAttribute('value', this.value);
			});

	$("textarea", $html).each(function() {
				this.setAttribute('value', this.value);
				$(this).text(this.value);
			});
	$(":radio,:checkbox", $html).each(function() {
				if (this.checked)
					this.setAttribute('checked', 'checked');
				else
					this.removeAttribute('checked');
			});
	$("option", $html).each(function() {
				if (this.selected)
					this.setAttribute('selected', 'selected');
				else
					this.removeAttribute('selected');
			});
	var ___ = $html[0].innerHTML;
	
	$(":radio", $html).each(function() {
		this.setAttribute('data-name',this.getAttribute('name'));
		this.setAttribute('name','______'+this.getAttribute('name'));	
	});
	return ___;
}

function DocList_getFiledValue(areaDom, elemName){
	var elemObj = $("[name='"+elemName+"']:input",areaDom);
	if(elemObj.is(":text")){
		return elemObj.val();
	}else if(elemObj.is(":radio")||elemObj.is(":checkbox")){
		var rtnStr = "";
		elemObj.each(function(index,ele){
			var tmpObj = $(ele);
			if(tmpObj.is(":checked")){
				rtnStr += ";" + tmpObj.val();
			}
		});
		return rtnStr!=""?rtnStr.substring(1):"";
	}else{
		return elemObj.val();
	}
	return "";
}

/*******************************************************************************
 * 功能：复制行 参数： optTR： 可选，操作行对象，默认取当前操作的当前行
 ******************************************************************************/
function DocList_CopyRow(optTR){
	if(optTR==null)
		optTR = DocListFunc_GetParentByTagName("TR");
	var vals = {};
	$(optTR).find(":input").each(function(idx, elem){
		var domNode = $(elem); 
		if(elem && elem.name){
			var formatName = elem.name.replace(/\[\d+\]/g, "[!{index}]");
			formatName = formatName.replace(/\.\d+\./g, ".!{index}.");
			vals[formatName] = DocList_getFiledValue(optTR,elem.name);
		}
	});
	var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
	var newOptTB = DocList_AddRow(optTB,null,vals);
	//#22172 复制后进行校验
	//延时执行，是因为动态下拉，选项还没加载完毕，就先执行校验了 #49138明细表中动态下拉框设置了必填如果复制行动态下拉框仍然提示不能为空
	setTimeout(function(){
		if(typeof($KMSSValidation) == 'function'){
			var validation = $KMSSValidation();
			$(newOptTB).find(":input").each(function(idx, elem){
				validation.validateElement(elem);
			});
		}
	},500);
	// 兼容业务模块
	var $fdIdDom = $(newOptTB).find("input[type='hidden'][name$='.fdId)'],input[type='hidden'][name$='.fdId']");
	if($fdIdDom.length > 0){
		// 理论上只会找到一个fdId，多个也只清最后一个fdId by zhugr 2018-04-09
		$fdIdDom[$fdIdDom.length - 1].value = '';
	}
	//增加表格操作事件 作者 曹映辉 #日期 2014年6月19日
	$(optTB).trigger($.Event("table-copy"),optTR);
}

/***********************************************
功能：删除行
参数：
	optTR：
		可选，操作行对象，默认取当前操作的当前行
***********************************************/
function DocList_DeleteRow(optTR){
	if(optTR==null)
		optTR = DocListFunc_GetParentByTagName("TR");
	//操作行不允许删除
	if(optTR.getAttribute('type')=='optRow'){
		return;
	}
	var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
	//增加表格操作事件 作者 曹映辉 #日期 2014年6月19日
	$(optTB).trigger($.Event("table-delete"),optTR);
	var tbInfo = DocList_TableInfo[optTB.id];
	var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
	var index = DocList_GetRowIndex(tbInfo,optTR);
	optTB.deleteRow(rowIndex);
	
	tbInfo.lastIndex--;
	for(var i = rowIndex; i<tbInfo.lastIndex; i++)
		DocListFunc_RefreshIndex(tbInfo, i, index++);
	$(optTB).trigger($.Event("table-delete-finish"), {'row':optTR,'table':optTB});
}

/*
 * 全选
 * 
 * */
function DocList_SelectAllRow(dom){
	var selected = dom.checked;
	var optTB = DocListFunc_GetParentByTagName("TABLE", dom);
	$(optTB).find("input[name='DocList_Selected']").each(function() {
		this.checked = selected;
	});
}


/*
 * 每行checkBox点击事件
 */
function DocList_SelectRow(dom){
	var checked = dom.checked;
	var optTB = DocListFunc_GetParentByTagName("TABLE", dom);
	var $selectAll = $(optTB).find("input[name='DocList_SelectAll']");
	if (checked){
		var checkedNum = $(optTB).find("input[name='DocList_Selected']:checked").size();
		var allNum = $(optTB).find("input[name='DocList_Selected']").size();
		if (checkedNum == allNum){
			$selectAll.prop("checked",checked);
		}
	}else{
		$selectAll.prop("checked",checked);
	}
}

Com_AddEventListener(window, "load", function(){
	$(document).on("table-copy",function(event,source){
		var checkBoxObj = $(source).find("[name='DocList_Selected']");
		if (checkBoxObj[0]){
			DocList_SelectRow(checkBoxObj[0]);
		}
	});


	$(document).on("table-delete-new",function(event,source){
		if (source && source.table){
			DocList_CheckedSelectAll(source.table);
		}
	});


	$(document).on("table-add-new",function(event,source){
		if (source && source.table){
			DocList_CheckedSelectAll(source.table);
		}
	});
});

//是否选中全选按钮
function DocList_CheckedSelectAll(table){
	var $selectAll = $(table).find("input[name='DocList_SelectAll']");
	var checkedNum = $(table).find("input[name='DocList_Selected']:checked").size();
	var allNum = $(table).find("input[name='DocList_Selected']").size();
	if (checkedNum == allNum){
		$selectAll.prop("checked",true);
	}else{
		$selectAll.prop("checked",false);
	}
}
//全选结束

/*
 * 批量删除行
 *  
 * */
function DocList_BatchDeleteRow(){
	var optTR = DocListFunc_GetParentByTagName("TR");
	var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
	var docList_Selected = $("input[name='DocList_Selected']:checked",optTB);
	var tbInfo = DocList_TableInfo[optTB.id];
	if(docList_Selected.size() == 0){
		alert(Data_GetResourceString('page.noSelect'));
		return;
	}
	for(var i = docList_Selected.size() - 1;i >= 0 ;i--){
		var _optTR = docList_Selected.eq(i).closest('tr');
		if(_optTR && _optTR.size() > 0){
			DocList_DeleteRow_ClearLast(_optTR[0]);
		}
	}
	// 删除完之后，全选复选框重新置为未选状态
	$(optTB).find("input[name='DocList_SelectAll']").each(function(){
		this.checked = false;
	});
	$(optTB).trigger($.Event("table-batchDelete"),optTB);
}

/**
 * 增加删除到最后一行后，生成一空行的函数。
 * 解决明细表无法删除最后一函数数据问题
 * @作者：曹映辉 @日期：2012年4月28日 
 */
function DocList_DeleteRow_ClearLast(optTR,deltype){
	if(optTR==null)
		optTR = DocListFunc_GetParentByTagName("TR");
	var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
	// 兼容移动端删除最后一行报脚本 by zhugr 2017-08-04
	if(!optTB){
		return ;
	}
	var tbInfo = DocList_TableInfo[optTB.id];
	var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
	var index = DocList_GetRowIndex(tbInfo,optTR);
	//最后索引的减少需要放到事件触发之前 by zhugr 2017-05-04
	tbInfo.lastIndex--;
	optTB.deleteRow(rowIndex);
	var delS=deltype=="child"?"table-delete-child":"table-delete";
	if(deltype=="child")
		$(optTB).trigger($.Event(delS),optTB.id);
	else
		$(optTB).trigger($.Event(delS),optTR);
	// 新的删除行事件，有更好的扩展性 by zhugr 2018-01-15
	$(optTB).trigger($.Event("table-delete-new"),{'row':optTR,'table':optTB});
	if(deltype!="child"&&deltype.indexOf("locationsList_")==-1){
		for(var i = rowIndex; i<tbInfo.lastIndex; i++)
			DocListFunc_RefreshIndex(tbInfo, i, index++);
	}
	//删除最后一行数据时生成一空行，避免导致最后一行数据无法删除
	if((tbInfo.lastIndex - tbInfo.firstIndex) == 0){
		DocList_AddDeleteAllFlag(optTB);
	}
	window.focus();
}

function DocList_GetRowIndex(tbInfo,currentRow){
	var index = $(currentRow).index();
	return index + 1 - tbInfo.firstIndex;
}

// ====== 明细表为全部删除标识相关函数 =======
function DocList_GetDeleteAllFlagName(optTB){
	var name = null;
	var tbInfo = DocList_TableInfo[optTB.id];
	for(var i=0; i<tbInfo.cells.length; i++){
		var html = tbInfo.cells[i].innerHTML;
		if (html.indexOf("!{index}") > -1) {
			var reg = /<([b-z][^>]*)\sname\s*=\s*("[^"]+!{index}[^"]+"|'[^']+!{index}[^']+'|[^\s]+!{index}[^\s]+)([^>]*)>/gi;
			reg.exec(html);
			var fname = RegExp.$2;
			if (fname != null && fname.length > 0) {
				name = fname.replace(/[\[\.]!\{index\}[\]\.]\S[^\)]*/ig, "__autolist__");
				break;
			}
		}
	}
	if (name != null && (name.indexOf("\"") == 0 || name.indexOf("'") == 0)) {
		name = name.substring(1, name.length - 1);
	}
	return name;
}
function DocList_AddDeleteAllFlag(optTB){
	var id = optTB.id + "__autolist__";
	var name = DocList_GetDeleteAllFlagName(optTB);
	if (name == null) {
		return;
	}
	var hidden = document.createElement("input");
	hidden.type = "hidden";
	hidden.id = id;
	hidden.name = name;
	hidden.value = "true";
	optTB.rows[0].cells[0].appendChild(hidden);
}
function DocList_RemoveDeleteAllFlag(optTB){
	var id = optTB.id + "__autolist__";
	var hidden = document.getElementById(id);
	if (hidden) {
		var parent = hidden.parentNode;
		parent.removeChild(hidden);
	}
}
/***********************************************
功能：移动行
参数：
	direct：
		必选，1：下移动，-1上移动
	optTR：
		可选，操作行对象，默认取当前操作的当前行
***********************************************/
function DocList_MoveRow(direct, optTR){
	if(optTR==null)
		optTR = DocListFunc_GetParentByTagName("TR");
	var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
	var tbInfo = DocList_TableInfo[optTB.id];
	var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
	var tagIndex = rowIndex + direct;
	var rowIndexVal = DocList_GetRowIndex(tbInfo,optTR);
	var tagIndexVal = DocList_GetRowIndex(tbInfo,optTB.rows[tagIndex]);
	if(direct==1){
		if(tagIndex>=tbInfo.lastIndex){
			alert(Data_GetResourceString('doclist.bottom.tip'));
			return 'bottom';
		}
		optTB.rows[rowIndex].parentNode.insertBefore(optTB.rows[tagIndex], optTB.rows[rowIndex]);
	}else{
		if(tagIndex<tbInfo.firstIndex){
			alert(Data_GetResourceString('doclist.top.tip'));
			return 'top';
		}
		optTB.rows[rowIndex].parentNode.insertBefore(optTB.rows[rowIndex], optTB.rows[tagIndex]);
	}
	
	DocListFunc_StoreCheckedProp(optTB);
	DocListFunc_RefreshIndex(tbInfo, rowIndex, rowIndexVal);
	DocListFunc_RefreshIndex(tbInfo, tagIndex, tagIndexVal);
	DocListFunc_ReloadCheckedProp(optTB);
	
	//增加表格操作事件 作者 曹映辉 #日期 2014年6月19日
	$(optTB).trigger($.Event("table-move"),[rowIndex,tagIndex]);
}

//缓存radio的check属性,用于name值改变后check属性丢失的还原
function DocListFunc_StoreCheckedProp(html){
	var $html = $(html);
	$(":radio", $html).each(function() {
		if (this.checked)
			this.setAttribute('___checked', 'checked');
		else
			this.removeAttribute('___checked');
	});
}
//还原radio丢失的check属性
function DocListFunc_ReloadCheckedProp(html){
	var $html = $(html);
	$(":radio", $html).each(function() {
		if(this.getAttribute('___checked') && this.getAttribute('___checked') == 'checked'){
			this.checked = true;
			this.removeAttribute('___checked');
		}
	});
}

function DocList_MoveRowBySelect(direct){
	var optTR = DocListFunc_GetParentByTagName("TR");
	var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
	var docList_Selected = $("input[name='DocList_Selected']:checked",optTB);
	if(docList_Selected.size() == 0){
		alert(Data_GetResourceString('page.noSelect'));
		return;
	}
	if(direct == -1){
		for(var i=0;i<docList_Selected.size();i++){
			var _optTR = docList_Selected.eq(i).closest('tr');
			if(_optTR && _optTR.size()>0){
				var result = DocList_MoveRow(direct,_optTR[0]);
				if(result == 'top'){
					break;
				}
			}
		}
	}
	if(direct == 1){
		for(var i=docList_Selected.size() - 1;i>=0;i--){
			var _optTR = docList_Selected.eq(i).closest('tr');
			if(_optTR && _optTR.size()>0){
				result = DocList_MoveRow(direct,_optTR[0]);
				if(result == 'bottom'){
					break;
				}
			}
		}
	}
}

/***********************************************
功能：获取obj前面以fieldName命名的第一个HTML元素
参数：
	obj：
		必选，参考对象
	fieldName：
		必选，HTML元素名
	isWholeWord：
		可选，是否全字匹配，默认为false
返回：HTML元素，找不到则返回null
***********************************************/
function DocList_GetPreField(obj, fieldName, isWholeWord){
	if(obj==null)
		return null;
	if(obj.name!=null){
		if(isWholeWord){
			if(obj.name==fieldName)
				return obj;
		}else{
			if(obj.name.indexOf(fieldName)>-1)
				return obj;
		}
	}
	var tmpObj = obj.previousSibling;
	if(tmpObj!=null){
		for(;tmpObj.lastChild!=null;)
			tmpObj = tmpObj.lastChild;
		return DocList_GetPreField(tmpObj, fieldName, isWholeWord);
	}
	return DocList_GetPreField(obj.parentNode, fieldName, isWholeWord);
}
/***********************************************
功能：获取obj后面以fieldName命名的第一个HTML元素
参数：
	obj：
		必选，参考对象
	fieldName：
		必选，HTML元素名
	isWholeWord：
		可选，是否全字匹配，默认为false
返回：HTML元素，找不到则返回null
***********************************************/
function DocList_GetSufField(obj, fieldName, isWholeWord){
	if(obj==null)
		return null;
	if(obj.name!=null){
		if(isWholeWord){
			if(obj.name==fieldName)
				return obj;
		}else{
			if(obj.name.indexOf(fieldName)>-1)
				return obj;
		}
	}
	var tmpObj = obj.nextSibling;
	if(tmpObj!=null){
		for(;tmpObj.firstChild!=null;)
			tmpObj = tmpObj.firstChild;
		return DocList_GetSufField(tmpObj, fieldName, isWholeWord);
	}
	return DocList_GetSufField(obj.parentNode, fieldName, isWholeWord);
}

/***********************************************
功能：获取指定name在当前行的对象，但如果不存在，通过index修正
参数：
	obj：
		必选，参考对象
	fieldName：
		必选，HTML元素名
返回：HTML元素，找不到则返回null
***********************************************/
function DocList_GetRowField(obj, fieldName) {
	var rtn = DocList_GetRowFields(obj, fieldName);
	return rtn == null ? null : rtn[0];
}

function DocList_GetRowFields(obj, fieldName) {
	// 测试是否是明细表内容
	if (!(/\[\d+\]/g.test(fieldName)) && !(/\.\d+\./g.test(fieldName))) {
		return document.getElementsByName(fieldName);
	}
	var optTR = $(obj).closest('tr')[0];
	var fields = $(optTR).find('[name="'+fieldName+'"]');
	if (fields.length > 0) {
		var r = [];
		fields.each(function() {r.push(this);});
		return r;
	}
	var optTB = $(optTR).closest('table')[0];
	if(!optTB){
		return document.getElementsByName(fieldName);
	}
	var tbInfo = DocList_TableInfo[optTB.id];
	if (tbInfo == null) {
		return document.getElementsByName(fieldName);
	}
	var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
	// 找到正确的对象
	var fn = fieldName.replace(/\[\d+\]/g, "[!{index}]").replace(/\.\d+\./g, ".!{index}.");
	fn = fn.replace(/!\{index\}/g, rowIndex - tbInfo.firstIndex);
	fields = $(optTR).find('[name="'+fn+'"]');
	if (fields.length > 0) {
		var r = [];
		fields.each(function() {r.push(this);});
		return r;
	}
	return null;
}

/***********************************************
功能：明细表下载模板（excel） (表单明细表已不再用到这个方法)
参数：
	optTB：
		必选，明细表表id或者明细表对象 例如："TABLE_DocList"
	itemName：
		必选，明细表字段名 例如："fdItems"
	modelName：
		必选，当前完整的模块名 例如："com.landray.kmss.km.asset.model.KmAssetApplyStock"
	isXform:
		可选：是否是表单的明细表 例如："1"或者"true"
	fieldNameArray:
		可选：业务模块，数组类型，元素是列的字段名，可以自行设置那些列需要导出，例如：['fdTotalMoney','fdStockMatter',...]
返回：excel模板
***********************************************/
function DocList_downLoadExcel(optTB,itemName,modelName,isXform,fieldNameArray){
	if(optTB==null)
		optTB = DocListFunc_GetParentByTagName("TABLE");
	else if(typeof(optTB)=="string")
		optTB = document.getElementById(optTB);
	var propertyName = [];
	if(isXform && ( isXform == '1' || isXform =='true')){
		isXform = 'true';
	}else{
		isXform = 'false';
	}
	var fieldNames;
	if(fieldNameArray){
		fieldNames = fieldNameArray;
	}else{
		//得到明细表字段名，用于数据字典的查找	
		//得到标准行的所有字段的name（过滤过的，是去掉XXX[index].之后的）
		var tbObj = DocList_TableInfo[optTB.id];
		fieldNames = tbObj.fieldNames;	 	
		for(var i = 0 ; i < fieldNames.length; i++){
			var fdFiledId = DocList_parseProName(itemName,fieldNames[i],isXform);
			propertyName.push(fdFiledId);
		}
	}
	
	//去除相同的项
	propertyName = DocList_promiseUnique(propertyName);
	//把modelName、明细表字段名、需要下载的字段的name传到后台处理
	var url = Com_Parameter.ContextPath + "sys/transport/detailTableExport.do?method=deTableExportTemplate&modelName="+modelName+"&itemName="+itemName+"&propertyName="+propertyName+"&isXform="+isXform;	
	Com_OpenWindow(url,"_parent");
}

/***********************************************
功能：把数组里面相同的项删除
参数：
	array：
		必选，原来数组
返回：删除相同项之后的数组
***********************************************/
function DocList_promiseUnique(array){
	var result = [], hash = {},elem;
	for (var i = 0; i < array.length; i++) {
		if(array[i]){
			elem = array[i];
			if (!hash[elem]) {
				result.push(elem);
				hash[elem] = true;
			}
		}	
	}
	return result;
}

/***********************************************
功能：解析字段名，只取后面部分的字段名
参数：
	itemName：
		必选，明细表字段名，例如：kmImeetingAgendaForms
	proName：
		必选，明细表里面的字段名，例如：kmImeetingAgendaForms[${vstatus.index}].attachmentName
	isXform:
		可选：是否是表单的明细表 例如："1"或者"true"
返回：解析后的字段名
***********************************************/
function DocList_parseProName(itemName,proName,isXform){
	
	if(isXform && isXform == 'true'){
		var newItem = 'extendDataFormInfo.value('+itemName+'.!{index}.fd_';
		if(proName.lastIndexOf(newItem) > -1){
			return 'fd_'+proName.replace(newItem,'').replace(')','');
		}
	}else if(proName.lastIndexOf(itemName) > -1){
		return proName.substring(proName.lastIndexOf('.')+1);
	}
}

/***********************************************
功能：明细表导导出excel（view页面，数据从后端取）
参数：
	fdId：
		必选，文档ID 例如："15c0b5e3d40318a21b350d446f5804d9"
	itemName：
		必选，form里面的明细表字段名 例如："fdItems"
	modelName：
		必选，完整的模块名 例如："com.landray.kmss.km.asset.model.KmAssetApplyStock"
	isXform:
		可选：是否是表单的明细表 例如："1"或者"true"
	fieldNameArray:
		可选：业务模块，数组类型，元素是列的字段名，可以自行设置那些列需要导出，例如：['fdTotalMoney','fdStockMatter',...]
返回：null
***********************************************/
function DocList_exportExcel(fdId,itemName,modelName,isXform,fieldNameArray){
	if(typeof(fdId) == 'undefined'){
		return false;
	}
	if(isXform && ( isXform == '1' || isXform =='true')){
		isXform = 'true';
	}else{
		isXform = 'false';
	}
	//通过xformflag获取明细表里面的控件ID
	var fieldArray = [];
	if(fieldNameArray){
		fieldArray = fieldNameArray;
	}else{
		fieldArray = DocList_GetDetailsTableXformflag(null,null,false);	
	}
	if(fieldArray.length == 0){
		alert("当前明细表不含可供导出的数据！");
		return;
	}
	var url = Com_Parameter.ContextPath + "sys/transport/detailTableExport.do?method=detailTableExportData";
	var params = {
			"fdId":fdId,
			"modelName":modelName,
			"itemName":itemName,
			"isXform":isXform,
			"field":JSON.stringify(fieldArray)
	};
	DocList_PostForm(url,params,'_parent');
}

/***********************************************
功能：明细表导导出excel（edit页面，数据从前端取）暂只支持表单
参数：
	optTB：
		必选，参考对象 例如："TABLE_DocList"
	itemName：
		必选，form里面的明细表字段名 例如："fdItems"
	modelName：
		必选，完整的模块名 例如："com.landray.kmss.km.asset.model.KmAssetApplyStock"，表单的是fileName
	isXform:
		可选：是否是表单的明细表 例如："1"或者"true"
	fieldNameArray:
		可选：业务模块，数组类型，元素是列的字段名，可以自行设置那些列需要导出，例如：['fdTotalMoney','fdStockMatter',...]
返回：null
***********************************************/
function DocList_exportExcelInEdit(optTB,itemName,modelName,isXform,fieldNameArray){
	if(optTB==null)
		optTB = DocListFunc_GetParentByTagName("TABLE");
	else if(typeof(optTB)=="string")
		optTB = document.getElementById(optTB);
	if(isXform && ( isXform == '1' || isXform =='true')){
		isXform = 'true';
	}else{
		isXform = 'false';
	}
	//判断明细表里面是否有数据
	var rows = $("input[name='DocList_Selected']:checked",optTB);
	if(rows.size() == 0){
		alert(Data_GetResourceString('page.noSelect'));
		return;
	}
	var propertyName = [];
	//如果是自定义表单
	if(isXform == 'true'){
		//不需要导出的控件类型
		var notExportControlType = ['xform_relation_attachment','divcontrol','xform_validator'];
		var exportDataJSON = {};
		//需要导出的数据组和ID数组
		var exportDatasArray = []; 
		var exportControlIds = [];
		//收集数据
		//遍历所有行
		for(var i = 0;i < rows.length;i++){
			var exportDataArray = [];
			var row = DocListFunc_GetParentByTagName('TR',rows[i]);
			//遍历行里面每个xformflag
			var xformFlags = $(row).find('xformflag');
			for(var j = 0;j < xformFlags.length;j++){
				var xformFlag = xformFlags[j];
				var controlType = $(xformFlag).attr("flagtype");
				if(notExportControlType.indexOf(controlType) > -1){
					continue;
				}
				//控件处理
				var control = DocList_XformHandleControl(controlType,$(xformFlag));
				if(control){
					exportDataArray.push(control);	
				}
			}
			exportDatasArray.push(exportDataArray);
		}
		//收集控件ID
		if(exportDatasArray.length > 0){
			var tempArray = exportDatasArray[0];
			if(tempArray.length == 0){
				alert("当前明细表不含可供导出的数据");
				return;	
			}
			for(var i = 0 ;i < tempArray.length;i++){
				exportControlIds.push(tempArray[i].id);
			}
		}
		exportDataJSON.data = exportDatasArray;
		exportDataJSON.ids = exportControlIds;
		var url = Com_Parameter.ContextPath + "sys/transport/detailTableExport.do?method=detailTableExportDataInEdit";
		var params = {
			'export':JSON.stringify(exportDataJSON),
			'itemName':itemName,
			'fileName':modelName,
			'isXform':isXform
		};
		DocList_PostForm(url,params,'_parent');
	}
}

/*
 * 以表单的形式提交数据
 * */
function DocList_PostForm(URL,PARAMTERS,target){
	//创建form表单
    var temp_form = document.createElement("form");
    temp_form.action = URL;
    temp_form.target = target;
    temp_form.method = "post";
    temp_form.style.display = "none";
    //添加参数
    for (var item in PARAMTERS) {
        var opt = document.createElement("textarea");
        opt.name = item;
        opt.value = PARAMTERS[item];
        temp_form.appendChild(opt);
    }
    document.body.appendChild(temp_form);
    //提交数据
    temp_form.submit();
    $(temp_form).remove();
}

/*
 * 处理表单控件的输入
 * */
function DocList_XformHandleControl(type,xformFlag){
	if(type){
		var control = {type:type,value:''};
		var name = xformFlag.attr('flagid');
		if(name == null){
			return;
		}
		control.id = name.substring(name.lastIndexOf('.') + 1); 
		var checkedControls = ['xform_radio','xform_checkbox','xform_relation_radio','xform_relation_checkbox'];//单选、多选，动态单选、多选
		var selectedControls = ['xform_select','xform_relation_select'];//兼容动态下拉
		//地址本
		if(type == 'xform_address' || type == 'xform_new_address'){
			var val = xformFlag.find("[name*='"+ control.id +".name']").val();
			if(val){
				control.value += val;	
			}
		}else if(checkedControls.indexOf(type) > -1){
			var options = xformFlag.find("input[name*='"+ control.id +"']:checked");
			for(var i = 0;i < options.length;i++){
				control.value += $(options[i]).closest('label').text().replace(/\n/g,'') + ";";	
			}
			if(control.value.length > 0){
				control.value = control.value.substring(0,control.value.length - 1);
			}
		}else if(selectedControls.indexOf(type) > -1){
			var option = xformFlag.find("select[name*='"+ control.id +"'] option:selected");
			control.value = option.text();
		}else{
			var input = xformFlag.find("[name*='"+ control.id +"']");
			if(input.length > 0){
				control.value += input.val();	
			}
		}		
		return control;
	}
}

/*
 * 通过xformflag标签获取明细表里面的控件ID
 * 
 * isFilter : 是否需要过滤没有可供输入的表单元素区域
 */
function DocList_GetDetailsTableXformflag(optTB,tempRow,isFilter){
	if(optTB == null)
		optTB = DocListFunc_GetParentByTagName("TABLE");
	else if(typeof(optTB)=="string")
		optTB = document.getElementById(optTB);
	//获取一个标准行
	if(tempRow == null){
		var $dataRow = $(optTB).find("[type='templateRow']");
		if($dataRow.length > 0){
			tempRow = $dataRow[0];
		}
	}
	var fieldArray = [];
	if(tempRow){
		//不需要导出的控件类型
		var notExportControlType = ['xform_relation_attachment','divcontrol','xform_validator'];
		$(tempRow).find("xformflag").each(function(){
			var controlType = $(this).attr("flagtype");
			if(jQuery.inArray(controlType, notExportControlType) > -1){
				return;
			}
			// 若没有任何可供输入的元素，不需要
			if(isFilter){
				var isAddress = $(this).attr("_xform_type")=="address";
				var formElement = $(this).find("input[type!='hidden'],textarea,select");
				if(formElement.length == 0){
					// 没有表单元素就返回
					return;
				}else{
					// 如果存在表单元素，校验是否存在name
					var hasInput = false;
					for(var i = 0;i < formElement.length;i++){
						var e = formElement[i];
						if($(e).attr("name") ){
							// 下拉菜单没有只读属性
							//地址本无论是否只读，都会设置readOnly，故无法通过readOnly判断
							if(isAddress){
								if($(e).attr('_edit')=='true'){
									hasInput = true;
									break;
								}
							}else{
								if($(e).css('display') != 'none' && ($(e).attr('readOnly') == null || $(e).attr('readOnly') == false)){
									hasInput = true;
									break;
								}
							}
						}
					}
					if(!hasInput){
						return;
					}
				}
			}
			if($(this).attr('flagid')){
				var fieldJSON = {};
				var fieldId = $(this).attr('flagid');
				fieldId = fieldId.substring(fieldId.lastIndexOf('.') + 1);
				fieldJSON.fieldId = fieldId;
				fieldJSON.fieldType = $(this).attr('flagtype'); 
				fieldArray.push(fieldJSON);
			}
		});
	}
	return fieldArray;
}

/***********************************************
功能：明细表导入excel
参数：
	optTB：
		必选，参考对象 例如："TABLE_DocList"
	itemName：
		必选，form里面的明细表字段名 例如："fdItems"
	modelName：
		必选，完整的模块名 例如："com.landray.kmss.km.asset.model.KmAssetApplyStock"，表单的是fileName
	isXform:
		可选：是否是表单的明细表 例如："1"或者"true"
	fieldNameArray:
		可选：业务模块，数组类型，元素是列的字段名，可以自行设置那些列需要导出，例如：['fdTotalMoney','fdStockMatter',...]
返回：null
***********************************************/
function DocList_importExcel(optTB,itemName,modelName,isXform,fieldNameArray){
	if(optTB==null)
		optTB = DocListFunc_GetParentByTagName("TABLE");
	else if(typeof(optTB)=="string")
		optTB = document.getElementById(optTB);
	if(isXform && ( isXform == '1' || isXform =='true')){
		isXform = 'true';
	}else{
		isXform = 'false';
	}
	var propertyName = [];
	//新增一个基准行dom，以免导入的时候没有一条数据，后面获取的数据不好校验，这个temp相当于一个基准行的dom对象
 	var temp = DocList_AddRow(optTB.id);
 	//新增的这一行，有可能包含附件，如果有附件的话，必须先等附件初始化完再删除，因为附件有个setTimeout，故删除之前需要隐藏这个虚拟行 by 朱国荣 2016-09-03
 	if(temp){
 		temp.style.display = 'none';
 	}
 	var dataJson = [];
	//如果是自定义表单
	if(isXform == 'true'){
		dataJson = DocList_GetDetailsTableXformflag(optTB,temp,true);
		//模板下载不支持导出的控件
		var unsuportedControl = ['xform_chinavalue','xform_calculate','xform_relation_radio','xform_relation_checkbox','xform_relation_select','xform_relation_choose'];
		for(var i = 0;i < dataJson.length;i++){
			var data = dataJson[i];
			// IE8不支持数组的indexof by zhugr 2017-08-26
			if($.inArray(data.fieldType,unsuportedControl) > -1){
				continue;
			}
			propertyName.push(data.fieldId);
		}
	}else{
		if(fieldNameArray != null){
			propertyName = fieldNameArray;
		}else{
			//以下是普通业务模块的Excel导出
			//得到明细表字段名，用于数据字典的查找	
			var tbObj = DocList_TableInfo[optTB.id];
			var fieldNames = tbObj.fieldNames;
			
			for(var i = 0 ; i < fieldNames.length; i++){
				var parseField = DocList_parseProName(itemName,fieldNames[i],isXform);
				if(parseField && parseField != undefined && parseField != ''){
					var $fdFiled = $(temp).find("[name*='"+parseField+"']");
					//特殊控件处理
					if($fdFiled && $fdFiled.length == 1){
						//大小写控件不需要匹配
						if($fdFiled.attr('chinavalue') && $fdFiled.attr('chinavalue') == 'true'){
							continue;
						}
						//计算控件不需要匹配
						if($fdFiled.attr('calculation') && $fdFiled.attr('calculation') == 'true'){
							continue;
						}			
					}
					propertyName.push(parseField);
				}		
			}		
		}
		//去除相同的项
		propertyName = DocList_promiseUnique(propertyName);
		//增加type属性	
		for(var i = 0; i < propertyName.length; i++){
			var $dom = $(temp).find("[name*='"+propertyName[i]+"'][type!='hidden']");
			if($dom[0] && $dom[0].type){
				dataJson.push({'fieldType':$dom[0].type,'fieldId':propertyName[i]});
			}
		}
	}
	
	//删除自己增加的dom行
	//新增的这一行，有可能包含附件，如果有附件的话，必须先等附件初始化完再删除，因为附件有个setTimeout，附件的延时是300，故这里稍微设得长一点就可以了 by 朱国荣 2016-09-03
	window.setTimeout(function(){
		DocList_DeleteRow(temp);
	},310);
	
	//给弹出框调用
	window._paramJsonExcelUpload = {
		'modelName' : modelName,
		'itemName' : itemName,
		'field' : JSON.stringify(dataJson),
		'propertyName' : propertyName,
		'optTBId' : optTB.id,
		'maxLimitedNum' : '200',//限制最大输入数量
		//'validateType' : 'false',//用于是否需要导入的时候校验属性的必填、数据类型等等
		'isXform' : isXform
	};
	
	var url = "/sys/transport/sys_transport_xform/SysTransportImportDetailTable_upload.jsp";
	var height = document.documentElement.clientHeight * 0.5;
	var width = document.documentElement.clientWidth * 0.6;
	seajs.use(['lui/dialog'], function(dialog) {
		dialog.iframe(url,Data_GetResourceString('sys-transport:sysTransport.import.dataImport'),null,{width:width,height : height,close:false});
	});
}

/***********************************************
 * 功能：导入单条数据
 * 
 ***********************************************/
function DocList_importSingleData(optTBId,jsonArray,fieldNames,temp,isXform){
	var fieldValues = new Object();	
	var tbObj = DocList_TableInfo[optTBId];
	if(isXform && isXform != null && isXform+'' == 'true'){
		//当明细表一条数据都没有的时候，需要增加一条数据给下面的$("[name*='"+fileName+"']")调用,后面会删除这条数据			
		for(var fileName in jsonArray){
			for(var j = 0 ;j < fieldNames.length; j++){
				//这里用模糊匹配，因为id一般是独有的，而且精确匹配麻烦
				if(fieldNames[j].lastIndexOf(fileName) > -1){
					var fieldObj = $(temp).find("[name*='"+fileName+"']");		
					//特殊字段的处理						
					if(fileName.indexOf(".name") == -1){ //地址本的name字段是只读的，在这里需排除							
						//输入的dom为只读时，不处理;输入的dom为计算控件，不处理;
						if(fieldObj && fieldObj[0] && fieldObj[0].readOnly == true && ( fieldObj[0].getAttribute("calculation") != "true" || fieldObj[0].getAttribute("autocalculate") != "true") ){
							break;
						}
					}
					//做个为null判断，以免文本域直接写入“null”
					if(jsonArray[fileName] != null){
						fieldValues[fieldNames[j]] = jsonArray[fileName];
					}						
					//由于多选控件的字段有两个需要输入的，这个先判断是否是第一个,因为第一个一般是以_开始的，故用此来判断
					if(fieldNames[j].indexOf("_") == 0){
						continue;
					}
					break;
				}
			}
		}
		DocList_AddRow(optTBId,null,fieldValues);				
	}else{
		for(var fileName in jsonArray){
			//这里或许应该弄个特殊字段的处理接口
			for(var j = 0 ;j < fieldNames.length; j++){
				if(fieldNames[j].lastIndexOf(fileName) > -1){
					//这里需要用精确匹配，不然容易重复
					if(fieldNames[j].substring(fieldNames[j].lastIndexOf(".")+1) == fileName){
						/*//输入的dom为只读时，不处理
						var fieldObj = $("[name*="+fileName+"]");
						if(fieldObj[0] && fieldObj[0].readOnly == true){
							break;
						}*/
						//做个为null判断，以免文本域直接写入“null”
						if(jsonArray[fileName] && jsonArray[fileName] != null){
							fieldValues[fieldNames[j]] = jsonArray[fileName];
						}
						break
					}
				}
			}
		}
		DocList_AddRow(optTBId,null,fieldValues);
	}
	
}

/***********************************************
* 功能：递归导入明细表数据
* 
***********************************************/
function DocList_loopImportData(optTBId,jsonData,fieldNames,index,lastIndex,temp,isXform){
	if(index < lastIndex){
		var jsonArray = jsonData[index];												
		index++;
		DocList_importSingleData(optTBId,jsonArray,fieldNames,temp,isXform);						
		DocList_loopImportData(optTBId,jsonData,fieldNames,index,lastIndex,temp,isXform);
	}else{		
		//数据全部输入之后，调用XForm_CalculationDoExecutorAll计算所有计算控件值	
		if (typeof XForm_CalculationDoExecutorAll != 'undefined' && XForm_CalculationDoExecutorAll instanceof Function) {  
			XForm_CalculationDoExecutorAll();
		}
		return;
	}
}

/**************************************************
 * 功能：递归每次导入指定数量的行
 *  
 ************************************************/
function DocList_loopImportDataByNum(optTBId,jsonData,fieldNames,subIframe,uploadProcess,maxImportNum,startIndex,temp,isXform){	
		if(startIndex + maxImportNum < jsonData.length){
			//导入指定数量的数据
			if(_uploadNewProgress || _uploadNewProgress==true){ 
				DocList_loopImportDataNew(optTBId,jsonData,fieldNames,startIndex,startIndex + maxImportNum,temp,isXform);
			}else{
				DocList_loopImportData(optTBId,jsonData,fieldNames,startIndex,startIndex + maxImportNum,temp,isXform);
			}
			//获取进度百分比
			var processWidth = parseInt((startIndex + maxImportNum)/jsonData.length*100);
			//更新进度条
			$(uploadProcess).css("width",processWidth + "%");
			//阻塞js引擎，开始渲染页面
			window.setTimeout(function(){
				//当页面点击关闭按钮的时候，subIframe.contentWindow为空，则不再进行导入
				if(subIframe.contentWindow){
					startIndex += maxImportNum;
					DocList_loopImportDataByNum(optTBId,jsonData,fieldNames,subIframe,uploadProcess,maxImportNum,startIndex,temp,isXform);
				}else{
					//新增的这一行，有可能包含附件，如果有附件的话，必须先等附件初始化完再删除，因为附件有个setTimeout，附件的延时是300，故这里稍微设得长一点就可以了 by 朱国荣 2016-09-03
					window.setTimeout(function(){
						DocList_DeleteRow(temp);
						temp = null;
					},310);
					
					return;
				}
				
			},0);
		}else{
			if(_uploadNewProgress || _uploadNewProgress==true){
				DocList_loopImportDataNew(optTBId,jsonData,fieldNames,startIndex,jsonData.length,temp,isXform);
			}else{
				DocList_loopImportData(optTBId,jsonData,fieldNames,startIndex,jsonData.length,temp,isXform);
			}
			if(subIframe && subIframe != null){
				subIframe.contentDocument.getElementById("importSuccessed").style.display='';
				subIframe.contentWindow.importExcelStatus = "finishImported";			
			}	
			//新增的这一行，有可能包含附件，如果有附件的话，必须先等附件初始化完再删除，因为附件有个setTimeout，附件的延时是300，故这里稍微设得长一点就可以了 by 朱国荣 2016-09-03
			window.setTimeout(function(){
				DocList_DeleteRow(temp);
				temp = null;
			},310);			
			//更新进度条
			$(uploadProcess).css("width","100%");		
		}
}

/***********************************************
功能：明细表导入数据
参数：
	optTBId：
		必选，明细表表id
	contentList：
		必选，json数据，格式为【【{name：信息学}，{id：323244}】【{name：发动机}，{id：45435}】】
	isXform：
		可选，表示是否为表单的明细表
返回：HTML元素，找不到则返回null
***********************************************/
function DocList_importData(optTBId,contentList,isXform, maxLimitedNum){
	var jsonData = eval(contentList);
	var tbObj = DocList_TableInfo[optTBId];
	var subIframe;
	//获得弹出窗口的document
	if(window.frames["dialog_iframe"] && window.frames["dialog_iframe"].getElementsByTagName("iframe")[0]){
		subIframe = window.frames["dialog_iframe"].getElementsByTagName("iframe")[0];
	}
	var importType = subIframe.contentWindow.$("input[name='importType']:checked").val();
	if(importType && importType == 'update'){
		$(tbObj.DOMElement).children().children("[invalidrow!='true']").each(function(){
			DocList_DeleteRow_ClearLast(this);
		});			
	}
	//判断导入数据之后的记录条数是否有超过200条，如果超过就提醒
	var limitImportNum = 200;
	if(maxLimitedNum){
		limitImportNum = maxLimitedNum;
	}
	//单次导入强制只能导入200条
	if(jsonData.length >= limitImportNum){
		if(!confirm("系统检测到导入的文档数据行超过" + limitImportNum + "行，由于明细表性能问题，单次导入最多为" + limitImportNum + "条！你确定需要导入吗？")){
			subIframe.contentWindow.importExcelStatus = "unUpload";
			return false;
		}
	}else{
		//导入之后的总条数
		var importedNum = tbObj.lastIndex + jsonData.length - 1;
		if(importedNum > limitImportNum){
			//如果取消就不导入
			if(!confirm(Data_GetResourceString("sys-transport:sysTransport.title.overDetailTableMaxNum"))){
				subIframe.contentWindow.importExcelStatus = "unUpload";
				return false;
			};
		}
	}	
	var fieldNames = tbObj.fieldNames;
	fieldNames = DocList_promiseUnique(fieldNames);
	if(jsonData == '' || jsonData == null){
		return
	}
		
	var uploadProcess;
	if(subIframe && subIframe != null){
		uploadProcess = subIframe.contentDocument.getElementById("bar");
		subIframe.contentDocument.getElementById("resultTr").style.display = '';
		subIframe.contentDocument.getElementById("progressBar").style.display = '';
		subIframe.contentDocument.getElementById("progress").style.display = '';
	}
	try{
		//每次导入maxImportNum数据就停止渲染一次页面，以免页面点击无响应
		var maxImportNum = 10;	
		//新增一个基准行dom，以免导入的时候没有一条数据，后面获取的数据不好校验，这个temp相当于一个基准行的dom对象
	 	var temp = DocList_AddRow(optTBId);
	 	//新增的这一行，有可能包含附件，如果有附件的话，必须先等附件初始化完再删除，因为附件有个setTimeout，故删除之前需要隐藏这个虚拟行 by 朱国荣 2016-09-03
	 	if(temp){
	 		temp.style.display = 'none';
	 	}
		DocList_loopImportDataByNum(optTBId,jsonData,fieldNames,subIframe,uploadProcess,maxImportNum,0,temp,isXform);
		subIframe.contentDocument.getElementById("continueImport").style.display = 'none';
	}catch(e){
		subIframe.contentWindow.importExcelStatus = "unUpload";
		return;
	}
}

//构建用于明细表校验的定位input
function DocList_Xform_DetailsTable_AddInputPosition(tableId,validateName,formValidation){
	if(tableId == null){
		return false;
	}
	if(formValidation == null){
		formValidation = $KMSSValidation(document.forms['${param.formName}']);
	}
	var tbDom = document.getElementById(tableId);
	var listendDomId = tableId + "_position";
	var tipMessage = Data_GetResourceString('sys-xform:sysForm.detailsTable.tipMessage');
	var inputTemplate = "<input id='" + listendDomId + "' type='text' style='display:table-cell;width:0px;height:1px;border:0px;' subject='" + tipMessage + "' validate='" + validateName + "'/>";
	$(tbDom).after(inputTemplate);
	//一开始的时候就需要设置input的值，以免在重新编辑页面，input没有值
	DocList_Xform_DetailsTable_SetInputValue($('#'+listendDomId),tableId);
	//增加监听
	DocList_Xform_DetailsTable_ValidateListen(listendDomId,formValidation,tableId);
}

//监听明细表的校验
function DocList_Xform_DetailsTable_ValidateListen(listendDomId,formValidation,tableId){
	var listened;
	if(listendDomId == null || formValidation == null){
		return;
	}
	if(tableId == null){
		listened = 'table[showStatisticRow]';
	}else{
		listened = '#' + tableId;
	}
	var $listen = $('#' + listendDomId);
	//添加行、删除行触发
	$(document).on('table-delete-new table-add-new',listened,function(e,obj){
		if(obj && obj.table){
			DocList_Xform_DetailsTable_SetInputValue($listen,obj.table.id);
			//执行校验
			formValidation.validateElement($listen[0]);
		}
	});
}

function DocList_Xform_DetailsTable_SetInputValue($input,tbId){
	var tbInfo = DocList_TableInfo[tbId];
	// IE8下，初始化的时候tbInfo还是空 by zhugr 2017-08-26
	if(tbInfo){
		var curRowNum = tbInfo.lastIndex - tbInfo.firstIndex;
		if(curRowNum == 0){
			$input.val('');
			return;
		}
	}
	
	$input.val(1);
}

//=============================以下函数为内部函数，普通模块请勿调用==============================
/***********************************************
功能：获取父级对象中的指定tagName的对象
参数：
	tagName：必选，页面对象的tagName属性
	obj：可选，默认取当前事件触发的对象
返回：找到的对象
***********************************************/
function DocListFunc_GetParentByTagName(tagName, obj){
	if(obj==null){
		if(Com_Parameter.IE)
			obj = event.srcElement;
		else
			obj = Com_GetEventObject().target;
	}
	for(; obj!=null; obj = obj.parentNode)
		if(obj.tagName == tagName)
			return obj;
}

function DocListFunc_ReplaceIndex(htmlCode, index,addtype){
	if(htmlCode==null)
		return "";
	htmlCode = ""+htmlCode;
	if(addtype=="child"){
		return htmlCode.replace(/!\{indexChild\}/g, index);
	}
	return htmlCode.replace(/!\{index\}/g, index);
}

/**
 * @param tbInfo
 * @param rowInedx 更新的行
 * @param index 更新的索引
 * @returns
 */
function DocListFunc_RefreshIndex(tbInfo,rowIndex, index){
	if(index == null){
		index = rowIndex;
	}
	for (var n = 0; n < tbInfo.cells.length; n ++) {
		if (tbInfo.cells[n].isIndex) {
			var htmlCode = tbInfo.cells[n].innerHTML;
			if(htmlCode==null || htmlCode==""){
				htmlCode = ''+index;
			}else{
				htmlCode = DocListFunc_ReplaceIndex(htmlCode, index);
				htmlCode =  htmlCode.replace("{1}", index );//自定义表单明细表序号
			}
			tbInfo.DOMElement.rows[rowIndex].cells[n].innerHTML = htmlCode;
		}
	}
	DocListFunc_RefreshFieldIndex(tbInfo, rowIndex, "INPUT");
	DocListFunc_RefreshFieldIndex(tbInfo, rowIndex, "TEXTAREA");
	DocListFunc_RefreshFieldIndex(tbInfo, rowIndex, "SELECT");
	DocListFunc_RefreshFormFieldIndex(tbInfo, rowIndex, "DIV", "id");//处理cdp生成代码的影响
	DocListFunc_RefreshFormFieldIndex(tbInfo, rowIndex, "xformflag", "flagid");
	// id不更新的话，可能以后有问题
	DocListFunc_RefreshFormFieldIndex(tbInfo, rowIndex, "xformflag", "id");
	DocListFunc_RefreshFormFieldIndex(tbInfo, rowIndex, "DIV", "__id");
	DocListFunc_RefreshFormFieldIndex(tbInfo, rowIndex, "DIV", "__name");
}

function DocListFunc_RefreshFieldIndex(tbInfo, index, tagName){
	var optTR = tbInfo.DOMElement.rows[index];
	var fields = optTR.getElementsByTagName(tagName);
	var fieldAttrReplaceIndex = function(field,attrName,idx) {
		if($(field).attr(attrName)){
			// 更换索引
			var _attNameValue = $(field).attr(attrName);
			var fieldName = _attNameValue.replace(/\[\d+\]/g, "[!{index}]");
			fieldName = fieldName.replace(/\.\d+\./g, ".!{index}.");
			fieldName = fieldName.replace(/!\{index\}/g, idx);
			$(field).attr(attrName,fieldName);
		}
	};
	for(var i=0; i<fields.length; i++){
		if(fields[i].name){
			var fieldName = fields[i].name.replace(/\[\d+\]/g, "[!{index}]");
			fieldName = fieldName.replace(/\.\d+\./g, ".!{index}.");
			var j = Com_ArrayGetIndex(tbInfo.fieldFormatNames, fieldName);
			if(j>-1 && fields[i].name!=fieldName){
				fieldName = tbInfo.fieldFormatNames[j].replace(/!\{index\}/g, index-tbInfo.firstIndex);
				if(document.documentMode !=null && document.documentMode <= 7)
					fields[i].outerHTML = fields[i].outerHTML.replace("name=" + fields[i].name, "name="+fieldName);
				else
					fields[i].name = fieldName;
				//新地址本
				/*if(Address_isNewAddress(fields[i]) && window.$) {
					var idFieldName = fieldName.replace('.name','.id');
					//fields[i].setAttribute('xform-name',fieldName);
					$("[xform-name='mf_"+fields[i].name+"']").data('propertyid',idFieldName).attr('data-propertyid',idFieldName)
					.data('propertyname',fieldName).attr('data-propertyname',fieldName).attr('xform-name','mf_'+fieldName);
				}*/
			}
		}
		// 地址本改变下标
		if(fields[i].getAttribute("xform-name") != null) {
			fieldAttrReplaceIndex(fields[i],'xform-name',index-tbInfo.firstIndex);
			fieldAttrReplaceIndex(fields[i],'data-propertyid',index-tbInfo.firstIndex);
			fieldAttrReplaceIndex(fields[i],'data-propertyname',index-tbInfo.firstIndex);
			fieldAttrReplaceIndex(fields[i],'callback',index-tbInfo.firstIndex);
		}
	}
}

/**
 * 更新表单的xformflag标签的索引，仅限表单使用
 * @param tbInfo
 * @param index
 * @param tagName
 * @param attr
 * @returns
 */
function DocListFunc_RefreshFormFieldIndex(tbInfo, index, tagName, attr){
	var optTR = tbInfo.DOMElement.rows[index];
	var fields = optTR.getElementsByTagName(tagName);
	for(var i=0; i<fields.length; i++){
		if($(fields[i]).attr(attr)){
			var fieldName = $(fields[i]).attr(attr).replace(/\[\d+\]/g, "[!{index}]");
			fieldName = fieldName.replace(/\.\d+\./g, ".!{index}.");
			fieldName = fieldName.replace(/!\{index\}/g, index-tbInfo.firstIndex);
			$(fields[i]).attr(attr,fieldName);
		}
	}
}

function DocListFunc_ArrayGetIndex(arr, content){
	for(var i=0; i<arr.length; i++)
		if(content.indexOf(arr[i]) > -1)
			return i;
	return -1;
}

function DocListFunc_Init(){
	var i, j, k, tbObj, trObj, tbInfo, att, fields;
	//表格循环
	for(i=0; i<DocList_Info.length; i++){
		tbObj = document.getElementById(DocList_Info[i]);
		if(tbObj==null || DocList_TableInfo[tbObj.id] != null)//表格不存在,或者表格以初始化后不执行
			continue;
		tbInfo = new Object;
		tbInfo.DOMElement = tbObj;
		tbInfo.fieldNames = new Array;
		tbInfo.fieldFormatNames = new Array;
		tbInfo.cells = new Array;
		tbInfo.tbdraggable = tbObj.getAttribute('tbdraggable')=='true'?true:false;

		//表格行循环
		for(j=0; j<tbObj.rows.length; j++){
			trObj = tbObj.rows[j];
			att = trObj.getAttribute("KMSS_IsReferRow")|trObj.getAttribute("EKP_IsReferRow")|trObj.getAttribute("IsReferRow");
			if(att=="1" || att=="true"){
				tbInfo.firstIndex = j;
				tbInfo.lastIndex = j;
				tbInfo.className = trObj.className;
				for(k=0; k<trObj.cells.length; k++){
					tbInfo.cells[k] = new Object;
					tbInfo.cells[k].innerHTML = trObj.cells[k].innerHTML;
					tbInfo.cells[k].className = trObj.cells[k].className;
					tbInfo.cells[k].align = trObj.cells[k].align;
					tbInfo.cells[k].vAlign = trObj.cells[k].vAlign;
					att = trObj.cells[k].getAttribute("KMSS_IsRowIndex");
					tbInfo.cells[k].isIndex = (att=="1" || att=="true");
				}
				DocListFunc_AddReferFields(tbInfo, trObj, "INPUT");
				DocListFunc_AddReferFields(tbInfo, trObj, "TEXTAREA");
				DocListFunc_AddReferFields(tbInfo, trObj, "SELECT");
				tbObj.deleteRow(j);
				Com_SetOuterHTML(trObj, "");
				break;
			}
		}
		for(; j<tbObj.rows.length; j++){
			att = tbObj.rows[j].getAttribute("KMSS_IsContentRow")|tbObj.rows[j].getAttribute("EKP_IsContentRow");
			if(att!="1" && att!="true")
				break;
			tbInfo.lastIndex++;
			//DocList_ResetFieldWidth(tbObj.rows[j]);重置宽度，会导致百分比失效，因此去掉。---modify by miaogr
		}
		DocList_TableInfo[tbObj.id] = tbInfo;
		
		//拖拽能力
		if(window.doclistdnd && tbInfo.tbdraggable){
			window.doclistdnd.build(tbInfo.DOMElement,{
				onDrop:function(currentTable,dragObject){
					DocListFunc_StoreCheckedProp(currentTable);
					var __tbInfo = DocList_TableInfo[currentTable.id];
					var index = 1;
					for(var i = __tbInfo.firstIndex; i<__tbInfo.lastIndex; i++)
						DocListFunc_RefreshIndex(__tbInfo, i, index++);
					DocListFunc_ReloadCheckedProp(currentTable);
				}
			});
		}
		//DocList_Compatible(tbInfo);
		DocList_ResetOptCol(tbInfo);
		//增加明细表初始化事件 作者 曹映辉 #日期 2016年5月16日
		$(tbObj).trigger($.Event("detaillist-init"),tbObj);
	}
	
}

function DocListFunc_AddReferFields(tbInfo, trObj, tagName){
	var fields = trObj.getElementsByTagName(tagName);
	for(var i=0; i<fields.length; i++){
		if(fields[i].name==null || fields[i].name=="")
			continue;
		tbInfo.fieldNames[tbInfo.fieldNames.length] = fields[i].name;
		var formatName = fields[i].name.replace(/\[\d+\]/g, "[!{index}]");
		formatName = formatName.replace(/\.\d+\./g, ".!{index}.");
		tbInfo.fieldFormatNames[tbInfo.fieldFormatNames.length] = formatName;
	}
}



/***********************************************
* 功能：递归导入明细表数据(新版进度条)
* 
***********************************************/



	
 function DocList_loopImportDataNew(optTBId,jsonData,fieldNames,index,lastIndex,temp,isXform){
	 seajs.use(['lui/dialog'],function(dialog){
	if(!window.progress){
		window.progress = dialog.progress(false);
		window.progress.setProgress(0);
	}
	
	if(_uploadProgressflag==true){
		window.progress = dialog.progress(false);
		_uploadProgressflag=false;
		window.progress.setProgress(0);
	}
	if(index < lastIndex){
		var jsonArray = jsonData[index];												
		index++;
		DocList_importSingleData(optTBId,jsonArray,fieldNames,temp,isXform);
		window.progress.setProgress(index,jsonData.length);
		DocList_loopImportDataNew(optTBId,jsonData,fieldNames,index,lastIndex,temp,isXform);
	}else{		
		//数据全部输入之后，调用XForm_CalculationDoExecutorAll计算所有计算控件值	
		if (typeof XForm_CalculationDoExecutorAll != 'undefined' && XForm_CalculationDoExecutorAll instanceof Function) {  
			XForm_CalculationDoExecutorAll();
		}
		if(index == jsonData.length){
		window.progress.setProgress(index,jsonData.length);
		window.progress.hide();
		_uploadProgressflag=true;
		}
		return;
	}
	 });
}



Com_AddEventListener(window, "load", DocListFunc_Init);

//=============================以上函数为内部函数，普通模块请勿调用==============================