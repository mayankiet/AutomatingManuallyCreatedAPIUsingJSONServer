package utils;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class configdataprovider {


    Properties properties;
    Logger log = Logger.getLogger(configdataprovider.class);

    public configdataprovider() {

        File src = new File("./src/main/resources/Config.properties");

        try {
            FileInputStream fp = new FileInputStream(src);
            properties = new Properties();
            properties.load(fp);
        } catch (Exception e) {

            log.info("Not able to load config file");
            e.printStackTrace();

        }
    }

    public String getAPIURL(String APIURL) {

        return properties.getProperty(APIURL);
    }

    public String getProuctName(String ProductSearch){

        return properties.getProperty(ProductSearch);
    }
}