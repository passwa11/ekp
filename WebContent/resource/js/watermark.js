;(function (window, factory) {
    if(document.body){
        var style = document.createElement("style");
        style.type = "text/css";
        try{
            style.appendChild(document.createTextNode(".water-mark{position:absolute;overflow:hidden;z-index:99999999999999;pointer-events:none;cursor:default;text-align:center;display:block;-webkit-user-select:none; -moz-user-select:none;-ms-user-select:none;user-select:none;word-break: break-all}"));
        }catch(e){
            style.styleSheet.cssText = ".water-mark{position:absolute;overflow:hidden;z-index:9999999;pointer-events:none;cursor:default;text-align:center;display:block;-webkit-user-select:none; -moz-user-select:none;-ms-user-select:none;user-select:none;word-break: break-all}";
        }
        var head = document.getElementsByTagName("head")[0];
        head.appendChild(style);
    }else
        document.write('<style>.water-mark{position:absolute;overflow:hidden;z-index:9999999;pointer-events:none;cursor:default;text-align:center;display:block;-webkit-user-select:none; -moz-user-select:none;-ms-user-select:none;user-select:none;word-break: break-all}</style>');
    //document.body.onselectstart = function () { return false;};
    window.WaterMark = factory();
}(window, function () {

    function WaterMark(opts,tarDom) {
        var defoPts = {
            context: "水印文字",//水印内容
            x: 20,//水印起始位置x轴坐标
            y: 50,//水印起始位置Y轴坐标
            rows: 0,//水印行数
            cols: 0,//水印列数
            x_space: 100,//水印x轴间隔
            y_space: 50,//水印y轴间隔
            color: '#aaa',//水印字体颜色
            alpha: 0.4,//水印透明度
            fontsize: '15px',//水印字体大小
            font: '微软雅黑',//水印字体
            width: 210,//水印宽度
            height: 88,//水印长度
            angle: 30,//水印倾斜度数
            fontbold: "normal",//水印字体加粗
            isHold:false
        };
        for (key in opts) {
            defoPts[key] = opts[key];
        }
        this.opts = defoPts;
        this.isRender= false;
        this.isRefresh= false;
        this.tarDom = tarDom;
    }

    WaterMark.prototype.render = function () {

        if(this.isRender){
            return;
        }
        this.isRender= true;
        var opts = this.opts;
        //获取页面最大宽度
        var pageW;
        var innerHeight;
        var pageH;

        pageW = Math.max(document.body.scrollWidth, document.body.clientWidth) - 20;
        // //获取页面最大高度
        innerHeight = window.innerHeight || document.documentElement.clientHeight;
        pageH = Math.max(document.body.scrollHeight, document.body.clientHeight, innerHeight-50);

        pageH = pageH - 20;
        var cols = opts.cols;
        var x_space = opts.x_space;
        //如果将水印列数设置为0，或水印列数设置过大，超过页面最大宽度，则重新计算水印列数和水印x轴间隔
        if (opts.cols <= 0 || (parseInt(opts.x + opts.width * opts.cols + opts.x_space * (opts.cols - 1)) > pageW)) {
            cols = parseInt((pageW - opts.x) / (opts.width + opts.x_space));
            x_space = parseInt((pageW - opts.x - opts.width * cols) / (cols - 1));
        }

        var rows = opts.rows;
        var y_space = opts.y_space;
        //如果将水印行数设置为0，或水印行数设置过大，超过页面最大长度，则重新计算水印行数和水印y轴间隔
        if (opts.rows <= 0 || (parseInt(opts.y + opts.height * opts.rows + opts.y_space * (opts.rows - 1)) > pageH)) {
            rows = parseInt((pageH - opts.y) / (opts.height + opts.y_space));
            y_space = parseInt((pageH - opts.y - opts.height * rows) / (rows - 1));
        }

        var domTemp = document.createDocumentFragment();
        var x;
        var y;
        for (var i = 0; i < rows; i++) {
            y = opts.y + (y_space + opts.height) * i;
            for (var j = 0; j < cols; j++) {
                x = opts.x + (opts.width + x_space) * j;
                var maskDiv = document.createElement('div');
                maskDiv.id = 'mask_div' + i + j;
                maskDiv.className="water-mark";
                maskDiv.setAttribute('name','waterMark');
                maskDiv.appendChild(document.createTextNode(opts.context));
                //设置水印div倾斜显示
                maskDiv.style.webkitTransform = "rotate(-" + opts.angle + "deg)";
                maskDiv.style.MozTransform = "rotate(-" + opts.angle + "deg)";
                maskDiv.style.msTransform = "rotate(-" + opts.angle + "deg)";
                maskDiv.style.OTransform = "rotate(-" + opts.angle + "deg)";
                maskDiv.style.transform = "rotate(-" + opts.angle + "deg)";
                maskDiv.style.left = x + 'px';
                maskDiv.style.top = y + 'px';
                maskDiv.style.opacity = opts.alpha;
                if(typeof(isLowerThanIE8)!="undefined" && isLowerThanIE8=='true') {
                    maskDiv.style.filter = "progid:DXImageTransform.Microsoft.Matrix(sizingMethod='auto expand', M11=0.7071,M12=0.7071, M21=-0.7071, M22=0.7071)progid:DXImageTransform.Microsoft.Alpha(opacity=" + opts.alpha * 100 + ")";
                }
                maskDiv.style.fontSize = opts.fontsize;
                maskDiv.style.fontFamily = opts.font;
                maskDiv.style.color = opts.color;
                maskDiv.style.width = opts.width + 'px';
                maskDiv.style.height = opts.height + 'px';
                maskDiv.style.fontWeight = opts.fontbold;
                domTemp.appendChild(maskDiv);
            }
        }
        if(this.tarDom){
            this.tarDom.append(domTemp);
        }else{
            document.body.appendChild(domTemp);
        }
        // document.body.appendChild(domTemp);
        this.isRender= false;
    };

    function removeElement(_element){
        if ( _element.remove ) {
            _element.remove();
        } else {
            var _parentElement = _element.parentNode;
            if (_parentElement) {
                _parentElement.removeChild(_element);
            }
        }
    }

    var isIE8 = function() {
        return navigator.userAgent.indexOf("MSIE") > -1 && document.documentMode == null || document.documentMode <= 8;
    }

    WaterMark.prototype.refresh = function () {
        if(this.isRefresh){
            return;
        }
        this.isRefresh= true;
        var marks;
        if(typeof($)!="undefined"){
            marks = $("[name=waterMark]",this.tarDom);
        }else{
            marks = document.getElementsByName("waterMark");
        }


        var markArr = [];
        for (var i = 0; i < marks.length; i++) {
            markArr.push(marks[i]);
        }
        for (var i = 0; i < markArr.length; i++) {
            removeElement( markArr[i] );
        }
        this.render();
        this.isRefresh= false;
    }
    return WaterMark;
}));