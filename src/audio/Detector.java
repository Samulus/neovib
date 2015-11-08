package src.audio;

import be.tarsos.dsp.*;
import be.tarsos.dsp.io.jvm.*;
import be.tarsos.dsp.util.fft.*;
import be.tarsos.dsp.util.fft.*;
import java.io.*;
import java.util.*;

public class Detector {

   static final int CUTOFF  = 20;   // 20
   static final float MEAN  = 1.5f; // 1.45f;
   static final float DST   = 0.3f;  // 0.1

   public static LinkedList<Float> load(File f) {

      AudioDispatcher dispatcher = null;
      final FFT fft = new FFT(1024);

      try {
         dispatcher = AudioDispatcherFactory.fromPipe(f.getCanonicalPath(), 44100, 1024, 3);
      } catch (Exception e) {
         System.out.printf("%s decoding error\n", f.getName());
         return null;
      }

      /* copied and adapted slightly from http://www.badlogicgames.com/wordpress/?cat=18 */
      /* thanks badlogic games */

      /*
         Onset Note Detection Pulled from BadLogicsGames awesome article
         http://www.badlogicgames.com/wordpress/?cat=18
         Adapted to to use TarsosDSP, HammingWindows,
       */

      final LinkedList<Float> output = new LinkedList<Float>();
      final HammingWindow ham = new HammingWindow();

      /*
       * Beat Detection
       */

      AudioProcessor detectProcessor = new AudioProcessor() {

         float[] spectrum = new float[1024 / 2 + 1];
         float[] lastSpectrum = new float[1024 / 2 + 1];
         ArrayList<Float> spectralFlux = new ArrayList<Float>();
         ArrayList<Float> threshould = new ArrayList<Float>();
         ArrayList<Float> pruned = new ArrayList<Float>();
         ArrayList<Float> peaks = new ArrayList<Float>();

         @Override
         public boolean process(AudioEvent audioEvent) {
            float[] buff = audioEvent.getFloatBuffer();
            ham.apply(buff);
            fft.forwardTransform(buff);

            /* update float buffers */
            System.arraycopy(spectrum, 0, lastSpectrum, 0, spectrum.length);
            System.arraycopy(buff, 0, spectrum, 0, spectrum.length);

            /* calculate spectrual flux (distance variance between each height) */
            float flux = 0;
            for (int i = 0; i < spectrum.length; ++i) {
               float value = (spectrum[i] - lastSpectrum[i]);
               flux += value < 0 ? 0 : value; // ignore negatives
            }

            spectralFlux.add(flux);
            return true;
         }

         @Override
         public void processingFinished() {

            /* Use threshold to eliminate noise */
            /* its the running average of the spectral flux function */
            /* we can detect outliers which indicate a rhythmic onset / beat */
            for (int i = 0; i < spectralFlux.size(); i++) {

               int start = Math.max(0, i - CUTOFF);
               int end = Math.min(spectralFlux.size() - 1, i + CUTOFF);

               float mean = 0;
               for (int j = start; j <= end; ++j)
                  mean += spectralFlux.get(j);
               mean /= (end - start);
               threshould.add(mean * MEAN);
            }

            /* disregard beats that are not equal to or above the threshold */
            for (int i = 0; i < threshould.size(); ++i) {

               float value = (threshould.get(i) <= spectralFlux.get(i)) ? spectralFlux.get(i) - threshould.get(i) : 0;
               pruned.add(value);

            }

            /* finally capture the peaks in the audio */
            for (int i = 0; i < pruned.size() - 1; ++i) {
               float value = (pruned.get(i) > pruned.get(i + 1)) ? pruned.get(i) : 0;
               peaks.add(value);
            }

            int count = 0;
            float avg = 0.0f;
            //avg = 0.0f;
            //System.out.println(avg); // shut up eclipse

            float prev = 0.0f;

            float time = 1024.0f / 44100.0f;
            for (int i = 0; i < peaks.size(); ++i) {
               float p = peaks.get(i);
               if (p > 0) {
                  if (Math.abs((i * time) - prev) >= DST) { // if there is at least 0.4 seconds between
                     System.out.println(i * time);
                     output.add(i * time);
                     count++;
                  }
                  avg += i * time;
                  prev = i * time;
               }

            }

            System.out.println("---");
            System.out.println(count);

         }
      };

      dispatcher.addAudioProcessor(detectProcessor);
      dispatcher.run();
      return output;

   }

   public static void main(String[] args) {
      Detector.load(new File("closer.mp3"));
   }
}
