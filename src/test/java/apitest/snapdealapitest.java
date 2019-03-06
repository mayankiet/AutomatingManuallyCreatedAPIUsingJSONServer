package apitest;

import org.testng.annotations.Test;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import static io.restassured.RestAssured.given;
import utils.DBAndUiInterface;
import utils.configdataprovider;
import org.apache.log4j.Logger;

public class snapdealapitest {

    configdataprovider config;
    DBAndUiInterface productInfoAmazon;
    Logger log = Logger.getLogger(amazonapitest.class);
    String productname = "ProductName";
    String productprice = "ProductPrice";
    String deliverytype = "ProductDeliveryType";
    String website = "Site";


    @Test
    public void getSnapealProductInfoThroughAPI(){

        config = new configdataprovider();
        productInfoAmazon = new DBAndUiInterface();

        String SnapdealendpointURL = config.getAPIURL("SnapdealAPI");

        Response response = given()
                .when()
                .get(SnapdealendpointURL);

        int statusCode = response.getStatusCode();

        log.info("Verifying status code");
        Assert.assertEquals(statusCode, 200);

        log.info("Converting response to String");

        String SnapdealResponse = response.asString();

        log.info(SnapdealResponse);

        JsonPath jsonPathEvaluator = response.jsonPath();
        String productName = jsonPathEvaluator.get(productname).toString();
        String productPrice = jsonPathEvaluator.get(productprice).toString();
        String productDeliveryType = jsonPathEvaluator.get(deliverytype).toString();
        String siteName = jsonPathEvaluator.get(website).toString();

    //    Assert.assertEquals(productName, config.getProuctName("ProductSearch"), "Searched product name does not match with API Response");
        productInfoAmazon.saveProductInfotoDB(productName, productPrice, productDeliveryType, siteName);
        productInfoAmazon.retrieveDbInfo(siteName);
    }
}