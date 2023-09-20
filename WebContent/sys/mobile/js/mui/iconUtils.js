define([
    "dojox/mobile/iconUtils",
	"dojo/_base/lang",
	"dojo/dom-class",
	"dojo/dom-construct",
	"dojo/dom-style"
	], function(iconUtils, lang, domClass, domConstruct, domStyle) {
	
	var setIcon = function(
			/*String*/icon, 
			/*String*/iconPos, 
			/*DomNode*/iconNode, 
			/*String?*/alt, 
			/*DomNode*/parent, 
			/*DomNode?*/refNode, 
			/*String?*/pos) {
		if(!parent || !icon && !iconNode){ return null; }

		if(icon && icon !== "none" && (icon.indexOf('mui-') > -1 || icon.indexOf('muis-') > -1)){
			if(iconNode && iconNode.tagName !== "I"){
				domConstruct.destroy(iconNode);
				iconNode = null;
			}
			iconNode = this.createIcon(icon, null, iconNode, alt, parent, refNode, pos);
			return iconNode;
		}
		this._setIcon.apply(this, arguments);
	};
	
	var createIcon = function(
			/*String*/icon, 
			/*String?*/iconPos, 
			/*DomNode?*/node, 
			/*String?*/title, 
			/*DomNode?*/parent, 
			/*DomNode?*/refNode, 
			/*String?*/pos) {
		if(icon && icon !== "none" && (icon.indexOf('mui-') > -1 || icon.indexOf('muis-') > -1)){
			if(!node){
				node = domConstruct.create("I", null, refNode || parent, pos);
			}
			
			if(icon.indexOf('mui-') > -1){
				icon += " mui"
			}
			if(icon.indexOf('muis-') > -1){
				icon += " fontmuis"
			}
			
			node.title = title;
			domClass.add(node, icon);
			if(iconPos && parent){
				var arr = iconPos.split(/[ ,]/);
				domStyle.set(parent, {
					position: "relative",
					width: arr[2] + "px",
					height: arr[3] + "px"
				});
				domClass.add(parent, "mblSpriteIconParent");
			}
			return node;
		}
		return	this._createIcon.apply(this, arguments);
	};
	
	/********************************** 钉钉专用 ***********************************/
	var createDingIcon = function(fdName, tagName) {
		tagName = tagName || "span";
		var dingImg = "<" + tagName + " class='mui-address-imgcontainer-sm mui-ding-address-imgcontainer-sm'>"
		+ "<span class='mui-ding-address-name'>"
		+ getDingImgName(fdName)
		+ "</span></"+ tagName +">";
		return dingImg;
	};
	
	var getDingImgName = function(fdName){
		let showName = fdName || ''; 
		let arr =[];
		let _isEnglish = showName.match(/^([a-zA-Z]|\s|,|\.)+$/) !== null ? true : false; 
		if (_isEnglish) {
			//英 文 名 字 将 转 为 空 格 & 将 连 续 空 格 转 换 为 单 个 空 格
			showName = showName.replace(/,|\./g, ' ').replace(/\s+/g,' ');
			arr = showName.split(' ');
			if (arr.length === 1) {
				return showName.slice(0, 2);
			}
			return arr[0].slice(0, 1) + arr[1].slice(0, 1);
		}
		//中 文 名 字 - 取 后 两 位
		return showName.replace(/,|\.|\s+/g, '').slice(-2);
	}
	/********************************** 钉钉专用 ***********************************/	 
	
	iconUtils._setIcon = iconUtils.setIcon;
	iconUtils.setIcon = setIcon;
	
	iconUtils._createIcon = iconUtils.createIcon;
	iconUtils.createIcon = createIcon;
	
	iconUtils.createDingIcon = createDingIcon;
	
	return iconUtils;
});