function showNotification(title, options) {
    let Notification = window.Notification || window.mozNotification || window.webkitNotification;
    if (Notification) {
        Notification.requestPermission(function (status) {
            // status默认值
            // 'default' 表示拒绝，
            // 'denied' 表示用户不想要通知，
            // 'granted' 表示用户同意启用通知
            if (status === "granted") {
                var tag = "sds" + Math.random();
                var notify = new Notification(title, {
                    dir: 'auto',
                    lang: 'zh-CN',
                    requireInteraction: true,
                    data: options.data,
                    tag: tag,//实例化的notification的id
                    icon: options.icon,//通知的缩略图,//icon 支持ico、png、jpg、jpeg格式
                    body: options.body //通知的具体内容
                });
                notify.onclick = function () {
                    //如果通知消息被点击,通知窗口将被激活
                    window.focus();
                    notify.close();
                    //打开对应的界面
                    window.open(notify.data.url, '_blank');
                };
            }
        });
    } else {
        console.log("您的浏览器不支持桌面消息");
    }
}
// 打开页面显示通知
function notifyEvent() {
    var checkNewNotifies = function(){
        if(typeof(Com_Parameter) !== 'undefined'){
            $.get(Com_Parameter.ContextPath+"sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getNewNotifies",
                function(data,status,xhr){
                    if(status === 'success' && data.size > 0){
                        if(data.url.startsWith("/")){
                            data.url = Com_Parameter.ContextPath + data.url.substring(1);
                        }
                        else{
                            data.url = Com_Parameter.ContextPath + data.url;
                        }
                        if(data.icon){
                            if(data.icon.startsWith("/")){
                                data.icon = Com_Parameter.ContextPath + data.icon.substring(1);
                            }
                            else{
                                data.icon = Com_Parameter.ContextPath + data.icon;
                            }
                        }
                        showNotification(data.title, {
                            icon: data.icon,
                            body: data.content,
                            data: {
                                url: data.url
                            }
                        });
                    }
                },
            "json");
        }
    };
    checkNewNotifies();
    const int = setInterval(checkNewNotifies,1000 * 60);
}
if(typeof(seajs) !== 'undefined'){
    seajs.use(['lui/jquery'], function($){
        $.get(Com_Parameter.ContextPath + "sys/notify/sys_notify_todo/sysNotifyTodo.do?method=checkIsDesktopPushingEnalbed", function(data,status){
            if(data === 'true'){
                var ishttps = 'https:' == document.location.protocol ? true : false;
                if(ishttps){
                    let $top_events= $._data(top,'events');
                    let isRegistried = false;
                    if($top_events && $top_events['load']){
                        for(let i in $top_events['load']){
                            if($top_events['load'][i].handler.name === "notifyEvent"){
                                isRegistried = true;
                                break;
                            }
                        }
                    }
                    if(!isRegistried){
                        $(top).load(notifyEvent);
                    }
                }
            }
        }, "text");
    });
}
