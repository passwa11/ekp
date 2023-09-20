define(["dijit/registry", "mui/dialog/Tip",  "dojo/query", "dojo/dom-class"], function(registry, Tip, query,domClass) {
	var hexcase=0;function hex_md5(a){return rstr2hex(rstr_md5(str2rstr_utf8(a)))}function hex_hmac_md5(a,b){return rstr2hex(rstr_hmac_md5(str2rstr_utf8(a),str2rstr_utf8(b)))}function md5_vm_test(){return hex_md5("abc").toLowerCase()=="900150983cd24fb0d6963f7d28e17f72"}function rstr_md5(a){return binl2rstr(binl_md5(rstr2binl(a),a.length*8))}function rstr_hmac_md5(c,f){var e=rstr2binl(c);if(e.length>16){e=binl_md5(e,c.length*8)}var a=Array(16),d=Array(16);for(var b=0;b<16;b++){a[b]=e[b]^909522486;d[b]=e[b]^1549556828}var g=binl_md5(a.concat(rstr2binl(f)),512+f.length*8);return binl2rstr(binl_md5(d.concat(g),512+128))}function rstr2hex(c){try{hexcase}catch(g){hexcase=0}var f=hexcase?"0123456789ABCDEF":"0123456789abcdef";var b="";var a;for(var d=0;d<c.length;d++){a=c.charCodeAt(d);b+=f.charAt((a>>>4)&15)+f.charAt(a&15)}return b}function str2rstr_utf8(c){var b="";var d=-1;var a,e;while(++d<c.length){a=c.charCodeAt(d);e=d+1<c.length?c.charCodeAt(d+1):0;if(55296<=a&&a<=56319&&56320<=e&&e<=57343){a=65536+((a&1023)<<10)+(e&1023);d++}if(a<=127){b+=String.fromCharCode(a)}else{if(a<=2047){b+=String.fromCharCode(192|((a>>>6)&31),128|(a&63))}else{if(a<=65535){b+=String.fromCharCode(224|((a>>>12)&15),128|((a>>>6)&63),128|(a&63))}else{if(a<=2097151){b+=String.fromCharCode(240|((a>>>18)&7),128|((a>>>12)&63),128|((a>>>6)&63),128|(a&63))}}}}}return b}function rstr2binl(b){var a=Array(b.length>>2);for(var c=0;c<a.length;c++){a[c]=0}for(var c=0;c<b.length*8;c+=8){a[c>>5]|=(b.charCodeAt(c/8)&255)<<(c%32)}return a}function binl2rstr(b){var a="";for(var c=0;c<b.length*32;c+=8){a+=String.fromCharCode((b[c>>5]>>>(c%32))&255)}return a}function binl_md5(p,k){p[k>>5]|=128<<((k)%32);p[(((k+64)>>>9)<<4)+14]=k;var o=1732584193;var n=-271733879;var m=-1732584194;var l=271733878;for(var g=0;g<p.length;g+=16){var j=o;var h=n;var f=m;var e=l;o=md5_ff(o,n,m,l,p[g+0],7,-680876936);l=md5_ff(l,o,n,m,p[g+1],12,-389564586);m=md5_ff(m,l,o,n,p[g+2],17,606105819);n=md5_ff(n,m,l,o,p[g+3],22,-1044525330);o=md5_ff(o,n,m,l,p[g+4],7,-176418897);l=md5_ff(l,o,n,m,p[g+5],12,1200080426);m=md5_ff(m,l,o,n,p[g+6],17,-1473231341);n=md5_ff(n,m,l,o,p[g+7],22,-45705983);o=md5_ff(o,n,m,l,p[g+8],7,1770035416);l=md5_ff(l,o,n,m,p[g+9],12,-1958414417);m=md5_ff(m,l,o,n,p[g+10],17,-42063);n=md5_ff(n,m,l,o,p[g+11],22,-1990404162);o=md5_ff(o,n,m,l,p[g+12],7,1804603682);l=md5_ff(l,o,n,m,p[g+13],12,-40341101);m=md5_ff(m,l,o,n,p[g+14],17,-1502002290);n=md5_ff(n,m,l,o,p[g+15],22,1236535329);o=md5_gg(o,n,m,l,p[g+1],5,-165796510);l=md5_gg(l,o,n,m,p[g+6],9,-1069501632);m=md5_gg(m,l,o,n,p[g+11],14,643717713);n=md5_gg(n,m,l,o,p[g+0],20,-373897302);o=md5_gg(o,n,m,l,p[g+5],5,-701558691);l=md5_gg(l,o,n,m,p[g+10],9,38016083);m=md5_gg(m,l,o,n,p[g+15],14,-660478335);n=md5_gg(n,m,l,o,p[g+4],20,-405537848);o=md5_gg(o,n,m,l,p[g+9],5,568446438);l=md5_gg(l,o,n,m,p[g+14],9,-1019803690);m=md5_gg(m,l,o,n,p[g+3],14,-187363961);n=md5_gg(n,m,l,o,p[g+8],20,1163531501);o=md5_gg(o,n,m,l,p[g+13],5,-1444681467);l=md5_gg(l,o,n,m,p[g+2],9,-51403784);m=md5_gg(m,l,o,n,p[g+7],14,1735328473);n=md5_gg(n,m,l,o,p[g+12],20,-1926607734);o=md5_hh(o,n,m,l,p[g+5],4,-378558);l=md5_hh(l,o,n,m,p[g+8],11,-2022574463);m=md5_hh(m,l,o,n,p[g+11],16,1839030562);n=md5_hh(n,m,l,o,p[g+14],23,-35309556);o=md5_hh(o,n,m,l,p[g+1],4,-1530992060);l=md5_hh(l,o,n,m,p[g+4],11,1272893353);m=md5_hh(m,l,o,n,p[g+7],16,-155497632);n=md5_hh(n,m,l,o,p[g+10],23,-1094730640);o=md5_hh(o,n,m,l,p[g+13],4,681279174);l=md5_hh(l,o,n,m,p[g+0],11,-358537222);m=md5_hh(m,l,o,n,p[g+3],16,-722521979);n=md5_hh(n,m,l,o,p[g+6],23,76029189);o=md5_hh(o,n,m,l,p[g+9],4,-640364487);l=md5_hh(l,o,n,m,p[g+12],11,-421815835);m=md5_hh(m,l,o,n,p[g+15],16,530742520);n=md5_hh(n,m,l,o,p[g+2],23,-995338651);o=md5_ii(o,n,m,l,p[g+0],6,-198630844);l=md5_ii(l,o,n,m,p[g+7],10,1126891415);m=md5_ii(m,l,o,n,p[g+14],15,-1416354905);n=md5_ii(n,m,l,o,p[g+5],21,-57434055);o=md5_ii(o,n,m,l,p[g+12],6,1700485571);l=md5_ii(l,o,n,m,p[g+3],10,-1894986606);m=md5_ii(m,l,o,n,p[g+10],15,-1051523);n=md5_ii(n,m,l,o,p[g+1],21,-2054922799);o=md5_ii(o,n,m,l,p[g+8],6,1873313359);l=md5_ii(l,o,n,m,p[g+15],10,-30611744);m=md5_ii(m,l,o,n,p[g+6],15,-1560198380);n=md5_ii(n,m,l,o,p[g+13],21,1309151649);o=md5_ii(o,n,m,l,p[g+4],6,-145523070);l=md5_ii(l,o,n,m,p[g+11],10,-1120210379);m=md5_ii(m,l,o,n,p[g+2],15,718787259);n=md5_ii(n,m,l,o,p[g+9],21,-343485551);o=safe_add(o,j);n=safe_add(n,h);m=safe_add(m,f);l=safe_add(l,e)}return Array(o,n,m,l)}function md5_cmn(h,e,d,c,g,f){return safe_add(bit_rol(safe_add(safe_add(e,h),safe_add(c,f)),g),d)}function md5_ff(g,f,k,j,e,i,h){return md5_cmn((f&k)|((~f)&j),g,f,e,i,h)}function md5_gg(g,f,k,j,e,i,h){return md5_cmn((f&j)|(k&(~j)),g,f,e,i,h)}function md5_hh(g,f,k,j,e,i,h){return md5_cmn(f^k^j,g,f,e,i,h)}function md5_ii(g,f,k,j,e,i,h){return md5_cmn(k^(f|(~j)),g,f,e,i,h)}function safe_add(a,d){var c=(a&65535)+(d&65535);var b=(a>>16)+(d>>16)+(c>>16);return(b<<16)|(c&65535)}function bit_rol(a,b){return(a<<b)|(a>>>(32-b))};
	return {
		parseXformName: function(wgt) {
			var name = wgt.get("name");
			if(name == null || name == ''){
				return null;
			}
			var sIndex = name.indexOf('.value(');
			if (sIndex >-1) {
				sIndex = sIndex + 7;
			}else{
				sIndex = 0;
			}
			var eIndex = name.lastIndexOf(')');
			if(eIndex>-1 && (eIndex+1)==name.length){
				eIndex = eIndex;
			}else{
				eIndex = name.length;
			}
			name = name.substring(sIndex, eIndex);
			name = name.replace(/\.id/gi,"");
			name = name.replace(/\.name/gi,"");
			var dIndex = name.lastIndexOf('.');
			if (dIndex > -1) {		//参数在明细表中
				var indexs = name.match(/\.(\d+)\./g);
				indexs = indexs?indexs:[];
				if(indexs.length>0)
					name = name.replace(indexs[0],".");
			}
			return name;
		},
		
		getCalculationRowNo:function(name){
			if(name == null || name == ''){
				return null;
			}
			var sIndex = name.indexOf('.value(');
			if (sIndex >-1) {
				sIndex = sIndex + 7;
			}else{
				sIndex = 0;
			}
			var eIndex = name.lastIndexOf(')');
			if(eIndex>-1 && (eIndex+1)==name.length){
				eIndex = eIndex;
			}else{
				eIndex = name.length;
			}
			name = name.substring(sIndex, eIndex);
			name = name.replace(/\.id/gi,"");
			name = name.replace(/\.name/gi,"");
			var dIndex = name.lastIndexOf('.');
			if (dIndex > -1) {		//参数在明细表中
				var indexs = name.match(/\.(\d+)\./g);
				indexs = indexs?indexs:[];
				if(indexs.length>0)
					var rowNo = indexs[0].substring(1,indexs[0].lastIndexOf("."));
					return rowNo;
			}
			return null;
		},
		parseName:function(wgt){
				var valueField = wgt.get("name");
				if(valueField == null || valueField == ""){
					return null;
				}
				var sIndex = valueField.indexOf(".value(");
				if (sIndex >-1) {
					sIndex = sIndex + 7;
				}else{
					sIndex = 0;
				}
				var eIndex = valueField.lastIndexOf(')');
				if(eIndex>-1 && (eIndex+1)==valueField.length){
					eIndex = eIndex;
				}else{
					eIndex = valueField.length;
				}
				var tmpName = valueField.substring(sIndex, eIndex);
				tmpName = tmpName.replace(/\.id/gi,"");
				tmpName = tmpName.replace(/\.name/gi,"");
				return tmpName;
		},
		
		// 判断控件是否在明细表里面，简单的控件可以直接通过name来判断，但是明细表统计行里面的不行
		isInDetail : function(wgt){
			return this.hasParentClass(wgt,"detailTableContent");
		},
		
		// 判断控件是否在明细表里面，简单的控件可以直接通过name来判断，但是明细表统计行里面的不行
		isInStatisticTr : function(wgt){
			return this.hasParentClass(wgt,"detail_statistic");
		},
		
		// wgt 的父元素是否含有某className
		hasParentClass : function(wgt,className){
			var domNode = wgt.domNode;
			if(domNode){
				var parent = domNode.parentNode;
				while(parent){
					if(domClass.contains(parent,className)){
						return true;
					}
					parent = parent.parentNode;
				}
			}
			return false;
		},
		
		isGroupMemWidget:function(wgtName){
			if(wgtName!=null && wgtName!='' ){
				var extPrified = ["_single","_group"];
				for (var i=0; i<extPrified.length;i++ ) {
					var tmpStr = extPrified[i];
					if(wgtName.indexOf(tmpStr)>-1 && (wgtName.indexOf(tmpStr) + tmpStr.length==wgtName.length)){
						return true;
					}
				}
			}
			return false;
		},
		//mul 是否多个， val 是否取值, blur 模糊查找， isFilterIndex 是否过滤明细表的索引
		_getXformWidget:function(areaDom, xformId, mul, val, blur, isFilterIndex){
            var param ='';
            var tempXformId = xformId;
            if(/-fd(\w+)/g.test(xformId)){
                param = xformId.match(/-fd(\w+)/g)[0].replace("-","");
                xformId = xformId.match(/(\S+)-/g)[0].replace("-","");
                tempXformId = tempXformId.match(/(\S+)-/g)[0].replace("-",")");
            }
            blur = blur!=null?blur:true;
            if(!areaDom)
                areaDom = document.forms[0];
            var domArr = query('[widgetid]', areaDom);
            var wgts = [];
            if(domArr.length>0){
                for ( var i = 0; i < domArr.length; i++) {
                    var tmpWgt = registry.byNode(domArr[i]);
                    var result = false;
                    if(blur){
                        var tmpName;
                        if(typeof(isFilterIndex) != 'undefined' && isFilterIndex == false){
                            tmpName = this.parseName(tmpWgt);
                        }else{
                            tmpName = this.parseXformName(tmpWgt);
                        }
                        if(tmpName!=null && !this.isGroupMemWidget(tmpWgt.get('name'))){
                            result = tmpName.indexOf(xformId)>-1;
                            //动态下拉联动,传入参数为显示值,则xformId为xxxxx_text,而tmpName为xxxxx
                            var isText = false;
                            if (/extendDataFormInfo.value/ig.test(tempXformId)) {
                                xformId = /\((\S+)\)/ig.exec(tempXformId)[1];
                            }
                            if (!result && /_text/.test(xformId) && (xformId.length - "_text".length == xformId.indexOf("_text"))){//需要保证_text是在结尾
                                isText = true;
                                var _tmp = /(\S+)_text/.exec(xformId)[1];
                                result = tmpName.indexOf(_tmp)>-1;
                            }
                        }
                    }else{
                        var name  =  tmpWgt.get('name');
                        result = (name!=null && name!='' && name.indexOf(xformId)>-1 && !this.isGroupMemWidget(name))?true:false;
                        //动态下拉联动,传入参数为显示值,则xformId为xxxxx_text,而tmpName为xxxxx
                        var isText = false;
                        if (/extendDataFormInfo.value/ig.test(tempXformId)) {
                            xformId = /\((\S+)\)/ig.exec(tempXformId)[1];
                        }
                        if (!result && /_text/.test(xformId) && (xformId.length - "_text".length == xformId.indexOf("_text"))){//需要保证_text是在结尾
                            isText = true;
                            var _tmp = /(\S+)_text/.exec(xformId)[1];
                            result = name && name.indexOf(_tmp)>-1;
                        }
                    }
                    if(result){
                        if(!val){
                            wgts.push(tmpWgt);
                        }else{
                            if(tmpWgt.get("value")!=null && tmpWgt.get("value")!=''){
                                if(param&&/Name/g.test(param)){
                                    wgts.push(tmpWgt.get("curNames"));
                                }else if(isText){
                                    wgts.push(tmpWgt.get("text"));
                                }
                                else{
                                	var value = tmpWgt.get("value");
                                	if('number' == tmpWgt.get("datatype")){
                                      //去除千分位
										if(value.indexOf(",") > -1){
											value = value.replace(/[,]/g,"");
										}
										//去除百分比
										if(value.indexOf("%") > -1){
											value = value.replace(/%/,"");
										}
									}
                                    wgts.push(value);
                                }
                            }
                        }
                        if(!mul){
                            break;
                        }
                    }
                }
            }
            if(!mul){
                return wgts.length>0?wgts[0]:null;
            }else{
                return wgts;
            }
		},
		
		getXformWidgetBlur:function(areaDom, xformId){
			return this._getXformWidget(areaDom, xformId, false, false, true);
		},
		
		getXformWidgetsBlur:function(areaDom, xformId){
			return this._getXformWidget(areaDom, xformId, true, false, true);
		},
		
		getXformWidgetsNoFilIndex:function(areaDom, xformId){
			return this._getXformWidget(areaDom, xformId, true, false, true, false);
		},
		
		getXformWidgetsFilIndex:function(areaDom, xformId){
			return this._getXformWidget(areaDom, xformId, true, false, true, true);
		},
		
		getXformWidget:function(areaDom, xformId){
			return this._getXformWidget(areaDom, xformId, false, false, false);
		},
		
		getXformWidgets:function(areaDom, xformId){
			return this._getXformWidget(areaDom, xformId, true, false, false);
		},
		
		getXformWidgetValues:function(areaDom, xformId, blur){
			return this._getXformWidget(areaDom, xformId, true, true, (blur!=null?blur:true));
		},
		
		toMD5:function(s){
			 return hex_md5(s);
		},
		
		buildInputParams:function(controlName,inputsJSON,isInit){
			var data = [];
			//构建输入参数
			for (var uuid in inputsJSON) {
				var formId = inputsJSON[uuid].fieldIdForm;
				if(!formId){
					continue;
				}
				var required = inputsJSON[uuid]._required;
				var formName = inputsJSON[uuid].fieldNameForm;
				var fieldType = inputsJSON[uuid].fieldTypeForm || "";
				formId = formId.replace(/\$/g, "");
				var val = null;
				var indetail=false;
				if(formId && formId.indexOf("fixed_") == 0){
					//固定值
					val=[formName];
				}else if(formId.indexOf(".")>=0){    //参数在明细表内 
					var indexs = controlName.match(/\.(\d+)\./g);
					indexs = indexs?indexs:[];
					var detailFromId=formId.split(".")[0];
					if(indexs.length>0 && controlName.indexOf(detailFromId)>=0){//控件在明细表内,并且是在相同的明细表 取同行控件的值
						formId = formId.replace(".",indexs[0]);
						val = this.getXformWidgetValues(query("#TABLE_DL_" + detailFromId)[0], formId ,false);
						indetail = true;
					}else{ 														//参数在其他其他明细表中 去所有行的数据
						var fieldId = formId.split(".")[1];
						val = this.getXformWidgetValues(query("#TABLE_DL_" + detailFromId)[0], fieldId);
					}
				}else{
					//获取字段的值
					val = this.getXformWidgetValues(null, formId);
					if(val!=null && val.length==0 && formId=="fdId"){
						var arr = [];
						//#106292 移动端表单映射没有参数,直接获取隐藏id域的值
						arr.push($("input[type=hidden][name=fdId]")[0].value);
						val = arr;
					}
				}
				if(val!=null && val.length==0){
					if(required=="1"){
						if(!isInit){
							Tip.fail({text:formName+" 作为该执行条件时不能为空"});
						}
						return null;
					}
					 data.push({
						"uuId" : uuid,
						"fieldIdForm" : formId,
						"indetail":indetail,
						"fieldValueForm" : "",
						"fieldTypeForm" : fieldType
					});
				}else{
					var isAllNull=true;
					for(var i=0;i<val.length;i++){		//是否所有值为空
						if(val[i]){
							isAllNull=false;
						}
						data.push({
							"uuId" : uuid,
							"fieldIdForm" : formId,
							"indetail":indetail,
							"fieldValueForm" : val[i],
							"fieldTypeForm" : fieldType
						});
					}
					if(isAllNull && required=="1"){
						Tip.fail({text:formName+" 作为该执行条件时不能为空"});
						return null;
					}
				}
			}
			return data;
		},
		
		matchTime:function(value){
			var reg = /^(20|21|22|23|[0-1]\d):[0-5]\d$/;
			var reg1 = /^(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;
			var regExp = new RegExp(reg);
			var regExp1 = new RegExp(reg1);
			if(regExp.test(value) || regExp1.test(value)){
			　　return true;
			}
			return false;
		},
		
		matchDate:function(value){
			//年月日
			var reg = /^[1-9]\d{3}\/(0[1-9]|1[0-2])\/(0[1-9]|[1-2][0-9]|3[0-1])$/;
			//年月
			var reg1 = /^[1-9]\d{3}\/(0[1-9]|1[0-2])$/;
			//月日
			var reg2 = /^(0[1-9]|1[0-2])\/(0[1-9]|[1-2][0-9]|3[0-1])$/;
			var regExp = new RegExp(reg);
			var regExp1 = new RegExp(reg1);
			var regExp2 = new RegExp(reg2);
			var isDate = true;
			if(!regExp.test(value) && !regExp1.test(value) && !regExp2.test(value)){
			　　return false;
			}
			return true;
		},
		
		formatDateVal:function(wgt,value) {
			var type = wgt.type;
			var isDate = (type === "datetime" || type === "date" || type === "time");
			if (isDate) {
				if(value.match(RegExp(/-/ig))){
					//替换-（如：2021-10-31）
					value = value.replace(/-/ig,"/");
				}else if(value.match(RegExp(/[年月日]/g))){
					//替换年月日（如：2021年10月31日）
					value = value.replace(/[年月]/g,"/");
					value = value.replace(/[日]/g,"");
				}else if(value.match(RegExp(/:/g))||value.match(RegExp(/\//g))){

				}else if(value && value.length!=0  && value.length<10) { //#153537 value没值的情况下会出现”//“，有值才进
					//替换字符串（如：20211031 ）
					value = value.substring(0,4) + '/' +  value.substring(4,6) +'/' + value.substring(6,8);
				}else if(value && typeof value == "string" && value.length>10 && /^[0-9]*$/.test(value)){
					value = new Date(parseInt(value));
				}
				var dimensions = "";
				if(wgt && wgt.dimension){
					if(wgt.dimension == "yearMonthDay"){
						dimensions = "";
					}else{
						dimensions=wgt.dimension;
					}
				}
				//判断数据类型

				if(this.matchDate(value)){//日期格式
					//若是日期类型，需要拼接时间格式
					value = value+" 00:00:00";//这里是直接构建时间的格式，然后再通过Date对象
				}else if(this.matchTime(value)){
					//若是时间类型，需要拼接日期格式
					var curDate = new Date();
					var valueArr = value.split(":");
					if(valueArr.length == 2){
						value = curDate.setHours(valueArr[0],valueArr[1]);
					}
					if(valueArr.length>2){
						value = curDate.setHours(valueArr[0],valueArr[1],valueArr[2]);
					}
				}
				if(type){
					type = type.replace("[]","");
					if(value&&value.length!=0){
						if (this.isIOS()) {
							//value = value.replace(/-/g, "/");
							if (typeof value == "string" && value.indexOf(".") > -1) {
								value = value.substring(0, value.indexOf("."));
							}
						}
						value = new Date(value);
						value = window.$form.str(value,type);
					}
				}
			}
			return value;
		},

        isIOS : function(){
            var ua = navigator.userAgent.toLowerCase();
            if (/(iPhone|iPad|iPod|iOS)/i.test(ua)) {
                return true;
            }
            return false;
        },
		
		setXformWidgetValues : function(wgt,val,prop){
			if(wgt){
				var fieldId = prop;
				// 处理地址本下的id和name的情况
				if(/-fd(\w+)/g.test(fieldId)){
					var param ='';
					param = fieldId.match(/-fd(\w+)/g)[0].replace("-fd","").toLowerCase();
					fieldId = fieldId.match(/(\S+)-/g)[0].replace("-",".") + param;
				}
				if(wgt.declaredClass == "mui.form.Address"
					|| wgt.declaredClass == "sys.xform.mobile.controls.NewAddress"){
					if(val instanceof Array){
						wgt.set("curIds",val[0]);
						wgt.set("curNames",(val[1]!=null?val[1]:val[0]));
					}else if(fieldId){
						if(fieldId.indexOf('.id') > -1){
							wgt.set("curIds",val);
							// setcurIds的时候，没有构建视图，不想改category的代码，手动在这里调用
							wgt.buildValue(wgt.cateFieldShow);
						}else if(fieldId.indexOf('.name') > -1){
							wgt.set("curNames",val);
						}
					}
				}else if(wgt.declaredClass == "mui.form.RadioGroup"){
					// 单选
					//获取dataType
					var dataType = wgt.domNode.getAttribute("datatype");
					var childrenArray = wgt.getChildren();
					for(var i = 0;i < childrenArray.length;i++){
						if((childrenArray[i].value == val || (dataType && dataType == "Double" && parseFloat(childrenArray[i].value) == parseFloat(val))) && childrenArray[i]._onClick){
							childrenArray[i]._onClick();
						}
					}
				}else if(wgt.declaredClass == "mui.form.CheckBoxGroup"){
					var childrenArray = wgt.getChildren();
					// 多选
					var valArray = val.split(";");
					// 遍历子组件
					for(var i = 0;i < childrenArray.length;i++){
						if(childrenArray[i]._onClick){
							if(childrenArray[i].value && valArray.indexOf(childrenArray[i].value) > -1){
								// 此处设置相反，在_onClick的方法里面会取反
								childrenArray[i].checked = false;
							}else{
								childrenArray[i].checked = true;
							}
							childrenArray[i]._onClick();
						}
					}
				}else if(wgt.declaredClass == "mui.form.Select"){
					//下拉控件单选
					var _val = val;
					if (!wgt.mul){
						// 多个传入值
						var valArray = val.split(";");
						if (valArray.length > 1){
							_val = "";
						}
					}
					if(wgt.textName && prop.indexOf('_text') > -1 && wgt.textName.indexOf(prop) > -1){
						wgt.set('text',_val);
					}else{
						wgt.set("value",_val);
					}
				}
				else if(wgt.textName && prop.indexOf('_text') > -1 && wgt.textName.indexOf(prop) > -1){
					wgt.set('text',val);
					if (wgt.declaredClass == "sys.xform.mobile.controls.RelationChoose") {
						wgt.buildTextItem({});
					}
				}else{
					val = this.formatDateVal(wgt, val);
					wgt.set("value",val);
				}
			}
		},
	};
});
