package id.sch.mandirihaurgeulis.examora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Exam(val title: String, val grade: String, val category: String, val major: String, val duration: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EduExamApp() }
    }
}

@Composable
fun EduExamApp() {
    var page by remember { mutableStateOf("home") }
    var role by remember { mutableStateOf("Siswa") }
    var grade by remember { mutableStateOf("Kelas 10") }
    var category by remember { mutableStateOf("Ujian Biasa") }
    var major by remember { mutableStateOf("TKR") }
    var title by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("60") }
    var exams by remember { mutableStateOf(listOf(
        Exam("Matematika", "Kelas 10", "Ujian Biasa", "-", "60 menit"),
        Exam("Bahasa Indonesia", "Kelas 11", "Ujian Biasa", "-", "60 menit"),
        Exam("Sistem Kelistrikan Kendaraan", "Kelas 11", "Produktif", "TKR", "90 menit"),
        Exam("Administrasi Jaringan", "Kelas 12", "Produktif", "TKJ", "90 menit")
    )) }
    var notice by remember { mutableStateOf("") }

    MaterialTheme(colorScheme = lightColorScheme(
        primary = Color(0xFF1769AA),
        secondary = Color(0xFF16866A),
        background = Color(0xFFF5F7FB),
        surface = Color.White
    )) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(Modifier.fillMaxSize().padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("EXAMORA", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                        Text("SMK MANDIRI HAURGEULIS", fontSize = 12.sp, color = Color.Gray)
                    }
                    Text("DEMO", color = Color(0xFF16866A), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
                Spacer(Modifier.height(18.dp))
                when (page) {
                    "home" -> {
                        Text("Selamat datang di EXAMORA", fontSize = 23.sp, fontWeight = FontWeight.Bold)
                        Text("Pilih akses untuk melanjutkan.", color = Color.Gray)
                        Spacer(Modifier.height(22.dp))
                        ActionCard("Masuk sebagai Siswa", "Lihat jadwal dan kerjakan ujian", "siswa") { role = "Siswa"; page = "student" }
                        ActionCard("Masuk sebagai Admin", "Kelola ujian, soal, dan hasil siswa", "admin") { role = "Admin"; page = "admin" }
                        Spacer(Modifier.weight(1f))
                        Text("PROTOTIPE • Belum terhubung ke server sekolah", fontSize = 11.sp, color = Color.Gray)
                    }
                    "student" -> {
                        Header("Portal Siswa") { page = "home" }
                        Text("Pilih tingkat kelas", fontWeight = FontWeight.Bold)
                        ChoiceRow(listOf("Kelas 10", "Kelas 11", "Kelas 12"), grade) { grade = it }
                        Spacer(Modifier.height(14.dp))
                        Text("Jenis ujian", fontWeight = FontWeight.Bold)
                        ChoiceRow(listOf("Ujian Biasa", "Produktif"), category) { category = it }
                        if (category == "Produktif") {
                            Spacer(Modifier.height(10.dp))
                            Text("Pilih jurusan", fontWeight = FontWeight.Bold)
                            ChoiceRow(listOf("TKR", "TKJ"), major) { major = it }
                        }
                        Spacer(Modifier.height(16.dp))
                        Text("Ujian tersedia", fontWeight = FontWeight.Bold)
                        val visible = exams.filter { it.grade == grade && it.category == category && (category != "Produktif" || it.major == major) }
                        if (visible.isEmpty()) EmptyPanel("Belum ada ujian untuk pilihan ini.")
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(visible) { exam ->
                                Card(shape = RoundedCornerShape(14.dp)) {
                                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                                        Text(exam.title, fontWeight = FontWeight.Bold)
                                        Text("${exam.grade} • ${exam.category}${if (exam.category == "Produktif") " • ${exam.major}" else ""}", color = Color.Gray, fontSize = 12.sp)
                                        Text("Durasi: ${exam.duration}", fontSize = 12.sp)
                                        Spacer(Modifier.height(8.dp))
                                        Button(onClick = { notice = "Simulasi: ujian ${exam.title} dipilih." }) { Text("Mulai ujian") }
                                    }
                                }
                            }
                        }
                        if (notice.isNotBlank()) Text(notice, color = MaterialTheme.colorScheme.primary)
                    }
                    "admin" -> {
                        Header("Panel Admin") { page = "home" }
                        Text("Buat ujian baru", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        OutlinedTextField(title, { title = it }, label = { Text("Nama ujian / mata pelajaran") }, modifier = Modifier.fillMaxWidth())
                        Spacer(Modifier.height(8.dp))
                        Text("Target kelas", fontWeight = FontWeight.Bold)
                        ChoiceRow(listOf("Kelas 10", "Kelas 11", "Kelas 12"), grade) { grade = it }
                        Spacer(Modifier.height(8.dp))
                        Text("Kategori", fontWeight = FontWeight.Bold)
                        ChoiceRow(listOf("Ujian Biasa", "Produktif"), category) { category = it }
                        if (category == "Produktif") {
                            Spacer(Modifier.height(8.dp))
                            Text("Jurusan", fontWeight = FontWeight.Bold)
                            ChoiceRow(listOf("TKR", "TKJ"), major) { major = it }
                        }
                        OutlinedTextField(duration, { duration = it.filter(Char::isDigit) }, label = { Text("Durasi (menit)") }, modifier = Modifier.fillMaxWidth())
                        Spacer(Modifier.height(10.dp))
                        Button(onClick = {
                            if (title.isBlank() || duration.isBlank()) notice = "Isi nama ujian dan durasi terlebih dahulu."
                            else {
                                exams = exams + Exam(title.trim(), grade, if (category == "Ujian Biasa") "Ujian Biasa" else "Produktif", if (category == "Produktif") major else "-", "$duration menit")
                                notice = "Ujian ditambahkan pada daftar demo."
                                title = ""
                            }
                        }, modifier = Modifier.fillMaxWidth()) { Text("Tambah ujian") }
                        if (notice.isNotBlank()) Text(notice, color = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.height(16.dp))
                        Text("Daftar ujian demo", fontWeight = FontWeight.Bold)
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(exams) { e ->
                                Card {
                                    Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Column(Modifier.weight(1f)) {
                                            Text(e.title, fontWeight = FontWeight.SemiBold)
                                            Text("${e.grade} • ${e.category} • ${e.major} • ${e.duration}", fontSize = 11.sp, color = Color.Gray)
                                        }
                                        TextButton(onClick = { exams = exams - e }) { Text("Hapus") }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Header(title: String, back: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text("‹", fontSize = 30.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.clickable { back() }.padding(end = 12.dp))
        Text(title, fontSize = 21.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ActionCard(title: String, subtitle: String, tag: String, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable { onClick() }, shape = RoundedCornerShape(18.dp)) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(46.dp).background(if (tag == "admin") Color(0xFFE5F0FF) else Color(0xFFE2F5EE), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                Text(if (tag == "admin") "A" else "S", fontSize = 21.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.width(14.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ChoiceRow(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
        options.forEach { option ->
            FilterChip(selected = selected == option, onClick = { onSelect(option) }, label = { Text(option, fontSize = 11.sp) })
        }
    }
}

@Composable
fun EmptyPanel(message: String) {
    Card(Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
        Text(message, Modifier.padding(16.dp), color = Color.Gray, fontSize = 13.sp)
    }
}
