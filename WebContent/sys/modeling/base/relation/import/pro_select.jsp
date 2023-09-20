<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil"%>
<template:include ref="default.dialog">
    <template:replace name="head">
        <style type="text/css">
        #paramInput{
        display:none}
            #paramInput,
            #paramSelect {
                width: 320px;
            }

            #paramSelect {
                height: 400px;
                overflow-y: scroll;
                overflow-x: hidden;
            }

            #paramSelect .item {
                height: 30px;
                line-height: 30px;
                font-size: 16px;
                padding: 5px;
                margin-top: 15px;
                border: 1px solid #999;
            }

            #paramSelect .item .addBtn {
                height: 20px;
                line-height: 20px;
                cursor: pointer;
                float: right;
                color: #39c;
                border: 1px solid #39c;
                padding: 0px 10px;
                font-size: 14px;

            }

            #paramArea .item {
                display: inline-block;
                color: #39c;
                position: relative;
                height: 35px;
                line-height: 35px;
                font-size: 16px;
                padding: 5px 15px;
                border: 1px solid #39c;
            }

            #paramArea .item .delBtn {
                cursor: pointer;
                font-size: 14px;
                height: 16px;
                line-height: 16px;
                display: block;
                position: absolute;
                top: 3px;
                right: 3px;
                color: red;
            }
            #paramArea .item .addBtn,
            #paramSelect .item .delBtn{
            	display:none;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <p class="txttitle" style="margin: 10px 0;">选择参数</p>
        <table class="tb_normal" width="98%">
            <tr>
                <td width="30%" valign="top">
                    <p>参数选择：</p>
                        <div id="paramInput">  自定义：<input type="text" id="inputText" />
                            <button onclick="addInputParam()">添加</button>
                        </div>
                
                    <div id="paramSelect">
                       
                          
                        
                    </div>
                </td>
                <td valign="top">
                    <p>结果：</p>
                    <br>
                    <div id="paramArea">

                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <%--选择--%>
                    <ui:button text="${lfn:message('button.select')}" onclick="doSubmit();" /> <%--取消--%>
                    <ui:button text="${lfn:message('button.cancel') }" onclick="cancel();" />
                </td>
            </tr>
        </table>

        <script type="text/javascript">
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/util/str'],function ($, dialog, topic, str) {
                //监听数据传入
                var _param ;
                var intervalEndCount = 10;
            	var interval = setInterval(__interval,"50");
                function __interval(){
                	if(intervalEndCount==0){
                		console.error("数据解析超时。。。");
                		clearInterval(interval);
                	}
                	intervalEndCount--;
                	if(!window['$dialog']){
                		return;
                	}

                	_param = $dialog.___params;
                    console.log(JSON.stringify(_param))
                	initData();
                	clearInterval(interval);
                			
                			
                }  
                //参数计数
                var idxP = 0;
                var paramArray = [];
               function initData(){
            	   //数据展现
            	   if(_param.multi){
            		   $("#paramInput").show()
            	   }
            	  //选择框生成
            	  for(var k in _param.data){
            		var d = _param.data[k]
            		var div =   $(" <div class='item'/>");
            		div.append("<span>"+d.label+"</span>")
            		div.append("<span class=\"addBtn\" onclick=\"addParam('"+k+"')\">添加+</span>");
            		div.append("<span class=\"delBtn\" onclick=\"delParam('"+k+"')\">X</span>");
            		div.attr("lui_param_id",k);
            		$("#paramSelect").append(div)	
            	  }
            	  
            	  //历史数据
            	  if(_param.oldData){
            	      if (typeof  _param.oldData === "string"){
                          _param.oldData =  JSON.parse(_param.oldData);
                      }
            		  if(_param.multi){
            			paramArray = _param.oldData
                		  if(!paramArray){
                			  paramArray = [];
                		  }
                		  showText();
                	  }else{
                		 	$.each(_param.oldData,function(i,v){
                		 		addParam(v.name)
                		 	})
                	  }
            	  }
               }
               
            
               window.addInputParam = function () {
                   var v = $("#inputText").val();
                   if (v != null && v.length > 0) {
                       var p = {
                           id: idxP,
                           label: v,
                           name: v,
                           type: "_inputType",
                           businessType: "_inputType"
                       }
                       paramArray.push(p);
                       idxP++;
                       showText();
                   }

               }
               window.addParam = function (id) {
                   if(_param.multi){
                	   	var p = _param.data[id];
                       	p.id = idxP;
                       	paramArray.push(p);
                       	idxP++;
                   		showText();
                   }else{
                	  var d = $("[lui_param_id='"+id+"']");
                	  d.remove();
                	  $("#paramArea").append(d);
                   }
               }
               window.delParam = function (idx) {
            	   if(_param.multi){

                       var delidx = -1;
                       $.each(paramArray, function (index, value) {
                           if (value.id == idx) {
                               delidx = index;
                           }
                       });
                       if (delidx >= 0) {
                           paramArray.splice(delidx, 1);
                       }
                    	showText()
                     
                  }else{
	               	  var d = $("[lui_param_id="+idx+"]");
	               	  d.remove();
	               	  $("#paramSelect").append(d);
                  }
                  
               }
               window.doSubmit = function () {
            	   if(!_param.multi){
            		   var eles = $("#paramArea").find(".item");
            		   $.each(eles, function (idx, e) {
            			 	var id = $(e).attr("lui_param_id");
            			   var p = _param.data[id];
                           	p.id = idxP;
                           	paramArray.push(p);
                        	idxP++;
                	   });
            	   }
            	   $dialog.hide(paramArray);
               };
			   window.cancel = function () {
                 	$dialog.hide(null);
               };
               function showText() {
                   $("#paramArea").html("");
                   $.each(paramArray, function (index, value) {
                       var textItem = $("<div class='item'\>")
                       textItem.html(value.label);
                       var delbtn = $("<span class='delBtn' onclick='delParam(" + value.id + ")'/>");
                       delbtn.html("X")
                       textItem.append(delbtn)
                       $("#paramArea").append(textItem);
                   });
               }
  		});
        </script>
    </template:replace>
</template:include>