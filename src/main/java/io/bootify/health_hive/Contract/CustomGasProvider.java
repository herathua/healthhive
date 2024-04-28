package io.bootify.health_hive.Contract;

import java.math.BigInteger;
import org.web3j.tx.gas.DefaultGasProvider;


public class CustomGasProvider extends DefaultGasProvider {
    @Override
    public BigInteger getGasLimit(String contractFunc) {
        // You can specify different limits based on the function being called
        if("storeString".equals(contractFunc)) {
            return BigInteger.valueOf(500000);  // Example: higher limit for storeString function
        }
        return super.getGasLimit(contractFunc);  // Default limit for other functions
    }

    @Override
    public BigInteger getGasPrice(String contractFunc) {
        return super.getGasPrice(contractFunc);  // Default price
    }
}
