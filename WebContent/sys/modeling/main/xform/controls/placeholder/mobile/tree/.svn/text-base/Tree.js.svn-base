/**
 * 单选树图
 */
define(["dojo/_base/declare","dojo/request","dojo/_base/lang","dojo/dom-construct",
	"dojo/dom-class","dojo/_base/array","dojo/json","mui/util","dojo/query","mui/form/_CategoryBase",
	"sys/modeling/main/xform/controls/placeholder/mobile/tree/TreeMixin",
	"dijit/_WidgetBase","mui/i18n/i18n!sys-modeling-main", "mui/form/_FormBase"], function(
  declare,
  request,
  lang,
  domConstruct,
  domClass,
  array,
  JSON,
  util,
  query,
  CategoryBase,
  TreeMixin,
  WidgetBase,
  Msg,
  _FormBase
) {
  return declare("sys.modeling.main.xform.controls.placeholder.mobile.tree.Tree", [WidgetBase,CategoryBase,TreeMixin,_FormBase], {
	  	authed : true,// 树图不需要进行权限校验
	  
	  	isMul : false,
	  	
	  	key : '_nodeselect',
	  	
	  	//文档数据获取路径
		dataUrl:"/sys/modeling/main/mobile/ModelingAppTreeMobile.do?method=nodeList&parentId=!{parentId}",
		
		//层级路径请求链接
		detailUrl : '/sys/modeling/main/mobile/ModelingAppTreeMobile.do?method=pathList&parentId=!{curId}',
		
		//回调获取真正的数据(包括真实的id，父层级等）
		callbackUrl : "/sys/modeling/main/mobile/ModelingAppTreeMobile.do",
		
		titleNode : Msg['mui.tree.model'],
		
		valJsonArr : [],
		
	  	// 加载：dom已经加载完成
	  	startup : function(){
	  		this.inherited(arguments);
	  		var fdId = query("input[name='fdId']").val() || "";
	  		this.key = this.key + "_" + this.controlId;
	  		this.modelId = fdId;
	  		this.dataUrl = this.dataUrl + "&fdId="+fdId+"&controlId="+ this.controlId + "&appModelId=" + this.appModelId;
	  		this.detailUrl = this.detailUrl + "&controlId="+ this.controlId + "&appModelId=" + this.appModelId;
	  		//初始化内容
	  		this._initValue();
	  	},
	  	
	  	_initValue : function(){
	  		var self = this;
	  		if(!this.value){
	  			return;
	  		}
	  		this.curIds = this.value;
	  		this.curNames = this.text;
	  		this.contentNode.innerHTML = this.text;
	  		//请求后台替换id
	  		var url = '/sys/modeling/main/mobile/ModelingAppTreeMobile.do';
	  		url = util.formatUrl(url);
	  		request.post(url, {data:{
				"method":"replaceId",
				'curIds':self.curIds,
				"controlId":self.controlId,
				"appModelId":self.appModelId,
				"modelId":query("input[name='fdId']").val()
			},handleAs: "json"}).then(
			    function(data){
			    	if(data){
		    			self.curIds = data.curIds;
		    		}
			    }
			);
	  	},
	  	
	  	// 加载dom
	  	buildRendering : function(){
	  		this.inherited(arguments);
	  		domClass.add(this.domNode,"oldMui muiFormEleWrap muiFormStatusEdit");
	  		// 存放隐藏字段的节点
	  		this.inputContent = domConstruct.create("div", {
	  			className : "muiSelInput",
	  			style : {
	  				"line-height" : "1.8rem",
	  				"padding-right" : "24px"	
	  			}
	  		}, this.domNode)
	  		// 显示文本的节点
	  		this.contentNode = domConstruct.create("div", {
	  			className : "relationChooseText",
	  			placeholder : Msg['mui.tree.placeholder'],
	  			style:{
	  			    'height': '3rem',
	  		   		'line-height': '3rem'
	  			}
	  		}, this.inputContent);
	  		this.buildEdit();
	  	},
	  	
	  	postCreate : function(){
	  		this.inherited(arguments);
	  		this.connect(this.contentNode, "click", lang.hitch(this,this.openView));
			this.eventBind();
	  	},
	  
	  	// 编辑状态渲染
		buildEdit : function() {
			this.hiddenValueNode = this._buildHiddenInput(
					this.name, this.value, this.inputContent)
			this.hiddenTextNode = this._buildHiddenInput(
					this.textName, this.text, this.inputContent);
		},
	  	
	  	// 查看状态渲染
	  	buildView : function() {
			this.hiddenValueNode = this._buildHiddenInput(
					this.name, this.value, this.inputContent)
			this.hiddenTextNode = this._buildHiddenInput(
					this.textName, this.text, this.inputContent)
	  	},

		_buildHiddenInput : function(name, val,
				parentNode) {
			var input = domConstruct.create("input", {
				type : "hidden",
				name : name
			}, parentNode)
			if (val) {
				input.value = val
			}
			return input
		},
		
		openView : function(){
			if(domClass.contains(this.domNode,"muiFormStatusReadOnly")){
				return;
			}
			// 直接进入树图，用于测试
			this._selectCate();
		},
		
		//回调
		afterSelect : function(evt){
			var self = this;
			if(evt){
				var curIds = evt.curIds;
				var curNames = evt.curNames;
				this.contentNode.innerHTML = curNames;
				this.hiddenTextNode.value = curNames;
				
				//请求后台获取真正的id
				if(curIds){
					var url = util.formatUrl(this.callbackUrl)
			        var promise = request.post(url, {data:{
						"method":"dataList",
						'curIds':curIds
					},handleAs: "json"})
			        promise.then(
			        	lang.hitch(this,function(data){
						    //请求成功后的回调
							if(data){
								curIds = "";
								this.valJsonArr = [];//每次回调清空重新配置
								array.forEach(data,function(item){
									curIds += item.modelId + ";";
									curIds = curIds.substring(0,curIds.length-1);
									var valJson = item.value;
									this.valJsonArr.push(valJson);
								},this);
							}
							this.hiddenValueNode.value = curIds;
			        	})
					);
				}else{
					this.hiddenValueNode.value = curIds;
				}
			}
			
			Com_Parameter.event["submit"].push(function() {
				self._synValue();
				return true;
			});
		},
		
		_synValue : function(){
    		//获取内容
    		var fdTreeNodeData = query("input[name='fdTreeNodeData']").val();
    		var dataJsonArr = this.generateDataJsonArr();
    		var fdTreeNodeDataJson = {};
    		if(fdTreeNodeData){
    			try{
    				fdTreeNodeDataJson = JSON.parse(fdTreeNodeData);
    			}catch(e){
    			}
    		}
    		fdTreeNodeDataJson[this.controlId] = dataJsonArr;
    		query("input[name='fdTreeNodeData']").val(JSON.stringify(fdTreeNodeDataJson));
		},
		
		generateDataJsonArr : function(){
        	var dataJsonArr = [];
        	var valJsonArr = this.valJsonArr;
    		for(var i=0; i<valJsonArr.length; i++){
    			var valJson = valJsonArr[i];
    			for(var key in valJson){
    				var dataJson = {};
    				dataJson['hierarchyId'] = key;
    				dataJson['value'] = valJson[key];
    				dataJsonArr.push(dataJson);
        		}
    		}
        	return dataJsonArr;
        },
  })
})
