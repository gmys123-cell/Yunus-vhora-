package com.example.model

import java.util.UUID

enum class MembershipType(val label: String) {
  GENERAL("General Member"),
  VOLUNTEER("Volunteer"),
  LIFE_MEMBER("Life Member"),
  EXECUTIVE("Executive Member"),
  DONOR("Donor / Patron"),
  SUPPORTER("Supporter")
}

enum class MemberStatus(val label: String) {
  ACTIVE("Active"),
  PENDING("Pending"),
  INACTIVE("Inactive"),
  SUSPENDED("Suspended")
}

data class Member(
  val id: String = UUID.randomUUID().toString(),
  val memberCode: String,
  val fullName: String,
  val guardianName: String,
  val designation: String,
  val district: String,
  val taluka: String,
  val bloodGroup: String,
  val mobile: String,
  val email: String,
  val membershipType: MembershipType = MembershipType.GENERAL,
  val status: MemberStatus = MemberStatus.ACTIVE,
  val joiningDate: String = "15 Jan 2024",
  val validUntil: String = "31 Dec 2026",
  val qrToken: String = UUID.randomUUID().toString().take(12),
  val isExecutive: Boolean = false,
  val avatarInitials: String = fullName.split(" ").take(2).mapNotNull { it.firstOrNull()?.toString() }.joinToString("")
)

enum class PaymentMode(val label: String) {
  UPI("UPI / QR"),
  BANK_TRANSFER("Bank Transfer (NEFT/RTGS)"),
  CASH("Cash"),
  CHEQUE("Cheque"),
  ONLINE("Online Gateway")
}

enum class DonationStatus {
  VERIFIED, PENDING, CANCELLED
}

data class Donation(
  val id: String = UUID.randomUUID().toString(),
  val donationCode: String,
  val receiptNumber: String,
  val donorName: String,
  val mobile: String,
  val email: String = "",
  val panNumber: String = "",
  val amount: Double,
  val paymentMethod: PaymentMode,
  val transactionRef: String = "UPI-" + UUID.randomUUID().toString().take(8).uppercase(),
  val date: String,
  val purpose: String,
  val campaign: String,
  val status: DonationStatus = DonationStatus.VERIFIED,
  val is80GEligible: Boolean = true,
  val qrVerification: String = "VER-REC-" + UUID.randomUUID().toString().take(8).uppercase()
) {
  val amountInWords: String get() = convertNumberToWords(amount.toLong()) + " Rupees Only"
}

enum class TxnType(val label: String) {
  INCOME("Income"),
  EXPENSE("Expense"),
  TRANSFER("Bank Transfer")
}

data class TransactionRecord(
  val id: String = UUID.randomUUID().toString(),
  val code: String,
  val type: TxnType,
  val category: String,
  val amount: Double,
  val account: String, // e.g. "SBI A/C ...9842" or "Office Cash"
  val description: String,
  val date: String,
  val paymentMethod: PaymentMode,
  val voucherNumber: String = "VCH-" + UUID.randomUUID().toString().take(6).uppercase(),
  val approvedBy: String = "Treasurer (GMYS)"
)

enum class ProjectStatus(val label: String) {
  ACTIVE("Active"),
  PLANNING("Planning"),
  COMPLETED("Completed")
}

data class ProjectRecord(
  val id: String = UUID.randomUUID().toString(),
  val code: String,
  val name: String,
  val category: String,
  val initialLetter: String,
  val budget: Double,
  val actualExpense: Double,
  val targetBeneficiaries: Int,
  val achievedBeneficiaries: Int,
  val district: String,
  val location: String,
  val status: ProjectStatus = ProjectStatus.ACTIVE,
  val manager: String,
  val volunteerCount: Int,
  val accentColorCode: String // "emerald" or "amber"
)

data class BeneficiaryRecord(
  val id: String = UUID.randomUUID().toString(),
  val code: String,
  val name: String,
  val mobile: String,
  val district: String,
  val taluka: String,
  val needType: String,
  val approvedAmount: Double,
  val status: String = "Approved & Aided",
  val projectAssigned: String
)

data class OrganizationProfile(
  val name: String = "Gujarat Muslim Yuva Sangathan",
  val acronym: String = "GMYS",
  val regNumber: String = "GUJ/TR/8942/2014",
  val panNumber: String = "AAATG8942F",
  val section80G: String = "CIT(E)/80G/2021-22/A/1049",
  val section12A: String = "CIT(E)/12A/2019-20/Del/9842",
  val csrNumber: String = "CSR00049281",
  val headOffice: String = "GMYS Central Bhavan, Relief Road, Ahmedabad, Gujarat - 380001",
  val phone: String = "+91 79 2538 9000 / +91 98250 12345",
  val email: String = "contact@gmys.org",
  val website: String = "https://gmys.org",
  val bankName: String = "State Bank of India (SBI)",
  val accountNumber: String = "389201948291",
  val ifscCode: String = "SBIN0001048",
  val upiId: String = "gmys.relief@sbi"
)

fun convertNumberToWords(num: Long): String {
  if (num == 0L) return "Zero"
  val units = arrayOf(
    "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
    "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
  )
  val tens = arrayOf(
    "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
  )

  fun helper(n: Long): String {
    return when {
      n < 20 -> units[n.toInt()]
      n < 100 -> tens[(n / 10).toInt()] + (if (n % 10 != 0L) " " + units[(n % 10).toInt()] else "")
      n < 1000 -> units[(n / 100).toInt()] + " Hundred" + (if (n % 100 != 0L) " " + helper(n % 100) else "")
      n < 100000 -> helper(n / 1000) + " Thousand" + (if (n % 1000 != 0L) " " + helper(n % 1000) else "")
      n < 10000000 -> helper(n / 100000) + " Lakh" + (if (n % 100000 != 0L) " " + helper(n % 100000) else "")
      else -> helper(n / 10000000) + " Crore" + (if (n % 10000000 != 0L) " " + helper(n % 10000000) else "")
    }
  }

  return helper(num).trim()
}
