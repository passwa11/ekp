var formOption = {
    formName: 'sysPortalPopMainForm',
    modelName: 'com.landray.kmss.sys.portal.model.SysPortalPopMain',
    templateName: '',
    subjectField: 'docSubject',
    mode: ''

    ,
    dialogs: {
        sys_portal_pop_category_selectCategory: {
            modelName: 'com.landray.kmss.sys.portal.model.SysPortalPopCategory',
            sourceUrl: '/sys/portal/pop/sys_portal_pop_category/sysPortalPopCategoryData.do?method=selectCategory'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};