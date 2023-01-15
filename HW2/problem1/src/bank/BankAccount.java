package bank;

import bank.event.*;

class BankAccount {
    private Event[] events = new Event[maxEvents];
    final static int maxEvents = 100;

    private int event_num;
    private int accountId;
    private int balance;

    BankAccount(int accountID, int balance) {
        //TODO: Problem 1.1
        this.accountId = accountID;
        this.balance = balance;
        this.event_num = 0;
    }

    int getAccountId() {
        return accountId;
    }
    int getBalance() {
        return balance;
    }

    Event[] getEvents() {
        Event[] events = new Event[event_num];
        for (int i=0; i<event_num; i++)
            events[i] = this.events[i];
        return events;
    }

    void deposit(int amount) {
        //TODO: Problem 1.1
        this.events[event_num] = new DepositEvent();
        event_num++;
        this.balance += amount;
    }

    boolean withdraw(int amount, String membership) {
        //TODO: Problem 1.1
        if (membership == "Normal") {
            if (this.balance >= 5 + amount) {
                this.balance -= amount + 5;
                this.events[event_num] = new WithdrawEvent();
                event_num++;
                return true;
            }

        } else if (membership == "VIP") {
            if (this.balance >= amount) {
                this.balance -= amount;
                this.events[event_num] = new WithdrawEvent();
                event_num++;
                return true;
            }
        }
        return false;
    }

    void receive(int amount) {
        //TODO: Problem 1.1
        this.balance += amount;
        this.events[event_num] = new ReceiveEvent();
        event_num++;
    }

    boolean send(int amount, String membership) {
        if (membership == "Normal") {
            if (this.balance >= amount + 5) {
                this.balance -= amount + 5;
                this.events[event_num] = new SendEvent();
                event_num++;
                return true;
            }

        } else if (membership == "VIP") {
            if (this.balance >= amount) {
                this.balance -= amount;
                this.events[event_num] = new SendEvent();
                event_num++;
                return true;
            }
        }

        //TODO: Problem 1.1
        return false;
    }
}
