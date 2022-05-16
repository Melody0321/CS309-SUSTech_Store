<template>
  <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="register">

    <el-form-item label="用户名" prop="username" required>
      <el-input type="text" v-model="ruleForm.username" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="pass" required>
      <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPass" required>
      <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="学号" prop="sid" required>
      <el-input v-model.number="ruleForm.sid"></el-input>
    </el-form-item>

    <el-form-item label="地址" prop="address" required>
      <el-input v-model.number="ruleForm.address"></el-input>
    </el-form-item>

    <el-form-item label="邮箱" class="em" required>
      <el-input v-model="ruleForm.email"></el-input>
      <el-button type="info" @click="getCode" class="b">获取验证码</el-button>
    </el-form-item>
    <el-form-item label="验证码" class="cd" required>
      <el-input v-model="ruleForm.code"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="info" @click="submit">注册</el-button>
      &emsp;&emsp;
      <el-link href="http://localhost:8080/login">返回登录页面</el-link>
    </el-form-item>
  </el-form>


</template>

<script>
import axios from "axios";

const TIME_COUNT = 60
export default {
  data() {
    var checkMobile = (rule, value, callback) => {
      if (!value) {
        callback(new Error('地址不可为空'))
      } else {
        callback()
      }
    };

    let validateMobile = (rule, value, callback) => {
      if (!value) {
        callback(new Error('邮箱不可为空'))
      } else {
        if (value !== '') {
          let reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
          if (!reg.test(value)) {
            callback(new Error('请输入格式正确有效的邮箱号'))
          }
        }
        callback()
      }
    }
    // 验证码校验
    let validateCheckCode = (rule, value, callback) => {
      if (!value) {
        callback(new Error('验证码不可为空'))
      } else {
        if (value !== '') {
          let reg = /^[0-9]{6}$/
          if (!reg.test(value)) {
            callback(new Error('请输入收到的6位随机验证码'))
          }
        }
        callback()
      }
    }


    var checksid = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('学号不能为空'));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error('请输入数字值'));
        } else {
          if (value < 10000000 || value > 99999999) {
            callback(new Error('请输入正确的学号'));
          } else {
            callback();
          }
        }
      }, 1000);
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        username: '',
        password: '',
        sid:'',
        address: '',

        email: '',
        code: ''
      },
      rules: {
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
        sid: [
          {validator: checksid, trigger: 'blur'}
        ],
        checkMobile: [
          {validator: checkMobile, trigger: 'blur'}
        ],

        email: [
          {validator: validateMobile, trigger: 'blur'}
        ],
        code: [
          {validator: validateCheckCode, trigger: 'blur'}
        ]


      }
    };
  },

  methods: {


    getCode() {
      if (this.ruleForm.email === '') {
        this.$message.error('请先输入邮箱再点击获取验证码')
      } else {
        let regemail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
        if (!regemail.test(this.ruleForm.email)) {
          this.$message({showClose: true, message: '请输入格式正确有效的邮箱号!', type: 'error'})
        } else {
          this.$axios.post('/register/mail', {
            'email': this.ruleForm.email
          }).then(function (res) {
            console.log('111111111')
            //that.loginForm.test_ecode = res.data.ecode
            //console.log(that.User.Test_Ecode)
            if (res.data.code === 400) {
              this.$message({showClose: true, message: '邮箱不正确，验证码发送失败', type: 'error'})
            } else {
              this.$message({showClose: true, message: '已发送验证码,如未收到请检查邮箱是否正确', type: 'success'})
            }
          })

        }
        // 验证码倒计时
        if (!this.timer) {
          this.count = TIME_COUNT
          this.show = false
          this.timer = setInterval(() => {
            if (this.count > 0 && this.count <= TIME_COUNT) {
              this.count--
            } else {
              this.show = true
              clearInterval(this.timer)
              this.timer = null
            }
          }, 1000)
        }
      }
    },
    submit() {
      const data = {
        username: this.ruleForm.username,
        password: this.ruleForm.pass,
        sid:this.ruleForm.sid,
        address: this.ruleForm.address,

        email: this.ruleForm.email,
        code: this.ruleForm.code
      };
      axios
        .post('/register', data)
        .then(res => {
          if (this.ruleForm.email !== '') {
            let regemail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
            if (!regemail.test(this.ruleForm.email)) {
              this.$message({showClose: true, message: '请输入格式正确有效的邮箱号!', type: 'error'})
            } else {

              if (res.data.code === 400) {
                this.$message({showClose: true, message: '用户名已存在，注册失败', type: 'error'})
              } else if (res.data.code === 401) {
                this.$message({showClose: true, message: '验证码错误，注册失败', type: 'error'})
              } else if (res.data.code === 402) {
                 this.$message({showClose: true, message: '该学号已有账户，注册失败', type: 'error'})
                             }
              else {
                this.$message({showClose: true, message: '注册成功', type: 'success'})
                var path = this.$route.query.redirect
                this.$router.replace({path: path === '/register' || path === undefined ? '/' : path})
              }
            }
          } else {
            this.$message({showClose: true, message: '邮箱不可为空！', type: 'error'})
          }

        })
    },
  }
}
</script>


<style scoped>
.register {
  margin: 90px auto;
  width: 600px;
}

.b {
  float: right;
}

/*.em{*/
/*  width: 485px;*/
/*  !*float:left;*!*/
/*}*/


</style>
