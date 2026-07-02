package com.shardul.nprtracker.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.shardul.nprtracker.ui.theme.screens.CurrencyItem
import com.shardul.nprtracker.viewmodel.ForexState
import com.shardul.nprtracker.viewmodel.ForexViewModel

val currencyToCountry = mapOf(
    "AED" to "United Arab Emirates", "AFN" to "Afghanistan", "ALL" to "Albania", "AMD" to "Armenia", "ANG" to "Netherlands Antilles",
    "AOA" to "Angola", "ARS" to "Argentina", "AUD" to "Australia", "AWG" to "Aruba", "AZN" to "Azerbaijan", "BAM" to "Bosnia and Herzegovina",
    "BBD" to "Barbados", "BDT" to "Bangladesh", "BGN" to "Bulgaria", "BHD" to "Bahrain", "BIF" to "Burundi", "BMD" to "Bermuda",
    "BND" to "Brunei", "BOB" to "Bolivia", "BRL" to "Brazil", "BSD" to "Bahamas", "BTN" to "Bhutan", "BWP" to "Botswana",
    "BYN" to "Belarus", "BZD" to "Belize", "CAD" to "Canada", "CDF" to "Democratic Republic of the Congo", "CHF" to "Switzerland",
    "CLP" to "Chile", "CNY" to "China", "COP" to "Colombia", "CRC" to "Costa Rica", "CUP" to "Cuba", "CVE" to "Cape Verde",
    "CZK" to "Czech Republic", "DJF" to "Djibouti", "DKK" to "Denmark", "DOP" to "Dominican Republic", "DZD" to "Algeria",
    "EGP" to "Egypt", "ERN" to "Eritrea", "ETB" to "Ethiopia", "EUR" to "European Union", "FJD" to "Fiji", "FKP" to "Falkland Islands",
    "GBP" to "United Kingdom", "GEL" to "Georgia", "GGP" to "Guernsey", "GHS" to "Ghana", "GIP" to "Gibraltar", "GMD" to "The Gambia",
    "GNF" to "Guinea", "GTQ" to "Guatemala", "GYD" to "Guyana", "HKD" to "Hong Kong", "HNL" to "Honduras", "HRK" to "Croatia",
    "HTG" to "Haiti", "HUF" to "Hungary", "IDR" to "Indonesia", "ILS" to "Israel", "IMP" to "Isle of Man", "INR" to "India",
    "IQD" to "Iraq", "IRR" to "Iran", "ISK" to "Iceland", "JEP" to "Jersey", "JMD" to "Jamaica", "JOD" to "Jordan",
    "JPY" to "Japan", "KES" to "Kenya", "KGS" to "Kyrgyzstan", "KHR" to "Cambodia", "KMF" to "Comoros", "KRW" to "South Korea",
    "KWD" to "Kuwait", "KYD" to "Cayman Islands", "KZT" to "Kazakhstan", "LAK" to "Laos", "LBP" to "Lebanon", "LKR" to "Sri Lanka",
    "LRD" to "Liberia", "LSL" to "Lesotho", "LYD" to "Libya", "MAD" to "Morocco", "MDL" to "Moldova", "MGA" to "Madagascar",
    "MKD" to "North Macedonia", "MMK" to "Myanmar", "MNT" to "Mongolia", "MOP" to "Macau", "MRU" to "Mauritania", "MUR" to "Mauritius",
    "MVR" to "Maldives", "MWK" to "Malawi", "MXN" to "Mexico", "MYR" to "Malaysia", "MZN" to "Mozambique", "NAD" to "Namibia",
    "NGN" to "Nigeria", "NIO" to "Nicaragua", "NOK" to "Norway", "NPR" to "Nepal", "NZD" to "New Zealand", "OMR" to "Oman",
    "PAB" to "Panama", "PEN" to "Peru", "PGK" to "Papua New Guinea", "PHP" to "Philippines", "PKR" to "Pakistan", "PLN" to "Poland",
    "PYG" to "Paraguay", "QAR" to "Qatar", "RON" to "Romania", "RSD" to "Serbia", "RUB" to "Russia", "RWF" to "Rwanda",
    "SAR" to "Saudi Arabia", "SBD" to "Solomon Islands", "SCR" to "Seychelles", "SDG" to "Sudan", "SEK" to "Sweden",
    "SGD" to "Singapore", "SHP" to "Saint Helena", "SLL" to "Sierra Leone", "SOS" to "Somalia", "SRD" to "Suriname",
    "SSP" to "South Sudan", "STN" to "São Tomé and Príncipe", "SYP" to "Syria", "SZL" to "Eswatini", "THB" to "Thailand",
    "TJS" to "Tajikistan", "TMT" to "Turkmenistan", "TND" to "Tunisia", "TOP" to "Tonga", "TRY" to "Turkey", "TTD" to "Trinidad and Tobago",
    "TWD" to "Taiwan", "TZS" to "Tanzania", "UAH" to "Ukraine", "UGX" to "Uganda", "USD" to "United States", "UYU" to "Uruguay",
    "UZS" to "Uzbekistan", "VES" to "Venezuela", "VND" to "Vietnam", "VUV" to "Vanuatu", "WST" to "Samoa", "XAF" to "Central African CFA Franc",
    "XCD" to "East Caribbean Dollar", "XOF" to "West African CFA Franc", "XPF" to "CFP Franc", "YER" to "Yemen", "ZAR" to "South Africa",
    "ZMW" to "Zambia", "ZWL" to "Zimbabwe"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForexListScreen(viewModel: ForexViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("1.00") }

    val multiplier = amountInput.toDoubleOrNull() ?: 0.0

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Global Converter", fontWeight = FontWeight.Black) },
                actions = {
                    IconButton(onClick = { viewModel.fetchRates() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().background(MaterialTheme.colorScheme.surface)) {

            Card(
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Amount in USD", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    OutlinedTextField(
                        value = amountInput,
                        onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) amountInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        prefix = { Text("$ ", style = MaterialTheme.typography.headlineMedium) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search by country (Nepal) or code (NPR)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(50.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- DATA LIST ---
            Box(modifier = Modifier.fillMaxSize()) {
                when (val s = state) {
                    is ForexState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                    is ForexState.Error -> Text(s.message, Modifier.align(Alignment.Center), color = Color.Red)
                    is ForexState.Success -> {
                        val filteredList = s.data.filter { entry ->
                            val countryName = currencyToCountry[entry.key] ?: ""
                            entry.key.contains(searchQuery, ignoreCase = true) ||
                                    countryName.contains(searchQuery, ignoreCase = true)
                        }.toList()

                        LazyColumn {
                            items(filteredList) { pair ->
                                CurrencyRow(
                                    code = pair.first,
                                    convertedAmount = pair.second * multiplier,
                                    baseAmount = amountInput
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyRow(code: String, convertedAmount: Double, baseAmount: String) {
    val countryName = currencyToCountry[code] ?: "Global Currency"
    val countryCode = if (code == "EUR") "eu" else if (code == "USD") "us" else code.take(2).lowercase()
    val flagUrl = "https://flagcdn.com/w160/$countryCode.png"

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = flagUrl,
                contentDescription = null,
                modifier = Modifier.size(50.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(countryName, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                Text("$baseAmount USD =", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = String.format("%,.2f", convertedAmount),
                    fontWeight = FontWeight.Black,
                    fontSize = 19.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(code, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            }
        }
    }
}

