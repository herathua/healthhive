package io.bootify.health_hive.service;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class IPFSConfig {

    IPFS ipfs;

    public IPFSConfig() {
        ipfs = new IPFS("/ip4/172.21.0.8/tcp/5001");
    }

}
