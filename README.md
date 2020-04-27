# FunderMobileApps
<i>English</i>


<i>Bahasa</i>

Aplikasi Funder adalah 
aplikasi yang menghubungkan antara para event organizer dengan para funding dengan cara melelang sponsorhip dengan bidder proposal

Funder memiliki fitur bidding auction, chat, trackrecord

Aplikasi ini menggunakan Firebase Firestore, Firebase Cloud Messaging, dan Firebase Storage.

- Firebase Firestore digunakan untuk menyimpan data-data para user dari event organizer
- Firebase Cloud Messaging digunakan untuk memunculkan notifikasi secara realtime
- Firebase Storage digunakan untuk menyimpan file-file yang digunakan pada aplikasi Funder

Untuk dapat membuild aplikasi ini yang harus dilakukan adalah:
  1. Menginstall git pada komputer, git bisa di download disini https://git-scm.com/downloads
  2. Buka terminal anda kemudian pilih folder dimana project ini akan disimpan kemudian ketik perintah git clone https://github.com/Lukmannudin/FunderMobileApps.git
  3. Pastikan internet anda tidak lambat kemudian buka project hasil dari perintah ke 2 menggunakan android studio
  4. Tunggu proses compiling dan download resource selesai. Untuk memastikan tidak ada error yang banyak jangan di update versi gradlenya.
  5. Ganti branch project pada pojok kanan bawah dari master menjadi development
  5. selesai
  
Aplikasi ini menggunakan 2 tempat penyimpanan data yaitu server funder dan firebase. Namun jika server mati maka untuk bisa login
adalah buka file LoginPresenter.kt kemudian pada kode berikut: 
        if (USER_TYPE != SharedPreferenceUtils.EMPTY) {
            ....
        }
Rubah menjadi 
        if (USER_TYPE == SharedPreferenceUtils.EMPTY) {
            ....
        }
