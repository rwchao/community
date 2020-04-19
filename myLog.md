## Bugs
p7  
- bootstrap本地css不成功，使用官方的<link>才能成功[已解决，引用路径错误]
- 没有下拉效果  [已解决，是因为没有引入JQuery的原因]  

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


## 我的一些问题
p21  
1. 为什么回显 `title` 和 `tag` 的时候，用 `th:value="*{}"` ，而回显 `description` 的时候
用 `th:text="${}"` 呢  
Q:因为 title 和 tag 都是 input标签 ，而 description 是 textarea 标签(这应该是只是一部分原因)
2. ${} 和 *{} 的区别是什么？
3. 为什么在 thymeleaf template 中可以直接获取对象的 private变量？
4. 下划线转驼峰的问题是什么情况，为什么之前没有收到影响？

p26 实现分页功能  
1. 为什么在Mysql中计数用 `count(1)` 而不是 `count(*)` ,它们的区别是什么？


## Markdown的使用
- 如何缩进?
- 如何打出空格？