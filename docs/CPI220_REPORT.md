# Project Specification

> 1. What dataset or real-world application does your project relate to?

For my project I've decided to make a clone of an old game for the original 
Playstation 1 called Vib Ribbon. It's an esoteric and fun little rhythm game 
that allows you to use any music cd and generates a track of obstacles 
that the player must avoid in sync with the music.

> 2. What is the problem you are trying to solve or need are you trying to meet?

The game actually reads the audio, performs analysis on the raw audio data to determine 
the beats / rhythmic peeks of the song, and then generates a track 
with obstacles that correspond to the rhythmic peeks in the song. 
I'm assuming it does it in real time because there are no loading screens. 
Creating elegant tracks and doing good beat / rhythm detection for an 
arbitrary audio file is the real world dataset for this project. 
If anyone wanted to the game to play their entire music library they 
should be able to do so. 

I wanted to improve on the original algorithm by doing multiple passes on the audio waveform 
with different frequency filters so that I could see which instruments are most prevalent during different parts of the 
songs and tweak the tracks accordingly. This way it works on more genres of music and is generally more 
accurate at sensing the "overall rhythm" of a given track. 
The original algorithm in Vib-Ribbon had a tendency to generate subpar levels on noisy genres like 
Shoegaze, Metal, and generally "aggressive" music without a strong beat. 
This is one of the primary things I intend to improve. 

> 3. How did you solve this problem or meet this need? What is the basic functionality of your project? 

What I ultimately ended up doing wasn't multiple passes but a single pass using an Onset detection algorithm
that records when the peeks in the song occur and stores them in a LinkedList. We then
pass this LinkedList to the Game and BeatKonducta modules so that we can dynamically add 
obstacles for the player to dodge in real time.

> 4. Explain the knowledge you demonstrated as part of this project. You should touch on at least 5 algorithms or data structures used in your code design.

I used ArrayLists, LinkedLists, Queues, Sets, Graphs, and Primitive Arrays throughout
the assignment for almost every single thing. There are examples in CPI220_RUBRIC.MD

> 5. Explain 5 skills you demonstrated as part of this project. Reference specific examples from your implementation or design process.

I used Sorting, Graphs, Server / Client, Algorithm Analysis, and Dealing With Buggy Libraries.
There are examples in CPI220_RUBRIC.md

> 6. What external libraries or tools did you use to make your project more interesting and successful? How did they shape your thought process? What challenges did you have, and how did you try to overcome them?

I used Processing and TarsosDSP and FFMPEG which was good. TarsosDSP uses JSound internally though
which was bad and proved to be super buggy. I ultimately couldn't find a cross platform
solution but was able to fix it on my Laptop and Desktop. There are more details in CPI220_Rubric.md

Also there IS support for showing similar artists to the one that was just played. I used a library
by this cool company called Echonest that allows you to query similar artists if you have a valid API KEY.
I didn't include it in this distribution but you can register for one. For free. In order to use it
you have to create a textfile in the games root directory called apikey.txt that contains the api key on the first line.
Then you have to set  a music path in the  game. Then everytime you play a song
and get to the end screen it will show similar artists using a graph.

> 7. Describe the organization of your code. In what ways do you think it is well organized? In what ways could the organization be improved? To answer this questions, imagine if you had to fix bugs or add new functionality to your code: would it be easy to do so?

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


The game is pretty modular and not too hard to follow.

> 8. What approaches or solutions did you consider to your problem? Why did you decide on the approach you ultimately took?

Ultimately I went with the Onset note detection method vs the raw frequency / bass detection
because it was more accurate. I went into more detail in CPI220_RUBRIC.md

> 9. 9. Does your code always return a correct response? Is it efficient? Explain your answers, and discuss in what ways you think its performance could be improved.

Due to the nature of beat detection it can't always return a correct result. But in the majority of cases the algorithm does return a correct result in the vast majority of cases.
I tried almost every single song in my 26GB library of music across generes and it generated a satisfying playthrough in about 85-90% of cases.
I went into more detail about this in CPI220_RUBRIC.MD

One way I would improve the algorithm is basically to detect large gaps of time
between two beats. So if we see that there were 5 seconds between two given beats we could do
a second pass with more sensitivty and see if there are more beats "hidden" within those beats.
I haven't actually tried implementing this yet but it's just a thought. If it worked
we could generate "harder" tracks. 
