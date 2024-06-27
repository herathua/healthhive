package io.bootify.health_hive.config;


import io.ipfs.api.IPFS;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class IPFSConfig {

    public IPFS ipfs;

    public IPFSConfig() {
        ipfs = new IPFS("/dns4/ipfs0/tcp/5001");
    }

}

