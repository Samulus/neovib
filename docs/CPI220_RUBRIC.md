# CPI 220 Project Rubric

> The README and related documentation clearly outlines test cases, known bugs, and how to install and run the application. Materials enable us to run and test the code.
> The project successfully includes use of a real dataset and external library.
> The project implements an interesting, novel, or useful solution to a real-world problem or need. It is of reasonable scope for the size of the team.

I included several songs in the dst folder that can be played in the game to
make sure that it is reliably functioning. These are a mix of neo-classical
music and hip-hop songs from two artist on Jamendo to illustrate that
the onset detection algorithm works with a variety of music. You can also 
test the game with any music of your own. Neovib accepts everything ffmpeg 
accepts, which is essentially anything with an audiotrack. The project was
definitely within reasonable scope. The only thing I wish I had more time
is so that I could implement more tests and refactor a few design elements
of the project.

> The project properly uses a minimum of 5 algorithms and data structures learned in the course.

Neovib makes extensive use of:

* ArrayLists
  * Used in FileBrowser module to list files
  * Used in Menu module to list files
  * Used in Beat Detection algorithm to store track peek information
  * Various uses
* LinkedLists
  * Used in Game and Beat Detection modules
  * Various uses 
* HashTables
  * Used in the Graph for the Adjaceny List
  * Used in the Scene module for storing eachgame Scene
* Graphs
  * Used for looking up artists similar to the one just played.
* Queues
  * The main game input queue
  * The incoming beats in the game
* Sets
  * Used to keep track of similar Aritsts

Plus many more unmentioned uses.

> The project properly demonstrates a minimum of 5 skills acquired in the course.

* Sorting
  * We use Timsort in the FileBrowser module via the Collections.sort method to 
    automatically sort filesystem information so that paths are displayed in order.

* Graphs
  * If the user provides a valid Echonest APIKEY in a textfile called apikey.txt
    in the root directory on the first line and then subsequentally sets a music
    library path in the game, then everytime the user pauses the game / ends a track
    we use the awesome Echonest API to look up artists similar to the one just played
    and display those to the right. The information is stored in a graph offline and loaded
    into a graph during gameplay.

* Server / Client interfaces
The games various screens are referred to as "Scenes" internally in the game.
Each Scene is kind of like a server in that it accepts input from the main client
class, Scene.java. This way we can design each scene using a Callback system so
that we can simply tell the client to switch the scene and the servers will change
without caring about each other. Please see the Scene package and the main Neovib gameloop
for more clarity.

* Dealing with a Buggy Library (Employment Readiness Skills?)
Background: http://stackoverflow.com/a/20962169

I'm using TarsosDSP which is an awesome library but relies on
AudioSystem.getLine() directly to determine where it should start
playing the audio. The problem with this (which is outlined in that
StackOverflow post) is that it doesn't always find the proper line.
On my Desktop it always found the correct line but on my Chromebook
it would crash with the error 

> javax.sound.sampled.LineUnavailableException: line with format PCM_SIGNED 44100.0 Hz, 16 bit, stereo, 4 bytes/frame, little-endian not supported.

Everytime. I tried implementing the fix there but it *still* wouldn't work. Ultimately what
I setteled on was overloading the constructor so that I could modify how I called it
on my Chromebook and it would setup my AudioPlayer device correctly on there. It's ugly
and device specific but works for the purposes in the inclass demo. The real solution would
be to switch to OpenAL / stop using Java entirely. 

* Analyzing Multiple Solutions to a problem.

Originally when I first started protyping the game I was using Processing and
an audio library for it called Minim. Minim has a built in beat detection module
called BeatListener. BeatListener works by basically performing a Fast Fourier Transformation
on the audio in real time and triggering a "beat" if the current frequency is low enough to constitute
as a beat. Essentially what its doing is looking for peeks in the bass drum. The problem
with this that I noticed quickly was that this technique was unreliable and completely
fell apart for music that lacked a bass drum, such as classical music.

After much searching for techniques on how to do reliable beat detection
across a variety of genres I discovered an article by the group BadLogicGames
that came up with an awesome technique on how to do it. A big over simplification
of how it works (you can read the article for more details its very interesting) is that
basically for a certain window of time throughout the song we analyze the average amount of varation
in the "energy" of the song and detect sudden spikes in loudness. These peaks
almoat always map to kick drums, piano hits, vocal influxtions, or other rhthymic
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

> Code is well-designed and adheres to principles of modular programming.
> Team member made commits on a regular basis to Git, If a student worked alone, he or she still used good Git practices.

✓

> Code performs well. It runs efficiently without using unnecessary space.
> Code returns a correct result for all cases.

I can't say that it runs perfectly due to the nature of BeatDetection. But 
it absolutely does work well for a multitude of genres.  Performance wise
we do various techniques to keep things speedy. The most significant performance
optimization is that everytime a beat scrolls offscreen we actually pop that element
from memory. This way only the things that are onscreen are rendered and nothing else is.

All of the algorithms in the code are basically proportional to n (with the exception
of Timsort and stuff). The main detection algorithm iterates over each sample exactly once 
so the amount of time it takes for it to run corresponds to the length of the song linearlly.