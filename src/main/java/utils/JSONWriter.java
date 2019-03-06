package utils;

import entities.productdetails;
import com.google.gson.Gson;
import java.util.List;
import org.bson.Document;
import java.util.stream.Collectors;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

public class JSONWriter {


    String productName = "ProductName";
    String priceDetail = "priceDetail";
    String DeliveryType = "deliveryType";
    String WebSite = "WebSite";

    public void printInJSONFile(List<Document> documentList){


        List<productdetails> productDetailsList = documentList.stream().map(document -> {
            productdetails productDetail = new productdetails();

            productDetail.setProductName(document.getString(productName));
            productDetail.setPrice(document.getString(priceDetail));
            productDetail.setDeliveryType(document.getString(DeliveryType));
            productDetail.setWebsite(document.getString(WebSite));
            return productDetail;
        }).collect(Collectors.toList());

        Gson gson = new Gson();
        String s = gson.toJson(productDetailsList);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("./src/main/java/utils/productdetailinjson.json"),true);
            fileOutputStream.write(s.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}