/*  Name 	   : n11.com Favorilerim,Ürün Favorilere Ekleme ve Kaldýrma Test Otomasyonu
 *  Description: Bu class ile http://www.n11.com ' da uygun email ve parola ileüye giriþi yapýlýr
 *  ve verilen kelimede arama yapýlýr. 2. sayfadaki 3. ürünün favori ekle butonuna basýlýr.
 *  Favori Butonuna basýlan ürünün div id ' sinde ürün idsi bulunur , bu id saklanýr.
 *  Daha sonra favorilerim sayfasýna gitmek için iki defa hover týklama yapýlýr.
 *  ve Favorilerim sayfasý açýlýr. Favorilere eklenen ürünün ID ' sine göre kontrol yapýlýr.
 *  Ürünün Favorilerim'de olduðu onaylanýr. Daha sonra "Sil" linkiyle Favorilerim'den silinir.
 *  Tekrar Favorilerim listesi kontrol edilerek ürünün kaldýrýldýðýna dair onay verilir. 
 * 
 * 
 * 
 * 
 */

package main.java.com.mumy81;

import java.util.concurrent.TimeUnit;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// JUnit ' in testleri alfabeti artan sýraya göre sürmesi yukarýdaki satýrla
// saðlanýr.
public class SiteCheck {

	@Rule
	// Bu satýrla @Test lerin timeout süreleri 15 sn olarak ayarlanýr
	public Timeout timeout = new Timeout(15, TimeUnit.SECONDS);

	private static WebDriver driver;
	// Ürün div'inin idsi , içinde ürün kendine has IDsi bulunuyor
	// Alttaki 2 deðiþken, birden fazla methodda kullanýldýðýndan class içinde
	// tanýmlanmýþtýr.
	private static String favProDivID;
	private static WebElement favProDiv; // Ürün Div WebElement'i
	private static String url, email, password, keyword, page;
	private static int product;

	@Test
	public void test00_setupDriver() throws Exception {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability("version", "latest");
		firefoxOptions.setCapability("platform", Platform.WINDOWS);
		firefoxOptions.setCapability("name", "Testing n11 Favori Butonu ve Favori Kaldýrma ");

		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

		SiteCheck.driver = new FirefoxDriver(firefoxOptions);
		SiteCheck.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		url = "http://www.n11.com";
		email = "muhammedcelik@gmail.com";
		password = "mumy8192";
		keyword = "samsung";
		page = "2";
		product = 3;

	}

	@Test
	// WebDriver'ý kapatýr
	public void test10_quitDriver() throws Exception {
		driver.quit();
	}

	public void close() {
		driver.quit();
	}

	@Test
	// verilen url'ye yani http://www.n11.com ' u browserda getirir
	public void test01_goURL() throws Exception {
		driver.get(url);
		Assert.assertEquals(url + "adresine giriþ baþarýsýz", driver.getTitle(), "n11.com - Alýþveriþin Uðurlu Adresi");

	}

	@Test
	// EMail ve Parola ile giriþ yapar
	public void test02_Login() throws Exception {

		driver.findElement(By.linkText("Giriþ Yap")).click();
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("loginButton")).click();

		Assert.assertNotEquals("Üye giriþinde hata oluþtu", driver.getCurrentUrl(), "https://www.n11.com/giris-yap");

		System.out.println("Üye giriþi yapýldý");

	}

	@Test
	// Verilen kelimede arama yapar. Kelime class'ýn en baþýndan deðiþtirebilir
	public void test03_Search() throws Exception {

		driver.findElement(By.id("searchData")).sendKeys(keyword);
		driver.findElement(By.className("searchBtn")).click();

		Assert.assertEquals("Arama gerçekleþirken hata oluþtu.", "https://www.n11.com/arama?q=" + keyword,
				driver.getCurrentUrl());

		System.out.println(keyword + " aramasý baþarýyla yapýldý.");

	}

	// Ekraný WebElement ' in koordinatlarýna getirir
	// Hover linklere týklarken gerekli oluyor
	public void setCoor(By loc) {
		WebElement elm = driver.findElement(loc);
		Coordinates coordinate = ((Locatable) elm).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();

	}

	// Mouse'u parametlerde verilen ilk WebElement'in üstüne getirir ve ikinci
	// WebElement'e týklar
	public void hoverClick(By loc1, By loc2) {

		try {
			setCoor(loc1);
			Actions builder = new Actions(driver);
			// String previousURL = driver.getCurrentUrl();
			// WebDriverWait wait = new WebDriverWait(driver, 10);

			System.out.println("Hover eylemi gerçekleþirken mouse'u hareket ettirmeyiniz");
			builder.moveToElement(driver.findElement(loc1)).moveToElement(driver.findElement(loc2)).click().perform();
			// WebDriverWait wait = new WebDriverWait(driver, 2);
			// wait.until(ExpectedConditions.elementToBeClickable(loc2));

		} catch (Exception e) {

		}
	}

	@Test
	// Aramadan sonraki ürün listelemede verilen numaralý sayfaya gider
	public void test04_goToPage() throws Exception {
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[3]/a[" + page + "]")).click();
		Assert.assertEquals("Ýstenilen sayfa açýlamadý.",
				driver.findElement(By.name("currentPage")).getAttribute("value"), page);
		System.out.println("Ürün aramada" + page + " . sayfa þuan açýk halde.");

	}

	@Test
	// Arama listelemesinde sýrasý verilen ürünün favori butonuna týklar
	public void test05_clickFavBtn() throws Exception {
		// Favori butonununa ve ürün ID sine xPath yoluyla ulaþýr
		// ve verilen sýaradaki ürünün ID'sini saklar ve favori butonuna týklar.
		String ProXPath = "/html/body/div[1]/div/div/div/div[2]/section[2]/div[2]/ul/li[" + product + "]/div";

		WebElement ProDiv = driver.findElement(By.xpath(ProXPath));
		favProDivID = ProDiv.getAttribute("id");
		System.out.println(favProDivID);

		ProDiv.findElement(By.className("followBtn")).click();

	}

	@Test
	// Favorilerim sayfasýna direk link yok , bu yüzden hover menüden önce
	// Ýstek Listem'e oradan da yine hover linkten Favorilerim'e týklanýr
	public void test06_openMyFavList() throws Exception {

		hoverClick(By.className("myAccountHolder"), By.linkText("Ýstek Listem"));
		Thread.sleep(1000);
		hoverClick(By.className("favorites"), By.linkText("FAVORÝLERE GÝT"));
		Thread.sleep(1000);
	}

	@Test
	// Favori listesinde verilen ürünün olduðunu/olmadýðýný test eder
	public void test07_checkExistFavPro() throws Exception {

		try {
			favProDiv = driver.findElement(By.id(favProDivID));
			Assert.assertTrue("Favori listesinde ilgili ürün bulunamadý", favProDiv.isDisplayed());
			System.out.println("Ürün favorilerim listesinde.");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			Assert.fail("Ürün favorilerim listesinde artýk bulunmuyor");
			// System.out.println("Ürün favorilerim listesinde artýk bulunmuyor");
		}

	}

	@Test
	// Ürün favorilerimden silindikten sonra, ürüün artýk favorilerimde
	// olmadýðýný kontrol eden method.
	public void test09_checkNotExistFavPro() throws Exception {

		try {
			/*
			 * Burada iþlem bekleme süresini 0.1 sn indiriyoruz , böylece ürünün div ' ini
			 * bulamadýðýnda beklemeden iþleme devam ediyor. Daha sonra iþlem bekleme
			 * süresini tekrar 10 sn'ye çýkarýyoruz
			 */
			driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
			favProDiv = driver.findElement(By.id(favProDivID));
			Assert.assertTrue("Favori listesinde ilgili ürün bulunamadý", favProDiv.isDisplayed());
			System.out.println("Ürün hala favorilerim listesinde.");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			System.out.println("Ürün artýk favorilerim listesinde bulunmuyor.");
		}
	}

	@Test
	// Ürünü favorilerim listesinden silmek için "Sil" linkine týklar
	public void test08_removeFavPro() throws Exception {
		favProDiv.findElement(By.className("deleteProFromFavorites")).click();
		driver.navigate().refresh();
	}

	public static void main(String[] args) throws Exception {

		/*
		 * Eclipse IDE kullanýldýðýnda test00_ þeklinde baþlayan methodlar Eclipse
		 * içindeki JUnit ile sýrayla sürülür. Maven ile oluþturulan executable jar ile
		 * testlerin gerçekleþmesi kolay olmasý için main method un içinde sýrayla test
		 * object oluþturup methodlarýný çaðýrdým. Otomasyon testinin ne þekilde
		 * sürüleceðini bilmediðim için bu þekilde de denedim.
		 */

		SiteCheck n11 = new SiteCheck();
		n11.test00_setupDriver();
		n11.test01_goURL();
		n11.test02_Login();
		n11.test03_Search();
		n11.test04_goToPage();
		n11.test05_clickFavBtn();
		n11.test06_openMyFavList();
		n11.test07_checkExistFavPro();
		n11.test08_removeFavPro();
		n11.test09_checkNotExistFavPro();
		n11.test10_quitDriver();

	}

}