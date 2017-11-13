/*  Name 	   : n11.com Favorilerim,Urun Favorilere Ekleme ve Kaldirma Test Otomasyonu
 *  Description: Bu class ile http://www.n11.com ' da uygun email ve parola ile uye girisi yapilir
 *  ve verilen kelimede arama yapilir. 2. sayfadaki 3. urunun favori ekle butonuna basilir.
 *  Favori Butonuna basilan urunun div id ' sinde urun idsi bulunur , bu id saklanir.
 *  Daha sonra favorilerim sayfasina gitmek icin iki defa hover tiklama yapilir.
 *  ve Favorilerim sayfasi acilir. Favorilere eklenen urunun ID ' sine gore kontrol yapilir.
 *  Urunun Favorilerim'de olduðu onaylanir. Daha sonra "Sil" linkiyle Favorilerim'den silinir.
 *  Tekrar Favorilerim listesi kontrol edilerek urunun kaldirildiðina dair onay verilir. 
 * 
 * 
 * 
 */

package main.java.com.mumy81;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;

public class SiteCheck {

	private static WebDriver driver;
	// Urun div'inin id'si bulunuyor , icinde urun kendine has IDsi var
	// Urunun adi da alltaki satir da tanimlanmistir
	private static String favProDivID, favProName;
	private static WebElement favProDiv; // Urun Div WebElement'i
	// Site acilirken ve test uygulanirken kullanilcak bilgiler
	private static String url, email, password, keyword, page;
	private static int product;

	public void setupDriver() {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability("version", "latest");
		firefoxOptions.setCapability("platform", Platform.WINDOWS);
		firefoxOptions.setCapability("name", "Testing n11 Favori Butonu ve Favori Kaldirma ");

		// Eger geckodriver.exe bulunamazsa, "C:\geckodriver\geckodriver.exe" adresine
		// geckodriver.exe ' nin son versiyonu koyup asagidaki satiri aktif hale getirin

		// System.setProperty("webdriver.gecko.driver",
		// "C:\\\\geckodriver\\geckodriver.exe");

		// Eger geckodriver/Marionette driver ' in loglarini gormek isterseniz asagidaki
		// satiri pasif hale getirin
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

		SiteCheck.driver = new FirefoxDriver(firefoxOptions);

		// Asagidaki satir bir islem icin WebDriver'in beklemesi gereken sureyi 10sn
		// olarak ayarlar
		SiteCheck.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// Bu method default veriler ile siteyi ayarlar
	public void setupSite() {

		setURL("http://www.n11.com");
		setUser("muhammedcelik@gmail.com1", "n11deneme");
		setInputs("samsung", "2", 3);
	}

	public void setURL(String url) {
		this.url = url;
	}

	public void setUser(String user, String password) {
		this.email = user;
		this.password = password;
	}

	public void setInputs(String keyword, String page, int product) {
		this.keyword = keyword;
		this.page = page;
		this.product = product;
	}

	// WebDriver'i kapatir
	public void quitDriver() {
		driver.quit();
		System.out.println("WebDriver kapatildi");
	}

	public void close() {
		driver.quit();
	}

	// verilen url'ye yani http://www.n11.com ' u browserda getirir
	public void goURL() {
		driver.get(url);

		try {
			Assert.assertEquals(url + "adresine giris basarisiz", driver.getCurrentUrl(),"https://www.n11.com/");

			System.out.println("Siteye giris yapildi: https://www.n11.com");
		} catch (AssertionError e) {
			System.out.println("Siteye giris yapilamadi");
			System.out.println(e);
			this.close();
		}

	}

	// EMail ve Parola ile giris yapar
	public void Login() {

		driver.findElement(By.className("btnSignIn")).click();
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("loginButton")).click();

		try {
			Assert.assertNotEquals("Uye girisinde hata olustu", driver.getCurrentUrl(),
					"https://www.n11.com/giris-yap");

			System.out.println("Uye girisi yapildi: " + email);
		} catch (AssertionError e) {
			System.out.println("Uye girisinde hata olustu.");
			System.out.println(e);
			this.close();
		}

	}

	// Verilen kelimede arama yapar. Kelime class'in en basindan deðistirebilir
	public void Search() {

		driver.findElement(By.id("searchData")).sendKeys(keyword);
		driver.findElement(By.className("searchBtn")).click();

		try {
			Assert.assertEquals("Arama gerceklesirken hata olustu.", "https://www.n11.com/arama?q=" + keyword,driver.getCurrentUrl());

			System.out.println(keyword + " aramasi basariyla yapildi.");

		} catch (AssertionError e) {
			System.out.println("Aramada yaparken hata olustu: " + keyword);
			System.out.println(e);
			this.close();
		}

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
	public void hoverClick(By loc1, By loc2) throws Exception {

		try {
			// Asagidaki ilk satir sayfada mouse hareket ettirerek gerceklesen eylemler
			// oldugunda mouse kullanici tarafindan kullanailirsa bu eylemlerler
			// hata verdiginden dolayi Firefox ' u ekranda gorunmez yapar
			driver.manage().window().setPosition(new Point(-2000, 0));

			setCoor(loc1);
			Actions builder = new Actions(driver);
			// String previousURL = driver.getCurrentUrl();
			// WebDriverWait wait = new WebDriverWait(driver, 10);

			// System.out.println("Hover eylemi gerceklesirken mouse'u hareket ettirmeyiniz");
			builder.moveToElement(driver.findElement(loc1)).moveToElement(driver.findElement(loc2)).click().perform();

			// Browseri ekranda gorunur yapar
			driver.manage().window().setPosition(new Point(0, 0));

		} catch (Exception e) {
			System.out.println("Hover tiklama yaparken hata olustu.");
			System.out.println(e);
			this.close();
		}
	}

	// Aramadan sonraki urun listelemede verilen numarali sayfaya gider
	public void goToPage() {
		// xPath yolu sayfadaki HTML taglara gore degisebilir, bu yuzden xPath
		// yontemiyle sayfa linkine tiklamayi biraktim
		// driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[3]/a["
		// + page + "]")).click();
		WebElement paginationSection = driver.findElement(By.className("pagination"));
		paginationSection.findElement(By.linkText(page)).click();

		try {
			Assert.assertEquals("Istenilen sayfa acilamadi.",
					driver.findElement(By.name("currentPage")).getAttribute("value"), page);

			System.out.println("Urun aramada " + page + " . sayfa suan acik halde.");

		} catch (AssertionError e) {
			System.out.println("Sayfaya giderken hata olustu.: " + page);
			System.out.println(e);
			this.close();
		}
	}

	// Arama listelemesinde sirasi verilen urunun favori butonuna tiklar
	public void clickFavBtn() {
		// Arama bolumunun <section> ' una ulasip icindeki urunleri liste olarak alir
		// Istenilen urunun Favori butonununa ve urun ID sine ulasir
		// Daha sonra urunun div ID'sini saklar ve favori butonuna tiklar.
		WebElement resultSection = driver.findElement(By.className("resultListGroup"));
		List<WebElement> ProDivs = resultSection.findElements(By.className("columnContent"));

		WebElement ProDiv = ProDivs.get(product - 1);
		favProName = ProDiv.findElement(By.className("plink")).getAttribute("title");
		System.out.println("Favorilere eklenen urun: " + favProName);
		favProDivID = ProDiv.getAttribute("id");

		ProDiv.findElement(By.className("followBtn")).click();

	}

	// Favorilerim sayfasina direk link yok , bu yuzden hover menuden once
	// Istek Listem'e oradan da yine hover linkten Favorilerim'e tiklanir
	public void openMyFavList() throws Exception {

		hoverClick(By.className("myAccountHolder"), By.linkText("Ýstek Listem"));
		hoverClick(By.className("favorites"), By.linkText("FAVORÝLERE GÝT"));
	}

	// Favori listesinde verilen urunun olduðunu/olmadiðini test eder
	public void checkExistFavPro() {

		try {
			favProDiv = driver.findElement(By.id(favProDivID));
			Assert.assertTrue("Favori listesinde ilgili urun bulunamadi", favProDiv.isDisplayed());
			System.out.println("Urun favorilerim listesinde.");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			Assert.fail("Urun favorilerim listesinde bulunuyor");
			this.close();
			}

	}

	// Urun favorilerimden silindikten sonra, uruun artik favorilerimde
	// olmadiðini kontrol eden method.
	public void checkNotExistFavPro() {
			
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		
		try {
			/*
			 * Burada islem bekleme suresini 0.1 sn indiriyoruz , boylece urunun div ' ini
			 * bulamadiðinda beklemeden isleme devam ediyor. Daha sonra islem bekleme
			 * suresini tekrar 10 sn'ye cikariyoruz
			 */
			favProDiv = driver.findElement(By.id(favProDivID));
			Assert.assertTrue("Favori listesinde ilgili urun bulunamadi", favProDiv.isDisplayed());
			System.out.println("Urun hala favorilerim listesinde.");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			
			System.out.println("Urun artik favorilerim listesinde bulunmuyor.");
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
	}

	// Urunu favorilerim listesinden silmek icin "Sil" linkine tiklar
	public void removeFavPro() {
	
		favProDiv.findElement(By.className("deleteProFromFavorites")).click();
		driver.navigate().refresh();
			
	}

}