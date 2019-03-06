package utils;
//import pages.com.Amaon.ProductDetailPage;
import org.apache.log4j.Logger;
import org.bson.Document;
import java.util.List;

public class DBAndUiInterface {

    MongoDBUtility mongoDBUtililty;
    Logger log = Logger.getLogger(DBAndUiInterface.class);


    public void saveProductInfotoDB(String productName, String price, String deliveryType, String Website) {

        mongoDBUtililty = new MongoDBUtility();
        mongoDBUtililty.writeInDatabase(productName, price, deliveryType, Website);
    }


    public void retrieveDbInfo(String Website) {

        mongoDBUtililty = new MongoDBUtility();
        List<Document> ProductInfo = mongoDBUtililty.readFromDatabase(Website);

        log.info("Product Information");

        for (Document document : ProductInfo) {
            System.out.println(document);
        }
    }
}
