define(function(require, exports, module) {
	
	var base = require("lui/base");
	var env = require("lui/util/env");
	var toolbar = require("lui/toolbar");
	var dialog = require('lui/dialog');
	var topic = require("lui/topic");
	var lang = require("lang!sys-attachment:attachment.mechanism");
	
	var $ = null;
	
	var _MAXWH = 500;	// 原图用来裁剪时，展示时的宽、高最大值
	var _DEFAULTWH = [120, 60, 30];								// 大中小图对应的默认大小
	var _CROP_NUM = {one: 1, two: 2, three: 3};					//裁剪所得图片个数：1 大; 2 大/中; 3 大/中/小 
	var _EDIT_MODE = {add: 'add', edit: 'edit', view: 'view'};	// 对应三种情况： add 新建;  edit 编辑;  view 不对主文档作操作
	var _CROP_KEYS = ['_b', '_m', '_s'];						// 大中小的cropKey
	
	function buildCropImage(_options){
		//JQuery 由外部传入，防止JQuery.Jcrop无法调用
		$ = _options._$;
		var crop = new CropInfo(_options);
		crop.startup();
		//动态改变截取图片的大小
		topic.subscribe("/lui/sys/attachment/crop/changeImgSize", function(size) {
			crop._changeImgSize(size);
		});
	}
	
	var CropInfo = base.Base.extend({
		initProps : function(_config){
			this.config = _config;
			
			this.fdKey  = _config.fdKey;
			this.modelId   = _config.fdModelId;
			this.modelName = _config.fdModelName;
			this.attObj  = _config.attObj;
			
			this.cropHolder = $("div[data-crop-id='" + _config.fdKey + "']");
			
			// 裁剪后图的宽高
			this.__init__();
			
			this.downUrl = env.fn.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=");
			this.cropUrl = env.fn.formatUrl("/sys/attachment/sys_att_image_crop/sysAttImageCrop.do?method=");
		},
		startup: function(){
			var self = this;
			// 原始图片上传成功
			this.attObj.on('uploadSuccess', function(){
				self.uploadSuccess();
			});
			
			this.__buildBtns__();
			this.__loadready__();
		},
		__init__: function(){
			var _config = this.config;
			
			this._setCropNum(_config.cropNum);
			this._setCropKeys();
			this._setEditMode(_config.editMode);
			
			this.cropRatio = 1;
			this.currentPosition = null;
			
			var _fdKey = this.fdKey;
			var _holder = this.cropHolder;
			this.targetEle = _holder.find("#crop_" + _fdKey);
			this.bigCropElement = _holder.find("#" + _fdKey + _CROP_KEYS[0]);
			this.mediumCropElement = _holder.find("#" + _fdKey + _CROP_KEYS[1]);
			this.smallCropElement = _holder.find("#" + _fdKey + _CROP_KEYS[2]);
			
			this._bigCueBox = _holder.find(".crop_image_box_b");
			this._mediumCueBox = _holder.find(".crop_image_box_m");
			this._smallCueBox = _holder.find(".crop_image_box_s");
			
			this.maxWidthAndHeight = this._obtain(_config.fdMaxWidthHeight, _MAXWH);
			
			this._bW = this._obtain(_config.bWidth,  _DEFAULTWH[0]);
			this._bH = this._obtain(_config.bHeight, _DEFAULTWH[0]);
			this._mW = this._obtain(_config.mWidth,  _DEFAULTWH[1]);
			this._mH = this._obtain(_config.mHeight, _DEFAULTWH[1]);
			this._sW = this._obtain(_config.sWidth,  _DEFAULTWH[2]);
			this._sH = this._obtain(_config.sHeight, _DEFAULTWH[2]);
			
			this.bigCropElement.parent().css({'width': this._bW, 'height': this._bH});
			this._bigCueBox.css({'width': this._bW, 'height': this._bH, 'line-height': this._bH + "px"}).text(this._bW + "*" + this._bH);
			var _inlineBlock = {'display': 'inline-block'};
			if(this.cropNum > 1){
				this.mediumCropElement.parent().css({'width': this._mW, 'height': this._mH}).css(_inlineBlock);
				this._mediumCueBox.css({'width': this._mW, 'height': this._mH, 'line-height': this._mH + "px"}).css(_inlineBlock).text(this._mW + "*" + this._mH);
			}
			if(this.cropNum > 2){
				this.smallCropElement.parent().css({'width': this._sW, 'height': this._sH}).css(_inlineBlock);
				this._smallCueBox.css({'width': this._sW, 'height': this._sH, 'line-height': this._sH + "px"}).css(_inlineBlock).text(this._sW + "*" + this._sH);	
			}
			
		},		
		uploadSuccess: function(){
			var fileList = this.attObj.fileList;
			this.attId = fileList[fileList.length - 1].fdId;
			this.clearFileList();
			this.destoryJcrop();
			this.buttonDisplay(true);
			//回显图片
			this.resetCropImages(this.downUrl + this.attId);
			
			var self = this;
			$.ajax({
				type: "post",
				url: self.cropUrl + "imageInfo",
				data: "attId=" + self.attId,
				dataType:"json",	
				success: function(data) {
					if(data && !isNaN(data.width) && !isNaN(data.height)){
						self.calculate(data.width, data.height);
					}
					self._displayArea(true);
					self.__createCrop__();
				}
			});	
		},
		calculate: function(width, height){
			var MAX = this.maxWidthAndHeight;
			var w = MAX, h = MAX, ratio = 1;
			if(width > MAX || height > MAX){
				if(width > height){
					ratio = width / MAX;
					h = height * MAX / width;
				} else {
					ratio = height / MAX;
					w = width * MAX / height;
				}
			} else {
				w = width;
				h = height;
			}
			this.targetEle.parent().css({'width': w, 'height': h});
			this.targetEle.css({'width': w, 'height': h});
			// 收缩比例
			this.cropRatio = ratio;
		},
		clearFileList: function(){
			this.attObj.fileList.length = 0;
			//清除原有附件机制的render
			$("#att_xdiv_" + this.fdKey).empty();
		},
		destoryJcrop: function(){
			if(this.cropHolder.find(".jcrop-holder").size() > 0) {
				this.cropHolder.find(".jcrop-holder").attr("style", "").remove();
			}
			if(this.jcrop_api){
				this.jcrop_api.destroy();
			}
		},
		__buildBtns__: function(){
			var check = lang['attachment.mechanism.ok'];
			var cancle = lang['attachment.mechanism.cancle'];
			this.submitBtn = toolbar.buildButton({'text': check, 'style': 'display: none'});
			this.cropHolder.find(".crop_submit").append(this.submitBtn.element);
			this.submitBtn.draw();
			this.cancelBtn = toolbar.buildButton({'text': cancle, 'style': 'display: none'});
			this.cropHolder.find(".crop_cancel").append(this.cancelBtn.element);
			this.cancelBtn.draw();
			// 修改点击事件
			var self = this;
			this.submitBtn.onClick = function(){self.submitCrop();};
			this.cancelBtn.onClick = function(){self.cancelCrop();};
		},
		buttonDisplay: function(flag){
			this.submitBtn.setVisible(flag);
			this.cancelBtn.setVisible(flag);
			topic.publish('crop.button.show',{crop:this,isShow:flag});
		},
		__createCrop__: function(){
			var self = this;
			this.targetEle.show().Jcrop({
				allowSelect: false,
				bgFade: true,
				bgOpacity: 0.5,
				aspectRatio: self._bW / self._bH,
				onChange: function(c){self.__showCoords__(c);}
			},function(){
				self.jcrop_api = this;
				self.jcrop_api.animateTo([0, 0, self._bW, self._bH]);
			});
		},
		__showCoords__: function(c) {
			var ratio = this.cropRatio;
			this.currentPosition = {x1: Math.round(c.x * ratio),  y1: Math.round(c.y * ratio), 
									x2: Math.round(c.x2 * ratio), y2: Math.round(c.y2 * ratio),
									w:  Math.round(c.w * ratio),  h:  Math.round(c.h * ratio)};
		 	if (parseInt(c.w) > 0) {
		 	     this.__cropImageView__(c);
		 	}
		 },
		__cropImageView__: function(c){
			var bounds = this.jcrop_api.getBounds();
			var boundx = bounds[0];
		    var boundy = bounds[1];
		    this.bigCropElement.css({
		        width: Math.round((this._bW / c.w) * boundx) + 'px',
		        height: Math.round((this._bH / c.h) * boundy) + 'px',
		        marginLeft: '-' + Math.round((this._bW / c.w) * c.x) + 'px',
		        marginTop: '-' + Math.round((this._bH / c.h) * c.y) + 'px'
		    });

		    this.mediumCropElement.css({
		        width: Math.round((this._mW / c.w) * boundx) + 'px',
		        height: Math.round((this._mH / c.h) * boundy) + 'px',
		        marginLeft: '-' + Math.round((this._mW / c.w) * c.x) + 'px',
		        marginTop: '-' + Math.round((this._mH / c.h) * c.y) + 'px'
		    });

		    this.smallCropElement.css({
		        width: Math.round((this._sW / c.w) * boundx) + 'px',
		        height: Math.round((this._sH / c.h) * boundy) + 'px',
		        marginLeft: '-' + Math.round((this._sW / c.w) * c.x) + 'px',
		        marginTop: '-' + Math.round((this._sH / c.h) * c.y) + 'px'
		    });
		},
		submitCrop: function() {
			var self = this;
			var _pos = this.currentPosition;
			var _modelId = self.modelId;
			if(self.editMode != _EDIT_MODE.view){
				_modelId = null;
			}
			var load = dialog.loading("图片裁剪中...", null, null, 610);
			$.ajax({
				type: "post",
				url: self.cropUrl + "addCrop",
				data: {fdStartX: _pos.x1, fdStartY: _pos.y1, fdCropWidth: _pos.w, fdCropHeight: _pos.h, 
					   fdCropId: self.attId, fdModelName: self.modelName, fdModelId: _modelId, fdCropKeys: self.cropKeys,
					   fdBigWidth: self._bW, fdBigHeight: self._bH, fdMediumWidth: self._mW ,fdMediumHeight: self._mW,
					   fdSmallWidth: self._sW, fdSmallHeight: self._sH
					},
				dataType: "json",	
				success: function(data) {
					self.resetCropImages();
					self.reflushCrop(data);
					self.attObj.emit('cropSuccess', data);//执行裁剪成功绑定的方法
					var obj =  {
							modelId : self.modelId,
							modelName : self.modelName,
							fdKey : self.fdKey
					};
					topic.publish('lui.crop.success',$.extend(obj, data));
					load.hide();
				},
				error: function() {
					load.hide();
				}
			});
		},
		//取消裁剪
		cancelCrop: function() {
			var self = this;
			$.ajax({
				type: "post",
				url: self.cropUrl + "cancelCrop",
				data: "attId=" + self.attId,
				dataType:"json",	
				success: function(data) {
				 	self.resetCropImages();
				 	self.__loadready__();
				}
			});
		},
		__loadready__: function(){
			var self = this;
			var _modelId = self.modelId;
			if(self.editMode == _EDIT_MODE.add){
				_modelId = null;
			}
			$.ajax({
				type: 'POST',
				url: self.cropUrl + "listCrop",
				data: {fdKey: self.fdKey, fdModelName: self.modelName, fdModelId: _modelId, fdCropKeys: self.cropKeys},
				dataType: 'json',
				success: function(data){
					self.reflushCrop(data);
					topic.publish('crop.load.ready', self);
				}
			});
		},
		reflushCrop: function(data){
			var self = this;
			var isHas = false;
			if(data && data.length > 0) {
				for(var i = 0 ; i < data.length ; i++){
					// 展示最新的裁剪成功的图片，并去除当前裁剪的定位样式
					$("#" + data[i].key).attr("src", self.downUrl + data[i].value).attr("style", "").parent().find('span').remove();
					// 设置附件id，后台保存文档时关联裁剪后的图片附件
					$("input[name='attachmentForms." + data[i].key + ".attachmentIds']").val(data[i].value);
					isHas = true;
				}
			}
			self.destoryJcrop();
			self.targetEle.parent().css({'width': 0, 'height': 0});
			self.targetEle.attr("src", "").hide();
			self.buttonDisplay(false);
		 
			self._displayArea(isHas);
		},
		resetCropImages: function(src){
			src = src == null ? "" : src;
			this.targetEle.attr('src', src);
			this.bigCropElement.attr('src', src);
			this.mediumCropElement.attr('src', src);
			this.smallCropElement.attr('src', src);
		},
		_displayArea: function(isHas){
			// 如没有裁剪成功的图片，则显示裁剪提示框，否则，显示图片
			var _ctr = this.bigCropElement.parent().parent();
			var _cueCtr = this._bigCueBox.parent();
			if(isHas){
				_ctr.show();
				_cueCtr.hide();
			}else{
				_ctr.hide();
				_cueCtr.show();
			}
		},
		_setCropKeys: function(){
			var arr = [];
			if(this.cropNum == _CROP_NUM.one){
				arr.push(_CROP_KEYS[0]);
			} else if(this.cropNum == _CROP_NUM.two){
				arr.push(_CROP_KEYS[0], _CROP_KEYS[1]);
			} else {
				arr = _CROP_KEYS;
			}
			this.cropKeys = arr.join(";");
		},
		_setCropNum: function(num){
			var _num = parseInt(num);
			if(isNaN(_num) || _num < _CROP_NUM.one || _num > _CROP_NUM.three){
				_num = _CROP_NUM.three;
			}
			this.cropNum = _num;
		},
		_setEditMode: function(_mode){
			if(_mode == _EDIT_MODE.edit){
				this.editMode = _EDIT_MODE.edit;
			}else if(_mode == _EDIT_MODE.view){
				this.editMode = _EDIT_MODE.view;
			}else{
				this.editMode = _EDIT_MODE.add;
			}
		},
		_obtain: function(_wh, _default){
			if(!_wh || isNaN(_wh)){
				return _default;
			}
			return _wh;
		},
		_changeImgSize : function(size){
			var config = this.config;
			config.bWidth = size;
			config.bHeight = size;
			this.initProps(config);
		}
	});
	
	exports.buildCropImage = buildCropImage;
});