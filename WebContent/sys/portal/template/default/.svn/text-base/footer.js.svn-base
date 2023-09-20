//语言切换
function changeLang(j_lang) {
	var txt = $("select[name=j_lang]").find("option:selected").text();
	if (equalsIgnoreCase(Com_Parameter.Lang, "en-US")) {
		var val = $("select[name=j_lang]").find("option:selected").val();
		if (equalsIgnoreCase(val, "zh-CN"))
			txt = "Chinese(Simplified)";
		if (equalsIgnoreCase(val, "zh-HK"))
			txt = "Chinese(Traditional)";
	}
	
	seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
		dialog.confirm(langSwitch.replace("{0}", txt), function(value){
			if(value){
				var url = document.location.href;
				var temp = location.href.split("#");
				var isPage = false;
				if (temp.length > 1) {
					var urlPrx = url.substring(0,url.indexOf('#'));
					var hash = url.substring(url.indexOf('#'),url.length);
					url = urlPrx;
					
					if(hash.indexOf("pageId") > -1){
						isPage = true;
					}
					if(!isPage){
						url = urlPrx + hash;
					}
				}else{
					if(url.indexOf("pageId") > -1){
						isPage = true;
					}
					if(isPage){
						if(location.search !=""){
							var paraList = window.location.search.substring(1).split("&");
							var newUrl = url.substring(0,url.indexOf("?"));
							var i, j, para, pValue;
							for(i=0; i<paraList.length; i++){
								j = paraList[i].indexOf("=");
								if(j==-1)
									continue;
								para = paraList[i].substring(0, j);
								pValue = Com_GetUrlParameter(url, para);
								if(pValue && para != "pageId")
									newUrl = Com_SetUrlParameter(newUrl, para, decodeURIComponent(paraList[i].substring(j+1)));
							}
							url = newUrl;
						}
					}
				}
				url = Com_SetUrlParameter(url, "j_lang", j_lang);
				location.href = url;
			} else {
				$("select[name=j_lang]").find("option").attr("selected", false);
				$.each($("select[name=j_lang]").find("option"), function(i, n) {
					if (equalsIgnoreCase(Com_Parameter.Lang, $(n).val()))
						$(n).attr("selected", true);
				});
			}			
		});
	});
}

function equalsIgnoreCase(str1, str2) {
	if (str1.toUpperCase() == str2.toUpperCase())
		return true;
	else
		return false;
}

window.onload = function() {
	$(document.getElementsByName('j_lang')[0]).addClass('i18n_select');
}