<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${ lfn:message('sys-modeling-main:modelingGantt.color.setting') }</title>
    <%@ include file="/sys/ui/jsp/common.jsp"%>
    <%@ include file="/sys/ui/jsp/jshead.jsp"%>
    <link rel="stylesheet" href="../css/color/index.css">
    <link rel="stylesheet" href="../css/common.css">
</head>
<script>
</script>
<body>
    <div class="model-gantt-setColor">
        <div class="setColor-dialog">
           <%-- <div class="setColor-dialog-head">
                <p>颜色设置</p>
                <i class="close-dialog"></i>
            </div>--%>
            <div class="setColor-dialog-content">
                <div class="select-color-schemes">
                    <div class="color-schemes-title">${ lfn:message('sys-modeling-main:modelingGantt.coloring.scheme') }</div>
                    <div class="select-box">
                        <input id="select-txt" placeholder="${ lfn:message('sys-modeling-main:modelingGantt.select.type') }" readonly="readonly">
                        <i class="select-arrow"></i>
                        <ul class="option-list">
                            <!-- <li class="option-list-item">
                                <ul class="color-list">
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                    <li></li>
                                </ul>
                                <p class="color-schemes-name">经典配色</p>
                            </li>-->
                        </ul>
                    </div>
                </div>
                <div class="edit-color-schemes">
                    <div class="project-area">
                        <ul class="project-lists">
                            <!-- <li>
                                <i class="project-color"></i>
                                <p class="project-title">项目名称</p>
                            </li> -->
                        </ul>
                    </div>
                    <div class="editing-area">
                        <div class="preset-color">
                            <p class="editing-color-title">${ lfn:message('sys-modeling-main:modelingGantt.preset.color') }</p>
                            <ul class="preset-color-list">
                                <!-- <li></li>
                                <li></li>
                                <li></li>
                                <li></li>
                                <li></li>
                                <li></li>
                                <li></li>
                                <li></li>
                                <li></li>
                                <li></li> -->
                            </ul>
                        </div>
                        <div class="custom-color">
                            <!-- <p class="editing-color-title">自定义</p>
                            <div class="custom-color-box">
                                <i class="custom-item-color"></i>
                            </div>
                            <i class="custom-color-add"></i> -->
                        </div>
                    </div>
                </div>
            </div>
            <div class="setColor-dialog-footer">
                <div class="cancel">${ lfn:message('sys-modeling-main:modeling.cancel') }</div>
                <div class="sure">${ lfn:message('sys-modeling-main:modeling.ok') }</div>
            </div>
        </div>
    </div>
    <%--<script src="../js/jquery-1.11.3.min.js"></script>--%>
    <script>Com_IncludeFile('dialog.js|data.js|jquery.js');</script>
    <script>Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js', 'js/jquery-plugin/manifest/');</script>

    <script>
        window.colorChooserHintInfo = {
            cancelText: '${ lfn:message('sys-modeling-main:modelingCalendar.cancel') }',
            chooseText: '${ lfn:message('sys-modeling-main:modeling.ok') }'
        };
        Com_IncludeFile("spectrum.js", Com_Parameter.ContextPath + 'resource/js/colorpicker/', 'js', true);
        Com_IncludeFile("spectrum.css", Com_Parameter.ContextPath + 'resource/js/colorpicker/css/', 'css', true);
        Com_IncludeFile("spectrumColorPicker.js", Com_Parameter.ContextPath + 'sys/modeling/base/views/business/res/', 'js', true);
        window.initProjectData =[];
        seajs.use(["sys/modeling/assembly/js/color"]
            , function ( modelingColor) {
                //监听数据传入
                var _param;
                var intervalEndCount = 10;
                var interval = setInterval(__interval, "50");

                function __interval() {
                    if (intervalEndCount == 0) {
                        console.error("数据解析超时。。。");
                        clearInterval(interval);
                    }
                    intervalEndCount--;
                    if (!window['$dialog']) {
                        return;
                    }
                    _param = $dialog.___params;
                    init(_param);
                    clearInterval(interval);
                }

                function init(param) {
                    var cfg = {
                        colorData:  param.colorData,
                        projectData: param.projectData,
                        colorListIndex: param.colorListIndex
                    };
                    for (let i = 0; i < param.projectData.length; i++) {
                        var data = {
                            id:param.projectData[i].id,
                            bgColor:param.projectData[i].bgColor,
                            name:param.projectData[i].name,
                            status:param.projectData[i].status
                        }
                        initProjectData.push(data)
                    }
                    window.modelingColor = new modelingColor.ModelingColor(cfg);
                    window.modelingColor.startup();
                }
                //取消按钮点击事件
                $(".setColor-dialog-footer").on('click', '.cancel', function () {
                    $dialog.hide(window.initProjectData);
                });
                //确定按钮点击事件
                $(".setColor-dialog-footer").on('click', '.sure', function () {
                    if(false == window.modelingColor.getIsPost()){
                        $dialog.hide(null);
                        return;
                    }
                    //发生ajax请求，保存自定义颜色跟默认颜色方案
                    var url = '${LUI_ContextPath}/sys/modeling/main/business.do?method=saveGanttCustomColor&modelId='+_param.modelId+'&businessId='+_param.businessId+'&type=gantt';
                   $.ajax({
                        type:"post",
                        url:url,
                        data:{
                            postProjectListData: JSON.stringify(window.modelingColor.getPostProjectListData()),
                            defaultColorListId: window.modelingColor.getDefaultColorListId()
                        },
                        async:false ,    //用同步方式
                        success:function(data){
                            //保存成功,隐藏dialog(回调刷新)
                            $dialog.hide('submit');
                        }
                    });
                });

            });
    </script>
</body>
</html>