package bank;

public class Session {

    private String sessionKey;
    private Bank bank;
    private boolean valid;
    private int transLimit = 3;
    private int numCall = 0;

    Session(String sessionKey,Bank bank){
        this.sessionKey = sessionKey;
        this.bank = bank;
        this.valid = true;
    }

    public boolean deposit(int amount) {
        //TODO: Problem 1.2
        if (valid) {
            boolean deposit = bank.deposit(sessionKey, amount);
            numCall++;
            if (transLimit <= numCall) {
                expireSession();
            }
            return deposit;
        }
        return false;
    }

    public boolean withdraw(int amount) {
        //TODO: Problem 1.2
        if (valid) {
            boolean withdraw = bank.withdraw(sessionKey, amount);
            numCall++;
            if (transLimit <= numCall) {
                expireSession();
            }
            return withdraw;
        }
        return false;
    }

    public boolean transfer(String targetId, int targetAccountID, int amount) {
        //TODO: Problem 1.2
        if (valid) {
            boolean transfer = bank.transfer(sessionKey, targetId, targetAccountID, amount);
            numCall++;
            if (transLimit <= numCall) {
                expireSession();
            }
            return transfer;
        }
        return false;
    }

    public void expireSession(){
        this.valid = false;
    }

}
