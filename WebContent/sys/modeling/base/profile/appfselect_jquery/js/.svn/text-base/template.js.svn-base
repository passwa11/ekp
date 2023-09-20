(function($) {

    $.fn.fappSelect = function(options) {

        if (typeof options == 'string' ) {
            var settings = options;
        }
        else {
            var settings = $.extend({
                placeholder: listOption.lang.allCategory,
                numDisplayed: 3,
                overflowText: listOption.lang.total+'{n}',
                searchText: listOption.lang.search,
                buttonOK: listOption.lang.buttonOkText,
                buttonQX: listOption.lang.buttonCancelText,
                searchnulltext: listOption.lang.searchnull,
                showSearch: true
            }, options);
        }

        /**
         * Constructor
         */
        function fappSelect(select, settings) {
            this.$select = $(select);
            this.settings = settings;
            this.create();
        }


        /**
         * Prototype class
         */
        fappSelect.prototype = {
            create: function() {
                var multiple = this.$select.is('[multiple]') ? ' multiple' : '';
                this.$select.wrap('<div class="fs-wrap' + multiple + '"></div>');
                this.$select.before('<div class="fs-label-wrap"><div class="fs-label">' + this.settings.placeholder + '</div><span class="fs-arrow"><b class="fs-arrow-bottom"><i class="fs-arrow-bottom-arrow1"></i><i class="fs-arrow-bottom-arrow2"></i></b></span></div>');
                this.$select.before('<div class="fs-dropdown hidden"><div class="fs-options"></div><div class="muiPerformanceDropdownBoxOption"><div class="editing_operation_confirm" onclick="searchAppByCate()">'+ this.settings.buttonOK +'</div><div class="editing_operation_cancel" onclick="clearAppByCate()">'+ this.settings.buttonQX +'</div></div></div>');
                this.$select.addClass('hidden');
                this.$wrap = this.$select.closest('.fs-wrap');
                this.reload();
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
                choices+= '<div class="search-null" style="display: none;"><span>' + this.settings.searchnulltext + '</span></div>'
                return choices;
            },

            reloadDropdownLabel: function() {
                var settings = this.settings;
                var labelText = [];
                //增加鼠标移入悬浮框显示label
                var labelTile = [];
                this.$wrap.find('.fs-option.selected').each(function(i, el) {
                    labelText.push($(el).find('.fs-option-label').text());
                    labelTile.push($(el).find('.fs-option-label').text());
                });

                if (labelText.length < 1) {
                    labelText = settings.placeholder;
                    labelTile = settings.placeholder;
                }
                else if (labelText.length > settings.numDisplayed) {
					 var text = "";
					 var title = "";
					 for (var i = 0;i < settings.numDisplayed; i++){
					     //当选项超过3个且名称过长时显示截取长度为4个字符加...
                        title += labelText[i];
                        title += " ; ";
                         if(labelText[i].length>4){
                             //#161320 分类搜索的选择4项，缩略有误,未超过四个字符时，显示全名。
                             text += labelText[i].substring(0, 4)+ "... ; ";
                         }else{
                             text += labelText[i]+ " ; ";
                         }
                    }
                    labelText = text +settings.overflowText.replace('{n}', labelText.length);
                    labelTile = title +settings.overflowText.replace('{n}', labelTile.length);
                }
                else {
                    labelText = labelText.join(' ; ');
                    labelTile = labelTile.join(' ; ');
                }

                this.$wrap.find('.fs-label').html(labelText);
                //给复选下拉框文本显示增加title元素，实现鼠标移入时显示全部名称
                this.$wrap.find('.fs-label').attr("title",labelTile);
                this.$select.change();
            }
        }


        /**
         * Loop through each matching element
         */
        return this.each(function() {
            var data = $(this).data('fappSelect');

            if (!data) {
                data = new fappSelect(this, settings);
                $(this).data('fappSelect', data);
            }

            if (typeof settings == 'string') {
                data[settings]();
            }
        });
    }


    /**
     * Events
     */
    window.fappSelect = {
        'active': null,
        'idx': -1
    };

    function setIndexes($wrap) {
        $wrap.find('.fs-option:not(.hidden)').each(function(i, el) {
            $(el).attr('data-index', i);
            $wrap.find('.fs-option').removeClass('hl');
        });
        $wrap.find('.fs-search input').focus();
        window.fappSelect.idx = -1;
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

    $(document).on('click', '.fs-option', function() {
        var $wrap = $(this).closest('.fs-wrap');

        if ($wrap.hasClass('multiple')) {
            var selected = [];

            $(this).toggleClass('selected');
            $wrap.find('.fs-option.selected').each(function(i, el) {
                selected.push($(el).attr('data-value'));
            });
        }
        else {
            var selected = $(this).attr('data-value');
            $wrap.find('.fs-option').removeClass('selected');
            $(this).addClass('selected');
            $wrap.find('.fs-dropdown').hide();
        }

        $wrap.find('select').val(selected);
        $wrap.find('select').fappSelect('reloadDropdownLabel');
    });

    $(document).on('keyup', '.fs-search input', function(e) {
        if (40 == e.which) {
            $(this).blur();
            return;
        }

        var $wrap = $(this).closest('.fs-wrap');
        var keywords = $(this).val();

        $wrap.find('.fs-option, .fs-optgroup-label').removeClass('hidden');

        if ('' != keywords) {
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
        }
        //#159726 【关键任务缺陷】【分类搜索】对于搜索结果为空，建议给出提示信息，搜索暂无内容
        var isShowSearchNull=true;
        $('.fs-option').each(function(){
            var optionClassName=$(this).attr("class");
            if(optionClassName.indexOf("hidden") == -1){
                isShowSearchNull = false;
            }
        });
        if(isShowSearchNull){
            $(".search-null").show();
        }else {
            $(".search-null").hide();
        }
        setIndexes($wrap);
    });

    $(document).on('click', function(e) {
        var $el = $(e.target);
        var $wrap = $el.closest('.fs-wrap');

        if (0 < $wrap.length) {
            if ($el.hasClass('fs-label')) {
                window.fappSelect.active = $wrap;
                var is_hidden = $wrap.find('.fs-dropdown').hasClass('hidden');
                $('.fs-dropdown').addClass('hidden');

                if (is_hidden) {
                    $wrap.find('.fs-dropdown').removeClass('hidden');
                }
                else {
                    $wrap.find('.fs-dropdown').addClass('hidden');
                }

                setIndexes($wrap);
            }
        }
        else {
            $('.fs-dropdown').addClass('hidden');
            window.fappSelect.active = null;
            //手动清空搜索框内容。
            $('.fs-search input').val("");
            $('.fs-search input').keyup();
        }
    });

    $(document).on('keydown', function(e) {
        var $wrap = window.fappSelect.active;

        if (null === $wrap) {
            return;
        }
        else if (38 == e.which) { // up
            e.preventDefault();

            $wrap.find('.fs-option').removeClass('hl');

            if (window.fappSelect.idx > 0) {
                window.fappSelect.idx--;
                $wrap.find('.fs-option[data-index=' + window.fappSelect.idx + ']').addClass('hl');
                setScroll($wrap);
            }
            else {
                window.fappSelect.idx = -1;
                $wrap.find('.fs-search input').focus();
            }
        }
        else if (40 == e.which) { // down
            e.preventDefault();

            var last_index = $wrap.find('.fs-option:last').attr('data-index');
            if (window.fappSelect.idx < parseInt(last_index)) {
                window.fappSelect.idx++;
                $wrap.find('.fs-option').removeClass('hl');
                $wrap.find('.fs-option[data-index=' + window.fappSelect.idx + ']').addClass('hl');
                setScroll($wrap);
            }
        }
        else if (32 == e.which || 13 == e.which) { // space, enter
            $wrap.find('.fs-option.hl').click();
        }
        else if (27 == e.which) { // esc
            $('.fs-dropdown').addClass('hidden');
            window.fappSelect.active = null;
        }
    });

})(jQuery);