package com.example.composecounterapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun CounterApp() {

    //Snackbar uygulamak için önce SnackbarHost oluşturursunuz. Bu, SnackbarHostState özelliğini içerir.
    // SnackbarHostState, snackbar'ınızı göstermek için kullanabileceğiniz showSnackbar() işlevine erişim sağlar.
    //Bu askıya alma işlevi, rememberCoroutineScope tarafından döndürülen gibi bir CoroutineScope gerektirir ve Scaffold içinde
    // Snackbar göstermek için kullanıcı arayüzü etkinliklerine yanıt olarak çağrılabilir.
    // snackbar eklemek için scope ve snackbarHostState tanımlama
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // snackbar için scaffold tanımlama
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        // floating action button eklemek için
//        floatingActionButton = {
//            ExtendedFloatingActionButton(
//                text = { Text(text = "Show Snackbar") },
//                icon = { Icon(imageVector = Icons.Filled.Close, contentDescription = null)},
//                onClick = {
//                    scope.launch {
//                        snackbarHostState.showSnackbar(message = "Snackbar opened!")
//                    }
//                }
//            )
//        }
    ) { contentPadding ->

        // sayac ve başlangıç sayısı için remember tanımlama
        // eğer textfield dan değer alınacaksa remember kullanılmalı
        val startNumber = remember { mutableStateOf("") }
        val counter = remember { mutableIntStateOf(0) }

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineLarge,
                text = "Sayaç Uygulaması\nremember mutablestate",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            MyTextField(
                text = startNumber.value,
                hint = "Başlangıç Sayısı",
                onValueChange = { it ->
                    // alfanümerik klavyede girişi sayılar ile sınırlama
                    if(it.all {it.isDigit()}){
                        startNumber.value = it
                        if(it.isNotEmpty()){
                            counter.intValue = it.toInt()
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button( // artır butonu
                    modifier = Modifier.padding(end = 8.dp),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        counter.intValue++
                    }
                ) {
                    Text(text = "Artır")
                }
                Button( // azalt butonu
                    modifier = Modifier.padding(end = 8.dp),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        if(counter.intValue == 0){
                            // snackbar göstermek için scope ve snackbarHostState tanımlama
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Sayac 0'dan küçük olamaz!",
                                    actionLabel = "Kapat",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }else{
                            counter.intValue--
                        }
                    }
                ) {
                    Text(text = "Azalt")
                }
                Button( // sıfırlama butonu
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        counter.intValue = 0
                        startNumber.value = ""
                    }
                ) {
                    Text(text = "Sıfırla")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // sayacı göstermek için text tanımlama
            Text(
                text = counter.intValue.toString(),
                style = TextStyle(
                    fontSize = 48.sp,
                    fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
                )
            )
        }

    } // scaffold

}

// State Hoisting uygulamak için TextField oluşturma
@Composable
fun MyTextField(text: String, hint: String, onValueChange: (String) -> Unit){
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        modifier = Modifier,
        keyboardOptions = KeyboardOptions( // kullanıcı girişini sayılar ile sınırlama
            keyboardType = KeyboardType.Number
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CounterAppPreview() {
    CounterApp()
}