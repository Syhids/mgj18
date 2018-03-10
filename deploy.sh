#!/bin/bash
./gradlew desktop:dist
mkdir -p ./bin
mv desktop/build/libs/desktop-1.0.jar ./bin/game.jar
cp play.sh ./bin/
cp play.bat ./bin/

mkdir -p ./bin/assets/
cp assets/* ./bin/assets/
cd bin
rm -f game.zip
zip -r game.zip .
#rm -f game.jar
#nautilus . &
cd ..
echo "Done"