package com.example.rock_paper_scissors_lizard_spock_game
/*
    Android application in Kotlin
    Rock Paper Scissors lizard Spock Game:
    Created by Stephen Harr on December 13, 2024

    A player chooses their selection and a randomizer for Ai makes a selection afterwards.
    The results are compared and a winner is chosen.
 */

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rock_paper_scissors_lizard_spock_game.ui.theme.RockPaperScissorsLizardSpockGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RockPaperScissorsLizardSpockGameTheme {
                    GameScreen()
                }
            }
        }
    }

@Composable
fun GameScreen() {
    val elements = listOf("Rock", "Paper", "Scissors", "Lizard", "Spock")
    var playerChoice by remember { mutableStateOf<String?>(null) }
    var aiChoice by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<String?>(null) }
    var resultDetail by remember { mutableStateOf<String?>(null) }

    // List of images corresponding to each element
    val elementImages = mapOf(
        "Rock" to R.drawable.rock,
        "Paper" to R.drawable.paper,
        "Scissors" to R.drawable.scissors,
        "Lizard" to R.drawable.lizard,
        "Spock" to R.drawable.spock
    )
    // Function to determine the winner and provide detailed result
    fun determineWinner(player: String?, ai: String?): String {
        val detail = when {
            player == "Scissors" && ai == "Paper" -> "Scissors cuts Paper"
            player == "Paper" && ai == "Rock" -> "Paper covers Rock"
            player == "Rock" && ai == "Lizard" -> "Rock crushes Lizard"
            player == "Lizard" && ai == "Spock" -> "Lizard poisons Spock"
            player == "Spock" && ai == "Scissors" -> "Spock smashes Scissors"
            player == "Scissors" && ai == "Lizard" -> "Scissors decapitates Lizard"
            player == "Lizard" && ai == "Paper" -> "Lizard eats Paper"
            player == "Paper" && ai == "Spock" -> "Paper disproves Spock"
            player == "Spock" && ai == "Rock" -> "Spock vaporizes Rock"
            player == "Rock" && ai == "Scissors" -> "Rock crushes Scissors"
            ai == "Scissors" && player == "Paper" -> "Scissors cuts Paper"
            ai == "Paper" && player == "Rock" -> "Paper covers Rock"
            ai == "Rock" && player == "Lizard" -> "Rock crushes Lizard"
            ai == "Lizard" && player == "Spock" -> "Lizard poisons Spock"
            ai == "Spock" && player == "Scissors" -> "Spock smashes Scissors"
            ai == "Scissors" && player == "Lizard" -> "Scissors decapitates Lizard"
            ai == "Lizard" && player == "Paper" -> "Lizard eats Paper"
            ai == "Paper" && player == "Spock" -> "Paper disproves Spock"
            ai == "Spock" && player == "Rock" -> "Spock vaporizes Rock"
            ai == "Rock" && player == "Scissors" -> "Rock crushes Scissors"
            player == ai -> "It's a tie!"
            else -> "Error in determining result."
        }
        resultDetail = detail
        return when {
            player == ai -> "It's a tie!"
            player == "Scissors" && (ai == "Paper" || ai == "Lizard") -> "Player wins!"
            player == "Paper" && (ai == "Rock" || ai == "Spock") -> "Player wins!"
            player == "Rock" && (ai == "Lizard" || ai == "Scissors") -> "Player wins!"
            player == "Lizard" && (ai == "Spock" || ai == "Paper") -> "Player wins!"
            player == "Spock" && (ai == "Scissors" || ai == "Rock") -> "Player wins!"
            else -> "AI wins!"
        }
    } // End function DetermineWinner

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.star_trek),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        ) // Background image

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Welcome to Rock, Paper, Scissors, Lizard, Spock!", color = Color.Blue, style = TextStyle(fontSize = 40.sp))
            Spacer(modifier = Modifier.height(16.dp))

            Text("Choose your element:", color = Color.Blue, style = TextStyle(fontSize = 30.sp))
            Spacer(modifier = Modifier.height(8.dp))

            elements.forEach { element ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (playerChoice == element),
                            onClick = {
                                playerChoice = element
                                // On click of RadioButton, Clear previous results
                                aiChoice = null
                                result = null
                                resultDetail = null
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = element)
                    }
                    Icon(
                        painter = painterResource(id = elementImages[element] ?: 0),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                }
            } // End of Radio Button placement for each

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                // Generate new AI choice and determine winner
                aiChoice = elements.random()
                result = determineWinner(playerChoice, aiChoice)
            }) {
                Text("Play")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                playerChoice?.let {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text("Player choice:")
                        Text(it, color = Color.Blue, style = TextStyle(fontSize = 20.sp))
                        // Display the corresponding image
                        val imageResource = elementImages[it]
                        imageResource?.let { resId ->
                            Image(
                                painter = painterResource(id = resId),
                                contentDescription = it,
                                modifier = Modifier.size(64.dp)
                            )
                        } // End of image let
                    } // End of PlayerChoice Column
                } // End of let
                aiChoice?.let {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text("AI choice:")
                        Text(it, color = Color.Blue, style = TextStyle(fontSize = 20.sp))
                        // Display the corresponding image
                        val imageResource = elementImages[it]
                        imageResource?.let { resId ->
                            Image(
                                painter = painterResource(id = resId),
                                contentDescription = it,
                                modifier = Modifier.size(64.dp)
                            )
                        } // End of image let
                    } // End of aiChoice Column
                } // End of let
            } // End of Player & AI Row

            Spacer(modifier = Modifier.height(16.dp))

            result?.let {
                Text("Result:", color = Color.Red, style = TextStyle(fontSize = 24.sp))
                Text(it, color = Color.Blue, style = TextStyle(fontSize = 20.sp))
            }
            resultDetail?.let {
                Text("Detail: $it", color = Color.Blue, style = TextStyle(fontSize = 20.sp))
            }
        } // end of Column within Box
    } // End of Box for screen and background image
} // End of Class

fun determineWinners(playerChoice: String?, aiChoice: String?): String {
    if (playerChoice == null || aiChoice == null) return "No winner"

    return when {
        playerChoice == aiChoice -> "It's a tie!"
        playerChoice == "Scissors" && (aiChoice == "Paper" || aiChoice == "Lizard") -> "Player wins!"
        playerChoice == "Paper" && (aiChoice == "Rock" || aiChoice == "Spock") -> "Player wins!"
        playerChoice == "Rock" && (aiChoice == "Lizard" || aiChoice == "Scissors") -> "Player wins!"
        playerChoice == "Lizard" && (aiChoice == "Spock" || aiChoice == "Paper") -> "Player wins!"
        playerChoice == "Spock" && (aiChoice == "Scissors" || aiChoice == "Rock") -> "Player wins!"
        else -> "AI wins!"
    }
} // End function. This function is older model and unused.


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RockPaperScissorsLizardSpockGameTheme {
        GameScreen()
    }
}
