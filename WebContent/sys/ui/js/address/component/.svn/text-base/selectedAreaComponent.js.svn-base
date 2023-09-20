/**
 * 已选区域组件
 */
define(function(require, exports, module) {
	
	var $=require("resource/js/jquery-ui/jquery.ui");
	var base = require('lui/base');
	var layout = require('lui/view/layout');
	var topic = require('lui/topic');
	var lang = require('lang!sys-ui');
	var constant = require('lui/address/addressConstant');
	var dingAddressUtil = require('lui/address/dingAddressUtil');
	var dialog = require('lui/dialog');
	
	var SelectedAreaComponent = base.Container.extend({
		initProps : function($super, cfg){
			this.parent = cfg.parent;
			this.addresscfg = this.parent.addresscfg;
			this.params = this.parent.params;
			this.optionType = this.parent.optionType;
			this.startup();
		},
		startup : function(){
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('../tmpl/address-selectedbox.jsp#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
			topic.subscribe( constant.event['ADDRESS_ELEMENT_SELECT'],this.handleSelectElement,this);
			topic.subscribe( constant.event['ADDRESS_ELEMENT_REMOVE'],this.handleRemoveElement,this);
			topic.subscribe( constant.event['ADDRESS_ELEMENT_CLEAR'],this.handleClearElement,this);
			topic.subscribe( constant.event['ADDRESS_TAB_SELECTED'],this.selectChanged,this);
		},
		doLayout:function(obj){
			this.element.html($(obj));
			this.unLoadData = [];
			var self = this;
			
			if(this.params.valueData){
				var __kmsData = this.params.valueData;
				if(__kmsData!=null){
					var ids = '';
					for(var i =0;i<__kmsData.data.length;i++){
						if(__kmsData.data[i].id){
							ids += __kmsData.data[i].id + ';';
						}
					}
					if(ids.length > 0){
						var isHandleFinished = false;
						var loadingDialog = null;
						// 执行时间超过500毫秒需要显示loading加载遮罩层
						setTimeout(function(){
							if(!isHandleFinished){
								loadingDialog = dialog.loading();					
							}
						},500);
						
						ids = ids.substring(0,ids.length - 1);
						var kmssData = new KMSSData();
						kmssData.AddHashMap({
							ids : ids
						});
						kmssData.SendToBean('organizationDialogSearch', function(rtnData){
							self.handleSelectElement(rtnData.data);
							// 关闭loading遮罩层
							if(loadingDialog){
								loadingDialog.hide();
							}
							isHandleFinished = true;
						});
					}
				}
			}
			
			//清空按钮
			var delAll = this.element.find('[data-lui-mark="lui-address-selectedbox-delall" ]');
			if(!this.params.notNull){
				delAll.click(function(){
					topic.publish( constant.event['ADDRESS_ELEMENT_CLEAR']);
				});
			}else{
				delAll.remove();
			}
			
			var dndContainer = this.element.find('[data-lui-mark="lui-address-selectedbox-ul"]'),
				beginIndex = 0, endIndex = 0;
			
			setTimeout(function(){
				//拖拽排序初始化
				dndContainer.sortable({
					placeholderType : 'placeholder',
					distance : 5,
					delay : 100,
					scroll : false,
					start : function(event, ui){
						var $element = ui.item;
						beginIndex = $element.index();
						self.isdraging = true; 
					},
					stop : function(event, ui){
						var movingOut = ui.position.top <= -20,
							$element = ui.item;
						if(movingOut){
							var selectId = $element.attr('data-lui-selectid'),
								removeList = [ self.getElement(selectId) ];
							self.handleRemoveElement(removeList);
						}else{
							var endIndex =  $element.index();
							self.handleSwapElement(beginIndex,endIndex);
							$element.find('.lui-address-selectedbox-del').hide();
						}
						self.isdraging = false;
					}
				});
				//是否显示清空按钮
				self.toggleDelAll();
			},1);
			
			var self = this;
			$(this.element).unbind('scroll').scroll(function(){
				var ul= self.element.find('[data-lui-mark="lui-address-selectedbox-ul"]');
				if(ul.height() - $(this).height() > $(this).scrollTop() + 50 ){
					return;
				}
				self.handleLoadMoreElement();
			});
			
		},
		selectIds : '',
		selectList : [],
		getIndex : function($elements,$element){
			var index = 0;
			if($elements){
				for(var i  = 0;i < $elements.length; i++){
					if($elements.eq(i).attr('data-lui-selectid') == $element.attr('data-lui-selectid'))
						return i;
				}
			}
			return index;
		},
		getElement : function(selectId){
			for(var i = 0;i < this.selectList.length;i++){
				if(this.selectList[i].id == selectId){
					return this.selectList[i];
				}
			}
			return null;
		},
		handleSelectElement : function(list){
			if(this.optionType == 'sgl'){
				this.selectIds = '';
				this.selectList = [];
				this._removeAllElement();
				list.length = 1; // 如果是单选，这里只会选中第一条记录
			}

			// 取弹出框的尺寸
			var size = dialog.getSizeForAddress();
			var _selectedBoxdomHeight;
			// 计算已选区域的高度
			if(this.element.hasClass("selectedbox-for-common")) {
				_selectedBoxdomHeight = size.height - 200;
			} else {
				_selectedBoxdomHeight = size.height - 230;
			}
			
			// 根据已选区域的高度来计算需要显示的数据数量
			var _rows = Math.ceil(_selectedBoxdomHeight/72.0);
			var _total = _rows * 10;
			
			for(var i=0;i<list.length;i++){
				var reg = new RegExp();
				reg.compile("(^"+list[i].id+";)|(;"+list[i].id+";)|(^"+list[i].id+"$)|(;"+list[i].id+"$)");
				
				if( !reg.test(this.selectIds) && list[i].id){
					this.selectList.push( list[i] );
					this.selectIds += list[i].id + ';';
					if(i < _total && this.unLoadData.length == 0){
						this._generateElement(list[i]);
					}else{
						this.unLoadData.push(list[i]);
					}
				}
			}
			topic.publish( constant.event['ADDRESS_ELEMENT_UPDATE'],{
				selectIds : this.selectIds,
				selectList : this.selectList
			});
			
			//重绘拖拽
			var dndContainer = this.element.find('[data-lui-mark="lui-address-selectedbox-ul"]');
			dndContainer.sortable();	
			
			//是否显示清空按钮
			this.toggleDelAll();
			
		},
		handleLoadMoreElement : function(){
			var length = this.unLoadData.length > 10 ? 10 : this.unLoadData.length;
			if(length > 0){
				for(var i = 0;i < length;i++){
					var data = this.unLoadData.shift();
					this._generateElement(data);
				}
				//重绘拖拽
				var dndContainer = this.element.find('[data-lui-mark="lui-address-selectedbox-ul"]');
				dndContainer.sortable();	
			}
		},
		handleRemoveElement : function(list){
			var removeIds = '',
				newSelectList = [];
			for(var i=0;i<list.length;i++){
				var reg = new RegExp();
				reg.compile("(^"+list[i].id+";)|(;"+list[i].id+";)|(^"+list[i].id+"$)|(;"+list[i].id+"$)");
				
				if( reg.test(this.selectIds) && list[i].id ){
					removeIds += list[i].id+';';
					this.selectIds = this.selectIds.replace(reg,';');
					this._removeElement(list[i].id);
				}
			}
			for(var j=0;j<this.selectList.length;j++){
				var reg = new RegExp();
				reg.compile("(^"+this.selectList[j].id+";)|(;"+this.selectList[j].id+";)|(^"+this.selectList[j].id+"$)|(;"+this.selectList[j].id+"$)");
				if( !reg.test(removeIds) ){
					newSelectList.push(this.selectList[j]);
				}
			}
			this.selectList = newSelectList;
			topic.publish( constant.event['ADDRESS_ELEMENT_UPDATE'],{
				selectIds : this.selectIds,
				selectList : this.selectList
			});
			
			//重绘拖拽
			var dndContainer = this.element.find('[data-lui-mark="lui-address-selectedbox-ul"]');
			dndContainer.sortable();
			
			//是否显示清空按钮
			this.toggleDelAll();
			
		},
		handleClearElement : function(){
			this.selectIds = '';
			this.selectList = [];
			this._removeAllElement();
			topic.publish( constant.event['ADDRESS_ELEMENT_UPDATE'],{
				selectIds : this.selectIds,
				selectList : this.selectList
			});
			//是否显示清空按钮
			this.toggleDelAll();
		},
		handleSwapElement : function(aIndex,bIndex){
			//向后移
			if(aIndex < bIndex){
				var tmp =  this.selectList[aIndex];
				for(var i = aIndex;i < bIndex;i++){
					this.selectList[i] = this.selectList[i+1];
				}
				this.selectList[bIndex] = tmp;
			}
			//向前移
			if(aIndex > bIndex){
				var tmp =  this.selectList[aIndex];
				for(var i = aIndex;i > bIndex;i--){
					this.selectList[i] = this.selectList[i-1];
				}
				this.selectList[bIndex] = tmp;
			}
			topic.publish( constant.event['ADDRESS_ELEMENT_UPDATE'],{
				selectIds : this.selectIds,
				selectList : this.selectList
			});
		},
		_generateElement : function(element){
			var selectedboxUl = this.element.find('[data-lui-mark="lui-address-selectedbox-ul"]'),
				li = $('<li draggable="false" />'),
				del = $('<div class="lui-address-selectedbox-del" />'),
				self = this;
			if((element.orgType != ORG_TYPE_PERSON) && element.orgType){
				element.img = constant.image[element.orgType];
				if (element.isExternal && element.isExternal == "true") {
					element.img = constant.image[parseInt(element.orgType) + 100];
				}
				//钉钉头像
				if (element.dingImg == "true") {
					element.img = constant.dingImage[element.orgType];
					if (element.isExternal && element.isExternal == "true") {
						element.img = constant.dingImage[parseInt(element.orgType) + 100];
					}
				}
			}
			//钉钉头像
			if (element.dingImg === "true" && element.orgType == ORG_TYPE_PERSON && !element.img) {
				var img = dingAddressUtil.generateDefaultDingImg(element.name);
			} else {
				var img = "<img src='"+element.img+ "' />";
			}
			li.attr('data-lui-selectid',element.id)//ID
				.attr('title',element.info)//详情
					.append(del)//删除按钮
						.append($('<div class="lui-address-selectedbox-img">' + img + '<div class="lui-address-overlay-g"/></div>'))//头像
							.append( $('<div class="name"/>').text(element.name) )//名字
								.dblclick(function(evt){//双击删除该元素
									var selectId = element.id,
										removeList = [ self.getElement(selectId) ];
									self.handleRemoveElement(removeList); 
								}).mouseover(function(){
									if(!self.isdraging)
										del.show();//显示删除按钮
								}).mouseout(function(){
									del.hide();//隐藏删除按钮
								});
			//删除操作
			del.click(function(event){
				event.stopPropagation();
				var selectId = element.id,
				removeList = [ self.getElement(selectId) ];
				self.handleRemoveElement(removeList); 
			});
			selectedboxUl.append(li);
		},
		_removeElement : function(elementid){
			var li = this.element.find('[data-lui-selectid="'+elementid+'"]');
			if(li.length > 0){
				li.remove();
			}else{
				for(var i = 0;i < this.unLoadData.length;i++){
					var data = this.unLoadData[i];
					if(data.id == elementid){
						this.unLoadData.splice(i,1);
						break;
					}
				}
			}
			this.handleLoadMoreElement();
		},
		_removeAllElement : function(){
			var li = this.element.find('[data-lui-selectid]');
			li.remove();
			this.unLoadData = [];
		},
		//是否显示清空按钮(当已选列表中没有元素不显示)
		toggleDelAll : function(){
			var delall = this.element.find('[data-lui-mark="lui-address-selectedbox-delall"]');
			if(delall.length == 0){
				return;
			}
			if(this.selectList.length == 0){
				delall.hide();
			}else{
				delall.show();
			}
		},
		selectChanged : function(evt){
			//#21667 解决切换到搜索页签后已选列表框高度不足问题
			var tabId = evt.id || '';
			if(tabId == 'address.tabs.search'){
				this.element.addClass('selectedbox-for-search');
				this.element.removeClass('selectedbox-for-common');
			}else{
				this.element.addClass('selectedbox-for-common');
				this.element.removeClass('selectedbox-for-search');
			}
		}
	});
	
	module.exports = SelectedAreaComponent;
	
});