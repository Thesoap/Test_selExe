package test;

import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarsTest1 {

    private static final int WAIT_MAX = 4;
    static WebDriver driver;

    @BeforeClass
    public static void setup() {
        /*########################### IMPORTANT ######################*/
 /*## Change this, according to your own OS and location of driver(s) ##*/
 /*############################################################*/
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Tim\\Desktop\\Sel\\test_sel\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Tim\\Desktop\\Sel\\test_sel\\chromedriver.exe");

        //Reset Database
        com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
        driver = new ChromeDriver();
        driver.get("http://localhost:3000");
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
        //Reset Database 
        com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
    }

    @Test
    //Verify that page is loaded and all expected data are visible
    public void test1() throws Exception {
        (new WebDriverWait(driver, WAIT_MAX)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
            WebElement e = d.findElement(By.tagName("tbody"));
            List<WebElement> rows = e.findElements(By.tagName("tr"));
            Assert.assertThat(rows.size(), is(5));
            return true;
        });
    }

    @Test
    //Verify the filter functionality 
    public void test2() throws Exception {
        //No need to WAIT, since we are running test in a fixed order, we know the DOM is ready (because of the wait in test1)
        WebElement element = driver.findElement(By.id("filter"));
        element.sendKeys("2002");
        WebElement body = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = body.findElements(By.tagName("tr"));
        Assert.assertThat(rows.size(), is(2));
    }

    @Test
    //Verify the filter functionality 
    public void test3() throws Exception {
        //No need to WAIT, since we are running test in a fixed order, we know the DOM is ready (because of the wait in test1)
        WebElement element = driver.findElement(By.id("filter"));
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        WebElement body = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = body.findElements(By.tagName("tr"));
        Assert.assertThat(rows.size(), is(5));
    }

    @Test
    //Verify the filter functionality 
    public void test4() throws Exception {
        //No need to WAIT, since we are running test in a fixed order, we know the DOM is ready (because of the wait in test1)
        WebElement element = driver.findElement(By.id("h_year"));
        element.click();
        WebElement body = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = body.findElements(By.tagName("tr"));
        List<WebElement> c1 = rows.get(0).findElements(By.tagName("td"));
        List<WebElement> c2 = rows.get(4).findElements(By.tagName("td"));
        Assert.assertThat(c1.get(0).getText(), is("938"));
        Assert.assertThat(c2.get(0).getText(), is("940"));
    }

    @Test
    //Verify the filter functionality 
    public void test5() throws Exception {
        //No need to WAIT, since we are running test in a fixed order, we know the DOM is ready (because of the wait in test1)
        WebElement body = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = body.findElements(By.tagName("tr"));
        WebElement edit = rows.get(0).findElements(By.tagName("td")).get(7).findElement(By.tagName("a"));
        edit.click();
        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys(Keys.CONTROL + "a");
        description.sendKeys(Keys.DELETE);
        description.sendKeys("Cool car");
        WebElement save = driver.findElement(By.id("save"));
        save.click();
        rows = body.findElements(By.tagName("tr"));
        String description_string = rows.get(1).findElements(By.tagName("td")).get(5).getText();
        Assert.assertThat(description_string, is("Cool car"));
    }

    @Test
    //Verify the filter functionality 
    public void test6() throws Exception {
        //No need to WAIT, since we are running test in a fixed order, we know the DOM is ready (because of the wait in test1)
        WebElement new_button = driver.findElement(By.id("new"));
        new_button.click();
        WebElement save_button = driver.findElement(By.id("save"));
        save_button.click();
        WebElement error = driver.findElement(By.id("submiterr"));
        Assert.assertThat(error.getText(), is("All fields are required"));
        WebElement body = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = body.findElements(By.tagName("tr"));
        Assert.assertThat(rows.size(), is(5));
    }

    @Test
    //Verify the filter functionality 
    public void test7() throws Exception {
        //No need to WAIT, since we are running test in a fixed order, we know the DOM is ready (because of the wait in test1)
        WebElement new_button = driver.findElement(By.id("new"));
        new_button.click();
        WebElement year = driver.findElement(By.id("year"));
        year.sendKeys("2008");
        WebElement registered = driver.findElement(By.id("registered"));
        registered.sendKeys("2002-5-5");
        WebElement make = driver.findElement(By.id("make"));
        make.sendKeys("Kia");
        WebElement model = driver.findElement(By.id("model"));
        model.sendKeys("Rio");
        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys("As new");
        WebElement price = driver.findElement(By.id("price"));
        price.sendKeys("31000");
        WebElement save_button = driver.findElement(By.id("save"));
        save_button.click();
        WebElement body = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = body.findElements(By.tagName("tr"));
        Assert.assertThat(rows.size(), is(6));
    }

}