(function (root, factory) {
  //if (typeof define === 'function' && (define.amd || define.cmd) ) {
    // AMD,CMD,dojo AMD下有点问题先忽略
   // define(function () {
    //  return factory(root);
    //});
  //}
  root['doclistdnd'] = factory(root);
  
}(typeof window !== 'undefined' ? window : this, function (root) {
	
	/**
	 * 表格拖拽JS，对外暴露build、rebuild两个方法。
	 * 初始化时调用:
	 * 		doclistdnd.build(tbInfo.DOMElement,{onDragStart:function(){………………},…………});
	 * 重新渲染时调用(如添加新的一行):
	 * 		doclistdnd.rebuild(tbInfo.DOMElement);
	 * @version 1.0.0
	 */
	'use strict';
	var currentTable,/*处于拖拽的表格,原生DOM对象*/
		dragObject,/*处于拖拽的行,原生DOM对象*/
		mouseOffset,/*拖拽开始时偏移量*/
		isRequestRunning = false,
	    oldY = 0;
	    
	
	var doclistdnd={
		version:'1.0.0'	
	};
	
	/**
	 * 初始化表格时调用,使表格行具备拖拽能力
	 * @param {DomElement}table	表格,原生DOM对象
	 * @param {Object} options 附加参数
	 */
	var build = function(table,options){
		if(!table){
			return;
		}
		table.dndConfig =  $.extend({
			onDragStart: null,//拖拽开始触发事件
			onDrop: null//拖拽结束触发事件
		}, options || {});
		//拖拽节点
		var id = table.id,
			dragId = 'tableDrag'+id;
		table.$dragDomId = 	dragId;
		//可拖拽实现
		makeDraggable(table);
		$(document).bind('mousemove', mousemove).bind('mouseup', mouseup);
	};
	
	/**
	 * 重新初始化表格,如addRow后调用
	 * @param {DomElement}table	表格,原生DOM对象
	 */
	var rebuild = function(table){
		if(table.dndConfig){
			makeDraggable(table);
		}
	};
	
	/**
	 * 拖拽能力实现
	 * @param {DomElement}table	表格,原生DOM对象
	 */
	var makeDraggable = function(table){
		var config = table.dndConfig,
			$rows = $('tr',table).filter(function(){
				var obj = this,
					isParent = false;
				for(; obj!=null; obj = obj.parentNode){
					if(obj && obj.tagName.toLowerCase() == 'table'){
						if(obj == table){
							isParent = true;
						}
						break;
					}
				}
				return isParent;
			});
		$rows.each(function(){
			var $row = $(this);
			if(!$row.attr('trdraggable') && isValidRow($row) ){//未初始化拖拽
				$row.bind('mousedown',function(evt){
					if (evt.target.tagName == "TD") {
						currentTable = table;
						dragObject = evt.currentTarget;
						mouseOffset = getMouseOffset(this, evt);
						if (config.onDragStart) {
							config.onDragStart(table, this);
	                    }
						renderDragDom(evt);
						$(dragObject).css('visibility','hidden');
					}
				}).css('cursor','move');
				$row.attr('trdraggable','true');
				$row.css("user-select", "none");
			}else{
				if(!isValidRow($row)){
					$row.attr('invalidRow','true');
				}
			}
		});
	};
	
	/**
	 * 处理拖拽过程
	 * @param {Event}evt
	 */
	var mousemove = function(evt){
		if(dragObject == null || isRequestRunning == true)
			return;
		var config = currentTable.dndConfig,
			currentMousePos = mouseCoords(evt),
			y = currentMousePos.y - mouseOffset.y; 
		if (y != oldY) {
            var movingDown = y > oldY;
            oldY = y;
            var moveRow = function(y, movingDown, dragObj, row) {
                if (row == undefined) { 
                	row = dragObj; 
                }
                var currentRow = findDropTargetRow(row, y);
                if (currentRow) {
                    if (movingDown && dragObj != currentRow) {
                        try { 
                        	row.parentNode.insertBefore(row, currentRow.nextSibling); 
                        } catch (e) { }
                    } else if (! movingDown && dragObj != currentRow) {
                        try { 
                        	row.parentNode.insertBefore(row, currentRow); 
                        } catch (e) { }
                    }
                }
            };
            moveRow(y, movingDown, dragObject);
            var $dragDom = $('#' + currentTable.$dragDomId);
            $dragDom.css({
            	'left':currentMousePos.x - mouseOffset.x,
    			'top':currentMousePos.y - mouseOffset.y
            });
        }
	};
	
	/**
	 * 拖拽结束
	 * @param {Event}evt
	 */
	var mouseup = function(evt){
		if(dragObject!=null && currentTable!=null){
			var $dragDom = $('#' + currentTable.$dragDomId);
			$dragDom.remove();
			$(dragObject).css('visibility','');
			var config = currentTable.dndConfig;
			if(config.onDrop){
				isRequestRunning = true;
				config.onDrop(currentTable,dragObject);
				isRequestRunning = false;
			}
			dragObject = null;
			currentTable = null;
		}
	};
	
	/**
	 * 渲染克隆拖拽对象
	 */
	var renderDragDom = function(){
		var $dragDom = $('<div id="' + currentTable.$dragDomId + '"><table class="tb_normal" width="100%" align="center" /></div>');
		$dragDom.css({
			'position':'absolute',
			'cursor':'move'	
		});
		$(document.body).append($dragDom);
		var	$dragTable = $('table',$dragDom),
			position = getPosition(dragObject);
		$dragTable.empty();
		var _tr =$('<tr/>').appendTo($dragTable);
		$(dragObject).find('td').filter(function(){
			return dragObject == this.parentNode;
		}).each(function(){
			var $td = ___clone($(this));
			$td.css({
				'width':$(this).width()
			});
			_tr.append($td);
		});
		$dragDom.css({
			'left':position.x,
			'top':position.y,
			'opacity':'.5'
		}).show();
	};
	
	//修复JQuery clone方法对select、textarea存在值丢失问题....
	//相关插件:https://github.com/spencertipping/jquery.fix.clone
	var ___clone = function(element){
		var el = compatibleCloneNode(element),
			originalSelects = $(element).find('select').add( $(element).filter('select') ),
			newSelects = $(el).find('select').add( $(el).filter('select') ),
			originalTextarea = $(element).find('textarea').add( $(element).filter('textarea') ),
			newTextarea = $(el).find('textarea').add( $(el).filter('textarea') );
		//textarea处理
		for (var i = 0, l = originalTextarea.length; i < l; ++i){
			$(newTextarea[i]).val($(originalTextarea[i]).val());
		} 
		//select处理
		for (var i = 0, l = originalSelects.length;   i < l; ++i) {
	      for (var j = 0, m = originalSelects[i].options.length; j < m; ++j) {
	        if (originalSelects[i].options[j].selected === true) {
	        	newSelects[i].options[j].selected = true;
	        }
	      }
	    }
		return el;
	};
	
	// 由于IE8及IE8以下的版本，在复制的时候，script节点的脚本会被重新执行，故删除脚本，由于脚本已经存在浏览器当中了，删除不会有任何影响 by zhugr 2017-09-07
	function compatibleCloneNode(dom){
		if(dom){
			var scripts = $(dom).find("script"); 
			for(var i = scripts.length - 1, s; i >= 0; i --){ 
				s = scripts[i]; 
				s.parentNode.removeChild(s); 
			} 	
		}
		return $(dom).clone(); 
	} 
	
	/**
	 * 获取指定元素与鼠标位置的偏移值
	 * @param {DomElement}target 指定元素,原生DOM对象
	 * @param {Event}evt 
	 */
	var getMouseOffset = function(target, evt){
		evt = evt || window.event;
	    var docPos    =	getPosition(target),
	    	mousePos  = mouseCoords(evt);
	    return {x:mousePos.x - docPos.x, y:mousePos.y - docPos.y};
	};
	
	/**
	 * 获取指定元素所在位置
	 * @param {DomElement}target 指定元素,原生DOM对象
	 */
	 var getPosition = function(target){
		 var left = 0; var top  = 0;
	     if (target.offsetHeight == 0) {
	    	 target = target.firstChild; 
	     }
	     while (target.offsetParent){
	    	 left += target.offsetLeft;
	         top  += target.offsetTop;
	         target = target.offsetParent;
	     }
	     left += target.offsetLeft;
	     top  += target.offsetTop;
	     return {x:left, y:top};
	 };
	 
	 /**
	 * 获取鼠标所在位置
	 * @param {Event}evt
	 */
	 var mouseCoords = function(evt){
		 if (evt.pageX || evt.pageY) { 
			 return {
				 x:evt.pageX, 
				 y:evt.pageY
			 }; 
		 }
	     return {
	         x:evt.clientX + document.body.scrollLeft - document.body.clientLeft,
	         y:evt.clientY + document.body.scrollTop  - document.body.clientTop
	     };
	 };
	 
	 /**
	  * 找到拖拽释放的行位置
	  * @param {DomElement}draggedRow 正在拖拽的元素,原生DOM对象
	  * @param {Integer}y 拖拽偏移量
	  */
	 var findDropTargetRow = function(draggedRow, y) {
        var rows = currentTable.rows;
        for (var i=0; i<rows.length; i++) {
            var row = rows[i];
            if($(row).attr('invalidRow')=='true'){
            	continue;
            }
            var rowY = getPosition(row).y;
            var rowHeight = parseInt(row.offsetHeight)/2;
            if (row.offsetHeight == 0) {
                rowY = getPosition(row.firstChild).y;
                rowHeight = parseInt(row.firstChild.offsetHeight)/2;
            }
            if ((y > rowY - rowHeight) && (y < (rowY + rowHeight))) {
                if (row == draggedRow) {
                	return null;
                }
                return row;
            }
        }
        return null;
    };
	
	/**
	 * 是否有效行(即是否需要拖拽,自定义表单中的标题行、统计行均不参与拖拽)
	 * @param {DomElement}row 拖拽行,原生DOM对象
	 */
	var isValidRow = function(row){
		 if($(row).attr('type')=='titleRow' 
			 || $(row).attr('type')=='statisticRow' 
				 || $(row).attr('type')=='optRow'
					 ||$(row).attr('type')=='noDraggableRow' ){//标题行、统计行、操作行、type="noDraggableRow"的行不参与拖拽
			 return false;
		 }
		 var $cell = $('td',row);
		 if($(row).hasClass('tr_normal_title') 
			|| $cell.hasClass('td_normal_title')){//标题行不参与统计
			return false;
		 }
		 return true;
	};
	
	doclistdnd.build = build;
	doclistdnd.rebuild = rebuild;
	
	return doclistdnd;
	
}));