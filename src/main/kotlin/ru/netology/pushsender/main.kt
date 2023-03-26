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
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus rhoncus mi in semper commodo. 
        Ut sit amet nunc libero. Aliquam dignissim consectetur justo. 
        Aliquam ullamcorper leo eget turpis iaculis blandit. 
        Integer nunc augue, porta ut feugiat sit amet, rutrum sed ante. 
        Etiam ullamcorper fermentum augue eu ornare. Quisque luctus a leo nec pharetra. 
        Quisque ut viverra tellus. Aenean dolor metus, elementum sed molestie vitae, pellentesque vel erat. 
        Vivamus malesuada tincidunt ante. Mauris facilisis dolor eget libero ultricies dignissim. 
        Aenean et ultricies ante, et dignissim mauris. 
        Mauris facilisis nulla vel quam malesuada, ut convallis velit mollis.
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