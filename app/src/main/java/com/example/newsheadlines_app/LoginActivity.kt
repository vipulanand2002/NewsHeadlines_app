package com.example.newsheadlines_app

import android.app.backup.BackupAgentHelper
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

class LoginActivity: ComponentActivity(){
    private lateinit var databaseHelper: UserDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = UserDatabaseHelper(this)
        setContent{
            LoginMainScreen(this,databaseHelper)
        }
    }
}
@Composable
fun LoginMainScreen(context: Context,databaseHelper: UserDatabaseHelper) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var error by remember {
        mutableStateOf("")
    }

    Column(
        Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        Image(painter = painterResource(id = R.drawable.newlogin), contentDescription = "")

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Divider(color= Color.Gray, thickness = 2.dp, modifier = Modifier.width(150.dp).padding(top = 25.dp, end = 25.dp))

            Text(text = "Login", color = Color(0xFF86BAE1), fontWeight = FontWeight.Bold, fontSize = 25.sp, style = MaterialTheme.typography.bodyMedium)

            Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.width(155.dp).padding(top = 25.dp, start = 25.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(value = username, onValueChange = { username = it },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "personIcon", tint = Color.Blue) },

            placeholder = { Text(text = "username", color = Color.Black) },

            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
                )


        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = password, onValueChange = { password = it },
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "lockIcon", tint = Color(0XFF86BAE1)) },

            placeholder = { androidx.compose.material.Text(text = "password", color = Color.Black) },

            visualTransformation = PasswordVisualTransformation(),

            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )



        Spacer(modifier = Modifier.height(12.dp))
        if (error.isNotEmpty()) {
            androidx.compose.material.Text(text = error, color = androidx.compose.material.MaterialTheme.colors.error, modifier = Modifier.padding(vertical = 16.dp))
        }

        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    val user = databaseHelper.getUserByUsername(username)
                    if (user != null && user.password == password) {
                        error = "Successfully log in"
                        context.startActivity(
                            Intent(context, MainPage::class.java)
                        )
                    } else {
                        error = "Invalid username or password"
                    }
                } else {
                    error = "Please fill all fields"
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF86BAE1)),
            modifier = Modifier.width(200.dp).padding(top = 16.dp)
        ) {
            androidx.compose.material.Text(text = "Log In", fontWeight = FontWeight.Bold)
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { context.startActivity(Intent(context, RegistrationActivity::class.java))
                                })
            {
                Text(text = "Sign up", color = Color.Black)
            }

            Spacer(modifier = Modifier.width(110.dp))

            TextButton(onClick = { /*Code*/ })
            {
                Text(text = "Forgot password ?", color = Color.Black)
            }
        }

    }
}
private fun startMainPage(context: Context){
    val intent = Intent(context, MainPage::class.java)
    ContextCompat.startActivity(context,intent,null)
}
