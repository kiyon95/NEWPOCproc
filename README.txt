POCproc (c) 2011 Thomas Herzog
===============================================

http://herzogonline.net/pocproc

POCSAG to Mail/Prowl/NMA/SMS Gateway


Running:

java -jar POCproc-x.y.jar [-server|-editonly] [<configpath>]


Options

-server:        run without GUI - no way to configure anything, server reloads RICs/SUBRICs/Persons/Mail/NMA/Prowl/SMS Settings every 5 Minutes
-editonly:      run GUI but do not connect Crusader / POC32 - only for changing configuration

<configpath>:   read and save configuration files from there instead of default location


By default all configuration files are stored in ~/.pocproc (UNIX) or %USERPROFILE%/pocproc (WINDOWS)

If running without existing config files, some error messages occur in the terminal, just ignore them. They
will be gone if you at least once pressed "Store all Settings" in the GUI.