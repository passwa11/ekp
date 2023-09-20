
var formOption = {
    formName: 'fsscLoanTransferForm',
    modelName: 'com.landray.kmss.fssc.loan.model.FsscLoanTransfer',
    templateName: '',
    subjectField: 'docSubject',
    mode: ''

    ,
    dialogs: {
        fssc_loan_main_getLoanMain: {
            modelName: 'com.landray.kmss.fssc.loan.model.FsscLoanMain',
            sourceUrl: '/fssc/loan/fssc_loan_main/fsscLoanMainData.do?method=getLoanMain&type=transfer'
        },

        eop_basedata_cost_center_selectCostCenter: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=selectCostCenter'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};
