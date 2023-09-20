define(["dojo/_base/declare", "mui/i18n/i18n!km-archives:py"], function(declare, msg) {
	  var _functionalMenus = [[{
	      	icon: "muis-case-approval",
	    	text: msg['py.DangAnShenHe'],
	    	href: "/km/archives/mobile/examine/"
	    },
	    {
	        icon: "muis-shape-combine",
	        text: msg['py.DangAnJieYue'],
	        href: "/km/archives/mobile/km_archives_borrow/"
	    }]];
	  if(window.functionalMenus){
		  for(var i=0;i<window.functionalMenus.length;i++){
	          var authType = window.functionalMenus[i];
			  if(authType=="auth_appraisal"){
				  _functionalMenus[0].push({
			        	icon: "muis-case-authenticate",
			        	text: msg['py.DangAnJianDing'],
			        	href: "/km/archives/mobile/km_archives_appraisal/"
			   	  });			  
			  }else if(authType=="auth_destroy"){
				  _functionalMenus[0].push({
			        	icon: "muis-case-destory",
			        	text: msg['py.DangAnXiaoHui'],
			        	href: "/km/archives/mobile/km_archives_destroy/"
				  });			  
			  }else if(authType=="auth_prefile"){
				  _functionalMenus[0].push({
			        	icon: "muis-pigeonhole",
			        	text: msg['py.YuGuiDangKu'],
			        	href: "/km/archives/mobile/km_archives_prefile/"
				  });			  
			  }
		  }
	  }
	  
	return declare("", null, {
		datas: _functionalMenus
	})
})
