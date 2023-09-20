function kkConfig() {
	this.extend = function(target, exts) {
		if (!exts)
			return target;
		if (typeof exts === "string") {
			exts = eval("(" + exts + ")");
			return this.extend(target, exts);
		}
		for (var name in exts) {
			var copy = exts[name];
			if (typeof copy === "object") {
				target[name] = this.extend(target[name], copy);
			} else {
				target[name] = exts[name];
			}
		}
		return target;
	};
	this.options = {
		orgs : '',// 组织架构名字
		linkType : 'im',// tempgroup//群 im个人
		showMenu : 1, // 是否显示下拉按钮
		serverLink : "",// 连接kk服务器前缀
		idPrefix : "ims_kk_",// 自动生成图片的前缀
		kkLink : "landray://",// 连接kk客户端前缀
		def_UType : '0', // 0 1 2 3
		def_PType : '0', // 0 1 2 3
		img_orgs : '', //图片所链的组织架构,默认跟orgs一样
		uuid : '', //图片id
		leftHtml : '', //左文字
		rightHtml : '', //右文字
		refresh:true, //实时刷新 
		refreshTime:5000, //实时刷新
		htmlElem : " border='0' title='title' ", //图片标签额外元素
		stylePath:"third/im/kk/resource/css/tipsy.css",//引入css 路径 跟menuCfg.className 
		writeText : {
			//普通的超链接规则
			textRule : "!{leftHtml}<a href=\"!{kkLink}!{linkType}?u=!{orgs}&t=!{def_UType}\">"
					+ "<img id=\"!{uuid}\" src=\"!{serverLink}?p=!{def_PType}:!{img_orgs}\" !{htmlElem} /></a>!{rightHtml}",
		    //群组的超链接规则
		    textGroupRule : "!{leftHtml}<a href=\"!{kkLink}!{linkType}?u=!{orgs}&t=!{def_UType}\">"
					+ "<img id=\"!{uuid}\" src=\"!{serverLink}/images/group.png\" !{htmlElem} ></img></a>!{rightHtml}" 
		},
		//弹出框的内容
		menuCfg : {
			menuHtml : {
				//弹出框每个连接规则
				menuRule : "<a href=\"!{kkLink}!{linkType}?u=!{orgs}&t=!{def_Type}\">!{menuText}</a>"
			},
//			弹出框连接
			menuLink : [{
						menuText : ThirdImKKObject.Lang.menuType.sendmsg,//"发起会话",
						type : '0'
					}, {
						menuText : ThirdImKKObject.Lang.menuType.sendsms,//"发送短信",
						type : '1'
					}, {
						menuText :ThirdImKKObject.Lang.menuType.sendfile,//"传输文件",
						type : '2'
					}, {
						menuText :ThirdImKKObject.Lang.menuType.viewinfo, //"查看资料",
						type : '3'
					}],
//		====jquery tipsy 插件配置			
			fade : true, // 是否显示动画
			gravity : 's', // 显示的位置
			html : true,    //是否为html参数
			className:"defaults"//更换皮肤
		}

	};
	this.fn = {
		getUUID : function(options) {
			var id = new Date().getTime();
			return options.idPrefix + id; // .idPrefix + id;
		},
		getWriteText : function(options) {
			if (!options || !options.writeText)
				return "";
			var text="";
			if(options["linkType"]=="tempgroup"){
			text=options.writeText.textGroupRule;
			}
			else{
			text = options.writeText.textRule;
			}
			for (var name in options) {
				if (name != 'writeText' && name != 'menuCfg') {
					if (name == 'uuid') {
						if (options[name]) {
							text = text.replace("!{" + name + "}",
									options[name]);
							options.uuid = options[name];
						} else {
							var uuid = this.getUUID(options);
							text = text.replace("!{" + name + "}", uuid);
							options.uuid = uuid;
						}
					}
					if (name == 'img_orgs') {
						if (!options['img_orgs']) {
							options['img_orgs'] = options["orgs"];
						}
					}
					text = text.replace("!{" + name + "}", options[name]);
				}
			}
			return text;
		},
		getMenuList : function(options) {
			var menu_ = [];
			if (options["showMenu"]) {
				var menucfg = options["menuCfg"];
				if (menucfg) {
					var menuText = menucfg["menuHtml"]["menuRule"];
					for (var i = 0, len = menucfg["menuLink"].length; i < len; i++) {
						var menu = menucfg["menuLink"][i];
						menu_.push(menuText.replace("!{kkLink}",
								options["kkLink"]).replace("!{linkType}",
								options["linkType"]).replace("!{orgs}",
								options["orgs"]).replace("!{def_Type}",
								menu["type"]).replace("!{menuText}",
								menu["menuText"]));
					}
				}
			}
			return menu_.join("");

		},
		//绑定下拉按钮
		bindMenu : function(elemId, menuOptions) {
//			使用jQuery 的tipsy插件
			$("#" + elemId).tipsy(menuOptions);
		},
//		实时刷新图片
		refreshImg:function(elemId,options){
			if(options["refresh"]&&options["linkType"]!="tempgroup"){
			   var imgElem=document.getElementById(elemId);
			   var defSrc=imgElem.src;
 			   setInterval(function(){
 			   	var time=new Date().getTime();
 			    imgElem.src=defSrc+"&"+time;
 			   },50000);
			}
		}
	}
}
