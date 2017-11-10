# automationtest project
n11.com Ürün Araması'ndan ulaşılan Ürünlerin Favorilerim'e eklenip kaldırıldığını test eden otomasyon

/*  Name 	   : n11.com Favorilerim,Ürün Favorilere Ekleme ve Kaldırma Test Otomasyonu

 *  Description: Bu class ile http://www.n11.com ' da uygun email ve parola ileüye girişi yapılır
 *  ve verilen kelimede arama yapılır. 2. sayfadaki 3. ürünün favori ekle butonuna basılır.
 *  Favori Butonuna basılan ürünün div id ' sinde ürün idsi bulunur , bu id saklanır.
 *  Daha sonra favorilerim sayfasına gitmek için iki defa hover tıklama yapılır.
 *  ve Favorilerim sayfası açılır. Favorilere eklenen ürünün ID ' sine göre kontrol yapılır.
 *  Ürünün Favorilerim'de olduğu onaylanır. Daha sonra "Sil" linkiyle Favorilerim'den silinir.
 *  Tekrar Favorilerim listesi kontrol edilerek ürünün kaldırıldığına dair onay verilir. 
 * 
 *  NOT: Şuan sadece samsung kelimeside bu testi gerçekleştiriyor. Bu kelimede üstte promosyon ürünleri gösterildiği için 
 *  XPath ' leri ona göre ayarlamıştım. Diğer aramalarda da bu testi gerçekleştirmesi için <section>  ve <li> tagları ile 
 *  findElementBy methodu kullarak yeni bir yöntem geliştiriyorum. Vakit yetişmedi o yüzden bu versiyonda ekleyemedim.
 *
 *  ÖNEMLİ !
 *  Hover eylelmleri sırasında Firefox açık iken mouse hiç hareket ettirilmemesi gerekli!
 * 
 * 
 */
* KURULUM : Dosyaları ya Eclipse IDE ' ye Import ederek veya Maven ile install ederek çalıştırabilirsiniz.
* Bir klasöre pom.xml , src klaösrü ve geckodriver.exe yi yerleştirin.
* maven kurulu olması şartıyla,  "mvn -install" komutuyla pom.xml ' nin bulunduğu klaösrü içinde target klasörü altında           *"-jar-with-dependencies" etiketiyle çalıştırabilir bir .jar oluşturulur.  
* "java -jar target/n11favtest-1-jar-with-dependencies.jar"  komutuyla çalıştırılır.
