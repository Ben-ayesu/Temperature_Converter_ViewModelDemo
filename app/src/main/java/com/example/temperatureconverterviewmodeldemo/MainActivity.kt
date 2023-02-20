package com.example.temperatureconverterviewmodeldemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temperatureconverterviewmodeldemo.ui.theme.TemperatureConverterViewModelDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemperatureConverterViewModelDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenSetup()
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(
    viewModel: DemoViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        var textState by remember {
            mutableStateOf("")
        }

        val onTextChange = { text : String ->
            textState = text
        }

        Text(
            text = "Temperature Converter",
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.bodyMedium,
        )

        MainScreen(
            isFahrennheit = viewModel.isFahrenheit,
            result = viewModel.result,
            convertTemp = {
                viewModel.convertTemp(it)
            },
            switchChange = {
                viewModel.switchChange()
            }
        )
    }
}

@Composable
fun MainScreen(
    isFahrennheit: Boolean,
    result: String,
    convertTemp: (String) -> Unit,
    switchChange: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        var textState by remember {
            mutableStateOf("")
        }

        val onTextChange = { text: String ->
            textState = text
        }

        Text(
            text = "Temperature Converter",
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.bodyMedium,
        )

        InputRow(
            isFahrennheit = isFahrennheit,
            textState = result,
            switchChange = { convertTemp },
            onTextChange = {
                switchChange()
            }
        )

        Text(
            text = result,
            modifier = Modifier
                .padding(20.dp),
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = { convertTemp(textState) }
        ) {
            Text(text = "Convert Temperature")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputRow(
    isFahrennheit: Boolean,
    textState: String,
    switchChange: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = isFahrennheit,
            onCheckedChange = { switchChange }
        )

        OutlinedTextField(
            value = textState,
            onValueChange = {
                onTextChange(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            label = {
                Text(text = "Enter temperature")
            },
            modifier = Modifier
                .padding(8.dp),
            textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp),
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_ac_unit_24),
                    contentDescription = stringResource(R.string.frost),
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        )

        Crossfade(
            targetState = isFahrennheit,
            animationSpec = tween(2000)
        ) { visible ->
            when(visible) {
                true -> Text(
                    "\u2109",
                    style = MaterialTheme.typography.headlineMedium
                    )
                false -> Text(
                    "\u2103",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultPreview(
    model: DemoViewModel = viewModel()
) {
    TemperatureConverterViewModelDemoTheme {
        MainScreen(
            isFahrennheit = model.isFahrenheit,
            result = model.result,
            convertTemp = {
                model.convertTemp(it)
            },
            switchChange = {
                model.switchChange()
            }
        )
    }
}