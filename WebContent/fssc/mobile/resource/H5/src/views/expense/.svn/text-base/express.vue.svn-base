<template>
   <timeline>
      <timeline-item v-for="(item,index) in expressList" :key="index">
        <h4 class="recent" v-if="index==0">{{item.AcceptStation}}</h4>
        <h4 v-else> {{item.AcceptStation}}</h4>
        <p class="recent" v-if="index==0">{{item.AcceptTime}}</p>
        <p v-else>{{item.AcceptTime}}</p>
      </timeline-item>
   </timeline>
</template>


<script>
  export default {
  name: "ExpenseDetail",
  data() {
    return {
      expressList:[]
    };
  },
  activated(){
  	this.expressList=[];
    this.loadKdniaoDocs();
  },
  mounted(){
    this.loadKdniaoDocs();
  },
  methods:{
    loadKdniaoDocs(){
      let params=new URLSearchParams();
      params.append('fdModelId',this.$route.query.id);
      this.$api.post(serviceUrl + "loadKdniaoDocs&cookieId=" + LtpaToken, params).then(resData => {
          if (resData.data.result == "success") {
            this.expressList = resData.data.data;
          } else {
            console.log(resData.data.message);
          }
        }).catch(function(error) {
          console.log(error);
        });
    }
  }
}
</script>