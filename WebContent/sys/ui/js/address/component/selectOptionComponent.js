/**
 * 待选列表组件
 */
define(function(require, exports, module) {
	
	var base = require('lui/base');
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	var dialog = require('lui/dialog');
	var orglang = require('lang!sys-organization');
	var lang = require('lang!sys-ui');
	var layout = require('lui/view/layout');
	var constant = require('lui/address/addressConstant');
	var dingAddressUtil = require('lui/address/dingAddressUtil');
	
	var rowsize = 20;

	var SelectOptionComponent = base.Component.extend({
		
		selectIds : '',
		
		initProps : function($super, _config) {
			$super(_config);
			this.refName = _config.refName;
			this.ancestor = _config.ancestor;
			this.searchSize = _config.searchSize || Number.MAX_VALUE;
			this.canSelect = _config.canSelect;
		}, 
		
		draw: function($super){
			var self = this;
			this.generateSelectFilter(this.element);
			this.generateSelectOpt();
			$super();
		},
		
		generateSelectOpt : function(){
			topic.subscribe( constant.event['ADDRESS_ELEMENT_UPDATE'] ,this.handleElementUpdate,this);
			topic.channel(this.refName).subscribe(constant.event['ADDRESS_DATASOURCE_CHANGED'],this.handleDataChange,this);
			var addressSelectedBox = this.ancestor ? this.ancestor.selectedBox : null;
			if(addressSelectedBox){
				this.selectIds = addressSelectedBox.selectIds || '';
			}
		},
		
		handleElementUpdate:function(evt){
			this.selectIds = evt.selectIds;
			var ul = $(this.element).find('.lui-address-panel-selectlist'),
				li = ul.find('li'),
				self = this;
			//刷新checkbox
			li.each(function(index,element){
				var box = $(this).find('.radiobox,.checkbox'),
					selectiId = box.attr('data-lui-selectid');
				if( box.hasClass('checkboxAll') )//全选不处理
					return;
				
				var reg = new RegExp();
				reg.compile("(^"+selectiId+";)|(;"+selectiId+";)|(^"+selectiId+"$)|(;"+selectiId+"$)");
				
				if(self.selectIds && reg.test(self.selectIds) ){
					box.addClass('selected checked');
				}else{
					box.removeClass('selected checked');
				}
			});
			self.refreshMulAll();//重新刷新checkbox选项
		},
		
		handleDataChange:function(args){
			this.sourceComponent  = args.sourceComponent;
			var params = this.parent.params,
				optionType = params.optionType
				kmssData = args.data;
			if(kmssData!=null){
				kmssData = kmssData.Format("id:name:info", null, false);
				if(!(kmssData instanceof KMSSData))
					kmssData = new KMSSData(kmssData);
				var exceptValue = params.exceptValue;
				if(exceptValue!=null){
					if(!Array.isArray(exceptValue) || exceptValue[0]==null){
						var newArr = new Array;
						newArr[0] = exceptValue;
						exceptValue  = newArr;
					}
					for(var i=0; i<exceptValue.length; i++){
						var j = kmssData.IndexOf("id", exceptValue[i]);
						if(j>-1){
							kmssData.Delete(j);
						}
					}
				}
				kmssData = kmssData.Parse();
				kmssData.UniqueTrimByKey('id', 'id:name');
				if(optionType == 'mul'){
					this.generateMulList(kmssData);
				}else{
					this.generateSglList(kmssData);
				}
			}
		},
		
		generateSglList : function(kmssData){
			this.allData = kmssData.data;//全部元素
			this.undrawData = [];//待渲染元素
			
			if(this.element != null){
				var originalUl = $(this.element).find('.lui-address-panel-selectlist');
				if(originalUl.length > 0){
					originalUl.remove();
				}
				var ul = $('<ul class="lui-address-panel-selectlist"/>');
				if(this.parent.selectAreaHeight) {
					ul.css("height", this.parent.selectAreaHeight - 40);
				}
				ul.appendTo($(this.element));
				// 重置显示的记录数量
				this.resetRowsize(ul);
				
				if(this.allData.length > this.searchSize){
					this.allData = this.allData.slice(0,this.searchSize);
					var tip = orglang['sysOrg.address.search.selectoption.limit'].replace(/\{0\}/g,this.searchSize );
					if(!window.___landrayAddress___.hasShowlimitTip){
						dialog.success(tip);
						window.___landrayAddress___.hasShowlimitTip = true;
					}
				}
				
				//操作栏
				ul.append(this.generateOpt());
				for(var i = 0;i < this.allData.length;i++){
					if(i >= rowsize){
						this.undrawData.push(this.allData[i]);
					}else{
						ul.append(this.generateSglItem(this.allData[i]));
					}
				}
				//数据为空,添加一个暂无数据的页面
				if(this.allData.length == 0){
					var li = $('<li class="noData" />');
					li.append('<div class="noData-img" />');
					li.append('<p class="noData-title" >'+ lang['address.noData'] +'</p>');
					ul.append(li);
				}
				var self = this;
				$(this.element).unbind('scroll').scroll(function(){
					if(ul.height() - $(this).height() > $(this).scrollTop() + 50 ){
						return;
					}
					var length = self.undrawData.length > rowsize ? rowsize : self.undrawData.length;
					if(length > 0){
						for(var i = 0;i < length;i++){
							var data = self.undrawData.shift();
							ul.append(self.generateSglItem(data));
						}
					}
				});
				$(this.element).scrollTop(0);
			}
		},
		
		generateSglItem : function(item){
			var self = this;
			var li = $('<li/>'),
				radiobox = $('<span class="radiobox"/>').attr('data-lui-selectid',item.id),
				text = $('<div class="name"/>'),
				name = $('<span class="nameText"/>').text(item.name),//名字
				staffingLevel = $('<div class="staffingLevel"/>').text(item.staffingLevel),//职级
				dept = $('<div class="dep"/>');//部门
			if(item.orgType != ORG_TYPE_PERSON){
				item.img = constant.image[item.orgType];
				if (item.isExternal && item.isExternal == "true") {
					item.img = constant.image[parseInt(item.orgType) + 100];
				}
				//钉钉头像
				if (item.dingImg == "true") {
					item.img = constant.dingImage[item.orgType];
					if (item.isExternal && item.isExternal == "true") {
						item.img = constant.dingImage[parseInt(item.orgType) + 100];
					}
				}
			}
			var img = $('<div class="lui-address-imgcontainer-sm" />');
			//钉钉头像
			if (item.dingImg === "true" && item.orgType == ORG_TYPE_PERSON && !item.img) {
				img.append(dingAddressUtil.generateDefaultDingImg(item.name));
			} else {
				img.append($('<img src="' + item.img +'" />'));
			}
			img.append($('<div class="lui-address-overlay-sm"/>'));
			
			text.append(name);
			
			if(item.parentName){
				text.addClass('name-person');
				dept.text(item.parentName);
			}
			
			if(item.staffingLevel){
				text.addClass('name-person');
				text.append(staffingLevel);
				if(!item.parentName){
					text.addClass('has-staffLevel');
				}
			}else{
				text.addClass('no-staffLevel');
			}
			
			var reg = new RegExp();
			reg.compile("(^"+item.id+";)|(;"+item.id+";)|(^"+item.id+"$)|(;"+item.id+"$)");
			
			if( reg.test(this.selectIds) ){
				radiobox.addClass('selected');
			}
			li.click(this,function(evt){
				var cb = $(this).find('.radiobox'),
					selectiId = cb.attr('data-lui-selectid'),
					self = evt.data,
					list = [];
				list.push(self.getSelectDataById(selectiId));
				var radioboxs =  $(self.element).find('.radiobox');
				radioboxs.removeClass('selected');
				cb.addClass("selected");
				topic.publish(constant.event['ADDRESS_ELEMENT_SELECT'],list);
				
			}).attr('title',item.info)
				.append(radiobox)
					.append(img)
						.append(text)
							.append(dept);
			
			if(item.isAvailable == 'false'){
				li.append('<div class="lui_address_isAvailable_false" />')
			}
			
			li.click(this,function(evt){
				topic.publish( constant.event['ADDRESS_ELEMENT_SAVE']);
			});
			
			return li;
		},
		
		generateMulList : function(kmssData){
			this.allData = kmssData.data;//全部元素
			this.undrawData = [];//待渲染元素
			
			if(this.element != null){
				var originalUl = $(this.element).find('.lui-address-panel-selectlist');
				if(originalUl.length > 0){
					originalUl.remove();
				}
				var ul = $('<ul class="lui-address-panel-selectlist"/>');
				if(this.parent.selectAreaHeight) {
					ul.css("height", this.parent.selectAreaHeight - 40);
				}
				ul.appendTo($(this.element));
				// 重置显示的记录数量
				this.resetRowsize(ul);
				
				if(this.allData.length > this.searchSize){
					this.allData = this.allData.slice(0,this.searchSize);
					var tip = orglang['sysOrg.address.search.selectoption.limit'].replace(/\{0\}/g,this.searchSize );
					if(!window.___landrayAddress___.hasShowlimitTip){
						dialog.success(tip);
						window.___landrayAddress___.hasShowlimitTip = true;
					}
				}
				//操作栏
				ul.append(this.generateOpt());
				if(this.allData.length > 0){
					
					for(var i=0;i<this.allData.length;i++){
						if(i >= rowsize){
							this.undrawData.push(this.allData[i]);
						}else{
							ul.append(this.generateMulItem(this.allData[i]));
						}
					}
				}else{
					var li = $('<li class="noData" />');
					li.append('<div class="noData-img" />');
					li.append('<p class="noData-title" >'+ lang['address.noData'] +'</p>');
					ul.append(li);
				}
			}
			
			this.refreshMulAll();//重新刷新checkbox选项
			
			var self = this;
			$(this.element).unbind('scroll').scroll(function(){
				if(ul.height() - $(this).height() > $(this).scrollTop() + 5 ){
					return;
				}
				var length = self.undrawData.length > rowsize ? rowsize : self.undrawData.length;
				if(length > 0){
					for(var i = 0;i < length;i++){
						var data = self.undrawData.shift();
						ul.append(self.generateMulItem(data));
					}
				}
			});
			$(this.element).scrollTop(0);
			
		},
		// 重置显示的记录数量
		resetRowsize : function(ul){
			// 在某些超高分辨率的显示器下，待选列表的空间会很大，如果还是只显示20条记录，会出现多于20条的记录无法显示（没有出现滚动条）
			// 所以这种情况要判断待选区域的空间高度，动态计算一下最多能显示多少条记录
			// 这里假定每条记录的高度是40PX
			var li_h = 40;
			// 计算一下原来给的20条记录能占用多少PX的高度
			var data_height = (rowsize / 2) * li_h;
			// 计算一下显示完20条记录后，还有多少剩余空间
			var ____height = ul.height() - data_height;
			// 如果还有剩余空间，再根据剩余空间计算一下能显示多少条记录，同时修改一下总显示的记录数
			if(____height > 0) {
				rowsize += (parseInt(____height / li_h) + 1) * 2;
			}
		},
		
		refreshMulAll : function(){
			var params = this.parent.params,
				optionType = params.optionType;
			if(optionType == 'mul' && this.allData){
				var index= 0,
					reg = new RegExp();
				for(index = 0;index < this.allData.length;index++){
					var item = this.allData[index];
					reg.compile("(^"+item.id+";)|(;"+item.id+";)|(^"+item.id+"$)|(;"+item.id+"$)");
					if( !reg.test(this.selectIds) ){
						break;
					}
				}
				var selectAll = $(this.element).find('.checkboxAll');
				if(index < this.allData.length){
					selectAll.removeClass('checked');
				}else{
					selectAll.addClass('checked');
				}
			}
		},
		
		generateMulItem : function(item){
			var li = $('<li/>'),
				checkbox = $('<span class="checkbox" />').attr('data-lui-selectid',item.id),
				text = $('<div class="name" />'),
				name = $('<span class="nameText" />').text(item.name),
				staffingLevel = $('<div class="staffingLevel"/>').text(item.staffingLevel),
				dept = $('<div class="dep"/>');
			if(item.orgType != ORG_TYPE_PERSON){
				item.img = constant.image[item.orgType];
				if (item.isExternal && item.isExternal == "true") {
					item.img = constant.image[parseInt(item.orgType) + 100];
				}
				//钉钉头像
				if (item.dingImg == "true") {
					item.img = constant.dingImage[item.orgType];
					if (item.isExternal && item.isExternal == "true") {
						item.img = constant.dingImage[parseInt(item.orgType) + 100];
					}
				}
			}
			//钉钉头像
			if (item.dingImg === "true" && item.orgType == ORG_TYPE_PERSON && !item.img) {
				var img = dingAddressUtil.generateDefaultDingImg(item.name);
			} else {
				var img = $('<div class="lui-address-imgcontainer-sm"><img src="'+item.img+'" /><div class="lui-address-overlay-sm"/></div>');
			}
			
			text.append(name);
			
			if(item.parentName){
				text.addClass('name-person');
				dept.text(item.parentName);
			}
			
			if(item.staffingLevel){
				text.addClass('name-person');
				text.append(staffingLevel);
				if(!item.parentName){
					text.addClass('has-staffLevel');
				}
			}else{
				text.addClass('no-staffLevel');
			}
			
			var reg = new RegExp();
			reg.compile("(^"+item.id+";)|(;"+item.id+";)|(^"+item.id+"$)|(;"+item.id+"$)");
			
			if( reg.test(this.selectIds) ){
				checkbox.addClass('checked');
			}
			li.click(this,function(evt){
				var cb = $(this).find('.checkbox'),
					selectiId = cb.attr('data-lui-selectid'),
					self = evt.data,
					list = [];
				list.push(self.getSelectDataById(selectiId));
				if ($(cb).is(".checked")) {
					$(cb).removeClass("checked");
					topic.publish(constant.event['ADDRESS_ELEMENT_REMOVE'],list);
				}else{
					$(cb).addClass("checked");
					topic.publish(constant.event['ADDRESS_ELEMENT_SELECT'],list);
				}
				self.refreshMulAll();//重新刷新全选checkbox
			}).attr('title',item.info)
				.append(checkbox)
					.append(img)
						.append(text)
							.append(dept);
			
			if(item.isAvailable == 'false'){
				li.append('<div class="lui_address_isAvailable_false" />')
			}
			
			return li;
		},
		
		generateOpt : function(){
			var li = $('<li class="selectAll"/>'),
				params = this.parent.params,
				optionType = params.optionType
			if(this.allData.length == 0 || optionType != 'mul')
				return li;
			var checkbox = $('<span class="checkbox checkboxAll" />'),
				text = $('<div class="name">'+lang['ui.listview.selectall']+'</div>');
			li.click(this,function(evt){
				loadingDialog = dialog.loading();
				var self = evt.data,
					cbs = self.element.find('[data-lui-selectid]'),
					cb = $(this).find('.checkbox');
				if ($(cb).is(".checked")) {
					$(cb).removeClass("checked");
					cbs.removeClass("checked");
					topic.publish( constant.event['ADDRESS_ELEMENT_REMOVE'] , self.allData);
				}else{
					$(cb).addClass("checked");
					cbs.addClass("checked");
					topic.publish( constant.event['ADDRESS_ELEMENT_SELECT'] , self.allData);
				}
				if(loadingDialog){
					loadingDialog.hide();
				}
			}).append(checkbox)
				.append(text);
			return li;
		},
		
		//#23220 待选列表新增按类型的过滤
		generateSelectFilter : function(container){
			if(!this.canSelect)
				return;
			var self = this;
			var orgTypefilterLayout = new layout.Template({
				src : require.resolve('../tmpl/address-orgType-filter.jsp#'),
				parent : this
			});
			orgTypefilterLayout.startup();
			orgTypefilterLayout.get(this,function(obj){
				var $obj = $(obj);
				// 如果待选列表类型过滤只有一种类型，则隐藏过滤
				// 下拉框默认有“全部”，所以，当选项值少3个时，不显示
				if($obj.find("option").length < 3) {
					return;
				}
				$(container).append($obj);
				var orgTypeFilter = self.orgTypeFilter || -1,
					option = $obj.find('option[value="' + orgTypeFilter +'"]');
				if(option.length > 0){
					option[0].selected = true;
				}
				$obj.click(function(event){
					event.stopPropagation();
				});
				$obj.change(function(event){
					var target = event.target,
						value = target.value;
					self.orgTypeFilter = value;
					topic.channel(self.refName).publish(constant.event['ADDRESS_ORGTYPEFILTER_CHANGE'],{
						orgTypeFilter : value
					} );
					if(self.sourceComponent && self.sourceComponent.reload){
						self.sourceComponent.reload();
					}
				});
			});
		},
		
		getSelectDataById : function(selectid){
			for(var i=0;i<this.allData.length;i++){
				var __data = this.allData[i];
				if(__data.id == selectid){
					return __data;
				}
			}
			return null;
		}
		
	});
	
	module.exports = SelectOptionComponent;
	
});

