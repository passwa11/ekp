Com_IncludeFile("quickSelect.css", Com_Parameter.ContextPath+"eop/basedata/resource/css/", 'css', true);
Com_IncludeFile("render.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
(function($,win){
	win.getPosition = function(selfDiv){
		var pos = selfDiv.getBoundingClientRect();
		var off = {left:pos.left,top:pos.bottom};
		off.top+=$(win).scrollTop()
		off.left+=$(win).scrollLeft()
		return off;
	}
	win.getModelName = function(param){
		var option = win.Designer_FormOption||formOption;
		if(!option||!option.dialogs[param.key]){
			return '';
		}
		return option.dialogs[param.key].modelName;
	}
	win.addHignLightClass = function(obj){
		$(obj).addClass('hignlight');
	}
	win.clearHignLightClass = function(obj){
		$(obj).removeClass('hignlight');
	}
	win.triggerValidate = function(e,init){
		if(init){
			return;
		}
		var validate = e.attr('validate');
		if(validate){
			var val = new KMSSValidation('form',{msgInsertPoint:function(e){return e.parentNode.parentNode.nextSibling}});
			var msg = '';
			if(win.seajs){
				win.seajs.use(['lang!eop-basedata'],function(lang){
					msg = lang['message.dialog.search.required'];
					val.getValidator('required').error = msg;
					val.addElements(e[0],validate);
					val.validateElement(e[0]);
				})
			}else{
				msg = '{name} 不能为空';
				val.getValidator('required').error = msg;
				val.addElements(e[0],validate);
				val.validateElement(e[0]);
			}
		}
	}
	win.deleteItem = function(obj){
		var isTextarea = $(obj).data('textarea');
		var idField = $(obj).data('idfield');
		var nameField = $(obj).data('namefield');
		var id = $(obj).data('id');
		var name = $(obj).data('name');
		var idValue = $("[name='"+idField+"']").val().split(';');
		var nameValue = $("[name='"+nameField+"']").val().split(';');
		for(var k=0;k<nameValue.length;k++){
			if(idValue[k]==id){
				idValue.splice(k,1);
				nameValue.splice(k,1);
				break;
			}
		}
		$("[name='"+idField+"']").val(idValue.join(';'))
		$("[name='"+nameField+"']").val(nameValue.join(';'));
		$(obj).parent().remove();
		win.triggerValidate($("[name='"+nameField+"']"));
	}
	win.getMulContainer = function(obj){
		var isTextarea = $(obj).find(".textarea").length>0;
		if(isTextarea){
			return $(obj).find(".textarea");
		}else{
			return $(obj).find(".input");
		}
	}
	win.buildMultiSelector = function(obj,param,init){
		if(!param){
			param = win.getParams($(obj).find('.selectitem').attr("onclick"),obj);
		}
		if(!param)return;
		var isTextarea = $(obj).find(".textarea").length>0;
		var container = win.getMulContainer(obj);
		if(isTextarea){
			var area = $(obj).find(".textarea");
			area.find("textarea").hide();
		}else{
			var input = $(obj).find(".input");
			input.find("input:not(.select_quick_mul_search)").hide();
		}
		container.find(".quick_select_mul_data_item").remove();
		var nameValues = $("[name='"+param.nameField+"']").val()||'';
		var idValues = $("[name='"+param.idField+"']").val()||'';
		if(idValues.length>0){
			nameValues = nameValues.split(';')
			idValues = idValues.split(';')
			for(var i=0;i<nameValues.length;i++){
				var item = '<div onmouseout="clearHignLightClass(this)" onmouseover="addHignLightClass(this)" class="quick_select_mul_data_item">';
				item+=nameValues[i]+'<span data-idField="'+param.idField+'" data-nameField="'+param.nameField+'" data-id="'+idValues[i]+'" data-name="'+nameValues[i]+'" onclick="deleteItem(this)" data-textarea="'+isTextarea+'">X</span></div>';
				container.append(item);
			}
		}
		win.triggerValidate($("[name='"+param.nameField+"']"),init);
	}
	win.getDialogFunc = function(func){
		if(func.indexOf('dialogSelect')>-1){
			var test = /(?=\()[\s\S]*(?=\))/;
			var s = func.split('dialogSelect')[1].match(test);
			return s[0].substring(1);
		}else if(func.indexOf('Designer_DialogSelect')>-1){
			var test = /(?=\()[\s\S]*(?=\))/;
			var s = func.split('Designer_DialogSelect')[1].match(test);
			return s[0].substring(1);
		}else{
			func = func.split('\(')[0];
			if(!win[func]){
				return '';
			}
			func =win[func].toString();
			return win.getDialogFunc(func);
		}
	}
	win.isDialogSelect = function(func){
		return win.getDialogFunc(func)!='';
	}
	win.replaceAreaSep = function(str,src,desc,sepLeft,sepRight){
		var rep = false,arr = [];
		for(var i=0;i<str.length;i++){
			var ch = str.charAt(i);
			if(!rep){
				arr.push(ch)
				if(ch==sepLeft){
					rep = true;
				}
			}else{
				if(ch==sepRight){
					arr.push(ch)
					rep = false;
				}else if(ch==src){
					arr.push(desc)
				}else{
					arr.push(ch);
				}
			}
		}
		return arr.join('')
	}
	win.getContext = function(func){
		if(func.indexOf('dialogSelect')>-1||func.indexOf('Designer_DialogSelect')>-1){
			return '';
		}else{
			func = func.split('\(')[0];
			if(!win[func]){
				return '';
			}
			func =win[func].toString();
			func = func.replace(/\n/g,';').replace(/\t/g,'');
			var reg = new RegExp('(var[^;]+;)');
			var s = func.match(/(var[^;]+;)/g);
			return s?s.join(''):'';
		}
	}
	win.executeExpression = function(param,exp){
		if(!exp){
			return '';
		}
		var ctx = [];
		for(var i in param){
			ctx.push("var "+i+"='"+param[i]+"'");
		}
		try{
			return eval("(function(){"+ctx.join('; ')+";return "+exp+"})()");
		}catch(e){
			return '';
		}
	}
	win.getTempParam = function(ctx){
		var param = {};
		if(!ctx){
			return param;
		}
		ctx = ctx.replace(/\s*var\s*/g,'').split(/[;]+/g);
		for(var i=0;i<ctx.length;i++){
			var keys = (";"+ctx[i]).match(/[,;][^,^;^\=]+\=/g);
			var vals = (";"+ctx[i]).split(/[,;][^,^;^\=]+\=/g);
			if(!keys){
				continue;
			}
			for(var k=0;k<keys.length;k++){
				if(!keys[k]){
					continue;
				}
				var key = keys[k].replace(/\s*/g,"");
				key=key.substring(1,key.length-1)
				param[key] = win.executeExpression(param,vals[k+1]);
			}
		}
		return param;
	}
	win.getExecutedParam = function(param){
		var ctx = [];
		for(var i in param){
			ctx.push("var "+i+"='"+param[i]+"'");
		}
		return ctx.join(';');
	}
	win.getParams = function(func,_obj){
		try{
			if(!win.isDialogSelect(func)){
				return null;
			}
			var context = win.getContext(func);
			var source = func.indexOf('dialogSelect')>-1?[]:func.split('\(')[1].split('\)')[0].split(',');
			var target=[],rfunc = func.split('\(')[0];
			if(source.length>0&&win[rfunc]){
				target = win[rfunc].toString().split('\(')[1].split('\)')[0].split(',');
			}
			var pa = {};
			for(var i=0;i<target.length;i++){
				if(!target[i]||!source[i]){
					continue;
				}
				pa[target[i]] = source[i].replace(/[\'\"]/g,"");
			}
			func = win.getDialogFunc(func);
			func = win.replaceAreaSep(func,',','##','{',"}");
			func = func.split(','); 
			var obj = {
				mul:func[0],
				key:func[1].replace(/[\'\"]/g,""),
				idField:pa[func[2]]||func[2].replace(/[\'\"]/g,""),
				nameField:pa[func[3]]||func[3].replace(/[\'\"]/g,""),
				temp:win.getTempParam(context)
			};
			obj.context = win.getExecutedParam(obj.temp);
			var anoumies = [],index =null;;
			for(var i=4;i<func.length;i++){
				func[i] = func[i].replace(/^\s+|\s+$/g,'');
				if(func[i]=='null'){
					continue;
				}
				if(func[i].charAt(0)=='{'){
					obj.param = {};
					var param = func[i].substring(1,func[i].length-1).split("##");
					for(var k=0;k<param.length;k++){
						var map = param[k].split('\:');
						map[0] = map[0].replace(/^[\'\"]|[\'\"]$/g,'');
						if(win.Designer_FormOption){
							map[1] = map[1].replace(/^[\'\"]|[\'\"]$/g,'');
						}
						if(map[1].charAt(0)=='$'){
							obj.param[map[0]] = eval(map[1])
						}else if(win.Designer_FormOption){
							_obj = (map[1].indexOf('!{index}')>-1&&win.DocListFunc_GetParentByTagName)?DocListFunc_GetParentByTagName("TR",_obj):document.body;
							var name = _obj.tagName=='BODY'?'(':('('+_obj.rowIndex-1);
							name+=map[1]+')';
							var val = $(_obj).find("[name*='"+name+"']").val();
							if(!val&&map[0]=='fdCompanyId'){//如果是公司参数，有可能没有配置公司字段，直接传
								obj.param[map[0]] = '';
							}else{
								obj.param[map[0]] = val||map[1];
							}
						}else{
							obj.param[map[0]] = win.executeExpression(obj.temp,map[1]);
						}
					}
					console.log(obj.param)
				}else if(index==null&&/^[^\"^\'^\{]/.test(func[i])&&func[i].substring(0,9)!='function('){
					obj.callback = func[i];
				}else if(func[i].substring(0,9)=='function('){
					anoumies.push("("+func[i]);
					index = i;
				}else if(index!=null&&i>index){
					anoumies.push(func[i]);
				}
			}
			if(anoumies.length>0){
				anoumies = anoumies.join(',').replace(/##/g,',');
				var test = /(?=\{)[\s\S]*(?=\})/;
				var head = anoumies.replace(/(function\([^\)]*\))[\s\S]+/ig,'$1');
				var s = anoumies.split(head)[1].match(test);
				obj.callback = "(function(data,id){"+obj.context+";"+head+"{"+s[0].substring(1)+"}(data,id))})";
			}
			obj.param = obj.param||{};
			obj.param.fdNotId = obj.param.fdNotId||'fdNotId';
			return obj;
		}catch(e){
			console.log(e)
			return null;
		}
		
	}
	if(!formOption||!formOption.modelName){
		return;
	}
	$(document).on("click",function(e){
		e = e.target||e.srcElement;
		if($(".select_quick_content").length==0){
			return;
		}
		var name = $(".select_quick_content").data("source");
		if(e.name==name){
			return;
		}
		var input = $("[name='"+name+"']");
		if($(".select_quick_content").attr('mul')=='true'){
			input.parent().find(".select_quick_mul_search").val('');
			input.parent().find(".select_quick_mul_search").focus();
			if($(e).hasClass('select_quick_data')||(e.tagName=='DIV'&&($(e).hasClass('input')||$(e).hasClass('textarea')))){
				return;
			}
		}else if(!input.parent().prev().val()&&input.attr("_readOnly")=='true'){
			input.val("");
		}
		$(".select_quick_content").remove();
	})
	var tmp = "{$<div class='select_quick_content' mul='{%mul%}'>$}";
	tmp+='for(var i=0;i<data.length;i++){';
	tmp+='if(!data[i].fdId){';
	tmp+="{$<div class='select_quick_data'><span>{%data[i].displayName%}</span></div>$}";
	tmp+="}else{";
	tmp+="{$<div class='select_quick_data select_quick_data_enable' onclick='window.quickSelectCallback({%JSON.stringify(data[i])%},this,{%JSON.stringify(data)%})'><span>{%data[i].displayName%}</span>$}";
	tmp+="if(mul){";
	tmp+="{$<div class='select_quick_data_icon {%selectedId.indexOf(data[i].fdId)>-1?'select_quick_data_icon_checked':''%}'></div>$}";
	tmp+="}{$</div>$}}}{$</div>$}";
	
	win.buildSglObject = function(){
		var selfDiv = this;
		var func = $(this).attr("onclick");
		if(!func){
			func = $(this).find(".selectitem").attr("onclick");
		}
		if(!func)return;
		if(!win.isDialogSelect(func)){
			return;
		}
		var paramObj = win.getParams(func,this);
		if(!paramObj)return;
		var modelName = win.getModelName(paramObj);
		if(!modelName)return;
		var data = new KMSSData();
		data.AddBeanData('eopBasedataBusinessService&type=getModleDisplayProperty&modelName='+modelName);
		data = data.GetHashMapArray();
		if(!data||data.length==0){
			return;
		}
		paramObj.displayName = data[0].displayName;
		if(paramObj.mul=='true'){
			win.buildMulObject.call(this,true);
			return;
		}
		$(this).removeAttr("onclick");
		$(this).find(".selectitem").attr("onclick",func);
		var nameField = data[0].displayName;
		var _tmp = tmp.replace(/displayName/g,nameField);
		$(this).removeAttr("onclick");
		var pos ={x:this.offsetTop+this.clientHeight,y:this.offsetLeft};
		var input = $(this).find(".input>input");
		input.css("cursor","input")
		if(win.seajs){
			win.seajs.use(['lang!eop-basedata'],function(lang){
				input.attr("placeholder",lang['message.dialog.search.placeholder']);
			})
		}else{
			input.attr("placeholder","请输入关键字进行搜索");
		}
		if(window.navigator.userAgent.toLowerCase().indexOf("msie")>-1
			||window.navigator.userAgent.toLowerCase().indexOf("trident")>-1){//IE
			input.attr("data-quickSelectFirstFlag","true");
		}
		var url = (win.Designer_FormOption||formOption).dialogs[paramObj.key].sourceUrl.substring(1);
		url = Com_Parameter.ContextPath+url;
		input.attr('autocomplete','off').attr("_readOnly",input.prop("readonly")).prop("readonly",false).on("input propertychange",function(e){
			if(window.navigator.userAgent.toLowerCase().indexOf("msie")>-1
				||window.navigator.userAgent.toLowerCase().indexOf("trident")>-1){//IE
				var quickSelectFirstFlag=input.attr("data-quickSelectFirstFlag");
				if(quickSelectFirstFlag=='true'){//第一次进不清空
					input.attr("data-quickSelectFirstFlag","false");
				}else{//非第一次进，清空对应的ID
					$(this).parent().prev().val("");   //IE内核首次进入会清空ID，谷歌无该问题
				}
			}else{
				$(this).parent().prev().val("");   //IE内核首次进入会清空ID，谷歌无该问题
			}
			$(".select_quick_content").remove();
			if(!this.value){
				return;
			}
			var selfInput = this;
			paramObj = win.getParams(func,selfDiv);
			paramObj.param = paramObj.param||{}
			paramObj.param['q._keyword'] = this.value;
			paramObj.param['pageno'] = 1;
			paramObj.param['rowsize'] = 8;
			$.post(
				url,
				paramObj.param,
				function(rtn){
					try{
						var datas =  [];
						if(rtn.datas.length==0){
							var obj = {},subject = $(selfInput).attr("subject");
							if(win.seajs){
								win.seajs.use(['lang!eop-basedata'],function(lang){
									obj[nameField] = lang['message.dialog.search.notFund'].replace("{0}",subject||lang['message.dialog.search.record']);
								})
							}else{
								obj[nameField] = '没有找到相关{0}'.replace("{0}",subject||'记录');
							}
							datas.push(obj)
						}else{
							for(var k=0;k<rtn.datas.length;k++){
								var row = {};
								for(var r=0;r<rtn.datas[k].length;r++){
									row[rtn.datas[k][r].col] = rtn.datas[k][r].value;
								}
								datas.push(row);
							}
						}
						var code = window.YayaTemplate(_tmp).render({
							data:datas,
							mul:false,
							selectedId:$(selfInput).parent().prev().val()
						});
						$(code).appendTo(document.body);
						var pos = win.getPosition(selfDiv);
						$(".select_quick_content").css({
							left:pos.left,
							top:pos.top,
							width:(selfDiv.clientWidth)+'px'
						}).data("source",selfInput.name);
						win.quickSelectCallback = function(data,obj,datas){
							if(paramObj.idField.indexOf('\*')>-1){
								var index = DocListFunc_GetParentByTagName("TR",selfDiv).rowIndex-1;
								paramObj.idField = paramObj.idField.replace(/\*/g,index).replace(/^\s*|\s*/g,'');
								paramObj.nameField = paramObj.nameField.replace(/\*/g,index).replace(/^\s*|\s*/g,'');
							}
							$("[name='"+paramObj.idField+"']").val(data.fdId);
							$("[name='"+paramObj.nameField+"']").val(data[nameField]);
							if(paramObj.callback){
								var name = selfInput.name.replace(/\_name\)$/,')');
								eval(paramObj.callback)([data],name);
							}
						}
					}catch(e){
						console.log(e)
					}
				}
			);
		});
	}
	win.buildMulObject = function(textarea){
		var selfDiv = this;
		var func = $(this).attr("onclick");
		if(!func){
			func = $(this).find(".selectitem").attr("onclick");
		}
		if(!func)return;
		if(!win.isDialogSelect(func)){
			return;
		}
		var paramObj = win.getParams(func,this);
		if(!paramObj)return;
		var modelName = win.getModelName(paramObj);
		if(!modelName)return;
		var data = new KMSSData();
		data.AddBeanData('eopBasedataBusinessService&type=getModleDisplayProperty&modelName='+modelName);
		data = data.GetHashMapArray();
		if(!data||data.length==0){
			return;
		}
		$(this).removeAttr("onclick");
		$(this).find(".selectitem").attr("onclick",func);
		paramObj.displayName = data[0].displayName;
		if(paramObj.idField.indexOf('\*')>-1){
			var index = DocListFunc_GetParentByTagName("TR",selfDiv).rowIndex;
			paramObj.idField = paramObj.idField.replace(/\*/g,index);
			paramObj.nameField = paramObj.nameField.replace(/\*/g,index);
		}
		win.buildMultiSelector(this,paramObj,true);
		var nameField = data[0].displayName;
		var _tmp = tmp.replace(/displayName/g,nameField);
		$(this).removeAttr("onclick");
		var pos ={x:this.offsetTop+this.clientHeight,y:this.offsetLeft};
		var url = (win.Designer_FormOption||formOption).dialogs[paramObj.key].sourceUrl.substring(1);
		
		var container = win.getMulContainer(this);
		container.append('<input class="inputsgl select_quick_mul_search" autocomplete="off"/>');
		container.on("click",function(){
			container.find("input").focus();
		})
		url = Com_Parameter.ContextPath+url;
		if(win.seajs){
			win.seajs.use(['lang!eop-basedata'],function(lang){
				container.find("input").attr("placeholder",lang['message.dialog.search.placeholder']);
			})
		}else{
			container.find("input").attr("placeholder","请输入关键字进行搜索");
		}
		container.find("input").on("input propertychange",function(e){
			$(".select_quick_content").remove();
			if(!this.value){
				return;
			}
			var selfInput = this;
			paramObj = win.getParams(func,selfDiv);
			paramObj.param = paramObj.param||{}
			paramObj.param['q._keyword'] = this.value;
			paramObj.param['pageno'] = 1;
			paramObj.param['rowsize'] = 8;
			$.post(
				url,
				paramObj.param,
				function(rtn){
					try{
						var datas =  [];
						if(rtn.datas.length==0){
							var obj = {},subject = $(selfInput).attr("subject");
							if(win.seajs){
								win.seajs.use(['lang!eop-basedata'],function(lang){
									obj[nameField] = lang['message.dialog.search.notFund'].replace("{0}",subject||lang['message.dialog.search.record']);
								})
							}else{
								obj[nameField] = '没有找到相关{0}'.replace("{0}",subject||'记录');
							}
							datas.push(obj)
						}else{
							for(var k=0;k<rtn.datas.length;k++){
								var row = {};
								for(var r=0;r<rtn.datas[k].length;r++){
									row[rtn.datas[k][r].col] = rtn.datas[k][r].value;
								}
								datas.push(row);
							}
						}
						var code = window.YayaTemplate(_tmp).render({
							data:datas,
							mul:paramObj.mul,
							selectedId:$("[name='"+paramObj.idField+"']").val()||''
						});
						$(code).appendTo(document.body);
						var offset = textarea?0:4;
						var pos = win.getPosition(selfDiv);
						$(".select_quick_content").css({
							left:pos.left,
							top:pos.top,
							width:(selfDiv.clientWidth-offset)+'px'
						}).data("source",paramObj.idField);
						win.quickSelectCallback = function(data,obj,datas){
							var idValue = []
							if($("[name='"+paramObj.idField+"']").val()){
								idValue.push($("[name='"+paramObj.idField+"']").val());
							}
							var nameValue = []
							if($("[name='"+paramObj.nameField+"']").val()){
								nameValue.push($("[name='"+paramObj.nameField+"']").val());
							}
							var checked = $(obj).find(".select_quick_data_icon").hasClass('select_quick_data_icon_checked');
							if(checked){
								idValue = idValue[0].split(';');
								nameValue = nameValue[0].split(';');
								for(var k=0;k<nameValue.length;k++){
									if(idValue[k]==data.fdId){
										idValue.splice(k,1);
										nameValue.splice(k,1);
										break;
									}
								}
								$(obj).find(".select_quick_data_icon").removeClass('select_quick_data_icon_checked');
								$("[name='"+paramObj.idField+"']").val(idValue.join(';'));
								$("[name='"+paramObj.nameField+"']").val(nameValue.join(';'));
								win.buildMultiSelector(selfDiv,paramObj);
							}else{
								idValue.push(data.fdId);
								nameValue.push(data[nameField]);
								$("[name='"+paramObj.idField+"']").val(idValue.join(';'));
								$("[name='"+paramObj.nameField+"']").val(nameValue.join(';'));
								$(obj).find(".select_quick_data_icon").addClass('select_quick_data_icon_checked');
								win.buildMultiSelector(selfDiv,paramObj);
							}
							if(paramObj.callback){
								var name = selfInput.name.replace(/\_name\)$/,')');
								eval(paramObj.callback)([data],name);
							}
						}
					}catch(e){
						console.log(e)
					}
				}
			);
		});
	}
	$("div.inputselectsgl").each(function(){
		if(win.DocListFunc_GetParentByTagName){
			var TR = win.DocListFunc_GetParentByTagName("TR",this);
			if($(TR).attr('KMSS_IsReferRow')=='1'){
				return;
			}
		}
		win.buildSglObject.call(this);
	});
	$("div.inputselectmul").each(function(){
		if(win.DocListFunc_GetParentByTagName){
			var TR = win.DocListFunc_GetParentByTagName("TR",this);
			if($(TR).attr('KMSS_IsReferRow')=='1'){
				return;
			}
		}
		win.buildMulObject.call(this);
	});
	if(win.DocList_AddRow){
		var func = win.DocList_AddRow;
		win.DocList_AddRow = function(optTB, content, fieldValues){
			var tr = func(optTB, content, fieldValues);
			$(tr).find("div.inputselectsgl").each(function(){
				win.buildSglObject.call(this);
			});
			$(tr).find("div.inputselectmul").each(function(){
				win.buildMulObject.call(this);
			});
			return tr;
		}
	}
}(jQuery,window))