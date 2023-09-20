define(['dojo/_base/declare',
		'dijit/_WidgetBase',
		'dojox/mobile/_ItemBase',
		"dojo/dom-construct",
		"dojo/dom-style",
		'./_AttachmentTableItemMixin'], 
		function(declare,  WidgetBase, ItemBase, domConstruct, domStyle, _AttachmentTableItemMixin){
	
	return declare("sys.attachment.maxhub.js.AttachmentListTableItem", [ ItemBase, _AttachmentTableItemMixin ] , {
		
		baseClass : 'mhuiAttachmentTileTableItem',
		
		buildRendering : function(){
			this.inherited(arguments);
			var itemTop = domConstruct.create('div',{ className : 'itemTop ' + this.getAttContainerType() },this.domNode);
			var iconWrapper = domConstruct.create('div',{ className : 'mhuiAttachmentItemIcon' }, itemTop);
			domConstruct.create('i',{ className : this.getAttTypeClass() },iconWrapper);
			domConstruct.create('div',{ className : 'itemBottom', innerHTML : this.fileName },this.domNode);
		},
		
		/*文档类型对应样式图标*/
		getAttTypeClass : function(){
			var typeStr = this.getType();
			if(typeStr!='')
				return "mui mui-file-" + typeStr ;
			return "mui mui-file";
		},
		
		/*文档类型对应外部容器样式，用于设置背景颜色*/
		getAttContainerType: function(){
			var typeStr = this.getType();
			if(typeStr!='')
				return "mui-files-" + typeStr ;
			return "mui-files";
		},
		
		getType : function(){
			var typeStr = "";
			if(this.fileName.lastIndexOf(".")>-1){
				var fileExt = this.fileName.substring(this.fileName.lastIndexOf(".")+1);
				if(fileExt!="")
					fileExt=fileExt.toLowerCase();
				switch(fileExt){
					case "doc":
					case "docx":
						typeStr = "word";
						break;
					case "xls":
					case "xlsx":
						typeStr = "excel";
						break;
					case "pps":
					case "ppt":
					case "pptx":
						typeStr = "ppt";
						break;
					case "pdf":
						typeStr = "pdf";
						break;
					case "txt":
						typeStr = "text";
						break;
					case "jpg":
					case "jpeg":
					case "ico":
					case "bmp":
					case "gif":
					case "png":
					case "tif":
						typeStr = "img";
						break;
					case "mht":
					case "html":
					case "htm":
						typeStr = "html";
						break;
					case "mpp":
						typeStr = "project";
						break;
					case "vsd":
						typeStr = "visio";
						break;
					case "zip":
					case "rar":
						typeStr = "zip";
						break;
					case "mp4":
						typeStr = "video";
						break;
					case "mp3":
						typeStr = "audio";
						break;
					}
			}
			return typeStr;
		}
		
	});
	
});