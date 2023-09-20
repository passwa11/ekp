/*
 modeling-validation="id;text;validates(以逗号分隔);name/id/class:modelTargetId;id/class:value"
id，
text
 校验类型，多个（逗号分隔），默认/为空=必填;
 校验值,
    （key:value）,ex. (name/id/class):(value),默认是name
 提示显示，
    （key:value）,ex.(id/class:value),默认是name

 */
if(!land){
    var land ={
        required:{
            text:"不能为空"
        }
    };
}

var _LOCAL_VALIDATE = {
    "required": {
        "fun": function (lvContext) {
            var $vDom = lvContext.dom.value;
            var $sDom = lvContext.dom.show;
            if ($vDom && $vDom.val()) {
                return true;
            }
            var tipsHtml = validateShowHtml(lvContext.idx + lvContext.property.id, lvContext.property.text, land.required.text);
            $sDom.append(tipsHtml)
        },
    },
    "requiredNullable": {
        "fun": function (lvContext) {
            //目标不存在则不校验
            var $vDom = lvContext.dom.value;
            var $sDom = lvContext.dom.show;
            if (!$vDom || $vDom.length ==0)
                return true;
            if ($vDom.val())
                return true;
            var tipsHtml = validateShowHtml(lvContext.idx + lvContext.property.id, lvContext.property.text, land.required.text);
            $sDom.append(tipsHtml)

        }
    },
    "time": {
    	"fun": function(lvContext) {
    		var $vDom = lvContext.dom.value;
            var $sDom = lvContext.dom.show;
            var regexs = /^(([0-2][0-3])|([0-1][0-9])):[0-5][0-9]$/;
            if($vDom.val() == ""){
            	return true;
            }
            if(regexs.test($vDom.val()))
            	return true;
            var tipsHtml = validateShowHtml(lvContext.idx + lvContext.property.id, lvContext.property.text, "");
            $sDom.find(".modeling-validation-show").remove();
            $sDom.append(tipsHtml);
    	}
    }
};

function buildLocalValidationContext(idx, $dom) {
    var _data = $dom.attr("modeling-validation");
    var _dataArr = _data.split(";");

    var context = {
        idx: idx,
        property: {
            id: _dataArr[0],
            text: _dataArr[1]
        },
        validates: _dataArr[2].split(","),
        dom: {
            value: getValidatesDom(_dataArr[3]),
            show: getValidatesDom(_dataArr[4])
        }
    };
    console.debug("buildLocalValidationContext",context)
    return context;
}

function _localValidation() {
    $(".modeling-validation-show").remove();
    var _lv = true;
    $('[modeling-validation]').each(function (idx, dom) {
        var $dom = $(dom);
        try {
            var lvContext = buildLocalValidationContext(idx, $dom);
            for (var i = 0; i < lvContext.validates.length; i++) {
                var fun = lvContext.validates[i];
                if (!_LOCAL_VALIDATE[fun].fun(lvContext)) {
                    _lv = false;
                }
            }
        } catch (e) {
            console.warn("_localValidation error ", e);
            console.warn("idx,dom,$dom is  ", idx, dom, $dom);
        }
    });
    return _lv;
}


function getValidatesDom(str) {
    if (str) {
        if (str.indexOf(":") == -1) {
            return $("[name='" + str + "']");
        }
        var val = str.split(":");
        return getDomByKeyValue(val[0], val[1]);
    } else {
        return null;
    }

}

function getDomByKeyValue(key, val) {
    var $dom = undefined;
    switch (key) {
        case "id":
            $dom = $("#" + val);
            break;
        case "class":
            $dom = $("." + val);
            break;
        case "name":
            $dom = $("[name='" + val + "']");
            break;
        default:
            $dom = $("#" + val);
            break;
    }
    return $dom;
}

function validateShowHtml(id, name, text) {
    var validateHtml = " <div class=\"validation-advice modeling-validation-show\"  id=\"" + id + "\" _reminder=\"true\">\n" +
        "                                            <table class=\"validation-table\">\n" +
        "                                                <tbody>\n" +
        "                                                <tr style='height: 20px'>\n" +
        "                                                    <td style='border:none'>\n" +
        "                                                        <div class=\"lui_icon_s lui_icon_s_icon_validator\"></div>\n" +
        "                                                    </td>\n" +
        "                                                    <td class=\"validation-advice-msg\" style='border:none;color: #000'><span\n" +
        "                                                            class=\"validation-advice-title\">" + name + "</span> " + text + "\n" +
        "                                                    </td>\n" +
        "                                                </tr>\n" +
        "                                                </tbody>\n" +
        "                                            </table>\n" +
        "                                        </div>"
    return validateHtml;
}