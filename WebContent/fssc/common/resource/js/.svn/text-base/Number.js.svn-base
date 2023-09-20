/**
 * 加法运算，避免数据相加小数点后产生多位数和计算精度损失。
 *
 * @param num1加数1 | num2加数2
 */
function numAdd(num1, num2) {
    var baseNum, baseNum1, baseNum2;
    try {
        baseNum1 = num1.toString().split(".")[1].length;
    } catch(e) {
        baseNum1 = 0;
    }
    try {
        baseNum2 = num2.toString().split(".")[1].length;
    } catch(e) {
        baseNum2 = 0;
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
    return (numMulti(num1,baseNum) + numMulti(num2,baseNum)) / baseNum;
};

/**
 * 加法运算，保留两位小数，若是要保留小数精度，可使用numAdd
 *
 * @param num1加数1 | num2加数2
 */
function addPoint(num1, num2) {
	var baseNum, baseNum1, baseNum2;
	try {
		baseNum1 = num1.toString().split(".")[1].length;
	} catch(e) {
		baseNum1 = 0;
	}
	try {
		baseNum2 = num2.toString().split(".")[1].length;
	} catch(e) {
		baseNum2 = 0;
	}
	baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
	return formatFloat(Number((num1 * baseNum + num2 * baseNum) / baseNum),2);
};
/**
 * 减法运算，避免数据相减小数点后产生多位数和计算精度损失。
 *
 * @param num1被减数  |  num2减数
 */
function numSub(num1, num2) {
    var baseNum, baseNum1, baseNum2;
    var precision; // 精度
    try {
        baseNum1 = num1.toString().split(".")[1].length;
    } catch(e) {
        baseNum1 = 0;
    }
    try {
        baseNum2 = num2.toString().split(".")[1].length;
    } catch(e) {
        baseNum2 = 0;
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
    precision = (baseNum1 >= baseNum2) ? baseNum1: baseNum2;
    return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision);
};
/**
 * 减法运算，保留两位小数
 *
 * @param num1被减数  |  num2减数
 */
function subPoint(num1, num2) {
	var baseNum, baseNum1, baseNum2;
	try {
		baseNum1 = num1.toString().split(".")[1].length;
	} catch(e) {
		baseNum1 = 0;
	}
	try {
		baseNum2 = num2.toString().split(".")[1].length;
	} catch(e) {
		baseNum2 = 0;
	}
	baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
	return formatFloat(((num1 * baseNum - num2 * baseNum) / baseNum),2);
};
/**
 * 乘法运算，避免数据相乘小数点后产生多位数和计算精度损失。
 *
 * @param num1被乘数 | num2乘数
 */
function numMulti(num1, num2) {
    var baseNum = 0;
    try {
        baseNum += num1.toString().split(".")[1].length;
    } catch(e) {

}
    try {
        baseNum += num2.toString().split(".")[1].length;
    } catch(e) {

}
    return Number(num1.toString().replace(".", "")) * Number(num2.toString().replace(".", "")) / Math.pow(10, baseNum);
};
/**
 * 乘法运算，保留两位小数
 *
 * @param num1被乘数 | num2乘数
 */
function multiPoint(num1, num2) {
	var baseNum = 0;
	try {
		baseNum += num1.toString().split(".")[1].length;
	} catch(e) {
		
	}
	try {
		baseNum += num2.toString().split(".")[1].length;
	} catch(e) {
		
	}
	return formatFloat(Number(Number(num1.toString().replace(".", "")) * Number(num2.toString().replace(".", "")) / Math.pow(10, baseNum)),2);
};
/**
 * 除法运算，避免数据相除小数点后产生多位数和计算精度损失。
 *
 * @param num1被除数 | num2除数
 */
function numDiv(num1, num2) {
    var baseNum1 = 0,
    baseNum2 = 0;
    var baseNum3, baseNum4;
    try {
        baseNum1 = num1.toString().split(".")[1].length;
    } catch(e) {
        baseNum1 = 0;
    }
    try {
        baseNum2 = num2.toString().split(".")[1].length;
    } catch(e) {
        baseNum2 = 0;
    }
    with(Math) {
        baseNum3 = Number(num1.toString().replace(".", ""));
        baseNum4 = Number(num2.toString().replace(".", ""));
        return (baseNum3 / baseNum4) * pow(10, baseNum2 - baseNum1);
    }
};
/**
 * 除法运算，保留两位小数
 *
 * @param num1被除数 | num2除数
 */
function divPoint(num1, num2) {
	var baseNum1 = 0,
	baseNum2 = 0;
	var baseNum3, baseNum4;
	try {
		baseNum1 = num1.toString().split(".")[1].length;
	} catch(e) {
		baseNum1 = 0;
	}
	try {
		baseNum2 = num2.toString().split(".")[1].length;
	} catch(e) {
		baseNum2 = 0;
	}
	with(Math) {
		baseNum3 = Number(num1.toString().replace(".", ""));
		baseNum4 = Number(num2.toString().replace(".", ""));
		return formatFloat(Number((baseNum3 / baseNum4) * pow(10, baseNum2 - baseNum1)),2);
	}
};

/**
 * 格式化小数，保留小数位，四舍五入
 *
 * @param num：需要格式化的小数 | pos：需要保留的小数位
 */
function formatFloat(num,pos){
	return (Math.round(num*Math.pow(10, pos))/Math.pow(10, pos)).toFixed(pos);
}

/**
 * 将科学技术法转换为数字，保留两位小数，四舍五入
 *
 * @param num：需要格式化的小数 | pos：需要保留的小数位
 */
function formatScientificToNum(num,pos){
	return formatFloat(new Number(num),pos);
}

//是否为数字
function isNumber(v){
	return (!isNaN(v) && !/^\s+$/.test(v)&& /^.{1,20}$/.test(v) && /(\.)?\d$/.test(v) );
}

//是否为整数
function isDigits(v){
	return  /^-?\d+$/.test(v);
}

//是否为货币金额
function isCurrencyDollar(v){
	return  /^\$?\-?([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/.test(v);
}
	/*********************************************
	 * 将数字金额转换为大写金额,isChinese传no则不包含大写两个字，否则包含
	 *********************************************/
	window.FSSC_MenoyToUppercase = function(money,isChinese) {
		//汉字的数字
		var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');
		//基本单位
		var cnIntRadice = new Array('', '拾', '佰', '仟');
		//对应整数部分扩展单位
		var cnIntUnits = new Array('', '万', '亿', '兆');
		//对应小数部分单位
		var cnDecUnits = new Array('角', '分', '毫', '厘');
		//整数金额时后面跟的字符
		var cnInteger = '整';
		//整数完以后的单位
		var cnIntLast = '元';
		//最大处理的数字
		var maxNum = 999999999999999.9999;
		var integerNum;	 //金额整数部分
		var decimalNum;	 //金额小数部分
		//输出的中文金额字符串
		var chineseStr = '';
		var parts;		//分离金额后用的数组，预定义
		if (!money) { 
			return ''; 
		}
		if((money+'').indexOf(',')>-1){  //千分位，则替换掉
			money=(money+'').replace(',','');
		}
		money = parseFloat(money);
		if (money >= maxNum) {
			//超出最大处理数字
			return '超出最大处理数字';
		}
		if (money == 0) {
			chineseStr = cnNums[0] + cnIntLast + cnInteger;
			return chineseStr;
		}

		//四舍五入保留两位小数,转换为字符串
		money = Math.round(money * 100).toString();
		integerNum = money.substr(0,money.length-2);
		decimalNum = money.substr(money.length-2);

		//获取整型部分转换
		if (parseInt(integerNum, 10) > 0) {
			var zeroCount = 0;
			var IntLen = integerNum.length;
			for (var i = 0; i < IntLen; i++) {
				var n = integerNum.substr(i, 1);
				var p = IntLen - i - 1;
				var q = p / 4;
				var m = p % 4;
				if (n == '0') {
					zeroCount++;
				} else {
					if (zeroCount > 0) {
						chineseStr += cnNums[0];
					}
					//归零
					zeroCount = 0;
					chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
				}
				if (m == 0 && zeroCount < 4) {
					chineseStr += cnIntUnits[q];
				}
			}
			chineseStr += cnIntLast;
		}
		//小数部分
		if (decimalNum != '') {
			var decLen = decimalNum.length;
			if(decLen<2){
				decimalNum="0"+decimalNum;
				decLen=2; //说明小数位两位
			}
			for (var i = 0; i < decLen; i++) {
				var n = decimalNum.substr(i, 1);
				if (n != '0') {
					chineseStr += cnNums[Number(n)] + cnDecUnits[i];
				}
			}
		}
		if (chineseStr == '') {
			chineseStr += cnNums[0] + cnIntLast + cnInteger;
		} else if (decimalNum == '' || /^0*$/.test(decimalNum)) {
			chineseStr += cnInteger;
		}
		if(isChinese&&"no"==isChinese){
			return chineseStr;
		}
		return '大写:'+chineseStr;
	}
/***********************************************
 功能：  对输入数字的整数部分插入千位分隔符 以及结尾两位小数
 ***********************************************/
function format(formatV){
	if(formatV){
		formatV=formatV.toString();
	}else{
		return "";
	}
	var array=new Array();
	array=formatV.split(".");

	var re=/(-?\d+)(\d{3})/;
	var tempSp = ".";
	while(re.test(array[0])){
		array[0]=array[0].replace(re,"$1,$2");
	}

	var returnV=array[0];
	for(var i=1;i<array.length;i++){
		tempSp += array[i].length == 1 ? array[i] + "0" : array[i] ;
		returnV+= tempSp;
	}
	returnV = (returnV == "NaN" || returnV == "") ? "" : (returnV.indexOf(".") != -1 ? returnV : returnV + ".00");
	return returnV;
}


/***********************************************
 功能：  过滤掉数据的千位分隔符
 ***********************************************/
function unformat(v){
	if(v){
		v=v.toString();
	}else{
		return "";
	}
	var x = v.split(',');
	var returnV=parseFloat(x.join(""));
	return returnV;
}

