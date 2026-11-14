# EXAMORA — SMK MANDIRI HAURGEULIS

Starter project Android untuk dirakit dari HP menggunakan GitHub dan GitHub Actions.

## Identitas
- Nama aplikasi: EXAMORA
- Sekolah: SMK MANDIRI HAURGEULIS
- Package: `id.sch.mandirihaurgeulis.examora`
- Bahasa: Kotlin
- UI: Jetpack Compose
- Minimum Android: Android 7.0 (API 24)

## Fitur prototipe saat ini
- Pilihan akses Siswa dan Admin (belum login dengan akun sungguhan).
- Portal siswa dengan pemilihan kelas 10, 11, dan 12.
- Pemisahan Ujian Biasa dan Produktif.
- Pilihan jurusan TKR dan TKJ untuk ujian Produktif.
- Panel admin demo untuk menambah dan menghapus daftar ujian.
- Workflow GitHub Actions untuk membangun APK debug dan menyimpan hasilnya sebagai artifact.

## Batasan penting
Ini masih **prototipe antarmuka**. Data ujian hanya tersimpan sementara di memori aplikasi dan kembali ke data contoh saat aplikasi ditutup. Belum ada database online, akun/login aman, editor bank soal, pengerjaan soal sebenarnya, timer ujian, autosave, penilaian, atau sinkronisasi antar-HP. Jangan gunakan untuk ujian sekolah sungguhan sebelum backend, keamanan, dan fitur ujian selesai dibuat dan diuji.

## Cara merakit dari HP Android
1. Unduh dan ekstrak ZIP project ini menggunakan aplikasi pengelola file/arsip di HP.
2. Buka https://github.com lalu masuk ke akun GitHub.
3. Buat repository baru bernama `examora-smk-mandiri-haurgeulis` (Public atau Private).
4. Unggah semua isi folder project ke repository, bukan folder ZIP-nya. Pastikan file `settings.gradle.kts`, `build.gradle.kts`, folder `app`, dan folder `.github` ikut masuk.
5. Jika aplikasi pengelola file tidak menampilkan folder tersembunyi `.github`, buat workflow secara manual di GitHub:
   - Pilih **Add file → Create new file**.
   - Isi nama file: `.github/workflows/build-apk.yml`.
   - Salin isi file `build-apk.yml` dari ZIP project.
   - Commit ke branch `main`.
6. Buka tab **Actions** di repository. Jika diminta, aktifkan GitHub Actions.
7. Pilih workflow **Build EXAMORA APK**, tekan **Run workflow**.
8. Tunggu sampai proses selesai dengan status hijau.
9. Buka hasil workflow tersebut, cari bagian **Artifacts**, lalu unduh `examora-debug-apk`.
10. Ekstrak file artifact ZIP, lalu instal `app-debug.apk` di HP. Jika Android meminta izin, izinkan instalasi dari sumber yang kamu gunakan.

## Build otomatis
Workflow berjalan saat ada push ke branch `main`, dan bisa dijalankan manual dari tab Actions.

## Catatan
APK dari workflow ini adalah APK debug untuk uji coba. Untuk distribusi resmi, perlu konfigurasi signing/release dan pengujian lebih lanjut.
