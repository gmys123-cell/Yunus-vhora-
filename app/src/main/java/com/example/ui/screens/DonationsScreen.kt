package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GmysDataRepository
import com.example.model.Donation
import com.example.model.PaymentMode
import com.example.ui.components.GeoHeader
import com.example.ui.components.GeoReceiptDialog
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationsScreen(
  onOpenAddDonation: () -> Unit,
  modifier: Modifier = Modifier
) {
  val donations by GmysDataRepository.donations.collectAsState()
  val org = GmysDataRepository.organization

  var searchQuery by remember { mutableStateOf("") }
  var selectedCampaignFilter by remember { mutableStateOf("All") }
  var selectedDonationForReceipt by remember { mutableStateOf<Donation?>(null) }

  val campaigns = listOf("All", "Education Scholarship 2026", "Surat Health Camp", "Emergency Relief Kit", "Yuva Skill Hub", "Infrastructure Fund")

  val totalDonationSum = donations.sumOf { it.amount }
  val total80GEligible = donations.filter { it.is80GEligible }.sumOf { it.amount }

  val filteredDonations = donations.filter { donation ->
    val matchesSearch = donation.donorName.contains(searchQuery, ignoreCase = true) ||
      donation.receiptNumber.contains(searchQuery, ignoreCase = true) ||
      donation.purpose.contains(searchQuery, ignoreCase = true) ||
      donation.mobile.contains(searchQuery)

    val matchesCampaign = selectedCampaignFilter == "All" || donation.campaign.equals(selectedCampaignFilter, ignoreCase = true)
    matchesSearch && matchesCampaign
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    GeoHeader(
      roleSubtitle = "Donations & 80G Hub",
      userName = "GMYS Treasury",
      initials = "TR"
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Summary Card (Geometric Balance Emerald & Gold)
      Surface(
        shape = RoundedCornerShape(24.dp),
        color = GeoPrimary,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "TOTAL COLLECTIONS",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.7f),
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Text(
                text = "₹" + String.format("%,.2f", totalDonationSum),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            }

            Surface(
              shape = CircleShape,
              color = GeoGoldContainer
            ) {
              Text(
                text = "80G Compliant",
                style = MaterialTheme.typography.labelSmall,
                color = GeoOnGoldContainer,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "80G Tax Exemption Total: ₹${String.format("%,.0f", total80GEligible)}",
              style = MaterialTheme.typography.bodySmall,
              color = Color.White.copy(alpha = 0.85f)
            )
            Text(
              text = "${donations.size} Receipts Issued",
              style = MaterialTheme.typography.bodySmall,
              color = GeoGoldContainer,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }

      // Search and Record Donation Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Search donor, receipt...", style = MaterialTheme.typography.bodyMedium) },
          leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = GeoPrimary) },
          shape = RoundedCornerShape(20.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GeoPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
          ),
          modifier = Modifier.weight(1f),
          singleLine = true
        )

        Button(
          onClick = onOpenAddDonation,
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = GeoPrimary),
          contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp)
        ) {
          Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Donate", style = MaterialTheme.typography.labelMedium)
        }
      }

      // Campaign Filter Chips
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 2.dp)
      ) {
        items(campaigns) { campaign ->
          val isSelected = selectedCampaignFilter == campaign
          FilterChip(
            selected = isSelected,
            onClick = { selectedCampaignFilter = campaign },
            label = { Text(campaign, style = MaterialTheme.typography.labelSmall) },
            shape = RoundedCornerShape(12.dp),
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = GeoPrimary,
              selectedLabelColor = Color.White,
              containerColor = MaterialTheme.colorScheme.surface
            ),
            border = FilterChipDefaults.filterChipBorder(
              enabled = true,
              selected = isSelected,
              borderColor = if (isSelected) GeoPrimary else MaterialTheme.colorScheme.outline
            )
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Donation Items List
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(bottom = 24.dp)
    ) {
      items(filteredDonations, key = { it.id }) { donation ->
        DonationCardItem(
          donation = donation,
          onClick = { selectedDonationForReceipt = donation }
        )
      }
    }
  }

  // Receipt Modal
  selectedDonationForReceipt?.let { donation ->
    GeoReceiptDialog(
      donation = donation,
      org = org,
      onDismiss = { selectedDonationForReceipt = null }
    )
  }
}

@Composable
private fun DonationCardItem(
  donation: Donation,
  onClick: () -> Unit
) {
  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(20.dp),
    color = MaterialTheme.colorScheme.surface,
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    shadowElevation = 1.dp,
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .padding(14.dp)
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Geometric payment method badge
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(GeoPrimaryContainer),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = when (donation.paymentMethod) {
            PaymentMode.UPI -> Icons.Filled.QrCode
            PaymentMode.BANK_TRANSFER -> Icons.Filled.AccountBalance
            PaymentMode.CASH -> Icons.Filled.Payments
            else -> Icons.Filled.CreditCard
          },
          contentDescription = null,
          tint = GeoPrimary,
          modifier = Modifier.size(22.dp)
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = donation.donorName,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )

          Text(
            text = "₹" + String.format("%,.0f", donation.amount),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = GeoPrimary
          )
        }

        Text(
          text = "${donation.purpose} • ${donation.date}",
          style = MaterialTheme.typography.bodySmall,
          color = GeoTextMuted,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = donation.receiptNumber,
            style = MaterialTheme.typography.labelSmall,
            color = GeoTextMuted
          )

          Text(
            text = "View 80G Receipt →",
            style = MaterialTheme.typography.labelSmall,
            color = GeoGold,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
