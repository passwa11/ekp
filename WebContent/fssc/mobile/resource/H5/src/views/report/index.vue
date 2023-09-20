<template>
  <div class="fs-report">
    <div class="header-wrap">
      <p class="title">个人报表</p>
      <div class="clearfix">
        <p class="date">(2018年1月1日-2018年6月1日)</p>
        <div class="filter-btn">筛选 <span class="fs-iconfont fs-iconfont-shaixuan"></span></div>
      </div>
    </div>
    <div class="chart-wrap">
      <tab active-color='#4285f4' :line-width=2 v-model="activeTab">
        <tab-item>按时间</tab-item>
        <tab-item >按类型</tab-item>
      </tab>
      <swiper v-model="activeTab" height="200px" :show-dots="false">
        <swiper-item>
          <div id="fsReportChart1" class="container"></div>
        </swiper-item>
        <swiper-item>
          <div id="fsReportChart2" class="container"></div>
        </swiper-item>
      </swiper>
    </div>
    <div class="list-wrap">
      <table>
        <tr>
          <th>月份</th>
          <th>已结算</th>
          <th>报销中</th>
        </tr>
        <tr v-for="(row, index) in listData" :key="index">
          <td>{{row.date}}</td>
          <td :style="{ color: row.settled > 0 && '#34A853' }">{{row.settled}} </td>
          <td :style="{ color: row.settled > 0 && '#EA4335' }">{{row.reimbursement}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'report',
  data () {
    return {
      activeTab: 0,
      listData: [{
        date: '2018年1月',
        settled: 20.00,
        reimbursement: 0.00
      }, {
        date: '2018年1月',
        settled: 0.00,
        reimbursement: 0.00
      }, {
        date: '2018年1月',
        settled: 0.00,
        reimbursement: 0.00
      }, {
        date: '2018年1月',
        settled: 20.00,
        reimbursement: 500.00
      }, {
        date: '2018年1月',
        settled: 230.00,
        reimbursement: 500.00
      }]
    }
  },
  mounted () {
    this.initChart()
  },
  methods: {
    initChart () {
      let fsReportChart1 = echarts.init(document.getElementById('fsReportChart1'))
      let fsReportChart2 = echarts.init(document.getElementById('fsReportChart1'))
      let fsReportChartOption1 = {
        tooltip: {},
        grid: {
          left: 40,
          right: 30,
          top: 25,
          bottom: 28
        },
        xAxis: {
          data: ['1月', '2月', '3月', '4月', '5月', '6月'],
          axisLabel: {
            textStyle: {
              color: '#999'
            }
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          }
        },
        yAxis: {
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          splitLine: {
            show: false
          },
          axisLabel: {
            textStyle: {
              color: '#999'
            }
          }
        },
        series: [{
          type: 'bar',
          itemStyle: {
            normal: {color: 'rgba(0,0,0,0.05)'}
          },
          barGap: '-100%',
          barCategoryGap: '40%',
          data: [100, 100, 100, 100, 100, 100],
          animation: false,
          barWidth: 14
        }, {
          type: 'bar',
          data: [15, 0, 26, 70, 40, 20],
          itemStyle: {
            normal: {color: '#4285f4'}
          },
          barWidth: 14
        }]
      }
      fsReportChart1.setOption(fsReportChartOption1)
      fsReportChart2.setOption(fsReportChartOption1)
    }
  }
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-report {
  padding-bottom: 70px;
  .header-wrap {
    background: #fff;
    padding: 12px 15px 16px 20px;
    position: relative;
    .title {
      font-size: 16px;
      color: #333;
      margin-bottom: 8px;
    }
    .date {
      font-size: 12px;
      color: #999;
      float: left;
    }
    .filter-btn {
      color: #999;
      float: right;
      font-size: 12px;
      position: relative;
      padding: 0 0 0 6px;
      position: relative;
      top: -5px;
      &:after {
        content: '';
        position: absolute;
        left: 0;
        top: 1px;
        bottom: 1px;
        width: 1px;
        background: #999;
        transform: scaleX(0.5);
      }
    }
  }
  .chart-wrap {
    .container {
      height: 200px;
      background: #fff;
    }
    .vux-tab {
      &:before,
      &:after {
        content: '';
        position: absolute;
        left: 0;
        right: 0;
        bottom: 0;
        height: 1px;
        background: @borderColor;
        transform: scaleY(0.5);
        .border-2x
      }
      &:before {
        top: 0;
      }
      .vux-tab-item {
        background: none;
        font-size: 16px;
        color: #333;
      }
    }
  }
  .list-wrap {
    background: #fff;
    margin-top: 10px;
    padding: 0 14px;
    table {
      width: 100%;
      th {
        text-align: left;
        font-size: 14px;
        color: #333;
        font-weight: normal;
        padding: 12px 0;
        position: relative;
        &:after {
          content: '';
          position: absolute;
          left: -14px;
          right: -14px;
          bottom: 0;
          height: 1px;
          background: @borderColor;
        }
      }
      td {
        font-size: 14px;
        color: #666;
        // padding: 12px 0;
        line-height: 45px;
        position: relative;
        &:after {
          content: '';
          position: absolute;
          left: -2px;
          right: 0;
          bottom: 0;
          height: 1px;
          background: #eaeaea;
        }
      }
      tr:last-child>td:after {
        display: none;
      }
    }
  }
}
</style>
