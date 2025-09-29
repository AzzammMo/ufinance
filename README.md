# 💰 UFinance - Web Penataan Tabungan & Pengeluaran

**UFinance** adalah aplikasi backend berbasis **Java Spring Boot** untuk mengelola keuangan pribadi.  
Aplikasi ini menyediakan **REST API** sehingga bisa diakses oleh aplikasi frontend (web/mobile).  

Dengan UFinance, pengguna dapat:
- 👀 Melihat daftar transaksi
- ➕ Menambahkan transaksi (pemasukan & pengeluaran)
- 🗂️ Menambahkan kategori untuk tabungan/pengeluaran

---

# Konfigurasi Database
1. Buat Database Ufinance
2. Ubah pengaturan database di src/main/resources/application.properties
   # Database connection
    spring.datasource.url=jdbc:mysql://localhost:3306/ufinance
    spring.datasource.username=root
    spring.datasource.password=yourpassword

  # Hibernate JPA
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Run Website
With CLI mvn spring-boot:run



