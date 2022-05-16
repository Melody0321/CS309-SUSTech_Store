<template>

  <el-form status-icon label-width="100px" class="modify">

    <el-form-item label="用户名：" prop="username">
      <div>
              <span id="modify_username" style="float:left;">
                        username
                      </span>
      </div>
    </el-form-item>
    <el-form-item label="学号：" prop="sid">
      <div>
              <span id="modify_sid" style="float:left;">
                        sid
                      </span>
      </div>
    </el-form-item>
    <el-form-item label="邮箱：" prop="email">
      <div>
              <span id="modify_email" style="float:left;">
                        email
                      </span>
      </div>
    </el-form-item>
    <el-form-item label="地址：" prop="address">
      <div>

        <span id="modify_address" style="float:left;">
                          address
                        </span>


      </div>
      <div>
        <el-button style="float: right" @click="addressVisible=true">
          修改
        </el-button>
      </div>

      <el-dialog
        title="修改地址"
        :visible.sync="addressVisible"
        width="30%"
      >
        <div class="demo-input-suffix">
          修改地址：
          <el-input v-model="modifyForm.address" placeholder="请输入新地址" style="width: 50%"></el-input>
        </div>
        <span slot="footer" class="dialog-footer">
              <el-button @click="addressVisible = false">取 消</el-button>
              <el-button type="primary" @click="modify_address">确 定</el-button>
            </span>
      </el-dialog>


    </el-form-item>
<!--    <el-form-item label="头像：" prop="headshot">-->
<!--      <span id="modify_headshot" style="float:left;">-->
<!--        .png-->
<!--      </span>-->
<!--      <div class="avatar">-->
<!--        <img :src="headshot" alt="">-->
<!--      </div>-->
<!--      <div class="img">-->
<!--        <el-avatar-->
<!--          :size="240"-->
<!--          :src="headshot"-->
<!--          align="center"-->
<!--          style="border: darkgrey solid 1px"-->
<!--          id="headshot"-->
<!--        ></el-avatar>-->
<!--      </div>-->
      <el-button style="float: right" @click="headshotVisible=true">
        修改头像
      </el-button>

      <el-dialog title="上传头像"
                 :visible.sync="headshotVisible"
                 width="30%">
<!--        <el-upload-->
<!--          class="avatar-uploader"-->
<!--          action="https://jsonplaceholder.typicode.com/posts/"-->
<!--          :show-file-list="true"-->
<!--          :on-success="handleAvatarSuccess"-->
<!--          :before-upload="beforeAvatarUpload"-->
<!--        :http-request="upLoad">-->
<!--          <img v-if="headshot" :src="headshot" class="avatar">-->
<!--          <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
<!--        </el-upload>-->
        <el-upload
          class="avatar-uploader"
          action="https://jsonplaceholder.typicode.com/posts/"
          :show-file-list="true"
          :on-success="handleAvatarSuccess"
          :http-request="upLoad">
          <img v-if="headshot" :src="this.headshot" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>

        <el-button @click="modify_headshot">
          确定
        </el-button>
      </el-dialog>
<!--    </el-form-item>-->
    <el-button @click="personal">
      返回
    </el-button>
  </el-form>
</template>

<script>
import axios from "axios";

export default {
  name: "ModifyInformation",
  data() {
    return {
      modifyForm: {
        username: '',
        sid: '',
        email: '',
        address: '',
        headshot: ''
      },
      imageUrl: '',
      file:'',
      headshotVisible:false,
      addressVisible:false,
      headshot:''
    }
  },
  created() {
    axios.get('http://localhost:8443/personal/11111111').then(function (resp) {
      var username = document.getElementById("modify_username");
      var sid = document.getElementById("modify_sid");
      var email = document.getElementById("modify_email");
      var address = document.getElementById("modify_address");

      username.innerHTML = resp.data.username;
      sid.innerHTML = resp.data.sid;
      email.innerHTML = resp.data.email;
      address.innerHTML = resp.data.address;
      this.headshot="data:image/jpg;base64," + resp.data.headshot;
      this.modifyForm.headshot="data:image/jpg;base64," + resp.data.headshot;
    });
    // axios.get('http://localhost:8443/headshot').then(function (resp) {
    //   var headshot=document.getElementById("modify_headshot");
    //   headshot.innerHTML=resp.data.headshot;
    // });
  },
  methods: {
    upLoad:function (item){
      this.file=item.file;
    },
    modify_headshot(){
      this.dialogVisible=false;
      this.headshotVisible=false;
      let formdata=new FormData();
      formdata.append('file',this.file)
      axios.post('/modify_headshot',formdata

      ).then(response => {
        if (response.data.code === 200) {
          this.$message({showClose: true, message: '修改成功！', type: 'success'});

        }else{
          this.$message({showClose: true, message: '修改失败！', type: 'error'});
        }
        axios.get('http://localhost:8443/personal/11111111').then(function (resp) {
          // this.modifyForm.headshot = "data:image/jpg;base64," + resp.data.headshot;
          this.headshot="data:image/jpg;base64," + resp.data.headshot;
          // this.modifyForm.headshot = resp.data.headshot;
          // this.headshot=resp.data.headshot;
        });
      });
    },
    modify_address() {
      this.addressVisible=false;
      axios.post('/modify_addr', {
        address: this.modifyForm.address
      }).then(response => {
        if (response.data.code === 200) {
          this.$message({showClose: true, message: '修改成功！', type: 'success'});

        }else{
          this.$message({showClose: true, message: '修改失败！', type: 'error'});
        }
        axios.get('http://localhost:8443/personal/11111111').then(function (resp) {
          var address = document.getElementById("modify_address");
          address.innerHTML = resp.data.address;
        });
      });

    },
    personal() {
      this.$router.push('/personal')
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.file=file.raw;
    },
    postFile(res, file){

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
    }
  }


}
</script>

<style scoped>
.modify {
  /*margin: 90px auto;*/
  /*width: 600px;*/
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
