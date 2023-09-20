var formOption = {
    formName: 'fsscBudgetDataForm',
    modelName: 'com.landray.kmss.fssc.budget.model.FsscBudgetData',
    templateName: '',
    subjectField: 'fdYear',
    mode: ''

    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
        },

        eop_basedata_company_group_fdGroup: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup',
            sourceUrl: '/eop/basedata/eop_basedata_company_group/eopBasedataCompanyGroupData.do?method=fdGroup'
        },

        eop_basedata_cost_center_selectCostCenter: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=selectCostCenter'
        },

        eop_basedata_budget_item_com_fdBudgetItem: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem',
            sourceUrl: '/eop/basedata/eop_basedata_budget_item/eopBasedataBudgetItemData.do?method=fdBudgetItem'
        },

        eop_basedata_project_project: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataProject',
            sourceUrl: '/eop/basedata/eop_basedata_project/eopBasedataProjectData.do?method=project'
        },

        eop_basedata_inner_order_fdInnerOrder: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder',
            sourceUrl: '/eop/basedata/eop_basedata_inner_order/eopBasedataInnerOrderData.do?method=fdInnerOrder'
        },

        eop_basedata_wbs_fdWbs: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataWbs',
            sourceUrl: '/eop/basedata/eop_basedata_wbs/eopBasedataWbsData.do?method=fdWbs'
        },

        eop_basedata_cost_center_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=fdParent'
        },

        eop_basedata_currency_fdCurrency: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
            sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};
