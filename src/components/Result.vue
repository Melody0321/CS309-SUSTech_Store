
<template>
  <el-container>
    <el-header height="63px">
      <el-menu
        :default-active="$route.path"
        router
        mode="horizontal"
        background-color="#1F2D3D"
        text-color="white"
        active-text-color="red"
        style="min-width: 1200px">
        <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
          {{ item.navItem }}
        </el-menu-item>
        <el-autocomplete
          class="inline-input"
          v-model="searchValue"
          :fetch-suggestions="querySearch"
          placeholder="请输入内容"
          :trigger-on-focus="false"
          @select="handleSelect"
          prefix-icon="el-icon-search"
          size="medium"
          style="width: 300px;position: relative;margin-top: 12px"
        ></el-autocomplete>
        <el-button style="margin-top: 12px" size="small" @click.native="searchName">
          Search
        </el-button>
      </el-menu>
    </el-header>
    <el-container>
      <el-main>
        <span style="color: black;padding-top: 15px;font-size: 15px;font-weight: bold">筛选条件:<span v-html="'\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0'"></span></span>
        <span style="font-size: 10px;font-weight: bold">数量:</span>
        <el-input-number v-model="number" :min="0" prop="number" size="small"></el-input-number>
        <span v-html="'\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0'"></span>
        <span style="font-size: 10px;font-weight: bold">价格区间：</span>
        <el-input-number v-model="lowest" :min="0" prop="lowest" size="small"></el-input-number>
        <span>-</span>
        <el-input-number v-model="highest" :min="lowest" prop="highest" size="small"></el-input-number>
        <span v-html="'\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0'"></span>
        <el-button size="small" style="color: white; background-color: #2c3e50" @click.native="searchName">
          commit
        </el-button>
          <div style="position: relative;top: 20px;left:20px;">
            <el-row>
              <el-col :span="4" v-for="(item, index) in result" :key="item.goods.goods_id"  :offset="1">
                <el-card shadow="hover" style="width: 230px;height: 320px;" @click.native="checkDetails(item)">
                  <div style="padding: 0px;height: 310px;">
                    <div><el-image :src= "result[index].goods.picture"  class="image" style="width: 100px; height: 150px; position: relative"></el-image></div>
                    <br>
                    <br>
                    <div>商品名称： {{item.goods.goods_name}}</div>
<!--                    <div>{{item.picture}}</div>-->
                    <br>
                    <br>
                    <div>剩余数量： {{item.goods.rest_num}}</div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
           </div>
      </el-main>
    </el-container>
    <el-footer>

    </el-footer>
  </el-container>
</template>


<script>
import axios from "axios";

export default {
  data() {
    return {
      picture: "../assets/home.jpg",
      searchValue: '',
      address: '',
      lowest: 0,
      highest: 0,
      number: 1,
      p2: 'http://p.moimg.net/project/project_20180502_1525258786_7271.jpg?imageMogr2/auto-orient/strip',
      navList: [
        {name: '/home', navItem: '首页'},
        {name: '/collection', navItem: '购物车'},
        {name: '/chatList', navItem: '消息'},
        {name: '/personal', navItem: '个人中心'}
      ],
      result: 'cnmd',
      searchTag:''
    };
  },
  created() {
    var tag = this.$route.query.tag;
    if (tag === '1'){
      this.searchTag = this.$route.query.value;
    }
    else {
      this.searchValue = this.$route.query.value;
    }
    this.highest = this.$route.query.highest
    this.lowest = this.$route.query.lowest
    this.number = this.$route.query.number
    axios.get('http://localhost:8443/info',{
      params:{
        tag: this.searchTag,
        name: this.searchValue,
        lowest: this.lowest,
        highest: this.highest,
        number: this.number,
      }
    })
    .then(res =>{
      let a = res.data;
      a.forEach(function (item, index){
        a[index].goods.picture = "data:image/jpg;base64," + item.picture
      });
      this.result = a
    })
    .catch(function (error){
      this.result = 'error'
    })
  },
  methods: {
    searchName(){
      this.$router.push({name: 'Result', query: {value: this.searchValue, tag:0, highest: this.highest, lowest: this.lowest, number: this.number}});
      this.$router.go(0)
    },
    checkDetails(item){
      this.$router.push({name: 'Detail', query: {goods_name: item.goods.goods_name, picture: item.goods.picture,
          goods_id: item.goods.goods_id, price: item.goods.price, detail: item.goods.detail, restNumber: item.goods.rest_num, sellerID: item.goods.seller_id, comment: item.comments}})
    },
    querySearch(queryString, cb) {
      var searchItems = this.searchItems;
      var results = queryString ? searchItems.filter(this.createFilter(queryString)) : searchItems;
      cb(results);
    },
    createFilter(queryString) {
      return (searchItem) => {
        return (searchItem.value.toLowerCase().indexOf(queryString.toLowerCase()) > -1);
      };
    },
    loadAll() {
      return [
        { "value": "水杯"},
        { "value": "水壶"},
        { "value": "水果"},
        { "value": "香皂" },
        { "value": "香水" },
        { "value": "洗发水" },
        { "value": "沐浴露" },
        { "value": "铅笔" },
        { "value": "钢笔" },
        { "value": "手机" },
        { "value": "耳机" },
        { "value": "线代" },
        { "value": "小说" },
        { "value": "杂志" },
        { "value": "教辅书" },
        { "value": "书" },
        { "value": "笔记本" },
        { "value": "便签" },
        { "value": "零食" },
        { "value": "食品" },
        { "value": "教材" },
      ];
    },
    handleSelect(item) {
      console.log(item);
    },
  },
  mounted() {
    this.searchItems = this.loadAll();
  }
}
</script>


<style scoped>
a{
  text-decoration: none;
}

span {
  pointer-events: none;
}
</style>
