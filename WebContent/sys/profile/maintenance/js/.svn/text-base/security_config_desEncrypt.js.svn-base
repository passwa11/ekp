define(function(require, exports, module) {
    var lang = require('lang!sys-profile');
    var $ = require("lui/jquery");
    var paramsHere = {

    }

    var init = function(params) {
        paramsHere = $.extend(paramsHere, params);
    }

    /**
     * 生成SM2秘钥
     * @returns {boolean}
     */
    var generateSm2Key = function() {
        var rtn = true;
        if (paramsHere.method == "edit" && v == paramsHere.oldSubject) {
            return true;
        }
        var generateSm2KeyUrl = Com_Parameter.ContextPath + "/sys/profile/sys_desEncrypt_config/SysDesEncryptConfig.do?method=generateSm2Key";
        $.ajax({
            type : "POST",
            dataType : "json",
            url : generateSm2KeyUrl,
            data : {
                type:"sm2"
            },
            success : function(result) {
                if (result.success) {
                    $("input[name='value(security.sm2.pubKey)']").val(result.pubKey);
                }
            },
            error : function(s, s2, s3) {

            }
        });
        return rtn;
    }

    module.exports.init = init;
    module.exports.generateSm2Key = generateSm2Key;
})
