package com.eforklift.dao;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.eforklift.dto.ItemOffRoad;



public class CargarListaItemsSeleniumDao {
	
	
    public List<ItemOffRoad> buscarPartes(String partnumberSearch) {

        List<ItemOffRoad> ls = new ArrayList<ItemOffRoad>();
        ItemOffRoad item;
        WebDriver driver = new FirefoxDriver();
//        WebDriver driver = new ChromeDriver();
        try {

       // WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());
            // HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
            //   driver.setJavascriptEnabled(true);
            // Go to the Google Suggest home page
            driver.get("http://www.offroadeq.com");

            WebElement user = driver.findElement(By.id("textinput1"));
            user.sendKeys("javier64116");

            WebElement pass = driver.findElement(By.id("textinput2"));
            pass.sendKeys("JLPS64116");

            WebElement buttonLogin = driver.findElement(By.id("input2"));
            buttonLogin.click();

            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("here"))); 

            WebElement buttonBotonStartQuote = driver.findElement(By.partialLinkText("here"));
            buttonBotonStartQuote.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quote_input_part")));

            WebElement inputPart = driver.findElement(By.id("quote_input_part"));
            inputPart.sendKeys(partnumberSearch);

            WebElement buttonFindPart = driver.findElement(By.id("quote_button_part"));

            buttonFindPart.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quote_warehouse_table")));

            WebElement tablaElementos = driver.findElement(By.id("quote_warehouse_table"));

            //  List<WebElement> allSuggestions = driver.findElements(By.xpath("//td[@class='gssb_a gbqfsf']"));
            List<WebElement> tr_collection = tablaElementos.findElements(By.xpath("id('quote_warehouse_table')/tbody/tr"));

            System.out.println(
                    "NUMBER OF ROWS IN THIS TABLE = " + tr_collection.size());

            for (WebElement trElement : tr_collection) {
                List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
                item = new ItemOffRoad();
                //col_num = 1;
                
                item.setPartnumber(partnumberSearch);
                item.setLocation(td_collection.get(3).getText());
                item.setDescription(td_collection.get(5).getText());
                item.setPrice(td_collection.get(6).getText());
                item.setBrand("CATERPILLAR");
                System.out.println("L " + item.getLocation());
                System.out.println("D " + item.getDescription());
                System.out.println("P " + item.getPrice());
                if(!item.getDescription().equals("UNKNOWN"))
                	ls.add(item);
                //row_num++;
            }

            //vintage parts 
            driver.get("http://www.vpartsinc.com/search?manufacturers=&sortField=sku&sortOrder=ASC&sSearch=" + partnumberSearch + "&x=0&y=0&searchMode=starts_with");

//         WebElement tablaElementosVparts = driver.findElement(By.id("resultsList"));
            List<WebElement> tr_collection2 = driver.findElements(By.xpath("id('resultsList')/table/tbody/tr"));

            for (WebElement trElement : tr_collection2) {
                List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
                if(td_collection.size()>0)
                {	
	                item = new ItemOffRoad();
	                item.setPartnumber( td_collection.get(1).getText());
	                item.setLocation("UNKNOWN");
	                item.setDescription(td_collection.get(2).getText());
	                item.setPrice("0");
	                item.setBrand(td_collection.get(3).getText());
	                System.out.println("partNumber: "+item.getPartnumber());
	                ls.add(item);
                }
            }

            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ls;
    }

}
