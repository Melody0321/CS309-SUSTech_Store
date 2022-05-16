<template>
  <el-container>
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
    <el-dialog title="修改密码"
               :visible.sync="passwordVisible"
               width="30%">

      <div class="demo-input-suffix">
        旧密码：
        <el-input v-model="old_password" placeholder="请输入旧密码" style="width: 50%" show-password></el-input>
      </div>
      <br>
      <br>

      <div class="demo-input-suffix">
        新密码：
        <el-input v-model="password" placeholder="请输入新密码" style="width: 50%" show-password></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
              <el-button @click="passwordVisible = false">取 消</el-button>
              <el-button type="primary" @click="modifyPassword">确 定</el-button>
            </span>

    </el-dialog>
    <el-container>
      <el-aside width="800px">
        <div>
          <el-carousel indicator-position="outside" style=" position: absolute;padding-top: 70px;padding-left: 150px; width: 400px; height: 520px;">
            <el-carousel-item v-for="(item, index) in imgList" :key="index">
              <el-image :src="'data:image/jpg;base64,'+imgList[index]"></el-image>
            </el-carousel-item>
          </el-carousel>
        </div>

      </el-aside>
      <el-main>
        <div>
          <div style="color: #2c3e50; position: absolute;padding-top: 6px;right: 43%;font-size: 25px;font-weight: bold">商品详情</div>
          <div style="position: absolute;padding-top: 80px;left: 55%;font-weight: bold;font-size: 20px; text-align: right">标题： {{goods_name}}</div>
          <div style="position: absolute;padding-top: 140px;left: 55%;font-weight: bold;font-size: 20px; text-align: right">价格： ￥{{price}}</div>
          <div style="position: absolute;padding-top: 200px;left: 55%;font-weight: bold;font-size: 20px; text-align: left">剩余数量： {{restNumber}}</div>
          <div style="position: absolute;padding-top: 260px;left: 55%;font-weight: bold;font-size: 20px; text-align: left">备注： {{detail}}</div>
          <el-button style="position: absolute;left: 50%; top: 72%;background-color: #505458; color: white" @click.native="add=true">
            <i class="el-icon-shopping-cart-2"></i>加入购物车
          </el-button>
          <el-button style="position: absolute;left: 60%; top: 72%;background-color: #505458; color: white" @click.native="purchase=true">
            <i class="el-icon-goods"></i>
            立即购买
          </el-button>
          <el-button style="position: absolute;left: 70%; top: 72%;background-color: #505458; color: white" @click.native="chatPage">
            <i class="el-icon-chat-dot-round"></i>
            联系卖家
          </el-button>

        </div>
        <el-dialog title="确认订单"
                   :visible.sync="purchase" width="500px">
          <div style="font-weight: bold;font-size: 16px;">
            标题： {{goods_name}}
          </div>
          <br>
          <br>
          <div style="font-weight: bold;font-size: 16px;">
            单价：￥{{price}}
          </div>
          <br>
          <br>
          <div style="font-weight: bold;font-size: 16px;">
            购买数量：
            <el-input-number v-model="purchaseNumber" :min="1" :max="restNumber"></el-input-number>
          </div>
          <br>
          <br>

          <div>
            <el-button style="background-color: #505458; color: white" @click.native="purchase = false; pay = true">付款</el-button>
          </div>
        </el-dialog>
        <el-dialog title="支付中……"
                    :visible.sync="pay" width="500px">
          <div style="font-weight: bold;font-size: 16px;">
            支付密码：
            <el-input v-model="password" show-password style="width: 200px"></el-input>
          </div>
          <br>
          <br>
          <el-button style="background-color: #505458; color: white" @click.native="purchaseCommit">
            确认支付
          </el-button>
        </el-dialog>
        <el-dialog title="加入购物车"
                    :visible.sync = add width="300px">
          <div style="font-weight: bold;font-size: 16px;">
            数量：
            <el-input-number v-model="purchaseNumber" :min=1 :max="restNumber"></el-input-number>
          </div>
          <br>
          <br>
          <el-button style="background-color: #505458; color: white" @click.native="addCollection">
            确认
          </el-button>
        </el-dialog>
        <div style="color: #2c3e50; position: absolute;padding-top: 440px;left: 15%;font-size: 20px;font-weight: bold">商品评价:</div>
        <div id="comment" style="position: absolute;padding-top: 500px;left: 20%;">
          <div  v-for = "(item,key) in comment">
            <div style="text-align:left;">{{ item.username }}:{{"\xa0\xa0\xa0\xa0\xa0\xa0\xa0"}}{{item.comment}}
              <el-rate v-model="item.score/10 + 3" disabled :show-score="true"></el-rate>
              <br>
            </div>
          </div>
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
      imgList: [],
      currentIndex: 0,
      timer: null,
      add:false,
      password:'',
      pay: false,
      purchase: false,
      passwordVisible:false,
      navList: [
        {name: '/home', navItem: '首页'},
        {name: '/collection', navItem: '购物车'},
        {name: '/chatList', navItem: '消息'},
        {name: '/personal', navItem: '个人中心'}
      ],
      picture: '',
      price: 0,
      detail: '',
      goods_id: 0,
      goods_name:'',
      restNumber:0,
      purchaseNumber:0,
      total_price:0,
      sellerID:'',
      comment:[],
     circleUrl:
       'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
  };
  },
  created() {

    axios.get('http://localhost:8443/personal/11111111').then(resp => {
      this.circleUrl = "data:image/jpg;base64," + resp.data.headshot;
    })
    this.goods_name = this.$route.query.goods_name
    this.picture = this.$route.query.picture
    this.goods_id = this.$route.query.goods_id
    this.price = this.$route.query.price
    this.restNumber = this.$route.query.restNumber
    this.detail = this.$route.query.detail
    this.sellerID = this.$route.query.sellerID
    this.comment = this.$route.query.comment
    this.imgList = this.$route.query.imgList

    axios.get('http://localhost:8443/api/getid',{
      params:{
        goods_id:this.goods_id
      }}).then(res =>{
      this.self_id=res.data[0]
      this.other_id=res.data[1]
      //alert(this.other_id)
    }).catch(
    )
  },
  methods: {
    modifyPassword() {
      this.passwordVisible = false;
      axios.post('/modify_pw', {
        old_password: this.old_password,
        password: this.password
      }).then(response => {
        if (response.data.code === 200) {
          this.$message({showClose: true, message: '修改成功！', type: 'success'});
        } else {
          this.$message({showClose: true, message: '旧密码输入错误，修改失败！', type: 'error'});
        }
      })
    },
    addCollection() {
      this.add = false
      axios.post('/add_cart', {
        goods_id: this.goods_id,
        seller_id: this.sellerID,
        num: this.purchaseNumber,
        price: this.price
      })
      .then(res =>{
        if (res.data.code === 400) {
          this.$message({showClose: true, message: '不能加购自己发布的商品', type: 'error'})
        } else {
          this.$message({showClose: true, message: '成功加入购物车', type: 'success'})
        }
      })
    },
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
    purchaseCommit(){
      this.pay=false
      // this.$message({showClose: true, message:this.goods_id, type:'success'})
      axios
      .post('http://localhost:8443/api/purchase',
        {password: this.password,
              goods_id: this.goods_id,
              number: this.purchaseNumber})
      .then(res => {
        if (res.data.code === 400){
          this.$message({showClose: true, message:'支付密码错误！', type:'error'})
        }
        else if (res.data.code === 401){
          this.$message({showClose: true, message:'余额不足，支付失败！', type:'error'})
        }
        if (res.data.code === 200){
          this.$message({showClose: true, message:'支付成功！', type:'success'})
          this.restNumber = this.restNumber-this.purchaseNumber
        }
      })
    },
    chatPage(){
      axios.get('http://localhost:8443/api/judgeid',
        {params:{
          other_id: this.sellerID
        }})

        .then(res => {
          if (res.data.code === 200){
            this.$router.push({name: 'chat', query: {self_id: this.self_id, other_id: this.other_id}})
          }
          else{
            this.$message({showClose: true, message:'请不要和自己聊天！', type:'error'})
          }
        })

    },

  },


}
</script>

<style scoped>

div{
  size: 20px;
  /*float: left;*/
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 160px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #d3dce6;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
