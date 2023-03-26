package ru.netology.pushsender

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import java.io.FileInputStream

fun main() {
    val token =
        "f8u4Jx1zRdqp2wKDWkV261:APA91bGJepKpUXSVwC3RMqRrFqxtoTEuNdnK-a9wNMeNlSW7LqZkSjUP1t8ZJFLmMMsbrcKtGxtsWGDqSkD3LzkbPcpDCz8azhQju4UoBDEgHmFJa28vpJxG8ddJuG84R6eSEJqMHkFM"
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(FileInputStream("fcm.json")))
        .build()

    FirebaseApp.initializeApp(options)
    val action = "action"
    val content = "content"
    val postContent = """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer aliquet semper turpis sit amet pretium. Donec ac feugiat urna. Nullam pharetra pulvinar eros eu congue.
    """.trimIndent()

    val messages = listOf(
        Message.builder()
            .putData(action, "${Action.LIKE}")
            .putData(
                content, """{
          "userId": 1,
          "userName": "Vasiliy",
          "postId": 1,
          "postAuthor": "Netology"
        }""".trimIndent()
            )
            .setToken(token)
            .build(),

        Message.builder()
            .putData(action, "${Action.SHARE}")
            .putData(
                content, """{
          "userId": 1,
          "userName": "Vasiliy"
        }""".trimIndent()
            )
            .setToken(token)
            .build(),

        Message.builder()
            .putData(action, "${Action.POST}")
            .putData(
                content, """{
          "userId": 1,
          "userName": "Vasiliy",
          "postId": 2,
          "postContent": "$postContent"
        }""".trimIndent()
            )
            .setToken(token)
            .build(),
    )

    messages.forEach { message ->
        Thread.sleep(2000)
        FirebaseMessaging.getInstance().send(message)
    }
}