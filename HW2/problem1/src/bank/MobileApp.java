package bank;

import security.key.BankPublicKey;
import security.key.BankSymmetricKey;
import security.*;

public class MobileApp {

    private String randomUniqueStringGen(){
        return Encryptor.randomUniqueStringGen();
    }
    private final String AppId = randomUniqueStringGen();
    public String getAppId() {
        return AppId;
    }

    private BankSymmetricKey bankSymmetricKey;

    String id, password;
    int accountID;
    public MobileApp(String id, String password, int accountID){
        this.id = id;
        this.password = password;
        this.accountID = accountID;
    }

    public Encrypted<BankSymmetricKey> sendSymKey(BankPublicKey publickey){
        //TODO: Problem 1.3
        this.bankSymmetricKey = new BankSymmetricKey(randomUniqueStringGen());
        Encrypted<BankSymmetricKey> CryptedSymetricKey = new Encrypted<BankSymmetricKey>(bankSymmetricKey, publickey);
        return CryptedSymetricKey;
    }

    public Encrypted<Message> deposit(int amount){
        //TODO: Problem 1.3
        Message deposit = new Message("deposit", id, password, accountID, amount);
        Encrypted<Message> cryptedDepositMessage = new Encrypted<Message>(deposit, bankSymmetricKey);
        return cryptedDepositMessage;
    }

    public Encrypted<Message> withdraw(int amount){
        //TODO: Problem 1.3
        Message withdraw = new Message("withdraw", id, password, accountID, amount);
        Encrypted<Message> cryptedWithdrawMessage = new Encrypted<Message>(withdraw, bankSymmetricKey);
        return cryptedWithdrawMessage;
    }


    public boolean processResponse(Encrypted<Boolean> obj){
        //TODO: Problem 1.3
        if (obj != null) {
            Boolean decrypted = obj.decrypt(bankSymmetricKey);
            if (decrypted != null) {
                return decrypted;
            }
        }
        return false;
    }
}

