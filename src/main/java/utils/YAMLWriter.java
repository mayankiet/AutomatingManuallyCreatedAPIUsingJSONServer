package utils;

import com.google.gson.Gson;
import entities.productdetails;
import org.bson.Document;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.IOException;

public class YAMLWriter {

    String productName = "ProductName";
    String priceDetail = "priceDetail";
    String DeliveryType = "deliveryType";
    String WebSite = "WebSite";

    public void writeInYAMLFile(List<Document> documentList)throws IOException{


        List<productdetails> productDetailsList = documentList.stream().map(document -> {
            productdetails productDetails = new productdetails();
            productDetails.setProductName(document.getString(productName));
            productDetails.setPrice(document.getString(priceDetail));
            productDetails.setDeliveryType(document.getString(DeliveryType));
            productDetails.setWebsite(document.getString(WebSite));
            return productDetails;
        }).collect(Collectors.toList());

        Gson gson = new Gson();
        String JsonString = gson.toJson(productDetailsList);
        //parse json string
        JsonNode jsonNodeTree = new ObjectMapper().readTree(JsonString);
        //save it as YAML string
        String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("./src/main/java/utils/DBdata.yaml"), true);
            fileOutputStream.write(jsonAsYaml.getBytes());
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