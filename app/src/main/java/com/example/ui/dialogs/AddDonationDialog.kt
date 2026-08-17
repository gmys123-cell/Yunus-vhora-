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
import com.example.model.Donation
import com.example.model.PaymentMode
import com.example.ui.theme.GeoPrimary
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDonationDialog(
  onDismiss: () -> Unit
) {
  var donorName by remember { mutableStateOf("") }
  var amountStr by remember { mutableStateOf("") }
  var mobile by remember { mutableStateOf("+91 ") }
  var panNumber by remember { mutableStateOf("") }
  var purpose by remember { mutableStateOf("Education Scholarship 2026") }
  var campaign by remember { mutableStateOf("Education Scholarship 2026") }
  var paymentMode by remember { mutableStateOf(PaymentMode.UPI) }

  val campaigns = listOf(
    "Education Scholarship 2026",
    "Surat Health Camp",
    "Emergency Relief Kit",
    "Yuva Skill Hub",
    "Infrastructure Fund",
    "General Welfare"
  )

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
            text = "Record Donation & 80G",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = GeoPrimary
          )
          IconButton(onClick = onDismiss) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
          }
        }

        OutlinedTextField(
          value = donorName,
          onValueChange = { donorName = it },
          label = { Text("Donor Name / Entity") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        OutlinedTextField(
          value = amountStr,
          onValueChange = { amountStr = it },
          label = { Text("Amount (₹ INR)") },
          placeholder = { Text("e.g. 10000") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            label = { Text("Mobile") },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.weight(1f),
            singleLine = true
          )
          OutlinedTextField(
            value = panNumber,
            onValueChange = { panNumber = it.uppercase() },
            label = { Text("PAN (for 80G)") },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.weight(1f),
            singleLine = true
          )
        }

        OutlinedTextField(
          value = purpose,
          onValueChange = {
            purpose = it
            campaign = it
          },
          label = { Text("Donation Purpose / Campaign") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        Text("Payment Mode", style = MaterialTheme.typography.labelSmall, color = GeoPrimary)

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          PaymentMode.values().take(3).forEach { mode ->
            val isSelected = paymentMode == mode
            FilterChip(
              selected = isSelected,
              onClick = { paymentMode = mode },
              label = { Text(mode.name, style = MaterialTheme.typography.labelSmall) },
              shape = RoundedCornerShape(10.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
          onClick = {
            val amount = amountStr.toDoubleOrNull() ?: 0.0
            if (donorName.isNotBlank() && amount > 0) {
              val dateFormatted = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(Date())
              val randomCode = "DON-2026-" + String.format("%06d", (100..99999).random())
              val recCode = "GMYS-REC-2026-" + String.format("%04d", (100..9999).random())

              val newDonation = Donation(
                donationCode = randomCode,
                receiptNumber = recCode,
                donorName = donorName,
                mobile = mobile,
                panNumber = panNumber,
                amount = amount,
                paymentMethod = paymentMode,
                date = dateFormatted,
                purpose = purpose,
                campaign = campaign,
                is80GEligible = true
              )

              GmysDataRepository.addDonation(newDonation)
              onDismiss()
            }
          },
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = GeoPrimary),
          modifier = Modifier.fillMaxWidth(),
          contentPadding = PaddingValues(vertical = 12.dp)
        ) {
          Text("Generate 80G Receipt", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
