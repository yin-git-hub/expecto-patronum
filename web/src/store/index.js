import { createStore } from 'vuex'

const MEMBER_KEY = "MEMBER";

export default createStore({
  state: {
    member: JSON.parse(window.sessionStorage.getItem(MEMBER_KEY)) || {}
  },
  getters: {
  },
  mutations: {
    setMember (state, _member) {
      state.member = _member;
      window.sessionStorage.setItem(MEMBER_KEY, JSON.stringify(_member));
    }
  },
  actions: {
  },
  modules: {
  }
})
