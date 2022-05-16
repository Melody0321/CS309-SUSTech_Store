<template>
  <el-container>
    <el-header style="height: 63px; width: 100%" >
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
        <span style="color: white; position: absolute;padding-top: 16px;right: 41%;font-size: 25px;font-weight: bold">SUSTech - Store</span>
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
      <el-aside width="800px">
      </el-aside>
      <el-main>
        <div>
          <div style="color: #2c3e50; position: absolute;padding-top: 6px;left: 46%;font-size: 25px;font-weight: bold">求购详情</div>
          <div style="position: absolute;padding-top: 80px;left: 34%;font-weight: bold;font-size: 20px; text-align: right">买家id： {{user_id}}</div>
          <div style="position: absolute;padding-top: 140px;left: 34%;font-weight: bold;font-size: 20px; text-align: right">标题： {{name}}</div>
          <div style="position: absolute;padding-top: 200px;left: 34%;font-weight: bold;font-size: 20px; text-align: right">详情： {{detail}}</div>
          <el-button style="position: absolute;left: 46%; top: 60%;background-color: #505458; color: white" @click.na.native="chatPage">
            <i class="el-icon-chat-dot-round"></i>
            联系卖家
          </el-button>
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
  name: "Detail",
  data() {
    return {
      add:false,
      password:'',
      pay: false,
      passwordVisible:false,
      navList: [
        {name: '/home', navItem: '首页'},
        {name: '/collection', navItem: '购物车'},
        {name: '/message', navItem: '消息'},
        {name: '/chatList', navItem: '个人中心'}
      ],
      detail: '',
      name: '',
      post_id: 0,
      user_id: 0,
      circleUrl:
        'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    };
  },
  created() {
    this.detail = this.$route.query.detail
    this.name = this.$route.query.name
    this.post_id = this.$route.query.post_id
    this.user_id = this.$route.query.user_id
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
    chatPage(){
      alert(this.user_id)
      this.$router.push({name: 'chat', query: {self_id: this.user_id, other_id: this.user_id}})
    },

  },

}
</script>

<style scoped>

div{
  size: 20px;
  /*float: left;*/
}
</style>
