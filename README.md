# Project-Zombie
### Building and dependancies
Let's aim to build on Java 1.8 and Spigot 1.8.3. The pom dependancies should look like as such:
```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>2.5.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
    </plugins>
</build>

<dependencies>
    <dependency>
        <groupId>org.spigotmc</groupId>
        <artifactId>spigot</artifactId>
        <version>1.8.3-R0.1-SNAPSHOT</version>
        <type>jar</type>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.spigotmc</groupId>
        <artifactId>spigot-api</artifactId>
        <version>1.8.3-R0.1-SNAPSHOT</version>
        <type>jar</type>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
### Documentation
We should also properly document our code and push that documentation during the build process. Your build process should contain at a minimal:
```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>2.5.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-javadoc-plugin</artifactId>
            <executions>
                <execution>
                    <id>attach-javadocs</id>
                    <goals>
                        <goal>jar</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
