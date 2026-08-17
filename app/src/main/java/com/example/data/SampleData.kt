package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object GmysDataRepository {
  val organization = OrganizationProfile()

  private val _members = MutableStateFlow(
    listOf(
      Member(
        memberCode = "GMYS-2024-0012",
        fullName = "Mohammed Ali",
        guardianName = "Ghulam Nabi",
        designation = "State Secretary",
        district = "Ahmedabad",
        taluka = "Ahmedabad City",
        bloodGroup = "B+",
        mobile = "+91 98251 44552",
        email = "mohammed.ali@gmys.org",
        membershipType = MembershipType.EXECUTIVE,
        status = MemberStatus.ACTIVE,
        joiningDate = "10 Jan 2021",
        validUntil = "31 Dec 2026",
        isExecutive = true
      ),
      Member(
        memberCode = "GMYS-2024-0001",
        fullName = "Zaid Mansuri",
        guardianName = "Ibrahim Mansuri",
        designation = "President (GMYS)",
        district = "Ahmedabad",
        taluka = "Daskroi",
        bloodGroup = "O+",
        mobile = "+91 94260 88910",
        email = "president@gmys.org",
        membershipType = MembershipType.EXECUTIVE,
        status = MemberStatus.ACTIVE,
        joiningDate = "01 Jan 2018",
        validUntil = "31 Dec 2027",
        isExecutive = true
      ),
      Member(
        memberCode = "GMYS-2024-0045",
        fullName = "Farhan Patel",
        guardianName = "Abdul Rahim Patel",
        designation = "District Coordinator",
        district = "Surat",
        taluka = "Choryasi",
        bloodGroup = "A+",
        mobile = "+91 97230 11928",
        email = "surat.lead@gmys.org",
        membershipType = MembershipType.EXECUTIVE,
        status = MemberStatus.ACTIVE,
        joiningDate = "15 Mar 2022",
        validUntil = "31 Dec 2026",
        isExecutive = true
      ),
      Member(
        memberCode = "GMYS-2024-0089",
        fullName = "Amina Khatun",
        guardianName = "Dr. Tariq Anwar",
        designation = "Education Wing Lead",
        district = "Vadodara",
        taluka = "Vadodara Urban",
        bloodGroup = "AB+",
        mobile = "+91 99040 33412",
        email = "amina.edu@gmys.org",
        membershipType = MembershipType.EXECUTIVE,
        status = MemberStatus.ACTIVE,
        joiningDate = "05 Aug 2022",
        validUntil = "31 Dec 2026",
        isExecutive = true
      ),
      Member(
        memberCode = "GMYS-2025-0156",
        fullName = "Bilal Qureshi",
        guardianName = "Yusuf Qureshi",
        designation = "Youth Volunteer Lead",
        district = "Bharuch",
        taluka = "Ankleshwar",
        bloodGroup = "O-",
        mobile = "+91 98980 77654",
        email = "bilal.bharuch@gmys.org",
        membershipType = MembershipType.VOLUNTEER,
        status = MemberStatus.ACTIVE,
        joiningDate = "12 Feb 2025",
        validUntil = "31 Dec 2026"
      ),
      Member(
        memberCode = "GMYS-2025-0210",
        fullName = "Suhail Vhora",
        guardianName = "Iqbal Vhora",
        designation = "Field Worker & Relief Member",
        district = "Anand",
        taluka = "Khambhat",
        bloodGroup = "B-",
        mobile = "+91 98790 66543",
        email = "suhail.anand@gmys.org",
        membershipType = MembershipType.GENERAL,
        status = MemberStatus.ACTIVE,
        joiningDate = "20 Apr 2025",
        validUntil = "31 Dec 2026"
      ),
      Member(
        memberCode = "GMYS-2025-0320",
        fullName = "Rashid Memon",
        guardianName = "Suleman Memon",
        designation = "Medical Relief Coordinator",
        district = "Rajkot",
        taluka = "Rajkot City",
        bloodGroup = "O+",
        mobile = "+91 94080 22110",
        email = "rashid.rajkot@gmys.org",
        membershipType = MembershipType.LIFE_MEMBER,
        status = MemberStatus.ACTIVE,
        joiningDate = "10 Jun 2023",
        validUntil = "31 Dec 2028"
      ),
      Member(
        memberCode = "GMYS-2026-0412",
        fullName = "Nasir Pathan",
        guardianName = "Ghaffar Pathan",
        designation = "Volunteer",
        district = "Bhavnagar",
        taluka = "Bhavnagar",
        bloodGroup = "A-",
        mobile = "+91 96010 44556",
        email = "nasir.bhav@gmys.org",
        membershipType = MembershipType.VOLUNTEER,
        status = MemberStatus.ACTIVE,
        joiningDate = "02 Jan 2026",
        validUntil = "31 Dec 2026"
      )
    )
  )
  val members: StateFlow<List<Member>> = _members.asStateFlow()

  private val _donations = MutableStateFlow(
    listOf(
      Donation(
        donationCode = "DON-2026-000104",
        receiptNumber = "GMYS-REC-2026-0104",
        donorName = "Haji Usman Abdul Gani",
        mobile = "+91 98240 55661",
        email = "usman.gani@gmail.com",
        panNumber = "ABCDE1234F",
        amount = 50000.0,
        paymentMethod = PaymentMode.UPI,
        transactionRef = "UPI/489201948291/UTIB",
        date = "15 Aug 2026",
        purpose = "Education Scholarship Fund for Underprivileged Students",
        campaign = "Education Scholarship 2026",
        status = DonationStatus.VERIFIED,
        is80GEligible = true
      ),
      Donation(
        donationCode = "DON-2026-000103",
        receiptNumber = "GMYS-REC-2026-0103",
        donorName = "Dr. Munir Shaikh",
        mobile = "+91 98980 11223",
        email = "drmunir@shaikhclinic.com",
        panNumber = "BKUPS9042K",
        amount = 25000.0,
        paymentMethod = PaymentMode.BANK_TRANSFER,
        transactionRef = "NEFT/SBIN894201934",
        date = "12 Aug 2026",
        purpose = "Free Health & Eye Checkup Camp Surat",
        campaign = "Surat Health Camp",
        status = DonationStatus.VERIFIED,
        is80GEligible = true
      ),
      Donation(
        donationCode = "DON-2026-000102",
        receiptNumber = "GMYS-REC-2026-0102",
        donorName = "Parvez Bhai Fancy",
        mobile = "+91 97240 77889",
        panNumber = "AAMFP4410H",
        amount = 15000.0,
        paymentMethod = PaymentMode.UPI,
        transactionRef = "UPI/412093849182/HDFC",
        date = "08 Aug 2026",
        purpose = "Ramadan & Flood Emergency Ration Distribution",
        campaign = "Emergency Relief Kit",
        status = DonationStatus.VERIFIED,
        is80GEligible = true
      ),
      Donation(
        donationCode = "DON-2026-000101",
        receiptNumber = "GMYS-REC-2026-0101",
        donorName = "Anwar Husain Vohra",
        mobile = "+91 94280 44332",
        amount = 10000.0,
        paymentMethod = PaymentMode.CASH,
        transactionRef = "CASH-REC-00101",
        date = "03 Aug 2026",
        purpose = "General Youth Welfare & Skill Training Center",
        campaign = "Yuva Skill Hub",
        status = DonationStatus.VERIFIED,
        is80GEligible = true
      ),
      Donation(
        donationCode = "DON-2026-000100",
        receiptNumber = "GMYS-REC-2026-0100",
        donorName = "Shabbir Ahmed Merchant",
        mobile = "+91 98252 99001",
        amount = 100000.0,
        paymentMethod = PaymentMode.BANK_TRANSFER,
        transactionRef = "RTGS/BARB893021948",
        date = "28 Jul 2026",
        purpose = "Community Center Renovation & Solar Infrastructure",
        campaign = "Infrastructure Fund",
        status = DonationStatus.VERIFIED,
        is80GEligible = true
      )
    )
  )
  val donations: StateFlow<List<Donation>> = _donations.asStateFlow()

  private val _transactions = MutableStateFlow(
    listOf(
      TransactionRecord(
        code = "TXN-2026-0089",
        type = TxnType.INCOME,
        category = "Donation / 80G",
        amount = 50000.0,
        account = "SBI Main A/C 9842",
        description = "Donation received from Haji Usman for Education Fund",
        date = "15 Aug 2026",
        paymentMethod = PaymentMode.UPI
      ),
      TransactionRecord(
        code = "TXN-2026-0088",
        type = TxnType.EXPENSE,
        category = "Medical & Relief",
        amount = 18500.0,
        account = "SBI Main A/C 9842",
        description = "Surat Mega Health Camp - Medicines & Diagnostic Kits",
        date = "14 Aug 2026",
        paymentMethod = PaymentMode.BANK_TRANSFER,
        approvedBy = "President (GMYS)"
      ),
      TransactionRecord(
        code = "TXN-2026-0087",
        type = TxnType.EXPENSE,
        category = "Education Aid",
        amount = 32000.0,
        account = "SBI Main A/C 9842",
        description = "Direct fee subsidy for 16 matriculate students",
        date = "10 Aug 2026",
        paymentMethod = PaymentMode.BANK_TRANSFER,
        approvedBy = "Treasurer (GMYS)"
      ),
      TransactionRecord(
        code = "TXN-2026-0086",
        type = TxnType.EXPENSE,
        category = "Office & Administrative",
        amount = 4500.0,
        account = "Office Cash",
        description = "ID Card Lamination, stationery & regional dispatch",
        date = "05 Aug 2026",
        paymentMethod = PaymentMode.CASH,
        approvedBy = "Secretary (GMYS)"
      ),
      TransactionRecord(
        code = "TXN-2026-0085",
        type = TxnType.INCOME,
        category = "Donation / 80G",
        amount = 25000.0,
        account = "SBI Main A/C 9842",
        description = "Dr. Munir Shaikh donation for Health Camp",
        date = "12 Aug 2026",
        paymentMethod = PaymentMode.BANK_TRANSFER
      )
    )
  )
  val transactions: StateFlow<List<TransactionRecord>> = _transactions.asStateFlow()

  private val _projects = MutableStateFlow(
    listOf(
      ProjectRecord(
        code = "PRJ-2026-01",
        name = "Education Scholarship 2026",
        category = "Education & Higher Studies",
        initialLetter = "E",
        budget = 500000.0,
        actualExpense = 280000.0,
        targetBeneficiaries = 600,
        achievedBeneficiaries = 450,
        district = "Statewide Gujarat",
        location = "Ahmedabad, Vadodara, Surat, Rajkot",
        status = ProjectStatus.ACTIVE,
        manager = "Amina Khatun",
        volunteerCount = 28,
        accentColorCode = "emerald"
      ),
      ProjectRecord(
        code = "PRJ-2026-02",
        name = "Health Camp - Surat District",
        category = "Free Healthcare & Eye Care",
        initialLetter = "H",
        budget = 150000.0,
        actualExpense = 92000.0,
        targetBeneficiaries = 1200,
        achievedBeneficiaries = 840,
        district = "Surat",
        location = "Rander & Limayat Centers",
        status = ProjectStatus.ACTIVE,
        manager = "Farhan Patel",
        volunteerCount = 12,
        accentColorCode = "amber"
      ),
      ProjectRecord(
        code = "PRJ-2026-03",
        name = "Yuva Skill Hub & Computer Lab",
        category = "Vocational & Digital Skills",
        initialLetter = "S",
        budget = 350000.0,
        actualExpense = 210000.0,
        targetBeneficiaries = 300,
        achievedBeneficiaries = 195,
        district = "Ahmedabad",
        location = "Relief Road Youth Center",
        status = ProjectStatus.ACTIVE,
        manager = "Zaid Mansuri",
        volunteerCount = 15,
        accentColorCode = "emerald"
      ),
      ProjectRecord(
        code = "PRJ-2026-04",
        name = "Monsoon Flood Rapid Relief",
        category = "Emergency Disaster Response",
        initialLetter = "R",
        budget = 200000.0,
        actualExpense = 175000.0,
        targetBeneficiaries = 1000,
        achievedBeneficiaries = 980,
        district = "Bharuch & Narmada",
        location = "Ankleshwar Low-lying Wards",
        status = ProjectStatus.COMPLETED,
        manager = "Bilal Qureshi",
        volunteerCount = 34,
        accentColorCode = "amber"
      )
    )
  )
  val projects: StateFlow<List<ProjectRecord>> = _projects.asStateFlow()

  fun addMember(member: Member) {
    _members.update { listOf(member) + it }
  }

  fun addDonation(donation: Donation) {
    _donations.update { listOf(donation) + it }
    // Auto post to transactions
    val newTxn = TransactionRecord(
      code = "TXN-2026-0" + (_transactions.value.size + 90),
      type = TxnType.INCOME,
      category = "Donation / 80G",
      amount = donation.amount,
      account = if (donation.paymentMethod == PaymentMode.CASH) "Office Cash" else "SBI Main A/C 9842",
      description = "Donation received from ${donation.donorName} (${donation.purpose})",
      date = donation.date,
      paymentMethod = donation.paymentMethod
    )
    _transactions.update { listOf(newTxn) + it }
  }

  fun addTransaction(txn: TransactionRecord) {
    _transactions.update { listOf(txn) + it }
  }
}
