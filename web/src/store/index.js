import { createStore } from 'vuex'
import {List} from "ant-design-vue";

const USER_KEY = "USERINFO";

export default createStore({
  state: {
    userInfo: JSON.parse(window.localStorage.getItem(USER_KEY)) || {},
    videoInfo: JSON,
    //视频标题名
    filename:String,
    videoCurrentTime:String,
    followingGroupList:List,
    collectionGroupList:List,
  },
  getters: {
  },
  mutations: {
    setUserInfo (state, _userInfo) {
      state.userInfo = _userInfo;

      window.localStorage.setItem(USER_KEY, JSON.stringify(_userInfo));
    },
    setVideoInfo(state,_videoInfo){
      state.videoInfo = _videoInfo;
    },
    setFilename(state,_filename){
      state.filename = _filename;
    },
    setVideoCurrentTime(state,_videoCurrentTime){
      state.videoCurrentTime = _videoCurrentTime;
    },
    setFollowingGroupList(state,_followingGroupList){
      state.followingGroupList = _followingGroupList;
    },
    setCollectionGroupList(state,_collectionGroupList){
      state.collectionGroupList = _collectionGroupList;
    }
  },
  actions: {
  },
  modules: {
  }
})
