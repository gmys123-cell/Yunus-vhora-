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
import com.example.model.PaymentMode
import com.example.model.TransactionRecord
import com.example.model.TxnType
import com.example.ui.theme.GeoPrimary
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionDialog(
  onDismiss: () -> Unit
) {
  var txnType by remember { mutableStateOf(TxnType.EXPENSE) }
  var category by remember { mutableStateOf("Medical Aid & Camp") }
  var amountStr by remember { mutableStateOf("") }
  var account by remember { mutableStateOf("SBI Main A/C 9842") }
  var description by remember { mutableStateOf("") }
  var paymentMode by remember { mutableStateOf(PaymentMode.BANK_TRANSFER) }

  val categories = listOf(
    "Medical Aid & Camp",
    "Education Scholarship",
    "Food & Ration Relief",
    "Youth Vocational Training",
    "Office & Stationery",
    "Event / Conference",
    "Donation / 80G",
    "Member Subscription"
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
            text = "Record Voucher / Transaction",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = GeoPrimary
          )
          IconButton(onClick = onDismiss) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
          }
        }

        // Type Selector
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          listOf(TxnType.EXPENSE, TxnType.INCOME).forEach { type ->
            val isSelected = txnType == type
            FilterChip(
              selected = isSelected,
              onClick = { txnType = type },
              label = { Text(type.name, style = MaterialTheme.typography.labelSmall) },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.weight(1f)
            )
          }
        }

        OutlinedTextField(
          value = category,
          onValueChange = { category = it },
          label = { Text("Category / Head of Account") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        OutlinedTextField(
          value = amountStr,
          onValueChange = { amountStr = it },
          label = { Text("Amount (₹ INR)") },
          placeholder = { Text("e.g. 5000") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        OutlinedTextField(
          value = account,
          onValueChange = { account = it },
          label = { Text("Account (Bank / Cash)") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          label = { Text("Description / Particulars") },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
          onClick = {
            val amount = amountStr.toDoubleOrNull() ?: 0.0
            if (amount > 0 && description.isNotBlank()) {
              val dateFormatted = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(Date())
              val randomCode = "TXN-2026-" + String.format("%04d", (100..9999).random())

              val newTxn = TransactionRecord(
                code = randomCode,
                type = txnType,
                category = category,
                amount = amount,
                account = account,
                description = description,
                date = dateFormatted,
                paymentMethod = paymentMode
              )

              GmysDataRepository.addTransaction(newTxn)
              onDismiss()
            }
          },
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = GeoPrimary),
          modifier = Modifier.fillMaxWidth(),
          contentPadding = PaddingValues(vertical = 12.dp)
        ) {
          Text("Post Ledger Voucher", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
