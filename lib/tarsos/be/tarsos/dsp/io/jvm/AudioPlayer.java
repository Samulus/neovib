/*
*      _______                       _____   _____ _____  
*     |__   __|                     |  __ \ / ____|  __ \ 
*        | | __ _ _ __ ___  ___  ___| |  | | (___ | |__) |
*        | |/ _` | '__/ __|/ _ \/ __| |  | |\___ \|  ___/ 
*        | | (_| | |  \__ \ (_) \__ \ |__| |____) | |     
*        |_|\__,_|_|  |___/\___/|___/_____/|_____/|_|     
*                                                         
* -------------------------------------------------------------
*
* TarsosDSP is developed by Joren Six at IPEM, University Ghent
*  
* -------------------------------------------------------------
*
*  Info: http://0110.be/tag/TarsosDSP
*  Github: https://github.com/JorenSix/TarsosDSP
*  Releases: http://0110.be/releases/TarsosDSP/
*  
*  TarsosDSP includes modified source code by various authors,
*  for credits and info, see README.
* 
*/


package be.tarsos.dsp.io.jvm;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import javazoom.jl.decoder.Source;

import javax.sound.sampled.*;

/**
 * This AudioProcessor can be used to sync events with sound. It uses a pattern
 * described in JavaFX Special Effects Taking Java RIA to the Extreme with
 * Animation, Multimedia, and Game Element Chapter 9 page 185: <blockquote><i>
 * The variable LineWavelet is the Java Sound object that actually makes the sound. The
 * write method on LineWavelet is interesting because it blocks until it is ready for
 * more data. </i></blockquote> If this AudioProcessor chained with other
 * AudioProcessors the others should be able to operate in real time or process
 * the signal on a separate thread.
 *
 * @author Joren Six
 */
public final class AudioPlayer implements AudioProcessor {


   /**
    * The LineWavelet to send sound to. Is also used to keep everything in sync.
    */
   private SourceDataLine line;


   private final AudioFormat format;

   /**
    * Creates a new audio player.
    *
    * @param format The AudioFormat of the buffer.
    * @throws LineUnavailableException If no output LineWavelet is available.
    */

   public AudioPlayer(final AudioFormat format) throws LineUnavailableException {
      final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
      this.format = format;

       //System.out.println(AudioSystem.getMixerInfo().length);
       /*
       Mixer.Info minfo = AudioSystem.getMixerInfo()[0]; // 2, 3, 4, 5 cash,
       Mixer mix = AudioSystem.getMixer(minfo);
       line = (SourceDataLine) mix.getLine(info);
       line.open(format);
       line.start();
       */

//       int i=0;
      for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
         //System.out.println(mixerInfo.getName());
          //System.out.println(mixerInfo.getDescription());
          //System.out.println("----");
         try {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            line = (SourceDataLine) mixer.getLine(info);
             if (mixerInfo.getName().equalsIgnoreCase("PCH [plughw:0,0]")) {
                 line.open(format);
                 line.start();
                 //System.out.println("BAIL");
                 return;
             }
            if (line == null) {
               continue; //Doesn't support this format
            }
         } catch (Exception ex) {
            //System.out.println("Can't open this");
            continue;
         }
         try {
            line.close();
         } catch (Exception ex) {
            ex.printStackTrace(); //Shouldn't get here
         }
      }


      if (line == null) {
         //System.out.println("Couldn't get a line");
         //No dataline capable of *really* playing the stream
      } else {
         //We have a non-lying dataline!
         if (!line.isOpen()){
            line.open(format);
            //System.out.println("Opened");
         }

         if (!line.isRunning()) {
            line.start();
            //System.out.println("Started");
         }
      }


      //line = (SourceDataLine) AudioSystem.getLine(info);
      //line.open();
      //line.start();
   }

   public AudioPlayer(final AudioFormat format, int bufferSize) throws LineUnavailableException {
      final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, bufferSize);
      this.format = format;
      line = (SourceDataLine) AudioSystem.getLine(info);
      line.open();
      line.start();
   }

   public AudioPlayer(final TarsosDSPAudioFormat format, int bufferSize) throws LineUnavailableException {
      this(JVMAudioInputStream.toAudioFormat(format), bufferSize);
   }

   public AudioPlayer(final TarsosDSPAudioFormat format) throws LineUnavailableException {
      this(JVMAudioInputStream.toAudioFormat(format));
   }


   @Override
   public boolean process(AudioEvent audioEvent) {
      int byteOverlap = audioEvent.getOverlap() * format.getFrameSize();
      int byteStepSize = audioEvent.getBufferSize() * format.getFrameSize() - byteOverlap;
      if (audioEvent.getTimeStamp() == 0) {
         byteOverlap = 0;
         byteStepSize = audioEvent.getBufferSize() * format.getFrameSize();
      }
      // overlap in samples * nr of bytes / sample = bytes overlap

      int bytesWritten = line.write(audioEvent.getByteBuffer(), byteOverlap, byteStepSize);
      if (bytesWritten != byteStepSize) {
         System.err.println(String.format("Expected to write %d bytes but only wrote %d bytes", byteStepSize, bytesWritten));
      }
      return true;
   }

   /*
    * (non-Javadoc)
    *
    * @see be.tarsos.util.RealTimeAudioProcessor.AudioProcessor#
    * processingFinished()
    */
   public void processingFinished() {
      // cleanup
      line.drain();//drain takes too long..
      line.stop();
      line.close();
   }
}
