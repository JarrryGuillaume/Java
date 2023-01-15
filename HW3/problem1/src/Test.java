import java.util.List;

public class Test {
    public static void main(String[] args){
        nftTest1();
        nftTest2();
        nftTest3();
        nftTest4();
    }
    
    static NFTMarket initializeNFTMarket(){
        NFTMarket nftMarket = new NFTMarket(new Buyer("Master", 300000));
        nftMarket.addAsset(10, "Doodle", 10000, "Alice");
        nftMarket.addAsset(20, "Pixel Art", 15000, "Alice");
        nftMarket.addAsset(30, "Landscape", 20000, "Alice");
        nftMarket.addAsset(40, "2D Character", 25000, "Alice");
        nftMarket.addAsset(50, "3D Character", 30000, "Alice");
        nftMarket.addAsset(100, "Doodle", 5000, "Bob");
        nftMarket.addAsset(200, "Pixel Art", 7500, "Bob");
        nftMarket.addAsset(300, "Landscape", 10000, "Bob");
        nftMarket.addAsset(400, "2D Character", 12500, "Bob");
        nftMarket.addAsset(500, "3D Character", 15000, "Bob");
        nftMarket.addAsset(1000, "Doodle", 15000, "Charles");
        nftMarket.addAsset(2000, "Pixel Art", 22500, "Charles");
        nftMarket.addAsset(3000, "Landscape", 30000, "Charles");
        nftMarket.addAsset(4000, "2D Character", 37500, "Charles");
        nftMarket.addAsset(5000, "3D Character", 45000, "Charles");
        nftMarket.addAsset(60, "3D Character", 4500, "Ray");
        nftMarket.addBuyer("Smart");
        nftMarket.addBuyer("Rich");
        nftMarket.addBuyer("ManyPeople");
        nftMarket.addBuyer("Newborn");
        return nftMarket;
    }

    static void nftTest1(){
        System.out.println("nft Test 1");
        NFTMarket nftMarket = initializeNFTMarket();
        Asset doodle = nftMarket.findAsset(10);
        Asset landscape = nftMarket.findAsset(30);
        Asset noArtwork = nftMarket.findAsset(1);
        Buyer smart = nftMarket.findBuyer("Smart");
        System.out.println("\tShould be ID 10 : " + doodle);
        System.out.println("\tShould be ID 30 : " + landscape);
        System.out.println("\tShould be null : " + noArtwork);
        System.out.println("\tShould be false : " + nftMarket.addAsset(30, "Landscape", 1000000, "David'"));
        System.out.println("\tShould be Smart : " + smart);
        System.out.println("\tShould be false : " + nftMarket.addBuyer("Rich"));
        System.out.println("\tShould be false : " + nftMarket.addAsset(5000, "3D Character", 450, "Ray"));
        System.out.println("\tShould be false : " + nftMarket.addBuyer(""));
        System.out.println("\tShould be null : " + nftMarket.findAsset(13));
        System.out.println("\tShould be false : " + nftMarket.addAsset(-20, "toothbrush", 10000, "David'"));
        System.out.println("\tShould be null : " + nftMarket.findBuyer("MadMax"));
        System.out.println("\tShould be false : " + nftMarket.addAsset(12, "", 1000, "David'"));
        System.out.println("\n");
    }

    static void nftTest2(){
        System.out.println("nft Test 2");
        NFTMarket nftMarket = initializeNFTMarket();
        List<Asset> foundAssets = nftMarket.findAssetsWithConditions(-1, -1, "All","All");
        System.out.println("\tShould be "+ nftMarket.getIdAsset().size() +" : "+ foundAssets.size());
        foundAssets = nftMarket.findAssetsWithConditions(-1, -1, "All","Charles");
        System.out.println("\tShould be [1000, 2000, 3000, 4000, 5000] : "+ foundAssets);
        foundAssets = nftMarket.findAssetsWithConditions(5000, 10000, "All","Bob");
        System.out.println("\tShould be [100, 200, 300] : "+ foundAssets);
        foundAssets = nftMarket.findAssetsWithConditions(-1, 20000, "3D Character","Bob");
        System.out.println("\tShoud be [] : " + foundAssets);
        foundAssets = nftMarket.findAssetsWithConditions(1000, -1, "3D Character","Bob");
        System.out.println("\tShoud be [] : " + foundAssets);
        foundAssets = nftMarket.findAssetsWithConditions(1000, 50000, "3D Character","All");
        System.out.println("\tShoud be [50, 60, 500, 5000] : " + foundAssets);
        foundAssets = nftMarket.findAssetsWithConditions(1000, 5000, "3D Character","Bob");
        System.out.println("\tShoud be [] : " + foundAssets);

        System.out.println("\n");
    }

    static void nftTest3(){
        System.out.println("nft Test 3");
        NFTMarket nftMarket = initializeNFTMarket();
        System.out.println("\tShould be [Master] : " +  nftMarket.getIdAsset().get(100).getOwners());
        nftMarket.trade(nftMarket.master, nftMarket.getNameBuyer().get("Smart"), 100, 0.5f);
        System.out.println("\tShould be [Master, Smart] : " +  nftMarket.getIdAsset().get(100).getOwners());
        nftMarket.trade(nftMarket.master, nftMarket.getNameBuyer().get("Smart"), 100, 1);
        System.out.println("\tShould be [Smart] : " +  nftMarket.getIdAsset().get(100).getOwners());
        nftMarket.addBuyer("Broke");
        nftMarket.addAsset(70, "expensive", 65000, "Ray");
        nftMarket.addAsset(72, "luxury", 55000, "Ray");
        nftMarket.trade(nftMarket.master, nftMarket.getNameBuyer().get("Broke"), 70, 1f);
        nftMarket.trade(nftMarket.master, nftMarket.getNameBuyer().get("Broke"), 72, 1f);
        System.out.println("\tShould be [Broke] : " +  nftMarket.getIdAsset().get(70).getOwners());
        System.out.println("\tShould be [Master] : " +  nftMarket.getIdAsset().get(70).getOwners());
        nftMarket.trade(nftMarket.getNameBuyer().get("Broke"), nftMarket.getNameBuyer().get("Rich"), 70, 0.5f);
        System.out.println("\tShould be [Broke, Rich] : " +  nftMarket.getIdAsset().get(70).getOwners());
        nftMarket.trade(nftMarket.getNameBuyer().get("Broke"), nftMarket.getNameBuyer().get("Rich"), 72, 0.5f);
        System.out.println("\tShould be [Master] : " +  nftMarket.getIdAsset().get(72).getOwners());
        nftMarket.trade(nftMarket.master, nftMarket.getNameBuyer().get("No one"), 72, 0.5f);
        System.out.println("\tShould be [Master] : " +  nftMarket.getIdAsset().get(72).getOwners());
        System.out.println("\tShould be false : " +  nftMarket.trade(nftMarket.master, nftMarket.getNameBuyer().get("No one"), 76, 0.5f));
        System.out.println("\n");
    }

    static void nftTest4(){
        System.out.println("nft Test 4");
        NFTMarket nftMarket = initializeNFTMarket();
        System.out.println("\tShould be [Master] : " +  nftMarket.getIdAsset().get(100).getOwners());
        nftMarket.trade(nftMarket.master, nftMarket.getNameBuyer().get("Smart"), 100, 0.5f);
        System.out.println("\tShould be [Master, Smart] : " +  nftMarket.getIdAsset().get(100).getOwners());
        System.out.println("\tBalance of Smart should be 97500.0 : " +  nftMarket.getNameBuyer().get("Smart").getBalance());
        System.out.println("\tTotal value of Smart should be 100000.0 : " +  nftMarket.getNameBuyer().get("Smart").getTotalValue());
        nftMarket.reflectIssues(nftMarket.getIdAsset().get(100), 0.5f);
        System.out.println("\tShould be 2500.0 : " + nftMarket.getIdAsset().get(100).getPrice());
        System.out.println("\tTotal value of Smart should be 98750.0 : " +  nftMarket.getNameBuyer().get("Smart").getTotalValue());
        nftMarket.trade(nftMarket.master, nftMarket.getNameBuyer().get("Rich"), 500, 1f);
        System.out.println("\tShould be [Rich] : " +  nftMarket.getIdAsset().get(500).getOwners());
        System.out.println("\tBalance of Rich should be 85000.0 : " +  nftMarket.getNameBuyer().get("Rich").getBalance());
        nftMarket.reflectIssues("Bob", 0.5f);
        System.out.println("\tShould be 1250.0 : " + nftMarket.getIdAsset().get(100).getPrice());
        System.out.println("\tTotal value of Smart should be 98125.0 : " +  nftMarket.getNameBuyer().get("Smart").getTotalValue());
        System.out.println("\tTotal value of Rich should be 92500.0 : " +  nftMarket.getNameBuyer().get("Rich").getTotalValue());
        System.out.println("\n");
    }
}