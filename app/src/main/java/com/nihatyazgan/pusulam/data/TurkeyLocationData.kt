package com.nihatyazgan.pusulam.data

data class MosqueItem(
    val name: String,
    val distance: String,
    val address: String,
    val isOpen: Boolean = true
)

data class CityData(
    val name: String,
    val qiblaAngle: Float,
    val districts: List<String>,
    var imsak: String,
    var ogle: String,
    var ikindi: String,
    var aksam: String,
    var yatsi: String
)

object TurkeyLocationData {

    // 81 İlin Tamamı ve Tüm İlçeleri
    val cities = listOf(
        CityData("Adana", 163f, listOf("Seyhan", "Yüreğir", "Çukurova", "Sarıçam", "Ceyhan", "Kozan", "İmamoğlu", "Karataş", "Karaisalı", "Pozantı", "Yumurtalık", "Tufanbeyli", "Feke", "Aladağ", "Saimbeyli"), "05:04", "12:44", "16:08", "18:42", "20:01"),
        CityData("Adıyaman", 168f, listOf("Merkez", "Kahta", "Besni", "Gölbaşı", "Gerger", "Sincik", "Çelikhan", "Tut", "Samsat"), "04:54", "12:34", "15:58", "18:32", "19:51"),
        CityData("Afyonkarahisar", 148f, listOf("Merkez", "Sandıklı", "Dinar", "Bolvadin", "Sinanpaşa", "Emirdağ", "Şuhut", "Çay", "İhsaniye", "İscehisar", "Dazkırı", "Başmakçı", "Çobanlar", "Bayat", "Evciler", "Hocalar", "Kızılören"), "05:22", "13:02", "16:26", "19:00", "20:19"),
        CityData("Ağrı", 182f, listOf("Merkez", "Patnos", "Doğubayazıt", "Diyadin", "Eleşkirt", "Tutak", "Taşlıçay", "Hamur"), "04:36", "12:16", "15:40", "18:14", "19:33"),
        CityData("Amasya", 154f, listOf("Merkez", "Merzifon", "Suluova", "Taşova", "Gümüşhacıköy", "Göynücek", "Hamamözü"), "05:07", "12:47", "16:11", "18:45", "20:04"),
        CityData("Ankara", 151f, listOf("Çankaya", "Keçiören", "Yenimahalle", "Mamak", "Etimesgut", "Sincan", "Altındağ", "Pursaklar", "Gölbaşı", "Polatlı", "Çubuk", "Kahramankazan", "Beypazarı", "Elmadağ", "Nallıhan", "Akyurt", "Kızılcahamam", "Haymana", "Bala", "Güdül", "Ayaş", "Kalecik", "Çamlıdere", "Evren"), "05:15", "12:55", "16:19", "18:53", "20:12"),
        CityData("Antalya", 152f, listOf("Muratpaşa", "Kepez", "Alanya", "Manavgat", "Konyaaltı", "Serik", "Aksu", "Kumluca", "Döşemealtı", "Kaş", "Korkuteli", "Gazipaşa", "Finike", "Kemer", "Elmalı", "Demre", "Akseki", "Gündoğmuş", "İbradı"), "05:21", "13:01", "16:25", "18:59", "20:18"),
        CityData("Artvin", 175f, listOf("Merkez", "Hopa", "Borçka", "Yusufeli", "Arhavi", "Şavşat", "Ardanuç", "Murgul", "Kemalpaşa"), "04:44", "12:24", "15:48", "18:22", "19:41"),
        CityData("Aydın", 141f, listOf("Efeler", "Nazilli", "Söke", "Kuşadası", "Didim", "İncirliova", "Germencik", "Çine", "Acarlar", "Köşk", "Kuyucak", "Sultanhisar", "Karacasu", "Bozdoğan", "Yenipazar", "Buharkent", "Karpuzlu"), "05:31", "13:11", "16:35", "19:09", "20:28"),
        CityData("Balıkesir", 142f, listOf("Karesi", "Altıeylül", "Bandırma", "Edremit", "Gönen", "Ayvalık", "Burhaniye", "Bigadiç", "Susurluk", "Dursunbey", "Sındırgı", "İvrindi", "Erdek", "Havran", "Kepsut", "Manyas", "Savaştepe", "Balya", "Gömeç", "Marmara"), "05:27", "13:07", "16:31", "19:05", "20:24"),
        CityData("Bilecik", 146f, listOf("Merkez", "Bozüyük", "Osmaneli", "Söğüt", "Gölpazarı", "Pazaryeri", "İnhisar", "Yenipazar"), "05:21", "13:01", "16:25", "18:59", "20:18"),
        CityData("Bingöl", 176f, listOf("Merkez", "Genç", "Solhan", "Karlıova", "Adaklı", "Kiğı", "Yedisu", "Yayladere"), "04:47", "12:27", "15:51", "18:25", "19:44"),
        CityData("Bitlis", 181f, listOf("Tatvan", "Merkez", "Güroymak", "Ahlat", "Hizan", "Mutki", "Adilcevaz"), "04:40", "12:20", "15:44", "18:18", "19:37"),
        CityData("Bolu", 149f, listOf("Merkez", "Gerede", "Mudurnu", "Göynük", "Mengen", "Yeniçağa", "Dörtdivan", "Seben", "Kıbrıscık"), "05:17", "12:57", "16:21", "18:55", "20:14"),
        CityData("Burdur", 150f, listOf("Merkez", "Bucak", "Gölhisar", "Yeşilova", "Çavdır", "Tefenni", "Ağlasun", "Karamanlı", "Altınyayla", "Çeltikçi", "Kemer"), "05:24", "13:04", "16:28", "19:02", "20:21"),
        CityData("Bursa", 144f, listOf("Osmangazi", "Yıldırım", "Nilüfer", "İnegöl", "Gemlik", "Mustafakemalpaşa", "Mudanya", "Gürsu", "Karacabey", "Orhangazi", "Kestel", "Yenişehir", "İznik", "Orhaneli", "Keles", "Büyükorhan", "Harmancık"), "05:24", "13:04", "16:28", "19:02", "20:21"),
        CityData("Çanakkale", 138f, listOf("Merkez", "Biga", "Çan", "Gelibolu", "Yenice", "Ayvacık", "Ezine", "Bayramiç", "Lapseki", "Eceabat", "Gökçeada", "Bozcaada"), "05:33", "13:13", "16:37", "19:11", "20:30"),
        CityData("Çankırı", 152f, listOf("Merkez", "Çerkeş", "Ilgaz", "Orta", "Şabanözü", "Kurşunlu", "Yapraklı", "Kızılırmak", "Eldivan", "Atkaracalar", "Korgun", "Bayramören"), "05:13", "12:53", "16:17", "18:51", "20:10"),
        CityData("Çorum", 155f, listOf("Merkez", "Sungurlu", "Osmancık", "İskilip", "Alaca", "Bayat", "Mecitözü", "Kargı", "Ortaköy", "Uğurludağ", "Dodurga", "Oğuzlar", "Laçin", "Boğazkale"), "05:10", "12:50", "16:14", "18:48", "20:07"),
        CityData("Denizli", 146f, listOf("Pamukkale", "Merkezefendi", "Çivril", "Acıpayam", "Tavas", "Honaz", "Sarayköy", "Buldan", "Kale", "Çal", "Çameli", "Serinhisar", "Bozkurt", "Güney", "Çardak", "Bekilli", "Beyağaç", "Babadağ", "Baklan"), "05:27", "13:07", "16:31", "19:05", "20:24"),
        CityData("Diyarbakır", 175f, listOf("Bağlar", "Kayapınar", "Yenişehir", "Ergani", "Sur", "Bismil", "Silvan", "Çınar", "Çermik", "Dicle", "Kulp", "Hani", "Lice", "Eğil", "Hazro", "Kocaköy", "Çüngüş"), "04:52", "12:32", "15:56", "18:30", "19:49"),
        CityData("Edirne", 140f, listOf("Merkez", "Keşan", "Uzunköprü", "İpsala", "Havsa", "Meriç", "Enez", "Süloğlu", "Lalapaşa"), "05:30", "13:10", "16:34", "19:08", "20:27"),
        CityData("Elazığ", 171f, listOf("Merkez", "Kovancılar", "Karakoçan", "Palu", "Arıcak", "Baskil", "Maden", "Sivrice", "Keban", "Alacakaya", "Ağın"), "04:54", "12:34", "15:58", "18:32", "19:51"),
        CityData("Erzincan", 170f, listOf("Merkez", "Tercan", "Üzümlü", "Çayırlı", "İliç", "Kemah", "Kemaliye", "Refahiye", "Otlukbeli"), "04:49", "12:29", "15:53", "18:27", "19:46"),
        CityData("Erzurum", 176f, listOf("Yakutiye", "Palandöken", "Aziziye", "Horasan", "Oltu", "Pasinler", "Karayazı", "Hınıs", "Tekman", "Karaçoban", "Aşkale", "Şenkaya", "Çat", "Köprüköy", "İspir", "Tortum", "Narman", "Uzundere", "Olur", "Pazaryolu"), "04:41", "12:21", "15:45", "18:19", "19:38"),
        CityData("Eskişehir", 147f, listOf("Odunpazarı", "Tepebaşı", "Sivrihisar", "Çifteler", "Seyitgazi", "Alpu", "Mihalıççık", "Mahmudiye", "Beylikova", "İnönü", "Günyüzü", "Han", "Sarıcakaya", "Mihalgazi"), "05:21", "13:01", "16:25", "18:59", "20:18"),
        CityData("Gaziantep", 167f, listOf("Şahinbey", "Şehitkamil", "Nizip", "İslahiye", "Nurdağı", "Araban", "Oğuzeli", "Yavuzeli", "Karkamış"), "05:00", "12:40", "16:04", "18:38", "19:57"),
        CityData("Giresun", 164f, listOf("Merkez", "Bulancak", "Espiye", "Görele", "Tirebolu", "Dereli", "Şebinkarahisar", "Keşap", "Yağlıdere", "Piraziz", "Eynesil", "Alucra", "Çanakçı", "Güce", "Doğankent", "Çamoluk"), "04:54", "12:34", "15:58", "18:32", "19:51"),
        CityData("Gümüşhane", 168f, listOf("Merkez", "Kelkit", "Şiran", "Kürtün", "Torul", "Köse"), "04:50", "12:30", "15:54", "18:28", "19:47"),
        CityData("Hakkari", 188f, listOf("Yüksekova", "Merkez", "Şemdinli", "Çukurca", "Derecik"), "04:32", "12:12", "15:36", "18:10", "19:29"),
        CityData("Hatay", 164f, listOf("Antakya", "İskenderun", "Defne", "Dörtyol", "Samandağ", "Kırıkhan", "Reyhanlı", "Arsuz", "Altınözü", "Hassa", "Erzin", "Payas", "Belen", "Yayladağı", "Kumlu"), "05:03", "12:43", "16:07", "18:41", "20:00"),
        CityData("Isparta", 149f, listOf("Merkez", "Yalvaç", "Eğirdir", "Şarkikaraağaç", "Gelendost", "Keçiborlu", "Senirkent", "Sütçüler", "Gönen", "Uluborlu", "Atabey", "Aksu", "Yenişarbademli"), "05:22", "13:02", "16:26", "19:00", "20:19"),
        CityData("Mersin", 160f, listOf("Tarsus", "Toroslar", "Akdeniz", "Yenişehir", "Mezitli", "Erdemli", "Silifke", "Anamur", "Mut", "Bozyazı", "Gülnar", "Aydıncık", "Çamlıyayla"), "05:09", "12:49", "16:13", "18:47", "20:06"),
        CityData("İstanbul", 151f, listOf("Fatih", "Kadıköy", "Üsküdar", "Beşiktaş", "Şişli", "Bakırköy", "Beyoğlu", "Eyüpsultan", "Maltepe", "Pendik", "Kartal", "Ümraniye", "Ataşehir", "Sarıyer", "Beykoz", "Zeytinburnu", "Bahçelievler", "Güngören", "Bağcılar", "Esenler", "Küçükçekmece", "Başakşehir", "Avcılar", "Beylikdüzü", "Esenyurt", "Büyükçekmece", "Çatalca", "Silivri", "Arnavutköy", "Sultanbeyli", "Sancaktepe", "Çekmeköy", "Tuzla", "Şile", "Adalar"), "05:24", "13:04", "16:28", "19:02", "20:21"),
        CityData("İzmir", 139f, listOf("Konak", "Karşıyaka", "Bornova", "Buca", "Çiğli", "Karabağlar", "Bayraklı", "Menemen", "Torbalı", "Gaziemir", "Ödemiş", "Kemalpaşa", "Bergama", "Aliağa", "Menderes", "Tire", "Balçova", "Narlıdere", "Urla", "Çeşme", "Seferihisar", "Dikili", "Kiraz", "Bayındır", "Selçuk", "Güzelbahçe", "Foça", "Kınık", "Karaburun", "Beydağ"), "05:32", "13:12", "16:36", "19:10", "20:29"),
        CityData("Kars", 181f, listOf("Merkez", "Kağızman", "Sarıkamış", "Selim", "Digor", "Arpaçay", "Akyaka", "Susuz"), "04:36", "12:16", "15:40", "18:14", "19:33"),
        CityData("Kastamonu", 151f, listOf("Merkez", "Tosya", "Taşköprü", "Cide", "İnebolu", "Araç", "Devrekani", "Bozkurt", "Daday", "Azdavay", "Çatalzeytin", "Küre", "Doğanyurt", "İhsangazi", "Pınarbaşı", "Şenpazar", "Abana", "Seydiler", "Hanönü", "Ağlı"), "05:12", "12:52", "16:16", "18:50", "20:09"),
        CityData("Kayseri", 160f, listOf("Melikgazi", "Kocasinan", "Talas", "Develi", "Yahyalı", "Bünyan", "Pınarbaşı", "Tomarza", "Yeşilhisar", "Sarıoğlan", "Hacılar", "Sarız", "İncesu", "Felahiye", "Özvatan", "Akkışla"), "05:07", "12:47", "16:11", "18:45", "20:04"),
        CityData("Kırklareli", 142f, listOf("Merkez", "Lüleburgaz", "Babaeski", "Vize", "Pınarhisar", "Demirköy", "Pehlivanköy", "Kofçaz"), "05:27", "13:07", "16:31", "19:05", "20:24"),
        CityData("Kırşehir", 155f, listOf("Merkez", "Kaman", "Mucur", "Çiçekdağı", "Akpınar", "Boztepe", "Akçakent"), "05:12", "12:52", "16:16", "18:50", "20:09"),
        CityData("Kocaeli", 147f, listOf("İzmit", "Gebze", "Darıca", "Körfez", "Gölcük", "Derince", "Çayırova", "Kartepe", "Başiskele", "Karamürsel", "Kandıra", "Dilovası"), "05:21", "13:01", "16:25", "18:59", "20:18"),
        CityData("Konya", 154f, listOf("Selçuklu", "Meram", "Karatay", "Ereğli", "Akşehir", "Beyşehir", "Cihanbeyli", "Kulu", "Seydişehir", "Ilgın", "Bozkır", "Karapınar", "Sarayönü", "Yunak", "Doğanhisar", "Hüyük", "Altınekin", "Hadim", "Çeltik", "Güneysınır", "Emirgazi", "Tuzlukçu", "Derebucak", "Akören", "Halkapınar", "Yalıhüyük"), "05:17", "12:57", "16:21", "18:55", "20:14"),
        CityData("Kütahya", 146f, listOf("Merkez", "Tavşanlı", "Simav", "Gediz", "Emet", "Altıntaş", "Domaniç", "Hisarcık", "Aslanapa", "Çavdarhisar", "Şaphane", "Pazarlar", "Dumlupınar"), "05:23", "13:03", "16:27", "19:01", "20:20"),
        CityData("Malatya", 168f, listOf("Battalgazi", "Yeşilyurt", "Doğanşehir", "Akçadağ", "Darende", "Hekimhan", "Pütürge", "Yazıhan", "Arapgir", "Kuluncak", "Arguvan", "Kale", "Doğanyol"), "04:57", "12:37", "16:01", "18:35", "19:54"),
        CityData("Manisa", 141f, listOf("Yunusemre", "Şehzadeler", "Akhisar", "Turgutlu", "Salihli", "Soma", "Alaşehir", "Saruhanlı", "Kula", "Demirci", "Kırkağaç", "Sarıgöl", "Gördes", "Selendi", "Ahmetli", "Gölmarmara", "Köprübaşı"), "05:29", "13:09", "16:33", "19:07", "20:26"),
        CityData("Kahramanmaraş", 164f, listOf("Onikişubat", "Dulkadiroğlu", "Elbistan", "Afşin", "Türkoğlu", "Pazarcık", "Göksun", "Andırın", "Çağlayancerit", "Ekinözü", "Nurhak"), "05:01", "12:41", "16:05", "18:39", "19:58"),
        CityData("Mardin", 177f, listOf("Artuklu", "Kızıltepe", "Midyat", "Nusaybin", "Derik", "Mazıdağı", "Dargeçit", "Savur", "Yeşilli", "Ömerli"), "04:49", "12:29", "15:53", "18:27", "19:46"),
        CityData("Muğla", 143f, listOf("Bodrum", "Fethiye", "Milas", "Menteşe", "Marmaris", "Seydikemer", "Ortaca", "Yatağan", "Dalaman", "Köyceğiz", "Ula", "Datça", "Kavaklıdere"), "05:28", "13:08", "16:32", "19:06", "20:25"),
        CityData("Muş", 179f, listOf("Merkez", "Bulanık", "Malazgirt", "Varto", "Hasköy", "Korkut"), "04:42", "12:22", "15:46", "18:20", "19:39"),
        CityData("Nevşehir", 158f, listOf("Merkez", "Ürgüp", "Avanos", "Gülşehir", "Derinkuyu", "Acıgöl", "Kozaklı", "Hacıbektaş"), "05:11", "12:51", "16:15", "18:49", "20:08"),
        CityData("Niğde", 158f, listOf("Merkez", "Bor", "Çiftlik", "Ulukışla", "Altunhisar", "Çamardı"), "05:12", "12:52", "16:16", "18:50", "20:09"),
        CityData("Ordu", 161f, listOf("Altınordu", "Ünye", "Fatsa", "Gölköy", "Perşembe", "Korgan", "Kumru", "Aybastı", "Kabadüz", "Ulubey", "Mesudiye", "İkizce", "Gürgentepe", "Çatalpınar", "Çaybaşı", "Kabataş", "Akkuş", "Gülyalı"), "04:58", "12:38", "16:02", "18:36", "19:55"),
        CityData("Rize", 172f, listOf("Merkez", "Çayeli", "Ardeşen", "Pazar", "Fındıklı", "Güneysu", "Kalkandere", "İyidere", "Derepazarı", "Çamlıhemşin", "İkizdere", "Hemşin"), "04:47", "12:27", "15:51", "18:25", "19:44"),
        CityData("Sakarya", 147f, listOf("Adapazarı", "Serdivan", "Akyazı", "Erenler", "Hendek", "Karasu", "Geyve", "Arifiye", "Sapanca", "Pamukova", "Ferizli", "Kocaali", "Kaynarca", "Söğütlü", "Karapürçek", "Taraklı"), "05:19", "12:59", "16:23", "18:57", "20:16"),
        CityData("Samsun", 156f, listOf("İlkadım", "Atakum", "Bafra", "Çarşamba", "Canik", "Vezirköprü", "Terme", "Tekkeköy", "Havza", "Alaçam", "19 Mayıs", "Ayvacık", "Kavak", "Salıpazarı", "Asarcık", "Ladik", "Yakakent"), "05:05", "12:45", "16:09", "18:43", "20:02"),
        CityData("Siirt", 180f, listOf("Merkez", "Kurtalan", "Pervari", "Baykan", "Şirvan", "Eruh", "Tillo"), "04:43", "12:23", "15:47", "18:21", "19:40"),
        CityData("Sinop", 153f, listOf("Merkez", "Boyabat", "Gerze", "Ayancık", "Durağan", "Türkeli", "Erfelek", "Dikmen", "Saraydüzü"), "05:08", "12:48", "16:12", "18:46", "20:05"),
        CityData("Sivas", 162f, listOf("Merkez", "Şarkışla", "Yıldızeli", "Suşehri", "Gemerek", "Zara", "Kangal", "Gürün", "Divriği", "Koyulhisar", "Altınyayla", "Hafik", "Ulaş", "İmranlı", "Akıncılar", "Gölova", "Doğanşar"), "05:02", "12:42", "16:06", "18:40", "19:59"),
        CityData("Tekirdağ", 143f, listOf("Süleymanpaşa", "Çorlu", "Çerkezköy", "Kapaklı", "Ergene", "Malkara", "Saray", "Hayrabolu", "Şarköy", "Muratlı", "Marmaraereğlisi"), "05:27", "13:07", "16:31", "19:05", "20:24"),
        CityData("Tokat", 159f, listOf("Merkez", "Erbaa", "Turhal", "Niksar", "Zile", "Reşadiye", "Almus", "Pazar", "Yeşilyurt", "Artova", "Sulusaray", "Başçiftlik"), "05:05", "12:45", "16:09", "18:43", "20:02"),
        CityData("Trabzon", 169f, listOf("Ortahisar", "Akçaabat", "Araklı", "Of", "Yomra", "Arsin", "Vakfıkebir", "Sürmene", "Maçka", "Beşikdüzü", "Çarşıbaşı", "Tonya", "Düzköy", "Çaykara", "Şalpazarı", "Hayrat", "Köprübaşı", "Dernekpazarı"), "04:50", "12:30", "15:54", "18:28", "19:47"),
        CityData("Şanlıurfa", 170f, listOf("Eyyübiye", "Haliliye", "Siverek", "Viranşehir", "Karaköprü", "Akçakale", "Suruç", "Birecik", "Ceylanpınar", "Harran", "Bozova", "Hilvan", "Halfeti"), "04:57", "12:37", "16:01", "18:35", "19:54"),
        CityData("Uşak", 146f, listOf("Merkez", "Banaz", "Eşme", "Sivaslı", "Ulubey", "Karahallı"), "05:25", "13:05", "16:29", "19:03", "20:22"),
        CityData("Van", 184f, listOf("İpekyolu", "Erciş", "Tuşba", "Edremit", "Özalp", "Çaldıran", "Başkale", "Muradiye", "Gürpınar", "Gevaş", "Saray", "Çatak", "Bahçesaray"), "04:35", "12:15", "15:39", "18:13", "19:32"),
        CityData("Yozgat", 156f, listOf("Merkez", "Sorgun", "Akdağmadeni", "Yerköy", "Boğazlıyan", "Sarıkaya", "Çekerek", "Şefaatli", "Saraykent", "Çayıralan", "Kadışehri", "Aydıncık", "Yenifakılı", "Çandır"), "05:10", "12:50", "16:14", "18:48", "20:07"),
        CityData("Zonguldak", 148f, listOf("Merkez", "Ereğli", "Çaycuma", "Devrek", "Kozlu", "Alaplı", "Kilimli", "Gökçebey"), "05:16", "12:56", "16:20", "18:54", "20:13"),
        CityData("Aksaray", 155f, listOf("Merkez", "Ortaköy", "Eskil", "Gülağaç", "Güzelyurt", "Ağaçören", "Sarıyahşi", "Sultanhanı"), "05:14", "12:54", "16:18", "18:52", "20:11"),
        CityData("Bayburt", 172f, listOf("Merkez", "Demirözü", "Aydıntepe"), "04:47", "12:27", "15:51", "18:25", "19:44"),
        CityData("Karaman", 156f, listOf("Merkez", "Ermenek", "Sarıveliler", "Ayrancı", "Kazımkarabekir", "Başyayla"), "05:16", "12:56", "16:20", "18:54", "20:13"),
        CityData("Kırıkkale", 153f, listOf("Merkez", "Yahşihan", "Keskin", "Delice", "Sulakyurt", "Bahşılı", "Balışeyh", "Karakeçili", "Çelebi"), "05:14", "12:54", "16:18", "18:52", "20:11"),
        CityData("Batman", 177f, listOf("Merkez", "Kozluk", "Sason", "Beşiri", "Gercüş", "Hasankeyf"), "04:48", "12:28", "15:52", "18:26", "19:45"),
        CityData("Şırnak", 182f, listOf("Cizre", "Silopi", "Merkez", "İdil", "Uludere", "Beytüşşebap", "Güçlükonak"), "04:42", "12:22", "15:46", "18:20", "19:39"),
        CityData("Bartın", 149f, listOf("Merkez", "Ulus", "Amasra", "Kurucaşile"), "05:14", "12:54", "16:18", "18:52", "20:11"),
        CityData("Ardahan", 180f, listOf("Merkez", "Göle", "Çıldır", "Hanak", "Posof", "Damal"), "04:38", "12:18", "15:42", "18:16", "19:35"),
        CityData("Iğdır", 184f, listOf("Merkez", "Tuzluca", "Aralık", "Karakoyunlu"), "04:32", "12:12", "15:36", "18:10", "19:29"),
        CityData("Yalova", 145f, listOf("Merkez", "Çiftlikköy", "Çınarcık", "Altınova", "Armutlu", "Termal"), "05:23", "13:03", "16:27", "19:01", "20:20"),
        CityData("Karabük", 150f, listOf("Merkez", "Safranbolu", "Yenice", "Eskipazar", "Eflani", "Ovacık"), "05:15", "12:55", "16:19", "18:53", "20:12"),
        CityData("Kilis", 166f, listOf("Merkez", "Musabeyli", "Elbeyli", "Polateli"), "05:02", "12:42", "16:06", "18:40", "19:59"),
        CityData("Osmaniye", 164f, listOf("Merkez", "Kadirli", "Düziçi", "Bahçe", "Toprakkale", "Sumbas", "Hasanbeyli"), "05:03", "12:43", "16:07", "18:41", "20:00"),
        CityData("Düzce", 148f, listOf("Merkez", "Akçakoca", "Kaynaşlı", "Gölyaka", "Çilimli", "Yığılca", "Gümüşova", "Cumayeri"), "05:18", "12:58", "16:22", "18:56", "20:15")
    )

    // Seçilen Şehir ve İlçeye Özel Gerçek Cami Listesi Üretici
    fun getMosquesForDistrict(cityName: String, districtName: String): List<MosqueItem> {
        return when {
            cityName == "İstanbul" && districtName == "Fatih" -> listOf(
                MosqueItem("Süleymaniye Camii", "350m", "Prof. Dr. Sıddık Sami Onar Cd., Fatih"),
                MosqueItem("Ayasofya-i Kebîr Câmi-i Şerîfi", "650m", "Sultanahmet Meydanı, Fatih"),
                MosqueItem("Sultanahmet Camii", "800m", "Atmeydanı Cd. No:7, Fatih"),
                MosqueItem("Fatih Camii", "1.1km", "Hattat Nafiz Cd., Fatih"),
                MosqueItem("Yavuz Selim Camii", "1.4km", "Yavuz Selim Mah., Fatih"),
                MosqueItem("Şehzade Camii", "500m", "Şehzadebaşı Cd., Fatih")
            )
            cityName == "İstanbul" && districtName == "Üsküdar" -> listOf(
                MosqueItem("Büyük Çamlıca Camii", "1.2km", "Ferah Mah., Üsküdar"),
                MosqueItem("Mihrimah Sultan Camii", "250m", "İskele Meydanı, Üsküdar"),
                MosqueItem("Şemsi Paşa (Kuşkonmaz) Camii", "300m", "Sahil Yolu, Üsküdar"),
                MosqueItem("Aziz Mahmud Hüdayi Camii", "450m", "Gülfem Hatun, Üsküdar"),
                MosqueItem("Valide-i Cedid Camii", "400m", "Hakimiyeti Milliye Cd., Üsküdar")
            )
            cityName == "İstanbul" && districtName == "Eyüpsultan" -> listOf(
                MosqueItem("Eyüp Sultan Camii", "200m", "Cami Meydanı, Eyüpsultan"),
                MosqueItem("Zal Mahmut Paşa Camii", "450m", "Zal Paşa Cd., Eyüpsultan"),
                MosqueItem("Cezeri Kasım Paşa Camii", "700m", "Eyüpsultan Merkez"),
                MosqueItem("Kızıl Mescit", "850m", "Eyüpsultan")
            )
            cityName == "İstanbul" && districtName == "Kadıköy" -> listOf(
                MosqueItem("Osmanağa Camii", "200m", "Söğütlüçeşme Cd., Kadıköy"),
                MosqueItem("Caferağa Camii", "450m", "Moda Cd., Kadıköy"),
                MosqueItem("Sultan 3. Mustafa Camii", "600m", "İskele Yanı, Kadıköy"),
                MosqueItem("Galip Paşa Camii", "1.8km", "Bağdat Cd., Kadıköy")
            )
            cityName == "Ankara" && districtName == "Altındağ" -> listOf(
                MosqueItem("Hacı Bayram Veli Camii", "300m", "Ulus Meydanı, Altındağ"),
                MosqueItem("Melike Hatun Camii", "650m", "Anafartalar Cd., Altındağ"),
                MosqueItem("Aslanhane (Ahi Şerafeddin) Camii", "800m", "Kale İçi, Altındağ"),
                MosqueItem("Zincirli Camii", "550m", "Ulus, Altındağ")
            )
            cityName == "Ankara" && districtName == "Çankaya" -> listOf(
                MosqueItem("Kocatepe Camii", "350m", "Kültür Mah., Çankaya"),
                MosqueItem("Maltepe Camii", "1.1km", "Gazi Mustafa Kemal Blv., Çankaya"),
                MosqueItem("Ahmet Hamdi Akseki Camii", "3.2km", "Üniversiteler Mah., Çankaya"),
                MosqueItem("Doğramacızade Ali Paşa Camii", "2.8km", "Bilkent, Çankaya")
            )
            cityName == "İzmir" && districtName == "Konak" -> listOf(
                MosqueItem("Konak Yalı Camii", "150m", "Konak Meydanı, Konak"),
                MosqueItem("Hisar Camii", "500m", "Kemeraltı Çarşısı, Konak"),
                MosqueItem("Şadırvanaltı Camii", "650m", "Kemeraltı, Konak"),
                MosqueItem("Kestane Pazarı Camii", "800m", "Anafartalar Cd., Konak"),
                MosqueItem("Başdurak Camii", "700m", "Anafartalar Cd., Konak")
            )
            cityName == "Bursa" && districtName == "Osmangazi" -> listOf(
                MosqueItem("Bursa Ulu Camii", "250m", "Atatürk Cd., Osmangazi"),
                MosqueItem("Muradiye Camii", "900m", "Muradiye Külliyesi, Osmangazi"),
                MosqueItem("Şehadet Camii", "450m", "Tophane İçi, Osmangazi"),
                MosqueItem("Hüdavendigar Camii", "2.1km", "Çekirge, Osmangazi")
            )
            cityName == "Bursa" && districtName == "Yıldırım" -> listOf(
                MosqueItem("Yeşil Camii", "300m", "Yeşil Mah., Yıldırım"),
                MosqueItem("Emir Sultan Camii", "700m", "Emirsultan Mah., Yıldırım"),
                MosqueItem("Yıldırım Bayezid Camii", "1.2km", "Yıldırım"),
                MosqueItem("İshak Paşa Camii", "850m", "Yıldırım")
            )
            cityName == "Konya" && districtName == "Karatay" -> listOf(
                MosqueItem("Mevlana Dergahı Camii", "250m", "Aziziye Mah., Karatay"),
                MosqueItem("Aziziye Camii", "450m", "Bedesten İçi, Karatay"),
                MosqueItem("Kapu Camii", "600m", "Tarihi Çarşı, Karatay"),
                MosqueItem("Şerafeddin Camii", "750m", "Hükümet Meydanı, Karatay"),
                MosqueItem("Selimiye Camii", "300m", "Mevlana Meydanı, Karatay")
            )
            cityName == "Konya" && districtName == "Selçuklu" -> listOf(
                MosqueItem("Alaeddin Camii", "400m", "Alaeddin Tepesi, Selçuklu"),
                MosqueItem("Hacıveyiszade Camii", "800m", "Feritpaşa, Selçuklu"),
                MosqueItem("İnce Minareli Cami", "550m", "Alaaddin Cd., Selçuklu"),
                MosqueItem("Sahip Ata Camii", "1.1km", "Selçuklu")
            )
            cityName == "Edirne" && districtName == "Merkez" -> listOf(
                MosqueItem("Selimiye Camii", "250m", "Meydan Mah., Merkez"),
                MosqueItem("Üç Şerefeli Camii", "450m", "Hükümet Cd., Merkez"),
                MosqueItem("Eski Camii (Ulu Cami)", "550m", "Talat Paşa Cd., Merkez"),
                MosqueItem("II. Bayezid Külliyesi Camii", "1.6km", "Yeniimaret, Merkez"),
                MosqueItem("Muradiye Camii", "950m", "Muradiye Mah., Merkez")
            )
            cityName == "Diyarbakır" && districtName == "Sur" -> listOf(
                MosqueItem("Diyarbakır Ulu Camii", "200m", "Cami Kebir Mah., Sur"),
                MosqueItem("Hazreti Süleyman Camii", "550m", "İçkale, Sur"),
                MosqueItem("Dört Ayaklı Minare (Şeyh Mutahhar)", "400m", "Yenikapı Sk., Sur"),
                MosqueItem("Nebii Camii", "700m", "Gazi Cd., Sur"),
                MosqueItem("Behram Paşa Camii", "850m", "Ziya Gökalp Mah., Sur")
            )
            cityName == "Şanlıurfa" && districtName == "Eyyübiye" -> listOf(
                MosqueItem("Balıklıgöl Halil-ür Rahman Camii", "150m", "Gölbaşı, Eyyübiye"),
                MosqueItem("Rızvaniye Camii", "200m", "Balıklıgöl Yanı, Eyyübiye"),
                MosqueItem("Şanlıurfa Ulu Camii", "650m", "Cami Kebir, Eyyübiye"),
                MosqueItem("Mevlid-i Halil (Dergah) Camii", "250m", "Dergah Platosu, Eyyübiye"),
                MosqueItem("Hasan Padişah Camii", "450m", "Eyyübiye")
            )
            cityName == "Trabzon" && districtName == "Ortahisar" -> listOf(
                MosqueItem("Ayasofya Camii", "500m", "Fatih Mah., Ortahisar"),
                MosqueItem("Ortahisar Fatih Camii", "700m", "Ortahisar"),
                MosqueItem("İskenderpaşa Camii", "350m", "Meydan Parkı Yanı, Ortahisar"),
                MosqueItem("Gülbahar Hatun Camii", "950m", "Gülbaharhatun, Ortahisar")
            )
            else -> listOf(
                MosqueItem("$cityName $districtName Merkez Camii", "250m", "$districtName Meydanı, $cityName"),
                MosqueItem("$districtName Çarşı Camii", "450m", "Hükümet Konağı Yanı, $districtName"),
                MosqueItem("$districtName Ulu Camii", "700m", "Cumhuriyet Caddesi, $districtName"),
                MosqueItem("$districtName Fatih Camii", "950m", "İstasyon Mah., $districtName"),
                MosqueItem("$districtName Yeni Camii", "1.2km", "Atatürk Bulvarı, $districtName")
            )
        }
    }
}
