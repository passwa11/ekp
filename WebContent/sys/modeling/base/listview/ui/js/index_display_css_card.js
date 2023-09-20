
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {

	//列表数据加载完成进行显示项样式设置
	topic.subscribe('gridTable.onload',function(datas){
		//121800：每次做样式设置前将全局变量的样式清空
		if(rowCss){
			rowCss = {};
		}
		if(cssData){
			cssData ={};
		}
		//dataFormat([],datas)
		displayCssSet(datas);
	})

	//显示项改变行样式
	topic.subscribe('list.loaded',function(datas){
		for(var key in rowCss){
			$('div[data-id="'+ key +'"]').find(".cardClassifyDetails").css(rowCss[key]);
			$('div[data-id="'+ key +'"]').find(".cardClassifyDetailsText").css(rowCss[key]);
		}

		for(var dateKey in cssData){
           var data = cssData[dateKey];
           for(var cssKey in data){
           	var className = ".cardClassifyAbstractContent."+cssKey;
           	$('div[data-id="'+ dateKey +'"]').find(className).css(data[cssKey]);
		   }
		}
    })
		
	//对页面列表数据进行预处理
    function preHandleData(datas){
    	var obj = {};

    	for(var row = 0;row < datas.datas.length;row++){
    		for(var col = 0;col < datas.columns.length;col++){
    			var item = datas.datas[row][col];
    			if (item){
					if(!obj[item.col]){
						obj[item.col] = [];
					}
					obj[item.col].push({
						x:row,
						y:col,
						value:item.value
					});
				}
    		}
    	}
    	return obj;
    }

	// 依据列元素调整数据源结构
	function dataFormat(data, _data) {

	}
    
    //显示项样式设置
    function displayCssSet(datas){
    	//1.取显示项的样式设置
    	//var fdDisplayCssObj = '${fdDisplayCss}';
    	/*if(fdDisplayCssObj){
    		fdDisplayCssObj = $.parseJSON(fdDisplayCssObj);
    	}*/
    	var fdDisplayCssObj = $.parseJSON(listOption.fdDisplayCssObj);
    	if(listOption.allField){
			var allField = $.parseJSON(listOption.allField);
			//处理表单控件属性变化后，对应地改变where条件
			doChangeWhere(allField,fdDisplayCssObj);
		}
    	//2.取列表数据并预处理
    	var dataObj = preHandleData(datas);	
    	if(fdDisplayCssObj){
     		for(var key in fdDisplayCssObj){
     			if(dataObj[key]){
     				doCssSet(key,dataObj,fdDisplayCssObj,datas);
     			}
			}
    	}
    }
    function doChangeWhere(allField,fdDisplayCssObj){
    	var allFieldObj = {}; 
		for(var i = 0;i < allField.length;i++){
			allFieldObj[allField[i].field.split(".")[0]] = {
					text:allField[i].text,
					type:allField[i].type
			}
		}
    	for (var key in fdDisplayCssObj){
    		for(var i = 0;i < fdDisplayCssObj[key].length;i++){
    			if(fdDisplayCssObj[key][i].selected.type != allFieldObj[key].type){
    				//属性类型已改，where恢复默认 
    				fdDisplayCssObj[key][i].selected.type = allFieldObj[key].type;
					//where条件运算符和值恢复默认
					changeWhereData(fdDisplayCssObj[key][i].where,key,allFieldObj);
    			}
    			
    		}
    	}
    }
    
    function changeWhereData(whereArr,key,allFieldObj){
    	for(var j = 0;j < whereArr.length;j++){
			whereArr[j].expression = {};
			if(allFieldObj[key].type != "String"){
				whereArr[j].match = "eq";
			}else{
				whereArr[j].match = "=";
			}
		}
    }
    
    function doCssSet(key,dataObj,fdDisplayCssObj,datas){
    	for(var i = 0;i < dataObj[key].length;i++){
			var cssArr = fdDisplayCssObj[key];
			if(cssArr){
				doMatchAndCssSet(cssArr,dataObj,datas,key,i);
			}
		}
    }
    
    function doMatchAndCssSet(cssArr,dataObj,datas,key,i){
    	for(var j = 0;j < cssArr.length;j++){
			var isOk = true;
			var whereArr = cssArr[j].where;
			var whereType = cssArr[j].whereType.whereTypeValue;
			var fieldObj = cssArr[j].field;
			var fieldItemObj = cssArr[j].fieldItem;
			if(whereType == "2"){
				//无条件，都满足，无需匹配
				cssSet(fieldObj,dataObj[key][i],datas.datas,fieldItemObj);
			}else{
				if(whereArr.length == 0){
					//没配置查询条件
					isOk = false;
				}
				for(var m = 0;m < whereArr.length;m++){
					if(whereArr[m].expression){
						whereArr[m].expression.text = decodeURI(whereArr[m].expression.text);
						whereArr[m].expression.value = decodeURI(whereArr[m].expression.value);
						whereArr[m].name.value = decodeURI(whereArr[m].name.value);
						whereArr[m].name.type = decodeURI(whereArr[m].name.type);
						var value = whereArr[m].expression.text || "";
						var match = whereArr[m].match;
						var type = whereArr[m].type.value;
						var keyValueField = whereArr[m].name.value;
						//实际值
						var keyValue = dataObj[keyValueField][i].value;

						if(type == "4"){
							//解析公式定义器
							$.ajax({
								url: Com_Parameter.ContextPath + "sys/modeling/main/listview.do?method=parseFormula&modelId="+listOption.fdAppModelId+"&script="+value,
								async: false,
								success: function(data){
									if(data){
										value = data.value;
									}
								}
							});
						}
						if(whereType == "0"){
							if(type != "3" && value == ""){
								isOk = true;
							}else{
								if ("enum" === whereArr[m].name.type){
									var backisOk = matchEnumWhere(keyValue,match,value);
									if(backisOk == false){	//and查询有一个不匹配就都不满足
										isOk = false;
										break;
									}
								}else {
									var backisOk = matchWhere(keyValue, match, value);
									if (backisOk == false) {	//and查询有一个不匹配就都不满足
										isOk = false;
									}
								}
							}
						}else if(whereType == "1"){	//or查询匹配就满足
							if(type != "3" && value == ""){
								cssSet(fieldObj,dataObj[key][i],datas.datas,fieldItemObj);
							}else{
								//枚举类型的另外判断
								if ("enum" === whereArr[m].name.type){
									if(matchEnumWhere(keyValue,match,value)){
										cssSet(fieldObj,dataObj[key][i],datas.datas,fieldItemObj);
										break;
									}
								}else {
									if (matchWhere(keyValue, match, value)) {
										cssSet(fieldObj, dataObj[key][i], datas.datas, fieldItemObj);
										break;
									}
								}
							}
						}
					}
				}
				if(isOk && whereType == "0"){
					cssSet(fieldObj,dataObj[key][i],datas.datas,fieldItemObj);
				}
			}
		}
    }
	var cssData={};
    var rowCss = {};	//全局变量，用于存放显示项字段所在行的样式
    function cssSet(fieldObj,colDataObj,datas,fieldItemObj){
    	var row = colDataObj.x;
    	var col = colDataObj.y;
    	var name = datas[row][col].col;
    	var fontcolor = fieldObj.fontColor;
		var bgcolor = fieldObj.backgroundColor;
		var value = getKeyValue(datas[row],'fdId');
    	if(!value){
    		return;
		}
		cssData[value] = {};
    	cssData[value][name] = {
    		color:fontcolor,
			"background-color":bgcolor
    	};
		var ItemBackgroundColor = fieldItemObj.ItemBackgroundColor;
		var css = {
			backgroundColor: ItemBackgroundColor
		};
		rowCss[value] = css;
    }

    function getKeyValue(datas,key){
    	for(var i =0;i<datas.length;i++){
    		var data = datas[i];
    		if(key == data.col){
    			return data.value;
			}
		}
	}
    
  //显示项增加标记
  function AddTab(tab,datas,value,row,col){
	  var showPosition = tab.showPosition;
	  var tabType = tab.tabType;
	  
      var tabIcon = tab.defaultIcon;
	  var iconsize = tab.iconSize;
	  
	  var tabBackgroundColor = tab.tabBackgroundColor;
	  var tabContent = tab.tabContent;
	  var tabFontColor = tab.tabFontColor;
	  var tabFontSize = tab.tabFontSize;
	  if(tabType == "0"){	//自定义
		  if(showPosition == "0"){
			  datas[row][col].value = '<span style="color:'+tabFontColor+';background-color:'+tabBackgroundColor+';font-size:'+tabFontSize+'px">'+tabContent+'</span>'+value;
		  }else if(showPosition == "1"){
			  datas[row][col].value = value + '<span style="color:'+tabFontColor+';background-color:'+tabBackgroundColor+';font-size:'+tabFontSize+'px">'+tabContent+'</span>';
		  }
	  }else if(tabType == "1"){		//图标
		  if(showPosition == "0"){
			  datas[row][col].value = '<i style="font-size:'+iconsize+';color:black;" class="'+tabIcon+'"></i>'+value;
		  }else if(showPosition == "1"){
			  datas[row][col].value = value + '<i style="font-size:'+iconsize+';color:black;" class="'+tabIcon+'"></i>';
		  }
	  }
	  
  }
  function checkHtml(htmlStr) {
	    var  reg = /<[^>]+>/g;
	    return reg.test(htmlStr);
  }
    
  //显示项，条件匹配
  function matchWhere(keyValue,match,value){
	    if(checkHtml(keyValue)){
	    	if($(keyValue)[1]){
	    		keyValue = $($(keyValue)[1]).text();
	    	}else{
	    		//百分数
	    		keyValue = $($(keyValue)[0]).text();
	    	}
	    }
	    if(keyValue.indexOf("%") != -1){
	    	//百分数
	    	keyValue = (keyValue.replace("%",""))/100;
	    }
	    if(value.indexOf("%") != -1){
    		//后台配置是百分数
    		value = (value.replace("%",""))/100;
    	}
    	var isOk = false;
    	switch(match){
    		case "=" :
    			if(keyValue.trim() == value){
    				isOk = true;
    			}
    			break;
    		case "like" :
    			if(value == ""){
    				if(keyValue.trim() == value){
    					isOk = true;
    				}
    			}else{
    				if(keyValue.indexOf(value) != -1){
        				isOk = true;
        			}
    			}
    			break;
    		case "eq" :
				if(keyValue.trim() == ""){
					if(keyValue.trim() == value){
						isOk = true;
						break;
					}
				}
    			if(keyValue.indexOf(":") != -1 || keyValue.indexOf("-") != -1){
    				//日期类型
    				if(keyValue == value){
        				isOk = true;
        			}
    			}else{
    				if(parseFloat(keyValue) == parseFloat(value)){
        				isOk = true;
        			}
    			}
    			break;
    		case "lt" :
    			if(keyValue.indexOf(":") != -1 || keyValue.indexOf("-") != -1){
    				if(keyValue < value){
        				isOk = true;
        			}
    			}else{
    				if(parseFloat(keyValue) < parseFloat(value)){
        				isOk = true;
        			}
    			}
    			break;
    		case "le" :
    			if(keyValue.indexOf(":") != -1 || keyValue.indexOf("-") != -1){
    				if(keyValue <= value){
        				isOk = true;
        			}
    			}else{
    				if(parseFloat(keyValue) <= parseFloat(value)){
        				isOk = true;
        			}
    			}
    			break;
    		case "gt":
    			if(keyValue.indexOf(":") != -1 || keyValue.indexOf("-") != -1){
    				if(keyValue > value){
        				isOk = true;
        			}
    			}else{
    				if(parseFloat(keyValue) > parseFloat(value)){
        				isOk = true;
        			}
    			}
    			break;
    		case "ge":
    			if(keyValue.indexOf(":") != -1 || keyValue.indexOf("-") != -1){
    				if(keyValue >= value){
        				isOk = true;
        			}
    			}else{
    				if(parseFloat(keyValue) >= parseFloat(value)){
        				isOk = true;
        			}
    			}
    			break;
			case "!{notequal}":
				if(keyValue.trim() != value){
					isOk = true;
				}
				break;
    	}
    	return isOk;
   }
	function matchEnumWhere(keyValue,match,value){
		//实际值数组
		var keyValueArr=keyValue.split(';');
		//查询条件数组
		var valueArr=value.split(';');

		var isOk = false;
		var amount = 0;
		switch(match){
			case "=" :
				if(keyValue == ""){
					if(keyValue == value){
						isOk = true;
						break;
					}else {
						break;
					}
				}
				if(keyValueArr.length == valueArr.length){
					if (keyValueArr.length === 0){
						isOk = true;
					}else {
						for (var i = 0; i<keyValueArr.length;i++){
							var keyValueItem = keyValueArr[i];
							for (var j = 0;j<valueArr.length;j++){
								var valueItem = valueArr[j];
								if (keyValueItem === valueItem){
									amount +=1;
								}
							}
						}
						//数量一致且相同数量一样就是等于
						if (keyValueArr.length == amount){
							isOk = true;
						}
					}
				}
				break;
			case "!{notequal}":
				if(keyValueArr.length == valueArr.length){
					for (var i = 0; i<keyValueArr.length;i++){
						var keyValueItem = keyValueArr[i];
						for (var j = 0;j<valueArr.length;j++){
							var valueItem = valueArr[j];
							if (keyValueItem === valueItem){
								amount +=1;
							}
						}
					}
					//数量一致且相同数量和数量不一样就是不等于
					if (keyValueArr.length != amount){
						isOk = true;
					}
				}else {
					//长度不一致一定不等于
					isOk = true;
				}
				break;
			case "like" :
				if(value == ""){
					if(keyValue == value){
						isOk = true;
					}
				}else{
					for (var i = 0; i<valueArr.length;i++){
						var valueItem = valueArr[i];
						for (var j = 0;j<keyValueArr.length;j++){
							var keyValueItem = keyValueArr[j];
							if (keyValueItem === valueItem){
								isOk = true;
								break;
							}
						}
					}
				}
				break;
			case "!{notContain}":
				if(value == ""){
					if(keyValue != value){
						isOk = true;
					}
				}else {
					isOk = true;
					fi:for (var i = 0; i < valueArr.length; i++) {
						var valueItem = valueArr[i];
						for (var j = 0;j<keyValueArr.length;j++){
							var keyValueItem = keyValueArr[j];
							if (keyValueItem === valueItem){
								isOk = false;
								break fi;
							}
						}

					}

				}
				break;
		}
		return isOk;
	}

	window.displayCssSet = displayCssSet;
});