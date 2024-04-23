package io.bootify.health_hive.Contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.5.0.
 */
@SuppressWarnings("rawtypes")
public class StringStorage extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b5061073c806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80631bb8305d146100515780637eddf5b41461006d5780638b3c8a651461009d578063b77bf600146100cd575b600080fd5b61006b600480360381019061006691906104af565b6100eb565b005b6100876004803603810190610082919061052e565b610168565b60405161009491906105e3565b60405180910390f35b6100b760048036038101906100b2919061052e565b61020c565b6040516100c491906105e3565b60405180910390f35b6100d56102ac565b6040516100e29190610614565b60405180910390f35b600160008154809291906100fe9061065e565b9190505550806000806001548152602001908152602001600020908051906020019061012b9291906102b2565b506001547e73b9f32bc5b2c870383e7aa1f8fc9bcf80d32f8b95788b9670cca8f05d8dcd8260405161015d91906105e3565b60405180910390a250565b60606000808381526020019081526020016000208054610187906106d5565b80601f01602080910402602001604051908101604052809291908181526020018280546101b3906106d5565b80156102005780601f106101d557610100808354040283529160200191610200565b820191906000526020600020905b8154815290600101906020018083116101e357829003601f168201915b50505050509050919050565b6000602052806000526040600020600091509050805461022b906106d5565b80601f0160208091040260200160405190810160405280929190818152602001828054610257906106d5565b80156102a45780601f10610279576101008083540402835291602001916102a4565b820191906000526020600020905b81548152906001019060200180831161028757829003601f168201915b505050505081565b60015481565b8280546102be906106d5565b90600052602060002090601f0160209004810192826102e05760008555610327565b82601f106102f957805160ff1916838001178555610327565b82800160010185558215610327579182015b8281111561032657825182559160200191906001019061030b565b5b5090506103349190610338565b5090565b5b80821115610351576000816000905550600101610339565b5090565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6103bc82610373565b810181811067ffffffffffffffff821117156103db576103da610384565b5b80604052505050565b60006103ee610355565b90506103fa82826103b3565b919050565b600067ffffffffffffffff82111561041a57610419610384565b5b61042382610373565b9050602081019050919050565b82818337600083830152505050565b600061045261044d846103ff565b6103e4565b90508281526020810184848401111561046e5761046d61036e565b5b610479848285610430565b509392505050565b600082601f83011261049657610495610369565b5b81356104a684826020860161043f565b91505092915050565b6000602082840312156104c5576104c461035f565b5b600082013567ffffffffffffffff8111156104e3576104e2610364565b5b6104ef84828501610481565b91505092915050565b6000819050919050565b61050b816104f8565b811461051657600080fd5b50565b60008135905061052881610502565b92915050565b6000602082840312156105445761054361035f565b5b600061055284828501610519565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b8381101561059557808201518184015260208101905061057a565b838111156105a4576000848401525b50505050565b60006105b58261055b565b6105bf8185610566565b93506105cf818560208601610577565b6105d881610373565b840191505092915050565b600060208201905081810360008301526105fd81846105aa565b905092915050565b61060e816104f8565b82525050565b60006020820190506106296000830184610605565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000610669826104f8565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff820361069b5761069a61062f565b5b600182019050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806106ed57607f821691505b602082108103610700576106ff6106a6565b5b5091905056fea2646970667358221220beeb99cf669351a6210f5009d9194172c16aabd657c013008e5e890e828c3e8964736f6c634300080d0033";

    public static final String FUNC_STRINGS = "strings";

    public static final String FUNC_TRANSACTIONCOUNT = "transactionCount";

    public static final String FUNC_STORESTRING = "storeString";

    public static final String FUNC_RETRIEVESTRING = "retrieveString";

    public static final Event STRINGSTORED_EVENT = new Event("StringStored", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1713854696625", "0xF12b5dd4EAD5F743C6BaA640B0216200e89B60Da");
    }

    @Deprecated
    protected StringStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected StringStorage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected StringStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected StringStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<StringStoredEventResponse> getStringStoredEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> eventValuesList = Contract.staticExtractEventParametersWithLog(STRINGSTORED_EVENT, transactionReceipt);
//        List<StringStoredEventResponse> responses = new ArrayList<>();
//        for (Contract.EventValuesWithLog eventValues : eventValuesList) {
//            StringStoredEventResponse response = new StringStoredEventResponse();
//            response.log = eventValues.getLog();
//            response.transactionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//            response.storedString = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(response);
//        }
//        return responses;
//    }

    // this is the real code genarated by web3j
    public static List<StringStoredEventResponse> getStringStoredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(STRINGSTORED_EVENT, transactionReceipt);
        ArrayList<StringStoredEventResponse> responses = new ArrayList<StringStoredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StringStoredEventResponse typedResponse = new StringStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.transactionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.storedString = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static StringStoredEventResponse getStringStoredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(STRINGSTORED_EVENT, log);
        StringStoredEventResponse typedResponse = new StringStoredEventResponse();
        typedResponse.log = log;
        typedResponse.transactionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.storedString = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<StringStoredEventResponse> stringStoredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getStringStoredEventFromLog(log));
    }

    public Flowable<StringStoredEventResponse> stringStoredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STRINGSTORED_EVENT));
        return stringStoredEventFlowable(filter);
    }

    public RemoteFunctionCall<String> strings(BigInteger param0) {
        final Function function = new Function(FUNC_STRINGS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> transactionCount() {
        final Function function = new Function(FUNC_TRANSACTIONCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> storeString(String inputString) {
        final Function function = new Function(
                FUNC_STORESTRING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(inputString)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> retrieveString(BigInteger transactionId) {
        final Function function = new Function(FUNC_RETRIEVESTRING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(transactionId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static StringStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StringStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static StringStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StringStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static StringStorage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new StringStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static StringStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new StringStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<StringStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StringStorage.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StringStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StringStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<StringStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StringStorage.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StringStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StringStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class StringStoredEventResponse extends BaseEventResponse {
        public BigInteger transactionId;

        public String storedString;
    }
}
