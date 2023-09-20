define( [ "dojo/_base/declare","dojox/mobile/_ItemBase","dojo/dom-class", "./_AttachmentTypeMixin" ], function(
		declare, ItemBase, domClass, AttachmentTypeMixin) {
	//附件项抽象类
	return declare("sys.attachment.mobile.js._AttachmentItem", [ItemBase, AttachmentTypeMixin], {
	
		baseClass : 'muiAttachmentItem',
		
		//附件ID
		fdId : null,
		
		filekey : null,
		
		//附件大小
		size : 0,
		
		//附件名称
		name : '',
		
		//附件类型
		type : '',

		//图片地址或图标地址
		icon : '',
		
		//附件打开地址
		href : '',
		
		key : '',
		
		edit: false,
		
		//仅显示名称
		justDiaplayName:false,
		
		buildRendering:function(){
			this.inherited(arguments);
			var parantWiget = this.getParent();
			if(parantWiget!=null){
				this.edit = parantWiget.editMode=="edit";
				this.key = parantWiget.fdKey;
			}
			this.buildItem();
		},
		
		startup:function(){
			this.inherited(arguments);
		},
		
		/* 格式化文件大小 */
		formatFileSize:function(){
			var result = "";
			var index;
			var strSize = ''; 
			if(typeof(this.size)!='string')
				strSize += this.size;
			else
				strSize = this.size;
			if(strSize!=null && strSize!="") {
				var tmpsize = 0;
				if((index = strSize.indexOf("E"))>0) {			
					tmpsize = parseFloat(strSize.substring(0,index))*Math.pow(10,parseInt(strSize.substring(index+1)));
				}else{
					tmpsize = parseInt( strSize);
				}
				if(tmpsize<1024){
					result = tmpsize + "B";
				}else{
					tmpsize = Math.round(tmpsize*100/1024)/100;
					if(tmpsize < 1024){
						result = tmpsize + "KB";
					}else{
						tmpsize = Math.round(tmpsize*100/1024)/100;
						if(tmpsize<1024){
							result = tmpsize + "M";
						}else {
							tmpsize = Math.round(tmpsize*100/1024)/100;
							result = tmpsize + "G";
						}
					}
				}
			}
			return result;
		},
		/*文档类型对应样式图标*/
		getAttTypeClass : function(){
			var typeStr = this.getType();
			// if(typeStr==='ofd')
			// 	return "mui-icon";
			if(typeStr!='')
				return "mui-icon-" + typeStr ;
			return "mui-icon";
		},
		
		/*文档类型对应外部容器样式，用于设置背景颜色*/
		getAttContainerType: function(){
			var typeStr = this.getType();
			if(typeStr!='')
				return "mui-files-" + typeStr ;
			return "mui-files";
		}
		
	});
});