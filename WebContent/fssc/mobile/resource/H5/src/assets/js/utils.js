const setWechatTitle = function (title) {
  document.title = title
  let mobile = navigator.userAgent.toLowerCase()
  if (/iphone|ipad|ipod/.test(mobile)) {
    let iframe = document.createElement('iframe')
    iframe.style.visibility = 'hidden'
    let iframeCallback = function () {
      setTimeout(function () {
        iframe.removeEventListener('load', iframeCallback)
        document.body.removeChild(iframe)
      }, 10)
    }
    iframe.addEventListener('load', iframeCallback)
    document.body.appendChild(iframe)
  }
}
// 第二种修改title的方法，其中包含iframe的设置：
let setTitleHack = function (t) {
  document.title = t
  let iframe = document.createElement('iframe')
  iframe.style.visibility = 'hidden'
  iframe.style.width = '1px'
  iframe.style.height = '1px'
  // iframe.src = '//m.baidu.com/favicon.ico';
  iframe.onload = function () {
    setTimeout(function () {
      iframe.remove()
    }, 10)
  }
  document.body.appendChild(iframe)
}

// 在文件的最下方输出这两个方法：
module.exports = {
  setWechatTitle,
  setTitleHack
}
