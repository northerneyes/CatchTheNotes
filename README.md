![alt tag](https://raw.githubusercontent.com/northerneyes/CatchTheNotes/master/Content/Feature%20Graphics.png)
##Game
Catch The Notes is a libgdx game. It has beautiful music and art.

You need to catch colorful raindrops with different types of shape: music notes, floral, sky and abstract in rhythm of the music.

Game has several musical compositions that provides a unique gaming experience, because the behavior of raindrops depends on the music.

Also game provides to load existing music files from a phone like media player.

## Project Structure
* **CatchTheNotes** project. The core of the game. This is non-Android library using only libgdx and several other libraries.
Separated business logic from a particular platform allow to us run the game like desktop application on Windows/Max OS X/Linux or run the game on Android. It is very helpful to test application and maintain a code. 
* **CatchTheNotes-desktop** project. This is the desktop application project to run game on Windows/Max OS X/Linux, depends on the *CatchTheNotes* project.
* **CatchTheNotes-android** project. This is a abstract android application project. We need it for build free or full version of the game later. Depends on the *CatchTheNotes* project
* **CatchTheNotes-android-free** project. This is a free version of our game. We publish this project to a market. It depends on the *CatchTheNotes-android* project.
* **CatchTheNotes-android-full** project. This is a full version of our game. We publish this project to a market. It depends on the *CatchTheNotes-android* project.
