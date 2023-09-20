require(['dojo/topic','dojo/ready','dijit/registry','dojo/on','dojox/mobile/TransitionEvent',"mui/util","mui/device/adapter",
         "dojo/dom-style","dojo/dom","dojo/query","dojo/request","mui/dialog/Tip","mui/i18n/i18n!sys-lbpmperson"
         ],function(topic,ready,registry,on,TransitionEvent,util,adapter,domStyle,dom,query,request,Tip,msg){
	          
	        // 搜索框输入搜索关键字时控制“清空”图标显示或隐藏
    		window.checkKeyword = function(value){
    			var cancelDom = query('.muiNotifySearchClear',dom.byId("cardSearchView"))[0];
    			if(value){
    				domStyle.set(cancelDom, {display : 'inline-block'});
        		}else{
        			domStyle.set(cancelDom, {display : 'none'});
            	}
        	};
        	
        	//(???????? 暂未发现此函数有被任何地方调用)
     		window.onHrefBackToMainView = function(obj){
     			var backUrl = "sys/notify/mobile/index.jsp?moduleName="+util.getUrlParameter(location.href,'moduleName');
     			window.location.href = util.formatUrl(backUrl);
         	};
         	
         	// 点击“清空”图标处理函数
         	window.resetKeyword = function(){
				document.getElementsByName('keyWord')[0].value='';
				checkKeyword();
	        };
	        
	        // 点击“取消”按钮隐藏弹出的搜索栏区域，返回主视图区域
     		window.onBackToMainView = function(obj){
				var opts = {
						moveTo:'notifyMainView'
				};
				new TransitionEvent(document.body, opts).dispatch();
         	};
         	
         	// 搜索行为响应函数
     		window._onSearch = function(evt) {
     			var searchUrl = "/sys/notify/mobile/searchresult.jsp?keyword=!{keyword}&moduleName=!{moduleName}&searchType=!{searchType}";
				if (evt.keyWord.value != '' && evt.query) {
					var moduleName = util.getUrlParameter(location.href.replace(location.hash,''),'moduleName');
					var url =  util.formatUrl(util.urlResolver(searchUrl, {keyword:evt.keyWord.value,moduleName:moduleName,searchType:evt.searchType}));
					url = url+location.hash;
					var self = this;
					setTimeout(function(){
						topic.publish("/notify/search/submit",self, {url:url});
					},300);
				}
				return false;
			};
			
     		window._onSubmit = function(srcObj,ctx){
     			if(ctx && ctx.url){
     				window.location.href = ctx.url;
				}
         	};
         	
         	// 按键按下响应函数，处理回车键调用查询行为
         	window._onKeydown = function(e){
         		var keyCode = e.keyCode;
    			if(e.keyCode == 13 && e.target && e.target.value){
    				var value = e.target.value;
    				var searchType = document.getElementsByName('searchType')[0].value;
    				_onSearch({keyWord:{value:value},searchType:searchType,query:true});
    			}
         	}
         	
         	// 搜索类型变更响应函数
         	window._onSearchTypeChange = function(obj,evt){
         		if(obj.name=='searchType'){
         			var keyWord = document.getElementsByName('keyWord')[0].value;
         			if(keyWord){
         				_onSearch({keyWord:{value:keyWord},searchType:evt.value,query:true});
         			}
         		}
         	},
         	
         	// 快速审批“通过”、“驳回”按钮响应函数
         	window.mobile_fastApprove=function(processId,opType,uuid){
         		var params={"processId":processId,"opType":opType};
         		request.post(util.formatUrl("/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=approveOne"),{data:params, handleAs : 'json', sync:true}).then(function(json){
         			var doc=document.getElementById("reviewOp_"+uuid);
         			if(doc){
         				if(opType=='pass'){
         					doc.innerHTML="<span class='muiFastReviewTodoOperatedTag'>"+msg['mui.lbpmperson.fastreview.approve']+"</span>";
         				}
         				else if(opType=='refuse'){
         					doc.innerHTML="<span class='muiFastReviewTodoOperatedTag'>"+msg['mui.lbpmperson.fastreview.reject']+"</span>";
         				}
						if(opType=='assignPass'){
							doc.innerHTML="<span class='muiFastReviewTodoOperatedTag'>"+msg['mui.lbpmperson.fastreview.assignpass']+"</span>";
						}
						else if(opType=='assignRefuse'){
							doc.innerHTML="<span class='muiFastReviewTodoOperatedTag'>"+msg['mui.lbpmperson.fastreview.assignrefuse']+"</span>";
						}
         			}
    			},function(){
    				Tip.fail({text:"执行获取数据过程中出错!"});
    			});
         	};
         	
     		topic.subscribe("/notify/search/submit", _onSubmit);
     		topic.subscribe("/mui/form/valueChanged", _onSearchTypeChange);
     		on(dom.byId('keyWord'),'keydown',_onKeydown);
});