var flag = false;
function toggleFullScreen(obj){
  if(!flag){
    fullscreen();
    $(obj).html("<span>退出全屏</span>");
  }else{
    exitFullscreen();
    $(obj).html("<span>全屏</span>");
  }
}
// 全屏
function fullscreen() {
  flag = true;
  var docElm = document.documentElement;
  if (docElm.requestFullscreen) {
    docElm.requestFullscreen();
  } else if (docElm.mozRequestFullScreen) {
    docElm.mozRequestFullScreen();
  } else if (docElm.webkitRequestFullScreen) {
    docElm.webkitRequestFullScreen();
  } else if (docElm.msRequestFullscreen) {
    docElm.msRequestFullscreen();
  }
}

// 退出全屏
function exitFullscreen() {
  flag = false;
  if (document.exitFullscreen) {
    document.exitFullscreen();
  } else if (document.mozCancelFullScreen) {
    document.mozCancelFullScreen();
  } else if (document.webkitCancelFullScreen) {
    document.webkitCancelFullScreen();
  } else if (document.msExitFullscreen) {
    document.msExitFullscreen();
  }
}

// 监听是否全屏
window.onload = function() {
  var elem = document.getElementById('state');
  document.addEventListener('fullscreenchange',
    function() {
      elem.innerText = document.fullscreen ? 'yes': 'no';
    },
  false);
  document.addEventListener('mozfullscreenchange',
    function() {
      elem.innerText = document.mozFullScreen ? 'yes': 'no';
    },
  false);
  document.addEventListener('webkitfullscreenchange',
    function() {
      elem.innerText = document.webkitIsFullScreen ? 'yes': 'no';
    },
  false);
  document.addEventListener('msfullscreenchange',
    function() {
      elem.innerText = document.msFullscreenElement ? 'yes': 'no';
    },
  false);
}