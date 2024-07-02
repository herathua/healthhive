package io.bootify.health_hive.service;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.crypto.Credentials;
import io.bootify.health_hive.Contract.StringStorage;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

@Service
public class StringStorageService {

    private final StringStorage stringStorage;

    public StringStorageService() {
        Web3j web3j = Web3j.build(new HttpService("http://13.202.67.81:8545")); // URL of your Ethereum node
        Credentials credentials = Credentials.create("0xc87509a1c067bbde78beb793e6fa76530b6382a4c0241e5e4a9ec0a0f44dc0d3"); // Wallet private key

        BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L); // Custom gas price
        BigInteger gasLimit = BigInteger.valueOf(4_300_000); // Custom gas limit
        ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);

        stringStorage = StringStorage.load("0x8CdaF0CD259887258Bc13a92C0a6dA92698644C0", web3j, credentials, gasProvider);
    }

    public String storeString(String inputString) throws Exception {
        TransactionReceipt transactionReceipt = stringStorage.storeString(inputString).send();
        return transactionReceipt.getTransactionHash(); // Return transaction hash
    }

    public String retrieveString(BigInteger transactionId) throws Exception {
        return stringStorage.retrieveString(transactionId).send();
    }
}
