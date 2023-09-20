<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>


<%@page import="com.landray.kmss.third.im.kk.util.NotifyConfigUtil"%>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}third/im/kk/resource/im_kk_lang.jsp"></script>
<script>
//引入jQuery,基于kms的需要
if(typeof(jQuery)=='undefined'){
Com_IncludeFile("jquery.js");
}
</script>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}third/im/kk/resource/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}third/im/kk/resource/js/kkDefaults.js"></script>


<script type="text/javascript">
    function loadScript(path){
       var jsFile=document.createElement("script");
       jsFile.setAttribute("type","text/javascript"); 
       jsFile.setAttribute("src", "${KMSS_Parameter_ContextPath}"+path); 
       document.getElementsByTagName("head")[0].appendChild(jsFile);
        }

    function loadCss(path){
    	var cssFile=document.createElement("link");
    	cssFile.setAttribute("rel","stylesheet"); 
    	cssFile.setAttribute("type", "text/css");
    	cssFile.setAttribute("href", "${KMSS_Parameter_ContextPath}"+path); 
        document.getElementsByTagName("head")[0].appendChild(cssFile);
    }

</script>
<div>
<script type="text/javascript">
	;( function() {
		var kkCfg=new kkConfig();
		//NotifyConfigUtil 用途:如果kmssconfig获取不到信息到 KK 资源文件获取
        var server="http://!{ip}:!{port}/kkonline".replace("!{ip}","${param.awareIp}").replace("!{port}","${param.awarePort}");
        kkCfg.options=kkCfg.extend(kkCfg.options,{serverLink:server});
		kkCfg.options=kkCfg.extend(kkCfg.options,{imsName:"${param.imName}"});
		kkCfg.options=kkCfg.extend(kkCfg.options,"${param.imParams}");
		loadCss(kkCfg.options["stylePath"]);
		//loadScript("${KMSS_Parameter_ContextPath}"+"sys/ims/kk/js/jquery.tipsy.js");
		var writeText=kkCfg.fn.getWriteText(kkCfg.options);
		document.write(writeText);
		var menuDiv=kkCfg.fn.getMenuList(kkCfg.options);
		var menuCfg=kkCfg.options["menuCfg"];
		menuCfg.title=menuDiv;
		if(kkCfg.options["showMenu"]){
		kkCfg.fn.bindMenu(kkCfg.options.uuid,menuCfg);
		}
		kkCfg.fn.refreshImg(kkCfg.options.uuid,kkCfg.options);
	})();
</script></div>

