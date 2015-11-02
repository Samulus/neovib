package src.audio;
import java.io.File;
import java.util.ArrayList;
import be.tarsos.dsp.*;
import be.tarsos.dsp.io.jvm.*;
import be.tarsos.dsp.util.fft.FFT;
import be.tarsos.dsp.filters.LowPassSP;
import be.tarsos.dsp.filters.LowPassFS;
import be.tarsos.dsp.filters.*;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.io.PipedAudioStream;

public class Detector {

   static final int CUTOFF = 20; // 20
   static final float MEAN = 1.4f; // 1.45f;
   static final float DST = 0.4f; // 0.1

   public static ArrayList<Float> load(String[] args) {
      

      File f = null;
      AudioDispatcher dispatcher = null;
      final FFT fft = new FFT(1024);

      try {
         f = new File("samson.mp3");
      } catch (Exception e) {
         System.out.printf("Unable to open %s\n", args[0]);
         return null;
      }

      try {
         dispatcher = AudioDispatcherFactory.fromPipe(f.getName(), 44100, 1024, 3);
      } catch (Exception e) {
         System.out.printf("%s decoding error\n", f.getName());
         return null;
      }
      

      /* copied from http://www.badlogicgames.com/wordpress/?cat=18 */
      /* thanks badlogic games */
      
      final ArrayList<Float> output = new ArrayList<Float>();
      
      /*
       * Beat Detection
       * 
       */
      
      AudioProcessor beats = new AudioProcessor() {

         float[] spectrum = new float[1024 / 2 + 1];
         float[] lastSpectrum = new float[1024 / 2 + 1];
         ArrayList<Float> spectralFlux = new ArrayList<Float>();
         ArrayList<Float> threshould = new ArrayList<Float>();
         ArrayList<Float> pruned = new ArrayList<Float>();
         ArrayList<Float> peaks = new ArrayList<Float>();
       

         @Override
         public boolean process(AudioEvent audioEvent) {
           float[] buff = audioEvent.getFloatBuffer();
           fft.forwardTransform(buff);

            /* update float buffers */
            System.arraycopy(spectrum, 0, lastSpectrum, 0, spectrum.length);
            System.arraycopy(buff, 0, spectrum, 0, spectrum.length);

            /* calculate flux */
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

            /* Use threshould to eliminate noise */
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

            /* disregard beats thatare not equal to or above the threshould */
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

         };
      };
      
      //dispatcher.addAudioProcessor(new HighPass(200.f, 1024));
      dispatcher.addAudioProcessor(new LowPassFS(100.0f, 1024));
     // dispatcher.addAudioProcessor(new HighPass(500.0f, 1024));

      dispatcher.addAudioProcessor(beats);
      dispatcher.run();
      return output;

   }
   
   public static void main(String[] args) {
      Detector.load(args);
   }
}
