
<template>
  <el-container style="margin: 0; padding: 0; height: 100%">
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
                :src="circleUrl"
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
        <el-image :src="localImage" style="height: 400px; width: 800px">
        </el-image>
      </el-main>
    </el-container>
  </el-container>
</template>


<script>
import axios from "axios";

export default {
  name: 'home',
  data() {
    return {
      localImage: require("../assets/home.jpg"),
      searchValue: '',
      passwordVisible: false,
      circleUrl:
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
      tagList:[
        {url: '/result', tagItem:'居家日用', value:'daily expense'},
        {url: '/result', tagItem:'个护美妆', value:'makeup'},
        {url: '/result', tagItem:'家用电器', value:'electric appliance'},
        {url: '/result', tagItem:'电子商品', value:'electronic product'},
        {url: '/result', tagItem:'闲置书籍', value:'book'},
        {url: '/result', tagItem:'自行车/用品', value: 'bike'},
        {url: '/result', tagItem:'办公用品', value: 'office supply'},
        {url: '/result', tagItem:'箱包', value: 'bag'},
        {url: '/result', tagItem:'动漫/周边', value: 'cartoon'},
        {url: '/result', tagItem:'服装', value: 'clothing'},
        {url: '/result', tagItem:'玩具乐器', value: 'instrument'},
        {url: '/result', tagItem:'票务娱乐', value: 'ticket'},
        {url: '/result', tagItem:'其他', value: 'other'}
      ]
    };
  },
  methods: {
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
