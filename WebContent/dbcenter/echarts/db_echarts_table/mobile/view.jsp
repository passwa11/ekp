<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath }/sys/mobile/css/themes/default/view.css?s_cache=${MUI_Cache}"/>
		<style>
			.close-detail {
				display:block;
				width:20px;
				height:20px;
				line-height:20px;
				text-align:center;
				border-radius:50%;
				background:rgba(0,0,0,0.3);
				color:#fff;
				font-size:22px;
				margin:3px auto;
				top:0;
				right:5px;
				position:fixed;
				z-index:999;
			}
		</style>
	</template:replace>
	<template:replace name="title">
		
	</template:replace>
	<template:replace name="content">
		<script>
		require(["dojo/ready","dojo/request","dojo/window","dojo/dom-style","dojo/dom",'dojo/dom-construct',"dojo/query","dojo/parser"],function(ready,request,win,domStyle,dom,domConstruct,$,parser) {
			var parseUrlParam = function(url) {
				var hash = location.hash;
				if(hash && hash.substring(0,7)!='#cri.q='){
					return '';
				}
				hash = hash.substring(7);
				var arrs = hash.split(';'),q = '';
				for(var i=0; i<arrs.length; i++){
					var arr = arrs[i].split(':');
					q += '&q.'+arr[0]+"="+arr[1];
				}
				return q;
			};
			ready(function() {
				var url = '${LUI_ContextPath }/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=data&rowsize=1000&fdId=${param.fdId}'+parseUrlParam(window.location.href);
				request.get(url).response.then(function(datas) {
					var url = "${info.config.listview.url}";
					console.log(url);
					var data = JSON.parse(datas.data);
					var rows = data.datas;
					var contentTB = document.getElementById('contentTB');
					
					var col = contentTB.getElementsByTagName('input');
					var hasdidden = false;
					var heddenArray;
					if(col.length>0){
						hasdidden=true;
						heddenArray= new Array(col.length);
					}
					for (var k = 0; k < col.length; k++) {
						heddenArray[k]=col[k].value;
					}
					if(hasdidden){
						//有隐藏列
						for (var i = 0; i < rows.length; i++) {
						var newRow = contentTB.insertRow();
						if(url != ""){
							newRow.setAttribute("url",url);
							newRow.setAttribute("onclick","toUrl(this)");
						}
					
						for (var j = 0; j < rows[i].length; j++) {
							var column = rows[i][j];
							if(heddenArray.includes(column.col)){
								//var hiddenText = "<div style='display:none;'>"+column.value+"</div>";
								var h = document.createElement("div");
								h.innerHTML = column.value;
								h.setAttribute("name",column.col);
								h.style.display = "none";
								newRow.append(h);
								//newRow.innerHTML = "<div style='display:none;'>"+column.value+'</div>';
								continue;
							}
							var newCell = newRow.insertCell(-1);
							if(column.col == 'index') {
							//	newCell.className = 'detailTableIndex';
								newCell.innerHTML = '<span>'+column.value+'</span>';
							}else {
         						newCell.innerHTML = "<div name='"+column.col+"'>"+column.value+'</div>';
							}
						}
					   }
						
					}else{
						//无隐藏列
						for (var i = 0; i < rows.length; i++) {
							var newRow = contentTB.insertRow();
							if(url != ""){
								newRow.setAttribute("url",url);
								newRow.setAttribute("onclick","toUrl(this)");
							}
							for (var j = 0; j < rows[i].length; j++) {
								var column = rows[i][j];
								var newCell = newRow.insertCell(-1);
								if(column.col == 'index') {
								//	newCell.className = 'detailTableIndex';
									newCell.innerHTML = '<span>'+column.value+'</span>';
								}else {															
									newCell.innerHTML = "<div name='"+column.col+"'>"+column.value+'</div>';
								}
							}
						}
					}					
					var h = domStyle.get(dom.byId('contentTB'),'height');
					document.getElementById('mui_table_ScrollableHContainer_0').style["height"] = (h+100)+"px";
					if(window.parent && window.parent.frameElement!=null && window.parent.frameElement.tagName=="IFRAME"){
						var ph = window.parent.frameElement.style.height;
						if(ph != null && ph != '') {
							ph = parseInt(ph.substring(0,ph.length-2)); //去掉"px"
							window.parent.frameElement.style.height = (ph+225)+'px';
						}
					}
				});
			});
			window.toUrl=function(tar){
				var href = tar.getAttribute("url");
				var url="";
				var re = /!\{([^\}]+)\}/g;
				var index = 0;
				for(var arr = re.exec(href); arr!=null; arr=re.exec(href)){
					url += href.substring(index,arr.index);
					console.log("url:"+url);
					
					var target = arr[1];
					var target = tar.querySelector("div[name='"+arr[1]+"']");
					//console.log($(tar).find("div"));
					console.log(target);
					var value = target.innerHTML;
					
					url += value;
					index = arr.index + arr[0].length;
				}
				if(url==""){
					url = href;
				}
				console.log(url);
				window.open(url);
			}
			
		});
		function innerCloseDetail() {
			if(window.parent && window.parent.echartschart) {
				window.parent.echartschart.innerCloseDetail('${param.fid}');
			}
		}
		
		
		</script>
		<c:if test="${'1' eq param.showDetail }">
			<div align="center">
				<h5 style="margin-top:5px;">${param.title }</h5>
				<a class="close-detail" href="javascript:;" onclick="innerCloseDetail();">&times;</a>
			</div>
		</c:if>
		 
		<div id="scrollView" 
			data-dojo-type="mui/view/DocScrollableView">
			<div data-dojo-type="mui/table/ScrollableHContainer">
				<div data-dojo-type="mui/table/ScrollableHView">
				   
					<table cellspacing="0" cellpadding="0" class="detailTableNormal">
						<tr>
							<td class="detailTableNormalTd">
								<table id="contentTB" class="muiAgendaNormal muiNormal" width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td><bean:message key="page.serial"/></td>
							  			<c:forEach items="${info.config.columns }" var="column">
							  			  
							  			  <c:if test="${empty column.template}">
							  			  
							  			    <c:if test="${column.hidden!='true'}">
							                   <td>${column.name }</td>
					                    	</c:if>
							  				<c:if test="${column.hidden =='true'}">
							  				    <!-- 保存隐藏列字段  数据显示用用到  -->
							                   <input type="hidden" attr="${column.key }" value="${column.key }"/>						                   
					                    	</c:if>
					                    	
							  			  </c:if>
							  			  <c:if test="${not empty column.template}">
					                    	  
										  </c:if>
							  			</c:forEach>
							       	</tr>
								</table>
							</td>
						</tr>
					</table>
					
				</div>
			</div>
		</div>
		<div data-dojo-type="mui/top/Top" 
           data-dojo-mixins="mui/top/_TopViewMixin"></div>
	</template:replace>
</template:include>
