package com.alipay.pussycat.core.common.register;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 *
 * @author recollects
 * @date 2018年06月13日 下午7:17 
 * @version V1.0
 *
 */
public class ProviderGroup implements Serializable {
    private String name;

    private List<ProviderInfo> providerInfoList;

    public ProviderGroup(List<ProviderInfo> providerInfoList) {
        this.providerInfoList = providerInfoList;
    }

    public static class ProviderInfo implements Serializable {

        private String host;
        private int    port;
        private String version;
        /**
         * 与优先级类似
         */
        private int    weight;
        /**
         * 可用\禁用\恢复中\暂停
         */
        private int    status = 0;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public int hashCode() {
            return  HashCodeBuilder.reflectionHashCode(this,false);
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(obj,this,false);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProviderInfo> getProviderInfoList() {
        return providerInfoList;
    }

    public void setProviderInfoList(
            List<ProviderInfo> providerInfoList) {
        this.providerInfoList = providerInfoList;
    }

    public void addProviderInfo(ProviderInfo providerInfo) {
        if (CollectionUtils.isNotEmpty(providerInfoList)) {
            providerInfoList.add(providerInfo);
        } else {
            providerInfoList = Lists.newArrayList();
            providerInfoList.add(providerInfo);
        }
    }

    public ProviderGroup removeProviderInfo(ProviderInfo providerInfo){
        Set<ProviderInfo> set = Sets.newConcurrentHashSet();
        set.remove(providerInfo);

        this.providerInfoList=Lists.newArrayList(set);
        return this;
    }
}
