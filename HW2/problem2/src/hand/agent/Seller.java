package hand.agent;

public class Seller extends Agent{
    public Seller(double priceLimit) {
        super(priceLimit);
    }

    @Override
    public boolean willTransact(double price) {
        //TODO: Problem 2.1
        if (!hadTransaction && price >= expectedPrice) { return true; }
        return false;
    }

    @Override
    public void reflect() {
        //TODO: Problem 2.1
        if (hadTransaction) {
            expectedPrice += adjustment;
            adjustment += 5;
            if (adjustment > adjustmentLimit) {adjustment = adjustmentLimit;}
        } else {
            expectedPrice -= adjustment;
            if (expectedPrice < priceLimit) {expectedPrice = priceLimit; }
            adjustment -= 5;
            if (adjustment < 0) { adjustment = 0;  }
        }
        hadTransaction = false;
    }
}
