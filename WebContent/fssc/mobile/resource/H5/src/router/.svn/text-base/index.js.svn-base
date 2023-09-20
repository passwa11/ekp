import Vue from 'vue'
import Router from 'vue-router'

import Main from '@views/main/index'
import Me from '@views/me/index'
import BankCardEdit from '@views/me/bankCardEdit'
import Report from '@views/report/index'
import ExpenseList from '@views/expense/list'
import ExpenseNew from '@views/expense/new'
import ExpenseDetail from '@views/expense/detail'
import ExpenseDetailView from '@views/expense/detailView'
import ExpenseManualEdit from '@views/expense/manualEdit'
import ExpenseAddAccount from '@views/expense/addAccount'
import ExpenseViewAccount from '@views/expense/viewAccount'
import FeeList from '@views/fee/list'
import FeeNew from '@views/fee/new'
import FeeNewTravel from '@views/fee/newTravel'
import FeeDetailView from '@views/fee/detailView'
import FeeViewTravel from '@views/fee/viewTravel'
import FeeEditTravel from '@views/fee/editTravel'
import NewManual from '@views/manual/new'
import InvoiceDetail from '@views/invoice/detail'
import MyInvoiceTitleList from '@views/me/invoiceTitleList'
import MyInvoiceTitleDetail from '@views/me/invoiceTitleDetail'
import MyInvoiceTitleEdit from '@views/me/invoiceTitleEdit'
import MyInvoiceList from '@views/me/invoiceList'
import BankCardList from '@views/me/bankCardList'
import ApprovalList from '@views/approval/list'
import Express from '@views/expense/express'
import ThirdTaxi from '@views/third/taxi'
import ExpenseManualView from '@views/expense/manualView'
import ExpenseAddTravel from '@views/expense/travelEdit'
import ExpenseViewTravel from '@views/expense/travelView'
import ExpenseAddInvoice from '@views/expense/invoiceEdit'
import ExpenseViewInvoice from '@views/expense/invoiceView'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '',
      redirect: 'index'
    }, {
      path: '/expense/travelView',
      name: 'expenseViewTravel',
      component: ExpenseViewTravel,
      meta: {
        name: '查看行程',
        navShow: false
      }
    }, {
      path: '/expense/travelEdit',
      name: 'expenseAddTravel',
      component: ExpenseAddTravel,
      meta: {
        name: '编辑行程',
        navShow: false
      }
    }, {
      path: '/expense/invoiceView',
      name: 'expenseViewInvoice',
      component: ExpenseViewInvoice,
      meta: {
        name: '查看发票',
        navShow: false
      }
    }, {
      path: '/expense/invoiceEdit',
      name: 'expenseAddInvoice',
      component: ExpenseAddInvoice,
      meta: {
        name: '编辑发票',
        navShow: false
      }
    }, {
      path: '/index',
      name: 'index',
      component: Main,
      meta: {
        name: '快报销',
        navShow: true
      }
    }, {
      path: '/report',
      name: 'report',
      component: Report,
      meta: {
        name: '报表',
        navShow: true
      }
    }, {
      path: '/approval/list',
      name: 'approvalList',
      component: ApprovalList,
      meta: {
        name: '审批',
        navShow: false
      }
    }, {
      path: '/expense/list',
      name: 'expenseList',
      component: ExpenseList,
      meta: {
        name: '报销单',
        navShow: false
      }
    }, {
      path: '/expense/new',
      name: 'expenseNew',
      component: ExpenseNew,
      meta: {
        name: '新建报销',
        navShow: false
      }
    }, {
      path: '/expense/detail',
      name: 'expenseDetail',
      component: ExpenseDetail,
      meta: {
        name: '未报费用',
        navShow: false
      }
    }, {
      path: '/expense/addAccount',
      name: 'expenseAddAccount',
      component: ExpenseAddAccount,
      meta: {
        name: '新增账户',
        navShow: false
      }
    }, {
      path: '/expense/viewAccount',
      name: 'expenseViewAccount',
      component: ExpenseViewAccount,
      meta: {
        name: '查看账户',
        navShow: false
      }
    }, {
      path: '/expense/detailView',
      name: 'expenseDetailView',
      component: ExpenseDetailView,
      meta: {
        name: '报销详情',
        navShow: false
      }
    }, {
      path: '/expense/manualEdit',
      name: 'expenseManualEdit',
      component: ExpenseManualEdit,
      meta: {
        name: '费用明细',
        navShow: false
      }
    },{
      path: '/expense/manualView',
      name: 'expenseManualView',
      component: ExpenseManualView,
      meta: {
        name: '费用明细',
        navShow: false
      }
    }, {
      path: '/fee/list',
      name: 'feelList',
      component: FeeList,
      meta: {
        name: '申请单',
        navShow: false
      }
    }, {
      path: '/fee/new',
      name: 'feeNew',
      component: FeeNew,
      meta: {
        name: '新建申请',
        navShow: false
      }
    }, {
      path: '/fee/detailView',
      name: 'feeDetailView',
      component: FeeDetailView,
      meta: {
        name: '申请详情',
        navShow: false
      }
    }, {
      path: '/fee/travel/new',
      name: 'feeNewTravel',
      component: FeeNewTravel,
      meta: {
        name: '新建行程',
        navShow: false
      }
    }, {
      path: '/fee/travel/view',
      name: 'FeeViewTravel',
      component: FeeViewTravel,
      meta: {
        name: '行程',
        navShow: false
      }
    }, {
      path: '/fee/travel/edit',
      name: 'feeEditTravel',
      component: FeeEditTravel,
      meta: {
        name: '编辑行程',
        navShow: false
      }
    }, {
      path: '/manual/new',
      name: 'NewManual',
      component: NewManual,
      meta: {
        name: '记一笔',
        navShow: false
      }
    }, {
      path: '/invoice/detail',
      name: 'invoiceDetail',
      component: InvoiceDetail,
      meta: {
        name: '发票信息',
        navShow: false
      }
    }, {
      path: '/invoice/detail',
      name: 'invoiceDetail',
      component: InvoiceDetail,
      meta: {
        name: '发票抬头',
        navShow: false
      }
    }, {
      path: '/me',
      name: 'me',
      component: Me,
      meta: {
        name: '我的',
        navShow: true
      }
    }, {
        path: '/me/bankCardEdit',
        name: 'bankCardEdit',
        component: BankCardEdit,
        meta: {
          name: '新增银行账户信息',
          navShow: false
        }
      }, {
      path: '/me/invoice/title/list',
      name: 'myInvoiceList',
      component: MyInvoiceTitleList,
      meta: {
        name: '发票抬头',
        navShow: false
      }
    }, {
      path: '/me/invoice/title/detail',
      name: 'myInvoiceDetail',
      component: MyInvoiceTitleDetail,
      meta: {
        name: '发票抬头',
        navShow: false
      }
    }, {
      path: '/me/invoice/title/edit',
      name: 'myInvoiceEdit',
      component: MyInvoiceTitleEdit,
      meta: {
        name: '发票抬头',
        navShow: false
      }
    }, {
      path: '/me/invoice/list',
      name: 'myInvoiceLst',
      component: MyInvoiceList,
      meta: {
        name: '我的发票',
        navShow: false
      }
    }, {
      path: '/expense/express',
      name: 'express',
      component: Express,
      meta: {
        name: '快递信息',
        navShow: false
      }
    }, {
      path: '/me/bankcards',
      name: 'bankCardList',
      component: BankCardList,
      meta: {
        name: '我的账户',
        navShow: false
      }
    }, {
      path: '/third/taxi',
      name: 'ThirdTaxi',
      component: ThirdTaxi,
      meta: {
        name: '打车',
        navShow: false
      }
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
