package techproed.tests.dataprovider;

import org.testng.annotations.Test;
import techproed.utilities.DataProviderUtils;

public class Day25_DataProviderTest4 {
    //Bu ornekte , data provider metodumuz Utill class 'dan gelecek

//musteriVerileri
    @Test(dataProvider = "musteriVerileri",dataProviderClass = DataProviderUtils.class)
    public void musteriVerileriTest(String username, String password, String sube) {
        System.out.println("username : " + username+ ". password : " + password+ " . sube : " + sube);

    }

    //musteriHizmetleriVerileri
    @Test(dataProvider = "musteriHizmetleriVerileri",dataProviderClass = DataProviderUtils.class)
    public void musteriHizmetleriVerileriTest(String username, String password) {
        System.out.println("username : " + username+ ". password : " + password);
    }

    //personeliVerileri
    @Test(dataProvider = "personelVerileri",dataProviderClass = DataProviderUtils.class)
    public void personeliVerileriTest(String username, String password) {
        System.out.println("username : " + username+ ". password : " + password);
    }


//customerData


    @Test(dataProvider = "customerData",dataProviderClass = DataProviderUtils.class)
    public void customerDataTest(String username, String password) {
        System.out.println("EXCEL DATALARIM");
        System.out.println( username+ "|||" + password);

    }
}
