<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.simple" tiny="true" isNative="true">
	<template:replace name="title">移动端弹出框控件demo</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-view.js" cacheType="md5" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/view-tiny.css">
		<style>
			h2.categoryTitle {
				position: relative;
				margin-top: 1.6rem;
				color: #2a304a;
				background-color: #F0F8FF;
				padding-top: .6rem;
				padding-bottom: .6rem;
				padding-left: 1.2rem;
				letter-spacing: .4rem;
			}
			
			h3.categoryTitle {
				position: relative;
				margin-top: 1.6rem;
				color: #2a304a;
				background-color: #FFF5EE;
				padding-top: .6rem;
				padding-bottom: .6rem;
				padding-left: 1.2rem;
				letter-spacing: .4rem;
			}
			
			h2.categoryTitle>i.fontmuis, h3.categoryTitle>i.fontmuis {
				position: absolute;
				right: 1.2rem;
				top: 1rem;
				font-size: 1rem;
			}
			
			.muiFormContent {
				padding: 0 1.2rem;
			}
			input[type='text'].toastText{
			    height: 2.4rem;
			    width: 100%;
			    text-indent: .6rem;
			    color: #555555;
			    border: 1px solid #cccccc;
			    border-radius: .4rem;
			    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
			    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
			    -webkit-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
			    transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;		
			} 
			input[type='text'].toastText:focus {
				border-color: #66afe9;
				outline: 0;
				-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, 0.6);
				box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, 0.6);
			}
			td input[type='text'].toastText:not(:first-child){
			    margin-top: .6rem;
			}
			input[type='button'] {
				border: 0;
				padding: .4rem 1rem;
				border-radius: 1.7rem;
				color: var(- -muiFontColor);
				background-color: rgba(153, 159, 183, 0.08);
			}
			td input[type='button'].barTip{
			    margin-left: 1rem;
			}
			
		</style>
		<script>
		window.onload = function() {
	        require([
	          "dojo/query",
	          "dojo/on",
	          "dojo/dom-attr",
	          "dojo/dom",
	          "dojo/dom-class",
	          "dojo/topic",
	          "dijit/registry",
	          "mui/dialog/Tip",
	          "mui/dialog/BarTip",
	          "mui/dialog/Confirm",
	          "mui/dialog/Alert",
	          "mui/dialog/Modal"
	        ], function(query, on, domAttr, dom, domClass, topic, registry, tip, BarTip, Confirm, Alert, Modal) {
	        	
	          // 绑定提示框点击事件
	          on(query("input[type='button'].dialogs"), "click", function(evt) {
	            var target = evt.target;
	            var type = domAttr.get(target, "dialogType");
	            var tr = target.parentNode.parentNode;
	            var text = query(".text", tr)[0].value;
	            var option;
	            if (text) {
	              option = {};
	              option.text = text;
	            }
	            var iconNode = query(".icon", tr);
	            if (iconNode.length > 0) {
	            	  var icon = iconNode[0].value;
	              if (!option) {
	                option = {};
	              }
	              option.icon = icon;
	            }
	            if (type) {
	              if (option) {
	            	var wdt = tip[type](option);
	            	if(type=="progressing"){
		            	setTimeout(function(){
		            		wdt.hide();
		            	},3000);
	            	}
	              } else {
	                var wdt = tip[type]();
	              }
	            }
	          });
	          
			  // 绑定通告栏点击事件
			  on(query("input[type='button'].barTip"),"click",function(){
				 var barTips = query(".muiDialogBarTip");
				 for(var i=0;i<barTips.length;i++){
					 barTips[i].style.display='none';
				 }
				 var tipStr = "N8.审批节点: 王安石";
				 if(this.name=='twoRowTip'){
					 var tipStr = "N8.审批节点: 王安石<div class='tipRowSplitLine'></div>N4.审批节点: 张三";
				 }
				 BarTip.tip({text: tipStr});
			  });
			  
			  // 绑定Alert点击事件
			  on(query("input[type='button'].alert"),"click",function(){
				    var contentHtml = "";
				    var icon = "";
				    if(this.name=='alert1'){
				    	contentHtml = "流程申请已保存成功";
				    	icon = "success";
				    }else if(this.name=='alert2'){
				    	contentHtml = "非常抱歉，流程申请保存失败";
				    	icon = "fail";
				    }else if(this.name=='alert3'){
				    	contentHtml = "录入的数据不符合格式要求，请检查";
				    	icon = "warn";
				    }
				    var title = "";
				    var callback = function(dialog){
				    	console.log("点击了Alert[确定]按钮");
				    }  
				    Alert(contentHtml,title,callback,icon);
			  });
				 
			  // 绑定Confirm点击事件
			  on(query("input[type='button'].confirm"),"click",function(){
				    var contentHtml = "";
				    if(this.name=='confirm1'){
				    	contentHtml = "蓝凌的小伙伴，您是否要继续操作呢？";
				    }else if(this.name=='confirm2'){
				    	contentHtml = "蓝凌的小伙伴，你好呀！这个弹出框的内容文字是很长很长非常长的，目的是为了测试当文本超长的极端场景时，弹出框的内容是否能够正常显示，";
				    }
				    var title = "提示信息";
				    var callback = function(bool,dialog){
				    	if(bool){
				    		console.log("点击了Confirm[确定]按钮");
				    	}else{
				    		console.log("点击了Confirm[取消]按钮");
				    	}
				    }  
					Confirm(contentHtml,title,callback);
			  });
			  
			  // 绑定Modal点击事件
			  on(query("input[type='button'].modal"),"click",function(){
				    if(this.name=='modal1'){
				    	
				    	var contentHtml = '<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="myModalFormView1">'+
				    	'<form id="myModalForm" method="post">'+
				    	'<table class="muiSimple" cellpadding="0" cellspacing="0">'+
			            '<tr>'+
		              	'<td>'+
				    	'<div data-dojo-type="mui/form/Input" data-dojo-props="value:\'\',name:\'modal_input_username\',showStatus:\'edit\',subject:\'员工姓名\',validate:\'required\',required:true,orient:\'vertical\'"></div>'+
			            '</td>'+
		              	'</tr>'+
			            '<tr>'+
		              	'<td>'+
				    	'<div data-dojo-type="mui/form/Input" data-dojo-props="value:\'\',name:\'modal_input_nativeplace\',showStatus:\'edit\',subject:\'员工籍贯\',validate:\'required\',required:true,orient:\'vertical\'"></div>'+
			            '</td>'+
		              	'</tr>'+		              	
			            '<tr>'+
		              	'<td>'+
				    	'<div data-dojo-type="mui/form/Switch" data-dojo-props="property:\'modal_switch_inservice\',showStatus:\'edit\',orient:\'vertical\',subject:\'是否在职\',leftLabel:\'是\',rightLabel:\'否\',value:\'on\'"></div>'+
			            '</td>'+
		              	'</tr>'+
				    	'</table>'+
				    	'</form>'+
				    	'</div>';
					    var title = "员工基本信息";
					    var modalButtons = [ 
					        {
								title : "取消",
								fn : function(dialog) {
									console.log("点击了Modal[取消]按钮");
									var formView =  registry.byId("myModalFormView1");
									/*
									    注：如果使用Modal组件时传递的是HTML字符串，会被调用dojo/parser进行dojo对象解析，多次传入带有相同id的HTML会解析报错，因此关闭弹窗时需要手动调用destroy销毁
									  如果需要每次打开Modal窗口时保持上一次录入的表单数据内容，可以先自行将HTML字符串解析转换为DOM对象后，每次传递相同的DOM对象给Modal（详可参照下面modal2的示例）
									*/
									formView.destroy();  
									dialog.hide(); // 关闭弹窗
								}
					        }, 				                        
					        {
								title : "保存",
								fn : function(dialog) {
									console.log("点击了Modal[保存]按钮");
									var formView =  registry.byId("myModalFormView1");
									if(!formView.validate()) {
										console.log("表单校验未通过");
										return;
									}											
									console.log("获取到的表单数据:");
									console.log(dojo.formToObject("myModalForm")); // dojo.formToObject方法支持从表单获取数据，参数为表单id
									/*
									    注：如果使用Modal组件时传递的是HTML字符串，会被调用dojo/parser进行dojo对象解析，多次传入带有相同id的HTML会解析报错，因此关闭弹窗时需要手动调用destroy销毁
									  如果需要每次打开Modal窗口时保持上一次录入的表单数据内容，可以先自行将HTML字符串解析转换为DOM对象后，每次传递相同的DOM对象给Modal（详可参照下面modal2的示例）
									*/									
									formView.destroy();
									dialog.hide(); // 关闭弹窗
								}
					        } 
					    ];  
					    Modal(contentHtml,title,modalButtons);	
					    
				    }else if(this.name=='modal2'){
				    	
				    	var contentDom = dom.byId("myModalFormView2");
					    var title = "员工入职信息";
					    var modalButtons = [ 
					        {
								title : "取消",
								fn : function(dialog) {
									console.log("点击了Modal[取消]按钮");
									dialog.hide(); // 关闭弹窗
								}
					        }, 				                        
					        {
								title : "保存",
								fn : function(dialog) {
									console.log("点击了Modal[保存]按钮");
									var formView =  registry.byId("myModalFormView2");
									if(!formView.validate()) {
										console.log("表单校验未通过");
										return;
									}
									console.log("获取到的表单数据:");
									console.log(dojo.formToObject("myModalForm2")); // dojo.formToObject方法支持从表单获取数据，参数为表单id
									dialog.hide(); // 关闭弹窗
								}
					        } 
					    ]; 
					    Modal(contentDom,title,modalButtons);
					    
				    }
			  });
			  
			  
			  
	        });
	      };
		</script>		
	</template:replace>

	<template:replace name="content">
		<div data-dojo-type="mui/view/DocScrollableView"
			data-dojo-mixins="mui/form/_ValidateMixin">
			<div class="muiFlowInfoW muiFormContent">
				<h2 class="categoryTitle">toast组件</h2>
				 <div>
	           		<table class="muiSimple" cellpadding="0" cellspacing="0">           
		              <tr>
		              	<td>
							<input class="dialogs" type="button" value="成功提示！点我"  dialogType="success">
		              	</td>
		              	<td>
		              		<input type="text" class="toastText text" value="操作成功" placeholder="请输入自定义文字">
		              	</td>
		              </tr>
		              <tr>
		              	<td>
							<input class="dialogs" type="button" value="失败提示！点我"  dialogType="fail">
		              	</td>
		              	<td>
		              		<input type="text" class="toastText text" value="操作失败" placeholder="请输入自定义文字">
		              	</td>
		              </tr>
		              <tr>
		              	<td>
							<input class="dialogs" type="button" value="警告提示！点我"  dialogType="warn">
		              	</td>
		              	<td>
		              		<input type="text" class="toastText text" value="警告提示" placeholder="请输入自定义文字">
		              	</td>
		              </tr>
		              <tr>
		              	<td>
							<input class="dialogs" type="button" value="加载中！点我"  dialogType="progressing">
		              	</td>
		              	<td>
							<input type="text" class="toastText text" value="加载中" placeholder="请输入自定义文字">		              	
		              	</td>
		              </tr>
		               <tr>
		              	<td>
		              		<input class="dialogs" type="button" value="自定义提示框！点我" dialogType="tip">
		              	</td>
		              	<td>
		              		<input type="text" class="toastText text" value="自定义文字" placeholder="请输入自定义文字">
		              		<input type="text" class="toastText icon" value="自定义图标" placeholder="请输入图标类名">
		              	</td>
		              </tr>
		             </table>
				</div>
				
				<h2 class="categoryTitle">弹出框组件</h2>
				<div>
	           		<table class="muiSimple" cellpadding="0" cellspacing="0">    
		              <tr>
		              	<td>
							Alert（操作成功）
		              	</td>
		              	<td>
		              		<input class="alert" type="button" value="点击弹出Alert对话框" name="alert1">
		              	</td>
		              </tr>
		              <tr>
		              	<td>
							Alert（操作失败）
		              	</td>
		              	<td>
		              		<input class="alert" type="button" value="点击弹出Alert对话框" name="alert2">
		              	</td>
		              </tr>
		              <tr>
		              	<td>
							Alert（操作提醒）
		              	</td>
		              	<td>
		              		<input class="alert" type="button" value="点击弹出Alert对话框" name="alert3">
		              	</td>
		              </tr>		              		              
		              <tr>
		              	<td>
							Confirm（一行文字）
		              	</td>
		              	<td>
		              		<input class="confirm" type="button" value="点击弹出Confirm对话框" name="confirm1" >
		              	</td>
		              </tr>
		              <tr>
		              	<td>
							Confirm（多行文字）
		              	</td>
		              	<td>
		              		<input class="confirm" type="button" value="点击弹出Confirm对话框" name="confirm2" >
		              	</td>
		              </tr>
		              <tr>
		              	<td>
							Confirm（仅显示标题）
		              	</td>
		              	<td>
		              		<input class="confirm" type="button" value="点击弹出Confirm对话框" name="confirm3" >
		              	</td>
		              </tr>	
		              <tr>
		              	<td>
							Modal（拼表单HTML字符串）
		              	</td>
		              	<td>
		              		<input class="modal" type="button" value="点击弹出Modal模态框" name="modal1" >
		              	</td>
		              </tr>
		              <tr>
		              	<td>
							Modal（直接取DOM表单对象）
		              	</td>
		              	<td>
		              		<input class="modal" type="button" value="点击弹出Modal模态框" name="modal2" >
		              		<!-- 隐藏的表单，提供给Modal模态框获取 -->
		              		<div style="display:none;">
		              		    <div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="myModalFormView2">
			              		    <xform:config orient="vertical" >
				              		<form id="myModalForm2" >
				              		    <table class="muiSimple" cellpadding="0" cellspacing="0">
				              		      <tr>
				              		         <td>
				              		            <xform:text property="modal_input_username" mobile="true" subject="员工姓名" title="员工姓名" showStatus="edit" required="true"></xform:text>
				              		         </td>
				              		      </tr>
				              		      <tr>
				              		         <td>
											   <xform:radio property="modal_radio_sex" mobile="true" subject="员工性别" title="员工性别" mobileRenderType="normal" value="1" showStatus="edit" required="true">
													<xform:simpleDataSource value="1">男</xform:simpleDataSource>
													<xform:simpleDataSource value="2">女</xform:simpleDataSource>
												</xform:radio>
				              		         </td>
				              		      </tr>				              		      
				              		      <tr>
				              		         <td>
				              		            <xform:datetime property="modal_datetime" mobile="true" subject="入职日期" title="入职日期" showStatus="edit" required="true"></xform:datetime>
				              		         </td>
				              		      </tr>
				              		      <tr>
				              		         <td>
							                    <xform:select property="modal_select" mobile="true" subject="开发技能" title="开发技能"  showStatus="edit" showPleaseSelect="true" >
													<xform:simpleDataSource value="1">JAVA</xform:simpleDataSource>
													<xform:simpleDataSource value="2">.NET</xform:simpleDataSource>
													<xform:simpleDataSource value="3">C++</xform:simpleDataSource>
													<xform:simpleDataSource value="4">Python</xform:simpleDataSource>              
							                    </xform:select>		              		         
				              		         </td>
				              		      </tr>
				              		      <tr>
				              		         <td>
												<xform:checkbox property="modal_checkbox" mobile="true" subject="家庭成员" title="家庭成员" showStatus="edit"  mobileRenderType="normal" >
													<xform:simpleDataSource value="1">爸爸</xform:simpleDataSource>
													<xform:simpleDataSource value="2">妈妈</xform:simpleDataSource>
													<xform:simpleDataSource value="3">配偶</xform:simpleDataSource>
													<xform:simpleDataSource value="4">儿子</xform:simpleDataSource>
													<xform:simpleDataSource value="5">女儿</xform:simpleDataSource>
												</xform:checkbox>	              		         
				              		         </td>
				              		      </tr>		              		      		              		      
				              		    </table>
				              		</form>
				              		</xform:config>
			              		</div>
		              		</div>

		              	</td>
		              </tr>		              		              	              		              		              	           		
	           		</table>
	           	 </div>
	           	  
	           	 <h2 class="categoryTitle">提示组件</h2>
				 <div>
	           		<table class="muiSimple" cellpadding="0" cellspacing="0">
           		      <tr>
		              	<td colspan="2">
		              		 通告栏：
							<input class="barTip" type="button" value="显示一行文字"  name="oneRowTip">
							<input class="barTip" type="button" value="显示两行文字"  name="twoRowTip">
		              	</td>
		              </tr>    
	           		</table>
	           	  </div>
			</div>
		</div>

	</template:replace>
</template:include>
