import java.util.*;

public class Buyer {
    private String buyername;
    private float balance;
    private Map<Integer, Pair<Asset, Float>> portfolio;

    public Buyer(String buyername) {
        // initial balance is 100000 for each buyer
        this(buyername, 100000);
    }

    public Buyer(String buyername, float balance){
        this.balance = balance;
        this.buyername = buyername;
        portfolio = new HashMap<Integer, Pair<Asset, Float>>();
    }
    public float getBalance(){return balance;}

    public void setBalance(float newBalance) { balance = newBalance; }

    // TODO sub-problem 1~4
    public void addAsset(Asset asset, float portion) {
        Pair<Asset, Float> AssetPortionPair = new Pair(asset, portion);
        portfolio.put(asset.getId(), AssetPortionPair);
    }

    public void removeAsset(Asset asset) {
        portfolio.remove(asset.getId());
    }

    public void setAssetPortion(int id, float portion) {
        Pair<Asset, Float> assetPortionPair = portfolio.get(id);
        assetPortionPair.setValue(portion);
    }

    public float getAssetPortion(int id) {
        Pair<Asset, Float> assetPortionPair = portfolio.get(id);
        if (assetPortionPair != null) {
            float portion = assetPortionPair.getValue();
            return portion;
        }
        return 0;
    }

    public float getTotalValue() {
        float totalValue = balance;
        for (Pair<Asset, Float> pair: portfolio.values()) {
            Asset asset = pair.getKey();
            float portion = pair.getValue();
            float assetPrice = asset.getPrice()*portion;
            totalValue += assetPrice;
        }
        return totalValue;
    }

    @Override
    public String toString() {
        return buyername;
    }
}