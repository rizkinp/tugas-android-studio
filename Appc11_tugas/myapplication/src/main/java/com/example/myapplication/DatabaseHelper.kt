import com.example.myapplication.Mahasiswa
import java.sql.*

class DatabaseHelper(private val url: String, private val username: String, private val password: String) {
    private var connection: Connection? = null

    fun connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            connection = DriverManager.getConnection(url, username, password)
        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            connection?.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun insertMahasiswa(mahasiswa: Mahasiswa) {
        val query = "INSERT INTO mahasiswa (nim, nama, alamat, jenis_kelamin, gambar) VALUES (?, ?, ?, ?, ?)"
        try {
            val statement = connection?.prepareStatement(query)
            statement?.apply {
                setString(1, mahasiswa.nim)
                setString(2, mahasiswa.nama)
                setString(3, mahasiswa.alamat)
                setString(4, mahasiswa.jenisKelamin)
                setBytes(5, mahasiswa.gambar)
                executeUpdate()
                close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun updateMahasiswa(mahasiswa: Mahasiswa) {
        val query = "UPDATE mahasiswa SET nama = ?, alamat = ?, jenis_kelamin = ?, gambar = ? WHERE nim = ?"
        try {
            val statement = connection?.prepareStatement(query)
            statement?.apply {
                setString(1, mahasiswa.nama)
                setString(2, mahasiswa.alamat)
                setString(3, mahasiswa.jenisKelamin)
                setBytes(4, mahasiswa.gambar)
                setString(5, mahasiswa.nim)
                executeUpdate()
                close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun deleteMahasiswa(nim: String) {
        val query = "DELETE FROM mahasiswa WHERE nim = ?"
        try {
            val statement = connection?.prepareStatement(query)
            statement?.apply {
                setString(1, nim)
                executeUpdate()
                close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun getMahasiswaByNim(nim: String): Mahasiswa? {
        val query = "SELECT * FROM mahasiswa WHERE nim = ?"
        var mahasiswa: Mahasiswa? = null
        try {
            val statement = connection?.prepareStatement(query)
            statement?.apply {
                setString(1, nim)
                val resultSet = executeQuery()
                if (resultSet.next()) {
                    val nama = resultSet.getString("nama")
                    val alamat = resultSet.getString("alamat")
                    val jenisKelamin = resultSet.getString("jenis_kelamin")
                    val gambar = resultSet.getBytes("gambar")
                    mahasiswa = Mahasiswa(nim, nama, alamat, jenisKelamin, gambar)
                }
                resultSet.close()
                close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return mahasiswa
    }

    fun getAllMahasiswa(): List<Mahasiswa> {
        val query = "SELECT * FROM mahasiswa"
        val mahasiswaList = mutableListOf<Mahasiswa>()
        try {
            val statement = connection?.createStatement()
            val resultSet = statement?.executeQuery(query)
            while (resultSet?.next() == true) {
                val nim = resultSet.getString("nim")
                val nama = resultSet.getString("nama")
                val alamat = resultSet.getString("alamat")
                val jenisKelamin = resultSet.getString("jenis_kelamin")
                val gambar = resultSet.getBytes("gambar")
                val mahasiswa = Mahasiswa(nim, nama, alamat, jenisKelamin, gambar)
                mahasiswaList.add(mahasiswa)
            }
            resultSet?.close()
            statement?.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return mahasiswaList
    }
}
