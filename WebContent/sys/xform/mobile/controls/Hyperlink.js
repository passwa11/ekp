/**
 * 超链接表单控件
 */
define(['dojo/_base/declare','dijit/_WidgetBase','dojo/_base/lang','dojo/dom-construct','dojo/dom-style','dojo/dom-class','dojo/dom-attr','mui/util','mui/form/_AlignMixin'],
		function(declare, WidgetBase, lang, domConstruct, domStyle, domClass, domAttr, util, _AlignMixin){
	return declare('sys.xform.mobile.controls.Hyperlink',[ WidgetBase, _AlignMixin ],{

		isForm: true,
		
		/* 超链接外层容器class类名    */
		baseClass: 'muiHyperlinkWrap',
		
		/* 超链接标题 (超链接的显示文本) */
		subject: '',
		
		/* 超链接URL */
		linkUrl: '',
		
		/* 超链接   target 属性 */
		linkTarget: '_blank',
		
	    /* 超链接行内样式 */
	    linkStyle: '',
		
	    /* 组件布局朝向：   horizontal:横向, vertical:纵向(默认),  none: 不做布局处理仅显示控件 */
	    orient: 'none',	
		
	    /* 对齐方式：    left:居左、right:居右、center:居中  */
	    align: 'left',    
	    
	    /* 是否隐藏超链接  */
	    hidden: false,
	    
		buildRendering: function(){
			this.inherited(arguments);
			
			// 设置隐藏
			if(this.hidden){
				domStyle.set(this.domNode, { 'display': 'none' } );
			}
			
        	// 表单元素类名
        	domClass.add(this.domNode,'muiFormEleWrap');
        	
        	// 对齐方式类名: 左对齐（muiFormLeft）、右对齐（muiFormRight）、居中对齐（muiFormCenter）
        	domClass.add(this.domNode,'muiForm'+this.align.substring(0,1).toUpperCase()+this.align.substring(1));
			
        	if(this.orient=='vertical'){
    			// 标题容器DOM
    			var subjectNode = domConstruct.create('div',{ 'className':'muiHyperlinkSubject' },this.domNode);
    			
    			// 标题内容（muiFormEleTip、muiFormEleTitle 这些类名是为了保持与表单页面的其它的标准组件一致的样式格式）
    			var tipNode = domConstruct.create("div",{ 'className':'muiFormEleTip' }, subjectNode);
    			var titleNode = domConstruct.create("span", { 'className':'muiFormEleTitle', 'innerHTML':util.formatText(this.subject) },tipNode);        		
        	}

			// 链接DOM(添加label节点并设置xform_linkLabel类名以及添加fd_type属性)
			var linkLabelNode = domConstruct.create('label',{ 'className':'xform_linkLabel' },this.domNode);
			domAttr.set(linkLabelNode,'fd_type','linkLabel');
			var linkAttr = {
				'className': 'muiHyperlink',
				'_target': this.linkTarget,
				'_href': this.linkUrl,
				'href': 'javascript:void(0)'					
			};
			if(this.linkStyle){
				linkAttr['style'] = this.linkStyle;
			}
			var linkNode = domConstruct.create('a',linkAttr,linkLabelNode);
			linkNode.innerText = this.subject;
			
			// 绑定点击事件
			this.connect(linkNode, 'click', lang.hitch(this, function() {
				window.open(this.linkUrl,'_self');
			}));
			
		}
		
		
	});
	
});