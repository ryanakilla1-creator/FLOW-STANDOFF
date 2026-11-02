package com.flow.standoff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF090D16)
private val Panel = Color(0xFF131B2A)
private val Accent = Color(0xFF66E3C4)
private val Muted = Color(0xFF91A0B8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FlowApp() }
    }
}

@Composable
private fun FlowApp() {
    var espEnabled by remember { mutableStateOf(false) }
    var trainerEnabled by remember { mutableStateOf(true) }
    var targetMode by remember { mutableStateOf("Cabeça") }
    var hits by remember { mutableIntStateOf(0) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("FLOW", color = Accent, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text("STANDOFF", color = Color.White, fontSize = 29.sp, fontWeight = FontWeight.Black)
                    Text("Painel de treinamento", color = Muted, fontSize = 14.sp)
                }

                FeatureCard(
                    title = "ESP DEMO",
                    description = "Marcadores ilustrativos dentro deste aplicativo.",
                    checked = espEnabled,
                    onCheckedChange = { espEnabled = it }
                )

                FeatureCard(
                    title = "AIM TRAINER",
                    description = "Ative o exercício de precisão independente do jogo.",
                    checked = trainerEnabled,
                    onCheckedChange = { trainerEnabled = it }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Panel)
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text("ALVO DE TREINO", color = Color.White, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        TargetOption("Cabeça", targetMode == "Cabeça") { targetMode = "Cabeça" }
                        TargetOption("Peito", targetMode == "Peito") { targetMode = "Peito" }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF0B1220)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (espEnabled) {
                            DemoMarker(Modifier.align(Alignment.TopStart).padding(18.dp), "ALVO A")
                            DemoMarker(Modifier.align(Alignment.BottomEnd).padding(18.dp), "ALVO B")
                        }
                        if (trainerEnabled) {
                            Box(
                                modifier = Modifier
                                    .size(if (targetMode == "Cabeça") 54.dp else 76.dp)
                                    .clip(CircleShape)
                                    .background(Accent.copy(alpha = 0.16f))
                                    .border(2.dp, Accent, CircleShape)
                                    .clickable { hits++ },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(9.dp)
                                        .clip(CircleShape)
                                        .background(Accent)
                                )
                            }
                        } else {
                            Text("Treinador pausado", color = Muted)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Acertos: $hits", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text("Toque no alvo para marcar", color = Muted, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "Aplicativo independente • Não interage com partidas ou memória do jogo.",
                    color = Muted,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun FeatureCard(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Panel)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(description, color = Muted, fontSize = 12.sp)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun TargetOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) Accent.copy(alpha = 0.18f) else Color(0xFF202B3D))
            .border(
                width = 1.dp,
                color = if (selected) Accent else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Text(
            label,
            color = if (selected) Accent else Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun DemoMarker(modifier: Modifier = Modifier, label: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xCC18283A))
            .border(1.dp, Accent.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
            .padding(horizontal = 9.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(label, color = Accent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Text("DEMO", color = Color.White, fontSize = 9.sp)
    }
}
