# neovib
neovib is a simple rhythm game inspired by classic rhythm games like Osu!
and Vib-Ribbon. In Neovib the user provides an audio file / selects a random
audio file from their music library and the game will automatically generate
obstacles that coincide with musical onsets in the game. It's in an early state
currently but works on Linux. 

# Screenshot
![neovib](docs/screenshot.png)

# Controls

|Button | Action|
|-------|-------|
|k      | Travel up in a menu|
|j      | Travel down in a menu|
|m      | Mark a directory (used for marking your music librray)|
|q      | Cancel the current action / return to the previous screen|
|a      | Press when a Cross is coming |
|s      | Press when a Circle is coming |
|d      | Press when a Square is coming |
|f      | Press when a Triangle is coming |
|Esc    | Quit Game|

# Try It
Neovib requires java8. The project doesn't correctly import in Eclipse at the moment but works 
fine in Intellij. You can try it without compiling it by downloading the folder dist.zip but
JSound / JavaSound is *inherently broken* on multiple platforms and may not work unless you do
device specific hacks to get it functioning. I'm planning on switching to OpenAL soon
to bypass this.