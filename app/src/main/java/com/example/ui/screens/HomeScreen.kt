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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GmysDataRepository
import com.example.model.Donation
import com.example.model.Member
import com.example.model.ProjectRecord
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun HomeScreen(
  onNavigateToTab: (GeoNavTab) -> Unit,
  onOpenAddDonation: () -> Unit,
  onOpenAddMember: () -> Unit,
  onOpenVerification: () -> Unit,
  modifier: Modifier = Modifier
) {
  val members by GmysDataRepository.members.collectAsState()
  val donations by GmysDataRepository.donations.collectAsState()
  val projects by GmysDataRepository.projects.collectAsState()
  val org = GmysDataRepository.organization

  var selectedMemberForCard by remember { mutableStateOf<Member?>(null) }
  var selectedDonationForReceipt by remember { mutableStateOf<Donation?>(null) }

  // Default featured member for the Digital Member ID Hero Card
  val featuredMember = members.firstOrNull() ?: Member(
    memberCode = "GMYS-2024-0012",
    fullName = "Mohammed Ali",
    guardianName = "Ghulam Nabi",
    designation = "State Secretary",
    district = "Ahmedabad",
    taluka = "Ahmedabad City",
    bloodGroup = "B+",
    mobile = "+91 98251 44552",
    email = "mohammed.ali@gmys.org"
  )

  // Calculate live dynamic metrics
  val totalMembersCount = 1428 + members.size - 8
  val totalDonationAmount = donations.sumOf { it.amount }
  val formattedDonationStat = "₹" + String.format("%.1fL", (400000.0 + totalDonationAmount) / 100000.0)

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // Top Header (Geometric Balance)
    GeoHeader(
      roleSubtitle = "Administrator",
      userName = "Zaid Mansuri",
      initials = "ZM",
      onAvatarClick = { selectedMemberForCard = members.find { it.memberCode == "GMYS-2024-0001" } ?: featuredMember }
    )

    Column(
      modifier = Modifier
        .weight(1f)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Organization Banner (#E2E7DE rounded 28dp with rotated square badge)
      GeoOrgBanner(
        orgName = org.name,
        subLocation = "Ahmedabad District Headquarters",
        onClick = onOpenVerification
      )

      // 2x Grid of Metrics Cards
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        GeoMetricCard(
          label = "Members",
          value = String.format("%,d", totalMembersCount),
          badgeText = "+12%",
          badgeBgColor = GeoPrimaryContainer,
          badgeTextColor = GeoPrimary,
          onClick = { onNavigateToTab(GeoNavTab.MEMBERS) },
          modifier = Modifier.weight(1f)
        )

        GeoMetricCard(
          label = "Donations",
          value = formattedDonationStat,
          badgeText = "Goal",
          badgeBgColor = GeoGoldContainer,
          badgeTextColor = GeoOnGoldContainer,
          onClick = { onNavigateToTab(GeoNavTab.DONATIONS) },
          modifier = Modifier.weight(1f)
        )
      }

      // Digital Member ID Hero Card (#064E3B rounded 32dp)
      GeoDigitalIdCard(
        member = featuredMember,
        onCardClick = { selectedMemberForCard = featuredMember }
      )

      // Quick Operations Toolbar
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
          horizontalArrangement = Arrangement.SpaceAround,
          verticalAlignment = Alignment.CenterVertically
        ) {
          QuickActionButton(
            icon = Icons.Filled.PersonAdd,
            label = "Add Member",
            onClick = onOpenAddMember
          )
          QuickActionButton(
            icon = Icons.Filled.VolunteerActivism,
            label = "New Donation",
            onClick = onOpenAddDonation
          )
          QuickActionButton(
            icon = Icons.Filled.QrCodeScanner,
            label = "Verify QR",
            onClick = onOpenVerification
          )
          QuickActionButton(
            icon = Icons.Filled.ReceiptLong,
            label = "Cashbook",
            onClick = { onNavigateToTab(GeoNavTab.ACCOUNTS) }
          )
        }
      }

      // Current Projects Section (rounded-t-[32px] container)
      Surface(
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomStart = 20.dp, bottomEnd = 20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Current Projects",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(
              onClick = { onNavigateToTab(GeoNavTab.PROJECTS) },
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text(
                text = "View All",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = GeoPrimary
              )
            }
          }

          projects.take(3).forEach { project ->
            GeoProjectRow(
              project = project,
              onClick = { onNavigateToTab(GeoNavTab.PROJECTS) }
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }
  }

  // Modals / Dialogs
  selectedMemberForCard?.let { member ->
    GeoIdCardDialog(
      member = member,
      org = org,
      onDismiss = { selectedMemberForCard = null }
    )
  }

  selectedDonationForReceipt?.let { donation ->
    GeoReceiptDialog(
      donation = donation,
      org = org,
      onDismiss = { selectedDonationForReceipt = null }
    )
  }
}

@Composable
private fun QuickActionButton(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  onClick: () -> Unit
) {
  Column(
    modifier = Modifier
      .clip(RoundedCornerShape(14.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 6.dp, vertical = 4.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    Box(
      modifier = Modifier
        .size(40.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(GeoPrimaryContainer),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = GeoPrimary,
        modifier = Modifier.size(20.dp)
      )
    }
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurface,
      fontWeight = FontWeight.Medium,
      fontSize = 10.sp
    )
  }
}
