import { createStore } from 'vuex'

const MEMBER_KEY = "MEMBER";

export default createStore({
  state: {
    member: JSON.parse(window.sessionStorage.getItem(MEMBER_KEY)) || {},
    videoInfo: JSON,
    filename:String,

  },
  getters: {
  },
  mutations: {
    setMember (state, _member) {
      state.member = _member;
      window.sessionStorage.setItem(MEMBER_KEY, JSON.stringify(_member));
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
