package br.com.fiap.emailboxapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val BlueAlpha = Color.Blue.copy(alpha = .2f)
private val CircleSize = 30.dp

data class EmailRow(
    val sender: Sender,
    val subject: String,
    val body: String,
    val deliveryDate: String,
    val isUnread: Boolean,
    val isRead: Boolean,
    val isStarred: Boolean,
) {
    data class Sender(val name: String, val color: Color)
}

@Composable
fun EmailRowItem(emailRow: EmailRow) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(IntrinsicSize.Min)
    ) {
        // Sender's initial letter
        Column {
            Surface(shape = CircleShape, modifier = Modifier.size(CircleSize), color = emailRow.sender.color) {
                Text(text = emailRow.sender.name.first().toString(), modifier = Modifier.wrapContentSize())
            }
        }

        // Email details
        val weight = if (emailRow.isUnread) FontWeight.Bold else FontWeight.Light
        Column(modifier = Modifier.padding(horizontal = 8.dp).weight(1f)) {
            Text(text = emailRow.sender.name, fontWeight = weight)
            Text(text = emailRow.subject, fontWeight = weight)
            Text(text = emailRow.body, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Light)
        }

        // Starred status
        val icon = if (emailRow.isStarred) Icons.Filled.Star else Icons.Filled.Clear
        Column(horizontalAlignment = Alignment.End) {
            Text(text = emailRow.deliveryDate)
            Spacer(modifier = Modifier.weight(1f))
            Image(imageVector = icon, contentDescription = "starred-status")
        }
    }
}

@Composable
fun EmailInbox() {
    var searchText by remember { mutableStateOf("") }
    var menuExpanded by remember { mutableStateOf(false) }

    val emails = listOf(
        EmailRow(
            sender = EmailRow.Sender("Andres Llinares", Color.Blue.copy(alpha = .2f)),
            subject = "Entrega Projeto Locaweb",
            body = "Olá, este é um email de teste para o projeto Locaweb",
            deliveryDate = "Today",
            isUnread = false,
            isRead = false,
            isStarred = true
        ),
        EmailRow(
            sender = EmailRow.Sender("Juarez da Cruz", Color.Green.copy(alpha = .2f)),
            subject = "Recebimento de Mensagem",
            body = "Teste de Mensagem Recebida.",
            deliveryDate = "Jun 10",
            isUnread = false,
            isRead = false,
            isStarred = false
        ),
        EmailRow(
            sender = EmailRow.Sender("Projeto FIAP", Color.Blue.copy(alpha = .2f)),
            subject = "Obrigado por participar!",
            body = "Este email é um teste para o projeto FIAP.",
            deliveryDate = "Jun 04",
            isUnread = true,
            isRead = false,
            isStarred = false
        ),
        EmailRow(
            sender = EmailRow.Sender("FIAP", Color.Red.copy(alpha = .5f)),
            subject = "Obrigado por participar!",
            body = "Este email é um teste para o projeto FIAP.",
            deliveryDate = "Jun 04",
            isUnread = false,
            isRead = true,
            isStarred = true
        ),
        EmailRow(
            sender = EmailRow.Sender("Iuri Naturesa", Color.Blue.copy(alpha = .2f)),
            subject = "Email de Teste",
            body = "1, 2, 3 Teste.",
            deliveryDate = "Jun 04",
            isUnread = true,
            isRead = false,
            isStarred = false
        ),
        EmailRow(
            sender = EmailRow.Sender("FIAP", Color.Green.copy(alpha = .3f)),
            subject = "Obrigado por participar!",
            body = "Este email é um teste para o projeto FIAP.",
            deliveryDate = "Jun 04",
            isUnread = false,
            isRead = true,
            isStarred = true
        ),
        EmailRow(
            sender = EmailRow.Sender("Locaweb", Color.Blue.copy(alpha = .2f)),
            subject = "Codigo de Acesso",
            body = "O codigo de acesso à plataforma é 123456, caso seja necessário encaminhar novamete.",
            deliveryDate = "Jun 04",
            isUnread = false,
            isRead = true,
            isStarred = true
        ),
        EmailRow(
            sender = EmailRow.Sender("Maria Silva", Color.Red.copy(alpha = .5f)),
            subject = "Reunião de equipe",
            body = "Lembrando que teremos uma reunião de equipe amanhã.",
            deliveryDate = "Jun 05",
            isUnread = true,
            isRead = false,
            isStarred = false
        ),
        EmailRow(
            sender = EmailRow.Sender("João Oliveira", Color.Green.copy(alpha = .3f)),
            subject = "Atualização do projeto",
            body = "Segue a última atualização sobre o progresso do nosso projeto.",
            deliveryDate = "Jun 06",
            isUnread = false,
            isRead = true,
            isStarred = false
        ),
        EmailRow(
            sender = EmailRow.Sender("Equipe de Marketing", Color.Yellow.copy(alpha = .4f)),
            subject = "Ideias para campanha",
            body = "Estamos buscando novas ideias para campanhas, sua opinião é valiosa.",
            deliveryDate = "Jun 07",
            isUnread = true,
            isRead = false,
            isStarred = true
        ),
        EmailRow(
            sender = EmailRow.Sender("Suporte Técnico", Color.Blue.copy(alpha = .2f)),
            subject = "Problema técnico",
            body = "Foi reportado um problema técnico por um usuário, por favor verifique.",
            deliveryDate = "Jun 08",
            isUnread = false,
            isRead = true,
            isStarred = false
        ),
    )

    val filteredEmails = remember(searchText) {
        emails.filter {
            it.sender.name.contains(searchText, ignoreCase = true) ||
                    it.subject.contains(searchText, ignoreCase = true) ||
                    it.body.contains(searchText, ignoreCase = true)
        }
    }

        val menuItems = listOf("Option 1", "Option 2", "Option 3")
        var expanded by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableStateOf(0) }


    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar") },
            leadingIcon = null,
            trailingIcon = {
                Row {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismiss = { expanded = false },
                        menuItems = menuItems,
                        selectedIndex = selectedIndex,
                        onMenuItemClick = { index ->
                            selectedIndex = index
                            expanded = false
                            /* Handle menu item click */
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        LazyColumn {
            items(filteredEmails) { email ->
                EmailRowItem(emailRow = email)
            }
        }
    }
}

@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    menuItems: List<String>,
    selectedIndex: Int,
    onMenuItemClick: (Int) -> Unit
) {
    if (expanded) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .border(1.dp, Color.Gray)
                .padding(4.dp)
        ) {
            Column {
                menuItems.forEachIndexed { index, text ->
                    Text(
                        text = text,
                        modifier = Modifier
                            .clickable { onMenuItemClick(index) }
                            .padding(8.dp)
                    )
                    if (index < menuItems.size - 1) {
                        Divider(color = Color.Gray, thickness = 1.dp)
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun EmailInboxPreview() {
    EmailInbox()
}
