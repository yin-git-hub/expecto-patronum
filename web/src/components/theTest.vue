<template>
  <input type="text" id="inputField" placeholder="context">
  <button @click="sendMessage()">Send</button>
  <div><p id="videoIdShow"></p>:<p id="currentCount"></p></div>
  <p id="displayField"></p>

</template>

<script setup >
const ws = new WebSocket("ws://localhost:7330/water-sty/scrolling/123/122");

ws.onopen = function() {
  console.log('WebSocket connection established');
};

ws.onmessage = function(message) {
  console.log("message.data===>",message.data)
  const data = JSON.parse(message.data);
  if(data.scrollingContext!=null){
    const displayField = document.getElementById("displayField");
    const videoIdShow = document.getElementById("videoIdShow");
    const timestamp = new Date(data.relativeTime).toLocaleString();
    displayField.innerHTML += `<div>Received at ${timestamp}: ${data.scrollingContext}</div>`;
    videoIdShow.innerHTML = `<div>${data.videoId}</div>`;

  }
  if(!isNaN(message.data)){
    const currentCount = document.getElementById("currentCount");
    currentCount.innerHTML = `<div>${message.data}</div>`;

  }

};

const sendMessage=()=> {

  const inputField = document.getElementById("inputField");
  const relativeTime = Date.now();
  const scrollingContext = inputField.value;

  const jsonData = {
    currentCount:0,
    videoId:"121",
    relativeTime: relativeTime,
    scrollingContext: scrollingContext
  };

  const jsonStr = JSON.stringify(jsonData);

  ws.send(jsonStr);

  inputField.value = ''; // 清空输入框
}
</script>

