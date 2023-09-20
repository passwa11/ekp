define(function(require, exports, module) {
	var listview = require('lui/listview/listview');
	var layout = require('lui/view/layout');
	var template = require('lui/listview/template');
	var source = require('lui/data/source');
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	var env = require('lui/util/env');
	var strutil = require('lui/util/str');
	var modelingLang = require("lang!sys-modeling-main");
	var LIST_SELECT_ALL_CHECKED = 'list.select.all.checked'; // 全选按钮发出事件
	var LIST_SELECT_ALL_CHECKED_ON = 'list.select.all.checked.on'; // 被动触发全选事件

	var BoardTable = listview.AbstractTable.extend(listview.ResourceTable,{

				className : 'boardTable',
				rowCss: {},	//全局变量，用于存放显示项字段所在行的样式

				initProps : function($super, _cfg) {
					$super(_cfg);
					this.selected = true;
					this.channel = this.config.channel;
					this.gridHref = this.config.gridHref;
					this.target = this.config.target || '_blank';
					this.name = this.config.name;
					this.columnNum = 1;
					this.container = this.config.container || null;
					this.title = this.config.title || null;
				},

				startup : function($super) {
					if (this.isStartup) {
						return;
					}
					if (!this.source) {
						this.setSource(this.parent.source);
					}
					if (!this.layout) {
						this.setLayout(new layout.Template({
									src : require
											.resolve('./board-table-layout.html#'),
									parent : this
								}));
					}
					this.on('show', this.onSourceGet, this);
					this.source.on('error', this.onError, this);
					this.source.on('data', this.onSourceLoaded, this);
					this.template.on('error', this.onError, this);
					this.template.on('html', this.doTemplate, this);
					// 判断是否全选
					topic.channel(this).subscribe(
						LIST_SELECT_ALL_CHECKED, this.selectAll,
						this);

					topic.channel(this).subscribe(
						LIST_SELECT_ALL_CHECKED, this.beingSelectAll,
						this);

					topic.channel(this).subscribe(
						LIST_SELECT_ALL_CHECKED_ON, this.beingSelectAll,
						this);

					$super();
				},

				selectAll : function(evt) {
					this.container
						.find(
							'input[type=checkbox][data-lui-mark="table.content.checkbox"]')
						.each(function() {

							this.checked = evt.checked;
							if($(this).is(':checked')){
								$(this).parents('.cardClassifyDetails').addClass('selected')
							}else{
								$(this).parents('.cardClassifyDetails').removeClass('selected')
							}
						});

				},

				beingSelectAll : function(evt){
					this.isSelectAll = evt.checked;
				},

				bindAllSelect : function() {
					var self = this;
					var checkOn = true;
					self.container.parent()
						.find(
							'input[type=checkbox][data-lui-mark="table.content.checkbox"]')
						.each(
							function() {
								if (!this.checked) {
									checkOn = false;
									return false
								}
							});
					topic
						.channel(
							self)
						.publish(
							LIST_SELECT_ALL_CHECKED_ON,{checked:checkOn});
				},


				buildEvent:function() {
					var self= this;
					$(this.container).scroll(function() {
						self.scrollHandle(this);
					});
					this.container.find(".cardClassifyDetails").each(function() {
						for(var key in self.rowCss){
							if(key === $(this).data("id")){
								$(this).css(self.rowCss[key]);
								$(this).find(".cardClassifyDetailsText").css(self.rowCss[key]);
							}
						}
					})
					//卡片选中状态
					$('.cardClassifyDetailsOpt').on('click', function(event){
						event.stopPropagation()
						if($(this).is(':checked')){
							$(this).parents('.cardClassifyDetails').addClass('selected')
						}else{
							$(this).parents('.cardClassifyDetails').removeClass('selected')
						}
						self.bindAllSelect();
					})
					//最小化状态切换
					var $cardClassifyHeaderBtn = this.container.find(".cardClassifyHeaderBtn");
					$cardClassifyHeaderBtn.on('click', function(){
						if($(this).parents('.cardClassifyContent').hasClass('minimize')){
							$(this).parents('.cardClassifyContent').removeClass('minimize')
						}else{
							$(this).parents('.cardClassifyContent').addClass('minimize')
						}
					})

					//title全部显示事件
					this.buildPanelShowEvent();

					var $li = $(".model-board-list-view-module li.active");
					this.renderCardContentView($li);
					$(".model-board-list-view-module li").click(function(){
						$(this).addClass("active");
						$(".model-board-list-view-module li").not(this).removeClass("active");
						self.renderCardContentView($(this));
						self.buildPreviewEvent();
					})
					//极简模式下预览框显示事件
					this.buildPreviewEvent();
				},

				renderCardContentView:function ($dom){
					var module = $dom.data("value");
					if(module === "normal"){
						$(".boardContainer .cardClassifyContent").removeClass('minimalism');
						$(".boardContainer .cardClassifyContent").removeClass('overview')
					}else if(module === "simple"){
						$(".boardContainer .cardClassifyContent").addClass('minimalism');
						$(".boardContainer .cardClassifyContent").removeClass('overview');

					}else if(module === "summary"){
						$(".boardContainer .cardClassifyContent").addClass('overview');
						$(".boardContainer .cardClassifyContent").removeClass('minimalism')
					}
				},

				buildPanelShowEvent:function() {
					var domSpan="";
					//移入标题显示全部
					$('.panelShowText').mouseenter(function(){
						if($(this).parent('.cardClassifyDetailsText').children('span').length>0){

						}else{
							var scrollHeight = $(this)[0].scrollHeight;
							var height = $(this).height();
							var showTxt = $(this).text().trim();
							domSpan = '<span>'+showTxt+'<\/span>';

							//判断是否有隐藏内容
							if(scrollHeight > height){
								$(this).parent('.cardClassifyDetailsText').append(domSpan)
							}
						}
					}).mouseleave(function(){
						$(this).parent('.cardClassifyDetailsText').children('span').remove();
					});
				},

				buildPreviewEvent:function() {
					var self = this;
					if($(".boardContainer .cardClassifyContent").hasClass("minimalism")){
						//极简模式下鼠标移入预览
						$('.cardClassifyContent.minimalism').find(".cardClassifyDetails").unbind("mouseenter").mouseenter(function(){
							var detailsNull = $(this).find(".cardClassifyDetailsNull");
							$('.cardClassifyDetailsPreview').remove();
							if(detailsNull.length == 0){
								var clientTop = $(this).offset().top;
								var clientleft = $(this).offset().left;
								$(this).parents('.boardContainer').append("<div class='cardClassifyDetailsPreview'></div>");
								let preview = $(this).parents('.boardContainer').find('.cardClassifyDetailsPreview');
								let content = $(this).html();
								var width = $(".boardContainer").innerWidth();
								clientleft = clientleft + 254;
								//超过容器宽度时，clientleft = 当前dom的offleft - 预览弹框宽度
								clientleft = clientleft + 280 > width ? clientleft - 254 - 230 : clientleft;
								preview.css({
									"top": clientTop + 16,
									"left": clientleft
								});
								preview.append(content).show();
								$('.cardClassifyDetailsPreview').find(".cardClassifyDetailsOpt").remove();
								$('.cardClassifyDetailsPreview').mouseenter(function() {
									$('.cardClassifyDetailsPreview').show();
								}).mouseleave(function() {
									$('.cardClassifyDetailsPreview').remove();
								})
							}
						}).mouseleave(function(){
							$('.cardClassifyDetailsPreview').hide();
						});
					}else{
						$('.cardClassifyContent').find(".cardClassifyDetails").unbind("mouseenter");
						self.buildPanelShowEvent($(".boardContainer"));
					}
				},

				converKvDatas : function(_data) {
					var _datas = _data.datas;
					this.kvData = [];
					for (var i = 0; i < _datas.length; i++) {
						var json = {};
						for (var j = 0; j < _datas[i].length; j++) {
							json[_datas[i][j]['col']] = _datas[i][j]['value'];
						}
						json['rowId'] = listview.UniqueId();
						this.kvData.push(json);
					}

					var _columns = _data.columns;
					for (var i = 0; i < _columns.length; i++) {
						_columns[i]['rowId'] = listview.UniqueId();
						_columns[i]['sort'] = '';
					}
					return this.kvData;
				},

				// 依据列元素调整数据源结构
				dataFormat : function(data, _data) {
					var kvData = this.converKvDatas(_data);
				},

				onSourceLoaded : function($super, _data) {
					if (!this.isSelected()) {
						return;
					}
					this.parent._data = {};
					$.extend(true, this.parent._data, _data);

					this._data = _data;
					topic.channel(this.parent).publish('list.changed', _data);

					if (!_data.datas || _data.datas.length == 0) {
						this.noRecode();
						return;
					}
					this.dataFormat(this.datas, this._data);
					if(this.rowCss && !this.isPage){
						this.rowCss = {};
					}
					this.displayCssSet(this._data);
					this.get(this._data);
				},

				noRecodeLoaded : function(data) {
					var that = this;
					var data ='<div class="cardClassifyHeader clearfix">\n' +
						'                <div class="cardClassifyHeaderText"></div>\n' +
						'                <div class="cardClassifyHeaderCount">0</div>\n' +
						'                <span class="cardClassifyHeaderBtn"></span>\n' +
						'            </div>\n' +
						'            <div class="">\n' +
						'                <div class="cardClassifyDetailsNull">\n' +
						'                    '+modelingLang["modeling.listview.board.nodata"]+'\n' +
						'                </div>\n' +
						'            </div>';
					setTimeout(function() {
						that.element.html(data);
						that.container.html(data);
						that.element.prepend($('<span class="cardClassifyHeaderBtn"></span>'));
						that.container.prepend($('<span class="cardClassifyHeaderBtn"></span>'));
						that.element.find(".cardClassifyHeaderText").html(that.title);
						that.container.find(".cardClassifyHeaderText").html(that.title);
						//最小化状态切换
						that.container.find(".cardClassifyHeaderBtn").on('click', function(){
							if($(this).parents('.cardClassifyContent').hasClass('minimize')){
								$(this).parents('.cardClassifyContent').removeClass('minimize')
							}else{
								$(this).parents('.cardClassifyContent').addClass('minimize')
							}
						})
						that.container.addClass('minimize')
					},110)
				},

				setTemplate : function(_template) {
					this.template = _template
				},

				setLayout : function(_layout) {
					this.layout = _layout
				},

				addChild : function($super, child) {
					if (child instanceof template.AbstractTemplate) {
						this.setTemplate(child);
					}
					if (child instanceof source.BaseSource) {
						this.setSource(child);
					}
					$super(child);
				},

				onClick : function($super, evt) {
					var $target = $(evt.target);
					while ($target.length > 0) {
						if ($target.attr('data-lui-mark-id')) {
							var code = '';
							var rowId = $target.attr('data-lui-mark-id');
							if (this.gridHref) {
								for (var j = 0; j < this.kvData.length; j++) {
									if (rowId === this.kvData[j]['rowId']) {
										var href = strutil.variableResolver(
												this.gridHref, this.kvData[j])
										code = ["window.open('",
												env.fn.formatUrl(href), "','",
												this.target, "')"].join('');
										break;
									}
								}

							} else if (this.onGridClick) {
								for (var j = 0; j < this.kvData.length; j++) {
									if (rowId === this.kvData[j]['rowId']) {
										code = strutil.variableResolver(
												this.onGridClick,
												this.kvData[j]);
										break;
									}
								}
							}
							new Function(code).apply(this.element.get(0));
							break;
						}
						$target = $target.parent();
					}
				},

				draw : function($super) {
					if (this.isDrawed)
						return;

					var self = this;
					if (this.layout) {
						this.layout.on("error", function(msg) {
							self.element.append(msg);
						});
						this.layout.get(this, function(obj) {
							self.doLayout(obj);
						});
					}
					this.element.show();
					if (this.isSelfInit())
						this.resolveUrls(this.parent.cacheEvt);
					this.isDrawed = true;
					return this;
				},

				doLayout : function($super, html) {
					this.layoutHtml = html;
					$super(html);
				},

				clearHtml : function() {
					this.element.empty();
					this.container.empty();
				},

				doTemplate : function($super, html) {
					var _self = this;
					setTimeout(function() {
						if (_self.template.datas) {
							_self.kvData = _self.template.datas;
						}
						if(_self.isPage){
							_self.element.find(".board-list-content")
								.append(html);
							_self.container.find(".board-list-content").append(html);
						}else{
							_self.element.html(_self.layoutHtml);
							_self.element.find("[data-lui-mark='table.content.inside']")
								.html(html);
							_self.element.find(".cardClassifyHeaderCount").html(_self._data.page.totalSize);
							_self.element.find(".cardClassifyHeaderText").html(_self.title);

							_self.parent.element.html(_self.element[0].outerHTML);
							_self.container.html(_self.parent.element[0].outerHTML);
							_self.container.removeClass('minimize');
						}
						_self.element.prepend($('<span class="cardClassifyHeaderBtn"></span>'));
						_self.container.prepend($('<span class="cardClassifyHeaderBtn"></span>'));
						_self.isLoad = true;
						_self.fire({
							"name" : "load",
							'table' : _self
						});
						_self.isPage = false;
						_self.buildEvent();
					}, 100);
				},

				renderLoaded : function($super, datas, render) {
					var txt = "";
					if (render) {
						for (var i = 0; i < this._data.datas.length; i++) {
							var json = {};
							for (var j = 0; j < this._data.datas[i].length; j++) {
								json[this._data.datas[i][j]['col']] = this._data.datas[i][j]['style'];
							}
						}
						for (var i = 0; i < datas.length; i++) {
							txt += (render).call(this.template, datas[i], i,
								'grid');
						}
					} else
						txt = this.template.html.call(this.template, datas);
					if(txt)
						this.template.fireHtml(txt);
					$super(datas, render);
				},

				get : function(data) {
					this.template.datas = [];
					this.template._datas = [];
					var datas = data['datas'], columns = data['columns'];
					for (var i = 0; i < datas.length; i++) {
						var json = {};
						var style={};
						for (var j = 0; j < datas[i].length; j++) {
							json[datas[i][j]['col']] = datas[i][j]['value'];
							style[datas[i][j]['col']] = datas[i][j]['style'];
						}
						json['rowIndex'] = i;
						json['rowId'] = listview.UniqueId();
						json['style'] = style;
						this.template.datas.push(json);
					}
					this.renderLoaded(this.template.datas, this.template.customHtml);
				},

				resolveUrl : function($super,params) {
					params = params == null ? {} : params;
					this.parent.query = params.query || this.parent.query || [];
					// 暂时增加query属性支持额外参数查询
					this.parent.criterions = params.criterions
						|| this.parent.criterions || [];
					this.page = params.page || this.page || [];
					this.parent.sorts = params.sorts || this.parent.sorts || [];

					this.buildUrl(this.parent.criterions.concat(this.parent.query),
						this.page, this.parent.sorts);
					this.isPage = params.isPage || false;
					this.onSourceGet();
				},

				scrollHandle: function(e) {
					var self = this;
					var scrollTop = $(e).scrollTop(),scrollHeight = e.scrollHeight,windowHeight = $(e).height();
					var height = scrollHeight - scrollTop - windowHeight;
					if (this.onFetching) {
						// do nothing
					} else {
						if (height <= 30) {
							this.onFetching = true;
							setTimeout(function(){
								self.getData();
								self.onFetching = false;
							}, 500);
						}
					}
				},

				getData : function() {
					this.currentPage = parseInt(this._data.page.currentPage);
					this.currentPage+=1;
					this.totalSize=parseInt(this._data.page.totalSize);
					this.pageSize=parseInt(this._data.page.pageSize);
					var rowsize = parseInt(this.totalSize / this.pageSize);
					rowsize += this.totalSize % this.pageSize > 0 ? 1 : 0 ;
					if (this.currentPage>rowsize){
						return;
					}
					var evt = {
						isPage:true,
						page : [{
							key : 'pageno',
							value : [this.currentPage]
						}, {
							key : 'rowsize',
							value : [this.pageSize]
						},{
							key:"pagingtype",
							"value" : ["simple"]
						}],
						query : [{
							key : "s_raq",
							"value" : [Math.random()]
						}]
					};
					this.resolveUrl(evt);
				},
				onSourceGet : function($super) {
					if (!this.parent.sourceURL
						|| this.parent.sourceURL != this.source.url) {
						if(this.source.url){
							this.queue.push(this.source.url);
							this.parent.sourceURL = this.queue[0];
							if(this.queue.length > 1)
								return;
							this.source.get();
						}
					} else {
						this.onSourceLoaded($.extend(true, {}, this.parent._data));
					}
					this.parent.element.css("min-height","auto");
				},

				//对页面列表数据进行预处理
				preHandleData:function(datas){
					var obj = {};

					for(var row = 0;row < datas.datas.length;row++){
						for(var col = 0;col < datas.columns.length;col++){
							var item = datas.datas[row][col];
							if (item){
								if(!obj[item.col]){
									obj[item.col] = [];
								}
								var value= this.handleEnums(item.col,item.value);
								obj[item.col].push({
									x:row,
									y:col,
									value:value
								});
							}
						}
					}
					return obj;
				},
				handleEnums:function(col,value){
					if(listOption.modelDict){
						var modelDict = JSON.parse(listOption.modelDict);
						for (var i = 0; i < modelDict.length; i++) {
						    var dict = modelDict[i];
						    if(dict.field === col){
						    	if(dict.fieldType === "enum" && dict.enumValues){
									for (var j = 0; j < dict.enumValues.length; j++) {
									    if (dict.enumValues[j].fieldEnumLabel === value) {
											return dict.enumValues[j].fieldEnumValue;
									    }
									}
								}
						    	break;
							}
						}
					}
					return value;
				},

			//显示项样式设置
			 displayCssSet:function(datas){
				var fdDisplayCssObj = $.parseJSON(listOption.fdDisplayCssObj);
				if(listOption.allField){
					var allField = $.parseJSON(listOption.allField);
					//处理表单控件属性变化后，对应地改变where条件
					this.doChangeWhere(allField,fdDisplayCssObj);
				}
				//2.取列表数据并预处理
				var dataObj = this.preHandleData(datas);
				if(fdDisplayCssObj){
					for(var key in fdDisplayCssObj){
						if(dataObj[key]){
							this.doCssSet(key,dataObj,fdDisplayCssObj,datas);
						}
					}
				}
			},
			doChangeWhere:function(allField,fdDisplayCssObj){
				var allFieldObj = {};
				for(var i = 0;i < allField.length;i++){
					allFieldObj[allField[i].field.split(".")[0]] = {
						text:allField[i].text,
						type:allField[i].type
					}
				}
				for (var key in fdDisplayCssObj){
					for(var i = 0;i < fdDisplayCssObj[key].length;i++){
						if(fdDisplayCssObj[key][i].selected.type != allFieldObj[key].type){
							//属性类型已改，where恢复默认
							fdDisplayCssObj[key][i].selected.type = allFieldObj[key].type;
							//where条件运算符和值恢复默认
							this.changeWhereData(fdDisplayCssObj[key][i].where,key,allFieldObj);
						}

					}
				}
			},

			changeWhereData:function(whereArr,key,allFieldObj){
				for(var j = 0;j < whereArr.length;j++){
					whereArr[j].expression = {};
					if(allFieldObj[key].type != "String"){
						whereArr[j].match = "eq";
					}else{
						whereArr[j].match = "=";
					}
				}
			},

			 doCssSet:function(key,dataObj,fdDisplayCssObj,datas){
				for(var i = 0;i < dataObj[key].length;i++){
					var cssArr = fdDisplayCssObj[key];
					if(cssArr){
						this.doMatchAndCssSet(cssArr,dataObj,datas,key,i);
					}
				}
			},

			doMatchAndCssSet:function(cssArr,dataObj,datas,key,i){
				for(var j = 0;j < cssArr.length;j++){
					var isOk = true;
					var whereArr = cssArr[j].where;
					var whereType = cssArr[j].whereType.whereTypeValue;
					var fieldObj = cssArr[j].field;
					var fieldItemObj = cssArr[j].fieldItem;
					if(whereType == "2"){
						//无条件，都满足，无需匹配
						this.cssSet(fieldObj,dataObj[key][i],datas.datas,fieldItemObj);
					}else{
						if(whereArr.length == 0){
							//没配置查询条件
							isOk = false;
						}
						for(var m = 0;m < whereArr.length;m++){
							if(whereArr[m].expression){
								whereArr[m].expression.text = decodeURI(whereArr[m].expression.text);
								whereArr[m].expression.value = decodeURI(whereArr[m].expression.value);
								whereArr[m].name.value = decodeURI(whereArr[m].name.value);
								whereArr[m].name.type = decodeURI(whereArr[m].name.type);
								var value = whereArr[m].expression.text || "";
								var match = whereArr[m].match;
								var type = whereArr[m].type.value;
								var keyValueField = whereArr[m].name.value;
								//实际值
								var keyValue = dataObj[keyValueField][i].value;
								if(whereType == "0"){
									if(type != "3" && value == ""){
										isOk = true;
									}else{
										if ("enum" === whereArr[m].name.type){
											var backisOk = this.matchEnumWhere(keyValue,match,value);
											if(backisOk == false){	//and查询有一个不匹配就都不满足
												isOk = false;
												break;
											}
										}else {
											var backisOk = this.matchWhere(keyValue, match, value);
											if (backisOk == false) {	//and查询有一个不匹配就都不满足
												isOk = false;
											}
										}
									}
								}else if(whereType == "1"){	//or查询匹配就满足
									if(type != "3" && value == ""){
										this.cssSet(fieldObj,dataObj[key][i],datas.datas,fieldItemObj);
									}else{
										//枚举类型的另外判断
										if ("enum" === whereArr[m].name.type){
											if(this.matchEnumWhere(keyValue,match,value)){
												cssSet(fieldObj,dataObj[key][i],datas.datas,fieldItemObj);
												break;
											}
										}else {
											if (this.matchWhere(keyValue, match, value)) {
												this.cssSet(fieldObj, dataObj[key][i], datas.datas, fieldItemObj);
												break;
											}
										}
									}
								}
							}
						}
						if(isOk && whereType == "0"){
							this.cssSet(fieldObj,dataObj[key][i],datas.datas,fieldItemObj);
						}
					}
				}
			},

			cssSet:function(fieldObj,colDataObj,datas,fieldItemObj){
				var row = colDataObj.x;
				var col = colDataObj.y;
				var fontcolor = fieldObj.fontColor;
				var bgcolor = fieldObj.backgroundColor;
				if(datas[row][col].style != ""){
					datas[row][col].style +=";";
				}
				datas[row][col].style +="color:"+fontcolor;
				datas[row][col].style += ";background-color:"+bgcolor;

				var ItemBackgroundColor = fieldItemObj.ItemBackgroundColor;
				var css = {
					backgroundColor: ItemBackgroundColor
				};
				this.rowCss[datas[row][0].value] = css;
			},
			checkHtml:function(htmlStr) {
				var  reg = /<[^>]+>/g;
				return reg.test(htmlStr);
			},

			//显示项，条件匹配
			matchWhere:function(keyValue,match,value){
				if(this.checkHtml(keyValue)){
					if($(keyValue)[1]){
						keyValue = $($(keyValue)[1]).text();
					}else{
						//百分数
						keyValue = $($(keyValue)[0]).text();
					}
				}
				if(keyValue.indexOf("%") != -1){
					//百分数
					keyValue = (keyValue.replace("%",""))/100;
				}
				if(value.indexOf("%") != -1){
					//后台配置是百分数
					value = (value.replace("%",""))/100;
				}
				var isOk = false;
				switch(match){
					case "=" :
						if(keyValue.trim() == value){
							isOk = true;
						}
						break;
					case "like" :
						if(value == ""){
							if(keyValue.trim() == value){
								isOk = true;
							}
						}else{
							if(keyValue.indexOf(value) != -1){
								isOk = true;
							}
						}
						break;
					case "eq" :
						if(keyValue.trim() == ""){
							if(keyValue.trim() == value){
								isOk = true;
								break;
							}
						}
						if(keyValue.indexOf(":") != -1 || keyValue.indexOf("-") != -1){
							//日期类型
							if(keyValue == value){
								isOk = true;
							}
						}else{
							if(parseFloat(keyValue) == parseFloat(value)){
								isOk = true;
							}
						}
						break;
					case "lt" :
						if(keyValue.indexOf(":") != -1 || keyValue.indexOf("-") != -1){
							if(keyValue < value){
								isOk = true;
							}
						}else{
							if(parseFloat(keyValue) < parseFloat(value)){
								isOk = true;
							}
						}
						break;
					case "le" :
						if(keyValue.indexOf(":") != -1 || keyValue.indexOf("-") != -1){
							if(keyValue <= value){
								isOk = true;
							}
						}else{
							if(parseFloat(keyValue) <= parseFloat(value)){
								isOk = true;
							}
						}
						break;
					case "gt":
						if(keyValue.indexOf(":") != -1 || keyValue.indexOf("-") != -1){
							if(keyValue > value){
								isOk = true;
							}
						}else{
							if(parseFloat(keyValue) > parseFloat(value)){
								isOk = true;
							}
						}
						break;
					case "ge":
						if(keyValue.indexOf(":") != -1 || keyValue.indexOf("-") != -1){
							if(keyValue >= value){
								isOk = true;
							}
						}else{
							if(parseFloat(keyValue) >= parseFloat(value)){
								isOk = true;
							}
						}
						break;
					case "!{notequal}":
						if(keyValue.trim() != value){
							isOk = true;
						}
						break;
				}
				return isOk;
			},
		matchEnumWhere: function(keyValue,match,value){
				//实际值数组
				var keyValueArr=keyValue.split(';');
				//查询条件数组
				var valueArr=value.split(';');

				var isOk = false;
				var amount = 0;
				switch(match){
					case "=" :
						if(keyValue == ""){
							if(keyValue == value){
								isOk = true;
								break;
							}else {
								break;
							}
						}
						if(keyValueArr.length == valueArr.length){
							if (keyValueArr.length === 0){
								isOk = true;
							}else {
								for (var i = 0; i<keyValueArr.length;i++){
									var keyValueItem = keyValueArr[i];
									for (var j = 0;j<valueArr.length;j++){
										var valueItem = valueArr[j];
										if (keyValueItem === valueItem){
											amount +=1;
										}
									}
								}
								//数量一致且相同数量一样就是等于
								if (keyValueArr.length == amount){
									isOk = true;
								}
							}
						}
						break;
					case "!{notequal}":
						if(keyValueArr.length == valueArr.length){
							for (var i = 0; i<keyValueArr.length;i++){
								var keyValueItem = keyValueArr[i];
								for (var j = 0;j<valueArr.length;j++){
									var valueItem = valueArr[j];
									if (keyValueItem === valueItem){
										amount +=1;
									}
								}
							}
							//数量一致且相同数量和数量不一样就是不等于
							if (keyValueArr.length != amount){
								isOk = true;
							}
						}else {
							//长度不一致一定不等于
							isOk = true;
						}
						break;
					case "like" :
						if(value == ""){
							if(keyValue == value){
								isOk = true;
							}
						}else{
							for (var i = 0; i<valueArr.length;i++){
								var valueItem = valueArr[i];
								for (var j = 0;j<keyValueArr.length;j++){
									var keyValueItem = keyValueArr[j];
									if (keyValueItem === valueItem){
										isOk = true;
										break;
									}
								}
							}
						}
						break;
					case "!{notContain}":
						if(value == ""){
							if(keyValue != value){
								isOk = true;
							}
						}else {
							isOk = true;
							fi:for (var i = 0; i < valueArr.length; i++) {
								var valueItem = valueArr[i];
								for (var j = 0;j<keyValueArr.length;j++){
									var keyValueItem = keyValueArr[j];
									if (keyValueItem === valueItem){
										isOk = false;
										break fi;
									}
								}

							}

						}
						break;
				}
				return isOk;
			}
			});

	exports.BoardTable = BoardTable;
})