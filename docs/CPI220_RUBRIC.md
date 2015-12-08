# CPI 220 Project Rubric

> The README and related documentation clearly outlines test cases, known bugs, and how to install and run the application. Materials enable us to run and test the code.
> The project successfully includes use of a real dataset and external library.
> The project implements an interesting, novel, or useful solution to a real-world problem or need. It is of reasonable scope for the size of the team.

I included a classical song that you can use to test it.
You can also test the game with any music of your own. Neovib accepts everything ffmpeg 
accepts, which is essentially anything with an audiotrack. The project was
definitely within reasonable scope. The only thing I wish I had more time
is so that I could implement more tests and refactor a few design elements
of the project.

> The project properly uses a minimum of 5 algorithms and data structures learned in the course.
Yes, Neovib uses > 5 algorithms and data structures in its entirety.

> The project properly demonstrates a minimum of 5 skills acquired in the course.

* Sorting
* Graphs
* Server / Client interfaces
* Dealing with a Buggy Library (Employment Readiness Skills?)
* Analyzing Multiple Solutions to a problem.

> Code is well-designed and adheres to principles of modular programming.
> Team member made commits on a regular basis to Git, If a student worked alone, he or she still used good Git practices.

✓

> Code performs well. It runs efficiently without using unnecessary space.
> Code returns a correct result for all cases.

Due to the nature of beat detection it can't always return a correct result. But in the majority of cases the algorithm does return a correct result in the vast majority of cases.
I tried almost every single song in my 26GB library of music across generes and it generated a satisfying playthrough in about 85-90% of cases.

The algorithm for beat detection only does a single pass of the audio, taking only a few ms on my desktop and about
1000ms-2000ms max on my Chromebook to do the onset detection for most songs.
