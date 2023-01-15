package bank;

public class Client {
    private int numAccounts = 0;
    final static int maxAccounts = 10;
    private BankAccount[] accounts = new BankAccount[maxAccounts];

    private String id;
    private String password;
    private String membership;
    private boolean authenticated = false;

    Client(String id, String password) {
        //TODO: Problem 1.1
        this.id = id;
        this.password = password;

    }

    String getId(){
        return this.id;
    }

    String checkMembership(){
        //TODO: Problem 1.1
        int total_balance = 0;
        for (int i=0; i<numAccounts; i++) {
            total_balance += accounts[i].getBalance();
        }
        if (total_balance > 10000) {
            this.membership = "VIP";
            return "VIP";
        } else {
            this.membership = "Normal";
            return "Normal";
        }
    }

    boolean authenticate(String password){
        //TODO: Problem 1.1
        if (password == this.password) {
            this.authenticated = true;
            return true;
        }
        return false;
    }

    void expireAuthenticatedState(){
        //TODO: Problem 1.1
        this.authenticated = false;
    }

    BankAccount findAccount(int accountID){
        //TODO: Problem 1.1
        for (int i=0; i<numAccounts; i++) {
            if (accounts[i].getAccountId() == accountID) {
                return accounts[i];
            }
        }
        return null;
    }

    boolean createAccount(int accountID, int initBalance){
        //TODO: Problem 1.1
        if (findAccount(accountID) == null) {
            BankAccount account = new BankAccount(accountID, initBalance);
            accounts[this.numAccounts++] = account;
            return true;
        }
        return false;
    }
}
