define([ 
	"dojo/_base/declare", 
	"dojo/_base/array", 
	"dojo/topic", 
	"dojo/query",
	"dojo/dom-construct", 
	"mui/form/Address",
	"mui/util",
	"dojo/dom-style",
	"dojo/on",
	"mui/i18n/i18n!sys-lbpmservice:lbpm.process.noticeHandler"
	], function(declare, array, topic, query, domConstruct, Address, util,domStyle,on,msg) {
	
	//获取处理人暂存的信息
	lbpm.globals.initNoticeHandlersDataAction=function() {
		try {
			var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=getNoticeHandlers&ajax=true';
			var kmssData = new KMSSData();
			var obj = {};
			obj["processId"] = query("[name='sysWfBusinessForm.fdProcessId']")[0].value;
			obj["taskId"] = lbpm.globals.getOperationParameterJson("id");
			kmssData.AddHashMap(obj);
			kmssData.SendToUrl(url, function(http_request) {
				var handlers = http_request.responseText;
				if(handlers){
					handlers = handlers.split("|");
					var noticeHandlers = [];
					for(var i=0; i<handlers.length; i++){
						var noticeHandler = {};
						var handler = handlers[i];
						if(handler){
							var elems = handler.split(";");
							if(elems.length >= 4){
								noticeHandler.id = elems[0];
								noticeHandler.name = elems[1];
								noticeHandler.startPos = parseInt(elems[2]);
								noticeHandler.endPos = parseInt(elems[3]);
								noticeHandlers.push(noticeHandler);
							}
						}
					}
					lbpm.globals.updatenoticeHandlerNum(noticeHandlers);
				}
			});
		} catch(e) {
			throw e;
		}
	}
	
	lbpm.globals.updatenoticeHandlerNum = function(handlers){
		//更新选择的人数
		if(handlers && Object.prototype.toString.call(handlers) === '[object Array]'){
			if(lbpm.noticeHandlers){
				var noticeHandlers = lbpm.noticeHandlers;
				for(var i=0; i<handlers.length; i++){
					var newHandler = handlers[i];
					var isNew = true;
					for(var j=0; j<lbpm.noticeHandlers.length; j++){
						var handler = lbpm.noticeHandlers[j];
						if(handler.id === newHandler.id){
							isNew = false;
							break;
						}
					}
					if(isNew){
						lbpm.noticeHanlerNum += 1;
					}
					noticeHandlers.push(newHandler);
				}
				lbpm.noticeHandlers = noticeHandlers;
			}else{
				//第一次选择
				lbpm.noticeHandlers = [];
				lbpm.noticeHanlerNum = 0;
				var ids = "";
				for(var i=0; i<handlers.length; i++){
					lbpm.noticeHandlers.push(handlers[i]);
					if(ids.indexOf(handlers[i].id) == -1){
						lbpm.noticeHanlerNum += 1;
						ids+=handlers[i].id+";";
					}
				}
			}
			//选择的人数
			query('#noticeHandlerNum').text(lbpm.noticeHanlerNum);
		}
	}

	//提交前匹配和去重处理
	lbpm.globals.filterNoticeHandler = function(){
		if(lbpm.noticeHandlers){
			var fdUsageContent = lbpm.operations.getfdUsageContent();
			var content = fdUsageContent.value;
			//var noticeHandlerIds = [];
			var noticeHandlerIdStr = "";
			for(var i=0; i<lbpm.noticeHandlers.length; i++){
				var handler = lbpm.noticeHandlers[i];
				var startPos = handler.startPos;
				var endPos = handler.endPos;
				var text = content.substring(startPos, endPos);
				if(/\s@\S+\s/.test(text)){
					if(noticeHandlerIdStr.indexOf(handler.id) == -1){
						//noticeHandlerIds.push(handler.id);
						noticeHandlerIdStr += handler.id + ";";
					}
				}
			}
			query("input[name='noticeHandlerIds']").val(noticeHandlerIdStr);
			return noticeHandlerIdStr;
		}
		return null;
	}
	
	return declare("sys.lbpmservice.mobile.noticeHandler.NoticeHandler", [Address], {
		type: ORG_TYPE_PERSON,
		
		text: msg["lbpm.process.noticeHandler.name"],
		
		//是否显示头像
		showHeadImg : false,
		
		isMul: true , 
		
		isNew:false,
		
		idField : "noticeHandlerIds",
		
		nameField : "noticeHandlerNames",
		
		templURL:  "sys/lbpmservice/mobile/noticeHandler/noticeHandler.jsp",
		
		dataUrl: '/sys/lbpmservice/mobile/noticeHandler.do?method=handlers&processId=!{processId}',
		
		startup : function() {
			this.inherited(arguments);
			this.dataUrl = util.urlResolver(this.dataUrl, {
				processId:lbpm.modelId || query("[name='sysWfBusinessForm.fdProcessId']")[0].value
			});
			this.templURL ="sys/lbpmservice/mobile/noticeHandler/noticeHandler.jsp";
			this.searchUrl = this.dataUrl+'&keyword=!{keyword}';
			var thisObj = this;
			var num =setInterval(function(){
				if(lbpm && lbpm.globals){
					clearInterval(num);
					thisObj.init();
					topic.publish("/sys/lbpmservice/mobile/noticeHandler/init", this);
				}
			}, 200);
		},
		
		domNodeClick : function(){
			if(this.showHeadImg){
				var evtNode = query(arguments[0].target).closest(".muiCategoryAdd");
				if(evtNode.length <= 0){
					return;
				}
			}
			this.inherited(arguments);
			this.set("value","");
		},
		
		init : function(){//初始化
			var noticeHandler = query("div#noticeHandler");
			//构建内容
			var html = "";
			html += "<input type='hidden' name='noticeHandlerIds' id='noticeHandlerIds'>";
			html += "<input type='hidden' name='noticeHandlerNames' id='noticeHandlerNames'>";
			html += '<a href="javascript:;" style="display:inline-block;font-size: 1.2rem;color:#4285f4;border: 1px solid #4285F4;border-radius: 26px;padding: 0.5rem 1rem;" class="com_btn_link">'+this.text+'</a>';
			html += '<span style="display:none">(<span id="noticeHandlerNum">0</span>/<span id="noticeHandlerTotal">0</span>)</span>';
			noticeHandler.html(html);
			this.subscribe("/mui/category/submit","selectNoticeHandler");
			//初始化@处理人的总数
			//this.updatenoticeHandlers();
			//this.updatenoticeHandlerTotal();
			var fdUsageContent = query('[name=fdUsageContent]')[0];
			var isInput = false;
			lbpm.noticeAuditNote = "";
			var runNum = 0;
			var num = setInterval(function(){
				lbpm.noticeAuditNote = fdUsageContent.value;
				if(runNum >= 15){//最多执行15次
					clearInterval(num);
				}else{
					if(lbpm.noticeAuditNote){
						clearInterval(num);
					}else{
						runNum ++;
					}
				}
			}, 200);
			on(fdUsageContent,'compositionstart', function(event){
				isInput = true;
			})
			on(fdUsageContent,'compositionend', function(event){
				isInput = false;
				//lbpm.noticeAuditNote = query(this).val();
			})
			//监听输入事件
			var thisObj = this;
			//$(fdUsageContent).bind('input propertychange', function(event){
			on(fdUsageContent,"input,propertychange",function(event){
				event.preventDefault();																																
				event.stopPropagation();
				var obj = this;
				//setTimeout(function(){
					//if(!isInput){
						if(lbpm.noticeAuditNote.length > query(obj).val().length){//删除
							// 获取选区的开始位置
							var startPos = obj.selectionStart;
				            // 获取选区的结束位置
				            var endPos = obj.selectionStart + (lbpm.noticeAuditNote.length - query(obj).val().length);
							//根据位置遍历匹配
							var leftStr, rightStr, delContent;
							if(startPos+1 == endPos){//删除一个
								thisObj.delNoticeHandler(startPos,null,lbpm.noticeAuditNote.substring(startPos,endPos));
							}else{//选中删除
								thisObj.delNoticeHandler(startPos,endPos,lbpm.noticeAuditNote.substring(startPos,endPos));
							}
						}else if(lbpm.noticeAuditNote.length < query(obj).val().length){
							obj.focus();
							var pos = obj.selectionStart;
							var len = query(obj).val().length - lbpm.noticeAuditNote.length || 1;
							thisObj.changeNoticeHandlerToStr(pos-len);
							thisObj.updatenoticeHandlerPos(pos, len);
							lbpm.noticeAuditNote = query(obj).val();
						}
					//}
				//}, 0);
			});
			//快速审批语特殊处理
			on(fdUsageContent,"sys/lbpmservice/mobile/NoticeHandler",function(event){
				event.preventDefault();																																
				event.stopPropagation();
				var obj = this;
				//setTimeout(function(){
					//if(!isInput){
						if(lbpm.noticeAuditNote.length > query(obj).val().length){//删除
							// 获取选区的开始位置
							var startPos = obj.selectionStart;
				            // 获取选区的结束位置
				            var endPos = obj.selectionStart + (lbpm.noticeAuditNote.length - query(obj).val().length);
							//根据位置遍历匹配
							var leftStr, rightStr, delContent;
							if(startPos+1 == endPos){//删除一个
								thisObj.delNoticeHandler(startPos,null,lbpm.noticeAuditNote.substring(startPos,endPos));
							}else{//选中删除
								thisObj.delNoticeHandler(startPos,endPos,lbpm.noticeAuditNote.substring(startPos,endPos));
							}
						}else if(lbpm.noticeAuditNote.length < query(obj).val().length){
							var pos = query(obj).val().length;
							var len = query(obj).val().length - lbpm.noticeAuditNote.length || 1;
							thisObj.changeNoticeHandlerToStr(pos-len);
							thisObj.updatenoticeHandlerPos(pos, len);
							lbpm.noticeAuditNote = query(obj).val();
						}
					//}
				//}, 0);
			});
		},
		
		selectNoticeHandler : function(elem, data){
			if(!data || !data.key || !data.curIds || !data.curNames){
				return;
			}
			var key = data.key;
			if(key != 'noticeHandlerIds'){
				return;
			}
			var curIds = data.curIds.split(";");
			var curNames = data.curNames.split(";");
			var fdUsageContent = query('[name=fdUsageContent]')[0];
			var content = fdUsageContent.value;
			var handlerObjs = [];
			var text = "";
			var pos = fdUsageContent.selectionStart;
			this.changeNoticeHandlerToStr(pos);
			for(var i=0; i<curIds.length; i++){
				var handlerObj = {};
				var id = curIds[i];
				var name = curNames[i];
				var startPos = pos + text.length;
				var endPos = startPos + name.length + 3;
				var space = this.ToCDB(" ");
				text += space + "@"+name+space;
				
				handlerObj.id = id;
				handlerObj.name = name;
				handlerObj.startPos = startPos;
				handlerObj.endPos = endPos;
				handlerObjs.push(handlerObj);
			}
			var leftStr = fdUsageContent.value.substring(0,pos);
			var rightStr = fdUsageContent.value.substring(pos);
			fdUsageContent.value = leftStr + text + rightStr;
			lbpm.noticeAuditNote = fdUsageContent.value;
			fdUsageContent.setSelectionRange(text.length+pos,text.length+pos);
			//fdUsageContent.focus();
			//更新已经存在的处理人的位置
			this.updatenoticeHandlerPos(pos+text.length, text.length);
			//更新所有的处理人
			this.updatenoticeHandlers();
			//更新选择人数
			this.updatenoticeHandlerNum(handlerObjs);
			//更新选择总数
			this.updatenoticeHandlerTotal();
		},
		
		updatenoticeHandlerPos :function(pos, len, type){//更新处理人的位置
			if(lbpm.noticeHandlers){
				for(var i=0; i<lbpm.noticeHandlers.length; i++){
					var handler = lbpm.noticeHandlers[i];
					var startPos = handler.startPos;
					var endPos = handler.endPos;
					if(type == 0){
						//减法
						if(startPos >= pos){
							handler.startPos -= len;
							handler.endPos -= len;
						}
					}else{
						//加法
						if(startPos >= (pos-len)){
							handler.startPos += len;
							handler.endPos += len;
						}
					}
				}
			}
		},
		
		updatenoticeHandlers : function(){//更新所有的已处理人
			//总数
			var _dojoConfig = window.dojoConfig || {};
			var jsonUrl=Com_Parameter.ContextPath+"sys/lbpmservice/mobile/noticeHandler.do?method=handlers";
			if(_dojoConfig && _dojoConfig.serverPrefix){
				jsonUrl = _dojoConfig.serverPrefix + "/sys/lbpmservice/mobile/noticeHandler.do?method=handlers";
			}
			jsonUrl += "&isNormal=true&processId=" + query("[name='sysWfBusinessForm.fdProcessId']")[0].value;
			var jsonRtnObj=null;
			var options = {
				url : jsonUrl,
				async : false,
				dataType : 'json',
				success : function(json){
					jsonRtnObj = json;
				}
			};
			$.ajax(options);
			//jsonRtnObj = eval('('+jsonRtnObj+')');
			var handlersObj = [];
			for(var i=0; i<jsonRtnObj.length; i++){
				var handlerObj = {};
				handlerObj.id = jsonRtnObj[i].id;
				handlerObj.name = jsonRtnObj[i].name;
				handlersObj.push(handlerObj);
			}
			lbpm.allNoticeHandlers = handlersObj;
		},
		
		updatenoticeHandlerNum : function(handlers){
			//更新选择的人数
			if(handlers && Object.prototype.toString.call(handlers) === '[object Array]'){
				if(lbpm.noticeHandlers){
					var noticeHandlers = lbpm.noticeHandlers;
					for(var i=0; i<handlers.length; i++){
						var newHandler = handlers[i];
						var isNew = true;
						for(var j=0; j<lbpm.noticeHandlers.length; j++){
							var handler = lbpm.noticeHandlers[j];
							if(handler.id === newHandler.id){
								isNew = false;
								break;
							}
						}
						if(isNew){
							lbpm.noticeHanlerNum += 1;
						}
						noticeHandlers.push(newHandler);
					}
					lbpm.noticeHandlers = noticeHandlers;
				}else{
					//第一次选择
					lbpm.noticeHandlers = [];
					lbpm.noticeHanlerNum = 0;
					var ids = "";
					for(var i=0; i<handlers.length; i++){
						lbpm.noticeHandlers.push(handlers[i]);
						if(ids.indexOf(handlers[i].id) == -1){
							lbpm.noticeHanlerNum += 1;
							ids+=handlers[i].id+";";
						}
					}
				}
				//选择的人数
				query('#noticeHandlerNum').text(lbpm.noticeHanlerNum);
			}
		},
		
		updatenoticeHandlerTotal : function(){
			//总数
			var _dojoConfig = window.dojoConfig || {};
			var jsonUrl=Com_Parameter.ContextPath+"sys/lbpmservice/mobile/noticeHandler.do?method=handlers";
			if(_dojoConfig && _dojoConfig.serverPrefix){
				jsonUrl = _dojoConfig.serverPrefix + "/sys/lbpmservice/mobile/noticeHandler.do?method=handlers";
			}
			jsonUrl += "&type=count&isNormal=true&processId=" + query("[name='sysWfBusinessForm.fdProcessId']")[0].value;
			var jsonRtnObj=null;
			var options = {
				url : jsonUrl,
				async : false,
				dataType : 'json',
				success : function(json){
					jsonRtnObj = json;
				}
			};
			$.ajax(options);
			//jsonRtnObj = eval('('+jsonRtnObj+')');
			if(jsonRtnObj){
				//每次都更新总数
				lbpm.noticeHandlerTotal = parseInt(jsonRtnObj[0].count);
			}else{
				lbpm.noticeHandlerTotal = 0;
			}
			query('#noticeHandlerTotal').text(lbpm.noticeHandlerTotal);
		},
		
		//检测输入是否在处理人中间，也就是破坏了处理人的格式，这时候删除处理人数据，将其变成普通字符串
		changeNoticeHandlerToStr : function(pos){
			if(lbpm.noticeHandlers){
				var newNoticeHandlers = [];
				for(var i=0; i<lbpm.noticeHandlers.length; i++){
					var handler = lbpm.noticeHandlers[i];
					var startPos = parseInt(handler.startPos);
					var endPos = parseInt(handler.endPos);
					if(!(pos > startPos && pos < endPos)){//输入在处理人中间，将该处理人变成字符串
						newNoticeHandlers.push(handler);
					}
				}
				lbpm.noticeHandlers = newNoticeHandlers;
			}
		},
		
		//根据位置删除处理人
		delNoticeHandler : function(delStartPos, delEndPos, delContent){
			var fdUsageContent = query("[name=fdUsageContent]")[0];
			var content = fdUsageContent.value;
			if(lbpm.noticeHandlers){
				var isDel = false;
				var rangeStartPos;
				var rangeEndPos;
				var delHandlersIndex = [];
				//补充删除的内容，保证逻辑的正常
				if(delEndPos){
					content = lbpm.noticeAuditNote.substring(0, delStartPos) + delContent + lbpm.noticeAuditNote.substring(delEndPos);
				}else{
					content = lbpm.noticeAuditNote.substring(0, delStartPos) + delContent + lbpm.noticeAuditNote.substring(delStartPos+1);
				}
				for(var i=0; i<lbpm.noticeHandlers.length; i++){
					var handler = lbpm.noticeHandlers[i];
					var startPos = handler.startPos;
					var endPos = handler.endPos;
					var name = handler.name;
					var id = handler.id;
					if(delEndPos){//删除选中
						if((delStartPos > startPos && delStartPos <= endPos) || (delEndPos > startPos && delEndPos <= endPos) || (
									delStartPos <= startPos && delEndPos >= endPos) || (delStartPos > startPos && delEndPos <= endPos)){
							//符合条件，删除处理人
							isDel = true;
							//lbpm.noticeHandlers.splice(i, 1);//删除处理人
							delHandlersIndex.push(i+'');
							//更新选中人数
							var isExit = false;
							for(var j=0; j<lbpm.noticeHandlers.length; j++){
								if(delHandlersIndex.indexOf(j+'') != -1){
									//说明已经被标记为删除，跳过
									continue;
								}
								if(i != j && lbpm.noticeHandlers[j].id === id){
									//存在
									isExit = true;
									break;
								}
							}
							if(!isExit){
								lbpm.noticeHanlerNum -= 1;
								query('#noticeHandlerNum').text(lbpm.noticeHanlerNum);
							}
						}
						if((rangeStartPos != 0 && !rangeStartPos) && (delStartPos > startPos && delStartPos <= endPos) || (delStartPos > startPos && delEndPos <= endPos)){
							rangeStartPos = startPos;
						}
						if((rangeEndPos != 0 && !rangeEndPos) && (delEndPos > startPos && delEndPos <= endPos) || (delStartPos > startPos && delEndPos <= endPos)){
							rangeEndPos = endPos;
						}
					}else{//删除一个
						if(delStartPos >= startPos && delStartPos < endPos){
							//符合条件，删除处理人
							isDel = true;
							var leftStr = content.substring(0, startPos);
							var rightStr = content.substring(endPos);
							fdUsageContent.value = leftStr + rightStr;
							lbpm.noticeAuditNote = leftStr + rightStr;
							lbpm.noticeHandlers.splice(i, 1);//删除处理人
							this.updatenoticeHandlerPos(endPos,name.length+3,0);//更新处理人位置
							//更新选中人数
							var isExit = false;
							for(var j=0; j<lbpm.noticeHandlers.length; j++){
								if(lbpm.noticeHandlers[j].id === id){
									//存在
									isExit = true;
									break;
								}
							}
							if(!isExit){
								lbpm.noticeHanlerNum -= 1;
								query('#noticeHandlerNum').text(lbpm.noticeHanlerNum);
							}
							fdUsageContent.setSelectionRange(startPos,startPos);
							break;
						}
					}
				}
				if(delEndPos){
					if(isDel){
						//删除元素
						var newNoticeHandlers = [];
						for(var i=0; i<lbpm.noticeHandlers.length; i++){
							if(delHandlersIndex.indexOf(i+'') == -1){
								newNoticeHandlers.push(lbpm.noticeHandlers[i]);
							}
						}
						lbpm.noticeHandlers = newNoticeHandlers;
						//选中删除进行信息更新
						var leftStr,rightStr;
						if(rangeStartPos == 0 || rangeStartPos){
							leftStr = content.substring(0, rangeStartPos);
							fdUsageContent.setSelectionRange(rangeStartPos,rangeStartPos);
						}else{
							leftStr = content.substring(0, delStartPos);
							fdUsageContent.setSelectionRange(delStartPos,delStartPos);
						}
						if(rangeEndPos == 0 || rangeEndPos){
							rightStr = content.substring(rangeEndPos);
							var len = content.length - leftStr.length - rightStr.length;
							this.updatenoticeHandlerPos(rangeEndPos,len,0);//更新处理人位置
						}else{
							rightStr = content.substring(delEndPos);
							var len = content.length - leftStr.length - rightStr.length;
							this.updatenoticeHandlerPos(delEndPos,len,0);//更新处理人位置
						}
						fdUsageContent.value = leftStr + rightStr;
						lbpm.noticeAuditNote = leftStr + rightStr;
					}else{//普通删除
						var leftStr = content.substring(0, delStartPos);
						var rightStr = content.substring(delEndPos);
						var len = content.length - leftStr.length - rightStr.length;
						this.updatenoticeHandlerPos(delEndPos,len,0);//更新处理人位置
					}
				}else{
					if(!isDel){//普通删除
						this.updatenoticeHandlerPos(delStartPos,1,0);//更新处理人位置
					}
				}
			}
			lbpm.noticeAuditNote = fdUsageContent.value;
		},
		
		ToCDB : function(str) {
		    var tmp = "";
		    for (var i = 0; i < str.length; i++) {
		      if (str.charCodeAt(i) > 65248 && str.charCodeAt(i) < 65375) {
		        tmp += String.fromCharCode(str.charCodeAt(i) - 65248);
		      }
		      else {
		        tmp += String.fromCharCode(str.charCodeAt(i));
		      }
		    }
		    return tmp
		 }
	});
});