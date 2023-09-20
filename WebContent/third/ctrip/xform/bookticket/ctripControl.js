
/**
 * 携程组件
 * @returns
 */
(function(){
	function CtripControl(config){

		this.domNode;
		this.docId;
		this.controlId;
		
		// 状态
		this.showStatus = '';
		// 主文档的状态 “add|edit|view”
		this.mainDocStatus = '';
		this.modelName;
		
		this.validateService = {};
		
		this.ticketType; // 'plane'、‘hotel’、‘plane|hotel’
		this.bookTypeControlId; // 预订类型的控件id
		this.bookTypeValue; // 预订类型的控件值
		
		this.buildView = CtripControl_BuildView;// 画视图
		this.buildButton = CtripControl_BuildButton;// 画按钮
		this.openCtrip = CtripControl_OpenCtrip; // 点击跳转携程
		this.validate = CtripControl_Validate; // 校验必填
		this.showButton = CtripControl_ShowButton;
		this._init = _CtripControl_Init; // 初始化
		
		this._init(config);
	}
	
	function _CtripControl_Init(config){
		this.domNode = config.domNode;
		this.ticketType = config.ticketType || 'plane|hotel';
		this.bookTypeControlId = config.bookTypeControlId || '';
		this.showStatus = config.showStatus || 'edit';
		this.mainDocStatus = config.mainDocStatus || 'view';
		this.modelName = config.modelName || '';
		this.docId = config.docId || '';
		this.controlId = config.controlId || '';
		this.validateService = config.validateService || [];
		this.bookTypeValue = config.bookTypeValue || '';
		
		if(typeof Xform_ObjectInfo.Xform_Controls.ctripControl == "undefined")
			Xform_ObjectInfo.Xform_Controls.ctripControl = new Array();
		Xform_ObjectInfo.Xform_Controls.ctripControl.push(this);
		
		this.buildView();
	}
	
	function CtripControl_BuildView(){
		var self = this;
		var tickets = self.ticketType.split('|');
		for(var i = 0;i < tickets.length;i++){
			this.domNode.append(self.buildButton(tickets[i]));
		}
		
		var bookTypeControlId = self.bookTypeControlId;
		if(bookTypeControlId != ''){
			var $bookType = $("input[name*='"+ bookTypeControlId +"'][type='checkbox']");
			if($bookType && $bookType.length > 0){
				for(var i = 0; i < $bookType.length ;i++){								
					//绑定value改变事件，以便可以控制展示图片的隐藏和展示
					$($bookType[i]).change(function() {								
						if(this.checked){
							self.showButton(true,this.value);
						}else{
							self.showButton(false,this.value);
						};
					});
				}
			}
			this.domNode.find("[data-ctriptype]").hide();
			self.showButton(true,self.bookTypeValue);
		}
	}
	
	function CtripControl_ShowButton(show,value){
		// 如果value为空，则按钮全部隐藏
		if(value){
			var arr = value.split(";");
			for(var i = 0;i < arr.length;i++){
				if(show){
					// 为不空，则显示对应value的按钮
					this.domNode.find("[data-ctriptype='"+ arr[i] +"']").css("display","inline-block");					
				}else{
					this.domNode.find("[data-ctriptype='"+ arr[i] +"']").hide();
				}
			}
		}else{
			this.domNode.find("[data-ctriptype]").hide();
		}
	}
	
	function CtripControl_BuildButton(type){
		var button = $("<div>");
		var self = this;
		if(this.showStatus == 'edit'){
			button.addClass("third_ctrip_btn third_ctrip_btn_" + type);
			button.bind('click',function(){
				self.openCtrip(type);
			});
		}else{
			button.addClass("third_ctrip_disabled third_ctrip_disabled_" + type);
		}
		button.css("display","inline-block");
		button.attr("data-ctriptype",type);
		var msg;
		if(type == 'plane'){
			msg = '预定飞机';
		}else if(type == 'hotel'){
			msg = '预定酒店';
		}
		button.append("<i></i>" + msg);
		return button;
	}
	
	function CtripControl_OpenCtrip(type){
		var docId = this.docId;
		if(docId != ''){
			var url = Com_Parameter.ContextPath+"third/ctrip/xform/resource/jsp/thirdCtripXformMainTemplate_toCtrip.jsp?fdId="+ docId +"&modelName="+ this.modelName +"&fdBookId="+ this.controlId +"&type=" + type;
			window.open(url, "_blank");	
		}else{
			alert("无法找到流程文档id！");
		}
	}
	
	function CtripControl_Validate(){
		if(this.mainDocStatus != "view"){
			if(this.bookTypeControlId){
				// 找到预订类型的控件
				var $bookType = $("input[name*='"+ this.bookTypeControlId +"'][type='checkbox']:checked");
				if($bookType.length == 0){
					alert("携程控件要求  \""+ $("input[name*='"+ this.bookTypeControlId +"']").attr("subject") +"\" 必填!");
					return false;
				}
				// 哪个选中，就执行哪套校验
				for(var i = 0;i < $bookType.length;i++){
					var bookTypeVal = $($bookType[i]).val();
					if(this.validateService.hasOwnProperty(bookTypeVal)){
						var rs = this.validateService[bookTypeVal].validate(this.domNode.find("div[name*='sysXform']"));
						if(rs.status == '00'){
							alert(rs.msg);
							return false;
						}
					}
				}
			}			
		}
		return true;
	}
	
	window.CtripControl = CtripControl;
})()
