define(['mui/createUtils'], function (createUtils) {
    var h = createUtils.createTemplate;
    var index = h('ul', {
        className: 'kmsKnowledgeIndexList',
        dojoType: 'mui/list/HashJsonStoreList',
        dojoMixins: 'kms/knowledge/mobile/js/headTemplate/index/KmsKnowledgeIndexListMixin'
    });
    return [index].join(' ');
});


