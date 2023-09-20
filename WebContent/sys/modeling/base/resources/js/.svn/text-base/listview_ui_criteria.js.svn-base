seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function ($, dialog, topic) {
    var criterions = null;

    //改变搜索条件时，动态往查询参数里增加自定义参数
    topic.subscribe('criteria.changed', function (evt) {
        criterions = evt.criterions;
        if (evt && evt.criterions && evt.criterions.length > 0) {
            for (var i in criterions) {
                //部门：包含子节点
                let isCludeSubDeptName = 't1_' + criterions[i].key;
                let t1 = $('input[name="' + isCludeSubDeptName + '"]');
                if (t1.length > 0 && t1[0].checked && t1.attr('listview-creteria-type') === 'dept') {
                    evt.criterions.push({"key": isCludeSubDeptName, "value": [$(t1).val()]});
                    break;
                }
            }
        }
        //未选部门时，勾选框隐藏
        $('input[listview-creteria-type="dept"]').each(function () {
            let isCheckDept = false;
            let deptName = $(this).attr('name').substring("t1_".length);
            for (var i in criterions) {
                if(deptName === criterions[i].key) {
                    isCheckDept = true;
                }
            }
            if(isCheckDept) {
                $(this).closest('div').css("display","inline-block");
            } else {
                $(this)[0].checked = false;
                $(this).closest('div').hide();
            }
        });
    });

    $(function () {
        //为"部门：包含子节点"勾选框绑定改变时触发criteria条件改变事件
        $('input[listview-creteria-type="dept"]').on("change", function () {
            if (!criterions || criterions.length === 0)
                return;
            //通过控件改变事件生成的criterions不包含自定义push进去的数据，此处删除后由subscribe去判断是否push
            var delIndex = -1;
            for (var i in criterions) {
                let key = criterions[i].key;
                if (key == $(this).attr('name')) {
                    delIndex = i;
                    break;
                }
            }
            if (delIndex !== -1)
                criterions.splice(delIndex, 1);
            topic.publish('criteria.changed', {criterions: criterions});
        });
    });
});

function criterionIsIncludeSubDivInit(canMulti) {
    // 重新计算 包含子节点 复选框样式
    var $criterion = $('.criterion-is-include-sub');

    var lineHeight = $criterion.closest('.criterion-value').prev().height()-2;
    $criterion.css('line-height', lineHeight + 'px');

    var marginLeft = parseInt( $criterion.siblings(".criterion-collapse").css("margin-left"));
    $criterion.css('margin-left', marginLeft +4);
}