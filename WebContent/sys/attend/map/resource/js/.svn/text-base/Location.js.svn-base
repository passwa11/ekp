define( function(require, exports, module) {
	
	var base = require('lui/base');
	var $ = require("lui/jquery");
	var layout = require('lui/view/layout');
	var toolbar = require("lui/toolbar");
	var dialog = require("lui/dialog");
	var topic = require('lui/topic');
	var strategys =  require('sys/attend/map/resource/js/LocationStrategy.js');
	var MapUtil = require('./common/MapUtil');
	var lang = require('lang!sys-attend');
	require('../css/location.css');
	
	var Location = base.Component.extend({
		
		className: 'lui_location_base',
		
		strategy : 'bmap',
		mapKey : MapUtil.bMapKey,
		mapKeyPc: MapUtil.bMapKey,
		mapKeyPcSecurityKey: '',

		initProps : function($super, cfg) {
			this.uuId = 'map' + new Date().getTime();//唯一标识符
			this.fullScreen = false;//是否全屏显示(该参数暂时未用到)
			this.propertyName = this.config.propertyName;//地图名称
			this.propertyCoordinate = this.config.propertyCoordinate;//地图坐标
			this.nameValue = this.config.nameValue;//地图名称值
			this.coordinateValue = this.config.coordinateValue;//地图坐标值
			this.propertyDetail = this.config.propertyDetail;//详细地址
			this.detailValue = this.config.detailValue;//详细地址值
			this.propertyProvince=this.config.propertyProvince;//省
			this.provinceValue=this.config.provinceValue;
			this.propertyCity=this.config.propertyCity;//市
			this.cityValue=this.config.cityValue;
			this.propertyDistrict=this.config.propertyDistrict;//区
			this.districtValue=this.config.districtValue;
			this.showStatus = this.config.showStatus;
			this.edit = this.showStatus === 'edit';
			this.isDetailed = this.config.propertyDetail ? true : false;
			this.defaultValue = this.config.defaultValue;
			$super(cfg);
		},
		
		startup : function($super){
			var self = this;
			$super();
			topic.channel(this.uuId).subscribe('sys.attend.map.data.change',function(evt){
				var nameValue = evt.nameValue,
					coordinateValue = evt.coordinateValue,
					detailValue = evt.detailValue;
				self.nameValuedom.html(nameValue).attr('title',nameValue);
    			self.coordinateValuedom.val(coordinateValue);
				self.detailValueDom.html(detailValue).attr('title',detailValue);
				if(self.propertyProvince){
					self.provinceValuedom.val(evt.province);
				}
				if(self.propertyCity){
					self.cityValuedom.val(evt.city);
				}
				if(self.propertyDistrict){
					self.districtValuedom.val(evt.district);
				}
			});
		},
		
		draw : function() {
			if(this.isDrawed)
				return;
			var showStatus = this.showStatus;
			var setBuildName = 'build' + showStatus.substring(0,1).toUpperCase() + showStatus.substring(1);
			this[setBuildName] ? this[setBuildName]() : '';
			// 初始化
			this.initDefaultValue();
			this.isDrawed = true;
			return this;
		},

		// 获取初始值（获取当前位置）
		initDefaultValue: function() {
			var self = this;
			if (!this.showStatus){
				this.showStatus = Com_GetUrlParameter(window.location,'method');
			}
			if(this.defaultValue == 'PERSON_POSITION' && this.showStatus != 'view') {
				// 监听初始化回调事件（防止同一个页面有多个控件时，并发请求当前位置）
				topic.subscribe("MAP_INIT_DEFAULT_VALUE", function(data) {
					self.coordinateDom.val(data.coordinateValue);
					self.propertyDom.val(data.nameValue);
					self.nameValuedom.html(data.nameValue);
					self.detailDom.val(data.detailValue);
					self.provinceDom.val(data.provinceValue);
					self.cityDom.val(data.cityValue);
					self.districtDom.val(data.districtValue);
				});
				if(window.initMapLocation) {
					// 同一个页面，不管有多少个地图控件，只需要初始化一次
					return;
				}
				window.initMapLocation = true;
				MapUtil.getCurrentMap(function(result) {
					if(result && result.mapType){
						self.strategy=result.mapType;
						self.mapKey = result.mapKey;
						self.mapKeyPc = result.mapKeyPc;
					}
					var StrategyMap = strategys[self.strategy];
					if(StrategyMap) {
						var map = new StrategyMap({
							context : self
						});
						// 获取当着位置，用于地图控件初始化
						map.getInitLocation(function(data) {
							// 发布事件，防止同一个页面有多个地图时，有些地图请求失败而无法填充默认值
							topic.publish("MAP_INIT_DEFAULT_VALUE", data);
						});
					}
				});
			}
		},
		
		buildEdit : function() {
			var self = this,
				config = this.config;
			//容器
			var containerDom = $('<div class="inputselectsgl" />').addClass('lui-location-inputselectsgl');
			//字段
			var inputContainer = $('<div class="input"></div>');

			var propertyDom  = this.propertyDom = $('<input type="text" />').attr('name',this.propertyName).val(this.nameValue).appendTo(inputContainer);
			
			if(config.isModify=='false'){
		    	  //不允许修改
				propertyDom.attr('readOnly', true);
		      }
			if(config.validators) {
				propertyDom.attr('validate', config.validators);
			}
			if(config.subject) {
				propertyDom.attr('subject', config.subject);
			}
			if(config.placeholder) {
				propertyDom.attr('placeholder', config.placeholder);
			}
			this.coordinateDom = $('<input type="hidden" />').attr('name',this.propertyCoordinate).val(this.coordinateValue).appendTo(inputContainer);
			if(this.isDetailed){
				this.detailDom = $('<input type="hidden" />').attr('name',this.propertyDetail).val(this.detailValue).appendTo(inputContainer);
			}
			if(this.propertyProvince){
				this.provinceDom = $('<input type="hidden" />').attr('name',this.propertyProvince).val(this.provinceValue).appendTo(inputContainer);
			}
			if(this.propertyCity){
				this.cityDom = $('<input type="hidden" />').attr('name',this.propertyCity).val(this.cityValue).appendTo(inputContainer);
			}
			if(this.propertyDistrict){
				this.districtDom = $('<input type="hidden" />').attr('name',this.propertyDistrict).val(this.districtValue).appendTo(inputContainer);
			}
			//图标
			var iconDom = $('<div class="lui_map_icon"></div>');
			
			containerDom.append(inputContainer);
			containerDom.append(iconDom);
			this.element.append(containerDom);
			
			iconDom.on('click', function() {
				MapUtil.getCurrentMap(function(result){
					if(result && result.mapType){
						self.strategy=result.mapType;
						self.mapKey = result.mapKey;
						self.mapKeyPc = result.mapKeyPc;
						self.mapKeyPcSecurityKey = result.mapKeyPcSecurityKey;
					}
					self.openMapDialog();
				},function(){
					self.openMapDialog();
				});
			});
			this.element.show();
		},
		
		buildView : function() {
			var self = this;
			//容器
			var containerDom = $('<div />').css({
				"position" :"relative",
			});
			//文字
			this.nameValuedom = $('<span/>').html(this.nameValue).appendTo(containerDom);
			//var labelDom = $('<span/>').html(this.nameValue).appendTo(containerDom);
			//图标
			if(this.coordinateValue){//坐标不为空才显示坐标
				var iconDom = $('<div class="lui_map_icon"></div>').addClass('view').appendTo(containerDom);
			}
			//字段
			var inputContainer = $('<div class="input"></div>').appendTo(containerDom);
			this.propertyDom = $('<input type="hidden" />').attr('name', this.propertyName).val(this.nameValue).appendTo(inputContainer);
			this.coordinateDom = $('<input type="hidden" />').attr('name', this.propertyCoordinate).val(this.coordinateValue).appendTo(inputContainer);
			if(this.isDetailed){
				this.detailDom = $('<input type="hidden" />').attr('name',this.propertyDetail).val(this.detailValue).appendTo(inputContainer);
			}
			if(this.propertyProvince){
				this.provinceDom = $('<input type="hidden" />').attr('name',this.propertyProvince).val(this.provinceValue).appendTo(inputContainer);
			}
			if(this.propertyCity){
				this.cityDom = $('<input type="hidden" />').attr('name',this.propertyCity).val(this.cityValue).appendTo(inputContainer);
			}
			if(this.propertyDistrict){
				this.districtDom = $('<input type="hidden" />').attr('name',this.propertyDistrict).val(this.districtValue).appendTo(inputContainer);
			}
			this.element.append(containerDom);
			if(this.coordinateValue) {//坐标不为空才能打开地图
				containerDom.css("cursor","pointer");
				containerDom.on('click', function() {
					MapUtil.getCurrentMap(function(result){
						if(result && result.mapType){
							self.strategy=result.mapType;
							self.mapKey = result.mapKey;
							self.mapKeyPc = result.mapKeyPc;
							self.mapKeyPcSecurityKey = result.mapKeyPcSecurityKey;
						}
						self.openMapDialog();
					},function(){
						self.openMapDialog();
					});
				});
			}
			this.element.show();
		},
		
		buildReadOnly : function() {
			this.buildView();
		},
		
		buildHidden : function() {
			var self = this;
			this.propertyDom = $('<input type="hidden" />').attr('name',this.propertyName).val(this.nameValue).appendTo(this.element);
			this.coordinateDom = $('<input type="hidden" />').attr('name',this.propertyCoordinate).val(this.coordinateValue).appendTo(this.element);
			if(this.isDetailed){
				this.detailDom = $('<input type="hidden" />').attr('name',this.propertyDetail).val(this.detailValue).appendTo(this.element);
			}
			if(this.propertyProvince){
				this.provinceDom = $('<input type="hidden" />').attr('name',this.propertyProvince).val(this.provinceValue).appendTo(this.element);
			}
			if(this.propertyCity){
				this.cityDom = $('<input type="hidden" />').attr('name',this.propertyCity).val(this.cityValue).appendTo(this.element);
			}
			if(this.propertyDistrict){
				this.districtDom = $('<input type="hidden" />').attr('name',this.propertyDistrict).val(this.districtValue).appendTo(this.element);
			}
			this.element.show();
		},
		
		openMapDialog : function() {
			var self = this;
			this.mapMainContent = $('<div class="lui_map_maincontent" />');
			this.dia = dialog.build({
				config : {
					width : 760,
					height : 500,
					title : this.edit ? lang['sysAttend.mapDialog.title.selectMap'] : lang['sysAttend.mapDialog.title.viewMap'],
					content : {
						type : 'element',
						elem : self.mapMainContent[0],
						iconType : '',
						buttons : []
					},
					actor : {
						type : 'default',
						animate:false 
					},
					win : window
				},
				callback: function(){
					topic.channel(self.uuId).publish('sys.attend.map.dialog.close');
				}
			});
			this.dia.on('layoutDone', function() {
				var StrategyMap = strategys[self.strategy];
				if(StrategyMap){
					var map = new StrategyMap({
						context : self
					});
					map.render(function(){
						self.renderToolbar();
						self.initData();
					});
				}
			});
			this.dia.show();
		},
	    
	    // 加载完地图后的操作
	    initData : function() {
	    	var self = this;
	    	var nameValue = this.propertyDom.val().replace(/^\s+|\s+$/g, ''),
				coordinateValue = this.coordinateDom.val(),
				detailValue = this.detailDom == null ? '' : this.detailDom.val(),
				provinceValue=this.provinceDom ? this.provinceDom.val():'',
				cityValue=this.cityDom ? this.cityDom.val():'',
				districtValue=this.districtDom ? this.districtDom.val():'';
	    	if(!nameValue) {//文本框为空时打开地图，则清空文本框
				this.propertyDom.val('');
				this.coordinateDom.val('');
				if(this.provinceDom){
					this.provinceDom.val('');
				}
				if(this.cityDom){
					this.cityDom.val('');
				}
				if(this.districtDom){
					this.districtDom.val('');
				}
	    	}
	    	topic.channel(this.uuId).publish('sys.attend.map.data.init',{
	    		coordinateValue : coordinateValue,
	    		nameValue : nameValue,
	    		detailValue : detailValue,
	    		provinceValue :provinceValue,
	    		cityValue : cityValue,
	    		districtValue : districtValue
	    	});
	    },
	   
	    
	    saveMapDialog : function(){
			var nameValue = this.nameValuedom.html(),
				coordinateValue = this.coordinateValuedom.val();
		    $(this.propertyDom).val(nameValue);
		    $(this.coordinateDom).val(coordinateValue);
		    if(this.isDetailed)
		    	this.detailDom.val(this.detailValueDom.html());
		    if(this.propertyProvince){
				this.provinceDom.val(this.provinceValuedom.val());
			}
			if(this.propertyCity){
				this.cityDom.val(this.cityValuedom.val());
			}
			if(this.propertyDistrict){
				this.districtDom.val(this.districtValuedom.val());
			}
		    this.closeMapDialog();
		},
		
		closeMapDialog : function() {
			topic.channel(this.uuId).publish('sys.attend.map.dialog.close');
	    	this.dia.hide();
	    	this.propertyDom.focus();
	    },
	    
	    unselectMapDialog : function(){
	    	topic.channel(this.uuId).publish('sys.attend.map.dialog.unselect');
	    	$(this.propertyDom).val('');
			$(this.coordinateDom).val('');
	    	this.nameValuedom.html('');
			this.coordinateValuedom.val('');
			this.detailValueDom.html('');
			if(this.isDetailed){
				this.detailDom.val('');
			}
			if(this.propertyProvince){
				this.provinceDom.val('');
				this.provinceValuedom.val('');
			}
			if(this.propertyCity){
				this.cityDom.val('');
				this.cityValuedom.val('');
			}
			if(this.propertyDistrict){
				this.districtDom.val('');
				this.districtValuedom.val('');
			}
	    },
	    
	    openNavigationUri : function(){
	    	var nameValue = this.propertyDom.val(),
				coordinateValue = this.coordinateDom.val(),
				detailValue = this.detailDom == null ? '' : this.detailDom.val();
	    	var evt = {
	    		coordinateValue : coordinateValue,
	    		nameValue : nameValue,
	    		detailValue : detailValue
	    	};
	    	topic.channel(this.uuId).publish('sys.attend.map.uri.navigation.open',evt);
	    },
	    
	    renderToolbar : function(){
	    	if(this.showStatus == 'hidden'){
	    		return;
	    	}
			var self = this;
	    	var mapMainToolbar = $('<div class="lui_map_mapMainToolbar" />').appendTo(this.mapMainContent),
	    		valuedomContainer = $('<div class="lui_map_valuedomContainer" />').appendTo(mapMainToolbar),
	    		btnContainer =  $('<div class="lui_map_btnContainer" />').appendTo(mapMainToolbar);
	    	var nameValue = this.propertyDom.val(),
	    		coordValue = this.coordinateDom.val(),
	    		detailValue = this.detailDom == null ? '' : this.detailDom.val(),
	    		provinceValue = this.provinceDom ? this.provinceDom.val():'',
	    		cityValue = this.cityDom ? this.cityDom.val():'',
	    		districtValue = this.districtDom ? this.districtDom.val():'';
	    	//地点名
	    	var nameContainer = $('<div class="lui_map_nameContainer" />').appendTo(valuedomContainer);
	    	var nameTextdom = $('<span />').html(lang['sysAttend.mapDialog.text.addressName']).appendTo(nameContainer);
	    	this.nameValuedom = $('<span />').html(nameValue).attr('title',nameValue).appendTo(nameContainer);
	    	//详细地址
	    	var detailContainer = $('<div class="lui_map_detailContainer" />').appendTo(valuedomContainer);
	    	var detailTextdom = $('<span />').html(lang['sysAttend.mapDialog.text.detailedAddress']).appendTo(detailContainer);
	    	this.detailValueDom = $('<span />').html(detailValue).attr('title',detailValue).appendTo(detailContainer);
	    	//坐标
	    	this.coordinateValuedom = $('<input type="hidden" />').val(coordValue).appendTo(valuedomContainer);
	    	//省市
	    	this.provinceValuedom = $('<input type="hidden" />').val(provinceValue).appendTo(valuedomContainer);
	    	this.cityValuedom = $('<input type="hidden" />').val(cityValue).appendTo(valuedomContainer);
	    	this.districtValuedom = $('<input type="hidden" />').val(districtValue).appendTo(valuedomContainer);
	    	//按钮
	    	if(this.showStatus == 'edit'){
	    		this._buildEditToolBar(btnContainer);
	    	} else {
	    		this._buildViewToolBar(btnContainer);
	    	}
		},
		
		_buildEditToolBar : function(srcObj){
			var self = this;
			var okButton = this.buildButton({
		    		text : lang['sysAttend.mapDialog.button.confirm'],
		    		style : 'lui_map_btn lui_toolbar_btn_def'
	    		}),
	    		unselectButton = this.buildButton({
					text: lang['sysAttend.mapDialog.button.cancelSelect'],
					style: 'lui_map_btn lui_toolbar_btn_gray'
	    		}),
	    		cancelButton = this.buildButton({
					text : lang['sysAttend.mapDialog.button.cancel'],
					style : 'lui_map_btn lui_toolbar_btn_gray'
				});
			okButton.onClick = function(){
				self.saveMapDialog();
			};
			unselectButton.onClick = function(){
				self.unselectMapDialog();
			};
			cancelButton.onClick = function(){
				self.closeMapDialog();
			};
			srcObj.append(unselectButton.element).append(cancelButton.element).append(okButton.element);
		},
		
		_buildViewToolBar : function(srcObj){
			var self = this;
			var cancelButton = this.buildButton({
					text : lang['sysAttend.mapDialog.button.cancel'],
					style : 'lui_map_btn lui_toolbar_btn_gray'
				}),
				navButton = this.buildButton({
					text: lang['sysAttend.mapDialog.button.openNav'],
					style: 'lui_map_btn lui_toolbar_btn_def'
				});
			cancelButton.onClick = function(){
				self.closeMapDialog();
			};
			navButton.onClick = function(){
				self.openNavigationUri();
			};
			srcObj.append(navButton.element).append(cancelButton.element);
		},
		
		buildButton : function(cfg){
			var button = toolbar.buildButton({
				text : cfg.text,
				styleClass : cfg.style
			});
			button.draw();
			return button;
		}
		
	});
	
	exports.Location = Location;
});
