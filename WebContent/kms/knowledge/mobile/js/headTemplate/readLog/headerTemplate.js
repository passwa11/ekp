define(['mui/createUtils', 'mui/i18n/i18n!kms-knowledge:knowledge.4m.readTime'],

    function(createUtils, msg){

        var h = createUtils.createTemplate;

        var sortItem1 = h('div', {
            dojoType: 'mui/sort/SortItem',
            dojoProps : {
                name : 'fdReadTime',
                subject : msg['knowledge.4m.readTime'],
                value : 'down'
            }
        });


        var properties = h('div', {
            className: 'muiHeaderItemRight',
            dojoType: 'mui/property/FilterItem',
            dojoMixins: 'kms/knowledge/mobile/js/headTemplate/readLog/readLogPropertyMixin'
        });

        return [sortItem1, properties].join('') ;

    });