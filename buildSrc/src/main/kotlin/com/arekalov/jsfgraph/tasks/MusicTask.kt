import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

open class MusicTask : DefaultTask() {

    @TaskAction
    open fun execute() {
        playSound("/Users/arekalov/Itmo/4/OPI/lab3/opi-lab3/buildSrc/src/main/resources/sound.wav")
    }

    private fun playSound(fileName: String) {
        try {
            val audioInputStream: AudioInputStream = AudioSystem.getAudioInputStream(File(fileName))
            val clip: Clip = AudioSystem.getClip()

            clip.open(audioInputStream)
            clip.start()

            Thread.sleep(clip.microsecondLength / 1000)

            clip.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
