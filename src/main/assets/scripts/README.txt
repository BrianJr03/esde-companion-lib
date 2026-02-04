ES-DE Companion Integration Scripts

These scripts enable communication between ES-DE and the ES-DE Companion app.

Installation:
- Run the setup wizard in ES-DE Companion settings
- Scripts will be automatically copied to ES-DE's scripts directory
- Configure ES-DE to use these scripts in its settings

Script Descriptions:
- esdecompanion-game-select.sh: Triggered when a game is highlighted
- esdecompanion-game-start.sh: Triggered when a game is launched
- esdecompanion-game-end.sh: Triggered when a game exits
- esdecompanion-system-select.sh: Triggered when browsing systems
- esdecompanion-screensaver-start.sh: Triggered when screensaver starts
- esdecompanion-screensaver-end.sh: Triggered when screensaver ends
- esdecompanion-screensaver-game-select.sh: Triggered during screensaver

Arguments:
Scripts receive 1-3 arguments depending on the event:
- $1 = filename/path or event type
- $2 = game name for game events
- $3 = system name for game events

Log Directory:
/storage/emulated/0/ES-DE Companion/logs/

All scripts write event data to text files in the log directory for the ES-DE Companion app to monitor.
