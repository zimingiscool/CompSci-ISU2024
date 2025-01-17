import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    public void playSound(String filePath) {
        File soundFile = new File(filePath);
        if (!soundFile.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            System.out.println("Playing sound: " + filePath);
            while (clip.isRunning()) {
                Thread.sleep(100); // Wait for the sound to finish
            }
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the audio file: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Playback interrupted: " + e.getMessage());
        }
    }
}
