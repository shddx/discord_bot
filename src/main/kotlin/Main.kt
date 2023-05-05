import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.GuildEmoji
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList

suspend fun main(args: Array<String>) {
    val token = System.getenv("DISCORD_BOT_TOKEN")
    val kord = Kord(token)

    val sewersGuild = kord.getGuildOrThrow(Snowflake(294943212014141441))
    val pogOff = sewersGuild.emojis.filter { it.name == "pogoff" }.toList().first()

    kord.on<ReactionAddEvent> {
        if (isFlippableEmoji(this.emoji)) {
            val mention = this.user.mention
            this.channel.createMessage("$mention ${pogOff.asString()}")
        }
    }

    kord.login {
        presence { playing("your mum") }

        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}

fun isFlippableEmoji(emoji: ReactionEmoji): Boolean {
    return emoji.name == "pogoff"
}

suspend fun GuildEmoji.asString(): String {
    return "<:${data.name}:${data.id.value}>";
}

