(function(){var ui = {
	DataSource : function(queryUrl, options){
		this.queryUrl = (Com_Parameter.ContextPath.substring(Com_Parameter.ContextPath.length - 1) == '/' ? Com_Parameter.ContextPath.substring(0, Com_Parameter.ContextPath.length - 1) : Com_Parameter.ContextPath) + queryUrl;
		this.params = {pageNo:1};
		this.renders = [];
		this.data = null;
		this.dialog = new sui.Dialog();
		
		this.format = null;
		this.bindRender = function(render){
			this.renders.push(render);
			if(render.bindSource){
				render.bindSource(this);
			}
		};
		this.extend = function(params){
			if(params!=null){
				$.extend(this.params, params);
			}
		};
		this.load = function(){
			this.dialog.loading();
			var _self = this;
			$.ajax({
				url : this.queryUrl,
				cache : true,
				data : this.params,
				dataType : 'json',
				success : function(data){
					if(!data.state) {
						// 出错啦
						alert(data.errorMsg);
						_self.dialog.hide();
						return;
					}
					_self.dialog.hide();
					if(_self.format){
						_self.data = _self.format(data);
					}else{
						_self.data = data;
					}
					_self.draw();
				}
			});
		};
		this.draw = function(){
			for(var i=0; i<this.renders.length; i++){
				this.renders[i].draw(this.data);
			}
			// 发布完成事件
			$(document).trigger('sui.done');
		};
		$.extend(this, options);
	},
	CriteriaRender : function(root, options){
		this.root = root;
		this.children = null;
		this.key = null;
		this.click = null;
		
		this.draw = function(items){
			if(this.children==null){
				var root = $(this.root);
				this.children = {};
				for(var i=0; i<items.length; i++){
					this.drawEntry(root, items[i].key, items[i].name || items[i].key);
				}
			}
			this.refresh(this.key);
		};
		this.drawEntry = function(root, key, name){
			var entry = $('<span class="criteria_btn criteria_normal">'+name+'</span>');
			var _self = this;
			entry.click(function(){
				if(_self.click){
					var result = _self.click(key);
					if(result===false){
						return;
					}
					_self.refresh(key);
				}
			});
			entry.appendTo(root);
			this.children[key] = entry;
		};
		this.refresh = function(key){
			if(this.key!=null){
				this.children[this.key].removeClass('criteria_select');
				this.children[this.key].addClass('criteria_normal');
			}
			this.key = key;
			if(this.key!=null){
				this.children[this.key].removeClass('criteria_normal');
				this.children[this.key].addClass('criteria_select');
			}
		};
		$.extend(this, options);
	},
	MonthRender : function(root, options){
		this.root = root;
		this.month = null;
		this.source = null;
		this.children = null;
		
		this.bindSource = function(source){
			this.source = source;
			var month = ui.param('month');
			if(month!=null){
				this.source.params.month = month;
			}
		};
		this.draw = function(data){
			if(this.children==null){
				var root = $(this.root);
				this.children = {};
				for(var i=0; i<data.months.length; i++){
					this.drawEntry(root, data.months[i], data.months[i]);
				}
				if(this.source.params.month==null && data.months.length>0){
					this.source.params.month = data.months[data.months.length-1];
				}
			}
			this.refresh();
		};
		this.drawEntry = function(root, key, name){
			var entry = $('<span class="criteria_btn criteria_normal">'+name+'</span>');
			var _self = this;
			entry.click(function(){
				_self.source.extend({month:key, pageNo:1});
				_self.source.load();
			});
			entry.appendTo(root);
			this.children[key] = entry;
		};
		this.refresh = function(){
			var month = this.source.params.month;
			if(month==this.month){
				return;
			}
			if(this.month!=null){
				this.children[this.month].removeClass('criteria_select');
				this.children[this.month].addClass('criteria_normal');
			}
			this.month = month;
			this.children[this.month].removeClass('criteria_normal');
			this.children[this.month].addClass('criteria_select');
		};
		$.extend(this, options);
	},
	TableRender : function(root, options){
		this.root = root;
		this.dataKey = 'datas';
		this.columns = ['key', 'value'];
		this.align = [-1];
		this.operations = null;
		this.rowClick = null;
		
		this.draw = function(data){
			var _self = this;
			var datas = data[this.dataKey];
			var tb = $(this.root)[0];
			for(var i = tb.rows.length - 1; i>0; i--){
				tb.deleteRow(i);
			}
			$(this.root).show();
			for(var i = 0; i < datas.length; i++){
				var info = datas[i];
				var row = tb.insertRow(-1);
				var _row = $(row);
				_row.mouseover(function (){$(this).addClass('tb_current')});
				_row.mouseout(function (){$(this).removeClass('tb_current')});
				if(this.rowClick){
					_row.css({"cursor":"pointer"});
					_row.attr('data-row-index', i);
					_row.click(function(){
						var rowIndex = parseInt($(this).attr('data-row-index'));
						_self.rowClick(datas[rowIndex]);
					});
				}
				for(var j = 0; j < this.columns.length; j++){
					var cell = $(row.insertCell(-1));
					switch(this.align[j]){
					case -1:
						cell.css({'text-align':'left'});
						break;
					case 0:
						cell.css({'text-align':'center'});
						break;
					default:
						cell.css({'text-align':'right'});
					}
					if($.isFunction(this.columns[j])){
						this.columns[j].apply(cell, [{data:info, rowIndex:i, cellIndex:j}]);
					}else{
						cell.text(this.format(info[this.columns[j]]));
					}
				}
				if(this.operations){
					var cell = $(row.insertCell(-1));
					cell.css({'text-align':'center'});
					for(var j=0; j<this.operations.length; j++){
						var operation = this.operations[j];
						var dom = $('<a href="#" style="margin:0px 5px;"></a>');
						dom.text(operation.name);
						dom.attr('data-row-index', i);
						dom.attr('data-operation-index', j);
						dom.click(function(){
							var rowIndex = parseInt($(this).attr('data-row-index'));
							var operIndex = parseInt($(this).attr('data-operation-index'));
							_self.operations[operIndex].click(datas[rowIndex]);
							return false;
						});
						dom.appendTo(cell);
					}
				}
			}
		};
		this.format = function(value){
			if(value==null){
				return '';
			}
			var s = value + '';
			var re = /^-?\d{3,}\.?\d*$/;
			if(re.test(s)){
				return s.replace(/(\d{1,3})(?=(\d{3})+(?:$|\D))/g, "$1,");
			}
			return s;
		};
		$.extend(this, options);
	},
	Paging : function(root, options){
		this.root = root;
		this.input = null;
		this.endPage = 1;
		this.source = null;
		
		this.bindSource = function(source){
			this.source = source;
		};
		
		this.draw = function(data){
			var root = $(this.root);
			var total = data.paging.total;
			var rowsize = data.paging.rowsize;
			var endPage = Math.floor(total / rowsize);
			if(total % rowsize > 0){
				endPage += 1;
			}
			this.endPage = endPage;
			
			var _self = this;
			root.html('');
			
			var dom = $('<a href="#" class="paging_pre">'+i18n['sys.profile.behavior.prepage']+'</span>');
			dom.click(function(){
				_self.page(_self.source.params.pageNo-1);
				return false;
			});
			root.append(dom);
			
			$('<span>'+i18n['sys.profile.behavior.no']+'</span>').appendTo(root);
			dom = $('<input class="paging_input" value="'+this.source.params.pageNo+'">');
			dom.keypress(function(e){
				if(e.keyCode==13){
					_self.page(parseInt(_self.input.val(), 10));
					return false;
				}
			});
			this.input = dom;
			root.append(dom);
			$('<span> / '+endPage+i18n['sys.profile.behavior.page']+'</span>').appendTo(root);
			
			dom = $('<a href="#" class="paging_nxt">'+i18n['sys.profile.behavior.nxtpage']+'</span>');
			dom.click(function(){
				_self.page(_self.source.params.pageNo+1);
				return false;
			});
			root.append(dom);
		};
		this.page = function(pageNo){
			if(isNaN(pageNo) || pageNo<1 || pageNo>this.endPage){
				this.input.val(this.source.params.pageNo);
				return;
			}
			this.source.params.pageNo = pageNo;
			this.source.load();
		};
		$.extend(this, options);
	},
	SortButton : function(options){
		this.source = null;
		this.init = false;
		
		this.bindSource = function(source){
			this.source = source;
		};
		this.draw = function(){
			var _self = this;
			for(var i=0; i<options.buttons.length; i++){
				var button = options.buttons[i];
				var dom = $(button.dom);
				if(this.source.params.orderBy==button.key){
					dom.removeClass('sort_normal');
					if(this.source.params.desc){
						dom.addClass('sort_desc');
						dom.removeClass('sort_asc');
					}else{
						dom.removeClass('sort_desc');
						dom.addClass('sort_asc');
					}
				}else{
					dom.removeClass('sort_desc');
					dom.removeClass('sort_asc');
					dom.addClass('sort_normal');
				}
				if(!this.init){
					dom.attr('data-button-index', i);
					dom.click(function(){
						var index = parseInt($(this).attr('data-button-index'));
						var button = options.buttons[index];
						if(_self.source.params.orderBy==button.key){
							source.params.desc = !source.params.desc;
						}else{
							_self.source.params.orderBy=button.key
							source.params.desc = true;
						}
						source.params.pageNo = 1;
						_self.source.load();
					});
				}
			}
			this.init = true;
		};
	},
	Dialog : function(){
		this.loading = function(){
			var loading = '<div class="loading_content"> <div class="load_img"></div> </div>';
			var overlay = '<div class="loading_overlay" style="width: '
					     + $(document).width() + 'px; height: '
				      	 + $(document).height() + 'px;"></div>';
			$('body').append(loading).append(overlay);
			var width = 100;  
			var height = 100;  
			var left = ($(window).width() / 2) - (width / 2) + $(document).scrollLeft();  
			var top = ($(window).height() / 2) - (height / 2) + $(document).scrollTop();  
			$(".loading_content").css("top", top+"px").css("left",left+"px").css("display",'block'); 
		},
		this.hide = function(){
			if($(".loading_content").length >0){$(".loading_content").remove();}
			if($(".loading_overlay").length >0){$(".loading_overlay").remove();}
		};
	},
	url : function(){
		var param = "";
		for(var i = 1; i<arguments.length; i+=2){
			if(arguments[i+1]==null){
				continue;
			}
			param += "&" + arguments[i] + "=" + encodeURIComponent(arguments[i+1]);
		}
		var result = arguments[0];
		if(param==""){
			return result;
		}
		if(result.indexOf('?')==-1){
			return result + "?" + param.substring(1);
		}
		return result + param;
	},
	param : function(param, url){
		var re = new RegExp();
		re.compile("[\\?&]"+param+"=([^&]*)", "i");
		var arr = re.exec(url || location.href);
		if(arr==null){
			return null;
		}else if(arr[1]==''){
			return null;
		}else{
			return decodeURIComponent(arr[1]);
		}
	}
};
window[arguments[0]] = ui;})("sui");