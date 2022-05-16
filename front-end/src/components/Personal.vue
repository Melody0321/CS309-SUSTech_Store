<template>
  <div class="all">
    <el-menu :default-active="this.$router.path"
             @select="handleSelect"
             class="el-menu-demo"
             mode="horizontal"
             background-color="#222222"
             text-color="#fff"
             active-text-color="#ffd04b"
             router>
      <el-menu-item class="i">
        <el-dropdown style="position: absolute;right: 3%">
          <span class="el-dropdown-link">
              <el-avatar
                :size="40"
                :src="headshot"
                align="center"
                style="border: darkgrey solid 1px"
              ></el-avatar>
<!--                        <i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="personalHomepage">个人中心</el-dropdown-item>
            <el-dropdown-item @click.native="passwordVisible=true">修改密码</el-dropdown-item>
            <el-dropdown-item @click.native="exit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu-item>

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
      <el-menu-item index="/home" class="i">返回主页</el-menu-item>
      <el-menu-item>
        <el-autocomplete
          class="inline-input"
          v-model="searchValue"
          :fetch-suggestions="querySearch"
          placeholder="请输入内容"
          :trigger-on-focus="false"
          @select="handleSelect1"
          suffix-icon="el-icon-search"
          size="medium"
          style="width: 300px;position:relative;right: 1%"
        ></el-autocomplete>
<!--        <el-input v-model="search" placeholder="搜索商品"></el-input>-->
<!--        &emsp;-->
        <el-button icon="el-icon-search" type="info" plain size="mini" circle @click.native="searchName"></el-button>
      </el-menu-item>
      <el-menu-item style="float: right">
        &emsp;
        <el-button type="info" icon="el-icon-message"  plain size="mini" circle @click="chatPage"></el-button>
<!--        <el-badge value="newMessage" v-if="newMessage>0" is-dot style="margin-bottom: 10px;margin-left: -20px" ></el-badge>-->
      </el-menu-item>
    </el-menu>


    <el-container>
      <el-aside id="aside-style" width="30%">
        <br>
        <br>
        <div class="img">
          <el-avatar
            :size="240"
            :src="headshot"
            align="center"
            style="border: darkgrey solid 1px"
            id="headshot"
          ></el-avatar>
        </div>
        <br>
        <div class="information">
          <span style="font-weight: bold;font-size: 45px" id='username'>Name</span>
        </div>
        <div class="information">
          <span id='id'>ID</span>
        </div>
        <div class="information">
          <span id="email" style="font-size: 10px">E-mail</span>
        </div>
        <br>
        <div>
          <el-button style="width: 60%;height: 35px" @click="modify">
            <div style="padding-bottom: 2px">修改个人资料</div>
          </el-button>
          <!--          <el-button @click="test">test</el-button>-->


        </div>
        <br>
        <div>
          <span id='balance'>
            <span id="money">Balance</span>
            &emsp;&emsp;&emsp;
            <el-button style="width: 20%" @click="dialogVisible = true">充值</el-button>
          </span>
          <el-dialog
            title="充  值"
            :visible.sync="dialogVisible"
            width="30%"
          >
            <!--            <span>这是一段信息</span>-->
            <div class="demo-input-suffix">
              充值金额：
              <el-input v-model="rechargeForm.balance" placeholder="请输入充值金额" style="width: 50%"></el-input>
              元
            </div>
            <br>
            <br>
            登录密码：
            <el-input v-model="rechargeForm.password" placeholder="请输入登录密码" style="width: 55%" show-password></el-input>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="recharge">确 定</el-button>
            </span>
          </el-dialog>


        </div>
        <div>
          <br>
          <span id="credit" style="text-align: left">credit</span>
          <br>
          <span style="font-size: xx-small">信誉分低于0分时不可发布商品</span>
        </div>
      </el-aside>
      <div style="width: 100%;font-size: large">
        <br>
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="发布记录" name="first">
            <el-button type="primary" class="add_btn" @click="add">新增</el-button>
            <div class="table">
              <el-table :data="tableData" style="width: 78%;">
                <el-table-column prop="goods.goods_name" label="商品名称" width="160"></el-table-column>
                <el-table-column prop="goods.rest_num" label="剩余数量" min-width="120"></el-table-column>
                <el-table-column prop="goods.detail" label="商品描述" min-width="180"></el-table-column>
                <el-table-column label="操作" width="250">
                  <template slot-scope="scope">

                    <el-button type="primary"  @click="handleEdit(scope.$index, scope.row)">查看详细信息</el-button>
                    <el-dialog title="商品信息"
                               :append-to-body="true"
                               :visible.sync="modifyselling"
                               width="30%">
                        <el-form label-position="left" :model="goods">
                         <el-form-item label="商品名称">
                           {{ goods.goods_name }}
                          </el-form-item>
                          <el-form-item label="商品分类">{{ goods.tag }}</el-form-item>
                          <el-form-item label="商品价格">{{ goods.price }}</el-form-item>
                          <el-form-item label="剩余数量">{{ goods.rest_num }}</el-form-item>
                          <el-form-item label="商品描述">{{ goods.detail }}</el-form-item>
                          <el-form-item>
<!--                            <div>{{show_picture}}</div>-->
                            <el-carousel>
                            <el-carousel-item v-for="(item,index) in show_picture" :key="index">
                              <el-image :src="'data:image/jpg;base64,'+show_picture[index]">
                              </el-image>
                            </el-carousel-item>
                            </el-carousel>
                          </el-form-item>

                            <el-button style="float: right" @click="goodspictureVisible=true">
                              上传图片
                            </el-button>
                            <el-dialog title="上传图片"
                                       :visible.sync="goodspictureVisible"
                                       :append-to-body="true"
                                       width="30%">
                              <el-upload
                                class="avatar-uploader"
                                action="https://jsonplaceholder.typicode.com/posts/"
                                :show-file-list="true"
                                :on-success="handleAvatarSuccess"
                                :http-request="upLoad">
                                <img v-if="imageUrl" :src="imageUrl" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                              </el-upload>
                              <el-button @click="goods_picture(goods.goods_id)">
                                确定
                              </el-button>
                            </el-dialog>
<!--                          </el-form-item>-->
                        </el-form>

                      <el-button @click="modifyselling=false">
                        确定
                      </el-button>
                    </el-dialog>
                    <el-button type="danger" @click="deletegoods(scope.row.goods.goods_id)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <!--新增/编辑界面-->
            <el-dialog style="width: 1700px;height: 700px;" title="发布商品" :visible.sync="zdydialog">
              <el-form :label-position="labelPosition" label-width="50px" :model="formLabelAlign" class="form"
                       label-position="left">

                <el-row>
                  <el-col span=4>
                    <el-row>
                      <el-form-item label="名 称">
                        <el-input v-model="formLabelAlign.name"
                                  style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input>
                      </el-form-item>
                    </el-row>
                    <el-row>
                      <el-form-item label="分 类">
                        <el-select v-model="formLabelAlign.selectvalue" placeholder="请选择">
                          <el-option v-for="item in options" :key="item.value" :label="item.label"
                                     :value="item.label"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-row>
                    <el-row>
                      <el-form-item label="数 量">
                        <el-input-number v-model="formLabelAlign.num" controls-position="right" @change="handleChange"
                                         :min="1"
                                         style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input-number>
                      </el-form-item>
                    </el-row>
                    <el-row>
                      <el-form-item label="价 格">
                        <el-input v-model="formLabelAlign.price"
                                  style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input>
                      </el-form-item>
                    </el-row>
                  </el-col>
                  <el-col span=6>
                    <el-row>
                      <el-form-item>
                        <el-input type="textarea" :rows="9" placeholder="输入商品描述"
                                  v-model="formLabelAlign.textarea" maxlength="80" show-word-limit
                                  class="description"></el-input>
                      </el-form-item>
                    </el-row>
                  </el-col>
                </el-row>
              </el-form>


              <div slot="footer" class="dialog-footer">
                <el-button @click="cancel">取消</el-button>
                <el-button type="primary" @click="submit">确定</el-button>
              </div>
            </el-dialog>
          </el-tab-pane>
          <el-tab-pane label="出售记录" name="second">
            <div class="table">
              <el-table :data="sellRecord" style="width: 78%;">
                <el-table-column type="expand">
                  <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                      <el-form-item label="交易时间"><span>{{ props.row.time }}</span></el-form-item>
                      <el-form-item label="买家ID"><span>{{ props.row.customer_id }}</span></el-form-item>
                      <el-form-item label="商品名称"><span>{{ props.row.name }}</span></el-form-item>
                      <el-form-item label="商品价格"><span>{{ props.row.price }}</span></el-form-item>
                      <el-form-item label="商品数量"><span>{{ props.row.num }}</span></el-form-item>
                      <el-form-item label="商品描述"><span>{{ props.row.detail }}</span></el-form-item>
                      <el-form-item label="成交额（元）"><span>{{ props.row.total_price }}</span></el-form-item>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column prop="customer_id" label="买家" min-width="160"></el-table-column>
                <el-table-column prop="name" label="商品名称" width="160"></el-table-column>
                <el-table-column prop="num" label="数量" min-width="120"></el-table-column>
                <el-table-column prop="total_price" label="总金额" min-width="120"></el-table-column>
              </el-table>
            </div>
          </el-tab-pane>


          <el-tab-pane label="购买记录" name="third">
            <div class="table">
              <el-table :data="buyRecord" style="width: 78%;">
                <el-table-column type="expand">
                  <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                      <el-form-item label="交易时间"><span>{{ props.row.time }}</span></el-form-item>
                      <el-form-item label="卖家ID"><span>{{ props.row.seller_id }}</span></el-form-item>
                      <!--                      <el-form-item label="商品ID"><span>{{ props.row.goods_id }}</span></el-form-item>-->
                      <el-form-item label="商品名称"><span>{{ props.row.name }}</span></el-form-item>
                      <el-form-item label="商品价格"><span>{{ props.row.price }}</span></el-form-item>
                      <el-form-item label="商品数量"><span>{{ props.row.num }}</span></el-form-item>
                      <!--                      <el-form-item label="单位"><span>{{ props.row.unit }}</span></el-form-item>-->
                      <!--                      <el-form-item label="商品地点"><span>{{ props.row.location }}</span></el-form-item>-->
                      <el-form-item label="商品描述"><span>{{ props.row.detail }}</span></el-form-item>
                      <el-form-item label="成交额（元）"><span>{{ props.row.total_price }}</span></el-form-item>
                      <el-form-item></el-form-item>
                      <el-form-item label="评价内容"><span>{{props.row.comment}}</span>
                      </el-form-item>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column prop="seller_id" label="卖家" min-width="160"></el-table-column>
                <el-table-column prop="name" label="商品名称" width="160"></el-table-column>
                <el-table-column prop="num" label="数量" min-width="120"></el-table-column>
                <el-table-column prop="total_price" label="总金额" min-width="120"></el-table-column>
                <el-table-column>

                  <template slot-scope="props">
                    <el-button type="primary" @click="comment(props.row.transactionid)">评价</el-button>
                    </template>
                </el-table-column>
              </el-table>
              <el-dialog title="评价" :visible.sync="comment_visible">
<!--                <el-input v-model="comment_score" autocomplete="off"></el-input>-->

                <div class="block">
                  <span class="demonstration">打分</span>
                  <el-rate v-model="comment_score"></el-rate>
                </div>
                <br>
                <br>
                <span>
                  评价内容：
                  <el-input v-model="comment_text" autocomplete="off" style="width: 60%"></el-input>
                </span>
                <el-button @click="submit_comment">提交</el-button>
              </el-dialog>
            </div>
          </el-tab-pane>


          <el-tab-pane label="求购记录" name="fourth">
            <el-button type="primary" class="add_btn" @click="addpurchase">新增</el-button>
            <div class="table">
              <el-table :data="purchaseRecord" style="width: 90%;">
                <el-table-column prop="name" label="求购商品" width="160">
                </el-table-column>
                <el-table-column prop="detail" label="备注" width="200">
                </el-table-column>
                <el-table-column prop="post_id" v-if="false"></el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-button type="primary" @click="updatepost(scope.row.post_id)">修改</el-button>
                    <el-button type="danger" @click="removes(scope.row.post_id)">删除</el-button>
                  </template>
                </el-table-column>

              </el-table>

              <el-dialog title="编辑信息" :visible.sync="purchasedialog">
                <el-form :model="purchaseform" label-position="left">
                  <el-form-item label="求购商品" :label-width="formLabelWidth" style="width: 400px">
                    <el-input v-model="purchaseform.name" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="备注">
                    <el-input type="textarea" :rows="9" placeholder="请输入求购备注"
                              v-model="purchaseform.context" maxlength="80" show-word-limit style="width: 550px"
                    ></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="cancel">取 消</el-button>
                  <el-button type="primary" @click="submitpurchase">确 定</el-button>
                </div>
              </el-dialog>
            </div>
          </el-tab-pane>


          <el-tab-pane label="购物车" name="fifth">
            <div class="table">
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
          </el-tab-pane>


        </el-tabs>
      </div>

    </el-container>


  </div>
</template>

<script>
import axios from "axios";
import Vue from "vue";

export default {

  name: "Personal",

  data() {
    return {
      pay:false,
      // password:'',
      searchValue: '',
      show_picture:{},
      comment_score:0,
      comment_text:'',
      transactionID:0,
      comment_visible:false,
      editForm:{},
      goods:{},
      goodspictureVisible:false,
      rechargeForm: {
        balance: '',
        password: ''
      },
      modifyselling:false,
      passwordVisible: false,
      old_password: '',
      password: '',
      search: '',
      dialogVisible: false,

      headshot: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
      activeName: 'first',

      radio: '1',
      labelPosition: 'right',
      formLabelAlign: {
        name: '',
        price: '',
        selectvalue: '',
        num: '',
        picture: '',
        textarea: '',
      },
      options: [{
        value: 'daily expense', label: '居家日用'
      },
        {value: 'makeup', label: '个护美妆'},
        {value: 'electric appliance', label: '家用电器'},
        {value: 'electronic product', label: '电子商品'},
        {value: 'book', label: '闲置书籍'},
        {value: 'bike', label: '自行车/用品'},
        {value: 'office supply', label: '办公用品'},
        {value: 'bag', label: '箱包'},
        {value: 'cartoon', label: '动漫/周边'},
        {value: 'clothing', label: '服装'},
        {value: 'instrument', label: '玩具乐器'},
        {value: 'ticket', label: '票务娱乐'},
        {value: 'other', label: '其他'},
      ],
      num: '1',
      tableData: [],
      zdydialog: false,
      formLabelWidth: '80px',
      form: {},
      submitType: "",

      imageUrl: '',
      updating_post: false,


      sellRecord: [],
      buyRecord: [],
      purchaseRecord: [],
      purchaseform: {
        id: '',
        name: '',
        context: '',
      },
      purchasedialog: false,
      shoppingcart: [],

      totalPrice: 0,
      multipleSelection: [],
      file: '',
      modifygoodsnameDialog:false,
      modifygoodstagDialog:false,
      modifygoodspriceDialog:false,
      modifygoodsrestnumDialog:false,
      modifygoodsdetailDialog:false
    }
  },
  created() {
    axios.get('http://localhost:8443/personal/11111111').then(resp => {
      var Balance = document.getElementById("money");
      var username = document.getElementById("username");
      var ID = document.getElementById("id");
      var Email = document.getElementById("email");
      var Credit=document.getElementById("credit");

      console.log(resp.data);

      Balance.innerHTML = "余额：" + resp.data.balance + "元";
      username.innerHTML = resp.data.username;
      ID.innerHTML = resp.data.sid;
      Email.innerHTML = resp.data.email;
      Credit.innerHTML="信誉分： "+resp.data.credit;
      this.headshot = "data:image/jpg;base64," + resp.data.headshot;
      this.self_id=resp.data.sid
      Vue.prototype.$self=resp.data.sid
    })
    axios.get('http://localhost:8443/posted_record').then(resp => {
      this.purchaseRecord = resp.data
    }).catch(
      resp => {
        alert('fail')
      }
    )

    axios.get('http://localhost:8443/sales_record').then(resp => {
      this.sellRecord = resp.data
    }).catch(
      resp => {
        alert('fail')
      }
    )

    axios.get('http://localhost:8443/selling_record').then(resp => {
      this.tableData = resp.data
    }).catch(
      resp => {
        alert('fail')
      }
    )

    axios.get('http://localhost:8443/purchase_record').then(resp => {
      this.buyRecord = resp.data
    }).catch(
      resp => {
        alert('fail')
      }
    )

    axios.get('http://localhost:8443/api/get_carts').then(resp => {
      this.shoppingcart = resp.data
    }).catch(
      resp => {
        alert('fail')
      }
    )
  },
  methods: {
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

    comment(transactionID){
      this.comment_visible=true
      this.transactionID=transactionID
    },

    submit_comment(){
      this.comment_visible=false

      axios.post('http://localhost:8443/api/comment',{
        transaction_id:this.transactionID,
        score:this.comment_score,
        comment:this.comment_text
      }).then(response=>{
        this.$message({showClose: true, message: '评价成功！', type: 'success'});

        axios.get('http://localhost:8443/purchase_record').then(resp => {
          this.buyRecord = resp.data
        }).catch(
          resp => {
            alert('fail')
          }
        )
      })

    },

    deletegoods(ID){
      axios.post('http://localhost:8443/api/modify_goods/delete',{
        goods_id:ID
      }).then(response=>{
        this.$message({showClose: true, message: '删除成功！', type: 'success'});
        axios.get('http://localhost:8443/selling_record').then(resp => {
          this.tableData = resp.data
        }).catch(
          resp => {
            alert('fail')
          }
        )
      })


    },



    handleEdit: function (index, row) {
      this.modifyselling = true;
      this.editForm = Object.assign({}, row);
      this.goods=this.editForm.goods
      this.show_picture=this.editForm.imgs
      // this.show_picture=row.
      // console.log(this.show_picture)
    },

    goods_picture(ID){
      this.goodspictureVisible=false;
      let formdata=new FormData();
      formdata.append('file',this.file)
      axios.post('http://localhost:8443/api/addGoodsPicture/'+ID,formdata).then(response => {
        if (response.data.code === 200) {
          this.$message({showClose: true, message: '上传成功！', type: 'success'});

        }else{
          this.$message({showClose: true, message: '上传失败！', type: 'error'});
        }




      }).catch(response=>{
        alert(ID)
      })

      this.$router.go(0)
    },


    upLoad: function (item) {
      this.file = item.file;
    },

    modify() {
      this.$router.push('/modify');
    },

    recharge() {
      this.dialogVisible = false;
      axios.post('/recharge', {
        num: this.rechargeForm.balance,
        password: this.rechargeForm.password
      }).then(response => {
        if (response.data.code === 200) {
          this.$message({showClose: true, message: '充值成功！', type: 'success'});

        } else {
          this.$message({showClose: true, message: '密码错误！', type: 'error'});
        }
        axios.get('http://localhost:8443/personal/11111111').then(function (resp) {
          var Balance = document.getElementById("money");
          Balance.innerHTML = "余额:" + resp.data.balance;
        })
      });
    },
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
    searchName(){
      this.$router.push({path: '/result', query: {value: this.searchValue, tag: 0, highest: 0, lowest: 0, number: 1}});
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },


    handleClick(tab, event) {
      console.log(tab, event);
    },

    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.file=file.raw;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },

    add() {
      this.form = {
        name: this.formLabelAlign.name,
        category: this.formLabelAlign.selectvalue,
        price: this.formLabelAlign.price,
        number: this.formLabelAlign.num,
        // location: this.formLabelAlign.region,
        desc: this.formLabelAlign.textarea,
      }
      this.submitType = "add";
      this.zdydialog = true
    },
    handleSelect1(item) {
      console.log(item);
    },
    submit() {
      this.zdydialog = false;
      var aData = new Date();
      console.log(aData) //Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)
      if (this.submitType === "add") {
        this.form = {
          date: aData.getFullYear() + "-" + (aData.getMonth() + 1) + "-" + aData.getDate(),
          name: this.formLabelAlign.name,
          category: this.formLabelAlign.selectvalue,
          price: this.formLabelAlign.price,
          number: this.formLabelAlign.num,
          desc: this.formLabelAlign.textarea,
        };
        var pricepattern = /^[0-9]+$/;
        if (this.form.name === '' || this.form.category === '' || this.form.price === ''||this.form.desc==='') {
          this.$message({showClose: true, message: '信息不能为空，发布失败！', type: 'error'});
        } else if (!this.form.price.match(pricepattern)) {
          alert("输入无效")
        } else {
          axios.post('/addOne', {
            goods_name: this.formLabelAlign.name,
            detail: this.formLabelAlign.textarea,
            tag: this.formLabelAlign.selectvalue,
            price: this.formLabelAlign.price,
            rest_num: this.formLabelAlign.num
          }).then(response => {
            if(response.data.code===200){
              this.$message({showClose: true, message: '发布成功！', type: 'success'});
              axios.get('http://localhost:8443/selling_record').then(resp => {
                this.tableData = resp.data
              }).catch(
                resp => {
                  alert('fail')
                }
              )
            }else{
              this.$message({showClose: true, message: '信誉过低，发布失败！', type: 'error'});
              axios.get('http://localhost:8443/selling_record').then(resp => {
                this.tableData = resp.data
              }).catch(
                resp => {
                  alert('fail')
                }
              )
            }

          }).catch(response => {
            this.$message({showClose: true, message: '发布失败！', type: 'error'});
          })


        }

      } else {

      }

    },
    cancel() {
      this.zdydialog = false
    },
    handleChange(value) {
      console.log(value);
    },


    removes(v) {
      axios.post('/delete_post', {
        post_id: v,
        name: this.purchaseform.name,
        detail: this.purchaseform.context
      }).then(response => {
        if (response.data.code === 200) {
          this.$message({showClose: true, message: '删除成功！', type: 'success'});
        }
        axios.get('http://localhost:8443/posted_record').then(resp => {
          this.purchaseRecord = resp.data
        }).catch(
          resp => {
            alert('fail')
          }
        )
      });

    },
    edit(v) {
      console.log(v);
      this.purchasedialog = true;

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
      console.log(this.multipleSelection)
    },

    updatepost(id) {
      this.purchasedialog = true
      this.updating_post = true
      this.purchaseform.id = id
    },

    addpurchase() {
      this.purchasedialog = true
    },
    submitpurchase() {
      this.purchasedialog = false;

      var aData = new Date();
      console.log(aData) //Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)
      this.purchase = {
        // date: aData.getFullYear() + "-" + (aData.getMonth() + 1) + "-" + aData.getDate(),
        name: this.purchaseform.name,
        context: this.purchaseform.context,
      };
      if (this.purchase.name === '' || this.purchase.context === '') {
        alert('信息不完整');
      } else {
        if (this.updating_post === true) {
          this.updating_post = false;
          axios.post('/update_post', {
            post_id: this.purchaseform.id,
            name: this.purchaseform.name,
            detail: this.purchaseform.context
          }).then(response => {
            if (response.data.code === 200) {
              this.$message({showClose: true, message: '修改成功！', type: 'success'});
            }
            axios.get('http://localhost:8443/posted_record').then(resp => {
              this.purchaseRecord = resp.data
            }).catch(
              resp => {
                alert('fail')
              }
            )
          });
        } else {
          axios.post('/post', {
            name: this.purchaseform.name,
            detail: this.purchaseform.context
          }).then(response => {
            if (response.data.code === 200) {
              this.$message({showClose: true, message: '求购成功！', type: 'success'});
            }

            axios.get('http://localhost:8443/posted_record').then(resp => {
              this.purchaseRecord = resp.data
            }).catch(
              resp => {
                alert('fail')
              }
            )
          });
        }


      }
    },
    chatPage() {
      this.$router.push({name: 'chatList', query: {self_id: this.self_id}});
    }

  },
  mounted() {
    this.searchItems = this.loadAll();
  }

}

</script>

<style scoped>

.el-aside {
  height: 90vh;
  /*background-color: darkgrey;*/
  background-size: cover;
}

.information {
  width: 60%;
}

.i {
  float: right;
}

.form {
  width: 1700px;
  margin-left: 40px;
  margin-top: 0px;
  font-family: "Microsoft YaHei", serif;
}

.description {
  height: 200px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
}

.avatar-uploader {
  width: 130px;
  height: 130px;
  border: 5px dashed #d9d9d9;
  border-radius: 20px;
  cursor: pointer;
  /*position: relative;*/
  overflow: hidden;
  margin-left: 20px;
  margin-top: 20px;
  display: inline-block;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 30px;
  height: 30px;
  text-align: center;
  margin-top: 50px;
}

.avatar {
  width: 100px;
  height: 100px;
  display: block;
}

.add_btn {
  float: right;
  margin-right: 20px;
}

.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #2c3e50;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
