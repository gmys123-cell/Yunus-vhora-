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
import com.example.model.TransactionRecord
import com.example.model.TxnType
import com.example.ui.components.GeoHeader
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
  onOpenAddTransaction: () -> Unit,
  modifier: Modifier = Modifier
) {
  val transactions by GmysDataRepository.transactions.collectAsState()

  var selectedFilter by remember { mutableStateOf("All") }

  val totalIncome = transactions.filter { it.type == TxnType.INCOME }.sumOf { it.amount }
  val totalExpense = transactions.filter { it.type == TxnType.EXPENSE }.sumOf { it.amount }
  val netBalance = totalIncome - totalExpense

  val filteredTransactions = transactions.filter { txn ->
    when (selectedFilter) {
      "Income" -> txn.type == TxnType.INCOME
      "Expense" -> txn.type == TxnType.EXPENSE
      else -> true
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    GeoHeader(
      roleSubtitle = "Accounting & Cashbook",
      userName = "GMYS Accounts",
      initials = "AC"
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Cashbook Overview Card
      Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "AVAILABLE CLOSING BALANCE",
                style = MaterialTheme.typography.labelSmall,
                color = GeoTextMuted,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Text(
                text = "₹" + String.format("%,.2f", netBalance),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = GeoPrimary
              )
            }

            Surface(
              shape = CircleShape,
              color = GeoPrimaryContainer
            ) {
              Text(
                text = "Reconciled",
                style = MaterialTheme.typography.labelSmall,
                color = GeoPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }

          Divider(color = MaterialTheme.colorScheme.outline)

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text("Total Income", style = MaterialTheme.typography.labelSmall, color = GeoTextMuted)
              Text(
                text = "+₹" + String.format("%,.0f", totalIncome),
                style = MaterialTheme.typography.titleSmall,
                color = GeoSuccess,
                fontWeight = FontWeight.Bold
              )
            }
            Column(horizontalAlignment = Alignment.End) {
              Text("Total Expenses", style = MaterialTheme.typography.labelSmall, color = GeoTextMuted)
              Text(
                text = "-₹" + String.format("%,.0f", totalExpense),
                style = MaterialTheme.typography.titleSmall,
                color = GeoError,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }

      // Filter Tabs & Record Button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          listOf("All", "Income", "Expense").forEach { tab ->
            val isSelected = selectedFilter == tab
            FilterChip(
              selected = isSelected,
              onClick = { selectedFilter = tab },
              label = { Text(tab, style = MaterialTheme.typography.labelSmall) },
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

        Button(
          onClick = onOpenAddTransaction,
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = GeoPrimary),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Voucher", style = MaterialTheme.typography.labelMedium)
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Transactions Ledger List
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(bottom = 24.dp)
    ) {
      items(filteredTransactions, key = { it.id }) { txn ->
        TransactionCardItem(txn = txn)
      }
    }
  }
}

@Composable
private fun TransactionCardItem(txn: TransactionRecord) {
  val isIncome = txn.type == TxnType.INCOME

  Surface(
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
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(if (isIncome) GeoPrimaryContainer else Color(0xFFFFE4E6)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (isIncome) Icons.Filled.ArrowDownward else Icons.Filled.ArrowUpward,
          contentDescription = null,
          tint = if (isIncome) GeoPrimary else GeoError,
          modifier = Modifier.size(20.dp)
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = txn.category,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
          )

          Text(
            text = (if (isIncome) "+₹" else "-₹") + String.format("%,.0f", txn.amount),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = if (isIncome) GeoSuccess else GeoError
          )
        }

        Text(
          text = txn.description,
          style = MaterialTheme.typography.bodySmall,
          color = GeoTextMuted,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "${txn.voucherNumber} • ${txn.date}",
            style = MaterialTheme.typography.labelSmall,
            color = GeoTextMuted
          )
          Text(
            text = txn.account,
            style = MaterialTheme.typography.labelSmall,
            color = GeoPrimary,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }
  }
}
