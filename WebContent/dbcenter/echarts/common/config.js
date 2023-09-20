(function(){
	if(window.dbecharts!=null){
		return;
	}
	var dbecharts = {}; 

	/**从表单中读取数据，放入data中*/
	dbecharts.read = function(name, data, areaDom){
		LUI.$('[data-dbecharts-config="'+name+'"]',areaDom).each(function(){
			var value = this.value;			
			if(this.getAttribute('expressionvalue1')!=null && this.getAttribute('expressionvalue1')!=''){
				var index=this.name.substring(this.name.indexOf('[')+1,this.name.indexOf('[')+2)
				//编辑的时候 判断公式定义器有没有值
				var expressionValue = "inputs["+index+"].expressionValue1" ;
				if(document.getElementsByName(expressionValue)[0].getAttribute('value')!=null && document.getElementsByName(expressionValue)[0].getAttribute('value')!=''){
					 //有值
					$("#ddd"+index).parent().parent('center').css("display", "block");
					$("#initValue"+index).css("cursor", "not-allowed");	
					$("#initValue"+index).parent().css("cursor", "not-allowed")
					$("#initValue"+index).css("pointer-events", "none");										
					//$("#initValue"+index).css('color', '#cccccc');
				 }
				//编辑的时候 判断初始值有没有值
				if($("#initValue"+index).val()!=''){
					$("#ddd"+index).parent().parent('center').css("display", "none");
					$("#ddd"+index).css("cursor", "not-allowed");	
					$("#ddd"+index).parent().parent('center').css("display", "none");
					$("#ddd"+index).css("pointer-events", "none");										
					//$("#ddd"+index).css('color', '#cccccc');
				}		
			}
			
			if(this.tagName=='INPUT'){
				if(this.type=="checkbox"){
					value = this.checked+"";
				}else if(this.type=="radio"){
					if(!this.checked){
						return;
					}
				}
			}
			value = LUI.$.trim(value);
			
			/* 如果控件有默认值且控件输入值被清空，则记录下用户清空值的控件名称（name属性）至特殊数组 nullValueControls中,
			 * 这样做的目的是既要保障EChart配置的完整性，又要使用户视觉上能保存清空行为
             * 实际上当输入控件值为空时，渲染图表时取的是默认值，并且默认值会被保存下来，再次刷新编辑时 nullValueControls 记录的控件将不显示任何值*/
			if(value==""){
				var defaultValue = this.getAttribute("data-default-value"); // 默认值
				if( defaultValue && LUI.$.trim(defaultValue )!=""){
					value = defaultValue;
					if(data.nullValueControls){
						data.nullValueControls.push(this.name);
					}else{
						data.nullValueControls = [this.name];
					}
				}
			}

			
			if(value==""){
				return;
			}else{
				var digitUnit = this.getAttribute("data-digit-unit"); // 数字计量单位（百分比、像素......）
				if( digitUnit && LUI.$.trim(digitUnit)!="" && value.indexOf(digitUnit)==-1 ){
					value = value+LUI.$.trim(digitUnit);
				}
			}
			
			//填充控件输入的数据至配置信息对象中
			fillDataToConfig( this.name, data, value );

		});
	};
	
	
    /**
	* 填充控件输入的数据至配置信息对象中
	* @param controlName 控件名称
	* @param config 配置的echart option
	* @param value 值
	* @return
	*/  
	function fillDataToConfig( controlName, config, value ){
		var names = controlName.split('.');
		var parentObj = config;
		for(var i=0; i<names.length; i++){
			var attrName = names[i];
			var leftBracketIndex = attrName.indexOf('['); // 左括弧下标
			var isLastAttr = (i == names.length-1); // 判断是否最后一个属性
			if(leftBracketIndex>-1){ // 数组

			    attrName = attrName.substring(0, leftBracketIndex);
				var index = parseInt(names[i].substring(leftBracketIndex+1, names[i].length-1));
				if(isNaN(index)){
					// 如果数组没有设置下标，[]左右括弧也作为属性名的一部分
					if(parentObj.hasOwnProperty(names[i])==false){
						parentObj[names[i]] = {};
					}
					parentObj = parentObj[names[i]];
					continue;
				}else{
					if(parentObj.hasOwnProperty(attrName)==false){
						parentObj[attrName] = [];
					}
					parentObj = parentObj[attrName];
					if(isLastAttr){
						parentObj[index] = value; // 循环到最后一个属性时直接赋值
						break;
					}else{
						if(parentObj[index]==null){
							parentObj[index] = {};
						}
						parentObj = parentObj[index];
					}
				}

			}else{ // 非数组
				if(isLastAttr) {
					if(LUI.$.trim(value).indexOf("[") > -1 ||  LUI.$.trim(value).indexOf("{") > -1 ) {
						try {
							value =  (new Function("return (" + value + ");"))()
						} catch(e){}
					}
					parentObj[attrName] = value; // 循环到最后一个属性时直接赋值
					break;
				}else{
					if(parentObj.hasOwnProperty(attrName)==false){
						parentObj[attrName] = {};
					}
					parentObj = parentObj[attrName];
				}
				
			}
			
		}	
	}
	

	/**将data中的数据，写到表单中*/
	dbecharts.write = function(name, data, areaDom){
		// 先处理明细表
		LUI.$('[data-dbecharts-table="'+name+'"]',areaDom).each(function(){
			var name = this.id;
			var index = name.indexOf('_');
			name = name.substring(0, index);
			
			var tbInfo = DocList_TableInfo[this.id];
			var _data = data[name];
			var rowSize = _data==null?0:_data.length;
			//行不足则补齐
			for(var j=rowSize-tbInfo.lastIndex+tbInfo.firstIndex; j>0; j--){
				DocList_AddRow(this);
			}
			//行太多则删除
			for(var j=tbInfo.lastIndex-tbInfo.firstIndex-rowSize; j>0; j--){
				DocList_DeleteRow(this.rows[tbInfo.firstIndex+j-1]);
			}
		});
		
		//写入数据
		LUI.$('[data-dbecharts-config="'+name+'"]',areaDom).each(function(){
			
			var value = getValueFromConfig(this.name,data);
			if(this.getAttribute('expressionvalue1')!=null && this.getAttribute('expressionvalue1')!=''){	
				var index=this.name.substring(this.name.indexOf('[')+1,this.name.indexOf('[')+2)
				//编辑的时候 判断公式定义器有没有值
				 var expressionValue = "inputs["+index+"].expressionValue1" ;
				 if(document.getElementsByName(expressionValue)[0].getAttribute('value')!=null && document.getElementsByName(expressionValue)[0].getAttribute('value')!=''){
					 	//有值
						$("#ddd"+index).parent().parent('center').css("display", "block");
						$("#initValue"+index).css("cursor", "not-allowed");	
						$("#initValue"+index).parent().css("cursor", "not-allowed")
						$("#initValue"+index).css("pointer-events", "none");										
						//$("#initValue"+index).css('color', '#cccccc');
				 	}
				 	//编辑的时候 判断初始值有没有值
					if($("#initValue"+index).val()!=''){
						$("#ddd"+index).parent().parent('center').css("display", "none");
						$("#ddd"+index).css("cursor", "not-allowed");	
						$("#ddd"+index).parent().parent('center').css("display", "none")
						$("#ddd"+index).css("pointer-events", "none");
					}		
			}
			
			if(value==null){
				value = "";
			}else{
				var digitUnit = this.getAttribute("data-digit-unit"); // 数字计量单位（百分比、像素......）
				if(digitUnit && LUI.$.trim(digitUnit)!=""){
					value = value.replace(LUI.$.trim(digitUnit),"");
				}
			}
			
			/* 首先判断控件是否被用户清空默认值，没有被用户清空的相应控件则直接赋值，反之不做处理
			 * nullValueControls 的定义详见 dbecharts.read 函数
			 */
			if(!data.nullValueControls || $.inArray(this.name, data.nullValueControls)==-1 ) {
					if(this.tagName=='INPUT'){
						if(this.type=="checkbox"){
							this.checked = ('true'==value);
							// 如果有onclick事件，触发
							if($(this).attr("onclick")){
								this.onclick();
							}
						}else if(this.type=="radio"){
							// 图表中心 》 统计图表 》 普通编程模式 》图表选项 》展示模式   （默认选中 “图形视图”）
							if(this.name == 'chart.showMode' && (value == null || value == '')) {
								value = 'picView';
							}
							this.checked = (this.value == value);
						}else{
							this.value = value;
						}
					}else if(this.tagName=='SELECT'){						
						if(value && value != ''){
							if(this.options.length==0){
								//兼容将text变为select的旧数据
								var option = $("<option>").val(value).text(value);
								$(this).append(option);
							}
							this.value = value;	
							var name=$(this).attr("name");
							if(!(name.indexOf('outputs')>-1 && name.indexOf('format')>-1)){
								if($(this).attr("onchange")){
									this.onchange();
								}
							}
							
						}
					}else{
						this.value = value;
					}				
			}

			
			// 设置控件的默认值到特殊属性  data-default-value 中，目的是为了当用户清除输入框值的情况下，保存时读取默认值以确保EChart option配置的完整性
			var defaultValue = null;
			if(data.defaultEditValueOption){
				defaultValue = getValueFromConfig(this.name,data.defaultEditValueOption);
	            if(defaultValue!=null){
	            	$(this).attr("data-default-value",defaultValue);
	            }
			}
			
		});
		
	    //单独处理格式
		$.each($("#outputs_DocList tr:gt(0)"),function(i){
			//获取格式值
			 var formatName="outputs["+i+"].format";
			 var formatValue=$("select[name='" +formatName+ "']").val();
			 
			 var argumentName="outputs["+i+"].argument";
			 var argumentValue=$("input[name='" +argumentName+ "']").val();
			 
			 var formatValName="outputs["+i+"].formatVal";
			 var formatValValue=$("input[name='" +formatValName+ "']").val();
			 
			 changePageFormat(formatValue,i,argumentValue,formatValValue)
		});
		
	};
	
    /**
	* 从option配置中按照控件名称读取值
	* @param controlName 控件名称
	* @param config 配置的echart option
	* @return 返回配置值
	*/  
	function getValueFromConfig( controlName, config ){
		var names = controlName.split('.');
		var value = config;
		for(var i=0; i<names.length; i++){
			var name = names[i];
			var index = name.indexOf('[');
			if(index>-1){
				//数组
				name = name.substring(0, index);
				index = parseInt(names[i].substring(index+1, names[i].length-1));
				// 支持[]写法
				if(isNaN(index)){
					value = value[names[i]];
					if(value == null){
						break;
					}else{
						continue;
					}
				}
				value = value[name];
				if(value==null){
					break;
				}
				value = value[index];
			}else{
				//非数组
				value = value[name];
			}
			if(value==null){
				break;
			}
		}
		if( typeof(value)=="undefined" || $.trim(value)=="" ){
			value = null;
		}
		
		if(value!= null && value!="" && $.isPlainObject(value)){
		      value = JSON.stringify(value);
		}
		return value;
	}
	
	/**禁用配置字段，防止垃圾数据提交*/
	dbecharts.disable = function(disabled,areaDom){
		LUI.$('[data-dbecharts-config]',areaDom).each(function(){
			this.disabled = disabled;
		});
	};
	
	/**初始化*/
	dbecharts.init = function(callback){
		var id = window.setInterval(function(){
			if(LUI.$==null){
				return;
			}
			for(var i=0; i<DocList_Info.length; i++){
				if(document.getElementById(DocList_Info[i])!=null){
					var tbInfo = DocList_TableInfo[DocList_Info[i]];
					if(tbInfo==null){
						inited = false;
						return;
					}
				}
			}
			window.clearInterval(id);
			callback();
			bind();
		}, 100);
	};
	
	/**执行测试*/
	dbecharts.test = function(name, path){
		var data = {};
		dbecharts.read(name, data);
		var code = LUI.stringify(data);
		LUI.$.post(path+'/dbcenter/echarts/common/test.jsp', {'code':base64Encode(code)}, function(data){
			var win = window.open("about:blank", "_blank");
			win.document.write('<pre>'+LUI.$.trim(data)+'</pre>');
		});
	};
	
	/**查找多行输入框，绑定事件*/
	function bind(){
		LUI.$('textarea[data-dbecharts-config]').each(function(){
			bindEvent(this);
		});
		for(var i=0; i<DocList_Info.length; i++){
			var tb = document.getElementById(DocList_Info[i]);
			if(tb==null){
				continue;
			}
			LUI.$(tb).bind('table-add', function(tr){
				LUI.$(tr).children().each(function(){
					LUI.$(this).find('textarea[data-dbecharts-config]').each(function(){
						bindEvent(this);
					});
				});
			});
		}
	}
	/**绑定事件，获取焦点时把输入框放大*/
	function bindEvent(element){
		var me = LUI.$(element);
		if(me.height()>200){
			return;
		}
		me.focus(function(){
			var _self = LUI.$(element);
			var _parent = LUI.$(element.parentNode);
			var height = _parent.attr('data-dbecharts-height');
			if(!height){
				height = _self.height()+'px';
				_parent.attr('data-dbecharts-height', height);
			}
			_parent.css({'height':height, 'overflow-y':'visible', 'margin-bottom':'2px'});
			_self.css({'position':'relative', 'z-index':'1000', 'height':'300px'});
		});
		me.blur(function(){
			var _self = LUI.$(element);
			var _parent = LUI.$(element.parentNode);
			var height = _parent.attr('data-dbecharts-height');
			_parent.css({'height':'', 'overflow-y':'', 'margin-bottom':''});
			_self.css({'position':'', 'z-index':'', 'height':height});
		});
	}
	
	window.dbecharts = dbecharts;
	
	window.IEVersion = function() {
		var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
		var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
		var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
		var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
		if(isIE) {
			var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
			reIE.test(userAgent);
			var fIEVersion = parseFloat(RegExp["$1"]);
			if(fIEVersion == 7) {
				return 7;
			} else if(fIEVersion == 8) {
				return 8;
			} else if(fIEVersion == 9) {
				return 9;
			} else if(fIEVersion == 10) {
				return 10;
			} else {
				return 6;//IE版本<=7
			}   
		} else if(isEdge) {
			return 'edge';//edge
		} else if(isIE11) {
			return 11; //IE11  
		}else{
			return -1;//不是ie浏览器
		}
	}
	
	//解决 IE8、IE9 不支持 console问题，避免console报错
	window.console = window.console || (function () {
       var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile = c.clear = c.exception = c.trace = c.assert = function(){};
       return c;
    })();
})();