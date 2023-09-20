(function(window){
	/**
	 * 打印前表格设计
	 */
	var previewDesign={};
	previewDesign.dash={dashDom:'',isMouseDown:'',actionType:''};//拖拽相关信息
	
	previewDesign._mousedown = function(event){
		//设置拖拽信息
		var dashDom = previewDesign.dash.dashDom=event.target;
		previewDesign.dash.isMouseDown = true;
		if (event.offsetX > dashDom.offsetWidth - 10) {
			//下个节点
			var $nextTd = $(dashDom).next();
			if($nextTd.length==0){//最后节点不允许拖拽
				return;
			}
			//debugger;
			previewDesign.dash.actionType = "horizontal";
			dashDom.oldX = event.clientX || event.originalEvent.clientX; 
			//保存此时的table对象数据
			previewDesign.dash.oldCellsWidthObj = sysPreviewDesign._getDashDomWidthDatas();
			
		}else if(event.offsetY>dashDom.offsetHeight - 10){
			previewDesign.dash.actionType = "vertical";
			dashDom.oldY = event.clientY || event.originalEvent.clientY; 
			dashDom.oldHeight = dashDom.offsetHeight; 
		}else{
			previewDesign.dash.actionType = "";
		}
	 };
	previewDesign._mouseup = function(event){
		//结束宽度调整 
		if(previewDesign.dash.isMouseDown){
			previewDesign.dash.isMouseDown = false;
			$(previewDesign.dash.dashDom).addClass('sysprint_cursor_d').removeClass('sysprint_cursor_e sysprint_cursor_n');
			$(previewDesign.dash.dashDom).css("cursor","");
			previewDesign.dash.oldCellsWidthObj=null;
		}
	 };
	previewDesign._mousemove = function(event){
		//注：event.target可能非td对象，如：span等
		var curObj = event.target;
		if(!previewDesign.dash.isMouseDown){
			if(curObj.nodeName.toUpperCase()!='TD'){
				return;
			}
			var ctrlDom = previewDesign.getPrintDesignElement(curObj);
			//明细表不允许拖拽操作
			if(ctrlDom && $(ctrlDom).attr('fd_type')=='detailsTable' || $(ctrlDom).attr('fd_type')=='process'){
				return;
			}
			if (event.offsetX > curObj.offsetWidth-5){
				if($(curObj).next().length==0){//最后节点不允许拖拽
					return;
				}
				$(curObj).addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n'); 
				$(curObj).css("cursor","");
				previewDesign.dash.actionType = "horizontal"; 
			}else if(event.offsetY >curObj.offsetHeight-5){
				$(curObj).addClass('sysprint_cursor_n').removeClass('sysprint_cursor_e sysprint_cursor_d'); 
				$(curObj).css("cursor","");
				previewDesign.dash.actionType = "vertical";
			}else{ 
				$(curObj).addClass('sysprint_cursor_d').removeClass('sysprint_cursor_e sysprint_cursor_n');
				$(curObj).css("cursor","");
				previewDesign.dash.actionType = "";
			}
			return;
		}

		
		//调整宽度 
		if (previewDesign.dash.isMouseDown) {
			var dashDom = previewDesign.dash.dashDom;
			if (!dashDom){
				return;
			}
			if(previewDesign.dash.actionType=='horizontal'){
				var $curObj = $(dashDom).next();
				if($curObj.length==0){
					return;
				}
				//重新获取当前对象
				curObj = $curObj[0];
				if(curObj.nodeName.toUpperCase()!='TD'){//必须单元格对象
					return;
				}
				var distanceX = (event.clientX ||event.originalEvent.clientX) - dashDom.oldX;
				if(Math.abs(distanceX) <=2){
					//认为拖动>2时有效
					return;
				}
				$(curObj).addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n');
				//当前dashdom单元格的cellIndex
				var dashDomIndex = previewDesign._getDashDomCellIndex();
				var table = previewDesign.getTableObj(dashDom);
	    		if(!table)
	    			return;
				for (var j = 0; j < table.rows.length; j++) {
					var cells = table.rows[j].cells;
					if(dashDomIndex >cells.length-1){
						continue;
					}
					//计算每个cell的宽度
					var oldRowCellWidthObj = sysPreviewDesign._getOldRowCellWidthObj(j);
					var newWidth = oldRowCellWidthObj.curCellWidth + distanceX;
					//下个cell的宽度
					var oldNextCellWidth = oldRowCellWidthObj.nextCellWidth;
					if(!oldNextCellWidth)continue;
					var nextCellWidth = oldRowCellWidthObj.curnextWidth -newWidth;
					
					if(newWidth < 20 || nextCellWidth < 20){
						continue;
					}
					
					var _rowCurCell = sysPreviewDesign._getCellBycolumnIndex(cells,dashDomIndex);
					if(!_rowCurCell){
						continue;
					}
					//下个cell宽度调整
					var nextCellDom = sysPreviewDesign._getCellBycolumnIndex(cells,dashDomIndex+1);
					if(!nextCellDom){
						continue;
					}
					var $_rowCurCell = $(_rowCurCell);
					var $nextCell = $_rowCurCell.next();
					if(!$nextCell || nextCellDom !=$nextCell[0]){
						continue;
					}
					
					//console.log("_mousedown...start to reset newWidth"+j + ":" + newWidth +"nextCellWidth:"+nextCellWidth); 
					if(parseInt(_rowCurCell.colSpan) > 1){
						_rowCurCell.style.width = 'auto';
						$(_rowCurCell).addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n');
						$(_rowCurCell).css("cursor","");
						$(_rowCurCell).removeAttr('width');
						
					}else{
						_rowCurCell.style.width = newWidth +"px";
						$_rowCurCell.addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n');
						$_rowCurCell.css("cursor","");
					}
					
					//console.log("_mousedown....set next cell:" + nextCellIndex);
					if(parseInt(nextCellDom.colSpan) > 1){
					   nextCellDom.style.width='auto';
					   $nextCell.addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n');
					   $nextCell.css("cursor","");
					   $nextCell.removeAttr('width');
					}else{
					   nextCellDom.style.width=nextCellWidth+"px";
					   $nextCell.addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n');
					   $nextCell.css("cursor","");
					   $nextCell.removeAttr('width');
					}
				} 
		    	
			}else if(previewDesign.dash.actionType=='vertical'){
				var table = previewDesign.getTableObj(dashDom);
				var distanceY = (event.clientY ||event.originalEvent.clientY) - dashDom.oldY;
				if(table && $(curObj).parent().index()==table.rows.length-1){
				}else{
					if(Math.abs(distanceY) <=2){
						//认为拖动>2时有效
						return;
					}
				}
				$(curObj).css("cursor","");
				$(curObj).addClass('sysprint_cursor_n').removeClass('sysprint_cursor_e sysprint_cursor_d');
				
				var newHeight = dashDom.oldHeight + ((event.clientY ||event.originalEvent.clientY) - dashDom.oldY);
				var row = dashDom.parentElement;
				
				if(row && row.cells) {
					for (var i = 0;i < row.cells.length;i++) {
						var cell = row.cells[i];
						cell.style.height=newHeight + 'px';
					}
				}
			}else{
			}
		}
	 };
	 
	 
	//获取行对应位置单元格的宽
	 previewDesign._getDashDomWidthDatas = function(){
			//debugger;
			var dashDom = sysPreviewDesign.dash.dashDom;
			var table = sysPreviewDesign.getTableObj(dashDom);
			if(!table || !table.tagName.toUpperCase()=='TABLE'){
				previewDesign.dash.isMouseDown = false;
				$(previewDesign.dash.dashDom).addClass('sysprint_cursor_d').removeClass('sysprint_cursor_e sysprint_cursor_n');
				$(previewDesign.dash.dashDom).css("cursor","");
				return;
			}
			//当前dashdom单元格的cellIndex
			var dashDomIndex = sysPreviewDesign._getDashDomCellIndex();
			var rowCells = [];
			for (var j = 0; j < table.rows.length; j++) {
				var cells = table.rows[j].cells;
				var _rowCurCell = sysPreviewDesign._getCellBycolumnIndex(cells,dashDomIndex);
				var $_rowCurCell = $(_rowCurCell);
				var $nextCell = $_rowCurCell.next();
				var rowCellWidth = {'curCellWidth':$_rowCurCell.outerWidth(),'nextCellWidth':$nextCell.outerWidth(),'curnextWidth':$_rowCurCell.outerWidth()+$nextCell.outerWidth()};
				rowCells.push(rowCellWidth);
			}
			//最后cell宽度改为auto
//			for (var j = 0; j < table.rows.length; j++) {
//				var cells = table.rows[j].cells;
//				var lastCell = cells[cells.length-1];
//				$(lastCell).css('width','auto');
//			}
			return rowCells;
	};
	
	previewDesign._getOldRowCellWidthObj = function(rowIndex){
		var rowCells = sysPreviewDesign.dash.oldCellsWidthObj;
		var rowCellWidth = rowCells[rowIndex];
		return rowCellWidth;
	}
	
	previewDesign._getCellBycolumnIndex = function(cells,columnIndex){
		//debugger;
		for(var i = 0 ;i < cells.length;i++){
			var column = cells[i].getAttribute('column').split(',');
			for(var index in column){
				if(column[index]==columnIndex){
					return cells[i];
				}
			}
		}
		return null;
	}
	previewDesign._getDashDomCellIndex = function(){
		//debugger;
		var dashDom = sysPreviewDesign.dash.dashDom;
		var dashDomIndex = dashDom.getAttribute('column');
		var _column = [];
		if(parseInt(dashDom.colSpan) > 1){
			_column = dashDom.getAttribute('column').split(",");
			dashDomIndex = parseInt(_column[_column.length-1]);
		}
		dashDomIndex = parseInt(dashDomIndex);
		return dashDomIndex;
	};
	 
	 previewDesign.getTableObj = function(currCell){
		 var table = null;
		 var tableCtrl = sysPreviewDesign.getPrintDesignElement(currCell);
		 if(tableCtrl && $(tableCtrl).attr('fd_type')=='table'){
			table = tableCtrl;
		 }
		 return table;
	 };
	 //单元格内容宽度
	 previewDesign.cellDistance = function(cell) {
		var children = cell.childNodes, cellWidth = cellHeight = 0;
		var isColSpan = cell.getAttribute('colSpan') != null, isRowSpan = cell.getAttribute('rowSpan') != null;
		for (var i = children.length - 1; i >= 0; i--) {
			var nodeType = children[i].nodeType;
			if ( nodeType==1 || nodeType==2 || nodeType==5 || nodeType==6 || nodeType==9) {
				if(!isColSpan){
					if(children[i].tagName.toUpperCase()=='SCRIPT'){
						continue;
					}
					cellWidth += $(children[i]).width();
				}
				if(!isRowSpan) cellHeight += children[i].offsetHeight;
			}
		}
		return {width: cellWidth, height: cellHeight};
	};
	//列内容宽度
	previewDesign.colDistance = function(column,index) {
//		debugger;
		var distance = 0;
		var dashDomIndex = index
		var table = previewDesign.getTableObj(sysPreviewDesign.dash.dashDom);
		for (var j = 0; j < table.rows.length; j++) {
			var cells = table.rows[j].cells;
			var _rowCurCell = sysPreviewDesign._getCellBycolumnIndex(cells,dashDomIndex);
			distance = Math.max(distance, previewDesign.cellDistance(_rowCurCell).width);
		}
		return distance;
	};
	 
	//是否打印设计控件
	previewDesign.isDesignElement = function(node) {
		return node && node.getAttribute('printcontrol') && node.getAttribute('printcontrol') == 'true';
	};
	 //获取打印表单设计控件
	previewDesign.getPrintDesignElement = function(node, attr) {
		if (attr == null) attr = 'parentNode';
		for (var findNode = node; findNode && findNode.tagName && findNode.tagName.toLowerCase() != 'body'; findNode = findNode[attr]) {
			if (previewDesign.isDesignElement(findNode)) return findNode;
		}
		return null;
	};
	
	var ieVersion = (function IEVersion() {
        var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
        var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
        var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
        if(isIE) {
            var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
            reIE.test(userAgent);
            var fIEVersion = parseFloat(RegExp["$1"]);
            if(fIEVersion == 7) {
                return 7;
            } else if(fIEVersion == 8) {
                return 8;
            } else if(fIEVersion == 9) {
                return 9;
            } else if(fIEVersion == 10) {
                return 10;
            } else {
                return 6;//IE版本<=7
            }   
        } else if(isEdge) {
            return 'edge';//edge
        } else if(isIE11) {
            return 11; //IE11  
        }else{
            return -1;//不是ie浏览器
        }
    })();
	//调整附件高度
	$(function(){
		if (ieVersion != -1 && typeof Attachment_ObjectInfo != "undefined") {
			var num = setInterval(function() {
				var isAllShowed = true;
				for (var key in Attachment_ObjectInfo) {
					var swfObj = Attachment_ObjectInfo[key];
					var showed = swfObj.showed;
					var fdGroup = swfObj.fdGroup;
					if (fdGroup != "xform") {
						continue;
					}
					if (!showed) {
						isAllShowed = false;
					}
				}
				if (!isAllShowed) {
					return;
				}
				if (isAllShowed) {
					clearInterval(num);
					for (var key in Attachment_ObjectInfo) {
						var swfObj = Attachment_ObjectInfo[key];
						var fdGroup = swfObj.fdGroup;
						if (fdGroup != "xform") {
							continue;
						}
						var tdObj = $("#attachmentObject_" + key + "_content_div").closest("td");
						tdObj.css("height",tdObj.height() + 30);
					}
				}
			}, 200);
		}
	});
	//调整明细表宽度
	previewDesign.resetTbWidth = function(node){
		if ($(node).attr("fd_type") == "detailsTable" || $(node).attr("fd_type") == "seniorDetailsTable") {
			var _width = $(node)[0].style.width;
			if (_width.indexOf('%') == -1) {
				if(node.currentStyle){  
					_width = node.currentStyle.width;
				}else{  
					_width = getComputedStyle(node, null).width;
				}
				_width = _width.replace('px','');
				if(_width && !isNaN(_width)){
					_width = parseInt(_width);
					//宽度超过980px,设置成auto
					if (_width > 980) {
						$(node).css('width','100%');
					}
				}
			}
			var tds = $(node).find("[type='titleRow']").find("td");
			for (var tdIndex = 0; tdIndex < tds.length; tdIndex++){
				var tdDom = tds[tdIndex];
				$(tdDom).css("width", "auto");
			}
			$(node).find("tr[type='optRow']").hide();
		}
	};
	
	//根据比例调整div容器(由于textarea控件中div有自己的宽度，得按比例缩小)
	previewDesign.resetBoxWidth = function(parentNode){
		try {
			var  _parent = parentNode || null, childCount, node;
			if (!_parent) return;
			//遍历子节点
			childCount = _parent.childNodes.length;
			for (var i = 0; i < childCount; i++) {
				node = _parent.childNodes[i];
				if (node.nodeType != 3) {
					var _width = $(node)[0].style.width;
					//处理明细表宽度
					previewDesign.resetTbWidth(node);
					//div的宽度为%则不处理
					if((node.tagName=='DIV' || node.tagName=='IMG' 
						|| node.tagName=='SPAN' || node.tagName == "LABEL") 
							&& _width.indexOf('%')==-1){
						var tag_width;
						if(node.currentStyle){  
							tag_width = node.currentStyle.width;
						}else{  
							tag_width = getComputedStyle(node, null).width;
						} 
						if ($(node).closest("[fd_type='voteNode']").length > 0 
								|| $(node).closest("[flagtype='dbechart']").length > 0) {
							continue;
						}
						tag_width = tag_width.replace('px','');
						if(tag_width && !isNaN(tag_width)){
							tag_width = parseInt(tag_width);
							//$(node).css('width',window._percent*tag_width); //固定像数会导致表格变形
							if(node.tagName=='IMG'){
							}else{
								if ($(node).attr("fd_type") == "circulationOpinionShow") {
									$(node).css('width','100%');
								} else {
									$(node).css('width','auto');
                                    var divControl = $(node).parent("[flagtype='divcontrol']");
                                    if (divControl.length > 0 && $(node).css("overflow") == "scroll") {
                                        $(node).css("overflow", "hidden");
                                    }
								}
								$(node).css('height','auto');
							}
						}else{
							//$(node).css('width','100%');//防止图片放大
						}
						previewDesign.resetBoxWidth(node);
					}else{
						previewDesign.resetBoxWidth(node);
					}
				}
			}
		} catch (e) {
			// do nothing
		}
	};
	//重新计算表格行高,防止表格分页内容溢出(IE)
	previewDesign.resetTBTrHeight = function(rootNode){
		previewDesign.resetTBTrHeightNone(rootNode);
		var tables = $(rootNode).find("table[fd_type='table']");
		for(var i = 0 ;i < tables.length;i++){
			var table = tables[i];
			for (var j = 0; j < table.rows.length; j++) {
				var tr = table.rows[j];
				var tdH = $(tr).children().outerHeight()+5;
				$(tr).css('height',tdH+'px');
			}
		}
	};
	//自适应高度
	previewDesign.resetTBTrHeightNone = function(rootNode){
		var tables = $(rootNode).find("table[fd_type='table']");
		for(var i = 0 ;i < tables.length;i++){
			var table = tables[i];
			for (var j = 0; j < table.rows.length; j++) {
				var tr = table.rows[j];
				$(tr).css('height','');
			}
		}
	};
	
	 window.sysPreviewDesign = previewDesign;
})(window);