package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.model.Donation
import com.example.model.Member
import com.example.model.OrganizationProfile
import com.example.model.ProjectRecord
import com.example.ui.theme.*

/**
 * Top Header matching the Geometric Balance Theme:
 * - Subtitle: "ADMINISTRATOR" / "EXECUTIVE COUNCIL" in uppercase tracking
 * - Title: Name
 * - Rounded Avatar: 48dp circle with sage background & emerald initials
 */
@Composable
fun GeoHeader(
  roleSubtitle: String = "Administrator",
  userName: String = "Zaid Mansuri",
  initials: String = "ZM",
  onAvatarClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 12.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = roleSubtitle.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = GeoPrimary,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp
      )
      Text(
        text = userName,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }

    Box(
      modifier = Modifier
        .size(48.dp)
        .clip(CircleShape)
        .background(GeoPrimaryContainer)
        .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
        .clickable(onClick = onAvatarClick),
      contentAlignment = Alignment.Center
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(GeoPrimary),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = initials,
          color = Color.White,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

/**
 * Organization Banner:
 * - Background: #E2E7DE, Rounded 28dp
 * - Text: Gujarat Muslim Yuva Sangathan
 * - Right Icon: #064E3B container with 45-deg rotated square
 */
@Composable
fun GeoOrgBanner(
  orgName: String = "Gujarat Muslim Yuva Sangathan",
  subLocation: String = "Ahmedabad District Headquarters",
  onClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(28.dp),
    color = MaterialTheme.colorScheme.surfaceVariant,
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .padding(horizontal = 18.dp, vertical = 14.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = orgName.uppercase(),
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = subLocation,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(GeoPrimary),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .size(16.dp)
            .rotate(45f)
            .border(2.dp, Color.White, RoundedCornerShape(2.dp))
        )
      }
    }
  }
}

/**
 * Metric Card (Geometric Balance):
 * - Rounded 24dp, border #E0E3DB, subtle shadow
 * - Bold Value with Badge (+12%, Goal, etc.)
 */
@Composable
fun GeoMetricCard(
  label: String,
  value: String,
  badgeText: String,
  badgeBgColor: Color = GeoPrimaryContainer,
  badgeTextColor: Color = GeoPrimary,
  onClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(24.dp),
    color = MaterialTheme.colorScheme.surface,
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    shadowElevation = 1.dp,
    modifier = modifier
  ) {
    Column(
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 14.dp)
        .fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Text(
        text = label.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = GeoTextMuted,
        letterSpacing = 0.8.sp
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
      ) {
        Text(
          text = value,
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.Bold,
          color = GeoPrimary
        )

        Surface(
          shape = CircleShape,
          color = badgeBgColor
        ) {
          Text(
            text = badgeText,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = badgeTextColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
      }
    }
  }
}

/**
 * Digital Member ID Card (Hero Component):
 * - Emerald Green Gradient with Gold Badge & Circular geometric watermark
 * - Interactive: Tap to expand / flip card!
 */
@Composable
fun GeoDigitalIdCard(
  member: Member,
  onCardClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(32.dp))
      .background(
        Brush.linearGradient(
          colors = listOf(GeoPrimary, GeoDarkHeader)
        )
      )
      .clickable(onClick = onCardClick)
      .padding(20.dp)
  ) {
    // Background Geometric Watermark
    Box(
      modifier = Modifier
        .size(130.dp)
        .align(Alignment.BottomEnd)
        .offset(x = 24.dp, y = 24.dp)
        .border(12.dp, Color.White.copy(alpha = 0.08f), CircleShape)
    )

    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Card Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column {
          Text(
            text = "DIGITAL MEMBER ID",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.65f),
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
          )
          Text(
            text = "Executive Council Card",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
          )
        }

        Surface(
          shape = CircleShape,
          color = GeoGold
        ) {
          Text(
            text = member.status.label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }
      }

      // Member Bio Section
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Geometric Avatar Frame
        Box(
          modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .border(1.5.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(20.dp)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = member.avatarInitials,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
          )
        }

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = member.fullName,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = "ID: ${member.memberCode}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.85f),
            fontWeight = FontWeight.Medium
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = member.designation.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = GeoGoldContainer,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
        }
      }

      // Footer info
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "District: ${member.district}",
          style = MaterialTheme.typography.bodySmall,
          color = Color.White.copy(alpha = 0.7f)
        )
        Text(
          text = "Tap for QR & Details →",
          style = MaterialTheme.typography.labelSmall,
          color = GeoGoldContainer,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}

/**
 * Project Item Row:
 * - #F5F7F2 container, Rounded 20dp
 * - Colored Icon box ('E', 'H', etc.)
 * - Vertical bar indicator
 */
@Composable
fun GeoProjectRow(
  project: ProjectRecord,
  onClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val isEmerald = project.accentColorCode == "emerald"
  val iconBg = if (isEmerald) GeoPrimaryContainer else GeoGoldContainer
  val iconText = if (isEmerald) GeoPrimary else GeoOnGoldContainer
  val barColor = if (isEmerald) GeoPrimary else GeoGold

  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(20.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .padding(horizontal = 14.dp, vertical = 12.dp)
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Geometric letter icon
      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(iconBg),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = project.initialLetter,
          color = iconText,
          style = MaterialTheme.typography.titleMedium,
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
          text = "${project.achievedBeneficiaries} Beneficiaries • ${project.volunteerCount} Volunteers",
          style = MaterialTheme.typography.bodySmall,
          color = GeoTextMuted
        )
      }

      // Vertical indicator bar
      Box(
        modifier = Modifier
          .width(4.dp)
          .height(32.dp)
          .clip(CircleShape)
          .background(barColor)
      )
    }
  }
}

/**
 * Geometric Bottom Navigation Bar:
 * - 4 Primary destinations (Home, Members, Donations, Accounts/Reports)
 * - Active pill indicator in Sage green (#D1E8D5) + Emerald icon (#064E3B)
 * - Complies with navigationBarsPadding()
 */
enum class GeoNavTab(val label: String) {
  HOME("Home"),
  MEMBERS("Members"),
  DONATIONS("Donations"),
  ACCOUNTS("Accounts"),
  PROJECTS("Projects")
}

@Composable
fun GeoBottomNavBar(
  currentTab: GeoNavTab,
  onTabSelected: (GeoNavTab) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    color = MaterialTheme.colorScheme.surface,
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(horizontal = 12.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      GeoNavItem(
        tab = GeoNavTab.HOME,
        isSelected = currentTab == GeoNavTab.HOME,
        onClick = { onTabSelected(GeoNavTab.HOME) }
      )
      GeoNavItem(
        tab = GeoNavTab.MEMBERS,
        isSelected = currentTab == GeoNavTab.MEMBERS,
        onClick = { onTabSelected(GeoNavTab.MEMBERS) }
      )
      GeoNavItem(
        tab = GeoNavTab.DONATIONS,
        isSelected = currentTab == GeoNavTab.DONATIONS,
        onClick = { onTabSelected(GeoNavTab.DONATIONS) }
      )
      GeoNavItem(
        tab = GeoNavTab.ACCOUNTS,
        isSelected = currentTab == GeoNavTab.ACCOUNTS,
        onClick = { onTabSelected(GeoNavTab.ACCOUNTS) }
      )
      GeoNavItem(
        tab = GeoNavTab.PROJECTS,
        isSelected = currentTab == GeoNavTab.PROJECTS,
        onClick = { onTabSelected(GeoNavTab.PROJECTS) }
      )
    }
  }
}

@Composable
private fun GeoNavItem(
  tab: GeoNavTab,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Column(
    modifier = Modifier
      .clip(RoundedCornerShape(16.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 8.dp, vertical = 4.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    if (isSelected) {
      Box(
        modifier = Modifier
          .size(34.dp)
          .clip(CircleShape)
          .background(GeoPrimaryContainer),
        contentAlignment = Alignment.Center
      ) {
        when (tab) {
          GeoNavTab.HOME -> Box(
            modifier = Modifier
              .size(12.dp)
              .clip(RoundedCornerShape(3.dp))
              .background(GeoPrimary)
          )
          GeoNavTab.MEMBERS -> Icon(
            Icons.Filled.People,
            contentDescription = null,
            tint = GeoPrimary,
            modifier = Modifier.size(18.dp)
          )
          GeoNavTab.DONATIONS -> Icon(
            Icons.Filled.VolunteerActivism,
            contentDescription = null,
            tint = GeoPrimary,
            modifier = Modifier.size(18.dp)
          )
          GeoNavTab.ACCOUNTS -> Icon(
            Icons.Filled.AccountBalance,
            contentDescription = null,
            tint = GeoPrimary,
            modifier = Modifier.size(18.dp)
          )
          GeoNavTab.PROJECTS -> Icon(
            Icons.Filled.FolderSpecial,
            contentDescription = null,
            tint = GeoPrimary,
            modifier = Modifier.size(18.dp)
          )
        }
      }
      Text(
        text = tab.label,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = GeoPrimary
      )
    } else {
      Box(
        modifier = Modifier.size(34.dp),
        contentAlignment = Alignment.Center
      ) {
        when (tab) {
          GeoNavTab.HOME -> Box(
            modifier = Modifier
              .size(14.dp)
              .border(1.5.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f), RoundedCornerShape(3.dp))
          )
          GeoNavTab.MEMBERS -> Icon(
            Icons.Outlined.People,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
            modifier = Modifier.size(20.dp)
          )
          GeoNavTab.DONATIONS -> Icon(
            Icons.Outlined.VolunteerActivism,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
            modifier = Modifier.size(20.dp)
          )
          GeoNavTab.ACCOUNTS -> Icon(
            Icons.Outlined.AccountBalance,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
            modifier = Modifier.size(20.dp)
          )
          GeoNavTab.PROJECTS -> Icon(
            Icons.Outlined.FolderSpecial,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
            modifier = Modifier.size(20.dp)
          )
        }
      }
      Text(
        text = tab.label,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
      )
    }
  }
}

/**
 * Dual-Sided Member ID Card Dialog:
 * Allows flipping between Front and Back with full QR Code and Organization info
 */
@Composable
fun GeoIdCardDialog(
  member: Member,
  org: OrganizationProfile,
  onDismiss: () -> Unit
) {
  var showBack by remember { mutableStateOf(false) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      shape = RoundedCornerShape(32.dp),
      color = MaterialTheme.colorScheme.surface,
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .padding(16.dp)
    ) {
      Column(
        modifier = Modifier
          .padding(20.dp)
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (showBack) "ID Card (Back View)" else "ID Card (Front View)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = GeoPrimary
          )
          IconButton(onClick = onDismiss) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
          }
        }

        // Card Container
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = GeoPrimary,
          shadowElevation = 8.dp,
          modifier = Modifier
            .fillMaxWidth()
            .clickable { showBack = !showBack }
        ) {
          if (!showBack) {
            // FRONT VIEW
            Column(
              modifier = Modifier
                .background(
                  Brush.linearGradient(
                    listOf(GeoPrimary, GeoDarkHeader)
                  )
                )
                .padding(20.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              // Emblem Header
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = org.name.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = GeoGoldContainer,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = "Reg No: ${org.regNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                  )
                }

                Surface(
                  shape = CircleShape,
                  color = GeoGold
                ) {
                  Text(
                    text = member.membershipType.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(6.dp))

              // Big Avatar
              Box(
                modifier = Modifier
                  .size(80.dp)
                  .clip(RoundedCornerShape(24.dp))
                  .background(Color.White.copy(alpha = 0.2f))
                  .border(2.dp, GeoGold, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = member.avatarInitials,
                  style = MaterialTheme.typography.headlineLarge,
                  color = Color.White,
                  fontWeight = FontWeight.Bold
                )
              }

              Text(
                text = member.fullName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
              )

              Text(
                text = member.designation,
                style = MaterialTheme.typography.titleSmall,
                color = GeoGoldContainer,
                fontWeight = FontWeight.SemiBold
              )

              Divider(color = Color.White.copy(alpha = 0.2f))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(text = "Member ID", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.6f))
                  Text(text = member.memberCode, style = MaterialTheme.typography.labelMedium, color = Color.White, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(text = "Blood Group", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.6f))
                  Text(text = member.bloodGroup, style = MaterialTheme.typography.labelMedium, color = GeoGoldContainer, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(text = "District", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.6f))
                  Text(text = member.district, style = MaterialTheme.typography.labelMedium, color = Color.White, fontWeight = FontWeight.Bold)
                }
              }
            }
          } else {
            // BACK VIEW (Verification QR)
            Column(
              modifier = Modifier
                .background(
                  Brush.linearGradient(
                    listOf(GeoDarkHeader, GeoPrimary)
                  )
                )
                .padding(20.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Text(
                text = "OFFICIAL VERIFICATION",
                style = MaterialTheme.typography.labelMedium,
                color = GeoGoldContainer,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )

              // QR Code Graphic Simulation
              Box(
                modifier = Modifier
                  .size(120.dp)
                  .clip(RoundedCornerShape(16.dp))
                  .background(Color.White)
                  .padding(8.dp),
                contentAlignment = Alignment.Center
              ) {
                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
                ) {
                  Icon(
                    Icons.Filled.QrCode2,
                    contentDescription = "QR Code",
                    tint = GeoDarkHeader,
                    modifier = Modifier.size(90.dp)
                  )
                  Text(
                    text = "ID: ${member.memberCode}",
                    style = MaterialTheme.typography.labelSmall,
                    color = GeoDarkHeader,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }

              Text(
                text = "Scan to verify at gmys.org/verify/member/${member.memberCode}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                fontSize = 10.sp
              )

              Divider(color = Color.White.copy(alpha = 0.2f))

              Text(
                text = org.headOffice,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                fontSize = 10.sp
              )

              Text(
                text = "Valid Until: ${member.validUntil} • Helpline: ${org.phone.take(15)}",
                style = MaterialTheme.typography.labelSmall,
                color = GeoGoldContainer,
                fontSize = 9.sp
              )
            }
          }
        }

        Text(
          text = "👆 Tap card to flip Front / Back",
          style = MaterialTheme.typography.bodySmall,
          color = GeoTextMuted
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = { showBack = !showBack },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.weight(1f)
          ) {
            Icon(Icons.Filled.FlipCameraAndroid, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Flip")
          }
          Button(
            onClick = onDismiss,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GeoPrimary),
            modifier = Modifier.weight(1f)
          ) {
            Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Share")
          }
        }
      }
    }
  }
}

/**
 * Official 80G Tax Exemption Donation Receipt Dialog:
 */
@Composable
fun GeoReceiptDialog(
  donation: Donation,
  org: OrganizationProfile,
  onDismiss: () -> Unit
) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      shape = RoundedCornerShape(28.dp),
      color = MaterialTheme.colorScheme.surface,
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .padding(14.dp)
    ) {
      Column(
        modifier = Modifier
          .padding(18.dp)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Receipt Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(GeoPrimary),
              contentAlignment = Alignment.Center
            ) {
              Text("G", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Column {
              Text(
                text = org.acronym,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = GeoPrimary
              )
              Text(
                text = "Donation Receipt",
                style = MaterialTheme.typography.labelSmall,
                color = GeoTextMuted
              )
            }
          }

          IconButton(onClick = onDismiss) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
          }
        }

        // Receipt Content Box (Styled like official paper voucher)
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = GeoCardBgSubtle,
          border = BorderStroke(1.dp, GeoBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Text(
              text = org.name.uppercase(),
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = GeoDarkHeader,
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth()
            )
            Text(
              text = "80G Exemption No: ${org.section80G}\nPAN: ${org.panNumber} • 12A Reg: ${org.section12A}",
              style = MaterialTheme.typography.bodySmall,
              color = GeoTextMuted,
              fontSize = 9.sp,
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth()
            )

            Divider(color = GeoBorder)

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text("Receipt Number", style = MaterialTheme.typography.labelSmall, color = GeoTextMuted)
                Text(donation.receiptNumber, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = GeoPrimary)
              }
              Column(horizontalAlignment = Alignment.End) {
                Text("Date", style = MaterialTheme.typography.labelSmall, color = GeoTextMuted)
                Text(donation.date, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
              }
            }

            Divider(color = GeoBorder)

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("Received With Thanks From:", style = MaterialTheme.typography.labelSmall, color = GeoTextMuted)
              Text(donation.donorName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GeoDarkHeader)
              Text("Contact: ${donation.mobile}", style = MaterialTheme.typography.bodySmall, color = GeoTextMuted)
              if (donation.panNumber.isNotEmpty()) {
                Text("Donor PAN: ${donation.panNumber}", style = MaterialTheme.typography.bodySmall, color = GeoPrimary, fontWeight = FontWeight.SemiBold)
              }
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = GeoPrimaryContainer,
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text("Amount Received", style = MaterialTheme.typography.labelSmall, color = GeoPrimary)
                Text(
                  text = "₹" + String.format("%,.2f", donation.amount),
                  style = MaterialTheme.typography.headlineMedium,
                  fontWeight = FontWeight.Bold,
                  color = GeoPrimary
                )
                Text(
                  text = donation.amountInWords,
                  style = MaterialTheme.typography.bodySmall,
                  fontWeight = FontWeight.Medium,
                  color = GeoDarkHeader
                )
              }
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
              Text("Purpose / Campaign", style = MaterialTheme.typography.labelSmall, color = GeoTextMuted)
              Text(donation.purpose, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
              Text("Payment Mode: ${donation.paymentMethod.label} (${donation.transactionRef})", style = MaterialTheme.typography.bodySmall, color = GeoTextMuted, fontSize = 10.sp)
            }

            Divider(color = GeoBorder)

            // Authorized Sign & QR
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(60.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(Color.White)
                  .border(1.dp, GeoBorder, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Filled.QrCode2, contentDescription = null, tint = GeoDarkHeader, modifier = Modifier.size(50.dp))
              }

              Column(horizontalAlignment = Alignment.End) {
                Text("Authorized Signatory", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = GeoDarkHeader)
                Text("Hon. Treasurer / GMYS", style = MaterialTheme.typography.bodySmall, color = GeoTextMuted, fontSize = 10.sp)
                Text("Digitally Verified ✓", style = MaterialTheme.typography.labelSmall, color = GeoSuccess, fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.weight(1f)
          ) {
            Icon(Icons.Filled.Print, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Print PDF")
          }
          Button(
            onClick = onDismiss,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GeoPrimary),
            modifier = Modifier.weight(1f)
          ) {
            Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("WhatsApp")
          }
        }
      }
    }
  }
}
