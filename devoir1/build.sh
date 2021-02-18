mkdir .binaries
javac -d ./.binaries ./src/client/*.java
javac -d ./.binaries ./src/server/*.java
cd ./.binaries
jar cvmf ../src/client/META-INF/MANIFEST.MF Client.jar client
jar cvmf ../src/server/META-INF/MANIFEST.MF Server.jar server
read -p "Press [Enter] to finish..."