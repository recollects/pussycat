package com.alipay.pussycat.register.local;

import com.alipay.pussycat.core.common.enums.RegisterEnum;
import com.alipay.pussycat.core.common.register.AbstractRegistry;
import com.alipay.pussycat.core.common.register.ProviderGroup;
import com.alipay.pussycat.core.common.register.conf.ConsumerConfig;
import com.alipay.pussycat.core.common.register.conf.ProviderConfig;
import com.alipay.pussycat.core.common.register.conf.ServerConfig;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 *
 * @author recollects
 * @date 2018年06月13日 上午9:49 
 * @version V1.0
 *
 */
public class LocalRegistry extends AbstractRegistry {

    @Override
    public boolean isStart() {
        return false;
    }

    @Override
    public void register(ProviderConfig config) {
        String appName = config.getAppName();

        //表示这个服务不用注册
        if (!config.isRegister()) {
            return;
        }

        ProviderGroup providerGroup = providerLocalMap.get(config.getServiceName());

        List<ProviderGroup.ProviderInfo> providerInfos = buildProviderInfo(config);

        if (providerGroup != null) {
            providerGroup.setProviderInfoList(providerInfos);
            providerLocalMap.put(appName + "-" + config.getServiceName(), providerGroup);
        } else {
            providerLocalMap.put(appName + "-" + config.getServiceName(), new ProviderGroup(providerInfos));
        }

        //TODO 通知客户端
    }

    /**
     *
     * @param config
     * @return
     */
    private List<ProviderGroup.ProviderInfo> buildProviderInfo(ProviderConfig config) {

        List<ProviderGroup.ProviderInfo> providerInfos = Lists.newArrayList();

        List<ServerConfig> serverConfigs = config.getServerConfigs();
        ProviderGroup.ProviderInfo providerInfo = null;
        if (CollectionUtils.isNotEmpty(serverConfigs)) {
            for (ServerConfig serverConfig : serverConfigs) {

                providerInfo = new ProviderGroup.ProviderInfo();
                providerInfo.setHost(serverConfig.getHost());
                providerInfo.setPort(serverConfig.getPort());
                providerInfo.setStatus(0);
                providerInfo.setVersion(config.getVersion());
                providerInfo.setWeight(config.getWeight());
                providerInfos.add(providerInfo);
            }
        }

        return providerInfos;
    }

    @Override
    public void unregister(ProviderConfig config) {
        String appName = config.getAppName();

        if (!config.isRegister()) {
            return;
        }

        ProviderGroup providerGroup = providerLocalMap.get(config.getServiceName());

        if (providerGroup!=null){

            List<ServerConfig> serverConfigs = config.getServerConfigs();

            if (CollectionUtils.isNotEmpty(serverConfigs)){


                providerGroup.setProviderInfoList(Lists.newArrayList());
            }

        }
        //TODO 通知客户端

    }

    @Override
    public ProviderGroup subscribe(ConsumerConfig config) {

        String appName = config.getAppName();

        ProviderGroup providerGroup = providerLocalMap.get(appName + "-" + config.getIntefaceId());

        return providerGroup;
    }

    @Override
    public RegisterEnum registrar() {
        return RegisterEnum.LOCAL;
    }
}
