<template>
  <div style="height: 93%">
    <!--页眉-->
    <el-menu :default-active="this.$router.path"
             @select="handleSelect"
             class="el-menu-demo"
             mode="horizontal"
             background-color="#222222"
             text-color="#fff"
             active-text-color="#ffd04b"
             router>
      <el-menu-item @click="back" class="i">返回</el-menu-item>
    </el-menu>
    <div class="messagesBox-BigRight-down">
      <div class="record-wrapper">
        <div v-for="record in chat" :key="record.id">

          <div v-if="record.from === 1" class="msg msg-right">
            <!--          <div>-->
            <!--            <img class="img" :src="from1"/>-->
            <!--          </div>-->
            <div class=" info">
              <p class="p-right">{{ record.time }} {{ record.name }}</p>
              <div class="info-content-right">{{ record.message }}</div>
            </div>
          </div>

          <div v-else class="msg msg-left">
            <!--          <div>-->
            <!--            <img class="img" :src="from2"/>-->
            <!--          </div>-->
            <div class=" info">
              <p class="p-left">{{ record.name }} {{ record.time }} </p>
              <div class="info-content-left">{{ record.message }}</div>
            </div>
          </div>
        </div>
      </div>
      <form  ref="input">
        <el-input v-model="content" type="textarea" :rows="9" placeholder="请输入信息" @keydown.enter.native="send($event)" class="d" clearable></el-input>
      </form>
    </div>
  </div>
</template>


<script>
import axios from "axios";
export default {
  name: "chat",

  data() {
    return {
      from1: require("../assets/from1.jpg"),
      from2: require("../assets/from2.jpg"),
      chat: [
      ],
      content:'',
    };

  },
  created() {
    this.self_id=this.$route.query.self_id
    this.other_id=this.$route.query.other_id
    axios.get('http://localhost:8443/api/getselfid').then(resp => {
      console.log(resp.data[0])
      this.self_id=resp.data[0]
    }).catch(
      resp => {
        alert('fail get id')
      }
    );

    //alert(this.other_id)
    axios.get('http://localhost:8443/api/messages',{
      params:{
        other_id:this.other_id
      }}).then(res =>{
      this.record=res.data
      //alert(res.data)
      console.log(this.record)
      for (let i = 0; i < this.record.length; i++) {
        if(this.record[i].sender_id!==this.other_id){
          this.from=1;
        }else this.from=2;
        this.r={
          id:1,
          from: this.from,
          name: this.record[i].sender_id,
          time: this.record[i].time,
          message: this.record[i].text,
        }
        this.content='';
        console.log(this.r);
        this.chat.push(this.r)
      }
      //console.log(this.record)
      //alert(this.record)
    }).catch(
      resp=>{
        //alert('hahaha')
      }
    )
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    send(ev){
      if(ev.keyCode===13){
        if(this.content!==''){
          var _this = this;
          let yy = new Date().getFullYear();
          let mm = new Date().getMonth()+1;
          let dd = new Date().getDate();
          let hh = new Date().getHours();
          let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
          let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
          _this.gettime = yy+'-'+mm+'-'+dd+' '+hh+':'+mf+':'+ss;
          console.log(_this.gettime)
          console.log(ev)
          this.record={
            id:1,
            from: 1,
            name: this.self_id,
            time: _this.gettime,
            message: this.content,
          }

          this.chat.push(this.record)
          axios.post('http://localhost:8443/api/savemessage',
            {sender_id: this.self_id,
              receiver_id: this.other_id,
              text: this.content})
            .then(res => {

              if (res.data.code === 200){
                this.$message({showClose: true, message:'发送成功！', type:'success'})
                //this.restNumber = this.restNumber-this.purchaseNumber
              }
            })
          this.content='';
        }else{
          alert("不能为空");
        }
      }
    },
    back(){
      this.$router.go(-1);
    }
  },

}
</script>

<style scoped>

.messagesBox-BigRight-down {
  /*display: flex;*/
  height: 70%;
  /*!*min-height: 80vh;*!*/
  overflow: hidden;
  overflow-y: scroll;
}

.record-wrapper {
  padding: 55px;
  /*overflow: hidden;*/
  /*overflow-y: scroll ;*/
  /*height: 50%;*/
}

.msg {
  display: flex;
  flex-direction: row;
  margin-top: 15px;
}

.img {
  flex: auto;
  height: 50px;
  width: 50px;
  border-radius: 8px;
}

.msg-right {
  flex-direction: row-reverse;
}

.msg-left {
  flex-direction: row;
}

.p-right {
  font-size: 14px;
  color: rgba(51, 51, 51, 0.8);
  height: 7px;
  line-height: 10px;
  margin-top: 1px;
  margin-right: 10px;
  text-align: right;
}

.p-left {
  font-size: 14px;
  color: rgba(51, 51, 51, 0.8);
  height: 7px;
  line-height: 10px;
  margin-top: 1px;
  margin-left: 10px;
  text-align: left;
}

.info-content-right {
  max-width: 90%;
  padding: 10px;
  font-size: 16px;
  float: right;
  margin-right: 20px;
  position: relative;
  margin-top: 0px;
  background: #A3C3F6;
  text-align: left;
  border-radius: 12px 0px 12px 12px;
  word-wrap: break-word;
}

.info-content-left {
  max-width: 90%;
  padding: 10px;
  font-size: 16px;
  float: left;
  margin-left: 10px;
  position: relative;
  margin-top: 0px;
  background: #eaeaea;
  text-align: left;
  border-radius: 0px 12px 12px 12px;
  word-wrap: break-word;
}

.info {
  width: 60%;
  margin-left: 10px;

}

.d{
  bottom: 10px;
  position: absolute;
  margin-left: -670px;
  width: 90%;
  border-top-width: 30px;
  border-color: #2c3e50;
}
</style>
