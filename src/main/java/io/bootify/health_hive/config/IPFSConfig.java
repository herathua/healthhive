package io.bootify.health_hivw.config;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class IPFSConfig {

    public IPFS ipfs;

    public IPFSConfig() {
        ipfs = new IPFS("/ip4/172.19.0.3/tcp/5001");
    }

}
