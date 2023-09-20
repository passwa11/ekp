/*
* @Author: liwenchang
* @Date:   2018-07-31 22:04:29
* @Last Modified by:   liwenchang
* @Last Modified time: 2018-07-31 08:58:14
*/
(function(window){
	var win = window;
	var document = win.document;
	var PROMPT_CONTROL_SELECTOR = "[fd_type='prompt']";

	/*#141095-提示控件，放在明细表里面，显示页展示查看都有问题-开始*/
	FSelect_ImportCss(Com_Parameter.ContextPath + 'sys/xform/designer/prompt/prompt.css');
	function FSelect_ImportCss(url) {
		var link = document.createElement('link');
		link.type = 'text/css';
		link.rel = 'stylesheet';
		link.href = url;
		var head = document.getElementsByTagName('head')[0];
		head.appendChild(link);
	}
	/*#141095-提示控件，放在明细表里面，显示页展示查看都有问题-结束*/

	//所有提示控件对象,key是id,value是控件对象
	var xformPromptsControl = {};
	$(function(){
		//获取明细表外的提示控件
        prompt_ready();
	})

    function prompt_ready(context) {
	    context = context || document;
        var prompts = $(context).find(PROMPT_CONTROL_SELECTOR);
        buildControl(prompts);
        //获取明细表内的控件
        $(document).on("table-add",function(event,source){
            tableAdd(event,source);
        });
    }

    /** 监听高级明细表 */
    $(document).on("detailsTable-init", function(e, tbObj){
        prompt_ready(tbObj);
    })

	/**
	*提示控件对象
	*/
	function promptControl(dom){
		var obj = $(dom);
		//id
		this.id = obj.attr("flagid");
		//dom元素
		this.domElement = dom;
		$(this.domElement).addClass("lui_prompt_tooltip");
		$(this.domElement).css("width","16px");
		//label
		this.labelElement = $(dom).find("[name='" + this.id +"']")[0];
		$(this.labelElement).addClass("lui_prompt_tooltip_drop");
		//div
		this.divElement = $(dom).find("[name='" + this.id +"_content']")[0];
		$(this.divElement).addClass("lui_dropdown_tooltip_menu");
		//提示内容
		this.content = $(this.divElement).html();
		//鼠标悬停事件
		this.onmouseover = _promptOnMouseover;
		//鼠标离开事件
		this.onmouseout = _promptOnMouseOut;
		//提示元素点击事件
		this.contentElementClick = contentElementClick;
		//提示元素定位
		this.contentElementPosition = contentElementPosition;
		$(this.labelElement).on("mouseover",promptOnMouseover);
		$(this.labelElement).on("mouseout",promptOnMouseOut);
		$(this.divElement).on("click",contentElementClick);
	}
	
	/**
	 * 提示元素点击事件,
	 * @param event
	 * @returns
	 */
	function contentElementClick(event){
		event = event || window.event;
		event.cancelBubble = true;
		if (event.stopPropagation) {event.stopPropagation();}
		this.onmouseout = '';
		this.style.display = '';
	};
	
	/**
	 * 控件鼠标悬停事件,显示提示元素
	 * @param event
	 * @returns
	 */
	function _promptOnMouseover(event){
		this.contentElementPosition();
		with(this.divElement.style){
			zIndex = '2000',
			display = 'inline-block';
		}
		var content = htmlUnEscape(this.content);
		//判断是否含有html标签
		var hasTag = /<(\w+)\s*.+\/?>(?:<\/\1>)?/.test(content);
		if (hasTag) {
			this.divElement.innerHTML = content;
		} else {
			this.divElement.innerText = content;
		}
		
		var aObj = $(this.divElement).find("a");
		aObj.each(function(index,dom){
			if(!$(dom).attr("target")){
				$(dom).attr("target","_blank");
			}
		})
	}
	
	/**
	 * 根据元素内容的高度进行显示，该方法主要是兼容div控件套住提示控件时提示控件提示内容被遮挡
	 * 
	 * 暂时这么解决，因为父元素absolute在一些情况下会很飘吧，粗暴解法
	 */
	function showElementByHeight(elem){
		var $divControlDoms = $(elem).parent("xformflag[flagtype='divcontrol']");
		if($divControlDoms.length > 0){
			$(elem).css({
				'position':'absolute'
			})
		}
	}
	
	/**
	 * 根据元素内容的高度进行显示，该方法主要是兼容div控件套住提示控件时提示控件提示内容被遮挡
	 * 
	 * 第二种方案，但是在div控件有一定高度才有效果，若div是套input，那就没有效果
	 */
	function showElementByHeight_1(divElement){
		var $divControlDoms = $(divElement).parents("xformflag[flagtype='divcontrol']");
		if($divControlDoms.length > 0){
			$(divElement).css({
				'padding':'10px',
				'min-width':'unset',
				'height':'auto'
			})
			var height = $(divElement).outerHeight();
			var top = $(divElement).offset().top;
			var $divDom = $divControlDoms.eq(0).children('div').eq(0);
			var divControlHeight = $divDom.outerHeight();
			var divControlTop = $divDom.offset().top;
			var diffVal = Math.abs(top - divControlTop);
			console.log(height+"===="+divControlHeight);
			var width = $(divElement).width();
			if(height+20 > divControlHeight){//提示框内容高度大于div控件高度
				var html = $(divElement).html();
				var $newDiv= $("<div class='tip_content'></div>").html(html);
				$(divElement).empty().append($newDiv);
				$(divElement).css({
					'padding':'0px',
					'min-width':(width+20)+'px'
				})
				//设置最大高度，出现滚动条
				$($newDiv).css({
					'max-height':(divControlHeight-diffVal-50)+'px',
					'overflow-y':'auto',
					'padding':'10px'
				})
			}
		}
	}
	
	/**
	 * 控件鼠标离开事件,判断离开后的鼠标所在的元素是否提示元素,若是，则不隐藏提示元素
	 * 并为提示元素绑定鼠标离开事件，离开提示元素，则隐藏提示元素
	 * @param event
	 * @returns
	 */
	function _promptOnMouseOut(event){
		event = event || window.event;
		if((event.toElement || event.relatedTarget) == this.divElement){
			this.divElement.onmouseout = promptOnMouseOut;
			return;
		}
		var dom = event.toElement;
		if (dom && dom.nodeType){
			var contentObj = $(dom).closest("[name='" + this.id + "_content']");
			if (contentObj.length === 1 && contentObj[0] == this.divElement){
				this.divElement.onmouseout = promptOnMouseOut;
				return;
			}
		}
		with(this.divElement.style){
			display = "none";
		}
	}
	
	/**
	 * 提示元素定位
	 * @returns
	 */
	function contentElementPosition(){
		$(this.divElement).css("display",'');
		//图标x轴坐标
		var x=this.domElement.offsetLeft+document.body.scrollLeft;
		//图标y轴坐标
		var y=this.domElement.offsetTop;
		//scrollWidth 对象的实际内容的宽度
		var scrollWidth = document.body.scrollWidth;
		//scrollHeight 对象的实际内容的高度
		var scrollHeight = document.body.scrollHeight;
		//提示框的宽度
		var divWidth = $(this.divElement).width();
		//提示框的高度
		var divHeight = $(this.divElement).height();
		offset = {};
		if((y - divHeight) < 0){
			offset.top = (y + 16) + 'px';
			$(this.divElement).removeClass();
			$(this.divElement).addClass("lui_dropdown_tooltip_menu_totop");
		}else{
			//图标y坐标大于提示框的高度，提示框的y坐标就是,(图标y-提示框高度)
			//在明细表标题行,向下展示
			if ($(this.divElement).closest("[type='titleRow']").length > 0) {
				$(this.divElement).removeClass();
				$(this.divElement).addClass("lui_dropdown_tooltip_menu_totop");
			} else {
				offset.top = (y - divHeight) + 'px';
			}
		}
		
		var innerContentObj = $(".lui-fm-flexibleL-inner");
		var maxLeft = innerContentObj.outerWidth();
		if((x-110)<0){
			offset.left = (x + 8) + 'px';
			$(this.divElement).addClass("lui_dropdown_tooltip_menu_toleft");
		} else if (x + 110 > maxLeft && innerContentObj.length > 0){
			$(this.divElement).addClass("lui_dropdown_tooltip_menu_toright");
		}else{
			//图标x坐标大于提示框的宽度的一半，提示框的x坐标就是,(图标x-提示框宽度一半)
			offset.left = (x - divWidth/2)+'px';
		}
		$(this.divElement).offset(offset);
	}
	
	/**
	 * 鼠标悬停图标事件，获取对应的提示控件,调用对应控件的悬停事件
	 * @param event
	 * @param src
	 * @returns
	 */
	function promptOnMouseover(event,src){
		event = event || window.event;
		document.body.onclick = bodyClick;
		var dom = $(this).closest(PROMPT_CONTROL_SELECTOR);
		var id = dom.attr("flagid");
		var control = xformPromptsControl[id];
		control.onmouseover(event);
	}
	
	/**
	 * 鼠标离开图标事件,获取对应的提示控件,调用对应控件的离开事件
	 * @param event
	 * @param src
	 * @returns
	 */
	function promptOnMouseOut(event,src){
		event = event || window.event;
		var dom = $(this).closest(PROMPT_CONTROL_SELECTOR);
		var id = dom.attr("flagid");
		var control = xformPromptsControl[id];
		control.onmouseout(event);
	}

	/**
	 * 明细表行增加事件,初始化的时候也会触发此事件
	 * @param event
	 * @param source
	 * @returns
	 */
	function tableAdd(event,source){
		var prompts = $(source).find(PROMPT_CONTROL_SELECTOR);
		buildControl(prompts);
	}
	
	
	function bodyClick(){
		for (var id in xformPromptsControl){
			var control = xformPromptsControl[id];
			$(control.divElement).hide();
		}
	}

	function buildControl(prompts){
		if (prompts instanceof jQuery){
			for (var i = 0; i < prompts.length; i++){
				if(prompts[i] && prompts[i].nodeType){
					var control = new promptControl(prompts[i]);
					xformPromptsControl[control.id] = control;
					var isPrint = $(control.domElement).attr("isprint");
					//打印页面隐藏提示控件
					if (isPrint === "print"){
						$(control["domElement"]).hide();
					}else{
						//不隐藏控件时对被div控件套用的提示控件进行特殊处理（避免因为div控件的overflow属性而被挡住），by suyb
						/*var $divControlDoms = $(prompts[i]).parents("xformflag[flagtype='divcontrol']");
						if($divControlDoms.length > 0){//存在被div控件套住的情况
							$(prompts[i]).children("div").removeClass();
							$(prompts[i]).children("div").addClass("lui_dropdown_tooltip_menu_totop");
						}*/
						showElementByHeight(prompts[i]);
					}
				}
			}
		}
	}
	
	function htmlUnEscape (s){
		if (s == null || s ==' ') return '';
		s = s.replace(/&amp;/g, "&");
		s = s.replace(/&quot;/g, "\"");
		s = s.replace(/&lt;/g, "<");
		s = s.replace(/&nbsp;/g," ");
		return s.replace(/&gt;/g, ">");
	};
})(window);