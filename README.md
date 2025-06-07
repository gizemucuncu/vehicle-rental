# 🚗 Vehicle Rental System

Java ile geliştirilen bu konsol tabanlı uygulama, kullanıcıların araç kiralayabildiği bir araç kiralama sistemidir. Proje kapsamında admin kullanıcılar tarafından araç ve kategori yönetimi yapılabilirken, müşteri kullanıcılar ise araçları kiralayabilir ve kiralama geçmişlerini görüntüleyebilir.

---

## ✨ Özellikler

- 👤 Kullanıcı yönetimi (Admin & Customer)
- 🏷️ Kategori oluşturma, listeleme, silme
- 🚘 Araç ekleme, silme, arama ve kategoriye göre filtreleme
- 📆 Saatlik, günlük, haftalık, aylık kiralama seçenekleri
- 🧾 Kurumsal ve bireysel müşteriler için farklı kurallar
- 💰 Araç fiyatına ve kullanıcı yaşına göre depozito işlemleri
- 📋 Kiralama geçmişi görüntüleme (admin & müşteri)

---

## 🧠 Kurallar ve Koşullar

- 🚫 Kurumsal müşteriler sadece **aylık** kiralama yapabilir.
- 🧓 Araç fiyatı **2.000.000₺** üzerindeyse, kullanıcı en az **30 yaşında** olmalı ve **%10 depozito** ödemelidir.
- 💡 Kiralama süresi ve fiyatlar kategori bazında belirlenir.
- 🎯 Saatlik, günlük, haftalık ve aylık fiyatlandırma yapılabilir.

---

## 🛠️ Kullanılan Teknolojiler

| Teknoloji      | Açıklama                        |
|----------------|---------------------------------|
| Java           | Programlama dili                |
| JDBC           | Veritabanı bağlantısı           |
| PostgreSQL     | Veritabanı                      |
| IntelliJ IDEA  | Geliştirme ortamı               |
| Maven          | Bağımlılık ve proje yönetimi    |

---

## 📂 Proje Yapısı

├── model/ # Entity sınıfları

├── dao/ # Veritabanı işlemleri

├── service/ # İş mantığı katmanı

├── enums/ # Enum türleri

├── exception/ # Özel istisna sınıfları

├── utils/ # Yardımcı sınıflar

├── VehicleRentalMain.java # Ana uygulama giriş noktası


## 🚀 Kurulum ve Çalıştırma

### 1. Repo'yu klonla

[git clone https://github.com/kullanici/vehicle-rental-system.git](https://github.com/gizemucuncu/vehicle-rental.git)

### 2. Veritabanını oluştur
PostgreSQL üzerinde vehicle_rental isimli veritabanını oluştur.
category, vehicle, user, customer, rental tablolarını oluştur.
Gerekirse örnek veriler ekleyebilirsin.

### 3. IntelliJ üzerinden çalıştır
VehicleRentalMain dosyasını çalıştırarak konsol uygulamasını başlat.

🔐 Giriş Türleri

Admin Girişi: Araç ve kategori yönetimi yapabilir
Customer Girişi: Araç kiralama işlemleri yapabilir
