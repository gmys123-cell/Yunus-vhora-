package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.model.ProjectRecord
import com.example.model.ProjectStatus
import com.example.ui.components.GeoHeader
import com.example.ui.theme.*

@Composable
fun ProjectsScreen(
  modifier: Modifier = Modifier
) {
  val projects by GmysDataRepository.projects.collectAsState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    GeoHeader(
      roleSubtitle = "Humanitarian Operations",
      userName = "GMYS Projects",
      initials = "PR"
    )

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
      contentPadding = PaddingValues(bottom = 24.dp)
    ) {
      item {
        Text(
          text = "Active Relief & Welfare Drives",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onBackground
        )
      }

      items(projects, key = { it.id }) { project ->
        ProjectDetailCard(project = project)
      }
    }
  }
}

@Composable
private fun ProjectDetailCard(project: ProjectRecord) {
  val progress = (project.actualExpense / project.budget).coerceIn(0.0, 1.0).toFloat()
  val isEmerald = project.accentColorCode == "emerald"
  val iconBg = if (isEmerald) GeoPrimaryContainer else GeoGoldContainer
  val iconText = if (isEmerald) GeoPrimary else GeoOnGoldContainer
  val barColor = if (isEmerald) GeoPrimary else GeoGold

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
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(iconBg),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = project.initialLetter,
            color = iconText,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
          )
        }

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = project.name,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = "${project.category} • ${project.district}",
            style = MaterialTheme.typography.bodySmall,
            color = GeoTextMuted
          )
        }

        Surface(
          shape = CircleShape,
          color = if (project.status == ProjectStatus.ACTIVE) GeoPrimaryContainer else MaterialTheme.colorScheme.surfaceVariant
        ) {
          Text(
            text = project.status.label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = if (project.status == ProjectStatus.ACTIVE) GeoPrimary else GeoTextMuted,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
      }

      // Budget Progress
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Utilized: ₹${String.format("%,.0f", project.actualExpense)}",
            style = MaterialTheme.typography.bodySmall,
            color = GeoPrimary,
            fontWeight = FontWeight.SemiBold
          )
          Text(
            text = "Budget: ₹${String.format("%,.0f", project.budget)}",
            style = MaterialTheme.typography.bodySmall,
            color = GeoTextMuted
          )
        }

        LinearProgressIndicator(
          progress = { progress },
          modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(CircleShape),
          color = barColor,
          trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
      }

      Divider(color = MaterialTheme.colorScheme.outline)

      // Beneficiaries & Team
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Beneficiaries Helped",
            style = MaterialTheme.typography.labelSmall,
            color = GeoTextMuted
          )
          Text(
            text = "${project.achievedBeneficiaries} / ${project.targetBeneficiaries}",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = GeoDarkHeader
          )
        }

        Column(horizontalAlignment = Alignment.End) {
          Text(
            text = "Project Lead",
            style = MaterialTheme.typography.labelSmall,
            color = GeoTextMuted
          )
          Text(
            text = "${project.manager} (${project.volunteerCount} Volunteers)",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = GeoPrimary
          )
        }
      }
    }
  }
}
