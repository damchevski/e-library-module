package homework.elibrary.elibrarymodule;


import homework.elibrary.elibrarymodule.service.BookService;
import homework.elibrary.elibrarymodule.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeTest;

import static org.junit.Assert.*;

@SpringBootTest
class TestsForFrontEnd {
    private WebDriver driver;

    private String nameForTest;
    private String nCopiesForTest;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    @BeforeEach
    public void setup(){
        this.driver = getDriver();
        this.nameForTest= "Test Name";
        this.nCopiesForTest = "5";
    }

    @Test
    public void testListOfBooksWithPagination() throws InterruptedException {
        driver.get("http://localhost:3000/");
        Thread.sleep(3000);

        driver.findElement(By.id("books")).click();
        Thread.sleep(1500);

        var sum = 0;

        sum += driver.findElements(By.className("book")).size();
        Thread.sleep(1000);

        driver.findElement(By.linkText("Next")).click();
        Thread.sleep(3000);

        sum += driver.findElements(By.className("book")).size();
        Thread.sleep(1000);

        assertEquals(sum, bookService.getAll().size());
    }

    @Test
    public void testListOfCategories() throws InterruptedException {
        driver.get("http://localhost:3000/");
        Thread.sleep(3000);

        driver.findElement(By.id("categories")).click();
        Thread.sleep(1500);

        assertEquals(driver.findElements(By.className("cat")).size(),
                categoryService.getAll().size());
    }


    @Test
    public void testAddNewBook() throws InterruptedException {
        driver.get("http://localhost:3000/");
        Thread.sleep(3000);

        driver.findElement(By.id("books")).click();
        Thread.sleep(1500);

        driver.findElement(By.id("addBookBtn")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("name")).sendKeys(nameForTest);
        Thread.sleep(1000);

        driver.findElement(By.id("availableCopies")).sendKeys(nCopiesForTest);
        Thread.sleep(1000);

        driver.findElement(By.id("submit")).click();
        Thread.sleep(1000);

        var sum = 0;

        driver.get("http://localhost:3000/");
        Thread.sleep(3000);

        driver.findElement(By.id("books")).click();
        Thread.sleep(1500);

        sum += driver.findElements(By.className("book")).size();
        Thread.sleep(1000);

        driver.findElement(By.linkText("Next")).click();
        Thread.sleep(3000);

        sum += driver.findElements(By.className("book")).size();
        Thread.sleep(1000);

        assertEquals(sum, bookService.getAll().size());
    }

    @Test
    public void testBookDelete() throws InterruptedException {
        driver.get("http://localhost:3000/");
        Thread.sleep(3000);

        driver.findElement(By.id("books")).click();
        Thread.sleep(1500);

        driver.findElement(By.linkText("Next")).click();
        Thread.sleep(1500);

        var allBooksDelete = driver.findElements(By.linkText("Delete"));
        Thread.sleep(1500);

        allBooksDelete.get(allBooksDelete.size()-1).click();

        var sum = 0;

        driver.get("http://localhost:3000/");
        Thread.sleep(3000);

        sum += driver.findElements(By.className("book")).size();
        Thread.sleep(1000);

        driver.findElement(By.linkText("Next")).click();
        Thread.sleep(3000);

        sum += driver.findElements(By.className("book")).size();
        Thread.sleep(1000);

        assertEquals(sum, bookService.getAll().size());
    }

    @Test
    public void testEditBook() throws InterruptedException {
        driver.get("http://localhost:3000/");
        Thread.sleep(3000);

        driver.findElement(By.id("books")).click();
        Thread.sleep(1500);

        driver.findElements(By.linkText("Edit")).get(0).click();
        Thread.sleep(2000);

        driver.findElement(By.id("name")).sendKeys(nameForTest);
        Thread.sleep(1500);

        driver.findElement(By.id("submit")).click();
        Thread.sleep(2000);

        driver.findElement(By.linkText("Next")).click();
        Thread.sleep(1500);

        var allBooksOnLastPage = driver.findElements(By.className("bookName"));

        assertEquals(nameForTest, allBooksOnLastPage.get(allBooksOnLastPage.size()-1).getText());
    }

    @Test
    public void testTakeBook() throws InterruptedException {
        driver.get("http://localhost:3000/");
        Thread.sleep(3000);

        driver.findElement(By.id("books")).click();
        Thread.sleep(1500);

        var oldNCopies = driver.findElements(By.className("bookNCopies"))
                .get(0).getText();
        Thread.sleep(1000);

        driver.findElements(By.linkText("Take Book")).get(0).click();
        Thread.sleep(1500);

        driver.findElement(By.linkText("Next")).click();
        Thread.sleep(1500);

        var allBooksOnLastPage = driver.findElements(By.className("bookNCopies"));

        assertEquals(Integer.parseInt(oldNCopies) - 1,
                Integer.parseInt(allBooksOnLastPage.get(allBooksOnLastPage.size()-1).getText()));

    }

    private WebDriver getDriver(){
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Damchevski\\Desktop\\e-library-module+testing\\e-library-module\\backend\\src\\test\\java\\homework\\elibrary\\elibrarymodule\\resources\\chromedriver.exe");
        return new ChromeDriver();
    }


}
