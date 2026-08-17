package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GmysDataRepository
import com.example.model.Donation
import com.example.model.Member
import com.example.ui.components.GeoHeader
import com.example.ui.theme.*

@Composable
fun VerificationScreen(
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  val members by GmysDataRepository.members.collectAsState()
  val donations by GmysDataRepository.donations.collectAsState()
  val org = GmysDataRepository.organization

  var verifyInput by remember { mutableStateOf("GMYS-2024-0012") }
  var verifiedMember by remember { mutableStateOf<Member?>(null) }
  var verifiedDonation by remember { mutableStateOf<Donation?>(null) }
  var searchPerformed by remember { mutableStateOf(false) }

  fun performVerification() {
    val query = verifyInput.trim()
    verifiedMember = members.find { it.memberCode.equals(query, ignoreCase = true) || it.qrToken.equals(query, ignoreCase = true) }
    verifiedDonation = donations.find { it.receiptNumber.equals(query, ignoreCase = true) || it.donationCode.equals(query, ignoreCase = true) }
    searchPerformed = true
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    GeoHeader(
      roleSubtitle = "Public Trust & Security",
      userName = "Live Verification Portal",
      initials = "QR",
      onAvatarClick = onDismiss
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Verification Card Box
      Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Text(
            text = "Official Authenticity Checker",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = GeoPrimary
          )
          Text(
            text = "Enter Member ID or 80G Receipt Number to verify authentic credentials issued by Gujarat Muslim Yuva Sangathan.",
            style = MaterialTheme.typography.bodySmall,
            color = GeoTextMuted
          )

          OutlinedTextField(
            value = verifyInput,
            onValueChange = { verifyInput = it },
            placeholder = { Text("e.g. GMYS-2024-0012 or GMYS-REC-2026-0104") },
            leadingIcon = { Icon(Icons.Filled.QrCodeScanner, contentDescription = null, tint = GeoPrimary) },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
          )

          Button(
            onClick = { performVerification() },
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GeoPrimary),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Filled.VerifiedUser, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Verify Live Record", fontWeight = FontWeight.Bold)
          }
        }
      }

      if (searchPerformed) {
        when {
          verifiedMember != null -> {
            val mem = verifiedMember!!
            Surface(
              shape = RoundedCornerShape(24.dp),
              color = GeoPrimaryContainer,
              border = BorderStroke(1.5.dp, GeoPrimary),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = GeoPrimary)
                    Text("VERIFIED OFFICIAL MEMBER", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = GeoPrimary)
                  }
                  Surface(shape = CircleShape, color = GeoPrimary) {
                    Text("ACTIVE", color = Color.White, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                  }
                }

                Divider(color = GeoPrimary.copy(alpha = 0.3f))

                Text("Name: ${mem.fullName}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GeoDarkHeader)
                Text("Member ID: ${mem.memberCode}", style = MaterialTheme.typography.bodySmall, color = GeoDarkHeader, fontWeight = FontWeight.SemiBold)
                Text("Designation: ${mem.designation}", style = MaterialTheme.typography.bodySmall, color = GeoDarkHeader)
                Text("District: ${mem.district} • Joining Date: ${mem.joiningDate}", style = MaterialTheme.typography.bodySmall, color = GeoDarkHeader)
                Text("Organization: ${org.name}", style = MaterialTheme.typography.labelSmall, color = GeoPrimary, fontWeight = FontWeight.Bold)
              }
            }
          }

          verifiedDonation != null -> {
            val don = verifiedDonation!!
            Surface(
              shape = RoundedCornerShape(24.dp),
              color = GeoGoldContainer,
              border = BorderStroke(1.5.dp, GeoGold),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    Icon(Icons.Filled.Verified, contentDescription = null, tint = GeoOnGoldContainer)
                    Text("GENUINE 80G RECEIPT", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = GeoOnGoldContainer)
                  }
                }

                Divider(color = GeoOnGoldContainer.copy(alpha = 0.3f))

                Text("Donor: ${don.donorName}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GeoOnGoldContainer)
                Text("Receipt: ${don.receiptNumber} • Date: ${don.date}", style = MaterialTheme.typography.bodySmall, color = GeoOnGoldContainer)
                Text("Amount: ₹${String.format("%,.2f", don.amount)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = GeoOnGoldContainer)
                Text("Exemption: Section 80G Compliant (${org.section80G})", style = MaterialTheme.typography.labelSmall, color = GeoOnGoldContainer, fontWeight = FontWeight.Bold)
              }
            }
          }

          else -> {
            Surface(
              shape = RoundedCornerShape(24.dp),
              color = Color(0xFFFFE4E6),
              border = BorderStroke(1.dp, GeoError),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                Icon(Icons.Filled.Warning, contentDescription = null, tint = GeoError)
                Text(
                  text = "No matching records found in GMYS database. Please double-check ID or receipt number.",
                  style = MaterialTheme.typography.bodySmall,
                  color = GeoError,
                  fontWeight = FontWeight.Medium
                )
              }
            }
          }
        }
      }

      // Quick test sample chips
      Text(
        text = "Quick Verification Samples:",
        style = MaterialTheme.typography.labelSmall,
        color = GeoTextMuted
      )

      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        AssistChip(
          onClick = {
            verifyInput = "GMYS-2024-0012"
            performVerification()
          },
          label = { Text("Mohammed Ali ID") }
        )
        AssistChip(
          onClick = {
            verifyInput = "GMYS-REC-2026-0104"
            performVerification()
          },
          label = { Text("80G Receipt #0104") }
        )
      }

      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}
