/***********************************************
JS文件说明：
	为了解决前端计算控件，在流程结束之后，设置的样式不生效的问题。
作者：王雄峰
版本：1.0 2021-11-01
***********************************************/
setTimeout(function() {
    if ($("div.xform_Calculation.xform_calculation_readonly").length > 0) {
        $("div.xform_Calculation.xform_calculation_readonly").each(function() {
            var newStyle = $(this).attr("attrdivstyle");
            if (newStyle != "" || newStyle != null) {
                $(this).removeAttr("style");
                $(this).attr("style", newStyle);
                $(this).removeAttr("attrdivstyle");
                if ($(this).find('input').length > 0) {
                    // 如果包含有input元素的，移除掉style属性，防止出现重复样式造成的影响。
                    // 例如：div上有margin值，input上也有margin的情况
                    $(this).find('input').removeAttr("style");
                }
            }
        });
    }
},100);