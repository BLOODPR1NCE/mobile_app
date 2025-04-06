package com.example.mobil.Presentation.Screens.SignUp

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mobil.Domain.Сondition.ResultCondition
import com.example.mobil.Presentation.Navigate.Routes
import com.example.mobil.Presentation.Screens.Details.ButtonNavigate
import com.example.mobil.Presentation.Screens.Details.TextDefault
import com.example.mobil.Presentation.Screens.Details.TextEmail
import com.example.mobil.Presentation.Screens.Details.TextPassword
import com.example.mobil.R
import java.util.Calendar
import java.util.Date

@Composable
fun SignUpScreen(navController: NavHostController, signUpViewModel: SignUpViewModel = viewModel()) {

    val uiState = signUpViewModel.uiState
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("Выберите дату рождения") }

    val resultState by signUpViewModel.resultState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logotip",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 24.dp)
        )
        Text(
            "РЕГИСТРАЦИЯ",
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.W600,
        )
        Spacer(Modifier.height(10.dp))
        TextEmail(uiState.email, "Логин","Введите логин",uiState.EmailError, { signUpViewModel.UpdateState(uiState.copy(email = it)) })
        Spacer(Modifier.height(10.dp))

        TextDefault(uiState.username, "Имя", "Введите имя", { signUpViewModel.UpdateState(uiState.copy(username = it)) })
        Spacer(Modifier.height(10.dp))

        TextDefault(uiState.surname, "Фамилия", "Введите фамилию", { signUpViewModel.UpdateState(uiState.copy(surname = it)) })
        Spacer(Modifier.height(10.dp))

        TextPassword(uiState.password, "Пароль","Введите пароль", { signUpViewModel.UpdateState(uiState.copy(password = it)) })
        Spacer(Modifier.height(10.dp))

        TextPassword(uiState.repeatPassword, "Повторный пароль","Введите пароль повторно",{ signUpViewModel.UpdateState(uiState.copy(repeatPassword = it)) })
        Spacer(Modifier.height(10.dp))

        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mYear-${mMonth+1}-$mDayOfMonth"
                signUpViewModel.UpdateState(uiState.copy(dateBirth = "$mYear-${mMonth+1}-$mDayOfMonth"))
            }, mYear, mMonth, mDay
        )

        Text (
            text = mDate.value,
            modifier = Modifier.clickable {
                mDatePickerDialog.show()
            }
        )
        Spacer(Modifier.height(10.dp))
        when (resultState) {
            is ResultCondition.Error -> {
                ButtonNavigate("Зарегистрироваться") { signUpViewModel.SignUp() }
                Text((resultState as ResultCondition.Error).message)
            }
            is ResultCondition.Init -> {
                ButtonNavigate("Зарегистрироваться") { signUpViewModel.SignUp() }
            }
            ResultCondition.Loading -> {
                CircularProgressIndicator()
            }
            is ResultCondition.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.SignIn)
                    {
                        popUpTo(Routes.SignUn) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}