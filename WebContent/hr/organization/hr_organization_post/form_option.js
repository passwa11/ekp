
var formOption = {
    formName: 'hrOrganizationPostForm',
    modelName: 'com.landray.kmss.hr.organization.model.HrOrganizationPost',
    templateName: '',
    subjectField: 'fdType',
    mode: ''

    ,
    dialogs: {
    	hr_organization_post_seq: {
            modelName: 'com.landray.kmss.hr.organization.model.HrOrganizationPostSeq',
            sourceUrl: '/hr/organization/hr_organization_post_seq/hrOrganizationPostSeq.do?method=dialogData'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    detailTables: [],
    dataType: {},
    detailNotNullProp: {}
};