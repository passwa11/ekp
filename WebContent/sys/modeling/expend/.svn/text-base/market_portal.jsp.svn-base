<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.view" compatibleMode="true">
<template:replace name="title">
营销门户排行
</template:replace>
<template:replace name="content">
<div class="lux_lanYue_paihang">
        <div class="lux_lanYue_paihang_item">
            <div class="lux_lanYue_paihang_img paihang_a"></div>
            <div class="lux_lanYue_paihang_touxiang_a lux_lanYue_paihang_touxiang"></div>
            <div class="lux_lanYue_paihang_name" id="n1">吴晓波</div>
            <div class="lux_lanYue_paihang_num" ><span id="p1">560</span><span class="lux_lanYue_paihang_key" id="u1">万</span></div>
        </div>
        <div class="lux_lanYue_paihang_item">
            <div class="lux_lanYue_paihang_img paihang_b"></div>
            <div class="lux_lanYue_paihang_touxiang_b lux_lanYue_paihang_touxiang"></div>
            <div class="lux_lanYue_paihang_name" id="n2">张锋</div>
            <div class="lux_lanYue_paihang_num"><span id="p2">520</span><span class="lux_lanYue_paihang_key" id="u2">万</span></div>
        </div>
        <div class="lux_lanYue_paihang_item">
            <div class="lux_lanYue_paihang_img paihang_c"></div>
            <div class="lux_lanYue_paihang_touxiang_c lux_lanYue_paihang_touxiang"></div>
            <div class="lux_lanYue_paihang_name" id="n3">刘学敏</div>
            <div class="lux_lanYue_paihang_num"><span id="p3">460</span><span class="lux_lanYue_paihang_key" id="u3">万</span></div>
        </div>
    </div>
</template:replace>
</template:include>
<script>
function initData(){
	var url = Com_Parameter.ContextPath + "sys/modeling/base/mobile/modelingAppMobile.do?method=getPortalDatas&type=market";
    $.ajax({
        url: url,
        type: "post",
        async : false,
        success: function (data) {
        	if(data){
	    		var results =  eval("("+data+")");
	    		$("#n1").text(results.name);
	    		$("#p1").text(results.price);
	    		$("#u1").text("万");
        	}
        },
        error : function(){
        	
        }
    });
}

$(document).ready(function(){
	initData();
});
</script>

<style>
        .lux_lanYue_paihang_img {
            width: 42px;height: 42px;
            background-size: cover;
            background-repeat: no-repeat;
            margin-right: 20px;
        }
        .paihang_a {
            background-image: url(images/yingxiaomenhu-image/guanjun.png);
        }
        .paihang_b {
            background-image: url(images/yingxiaomenhu-image/yajun.png);
        }
        .paihang_c {
            background-image: url(images/yingxiaomenhu-image/jijun.png);
        }
        .lux_lanYue_paihang_item {
            display: -webkit-box;
            display: -ms-flexbox;
            display: flex;
            margin: 20px 20px;
            flex-wrap: nowrap;
            align-items: center;
        }
        .lux_lanYue_paihang_touxiang {
            width: 21px;height: 21px;
            margin-right: 12px;border-radius: 50%;
            background-position: center;
        }
        .lux_lanYue_paihang_touxiang_a {
            background-image: url(images/yingxiaomenhu-image/touxiang3.png);
        }
        .lux_lanYue_paihang_touxiang_b {
            background-image: url(images/yingxiaomenhu-image/touxiang2.png);
        }
        .lux_lanYue_paihang_touxiang_c {
            background-image: url(images/yingxiaomenhu-image/touxiang1.png);
        }
        .lux_lanYue_paihang_num{
            margin-left: 100%;color: #FFAD30;font-size: 22px;margin-left: auto;
        }
        .lux_lanYue_paihang_key {
            font-size: 19px;margin-left: 3px;
        }
    </style>