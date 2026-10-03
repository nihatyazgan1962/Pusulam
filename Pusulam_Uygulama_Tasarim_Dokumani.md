# 🧭 PUSULAM - Ürün Gereksinim ve Tasarım Dokümanı (PRD)

> **Geliştirici:** Nihat Yazgan  
> **Sürüm:** 1.0.0  
> **Tarih:** 2026-10-02  
> **Konsept:** Yaşamı ve Kalbi Hizalayan Minimalist Yaşam & Maneviyat Rehberi  

---

## 📌 1. Proje Vizyonu ve Özeti

**PUSULAM**, geleneksel namaz vakitleri ve kıble bulucu uygulamalarını bir adım öteye taşıyarak; insanın **manevi**, **fiziksel**, **zihinsel** ve **pratik** hayatını 4 ana yönde dengeleyen bütüncül (holistic) bir mobil yaşam asistanıdır.

Uygulamanın temel felsefesi: **"Pusula sadece yönü değil, hayatı hizalar."**

---

## 🧭 2. Dört Boyutlu Pusula Mimarisi

```mermaid
graph TD
    P[🧭 PUSULAM] --> M[1. Manevi Pusula (Kuzey)]
    P --> F[2. Fiziksel Pusula (Doğu)]
    P --> Z[3. Zihinsel Pusula (Güney)]
    P --> G[4. Pratik Pusula (Batı)]

    M --> M1[Dinamik Kıble Pusulası & AR]
    M --> M2[Akıllı Vakitler & Otomatik Sessiz]
    M --> M3[Akıllı Zikirmatik & İstatistik]

    F --> F1[Adım Sayar & Rota/Hicret Modu]
    F --> F2[Kadranlı Su Takip Pusulası]

    Z --> Z1[Günün Pusulası: Ayet/Hadis/Görev]
    Z --> Z2[Dijital Detoks & Odak Zamanlayıcı]

    G --> G1[Çevrimdışı Çalışma Modu]
    G --> G2[Akıllı Seyahat Asistanı & Konum Senkronu]
```

---

## 📱 3. Modüller ve Detaylı Fonksiyonel Gereksinimler

### 3.1. 🕋 Manevi Pusula (İnanç & Huzur)

1. **3D & AR Destekli Dinamik Kıble Pusulası:**
   - Cihazın **Manyetometre**, **İvmeölçer** ve **Jiroskop** sensörlerini birleştirir.
   - Gerçek zamanlı pürüzsüz rota düzeltmesi (low-pass filter).
   - Kamera açıldığında artırılmış gerçeklik (AR) ile Kabe yönünü ekranda işaretleme.
   - Titreşimli geri bildirim (Haptic Feedback): Kıble tam hizalandığında hafif dokunsal bildirim.

2. **Akıllı Vakitler & İbadet Asistanı:**
   - Diyanet ve uluslararası hesaplama yöntemleri (MWL, ISNA, Umm al-Qura vb.) desteği.
   - Vakit girişlerinde özel ezan/bildirim tonları.
   - **Camii / Namaz Modu:** Vakit girdiğinde veya kullanıcı ibadete başladığında telefonu otomatik "Rahatsız Etmeyin / Sessiz" moduna alma.
   - Kalan süre için dairesel geri sayım kadranı.

3. **Akıllı Zikirmatik & Günlük Çetele:**
   - Sesli/sessiz ve titreşimli sayım modu.
   - Esma-ül Hüsna ve hazır tesbihat listeleri.
   - Hedef belirleme (Örn: 33, 99, 1000) ve aşama tamamlama efektleri.
   - Haftalık ve aylık alışkanlık çetelesi (Streak & Isı haritası).

---

### 3.2. 🏃‍♂️ Fiziksel Pusula (Hareket & Sağlık)

1. **Adım Sayar & Keşif/Hicret Rotaları:**
   - Dahili adım sensörü entegrasyonu (Google Fit / Apple HealthKit entegre).
   - Adımları soyut hedeflerle eşleme (Örn: "Mekke - Medine Hicret Rotası", "İpek Yolu", "Gül Bahçesi Keşfi").
   - Günlük kalori ve mesafe tahmini.

2. **Kadranlı Su Pusulası (Hidrasyon):**
   - Pusula bezel'i şeklinde tasarlanmış şık su takip çemberi.
   - Hızlı tek dokunuşla bardak (+200ml, +330ml, +500ml) ekleme.
   - Kişiselleştirilmiş akıllı hatırlatıcılar.

---

### 3.3. 💡 Zihinsel Pusula (Gelişim & Odak)

1. **Günün Pusulası (Günlük İlham):**
   - Her sabah yenilenen: **1 Ayet**, **1 Hadis**, **1 Bilgelik Sözü** ve **1 İyilik/Gelişim Görevi** (Örn: "Bugün bir yakınına hal hatır sor", "Bugün 15 dk kitap oku").
   - Kartları estetik formatta kaydedip sosyal medyada paylaşabilme (Instagram Story formatında dışa aktarma).

2. **Dijital Detoks & Odaklanma Modu (Pomodoro & Tevekkül):**
   - Belirlenen süre boyunca (15, 25, 45 dk) ekranı kilitleyen ve rahatlatıcı ortam sesleri (ney, yağmur, su sesi, rüzgar) çalan odak modu.

---

### 3.4. 🌍 Pratik Pusula (Günlük Yaşam)

1. **Çevrimdışı (Offline) Mod:**
   - İnternet bağlantısı olmasa dahi son konum verisine göre 30 günlük namaz vakitlerini önbellekten hesaplama ve gösterme.
   - Çevrimdışı çalışan pusula kadranı.

2. **Seyahat Asistanı:**
   - Şehir/Ülke değiştiğinde otomatik konum algılama ve vakitleri yeniden senkronize etme.
   - Yakındaki camiler ve helal noktalar listesi (Harita entegrasyonu).

---

## 🎨 4. Tasarım Dili ve Kullanıcı Deneyimi (UI/UX)

* **Tasarım Trendi:** Modern Neumorphism & Glassmorphism karışımı, ultra-minimalist, rafine lüks hissi.
* **Renk Paleti:**
  * **Birincil (Primary):** Derin Zümrüt Yeşili (`#0F3D3E`), Gece Mavisi (`#0B132B`)
  * **Vurgu (Accent):** Sıcak Altın / Kum Sarısı (`#D4AF37`, `#E0A96D`)
  * **Arka Plan (Dark/Light):** Koyu Mat Obsidyen (`#12181B`) & Saf İnci Beyazı (`#F8F9FA`)
* **Tipografi:** Google Fonts *Outfit* / *Plus Jakarta Sans* (Okunabilir, modern, pürüzsüz).
* **Mikro Etkileşimler:** Pusula iğnesinde gerçekçi fizik simülasyonu, su dolumunda akışkan dalgalanma efekti.

---

## 🛠️ 5. Teknik Mimari ve Teknoloji Önerileri

| Katman | Önerilen Teknoloji | Açıklama |
| :--- | :--- | :--- |
| **Framework** | **Flutter (Dart)** veya **React Native** | Tek kod tabanı ile Android + iOS'ta yüksek 60/120 FPS performans |
| **Sensörler** | `sensors_plus`, `geolocator`, `flutter_compass` | Manyetometre, GPS ve İvmeölçer hassas kalibrasyonu |
| **Lokal Veritabanı** | `Hive` veya `Isar Database` | Hızlı ve şifrelenebilir offline veri saklama |
| **Durum Yönetimi** | `Bloc` / `Riverpod` (Flutter) veya `Zustand` (React Native) | Temiz ve ölçeklenebilir mimari |
| **Harita & Konum** | `Mapbox` / `Google Maps SDK` | Kıble çizgisi ve seyahat rotaları için |
| **Analitik & Bildirim**| `Firebase Cloud Messaging`, `Local Notifications` | Namaz vakti ezan bildirimleri ve hatırlatıcılar |

---

## 🚀 6. Geliştirme Yol Haritası (MVP & Fazlar)

- [ ] **Faz 1 (MVP - Çekirdek):**
  - Kıble Pusulası (Sensör kalibrasyonu ile).
  - Namaz Vakitleri & Geri Sayım Kadranı.
  - Zikirmatik Modülü.
  - Koyu & Açık Tema Desteği.
- [ ] **Faz 2 (Fiziksel & Zihinsel Modüller):**
  - Su Takip Çemberi & Bildirimler.
  - Günün İlham Kartları (Ayet/Hadis/Görev).
  - Adım Sayar Entegrasyonu.
- [ ] **Faz 3 (İleri Seviye & Deneyim):**
  - AR (Kamera ile) Kıble Bulucu.
  - Seyahat ve Yakındaki Camiler Haritası.
  - Widget desteği (iOS & Android Ana Ekran / Kilit Ekranı Widget'ları).
