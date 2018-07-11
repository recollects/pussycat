package com.alipay.pussycat.register.local;

import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.core.common.model.AckCustomBody;
import com.alipay.pussycat.core.common.model.RemotingTransporter;
import com.alipay.pussycat.core.common.netty.serializable.Serializer;
import com.alipay.pussycat.core.common.netty.transport.NettyTransportClientController;
import com.alipay.pussycat.core.common.register.AbstractRegistry;
import com.alipay.pussycat.core.common.enums.RegisterEnum;
import com.alipay.pussycat.core.common.register.ProviderGroup;
import com.alipay.pussycat.core.common.register.conf.ConsumerConfig;
import com.alipay.pussycat.core.common.register.conf.ProviderConfig;
import com.alipay.pussycat.core.common.utils.NettyUtils;
import com.alipay.pussycat.core.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.provider.DefaultProvider;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author recollects
 * @date 2018年06月13日 上午9:49 
 * @version V1.0
 *
 *  @description 默认的本地注册中心，处理注册端的所有事宜：
 * 1)处理provider端发送过来的注册信息
 * 2)处理consumer端发送过来的订阅信息
 * 3)当服务下线需要通知对应的consumer变更后的注册信息
 * 4)所有的注册订阅信息的储存和健康检查
 * 5)接收管理者的一些信息请求，比如 服务统计 | 某个实例的服务降级 | 通知消费者的访问策略  | 改变某个服务实例的比重
 * 6)将管理者对服务的一些信息 例如审核结果，负载算法等信息持久化到硬盘
 *
 */
public class LocalRegistry extends AbstractRegistry {

    private static final Logger logger = LoggerFactory.getLogger(LocalRegistry.class);

    private NettyTransportClientController nettyTransportClientController = new NettyTransportClientController();

    private Serializer serializer = PussycatServiceContainer.getInstance(Serializer.class);

    private final ConcurrentMap<Long, MsgNOTAck> messagesNonAcks = new ConcurrentHashMap<Long, MsgNOTAck>();

    @Override public boolean isStart() {
        return false;
    }

    @Override public void register(ProviderConfig config) {

    }

    @Override public void unregister(ProviderConfig config) {

    }

    @Override public List<ProviderGroup> subscribe(ConsumerConfig config) {
        return null;
    }

    @Override
    public RegisterEnum registrar() {
        return RegisterEnum.LOCAL;
    }



    public void registerAndStart(DefaultProvider defaultProvider) throws PussycatException, InterruptedException {
        if (CollectionUtils.isEmpty(defaultProvider.getRegisterService())){
            logger.info("service is empty,please check your services...");
            return;
        }
        String registerAddress = defaultProvider.getRegisterAddress();
        if (registerAddress == null){
            logger.warn("registry center address is empty please check your address");
            return;
        }
        String[] registerAddrs = registerAddress.split(",");
        if (registerAddrs !=null && registerAddrs.length >0){
            for(String registerAddr : registerAddrs){
                for (RemotingTransporter remotingTransporter : defaultProvider.getRegisterService()){
                    pushServiceToRegister(remotingTransporter,registerAddr);
                }

            }
        }
    }



    private void pushServiceToRegister(RemotingTransporter remotingTransporter, String registerAddr) throws InterruptedException {
        RemotingTransporter reponse = null;
        messagesNonAcks.putIfAbsent(remotingTransporter.getRequestId(),new MsgNOTAck(remotingTransporter,registerAddr));
        Channel channel = nettyTransportClientController.getAndCreateChannel(NettyUtils.string2SocketAddress(registerAddr));
        if (channel != null && channel.isActive()){
             reponse = nettyTransportClientController.remotingInvoke(channel, remotingTransporter, 3000L);
        }
        if (reponse != null){
            AckCustomBody ackCustomBody = serializer.readObject(remotingTransporter.getTransportItemBytes().getBodyBytes(), AckCustomBody.class);
            logger.info("receive ack info [{}] from register ",ackCustomBody);
            if (ackCustomBody.isSuccess()){
                messagesNonAcks.remove(remotingTransporter.getRequestId());
            }
        }else{
            logger.warn("register center timeout...");
        }


    }



    @Override
    public void processRequest(ChannelHandlerContext ctx, RemotingTransporter msg){
        switch (msg.getCode()) {

        }
    }

    /**
     * 记录没有正确注册的服务，重新注册
     */
    static class MsgNOTAck{
        private final long requestId;
        private final RemotingTransporter remotingTransporter;
        private final String regAddr;
        private final long timeStamp = System.currentTimeMillis();

        public MsgNOTAck(RemotingTransporter remotingTransporter, String regAddr) {
            this.requestId = remotingTransporter.getRequestId();
            this.remotingTransporter = remotingTransporter;
            this.regAddr = regAddr;
        }

        public long getRequestId() {
            return requestId;
        }

        public RemotingTransporter getRemotingTransporter() {
            return remotingTransporter;
        }

        public String getRegAddr() {
            return regAddr;
        }
    }
}
