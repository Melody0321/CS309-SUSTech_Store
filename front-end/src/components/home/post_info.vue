<template>

  <div>
    <!--页眉-->
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
      <el-menu-item>
        <el-input v-model="input" placeholder="搜索商品"></el-input>
        &emsp;
        <el-button icon="el-icon-search" type="info" plain size="mini" circle></el-button>
      </el-menu-item>
    </el-menu>

    <el-container>
      <el-form :label-position="labelPosition" label-width="70px" :model="formLabelAlign" class="form" label-position="left">
        <el-row>
          <el-col span="8">
            <el-form-item>
              <div>
                <el-radio v-model="radio" label="1" border>售卖</el-radio>
                <el-radio v-model="radio" label="2" border>求购</el-radio>
              </div>
            </el-form-item>
          </el-col>
          <el-col span="8">
            <el-form-item>
              <el-button type="info" class="button">发 布</el-button>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="名 称">
          <el-col span="8">
            <el-input v-model="formLabelAlign.name" style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input>
          </el-col>
        </el-form-item>
        <el-row>
          <el-col span="8">
            <el-row>
              <el-form-item label="地 点">
                <el-input v-model="formLabelAlign.region" style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="数 量">
                <el-input-number v-model="num" controls-position="right" @change="handleChange" :min="1" :max="10" style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input-number>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="价 格">
                <el-input v-model="formLabelAlign.type" style="box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)"></el-input>
              </el-form-item>
            </el-row>
          </el-col>
          <el-col span="14">
            <el-form-item>
              <el-input type="textarea" :rows="9" placeholder="Please input your description here" v-model="textarea" maxlength="80" show-word-limit class="description"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col span="5">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-col>
          <el-col span="5">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-col>
          <el-col span="5">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-col>
          <el-col span="5">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-col>
        </el-row>

      </el-form>


    </el-container>


  </div>
</template>

<script>
export default {
  name: "post_info",
  data() {
    return {
      imageUrl: '',
      text: '',
      textarea: '',
      radio:'1',
      labelPosition: 'right',
      formLabelAlign: {
        name: '',
        region: '',
        type: ''
      },
      num:'1'
    };
  },
  methods: {
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
    handleChange(value) {
      console.log(value);
    },
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
    }
  }
}
</script>

<style scoped>
.avatar-uploader{
  width: 200px;
  height: 200px;
  border: 5px dashed #d9d9d9;
  border-radius: 20px;
  cursor: pointer;
  /*position: relative;*/
  overflow: hidden;
  margin-left: 0px;
  margin-top: 50px;
  display: inline-block;
}
.avatar-uploader .el-upload:hover {
  border-width: 20px;
  border-color: #409EFF;
  border-left: 0px;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 30px;
  height: 30px;
  text-align: center;
  margin-top: 80px;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.description{
  width: 600px;
  height: 200px;
  box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.2);
}
.form{
  width: 2000px;
  margin-left: 100px;
  margin-top: 50px;
  font-family: "Microsoft YaHei",serif;
}
.button{
  width: 200px;
  font-size: 25px;
  border-radius: 15px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.5);
  background-color: #505458;
}
.i {
  float: right;
}

</style>
