import { createStore } from 'vuex'

const USER_KEY = "USERINFO";

export default createStore({
  state: {
    userInfo: JSON.parse(window.localStorage.getItem(USER_KEY)) || {},
    videoInfo: JSON,
    filename:String,

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
    }
  },
  actions: {
  },
  modules: {
  }
})
