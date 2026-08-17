package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ui.components.GeoBottomNavBar
import com.example.ui.components.GeoNavTab
import com.example.ui.dialogs.AddDonationDialog
import com.example.ui.dialogs.AddMemberDialog
import com.example.ui.dialogs.AddTransactionDialog
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        GmysApp()
      }
    }
  }
}

@Composable
fun GmysApp() {
  var currentTab by remember { mutableStateOf(GeoNavTab.HOME) }
  var showAddMember by remember { mutableStateOf(false) }
  var showAddDonation by remember { mutableStateOf(false) }
  var showAddTransaction by remember { mutableStateOf(false) }
  var showVerificationScreen by remember { mutableStateOf(false) }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
      if (!showVerificationScreen) {
        GeoBottomNavBar(
          currentTab = currentTab,
          onTabSelected = { currentTab = it }
        )
      }
    },
    contentWindowInsets = WindowInsets.statusBars
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      if (showVerificationScreen) {
        VerificationScreen(
          onDismiss = { showVerificationScreen = false }
        )
      } else {
        when (currentTab) {
          GeoNavTab.HOME -> HomeScreen(
            onNavigateToTab = { currentTab = it },
            onOpenAddDonation = { showAddDonation = true },
            onOpenAddMember = { showAddMember = true },
            onOpenVerification = { showVerificationScreen = true }
          )

          GeoNavTab.MEMBERS -> MembersScreen(
            onOpenAddMember = { showAddMember = true }
          )

          GeoNavTab.DONATIONS -> DonationsScreen(
            onOpenAddDonation = { showAddDonation = true }
          )

          GeoNavTab.ACCOUNTS -> AccountsScreen(
            onOpenAddTransaction = { showAddTransaction = true }
          )

          GeoNavTab.PROJECTS -> ProjectsScreen()
        }
      }
    }
  }

  // Active Modals
  if (showAddMember) {
    AddMemberDialog(onDismiss = { showAddMember = false })
  }

  if (showAddDonation) {
    AddDonationDialog(onDismiss = { showAddDonation = false })
  }

  if (showAddTransaction) {
    AddTransactionDialog(onDismiss = { showAddTransaction = false })
  }
}
