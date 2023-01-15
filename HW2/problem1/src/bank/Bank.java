package bank;

import bank.event.*;
import security.*;
import security.key.*;

public class Bank {
    private int numClients=0;
    final static int maxClients = 100;
    private Client[] clients = new Client[maxClients];

    public void createClient(String id, String password){
        //TODO: Problem 1.1
        if (getClientbyID(id) == null) {
            Client client = new Client(id, password);
            clients[numClients] = client;
            numClients++;
        }
    }
    public void createAccount(String id, String password, int accountID) {
        createAccount(id, password, accountID, 0);
    }

    public void createAccount(String id, String password, int accountID, int initBalance) {
        //TODO: Problem 1.1
        Client client = getClientbyID(id);
        if (client != null) {
            if (client.authenticate(password)) {
                client.createAccount(accountID, initBalance);
                client.expireAuthenticatedState();
            } else {
                client.expireAuthenticatedState();
            }
        }
    }

    public boolean deposit(String id, String password, int accountID, int amount) {
        //TODO: Problem 1.1
        Client client = getClientbyID(id);
        if (client != null) {
            if (client.authenticate(password)) {
                BankAccount account = client.findAccount(accountID);
                if (account != null) {
                    account.deposit(amount);
                    client.expireAuthenticatedState();
                    return true;
                } else {
                    client.expireAuthenticatedState();
                    return false;
                }
            }
        }
        return false;
    }

    public boolean withdraw(String id, String password, int accountID, int amount) {
        //TODO: Problem 1.1
        Client client = getClientbyID(id);
        if (client != null) {
            if (client.authenticate(password)) {
                BankAccount account = client.findAccount(accountID);
                if (account != null) {
                    boolean withdraw = account.withdraw(amount, client.checkMembership());
                    client.expireAuthenticatedState();
                    return withdraw;
                } else {
                    client.expireAuthenticatedState();
                    return false;
                }
            }
        }
        return false;
    }

    public boolean transfer(String sourceId, String password, int sourceAccountID, String targetId, int targetAccountID, int amount) {
        //TODO: Problem 1.1
        Client sender = getClientbyID(sourceId);
        Client receiver = getClientbyID(targetId);
        if (sender != null && receiver != null) {
            if (sender.authenticate(password)) {
                BankAccount source = sender.findAccount(sourceAccountID);
                BankAccount target = receiver.findAccount(targetAccountID);
                if (source != null && target != null) {
                    boolean sent = source.send(amount, sender.checkMembership());
                    if (sent) { target.receive(amount); }
                    sender.expireAuthenticatedState();
                    return sent;
                }
            }
            sender.expireAuthenticatedState();
        }
        return false;
    }

    public Event[] getEvents(String id, String password, int accountID) {
        //TODO: Problem 1.1
        Client client = getClientbyID(id);
        if (client != null) {
            BankAccount account = client.findAccount(accountID);
            if (account != null) {
                client.expireAuthenticatedState();
                return account.getEvents();
            } else {
                client.expireAuthenticatedState();
            }
        }
        return null;
    }

    public int getBalance(String id, String password, int accountID) {
        //TODO: Problem 1.1
        Client client = getClientbyID(id);
        if (client != null) {
            if (client.authenticate(password)) {
                BankAccount account = client.findAccount(accountID);
                if (account != null) {
                    client.expireAuthenticatedState();
                    return account.getBalance();
                } else {
                    client.expireAuthenticatedState();
                }
            }
        }
        return -1;
    }

    public Client getClientbyID(String id) {
        for (int i = 0; i < numClients; i++)
            if (clients[i].getId().equals(id)) return clients[i];
        return null;
    }

    private static String randomUniqueStringGen(){
        return Encryptor.randomUniqueStringGen();
    }
    private BankAccount find(String id, int accountID) {

        Client client = getClientbyID(id);
        BankAccount clientAccount = client.findAccount(accountID);

        return clientAccount;
    }

    final static int maxSessionKey = 100;
    int numSessionKey = 0;
    String[] sessionKeyArr = new String[maxSessionKey];
    Client[] bankClientmap = new Client[maxSessionKey];
    BankAccount[] bankAccountmap = new BankAccount[maxSessionKey];
    String generateSessionKey(String id, String password, int accountID){
        Client client = getClientbyID(id);

        if(client == null || !client.authenticate(password) || client.findAccount(accountID)==null){
            return null;
        }

        String sessionkey = randomUniqueStringGen();
        sessionKeyArr[numSessionKey] = sessionkey;
        bankClientmap[numSessionKey] = client;
        bankAccountmap[numSessionKey] = client.findAccount(accountID);
        numSessionKey += 1;
        return sessionkey;
    }

    Client getClient(String sessionkey){
        for(int i=0; i < numSessionKey; i++){
            if(sessionKeyArr[i] != null && sessionKeyArr[i].equals(sessionkey)) {
                return bankClientmap[i];
            }
        }
        return null;
    }

    BankAccount getAccount(String sessionkey){
        for(int i = 0 ;i < numSessionKey; i++){
            if(sessionKeyArr[i] != null && sessionKeyArr[i].equals(sessionkey)){
                return bankAccountmap[i];
            }
        }
        return null;
    }

    boolean deposit(String sessionkey, int amount) {
        //TODO: Problem 1.2
        BankAccount account = getAccount(sessionkey);
        if (account != null) {
            account.deposit(amount);
            return true;
        }
        return false;
    }

    boolean withdraw(String sessionkey, int amount) {
        //TODO: Problem 1.2
        BankAccount account = getAccount(sessionkey);
        Client client = getClient(sessionkey);
        if (account != null && client != null) {
            return account.withdraw(amount, client.checkMembership());
        }

        return false;
    }

    boolean transfer(String sessionkey, String targetId, int targetAccountID, int amount) {
        //TODO: Problem 1.2
        BankAccount source = getAccount(sessionkey);
        Client receiver = getClientbyID(targetId);
        BankAccount target = receiver.findAccount(targetAccountID);
        Client sender = getClient(sessionkey);
        if (source != null && sender != null) {
            if (source != null && target != null) {
                boolean sent = source.send(amount, sender.checkMembership());
                if (sent) { target.receive(amount); }
                return sent;
            }
        }
        return false;
    }

    private BankSecretKey secretKey;
    public BankPublicKey getPublicKey(){
        BankKeyPair keypair = Encryptor.publicKeyGen(); // generates two keys : BankPublicKey, BankSecretKey
        secretKey = keypair.deckey; // stores BankSecretKey internally
        return keypair.enckey;
    }

    int maxHandshakes = 10000;
    int numSymmetrickeys = 0;
    BankSymmetricKey[] bankSymmetricKeys = new BankSymmetricKey[maxHandshakes];
    String[] AppIds = new String[maxHandshakes];

    public int getAppIdIndex(String AppId){
        for(int i=0; i<numSymmetrickeys; i++){
            if(AppIds[i].equals(AppId)){
                return i;
            }
        }
        return -1;
    }

    public void fetchSymKey(Encrypted<BankSymmetricKey> encryptedKey, String AppId){
        //TODO: Problem 1.3
        if (encryptedKey != null) {
            BankSymmetricKey bankSymetricKey = encryptedKey.decrypt(secretKey);
            if (bankSymetricKey != null) {
                int index = getAppIdIndex(AppId);
                if (index == -1 && numSymmetrickeys < maxHandshakes) {
                    AppIds[numSymmetrickeys] = AppId;
                    bankSymmetricKeys[numSymmetrickeys] = bankSymetricKey;
                    numSymmetrickeys++;
                } else if (index > -1) {
                    bankSymmetricKeys[index] = bankSymetricKey;
                }
            }
        }
    }

    public Encrypted<Boolean> processRequest(Encrypted<Message> messageEnc, String AppId) {
        //TODO: Problem 1.3
        int index = getAppIdIndex(AppId);
        if (index != -1) {
            BankSymmetricKey bankSymmetrickey = bankSymmetricKeys[index];
            if (messageEnc != null) {
                Message message = messageEnc.decrypt(bankSymmetrickey);
                if (message != null) {
                    String type = message.getRequestType();
                    int amount = message.getAmount();
                    int accountId = message.getAccountID();
                    String id = message.getId();
                    String password = message.getPassword();
                    boolean bool = false;
                    if (type == "deposit") {
                        bool = deposit(id, password, accountId, amount);
                    } else if (type == "withdraw") {
                        bool = withdraw(id, password, accountId, amount);
                    }
                    Encrypted<Boolean> res = new Encrypted<Boolean>(bool, bankSymmetrickey);
                    return res;
                }
            }
        }
        return null;
    }
}