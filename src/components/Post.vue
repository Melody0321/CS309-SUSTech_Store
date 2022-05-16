
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
        <div style="color: #2c3e50; position: absolute;padding-top: 6px;right: 46%;font-size: 25px;font-weight: bold">求购信息</div>
        <div style="position: absolute;top: 170px;left:60px;">
          <el-row>
            <el-col :span="4" v-for="(item, index) in result" :key="item.goods_id"  :offset="1">
              <el-card shadow="hover" style="width: 230px;height: 120px;" @click.native="checkDetails(item)">
                <div style="padding: 0px;height: 110px;">
                  <div>商品名称： {{item.name}}</div>
                  <br>
                  <br>
                  <div>点击查看详情</div>
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
      searchValue: '',
      address: '',
      passwordVisible: false,
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
      result: [],
      searchTag:'',
      circleUrl:
        'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',

    };
  },
  created() {

    this.searchValue = this.$route.query.value;
    axios.get('http://localhost:8443/post/keyword',{
      params:{
        keyword: this.searchValue
      }
    })
      .then(res =>{
        this.result = res.data
      })
  },
  methods: {
    personalHomepage() {
      this.$router.push('/personal');
    },
    exit() {
      axios.post('/logout', {
        username: ''
      }).then(function (resp) {
        alert('successful')
      })
      this.$router.push('/login');
    },
    searchName(){
      this.$router.push({name: 'Post', query: {value: this.searchValue}});
      this.$router.go(0)
    },
    checkDetails(item){
      this.$router.push({name: 'postDetail', query: {name: item.name,
          post_id: item.post_id, detail: item.detail, user_id: item.user_id}})
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
