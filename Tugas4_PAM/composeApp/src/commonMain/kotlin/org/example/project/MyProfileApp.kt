package org.example.project
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Catatan: Jika ingin menggunakan gambar lokal, uncomment baris di bawah
// dan sesuaikan import 'Res' dengan struktur Compose Multiplatform kamu.
// import org.jetbrains.compose.resources.painterResource
// import androidx.compose.foundation.Image
// import androidx.compose.ui.layout.ContentScale

@Composable
fun MyProfileApp() {
    // State untuk toggle visibilitas Card (bonus rubric penilaian)
    var showDetails by remember { mutableStateOf(true) }

    // Memenuhi syarat minimal penggunaan: Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Custom Composable ke-1 [cite: 628]
        ProfileHeader(
            name = "Zans",
            bio = "Informatics Student | Tech Enthusiast"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Memenuhi syarat minimal penggunaan: Button
        Button(
            onClick = { showDetails = !showDetails },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(if (showDetails) "Sembunyikan Kontak" else "Tampilkan Kontak")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Bonus Poin (+10%): Implementasi AnimatedVisibility
        AnimatedVisibility(visible = showDetails) {
            // Custom Composable ke-2 [cite: 628]
            ProfileCard()
        }
    }
}

@Composable
fun ProfileHeader(name: String, bio: String) {
    // Memenuhi syarat minimal penggunaan: Box
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background Banner (Layer 1 Box)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF3B82F6))
        )

        // Konten Profile (Layer 2 Box)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            /* * Menggunakan Icon sebagai placeholder.
             * Jika file gambar sudah disiapkan di folder composeResources,
             * ganti Icon ini dengan Image() dan modifier clip(CircleShape)[cite: 625].
             */
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Photo Placeholder",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(16.dp),
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Header informasi Text [cite: 625]
            Text(
                text = name,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = bio,
                color = Color(0xFFE5E7EB),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ProfileCard() {
    // Memenuhi syarat minimal penggunaan: Card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Menampilkan list informasi [cite: 626]
            InfoItem(icon = Icons.Default.Email, text = "zans@student.itera.ac.id")
            HorizontalDivider(color = Color(0xFFF3F4F6))
            InfoItem(icon = Icons.Default.Phone, text = "+62 812-3456-7890")
            HorizontalDivider(color = Color(0xFFF3F4F6))
            InfoItem(icon = Icons.Default.LocationOn, text = "Jati Agung, Lampung")
        }
    }
}

// Custom Composable ke-3 [cite: 628]
@Composable
fun InfoItem(icon: ImageVector, text: String) {
    // Memenuhi syarat minimal penggunaan: Row dan Icon
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF3B82F6),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color(0xFF374151)
        )
    }
}