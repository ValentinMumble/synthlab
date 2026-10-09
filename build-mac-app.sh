#!/bin/bash
# Builds a standalone Synthlab.app (Java runtime included) into target/app,
# plus dist/Synthlab-macOS.zip (the app) and dist/Synthlab.jar (any OS with Java 21).
# Needs JDK 21+ (for jpackage) and Maven: brew install openjdk@21 maven
set -euo pipefail

cd "$(dirname "$0")"
export JAVA_HOME="${JAVA_HOME:-/opt/homebrew/opt/openjdk@21}"

mvn -q -B package -DskipTests

# Turn the app icon into a macOS .icns
iconset=target/Synthlab.iconset
rm -rf "$iconset" && mkdir -p "$iconset"
for size in 16 32 128 256 512; do
    sips -z "$size" "$size" src/main/resources/pics/synthlab_icon.png --out "$iconset/icon_${size}x${size}.png" >/dev/null
    double=$((size * 2))
    sips -z "$double" "$double" src/main/resources/pics/synthlab_icon.png --out "$iconset/icon_${size}x${size}@2x.png" >/dev/null
done
iconutil -c icns "$iconset" -o target/Synthlab.icns

rm -rf target/app target/jpackage-input
mkdir -p target/jpackage-input
cp target/synthlab-0.0.1-SNAPSHOT.jar target/jpackage-input/

"$JAVA_HOME/bin/jpackage" \
    --type app-image \
    --name Synthlab \
    --app-version 1.0.0 \
    --input target/jpackage-input \
    --main-jar synthlab-0.0.1-SNAPSHOT.jar \
    --main-class fr.istic.synthlab.Synthlab \
    --icon target/Synthlab.icns \
    --add-modules java.desktop,java.logging,java.sql,java.xml \
    --jlink-options "--strip-debug --no-man-pages --no-header-files" \
    --java-options "-Dapple.awt.application.name=Synthlab" \
    --dest target/app

mkdir -p dist
cp target/synthlab-0.0.1-SNAPSHOT.jar dist/Synthlab.jar
rm -f dist/Synthlab-macOS.zip
# ditto keeps the app bundle's symlinks and permissions intact
ditto -c -k --keepParent target/app/Synthlab.app dist/Synthlab-macOS.zip

echo "Built target/app/Synthlab.app, dist/Synthlab-macOS.zip and dist/Synthlab.jar"
echo "Install it with: cp -R target/app/Synthlab.app /Applications/"
