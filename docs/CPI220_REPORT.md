# Project Specification

> 1) What dataset or real-world application does your project relate to?

I decided to make a clone of an old video game called Vib-Ribbon. This was my original plan but ultimately
the game became more of an Osu! clone / BitTripRunner type of game. The important part is that the core goal of the
project, generating beats that coincide with rhythmic portions of audio across a variety of genres, was satisfied. 

> 2) What is the problem you are trying to solve or need are you trying to meet?
The original Vib-Ribbon game generated poor tracks for any music that wasn't incredibly simplistic because of the
way the detection algorithm worked. I wanted to create a game that accepted custom tracks and generated enjoyable
tracks based off the various types of music the game could be provided.

> 3) How did you solve this problem or meet this need? What is the basic functionality of your project? 

Ultimately I ended up using an Onset Detection algorithm engineered by the great folks at BadLogicGames and adapted
it to work with the library TarsosDSP + added several fixes. The game's audio package essentially performs this onset 
detection, hands it off to the BeatKonducta module, and then that module is responsible for generating the obstcales
that conincide with the music during gameplay. In a nutshell neovib is a video game that allows you to input your own
music and press buttons that coincide with the musical onsets in the song by pressing buttons that correspond to 
onscreen geometric shapes.

> 4) Explain the knowledge you demonstrated as part of this project. You should touch on at least 5 algorithms or data structures used in your code design.

I used many, many, many data structures and algorithms throughout the project. 

In the Audio package we use
ArrayLists extensively in the Detector.java module. I chose ArrayLists here because we don't know how many peeks / samples
/ spectral fluxations will occur ahead of time without very complex calculations and a LinkedList isn't well suited
to hold a jillion numbers like an ArrayList is.

There are also many usages of LinkedLists in the BeatKonducta module where we determine when to actually
send a shape onto the screen for rendering.

In the EQ module in the Event Package we pump the various events that happen in the game into a main
game EventQueue. This way the game module can just dequeue each event and do the appropriate action
in order.

In the MusicDB module we use a Graph and serialize that offline for every SimilarArtist the game
can load. Then if we were able to successfully connect to Echonest we display it in game.

In the MusicDB module we also return the similarArtists found in a TreeSet / use sets
so that we can take advantages of self balancing data structures / unique element only data structures.

> 5) Explain 5 skills you demonstrated as part of this project. Reference specific examples from your implementation or design process.

I'll start with the simplest stuff and work my way up.

* Sorting
   * In the ui.FileBrowser module we use Collections.sort which uses Timsort internally
     to actually display the contents of each directory in the FileBrowser in the correct lexographical
     order.
     
 * Graphs
   * If the user creates a file called apikey.txt with an API key for the EchonestAPI in the game's root folder
     AND they set a music path in the game using the set library path function in the title screen then we will
     render a list of Artists similar to the one they just played to the right of the main pause menu. It's disabled
     by default because the game is single threaded and blocks everytime the network connection is made
     which gets annoying. But the functionality is there!
 
 * Server / Client Interfaces
   * The Game's scene system is very similar to a server client interface. In Neovib each Scene registers itself
     with the main Scene class and then the main Scene class is responsible for calling each Game scene's callbacks like
     render, logic, pass, and input. This allows us to encapsulate functionality and keep the game modular in scope. 
   
 * Dealing with Difficult Situations / Buggy Software (Employment Readiness Skills in Rubric)
   * I decided to use TarsosDSP because it allows me to very easily perform digital signal processing
     on the games audio and to play it back using FFMPEG with very low latency. And it worked really
     great on my desktop! The problem is that TarsosDSP uses a JavaSound function called
     AudioSystem.getLine() to determine where it should start playing audio. The problem is that
     this method is **inherently broken** (http://stackoverflow.com/a/20962169). It doesn't actually
     always get the Line on the system that is capable of playing back audio so it just snatches the first
     one it sees without testing it. This led to a problem where everything was peachy keen on my desktop
     but the audio playback would crash with the error: 
     
     > javax.sound.sampled.LineUnavailableException: line with format PCM_SIGNED 44100.0 Hz, 16 bit, stereo, 4 bytes/frame, little-endian not supported.
     
     Every single time I tried to play a song. The problem was that I was using the single jar file TarsosDSP provides
     and I needed to actually modify one of its internal functions in AudioPlayer.java so that it would incorprate this fix.
     So I extracted the jar and implemented the changes but still nothing. I tried several solutions and tried implementing it 
     in multiple ways but ultimately nothing worked except for one fix. I essentially overloaded the AudioPlayer constructor
     in TarsosDSP so that if I removed the buffersize it would automatically jump to the one sound device that worked on my
     System, otherwise if passed the buffersize in it would play on the default sounddevice (and do the default TarsosDSP behavior).
     For the purposes of the inclass demo the code in that version is using the overloaded constructor. The code
     in the version currently in the dst/ folder and in this github repo is using the default TarsosDSP behaviour.
     So if it doesn't work on your platform its because of JavaSound lying to TarsosDSP about the first soundcard on your
     system. I only noticed this problem late into development so I didn't have enough time switch out the library
     for OpeanAL or find another solution.
     
   * I ran into another bug with getting the note to scroll accurately with the music. I ran into a problem where
     the audio would slowly become desynchronized as the game went on and on. When this problem was happening the 
     audio module was solely relying on TarsosDSP's secondsDispatched method which allows me to get the current position
     in the song. After doing much searching I came across this reddit post (https://www.reddit.com/r/gamedev/comments/13y26t/how_do_rhythm_games_stay_in_sync_with_the_music/c78aawd)
     which made me aware of the fact that the audio will natrually drift unless I use another timer to keep them on track. Everytime
     the amount of time drift between the two begins to drift we just average them and it helps keep things on track.
     This helped alleviate a lot of problems but ultimately after about 40 seconds of gameplay the audio lag seemed to keep
     accumulating anyway. At this point in the code I was only using floats everywhere to do all the calculations because
     I was used to using them in C. I decided on a whim to switch all of the logic to doubles and immediately the drift went 
     completely away. I was amazed! So that was a difficult bug to track down.
     
   * When I was writing the game's input detection I ran into a problem where consecutive notes that were 30-40ms away
     from each other could be reliably detected but anything closer would result in the second note being missed. I spent
     several days working on this until I ultimately figured out what was wrong. The problem was that whenever a button was pressed
     Neovib's input module would fire an event that basically said "hey a VibEvent.PLAYER_DODGE_CIRCLE" event just happened. The problem
     was that moving this message throughout the games subsystems was accumulating enough milliseconds of lag that it was actually causing
     the input lag to appear! I fixed it by providing methods in the Input module so that the Game module could directly query the internal
     boolean array thta the Input module has and check immediately if the key was down. This eliminated all input lag.
     
      
     
  * Analyzing Multiple Solutions to a Problem 
    * Orignally when I first began prototyping the game I was planning on using a library for Processing called Minim 
      and using it's own built in BeatDetection module. The problem with the BeatDetection in minim is that it was only
      realtime, it didn't support offline analysis like I needed. At least not in a trivial to implement way. It was also too
      inaccurate for real time control. Finally the way the beat detection worked was unreliable because it only looked at amplitute
      of a specific frequency in the audiofile (typically the bass drum). This would work alright on very bassy
      tracks but would completely fall apart the second anything that lacked a bass drum in it would play. This wasn't
      a good solution at all so I had to scrap it.
     
      After much searching for techniques on how to do reliable beat detection
      across a variety of genres I discovered an article by the group BadLogicGames
      that came up with an awesome technique on how to do it. A big over simplification
      of how it works (you can read the article for more details its very interesting) is that
      basically for a certain window of time throughout the song we analyze the average amount of 
      variation in the "energy" of the song and detect sudden spikes in loudness. These peaks
      almoat always map to kick drums, piano hits, vocal influxtions, or other rhythmic
      indicators in music. I adapted the source code they provided to work with TarsosDSP
      + FFMPEG and modified it to discount beats that were within 0.25 ms of each other.
      I also modified the sensitivty of the algorithm so it would give more hits in game.
      Determining how to get reliable onset detection working was probably the hardest
      part of the Assignment and took maybe 1-3 weeks by itself.

      I watched this video: https://wiki.xiph.org/Videos/A_Digital_Media_Primer_For_Geeks
      by the Xiph.Org foundation and it made my understanding of how digital audio works
      infinitely clearer. I reccomend watching that video first and then reading the
      tutorial to get a better understanding of how the beat detection in neovib works.
      http://www.badlogicgames.com/wordpress/?p=122
    
    * When it came to actually scrolling beats in time with the music I was a bit perplexed on how to do it.
      I sat down and basically sketched out all the information I knew I had about each obstacle. If
      we know the position of every beat, the distance between where we are now and where we need to scroll to, 
      the framerate of the game and the current position in the song we can actually calculate how many pixels per second
      it would take a shape to reach the player. After much trial and error I came up with the calcSpeed method
      in BeatKonducta.java. After implementing it I realized that it would probably more efficent to calculate where
      all the beats needed to be ahead of time but I didn't have time to implement this.
      
    * Finally when I got the input working and the notes scrolling succesfully I noticed there was a problem where
      the input would work but only if the user pressed the note early / exactly on time. The moment a note would cross
      the player line we removed this Beat from the game queue and basically had no way of detecting if the player had 
      hit the beat slightly late. I fixed this by keeping track of the lastBeat that was popped from the Beat list / queue.
      If the user presses dodges the right shape and they're within at least +- 40ms of the next or previous beat then the
      game marks it as a valid hit.
    


> 6) What external libraries or tools did you use to make your project more interesting and successful? How did they shape your thought process? What challenges did you have, and how did you try to overcome them?

I used Processing and TarsosDSP and FFMPEG which was good. TarsosDSP uses JSound internally though
which was bad and proved to be super buggy. I ultimately couldn't find a cross platform
solution but was able to fix it on my Laptop and Desktop.

Also there IS support for showing similar artists to the one that was just played. I used a library
by this cool company called Echonest that allows you to query similar artists if you have a valid API KEY.
I didn't include it in this distribution but you can register for one. For free. In order to use it
you have to create a textfile in the games root directory called apikey.txt that contains the api key on the first line.
Then you have to set  a music path in the  game. Then everytime you play a song
and get to the end screen it will show similar artists using a graph.

I enumerated a lot of the challenges up above.

> 7) Describe the organization of your code. In what ways do you think it is well organized? In what ways could the organization be improved? To answer this questions, imagine if you had to fix bugs or add new functionality to your code: would it be easy to do so?

The audio package is responsible for interfacing with TarsosDSP, detecting / manging incoming shapes, and actually performing
the onset detection analysis on a provided audiofile

The clock package allows us to use timers in various places in the program

The constants package WAS SUPPOSED TO BE used to avoid magic numbers all over the code
but I never got around to using it fully.

The event package is the games main event queue + stores all the events that can happen in the game
this way module scan just pump events into the queue and other modules can peek at them

The input package is responsible for detecting which keys are pressed in a low latency way.
Right now users have to wait at least 120ms between key presses to retrigger an event

The musicdb package contains code for randomly selecting another artist and interfacing with the Echonest library
so that it can show similar artists if a valid apikey is provided and if the music path has been set 
in game.

The primitives package contains code for drawing primitive shapes on the screen

The scene package consists of the games various scenes like the Title / Browser / In Game / Pause Screen / Device Menu

The ui package consists of elements that are similar to scenes but are lightweight and are injected in the current scene
These include things like the menu and file browser

The util package contains a single class Util that has a few small odd but needed functions


The game is pretty modular and not too hard to follow. All of these modules are annotated in greater detail directly
in the source code. The biggest thing I wish I had time for was to modify the Scene's callback system so that there
was a data queue that the scenes could pass data along instead of calling each others .pass methods. It would make things
easier to test / handle. I would have also included JUnit tests and more testing in general if I had more time.

> 8) What approaches or solutions did you consider to your problem? Why did you decide on the approach you ultimately took?

Ultimately I went with the Onset note detection method vs the raw frequency / bass detection
because it was more accurate. I went into more detail above.

> 9) Does your code always return a correct response? Is it efficient? Explain your answers, and discuss in what ways you think its performance could be improved.

Due to the nature of beat detection it can't always return a correct result. But in the majority of cases the algorithm does return a correct result in the vast majority of cases.
I tried almost every single song in my 26GB library of music across generes and it generated a satisfying playthrough in about 85-90% of cases.

One way I would improve the algorithm is basically to detect large gaps of time
between two beats. So if we see that there were 5 seconds between two given beats we could do
a second pass with more sensitivty and see if there are more beats "hidden" within those beats.
I haven't actually tried implementing this yet but it's just a thought. If it worked
we could generate "harder" tracks. 
