# BUGS
* Including a symlink to a folder that includes itself causes infinite recursion
in the browser when selecting a music library
* If you enter an invalid directory there is no way to get back
* Java sound is a mess and doesn't work on Windows / some linux Systems (not my fault)

# FEATURES TO ADD
* Modify the ui.FileBrowser module so that it uses a stack to remember the last location
the user was at

# REFACTORING
* Remove .pass method and use more DataQueues and EventQueues to make passing
data among modules more module
