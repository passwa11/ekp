<template>
    <div class="fs-footer">
        <tabbar>
            <tabbar-item v-for="(item,index) in navList" :key="index" selected :link="item.href">
                <span slot="label">{{item.title}}</span>
            </tabbar-item>   
        </tabbar>
    </div>
</template>

<script>
import { Tabbar, TabbarItem } from "vux";
export default {
  name: "fsFooter",
  data() {
    return {
      navList: [
      {
        title: "首页",
        icon:'',
        href: "index"
      },
      // {
      //   title: "报表",
      //   icon:'',
      //   href: "report"
      // },
      {
        title: "我的",
        icon:'',
        href: "me"
      }
    ]
    }
  },
  components: {
    Tabbar,
    TabbarItem
  }
};
</script>

<style lang="less">
</style>

