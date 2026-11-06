@echo off
REM Put intro.mp4 next to this file, then run it from a folder containing FFmpeg.
mkdir src\main\resources\assets\introanim\textures\intro 2>nul
ffmpeg -i intro.mp4 -vf "scale=1920:1080:force_original_aspect_ratio=decrease,pad=1920:1080:(ow-iw)/2:(oh-ih)/2,fps=30" -start_number 1 src\main\resources\assets\introanim\textures\intro\frame_%%04d.png
pause
