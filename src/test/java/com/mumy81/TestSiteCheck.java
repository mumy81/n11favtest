/*  Name 	   : n11.com Favorilerim,Ürün Favorilere Ekleme ve Kaldýrma Test Otomasyonu
 *  Description: Bu class ile http://www.n11.com ' da uygun email ve parola ileüye giriþi yapýlýr
 *  ve verilen kelimede arama yapýlýr. 2. sayfadaki 3. ürünün favori ekle butonuna basýlýr.
 *  Favori Butonuna basýlan ürünün div id ' sinde ürün idsi bulunur , bu id saklanýr.
 *  Daha sonra favorilerim sayfasýna gitmek için iki defa hover týklama yapýlýr.
 *  ve Favorilerim sayfasý açýlýr. Favorilere eklenen ürünün ID ' sine göre kontrol yapýlýr.
 *  Ürünün Favorilerim'de olduðu onaylanýr. Daha sonra "Sil" linkiyle Favorilerim'den silinir.
 *  Tekrar Favorilerim listesi kontrol edilerek ürünün kaldýrýldýðýna dair onay verilir. 
 * 
 *  ÖNEMLÝ !
 *  Hover eylelmleri sýrasýnda Firefox açýk iken mouse hiç hareket ettirilmemesi gerekli!
 * 
 * 
 */

package test.java.com.mumy81;

import main.java.com.mumy81.SiteCheck;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// JUnit ' in testleri alfabeti artan sýraya göre sürmesi yukarýdaki satýrla
// saðlanýr.
public class TestSiteCheck {

	@Rule
	// Bu satýrla @Test lerin timeout süreleri 15 sn olarak ayarlanýr
	public Timeout timeout = new Timeout(15, TimeUnit.SECONDS);
	
	
	private static SiteCheck testApp;	
	
	@BeforeClass 
	public static void testBegin() {
	   
	   try {
		   testApp = new SiteCheck();
		} catch(Exception e) {
			fail("Test edilecek object baþlatýlýrken hata oluþtu.");
			System.out.println(e);
		}
		
	}
	
	@AfterClass 
	public static void testClose() {
		System.out.println("Test tamamlandý.");
	}

	@Test
	public void test00_setupDriver() throws Exception {
				
		try {
			testApp.setupDriver();
		} catch(Exception e) {
			fail("WebDriver baþlatýlýrken hata oluþtu.");
			System.out.println(e);
		}
		
	}

	@Test
	public void test01_setupSite() throws Exception {
		
		
		try {
			testApp.setupSite();
		} catch(Exception e) {
			fail("Site ayarlanýrken hata oluþtu.");
			System.out.println(e);
		}
		
		}

	@Test
	// WebDriver'ý kapatýr
	public void test11_quitDriver() throws Exception {
		
		try {
			testApp.quitDriver();
		} catch(Exception e) {
			fail("WebDriver kapatýlýrken hata oluþtu.");
			System.out.println(e);
		}
		
	}

	
	@Test
	// verilen url'ye yani http://www.n11.com ' u browserda getirir
	public void test02_goURL() throws Exception {
		
		try {
			testApp.goURL();
		} catch(Exception e) {
			fail("Siteye girerken hata oluþtu.");
			System.out.println(e);
		}
		}

	@Test
	// EMail ve Parola ile giriþ yapar
	public void test03_Login() throws Exception {
		
		try {
			testApp.Login();
		} catch(Exception e) {
			fail("Login olurken hata oluþtu.");
			System.out.println(e);
		}
		
	}

	@Test
	// Verilen kelimede arama yapar. Kelime class'ýn en baþýndan deðiþtirebilir
	public void test04_Search() throws Exception {
		
		try {
			testApp.Search();
		} catch(Exception e) {
			fail("Arama yaparken hata oluþtu.");
			System.out.println(e);
		}
		
				}

	
		@Test
	// Aramadan sonraki ürün listelemede verilen numaralý sayfaya gider
	public void test05_goToPage() throws Exception {
		try {
			testApp.goToPage(); 
		} catch(Exception e) {
			fail("Sayfaya giderken hata olustu.");
			System.out.println(e);
		}
	}

	@Test
	// Arama listelemesinde sýrasý verilen ürünün favori butonuna týklar
	public void test06_clickFavBtn() throws Exception {
		try { 
			testApp.clickFavBtn(); 
			} catch(Exception e) {
				fail("Fav butonuna týklarken hata oluþtu");
			System.out.println(e);
		}


	}

	@Test
	// Favorilerim sayfasýna direk link yok , bu yüzden hover menüden önce
	// Ýstek Listem'e oradan da yine hover linkten Favorilerim'e týklanýr
	public void test07_openMyFavList() throws Exception {
		try { 
			testApp.openMyFavList();
			} catch(Exception e) {
				fail("Favorilerimi açarken hata oluþtu");
			System.out.println(e);
		}
		
		}

	@Test
	// Favori listesinde verilen ürünün olduðunu/olmadýðýný test eder
	public void test08_checkExistFavPro() throws Exception {
			try { 
				testApp.checkExistFavPro();
			} catch(Exception e) {
				fail("Ürünün Favorilerimde olduðu kontrol edilirken hata oluþtu.");
			System.out.println(e);
		}
	}

	@Test
	// Ürün favorilerimden silindikten sonra, ürüün artýk favorilerimde
	// olmadýðýný kontrol eden method.
	public void test10_checkNotExistFavPro() throws Exception {
			
		try {
			testApp.checkNotExistFavPro();
		} catch(Exception e) {
			fail("Ürünün Favorilerimde olmadýðý kontrol edilirken hata oluþtu.");
			System.out.println(e);
		}
		
	}

	@Test
	// Ürünü favorilerim listesinden silmek için "Sil" linkine týklar
	public void test09_removeFavPro() throws Exception {
		try {
			testApp.removeFavPro();
		} catch(Exception e) {
			fail("Ürün Favorilerim'den silinirken hata oluþtu.");
			System.out.println(e);
		}
		
		}

}