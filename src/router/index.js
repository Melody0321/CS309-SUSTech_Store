import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login'
import Register from "../components/Register";
import Home from '../components/Home'
import Personal from "../components/Personal";
import ModifyInformation from "../components/home/ModifyInformation";
import Detail from "../components/Detail";
import Result from "../components/Result";
import Post from "../components/Post";
import postDetail from "../components/postDetail";
import chat from "../components/chat"
import chatList from "../components/chatList"

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    { path: '/', redirect: '/login' },
    {
      path:'/login',
      name: 'Login',
      component: Login
    },
    {
      path:'/post',
      name: 'Post',
      component: Post
    },
    {
      path:'/postDetail',
      name: 'postDetail',
      component: postDetail
    },
    {
      path: '/register',
      name:'Register',
      component: Register
    },
    {
      path:'/personal',
      name:'Personal',
      component:Personal
    },
    {
      path: '/home',
      name:'Home',
      component: Home,
      // meta: {
      //   requireAuth: true
      // }
    },
    {
      path:'/modify',
      name:'ModifyInformation',
      component:ModifyInformation
    },
    {
      path: '/detail',
      name:'Detail',
      component: Detail
    },
    {
      path: '/result',
      name:'Result',
      component: Result
    },
    {
      path: '/chat',
      name:'chat',
      component: chat
    },
    {
      path: '/chatList',
      name:'chatList',
      component: chatList
    },

  ]
})

