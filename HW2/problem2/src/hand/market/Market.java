package hand.market;

import hand.agent.Buyer;
import hand.agent.Seller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Pair<K, V >{
    public K key;
    public V value;
    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    public K getKey() {return key; }

    public V getValue() {return value; }
}

public class Market {

    public ArrayList<Buyer> buyers;
    public ArrayList<Seller> sellers;

    public Market(int nb, ArrayList<Double> fb, int ns, ArrayList<Double> fs) {
        buyers = createBuyers(nb, fb);
        sellers = createSellers(ns, fs);
    }

    private ArrayList<Pair<Seller, Buyer>> matchedPairs(int day, int round) {
        if(buyers==null || sellers==null) return null;
        ArrayList<Seller> shuffledSellers = new ArrayList<>(sellers);
        ArrayList<Buyer> shuffledBuyers = new ArrayList<>(buyers);
        Collections.shuffle(shuffledSellers, new Random(71 * day + 43 * round + 7));
        Collections.shuffle(shuffledBuyers, new Random(67 * day + 29 * round + 11));
        ArrayList<Pair<Seller, Buyer>> pairs = new ArrayList<>();
        for (int i = 0; i < shuffledBuyers.size(); i++) {
            if (i < shuffledSellers.size()) {
                pairs.add(new Pair<>(shuffledSellers.get(i), shuffledBuyers.get(i)));
            }
        }
        return pairs;
    }

    public double simulate() {
        //TODO: Problem 2.2 and 2.3
        int totalAmount = 0;
        int numTransation = 0;

        for (int day = 1; day <= 2000; day++) { // do not change this line
            for (int round = 1; round <= 10; round++) { // do not change this line
                ArrayList<Pair<Seller, Buyer>> pairs = matchedPairs(day, round); // do not change this line
                for (Pair pair : pairs) {
                    Seller seller = (Seller) pair.getKey();
                    Buyer buyer = (Buyer) pair.getValue();
                    double price = seller.getExpectedPrice();
                    if (buyer.willTransact(price) && seller.willTransact(price)) {
                        buyer.makeTransaction();
                        seller.makeTransaction();
                        if (day == 2000) {
                            totalAmount += price;
                            numTransation++;
                        }
                    }
                }
            }
            if (day == 1999) {
                System.out.println(buyers.get(500).getExpectedPrice() + " " + sellers.get(500).getExpectedPrice());
                System.out.println(buyers.get(600).getExpectedPrice() + " " + sellers.get(600).getExpectedPrice());

            }
            for (Buyer buyer : buyers) { buyer.reflect(); }
            for (Seller seller : sellers) { seller.reflect(); }
        }

        int average = totalAmount/numTransation;
        return average;
    }

    private ArrayList<Buyer> createBuyers(int n, ArrayList<Double> f) {
        //TODO: Problem 2.3
        ArrayList<Buyer> buyers = new ArrayList<Buyer>(n);
        for (int i=1; i<=n; i++) {
            double priceLimit = polynomial(i/n, f);
            Buyer buyer = new Buyer(priceLimit);
            buyers.add(buyer);
        }
        return buyers;
    }

    private double polynomial (double i, ArrayList<Double> f) {
        double res = 0;
        for (int j=0; j<f.size(); j++) {
            res += Math.pow(i, j)*f.get(j);
        }
        return res;
    }


    private ArrayList<Seller> createSellers(int n, ArrayList<Double> f) {
        //TODO: Problem 2.3
        ArrayList<Seller> sellers = new ArrayList<Seller>(n);
        for (int i=1; i<=n; i++) {
            double priceLimit = polynomial(i/n, f);
            Seller seller = new Seller (priceLimit);
            sellers.add(seller);
        }
        return sellers;
    }
}
