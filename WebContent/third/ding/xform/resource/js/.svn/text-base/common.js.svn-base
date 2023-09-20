/**
 * 套件页面的公共逻辑
 */

function DingCommonInit() {
    handleSubject();

    /**
     * 主题
     */
    function handleSubject() {
        let titleTd = $('input[name="fdCanCircularize"]').closest('td').prev();
        if (!titleTd || titleTd.length < 1) {
            return;
        }
        var flag = $('[name="extendDataFormInfo.value(fd_batch_change_flag)"]').val();
        if("batchChange"!=flag){
        	let width = $('.sysDefineXform').find('table>tbody>tr>td.td_normal_title:first').css('width');
        	titleTd.css('width', width);
        }
        titleTd.addClass('td_normal_title');
        let subject = titleTd[0].innerHTML;
        titleTd[0].innerHTML = "<label>" + subject.trim() + "</label>";
    }
}

/**
 * 获取当前的文档状态（add|edit|view）
 * @returns
 */
function DingCommonnGetStatus() {
    return Xform_ObjectInfo.mainDocStatus;
}

Com_AddEventListener(window, "load", DingCommonInit);