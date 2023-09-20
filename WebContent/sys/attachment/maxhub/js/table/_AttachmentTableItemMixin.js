define(['dojo/_base/declare',
		'dojo/topic',
		'dojo/dom-construct',
		'dojo/dom-class',
		'mhui/device/jssdk'], 
		function(declare, topic, domConstruct, domClass, jssdk){
	
	return declare('sys.attachment.maxhub._AttachmentTableItemMixin',null, {
		
		buildRendering : function(){
			this.inherited(arguments);
			this.selectboxNode = domConstruct.create('div',{ className : 'itemSelectbox' }, this.domNode);
			if(this.selected){
				domClass.add(this.domNode,'active');
			}
		},
		
		startup : function(){
			this.inherited(arguments);
			this.connect(this.selectboxNode,'click','_onSelected');
			this.connect(this.domNode,'click','_onOpen');
			this.subscribe('attachmentObj_' + this.fdKey + '_selectedChange' ,'_handleSelected')
		},
		
		_onOpen : function(){
			jssdk.viewFile({
				path : this.filePath
			});
		},
		
		_onSelected : function(evt){
			evt.preventDefault();
			evt.stopPropagation();
			console.log('on seleled changed');
			topic.publish('attachmentObj_' + this.fdKey + '_selectedChange',this);
		},
		
		_handleSelected : function(evt){
			if(evt.filePath == this.filePath){
				domClass.toggle(this.domNode,'active');
			}
		}
		
	});
	
});