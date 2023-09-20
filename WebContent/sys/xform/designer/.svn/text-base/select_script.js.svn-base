(function($) {
    if(!window.XformObject_Lang){
        XformObject_Lang = {};
    }
    Select_ImportCss(Com_Parameter.ContextPath+'sys/xform/designer/fSelect/fSelect.css');
    function Select_ImportCss(url){
        var link = document.createElement('link');
        link.type = 'text/css';
        link.rel = 'stylesheet';
        link.href = url;
        var head = document.getElementsByTagName('head')[0];
        head.appendChild(link);
    }

    $.fn.Select = function(options) {

        if (typeof options == 'string' ) {
            var settings = options;
        }
        else {
            var settings = $.extend({
                placeholder: XformObject_Lang.FSelectPleaseSelect,
                numDisplayed: 1,
                overflowText: '{n} selected',
                showSearch: false,
                showPleaseSelect: true
            }, options);
        }

        /**
         * Constructor
         */
        function Select(select, settings) {
            this.$wrap = this.$select = $(select);
            this.settings = settings;
            this.isInit = false;
            //this.onEvent();
            this.reloadDropdownLabel();
        }

        /**
         * Prototype class
         */
        Select.prototype = {

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
                        choices += '<div class="fs-option' + selected + '" data-value="' + Com_HtmlEscape($el.prop('value')) + '"><span class="fs-checkbox"><i></i></span><div class="fs-option-label">' + Com_HtmlEscape($el.html()) + '</div></div>';
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
                if (!this.isInit) {
                    this.$wrap.find('.fs-option').each(function(i, el) {
                        $(el).find("input[type='hidden']").attr("select-option", "true");
                    });
                    this.isInit = true;
                }
                if (labelText.length < 1) {
                    labelText = settings.placeholder||XformObject_Lang.FSelectPleaseSelect;
                } else if (labelText.length > settings.numDisplayed) {
                    var text = "";
                    for (var i = 0;i < settings.numDisplayed; i++){
                        text += this.resolveLongText(labelText[i]);
                        text += ";";
                    }
                    labelText = text;
                } else {
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
            var data = $(this).data('select');

            if (!data) {
                var wrap = $(this).find(".fs-wrap");
                if ($(this)[0].parentNode){
                    if ($(this)[0].parentNode.nodeName == "DIV" && $(this)[0].parentNode.style.width != "") {
                        if ($(this)[0].parentNode.style.width === "auto"){
                            //自动宽度的改为120px
                            $(wrap).css("width", "120px");
                        }else {
                            $(wrap).css("width", $(this)[0].parentNode.style.width);
                        }
                    }
                }
                data = new Select(wrap, settings);
                $(this).data('select', data);
            }

            if (typeof settings == 'string') {
                data[settings]();
            }
        });
    }


    /**
     * Events
     */
    window.Select = {
        'active': null,
        'idx': -1
    };

    function setIndexes($wrap) {
        $wrap.find('.fs-option:not(.hidden)').each(function(i, el) {
            $(el).attr('data-index', i);
            $wrap.find('.fs-option').removeClass('hl');
        });
        $wrap.find('.fs-search input').focus();
        window.Select.idx = -1;
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

    $(document).on('click', '.select .fs-option', function() {
        var $wrap = $(this).closest('.fs-wrap');
        var selected = [];
        var options = [];
        var elems = [];
        if ($wrap.hasClass('multiple')) {
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
            selected.push($(this).attr('data-value'));
            elems.push(this);
            $wrap.find('.fs-option').removeClass('selected');
            $(this).addClass('selected');
            $wrap.find('.fs-dropdown').addClass('hidden');
        }
        var $xformflag = $wrap.closest("xformflag");
        var name = $xformflag.attr("id").substring("_xform_".length);
        var $valField = $xformflag.find('[type="text"][name="'+ name +'"]');
        $valField.val(selected.join(";"));
        //发布必填校验
        $wrap.trigger("doSelectValidate", {src: $wrap});
        $xformflag.Select('reloadDropdownLabel');
        // #165139 下拉框会触发两次值改变事件
        //__xformDispatch(selected.join(";"), $valField[0]);
        //触发值改变事件，兼容数据填充
        $valField.trigger("change");
    });

    //监听选择框事件
    $(document).on("relation_setvalue_select",function(event,src,val){
        var $wrap = src;
        var	elems = [],
            selected = [];
        var valArr = val ? val.split(";") : [];
        if (valArr.length == 1) {
            $wrap.find('.fs-option').each(function(i, el) {
                $(el).removeClass("selected");
                for (var i = 0; i < valArr.length; i++){
                    if (valArr[i] == $(el).attr("data-value")){
                        $(el).toggleClass('selected');
                        break;
                    }
                }
                elems.push($(el).find("input")[0]);
                if ($(el).hasClass("selected")){
                    selected.push($(el).attr('data-value'));
                }
            });
        } else {
            $wrap.find('.fs-option').each(function(i, el) {
                $(el).removeClass("selected");
            });
        }
        var $xformflag = $wrap.closest("xformflag");
        var name = $xformflag.attr("id").substring("_xform_".length);

        var $valField = $xformflag.find('input[name="'+ name +'"]');
        $valField.val(selected.join(";"));
        $xformflag.Select('reloadDropdownLabel');
        // #165139 下拉框会触发两次值改变事件
        //__xformDispatch(selected.join(";"), $valField[0]);
        //触发值改变事件，兼容数据填充
        $valField.trigger("change");
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

    $(document).on('mousedown', function(e) {
        var $el = $(e.target);
        var $wrap = $el.closest('.fs-wrap');
        if (0 < $wrap.length && $wrap.closest('xformflag').attr("flagtype") === "xform_select") {
            if ($el.hasClass('fs-label')) {
                window.Select.active = $wrap;
                var isHidden = $wrap.find('.fs-dropdown').hasClass('hidden');
                $('.fs-dropdown', $wrap).addClass('hidden');
                if (isHidden) {
                    var _readonly = $wrap.attr("_readonly");
                    if (typeof _readonly === "undefined"){
                        $wrap.find('.fs-dropdown').removeClass('hidden');
                        removeFreezeClass($wrap);
                    }
                } else {
                    $wrap.find('.fs-dropdown').addClass('hidden');
                    addFreezeClass($wrap);
                }
                setIndexes($wrap);
            }
        } else {
            //发布必填校验
            $(document).trigger("doSelectValidate");
            $('.select .fs-dropdown').addClass('hidden');
            $('.select .fs-dropdown').each(function(index, obj){
                addFreezeClass($(obj));
            });
            window.Select.active = null;
        }

    });

    //监听必填校验事件
    $(document).on("doSelectValidate", function(event, param) {
        var className;
        if (param && param.src) {
            className = param.src.find('.fs-dropdown').attr("class");
            doSelectValidate(param.src);
        } else {
            $('.fs-dropdown').each(function(){
                $wrap = $(this).closest(".fs-wrap");
                className = $(this).attr("class");
                // 下拉框如果展开则做必填校验
                if(className.indexOf("hidden")== -1){
                    doSelectValidate($wrap);
                }
            });
        }
    });

    function doSelectValidate($wrap){
        var xformflag = $wrap.closest("xformflag");
        var name = xformflag.attr("property");
        var validateEle = xformflag.find("[name='" + name + "']")[0];
        var validation = $KMSSValidation();
        if (validation) {
            validation.validateElement(validateEle);
        }
    }

    // 新增或者复制行时,复制文本
    $(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
        var row = argus.row;
        var selectXFormFlags = $(row).find("[flagtype='xform_select']");
        for (var i = 0; i < selectXFormFlags.length; i ++) {
            var $xformflag = $(selectXFormFlags[i]);
            var name = $xformflag.attr("id").substring("_xform_".length);
            var $valField = $xformflag.find('[name="'+ name +'"]');
            if ($valField.val()){
                var selectedValArr = $valField.val().split(";");
                //#162204 修复 明细表下拉菜单导入第二个选项的值，导入后两个值都显示了
                //#start 如果是下拉单选，移除之前选中的项目
                var itemType =  $xformflag[0].parentNode.getAttribute("fd_type");
                if(itemType && itemType == 'select'){
                    $xformflag.find('.fs-option:not(.hidden)').each(function(i, el) {
                        $(el).removeClass("selected");
                    });
                }
                //end
                $xformflag.find('.fs-option:not(.hidden)').each(function(i, el) {
                    var optionVal = $(el).attr('data-value');
                    if(selectedValArr.indexOf(optionVal) > -1){
                        $(el).addClass("selected");
                    }
                });
                $xformflag.Select('reloadDropdownLabel');
                //触发值改变事件，兼容数据填充
                $valField.trigger("change");
            }
        }
    });

    $(function() {
        $("xformflag[flagtype='xform_select']").Select();
    });

    //支持属性变更控件
    if(window['$form']){
        var _regist = $form.regist;
        _regist({
            support:function(target){
                if(target.type=='select'){
                    var cache = target.cache;
                    if(cache.select == null){
                        // 缓存中没有，构建缓存
                        var select = this.getSelect(target);
                        cache.select = select;
                    }
                    if (!cache.select) {
                        return false;
                    }
                    return true;
                }
                return false;
            },
            getSelect:function(target){
                if (target.cache.select){
                    return target.cache.select;
                }
                var _element = target.root;
                var $wrap = $(_element).find(".fs-wrap");
                return $wrap[0];
            },
            val : function(target, value){
                if(value==null){
                    if(target.element){
                        return target.element.val();
                    }
                    return;
                }
                if(target.element){
                    target.element.val(value);
                }
                if(target.display){
                    target.display.val(value);
                }
                if (target.root) {
                    target.root.find('.fs-option').each(function(i, el) {
                        if (value == $(el).attr("data-value")){
                            $(el).toggleClass('selected');
                        }
                    });
                    target.root.Select('reloadDropdownLabel');
                }
                return (target.element || target.display) != null;
            },
            readOnly : function(target, value){
                var select = this.getSelect(target);
                if (value){
                    $(select).attr("_readOnly","readOnly");
                }else{
                    $(select).removeAttr("_readOnly");
                }
            },
            getValidateElement : function(target){
                return target.element;
            },
            getRequiredFlagPreElement : function(target){
                return $(this.getSelect(target));
            }
        });
    }

})(jQuery);