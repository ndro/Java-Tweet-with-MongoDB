# Java Tweet with MongoDB

Simple tweet

Project IF4031 Pengembangan Aplikasi Terdistribusi
- - - -

Berikut ini adalah langkah-langkah untuk menjalankan program

1. Mengekstrak source code project
2. Membuka project menggunakan Netbeans IDE
3. Mengaktifkan server MongoDB
3. Memilih menu Run Project

Selanjutnya, program dapat dioperasikan dengan instruksi-instruksi berikut
1. /register [username] [password]
   Mendaftarkan pengguna baru
   (insert document ke column users)

2. /login [username] [password]
   Melakukan login dengan username dan password yang sudah terdaftar

3. /follow [username]
   Mem-follow pengguna lain   
   (insert document ke column friends dan followers)
   
4. [message]
   Untuk mengirimkan tweet, pesan langsung ditulis tanpa diawali instruksi apapun 	
   (insert ke tabel tweet, timeline, userline, dan timeline semua follower) 
   	
5. /userline [username]
   Melihat daftar tweet pengguna tertentu
   (menampilkan tweet per user)
   
6. /timeline
   Melihat daftar tweet sendiri
   (menampilkan timeline per user)
	
7. /logout
   Logout dari aplikasi

8. /exit
   Keluar dari aplikasi	


## Reference
klien: http://docs.mongodb.org/ecosystem/drivers/
tutorial: mongodb.github.io/mongo-java-driver/3.0/driver/getting-started/quick-tour/
