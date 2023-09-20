/**********************************************************
功能：片段集控件
使用：
	
作者：李文昌
创建时间：2018-05-22
**********************************************************/
Designer_Config.controls['fragmentSet'] = {
		dragRedraw : false,
		type : "fragmentSet",
		storeType : 'layout',
		inherit    : 'base',
		onDraw : _Designer_Control_FragmentSet_OnDraw,
		onInitialize : _Designer_Control_FragmentSet_OnInitialize,
		destroy : _Designer_Control_FragmentSet_Destroy,
		_destroy : Designer_Control_Destroy,
		drawXML : _Designer_Control_FragmentSet_DrawXML,
		onDrawEnd : null,
		// 在新建流程文档的时候，是否显示
		hideInMainModel : false,
		implementDetailsTable : false,
		info : {
			name: Designer_Lang.controlFragmentSet_info_name
		},
		fragmentSetId:"",
		resizeMode : 'no'
		
};

Designer_Config.operations['fragmentSet'] = {
		lab : "2",
		imgIndex : 65,
		title : Designer_Lang.controlFragmentSet_info_name,
		run : function (designer) {
			var isSubForm = _Designer_Control_Fragmentset_isSubForm();
			if (isSubForm){//暂不支持多表单
				alert(Designer_Lang.controlFragmentSet_not_support);
				return;
			}
			designer.toolBar.selectButton('fragmentSet');
		},
		type : 'cmd',
		order: 81,
		select: true,
		cursorImg: 'style/cursor/fragmentSet.cur',
		isShow: _Designer_Control_FragmentSet_isShow
	};

Designer_Config.buttons.tool.push("fragmentSet");
Designer_Menus.form.menu['fragmentSet'] = Designer_Config.operations['fragmentSet'];


function _Designer_Control_Fragmentset_isSubForm(){
	return (typeof Designer.instance.parentWindow.Form_getModeValue != "undefined") && 
			Designer.instance.parentWindow.Form_getModeValue(Designer.instance.fdKey)== 
			Designer.instance.template_subform;
}

/**
 * 片段集或者是通用模板中隐藏这个控件
 * @returns
 */
function _Designer_Control_FragmentSet_isShow(){
	//表单模板历史版本编辑页面标识,屏蔽通用
	var history = $("[name='_sysFormTemplateHistory']", parent.document);
	if (Designer.instance.fdModel || history.length != 0){
		return true;
	}else{
		return false;
	}
}

/**
 * 绘制片段集图标
 * @param parentNode
 * @param childNode
 * @returns
 */
function _Designer_Control_FragmentSet_OnDraw(parentNode, childNode) {
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	domElement.id = this.options.values.id;
	domElement.style.width='100%';
	domElement.style.display="inline";
	domElement.style.margin = "0 7px";
	var div = document.createElement("label");
	var span = document.createElement("span");
	//id隐藏域
	var fragmentSetContent = document.createElement("input");
	fragmentSetContent.type = 'hidden';
	fragmentSetContent.name = this.options.values.id + '_fragmentSet_id';
	span.appendChild(fragmentSetContent);
	//文本隐藏域
	fragmentSetContent = document.createElement("input");
	fragmentSetContent.type = 'hidden';
	fragmentSetContent.name = this.options.values.id + '_fragmentSet_name';
	span.appendChild(fragmentSetContent);
	//背景样式
	span.style.background = "url(style/img/fragmentSet.png) no-repeat";
	span.style.margin = '0px 0px 0px 0px';
	span.style.width='100%';
	span.style.height='24px';
	span.style.display="inline-block";
	span.style.position = 'relative';
	//绑定鼠标双击事件
	span.setAttribute("ondblclick","Designer_Control_FragmentSet_ShowEditFrame(event, this);");
	
	div.appendChild(span);
	domElement.innerHTML = div.innerHTML;
	domElement.title = Designer_Lang.controlFragmentSet_info_name;
	
	Designer_Config.controls.fragmentSet.ondrawDom = domElement;
}

/**
 * 片段集控件图标双击事件
 * @param event
 * @param dom
 * @returns
 */
function Designer_Control_FragmentSet_ShowEditFrame(event, dom) {
	//防止view模式下，点击控件出现异常
	if(!Designer.instance.shortcuts || Designer.instance.isMobile){
		return;
	}
	
	//阻止冒泡
	event = event || window.event;
	event.cancelBubble = true;
	if (event.stopPropagation) {event.stopPropagation();}
	
	var $div,fdId;
	$div = $(dom).closest("div[fd_type='fragmentSet']");//获取控件id
	fdId = $div.attr("id");

	dom = dom ? dom : this;
	
	var idField = fdId + '_fragmentSet_id';
	var nameField = fdId + '_fragmentSet_name';
	//片段集对象
	var self = _Designer_Control_FragmentSet_getControlByDom($div[0]);
	var fdModelName = $("input[type='hidden'][name='fdModelName']",window.top.document).val();
	action = function(rtnVal) {
		if(rtnVal==null)
			return;
		var data = new KMSSData();
//		data.AddBeanData('sysFormFragmentSetTreeService&fdId='+rtnVal.data[0].id);
		var fragmentSetId = rtnVal["_key"];
		data.AddBeanData('sysFormFragmentSetTreeService&fdId='+fragmentSetId);
		var returnData = data.GetHashMapArray()[0];
		var html = returnData['fdDesignerHtml'];
		//先销毁之前的控件
		_Designer_Control_FragmentSet_destoryChildrenByDom($div[0]);
		var wrapHtml = "<span controlId='" + fdId + "' fragmentSetId='" + fragmentSetId + "' flagtype='fragmentSet'" + ">";
		wrapHtml += html;
		wrapHtml += "</span>";
		$div.append(wrapHtml);
		var designerHtml = Designer.instance.builderDomElement.innerHTML;
		self.options.values.fragmentSetId = fragmentSetId;
		//解析片段集Html，生成控件
		Designer.instance.builder.parse(self,$div[0]);
		//记录控件和片段集{controlId,fragmentSetId}
		_Designer_Control_FragmentSet_SetId({"controlId":fdId,"fragmentSetId":fragmentSetId});
		//重新调整虚线框的位置
		Designer.instance.builder.resizeDashBox.attach(self);
	};
	//树形式
	//获取主文档全类名
	var fdMainModelName = window.top._xform_MainModelName;
	var url = "sys/xform/fragmentSet/dialog/dialog.jsp?springBean=fragmentSetControlTreeBean&fdMainModelName=" + fdMainModelName;
	new _fragmentSet_ModelDialog_Show(Com_Parameter.ContextPath+url,"",action).show();
	//列表形式
	/*var dialog = new KMSSDialog(false);
	dialog.BindingField(idField, nameField, null, null);
	dialog.SetAfterShow(action);
	
	dialog.URL = Com_Parameter.ContextPath + "sys/xform/designer/fragmentSet/sysFormFragmentSet_select.jsp?fdModelName=" + fdMainModelName;
	dialog.Show(window.screen.width*710/1366,window.screen.height*550/768);*/
}

/**
 * 根据控件dom元素获取控件对象
 * @param dom
 * @returns
 */
function _Designer_Control_FragmentSet_getControlByDom(dom){
	var builder = Designer.instance.builder;
	return builder.getControlByDomElement(dom);
}

/**
 * 销毁片段集的所有子控件
 * @param dom
 * @returns
 */
function _Designer_Control_FragmentSet_destoryChildrenByDom(dom){
	//根据dom元素获取片段集控件对象
	var fragmentSet = _Designer_Control_FragmentSet_getControlByDom(dom);
	//获取所有的子控件
	var children = fragmentSet.children;
	var builder = Designer.instance.builder;
	//销毁所有的子控件
	builder.deleteControls(children);
	$(dom).find("span[flagtype='fragmentSet']").remove();
}

/**
 * 将片段集控件的id和引用的片段集id保存到Designer对象属性中
 * @param info
 * @returns
 */
function _Designer_Control_FragmentSet_SetId(info){
	var fragmentSetInfo = Designer.instance.fragmentSetIds;
	if(fragmentSetInfo.length === 0){
		fragmentSetInfo.push(info);
		return ;
	}
	var isContainControl = false;
	$.each(fragmentSetInfo,function(i,value){
		if(info["controlId"] === value["controlId"]){
			isContainControl = true;
			value["fragmentSetId"] = info["fragmentSetId"];
			return false;
		}
	});
	if (!isContainControl){
		fragmentSetInfo.push(info);
	}
}

/**
 * 片段集控件删除事件
 * @returns
 */
function _Designer_Control_FragmentSet_Destroy(){
	var fragmentSetIds = Designer.instance.fragmentSetIds;
	var domElement = this.options.domElement;
	var fdId = $(domElement).attr("id");
	var spanElement = $(domElement).find("span[flagtype='fragmentSet']");
	var fragmentSetId = spanElement.attr("fragmentsetid");
	for (var i = 0; i < fragmentSetIds.length; i++){
		var obj = fragmentSetIds[i];
		if (obj["fragmentSetId"] == fragmentSetId && fdId == obj["controlId"]){
			fragmentSetIds.splice(i,1);
			break;
		}
	}
	Designer.instance.fragmentSetIds = fragmentSetIds;
	this._destroy();
}

function _Designer_Control_FragmentSet_OnInitialize(){
	var controlId = this.options.values.id;
	var fragmentSetWrap = $("span[controlId='" + controlId + "']");
	if (fragmentSetWrap.length > 0){
		var fragmentSetId = fragmentSetWrap.attr("fragmentsetid");
		_Designer_Control_FragmentSet_SetId({"controlId":controlId,"fragmentSetId":fragmentSetId});
	}else{
		if(this.options.values.fragmentSetId){
			var data = new KMSSData();
			var fragmentSetId = this.options.values.fragmentSetId;
			data.AddBeanData('sysFormFragmentSetTreeService&fdId='+fragmentSetId);
			var returnData = data.GetHashMapArray()[0];
			var html = returnData['fdDesignerHtml'];
			if (Designer.instance.isMobile) {
				var result = parent.requestGenerateMobileHtml({"html":html});
				html = result.html;
			}
			var $div = $(this.options.domElement);
			//先销毁之前的控件
			_Designer_Control_FragmentSet_destoryChildrenByDom($div[0]);
			var wrapHtml = "<span controlId='" + $div.attr("id") + "' fragmentSetId='" + fragmentSetId + "' flagtype='fragmentSet'" + ">";
			wrapHtml += html;
			wrapHtml += "</span>";
			$div.append(wrapHtml);
			//解析片段集Html，生成控件
			Designer.instance.builder.parse(this,$div[0]);
			//记录控件和片段集{controlId,fragmentSetId}
			_Designer_Control_FragmentSet_SetId({"controlId":$div.attr("id"),"fragmentSetId":fragmentSetId});
			//重新调整虚线框的位置
			Designer.instance.builder.resizeDashBox.attach(this);
			if (Designer.instance.isMobile) {
				Designer.instance.mobileDesigner.loadForm();
			}
		}
	}
	
}

/**
 * 绘制片段集内的控件xml
 * @returns
 */
function _Designer_Control_FragmentSet_DrawXML() {
	var dom = this.options.domElement;
	var fragmentSetId = $(dom).find("span[controlid='" + this.options.values.id +"']").attr("fragmentsetid");
	if (this.children.length > 0) {
		var xmls = [];
		//必须有且只有一个根元素,不然parseXml报错
		xmls.push("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		xmls.push("<fragmentSet>");
		this.children = this.children.sort(Designer.SortControl);
		for (var i = 0, l = this.children.length; i < l; i ++) {
			var c = this.children[i];
			if (c.drawXML) {
				var xml = c.drawXML();
				if (xml != null)
					xmls.push(xml, '\r\n');
			}
		}
		xmls.push("</fragmentSet>");
		var xml = xmls.join('');
		var tempXml = $.parseXML(xml);//必须有且只有一个根元素,不然parseXml报错
		//解析xml,加上片段集id标识
		for(var node=tempXml.firstChild; node!=null; node=node.nextSibling){
			if(node.nodeType==1 && node.tagName === "fragmentSet"){
				$(node).children().each(function () {
					var name = $(this).attr("name");
					if (name){
						var customElementProperties = $(this).attr("customElementProperties");
			            if (customElementProperties){
			            	customElementProperties = customElementProperties.replace(/quot;/g,"\"");
			            	customElementProperties = customElementProperties.replace(/&quot;/g,"\"");
			            	customElementProperties = JSON.parse(customElementProperties);
			            	customElementProperties.fragmentSetId = fragmentSetId;
			            }else{
			            	customElementProperties = {fragmentSetId:fragmentSetId};
			            }
			            customElementProperties = JSON.stringify(customElementProperties);
			        	$(this).attr("customElementProperties",customElementProperties);
					}
		        });
			}
		}
		if("ActiveXObject" in window){
			var rtnVal =new XMLSerializer().serializeToString(tempXml);
			rtnVal = rtnVal.replace("<fragmentSet>","");
			rtnVal = rtnVal.replace("</fragmentSet>","");
		}else{
			var rtnVal = $(tempXml).find("fragmentSet").html();
		}
		
		return rtnVal;
	}
	return '';
}


//选择片段集
function _fragmentSet_ModelDialog_Show(url,data,callback){
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
	    window.onclick=function (){
	    	try{
				win.focus();
			}catch(e){
				
			}
	    };
	};
	
}