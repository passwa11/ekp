(function($) {
    if(!window.XformObject_Lang){
        XformObject_Lang = {};
    }
    FSelect_ImportCss(Com_Parameter.ContextPath+'sys/xform/designer/fSelect/fSelect.css');
    function FSelect_ImportCss(url){
        var link = document.createElement('link');
        link.type = 'text/css';
        link.rel = 'stylesheet';
        link.href = url;
        var head = document.getElementsByTagName('head')[0];
        head.appendChild(link);
    }

    $.fn.fSelect = function(options) {

        if (typeof options == 'string' ) {
            var settings = options;
        }
        else {
            var settings = $.extend({
                placeholder: XformObject_Lang.FSelectPleaseSelect,
                numDisplayed: 3,
                overflowText: '{n} selected',
                searchText: XformObject_Lang.FSelectSearch,
                showSearch: true
            }, options);
        }


        /**
         * Constructor
         */
        function fSelect(select, settings) {
            this.$wrap = this.$select = $(select);
            this.settings = settings;
            /* this.create();*/
            this.onEvent();
            this.reloadDropdownLabel();
        }

        /**
         * Prototype class
         */
        fSelect.prototype = {
            create: function() {
                var multiple = this.$select.is('[multiple]') ? ' multiple' : '';
                this.$select.wrap('<div class="fs-wrap' + multiple + '"></div>');
                this.$select.before('<div class="fs-label-wrap"><div class="fs-label">' + this.settings.placeholder + '</div><span class="fs-arrow"></span></div>');
                this.$select.before('<div class="fs-dropdown hidden"><div class="fs-options"></div></div>');
                this.$select.addClass('hidden');
                this.$wrap = this.$select.closest('.fs-wrap');
                this.reload();
            },

            onEvent : function() {
                var self = this;
                this.$select.on("click",".fs-search-icon-del",function(){
                    self.$select.find('.fs-option, .fs-optgroup-label').removeClass('hidden');
                    self.$select.find('.fs-search input').focus().val("");
                    $(this).hide();
                });
            },

            reload: function() {
                if (this.settings.showSearch) {
                    var search = '<div class="fs-search"><input type="text" autocomplete="off" placeholder="' + this.settings.searchText + '" /></div>';
                    this.$wrap.find('.fs-dropdown').prepend(search);
                }
                var choices = this.buildOptions(this.$select);
                this.$wrap.find('.fs-options').html(choices);
                this.reloadDropdownLabel();
            },

            destroy: function() {
                this.$wrap.find('.fs-label-wrap').remove();
                this.$wrap.find('.fs-dropdown').remove();
                this.$select.unwrap().removeClass('hidden');
            },

            buildOptions: function($element) {
                var $this = this;

                var choices = '';
                $element.children().each(function(i, el) {
                    var $el = $(el);

                    if ('optgroup' == $el.prop('nodeName').toLowerCase()) {
                        choices += '<div class="fs-optgroup">';
                        choices += '<div class="fs-optgroup-label">' + $el.prop('label') + '</div>';
                        choices += $this.buildOptions($el);
                        choices += '</div>';
                    }
                    else {
                        var selected = $el.is('[selected]') ? ' selected' : '';
                        choices += '<div class="fs-option' + selected + '" data-value="' + $el.prop('value') + '"><span class="fs-checkbox"><i></i></span><div class="fs-option-label">' + $el.html() + '</div></div>';
                    }
                });

                return choices;
            },

            reloadDropdownLabel: function() {
                var settings = this.settings;
                var labelText = [];
                this.$wrap.find('.fs-option.selected').each(function(i, el) {
                    labelText.push($(el).find('.fs-option-label').text());
                });

                //164304 16复选下拉框选择了长度较短的值后，宽度会变得很窄，15无该问题
                var labelWrap = this.$wrap.find('.fs-label-wrap');
                /*if (labelWrap && labelWrap[0] && labelWrap[0].offsetWidth>0){
                    labelWrap.attr('label_width',labelWrap[0].clientWidth);
                }*/

                if (labelText.length < 1) {
                    labelText = settings.placeholder||XformObject_Lang.FSelectPleaseSelect;
                }
                else if (labelText.length > settings.numDisplayed) {
                    /*labelText = labelText.slice(0,settings.numDisplayed).join(', ') + "...";*/
                    var text = "";
                    for (var i = 0;i < settings.numDisplayed; i++){
                        text += this.resolveLongText(labelText[i]);
                        text += ";";
                    }
                    labelText = text + "...";
                }
                else {
                    /* labelText = labelText.join(', ');*/
                    var text = "";
                    for (var i = 0;i < labelText.length; i++){
                        text += this.resolveLongText(labelText[i]);
                        if (i != labelText.length -1){
                            text += ";";
                        }
                    }
                    labelText = text;
                }
                this.$wrap.find('.fs-label').html(Com_HtmlEscape(labelText));

                //163804 明细表内复选下拉必填星号换行
                var boxselect = $(this)[0].$select.context;
                if (boxselect){
                    $(boxselect).css("display","inline-flex");
                }
            },

            resolveLongText : function(text){
                /*text = text || "";
                if (text.length > 4){
                    text = text.substring(0,4) + "...";
                }*/
                return text;
            }
        }


        /**
         * Loop through each matching element
         */
        return this.each(function() {
            var data = $(this).data('fSelect');

            if (!data) {
                var wrap = $(this).find(".fs-wrap");
                data = new fSelect(wrap, settings);
                $(this).data('fSelect', data);
                if ($(this)[0].parentNode){
                    if ($(this)[0].parentNode.nodeName == "DIV" && $(this)[0].parentNode.style.width != "") {
                        if ($(this)[0].parentNode.style.width === "auto"){
                            //自动宽度的改为120px
                            //$(wrap).css("width", "120px");
                        }else {
                            if ($(this)[0].parentNode.style.width.indexOf("%") < 0){
                                $(wrap).css("width", $(this)[0].parentNode.style.width);
                            }else {
                                $(wrap).attr("set_width","true");
                                $(wrap).closest(".select_div_box.xform_fselect").css("width", $(this)[0].parentNode.style.width);
                                $(wrap).closest("xformflag").css("width", "100%");
                                $(wrap).css("width", "100%");
                                if (Com_GetUrlParameter(window.top.location.href, "method") && Com_GetUrlParameter(window.top.location.href, "method").indexOf("view") >= 0){
                                    $($(wrap).context).css("white-space","normal");
                                }
                                LUI.ready(function () {
                                    if ($(wrap).width() > 800){
                                        $(wrap).find(".fs-label").css("-webkit-line-clamp","1");
                                        $(wrap).find(".fs-label").css("-webkit-box-orient","vertical");
                                        $(wrap).find(".fs-label").css("display","-webkit-box");
                                        $(wrap).find(".fs-label").css("word-break","break-word");
                                        $(wrap).find(".fs-label").css("white-space","normal");
                                        $(wrap).find(".fs-label").css("padding-bottom","inherit");
                                    }
                                })
                            }
                        }
                    }
                }
            }

            if (typeof settings == 'string') {
                data[settings]();
            }
        });
    }

    $(document).on('click', '.select_div_box.xform_fselect', function() {
        setPxForFSelect(this);
    });
    $(document).on('click', '.select_div_box.xform_fselect', function() {
        setTitleForFSelect(this);
    });

    //添加title
    function setTitleForFSelect(f){
        var $wrap = $(f).find('.fs-options');
        if ($wrap){
            $wrap.find(".fs-option-label").each(function () {
                let text = $(this).text();
                $(this).attr("title",text);
            })
        }
    }

    //改%为px
    function setPxForFSelect(f){
        var $wrap = $(f).find('.fs-wrap');
        if ($wrap[0] && $wrap[0].style && $wrap[0].style.width && $wrap[0].style.width.indexOf("px")<0){
            if ($wrap.find(".fs-label").css("-webkit-line-clamp") == "1"){
                return;
            }
            let width = $wrap[0].getBoundingClientRect().width;
            console.info(width)
            if (width ==0){
                return;
            }
            let width1 = $(f).closest("td").width();
            $(f).closest("td").css("width",width1+"px")
            $($wrap[0]).css("width",width);
            $(f).css("width",width);
            $(f).find(".fs-label").css("width",(width-20)+"px");
        }
    }

    /**
     * Events
     */
    window.fSelect = {
        'active': null,
        'idx': -1
    };

    function setIndexes($wrap) {
        $wrap.find('.fs-option:not(.hidden)').each(function(i, el) {
            $(el).attr('data-index', i);
            $wrap.find('.fs-option').removeClass('hl');
        });
        $wrap.find('.fs-search input').focus();
        window.fSelect.idx = -1;
    }

    function setScroll($wrap) {
        var $container = $wrap.find('.fs-options');
        var $selected = $wrap.find('.fs-option.hl');

        var itemMin = $selected.offset().top + $container.scrollTop();
        var itemMax = itemMin + $selected.outerHeight();
        var containerMin = $container.offset().top + $container.scrollTop();
        var containerMax = containerMin + $container.outerHeight();

        if (itemMax > containerMax) { // scroll down
            var to = $container.scrollTop() + itemMax - containerMax;
            $container.scrollTop(to);
        }
        else if (itemMin < containerMin) { // scroll up
            var to = $container.scrollTop() - containerMin - itemMin;
            $container.scrollTop(to);
        }
    }

    $(document).on('click', '.multiple .fs-option', function() {
        var $wrap = $(this).closest('.fs-wrap');
        if ($wrap.hasClass('multiple')) {
            var selected = [];
            var options = [];
            var elems = [];
            $(this).toggleClass('selected');
            $wrap.find('.fs-option').each(function(i, el) {
                options.push(el);
                elems.push($(el).find("input")[0]);
                if ($(el).hasClass("selected")){
                    selected.push($(el).attr('data-value'));
                }
            });
        }
        else {
            var selected = $(this).attr('data-value');
            $wrap.find('.fs-option').removeClass('selected');
            $(this).addClass('selected');
            $wrap.find('.fs-dropdown').hide();
        }
        var $xformflag = $wrap.closest("xformflag");
        var name = $xformflag.attr("id").substring("_xform_".length);
        var $valField = $xformflag.find('[name="'+ name +'"]');
        $valField.val(selected.join(";"));
        $xformflag.fSelect('reloadDropdownLabel');
        __xformDispatch(selected.join(";"),elems);
        //触发值改变事件，兼容数据填充
        $valField.trigger("change");
    });

    //监听选择框事件
    $(document).on("relation_setvalue",function(event,src,val){
        var $wrap = src;
        var	elems = [],
            selected = [];
        $wrap.find('.fs-option').each(function(i, el) {
            var arr = val ? val.split(";") : [];
            $(el).removeClass("selected");
            for (var i = 0; i < arr.length; i++){
                if (arr[i] == $(el).attr("data-value")){
                    $(el).toggleClass('selected');
                    break;
                }
            }
            elems.push($(el).find("input")[0]);
            if ($(el).hasClass("selected")){
                selected.push($(el).attr('data-value'));
            }
        });
        var $xformflag = $wrap.closest("xformflag");
        var name = $xformflag.attr("id").substring("_xform_".length);

        //这里不能删除，会审驳回是从这里获取值
        var $valFieldVal = $xformflag.find('[type="hidden"][name="'+ name +'"]');
        $valFieldVal.val(selected.join(";"));

        var $valField = $xformflag.find('[name="'+ name +'"]');
        $valField.val(selected.join(";"));
        $xformflag.fSelect('reloadDropdownLabel');
        __xformDispatch(selected.join(";"),elems);
        //触发值改变事件，兼容数据填充
        $valField.trigger("change");
    });

    $(document).on('keyup', '.fs-search input', function(e) {
        if (40 == e.which) {
            $(this).blur();
            return;
        }

        var $wrap = $(this).closest('.fs-wrap');
        var keywords = $(this).val();

        $wrap.find('.fs-option, .fs-optgroup-label').removeClass('hidden');
        var $iconDel =  $wrap.find(".fs-search-icon-del");
        if ('' != keywords) {
            $iconDel.show();
            $wrap.find('.fs-option').each(function() {
                var regex = new RegExp(keywords, 'gi');
                if (null === $(this).find('.fs-option-label').text().match(regex)) {
                    $(this).addClass('hidden');
                }
            });

            $wrap.find('.fs-optgroup-label').each(function() {
                var num_visible = $(this).closest('.fs-optgroup').find('.fs-option:not(.hidden)').length;
                if (num_visible < 1) {
                    $(this).addClass('hidden');
                }
            });
        } else {
            $iconDel.hide();
        }

        setIndexes($wrap);
    });

    function removeFreezeClass($wrap) {
        var closestTd = $wrap.closest("td");
        if (closestTd.hasClass('freeze_left_col')) {
            closestTd.removeClass('freeze_left_col');
            closestTd.attr("_class", "freeze_left_col");
        }
    }

    function addFreezeClass($wrap) {
        var closestTd = $wrap.closest("td");
        if (closestTd.attr('_class')) {
            closestTd.addClass("freeze_left_col");
            closestTd.removeAttr('_class');
        }
    }

    //#170850,事件修改为mousedown
    $(document).on('mousedown', function(e) {
        var $el = $(e.target);
        var $wrap = $el.closest('.fs-wrap');
        if (0 < $wrap.length && $wrap.closest('xformflag').attr("flagtype") === "xform_fSelect") {
            if ($el.hasClass('fs-label')) {
                window.fSelect.active = $wrap;
                var is_hidden = $wrap.find('.fs-dropdown').hasClass('hidden');
                //发布必填校验
                $wrap.trigger("doValidate");
                $('.fs-dropdown', $wrap).addClass('hidden');

                if (is_hidden) {
                    var _readonly = $wrap.attr("_readonly");
                    if (typeof _readonly === "undefined"){
                        $wrap.find('.fs-dropdown').removeClass('hidden');
                        removeFreezeClass($wrap);
                    }
                }
                else {
                    $wrap.find('.fs-dropdown').addClass('hidden');
                    addFreezeClass($wrap);
                }
                setIndexes($wrap);
            }
        }else {
            //发布必填校验
            $el.trigger("doValidate");
            $('[flagtype="xform_fSelect"] .fs-dropdown').addClass('hidden');
            $('[flagtype="xform_fSelect"] .fs-dropdown').each(function(index, obj){
                addFreezeClass($(obj));
            });
            window.fSelect.active = null;
        }
    });

    $(document).on('keydown', function(e) {
        var $wrap = window.fSelect.active;

        if (null === $wrap) {
            return;
        }
        else if (38 == e.which) { // up
            e.preventDefault();

            $wrap.find('.fs-option').removeClass('hl');

            if (window.fSelect.idx > 0) {
                window.fSelect.idx--;
                $wrap.find('.fs-option[data-index=' + window.fSelect.idx + ']').addClass('hl');
                setScroll($wrap);
            }
            else {
                window.fSelect.idx = -1;
                $wrap.find('.fs-search input').focus();
            }
        }
        else if (40 == e.which) { // down
            e.preventDefault();

            var last_index = $wrap.find('.fs-option:last').attr('data-index');
            if (window.fSelect.idx < parseInt(last_index)) {
                window.fSelect.idx++;
                $wrap.find('.fs-option').removeClass('hl');
                $wrap.find('.fs-option[data-index=' + window.fSelect.idx + ']').addClass('hl');
                setScroll($wrap);
            }
        }
        else if (32 == e.which || 13 == e.which) { // space, enter
            $wrap.find('.fs-option.hl').click();
        }
        else if (27 == e.which) { // esc
            $('.fs-dropdown').addClass('hidden');
            window.fSelect.active = null;
        }
    });

    // 新增或者复制行时,复制文本
    $(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
        var row = argus.row;
        var fSelectXFormFlags = $(row).find("[flagtype='xform_fSelect']");
        for (var i = 0; i < fSelectXFormFlags.length; i ++) {
            var $xformflag = $(fSelectXFormFlags[i]);
            var name = $xformflag.attr("id").substring("_xform_".length);
            var $valField = $xformflag.find('[name="'+ name +'"]');
            if ($valField.val()){
                var selectedValArr = $valField.val().split(";");
                $xformflag.find('.fs-option:not(.hidden)').each(function(i, el) {
                    var optionVal = $(el).attr('data-value');
                    if(selectedValArr.indexOf(optionVal) > -1){
                        $(el).addClass("selected");
                    }
                });
                setPxForFSelect($xformflag.closest(".select_div_box.xform_fselect"));
                $xformflag.fSelect('reloadDropdownLabel');
                //触发值改变事件，兼容数据填充
                $valField.trigger("change");
            }
        }
    });

    //监听必填校验事件
    $(document).on("doValidate",function(){
        $('.fs-dropdown').each(function(){
            $wrap = $(this).closest(".fs-wrap");
            var context = $wrap.context;
            var inputValidation = $wrap.prev().children()[0];
            // 下拉框如果展开则做必填校验
            if(undefined != context && undefined != context.className  && context.className.indexOf("hidden")== -1){
                sysValidator(inputValidation);
            }
        });
    });
    function sysValidator(inputValidation){
        var _self = validation, oElement = new Elements();
        if (oElement.valiateElement(inputValidation)) {
            _self.validateElement(inputValidation);
        }
    }

    function doValidator($wrap){
        var selected = [];
        var isPass = true;
        var failMsg = XformObject_Lang.validateMsg;
        if (typeof $wrap !== "undefined"){
            var $options = $wrap.find(".fs-options");
            var leastNItem = $options.attr("leastnitem");
            if (leastNItem && (leastNItem = parseInt(leastNItem))){
                $wrap.find('.fs-option').each(function(i, el) {
                    if ($(el).hasClass("selected")){
                        selected.push(el);
                    }
                });
                isPass = selected.length >= leastNItem;
            }
        }else{

        }

        var _elm = $wrap[0];
        if (!isPass){
            _elm.type="fSelect";
            var _elements = new Elements();
            _elements.serializeElement(_elm);
        }
        var title = $wrap.find(".fs-label").attr("title");
        var leastNItem = $wrap.find(".fs-options").attr("leastnitem");
        failMsg = failMsg.replace("{title}",title).replace("{n}",leastNItem);
        var defaultOpt = { where: 'beforeend'};
        var _reminder = new Reminder(_elm,failMsg,"",defaultOpt);
        (!isPass) ? _reminder.show() : _reminder.hide();
        return isPass;
    }
    //监听提交事件
    Com_Parameter.event["submit"].unshift(function(){
        return true;
    });
    function doValidator_test(value,$wrap){
        var selected = [];
        var isPass = true;
        var failMsg = XformObject_Lang.validateMsg;
        if (typeof $wrap !== "undefined"){
            var $options = $wrap.find(".fs-options");
            var leastNItem = $options.attr("leastnitem");
            if (leastNItem && (leastNItem = parseInt(leastNItem))){
                $wrap.find('.fs-option').each(function(i, el) {
                    if ($(el).hasClass("selected")){
                        selected.push(el);
                    }
                });
                isPass = selected.length >= leastNItem;
            }
        }else{

        }

        var _elm = $wrap[0];
        if (!isPass){
            _elm.type="fSelect";
            var _elements = new Elements();
            _elements.serializeElement(_elm);
        }
        var title = $wrap.find(".fs-label").attr("title");
        var leastNItem = $wrap.find(".fs-options").attr("leastnitem");
        failMsg = failMsg.replace("{title}",title).replace("{n}",leastNItem);
        var defaultOpt = { where: 'beforeend'};
        // var _reminder = new Reminder(_elm,failMsg,"",defaultOpt);
        //(!isPass) ? _reminder.show() : _reminder.hide();
        return isPass;
    }
    Com_AddEventListener(window, 'load', function(){
        var tip = XformObject_Lang.validateMsg;
        tip = "{name}"+tip.replace("{title}","");
        //扩展的校验器定义
        var xform_fSelect_script = {
            'xform_fSelect_script(n)' : {
                error : tip,
                test  : function(value, elem) {
                    var t_elem =$(elem).parent().next();
                    var leastNItem = t_elem.find(".fs-options").attr("leastnitem");
                    return doValidator_test(value,t_elem)
                }
            }
        }
        function call(){

        }
        var form = document.forms[0];
        var _validation;
        if (form && $KMSSValidation) {
            if($KMSSValidation.forms && $KMSSValidation.forms[form.name || form]){
                _validation = $KMSSValidation.forms[form.name || form];
            }
            if (_validation ==  null) {
                _validation = $GetKMSSDefaultValidation();
            }
        }
        if (_validation != null) {
            validation =_validation;
            _validation.addValidators(xform_fSelect_script);
            //_validation.validate();
        }
    });

	 function fselect_ready(tbObj) {
	     var context = tbObj || document;
        $("xformflag[flagtype='xform_fSelect']", context).fSelect();
    }

    /** 监听高级明细表 */
    $(document).on("detailsTable-init", function(e, tbObj){
        fselect_ready(tbObj);
    })

    $(function() {
        fselect_ready();
    });

    //支持属性变更控件
    if(window['$form']){
        var _regist = $form.regist;
        _regist({
            support:function(target){
                if(target.type=='fSelect'){
                    var cache = target.cache;
                    if(cache.fSelect == null){
                        // 缓存中没有，构建缓存
                        var fSelect = this.getFSelect(target);
                        cache.fSelect = fSelect;
                    }
                    return true;
                }
                return false;
            },
            getFSelect:function(target){
                if (target.fSelect){
                    return target.fSelect;
                }
                var _element = target.root;
                var $wrap = $(_element).find(".fs-wrap");
                return $wrap[0];
            },
            readOnly : function(target, value){
                var fSelect = this.getFSelect(target);
                if (value){
                    $(fSelect).attr("_readOnly","readOnly");
                }else{
                    $(fSelect).removeAttr("_readOnly");
                }
            },
            required : function(target, value){
                var fSelect;
                if(target.cache.fSelect){
                    fSelect = target.cache.fSelect;
                }else{
                    fSelect = this.getFSelect(target);
                }
                if(value == null){
                    var options = $(fSelect).find(".fs-options");
                    return typeof options.attr("leastNItem") != "undefined";
                }
                return _requiredExec(fSelect,value);
            }
        });
    }
    function _requiredExec(fSelect,value){
        if(value == null){
            value = true;
        }
        var xformflag = $(fSelect).closest("xformflag");
        var requirdFlag = false;
        var span;
        if(xformflag){
            span = xformflag.find("span.txtstrong");
            if(span.length > 0){
                requirdFlag = true;
            }
        }else{
            return false;
        }
        var options = $(fSelect).find(".fs-options");
        if(value){
            // 设置必填
            options.attr("leastNItem","1");
            if(!requirdFlag){
                xformflag.append("<span class='txtstrong'>*</span>");
            }
        }else{
            // 设置非必填
            options.removeAttr("leastNItem");
            if(requirdFlag){
                span.remove();
            }
        }
        return true;
    }
})(jQuery);