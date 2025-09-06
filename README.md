# Furkan BASTURK Test Case - 06.09.2025
## Giriş

Bu proje, belirlenen senaryo için **Java**, **Selenium** ve **TestNG** kullanılarak geliştirilmiş bir web otomasyonu çalışmasıdır. Proje, **Page Object Model (POM)** tasarım desenine uygun, temiz ve sürdürülebilir bir yapıya sahiptir.

---
### 1. Proje Detayları

* **Ayarlar ve Konfigürasyon:** Proje ve test ortamı için gerekli tüm parametreler `src/main/resources/config.properties` dosyasında yönetilir. Tarayıcı seçenekleri, test sırasında kullanılan URL'ler ve diğer yapılandırma bilgileri bu dosya üzerinden kolayca güncellenebilir.
* **Test Çalıştırma:** Otomasyon testlerini başlatmak için `TestngFiles/WebAutomationStart.xml` dosyasını çalıştırmanız yeterlidir. Bu dosya, testlerin hangi tarayıcıda (`chrome`, `firefox` vb.) çalışacağını parametre olarak belirlemenize olanak tanır.
* **Örnek Çalıştırma:** Proje, Chrome tarayıcısı üzerinde başarıyla test edilmiştir. `WebTestSet` sınıfındaki tüm test senaryoları, istenen iş akışını doğru bir şekilde tamamlamıştır.

---

### 2. Proje Klasör Yapısı

```
   ├── src/
   │   ├── main/
   │   │   ├── java/
   │   │   │   └── insider/
   │   │   │       └── web/
   │   │   │           ├── base/      # Test ve sayfa nesneleri için temel sınıflar (BaseTest, BasePage)
   │   │   │           ├── driver/    # WebDriver yönetimi ve tarayıcı ayarlarını içerir (BrowserType, WebDriverFactory)
   │   │   │           ├── pages/     # Web sayfalarını temsil eden Page Object sınıfları (HomePage, CareersPage vb.)
   │   │   │           └── utils/     # Konfigürasyon okuma gibi yardımcı sınıflar (ConfigReader)
   │   │   └── resources/
   │   │       ├── config.properties  # Proje yapılandırma ayarları
   │   │       └── log4j2.xml         # Loglama ayarları
   │   └── test/
   │       └── java/
   │           └── insider/
   │               └── web/           # Test senaryolarını içeren sınıflar (WebTestSet)
   ├── TestngFiles 
   │   └── WebAutomationStart.xml     # Testlerin başlatıldığı TestNG XML dosyası
   ├── pom.xml                        # Maven proje bağımlılıkları ve ayarları
   └── README.md
```
