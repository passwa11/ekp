/**
 * 
 * CK 编辑内容过滤器
 */
Com_RegisterFile("ckeditor/ckfilter.js");
Com_IncludeFile("security.js");

CKFilter = new Object();

CKFilter.replaceFilters = [];
CKFilter.ckInstanceName = [];

CKFilter.fireReplaceFilters = function(str) {
	var rtn = str;
	var fns = CKFilter.replaceFilters;
	for (var i = 0; i < fns.length; i++) {
		rtn = fns[i](rtn);
	}
	return rtn;
};

CKFilter.addReplaceFilter = function(field, enforce) {
	CKFilter.ckInstanceName.push({
		name : field,
		enforce : enforce
	});
};
// 过滤非法html对象
CKFilter.replaceFilters.push(function(str) {
	return str.replace(/<(form|input|select|option|script|link|iframe)[^>]*>/ig, "").replace(
			/<\/(form|input|select|option|script|link|iframe)>/ig, "");
});

// 过滤Object标签中的data属性
CKFilter.replaceFilters
		.push(function(str) {
			return str.replace(
					/(<object[^>]*?)(data\s*=\s*["|'][\s\S]*?["|'])([^>]*>)/ig,
					"$1$3");
		});
// 过滤form
CKFilter.replaceFilters.push(function(str) {
	return str.replace(/<(form|input|select|option)[^>]*>/ig, "").replace(
			/<\/(form|input|select|option)>/ig, "");
});
// 过滤触发脚本
CKFilter.replaceFilters.push(function(str) {
	return str.replace(
			/<([a-z][^>]*)[\s|\\]+?on[a-z]+\s*=\s*("[^"]+"|'[^']+'|[^\s]+)([^>]*)>/ig,
			"<$1$3>");
});

CKFilter.specialChars = {
	'€' : '&euro;',
	'‘' : '&lsquo;',
	'’' : '&rsquo;',
	'“' : '&ldquo;',
	'”' : '&rdquo;',
	'–' : '&ndash;',
	'—' : '&mdash;',
	'¡' : '&iexcl;',
	'¢' : '&cent;',
	'£' : '&pound;',
	'¤' : '&curren;',
	'¥' : '&yen;',
	'¦' : '&brvbar;',
	'§' : '&sect;',
	'¨' : '&uml;',
	'©' : '&copy;',
	'ª' : '&ordf;',
	'«' : '&laquo;',
	'¬' : '&not;',
	'®' : '&reg;',
	'¯' : '&macr;',
	'°' : '&deg;',
	'²' : '&sup2;',
	'³' : '&sup3;',
	'´' : '&acute;',
	'µ' : '&micro;',
	'¶' : '&para;',
	'·' : '&middot;',
	'¸' : '&cedil;',
	'¹' : '&sup1;',
	'º' : '&ordm;',
	'»' : '&raquo;',
	'¼' : '&frac14;',
	'½' : '&frac12;',
	'¾' : '&frac34;',
	'¿' : '&iquest;',
	'À' : '&Agrave;',
	'Á' : '&Aacute;',
	'Â' : '&Acirc;',
	'Ã' : '&Atilde;',
	'Ä' : '&Auml;',
	'Å' : '&Aring;',
	'Æ' : '&AElig;',
	'Ç' : '&Ccedil;',
	'È' : '&Egrave;',
	'É' : '&Eacute;',
	'Ê' : '&Ecirc;',
	'Ë' : '&Euml;',
	'Ì' : '&Igrave;',
	'Í' : '&Iacute;',
	'Î' : '&Icirc;',
	'Ï' : '&Iuml;',
	'Ð' : '&ETH;',
	'Ñ' : '&Ntilde;',
	'Ò' : '&Ograve;',
	'Ó' : '&Oacute;',
	'Ô' : '&Ocirc;',
	'Õ' : '&Otilde;',
	'Ö' : '&Ouml;',
	'×' : '&times;',
	'Ø' : '&Oslash;',
	'Ù' : '&Ugrave;',
	'Ú' : '&Uacute;',
	'Û' : '&Ucirc;',
	'Ü' : '&Uuml;',
	'Ý' : '&Yacute;',
	'Þ' : '&THORN;',
	'ß' : '&szlig;',
	'à' : '&agrave;',
	'á' : '&aacute;',
	'â' : '&acirc;',
	'ã' : '&atilde;',
	'ä' : '&auml;',
	'å' : '&aring;',
	'æ' : '&aelig;',
	'ç' : '&ccedil;',
	'è' : '&egrave;',
	'é' : '&eacute;',
	'ê' : '&ecirc;',
	'ë' : '&euml;',
	'ì' : '&igrave;',
	'í' : '&iacute;',
	'î' : '&icirc;',
	'ï' : '&iuml;',
	'ð' : '&eth;',
	'ñ' : '&ntilde;',
	'ò' : '&ograve;',
	'ó' : '&oacute;',
	'ô' : '&ocirc;',
	'õ' : '&otilde;',
	'ö' : '&ouml;',
	'÷' : '&divide;',
	'ø' : '&oslash;',
	'ù' : '&ugrave;',
	'ú' : '&uacute;',
	'û' : '&ucirc;',
	'ü' : '&uuml;',
	'ý' : '&yacute;',
	'þ' : '&thorn;',
	'ÿ' : '&yuml;',
	'Œ' : '&OElig;',
	'œ' : '&oelig;',
	'Ŵ' : '&#372;',
	'Ŷ' : '&#374',
	'ŵ' : '&#373',
	'ŷ' : '&#375;',
	'‚' : '&sbquo;',
	'‛' : '&#8219;',
	'„' : '&bdquo;',
	'…' : '&hellip;',
	'™' : '&trade;',
	'►' : '&#9658;',
	'•' : '&bull;',
	'→' : '&rarr;',
	'⇒' : '&rArr;',
	'⇔' : '&hArr;',
	'♦' : '&diams;',
	'≈' : '&asymp;'
}

// 替换特殊字符
CKFilter.replaceFilters.push(function(str) {
	for ( var c in CKFilter.specialChars) {
		var re = new RegExp(c, "ig");
		str = str.replace(re, CKFilter.specialChars[c]);
	}
	return str;
});

// 过滤formaction
CKFilter.replaceFilters
		.push(function(str) {
			return str
					.replace(
							/<([a-z][^>]*)\sformaction\s*=\s*("[^"]+"|'[^']+'|[^\s]+)([^>]*)>/ig,
							"<$1$3>");
		});

// 过滤链接脚本
CKFilter.replaceFilters
		.push(function(str) {
			return str
					.replace(
							/<([a-z][^>]*)\s(href|src)\s*=\s*("\s*(javascript|vbscript):[^"]+"|'\s*(javascript|vbscript):[^']+'|(javascript|vbscript):[^\s]+)([^>]*)>/ig,
							'<$1 $2=""$7>');
		});

//过滤href的base64编码，防止XSS
CKFilter.replaceFilters
		.push(function(str) {
			return str
					.replace(
							/<([a-z][^>]*)\s(href)\s*=\s*("\s*(data):[^"]+"|'\s*(data):[^']+'|(data):[^\s]+)([^>]*)>/ig,
							'<$1 $2=""$7>');
		});

// 过滤src的base64编码，防止XSS
CKFilter.replaceFilters
		.push(function(str) {
			return str
					.replace(
							/<([a-z][^>]*)\s(src)\s*=\s*("\s*(data):[^"]+"|'\s*(data):[^']+'|(data):[^\s]+)([^>]*)>/ig,
							'<$1 $2=""$7>');
		});

// 去除隐藏域
CKFilter.replaceFilters.push(function(str) {
	return str.replace(
			/<([a-z][^>]*)\stype\s*=\s*("hidden"|'hidden'|hidden)([^>]*)>/gi,
			"");
});

// 去除附件相关信息
CKFilter.replaceFilters
		.push(function(str) {
			return str
					.replace(
							/(<div)\sid\s*=\s*"attachmentObject_[\s|\S]*?_content_div"\s*?(>)/ig,
							"$1$2");
		});

// 去除width为空的参数，防止ie10下宽度变更为1的问题
CKFilter.replaceFilters.push(function(str) {
	return str.replace(
			/<([a-z][^>]*)(width|height)\s*=\s*("\s*"|'\s*')([^>]*)>/gi,
			"<$1$4>");
});

// 去除表单名称
CKFilter.replaceFilters
		.push(function(str) {
			return str
					.replace(
							/<([b-z][^>][^param]*)\sname\s*=\s*("[^"]+"|'[^']+'|[^\s]+)([^>]*)>/gi,
							"<$1$3>");
		});
// 去除注释
CKFilter.replaceFilters.push(function(str) {
	return str.replace(/<!--[\s\S]*?-->/g, '');
});

// 过滤绝对定位，修复xsio攻击
CKFilter.replaceFilters.push(function(str) {
	return str.replace(/position\s*:\s*absolute/g, '');
});

CKFilter.SetOnAfterLinkedFieldUpdate = function(name) {
	
	var editor = CKEDITOR.instances[name];
	var element = editor.element;
	var fn = function() {
		var html = editor.getData();
		html = CKFilter.fireReplaceFilters(html);
		html = CKFilter.Base64Encode(html, null);

		element.setValue(html);
		element.setHtml(html);
		// editor.setData(base64Encode(html));
	};
	element.on('updateEditorElement', fn);
	element.on('updateEditorTextarea', fn);

};

CKFilter.SetBase64Encode = function(name) {
	var editor = CKEDITOR.instances[name];
	var element = editor.element;
	var fn = function() {
		var html = CKFilter.Base64Encode(editor.getData(), null);
		element.setValue(html);
		element.setHtml(html);
	}
	element.on('updateEditorElement', fn);
	element.on('updateEditorTextarea', fn);
	//element.on('updateEditor', fn);
};

CKFilter.Base64Encode = function(str,xForm){
	return base64Encode(str, null);
}

CKFilter.SetBase64Decode = function() {
    var instanceNames = CKFilter.ckInstanceName;
    for (var i = 0; i < instanceNames.length; i++) {
        if (instanceNames[i] && instanceNames[i].name) {
            var elements = $(document).find("textarea[name*='"+ instanceNames[i].name +"']");
            elements.each(function(index, obj){
                var rtfVal = $(obj).val();
                if (rtfVal) {
                    var matcher = /\u4241\u5345\u3634\{([^\}]+)\}/.exec(rtfVal);
                    if (matcher && matcher.length == 2) {
                        rtfVal = matcher[1];
                        rtfVal = Base64.decode(rtfVal);
                        $(obj).val(rtfVal);
                    }
                }
            });
        }
    }
}

CKFilter.registerLinkedFieldUpdateListeners = function() {
	var len = 0;
	for ( var k in CKEDITOR.instances) {
		len++;
	}
	if (len != CKFilter.ckInstanceName.length) {
		setTimeout(CKFilter.registerLinkedFieldUpdateListeners, 1000);
		return;
	}

	var i, names = CKFilter.ckInstanceName;
	for (i = 0; i < names.length; i++) {
		if (!names[i].enforce)
			CKFilter.SetOnAfterLinkedFieldUpdate(names[i].name);
		else
			CKFilter.SetBase64Encode(names[i].name);
	}
};

Com_AddEventListener(window, "load", function() {
    CKFilter.SetBase64Decode();
    CKFilter.registerLinkedFieldUpdateListeners();
});

//异步提交的时候不会调用赋值操作，这里手动调用
Com_Parameter.event["confirm"].push(function() {
	if(Com_Submit.ajaxSubmit) {
		for(instance in CKEDITOR.instances){
			CKEDITOR.instances[instance].updateElement();
		}
	}
	return true;
})