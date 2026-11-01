<div align="center">

# دوبله پارسی — Doble Parsi V1.1.0

**دوبلهٔ زندهٔ صدای گوشی با هوش مصنوعی، با خروجی صوتی و زیرنویس هم‌نام ویدئو**

_AI live dubbing for anything playing on your phone, with audio and subtitles named after the video_

Android 10+ (API 29) · فارسی / English · ساخته‌شده توسط [QW-AI-Code](https://github.com/QW-AI-Code)

</div>

---

<div dir="rtl" align="right">

## فارسی

### کدام فایل را دانلود کنم؟

| فایل | برای چه |
|---|---|
| `DobleParsi-1.1.0-universal.apk` | همهٔ گوشی‌های اندروید ۱۰ و بالاتر، با هر معماری (arm64-v8a، armeabi-v7a، x86_64) |

فقط یک APK منتشر می‌شود، چون این اپ هیچ کتابخانهٔ بومی (`.so`) ندارد و تفکیک بر اساس معماری چیزی برای جدا کردن نداشت. چک‌سام‌ها در `SHA256SUMS.txt` همین ریلیز هستند.

### تازه‌های نسخهٔ ۱.۱.۰

- **قطع واقعی صدای اصلی ویدئو:** مشکل اصلی این بود که صدای اصلی با «ducking» کم یا قطع نمی‌شد و دوبله درست شنیده نمی‌شد. حالا دکمهٔ قطع، ولوم رسانهٔ گوشی را واقعاً صفر می‌کند و صدای دوبله روی یک کانال جدا پخش می‌شود تا همراه صدای اصلی ساکت نشود. با پایان دوبله، ولوم به حالت قبلی برمی‌گردد.
- **فقط دو دکمهٔ کنترل صدا، مثل افزونهٔ کروم:** در بخش «خروجی‌ها» یک دکمه برای قطع/وصل صدای اصلی ویدئو و یک دکمه برای قطع/وصل صدای دوبله وجود دارد. هر دو وسط جلسه هم فوراً اثر می‌کنند.
- **اسلایدر بلندی زیر هر دکمه:** زیر دکمهٔ صدای اصلی و دکمهٔ صدای دوبله یک اسلایدر بلندی هست تا کاربر صدا را به اندازهٔ دلخواه کم یا زیاد کند.
- **حذف گزینه‌های گیج‌کننده:** سوئیچ‌های «پخش زندهٔ دوبله»، «کم‌کردن صدای اصلی هنگام دوبله»، «شروع دوبله همزمان با صدای اصلی»، «پایین نگه‌داشتن صدای اصلی در تمام جلسه» و اسلایدر «بلندی صدای دوبله» برداشته شد.
- **تست کلید و اتصال:** در بخش کلید API دکمهٔ «تست کلید و اتصال» اضافه شد. نتیجه می‌گوید کلید سالم است یا نه، سهمیه پر است یا نه، مدل در دسترس است یا نه و اینترنت وصل است یا نه.
- **اصلاح بیلد گیت‌هاب:** مرحلهٔ نصب Android SDK در ورک‌فلو دیگر بستهٔ منسوخ `tools` را نمی‌خواهد و فقط `platform-tools` نصب می‌شود.

### آموزش سریع

1. APK را نصب کن (نصب از منابع نامشخص را روشن کن).
2. اپ را باز کن، کلید API جِمینای را از [Google AI Studio](https://aistudio.google.com/apikey) بگیر و وارد کن، سپس با «تست کلید و اتصال» سالم بودنش را بررسی کن. کلید فقط روی همین گوشی و در حافظهٔ خصوصی اپ می‌ماند.
3. زبان دوبله را انتخاب کن (پیش‌فرض فارسی، ۳۱ زبان).
4. **شروع دوبله** را بزن و اجازه‌ها را بده: **میکروفون** و **ضبط صدای دستگاه** («Start now»). برای دکمهٔ شناور هم **نمایش روی برنامه‌های دیگر** لازم است.
5. در یوتیوب، تلگرام یا مرورگر ویدئو را پخش کن؛ دوبله همان لحظه پخش می‌شود. برای شنیدن دوبله بدون صدای اصلی، دکمهٔ «قطع صدای اصلی ویدئو» را بزن.
6. برای پایان، دکمهٔ شناور، **پایان** در اعلان یا **پایان دوبله** در خود اپ را بزن.
7. خروجی‌ها در `Download/Doble Parsi/<نام ویدئو>/` ذخیره می‌شوند.

> برای این‌که فایل‌ها **هم‌نام ویدئو** ذخیره شوند، یک بار دسترسی **«اعلان‌ها»** را برای دوبله پارسی روشن کن؛ نام ویدئوی درحال‌پخش در اندروید فقط از این راه خوانده می‌شود.

### دسترسی‌ها

| دسترسی | برای چه |
|---|---|
| میکروفون | شرط سیستم برای ضبط صدای پخش‌شده؛ صدای میکروفون تو ضبط و ارسال نمی‌شود |
| ضبط صدا/تصویر دستگاه | گرفتن صدای درحال‌پخش گوشی (MediaProjection) |
| اعلان | کنترل جلسه در نوار اعلان |
| نمایش روی برنامه‌های دیگر (اختیاری) | دکمهٔ شناور |
| دسترسی اعلان‌ها (اختیاری) | خواندن نام ویدئو برای نام‌گذاری فایل‌ها |

### قابلیت‌های نسخهٔ ۱.۰.۰ که همچنان هست

- **دکمهٔ شناور حرفه‌ای** با حلقهٔ گرادیان، تایمر ضبط دوبله (دقیقه:ثانیه) و پنج میله که با بلندی واقعی صدای دوبله حرکت می‌کنند. بعد از پایان دوبله سرِ جایش می‌ماند و لمس طولانی آن را تا شروع بعدی حذف می‌کند.
- **تنظیمات دکمه:** ماندن بعد از پایان، نمایش تایمر، کم‌رنگ شدن، جمع شدن در لبهٔ صفحه، جابه‌جایی خودکار به نزدیک‌ترین لبه، حالت هوشمند و اندازهٔ دکمه.
- **شروع همزمان دوبله:** بافر کوتاه و تاخیر عمدی صفر است؛ در تنظیمات پیشرفته می‌توان تاخیر را دستی تنظیم کرد. سهم صدای اصلی در فایل خروجی هم قابل تنظیم است.
- **مانیتور مصرف توکن API جِمینای:** مصرف امروز، ورودی/خروجی، توکن صوتی، سقف روزانه و باقی‌مانده، زمان بازنشانی، خطای سهمیهٔ ۴۲۹ با زمان تلاش مجدد و تاریخچهٔ روزهای قبل. گوگل API رسمی برای «باقی‌ماندهٔ سهمیه» ندارد، پس عدد تقریبی است و مرجع، داشبورد گوگل است.
- **رابط کاربری دارک سورمه‌ای** با آیکون رسمی برنامه، سوییچ کامل فارسی/انگلیسی و اعلان «در حال ذخیره» که دیگر فریز نمی‌شود.
- **نام فایل‌ها هم‌نام ویدئو** و **امضای ثابت** با کیستور اختصاصی (راهنما در ردمی) تا آپدیت‌ها روی نسخهٔ قبلی نصب شوند.
- **کارت سازنده و کپی‌رایت** با لینک [github.com/QW-AI-Code](https://github.com/QW-AI-Code).

### محدودیت‌ها

اندروید ۱۰ و بالاتر. تماس صوتی/تصویری (`VOICE_COMMUNICATION`) و اپ‌های DRM مثل نتفلیکس طبق تصمیم سیستم قابل ضبط نیستند. خروجی صوتی `m4a/AAC` است. قطع صدای اصلی از ولوم رسانهٔ گوشی استفاده می‌کند؛ هنگام دوبله ولوم رسانه را دستی تغییر ندهید.

### امنیت

ترافیک فقط با TLS و فقط به `generativelanguage.googleapis.com` است. تلمتری، آنالیتیکس و تبلیغ وجود ندارد. کلید API در حافظهٔ خصوصی اپ می‌ماند و در بکاپ نیست. کد بومی و SDK ثالث (به‌جز okhttp و Compose) وجود ندارد. ممیزی کامل در ردمی.

</div>

---

## English

### Which APK?

| File | For |
|---|---|
| `DobleParsi-1.1.0-universal.apk` | every Android 10+ phone, any ABI (arm64-v8a, armeabi-v7a, x86_64) |

Only one APK is published, because the app ships no native libraries (`.so`) and ABI splits had nothing to strip. Checksums are in `SHA256SUMS.txt` of this release.

### New in 1.1.0

- **Real mute of the original video sound:** the original used to stay audible because audio-focus ducking was ignored by most players, so the dub could not be heard properly. The mute button now sets the phone's media volume to 0, and the dub plays on its own stream so it is not silenced together with the original. Your volume is restored when dubbing ends.
- **Only two volume buttons, like the Chrome extension:** the Outputs section has one button to mute/unmute the original video sound and one to mute/unmute the dub voice. Both take effect instantly, even mid-session.
- **A volume slider under each button:** the original video sound and the dub voice each have a slider so the level can be set exactly.
- **Confusing options removed:** the "Play the dub live", "Duck original audio while dubbing", "Start the dub in sync with the original" and "Hold the original down for the whole session" switches and the dub volume slider are gone.
- **Test the key and connection:** a new button in the API key section tells you whether the key works, the quota is exhausted, the model is available and the network is reachable.
- **GitHub build fix:** the Android SDK setup step in the workflow no longer asks for the obsolete `tools` package and installs only `platform-tools`.

### Quick start

1. Install the APK (allow installs from unknown sources).
2. Open the app, paste a Gemini API key from [Google AI Studio](https://aistudio.google.com/apikey) and check it with "Test the key and connection". The key never leaves the device.
3. Pick the dub language (Persian by default, 31 available).
4. Hit **Start dubbing** and grant **microphone** + **device audio capture** ("Start now"); also **display over other apps** if you want the floating bubble.
5. Play something in YouTube/Telegram/a browser. The dub plays in real time. To hear the dub without the original, tap "Mute original video sound".
6. Stop from the bubble, the notification, or **Stop dubbing** in the app.
7. Output lands in `Download/Doble Parsi/<video name>/`.

> For files **named after the video**, turn on **notification access** once: on Android that is the only way to read the title of what is playing.

### Permissions

| Permission | Why |
|---|---|
| Microphone | the OS gate for playback capture; your mic audio is never recorded or sent |
| Device audio/screen capture | taps the phone playback (MediaProjection) |
| Notifications | session control in the shade |
| Display over other apps (optional) | floating bubble |
| Notification listener (optional) | reads the media title to name files |

### Still here from 1.0.0

- **Professional floating control** with a gradient ring, a dub timer (mm:ss) and five bars that move with the real dub loudness. It stays after a dub stops and a long press removes it until the next start.
- **Bubble settings:** keep after a session, show the timer, idle fade, collapse into an edge tab, auto-move to the nearest edge, smart mode and bubble size.
- **Synchronized start:** a short buffer and zero deliberate delay; the delay can be adjusted under Advanced, and so can the original's share in the saved file.
- **Gemini API token monitor:** today's usage, input/output, audio tokens, daily budget and remaining, reset countdown, 429 quota errors with their retry delay and per-day history. Google publishes no endpoint for "remaining quota", so the figure is an estimate and Google's dashboard stays the authority.
- **Navy dark UI** with the official app icon, a full Persian/English switch and a "Saving output…" notification that no longer freezes.
- **Files named after the video** and **stable signing** with your own keystore (guide in the README) so updates install over the previous build.
- **Developer & copyright card** linking to [github.com/QW-AI-Code](https://github.com/QW-AI-Code).

### Limits

Android 10+. Voice/video calls (`VOICE_COMMUNICATION`) and DRM apps such as Netflix cannot be captured — an OS decision. Audio output is `m4a/AAC`. Muting the original uses the phone's media volume, so avoid changing the media volume by hand while dubbing.

### Security

TLS only, to `generativelanguage.googleapis.com` only. No telemetry, analytics or ads. The API key lives in app-private storage and is excluded from backups. No native code, no third-party SDKs beyond okhttp and Compose. Full audit in the README.

---

© 2026 [QW-AI-Code](https://github.com/QW-AI-Code)
.