import java.util.*;

public class NFTMarket {

    private Map<Integer, Asset> idAsset = new HashMap<>();
    private Map<String, Buyer> nameBuyer = new HashMap<>();

    public Map<Integer, Asset> getIdAsset() {return idAsset;}
    public Map<String, Buyer> getNameBuyer() {return nameBuyer;}

    public Buyer master;

    public NFTMarket(Buyer master) {
        this.master = master;
        nameBuyer.put("Master", master);
    }


    public boolean addAsset(int id, String item, float price, String artist){
        // TODO sub-problem 1
        if (id >= 0 && !idAsset.containsKey(id)) {
            if (price < 100000) {
                Asset asset = new Asset(id, item, price, artist, master);
                idAsset.put(id, asset);
                return true;
            }
        }
        return false;
    }

    public boolean addBuyer(String buyername){
        // TODO sub-problem 1
        if ((buyername != null && !buyername.trim().isEmpty() ) && !nameBuyer.containsKey(buyername)) {
            Buyer buyer = new Buyer(buyername, 100000);
            nameBuyer.put(buyername, buyer);
            return true;
        }
        return false;
    }
    public Asset findAsset(int id){
        // TODO sub-problem 1
        Asset asset = idAsset.get(id);
        return asset;
    }
    public Buyer findBuyer(String buyername){
        // TODO sub-problem 1
        Buyer buyer = getNameBuyer().get(buyername);
        return buyer;
    }
    public List<Asset> findAssetsWithConditions(int minprice, int maxprice, String item, String artist){
        // TODO sub-problem 2
        LinkedList AssetWithConditions = new LinkedList<Asset>();

        // Corner Conditions
        if ((minprice == -1 && maxprice != -1) || (minprice != -1 && maxprice == -1)) { return AssetWithConditions; }

        for (Asset asset : idAsset.values()) {
            if ((asset.getPrice() <= maxprice && asset.getPrice() >= minprice) || (minprice == -1 && maxprice == -1)) {
                if (asset.getArtist().equals(artist) || artist.equals("All")) {
                    if (asset.getItem().equals(item) || item.equals("All")) {
                        AssetWithConditions.add(asset);
                    }
                }
            }
        }

        Comparator<Asset> comparator = new Comparator<Asset>() {
            @Override
            public int compare(Asset o1, Asset o2) {
                return o1.getId() > o2.getId() ? 1 : -1;
            }
        };
        AssetWithConditions.sort(comparator);


        return AssetWithConditions;
    }

    public boolean trade(Buyer seller, Buyer buyer, int id, float portion){
        // TODO sub-problem 3
        if (( idAsset.containsKey(id)) && (buyer != null && seller != null)) {
            Asset asset = idAsset.get(id);
            if (asset.getOwners().contains(seller)) {
                float sellerPortion = seller.getAssetPortion(id);
                if (buyer.getBalance() >= asset.getPrice()*portion*sellerPortion) {
                    if (!asset.getOwners().contains(buyer)) {
                        buyer.addAsset(asset, portion*sellerPortion);
                        asset.getOwners().add(buyer);
                    } else {
                        buyer.setAssetPortion(id, buyer.getAssetPortion(id) + sellerPortion*portion);
                    }

                    if (portion == 1) {
                        asset.getOwners().remove(seller);
                        seller.removeAsset(asset);
                    } else {
                        seller.setAssetPortion(id, sellerPortion*(1 - portion));
                    }

                    float price = asset.getPrice()*portion*sellerPortion;
                    seller.setBalance(seller.getBalance() + price);
                    buyer.setBalance(buyer.getBalance() - price);
                    return true;
                }
            }
        }

        return false;
    }

    public void reflectIssues(Asset asset, float effectFactor) {
        // TODO sub-problem 4
        if (effectFactor >= 0) {
            asset.setPrice(asset.getPrice()*effectFactor);
        }
    }

    public void reflectIssues(String artist, float effectFactor) {
        // TODO sub-problem 4
        for (Asset asset: idAsset.values()) {
            if (asset.getArtist() == artist) {
                asset.setPrice(asset.getPrice()*effectFactor);
            }
        }
    }
}
