## 修改注意事项 
确保包含.vscode配置文件，才可以开启Watch Sass进行编译,否则生成路径错乱

1.新建文件夹 .vscode
2.新建 settings.json
3.内容
{
  "liveSassCompile.settings.formats": [{
    "format": "expanded",
    "extensionName": ".css",
    "savePath": "/style"
  }],
  "liveSassCompile.settings.generateMap": false,
  "liveSassCompile.settings.excludeList": [
    "**/scss/lib/**",
    "**/node_modules/**"
  ],
}
## 页眉logo宽度调整
在/style/portal.css | /scss/portal.scss（需编译，非开发人员勿动SCSS文件）  中搜索【修改页眉logo尺寸】调整宽度即可

## 后台配置logo宽度调整
在/style/profile.css | /scss/profile.scss（需编译，非开发人员勿动SCSS文件） 中搜索【修改后台配置logo尺寸】调整宽度即可


## 颜色变量说明见 scss/lib/var.scss