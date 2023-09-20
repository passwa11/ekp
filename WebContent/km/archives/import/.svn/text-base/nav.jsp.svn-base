<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="key" value="${param.key}"/>
<c:set var="criteria" value="${param.criteria}"/>
		<!-- 档案信息管理 -->
        <ui:content title="${ lfn:message('km-archives:py.DangAnXinXiGuan') }">
            <ul class='lui_list_nav_list'>
                <li><a id="archives_all" href="javascript:void(0)" onclick="setUrl('archives','mydoc','all');resetMenuNavStyle(this);">${ lfn:message('km-archives:py.SuoYouDangAn') }</a>
                </li>
                <li><a id="archives_create" href="javascript:void(0)" onclick="setUrl('archives','mydoc','create');resetMenuNavStyle(this);">${lfn:message('km-archives:py.WoTiJiaoDe')}</a>
                </li>
                <li><a id="archives_approval" href="javascript:void(0)" onclick="setUrl('archives','mydoc','approval');resetMenuNavStyle(this);">${lfn:message('km-archives:py.DaiWoShenDe')}</a>
                </li>
                <li><a id="archives_approved" href="javascript:void(0)" onclick="setUrl('archives','mydoc','approved');resetMenuNavStyle(this);">${lfn:message('km-archives:py.WoYiShenDe')}</a>
                </li>
                <li><a id="details" href="javascript:void(0)" onclick="openUrl('km_archives_details','','details');">${lfn:message('km-archives:py.WoJieYueDe')}</a>
                </li>
            </ul>
        </ui:content>
        <!-- 档案借阅管理 -->
        <ui:content title="${ lfn:message('km-archives:py.DangAnJieYueGuan') }">
            <ul class='lui_list_nav_list'>
                <li><a id="borrow_all" href="javascript:void(0)" onclick="setUrl('borrow','mydoc','');resetMenuNavStyle(this);">${lfn:message('km-archives:py.SuoYouJieYue')}</a>
                </li>
                <li><a id="borrow_create" href="javascript:void(0)" onclick="setUrl('borrow','mydoc','create');resetMenuNavStyle(this);">${lfn:message('km-archives:py.WoDeJieYue')}</a>
                </li>
                <li><a id="borrow_approval" href="javascript:void(0)" onclick="setUrl('borrow','mydoc','approval');resetMenuNavStyle(this);">${lfn:message('km-archives:py.DaiWoShenDe')}</a>
                </li>
                <li><a id="borrow_approved" href="javascript:void(0)" onclick="setUrl('borrow','mydoc','approved');resetMenuNavStyle(this);">${lfn:message('km-archives:py.WoYiShenDe')}</a>
                </li>
            </ul>
        </ui:content>
        <!-- 档案鉴定/销毁 -->
        <ui:content title="${ lfn:message('km-archives:py.DangAnJianDingXiao') }">
            <ul class='lui_list_nav_list'>
                <li><a id="queryDate_soon" href="javascript:void(0)" onclick="setUrl('queryDate','dateType','soon');resetMenuNavStyle(this);">${lfn:message('km-archives:py.JiJiangDaoQi')}</a>
                </li>
                <li><a id="queryDate_already" href="javascript:void(0)" onclick="setUrl('queryDate','dateType','already');resetMenuNavStyle(this);">${lfn:message('km-archives:py.DaoQiDangAn')}</a>
                </li>
                <li><a id="appraise" href="javascript:void(0)" onclick="openUrl('km_archives_appraise','','appraise');">${lfn:message('km-archives:py.DangAnJianDing')}</a>
                </li>
                <li><a id="destroy" href="javascript:void(0)" onclick="openUrl('km_archives_destroy','','destroy');">${lfn:message('km-archives:py.DangAnXiaoHui')}</a>
                </li>
            </ul>
        </ui:content>
        <!-- 其他操作 -->
        <ui:content title="${ lfn:message('km-archives:py.QiTaCaoZuo') }">
            <ul class='lui_list_nav_list'>
                <li><a href="${LUI_ContextPath}/sys/profile/index.jsp#app/ekp/km/archives" target="_blank">${ lfn:message('list.manager') }</a>
                </li>
            </ul>
        </ui:content>
	<script type="text/javascript">
	seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog'], function($, strutil, dialog){
		window.setUrl= function (key,mykey,type){
			 if(key!="${key}"){
		    	 if(key == 'archives'){
			        if(type ==''){
				         openUrl('','',key);
					    }else{
			    		 openUrl('','cri.q='+mykey+':'+type,key);
				    }
			     }
				 if(key == 'borrow'){
					 if(type ==''){
				         openUrl('km_archives_borrow','',key);
					    }else{
			    		 openUrl('km_archives_borrow','cri.q='+mykey+':'+type,key);
					 }
				 }
				 if(key == 'queryDate'){
					 if(type ==''){
				         openUrl('km_archives_main','',key);
					    }else{
			    		 openUrl('km_archives_main','cri.q='+mykey+':'+type,key);
					 }
				 }
			 }else{
				openQuery();
				if(type==''){
					LUI('${criteria}').clearValue();
				}else{
				 	LUI('${criteria}').setValue(mykey, type);
				}
			 }
			};
			window.openUrl = function(prefix,hash,key){
			    var srcUrl = "${LUI_ContextPath}/km/archives/";
			    if(key=='archives'){
			    	 srcUrl = srcUrl+"index.jsp";
			    }else{
				   srcUrl = srcUrl+ prefix+"/index.jsp";
			    }
				if(hash!=""){
					srcUrl+="#"+hash;
			    }
				window.open(srcUrl,"_self"); 
			};
			
			LUI.ready(function(){
		 		  // 初始化左则菜单样式
	      	  setTimeout("initMenuNav('${JsParam.key}')", 300);
			});
			// 左则样式
			window.initMenuNav = function(fdKey) {
				var mydoc = "";
				if(fdKey == "archives"){
					mydoc = getValueByHash("mydoc");
					if(mydoc == ''){
						mydoc = "all";
					}
				}
				if(fdKey == "borrow"){
					mydoc = getValueByHash("mydoc");
					if(mydoc == ''){
						mydoc = "all";
					}
				}
				if(fdKey == "queryDate"){
					mydoc = getValueByHash("dateType");
					if(mydoc == ''){
						mydoc = "soon";
					}
				}
				if(mydoc != "") {
					resetMenuNavStyle($("#"+fdKey+"_" + mydoc));
				}else {
					resetMenuNavStyle($("#"+fdKey));
				}
			 }
			
	});
</script>
