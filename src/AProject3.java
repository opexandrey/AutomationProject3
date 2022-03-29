import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AProject3 {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\__DuoTech\\SoftWare\\Automation\\Selenium\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        //there are some commented lines with different selectors due
        //to once this assignment was almost done, carfax site was changed
        //due to VPN usage


        driver.get("https://www.carfax.com/");
     //   driver.findElement(By.cssSelector("div#main-content > div:nth-child(2) >div:nth-child(3) > a")).click();
        driver.findElement(By.xpath("//a[@href='/cars-for-sale']")).click();

        //verifying title
        Assert.assertTrue(driver.getTitle().contains("Used Cars"));
        System.out.println("Title verified");

//        WebElement makeDropDown = driver.findElement(By.cssSelector("select.form-control.search-make.search-make--lp"));
//        Select selectMake = new Select(makeDropDown);

     //  Select selectMake = new Select(driver.findElement(By.cssSelector("select.form-control.search-make.search-make--lp")));
        Select selectMake = new Select(driver.findElement(By.xpath("//select[@name='make']")));
       selectMake.selectByValue("Ram");// I think I found a bug here. If I choose tesla first, model dropDown is inactive
       selectMake.selectByValue("Tesla");



        // verifying Tesla models
        Select selectModel = new Select(driver.findElement(By.cssSelector("select[aria-label = 'Search Model']")));
        List<String> expectedTeslaModels = Arrays.asList("Model 3", "Model S", "Model X", "Model Y");
        List<WebElement> teslaModels = selectModel.getOptions();
        List<String>  actualTeslaModels = new ArrayList<>();

        for (int i =1; i < teslaModels.size()-1; i++){
            actualTeslaModels.add(teslaModels.get(i).getText().trim());
        }

        Assert.assertEquals(actualTeslaModels, expectedTeslaModels);
        System.out.println("Tesla models verified");


        // selecting model  and choosing zip code
        selectModel.selectByValue("Model S");
     //   driver.findElement(By.cssSelector("input.search-zip.ui-input.search-zip--lp.null")).sendKeys("22182");
        driver.findElement(By.cssSelector("input[name='zip']")).sendKeys("22182");
     //   driver.findElement(By.xpath("//button[@id='make-model-form-submit-button']")).click();
        driver.findElement(By.id("make-model-form-submit-button")).click();


        //verifying text
        String expectedText = "Step 2 - Show me cars with";
        String actualText = driver.findElement(By.cssSelector("h3.searchForm-wrapper-header--lp")).getText();
     //   String actualText = driver.findElement(By.cssSelector("h5")).getText();
        Assert.assertEquals(actualText, expectedText);
        System.out.println("Text verified");

        //storing checkboxes and selecting them
        List<WebElement> checkBoxes4 = driver.findElements(By.cssSelector("span[role='checkbox']"));

        for(WebElement checkbox1 : checkBoxes4){
            if (!checkbox1.isSelected()){
                checkbox1.click();
            }
        }

        //getting the count of results
        String countString = driver.findElement(By.xpath("//button[@class='button large primary-green show-me-search-submit']")).getText();
        int count = Integer.parseInt(countString.substring(8,10));
        System.out.println(count);

        driver.findElement(By.xpath("//button[@class='button large primary-green show-me-search-submit']")).click();

        //verifying results count
        List<WebElement> searchResults = driver.findElements(By.cssSelector("article[title = 'Click to see details..']"));
        Assert.assertEquals(searchResults.size(), count);
        System.out.println("Result count verified");

        //verifying tesla model s in the result headers
        List<WebElement> resultHeaders = driver.findElements(By.cssSelector("h4.srp-list-item-basic-info-model"));
        List<String> resultHeaderString = new ArrayList<>();

        for(WebElement header : resultHeaders){
            resultHeaderString.add(header.getText());
        }

        String actualPartialHeader = "Tesla Model S";

        for(String str : resultHeaderString){
            Assert.assertTrue(str.contains(actualPartialHeader));
        }
        System.out.println("Headers verified");



        //getting prices of the search result
        List<WebElement> priceList = driver.findElements(By.cssSelector("span.srp-list-item-price"));
        List<String> priceListString = new ArrayList<>();

        for (WebElement price : priceList){
            priceListString.add(price.getText());
        }


// checking the order of the appearance
//        for (int i = 0; i < priceListString.size(); i++){
//           System.out.println(priceListString.get(i));
//       }


        //verifying  price -highToLow
        Select sortBy = new Select(driver.findElement(By.xpath("//select[@class='srp-header-sort-select srp-header-sort-select-desktop--srp']")));
        sortBy.selectByValue("PRICE_DESC");

        String actualPriceSort = sortBy.getFirstSelectedOption().getText().trim();
        String expectedPriceSort = "Price - High to Low";
      //  Assert.assertTrue(actualPriceSort.equals(expectedPriceSort));
        Assert.assertEquals(actualPriceSort, expectedPriceSort);
        System.out.println("Price sort verified");


        // verifying mileage LowToHigh
        sortBy.selectByValue("MILEAGE_ASC");

        String actualMileageSort = sortBy.getFirstSelectedOption().getText().trim();
        String expectedMileageSort = "Mileage - Low to High";
      //  Assert.assertTrue(actualMileageSort.equals(expectedMileageSort));
        Assert.assertEquals(actualMileageSort,expectedMileageSort);
        System.out.println("Mileage sort verified");


        // verifying year - new to old
        sortBy.selectByVisibleText("Year - New to Old");
        String actualYearSort = sortBy.getFirstSelectedOption().getText().trim();
        String expectedYearSort = "Year - New to Old";
      //  Assert.assertTrue(actualYearSort.equals(expectedYearSort));
        Assert.assertEquals(actualYearSort,expectedYearSort);
        System.out.println("Year sort verified");


        driver.quit();


    }
}
