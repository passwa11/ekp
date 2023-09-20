
var element = render.parent.element;
if(data == null || data.length == 0){
    done();
}else{
    var ul = $('<ul>').attr('class', '').appendTo(element);
    for(var i = 0; i < data.length; i++){
        var node = buildNode(data[i]);
        node.appendTo(ul);
    }
}

function buildNode(data){
    var node = $('<li class="aside_item"/>');
    $('<span />').text(data.text).appendTo(node);
    if(data.selected){
        $(node).addClass('active');
    }

    node.on('click',function(){
        //移除导航选中状态
        LUI.$(".aside_item").removeClass("active");
        $(this).addClass('active');
        $("#"+data.iframeId).attr("src",Com_Parameter.ContextPath+ data.src) ;
    });
    return node;
}
