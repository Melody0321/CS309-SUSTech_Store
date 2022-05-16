<template>

  <div class="table">

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


    <el-table :data="shoppingcart" ref="multipleTable" style="width: 100%"
              @selection-change="handleSelectionChange" :close-on-click-modal="false"
              :close-on-press-escape="false"
              :header-cell-style="{background:'#f8f8f8',color:'#999'}">
      <el-table-column type="selection" width="60" align="center"/>
      <el-table-column prop="shopping_cart_goods_id" v-if="false"></el-table-column>
      <el-table-column prop="rest_num" v-if="false"></el-table-column>
      <el-table-column prop="shop" label="商品名称" align="center">
        <template slot-scope="scope">
          <span class="shop">{{ scope.row.goods_name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="price" label="单价" align="center">
        <template slot-scope="scope">
          <span class="price">¥{{ scope.row.price }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="number" label="数量" align="center">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.num" @change="scNumChange(scope.row)" :min="1" :max=scope.row.rest_num
                           size="small"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column prop="count" label="小计" align="center">
        <template slot-scope="scope">
          <span class="count">¥{{ scope.row.num*scope.row.price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <i class="el-icon-delete" style="font-size:18px;cursor: pointer;"
             @click="handleDelete(scope.row.shopping_cart_goods_id)"></i>
        </template>
      </el-table-column>
    </el-table>

    总计：<b>{{ totalPrice }}</b>元
    <span>共 <b>{{ shoppingcart.length }}</b> 件商品,已选择 <b>{{ multipleSelection.length }}</b> 件</span>
    <el-button :disabled='multipleSelection.length<=0'
               style="background-color: orange;color: white;border-radius: 22px;margin-top: 20px;width: 80px;margin-left:40px"
               @click="pay=true">
      结算
    </el-button>
    <el-dialog title="支付中……"
               :visible.sync="pay" width="500px">
      <div style="font-weight: bold;font-size: 16px;">
        支付密码：
        <el-input v-model="password" show-password style="width: 200px"></el-input>
      </div>
      <br>
      <br>
      <el-button style="background-color: #505458; color: white" @click.native="buy">
        确认支付
      </el-button>
    </el-dialog>
  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "collection",
  data(){
    return{
      pay:false,
      password:'',
      old_password:'',
      passwordVisible:false,
      navList: [
        {name: '/home', navItem: '首页'},
        {name: '/collection', navItem: '购物车'},
        {name: '/chatList', navItem: '消息'},
        {name: '/personal', navItem: '个人中心'}
      ],
      shoppingcart:[],
      totalPrice: 0,
      multipleSelection: [],
      circleUrl:
        'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    }

  },
  created() {
    axios.get('http://localhost:8443/personal/11111111').then(resp => {
      this.circleUrl = "data:image/jpg;base64," + resp.data.headshot;
    })
    axios.get('http://localhost:8443/api/get_carts').then(resp => {
      this.shoppingcart = resp.data
    }).catch(
      resp => {
        alert('fail')
      }
    )
  },
  methods:{
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
    buy(){
      this.pay=false
      console.log(this.multipleSelection)
      for (var i = 0; i < this.multipleSelection.length; i++) {
        // selection[i].goodTotal = (selection[i].num * selection[i].price).toFixed(2);
        // if (typeof selection[i].goodTotal == 'string') {
        //   selection[i].goodTotal = parseInt(selection[i].goodTotal);
        // }
        // this.totalPrice += selection[i].goodTotal;
        axios.post('http://localhost:8443/api/buy_cart',{
          password: this.password,
          id:this.multipleSelection[i].shopping_cart_goods_id,
          number:this.multipleSelection[i].num
        }).then(res=>{
          if (res.data.code === 400){
            this.$message({showClose: true, message:'支付密码错误！', type:'error'})
          }
          else if (res.data.code === 401){
            this.$message({showClose: true, message:'余额不足，支付失败！', type:'error'})
          }
          if (res.data.code === 200){
            this.$message({showClose: true, message:'支付成功！', type:'success'})
          }
          // this.$message({showClose: true, message: '结算成功！', type: 'success'});
          axios.get('http://localhost:8443/api/get_carts').then(resp => {
            this.shoppingcart = resp.data
          }).catch(
            resp => {
              alert('fail')
            }
          )
        })
          .catch(
            resp => {
              alert('fail')
            }
          )


      }










    },
    handleDelete(v) {
      this.$confirm('确定删除该商品？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //删除数组中指定的元素
        axios.post('/delete_cart', {
          cart_id:v,
          num:1
        }).then(response => {
          if (response.data.code === 200) {
            this.$message({showClose: true, message: '删除成功！', type: 'success'});
          }
          axios.get('http://localhost:8443/api/get_carts').then(resp => {
            this.shoppingcart = resp.data
          }).catch(
            resp => {
              alert('fail')
            }
          )
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    scNumChange(value) {
      console.log(value);
      value.goodTotal = (value.num * value.price).toFixed(2);//保留两位小数
      //增加商品数量需要重新计算商品总价
      this.handleSelectionChange(this.multipleSelection);
    },
    handleSelectionChange(selection) {
      this.multipleSelection = selection;
      this.totalPrice = 0;
      for (var i = 0; i < selection.length; i++) {
        selection[i].goodTotal = (selection[i].num * selection[i].price).toFixed(2);
        if (typeof selection[i].goodTotal == 'string') {
          selection[i].goodTotal = parseInt(selection[i].goodTotal);
        }
        this.totalPrice += selection[i].goodTotal;
      }
    }
  }
}
</script>

<style scoped>

</style>
