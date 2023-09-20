<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
    <link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}third/im/kk/resource/css/main.css"/>
    <script type="text/javascript" src="${KMSS_Parameter_ContextPath}resource/js/jquery.js"></script>
    <script type="text/javascript" src="${KMSS_Parameter_ContextPath}resource/js/domain.js"></script>
<body id="body">
	
	<section class="right-modeleMain" v-show="main.basePage">
        <section class="content">
            <header class="integra-head">
                <h3>${lfn:message('third-im-kk:kk.config.connect.title')}</h3>
            </header>
            <section class="integra-con" v-show="base.page1" style="width: 1000px;">
                <ul>
                    <li class="bdbuttom pdleft">
                        <label>
                            <div class="integra-list integra-listbox">
                                <div>
	                                <span class="corp-xitong">${lfn:message('third-im-kk:kk.config.connect.console')}:</span><span class="miyao" style="width: 75%;">
	                                <input type="text" id="conAddress" value="${conAddress }" style="border:solid 1px; width: 400px"/>(例如:http://{domain}:port/kk/kk/console/)
	                                </span>
                                </div>
                                
                            </div>
                        </label>
                    </li>
                     <li class="bdbuttom pdleft">
                        <label>
                       		 <div class="integra-list integra-listbox">
	                              <div>
	                                <span class="corp-xitong">选择加密方式:</span><span class="miyao" style="width: 75%;">
										<input type="radio" name="encryptType" value="1" checked="checked">旧的加密方式(KK 603以下版本使用)<br/>
										<input type="radio" name="encryptType" value="2">新的加密方式(KK 603及603以上版本使用)
	                                </span>
	                               </div>
	                          </div>
                        </label>
                    </li>
                </ul>
                <button class="integra-btn active" id="nextBtn" onclick="submit();">${lfn:message('third-im-kk:kk.config.connect.next')}</button>
            </section>
        </section>
        <input type="hidden" id="secret" value="${secret }" />
</body>

<script type="text/javascript">

	//打开kk控制台交换密钥
	function exchangeSecret(){
		var conAddress = $('#conAddress').val();
		var secret = $('#secret').val();
		if(null == conAddress || "" == conAddress){
			alert('请输入kk控制台地址!');
        	return;
		}
		//var re=new RegExp("http([\\d\\D])*");  
		var reg = /^(https|http):\/\/((([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)((\d|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))|[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?):(\d|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])\/kk\/kk\/console\/$/;
		var re=new RegExp(reg);  
		if(!re.test(conAddress)){
			alert("请输入正确的地址!")
			return;
		}
		window.open(conAddress + "ekp_exchange.jsp?secret=" + secret);
	}
	
	domain.register("connection_result",function(rtnData){
		var data = rtnData.data;
		//业务逻辑
		if('1' == data){
			var conAddress = $('#conAddress').val();
			//修改控制台地址
			$.ajax({
		        type: "post",
		        url: "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=modifyConAddress",
		        data: {"conAddress":conAddress},
		        async : false,
		        dataType: "json",
		        success: function (data ,textStatus, jqXHR)
		        {
		            if('0' == data.result ){
		            	return true;
		            }else{
		             	alert(data.error_msg);
		                return false;
		            }
		        }
	    	 });
			$('#nextBtn').attr("disabled",false);
			$('#connectionMsg').html('<bean:message bundle="third-im-kk" key="kk.config.connect.succes"/>');
		}else{
			$('#connectionMsg').html('<bean:message bundle="third-im-kk" key="kk.config.connect.failure"/>');
		}
	});
	

	//交换密钥并连接kk
	function exchange(){
		var defer = $.Deferred();
		var conAddress = $('#conAddress').val();
		var encryptType = $("input[name='encryptType']:checked").val();
		if(null == conAddress || "" == conAddress){
			alert('请输入kk控制台地址!');
        	return;
		}
		$.ajax({
	        type: "post",
	        url: "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=exchange",
	        data: {"conAddress":conAddress, "encryptType":encryptType},
	        //async : false,
	        dataType: "json",
	        success: function (data ,textStatus, jqXHR)
	        {
	            if('0' == data.result ){
	            	defer.resolve(true)
	            	return true;
	            }else{
	             	alert(data.error_msg);
	             	defer.resolve(false)
	             	//跳转连接配置页面
			        window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=connectPage";
	                return false;
	            }
	        },
	        error:function (XMLHttpRequest, textStatus, errorThrown) {      
	            alert("请求失败！");
	            defer.resolve(false)
	            //跳转连接配置页面
			    window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=connectPage";
	            return false;
	        }
	     });
		 return defer.promise();
	}

	//下一步
	function submit(){
		$('#nextBtn').attr("disabled",true);
		 $.when(exchange()).done(function(data){
             if(data){
				//KK服务器SAY HELLO 
				 $.ajax({
			        type: "post",
			        url: "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=kkHello",
			        async : false,
			        dataType: "json",
			        success: function (data ,textStatus, jqXHR)
			        {
			  
			            if('0' == data.result ){
			            	window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=synConfigPage";
			            	return true;
			            }else{
			            	alert(data.error_msg);
			            	$('#nextBtn').attr("disabled",false);
			            	//跳转连接配置页面
			            	window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=connectPage";
			                return false;
			            }
			        },
			        error:function (XMLHttpRequest, textStatus, errorThrown) {      
			            alert("请求失败！");
			            $('#nextBtn').attr("disabled",false);
			         	//跳转连接配置页面
		            	window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=connectPage";
			            return false;
			        }
			     });
			}else{
				$('#nextBtn').attr("disabled",false);
			}
		});
		
	}
	
	
</script>