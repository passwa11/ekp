function ModelDialog_Show(url,data,callback){
	this.AfterShow=callback;
	this.data=data;
	this.width=window.screen.width*600/1366;
	this.height=window.screen.height*400/768;
	this.url=url;
	this.setWidth=function(width){
		this.width=width;
	};
	this.setHeight=function(height){
		this.height=height;
	};
	this.setCallback=function(action){
		this.callback=action;
	};
	this.setData=function(data){
		this.data=data;
	};
	
	this.show=function(){
		var obj={};
		obj.data=this.data;
		obj.AfterShow=this.AfterShow;
		Com_Parameter.Dialog=obj;
		var left = (screen.width-this.width)/2;
		var top = (screen.height-this.height)/2;
		
		if(window.showModalDialog){
			var winStyle = "resizable:1;scroll:1;dialogwidth:"+this.width+"px;dialogheight:"+this.height+"px;dialogleft:"+left+";dialogtop:"+top;
			var win = window.showModalDialog(url, obj, winStyle);
		}else{
			var winStyle = "resizable=1,scrollbars=1,width="+this.width+",height="+this.height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
			var win = window.open(url, "_blank", winStyle);
		}
		try{
			win.focus();
		}
		catch(e){
			
		}
		//用window.open 达到模态效果
		/* 当回调函数有alert弹框的时候，窗口会被挡住 by zhugr 2017-08-31
		 window.onfocus=function (){
			try{
				win.focus();
			}catch(e){
				
			}
		};*/
	    window.onclick=function (){
	    	try{
				win.focus();
			}catch(e){
				
			}
	    };
	};
	
}