package com.example.appc06_sql05

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setelah view dibuat, tampilkan daftar mahasiswa yang ada di database
        tampilkanDaftarMahasiswa()
    }

    // Fungsi untuk menampilkan daftar mahasiswa yang ada di database
    private fun tampilkanDaftarMahasiswa() {
        // Buat objek DatabaseHelper untuk mengambil data dari database
        val db = DatabaseHelper(requireContext())

        // Ambil data mahasiswa dari database
        val mahasiswa = db.getMahasiswa()

        // Tampilkan data mahasiswa ke dalam RecyclerView
        rvMahasiswa.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MahasiswaAdapter(mahasiswa, object : MahasiswaAdapterListener {
                override fun onEdit(mahasiswa: Mahasiswa) {
                    // Buka fragment mahasiswa untuk mengedit data mahasiswa
                    val fragment = MahasiswaFragment.newInstance(mahasiswa)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit()
                }

                override fun onDelete(id: Int) {
                    // Hapus data mahasiswa dari database dan tampilkan kembali daftar mahasiswa
                    db.hapusMahasiswa(id)
                    tampilkanDaftarMahasiswa()
                }
            })
        }
    }

}



// ViewHolder untuk item daftar mahasiswa
class MahasiswaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(mahasiswa: Mahasiswa, listener: MahasiswaAdapterListener) {
        itemView.tvNama.text = mahasiswa.nama
        itemView.tvProdi.text = mahasiswa.prodi

        // Ketika tombol edit ditekan, panggil fungsi onEdit pada listener
        itemView.btnEdit.setOnClickListener { listener.onEdit(mahasiswa) }

        // Ketika tombol hapus ditekan, panggil fungsi onDelete pada listener
        itemView.btnHapus.setOnClickListener { listener.onDelete(mahasiswa.id) }
    }

}

// Interface untuk listener pada MahasiswaAdapter
interface MahasiswaAdapterListener {
    fun onEdit(mahasiswa: Mahasiswa)
    fun onDelete(id: Int)
}
