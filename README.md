# FunderMobileApps
<b><i>English</i></b><br>
Funder application is
application that connects event organizers with funding by auctioning sponsorship with bidder proposals

Funder has a bidding auction, chat, trackrecord feature

This application uses Firebase Firestore, Firebase Cloud Messaging, and Firebase Storage.

- Firebase Firestore is used to store user data from the event organizer
- Firebase Cloud Messaging is used to bring up notifications in realtime
- Firebase Storage is used to store files used in the Funder application

To be able to build this application all you have to do is:
  1. Installing git on your computer, git can be downloaded here https://git-scm.com/downloads
  2. Open your terminal then select the folder where this project will be saved then type the command git clone https://github.com/Lukmannudin/FunderMobileApps.git
  3. Make sure your internet is not slow then open the project results from the second command using Android Studio
  4. Wait for the compiling process and download the resource to finish. To ensure there are not many errors, don't update the gradual version.
  5. Change the project branch in the lower right corner from the master to development
  6. Done!
  
This application uses 2 data storage namely server funder and firebase. But if the server dies then to be able to log in
is open the LoginPresenter.kt file then in the following code:
if (USER_TYPE != SharedPreferenceUtils.EMPTY) {
            ....
        }
Rubah menjadi 
        if (USER_TYPE == SharedPreferenceUtils.EMPTY) {
            ....
        }

<b><i>Bahasa</i></i></b><br>

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
  6. Selesai!
  
Aplikasi ini menggunakan 2 tempat penyimpanan data yaitu server funder dan firebase. Namun jika server mati maka untuk bisa login
adalah buka file LoginPresenter.kt kemudian pada kode berikut: 
        if (USER_TYPE != SharedPreferenceUtils.EMPTY) {
            ....
        }
Rubah menjadi 
        if (USER_TYPE == SharedPreferenceUtils.EMPTY) {
            ....
        }
