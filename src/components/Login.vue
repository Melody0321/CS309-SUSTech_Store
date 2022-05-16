<template>
  <div>
    <el-form class="login-container" label-position="left"
             label-width="0px">
      <h3 class="login_title">登录</h3>
      <el-form-item>
        <el-input type="text" v-model="loginForm.username"
                  auto-complete="off" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="loginForm.password"
                  auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>



      <input type="checkbox" v-model="use" id="r1"><label for="r1">记住密码</label>
      <br><br>

      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">登录</el-button>
      </el-form-item>
      <div class="class1" id="r1">
        <el-link href="http://localhost:8080/register">注册</el-link>
      </div>


    </el-form>


  </div>


</template>


<script>

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      responseResult: []
    }
  },
  methods: {
    login() {
      var _this = this
      console.log(this.$store.state)
      this.$axios
        .post('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$message({showClose: true, message: '登录成功！', type: 'success'})
            // var data = this.loginForm
            _this.$store.commit('login', _this.loginForm)
            var path = this.$route.query.redirect
            this.$router.replace({path: path === '/' || path === undefined ? '/home' : path})
          }
          else{
            this.$message({showClose: true, message: '用户不存在或密码错误', type: 'error'})
          }
        })
        .catch(failResponse => {
        })
    },

  }
}
</script>

<style>



.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}


</style>

