# Test Automation Case Study Project

Case Study ' de istenilenler :

1. http://www.n11.com <http://www.n11.com/> sitesine gelecek ve anasayfanin acildigini onaylayacak
2. Login ekranini acip, bir kullanici ile login olacak ( daha once siteye uyeligin varsa o olabilir )
3. Ekranin ustundeki Search alanina 'samsung' yazip Ara butonuna tiklayacak
4. Gelen sayfada samsung icin sonuc bulundugunu onaylayacak
5. Arama sonuclarindan 2. sayfaya tiklayacak ve acilan sayfada 2. sayfanin su an gosterimde oldugunu onaylayacak
6. Ustten 3. urunun icindeki 'favorilere ekle' butonuna tiklayacak
7. Ekranin en ustundeki 'favorilerim' linkine tiklayacak 
8. Acilan sayfada bir onceki sayfada izlemeye alinmis urunun bulundugunu onaylayacak
9. Favorilere alinan bu urunun yanindaki 'Kaldir' butonuna basarak, favorilerimden cikaracak
10. Sayfada bu urunun artik favorilere alinmadigini onaylayacak.

Bu task için Java, C# veya istenilen herhangi bir programlama dili kullanılabilir. Junit veya Nunit gibi unit testing frameworklerinin ve Maven gibi bir build aracının kullanılması beklenmektedir. Test otomasyon kod tasarımın daha kolay anlaşılır ve bakımının kolay yapılabilir olması için Page-Object Design Pattern kullanılması özellikle tavsiye edilmektedir.

Projelerin bir versiyon kontrol sistemi; GitHub, Bitbucket, GitLab üzerinden paylaşılması beklenilmekte

 # CASE STUDY 

n11.com Ürün Araması'ndan ulaşılan ürünlerin Favorilerim'e eklenip kaldırıldığını test eden test otomasyonu

 Name 	   : n11.com Favorilerim,Ürün Favorilere Ekleme ve Kaldırma Test Otomasyonu
 
 Description: Bu otomasyon testi ile http://www.n11.com ' da uygun email ve parola ile üye girişi yapılır
 *  Verilen kelimede arama yapılır. 2. sayfadaki 4. ürünün favori ekle butonuna basılır.
 *  Favori Butonuna basılan ürünün div id ' sinde ürün idsi bulunur , bu id saklanır.
 *  Case Study ödevinde 7.madde belirtilen en üstteki favorilerim linki bulunmamakta, Favorilerim ' e girmek için
 *  Hesabım'ın altında bulunan İstek Listem'e oradan da Favorilerim listesinin içindeki Favorilerim'e Git linkine tıklayarak
 *  Favorilerim'e gidilmesi gerekmekte. Favorilerim ' i acarken iki defa hover efektini acarak linklere ulasmak gerekiyor.
 *  Bundan dolayı favorilerim sayfasına gitmek için iki defa hover tıklama yapılır. Bu tıklamalar yapılırken browser gorunmez
 *  yapilmasi gerekli, cunku mouse kullanici tarafindan kullanilirsa hover eylemi gerceklesirken hata vermekte. Bu hatayi  
 *  ortadan kaldirmak icin bu tiklamalar yapilirken kisa sureligine browser gorunmez yapilir.
 *  Ve Favorilerim sayfası açılır. Favorilere eklenen ürünün divID ' sine göre kontrol yapılır.
 *  Ürünün Favorilerim'de olduğu onaylanır. Daha sonra "Sil" linkiyle Favorilerim'den silinir.
 *  Tekrar Favorilerim listesi kontrol edilerek ürünün kaldırıldığına dair onay verilir. WebDriver kapatılır.
 * 
 *  Favorilerim sayfasi acilirken, hoverClick methodu kullanilir, bu methodun icinde browserin gorunmez yapilmasi icin
 *  gerekli webdriver.manage ayarlari degistirelerek hover tiklamalar yapilirken browser gorunmez hale getirebilir.
 *  Fakat Hover tiklamalari sırasında Firefox açık iken mouse hiç hareket ettirilmemesi gerekli!
 
# DOSYALAR

* n11testApp.java  --> Uygulamanın çalışması icin main() method bulunduran dosya
* SiteCheck.java ---> İşlemleri yapan class ve methodları barındıran dosya
* TestSiteCheck.java --> SiteCheck classının JUnit @Testlerini içinde bulunduran dosya
      
# KURULUM : Dosyalar Eclipse IDE ' ye Import edilerek veya Maven ile install ederek calisabilir.
* MAVEN INSTALL : Bir klasöre 1. pom.xml , 2. "src" klaösrü ve 3. "geckodriver.exe" yi yerleştirin.
* maven kurulu olması şartıyla,  "mvn -install" komutuyla pom.xml ' nin bulunduğu  klasörü içinde target klasörünün altında   
* "n11favtest-1-jar-with-dependencies.jar" isimli çalıştırabilir bir .jar oluşturulur.  
 
 # "java -jar target/n11favtest-1-jar-with-dependencies.jar"  komutuyla çalıştırılır.
  
