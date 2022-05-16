<template>
  <div>
    <!--页眉-->
    <el-menu :default-active="this.$router.path"
             class="el-menu-demo"
             mode="horizontal"
             background-color="#222222"
             text-color="#fff"
             active-text-color="#ffd04b"
             router>
      <el-menu-item index="/personal" class="i">返回</el-menu-item>
    </el-menu>
    <div class="messagesBox">
      <!--左侧-->
      <div class="messagesBox-left">
        <!--搜索框部分-->
        <div class="messagesBox-left-top">
          <div class="messagesBox-left-topOne">
            <span class="messagesBox-left-text">会话</span>
          </div>
        </div>
        <!--部门消息数据部分-->
        <div class="messagesBox-left-group">
          <!--数据1-->
          <div class="messagesBox-left-group-datas" v-for="(item,index) in memberList">
            <!--               :key="item.conversationId"-->
            <!--               :class="{'select-item':index === clickIndex}"-->

            <div class="messagesBox-left-group-datas-ctl">
              <!--点击改变样式，同时渲染聊天列表数据-->
              <div class="messagesBox-left-group-left"
              >
                <div class="messagesBox-left-group-leftContainer">
                  <!--除了数字外，设置is-dot属性，它接受一个Boolean。-->
                  <div class="messagesBox-left-group-leftContainerTwo">
                    <div class="messagesBox-left-group-leftContainerControl">
                      <div class="messagesBox-left-group-leftContainText">
                        <span class="messagesBox-left-group-leftContainText-one" @click="recordList(item)">{{
                            item
                          }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--右侧-->
      <div class="messagesBox-BigRight">
        <!--右侧顶栏-->
        <div class="messagesBox-BigRight-top">
          <div class="messagesBox-BigRight-topRight">
            <div>
              <span class="messagesBox-left-text">聊天</span>
            </div>
          </div>
        </div>
        <!--右侧下部分聊天区域-->
        <div class="messagesBox-BigRight-down">
          <div :style="messagesBoxSyle">
            <div class="record-wrapper">
              <div v-for="record in chat">
                <div v-if="record.from === 1" class="msg msg-right">
                  <!--                  <div>-->
                  <!--                    <img class="img" :src="from1"/>-->
                  <!--                  </div>-->
                  <div class=" info">
                    <p class="p-right">{{ record.time }} {{ record.name }}</p>
                    <div class="info-content-right">{{ record.message }}</div>
                  </div>
                </div>

                <div v-else class="msg msg-left">
                  <!--                  <div>-->
                  <!--                    <img class="img" :src="from2"/>-->
                  <!--                  </div>-->
                  <div class=" info">
                    <p class="p-left">{{ record.name }} {{ record.time }} </p>
                    <div class="info-content-left">{{ record.message }}</div>
                  </div>
                </div>
              </div>
            </div>
            <form id="inputArea" ref="input" style="display: none">
              <el-input v-model="content" type="textarea" :rows="6" placeholder="请输入信息" class="d" @keydown.enter.native="send($event)" clearable></el-input>
            </form>
<!--            <div id="sendButton" :style="messagesBoxBigRightBotton" style="display: none">-->
<!--              <button class="sendMessageBtn" @keydown.enter.native="send($event)">发送</button>-->
<!--            </div>-->
          </div>
        </div>

      </div>
    </div>

  </div>
</template>


<script>
import axios from "axios";
import SockJS from "sockjs-client"
import Stomp from 'stompjs';

export default {
  name: 'charList',
  data() {
    return {
      //消息-聊天右侧聊天界面样式
      messagesBoxSyle: {
        minWidth: '75%',
        minHeight: '80vh',
        // 'background-color': ' #b8e026'
      },
      memberList: [],
      chat: [],
      content: '',
      current_id: '',
    }
  },
  created() {
    //alert('你好');
    //this.self_id = this.$route.query.self_id;
    this.self_id=this.$self
    axios.get('http://localhost:8443/api/allmessages').then(resp => {
      this.memberList = resp.data;
      //alert(this.record);
    }).catch(
      resp => {
        alert('hahaha')
      }
    );
    var socket = new SockJS("http://localhost:8443/ws");


    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({},()=>{
      //alert('success connect')
      this.stompClient.subscribe("/user/" + this.self_id + "/queue/messages", (msg)=>{
        const message = JSON.parse(msg.body);
        //alert(message.sender_id);
        console.log(message.sender_id);
        this.r = {
          id: 1,
          from: 2,
          name: message.sender_id,
          time: message.time,
          message: message.text,
        }
        //alert(this.from)
        this.chat.push(this.r)
      });
    },(err)=>{
      //alert('fail web-socket')
    })

  },
  methods: {
    recordList(name) {
      this.current_id = name;
      document.getElementById('inputArea').style.display = '';
      //document.getElementById('sendButton').style.display = '';
      axios.get('http://localhost:8443/api/messages', {
        params: {
          other_id: name
        }
      }).then(res => {
        this.record = res.data
        //alert(res.data)
        console.log(this.record)
        this.chat = [];
        for (let i = 0; i < this.record.length; i++) {
          if (this.record[i].sender_id === this.self_id) {
            this.from = 1;
          } else {
            this.from = 2;
          }
          this.r = {
            id: 1,
            from: this.from,
            name: this.record[i].sender_id,
            time: this.record[i].time,
            message: this.record[i].text,
          }
          this.content = '';
          console.log(this.r);
          //alert(this.from)
          this.chat.push(this.r)
        }
        //console.log(this.record)
        //alert(this.record)
      }).catch(
        resp => {
          //alert('hahaha')
        }
      )
    },
    send(ev) {
      if(ev.keyCode===13){
        var _this = this;
        let yy = new Date().getFullYear();
        let mm = new Date().getMonth() + 1;
        let dd = new Date().getDate();
        let hh = new Date().getHours();
        let mf = new Date().getMinutes() < 10 ? '0' + new Date().getMinutes() : new Date().getMinutes();
        let ss = new Date().getSeconds() < 10 ? '0' + new Date().getSeconds() : new Date().getSeconds();
        this.gettime = yy + '-' + mm + '-' + dd + ' ' + hh + ':' + mf + ':' + ss;
        axios.post('http://localhost:8443/api/savemessage',
          {
            sender_id: this.self_id,
            receiver_id: this.current_id,
            text: this.content
          })
          .then(res => {
              if (res.data.code === 200) {
                this.r = {
                  id: 1,
                  from: 1,
                  name: this.self_id,
                  time: this.gettime,
                  message: this.content,
                }
                this.chat.push(this.r)
                var chatMessage = {
                  sender_id:this.self_id,
                  receiver_id:this.current_id,
                  text: this.content
                  // type: 'CHAT'
                };
                console.log(this.stompClient)
                this.stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
                console.log(JSON.stringify(this.content))
                this.content = ''
                this.$message({showClose: true, message: '发送成功！', type: 'success'})
                //this.restNumber = this.restNumber-this.purchaseNumber
              }
            }
          )
      }
    },
  }

}
</script>


<style scoped>
.record-wrapper {
  padding: 55px;
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
  max-width: 100%;
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

.d {
  bottom: 10px;
  position: absolute;
  margin-left: -350px;
  width: 55%;
  border-top-width: 30px;
  border-color: #2c3e50;
}

li {
  list-style: none;
}

.messagesBox {
  height: 93%;
  width: 100%;
  background-color: #f5f5f5;
  display: flex;
  position: absolute;
  top: 7%;
}

.messagesBox-left {
  width: 25%;
  height: 100%;
  background-color: #E2E3E9;
}

.messagesBox-BigRight {
  width: 75%;
  /*background-color: #9fbd6e;*/
}


.messagesBox-left-top {
  height: 80px;
  display: flex;
  /*margin-top: 3%;*/
  border-bottom: 2px solid #DADADA;
  /*justify-content: space-between;*/
  position: relative;
}

/*搜索框部分开始*/
.messagesBox-left-topOne {
  width: 30%;
  position: absolute;
  top: 50%;
  left: 30%;
  transform: translateY(-50%);
}

.messagesBox-left-text {
  /*  position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);*/
  font-family: SourceHanSansCN-Bold;
  font-size: 26px;
  font-weight: bold;
  font-stretch: normal;
  /*letter-spacing: 0px;*/
  color: #666666;
}

/*搜索框部分结束*/

/*左侧数据区域开始*/
.messagesBox-left-group {
  max-height: 86%;
  overflow: auto;
}

.messagesBox-left-group-datas {
  height: 80px;
  width: 100%;
  /*background-color: #fff;*/
  position: relative;
  border-bottom: 2px solid #eaeaea;
}

.messagesBox-left-group-datas-ctl {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  position: relative;
  display: flex;
}

.messagesBox-left-group-left {
  width: 70%;
  cursor: pointer;
  /*background-color: #de0e0e;*/
}

.messagesBox-left-group-leftContainer {
  display: flex;
  /*height: 100px;*/
  margin-left: 5%;
  position: relative;
}

.messagesBox-left-group-leftContainerTwo {
  margin-left: 5%;
}

.messagesBox-left-group-leftContainText {
  display: flex;
  flex-direction: column;
  position: relative;
}

.messagesBox-left-group-leftContainText-one {
  /*  width: 200px;
    margin-left: 30%;*/
  font-family: SourceHanSansCN-Regular;
  font-size: 25px;
  font-weight: bold;
  font-stretch: normal;
  letter-spacing: 2px;
  color: #333333;

}

.messagesBox-left-group-leftContainerControl {
  position: absolute;
  top: 30%;
  left: 30%;
  transform: translateY(-50%);
}

/*右侧开始*/
.messagesBox-BigRight-top {
  display: flex;
  height: 80px;
  line-height: 80px;
  background-color: #F5F5F5;
  /*justify-content: space-between;*/
  border-bottom: 1px solid #ECECEC;

}

.messagesBox-BigRight-topRight {
  display: block;
  width: 85%;
  /*margin-right: 50%;*/
  /*justify-content: space-between;*/
}

.messagesBox-BigRight-down {
  display: flex;
  height: 70%;
  /*min-height: 80vh;*/
  overflow: hidden;
  overflow-y: scroll;
}


</style>
