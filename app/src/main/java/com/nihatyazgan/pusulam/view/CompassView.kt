package com.nihatyazgan.pusulam.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class CompassView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var azimuth = 0f
    private var qiblaAngle = 148f

    // Lüks Zümrüt & Altın Renk Paleti
    private val colorGold = Color.parseColor("#F59E0B")
    private val colorGoldBright = Color.parseColor("#FCD34D")
    private val colorGoldLight = Color.parseColor("#FEF3C7")
    private val colorEmeraldDeep = Color.parseColor("#064E3B")
    private val colorEmeraldMedium = Color.parseColor("#047857")
    private val colorEmeraldBright = Color.parseColor("#10B981")
    private val colorEmeraldGlow = Color.parseColor("#34D399")
    private val colorBgDark = Color.parseColor("#06121E")

    private val outerBezelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#78350F") // Antik Altın/Bronz Dış Çerçeve
        strokeWidth = 3f
    }

    private val goldRimPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = colorGold
        strokeWidth = 7f
    }

    private val emeraldRingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = colorEmeraldMedium
        strokeWidth = 18f
    }

    private val innerBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = colorBgDark
    }

    private val sunburstPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#B45309")
    }

    private val tickPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val cardinalTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 30f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }

    private val subCardinalPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = colorGoldLight
        textSize = 18f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    }

    private val degreeNumberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#94A3B8")
        textSize = 18f
        textAlign = Paint.Align.CENTER
    }

    fun updateAzimuth(newAzimuth: Float) {
        azimuth = newAzimuth
        invalidate()
    }

    fun setQiblaAngle(angle: Float) {
        qiblaAngle = angle
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f
        val radius = (Math.min(width, height) / 2f) - 20f

        // 1. Dış Altın İşlemeli Çember
        canvas.drawCircle(cx, cy, radius, outerBezelPaint)
        canvas.drawCircle(cx, cy, radius - 6f, goldRimPaint)

        // 2. Zümrüt Kristal Halka
        canvas.drawCircle(cx, cy, radius - 18f, emeraldRingPaint)

        // 3. Koyu Gece Mavisi İç Zemin
        canvas.drawCircle(cx, cy, radius - 27f, innerBackgroundPaint)

        // ================= DÖNEN KADRAN =================
        canvas.save()
        canvas.rotate(-azimuth, cx, cy)

        // Merkez İslami Geometrik Güneş Rozeti (Sunburst)
        val sunRayCount = 16
        val sunInnerR = 18f
        val sunOuterR = radius - 80f
        for (j in 0 until sunRayCount) {
            val angle = j * (360f / sunRayCount)
            val rad = Math.toRadians(angle.toDouble())
            val radNext = Math.toRadians((angle + 360f / (sunRayCount * 2)).toDouble())

            val p = Path().apply {
                moveTo(cx, cy)
                lineTo((cx + sunInnerR * sin(rad)).toFloat(), (cy - sunInnerR * cos(rad)).toFloat())
                lineTo((cx + sunOuterR * sin(radNext)).toFloat(), (cy - sunOuterR * cos(radNext)).toFloat())
                close()
            }
            sunburstPaint.color = if (j % 2 == 0) Color.parseColor("#451A03") else Color.parseColor("#78350F")
            canvas.drawPath(p, sunburstPaint)
        }

        // Kadran Derece Çizgileri & Yönler (0: N/K, 90: E/D, 180: S/G, 270: W/B)
        for (i in 0 until 360 step 5) {
            val rad = Math.toRadians(i.toDouble())
            val isCardinal = i % 90 == 0
            val isMajor = i % 30 == 0
            val isMinor = i % 10 == 0

            val tickLen = when {
                isCardinal -> 22f
                isMajor -> 16f
                isMinor -> 10f
                else -> 6f
            }

            val rStart = radius - 10f
            val rEnd = rStart - tickLen

            val startX = (cx + rStart * sin(rad)).toFloat()
            val startY = (cy - rStart * cos(rad)).toFloat()
            val endX = (cx + rEnd * sin(rad)).toFloat()
            val endY = (cy - rEnd * cos(rad)).toFloat()

            tickPaint.strokeWidth = when {
                isCardinal -> 4.5f
                isMajor -> 3f
                isMinor -> 2f
                else -> 1f
            }

            tickPaint.color = when {
                i == 0 -> colorEmeraldBright
                isCardinal -> colorGoldBright
                isMajor -> Color.WHITE
                else -> Color.parseColor("#78716C")
            }

            canvas.drawLine(startX, startY, endX, endY, tickPaint)

            // Yön Harfleri (N/Kuzey, E/Doğu, S/Güney, W/Batı)
            if (isCardinal) {
                val label = when (i) {
                    0 -> "N"
                    90 -> "E"
                    180 -> "S"
                    270 -> "W"
                    else -> ""
                }
                val textRadius = radius - 46f
                val tx = (cx + textRadius * sin(rad)).toFloat()
                val ty = (cy - textRadius * cos(rad)).toFloat()

                canvas.save()
                canvas.translate(tx, ty)
                canvas.rotate(i.toFloat()) // Harfi kadranın dışına doğru okunabilir şekilde hizala
                cardinalTextPaint.color = if (i == 0) colorEmeraldBright else colorGoldBright
                canvas.drawText(label, 0f, 10f, cardinalTextPaint)
                canvas.restore()
            } else if (i == 45 || i == 135 || i == 225 || i == 315) {
                val subLabel = when (i) {
                    45 -> "NE"
                    135 -> "SE"
                    225 -> "SW"
                    315 -> "NW"
                    else -> ""
                }
                val textRadius = radius - 44f
                val tx = (cx + textRadius * sin(rad)).toFloat()
                val ty = (cy - textRadius * cos(rad)).toFloat()

                canvas.save()
                canvas.translate(tx, ty)
                canvas.rotate(i.toFloat())
                canvas.drawText(subLabel, 0f, 6f, subCardinalPaint)
                canvas.restore()
            }
        }

        // ================= KADRAN ÜZERİNDEKİ KIBLE İŞARETİ (KENARDAKİ KABE RESMİ) =================
        val qiblaRad = Math.toRadians(qiblaAngle.toDouble())
        val qiblaR = radius - 16f // Çemberin tam kenarında / halkasında
        val qiblaX = (cx + qiblaR * sin(qiblaRad)).toFloat()
        val qiblaY = (cy - qiblaR * cos(qiblaRad)).toFloat()

        canvas.save()
        canvas.translate(qiblaX, qiblaY)
        canvas.rotate(qiblaAngle)

        // Altın Parıltı Halesi
        val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.parseColor("#88F59E0B")
        }
        canvas.drawCircle(0f, 0f, 22f, glowPaint)

        // Kabe Rozeti Altın Dış Halka
        val badgeBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = colorGoldBright
            strokeWidth = 2.5f
        }
        canvas.drawCircle(0f, 0f, 18f, badgeBorderPaint)

        // Kabe Gövdesi (Siyah Küp)
        val kabeBodyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.parseColor("#0F172A")
        }
        val kabeRect = RectF(-12f, -12f, 12f, 12f)
        canvas.drawRoundRect(kabeRect, 3f, 3f, kabeBodyPaint)

        // Kabe Kisve Altın Kemeri (Hatt-ı Şerif)
        val kabeBeltPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = colorGoldBright
        }
        canvas.drawRect(-12f, -5f, 12f, -2f, kabeBeltPaint)

        // Kabe Altın Kapısı (Bab-ı Kabe)
        val doorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = colorGoldLight
            style = Paint.Style.FILL
        }
        canvas.drawRect(-3f, 1f, 3f, 10f, doorPaint)

        // Altın Çerçeve
        val kabeBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = colorGoldBright
            strokeWidth = 1.5f
        }
        canvas.drawRoundRect(kabeRect, 3f, 3f, kabeBorder)

        canvas.restore()

        // Kadranı kapat
        canvas.restore()

        // ================= MERKEZDEN KIBLEYE DOĞRU UZANAN LÜKS ALTIN KIBLE İBRESİ =================
        val relativeQiblaAngle = (qiblaAngle - azimuth + 360f) % 360f
        val relRad = Math.toRadians(relativeQiblaAngle.toDouble())

        val isFacingQibla = Math.abs(relativeQiblaAngle) < 3.5f || Math.abs(relativeQiblaAngle - 360f) < 3.5f

        val qiblaNeedleLen = radius - 45f
        val qiblaTipX = (cx + qiblaNeedleLen * sin(relRad)).toFloat()
        val qiblaTipY = (cy - qiblaNeedleLen * cos(relRad)).toFloat()

        // Kıble İbresi Gövdesi
        val qiblaNeedlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = if (isFacingQibla) colorEmeraldBright else colorGoldBright
            strokeWidth = if (isFacingQibla) 6f else 4.5f
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawLine(cx, cy, qiblaTipX, qiblaTipY, qiblaNeedlePaint)

        // Kıble Ok Başı (Kabe'ye Kilitlenen Parlak Altın / Zümrüt Ok)
        canvas.save()
        canvas.translate(qiblaTipX, qiblaTipY)
        canvas.rotate(relativeQiblaAngle)

        val arrowHeadPath = Path().apply {
            moveTo(0f, -24f)
            lineTo(-13f, 6f)
            lineTo(0f, 0f)
            lineTo(13f, 6f)
            close()
        }
        val arrowFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = if (isFacingQibla) colorEmeraldBright else colorGoldBright
        }
        canvas.drawPath(arrowHeadPath, arrowFillPaint)
        canvas.restore()

        // ================= TELEFON SABİT KUZEY/TEPE GÖSTERGESİ =================
        val topPointerPath = Path().apply {
            moveTo(cx, cy - radius + 6f)
            lineTo(cx - 10f, cy - radius - 14f)
            lineTo(cx + 10f, cy - radius - 14f)
            close()
        }
        val topPointerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = if (isFacingQibla) colorEmeraldBright else colorGoldBright
        }
        canvas.drawPath(topPointerPath, topPointerPaint)

        // İbre Merkez Altın & Zümrüt Mücevher Başlığı
        canvas.drawCircle(cx, cy, 20f, outerBezelPaint)
        canvas.drawCircle(cx, cy, 15f, goldRimPaint)
        canvas.drawCircle(cx, cy, 9f, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = if (isFacingQibla) colorEmeraldBright else colorEmeraldDeep
            style = Paint.Style.FILL
        })
    }
}
