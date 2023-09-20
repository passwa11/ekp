/*压缩类型：标准*/
/*******************************************************************************
 * JS文件说明： 本JS文件中的函数不提供给普通模块调用。 本JS文件提供了对某个字段加密或对某个字段Base64的处理
 * 
 * 作者：叶中奇 版本：1.0 2006-4-3
 ******************************************************************************/
Com_RegisterFile("security.js");
Com_IncludeFile("aes.js");
//Com_IncludeFile("des.js");
Com_IncludeFile("base64.js");
Com_IncludeFile("sm2.js");
var SECURITYKEY = {
	toHexString : function(str){
		var temp = "";
		for(i=0;i<str.length;i++){
			temp += str.charCodeAt(i).toString(16);
		}
		return temp;
	},
	_getSessionId:function(){
		$.ajax({
			async : false,
			dataType : "script",
			url : Com_Parameter.ResPath+"js/session.jsp?_="+new Date().getTime()
		});
		var str = "";
		if(window.getSessionId){
			str = getSessionId();
		}
		return str;
	},
	supportEncodings:function(){
		return ["aes" , "des", "sm2"];
	},
	get: function(encodeType){
		var str = SECURITYKEY._getSessionId();
		var key = {};
		if(encodeType==null || encodeType=='aes'){
			if (str.length < 32) {
				str += "abcdefghijklmnopqrstuvwxyz1234567890";
			}
			str = str.toUpperCase();
			key.key = str.substring(0, 16);
			key.iv = str.substring(16, 32);
			key.security = "\u4435\u5320\u4d35";
		}else if(encodeType=='sm2'){
            key.SM2PubKey = window.getSM2PubKey();
            key.security = "\u534d\u3220\u4d45";
		}else{
			if (str.length < 16) {
				str += "abcdefghijklmnopqrstuvwxyz";
			}
			str = str.toUpperCase();
			key.key = SECURITYKEY.toHexString(str.substring(0, 8));
			key.iv = SECURITYKEY.toHexString(str.substring(8, 16)); 
			key.security = "\u4445\u5320\u4d45";
		}
		return key;
	},
	getCookie : function (c_name){
		if (document.cookie.length>0) {
			var cookies = document.cookie.split(";");
			for(i = 0; i < cookies.length; i++) {
				 var xc = cookies[i];
				 var cn = xc.substring(0, xc.indexOf("=")).toUpperCase(); 
				  cn = cn.replace(/^\s*/,"").replace(/\s*$/,"");  
				 if (cn == c_name) {
					 return unescape(xc.substring(xc.indexOf("=") + 1, xc.length));				      
				 }
			}
		}
		return "";
	}
};
function base64Convert(){
	return "\u4241\u5345\u3634{"+ Base64.encode(arguments[0])+"}";
}
function _isMultipartForm(xForm){
	if(xForm == null){
		xForm = document.forms[0];
	}
	if(xForm != null){
		if(xForm.encoding == "multipart/form-data"){
			return true;
		}
	}
	return false;
}
function _base64Encode(str, xForm, isX) {
	if(_isMultipartForm(xForm)){
		return str;
	}else{
		var val = str;
		if(str != null && str.length > 0)
			str = base64Convert(str);
		if(val != str){
			if(isX==true){
				val = "\u4645\u5810\u4d40" + str;
			}else{
				val = "\u4649\u5820\u4d45" + str;
			}
		}
		return val;
	}
}

function paramEncrypt(value, type){
    return desEncrypt(value, null, type);
}

function desEncrypt(value, xForm, type){
	if(_isMultipartForm(xForm)){
		return value;
	}else{
		var keyObj = {};
		if(type==null || "sm2"==type.toLowerCase()){
            keyObj = SECURITYKEY.get('sm2');
            value = SM2Utils.encs(keyObj.SM2PubKey,value);
        }else if("aes"==type.toLowerCase()){
			keyObj = SECURITYKEY.get(); 
			value = CryptoJS.AES.encrypt(value, CryptoJS.enc.Utf8.parse(keyObj.key),{
					iv:CryptoJS.enc.Utf8.parse(keyObj.iv)
				}).toString();
        }
		else{
			keyObj = SECURITYKEY.get('des'); 
			value = CryptoJS.DES.encrypt(value, CryptoJS.enc.Hex.parse(keyObj.key), {
				iv : CryptoJS.enc.Hex.parse(keyObj.iv)
			});
		}
		return keyObj.security + value;
	}
}
function base64Encode(str, xForm) {	 //RTF传输
	return _base64Encode(str, xForm);
}
function base64Encodex(str, xForm) { //xform传输
	return _base64Encode(str, xForm, true);
}
