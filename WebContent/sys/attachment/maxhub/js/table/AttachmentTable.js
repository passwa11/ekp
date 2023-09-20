define(['dojo/_base/declare',
		"dijit/_WidgetBase",
		"dojox/mobile/Container",
		'dojo/topic',
		"dojo/dom-construct",
		"dojo/dom-style",
		'dojo/_base/array',
		'dojo/_base/lang',
		'mhui/device/jssdk'], 
		function(declare, WidgetBase, Container, topic, domConstruct, domStyle, array, lang, jssdk){
	
	if(!Object.assign){
		Object.assign = function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	}
	
	return declare("sys.attachment.maxhub.js.AttachmentSelectTable", [ WidgetBase, Container ] , {
		
		key : 'abstract',
		
		selectedValues : [],
		
		values : [],
		
		itemRenderer : null,
		
		render : function(){
			domConstruct.empty(this.domNode);
			var datas = this.values;
			if(datas && datas.length > 0){
				this._buildRunningNode(lang.hitch(this,function(){
					for(var i = 0; i < datas.length; i++){
						var selected = array.some(this.selectedValues,function(selectedData){
							return selectedData.filePath === datas[i].filePath;
						});
						var __data = Object.assign({}, datas[i], { fdKey : this.fdKey , selected : selected});
						var renderer = new this.itemRenderer(__data);
						renderer.startup();
						domConstruct.place(renderer.domNode, this.domNode);
					}
				}));
			}else{
				domConstruct.create('div',{ className : 'mhuiAttachementNodata' }, this.domNode);
				domConstruct.create('div',{ className : 'mhuiAttachementNodataLabel', innerHTML: '暂无会议纪录' }, this.domNode);
				this._buildRunningNode();
			}
		},
		
		_buildRunningNode : function(callback){
			jssdk.isBoardRunning(lang.hitch(this,function(isRunning){
				if(isRunning){
					var runningNode = domConstruct.create('div',{ className : 'mhuiBoardRunningNode' }, this.domNode);
					domConstruct.create('span',{ className: 'mhui-icon mhui-icon-warn' }, runningNode);
					domConstruct.create('span',{ innerHTML : '设备白板正在使用中，是否需要将白板数据保存到智能会议？' }, runningNode);
					var boardNode = domConstruct.create('span',{ className : 'mhuiBoardOpenNode', innerHTML : '去保存' },runningNode);
					this.connect(boardNode,'click',this._openBoard);
				}
				callback && callback.call(this);
			}));
		},
		
		_openBoard : function(){
			jssdk.board();
			topic.publish('attachmentObject_' + this.fdKey + '_openBoard');
		},
		
		show : function(){
			domStyle.set(this.domNode,'display', 'block');
		},
		
		hide : function(){
			domStyle.set(this.domNode,'display', 'none');
		}
		
	});
	
});