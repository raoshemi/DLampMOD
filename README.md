# Dimension Lamp MOD
Minecraft周边互动玩具模组

##数据交互
####MOD配网
使用UDP协议向局域网10000端口进行广播（255.255.255.255） 
 
开始配网:

`DLampManager.startAirkiss(String ssid, String password);`

停止配网：

`DLampManager.stopAirkiss();`