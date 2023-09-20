var formOption = {
    formName: 'eopBasedataBudgetItemForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem'

    ,
    dialogs: {
        eop_basedata_budget_item_com_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem',
            sourceUrl: '/eop/basedata/eop_basedata_budget_item/eopBasedataBudgetItemData.do?method=fdParent'
        },

        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    linkValidates: [],
    dataType: {}
};
