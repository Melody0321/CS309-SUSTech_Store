
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
        <span style="color: white; position: absolute;padding-top: 16px;right: 50%;font-size: 25px;font-weight: bold">SUSTech - Store</span>
        <el-autocomplete
          class="inline-input"
          v-model="searchValue"
          :fetch-suggestions="querySearch"
          placeholder="请输入内容"
          :trigger-on-focus="false"
          @select="handleSelect"
          suffix-icon="el-icon-search"
          size="medium"
          style="width: 300px;position:absolute;right: 20%;margin-top: 12px"
        ></el-autocomplete>
        <el-button size="small" style="position:absolute;right: 14%;margin-top: 13px" @click.native="searchName">
          购买
        </el-button>
        <el-button size="small" style="position:absolute;right: 7%;margin-top: 13px" @click.native="searchInfo">
          求购信息
        </el-button>
        <el-dropdown style="position: absolute;padding-top: 10px;right: 3%">
          <span class="el-dropdown-link">
              <el-avatar
                :size="40"
                :src="headshot"
                align="center"
                style="border: darkgrey solid 1px"
              ></el-avatar>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="personalHomepage">个人中心</el-dropdown-item>
            <el-dropdown-item @click.native="passwordVisible=true">修改密码</el-dropdown-item>
            <el-dropdown-item @click.native="exit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

      </el-menu>

    </el-header>
    <el-container>
      <el-aside width="150px"><el-menu
        :default-active="$route.path"
        router
        background-color="white"
        text-color="#222"
        active-text-color="red">
        <span style="font-size: 18px;font-weight: bold">推荐</span>
        <el-menu-item @click.native="searchTag(item)" v-for="(item,i) in tagList" :key="i" >
          {{ item.tagItem }}
        </el-menu-item>
      </el-menu></el-aside>
      <el-main>
        <el-image :src="localImage" style="height: 380px; width: 900px; position: relative;padding-left: 10px">
        </el-image>
        <div style="position: relative;font-size: 25px;font-weight: bold;top: 10px">推荐商品：</div>
        <div style="position: relative;top: 10px;left:20px;">
          <el-row>
            <el-col :span="4" v-for="(item, index) in recommend" :key="item.goods.goods_id"  :offset="1">
              <el-card shadow="hover" style="width: 230px;height: 320px;" @click.native="checkDetails(item)">
                <div style="padding: 10px;height: 310px;">
                  <div><el-image :src= "recommend[index].goods.picture"  class="image" style="width: 100px; height: 150px; position: relative"></el-image></div>
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
  </el-container>
</template>


<script>
import axios from "axios";
import Vue from "vue";

export default {
  name: 'home',
  data() {
    return {
      localImage: require("../assets/home.jpg"),
      searchValue: '',
      passwordVisible: false,
      headshot:
        'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
      p1: 'https://img-u-3.51miz.com/Templet/00/19/52/19/195219_c3e8071348f03e0b00338825d8d4992d.jpg-0.jpg',
      p2: 'http://p.moimg.net/project/project_20180502_1525258786_7271.jpg?imageMogr2/auto-orient/strip',
      homeImage: 'https://img.51miz.com/Templet/00/19/52/14/195214_a87e5cfda3f3b26edc70e295d7bab2d1.jpg-0.jpg',
      navList: [
        {name: '/home', navItem: '首页'},
        {name: '/collection', navItem: '购物车'},
        {name: '/chatList', navItem: '消息'},
        {name: '/personal', navItem: '个人中心'}
      ],
      searchItems: [],
      state2: '',
      recommend: [],
      tagList:[
        {url: '/result', tagItem:'居家日用', value:'居家日用'},
        {url: '/result', tagItem:'个护美妆', value:'个护美妆'},
        {url: '/result', tagItem:'家用电器', value:'家用电器'},
        {url: '/result', tagItem:'电子商品', value:'电子商品'},
        {url: '/result', tagItem:'闲置书籍', value:'闲置书籍'},
        {url: '/result', tagItem:'自行车/用品', value: '自行车/用品'},
        {url: '/result', tagItem:'办公用品', value: '办公用品'},
        {url: '/result', tagItem:'箱包', value: '箱包'},
        {url: '/result', tagItem:'动漫/周边', value: '动漫/周边'},
        {url: '/result', tagItem:'服装', value: '服装'},
        {url: '/result', tagItem:'玩具乐器', value: '玩具乐器'},
        {url: '/result', tagItem:'票务娱乐', value: '票务娱乐'},
        {url: '/result', tagItem:'其他', value: '其他'}
      ]
    };
  },
  created() {
    axios.get('http://localhost:8443/personal/11111111').then(resp => {

      this.headshot = "data:image/jpg;base64," + resp.data.headshot;
    })

    axios.get('http://localhost:8443/top8').then(res =>{
      let a = res.data;
      a.forEach(function (item, index){
        a[index].goods.picture = "data:image/jpg;base64," + item.goods.picture
      });
      this.recommend = a
    })
  },
  methods: {
    checkDetails(item){
      this.$router.push({name: 'Detail', query: {goods_name: item.goods.goods_name, picture: item.goods.picture,
          goods_id: item.goods.goods_id, price: item.goods.price, detail: item.goods.detail, restNumber: item.goods.rest_num, sellerID: item.goods.seller_id, comment: item.comments, imgList:item.imgs}})
    },
    exit() {
      axios.post('/logout', {
        username: ''
      }).then(function (resp) {
        alert('successful')
      })
      this.$router.push('/login');
    },
    personalHomepage() {
      this.$router.push('/personal');
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
    searchTag(item){
      console.log(item);
      this.$router.push({path: '/result', query: {value: item.value, tag: 1, highest: 0, lowest: 0, number: 1}});
    },
    searchName(){
      this.$router.push({path: '/result', query: {value: this.searchValue, tag: 0, highest: 0, lowest: 0, number: 1}});
    },
    searchInfo(){
      this.$router.push({path: '/post', query: {value: this.searchValue}})
    }
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
.i {
  float: right;
}

</style>
