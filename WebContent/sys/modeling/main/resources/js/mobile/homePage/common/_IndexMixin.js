/**
 * 
 */
define(["dojo/_base/declare", "dojo/dom-construct","mui/i18n/i18n!sys-modeling-main"], function(declare, domConstruct,modelingLang){
	
	return declare("sys.modeling.main.resources.js.mobile.homePage.common._IndexMixin",null,{
		
		// 获取当前页签处于列表页签的第几个页签，用于路由
		getTabIndex : function(key, arr){
			var tabIndex = 0;
			if(key && arr && arr.length > 0){
				for(var i = 0;i < arr.length;i++){
					if(key === arr[i].value){
						tabIndex = i;
						break;
					}
				}
			}
			return tabIndex;
		},
		
		showNoAuth : function(domNode,style1,style2,fontStyle,imageStyle,textStyle,notify){
			var html = "";
			html += "<div class='mui-auth-refuse'";
			if(style1){
				html += " style='" + style1 +"'";
			}
			html += ">";
			html += "<div class='no-auth-div'";
			if(style2){
				html += " style='" + style2 +"'";
			}
			html += ">";
			html += "<i class='no-auth-image'";
			if(imageStyle){
				html += " style='" + imageStyle +"'";
			}
			html += "></i>";
			html += "<div class='no-auth-text'";
			if(textStyle){
				html += " style='" + textStyle +"'";
			}
			html += ">";
			html += "<div class='no-auth-message'";
			if(fontStyle){
				html += " style='" + fontStyle +"'";
			}
			html += ">";
			if(notify){
				html += modelingLang['mui.modeling.no.permission.view']+",</div>";
			}else{
				html += modelingLang['mui.modeling.no.permission.view']+"</div>";
			}
			html += "<div class='no-auth-tip'";
			if(fontStyle){
				html += " style='" + fontStyle +"'";
			}
			html += ">"+modelingLang['mui.modeling.contact.administrator.open.permissions']+"</div>";
			html += "</div>";
			html += "</div>";
			html += "</div>";
			domConstruct.place(domConstruct.toDom(html),domNode,"last");
		},
		showNoData : function(domNode,style1,style2,fontStyle,imageStyle,textStyle,notify){
			var html = "";
			html += "<div class='mui-auth-refuse'";
			if(style1){
				html += " style='" + style1 +"'";
			}
			html += ">";
			html += "<div class='no-auth-div'";
			if(style2){
				html += " style='" + style2 +"'";
			}
			html += ">";
			html += "<i class='no-auth-image'";
			if(imageStyle){
				html += " style='" + imageStyle +"'";
			}
			html += "></i>";
			html += "<div class='no-auth-text'";
			if(textStyle){
				html += " style='" + textStyle +"'";
			}
			html += ">";
			html += "<div class='no-auth-message'";
			if(fontStyle){
				html += " style='" + fontStyle +"'";
			}
			html += ">";
			if(notify){
				html += modelingLang['mui.modeling.no.permission.view']+",</div>";
			}else{
				html += modelingLang['mui.modeling.no.permission.view']+"</div>";
			}
			html += "<div class='no-auth-tip'";
			if(fontStyle){
				html += " style='" + fontStyle +"'";
			}
			html += ">"+modelingLang['mui.modeling.questions.contact.administrator']+"</div>";
			html += "</div>";
			html += "</div>";
			html += "</div>";
			domConstruct.place(domConstruct.toDom(html),domNode,"last");
		},
		//获取当前页签的视图类型
		getNodeType : function(key,arr) {
			var nodeType = "";
			if(key && arr && arr.length > 0){
				for(var i = 0;i < arr.length;i++){
					if(key === arr[i].value){
						nodeType = arr[i].nodeType;
						break;
					}
				}
			}
			return nodeType;
		}
	});
});