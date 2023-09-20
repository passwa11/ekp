define(function(require, exports, module) {

//	打印界面新增样式编辑器
//	1、支持对表格宽度、高度、边框颜色、字体颜色、字体大小等进行调整
//	2、支持对图片大小进行扩大和缩小
//	3、支持删减字段和单元格、单元格行、单元格列
//	4、不支持编辑字段（防串改）
	
	//var $ = require('lui/jquery');
	var $ = require('lui/jquery-ui');
	
	require('resource/js/colorpicker/spectrum.js');
	require('resource/js/colorpicker/css/spectrum.css');
	
	/*
	require('sys/print/resource/js/colResizable.js');
	*/
	
	function PreviewDesign(element, options) {
		this.element = element;
		
		this.defaultOptions = {
			rootClass: 'previewdesign-root',
			cellClass: 'previewdesign-cell',
			menuClass: 'previewdesign-menu',
			menuItemClass: 'previewdesign-menu-item',
			dialogClass: 'previewdesign-dialog',
			dialogHeadClass: 'previewdesign-dialog-head',
			dialogBodyClass: 'previewdesign-dialog-body',
			dialogActionClass: 'previewdesign-dialog-action',
			dialogSetting: {
				
				canSetFontSize: true,
				fontSize: [
				    {text: '== 默认大小 ==', value: '', _value: 0},
				    {text: '11px', value: '11px', _value: 11},
				    {text: '12px', value: '12px', _value: 12},
				    {text: '14px', value: '14px', _value: 14},
				    {text: '16px', value: '16px', _value: 16},
				    {text: '18px', value: '18px', _value: 18},
				    {text: '20px', value: '20px', _value: 20},
				    {text: '22px', value: '22px', _value: 22},
				    {text: '24px', value: '24px', _value: 24},
				    {text: '26px', value: '26px', _value: 26},
				    {text: '28px', value: '28px', _value: 28},
				    {text: '36px', value: '36px', _value: 36},
				    {text: '48px', value: '48px', _value: 48},
				    {text: '72px', value: '72px', _value: 72}
				],
				font: [
				    {text: '== 默认字体 ==', value: ''},
				    {text: '宋体', value: '宋体'},
				    {text: '新宋体', value: '新宋体'},
				    {text: '楷体', value: '楷体'},
				    {text: '黑体', value: '黑体'},
				    {text: '幼圆', value: '幼圆'},
				    {text: '微软雅黑', value: '微软雅黑'},
				    {text: '华文中宋', value: '华文中宋'},
				    {text: '仿宋_GB2312', value: '仿宋_GB2312'},
				    {text: 'Courier New', value: 'Courier New'},
				    {text: 'Times New Roman', value: 'Times New Roman'},
				    {text: 'Tahoma', value: 'Tahoma'},
				    {text: 'Arial', value: 'Arial'},
				    {text: 'Verdana', value: 'Verdana'}
		        ],
		        borderWidth: [
		            {text: '== 默认厚度 ==', value: ''},
		            {text: '0px', value: '0px'},
		            {text: '1px', value: '1px'},
		            {text: '2px', value: '2px'},
		            {text: '3px', value: '3px'},
		            {text: '4px', value: '4px'},
		            {text: '5px', value: '5px'}
		        ]
			}
		};
		
		this.options = $.extend(this.defaultOptions, options);
		this.initialize();
	}
	
	PreviewDesign.prototype = {
		initialize: function() {
			this.beforeInit();
			this.initPorps();
			this.render();
			this.bindEvents();
			
			//标记表格准备好了，使部分强制性样式生效
			this.ready();
		},
		
		beforeInit: function() {
			this.element.addClass(this.options.rootClass);
		},
		
		initPorps: function() {
			
			var self = this;
			
			//控件校验函数
			this.widgetFunc = {
				injectWidgetStyle: function(cell) {
					
					var label = cell.children('[fd_type="label"]').get(0);
					if(label) {
						label = cell.children('[fd_type="label"]');
						label.css('color') && cell.css('color', label.css('color'));
						label.css('font-size') && cell.css('font-size', label.css('font-size'));
						label.css('font-family') && cell.css('font-family', label.css('font-family'));
					}
					
					var fieldLayout = cell.children('.xform_fieldlayout').get(0);
					if(fieldLayout) {
						fieldLayout = cell.children('.xform_fieldlayout');
						fieldLayout.css('color') && cell.css('color', fieldLayout.css('color'));
						fieldLayout.css('font-size') && cell.css('font-size', fieldLayout.css('font-size'));
						fieldLayout.css('font-family') && cell.css('font-family', fieldLayout.css('font-family'));
					}
					
				}
				
					
			};
			
			//菜单函数
			this.menuFunc = {
				//清空单元格
				clear: function(cell) {
					cell.children('.' + self.options.cellClass).empty();
				},
				clearLine: function(cell) {
					cell.parent().find('.' + self.options.cellClass).empty();
				},
				//删除单元格
				del: function(cell) {
					var t = null;
					if(cell.next().get(0)) {
						t = cell.next()
					} else if(cell.get(0)) {
						t = cell.prev();
					} else {
						cell.parent().remove();
						return;
					}
					
					var col1 = parseInt(cell.attr('colspan') || '1');
					var col2 = parseInt(t.attr('colspan') || '1');
					
					t.attr('colspan', col1 + col2);
					cell.remove();
				},
				delLine: function(cell) {
					cell.parent().remove();
				}
			};
			//弹窗函数
			this.dialogFunc = {
				buildPropsForm: function(element, form, cell, type) {
					
					
					var cellMain = cell;
					cell = cellMain.closest('td');
					
					if(type == 'cell') {
						
						//1-1.字体设置
						var row1_1 = $('<tr/>').appendTo(form);
						var row1_1_td1 = $('<td/>').appendTo(row1_1);
						$('<span/>').text('字体').appendTo(row1_1_td1);
						var row1_1_td2 = $('<td/>').attr('colspan', '3').appendTo(row1_1);
						var selectFontFamily = $('<select/>').attr('name', 'fontFamily').appendTo(row1_1_td2);
						var currentFontFamily = cellMain.css('font-family') || '';
						$.each(self.options.dialogSetting.font || [], function(_, f) {
							var opt = $('<option/>').attr('value', f.value)
								.text(f.text)
								.css('font-family', f.value)
								.appendTo(selectFontFamily);
							if(currentFontFamily == f.value) {
								opt.attr('selected', 'selected');
							}
						});
						
						//1-2.字体大小设置
						if(self.options.dialogSetting.canSetFontSize) {
							var row1_2 = $('<tr/>').appendTo(form);
							var row1_2_td1 = $('<td/>').appendTo(row1_2);
							$('<span/>').text('大小').appendTo(row1_2_td1);
							
							var row1_2_td2 = $('<td/>').attr('colspan', '3').appendTo(row1_2);
							var selectFontSize = $('<select/>').attr('name', 'fontSize').appendTo(row1_2_td2);
							var currentFontSize = cellMain.css('font-size') || '';
							var fontSizeFlag = false;
							var fontSizeArr = [];
							$.each(self.options.dialogSetting.fontSize || [], function(_, fs) {
								//var option = $('<option/>').attr('value', fs.value).text(fs.text).appendTo(selectFontSize);
								fontSizeArr.push(fs);
								if(fs.value == currentFontSize) {
									//option.attr('selected', 'selected');
									fontSizeFlag = true;
								}
							});
							//特殊字体大小处理
							if(!fontSizeFlag) {
								fontSizeArr.push({
									text: currentFontSize,
									value: currentFontSize,
									_value: parseInt(currentFontSize.replace(/px/g, ''))
								});
							}
							
							fontSizeArr.sort(function(a, b) {
								if(a._value > b._value) {
									return 1;
								} else if(a._value < b._value) {
									return -1;
								} else {
									return 0;
								}
							});
							
							$.each(fontSizeArr, function(_, fs) {
								var option = $('<option/>').attr('value', fs.value).text(fs.text).appendTo(selectFontSize);
								if(fs.value == currentFontSize) {
									option.attr('selected', 'selected');
								}
							});
							
						}
						
						
						//1-3.字体颜色设置
						var row1_3 = $('<tr/>').appendTo(form);
						var row1_3_td1 = $('<td/>').appendTo(row1_3);
						$('<span/>').text('字体颜色').appendTo(row1_3_td1);
						
						var row1_3_td2 = $('<td/>').attr('colspan', '3').appendTo(row1_3);
						var currentFontColor = cellMain.css('color');
						var inputFontColor = $('<input/>').attr('value', currentFontColor || '#000000')
												.attr('name', 'fontColor').appendTo(row1_3_td2);
						inputFontColor.spectrum({
				    		preferredFormat: 'hex'
				    	});
						
						//1-4.字体效果设置
						var row1_4 = $('<tr/>').appendTo(form);
						var row1_4_td1 = $('<td/>').appendTo(row1_4);
						$('<span/>').text('字体效果').appendTo(row1_4_td1);
						
						var row1_4_td2 = $('<td/>').attr('colspan', '3').appendTo(row1_4);
						var isBold = cellMain.css('font-weight') == '700';
						var checkboxIsBold = $('<input/>')
												.attr('type', 'checkbox')
												.attr('name', 'isBold');
						isBold && checkboxIsBold.attr('checked', 'checked');
						$('<label/>')
							.append(checkboxIsBold)
							.append($('<span/>').text('加粗'))
							.appendTo(row1_4_td2);
						
						var isItalic = cellMain.css('font-style') == 'italic';
						var checkboxIsItalic = $('<input/>')
												.attr('type', 'checkbox')
												.attr('name', 'isItalic');
						isItalic && checkboxIsItalic.attr('checked', 'checked');
						$('<label/>')
							.append(checkboxIsItalic)
							.append($('<span/>').text('斜体'))
							.appendTo(row1_4_td2);
						
						var isUnderline = (cellMain.css('text-decoration') || '').indexOf('underline') > -1;
						var checkboxIsUnderline = $('<input/>')
												.attr('type', 'checkbox')
												.attr('name', 'isUnderline');
						isUnderline && checkboxIsUnderline.attr('checked', 'checked');
						$('<label/>')
							.append(checkboxIsUnderline)
							.append($('<span/>').text('下划线'))
							.appendTo(row1_4_td2);

						//1-5.不换行设置
						var row1_5 = $('<tr/>').appendTo(form);
						var row1_5_td1 = $('<td/>').appendTo(row1_5);
						$('<span/>').text('换行设置').appendTo(row1_5_td1);
						
						var row1_5_td2 = $('<td/>').attr('colspan', '1').appendTo(row1_5);
						
						var isInLine = (cellMain.css('display') || '').indexOf('inline-block') > -1;
						
						var fontFamily = (cellMain.css('font-family') || '').length > 0;
						var checkboxIsInLine = $('<input/>')
												.attr('type', 'checkbox')
												.attr('name', 'isInLine');
						!isInLine && checkboxIsInLine.attr('checked', 'checked');
						$('<label/>')
							.append(checkboxIsInLine)
							.append($('<span/>').text('换行'))
							.appendTo(row1_5_td2);
					}
					
					if(type == 'td') {
						
						//2-1.样式设置
						var row2_1 = $('<tr/>').appendTo(form);
						var row2_1_td1 = $('<td/>').appendTo(row2_1);
						$('<span/>').text('样式').appendTo(row2_1_td1);
						var row2_1_td2 = $('<td/>').attr('colspan', '3').appendTo(row2_1);
						
						var radioStyle1 = $('<input/>').attr('name', 'style').attr('type', 'radio').attr('value', 'title');
						$('<label/>').append(radioStyle1)
							.append($('<span/>').text('标题'))
							.appendTo(row2_1_td2);
						var radioStyle2 = $('<input/>').attr('name', 'style').attr('type', 'radio').attr('value', 'normal');
						$('<label/>').append(radioStyle2)
							.append($('<span/>').text('普通'))
							.appendTo(row2_1_td2);
						if(cell.hasClass('td_normal_title')) {
							radioStyle1.attr('checked', 'checked');
						} else {
							radioStyle2.attr('checked', 'checked');
						}
						
						//2-2.内容对齐设置
						var row2_2 = $('<tr/>').appendTo(form);
						var row2_2_td1 = $('<td/>').appendTo(row2_2);
						$('<span/>').text('内容对齐').appendTo(row2_2_td1);
						
						var row2_2_td2 = $('<td/>').attr('colspan', '3').appendTo(row2_2);
						var alignLeftRadio = $('<input/>').attr('type', 'radio').attr('name', 'contentAlign').attr('value', 'left').insertBefore(
													$('<span/>').text('左对齐').appendTo($('<label/>').appendTo($('<div/>').appendTo(row2_2_td2)))
												);
						var alignMiddleRadio = $('<input/>').attr('type', 'radio').attr('name', 'contentAlign').attr('value', 'center').insertBefore(
													$('<span/>').text('居中').appendTo($('<label/>').appendTo($('<div/>').appendTo(row2_2_td2)))
												);
						var alignRightRadio = $('<input/>').attr('type', 'radio').attr('name', 'contentAlign').attr('value', 'right').insertBefore(
													$('<span/>').text('右对齐').appendTo($('<label/>').appendTo($('<div/>').appendTo(row2_2_td2)))
												);
						
						var currentContentAlign = cellMain.css('text-align');
						switch(true) {
							case currentContentAlign.indexOf('center') > -1:
								alignMiddleRadio.attr('checked', 'checked');
								break;
							case currentContentAlign.indexOf('right') > -1:
								alignRightRadio.attr('checked', 'checked');
								break;
							default:
								alignLeftRadio.attr('checked', 'checked');
								break;
						}
						
						//2-3.单元格行宽设置
						var row2_3 = $('<tr/>').appendTo(form);
						var row2_3_td1 = $('<td/>').appendTo(row2_3);
						$('<span/>').text('单元格行宽').appendTo(row2_3_td1);
						
						var row2_3_td2 = $('<td/>').attr('colspan', '3').appendTo(row2_3);
						var currentRowSpan = parseInt(cell.attr('rowspan') || '1');
						$('<input/>').attr('value', currentRowSpan)
							.attr('type', 'number')
							.attr('name', 'rowSpan').appendTo(row2_3_td2);
						
						row2_3 = $('<tr/>').appendTo(form);
						var row2_3_td3 = $('<td/>').appendTo(row2_3);
						$('<span/>').text('单元格列宽').appendTo(row2_3_td3);
						
						var row2_3_td4 = $('<td/>').attr('colspan', '3').appendTo(row2_3);
						var currentColSpan = parseInt(cell.attr('colspan') || '1');
						$('<input/>').attr('value', currentColSpan)
							.attr('type', 'number')
							.attr('name', 'colSpan').appendTo(row2_3_td4);
						
						//2-4.表格边框厚度
						var row2_4 = $('<tr/>').appendTo(form);
						var row2_4_td1 = $('<td/>').appendTo(row2_4);
						$('<span/>').text('表格边框厚度').appendTo(row2_4_td1);
						
						var row2_4_td2 = $('<td/>').attr('colspan', '3').appendTo(row2_4);
						var selectBorderWidth = $('<select/>').attr('name', 'borderWidth').appendTo(row2_4_td2);
						var currentBorderWidth = cell.css('border-bottom-width') || '1px';
						$.each(self.options.dialogSetting.borderWidth || [], function(_, bw) {
							var opt = $('<option/>').attr('value', bw.value)
								.text(bw.text)
								.css('font-family', bw.value)
								.appendTo(selectBorderWidth);
							if(currentBorderWidth == bw.value) {
								opt.attr('selected', 'selected');
							}
						});
						
						//2-5.表格边框颜色
						var row2_5 = $('<tr/>').appendTo(form);
						var row2_5_td1 = $('<td/>').appendTo(row2_5);
						$('<span/>').text('表格边框颜色').appendTo(row2_5_td1);
						
						var row2_5_td2 = $('<td/>').attr('colspan', '3').appendTo(row2_5);
						var currentBorderColor = cell.css('border-bottom-color');
						var inputBorderColor = $('<input/>').attr('value', currentBorderColor || '#000000')
												.attr('name', 'borderColor').appendTo(row2_5_td2);
						inputBorderColor.spectrum({
				    		preferredFormat: 'hex'
				    	});
					}
					
				},
				checkPropsForm: function(element, form, cell) {
					
					var cellMain = cell;
					cell = cellMain.closest('td');
					
					//1.样式设置
					if(form.find('input[name="style"]:checked').get(0)) {
						var style = form.find('input[name="style"]:checked').val();
						switch(style) {
						case 'normal': 
							if(cell.hasClass('td_normal_title')) {
								cell.removeClass('td_normal_title');
							}
							break;
						case 'title': 
							if(!cell.hasClass('td_normal_title')) {
								cell.addClass('td_normal_title');
							}
							break;
						default: break;
						}
					}

					//2.字体设置
					if(form.find('select[name="fontFamily"]').get(0)) {
						var fontFamily = form.find('select[name="fontFamily"]').val();
						cellMain.css('font-family', fontFamily);
					}
					
					//3.字体大小设置
					if(form.find('select[name="fontSize"]').get(0)) {
						var fontSize = form.find('select[name="fontSize"]').val();
						cellMain.css('font-size', fontSize);
					}
					
					//4.字体颜色设置
					if(form.find('input[name="fontColor"]').get(0)) {
						var fontColor = form.find('input[name="fontColor"]').val();
						cellMain.css('color', fontColor);
					}
					
					//5.内容对齐设置
					if(form.find('input[name="contentAlign"]:checked').get(0)) {
						var contentAlign = form.find('input[name="contentAlign"]:checked').val();
						cellMain.css('text-align', contentAlign);
					}
					
					//6.单元格行宽列宽设置
					if(form.find('input[name="rowSpan"]').get(0)) {
						var rowSpan = form.find('input[name="rowSpan"]').val();
						cell.attr('rowspan', rowSpan);
					}
					if(form.find('input[name="colSpan"]').get(0)) {
						var colSpan = form.find('input[name="colSpan"]').val();
						cell.attr('colspan', colSpan);
					}
					
					//7.单元格边框厚度设置
					if(form.find('select[name="borderWidth"]').get(0)) {
						var borderWidth = form.find('select[name="borderWidth"]').val();
						cell.closest('table').children('tbody').children('tr').children('td').css('border-width', borderWidth);
					}
					
					//8.单元格边框颜色设置
					if(form.find('input[name="borderColor"]').get(0)) {
						var borderColor = form.find('input[name="borderColor"]').val();
						cell.closest('table').children('tbody').children('tr').children('td').css('border-color', borderColor);
					}
					
					//9.字体效果设置
					if(form.find('input[name="isBold"]:checked').get(0)) {
						cellMain.css('font-weight', '700');
					} else {
						cellMain.css('font-weight', '400');
					}
					if(form.find('input[name="isItalic"]:checked').get(0)) {
						cellMain.css('font-style', 'italic');
					} else {
						cellMain.css('font-style', 'normal');
					}
					if(form.find('input[name="isUnderline"]:checked').get(0)) {
						cellMain.css('text-decoration', 'underline');
					} else {
						cellMain.css('text-decoration', 'none');
					}
					if(form.find('input[name="isInLine"]:checked').get(0)) {
						cellMain.css('display', 'block');
//						cellMain.css('width', '100%');
					}else if(!form.find('input[name="isInLine"]:checked').is(':checked')){
						cellMain.css('display', 'inline-block');
						cellMain.css('width', 'auto');
					} else { 
						cellMain.css('display', 'inline-block');
						//#89645 82658 
						//cellMain.css('width', 'auto');
					}
					
				}
			}
		},
		
		// 表格重绘
		/*
		render: function() {
			var self = this;
			
			this.element.find('table[fd_type="table"]').each(function(){
				
				$(this).children('tbody').children('tr').each(function() {
					
					$(this).children('td').each(function() {
						//标记单元格
						$(this).attr('data-flag', 'true');

						var cell = $('<div/>').addClass(self.options.cellClass); 
						if(!$.trim($(this).html())) {
							$(this).empty();
						}

						$(this).children().appendTo(cell);
						$(this).append(cell);

						//cell初始化逻辑
						self.widgetFunc.injectWidgetStyle(cell);
					});
				});
				
			});

		},*/
		
		//统一渲染
		render: function() {
			this.renderTables();
			this.afterRender();
		},
		
		//渲染表格
		renderTables: function() {
			var self = this;
			this.element.find('table').each(function() {
				$(this).children('tbody').children('tr').children('td').each(function() {
					//标记单元格
					$(this).attr('data-flag', 'true');
					
					//按顺序重绘控件，避免renderWidgets逻辑的排序问题
					$(this).children().each(function() {
						self.renderWidget($(this));
					});
				});
			});
		},
		
		/**
		 * 有三种定义控件的方式：1.属性fd_type 2.属性flagtype 3.类名 4.特殊
		 */
		renderWidget: function(widget) {
			
			if(!widget.get(0)) {
				return;
			}
			
			var self = this;
			
			//1.属性fd_type定义控件
			var fdType = widget.attr('fd_type');
			if(fdType) {
				switch(fdType) {
					case 'label':
					case 'input':
					case 'inputRadio':
					case 'checkbox':
					case 'select':
					case 'fSelect':
					case 'detailsTable':
					case 'qrCode':
					case 'brcontrol':
					case 'table':
						self._renderWidget(widget, {type: fdType});
						break;
					default: break;
				}
				return;
			}
			
			//2.属性flagtype定义控件
			var flagType = widget.attr('flagtype');
			if(flagType) {
				switch(flagType) {
					case 'xform_address':
					case 'xform_new_address':
					case 'xform_relation_attachment':
					case 'xform_docimg':
					case 'xform_rtf':
						self._renderWidget(widget, {flagType: flagType});
						break;
					default: break;
				}
				return;
			}

			//3.类名定义控件
			if(widget.hasClass('xform_textArea')) {
				self._renderWidget(widget, {classType: 'xform_textArea'});
			} else if(widget.hasClass('xform_fieldlayout')) {
				self._renderWidget(widget, {classType: 'xform_fieldlayout'});
			} else if(widget.hasClass('xform_auditshow')) {
				self._renderWidget(widget, {classType: 'xform_auditshow'});
			}
			
			//4.特殊控件
			try {
				
				var tagName = widget.get(0).tagName.toLowerCase();
				var hidden = widget.css('display') == 'none';
				//特殊或隐藏的标签不处理
				if($.inArray(tagName, ['script', 'link', 'style']) > -1 || hidden) {
					
				} else if(widget.hasClass('lui-component')) {
					
					var specType = '';
					
					//加减签
					if(widget.get(0).id == 'sign') {
						specType = 'sign'
					}
					
					self._renderWidget(widget, {classType: 'lui-component', specType: specType});
				} else {
					self._renderWidget(widget, {specType: 'other'});
				}
				
			} catch(err){ 
				
			}
			

		},
		
		_renderWidget: function(widget, types) {
			
			types = types || {};
			
			var cell = $('<div/>')
						.addClass(this.options.cellClass)
						.attr('data-type', types.type || '')
						.attr('data-flag-type', types.flagType || '')
						.attr('data-class-type', types.classType || '')
						.attr('data-spec-type', types.specType || '')
						.appendTo(widget.closest('td'));
			
			widget.css('color') && cell.css('color', widget.css('color'));
			widget.css('font-size') && cell.css('font-size', widget.css('font-size'));
			widget.css('font-family') && cell.css('font-family', widget.css('font-family'));
			widget.css('font-style') && cell.css('font-style', widget.css('font-style'));
			widget.css('font-weight') && cell.css('font-weight', widget.css('font-weight'));
			widget.css('text-decoration') && cell.css('text-decoration', widget.css('text-decoration'));
			
			widget.appendTo(cell);
		},
		
		//渲染后处理逻辑
		afterRender: function() {
			
		},
		
		setResizable: function(element, options) {
			var resizableOptions = {};
			resizableOptions = $.extend(resizableOptions, options || {});
			element.resizable(resizableOptions);
		},
		
		setDraggable: function(element, options) {
			var draggableOptions = {};
			draggableOptions = $.extend(draggableOptions, options || {});
			element.draggable(draggableOptions);
		},
		
		setDroppable: function(element, options) {
			var droppableOptions = {};
			droppableOptions = $.extend(droppableOptions, options || {});
			element.droppable(droppableOptions);
		},
		
		setMovable: function(element) {
			var movableOptions = {};
			movableOptions = $.extend(movableOptions, options || {});
			element.droppable(movableOptions);
		},
		
		//单元格菜单
		openMenu: function(e, cell, type) {
			
			var self = this;
			
			this.closeMenu();
			this.closePropsDialog();
			

			var menu = this.menu = $('<div/>').css('display', 'none')
										.addClass(this.options.menuClass)
										.appendTo(document.body);
			
			if(e) {
				if(e.pageX + 156 > window.innerWidth) {
					menu.css('left', e.pageX - 156);
				} else {
					menu.css('left', e.pageX);
				}
				menu.css('top', e.pageY);
			} else {
				dialog.css('left', cell.position().left);
				dialog.css('top', cell.position().top);
			}

			var propsItem = $('<div/>')
								.text('属性')
								.addClass(this.options.menuItemClass).appendTo(menu);
			propsItem.click(function(){
				self.openPropsDialog(e, cell, type);
				self.closeMenu();
			});
			
			if(type == 'cell') {
				
				var clearWidgetItem = $('<div/>').text('删除控件')
											.addClass(this.options.menuItemClass).appendTo(menu);
				
				clearWidgetItem.click(function() {
					self.closeMenu();
					var res = window.confirm('是否确认删除所选的控件？');
					if(res) {
						cell.remove();
					}
				});
			}
			
			if(type == 'td') {
				var clearWidgetsItem = $('<div/>').text('删除控件')
								.append($('<span/>').text('Delete'))
								.addClass(this.options.menuItemClass).appendTo(menu);
				clearWidgetsItem.click(function() {
					self.closeMenu();
					var res = window.confirm('是否确认删除所选单元格的控件？');
					if(res) {
						self.menuFunc.clear(cell);
					}
				});
				
				var delItem = $('<div/>').text('删除单元格')
								.append($('<span/>').text('Shift + Delete'))
								.addClass(this.options.menuItemClass).appendTo(menu);
				delItem.click(function() {
					self.closeMenu();
					var res = window.confirm('是否确认删除选择的单元格？');
					if(res) {
						self.menuFunc.del(cell);
					}
				});
				
				var delLineItem = $('<div/>').text('删除行')
							.addClass(this.options.menuItemClass).appendTo(menu);
				delLineItem.click(function() {
				self.closeMenu();
					var res = window.confirm('是否确认删除选择的行？');
					if(res) {
						self.menuFunc.delLine(cell);
					}
				});
			}
			
			$('<div/>').addClass(this.options.menuItemClass).attr('data-type', 'line').appendTo(menu);
			
			var cancelItem = $('<div/>').text('取消').addClass(this.options.menuItemClass).appendTo(menu);
			cancelItem.click(function() {
				self.closeMenu();
			});
			
			
			menu.fadeIn(150);
		},
		closeMenu: function() {
			var self = this;
			
			if(this.menu) {
				self.menu.remove();
				self.menu = null;
			}
		},
		
		//属性弹窗
		openPropsDialog: function(e, cell, type) {
			
			var self = this;
			
			this.closeMenu();
			this.closePropsDialog();
			
			var dialog = this.propsDialog = $('<div/>').css('display', 'none')
												.addClass(this.options.dialogClass)
												.appendTo(document.body);
			if(e) {
				if(e.pageX + 224 > window.innerWidth) {
					dialog.css('left', e.pageX - 224);
				} else {
					dialog.css('left', e.pageX);
				}
				dialog.css('top', e.pageY);
			} else {
				dialog.css('left', cell.position().left);
				dialog.css('top', cell.position().top);
			}
			
			var _dialogHead = $('<table/>')
									.appendTo(
										$('<div/>')
											.addClass(this.options.dialogHeadClass)
											.appendTo(dialog)
									);
			var dialogHead = $('<tr/>').appendTo(_dialogHead);
			
			var dialogTitle = $('<td/>').appendTo(dialogHead);
			
			var formLabel = '';
			switch(type) {
				case 'cell': 
					formLabel = '控件属性'; 
					break;
				case 'td': 
					formLabel = '单元格属性'; 
					break;
				default: break;
			}
			
			$('<span/>').text(formLabel).appendTo(dialogTitle);
			
			var dialogAction = $('<td/>').attr('data-type', 'action').appendTo(dialogHead);
			var btnToggle = $('<span/>').text('-').appendTo(dialogAction);
			btnToggle.click(function() {
				_dialogBody.toggle();
			});
			var btnClose = $('<span/>').text('x').appendTo(dialogAction);
			btnClose.click(function() {
				self.closePropsDialog();
			});
			
			var _dialogBody = $('<div/>')
								.addClass(this.options.dialogBodyClass)
								.appendTo(dialog);
			var dialogBody = $('<table/>')
								.appendTo(_dialogBody);
			
			this.dialogFunc.buildPropsForm(self.element, dialogBody, cell, type);
			
			var dialogAction = $('<div/>').addClass(this.options.dialogActionClass)
									.appendTo(_dialogBody);
			var btnCheck = $('<div/>').text('确定')
								.attr('data-type', 'check')
								.appendTo(dialogAction);
			btnCheck.click(function() {
				self.dialogFunc.checkPropsForm(self.element, dialogBody, cell);
				self.closePropsDialog();
			});
			
			var btnSubmit = $('<div/>').text('应用')
						.attr('data-type', 'check')
						.appendTo(dialogAction);
			btnSubmit.click(function() {
				self.dialogFunc.checkPropsForm(self.element, dialogBody, cell);
			});
			
			var btnCancel = $('<div/>').text('取消')
								.attr('data-type', 'cancel')
								.appendTo(dialogAction);
			btnCancel.click(function() {
				self.closePropsDialog();
			});
			
			
			dialog.fadeIn(150);
			
		},
		closePropsDialog: function() {
			var self = this;
			if(this.propsDialog) {
				self.propsDialog.remove();
				self.propsDialog = null;
			}
			
			//关闭属性弹窗时同时关闭颜色选择器弹窗
			$('.sp-container').addClass('sp-hidden');
		},
		
		bindEvents: function() {
			
			var self = this;
			
			//单元格缩放
			/*
			this.element.find('table[fd_type="table"]').colResizable();
			$(window).trigger('resize.JColResizer');
			$(window).trigger('resize.JColResizer');
			this.element.find('td[data-flag="true"]').each(function() {
				//if($(this).next().get(0)) {
					self.setResizable($(this), {
						handles: 's, e',
						start: function(event, ui) {
							
							//横向单元格不设置高度
							ui.helper.closest('tr').children('td[data-flag="true"]').css('height', 'unset');
							
							//纵向单元格不设置宽度
							//TODO
						},
						stop: function(event, ui) {
						},
						resize: function(event, ui) {
						}
					});
				//}
			});
			 */
			
			//延迟两秒设置缩放能力（IE浏览器加载缓慢）
			setTimeout(function() {
				
				//图片上传控件图片缩放
				self.element.find('[data-flag-type="xform_docimg"] .imgbox img').each(function() {
					self.setResizable($(this));
				});
				
				//富文本控件图片缩放
				self.element.find('[data-flag-type="xform_rtf"] img').each(function() {
					self.setResizable($(this));
				});
				
				//标题图片缩放
				self.element.find('[data-spec-type="img"] img').each(function() {
					self.setResizable($(this));
				});
				
				//审批意见图片缩放
				self.element.find('[data-class-type="xform_auditshow"] img').each(function() {
					self.setResizable($(this));
				});
			}, 2000);
			
			
			//单元格控件内容拖放
			/*
			this.setMovable(this.element.find('.previewdesign-cell'), {
			    drop: function(event, ui) {
			    	var src = ui.draggable;
			    	var tgt = $(event.target);
			    	
			    	if(tgt.has(src).get(0)) {
			    		//父子元素不允许调换位置
			    	} else {
			    		
			    		var srcParent = src.parent();
			    		var tgtParent = tgt.parent();
			    		
			    		// 控件交换逻辑
			    		srcParent.append(tgt);
			    		tgtParent.append(src);
			    	}
	
			    }
			});
			*/
			
			this.element.find('.' + this.options.cellClass).each(function() {
				//明细表内控件不允许拖动
				if(!$(this).closest('[fd_type="detailsTable"]').get(0)) {
					self.setDraggable($(this), {
						start: function(event, ui) {
							ui.helper.css('z-index', 10);
						},
						stop: function(event, ui) {
							ui.helper.css('left', 0);
							ui.helper.css('top', 0);
							ui.helper.css('z-index', 1);
						}
					});
				}
			});
			
			this.setDroppable(this.element.find('td[data-flag="true"]'), {
			    drop: function(event, ui) {
			    	var src = ui.draggable;
			    	var tgt = $(event.target);
			    	
			    	if(tgt.has(src).get(0)) {
			    		//父子元素不允许调换位置
			    	} else {
			    		tgt.append(src);
			    	}

			    }
			});

			//单元格右键菜单
			this.element.find('td[data-flag="true"]').contextmenu(function(e) {
				e.preventDefault();
				e.stopPropagation();
				self.openMenu(e, $(this), 'td');
			});
			//控件右键菜单
			this.element.find('.' + this.options.cellClass).contextmenu(function(e) {
				e.preventDefault();
				e.stopPropagation();
				self.openMenu(e, $(this), 'cell');
			});
			
			//单元格选取
			this.element.find('td[data-flag="true"]').click(function() {
				
				if($(this).hasClass('active')) {
					$(this).removeClass('active');
				} else {
					self.element.find('td[data-flag="true"]').removeClass('active');
					$(this).addClass('active');
				}
				
			});
			
			//表格单击隐藏菜单
			this.element.click(function() {
				self.closeMenu();
			});
			
			//单元格双击更改样式
			this.element.find('td[data-flag="true"]').dblclick(function(e) {
				e.preventDefault();
				e.stopPropagation();
				self.openPropsDialog(e, $(this), 'td');
			});
			
			//控件双击更改样式
			this.element.find('.' + this.options.cellClass).dblclick(function(e) {
				e.preventDefault();
				e.stopPropagation();
				self.openPropsDialog(e, $(this), 'cell');
			});
			
			//键盘事件处理
			$(document).keyup(function(e) {
				var selectedCell = self.element.find('td.active[data-flag="true"]');
				if(!selectedCell.get(0)) {
					return;
				}
				var keyCode = e.keyCode;
				var shiftKey = e.shiftKey;
				
				switch(keyCode) {
					case 46: 
						if(shiftKey) {
							var res = window.confirm('是否确认删除所选的单元格？');
							if(res) {
								self.menuFunc.del(selectedCell);
							}
						} else {
							var res = window.confirm('是否确认删除所选单元格的控件？');
							if(res) {
								self.menuFunc.clear(selectedCell);
							}
						}
						break;
					case 82: 
						//self.openPropsDialog(e, selectedCell);
						break;
					default: break;
				}
				
			});
			
			
		},

		//打印前处理逻辑
		ready: function(cb) {
			var self = this;
			this.element.attr('data-ready', 'true');

			//this.element.find('.ui-resizable').css('position', 'static');
			if(cb && $.isFunction(cb)) {
				cb(function() {
					//self.element.find('.ui-resizable').css('position', 'relative');
				});
			} else {
				//self.element.find('.ui-resizable').css('position', 'relative');
			}
		},
		
		setDialogSetting: function(setting) {
			this.options.dialogSetting = $.extend({}, this.options.dialogSetting, setting);
		}
	};
	
	var hideTip = function() {
		if(window._PREVIEWDESIGN_TIPNODE_) {
			window._PREVIEWDESIGN_TIPNODE_.fadeOut(function() {
				$(this).remove();
			});
			window._PREVIEWDESIGN_TIPNODE_ = null;
		}
		if(window._PREVIEWDESIGN_TIPNODE_TIMER_) {
			clearTimeout(window._PREVIEWDESIGN_TIPNODE_TIMER_);
			window._PREVIEWDESIGN_TIPNODE_TIMER_ = null;
		}
	}
	
	var showTip = function(options) {
		
		hideTip();
		
		var defaultOptions = {
			duration: 8000,
			text: '当前打印页可进行样式编辑'
		};
		
		options = $.extend({}, defaultOptions, options);
		
		var tipNode = window._PREVIEWDESIGN_TIPNODE_ = $('<div/>')
						.addClass('previewdesign-tip')
						.css('display', 'none')
						.appendTo($(document.body));

		var tipNodeMain = $('<div/>').addClass('previewdesign-tip-main')
							.appendTo(tipNode);
		
		$('<span/>').addClass('lui_icon_s')
			.addClass('lui_icon_s_cue3')
			.attr('data-type', 'icon')
			.appendTo(tipNodeMain);
		
		$('<span/>').attr('data-type', 'label').text(options.text).appendTo(tipNodeMain);
		
		var btnClose = $('<span/>').attr('data-type', 'button').text('×').appendTo(tipNodeMain);
		btnClose.click(function() {
			hideTip();
		});
		
		window._PREVIEWDESIGN_TIPNODE_TIMER_ = setTimeout(function() {
			hideTip();
		}, options.duration);
		
		tipNode.fadeIn();
	}
	
	exports.showTip = showTip;
	exports.hideTip = hideTip;
	exports.PreviewDesign = PreviewDesign;
	
});