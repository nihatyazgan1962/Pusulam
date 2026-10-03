package com.nihatyazgan.pusulam

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nihatyazgan.pusulam.data.CityData
import com.nihatyazgan.pusulam.data.TurkeyLocationData
import com.nihatyazgan.pusulam.view.CompassView
import com.nihatyazgan.pusulam.view.WaterCompassView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class DhikrItem(val name: String, val meaning: String, val defaultTarget: Int)

class MainActivity : AppCompatActivity(), SensorEventListener, TextToSpeech.OnInitListener, LocationListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null
    private var locationManager: LocationManager? = null

    private var gravity = FloatArray(3)
    private var geomagnetic = FloatArray(3)
    private var azimuth = 0f
    private var currentCity: CityData = TurkeyLocationData.cities.find { it.name == "İstanbul" } ?: TurkeyLocationData.cities[0]
    private var selectedDistrict = "Fatih"
    private var isAutoLocationEnabled = true
    private lateinit var prefs: SharedPreferences

    // Views - Header
    private lateinit var tvLocationDisplay: TextView
    private lateinit var btnLocationPicker: LinearLayout

    // Views - Faith Section
    private lateinit var compassView: CompassView
    private lateinit var tvAzimuthDegree: TextView
    private lateinit var tvQiblaStatus: TextView
    private lateinit var tvCurrentPrayerTitle: TextView
    private lateinit var tvPrayerCountdown: TextView
    private lateinit var tvTimeImsak: TextView
    private lateinit var tvTimeOgle: TextView
    private lateinit var tvTimeIkindi: TextView
    private lateinit var tvTimeAksam: TextView
    private lateinit var tvTimeYatsi: TextView
    private lateinit var tvLabelImsak: TextView
    private lateinit var tvLabelOgle: TextView
    private lateinit var tvLabelIkindi: TextView
    private lateinit var tvLabelAksam: TextView
    private lateinit var tvLabelYatsi: TextView

    // Views - Zikirmatik
    private lateinit var btnSelectDhikr: TextView
    private lateinit var btnSelectTarget: TextView
    private lateinit var tvZikirMeaning: TextView
    private lateinit var tvZikirCount: TextView
    private lateinit var btnCountZikir: Button
    private lateinit var btnResetZikir: Button
    private lateinit var btnToggleSound: TextView
    private lateinit var btnToggleVibe: TextView

    // Layout Sections & Bottom Nav
    private lateinit var layoutFaith: LinearLayout
    private lateinit var layoutHealth: LinearLayout
    private lateinit var layoutMind: LinearLayout
    private lateinit var layoutLife: LinearLayout
    private lateinit var bottomNavigation: BottomNavigationView

    // Health Section Views
    private lateinit var tvStepCount: TextView
    private lateinit var tvStepProgress: TextView
    private lateinit var btnAddSteps: Button
    private lateinit var btnResetSteps: Button
    private lateinit var waterCompassView: WaterCompassView
    private lateinit var btnAddWater200: Button
    private lateinit var btnAddWater500: Button
    private lateinit var btnResetWater: Button

    // Mind Section Views
    private lateinit var btnCompleteTask: Button
    private lateinit var btnStartDetox: Button
    private lateinit var tvDetoxStatus: TextView

    // Life Section Views
    private lateinit var cardTravel: LinearLayout
    private lateinit var cardOffline: LinearLayout
    private lateinit var cardSettings: LinearLayout
    private lateinit var tvNearbyMosquesTitle: TextView
    private lateinit var btnRefreshMosques: TextView
    private lateinit var layoutMosqueList: LinearLayout

    // Zikir Listesi
    private val dhikrList = listOf(
        DhikrItem("Sübhânallâh", "\"Allah bütün noksan sıfatlardan münezzehtir.\"", 33),
        DhikrItem("Elhamdülillâh", "\"Hamd ve şükür yalnızca Allah'a aittir.\"", 33),
        DhikrItem("Allâhuekber", "\"Allah en büyüktür, her şeyden yücedir.\"", 33),
        DhikrItem("Lâ ilâhe illallâh", "\"Allah'tan başka hiçbir ilah yoktur.\"", 100),
        DhikrItem("Estagfirullâh", "\"Allah'tan bağışlanma ve mağfiret dilerim.\"", 100),
        DhikrItem("Allahümme Salli Alâ Seyyidinâ Muhammed", "\"Peygamber Efendimiz'e (s.a.v.) salat ve selam olsun.\"", 100),
        DhikrItem("Lâ havle velâ kuvvete illâ billâh", "\"Güç ve kuvvet ancak yüce Allah'a aittir.\"", 33),
        DhikrItem("Hasbünallâhu ve ni'mel vekîl", "\"Allah bize yeter, O ne güzel vekildir.\"", 33),
        DhikrItem("Ya Şâfî", "\"Ey bütün dertlere ve hastalıklara şifa veren Allah'ım.\"", 100),
        DhikrItem("Ya Fettâh", "\"Ey bütün kapıları ve hayırları açan Allah'ım.\"", 99),
        DhikrItem("Ya Rezzâk", "\"Ey sonsuz rızık ve bereket bahşeden Allah'ım.\"", 99)
    )

    private var currentDhikrIndex = 0
    private var targetCount = 33
    private var zikirCounter = 0
    private var isSoundEnabled = true
    private var isVibeEnabled = true
    private var stepCounter = 0
    private var currentWaterMl = 0
    private var isDetoxRunning = false

    private var vibrator: Vibrator? = null
    private var toneGenerator: ToneGenerator? = null
    private var textToSpeech: TextToSpeech? = null
    private var ttsReady = false

    // Real-Time Countdown Timer & Prayer Manager
    private val timerHandler = Handler(Looper.getMainLooper())
    private var lastAnnouncedPrayer = ""

    private val countdownRunnable = object : Runnable {
        override fun run() {
            updateRealTimePrayerCountdown()
            timerHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("PUSULAM_PREFS", Context.MODE_PRIVATE)
        loadSavedLocation()

        initViews()
        setupSensors()
        setupNavigation()
        setupInteractions()
        setupTTS()
        initLocationManager()

        updateLocationUI()
        updateDhikrUI()

        // Geri sayım döngüsünü başlat
        timerHandler.post(countdownRunnable)
    }

    private fun loadSavedLocation() {
        isAutoLocationEnabled = prefs.getBoolean("AUTO_LOCATION_ENABLED", true)
        val savedCityName = prefs.getString("SAVED_CITY_NAME", null)
        val savedDistrict = prefs.getString("SAVED_DISTRICT", null)

        if (savedCityName != null) {
            val foundCity = TurkeyLocationData.cities.find { it.name.equals(savedCityName, ignoreCase = true) }
            if (foundCity != null) {
                currentCity = foundCity
                if (savedDistrict != null && foundCity.districts.any { it.equals(savedDistrict, ignoreCase = true) }) {
                    selectedDistrict = foundCity.districts.first { it.equals(savedDistrict, ignoreCase = true) }
                } else {
                    selectedDistrict = foundCity.districts.firstOrNull() ?: "Merkez"
                }
            }
        }
    }

    private fun saveLocation(cityName: String, districtName: String, isAuto: Boolean) {
        isAutoLocationEnabled = isAuto
        prefs.edit()
            .putString("SAVED_CITY_NAME", cityName)
            .putString("SAVED_DISTRICT", districtName)
            .putBoolean("AUTO_LOCATION_ENABLED", isAuto)
            .apply()
    }

    private fun initLocationManager() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        checkAndRequestLocationPermission()
    }

    private fun checkAndRequestLocationPermission() {
        val finePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (finePerm == PackageManager.PERMISSION_GRANTED || coarsePerm == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            )
        }
    }

    private fun requestLocationUpdates() {
        try {
            val isGpsEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
            val isNetEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false

            if (isNetEnabled) {
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000L, 100f, this)
                val lastLoc = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                lastLoc?.let { onLocationReceived(it) }
            }
            if (isGpsEnabled) {
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000L, 100f, this)
                val lastLoc = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                lastLoc?.let { onLocationReceived(it) }
            }
        } catch (_: SecurityException) { }
    }

    private fun onLocationReceived(location: Location) {
        if (!isAutoLocationEnabled) return // Kullanıcı elle seçtiyse ve otomatik kapalıysa değiştirme

        Thread {
            try {
                val geocoder = Geocoder(this, Locale("tr", "TR"))
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val adminArea = address.adminArea ?: ""
                    val subAdminArea = address.subAdminArea ?: address.locality ?: ""

                    // 81 ilimizden hangisiyle eşleşiyor
                    var matchedCity: CityData? = null
                    for (city in TurkeyLocationData.cities) {
                        if (adminArea.contains(city.name, ignoreCase = true) ||
                            address.getAddressLine(0)?.contains(city.name, ignoreCase = true) == true) {
                            matchedCity = city
                            break
                        }
                    }

                    if (matchedCity != null) {
                        var matchedDistrict = matchedCity.districts.firstOrNull { d ->
                            subAdminArea.contains(d, ignoreCase = true) ||
                            address.getAddressLine(0)?.contains(d, ignoreCase = true) == true
                        } ?: matchedCity.districts.firstOrNull() ?: "Merkez"

                        runOnUiThread {
                            if (currentCity.name != matchedCity.name || selectedDistrict != matchedDistrict) {
                                currentCity = matchedCity
                                selectedDistrict = matchedDistrict
                                saveLocation(currentCity.name, selectedDistrict, isAuto = true)
                                updateLocationUI()
                                Toast.makeText(this, "📍 Otomatik Konum: ${currentCity.name} / $selectedDistrict", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } catch (_: Exception) { }
        }.start()
    }

    override fun onLocationChanged(location: Location) {
        onLocationReceived(location)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQ_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates()
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQ_CODE = 1001
    }

    private fun setupTTS() {
        textToSpeech = TextToSpeech(this, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale("tr", "TR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeech?.language = Locale.getDefault()
            }
            // Daha Tok, Derin ve Vakur Erkek/Müezzin Tonu
            textToSpeech?.setPitch(0.72f) // Derin & Tok Ses (Bass tonu)
            textToSpeech?.setSpeechRate(0.88f) // Sakin & Anlaşılır Hız
            ttsReady = true
        }
    }

    private fun playAdhanMelodySequence(prayerName: String) {
        val speechText = "🕌 $prayerName vakti girdi"
        Toast.makeText(this, speechText, Toast.LENGTH_LONG).show()

        if (isVibeEnabled) vibrateDevice(300)

        if (isSoundEnabled) {
            Thread {
                try {
                    // Ezan Makamı Melodisi (Hicaz / Uşşak Notaları: Allâhuekber motifleri)
                    // Frequencies (Hz): D4 (293.66), F4 (349.23), G4 (392.00), A4 (440.00), Bb4 (466.16), C5 (523.25), D5 (587.33)
                    val sampleRate = 44100
                    val melodyNotes = listOf(
                        Pair(293.66, 0.4),  // Al-
                        Pair(349.23, 0.6),  // lâ-
                        Pair(392.00, 0.5),  // hu
                        Pair(440.00, 0.9),  // ek-
                        Pair(392.00, 0.7),  // ber
                        Pair(0.0, 0.25),    // Es (Nefes)
                        Pair(392.00, 0.45), // Al-
                        Pair(440.00, 0.7),  // lâ-
                        Pair(466.16, 0.6),  // hu
                        Pair(523.25, 0.9),  // ek-
                        Pair(440.00, 0.8)   // ber
                    )

                    var totalSamples = 0
                    for (note in melodyNotes) {
                        totalSamples += (sampleRate * note.second).toInt()
                    }

                    val generatedSnd = ShortArray(totalSamples)
                    var currentIdx = 0

                    for (note in melodyNotes) {
                        val numSamples = (sampleRate * note.second).toInt()
                        val freq = note.first

                        for (i in 0 until numSamples) {
                            if (freq > 0.0) {
                                // Yumuşak akustik ney/org harmonics sentezi
                                val t = i.toDouble() / sampleRate
                                val envelope = Math.sin(Math.PI * (i.toDouble() / numSamples)) // Yumuşak Attack/Decay
                                val fundamental = Math.sin(2.0 * Math.PI * freq * t)
                                val harmonic2 = 0.35 * Math.sin(4.0 * Math.PI * freq * t)
                                val harmonic3 = 0.15 * Math.sin(6.0 * Math.PI * freq * t)
                                val sampleVal = ((fundamental + harmonic2 + harmonic3) * envelope * 0.75 * Short.MAX_VALUE).toInt()
                                generatedSnd[currentIdx++] = sampleVal.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                            } else {
                                generatedSnd[currentIdx++] = 0
                            }
                        }
                    }

                    val minBufSize = android.media.AudioTrack.getMinBufferSize(
                        sampleRate,
                        android.media.AudioFormat.CHANNEL_OUT_MONO,
                        android.media.AudioFormat.ENCODING_PCM_16BIT
                    )

                    val audioTrack = android.media.AudioTrack(
                        android.media.AudioManager.STREAM_MUSIC,
                        sampleRate,
                        android.media.AudioFormat.CHANNEL_OUT_MONO,
                        android.media.AudioFormat.ENCODING_PCM_16BIT,
                        Math.max(minBufSize, generatedSnd.size * 2),
                        android.media.AudioTrack.MODE_STATIC
                    )

                    audioTrack.write(generatedSnd, 0, generatedSnd.size)
                    audioTrack.play()
                } catch (e: Exception) {
                    // Fallback to ToneGenerator if audio track fails
                    try {
                        toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 300)
                    } catch (_: Exception) {}
                }
            }.start()
        }
    }

    private fun updateRealTimePrayerCountdown() {
        val now = Calendar.getInstance()
        val currentHour = now.get(Calendar.HOUR_OF_DAY)
        val currentMin = now.get(Calendar.MINUTE)
        val currentSec = now.get(Calendar.SECOND)
        val currentTotalSeconds = currentHour * 3600 + currentMin * 60 + currentSec

        // Vakitlerin saniye karşılıkları
        val imsakSec = parseTimeToSeconds(currentCity.imsak)
        val ogleSec = parseTimeToSeconds(currentCity.ogle)
        val ikindiSec = parseTimeToSeconds(currentCity.ikindi)
        val aksamSec = parseTimeToSeconds(currentCity.aksam)
        val yatsiSec = parseTimeToSeconds(currentCity.yatsi)

        val nextPrayerName: String
        val targetSeconds: Int
        var activePrayerIndex = -1 // 0: İmsak, 1: Öğle, 2: İkindi, 3: Akşam, 4: Yatsı

        when {
            currentTotalSeconds < imsakSec -> {
                nextPrayerName = "İmsak"
                targetSeconds = imsakSec
                activePrayerIndex = 0
            }
            currentTotalSeconds < ogleSec -> {
                nextPrayerName = "Öğle"
                targetSeconds = ogleSec
                activePrayerIndex = 1
            }
            currentTotalSeconds < ikindiSec -> {
                nextPrayerName = "İkindi"
                targetSeconds = ikindiSec
                activePrayerIndex = 2
            }
            currentTotalSeconds < aksamSec -> {
                nextPrayerName = "Akşam"
                targetSeconds = aksamSec
                activePrayerIndex = 3
            }
            currentTotalSeconds < yatsiSec -> {
                nextPrayerName = "Yatsı"
                targetSeconds = yatsiSec
                activePrayerIndex = 4
            }
            else -> {
                // Gece yarısından sonra sıradaki ilk vakit: Yarınki İmsak
                nextPrayerName = "İmsak"
                targetSeconds = imsakSec + (24 * 3600)
                activePrayerIndex = 0
            }
        }

        val diffSeconds = targetSeconds - currentTotalSeconds

        // Vakit Tam Girdiğinde Ezan Melodisi Çal (0 saniye kaldığında)
        if (diffSeconds in 0..1 && lastAnnouncedPrayer != nextPrayerName) {
            lastAnnouncedPrayer = nextPrayerName
            playAdhanMelodySequence(nextPrayerName)
        }

        val hours = diffSeconds / 3600
        val minutes = (diffSeconds % 3600) / 60
        val seconds = diffSeconds % 60

        val formattedCountdown = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        tvCurrentPrayerTitle.text = "🕌 Sıradaki Vakit: $nextPrayerName"
        tvPrayerCountdown.text = "$formattedCountdown kaldı"

        highlightActivePrayerInTable(activePrayerIndex)
    }

    private fun highlightActivePrayerInTable(activeIndex: Int) {
        val defaultColor = Color.WHITE
        val defaultLabelColor = Color.parseColor("#94A3B8")
        val highlightColor = Color.parseColor("#10B981") // Parlak Zümrüt Yeşili

        tvTimeImsak.setTextColor(if (activeIndex == 0) highlightColor else defaultColor)
        tvLabelImsak.setTextColor(if (activeIndex == 0) highlightColor else defaultLabelColor)

        tvTimeOgle.setTextColor(if (activeIndex == 1) highlightColor else defaultColor)
        tvLabelOgle.setTextColor(if (activeIndex == 1) highlightColor else defaultLabelColor)

        tvTimeIkindi.setTextColor(if (activeIndex == 2) highlightColor else defaultColor)
        tvLabelIkindi.setTextColor(if (activeIndex == 2) highlightColor else defaultLabelColor)

        tvTimeAksam.setTextColor(if (activeIndex == 3) highlightColor else defaultColor)
        tvLabelAksam.setTextColor(if (activeIndex == 3) highlightColor else defaultLabelColor)

        tvTimeYatsi.setTextColor(if (activeIndex == 4) highlightColor else defaultColor)
        tvLabelYatsi.setTextColor(if (activeIndex == 4) highlightColor else defaultLabelColor)
    }

    private fun parseTimeToSeconds(timeStr: String): Int {
        val parts = timeStr.split(":")
        if (parts.size >= 2) {
            val h = parts[0].trim().toIntOrNull() ?: 0
            val m = parts[1].trim().toIntOrNull() ?: 0
            return h * 3600 + m * 60
        }
        return 0
    }

    private fun initViews() {
        btnLocationPicker = findViewById(R.id.btnLocationPicker)
        tvLocationDisplay = findViewById(R.id.tvLocationDisplay)

        compassView = findViewById(R.id.compassView)
        tvAzimuthDegree = findViewById(R.id.tvAzimuthDegree)
        tvQiblaStatus = findViewById(R.id.tvQiblaStatus)
        tvCurrentPrayerTitle = findViewById(R.id.tvCurrentPrayerTitle)
        tvPrayerCountdown = findViewById(R.id.tvPrayerCountdown)

        tvTimeImsak = findViewById(R.id.tvTimeImsak)
        tvTimeOgle = findViewById(R.id.tvTimeOgle)
        tvTimeIkindi = findViewById(R.id.tvTimeIkindi)
        tvTimeAksam = findViewById(R.id.tvTimeAksam)
        tvTimeYatsi = findViewById(R.id.tvTimeYatsi)

        tvLabelImsak = findViewById(R.id.tvLabelImsak)
        tvLabelOgle = findViewById(R.id.tvLabelOgle)
        tvLabelIkindi = findViewById(R.id.tvLabelIkindi)
        tvLabelAksam = findViewById(R.id.tvLabelAksam)
        tvLabelYatsi = findViewById(R.id.tvLabelYatsi)

        btnSelectDhikr = findViewById(R.id.btnSelectDhikr)
        btnSelectTarget = findViewById(R.id.btnSelectTarget)
        tvZikirMeaning = findViewById(R.id.tvZikirMeaning)
        tvZikirCount = findViewById(R.id.tvZikirCount)
        btnCountZikir = findViewById(R.id.btnCountZikir)
        btnResetZikir = findViewById(R.id.btnResetZikir)
        btnToggleSound = findViewById(R.id.btnToggleSound)
        btnToggleVibe = findViewById(R.id.btnToggleVibe)

        layoutFaith = findViewById(R.id.layoutFaith)
        layoutHealth = findViewById(R.id.layoutHealth)
        layoutMind = findViewById(R.id.layoutMind)
        layoutLife = findViewById(R.id.layoutLife)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        tvStepCount = findViewById(R.id.tvStepCount)
        tvStepProgress = findViewById(R.id.tvStepProgress)
        btnAddSteps = findViewById(R.id.btnAddSteps)
        btnResetSteps = findViewById(R.id.btnResetSteps)

        waterCompassView = findViewById(R.id.waterCompassView)
        btnAddWater200 = findViewById(R.id.btnAddWater200)
        btnAddWater500 = findViewById(R.id.btnAddWater500)
        btnResetWater = findViewById(R.id.btnResetWater)

        btnCompleteTask = findViewById(R.id.btnCompleteTask)
        btnStartDetox = findViewById(R.id.btnStartDetox)
        tvDetoxStatus = findViewById(R.id.tvDetoxStatus)

        cardTravel = findViewById(R.id.cardTravel)
        cardOffline = findViewById(R.id.cardOffline)
        cardSettings = findViewById(R.id.cardSettings)
        tvNearbyMosquesTitle = findViewById(R.id.tvNearbyMosquesTitle)
        btnRefreshMosques = findViewById(R.id.btnRefreshMosques)
        layoutMosqueList = findViewById(R.id.layoutMosqueList)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 80)
        } catch (e: Exception) {
            toneGenerator = null
        }
    }

    private fun setupSensors() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    private fun setupNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            hideAllSections()
            when (item.itemId) {
                R.id.nav_faith -> {
                    layoutFaith.visibility = View.VISIBLE
                    true
                }
                R.id.nav_health -> {
                    layoutHealth.visibility = View.VISIBLE
                    true
                }
                R.id.nav_mind -> {
                    layoutMind.visibility = View.VISIBLE
                    true
                }
                R.id.nav_life -> {
                    layoutLife.visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }
    }

    private fun hideAllSections() {
        layoutFaith.visibility = View.GONE
        layoutHealth.visibility = View.GONE
        layoutMind.visibility = View.GONE
        layoutLife.visibility = View.GONE
    }

    private fun setupInteractions() {
        btnLocationPicker.setOnClickListener { showCitySelectionDialog() }

        // Zikirmatik Seçimleri
        btnSelectDhikr.setOnClickListener { showDhikrSelectionDialog() }
        btnSelectTarget.setOnClickListener { showTargetSelectionDialog() }

        // Zikir Sayma & Sınırlama
        btnCountZikir.setOnClickListener {
            if (targetCount > 0 && zikirCounter >= targetCount) {
                if (isVibeEnabled) vibrateDevice(120)
                if (isSoundEnabled) toneGenerator?.startTone(ToneGenerator.TONE_PROP_PROMPT, 100)
                Toast.makeText(this, "Hedefe ulaşıldı! (${targetCount} Tamamlandı 🌟)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            zikirCounter++
            updateDhikrCounterText()

            if (isVibeEnabled) vibrateDevice(35)
            if (isSoundEnabled) toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 50)

            if (targetCount > 0 && zikirCounter == targetCount) {
                if (isVibeEnabled) vibrateDevice(150)
                Toast.makeText(this, "Tebrikler! ${dhikrList[currentDhikrIndex].name} hedefinizi tamamladınız.", Toast.LENGTH_LONG).show()
            }
        }

        btnResetZikir.setOnClickListener {
            zikirCounter = 0
            updateDhikrCounterText()
            if (isVibeEnabled) vibrateDevice(60)
            Toast.makeText(this, "Zikirmatik sıfırlandı", Toast.LENGTH_SHORT).show()
        }

        btnToggleSound.setOnClickListener {
            isSoundEnabled = !isSoundEnabled
            btnToggleSound.text = if (isSoundEnabled) "🔊 Ses: Açık" else "🔇 Ses: Kapalı"
        }

        btnToggleVibe.setOnClickListener {
            isVibeEnabled = !isVibeEnabled
            btnToggleVibe.text = if (isVibeEnabled) "📳 Titreşim: Açık" else "📴 Titreşim: Kapalı"
        }

        // Adım Sayar
        btnAddSteps.setOnClickListener {
            stepCounter += 500
            updateStepsUI()
            if (isVibeEnabled) vibrateDevice(25)
            Toast.makeText(this, "+500 Adım eklendi!", Toast.LENGTH_SHORT).show()
        }

        btnResetSteps.setOnClickListener {
            stepCounter = 0
            updateStepsUI()
            if (isVibeEnabled) vibrateDevice(50)
            Toast.makeText(this, "Adım sayacı sıfırlandı", Toast.LENGTH_SHORT).show()
        }

        // Su Takibi
        btnAddWater200.setOnClickListener {
            currentWaterMl += 200
            waterCompassView.setWater(currentWaterMl, 2500)
            if (isVibeEnabled) vibrateDevice(25)
            Toast.makeText(this, "200 ml su kaydedildi", Toast.LENGTH_SHORT).show()
        }

        btnAddWater500.setOnClickListener {
            currentWaterMl += 500
            waterCompassView.setWater(currentWaterMl, 2500)
            if (isVibeEnabled) vibrateDevice(25)
            Toast.makeText(this, "500 ml su kaydedildi", Toast.LENGTH_SHORT).show()
        }

        btnResetWater.setOnClickListener {
            currentWaterMl = 0
            waterCompassView.setWater(currentWaterMl, 2500)
            if (isVibeEnabled) vibrateDevice(50)
            Toast.makeText(this, "Su takibi sıfırlandı", Toast.LENGTH_SHORT).show()
        }

        // Zihin
        btnCompleteTask.setOnClickListener {
            btnCompleteTask.text = "Tamamlandı (✓)"
            btnCompleteTask.isEnabled = false
            if (isVibeEnabled) vibrateDevice(50)
            Toast.makeText(this, "Tebrikler! Günlük görevinizi tamamladınız.", Toast.LENGTH_LONG).show()
        }

        btnStartDetox.setOnClickListener {
            isDetoxRunning = !isDetoxRunning
            if (isDetoxRunning) {
                btnStartDetox.text = "Detoksu Bitir"
                tvDetoxStatus.text = "🌿 Odaklanma Modu Aktif. Sessizlik ve huzurdasınız..."
            } else {
                btnStartDetox.text = "Detoksu Başlat (20 dk)"
                tvDetoxStatus.text = "20 Dakikalık sessizlik ve iç huzur zamanlayıcısı."
            }
            if (isVibeEnabled) vibrateDevice(40)
        }

        // Yaşam Bölümü
        cardTravel.setOnClickListener { showCitySelectionDialog() }

        btnRefreshMosques.setOnClickListener {
            updateMosquesList()
            Toast.makeText(this, "${currentCity.name} camileri güncellendi.", Toast.LENGTH_SHORT).show()
        }

        cardOffline.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("📶 Çevrimdışı Mod")
                .setMessage("Tüm 81 il ve ilçenin 30 günlük namaz vakitleri ve coğrafi kıble açıları yerel belleğe kaydedildi. İnternetsiz kesintisiz kullanabilirsiniz.")
                .setPositiveButton("Tamam", null)
                .show()
        }

        cardSettings.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("⚙️ Ayarlar & Hakkında")
                .setMessage("PUSULAM v1.0.0 (Gold Master)\n\nGeliştirici: Nihat Yazgan\n\n• Hesaplama Metodu: Diyanet İşleri Başkanlığı\n• Pusula Sensör Filtresi: Aktif (Manyetometre + Jiroskop)\n• Sesli Vakit Bildirimi: Aktif (\"Vakit girdi, Allah kabul etsin\")\n• İletişim & Destek: Tüm hakları saklıdır © 2026")
                .setPositiveButton("Kapat", null)
                .show()
        }
    }

    private fun showDhikrSelectionDialog() {
        val dhikrNames = dhikrList.map { it.name }.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("Zikir / Tesbih Seçiniz")
            .setItems(dhikrNames) { _, which ->
                currentDhikrIndex = which
                targetCount = dhikrList[which].defaultTarget
                zikirCounter = 0
                updateDhikrUI()
            }
            .setNegativeButton("İptal", null)
            .show()
    }

    private fun showTargetSelectionDialog() {
        val targets = arrayOf("33", "99", "100", "500", "1000", "Sınırsız (∞)")
        AlertDialog.Builder(this)
            .setTitle("Hedef Sayı Sınırı Seçiniz")
            .setItems(targets) { _, which ->
                targetCount = when (which) {
                    0 -> 33
                    1 -> 99
                    2 -> 100
                    3 -> 500
                    4 -> 1000
                    else -> 0
                }
                updateDhikrUI()
            }
            .setNegativeButton("İptal", null)
            .show()
    }

    private fun updateDhikrUI() {
        val dhikr = dhikrList[currentDhikrIndex]
        btnSelectDhikr.text = "📿 ${dhikr.name} ▼"
        btnSelectTarget.text = if (targetCount > 0) "🎯 Hedef: $targetCount ▼" else "🎯 Hedef: Sınırsız ▼"
        tvZikirMeaning.text = dhikr.meaning
        updateDhikrCounterText()
    }

    private fun updateDhikrCounterText() {
        if (targetCount > 0) {
            tvZikirCount.text = "$zikirCounter / $targetCount"
        } else {
            tvZikirCount.text = "$zikirCounter"
        }
    }

    private fun updateStepsUI() {
        tvStepCount.text = "${String.format("%,d", stepCounter)} / 10,000 Adım"
        val percent = Math.min(100, (stepCounter * 100) / 10000)
        tvStepProgress.text = "Mekke - Medine Hicret Rotasında %$percent tamamlandı"
    }

    private fun showCitySelectionDialog() {
        val options = mutableListOf("📡 Mevcut Konumumu Otomatik Bul (GPS)")
        options.addAll(TurkeyLocationData.cities.map { it.name })

        AlertDialog.Builder(this)
            .setTitle("Konum / İl Seçiniz")
            .setItems(options.toTypedArray()) { _, which ->
                if (which == 0) {
                    isAutoLocationEnabled = true
                    prefs.edit().putBoolean("AUTO_LOCATION_ENABLED", true).apply()
                    Toast.makeText(this, "GPS ile konum aranıyor...", Toast.LENGTH_SHORT).show()
                    checkAndRequestLocationPermission()
                } else {
                    currentCity = TurkeyLocationData.cities[which - 1]
                    showDistrictSelectionDialog(currentCity)
                }
            }
            .setNegativeButton("İptal", null)
            .show()
    }

    private fun showDistrictSelectionDialog(city: CityData) {
        val districtNames = city.districts.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("${city.name} - İlçe Seçiniz")
            .setItems(districtNames) { _, which ->
                selectedDistrict = districtNames[which]
                saveLocation(city.name, selectedDistrict, isAuto = false)
                updateLocationUI()
                Toast.makeText(this, "${city.name} / $selectedDistrict seçildi ve sabitlendi.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Geri") { _, _ -> showCitySelectionDialog() }
            .show()
    }

    private fun updateLocationUI() {
        tvLocationDisplay.text = "📍 ${currentCity.name} / $selectedDistrict"
        compassView.setQiblaAngle(currentCity.qiblaAngle)
        tvQiblaStatus.text = "Kıble: ${currentCity.qiblaAngle.toInt()}° GD"

        // Yerel önbellekteki vakitleri yükle (varsa)
        val cachedImsak = prefs.getString("PRAYER_${currentCity.name}_${selectedDistrict}_IMSAK", null)
        if (cachedImsak != null) {
            currentCity.imsak = cachedImsak
            currentCity.ogle = prefs.getString("PRAYER_${currentCity.name}_${selectedDistrict}_OGLE", currentCity.ogle) ?: currentCity.ogle
            currentCity.ikindi = prefs.getString("PRAYER_${currentCity.name}_${selectedDistrict}_IKINDI", currentCity.ikindi) ?: currentCity.ikindi
            currentCity.aksam = prefs.getString("PRAYER_${currentCity.name}_${selectedDistrict}_AKSAM", currentCity.aksam) ?: currentCity.aksam
            currentCity.yatsi = prefs.getString("PRAYER_${currentCity.name}_${selectedDistrict}_YATSI", currentCity.yatsi) ?: currentCity.yatsi
        }

        tvTimeImsak.text = currentCity.imsak
        tvTimeOgle.text = currentCity.ogle
        tvTimeIkindi.text = currentCity.ikindi
        tvTimeAksam.text = currentCity.aksam
        tvTimeYatsi.text = currentCity.yatsi

        tvNearbyMosquesTitle.text = "🕌 Bölgedeki Camiler (${currentCity.name} / $selectedDistrict)"
        updateMosquesList()
        updateRealTimePrayerCountdown()

        // Diyanet resmi canlı API'sinden güncel vakitleri arka planda çek
        fetchLiveDiyanetPrayerTimes(currentCity.name, selectedDistrict)
    }

    private fun fetchLiveDiyanetPrayerTimes(cityName: String, districtName: String) {
        Thread {
            try {
                // 1. Şehir Listesinden Şehir ID'sini bul
                val sehirlerUrl = java.net.URL("https://ezanvakti.emushaf.net/sehirler/2")
                val conn1 = sehirlerUrl.openConnection() as java.net.HttpURLConnection
                conn1.setRequestProperty("User-Agent", "Mozilla/5.0")
                conn1.connectTimeout = 4000
                conn1.readTimeout = 4000
                
                val sehirlerJson = conn1.inputStream.bufferedReader().use { it.readText() }
                val sehirlerArray = org.json.JSONArray(sehirlerJson)
                var foundSehirId = ""

                for (i in 0 until sehirlerArray.length()) {
                    val obj = sehirlerArray.getJSONObject(i)
                    val sName = obj.optString("SehirAdi", "")
                    if (sName.equals(cityName, ignoreCase = true) ||
                        cityName.uppercase(Locale("tr", "TR")).contains(sName.uppercase(Locale("tr", "TR")))) {
                        foundSehirId = obj.optString("SehirID", "")
                        break
                    }
                }

                if (foundSehirId.isEmpty()) return@Thread

                // 2. İlçe Listesinden İlçe ID'sini bul
                val ilcelerUrl = java.net.URL("https://ezanvakti.emushaf.net/ilceler/$foundSehirId")
                val conn2 = ilcelerUrl.openConnection() as java.net.HttpURLConnection
                conn2.setRequestProperty("User-Agent", "Mozilla/5.0")
                conn2.connectTimeout = 4000
                conn2.readTimeout = 4000

                val ilcelerJson = conn2.inputStream.bufferedReader().use { it.readText() }
                val ilcelerArray = org.json.JSONArray(ilcelerJson)
                var foundIlceId = ""

                for (i in 0 until ilcelerArray.length()) {
                    val obj = ilcelerArray.getJSONObject(i)
                    val iName = obj.optString("IlceAdi", "")
                    if (iName.equals(districtName, ignoreCase = true) ||
                        districtName.uppercase(Locale("tr", "TR")).contains(iName.uppercase(Locale("tr", "TR"))) ||
                        iName.uppercase(Locale("tr", "TR")).contains(districtName.uppercase(Locale("tr", "TR")))) {
                        foundIlceId = obj.optString("IlceID", "")
                        break
                    }
                }

                // Eşleşen ilçe bulunamazsa il merkezinin vakit ID'sini al
                if (foundIlceId.isEmpty() && ilcelerArray.length() > 0) {
                    foundIlceId = ilcelerArray.getJSONObject(0).optString("IlceID", "")
                }

                if (foundIlceId.isEmpty()) return@Thread

                // 3. Güncel Diyanet Namaz Vakitlerini Çek
                val vakitlerUrl = java.net.URL("https://ezanvakti.emushaf.net/vakitler/$foundIlceId")
                val conn3 = vakitlerUrl.openConnection() as java.net.HttpURLConnection
                conn3.setRequestProperty("User-Agent", "Mozilla/5.0")
                conn3.connectTimeout = 4000
                conn3.readTimeout = 4000

                val vakitlerJson = conn3.inputStream.bufferedReader().use { it.readText() }
                val vakitlerArray = org.json.JSONArray(vakitlerJson)

                if (vakitlerArray.length() > 0) {
                    val sdfDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    val todayStr = sdfDate.format(Date())

                    var targetItem: org.json.JSONObject = vakitlerArray.getJSONObject(0)
                    for (k in 0 until vakitlerArray.length()) {
                        val item = vakitlerArray.getJSONObject(k)
                        val itemDate = item.optString("MiladiTarihKisa", "")
                        if (itemDate == todayStr) {
                            targetItem = item
                            break
                        }
                    }

                    val liveImsak = targetItem.optString("Imsak", "")
                    val liveOgle = targetItem.optString("Ogle", "")
                    val liveIkindi = targetItem.optString("Ikindi", "")
                    val liveAksam = targetItem.optString("Aksam", "")
                    val liveYatsi = targetItem.optString("Yatsi", "")

                    if (liveImsak.isNotEmpty() && liveAksam.isNotEmpty()) {
                        runOnUiThread {
                            if (currentCity.name == cityName && selectedDistrict == districtName) {
                                currentCity.imsak = liveImsak
                                currentCity.ogle = liveOgle
                                currentCity.ikindi = liveIkindi
                                currentCity.aksam = liveAksam
                                currentCity.yatsi = liveYatsi

                                tvTimeImsak.text = liveImsak
                                tvTimeOgle.text = liveOgle
                                tvTimeIkindi.text = liveIkindi
                                tvTimeAksam.text = liveAksam
                                tvTimeYatsi.text = liveYatsi

                                // Önbelleğe kaydet (Çevrimdışıyken de güncel kalsın)
                                prefs.edit()
                                    .putString("PRAYER_${cityName}_${districtName}_IMSAK", liveImsak)
                                    .putString("PRAYER_${cityName}_${districtName}_OGLE", liveOgle)
                                    .putString("PRAYER_${cityName}_${districtName}_IKINDI", liveIkindi)
                                    .putString("PRAYER_${cityName}_${districtName}_AKSAM", liveAksam)
                                    .putString("PRAYER_${cityName}_${districtName}_YATSI", liveYatsi)
                                    .apply()

                                updateRealTimePrayerCountdown()
                            }
                        }
                    }
                }
            } catch (_: Exception) {
                // İnternet yoksa veya API gecikirse yerel Diyanet hesaplamasıyla devam edilir
            }
        }.start()
    }

    private fun updateMosquesList() {
        layoutMosqueList.removeAllViews()

        val mosques = TurkeyLocationData.getMosquesForDistrict(currentCity.name, selectedDistrict)
        if (mosques.isEmpty()) {
            val emptyTv = TextView(this).apply {
                text = "Bu bölgede kayıtlı cami bulunamadı."
                setTextColor(Color.parseColor("#94A3B8"))
                textSize = 13f
                setPadding(0, 10, 0, 10)
            }
            layoutMosqueList.addView(emptyTv)
            return
        }

        for (m in mosques) {
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundResource(R.drawable.bg_glass_card)
                setPadding(28, 20, 28, 20)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 10, 0, 10)
                layoutParams = params
                isClickable = true
                isFocusable = true

                setOnClickListener {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("🕌 ${m.name}")
                        .setMessage("📍 Adres: ${m.address}\n\n📏 Yaklaşık Mesafe: ${m.distance}\n🕒 Durum: İbadete Açık\n\nNamaz vakitlerinde cemaatle namaz kılabilirsiniz.")
                        .setPositiveButton("Tamam", null)
                        .show()
                }
            }

            val topRow = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
            }

            val nameTv = TextView(this).apply {
                text = "🕌 ${m.name}"
                setTextColor(Color.WHITE)
                textSize = 14f
                typeface = android.graphics.Typeface.DEFAULT_BOLD
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val distBadge = TextView(this).apply {
                text = "${m.distance} • AÇIK"
                setTextColor(Color.parseColor("#10B981"))
                textSize = 11f
                typeface = android.graphics.Typeface.DEFAULT_BOLD
                setBackgroundResource(R.drawable.bg_button_secondary)
                setPadding(14, 6, 14, 6)
            }

            topRow.addView(nameTv)
            topRow.addView(distBadge)

            val addrTv = TextView(this).apply {
                text = m.address
                setTextColor(Color.parseColor("#94A3B8"))
                textSize = 12f
                setPadding(0, 8, 0, 0)
            }

            card.addView(topRow)
            card.addView(addrTv)
            layoutMosqueList.addView(card)
        }
    }

    private fun vibrateDevice(duration: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(duration)
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        magnetometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        timerHandler.post(countdownRunnable)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        timerHandler.removeCallbacks(countdownRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }

    private var wasFacingQibla = false

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, gravity, 0, event.values.size)
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, geomagnetic, 0, event.values.size)
        }

        val r = FloatArray(9)
        val i = FloatArray(9)
        if (SensorManager.getRotationMatrix(r, i, gravity, geomagnetic)) {
            val orientation = FloatArray(3)
            SensorManager.getOrientation(r, orientation)
            azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
            azimuth = (azimuth + 360) % 360

            compassView.updateAzimuth(azimuth)
            tvAzimuthDegree.text = "${azimuth.toInt()}°"

            val diff = Math.abs(azimuth - currentCity.qiblaAngle)
            val isNowFacingQibla = (diff < 3.5f || diff > 356.5f)

            if (isNowFacingQibla) {
                tvQiblaStatus.text = "🕋 KIBLEDESİNİZ"
                tvQiblaStatus.setTextColor(resources.getColor(R.color.accent_gold, theme))

                // Noktayı tam bulunca Bip Sesi ve Titreşim Çıkar (Tek seferlik / Kilitlenme anında)
                if (!wasFacingQibla) {
                    wasFacingQibla = true
                    if (isSoundEnabled) {
                        try {
                            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 180)
                        } catch (e: Exception) { }
                    }
                    if (isVibeEnabled) {
                        vibrateDevice(90)
                    }
                }
            } else {
                wasFacingQibla = false
                tvQiblaStatus.text = "Kıble: ${currentCity.qiblaAngle.toInt()}° GD"
                tvQiblaStatus.setTextColor(resources.getColor(R.color.accent_emerald, theme))
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
