package io.bootify.health_hive.service;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.crypto.Credentials;
import io.bootify.health_hive.Contract.StringStorage;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

@Service
public class StringStorageService {

    private final StringStorage stringStorage;

    public StringStorageService() {
        Web3j web3j = Web3j.build(new HttpService("http://localhost:8545")); // URL of your Ethereum node
        Credentials credentials = Credentials.create("0xae6ae8e5ccbfb04590405997ee2d52d2b330726137b875053c36d94e974d162f"); // Wallet private key
        stringStorage = StringStorage.load("0x8CdaF0CD259887258Bc13a92C0a6dA92698644C0", web3j, credentials, new DefaultGasProvider());
    }

    public String storeString(String inputString) throws Exception {
        TransactionReceipt transactionReceipt = stringStorage.storeString(inputString).send();
        return transactionReceipt.getTransactionHash(); // Return transaction hash
    }

    public String retrieveString(BigInteger transactionId) throws Exception {
        return stringStorage.retrieveString(transactionId).send();
    }
}
