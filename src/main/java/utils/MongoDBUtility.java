package utils;


import com.mongodb.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.BasicDBObject;
import org.apache.log4j.Logger;
import org.bson.Document;

public class MongoDBUtility {


    MongoClient mongoClient;
    MongoDatabase database;
    BasicDBObject basicObject;
    JSONWriter jsonWriter;
    String productNamestr = "ProductName";
    String priceDetailstr = "priceDetail";
    String deliveryTypestr = "deliveryType";
    String webSite = "WebSite";
    YAMLWriter yamlWriter;
    Logger log = Logger.getLogger(MongoDBUtility.class);

    public void createConnection() {

        //Creating a Mongo Client
        mongoClient = new MongoClient("127.0.0.1", 27017);
        database = mongoClient.getDatabase("ECommerceDatabaseforAPI");
        log.info("Established connection to Database");
        basicObject = new BasicDBObject();
    }

    public void closeConnection() {
        mongoClient.close();
    }

    public void writeInDatabase(String productName, String priceDetail, String deliveryType, String WebsiteName) {

        createConnection();

        List<Document> writes = new ArrayList<Document>();
        MongoCollection<Document> document = database.getCollection(WebsiteName + "ProductTable");
        log.info(WebsiteName + " table exist");

        Document d1 = new Document();
        d1.append(webSite, WebsiteName);
        d1.append(productNamestr, productName);
        d1.append(priceDetailstr, priceDetail);
        d1.append(deliveryTypestr, deliveryType);
        d1.append("TimeStamp", new Date());
        writes.add(d1);
        document.insertMany(writes);
    }


    public List<Document> readFromDatabase(String websiteName) {

        createConnection();

        MongoCollection<Document> collection = database.getCollection(websiteName + "ProductTable");

        List<Document> documents = collection.find().into(new ArrayList<Document>());

        //Call JSON Writer class in order to print data in JSON file
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.printInJSONFile(documents);

        try {
            sendDatatoYAMLFile(documents);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return documents;
    }

    //call YAML Writer class in order to print data in YAML file
    public void sendDatatoYAMLFile(List<Document> productInfo)throws IOException{

        yamlWriter = new YAMLWriter();
        yamlWriter.writeInYAMLFile(productInfo);
    }
}