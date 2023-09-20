define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/openProxyMixin"
	], function(declare, domConstruct,domClass, ItemBase , util,openProxyMixin) {
	var item = declare("sys.readlog.mobile.js.list.AccessLogItemMixin", [ItemBase,openProxyMixin], {
		tag:"li",
		
		baseClass:"muiAccessLogItem",
		
		//数据类型：默认是阅读记录
		dataType:"readLog",
		
		//人员图像
		fdIcon:"",
		
		//人员名称
		fdName:"",
		
		//人员部门
		fdDeptName:"",
		
		//创建时间
		fdCreateTime:"",
		
		//ip
		fdIp:"",
		
		//状态
		fdReadType:"",
		fdReadTypeValue:"",
		
		//文件图标
		fdFileIcon:"",
		
		//文件名称
		fdFileName:"",
		
		buildRendering:function(){
			this.inherited(arguments);
			this.contentNode = domConstruct.create(this.tag, { className : 'muiAccessLogListItem' });
			this.buildInternalRender();
			domConstruct.place(this.contentNode,this.containerNode);
		},
		
		buildInternalRender : function() {
			if(this.dataType == "readLog"){//阅读记录项
				if(this.dialog){
					domClass.add(this.contentNode,"borderTopFirst");
					this.buildItemNode(1);
				}else{
					// 已阅人员
					var personHeadIconNode = domConstruct.create("div",{className:"personIcon"},this.contentNode);
					domConstruct.create('img',{src :util.formatUrl(this.fdIcon),className : 'personIconImg'},personHeadIconNode);
					domConstruct.create("div",{className:"personName",innerHTML:this.fdName},this.contentNode);
				}
			}else if(this.dataType == "printLog"){//打印记录项
				this.buildItemNode(2);
			}else if(this.dataType == "downLog"){//下载记录项
				this.buildItemNode(2);
			}
		},
		
		buildItemNode:function(type){
			domClass.add(this.contentNode,"fullItem");
			//上部分，基本信息部分
			var baseInfoNode = domConstruct.create("div",{className:"itemBaseInfo"},this.contentNode);
			this.buildLeftNode(baseInfoNode);
			this.buildRightNode(baseInfoNode,type);
			if(type == 2){
				//下部分，文件部分
				var fileInfoNode = domConstruct.create("div",{className:"itemFileInfo"},this.contentNode);
				var fileNode = domConstruct.create("div",{className:"itemFile"},fileInfoNode);
				domConstruct.create("i",{className:"fileIcon"},fileNode);
				domConstruct.create("span",{className:"fileTitle",innerHTML:this.fdFileName},fileNode);
			}
		},
		
		buildLeftNode:function(parentNode){
			var leftNode = domConstruct.create("div",{className:"baseInfoLeft"},parentNode);
			this.buildPersonNode(leftNode);
		},
		
		buildPersonNode:function(parentNode){
			//头像
			var imgNode = domConstruct.create("div",{className:"personIcon"},parentNode);
			domConstruct.create("img",{src :util.formatUrl(this.fdIcon),className:"personIconImg"},imgNode);
			
			//名称和部门
			var personInfoNode = domConstruct.create("div",{className:"personInfo"},parentNode);
			domConstruct.create("div",{className:"personInfoName",innerHTML:this.fdName},personInfoNode);
			domConstruct.create("div",{className:"personInfoDept",innerHTML:this.fdDeptName},personInfoNode);
		},
		
		buildRightNode:function(parentNode,type){
			var rightNode = domConstruct.create("div",{className:"baseInfoRight"},parentNode);
			if(type == 1){//时间+状态
				domConstruct.create("div",{className:"timeInfo",innerHTML:this.fdCreateTime},rightNode);
				var className = "process"
				if(this.fdReadTypeValue != 1){
					className = "publish";
				}
				className += " statusInfo";
				domConstruct.create("div",{className:className,innerHTML:this.fdReadType},rightNode);
			}else if(type == 2){//时间+ip
				domConstruct.create("div",{className:"timeInfo",innerHTML:this.fdCreateTime},rightNode);
				domConstruct.create("div",{className:"ipInfo",innerHTML:"IP："+this.fdIp},rightNode);
			}
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
		
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}
	});
	return item;
});