package com.gala.blockchain.base;

import cn.tdchain.jbcc.Connection;
import cn.tdchain.jbcc.ConnectionFactory;
import com.gala.blockchain.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by brayden on 2020/7/28 15:10
 */
public abstract class BaseDemo {
    protected final static Logger log = LoggerFactory.getLogger("jbcc-samples");
    /**
     * 云区块链服务节点ip列表，当前是测试环境，所以只配置了一个节点，
     * 若为正式环境时，此处应为多个节点，eg:
     * iptables=new String[]{"125.12.2.33","190.11.22.45","210.45.88.52","116.45.3.12"};
     */

//    protected static String[] iptables = new String[]{
//            "192.168.1.211",
//            "192.168.1.212",
//            "192.168.1.213",
//            "192.168.1.214"};

//    protected static String[] iptables = new String[]{"127.0.0.1"};
//    protected final static String keystorePath = "D:\\keys\\rsa\\rsa_client.pfx";
//    protected final static String keystorePasswd = "123456";

    protected static String[] iptables = new String[]{
            "open-tdcb-node1.tdchain.cn",
            "open-tdcb-node2.tdchain.cn",
            "open-tdcb-node3.tdchain.cn",
            "open-tdcb-node4.tdchain.cn"};
    //C:/Users/brayden/Desktop/key/wb/
    //String path = XXXXXX.class.getResource("/文件名").getPath()
    //GetResource.class.getClassLoader().getResource("xx/xx.txt").getPath();
    //URL url = this.getClass().getClassLoader().getResource("file");
//File file = new File(url.getPath());
    //"C:"+File.separator +"Users\brayden\Desktop\key\wb\0x711153dd064983209542daa1e1a78a46.pfx";
    //"C:/Users/brayden/Desktop/key/wb/0x711153dd064983209542daa1e1a78a46.pfx"
//    "C:\\Users\\brayden\\Desktop\\key\\xiaoyan90\\0xa57350a78cba53ed25445dfaf1a087e7.pfx"
    protected final static String keystorePath = "C:\\Users\\brayden\\Desktop\\key\\wb\\wb.pfx";  //  天德个人中心下载的证书文件本机路径，如果没有该证书，需要在网页中申请
    protected final static String keystorePasswd = "wb2020";

    //# 访问天德云区块链的端口, 默认为18088
    protected final static int port = 18088;
    //# 连接超时时间，默认3秒,
    protected final static long timeout = 3000;
    //# 访问天德云区块链的token, 申请证书成功之后即可获取有效token
    protected final static String token = "32581b83-705d-40ef-9f62-1f9a4118fbe1";
    //# 访问天德云区块链时需要的授权文件,登录成功之后即可下载证书, keystorePath为证书存放路径



    //# 声明一个全局连接器connection
    public static Connection connection = null;
    public static SnowFlake snowFlake = null;

    static {
        try {

            //# 构建配置信息
            ConnectionFactory factory = ConnectionFactory.ConnectionConfig.builder()
                    .iptables(iptables)
                    .port(port)  //# 不配置即使用默认值 18088
                    .timeout(timeout)  //# 不配置即使用默认3秒
                    .token(token)
                    //.showPrint()
                    .keystorePath(keystorePath)
                    .keystorePassword(keystorePasswd).build();

            connection = factory.getConnection();

            snowFlake = new SnowFlake();
        } catch (Exception e) {
            log.info("请检查配置===> error: {}", e);
        }
    }
}
