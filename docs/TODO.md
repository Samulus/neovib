# BUGS
* Including a symlink to a folder that includes itself causes infinite recursion
* If you run the program on Windows / OSX / Linux and you're missing ffmpeg TarsosDSP will attempt
to download it for you, the problem is the game is single threaded and this will cause it to hang,
the workaround is to wait for it to finish downloading (there is no visual indicator but the ffmpeg bins are like 30mb
just wait)  and then restart the game.
in the browser when selecting a music library
* If you enter an invalid directory there is no way to get back
* Java sound is a mess and doesn't work on Windows / some linux Systems (not my fault ;\)

# FEATURES TO ADD
* Modify the ui.FileBrowser module so that it uses a stack to remember the last location
the user was at

# REFACTORING
* Remove .pass method and use more DataQueues and EventQueues to make passing
data among modules more modular
