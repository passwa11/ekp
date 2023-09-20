/*
 *  Ocr证件组件，只进行属性定义，内容渲染和事件绑定等，具体的能力操作在_OcrMixin.js中实现
 *  默认继承_OcrMixin.js
 */
define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dijit/_Contained",
    "dijit/_Container",
    "mui/ocr/_OcrMixin",
    "mui/form/_AlignMixin",
    "dojo/dom-construct",
    "dojo/dom-class",
    "dojo/dom-attr",
    "dojo/query",
    "dojo/html",
    "dojo/_base/lang",
    "dojo/on",
    "mui/util",
    "dojox/mobile/viewRegistry",
    "mui/i18n/i18n!sys-mobile:mui.ocr"
],function(declare, WidgetBase, _Contained, _Container, OcrMixin, _AlignMixin, domConstruct, domClass, domAttr, query,html,lang,on,util,viewRegistry,Msg){
    return declare("mui.ocr.Orc", [_Contained, _Container, WidgetBase, OcrMixin, _AlignMixin], {
        //容器className
        containerClass: "muiOcrContainer",

        //证件类型
        certificateType: "",

        //证件类型对应的数字，对应后台的常量类ThirdOcrConstant，扩展的类型也要提供这样一个数字，识别接口需要
        typeNum: '',

        //正反面，正面为1，反面为0，默认是正面
        front: '1',

        //保留图片，默认是保留
        isUpload: true,

        //组件渲染的模板路径，这里提供了默认ocr_default.jsp，可以覆盖
        tmpUrl: '/sys/mobile/js/mui/ocr/default/ocr_default.jsp',

        //组件渲染的模板的请求参数
        tmpUrlParams: 'front=!{front}&isUpload=!{isUpload}&ocrDesc=!{ocrDesc}&ocrTip=!{ocrTip}&showStatus=!{showStatus}',

        //识别接口api
        identifyUrl:"/third/ocr/thirdOcrPart.do?method=ocrPartRecogniz",

        //识别失败后的渲染模板，可替换
        failJsp:"/sys/mobile/js/mui/ocr/ocr_fail.jsp",

        //识别失败后的渲染模板的参数
        failJspParams:'isUpload=!{isUpload}',

        //下拉按钮列表的渲染jsp
        operDialogJspUrl: '/sys/mobile/js/mui/ocr/ocr_opt.jsp',

        //下拉按钮列表的渲染jsp请求参数
        operDialogJspUrlParams: 'showStatus=!{showStatus}',

        //控制点击事件的行为，false表示默认，会打开选择文件框，true则是打开下拉按钮列表
        showFlag: false,

        //作为组件的唯一标识，和附件是一致的
        fdKey: '',

        //显示状态，edit或者view
        showStatus: '',

        //对齐方式
        align: 'left',

        //是否进行识别，控制上传图片后是否进行识别
        toIdentify:true,

        //附件个数，默认为0
        attSize:0,

        //是否必填
        required:false,

        buildRendering: function () {
            this.isForm = true;//对齐需要
            this.inherited(arguments);
            this.buildInnerRendering();
        },

        postCreate: function () {
            this.inherited(arguments);
            if (this.showStatus == 'edit')
                this.bindEvents();
        },

        startup: function () {
            this.inherited(arguments);
            domClass.add(this.domNode, "muiOcr");
            this.setNodeAlign();
            this.setValidator();
        },

        //设置对齐方式
        setNodeAlign: function () {
            // if (!this.align)
            //     this.align = this.getAlign();
            domClass.add(
                this.domNode,
                "muiOcr" + this.align.substring(0, 1).toUpperCase() + this.align.substring(1)
            );
        },

        //设置校验器内容
        setValidator:function(){
            this.pView = this.getValidateView();
            if (!this.pView || this.showStatus != 'edit') {
                return;
            }
            var _self = this;
            var validate = '';
            if(this.required){
                this.requiredNode = domConstruct.create(
                    "div",
                    {
                        className: "muiFormRequired muiFormRequiredShow muiOcrImgRequired",
                        innerHTML: "*"
                    },
                    this.ocrContainerNode
                );

                this.pView._validation.addValidator("ocr_image_required_" + this.fdKey,Msg['mui.ocr.validator.pic.null'],function(){
                    return _self.validateOcrImgRequired();
                });

                validate += 'ocr_image_required_'+this.fdKey;
            }
            if(validate)
                domAttr.set(this.domNode, "validate", validate);
        },

        //校验Ocr图片必填，和图片附件是对应的
        validateOcrImgRequired:function(){
            if(this.showFlag == true){
                return true;
            }else{
                return false;
            }
        },

        reValidate:function(){
            //校验
            if(this.pView){
                this.pView._validation.validateElement(this.domNode);
            }
        },

        //获取带校验器的视图
        getValidateView: function(domNode) {
            var node = this.domNode || domNode;
            var view = viewRegistry.getEnclosingView(node);
            while (view != null && !view._validation) {
                view = viewRegistry.getParentView(view);
            }
            return view;
        },

        //渲染传入的tmpUrl文件
        buildInnerRendering: function () {
            var tmpUrl = this.tmpUrl;
            //接上参数
            if(tmpUrl.indexOf("?") == -1){
                tmpUrl += "?" + this.tmpUrlParams;
            }
            //格式化url
            tmpUrl = util.formatUrl(util.urlResolver(tmpUrl, this));
            if (tmpUrl) {
                //创建容器
                var className = this.containerClass;
                if(this.required){
                    className += " muiOcrRequiredContainer";
                }
                this.ocrContainerNode = domConstruct.create("div", {className: className}, this.domNode);

                if(this.showStatus == 'edit' || this.attSize > 0 || this.isUpload == false) {
                    //请求渲染内容并进行渲染
                    var _self = this;
                    require({async: false}, ["dojo/text!" + tmpUrl], function (text) {
                        var dhs = new html._ContentSetter({
                            node: _self.ocrContainerNode,
                            parseContent: true,
                            cleanContent: true,
                            onBegin: function () {
                                this.content = lang.replace(this.content, {categroy: _self})
                                this.inherited("onBegin", arguments)
                            }
                        })

                        dhs.set(text)
                        dhs.parseDeferred.then(function (results) {
                            _self.parseResults = results;
                            //若是没有初始化imgNode，给出imgNode的默认值
                            //这里有个规则：事件名称+Event='事件方法'作为绑定事件的属性，比如dom元素加上了clickEvent='selectImg'，表示该dom需要绑定click事件，调用方法为selectImg
                            if (!_self.imgNode) {
                                _self.imgNode = query("[clickEvent='selectImg']", _self.ocrContainerNode)[0];
                            }
                        })
                        dhs.tearDown();
                    })
                }
            }
        },

        //绑定事件，编辑情况下使用
        bindEvents: function () {
            if (this.imgNode)
                on(this.imgNode, "click", lang.hitch(this, this.selectImg));
        }
    });
})