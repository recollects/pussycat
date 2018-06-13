package com.alipay.pussycat.core.common.register;

import java.io.Serializable;
import java.util.List;

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
        private int    status;

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
}
