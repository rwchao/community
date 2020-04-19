## Bugs
p7  
- bootstrap本地css不成功，使用官方的<link>才能成功[已解决，引用路径错误]
- 没有下拉效果  [已解决，是因为没有引入JQuery的原因]  
[注意]应该用 the compressed version 而不是 the uncompressed version

p12
- 用 `https://api.github.com/user?access_token=9687d67ee1f4da66ff58e5e335615ce656
7e0b74` 测试请求github user数据，在chrome中无法访问，在firefox中却可以访问
  ![](.mylog_images/chorme-access_token-errro.png)
- user.getname()返回null,而将user的string信息打印也只得到下面信息[已解决]
  ![](.mylog_images/user-string.png)

p19:flyway
- mvn flyway:migrate之后出现错误 ` General error: "java.lang.IllegalStateException: 
Unable to read the page`
![](.myLog_images/flyway-error.png)

p27
- 引入本地JQuery文件失败
- 用户名旁边的下拉箭头在登陆后消失了  
A: `th:text="${session.user.getName()}"` 把 ` <span class="caret"></span>` 覆盖了

p28:个人资料问题列表
- 加载`profile.html`的时候不能加载到css,js等静态资源
![](.myLog_images/profile-link-error.png)  
A: 注意看各个地方的路径，当前显示 `profile.html` 的路径是 `http://localhost:8887/profile/questions`,
当前所在父目录是/profile,所以引用相对路径 `css/*.css`的时候，其绝对路径变成 
`http://localhost:8887/profile/css/*.css` (查看控制台信息验证)，而`css/*.css`资源正确的绝对路径应该是
`http://localhost:8887/css/*.css` ，所以引用错误
- page = 0 的情况是怎么产生的？
A：当totalpage=0时被错误地修正为了0;  
    当前的解决方法是,关于修正页数的逻辑等会再重构
    ```
    if(totalPages == 0){
             totalPages = 1;
         }
    ```

## 我的一些问题
p21  
1. 为什么回显 `title` 和 `tag` 的时候，用 `th:value="*{}"` ，而回显 `description` 的时候
用 `th:text="${}"` 呢  
A:因为 title 和 tag 都是 input标签 ，而 description 是 textarea 标签(这应该是只是一部分原因)
2. ${} 和 *{} 的区别是什么？
3. 为什么在 thymeleaf template 中可以直接获取对象的 private变量？
4. 下划线转驼峰的问题是什么情况，为什么之前没有收到影响？

p26 实现分页功能  
1. 为什么在Mysql中计数用 `count(1)` 而不是 `count(*)` ,它们的区别是什么？

关于dev-tool的使用：  
只修改html文件的时候需要build吗？如果修改（增减）了其 `script` tag呢？  
A:应该是都需要build

## 暂时搁置
p30中的源码分析


## Markdown的使用
- 如何缩进?
- 如何打出空格？
