define(['mui/createUtils'], function (createUtils) {
    var h = createUtils.createTemplate;
    var readLog = h('ul', {
        className: 'kmsKnowledgeIndexList',
        dojoType: 'mui/list/HashJsonStoreList',
        dojoMixins: 'kms/knowledge/mobile/js/headTemplate/readLog/ReadLogTipsMixin'
    });
    return [readLog].join(' ');
});


