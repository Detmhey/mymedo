МОД INTRO ANIMATION — Minecraft Java 1.20.1 Forge

Что делает:
- При первом открытии главного меню запускает вашу заставку.
- Одновременно проигрывает intro.ogg.
- После окончания возвращает обычное главное меню.

В папке уже лежат:
- исходный Java-код;
- ваша музыка intro.ogg;
- кадры, извлечённые из intro.mp4.

Для сборки нужен Forge MDK/Gradle для Minecraft 1.20.1 и Java 17.
1. Откройте папку проекта в IntelliJ IDEA.
2. Установите Java 17.
3. Выполните Gradle-задачу build.
4. Готовый JAR появится в build/libs.
5. Поместите JAR в папку .minecraft/mods вместе с Forge 1.20.1.

Важно: видео преобразовано в 224 PNG-кадра с частотой 3 кадра в секунду,
чтобы его можно было показывать внутри Minecraft как анимацию.

ОБНОВЛЕНИЕ FULL HD 30 FPS
--------------------------
Видео: 74.366667 секунд, 30 FPS, ожидаемое число кадров: 2231.

Перед сборкой запусти convert_video.bat (нужен установленный FFmpeg) рядом с intro.mp4.
Команда:
ffmpeg -i intro.mp4 -vf "scale=1920:1080:force_original_aspect_ratio=decrease,pad=1920:1080:(ow-iw)/2:(oh-ih)/2,fps=30" -start_number 1 src\main\resources\assets\introanim\textures\intro\frame_%%04d.png

Код IntroScreen загружает только текущий кадр через TextureManager и освобождает предыдущую текстуру через release().
Звук intro.ogg воспроизводится как streamed sound одновременно с запуском заставки.
