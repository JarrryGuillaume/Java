import java.util.*;

public class Asset {
    private int id;
    private String item;
    private float price;
    private String artist;
    private List<Buyer> owners;

    public Asset(int id, String item, float price, String artist, Buyer buyer){
        this.id = id;
        this.item = item;
        this.price = price;
        this.artist = artist;
        this.owners = new ArrayList<>();
        this.owners.add(buyer);
        buyer.addAsset(this, 1);
    }

    public List<Buyer> getOwners(){return owners;}
    // TODO sub-problem 1~4


    public void setPrice(float newprice) { price = newprice; }
    public String getArtist() { return artist; }

    public int getId() { return id; }

    public String getItem() { return item; }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "" + id;
    }

}
