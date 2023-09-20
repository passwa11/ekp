/**********************************************************
功能：HTML源码编辑对话框
使用：
	
作者：傅游翔
创建时间：2009-11-12
**********************************************************/

Designer_Config.operations['srceditor'] = {
		lab : "2",
		imgIndex : 20,
		title : Designer_Lang.edit_form_HTMLCode,
		run : function (designer) {
			var title = Designer_Lang.form_resource+'<button class="btnopt" onclick="Designer_Operation_SrceditorClose(this);">'+Designer_Lang.panelCloseTitle+'</button>';
			var domElement = document.createElement('div');
			domElement.className = 'srceditor';
			Designer.instance.srceditorOldscrtop = Designer.getDocumentAttr("scrollTop");
			document.documentElement.scrollTop = document.body.scrollTop = 0;
			document.documentElement.style.overflow = document.body.style.overflow = 'hidden';
			//document.body.style.overflowY = 'hidden';
			document.body.appendChild(domElement);
			var height = Designer.getDocumentAttr("clientHeight");
			var originalHeight = height;
			with(domElement.style) {
				position = 'absolute';
				width = Designer.getDocumentAttr("clientWidth") + 'px';
				height = Designer.getDocumentAttr("clientHeight") + 'px';
				top = Designer.getDocumentAttr("scrollTop") + 'px'; left = '0';
				zIndex = '9000';
				backgroundColor = '#FFF';
			}
			
			var setHTMLbtn = document.createElement('button');
			setHTMLbtn.innerHTML = Designer_Lang.button_setterHTML;
			setHTMLbtn.className = 'btnopt';
			var titleDom = document.createElement('div');
			titleDom.innerHTML = title;
			titleDom.appendChild(setHTMLbtn);
			titleDom.style.height = (20) + 'px';
			domElement.appendChild(titleDom);
			
			var bodyDom = document.createElement('textarea');
			bodyDom.value = designer.getHTML();
			bodyDom.style.width = '99.5%';
			bodyDom.style.height = height-60 + 'px';
			domElement.appendChild(bodyDom);
			
			setHTMLbtn.onclick = function() {
				//designer.builder.controls = [];
				//var oldHtml = designer._oldHTML;
				designer.setHTML(bodyDom.value);
				//designer._oldHTML = oldHtml;
				Designer_Operation_SrceditorClose(this,"setHTMLbtn");
				
			};
		},
		type : 'cmd',
		order: 21,
		line_splits_end:true,
		icon_s:true,
		select: false,
		isAdvanced: true,
		validate : function(designer) {
			return designer.toolBar.isShowAdvancedButton;
		}
	};

function Designer_Operation_SrceditorClose(dom) {
	while((dom = dom.parentNode).className != 'srceditor') {}
	document.documentElement.style.overflow = document.body.style.overflow = '';
	if (typeof arguments[1] == "undefined"){
		document.documentElement.scrollTop = document.body.scrollTop = Designer.instance.srceditorOldscrtop;
	}
	document.body.removeChild(dom);
}

Designer_Config.buttons.head.push("srceditor");
