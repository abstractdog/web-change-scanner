**Deploying the tester app**

```bash
# on your machine
#docker login #only once
docker build --no-cache --tag=abstractdog/web-change-scanner --file docker/Dockerfile .
docker push abstractdog/web-change-scanner

# on production server
docker pull abstractdog/web-change-scanner
#start with docker-compose commands
```


**Setting up environment and running CLI tool**

```bash
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo dpkg -i google-chrome-stable_current_amd64.deb
/opt/google/chrome/google-chrome #try to start google chrome

mvn clean install -DskipTests
mvn dependency:copy-dependencies
java -cp target/classes:target/dependency/* com.abstractdog.web.change.scanner.Runner
```


