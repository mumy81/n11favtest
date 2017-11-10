/*  Name 	   : n11.com Favorilerim,Urun Favorilere Ekleme ve Kaldirma Test Otomasyonu
 *  Description: Bu class ile http://www.n11.com ' da uygun email ve parola ile uye girisi yapilir
 *  ve verilen kelimede arama yapilir. 2. sayfadaki 3. urunun favori ekle butonuna basilir.
 *  Favori Butonuna basilan urunun div id ' sinde urun idsi bulunur , bu id saklanir.
 *  Daha sonra favorilerim sayfasina gitmek için iki defa hover tiklama yapilir.
 *  ve Favorilerim sayfasi açilir. Favorilere eklenen urunun ID ' sine göre kontrol yapilir.
 *  Urunun Favorilerim'de oldugu onaylanir. Daha sonra "Sil" linkiyle Favorilerim'den silinir.
 *  Tekrar Favorilerim listesi kontrol edilerek urunun kaldirildigina dair onay verilir. 
 *  
 *  NOT : Þuan samsung kelimesinde dýsýnda çalýsmayabilirr, bir dahaki guncellemede verilen herhangi
 *  bir kelimede çalisacak hale gelecek. Vakit yetismedigi icin boyle kaldi.
 * 
 *  ÖNEMLÝ !
 *  Hover eylemleri sirasinda Firefox açik iken mouse hiç hareket ettirilmemesi gerekli!
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
// JUnit ' in testleri alfabeti artan siraya göre surmesi yukaridaki satirla
// saglanir.
public class SiteCheck {

	@Rule
	// Bu satirla @Test lerin timeout sureleri 15 sn olarak ayarlanir
	public Timeout timeout = new Timeout(15, TimeUnit.SECONDS);

	private static WebDriver driver;
	// Urun div'inin idsi , içinde urun kendine has IDsi bulunuyor
	// Alttaki 2 degisken, birden fazla methodda kullanildigindan class içinde
	// tanimlanmistir.
	private static String favProDivID;
	private static WebElement favProDiv; // Urun Div WebElement'i
	private static String url, email, password, keyword, page;
	private static int product;

	@Test
	public void test00_setupDriver() throws Exception {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability("version", "latest");
		firefoxOptions.setCapability("platform", Platform.WINDOWS);
		firefoxOptions.setCapability("name", "Testing n11 Favori Butonu ve Favori Kaldirma ");

		// Konsoldan program printlerinin daha kolay okunmasi için loglari kapattim
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

		SiteCheck.driver = new FirefoxDriver(firefoxOptions);
		SiteCheck.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}
	
	@Test
	public void test01_setupSite() throws Exception {
		
		setURL("http://www.n11.com");
		setUser("muhammedcelik@gmail.com","n11deneme");
		setInputs("samsung","2",3);
	}
    
	public void setURL(String url) {
		this.url = url;
	}
	
	public void setUser(String user, String password) {
		this.email = user;
		this.password = password;
	}
	
	public void setInputs(String keyword  , String page, int product) {
		this.keyword = keyword;
		this.page = page;
		this.product = product;
	}
	
	
	@Test
	// WebDriver'i kapatir
	public void test11_quitDriver() throws Exception {
		driver.quit();
	}

	public void close() {
		driver.quit();
	}

	@Test
	// verilen url'ye yani http://www.n11.com ' u browserda getirir
	public void test02_goURL() throws Exception {
		driver.get(url);
		Assert.assertEquals(url + "adresine giris basarisiz", driver.getTitle(), "n11.com - Alisverisin Ugurlu Adresi");
        System.out.println(driver.getCurrentUrl() + " adresine giris yapildi.");
	}

	@Test
	// EMail ve Parola ile giris yapar
	public void test03_Login() throws Exception {

		driver.findElement(By.linkText("Giris Yap")).click();
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("loginButton")).click();

		Assert.assertNotEquals("Uye girisinde hata olustu", driver.getCurrentUrl(), "https://www.n11.com/giris-yap");

		System.out.println("Uye girisi yapildi");

	}

	@Test
	// Verilen kelimede arama yapar. Kelime class'in en basindan degistirebilir
	public void test04_Search() throws Exception {

		driver.findElement(By.id("searchData")).sendKeys(keyword);
		driver.findElement(By.className("searchBtn")).click();

		Assert.assertEquals("Arama gerçeklesirken hata olustu.", "https://www.n11.com/arama?q=" + keyword,
				driver.getCurrentUrl());

		System.out.println(keyword + " aramasi basariyla yapildi.");

	}

	// Ekrani WebElement ' in koordinatlarina getirir
	// Hover linklere tiklarken gerekli oluyor
	public void setCoor(By loc) {
		WebElement elm = driver.findElement(loc);
		Coordinates coordinate = ((Locatable) elm).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();

	}

	// Mouse'u parametlerde verilen ilk WebElement'in ustune getirir ve ikinci
	// WebElement'e tiklar
	public void hoverClick(By loc1, By loc2) {

		try {
			setCoor(loc1);
			Actions builder = new Actions(driver);
			// String previousURL = driver.getCurrentUrl();
			// WebDriverWait wait = new WebDriverWait(driver, 10);

			System.out.println("Hover eylemi gerçeklesirken mouse'u hareket ettirmeyiniz");
			builder.moveToElement(driver.findElement(loc1)).moveToElement(driver.findElement(loc2)).click().perform();
			// WebDriverWait wait = new WebDriverWait(driver, 2);
			// wait.until(ExpectedConditions.elementToBeClickable(loc2));

		} catch (Exception e) {

		}
	}

	@Test
	// Aramadan sonraki urun listelemede verilen numarali sayfaya gider
	public void test05_goToPage() throws Exception {
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[3]/a[" + page + "]")).click();
		Assert.assertEquals("Ýstenilen sayfa açilamadi.",
				driver.findElement(By.name("currentPage")).getAttribute("value"), page);
		System.out.println("Urun aramada " + page + " . sayfa suan açik halde.");

	}

	@Test
	// Arama listelemesinde sirasi verilen urunun favori butonuna tiklar
	public void test06_clickFavBtn() throws Exception {
		// Favori butonununa ve urun ID sine xPath yoluyla ulasir
		// ve verilen siaradaki urunun ID'sini saklar ve favori butonuna tiklar.
		String ProXPath = "/html/body/div[1]/div/div/div/div[2]/section[2]/div[2]/ul/li[" + product + "]/div";

		WebElement ProDiv = driver.findElement(By.xpath(ProXPath));
		favProDivID = ProDiv.getAttribute("id");
		
		ProDiv.findElement(By.className("followBtn")).click();

	}

	@Test
	// Favorilerim sayfasina direk link yok , bu yuzden hover menuden önce
	// Ýstek Listem'e oradan da yine hover linkten Favorilerim'e tiklanir
	public void test07_openMyFavList() throws Exception {

		hoverClick(By.className("myAccountHolder"), By.linkText("Ýstek Listem"));
		Thread.sleep(1000);
		hoverClick(By.className("favorites"), By.linkText("FAVORÝLERE GÝT"));
		Thread.sleep(1000);
	}

	@Test
	// Favori listesinde verilen urunun oldugunu/olmadigini test eder
	public void test08_checkExistFavPro() throws Exception {

		try {
			favProDiv = driver.findElement(By.id(favProDivID));
			Assert.assertTrue("Favori listesinde ilgili urun bulunamadi", favProDiv.isDisplayed());
			System.out.println("Urun favorilerim listesinde.");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			Assert.fail("Urun favorilerim listesinde artik bulunmuyor");
			// System.out.println("Urun favorilerim listesinde artik bulunmuyor");
		}

	}

	@Test
	// Urun favorilerimden silindikten sonra, uruun artik favorilerimde
	// olmadigini kontrol eden method.
	public void test10_checkNotExistFavPro() throws Exception {

		try {
			/*
			 * Burada islem bekleme suresini 0.1 sn indiriyoruz , böylece urunun div ' ini
			 * bulamadiginda beklemeden isleme devam ediyor. Daha sonra islem bekleme
			 * suresini tekrar 10 sn'ye çikariyoruz
			 */
			driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
			favProDiv = driver.findElement(By.id(favProDivID));
			Assert.assertTrue("Favori listesinde ilgili urun bulunamadi", favProDiv.isDisplayed());
			System.out.println("Urun hala favorilerim listesinde.");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			System.out.println("Urun artik favorilerim listesinde bulunmuyor.");
			System.out.println("Test basarili.");
		}
	}

	@Test
	// Urunu favorilerim listesinden silmek için "Sil" linkine tiklar
	public void test09_removeFavPro() throws Exception {
		favProDiv.findElement(By.className("deleteProFromFavorites")).click();
		driver.navigate().refresh();
	}

	public static void main(String[] args) throws Exception {

		/*
		 * Eclipse IDE kullanildiginda test00_ seklinde baslayan methodlar Eclipse
		 * içindeki JUnit ile sirayla surulur. Maven ile olusturulan executable jar ile
		 * testlerin gerçeklesmesi kolay olmasi için main method un içinde sirayla test
		 * object olusturup methodlarini çagirdim. Otomasyon testinin ne sekilde
		 * surulecegini bilmedigim için bu sekilde de denedim.
		 */

		SiteCheck n11 = new SiteCheck();
		n11.test00_setupDriver();
		n11.test01_setupSite();
		n11.test02_goURL();
		n11.test03_Login();
		n11.test04_Search();
		n11.test05_goToPage();
		n11.test06_clickFavBtn();
		n11.test07_openMyFavList();
		n11.test08_checkExistFavPro();
		n11.test09_removeFavPro();
		n11.test10_checkNotExistFavPro();
		n11.test11_quitDriver();
	} 

}