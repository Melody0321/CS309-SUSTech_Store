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
        <el-dropdown>
          <span class="el-dropdown-link">
              <el-avatar
                :size="40"
                :src="circleUrl"
                align="center"
                style="border: darkgrey solid 1px"
              ></el-avatar>
            <!--            <i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="personalHomepage">个人中心</el-dropdown-item>
            <el-dropdown-item>修改密码</el-dropdown-item>
            <el-dropdown-item @click.native="exit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu-item>
      <el-menu-item index="/home" class="i">返回主页</el-menu-item>
      <el-menu-item index="/release" style="font-size: x-large;float: right">+</el-menu-item>
      <!--      <el-menu-item index="3" class="i" disabled>消息中心</el-menu-item>-->
      <!--      <el-menu-item index="4" class="i"><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item>-->
      <el-menu-item>
        <el-input v-model="input" placeholder="搜索商品"></el-input>
        &emsp;
        <el-button icon="el-icon-search" type="info" plain size="mini" circle></el-button>
      </el-menu-item>
    </el-menu>


    <el-container>
      <el-aside id="aside-style" width="30%">
        <br>
        <br>
        <div class="img">
          <el-avatar
            :size="240"
            :src="circleUrl"
            align="center"
            style="border: darkgrey solid 1px"
          ></el-avatar>
        </div>
        <br>
        <div class="information">
          <span style="font-weight: bold;font-size: 45px">Name</span>
        </div>
        <div class="information">
          <span>ID</span>
        </div>
        <div class="information">
          <span>E-mail</span>
        </div>
        <br>
        <div>
          <el-button style="width: 60%;height: 35px">
            <div style="padding-bottom: 2px">修改个人资料</div>
          </el-button>
        </div>
        <br>
        <div>
          <span>
              Balance: 111
            &emsp;&emsp;&emsp;
            <el-button style="width: 20%">充值</el-button>
          </span>

        </div>
      </el-aside>
      <div style="width: 100%;font-size: large">
        <br>
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="发布记录" name="first">

            <el-button type="primary" class="add_btn" @click="add">新增</el-button>
            <div class="table">
              <el-table :data="tableData" style="width: 100%;">
                <el-table-column type="expand">
                  <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                      <el-row>
                        <el-form-item label="商品ID"><span>{{ props.row.name }}</span></el-form-item>
                      </el-row>

                      <el-form-item label="商品名称"><span>{{ props.row.name }}</span></el-form-item>
                      <el-form-item label="商品分类"><span>{{ props.row.category }}</span></el-form-item>
                      <el-form-item label="商品价格"><span>{{ props.row.price }}</span></el-form-item>
                      <el-form-item label="商品数量"><span>{{ props.row.number }}</span></el-form-item>
                      <el-form-item label="商品地点"><span>{{ props.row.location }}</span></el-form-item>
                      <el-form-item label="商品描述"><span>{{ props.row.desc }}</span></el-form-item>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column label="日期" width="180">
                  <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.date }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="name"  label="商品名称" width="160" ></el-table-column>
                <el-table-column prop="number" label="数量" min-width="120" ></el-table-column>
                <el-table-column prop="desc" label="商品描述" min-width="200" ></el-table-column>
              </el-table>
            </div>
            <!--新增/编辑界面-->
            <el-dialog style="width: 1800px;height: 1400px;" title="发布商品" :visible.sync="zdydialog">
              <el-form :label-position="labelPosition" label-width="50px" :model="formLabelAlign" class="form" label-position="left">
                <el-form-item>
                  <el-col span="4">
                    <div>
                      <el-radio v-model="radio" label="1" border>售卖</el-radio>
                      <el-radio v-model="radio" label="2" border>求购</el-radio>
                    </div>
                  </el-col>
                </el-form-item>

                <el-row>
                  <el-col span="4">
                    <el-row>
                      <el-form-item label="名 称">
                        <el-input v-model="formLabelAlign.name" style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input>
                      </el-form-item>
                    </el-row>
                    <el-row>
                      <el-form-item label="分 类">
                        <el-select v-model="formLabelAlign.selectvalue" placeholder="请选择">
                          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.label"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-row>
                    <el-row>
                      <el-form-item label="地 点">
                        <el-input v-model="formLabelAlign.region" style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input>
                      </el-form-item>
                    </el-row>
                    <el-row>
                      <el-form-item label="数 量">
                        <el-input-number v-model="formLabelAlign.num" controls-position="right" @change="handleChange" :min="1" :max="10" style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input-number>
                      </el-form-item>
                    </el-row>
                    <el-row>
                      <el-form-item label="价 格">
                        <el-input v-model="formLabelAlign.price" style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input>
                      </el-form-item>
                    </el-row>
                  </el-col>
                  <el-col span="6">
                    <el-row>
                      <el-form-item>
                        <el-input type="textarea" :rows="9" placeholder="Please input your description here" v-model="formLabelAlign.textarea" maxlength="80" show-word-limit class="description"></el-input>
                      </el-form-item>
                    </el-row>
                    <el-row>
                      <el-upload
                        class="avatar-uploader"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                      </el-upload>
                      <el-upload
                        class="avatar-uploader"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                      </el-upload>
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
          <el-tab-pane label="出售记录" name="second">出售记录</el-tab-pane>
          <el-tab-pane label="购买记录" name="third">购买记录</el-tab-pane>
          <el-tab-pane label="求购记录" name="fourth">求购记录</el-tab-pane>

        </el-tabs>
        <!--        <el-button round icon="el-icon-plus">-->
        <!--        </el-button>-->

        <!-- new-->

      </div>
    </el-container>

  </div>
</template>

<script>
export default {

  name: "Personal",
  data() {
    return {
      input:'',
      circleUrl:
        'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
      activeName: 'first',

      radio:'1',
      labelPosition: 'right',
      formLabelAlign: {
        name: '',
        region: '',
        price: '',
        selectvalue: '',
        num:'',
        textarea: '',
      },
      options:[{
        value: 'daily expense', label: '居家日用'},
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
      num:'1',
      tableData: [],
      zdydialog: false,
      formLabelWidth: '80px',
      form: {},
      submitType : "",
    }
  },
  methods: {
    exit() {
      this.$router.push('/login');
    },
    personalHomepage() {
      this.$router.push('/personal');
    },

    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },


    handleClick(tab, event) {
      console.log(tab, event);
    },

    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
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
        location: this.formLabelAlign.region,
        desc:this.formLabelAlign.textarea,
      }
      this.submitType = "add";
      this.zdydialog = true
    },
    submit() {
      this.zdydialog = false;
      var aData = new Date();
      console.log(aData) //Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)

      if(this.submitType === "add"){
        this.form = {
          date: aData.getFullYear() + "-" + (aData.getMonth() + 1) + "-" + aData.getDate(),
          name: this.formLabelAlign.name,
          category: this.formLabelAlign.selectvalue,
          price: this.formLabelAlign.price,
          number: this.formLabelAlign.num,
          location: this.formLabelAlign.region,
          desc:this.formLabelAlign.textarea,
        };
        var pricepattern=/^[0-9]+$/;
        if (this.form.name===''||this.form.category===''||this.form.price===''||this.form.location===''){
          alert(this.form.name);
        }else if(!this.form.price.match(pricepattern)){
          alert("输入无效")
        }
        else {
          this.tableData.push(this.form);
        }

      }else{
      }

    },
    cancel() {
      this.zdydialog = false
    },
    handleChange(value) {
      console.log(value);
    },
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

.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.tableAll{
  width:900px;
  height: 300px;
  /* border: red 1px solid; */
}
.addArea{
  height: 40px;
  width: 900px;
  /* border: red 1px solid; */
}
.add_btn{
  float: right;
  margin-right: 20px;
}
.dialog_input{
  width:220px;
}
.form{
  width: 1700px;
  margin-left: 40px;
  margin-top: 0px;
  font-family: "Microsoft YaHei",serif;
}
.description{
  height: 200px;
  box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.2);
}
.avatar-uploader{
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

</style>
