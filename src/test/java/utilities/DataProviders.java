package utilities;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "logindata")
    public String [] [] getData() throws IOException {
        String path = ".\\testdata\\OpenCartLoginData.xlsx";

        ExcelUtility xlutility = new ExcelUtility(path);
        int totalRows = xlutility.getRowCount("Sheet1");
        int totalcells = xlutility.getCellCount("Sheet1",1);

        String logindata [] [] = new String[totalRows][totalcells];
        for (int r=1;r<=totalRows;r++){
            for (int c=0;c<totalcells;c++){

                logindata[r-1][c] = xlutility.getCellData("Sheet1",r,c);
            }
        }
        return logindata;
    }
}
