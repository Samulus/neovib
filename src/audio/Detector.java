package src.audio;

import be.tarsos.dsp.*;
import be.tarsos.dsp.io.jvm.*;
import be.tarsos.dsp.util.fft.*;
import java.io.*;
import java.util.*;

public class Detector {

   static final int CUTOFF  = 20;   // 20
   static final double MEAN = 1.3f; // 1.45f;
   static final double DST = 0.3f;  // 0.1

   public static LinkedList<Double> load(File f) {

      AudioDispatcher dispatcher = null;
      final FFT fft = new FFT(1024);

      try {
         dispatcher = AudioDispatcherFactory.fromPipe(f.getCanonicalPath(), 44100, 1024, 0);
      } catch (Exception e) {
         System.out.printf("%s decoding error\n", f.getName());
         return null;
      }

      /*
         Onset Note Detection Pulled from BadLogicsGames awesome article:
         http://www.badlogicgames.com/wordpress/?cat=18
         It's mostly unchanged but adapted to use TarsosDSP, a HammingWindow,
         and ignores beats that are within 0.3ms of each other.

         Thanks BadLogicGames!
       */

      final LinkedList<Double> output = new LinkedList<Double>();
      final HammingWindow ham = new HammingWindow();

      /*
       * Beat Detection
       */

      AudioProcessor detectProcessor = new AudioProcessor() {

         double[] spectrum = new double[1024 / 2 + 1];
         double[] lastSpectrum = new double[1024 / 2 + 1];
         ArrayList<Double> spectralFlux = new ArrayList<Double>();
         ArrayList<Double> threshould = new ArrayList<Double>();
         ArrayList<Double> pruned = new ArrayList<Double>();
         ArrayList<Double> peaks = new ArrayList<Double>();

         @Override
         public boolean process(AudioEvent audioEvent) {
            float[] buff = audioEvent.getFloatBuffer();
            ham.apply(buff);
            fft.forwardTransform(buff);

            System.arraycopy(spectrum, 0, lastSpectrum, 0, spectrum.length);

            /* doubletime */
            for (int i = 0; i < spectrum.length; ++i) {
               spectrum[i] = (double) buff[i];
            }

            //System.arraycopy(buff, 0, spectrum, 0, spectrum.length);

            /* calculate spectrual flux (distance variance between each height) */
            double flux = 0;
            for (int i = 0; i < spectrum.length; ++i) {
               double value = (spectrum[i] - lastSpectrum[i]);
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

               double mean = 0;
               for (int j = start; j <= end; ++j)
                  mean += spectralFlux.get(j);
               mean /= (end - start);
               threshould.add(mean * MEAN);
            }

            /* disregard beats that are not equal to or above the threshold */
            for (int i = 0; i < threshould.size(); ++i) {

               double value = (threshould.get(i) <= spectralFlux.get(i)) ? spectralFlux.get(i) - threshould.get(i) : 0;
               pruned.add(value);

            }

            /* finally capture the peaks in the audio */
            for (int i = 0; i < pruned.size() - 1; ++i) {
               double value = (pruned.get(i) > pruned.get(i + 1)) ? pruned.get(i) : 0;
               peaks.add(value);
            }

            int count = 0;
            double avg = 0.0f;
            double prev = 0.0f;

            double time = 1024.0f / 44100.0f;
            for (int i = 0; i < peaks.size(); ++i) {
               double p = peaks.get(i);
               if (p > 0) {
                  if (Math.abs((i * time) - prev) >= DST) { // if there is at least DST seconds betof each other.                     //System.out.println(i * time);
                     output.add(i * time);
                     count++;
                  }
                  avg += i * time;
                  prev = i * time;
               }

            }

            //System.out.println("---");
            //System.out.println(count);

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
