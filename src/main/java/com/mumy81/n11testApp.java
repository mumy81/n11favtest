package main.java.com.mumy81;

public class n11testApp {

	public static void main(String[] args) throws Exception {

		/*
		 * Eclipse IDE kullanýldýðýnda test00_ þeklinde baþlayan methodlar Eclipse
		 * içindeki JUnit ile sýrayla sürülür. Maven ile oluþturulan executable jar ile
		 * testlerin sürülmesi kolay olmasý için main methodun içinde test
		 * object oluþturup, methodlarýný çaðýrýyoruz. .
		 */

		SiteCheck n11 = new SiteCheck();
		n11.setupDriver();
		// n11.test01_setupSite();
		n11.setURL("http://www.n11.com");
		n11.setUser("muhammedcelik@gmail.com","n11deneme");
		n11.setInputs("samsung", "2", 3); // parametreler : kelime, sayfa, ürün sýrasý
		n11.goURL();
		n11.Login();
		n11.Search();
		n11.goToPage();
		n11.clickFavBtn();
		n11.openMyFavList();
		n11.checkExistFavPro();
		n11.removeFavPro();
		n11.checkNotExistFavPro();
		n11.quitDriver();
								
	}

}
