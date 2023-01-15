package hand.agent;

public class Buyer extends Agent{
    public Buyer(double priceLimit) {
        super(priceLimit);
    }

    @Override
    public boolean willTransact(double price) {
        //TODO: Problem 2.1
        if (!hadTransaction && price <= expectedPrice) { return true; }
        return false;
    }

    @Override
    public void reflect() {
        //TODO: Problem 2.1
        if (hadTransaction) {
            expectedPrice -= adjustment;
            adjustment += 5;
            if (adjustment > adjustmentLimit) { adjustment = adjustmentLimit; }
            if (expectedPrice < 0) { expectedPrice = 0; }
        } else {
            expectedPrice += adjustment;
            if (expectedPrice > priceLimit) {expectedPrice = priceLimit; }
            else {
                adjustment -= 5;
                if (adjustment < 0) { adjustment = 0;  }
            }
        }
        hadTransaction = false;
    }
}
