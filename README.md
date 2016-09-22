# 运行说明
被监控服务器：
1） 安装 tsar 
2） 运行 nohup tsar -l -i3 >>tsardata.txt &
3） 安装 jdk1.7 +
4） client 打包
5） 运行 nohup java -Xms100m -Xmx100m   -jar tsarClient.jar >>tsarClient.log &

汇总服务器：
1）server 打包
2）运行  java -Xms100m -Xmx100m   -jar tsarServer.jar
3）浏览器访问 http://127.0.0.1:7002/tsar-server/

运行效果图如下：
![](https://github.com/51943143/tsar_help/blob/master/server/run_result.png)




enjoy！


