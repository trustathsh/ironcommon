### 1. modyfie the license-plugin:

* create the file "Dummy.java" at the path `/src/license/dummy/Dummy.java` ine your project
* open the "pom.xml" and go to the part of the license-plugin
* add the following lines:
`<canUpdateCopyright>true</canUpdateCopyright>`
`<root>src/license/dummy</root>`
* than run the license-plugin
   `license:update-file-header`

----------------------------------------------------------

### 2. integrate the maven-checkstyle-plugin

add the following code to the `pom.xml`
```XML
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>2.10</version>
    <configuration>
        <propertyExpansion>headerfile=${project.basedir}/src/license/dummy/Dummy.java</propertyExpansion>
        <configLocation>https://raw.githubusercontent.com/trustathsh/ironcommon/master/src/main/documents/trustAtHsHCheckstylePolicy.xml</configLocation>
    </configuration>
</plugin>
```

run the maven-checkstyle-plugin with the command 
`checkstyle:checkstyle`

to generate a HTML-Overview run first the command 
`site`
and than the command 
`checkstyle:checkstyle`
now you can find the overview at the path: `target/site/checkstyle.html`

----------------------------------------------------------

### 3. integrate the eclipse-checkstyle-plugin

just add the `.checkstyle`-File from a already existing trust project into the 

basedirectory from your proyect (e.g. 

https://raw.githubusercontent.com/trustathsh/ironcommon/master/.checkstyle)

check that the path for the hederfile points to the `Dummy.java`-file