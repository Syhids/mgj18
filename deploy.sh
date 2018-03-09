#!/bin/bash
./gradlew desktop:dist
mv desktop/build/libs/desktop-1.0.jar ./bin/game.jar
cp play.sh ./bin/
cp play.bat ./bin/
cd bin
rm game.zip
zip -r game.zip .
rm game.jar
rm play.bat
rm play.sh
nautilus . &
cd ..
echo "Done"