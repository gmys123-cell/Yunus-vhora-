package com.example.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.GmysDataRepository
import com.example.model.Member
import com.example.model.MemberStatus
import com.example.model.MembershipType
import com.example.ui.theme.GeoPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemberDialog(
  onDismiss: () -> Unit
) {
  var fullName by remember { mutableStateOf("") }
  var guardianName by remember { mutableStateOf("") }
  var designation by remember { mutableStateOf("Volunteer") }
  var district by remember { mutableStateOf("Ahmedabad") }
  var taluka by remember { mutableStateOf("City") }
  var bloodGroup by remember { mutableStateOf("B+") }
  var mobile by remember { mutableStateOf("+91 ") }
  var email by remember { mutableStateOf("") }
  var selectedType by remember { mutableStateOf(MembershipType.GENERAL) }

  val districts = listOf("Ahmedabad", "Surat", "Vadodara", "Bharuch", "Anand", "Rajkot", "Bhavnagar", "Jamnagar", "Godhra")
  val bloodGroups = listOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      shape = RoundedCornerShape(28.dp),
      color = MaterialTheme.colorScheme.surface,
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .padding(16.dp)
    ) {
      Column(
        modifier = Modifier
          .padding(20.dp)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Register GMYS Member",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = GeoPrimary
          )
          IconButton(onClick = onDismiss) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
          }
        }

        OutlinedTextField(
          value = fullName,
          onValueChange = { fullName = it },
          label = { Text("Full Name") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        OutlinedTextField(
          value = guardianName,
          onValueChange = { guardianName = it },
          label = { Text("Father / Guardian Name") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedTextField(
            value = designation,
            onValueChange = { designation = it },
            label = { Text("Designation") },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.weight(1f),
            singleLine = true
          )

          OutlinedTextField(
            value = bloodGroup,
            onValueChange = { bloodGroup = it },
            label = { Text("Blood Group") },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.weight(0.7f),
            singleLine = true
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedTextField(
            value = district,
            onValueChange = { district = it },
            label = { Text("District (Gujarat)") },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.weight(1f),
            singleLine = true
          )
          OutlinedTextField(
            value = taluka,
            onValueChange = { taluka = it },
            label = { Text("Taluka / City") },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.weight(1f),
            singleLine = true
          )
        }

        OutlinedTextField(
          value = mobile,
          onValueChange = { mobile = it },
          label = { Text("Mobile Number") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("Email Address (Optional)") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
          onClick = {
            if (fullName.isNotBlank()) {
              val randomCode = "GMYS-2026-" + String.format("%04d", (100..9999).random())
              val newMember = Member(
                memberCode = randomCode,
                fullName = fullName,
                guardianName = guardianName,
                designation = designation,
                district = district,
                taluka = taluka,
                bloodGroup = bloodGroup,
                mobile = mobile,
                email = email,
                membershipType = selectedType,
                status = MemberStatus.ACTIVE,
                isExecutive = designation.contains("President", ignoreCase = true) || designation.contains("Secretary", ignoreCase = true) || designation.contains("Lead", ignoreCase = true)
              )
              GmysDataRepository.addMember(newMember)
              onDismiss()
            }
          },
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = GeoPrimary),
          modifier = Modifier.fillMaxWidth(),
          contentPadding = PaddingValues(vertical = 12.dp)
        ) {
          Text("Issue Member ID Card", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
