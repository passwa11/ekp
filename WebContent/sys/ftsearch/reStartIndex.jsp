<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><kmss:message key="sys-ftsearch-db:sysFtsearch.reIndex.title" /></title>
	<link href="${KMSS_Parameter_ContextPath}resource/style/default/doc/document.css" rel="stylesheet" type="text/css" />
	<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/resource/jsp/common.jsp"%>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<style>
		body{
			margin:0px auto;
		}
		.con_div{
			position:absolute;
			top:50px;
			left:50%;
			margin:0 0 0 -200px;
			width:600px;
			height:100px;
		}
		.hit{
			text-align: left;
			line-height: 20px;
			color: red;
			width: 400px;
			margin-left: 10px;
		}
		.center{   
		    margin: 0 auto;   
		    width: 800px;   
		    height: 300px;   
		}  
		.stopIndexTipClass{   
		    display: none; 
		    color:red;
		}  
	</style>
</head>
<body>
<div class="center" style="margin-top: 30px;">
	<span style="font-size:10pt;color:WindowText;font-family:'Courier New';">
		<span style="font-size: 12pt;font-weight:bold;color: red;"><kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title1'/></span>
	</span>
	
	<div>
		<ul>
			<li>
				<font face="Courier New" size="3">
					<span style="line-height: 24px;">
						<span style="font-size: 16px;">
							<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title1.text1'/>
						</span><br>
					</span>
				</font>
			</li>
			<li>
				<font face="Courier New" size="3">
					<span style="line-height: 24px;">
						<span style="font-size: 16px;">
							<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title1.text2'/>
							<br>
						</span>
					</span>
				</font>
			</li>
			<li>
				<font face="Courier New" size="3">
					<span style="line-height: 24px;">
						<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title1.text3'/>
					</span>
				</font>
			</li>
		</ul>
		<input class="btnopt" type=button value="<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.btn1'/>" onclick="DoUpdate(this, true);"> 
	</div>
	
	<div>
		<br><br>
	</div>
	<div>
		<span style="font-size:10pt;color:WindowText;font-family:'Courier New';">
			<span style="font-size: 12pt;">
				<span style="font-size: 12pt;font-weight:bold;color: red;"><kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title2'/></span>
			</span>
		</span> 
		<br>
	</div>
	<div>
		<ul>
			<li>
				<font face="Courier New" size="3">
					<span style="line-height: 24px;">
						<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title2.text1'/>
					</span>
				</font>
			</li>
		</ul>
		<input class="btnopt" type=button value="<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.btn2'/>" onclick="DoUpdate(this, false);">
	</div>
	
		<div>
		<br><br>
	</div>
	<div>
		<span style="font-size:10pt;color:WindowText;font-family:'Courier New';">
			<span style="font-size: 12pt;">
				<span style="font-size: 12pt;font-weight:bold;color: red;"><kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title4'/></span>
			</span>
		</span> 
		<br>
	</div>
	<div>
		<ul>
			<li>
				<font face="Courier New" size="3">
					<span style="line-height: 24px;">
						<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title4.text1'/>
					</span>
				</font>
			</li>
		</ul>
		<input class="btnopt" type=button value="<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.btn4'/>" onclick="DoUpdate(this, false,true);">
	</div>
	<div id="controlButton" style="cursor:pointer;">&gt;&gt;&gt;</div>
	
	<div style="padding-top:10px;display:none" id="modelsAreaDiv">
<table width=80% border=0>
	 <tr>			 
		<td>		
			<label>
				<input type="checkbox" name='checkAllElement' id="checkAllBox" value="notAll"  onclick="selectAll(this)">
				<span id='checkBoxAll' value='<bean:message bundle="sys-ftsearch-db" key="search.moduleSelect.selectAll"/>'  style="color: red" >
				<bean:message bundle="sys-ftsearch-db" key="search.moduleSelect.selectAll"/>
				</span>
			</label>
		</td>
	</tr>
</table>
<table class="tb_normal" style="margin-left:0px;" width=80% id="modelTable">
	<input type='hidden'  name ='ftSearchModelName'  />	 
	<input type='hidden'  name ='entriesCount'  value='' />
</table> 
	</div>
	
	
	<div>
        <br><br>
    </div>
	<div>
		<span style="font-size:10pt;color:WindowText;font-family:'Courier New';">
			<span style="font-size: 12pt;">
				<span style="font-size: 12pt;font-weight:bold;color: red;"><kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title3'/></span>
			</span>
		</span> 
		<br>
	</div>
	<div>
		<ul>
			<li>
				<font face="Courier New" size="3">
					<span style="line-height: 24px;">
						<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title3.text1'/>
					</span>
				</font>
			</li>
		</ul>
		<input id="stopIndexInput" class="btnopt" type=button value="<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.btn3'/>" onclick="DoStop(this);">
	    <span class='stopIndexTipClass' id="stopIndexTipSpan"><kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.stoping.tip'/><span>
	</div>
</div>

<script>
init();
function init(){
	seajs.use(['lui/dialog','lui/jquery'], function (dialog,$) {
	$("#checkAllBox").removeAttr("checked");
    $("#controlButton").click(function(){
		$("#modelsAreaDiv").toggle();
    });
	
	var url = '<c:url value="/sys/ftsearch/ReStartIndex.do?method=initIndexModel"/>';
	$.ajax({
        url : url,
        method : "post",
        dataType : "text",
        async: true,
        success : function(data) {
            var modelArr = data.split(",");
            var titleArr = [];
            var modelNameArr = [];
            var html = "";
            for(var i=0; i<modelArr.length; i++){
                var title = modelArr[i].split("%")[0];
                var modelName = modelArr[i].split("%")[1];
                if(i%4 == 0){
					html +="<tr>";
                }
				html += "<td width=25%><label></label><input type='checkbox' value='"+modelName+"'/>"+title+"</td>"
				if(i%4 == 3){
					html +="</tr>";
				}
            }
            $("#modelTable").html("");
            $("#modelTable").html(html);
        }
    });
	});
}

function selectAll(obj){
	seajs.use('lui/jquery', function ($) {
		var checkAll = obj.checked ? true : false;
		$("#modelTable input[type='checkbox']").each(function() {
			this.checked = checkAll;
		});
	});
	
}


function DoUpdate(btn, reCreateMapping, flag) {
	seajs.use('lui/dialog', function (dialog) {
		var url = '<c:url value="/sys/ftsearch/ReStartIndex.do?method=reBuildIndex&reCreateMapping=' + reCreateMapping + '" />';
		if(flag == true){
			var param = "";
			$("#modelTable input[type='checkbox']").each(function() {
				if(this.checked){
					param += this.value + ",";
				}
			});
			if (param==""){
				dialog.alert("<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.title4.tip'/>");
				return;
			}
			url = '<c:url value="/sys/ftsearch/ReStartIndex.do?method=reBuildIndex&reCreateMapping=' + reCreateMapping + '&param='+param+'" />';
		}
		var loading = dialog.loading("<div style='color:red;font-size:14px;'><kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.checkingIndex'/></div>");
		$.get('<c:url value="/sys/ftsearch/ReStartIndex.do?method=indexIsRuning" />', function (data) {
			loading.hide();
			if (data == false) {
				Com_OpenWindow(url, '_self');
			} else {
				dialog.confirm("<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.confirm'/>" + '【' + btn.value + '】？', function (isOk) {
					if (isOk) {
						loading = dialog.loading("<div style='color:red;font-size:14px;'><kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.stopingIndex'/></div>");
						$.get('<c:url value="/sys/ftsearch/ReStartIndex.do?method=stopIndexTask" />', function (data) {
							waitStop(function(){
								loading.hide();
								Com_OpenWindow(url, '_self');
							});

						}, "json");
					}
				});
			}
		}, "json");
	});
}

function DoStop(btn) {
	seajs.use('lui/dialog', function (dialog) {
		dialog.confirm("<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.confirm_info'/>",function(isOK){
			if (isOK){
		    var loading = dialog.loading("<div style='color:red;font-size:14px;'><kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.checkingIndex'/></div>");
		        //console.log(loading);
				var url = "${KMSS_Parameter_ContextPath}/sys/ftsearch/ReStartIndex.do?method=stopIndexTask";
	                $.ajax({
	                    url : url,
	                    method : "post",
	                    dataType : "json",
	                    async: true,
	                    success : function(data) {
	                        if(data){
	                            loading.hide();
	                            dialog.alert("<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.stop.success'/>");
		                    }
	                        else{
	                            loading.hide();
	                            dialog.alert("<kmss:message key='sys-ftsearch-db:sysFtsearch.reIndex.no.index.task'/>");
		                    }
	                    }
	                });
			}
		});
        
	});
}

// 等待停止
function waitStop(callback){
	$.get('<c:url value="/sys/ftsearch/ReStartIndex.do?method=indexIsRuning" />', function (data) {
		if (data == false) {
			callback();
		} else {
			waitStop(callback);
		}
	}, "json");
}
</script>
</body>	
</html>


