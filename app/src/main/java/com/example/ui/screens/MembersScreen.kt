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
import androidx.compose.ui.window.Dialog
import com.example.data.GmysDataRepository
import com.example.model.Member
import com.example.model.MemberStatus
import com.example.model.MembershipType
import com.example.ui.components.GeoHeader
import com.example.ui.components.GeoIdCardDialog
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembersScreen(
  onOpenAddMember: () -> Unit,
  modifier: Modifier = Modifier
) {
  val members by GmysDataRepository.members.collectAsState()
  val org = GmysDataRepository.organization

  var searchQuery by remember { mutableStateOf("") }
  var selectedDistrictFilter by remember { mutableStateOf("All") }
  var selectedTypeFilter by remember { mutableStateOf("All") }
  var selectedMemberForCard by remember { mutableStateOf<Member?>(null) }

  val districts = listOf("All", "Ahmedabad", "Surat", "Vadodara", "Bharuch", "Anand", "Rajkot", "Bhavnagar")

  val filteredMembers = members.filter { member ->
    val matchesSearch = member.fullName.contains(searchQuery, ignoreCase = true) ||
      member.memberCode.contains(searchQuery, ignoreCase = true) ||
      member.designation.contains(searchQuery, ignoreCase = true) ||
      member.district.contains(searchQuery, ignoreCase = true)

    val matchesDistrict = selectedDistrictFilter == "All" || member.district.equals(selectedDistrictFilter, ignoreCase = true)
    val matchesType = when (selectedTypeFilter) {
      "Executive" -> member.membershipType == MembershipType.EXECUTIVE
      "Volunteer" -> member.membershipType == MembershipType.VOLUNTEER
      "Life" -> member.membershipType == MembershipType.LIFE_MEMBER
      else -> true
    }

    matchesSearch && matchesDistrict && matchesType
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    GeoHeader(
      roleSubtitle = "Member Directory",
      userName = "GMYS Cadre",
      initials = "GM"
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Search Bar with Geometric Rounded Styling
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search by name, ID, or district...", style = MaterialTheme.typography.bodyMedium) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = GeoPrimary) },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(Icons.Filled.Close, contentDescription = "Clear")
            }
          }
        },
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = GeoPrimary,
          unfocusedBorderColor = MaterialTheme.colorScheme.outline,
          focusedContainerColor = MaterialTheme.colorScheme.surface,
          unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
      )

      // District Filter Chips
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
      ) {
        items(districts) { district ->
          val isSelected = selectedDistrictFilter == district
          FilterChip(
            selected = isSelected,
            onClick = { selectedDistrictFilter = district },
            label = { Text(district, style = MaterialTheme.typography.labelSmall) },
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

      // Member Count Header with Add Button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${filteredMembers.size} Enrolled Members",
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold,
          color = GeoTextMuted
        )

        Button(
          onClick = onOpenAddMember,
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = GeoPrimary),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Icon(Icons.Filled.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Register Member", style = MaterialTheme.typography.labelMedium)
        }
      }
    }

    Spacer(modifier = Modifier.height(6.dp))

    // Members List
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(bottom = 24.dp)
    ) {
      items(filteredMembers, key = { it.id }) { member ->
        MemberCardItem(
          member = member,
          onClick = { selectedMemberForCard = member }
        )
      }
    }
  }

  // ID Card Dialog
  selectedMemberForCard?.let { member ->
    GeoIdCardDialog(
      member = member,
      org = org,
      onDismiss = { selectedMemberForCard = null }
    )
  }
}

@Composable
private fun MemberCardItem(
  member: Member,
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
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Geometric Avatar with Initials
      Box(
        modifier = Modifier
          .size(50.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(if (member.isExecutive) GeoPrimaryContainer else MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = member.avatarInitials,
          color = if (member.isExecutive) GeoPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = member.fullName,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )

          Surface(
            shape = CircleShape,
            color = if (member.isExecutive) GeoGoldContainer else GeoPrimaryContainer
          ) {
            Text(
              text = member.bloodGroup,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = if (member.isExecutive) GeoOnGoldContainer else GeoPrimary,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        Text(
          text = "${member.designation} • ${member.district}",
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
            text = "ID: ${member.memberCode}",
            style = MaterialTheme.typography.labelSmall,
            color = GeoPrimary,
            fontWeight = FontWeight.SemiBold
          )

          Text(
            text = "View ID Card →",
            style = MaterialTheme.typography.labelSmall,
            color = GeoGold,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
