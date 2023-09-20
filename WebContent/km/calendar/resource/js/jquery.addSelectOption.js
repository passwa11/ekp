/**
 * 附加select选择项
 * 不能写成插件$.fn.xx，因为插件闭包异步加载，后续初始化使用会取不到插件方法
 * @author  zhouw
 * @name    jquery.addSelectOption.js
 * @since   2022-7-29 17:30:50
 */
window.addSelectOption = function(obj,options,callbackFun) {
        //原始选中值
        var orgVal = $(obj).val();
        if(typeof options != "undefined" && !!options){
            for(var i = 0 ; i < options.length; i++ ){
                //空不追加
                if(!options[i]["value"]){
                    continue;
                }
                //赋值
                $(obj).val(options[i]["value"]);
                var opTmp = '<option value="'+options[i]["value"]+'">'+options[i]["name"]+'</option>';
                //赋值不成功 - 说明值不存在 - 则添加
                if(!$(obj).val()){
                    $(obj).append(opTmp);
                }
            }
            $(obj).val(orgVal);
        }

        //回调方法
        if(!!callbackFun){
            callbackFun();
        }
    };