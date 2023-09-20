
//当页面在iframe中时使外层iframe高度自适应
function iframeAutoFit(){
    if(!window.parent || !window.parent.document)
        return;
    var iframeObj = $(window.parent.document).find("iframe[name^='if_']");
    if(iframeObj.length <= 0 )
        return;
    iframeObj.each(function(){
        if(this.contentWindow === window) {
            iframeObj = this;
            return;
        }
    });
    setTimeout(function(){
        iframeObj.height = (iframeObj.Document ? iframeObj.Document.body.scrollHeight : iframeObj.contentDocument.body.offsetHeight);
    }, 100);
}